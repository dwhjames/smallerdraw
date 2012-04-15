package uk.ac.ox.softeng.dpa.smallerdraw.util;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

/**
 * Geometry utility methods.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public abstract class Geometry {
	
	/**
	 * Create the minimum rectangle that encloses two points
	 * 
	 * @param p1 the first point
	 * @param p2 the second point
	 * @return the minimum rectangle
	 */
	public static Rectangle rectangleFromPoints(Point p1, Point p2) {
		Rectangle r = new Rectangle(p1);
		r.add(p2);
		return r;
	}

	/**
	 * Test to see if the point centre touches the line (start,end) with an error radius of 5
	 * 
	 * @param start the start of the line
	 * @param end the end of the line
	 * @param centre the point to test for intersection
	 * 
	 * @return true, if centre is on the line
	 */
	public static boolean linePointIntersect(Point start, Point end, Point centre)
	{
		return Line2D.ptSegDist(start.x, start.y, end.x, end.y, centre.x, centre.y) < 5;
	}

	/**
	 * The square of the length of the vector (x,y)
	 * 
	 * @param x the x coord 
	 * @param y the y coord
	 * 
	 * @return the squared length of (x,y)
	 */
	public static long pythagoras(long x, long y)
	{
		return x*x + y*y;
	}

	/**
	 * Interpolate linearly between u and v with s as the increment
	 * 
	 * @param u the starting value of the interpolant
	 * @param v the ending value of the interpolant
	 * @param s the increment to be calculated along the interpolant, must be between 0 and 1.0f for linear interpolation
	 * 
	 * @return the interpolated value, which equals (u + (int) (0.5f + s * (float) (v - u)))
	 */
	public static int linearInterpolation(int u, int v, float s)
	{
		return u + (int) (0.5f + s * (float) (v - u));
	}
}
