package uk.ac.ox.softeng.dpa.smallerdraw.util;

/**
 * An interface for objects that play the Observer role in the Subject&ndash;Observer
 * pattern.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @param <T> the type of data to receive
 * @see Observable
 */
public interface Observer<T> {
	/**
	 * Receive data of type {@code T} as an update.
	 * 
	 * @param t the data of the update message
	 * @see #finished()
	 */
	void update(T t);

	/**
	 * Receive a signal that the observable object
	 * will no longer be broadcasting updates.
	 * 
	 * @see #update(Object)
	 */
	void finished();
}
