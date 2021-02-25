package store.entities;

public class Item {

	private String name;
	private String productId;
	private int quantity;
	private double unitPrice;
	private double itemPrice;

	public Item(String name, String productId, int quantity, double unitPrice) {
		this.name = name;
		this.productId = productId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.itemPrice = quantity * unitPrice;
	}

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
		return name + "  " + quantity + "x  ($" + String.format("%.2f", unitPrice) + "/unit)  $"
				+ String.format("%.2f", itemPrice);
	}

}
