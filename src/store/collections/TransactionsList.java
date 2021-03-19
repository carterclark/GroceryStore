package store.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import store.entities.Transaction;
import store.facade.GroceryStoreList;

/**
 * Holds list of transactions.
 *
 */
public class TransactionsList implements Iterable<Transaction>, GroceryStoreList<Transaction>, Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	/*
	 * Add a transaction to the list. Returns the total price if successful.
	 */
	public String add(Transaction transaction) {
		if (transactions.add(transaction)) {
			return String.valueOf(transactions.get(transactions.size() - 1).getTotalPrice());
		} else {
			return "";
		}
	}

	/*
	 * Returns the list of transactions.
	 */
	public Iterator<Transaction> iterator() {
		return transactions.iterator();
	}

	/*
	 * Unused. Transactions don't have IDs.
	 */
	public Transaction searchById(String id) {
		return null;
	}
}
