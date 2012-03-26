package uk.ac.ox.softeng.dpa.smallerdraw;

import java.awt.Point;

/**
 * The listener interface for receiving mouse events. Only one mouse button is
 * assumed, so the {@link #onMouseDown(Point) onMouseDown},
 * {@link #onMouseDrag(Point) onMouseDrag} and {@link #onMouseUp(Point)
 * onMouseUp} methods are called only for events corresponding to the primary
 * mouse button.
 * 
 * It is a specialization of the {@link java.awt.event.MouseListener
 * MouseListener} and {@link java.awt.event.MouseMotionListener
 * MouseMotionListener} interfaces in the Java standard libraries.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see java.awt.event.MouseListener
 * @see java.awt.event.MouseMotionListener
 */
public interface Tool {

	/**
	 * Invoked when the primary mouse button has been pressed at position
	 * {@code pos}.
	 * 
	 * @param pos
	 *            the location of the mouse press
	 */
	void onMouseDown(Point pos);

	/**
	 * Invoked when the primary mouse button has been pressed and then dragged.
	 * 
	 * @param pos
	 *            the location of the mouse cursor
	 */
	void onMouseDrag(Point pos);

	/**
	 * Invoked when the mouse cursor has been moved, but no buttons have been
	 * pushed.
	 * 
	 * @param pos
	 *            the location of the mouse cursor
	 */
	void onMouseMove(Point pos);

	/**
	 * Invoked when the primary mouse button has been released at position
	 * {@code pos}.
	 * 
	 * @param pos
	 *            the location of the mouse release
	 */
	void onMouseUp(Point pos);
}
