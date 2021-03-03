package store.facade;

public class Result extends DataTransfer {

	public static final int ACTION_SUCCESSFUL = 0;
	public static final int ACTION_FAILED = 1;
	public static final int INVALID_MEMBER_ID = 2;
	public static final int INVALID_PRODUCT_ID = 3;
	public static final int INVALID_ORDER_NUMBER = 4;

	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}
