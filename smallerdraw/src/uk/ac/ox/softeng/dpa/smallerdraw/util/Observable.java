package uk.ac.ox.softeng.dpa.smallerdraw.util;

import uk.ac.ox.softeng.dpa.smallerdraw.Disposable;

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
	 * This method returns a {@link Disposable} object, which acts as an
	 * unsubscriber. When the unsubscribe is {@linkplain Disposable#dispose() disposed}
	 * the observer is unsubscribed from this observable object. 
	 * 
	 * @param observer the observer to subscribe
	 * @return an unsubscriber object
	 */
	Disposable subscribe(Observer<T> observer);
}
