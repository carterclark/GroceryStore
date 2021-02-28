package store.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private String orderNumber;
	private String productName;
	private String productId;
	private Calendar dateOfOrder;
	private int quantity;
	private boolean isFulfilled;

	private static int orderCounter = 1;

	public Order(String productName, String productId, int quantity) {
		dateOfOrder = new GregorianCalendar();
		dateOfOrder.setTimeInMillis(System.currentTimeMillis());
		this.productName = productName;
		this.productId = productId;
		this.quantity = quantity;
		orderNumber = "O-" + orderCounter++;
		isFulfilled = false;
	}

	// for testing purposes
	public Order(String productName, String productId, int quantity, int month, int day, int year, int hour,
			int minute) {
		dateOfOrder = new GregorianCalendar();
		dateOfOrder.set(year, month - 1, day, hour, minute);
		this.productName = productName;
		this.productId = productId;
		this.quantity = quantity;
		orderNumber = "O-" + orderCounter++;
		isFulfilled = false;
	}

	public boolean isFulfilled() {
		return isFulfilled;
	}

	public void setFulfilled(boolean isFulfilled) {
		this.isFulfilled = isFulfilled;
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
