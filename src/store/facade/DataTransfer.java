package store.facade;

import java.util.Calendar;
import java.util.Iterator;

import store.entities.Item;
import store.entities.Member;
import store.entities.Order;
import store.entities.Product;
import store.entities.Transaction;

/**
 * Class DataTransfer is used as an engine for safe data transfer between the
 * front and back end of the business, created to suit the exact needs of this
 * grocery store.
 * 
 * @author Ben Hines, Carter Clark, Chris Lara-Batencourt, Pavel Danek, Ricky
 *         Nguyen
 *
 */
public class DataTransfer {

	private String memberName;
	private String memberId;
	private String memberAddress;
	private String memberPhoneNumber;
	private Calendar memberDateJoined;
	private double memberFeePaid;
	private String productName;
	private String productId;
	private int productStockOnHand;
	private double productCurrentPrice;
	private int productReorderLevel;
	private int orderQuantity;
	private String orderId;
	private boolean orderIsOutstanding;
	private Calendar dateOfOrder;
	private Iterator<Item> itemsList;
	private double totalPrice;
	private Calendar transactionDate;
	private int itemQuantity;
	private double unitPrice;
	private double itemPrice;

	public DataTransfer() {
		reset();
	}

	/**
	 * Resets all fields to "", null, or zero.
	 */
	public void reset() {
		memberName = "";
		memberId = "";
		memberAddress = "";
		memberPhoneNumber = "";
		memberDateJoined = null;
		memberFeePaid = 0.0;
		productName = "";
		productId = "";
		productStockOnHand = 0;
		productCurrentPrice = 0.0;
		productReorderLevel = 0;
		orderQuantity = 0;
		orderId = "";
		orderIsOutstanding = false;
		dateOfOrder = null;
		itemsList = null;
		totalPrice = 0.0;
		transactionDate = null;
		itemQuantity = 0;
		unitPrice = 0.0;
		itemPrice = 0.0;
	}

	public Calendar getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Calendar transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Iterator<Item> getItemsList() {
		return itemsList;
	}

	public void setItemsList(Iterator<Item> itemsList) {
		this.itemsList = itemsList;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberPhoneNumber() {
		return memberPhoneNumber;
	}

	public void setMemberPhoneNumber(String memberPhoneNumber) {
		this.memberPhoneNumber = memberPhoneNumber;
	}

	public Calendar getMemberDateJoined() {
		return memberDateJoined;
	}

	public void setMemberDateJoined(Calendar memberDateJoined) {
		this.memberDateJoined = memberDateJoined;
	}

	public double getMemberFeePaid() {
		return memberFeePaid;
	}

	public void setMemberFeePaid(double memberFeePaid) {
		this.memberFeePaid = memberFeePaid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getProductStockOnHand() {
		return productStockOnHand;
	}

	public void setProductStockOnHand(int productStockOnHand) {
		this.productStockOnHand = productStockOnHand;
	}

	public double getProductCurrentPrice() {
		return productCurrentPrice;
	}

	public void setProductCurrentPrice(double productCurrentPrice) {
		this.productCurrentPrice = productCurrentPrice;
	}

	public int getProductReorderLevel() {
		return productReorderLevel;
	}

	public void setProductReorderLevel(int productReorderLevel) {
		this.productReorderLevel = productReorderLevel;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean getIsOutstanding() {
		return orderIsOutstanding;
	}

	public Calendar getDateOfOrder() {
		return dateOfOrder;
	}

	public Iterator<Item> getTransactionsItemsList() {
		return itemsList;
	}

	/**
	 * Sets all member fields with supplied Member fields (to be used only by the
	 * back of the house because of the manipulation with sensitive data)
	 * 
	 * @param member - the Member object who's fields are to be loaded to the
	 *               DataTransfer
	 */
	public void setMemberFields(Member member) {
		memberName = member.getName();
		memberId = member.getId();
		memberAddress = member.getAddress();
		memberPhoneNumber = member.getPhoneNumber();
		memberDateJoined = member.getDateJoined();
		memberFeePaid = member.getFeePaid();
	}

	/**
	 * Sets all product fields with supplied Product fields (to be used only by the
	 * back of the house because of the manipulation with sensitive data)
	 * 
	 * @param product - the Product object who's fields are to be loaded to the
	 *                DataTransfer
	 */
	public void setProductFields(Product product) {
		productName = product.getName();
		productId = product.getId();
		productStockOnHand = product.getStockOnHand();
		productCurrentPrice = product.getCurrentPrice();
		productReorderLevel = product.getReorderLevel();
	}

	/**
	 * Sets all order fields with data from given Order object
	 * 
	 * @param order - the Order object to fill the data with
	 */
	public void setOrderFields(Order order) {
		orderId = order.getOrderNumber();
		productName = order.getProductName();
		productId = order.getProductId();
		dateOfOrder = order.getDateOfOrder();
		orderQuantity = order.getQuantity();
		orderIsOutstanding = order.isOutstanding();
	}

	/**
	 * Sets all transaction fields with data from given Transaction object
	 * 
	 * @param order - the Transaction object to fill the data with
	 */
	public void setTransactionFields(Transaction transaction) {
		this.itemsList = transaction.getItems();
		this.memberId = transaction.getMemberId();
		this.totalPrice = transaction.getTotalPrice();
		this.transactionDate = transaction.getDate();
	}

	/**
	 * Sets all item fields with data from given item object
	 * 
	 * @param item - the Item which to fill the data with
	 */
	public void setItemFields(Item item) {
		this.productName = item.getName();
		this.productId = item.getProductId();
		this.itemQuantity = item.getQuantity();
		this.unitPrice = item.getUnitPrice();
		this.itemPrice = item.getItemPrice();
	}

}
