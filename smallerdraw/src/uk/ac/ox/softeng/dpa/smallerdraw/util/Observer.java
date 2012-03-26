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
	void update(T t);
}
