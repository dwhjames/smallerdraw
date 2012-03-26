package uk.ac.ox.softeng.dpa.smallerdraw.util;

/**
 * An interface for objects that play the Subject role in the Subject&ndash;Observer
 * pattern.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @param <T> the type of data to broadcast
 * @see Observer
 */
public interface Observable<T> {
	/**
	 * Subscribe the given observer to the broadcasts of this observable object.
	 * 
	 * @param observer the observer to subscribe
	 */
	void subscribe(Observer<T> observer);
}
