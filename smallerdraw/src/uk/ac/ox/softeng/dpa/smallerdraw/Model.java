package uk.ac.ox.softeng.dpa.smallerdraw;

/**
 * An interface for an iterable collection of figures. This is the most basic
 * form of picture, simply a collection of figures.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see Figure
 * @see java.lang.Iterable
 */
public interface Model {

	/**
	 * Add a new figure to this model.
	 * 
	 * @param figure
	 *            the figure to add
	 * @see #remove(Figure)
	 * @see #delete(Figure)
	 */
	void add(Figure figure);

	/**
	 * Remove a figure from this model.
	 * 
	 * This picture will no longer directly include the given figure.
	 * 
	 * @param figure
	 *            the figure to remove
	 * @see #add(Figure)
	 * @see #delete(Figure)
	 */
	void remove(Figure figure);
	
	/**
	 * Delete a figure from this model..
	 * 
	 * The given figure will be {@linkplain #remove(Figure) removed},
	 * and all links (such as connecting lines) will be severed. 
	 * 
	 * @param figure the figure to delete
	 */
	void delete(Figure figure);

	/**
	 * Returns an iterable collection of figures, in the order they were added
	 * to this model.
	 * 
	 * @return a collection of figures
	 */
	Iterable<Figure> figures();
	
	/**
	 * Returns an iterable collection of figures, in the reverse of the
	 * order they were added to this model.
	 * 
	 * @return a collection of figures
	 */
	Iterable<Figure> figuresReversed();

	/**
	 * Accepts a visitor to this model.
	 * 
	 * @param visitor a visitor to this model
	 */
	void accept(Visitor visitor);
}