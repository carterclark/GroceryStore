package store.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Class Member represents a single member of a small co-op grocery store.
 * 
 * @author
 *
 */
public class Member implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phoneNumber;
	private Calendar dateJoined;
	private double feePaid;
	private String id;
	private ArrayList<Transaction> transactions;

	// static field necessary for generating member IDs automatically
	private static int idCounter = 1;

	/**
	 * The constructor. At the time of creation of a member object a list of his/her
	 * transactions (checkouts) is created as well.
	 * 
	 * @param name        - name of the new member
	 * @param address     - address of the new member
	 * @param phoneNumber - phone number of the new member stored in format
	 *                    "1234567890"
	 * @param dateJoined  - date the member joined (the constructor doesn't assign
	 *                    today's date to the member; the date is assigned by user
	 * @param feePaid     - membership fee the new member paid
	 */
	public Member(String name, String address, String phoneNumber, Calendar dateJoined, double feePaid) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.dateJoined = dateJoined;
		this.feePaid = feePaid;
		this.id = "M-" + idCounter++;
		transactions = new ArrayList<Transaction>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Calendar getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Calendar dateJoined) {
		this.dateJoined = dateJoined;
	}

	public double getFeePaid() {
		return feePaid;
	}

	public void setFeePaid(double feePaid) {
		this.feePaid = feePaid;
	}

	public String getId() {
		return id;
	}

	/**
	 * Adds a new transaction (checkout) to the member.
	 * 
	 * @param transaction - the transaction being added
	 */
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

	/**
	 * Gets a list of the member's transactions for a specific time period.
	 * 
	 * @param fromDate - upper bound of the time period for the transaction list
	 * @param toDate   - lower bound of the time period for the transaction list
	 * @return an iterator over the desired list of transactions
	 */
	public Iterator<Transaction> getTransactions(Calendar fromDate, Calendar toDate) {
		ArrayList<Transaction> output = new ArrayList<Transaction>();
		for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext();) {
			Transaction transaction = iterator.next();
			if (transaction.isBetweenDates(fromDate, toDate)) {
				output.add(transaction);
			}
		}
		return output.iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((dateJoined == null) ? 0 : dateJoined.hashCode());
		long temp;
		temp = Double.doubleToLongBits(feePaid);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		Member other = (Member) object;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (dateJoined == null) {
			if (other.dateJoined != null)
				return false;
		} else if (!dateJoined.equals(other.dateJoined))
			return false;
		if (Double.doubleToLongBits(feePaid) != Double.doubleToLongBits(other.feePaid))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Member name: " + name + "\tMember ID: " + id;
	}

}