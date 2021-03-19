package store.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * 
 * Class transaction creates a single checkout report. It's like one checkout
 * receipt in a grocery store with a list of items on it. It is used to keep
 * track of member's transactions and as an entity for a checkout.
 * 
 * @author
 *
 */
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;
	private Calendar date;
	private ArrayList<Item> itemsList;
	private double totalPrice;
	private String memberId;

	/**
	 * The constructor. At the point of creation of a transaction, the date and time
	 * is recorded for search purposes.
	 */
	public Transaction() {
		date = new GregorianCalendar();
		date.setTimeInMillis(System.currentTimeMillis());
		itemsList = new ArrayList<Item>();
		totalPrice = 0.0;
	}

	// for testing purposes (we may or may not need it)
	public Transaction(int month, int day, int year, int hour, int minute) {
		date = new GregorianCalendar();
		date.set(year, month - 1, day, hour, minute);
		itemsList = new ArrayList<Item>();
		totalPrice = 0.0;
	}

	// no setters - inadequate for the fields date and totalPrize to be externally
	// manipulated

	public Calendar getDate() {
		return date;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
	
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public String getMemberId() {
		return memberId;
	}

	/**
	 * Adds a new checkout item into the transaction and updates the totalPrice.
	 * 
	 * @param item - item being added
	 */
	public void addItem(Item item) {
		itemsList.add(item);
		totalPrice += item.getItemPrice();
	}

	/**
	 * Determines whether the transaction has been in certain time period.
	 * 
	 * @param firstDate  - date "from"
	 * @param secondDate - date "to"
	 * @return TRUE if transaction has been made between firstDate and secondDate
	 *         FALSE if otherwise
	 */
	public boolean isBetweenDates(Calendar firstDate, Calendar secondDate) {
		return (date.after(firstDate) && date.before(secondDate));
	}

	/**
	 * Gets a list of all items of the transaction.
	 * 
	 * @return an iterator to a list of all the items of the transaction
	 */
	public Iterator<Item> getItems() {
		return itemsList.iterator();
	}

	@Override
	public String toString() {
		String output;
		output = "Transaction made on ";
		output += String.format("%1$tm/%1$td/%1$tY at %1$tT", date);
		output += "\n----------------------------------------------------\n";
		for (Iterator<Item> iterator = itemsList.iterator(); iterator.hasNext();) {
			output += iterator.next().toString() + "\n";
		}
		output += "TOTAL ------------------------------------ $" + String.format("%8.2f", totalPrice) + "\n";
		return output;
	}

}
