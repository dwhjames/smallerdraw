package uk.ac.ox.softeng.dpa.smallerdraw;

import java.awt.Point;

/**
 * An interface for objects with a location.
 * 
 * @author Daniel W. H. James
 * @version DPA March 2012
 */
public interface Locatable {
	/**
	 * Returns the location of this object.
	 * 
	 * @return the location of this object
	 * @see #setLocation(Point)
	 */
	Point getLocation();

	/**
	 * Sets the new location for this object.
	 * 
	 * @param p
	 *            the new location for this object
	 * @see #getLocation()
	 */
	void setLocation(Point p);
}
