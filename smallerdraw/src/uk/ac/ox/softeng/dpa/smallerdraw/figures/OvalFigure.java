package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * A figure for ovals.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public class OvalFigure extends AbstractRectangularFigure {
	
	public OvalFigure() {
		super();
	}

	@Override
	public boolean contains(Point p) {
		if (isFilled()) {
			return createShape().contains(p);
		} else {
			Point c = this.getCenter();
			Rectangle rs = this.getBounds();
			rs.grow(-5, -5);
			Rectangle rb = this.getBounds();
			rb.grow(5, 5);
			long dx  = p.x - c.x;
			long dy  = p.y - c.y;
			long ws2 = rs.width  * rs.width  / 4;
			long hs2 = rs.height * rs.height / 4;
			long wb2 = rb.width  * rb.width  / 4;
			long hb2 = rb.height * rb.height / 4;
			
			// (2*dx / width)^2 + (2*dy / height)^2 = 1
			// point p must be outside smaller ellipse
			// and inside bigger ellipse
			
			return (dx * dx * hb2 + dy * dy * wb2 <= wb2 * hb2) &&
			       (dx * dx * hs2 + dy * dy * ws2 >= ws2 * hs2);
		}
	}
	
	@Override
	protected Shape createShape() {
		Rectangle r = this.getBounds();
		return new Ellipse2D.Float(r.x, r.y, r.width, r.height);
	}

}
