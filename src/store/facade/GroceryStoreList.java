package store.facade;

/**
 * Interface for various lists used in the grocery store facade
 * 
 * @author Ben Hines, Carter Clark, Chris Lara-Batencourt, Pavel Danek, Ricky
 *         Nguyen
 *
 * @param <E>, object type for interface
 */
public interface GroceryStoreList<E> {

	public String add(E object);

	public E searchById(String id);

}
