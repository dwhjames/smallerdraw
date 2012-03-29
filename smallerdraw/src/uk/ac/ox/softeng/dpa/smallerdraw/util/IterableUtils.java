package uk.ac.ox.softeng.dpa.smallerdraw.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public abstract class IterableUtils {
	
	/**
	 * Make a list of the elements in the given iterable object.
	 * 
	 * @param <E> the element type
	 * @param iter the iterable object to copy
	 * @return a list with the elements of the iterable
	 */
	public static <E> List<E> makeList(Iterable<E> iter) {
    	List<E> list = new ArrayList<E>();
    	for (E item : iter) {
    		list.add(item);
    	}
    	return list;
	}
}
