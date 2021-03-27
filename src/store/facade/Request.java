package store.facade;

import java.util.Calendar;

/**
 * Class Request is a child class to DataTransfer. It's a singleton facilitating
 * transfer from the front end to the back. Adds fields necessary only for
 * unidirectional transfer.
 * 
 * @author Ben Hines, Carter Clark, Chris Lara-Batencourt, Pavel Danek, Ricky
 *         Nguyen
 *
 */
public class Request extends DataTransfer {

	private static Request request;
	private Calendar date1;
	private Calendar date2;

	private Request() {
		reset();
	}

	public static Request instance() {
		if (request == null) {
			request = new Request();
		}
		return request;
	}

	public Calendar getDate1() {
		return date1;
	}

	public void setDate1(Calendar date1) {
		this.date1 = date1;
	}

	public Calendar getDate2() {
		return date2;
	}

	public void setDate2(Calendar date2) {
		this.date2 = date2;
	}

	@Override
	public void reset() {
		super.reset();
		date1 = null;
		date2 = null;
	}

}
