package store.facade;

import java.util.Calendar;

import store.entities.Member;
import store.entities.Product;

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
	private String orderId;
	private String list;

	public DataTransfer() {
		reset();
	}

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
		orderId = "";
		list = "";
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void getMemberFields(Member member) {
		memberName = member.getName();
		memberId = member.getId();
		memberAddress = member.getAddress();
		memberPhoneNumber = member.getPhoneNumber();
		memberDateJoined = member.getDateJoined();
		memberFeePaid = member.getFeePaid();
	}

	public void getProductFields(Product product) {
		productName = product.getName();
		productId = product.getId();
		productStockOnHand = product.getStockOnHand();
		productCurrentPrice = product.getCurrentPrice();
		productReorderLevel = product.getReorderLevel();
	}

}
