package store.facade;

public interface GroceryStoreList<E> {

	public String add(E object);

	public E search(String id);

}
