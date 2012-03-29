package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.Point;

import uk.ac.ox.softeng.dpa.smallerdraw.Disposable;
import uk.ac.ox.softeng.dpa.smallerdraw.Handle;
import uk.ac.ox.softeng.dpa.smallerdraw.Model;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Observer;

/**
 * A specialization of {@link LineFigure} for drawing connecting lines.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see LineFigure
 */
public class ConnectingLineFigure extends LineFigure {
	
	private Model model; // TODO we may need a getter and setter later 
	private StartObserver startObserver;
	private EndObserver endObserver;

	public ConnectingLineFigure(Point start, Point end, Model model) {
		super(start, end);
		handles = new Handle[0];
		this.model = model;
		initializeObservers();
	}
	
	public ConnectingLineFigure(ConnectingLineFigure that) {
		super(that);
		handles = new Handle[0];
		this.model = that.model;
		initializeObservers();
	}
	
	private void initializeObservers() {
		startObserver = new StartObserver(model);
		endObserver = new EndObserver(model);
		startObserver.setOther(endObserver);
		endObserver.setOther(startObserver);
	}
	
	public ConnectingLineFigure clone() {
		return new ConnectingLineFigure(this);
	}
	
	/**
	 * Get the observer that observes updates to the start
	 * of this connecting line.
	 * 
	 * @return an observer for the start of the line
	 */
	public ConnectingLineObserver getStartObserver() {
		return startObserver;
	}
	
	/**
	 * Get the observer that observes updates to the end
	 * of this connecting line.
	 * 
	 * @return an observer for the end of the line
	 */
	public ConnectingLineObserver getEndObserver() {
		return endObserver;
	}
	
	/** 
	 * Dispose of this connecting line by disposing of its
	 * start and end point observers.
	 * 
	 * @see AbstractFigure#dispose()
	 */
	@Override
	public void dispose() {
		startObserver.dispose();
		endObserver.dispose();
	}
	
	
	/**
	 * An {@linkplain Observer observer} for the start and end points
	 * of a connecting line. These observers are always created in pairs:
	 * one for the start and one for the end. If one receives a
	 * {@linkplain Observer#finished() finished} message, then this
	 * information is relayed to its twin. 
	 */
	public abstract class ConnectingLineObserver implements Observer<Point>, Disposable {
		protected final Model model;
		
		private Disposable unsubscriber;
		private ConnectingLineObserver other;
		
		private ConnectingLineObserver(Model model) {
			this.model = model;
		}
		
		protected void setOther(ConnectingLineObserver other) {
			this.other = other;
		}
		
		public void setUnsubscriber(Disposable unsubscriber) {
			this.unsubscriber = unsubscriber;
		}
		
		@Override
		public void finished() {
			other.dispose();
			model.remove(ConnectingLineFigure.this);
		}
		
		public void dispose() {
			unsubscriber.dispose();
		}
	}
	
	/**
	 * An observer to update the start location of this line figure.
	 */
	private final class StartObserver extends ConnectingLineObserver {
		
		private StartObserver(Model model) {
			super(model);
		}
		
		@Override
		public void update(Point p) {
			ConnectingLineFigure.this.setStart(p);
		}
	}

	/**
	 * An observer to update the end location of this line figure.
	 */
	private final class EndObserver extends ConnectingLineObserver {
		
		private EndObserver(Model model) {
			super(model);
		}
		
		@Override
		public void update(Point p) {
			ConnectingLineFigure.this.setEnd(p);
		}
	}

}
