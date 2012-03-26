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
 * @param <F> a subtype of {@link AbstractRectangularFigure}
 * @see AbstractRectangularFigure
 */
public class RectangularTool<F extends AbstractRectangularFigure> extends EmptyTool {
	
	private Point start;
	private F figure;
	private Class<F> type;

	public RectangularTool(Model model, Class<F> type) {
		super(model);
		this.type = type; 
	}

	@Override
	public void onMouseDown(Point pos) {
		start = pos;
		figure = createFigure();
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
	
	private F createFigure() {
		F result = null;
		try {
			result = type.newInstance();
		} catch (IllegalAccessException ex) {
			// cannot access class or nullary constructor
		} catch (InstantiationException ex) {
			// not an instantiable class with a nullary constructor
		}
		return result;
	}
}
