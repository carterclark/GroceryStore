package store.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class Transaction {

	private Calendar date;
	private ArrayList<Item> itemsList;
	private double totalPrice;

	public Transaction() {
		date = new GregorianCalendar();
		date.setTimeInMillis(System.currentTimeMillis());
		itemsList = new ArrayList<Item>();
		totalPrice = 0.0;
	}

	// for testing purposes
	public Transaction(int month, int day, int year, int hour, int minute) {
		date = new GregorianCalendar();
		date.set(year, month - 1, day, hour, minute);
		itemsList = new ArrayList<Item>();
		totalPrice = 0.0;
	}

	public Calendar getDate() {
		return date;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void addItem(Item item) {
		itemsList.add(item);
		totalPrice += item.getItemPrice();
	}

	public boolean isBetweenDates(Calendar firstDate, Calendar secondDate) {
		return (date.after(firstDate) && date.before(secondDate));
	}

	@Override
	public String toString() {
		String result;
		result = "Transaction made on ";
		result += (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR)
				+ " at " + date.get(Calendar.HOUR_OF_DAY) + ":";
		if (date.get(Calendar.MINUTE) < 10) {
			result += "0";
		}
		result += date.get(Calendar.MINUTE) + "\n";
		result += "---------------------------------------\n";
		for (Iterator<Item> iterator = itemsList.iterator(); iterator.hasNext();) {
			result += iterator.next().toString() + "\n";
		}
		result += "TOTAL -------------------------- $" + String.format("%.2f", totalPrice) + "\n";
		return result;
	}

}
