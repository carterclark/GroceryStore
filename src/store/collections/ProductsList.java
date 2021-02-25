package store.collections;

import java.util.ArrayList;
import java.util.Iterator;

import store.entities.Product;

public class ProductsList {

	private static ArrayList<Product> productsList;

	private ProductsList() {
	}

	public static ArrayList<Product> instance() {
		if (productsList == null) {
			productsList = new ArrayList<Product>();
		}
		return productsList;
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

}
