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

}
