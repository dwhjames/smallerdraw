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
	 */
	void add(Figure figure);

	/**
	 * Remove a figure from this model.
	 * 
	 * @param figure
	 *            the figure to remove
	 * @see #add(Figure)
	 */
	void remove(Figure figure);

	/**
	 * Returns an iterable collection of figures, in the order they were added
	 * to this model.
	 * 
	 * @return a collection of figures
	 */
	Iterable<Figure> figures();

}