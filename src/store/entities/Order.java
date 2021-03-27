package store.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class Order represents a grocery store's order (to a vendor) of a product,
 * that the store is either adding or running low on.
 * 
 * @author Ben Hines, Carter Clark, Chris Lara-Batencourt, Pavel Danek, Ricky
 *         Nguyen
 *
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private String orderNumber;
	private String productName;
	private String productId;
	private Calendar dateOfOrder;
	private int quantity;
	// the order is outstanding until delivery when isOutstanding is set to FALSE by
	// user
	private boolean isOutstanding;

	/**
	 * The constructor. At the point of creation of an order, the date and time is
	 * recorded for search purposes and order number is generated and stored.
	 * 
	 * @param productName - name of the product being ordered
	 * @param productId   - ID of the product being ordered
	 * @param quantity    - quantity of the product being ordered
	 */
	public Order(String productName, String productId, int quantity, int idCounter) {
		dateOfOrder = new GregorianCalendar();
		dateOfOrder.setTimeInMillis(System.currentTimeMillis());
		this.productName = productName;
		this.productId = productId;
		this.quantity = quantity;
		orderNumber = "O-" + idCounter;
		isOutstanding = true;
	}

	// for testing purposes (we may or may not need it)
	public Order(String productName, String productId, int quantity, int month, int day, int year, int hour, int minute,
			int idCounter) {
		dateOfOrder = new GregorianCalendar();
		dateOfOrder.set(year, month - 1, day, hour, minute);
		this.productName = productName;
		this.productId = productId;
		this.quantity = quantity;
		orderNumber = "O-" + idCounter;
		isOutstanding = true;
	}

	public boolean isOutstanding() {
		return isOutstanding;
	}

	public void setOutstanding(boolean isOutstanding) {
		this.isOutstanding = isOutstanding;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductId() {
		return productId;
	}

	public Calendar getDateOfOrder() {
		return dateOfOrder;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", productName=" + productName + ", dateOfOrder=" + dateOfOrder
				+ ", quantity=" + quantity + "]";
	}

}
