package store.entities;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private double currentPrice;
	private int stockOnHand;
	private int reorderLevel;
	private String id;
	private boolean isOrdered;

	public Product(String name, String id, double currentPrice, int stockOnHand, int reOrderLevel) {
		this.name = name;
		this.currentPrice = currentPrice;
		this.stockOnHand = stockOnHand;
		this.reorderLevel = reOrderLevel;
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

	public boolean setStockOnHand(int stockOnHand) {
		this.stockOnHand = stockOnHand;
		return (this.stockOnHand <= reorderLevel);
	}

	public int getReOrderLevel() {
		return reorderLevel;
	}

	public void setReOrderLevel(int reorderLevel) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(currentPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + reorderLevel;
		result = prime * result + stockOnHand;
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		Product other = (Product) object;
		if (Double.doubleToLongBits(currentPrice) != Double.doubleToLongBits(other.currentPrice))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reorderLevel != other.reorderLevel)
			return false;
		if (stockOnHand != other.stockOnHand)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product name: " + name;
	}

}
