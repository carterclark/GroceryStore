package store.facade;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import store.entities.Item;
import store.entities.Member;
import store.entities.Order;
import store.entities.Product;
import store.entities.Transaction;

/**
 * Class GroceryStore is the facade of the application. It acts as the (safe)
 * middle-man between the user interface and the business logic of the grocery
 * store.
 * 
 * @author Ben Hines, Carter Clark, Chris Lara-Batencourt, Pavel Danek, Ricky
 *         Nguyen
 */
public class GroceryStore implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String BACKUP_FILE_NAME = "GroceryStore.dat";
	private static GroceryStore singleton;
	// this class builds and maintains three essential lists: membersList,
	// productsList, and ordersList
	private MembersList membersList = new MembersList();
	private ProductsList productsList = new ProductsList();
	private OrdersList ordersList = new OrdersList();
	// static field necessary for generating member IDs automatically
	private static int memberIdCounter = 1;
	private static int orderIdCounter = 1;

	// ------------------------MembersList Class---------------------------------
	/**
	 * Inner class of the GroceryStore. Only GroceryStore can use its methods and
	 * manipulate the list of members.
	 * 
	 * @author
	 *
	 */
	private class MembersList implements Iterable<Member>, GroceryStoreList<Member>, Serializable {

		private static final long serialVersionUID = 1L;
		private ArrayList<Member> members = new ArrayList<Member>();

		/**
		 * Adds a new member to the list.
		 * 
		 * @param member - the Member being added
		 * @return the new member's ID if successful, an empty String if unsuccessful
		 */
		public String add(Member member) {
			if (members.add(member)) {
				return members.get(members.size() - 1).getId();
			} else {
				return "";
			}
		}

		/**
		 * This method removes a single member from the members list array.
		 * 
		 * @param id - the unique ID of the member to be removed
		 * @return TRUE if the member was removed, FALSE if the member was not removed
		 */
		public boolean remove(String id) {

			for (Member member : members) {

				if (member.getId().equalsIgnoreCase(id)) {
					return members.remove(member);
				}
			}
			return false;
		}

		/**
		 * Searches for a member with a particular ID.
		 * 
		 * @param id - the ID of the member searched for
		 * @return Member object if found, null if not found
		 */
		public Member searchById(String id) {
			for (Iterator<Member> iterator = members.iterator(); iterator.hasNext();) {
				Member member = iterator.next();
				if (member.getId().equalsIgnoreCase(id)) {
					return member;
				}
			}
			return null;
		}

		/**
		 * Gets a list of all members.
		 * 
		 * @return an iterator to the list of members
		 */
		public Iterator<Member> iterator() {
			return members.iterator();
		}

	}

	// ------------------------ProductsList Class---------------------------------
	/**
	 * Inner class of the GroceryStore. Only GroceryStore can use its methods and
	 * manipulate the list of products.
	 * 
	 * @author
	 *
	 */
	private class ProductsList implements Iterable<Product>, GroceryStoreList<Product>, Serializable {

		private static final long serialVersionUID = 1L;
		private ArrayList<Product> products = new ArrayList<Product>();

		/**
		 * Adds a new product to the list.
		 * 
		 * @param product - the Product being added
		 * @return the new product's ID if successful, an empty String if unsuccessful
		 */
		public String add(Product product) {
			if (products.add(product)) {
				return products.get(products.size() - 1).getId();
			} else {
				return "";
			}
		}

		/**
		 * Searches for a product with a particular ID.
		 * 
		 * @param id - the ID of the product searched for
		 * @return Product object if found, null if not found
		 */
		public Product searchById(String id) {
			for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
				Product product = iterator.next();
				if (product.getId().equalsIgnoreCase(id)) {
					return product;
				}
			}
			return null;
		}

		/**
		 * Gets a list of all products.
		 * 
		 * @return an iterator to the list of products
		 */
		public Iterator<Product> iterator() {
			return products.iterator();
		}

	}

	// ------------------------OrdersList Class---------------------------------
	/**
	 * Inner class of the GroceryStore. Only GroceryStore can use its methods and
	 * manipulate the list of orders.
	 * 
	 * @author
	 *
	 */
	private class OrdersList implements Iterable<Order>, GroceryStoreList<Order>, Serializable {

		private static final long serialVersionUID = 1L;
		private ArrayList<Order> orders = new ArrayList<Order>();

		/**
		 * Adds a new order to the list. There is only one product per order.
		 * 
		 * @param order - the Order being added
		 * @return the new order number if successful, an empty String if unsuccessful
		 */
		public String add(Order order) {
			if (orders.add(order)) {
				return orders.get(orders.size() - 1).getOrderNumber();
			} else {
				return "";
			}

		}

		/**
		 * Searches for an order with a particular order number/ID.
		 * 
		 * @param id - the ID of the order searched for
		 * @return Order object if found, null if not found
		 */
		public Order searchById(String id) {
			for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();) {
				Order order = iterator.next();
				if (order.getOrderNumber().equalsIgnoreCase(id)) {
					return order;
				}
			}
			return null;
		}

		/**
		 * Gets a list of all orders.
		 * 
		 * @return an iterator to the list of orders
		 */
		public Iterator<Order> iterator() {
			return orders.iterator();
		}

	}

	// ------------------------CheckOut Class---------------------------------
	/**
	 * Inner class of the GroceryStore. It's a public class so entities other than
	 * GroceryStore can use it.
	 * 
	 * @author
	 *
	 */
	public class CheckOut {

		private Transaction checkOut;
		// if checkOutOpen is set to FALSE, no items can be added or removed from
		// checkout - it's closed forever; it also cannot be set to TRUE (open) by user
		private boolean checkOutOpen;
		private String memberId;

		/**
		 * Opens a new checkout.
		 * 
		 * @param memberId - ID of the member checking out
		 */
		public CheckOut(String memberId) {
			checkOut = new Transaction();
			this.memberId = memberId;
			checkOutOpen = true;
		}

		/**
		 * Returns the state of the checkout.
		 * 
		 * @return TRUE if checkout is in progress, FALSE if not
		 */
		public boolean isOpen() {
			return checkOutOpen;
		}

		/**
		 * Gets the total of the running checkout.
		 * 
		 * @return checkout total
		 */
		public double getTotalPrice() {
			if (checkOutOpen) {
				return checkOut.getTotalPrice();
			} else {
				return 0.0;
			}
		}

		/**
		 * Puts an item of a certain quantity on a checkout.
		 * 
		 * @param request - object of the data transfer logic carrying the relevant
		 *                information (product ID and quantity checked out)
		 * @return result code indicating the result of the action
		 */
		public Result addItem(Request request) {
			Result result = new Result();
			Product product = productsList.searchById(request.getProductId());
			// next if clause is carried out if there is a running checkout AND the product
			// ID is valid AND there is enough quantity of the product on hand
			if (checkOutOpen && product != null && product.getStockOnHand() >= request.getOrderQuantity()) {
				// item is added to checkout, invoking Transaction's addItem method
				checkOut.addItem(new Item(product.getName(), product.getId(), request.getOrderQuantity(),
						product.getCurrentPrice()));
				// result fields are filled with relevant information (checked out product's
				// fields and quantity)
				result.setProductFields(product);
				result.setOrderQuantity(request.getOrderQuantity());
				// the stock on hand is updated
				product.setStockOnHand(product.getStockOnHand() - request.getOrderQuantity());
				// the result code is set
				result.setResultCode(Result.ACTION_SUCCESSFUL);
			} else {
				if (!checkOutOpen) {
					result.setResultCode(Result.ACTION_FAILED);
					return result;
				}
				if (product == null) {
					result.setResultCode(Result.INVALID_PRODUCT_ID);
					return result;
				}
				if (product.getStockOnHand() < request.getOrderQuantity()) {
					result.setResultCode(Result.INVALID_ORDER_QUANTITY);
					return result;
				}
			}
			return result;
		}

		/**
		 * Cancels the running checkout without effecting the member's transactions or
		 * stock-on-hand information.
		 * 
		 * @return result code indicating the result of the action
		 */
		public Result cancelCheckOut() {
			Result result = new Result();
			if (checkOutOpen) {
				// running checkout is closed
				checkOutOpen = false;
				// the subtracted quantities of items checked out are returned back to
				// stock-on-hand (the products are "re-stocked")
				for (Iterator<Item> iterator = checkOut.getItems(); iterator.hasNext();) {
					Item item = iterator.next();
					Product product = productsList.searchById(item.getProductId());
					product.setStockOnHand(product.getStockOnHand() + item.getQuantity());
				}
				// checkout is set to null for safety reasons: nothing can be added to it - a
				// new one has to be open
				memberId = "";
				checkOut = null;
				result.setResultCode(Result.ACTION_SUCCESSFUL);
			} else {
				result.setResultCode(Result.ACTION_FAILED);
			}
			return result;
		}

		/**
		 * Closes checkout with the transaction being recorded in member and low
		 * products reordered.
		 * 
		 * @return iterator on the list of items reordered (stored in an arrayList of
		 *         result (part of data transfer logic))
		 */
		public Iterator<Result> closeCheckOut() {
			ArrayList<Result> list = new ArrayList<Result>();
			if (checkOutOpen) {
				// running checkout is closed
				checkOutOpen = false;
				Member member = membersList.searchById(memberId);
				// new transaction is added to the member
				member.addTransaction(checkOut);
				// for loop is iterating over the list of all items checked out to find out if
				// any product needs to be reordered
				for (Iterator<Item> iterator = checkOut.getItems(); iterator.hasNext();) {
					Item item = iterator.next();
					Product product = productsList.searchById(item.getProductId());
					Result result = new Result();
					// next if clause is carried out if the product stock is low AND the product
					// doesn't have a pending order
					if (product.getStockOnHand() <= product.getReorderLevel() && !product.isOrdered()) {
						// a new order for a product (from a vendor) is placed and all relevant info
						// returned as a Result (for the particular product corresponding to the
						// iteration of the for loop)
						result = reorderProduct(product);
						// this result with fields indicating a reordered product is added to the list
						list.add(result);
					}
				}
				// checkout is set to null for safety reasons: nothing can be added to it - a
				// new one has to be open
				memberId = "";
				checkOut = null;
			}
			// and iterator on the created list of reordered products is returned
			return list.iterator();
		}
	}

	// ------------------------GroceryStore Class---------------------------------
	// -----------It's expected to be extended with new functionality.------------
	/**
	 * GroceryStore's constructor. It's a singleton.
	 */
	private GroceryStore() {
	}

	public static GroceryStore instance() {
		if (singleton == null) {
			singleton = new GroceryStore();
		}
		return singleton;
	}

	/**
	 * Used by UI, pre-loaded with request (data transfer logic), carries out the
	 * enrollMember of the MembersList (inner class).
	 * 
	 * @param request carries relevant member fields
	 * @return result (data transfer logic), filled with member fields and result
	 *         code
	 */
	public Result enrollMember(Request request) {
		Result result = new Result();
		String memberId = "";
		// member is added to membersList using MembersList method and request's member
		// fields
		memberId = membersList
				.add(new Member(request.getMemberName(), request.getMemberAddress(), request.getMemberPhoneNumber(),
						request.getMemberDateJoined(), request.getMemberFeePaid(), memberIdCounter++));
		// result is filled with relevant information (member ID and result code)
		result.setMemberFields(membersList.searchById(memberId));
		if (!memberId.equalsIgnoreCase("")) {
			result.setResultCode(Result.ACTION_SUCCESSFUL);
		} else {
			result.setResultCode(Result.ACTION_FAILED);
		}
		return result;
	}

	/**
	 * Removes a specific member from the members list
	 * 
	 * @param request carries the relevant member fields
	 * @return a code representing the outcome
	 */
	public Result removeMember(Request request) {
		Result result = new Result();
		Member member = membersList.searchById(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.INVALID_MEMBER_ID);
			return result;
		}
		result.setMemberFields(member);
		if (membersList.remove(request.getMemberId())) {
			result.setResultCode(Result.ACTION_SUCCESSFUL);
			return result;
		}
		result.setResultCode(Result.ACTION_FAILED);
		return result;
	}

	/**
	 * Used by UI, gets the list of all members on record without exposing the
	 * business logic of the back of the house.
	 * 
	 * @return iterator on the list of results containing member fields
	 */
	public Iterator<Result> getAllMembers() {
		ArrayList<Result> list = new ArrayList<Result>();
		for (Iterator<Member> iterator = membersList.iterator(); iterator.hasNext();) {
			Member member = iterator.next();
			Result result = new Result();
			result.setMemberFields(member);
			list.add(result);
		}
		return list.iterator();
	}

	/**
	 * Validates member ID.
	 * 
	 * @param memberId - ID being validated
	 * @return TRUE if member exists, FALSE if member doesn't exits
	 */
	public boolean memberIdExists(String memberId) {
		return (membersList.searchById(memberId) != null);
	}

	/**
	 * Gets a member with the specified ID.
	 * 
	 * @param memberId - ID of the member needed
	 * @return Result object with member fields set to a requested member
	 */
	public Result getMember(String memberId) {
		Member member = membersList.searchById(memberId);
		Result result = new Result();
		if (!(member == null)) {
			result.setMemberFields(member);
			return result;
		}
		return null;
	}

	/**
	 * Adds a product to the product list.
	 * 
	 * @param request carries the relevant product fields
	 * @return a result code that represents the outcome
	 */
	public Result addProduct(Request request) {
		Result result = new Result();
		String productId = productsList.add(new Product(request.getProductName(), request.getProductId(),
				request.getProductCurrentPrice(), request.getProductStockOnHand(), request.getProductReorderLevel()));

		if (!productId.equalsIgnoreCase("")) {

			result = reorderProduct(productsList.searchById(request.getProductId()));
		} else {
			result.setResultCode(Result.ACTION_FAILED);
		}
		return result;
	}

	/**
	 * Used by UI, gets the list of all products on record without exposing the
	 * business logic of the back of the house.
	 * 
	 * @return iterator on the list of results containing product fields
	 */
	public Iterator<Result> getAllProducts() {
		ArrayList<Result> list = new ArrayList<Result>();
		for (Iterator<Product> iterator = productsList.iterator(); iterator.hasNext();) {
			Product product = iterator.next();
			Result result = new Result();
			result.setProductFields(product);
			list.add(result);
		}
		return list.iterator();
	}

	/**
	 * Used by UI, get the list of all items in a transaction on record without
	 * exposing it directly.
	 * 
	 * @return iterator for list of items in a transaction
	 */
	public Iterator<Result> getTransactionItems(Result result) {
		ArrayList<Result> list = new ArrayList<Result>();
		for (Iterator<Item> iterator = result.getTransactionsItemsList(); iterator.hasNext();) {
			Item item = iterator.next();
			Result itemResult = new Result();
			itemResult.setItemFields(item);
			list.add(itemResult);
		}
		return list.iterator();
	}

	/**
	 * Used by UI, get the list of all orders on record without exposing the back
	 * end.
	 * 
	 * @return iterator for list of orders
	 */
	public Iterator<Result> getAllOrders() {
		ArrayList<Result> list = new ArrayList<Result>();
		for (Iterator<Order> iterator = ordersList.iterator(); iterator.hasNext();) {
			Order order = iterator.next();
			Result result = new Result();
			result.setOrderFields(order);
			list.add(result);
		}
		return list.iterator();
	}

	/**
	 * Used by UI, get the list of all transactions for a given member between given
	 * dates. @ return iterator for list of transactions for that member.
	 */
	public Iterator<Result> getMemberTransactions(Result memberResult, Calendar startingDate, Calendar endingDate) {
		Member member = null;
		for (Iterator<Member> iterator = membersList.iterator(); iterator.hasNext();) {
			member = iterator.next();
			if (member.getId().equals(memberResult.getMemberId())) {
				break;
			}
		}
		ArrayList<Result> list = new ArrayList<Result>();
		for (Iterator<Transaction> iterator = member.getTransactions(startingDate, endingDate); iterator.hasNext();) {
			Transaction transaction = iterator.next();
			Result result = new Result();
			result.setTransactionFields(transaction);
			list.add(result);
		}
		return list.iterator();
	}

	/**
	 * Validates product ID.
	 * 
	 * @param productId - ID being validated
	 * @return TRUE if product is on record, FALSE if product isn't on record
	 */
	public boolean productIdExists(String productId) {
		return (productsList.searchById(productId) != null);
	}

	/**
	 * Validates product name.
	 * 
	 * @param name - name being validated
	 * @return TRUE if the product name exists, FALSE if the product name does not
	 *         exist
	 */
	public boolean productNameExists(String name) {
		for (Iterator<Product> iterator = productsList.iterator(); iterator.hasNext();) {
			Product product = iterator.next();
			if (product.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A private method of GroceryStore. Reorders a given product to twice the
	 * amount of its reorder level.
	 * 
	 * @param product - the product to be reordered
	 * @return Result object with all necessary fields filled (order number, all
	 *         product fields, quantity ordered, and result code)
	 */
	private Result reorderProduct(Product product) {
		Result result = new Result();
		// result field orderId (that needs to be returned) is set in a one-step process
		// along with the creation of a new order
		result.setOrderId(ordersList
				.add(new Order(product.getName(), product.getId(), product.getReorderLevel() * 2, orderIdCounter++)));
		// next if clause is carried out if the placing of the order was unsuccessful
		if (result.getOrderId().equals("")) {
			result.setResultCode(Result.ACTION_FAILED);
		} else {
			// result fields for this product are filled with relevant information
			// (reordered product's fields and quantity ordered)
			result.setProductFields(product);
			result.setOrderQuantity(product.getReorderLevel() * 2);
			// the result code is set
			result.setResultCode(Result.ACTION_SUCCESSFUL);
			// this product is marked as ordered (pending order)
			product.setOrdered(true);
		}
		return result;
	}

	/**
	 * Changes the price of a product
	 * 
	 * @param request carries the relevant product fields
	 * @return a result code that represents the outcome
	 */
	public Result changePrice(Request request) {
		Result result = new Result();
		Product product = productsList.searchById(request.getProductId());
		product.setCurrentPrice(request.getProductCurrentPrice());

		if (product.getCurrentPrice() == request.getProductCurrentPrice()) {
			result.setResultCode(Result.ACTION_SUCCESSFUL);
			result.setProductFields(product);
		} else {
			result.setResultCode(Result.ACTION_FAILED);
		}

		return result;
	}

	/**
	 * Validates order number/ID.
	 * 
	 * @param orderId - number/ID being validated
	 * @return TRUE if order is on record, FALSE if order isn't on record
	 */
	public boolean orderIdExists(String orderId) {
		return (ordersList.searchById(orderId) != null);
	}

	/**
	 * Validates an order as outstanding (not yet fulfilled).
	 * 
	 * @return TRUE if order is not yet fulfilled (is outstanding), FALSE if not
	 */
	public boolean orderIsOutstanding(String orderId) {
		if (ordersList.searchById(orderId) == null) {
			return false;
		} else {
			return (ordersList.searchById(orderId).isOutstanding());
		}
	}

	/**
	 * Processes an outstanding order (aka. new shipment) from the ordersList.
	 * 
	 * @param request - Request object (part of DataTransfer logic) carrying the
	 *                order number/ID info
	 * @return Result object (part of DataTransfer logic) carrying info about the
	 *         product re-stocked, the order number, and result code
	 */
	public Result processShipment(Request request) {
		Order order = ordersList.searchById(request.getOrderId());
		Result result = new Result();
		// following if clause carried out if the order does not exist OR has already
		// been processed
		if (order == null || !order.isOutstanding()) {
			result.setResultCode(Result.ACTION_FAILED);
			return result;
		} else {
			// product matched to order's product ID
			Product product = productsList.searchById(order.getProductId());
			// product on-hand quantity increased by the order quantity
			product.setStockOnHand(product.getStockOnHand() + order.getQuantity());
			// product set as no longer pending order
			product.setOrdered(false);
			// result's product fields set
			result.setProductFields(product);
			// order number repeated back to result
			result.setOrderId(request.getOrderId());
			// result code set
			result.setResultCode(Result.ACTION_SUCCESSFUL);
			// order set as fulfilled
			order.setOutstanding(false);
			return result;
		}
	}

	/**
	 * Gets product information of products that start with string startsWith
	 * 
	 * @param startsWith is the name string
	 * @return a result ArrayList's iterator that represents the outcome of the
	 *         search
	 */
	public Iterator<Result> getProductInfo(String startsWith) {
		ArrayList<Result> result = new ArrayList<Result>();
		String name = startsWith.toUpperCase();

		// checks if any products start with the given name and stores into result if it
		// does
		int index = 0;
		for (Iterator<Product> iterator = productsList.iterator(); iterator.hasNext();) {
			Product product = iterator.next();
			if (product.getName().toUpperCase().startsWith(name)) {
				result.add(new Result());
				result.get(index).setResultCode(Result.ACTION_SUCCESSFUL);
				result.get(index).setProductFields(product);
				index++;
			}
		}

		return result.iterator();
	}

	/**
	 * Gets Member information for members that start with string startsWith
	 * 
	 * @param startsWith is the name string
	 * @return a result ArrayList's iterator that represents the outcome of the
	 *         search
	 */
	public Iterator<Result> getMemberInfo(String startsWith) {
		ArrayList<Result> result = new ArrayList<Result>();
		String name = startsWith.toUpperCase();

		// checks if any of the Member's start with the given string and stores into
		// result if it
		// does
		int index = 0;
		for (Iterator<Member> iterator = membersList.iterator(); iterator.hasNext();) {
			Member member = iterator.next();
			if (member.getName().toUpperCase().startsWith(name)) {
				result.add(new Result());
				result.get(index).setResultCode(Result.ACTION_SUCCESSFUL);
				result.get(index).setMemberFields(member);
				index++;
			}
		}

		return result.iterator();
	}

	/**
	 * Saves the GroceryStore object to file BACKUP_FILE_NAME in current directory,
	 * including static fields memberIdCounter and orderIdCounter.
	 * 
	 * @param groceryStore - GroceryStore object being saved.
	 * @return TRUE if file was successfully saved, FALSE otherwise
	 * @throws Exception for any problems opening or writing the file throws an
	 *                   Exception and returns FALSE
	 */
	public static boolean save(GroceryStore groceryStore) throws Exception {
		try {
			FileOutputStream file = new FileOutputStream(BACKUP_FILE_NAME);
			ObjectOutputStream object = new ObjectOutputStream(file);
			object.writeObject(groceryStore);
			object.writeObject(memberIdCounter);
			object.writeObject(orderIdCounter);
			object.close();
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * Loads the GroceryStore object from the backup file BACKUP_FILE_NAME residing
	 * in current directory, including static fields memberIdCounter and
	 * orderIdCounter.
	 * 
	 * @return GroceryStore OBJECT: from the backup, if such existed and was
	 *         readable, OR from memory, if the object had already existed; returns
	 *         NULL, if the object was not found in memory, AND was not found or
	 *         readable on disk
	 * @throws Exception for any problems opening or reading the file throws an
	 *                   Exception and returns NULL
	 */
	public static GroceryStore load() throws Exception {
		try {
			FileInputStream file = new FileInputStream(BACKUP_FILE_NAME);
			ObjectInputStream object = new ObjectInputStream(file);
			if (singleton == null) {
				singleton = (GroceryStore) object.readObject();
				memberIdCounter = (int) object.readObject();
				orderIdCounter = (int) object.readObject();
			}
			object.close();
			return singleton;
		} catch (Exception exception) {
			return null;
		}
	}

}
