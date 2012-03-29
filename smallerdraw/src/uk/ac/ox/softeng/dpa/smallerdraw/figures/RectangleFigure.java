package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.Point;
import java.awt.Shape;

import uk.ac.ox.softeng.dpa.smallerdraw.util.Geometry;

/**
 * A figure for rectangles
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public class RectangleFigure extends AbstractRectangularFigure {

	public RectangleFigure() {
		super();
	}
	
	public RectangleFigure(RectangleFigure that) {
		super(that);
	}
	
	public RectangleFigure clone() {
		return new RectangleFigure(this);
	}
	
	@Override
	public boolean contains(Point p) {
		if (isFilled()) {
			return getBounds().contains(p);
		} else {
			Point nw = getNW(), sw = getSW(), ne = getNE(), se = getSE();
			// check the left, right, top, and bottom edges as line segments
			return Geometry.linePointIntersect(nw, sw, p) ||
			       Geometry.linePointIntersect(ne, se, p) ||
			       Geometry.linePointIntersect(nw, ne, p) ||
			       Geometry.linePointIntersect(sw, se, p);
		}
	}
	
	@Override
	protected Shape createShape() {
		return getBounds();
	}

}
