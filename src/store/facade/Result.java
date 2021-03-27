package store.facade;

/**
 * Class Result is a child class to DataTransfer. It facilitates transfer from
 * the back end to the front. Adds result codes for deciphering the outcome of
 * the action taken.
 * 
 * @author Ben Hines, Carter Clark, Chris Lara-Batencourt, Pavel Danek, Ricky
 *         Nguyen
 *
 */

public class Result extends DataTransfer {

	// action codes to be expanded to meet the needs of additional functionalities
	// of the front end of the house.
	public static final int ACTION_SUCCESSFUL = 0;
	public static final int ACTION_FAILED = 1;
	public static final int INVALID_MEMBER_ID = 2;
	public static final int INVALID_PRODUCT_ID = 3;
	public static final int INVALID_PRODUCT_NAME = 4;
	public static final int INVALID_ORDER_NUMBER = 5;
	public static final int INVALID_ORDER_QUANTITY = 6;

	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}
