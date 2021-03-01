package store.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import store.entities.Product;

public class ProductsList implements Serializable {

	private static final long serialVersionUID = 1L;
	private static ProductsList singleton;
	private ArrayList<Product> productsList;

	private ProductsList() {
		productsList = new ArrayList<Product>();
	}

	public static ProductsList instance() {
		if (singleton == null) {
			singleton = new ProductsList();
		}
		return singleton;
	}

	public void addProduct(Product product) {
		productsList.add(product);
	}

	public Product search(String productId) {
		for (Iterator<Product> iterator = productsList.iterator(); iterator.hasNext();) {
			Product next = iterator.next();
			if (next.getId().equals(productId)) {
				return next;
			}
		}
		return null;
	}

	// WE WILL RESOLVE THE DIFFERENCES BETWEEN ProductsList CLASSES ABOVE AND BELOW
	// LATER.

public class ProductsList {
	
	private ArrayList<Product> productsList = new ArrayList<Product>();

	/**
	 * This method takes in product parameters, creates a product object, and adds
	 * it to the productsList array if and only if there are no other products with
	 * the same name in the array, case insensitive
	 * 
	 * @param name         the name of the product
	 * @param currentPrice the current price the product will be listed at
	 * @param stockOnHand  the amount of the product currently in the store
	 * @param reorderLevel the amount of product in the store that necessitates a
	 *                     reorder of that product
	 * @return true if the product was added, false if it was not added
	 */
	public boolean addProduct(String name, double currentPrice, int stockOnHand, int reorderLevel) {

		for (Product product : productsList) {
			if (product.getName().equalsIgnoreCase(name)) {
				productsList.add(new Product(name, currentPrice, stockOnHand, reorderLevel));
				return true;
			}
		}
		return false;
	}

}
