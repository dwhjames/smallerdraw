package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import uk.ac.ox.softeng.dpa.smallerdraw.Visitor;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Geometry;
import uk.ac.ox.softeng.dpa.smallerdraw.util.RectangleUtils;

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
		Rectangle bounds = getBounds();
		if (isFilled()) {
			return bounds.contains(p);
		} else {
			Point nw = RectangleUtils.getNW(bounds),
			      sw = RectangleUtils.getSW(bounds),
			      ne = RectangleUtils.getNE(bounds),
			      se = RectangleUtils.getSE(bounds);
			// check the left, right, top, and bottom edges as line segments
			return Geometry.linePointIntersect(nw, sw, p) ||
			       Geometry.linePointIntersect(ne, se, p) ||
			       Geometry.linePointIntersect(nw, ne, p) ||
			       Geometry.linePointIntersect(sw, se, p);
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	protected Shape createShape() {
		return getBounds();
	}

}
