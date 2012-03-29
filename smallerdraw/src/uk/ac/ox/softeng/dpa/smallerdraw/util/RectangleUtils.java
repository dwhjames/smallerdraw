package uk.ac.ox.softeng.dpa.smallerdraw.util;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public abstract class RectangleUtils {
	public static Point getCenter(Rectangle r) {
		Point p = r.getLocation();
		p.translate(r.width / 2, r.height / 2);
		return p;
	}

	public static Point getN(Rectangle r) {
		Point p = r.getLocation();
		p.translate(r.width / 2, 0);
		return p;
	}

	public static Point getS(Rectangle r) {
		Point p = r.getLocation();
		p.translate(r.width / 2, r.height);
		return p;
	}

	public static Point getE(Rectangle r) {
		Point p = r.getLocation();
		p.translate(r.width, r.height / 2);
		return p;
	}

	public static Point getW(Rectangle r) {
		Point p = r.getLocation();
		p.translate(0, r.height / 2);
		return p;
	}

	public static Point getNE(Rectangle r) {
		Point p = r.getLocation();
		p.translate(r.width, 0);
		return p;
	}

	public static Point getNW(Rectangle r) {
		return r.getLocation();
	}

	public static Point getSE(Rectangle r) {
		Point p = r.getLocation();
		p.translate(r.width, r.height);
		return p;
	}

	public static Point getSW(Rectangle r) {
		Point p = r.getLocation();
		p.translate(0, r.height);
		return p;
	}
}
