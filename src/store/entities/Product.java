package store.entities;

public class Product {

	private String name;
	private double currentPrice;
	private int stockOnHand;
	private int reOrderLevel;
	private int id;

	private static int idCounter = 1;

	public Product(String name, double currentPrice, int stockOnHand, int reOrderLevel) {
		this.name = name;
		this.currentPrice = currentPrice;
		this.stockOnHand = stockOnHand;
		this.reOrderLevel = reOrderLevel;
		this.id = idCounter++;
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

	public void setStockOnHand(int stockOnHand) {
		this.stockOnHand = stockOnHand;
	}

	public int getReOrderLevel() {
		return reOrderLevel;
	}

	public void setReOrderLevel(int reOrderLevel) {
		this.reOrderLevel = reOrderLevel;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(currentPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + reOrderLevel;
		result = prime * result + stockOnHand;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (Double.doubleToLongBits(currentPrice) != Double.doubleToLongBits(other.currentPrice))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reOrderLevel != other.reOrderLevel)
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
