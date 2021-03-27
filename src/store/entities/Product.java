package store.entities;

import java.io.Serializable;

/**
 * Class Product represents a single product from the catalog of products
 * carried by a grocery store.
 * 
 * @author Ben Hines, Carter Clark, Chris Lara-Batencourt, Pavel Danek, Ricky
 *         Nguyen
 *
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private double currentPrice;
	private int stockOnHand;
	private int reorderLevel;
	// isOrdered field indicates if the product is back-ordered (in the process of
	// being delivered) to facilitate control over subsequent orders
	private boolean isOrdered;

	/**
	 * The constructor. Every Product created is "marked" as not ordered.
	 * 
	 * @param name
	 * @param id
	 * @param currentPrice
	 * @param stockOnHand
	 * @param reorderLevel
	 */
	public Product(String name, String id, double currentPrice, int stockOnHand, int reorderLevel) {
		this.name = name;
		this.currentPrice = currentPrice;
		this.stockOnHand = stockOnHand;
		this.reorderLevel = reorderLevel;
		this.isOrdered = false;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public int getStockOnHand() {
		return stockOnHand;
	}

	/**
	 * One of the Product's setters.
	 * 
	 * @param stockOnHand - the quantity of the product available
	 * @return TRUE if the stock should be reordered, FALSE if the stock is
	 *         sufficient
	 */
	public boolean setStockOnHand(int stockOnHand) {
		this.stockOnHand = stockOnHand;
		return (this.stockOnHand <= reorderLevel);
	}

	public int getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(int reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public String getId() {
		return id;
	}

	public boolean isOrdered() {
		return isOrdered;
	}

	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	@Override
	public String toString() {
		return "Product name: " + name + "\tProduct ID: " + id;
	}

}
