package uk.ac.ox.softeng.dpa.smallerdraw;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import uk.ac.ox.softeng.dpa.smallerdraw.command.RedrawCommand;

/**
 * An interface for figures. Figures are abstract representations of graphical
 * objects. They held by a {@linkplain Model model} of a picture, and that
 * picture is rendered on a {@linkplain View canvas}. Figures have bounding
 * boxes, they can be selected, they can be rendered as filled or wire shapes,
 * they can be moved, and their shape can be tested for containment of a point.
 * Figures are manipulated through {@linkplain Handle handles}, another type of
 * graphical object. Figures can also be tested for the handles it possesses.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public interface Figure extends Drawable, Disposable, Cloneable {
	/**
	 * Compute the smallest rectangle that bounds this figure.
	 * 
	 * @return the bounding rectangle
	 */
	Rectangle getBounds();

	/**
	 * Returns true if this figure is selected.
	 * 
	 * @return true if this figure is selected
	 * @see #setSelected(boolean)
	 */
	boolean isSelected();

	/**
	 * Set the selection status of this figure.
	 * 
	 * @param selected
	 *            true if this figure should be selected
	 * @see #isSelected()
	 */
	void setSelected(boolean selected);

	/**
	 * Returns true if this figure is a filled shape.
	 * 
	 * @return true if this figure is filled
	 * @see #setFilled(boolean)
	 */
	boolean isFilled();

	/**
	 * Set the filled status of this figure.
	 * 
	 * @param filled
	 *            true if this figure should be filled.
	 * @see #isFilled()
	 */
	void setFilled(boolean filled);

	/**
	 * Move the figure by an offset.
	 * 
	 * @param offset
	 *            the dimension to move by
	 */
	void move(Dimension offset);

	/**
	 * Returns true if the given point is contained by this figure. If the
	 * figure is not filled, then this method will only return true if the point
	 * touches the border of this figure.
	 * 
	 * @param p
	 *            the point to test
	 * @return true if the figure contains the point
	 */
	boolean contains(Point p);

	/**
	 * Get the handle that belongs to this figure at a given point. If no handle
	 * is found, then {@code null} is returned.
	 * 
	 * @param p
	 *            the point to test
	 * @return the handle at the given point, or null if no handle exists
	 * @see Handle#contains(Point)
	 */
	Handle handleAt(Point p);

	/**
	 * Accept a command object for issuing a redraw request.
	 * 
	 * @param redrawCommand the command object for redrawing
	 */
	void setRedrawCommand(RedrawCommand redrawCommand);

	/**
	 * Returns a clone of this figure.
	 * 
	 * @return a clone of this figure
	 * @see Cloneable
	 */
	Figure clone();
}
