package uk.ac.ox.softeng.dpa.smallerdraw.tools;

import java.awt.Point;

import uk.ac.ox.softeng.dpa.smallerdraw.Disposable;
import uk.ac.ox.softeng.dpa.smallerdraw.Figure;
import uk.ac.ox.softeng.dpa.smallerdraw.Handle;
import uk.ac.ox.softeng.dpa.smallerdraw.Model;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.ConnectingLineFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.ConnectingLineFigure.ConnectingLineObserver;

/**
 * A tool for drawing connecting lines between handles.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see ConnectingLineFigure
 * @see Handle
 */
public class ConnectorTool extends EmptyTool {
	
	private Handle handle;
	private ConnectingLineFigure figure;

	public ConnectorTool(Model model) {
		super(model);
	}
	
	@Override
	public void onMouseDown(Point pos) {
		for (Figure figure : model.figuresReversed()) {
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
				figure = new ConnectingLineFigure(handle.getLocation(), pos, model);
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
			for (Figure figure : model.figuresReversed()) {
				if (this.figure != figure &&
					(endHandle = figure.handleAt(pos)) != null) {
					break;
				}
			}
			if (endHandle == null) {
				model.remove(figure);
			} else {
				figure.setEnd(endHandle.getLocation());
				
				ConnectingLineObserver startObs = figure.getStartObserver();
				ConnectingLineObserver endObs   = figure.getEndObserver();
				
				Disposable unsubscriber;
				unsubscriber = startHandle.subscribe(startObs);
				startObs.setUnsubscriber(unsubscriber);
				
				unsubscriber = endHandle.subscribe(endObs);
				endObs.setUnsubscriber(unsubscriber);
			}
		}
		handle = null;
		figure = null;
	}

}
