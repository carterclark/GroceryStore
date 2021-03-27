package store.entities;

import java.io.Serializable;

/**
 * 
 * Class Item represents a single item of a Transaction. In a grocery store
 * environment, it can be understood as a single line on a check out receipt.
 * 
 * @author Ben Hines, Carter Clark, Chris Lara-Batencourt, Pavel Danek, Ricky
 *         Nguyen
 *
 */
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String productId;
	private int quantity;
	private double unitPrice;
	private double itemPrice;

	/**
	 * The constructor.
	 * 
	 * @param name      - product name of the item checked out
	 * @param productId - product ID of the item checked out
	 * @param quantity  - quantity of the item checked out
	 * @param unitPrice - price of a single quantity of the item checked out
	 */
	public Item(String name, String productId, int quantity, double unitPrice) {
		this.name = name;
		this.productId = productId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.itemPrice = quantity * unitPrice;
	}

	// no setters necessary; all fields set just once - in the constructor

	public String getName() {
		return name;
	}

	public String getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	@Override
	public String toString() {
		String fittedName;
		if (name.length() > 18) {
			fittedName = name.substring(0, 18);
		} else {
			fittedName = name;
		}
		return String.format("%-18s", fittedName) + "  " + String.format("%3s", quantity) + "x  ("
				+ String.format("$%6.2f", unitPrice) + "/unit):  " + String.format("$%8.2f", itemPrice);
	}

}
