package uk.ac.ox.softeng.dpa.smallerdraw.tools;

import java.awt.Point;
import java.awt.Rectangle;

import uk.ac.ox.softeng.dpa.smallerdraw.Model;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.AbstractRectangularFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Geometry;

/**
 * A generic tool for drawing rectangular figures.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see AbstractRectangularFigure
 */
public class RectangularTool extends EmptyTool {
	
	private Point start;
	private AbstractRectangularFigure figure;
	private AbstractRectangularFigure prototype;

	public RectangularTool(Model model, AbstractRectangularFigure prototype) {
		super(model);
		this.prototype = prototype;
	}

	@Override
	public void onMouseDown(Point pos) {
		start = pos;
		figure = prototype.clone();
		figure.setRectangle(new Rectangle(pos));
		model.add(figure);
	}
	
	@Override
	public void onMouseDrag(Point pos) {
		figure.setRectangle(Geometry.rectangleFromPoints(start, pos));
	}
	
	@Override
	public void onMouseUp(Point pos) {
		if (Geometry.pythagoras(figure.getBounds().width, figure.getBounds().height)
				< AbstractRectangularFigure.MINWIDTH * AbstractRectangularFigure.MINHEIGHT) {
			model.remove(figure);
		}
		figure = null;
	}
}
