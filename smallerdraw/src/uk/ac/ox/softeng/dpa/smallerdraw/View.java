package uk.ac.ox.softeng.dpa.smallerdraw;

import java.awt.Rectangle;

import uk.ac.ox.softeng.dpa.smallerdraw.util.Observer;

/**
 * An interface to a view that renders a picture, or in terms of design
 * patterns, a view on a {@linkplain SelectableModel model}. It has a single
 * method, {@link #redraw(Rectangle) redraw}, though which it is notified of
 * changes to the underlying picture/model. This interface can be considered a
 * specialization of the {@linkplain Observer observer} pattern.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see Canvas
 * @see SelectableModel
 * @see Observer
 */
public interface View {

	/**
	 * Notify this view that an area of its display has become invalid and must
	 * be redrawn.
	 * 
	 * @param clip
	 *            the rectangular area to redraw
	 */
	void redraw(Rectangle clip);
}
