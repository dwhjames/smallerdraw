package uk.ac.ox.softeng.dpa.smallerdraw;

import java.awt.Graphics2D;

/**
 * An interface for drawable objects. Implementing classes will render
 * themselves using the Java 2D API.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public interface Drawable {
	/**
	 * Draw this object using a Java 2D graphics context.
	 * 
	 * @param g a Java 2D graphics context
	 */
	void draw(Graphics2D g);
}
