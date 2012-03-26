package uk.ac.ox.softeng.dpa.smallerdraw;

import java.awt.Rectangle;

/**
 * An extended interface for a picture. As well as a {@linkplain Model model}
 * being a collection of figures, a selectable model can retrieve just the
 * selected figures, as well as manipulate the bounds of the selection.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see Model
 * @see Figure
 */
public interface SelectableModel extends Model {

	/**
	 * Returns an iterable collection of only the selected figures in this
	 * model.
	 * 
	 * @return a collection of the selected figures
	 * @see Model#figures()
	 */
	Iterable<Figure> selectedFigures();

	/**
	 * Set the bounds of the selection, thus selecting all figures in this model
	 * that are contains within these bounds.
	 * 
	 * @param bounds
	 *            the selection bounds
	 */
	void setSelection(Rectangle bounds);

}