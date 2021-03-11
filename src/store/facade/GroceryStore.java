package store.facade;

import java.io.Serializable;
import java.util.ArrayList;
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
 * @author
 */
public class GroceryStore implements Serializable {

	private static final long serialVersionUID = 1L;
	private static GroceryStore singleton;
	// this class builds and maintains three essential lists: membersList,
	// productsList, and ordersList
	private MembersList membersList = new MembersList();
	private ProductsList productsList = new ProductsList();
	private OrdersList ordersList = new OrdersList();

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
		 * Gets a list of outstanding (not yet fulfilled) orders.
		 * 
		 * @return an iterator to the list of outstanding orders
		 */
		public Iterator<Order> outstanding() {
			ArrayList<Order> output = new ArrayList<Order>();
			for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();) {
				Order order = iterator.next();
				if (order.isOutstanding()) {
					output.add(order);
				}
			}
			return output.iterator();
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
	 * Inner class of the GroceryStore. It's a public class so other entities than
	 * GroceryStore can use it.
	 * 
	 * @author
	 *
	 */
	public class CheckOut {

		private Transaction checkOut;
		private boolean checkOutOpen = false;
		private String memberId;

		/**
		 * Opens a new checkout, but only if there isn't one open.
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
				// item is added to checkout
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
				// checkout is set to null for safety reasons: nothing can be added to it--a new
				// one has to be open
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
				// checkout is set to null for safety reasons: nothing can be added to it--a new
				// one has to be open
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
		memberId = membersList.add(new Member(request.getMemberName(), request.getMemberAddress(),
				request.getMemberPhoneNumber(), request.getMemberDateJoined(), request.getMemberFeePaid()));
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
	 * Adds a product to the product list
	 * 
	 * @param request carries the relevant product fields
	 * @return a result code that represents the outcome
	 */
	public Result addProduct(Request request) {
		Result result = new Result();
		String productId = productsList.add(new Product(request.getProductName(), request.getProductId(),
				request.getProductCurrentPrice(), request.getProductStockOnHand(), request.getProductReorderLevel()));
		if (!productId.equalsIgnoreCase("")) {
			result.setResultCode(Result.ACTION_SUCCESSFUL);
			reorderProduct(productsList.searchById(request.getProductId()));
		} else {
			result.setResultCode(Result.ACTION_FAILED);
		}
		return result;
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
		result.setOrderId(ordersList.add(new Order(product.getName(), product.getId(), product.getReorderLevel() * 2)));
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

	public boolean orderIsOutstanding(String orderId) {
		if (ordersList.searchById(orderId) == null) {
			return false;
		} else {
			return (ordersList.searchById(orderId).isOutstanding());
		}
	}

	/**
	 * Processes an outstanding order from the ordersList.
	 * 
	 * @param request - Request object (part of DataTransfer logic) carrying the
	 *                order number/ID info
	 * @return Result object (part of DataTransfer logic) carrying info about the
	 *         product re-stocked, the order number, and result code
	 */
	public Result processOrder(Request request) {
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
}
