package store.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import store.entities.Product;

public class ProductsList implements Serializable {

	private static final long serialVersionUID = 1L;
	private static ProductsList singleton;
	private ArrayList<Product> productsList1;

	private ProductsList() {
		productsList1 = new ArrayList<Product>();
	}

	public static ProductsList instance() {
		if (singleton == null) {
			singleton = new ProductsList();
		}
		return singleton;
	}

	public void addProduct(Product product) {
		productsList1.add(product);
	}

	public Product search(String productId) {
		for (Iterator<Product> iterator = productsList1.iterator(); iterator.hasNext();) {
			Product next = iterator.next();
			if (next.getId().equals(productId)) {
				return next;
			}
		}
		return null;
	}

	private ArrayList<Product> productsList = new ArrayList<Product>();

	public boolean addProduct(String name, String id, double currentPrice, int stockOnHand, int reorderLevel) {

		for (Product product : productsList1) {
			if (product.getName().equalsIgnoreCase(name)) {
				productsList1.add(new Product(name, id, currentPrice, stockOnHand, reorderLevel));
				return true;
			}
		}
		return false;
	}
}