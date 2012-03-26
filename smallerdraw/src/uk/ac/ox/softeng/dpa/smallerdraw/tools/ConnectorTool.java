package uk.ac.ox.softeng.dpa.smallerdraw.tools;

import java.awt.Point;

import uk.ac.ox.softeng.dpa.smallerdraw.Handle;
import uk.ac.ox.softeng.dpa.smallerdraw.Figure;
import uk.ac.ox.softeng.dpa.smallerdraw.Model;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.LineFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Observer;

/**
 * A tool for drawing connecting lines between handles.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see LineFigure
 * @see Handle
 */
public class ConnectorTool extends EmptyTool {
	
	private Handle handle;
	private LineFigure figure;

	public ConnectorTool(Model model) {
		super(model);
	}
	
	@Override
	public void onMouseDown(Point pos) {
		for (Figure figure : model.figures()) {
			if ((handle = figure.handleAt(pos)) != null) {
				break;
			}
		}
		this.figure = null;
	}
	
	@Override
	public void onMouseDrag(Point pos) {
		if (handle != null) {
			if (figure == null) {
				figure = new LineFigure(handle.getLocation(), pos);
				model.add(figure);
			} else {
				figure.setEnd(pos);
			}
		}
	}
	
	@Override
	public void onMouseUp(Point pos) {
		if (figure != null) {
			Handle startHandle = handle;
			Handle endHandle = null;
			for (Figure figure : model.figures()) {
				if (this.figure != figure &&
					(endHandle = figure.handleAt(pos)) != null) {
					break;
				}
			}
			if (endHandle == null) {
				model.remove(figure);
			} else {
				figure.setEnd(endHandle.getLocation());
				startHandle.subscribe(new StartObserver(figure));
				endHandle.subscribe(new EndObserver(figure));
			}
		}
		handle = null;
		figure = null;
	}
	
	/**
	 * An observer to update the start location of this line figure.
	 */
	private final class StartObserver implements Observer<Point> {
		
		private final LineFigure figure;
		
		private StartObserver(LineFigure figure) {
			this.figure = figure;
		}
		
		@Override
		public void update(Point p) {
			this.figure.setStart(p);
		}
	}

	/**
	 * An observer to update the end location of this line figure.
	 */
	private final class EndObserver implements Observer<Point> {
		
		private final LineFigure figure;
		
		private EndObserver(LineFigure figure) {
			this.figure = figure;
		}
		
		@Override
		public void update(Point p) {
			this.figure.setEnd(p);
		}
	}

}
