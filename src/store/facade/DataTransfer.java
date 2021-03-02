package store.facade;

public class DataTransfer {

	private String memberName;
	private String memberId;
	private String productName;
	private String productId;
	private String orderId;
	private String list;

	public DataTransfer() {
		reset();
	}

	public void reset() {
		memberName = "";
		memberId = "";
		productName = "";
		productId = "";
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

}
