package ui;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Scanner;

import store.facade.GroceryStore;
import store.facade.GroceryStore.CheckOut;
import store.facade.Request;
import store.facade.Result;
import store.tests.AutomatedTester;

/**
 * Class UserInterface represents the front end of the house, interacting
 * directly with the user (grocery store clerk or even a customer). It also
 * interacts with the business logic of the back end via data transfer pattern,
 * which is free of any business logic itself, to keep the safety at maximum.
 * This class is a singleton.
 * 
 * @author Ben Hines, Carter Clark, Chris Lara-Batencourt, Pavel Danek, Ricky
 *         Nguyen
 *
 */
public class UserInterface implements Serializable {

	private static final long serialVersionUID = 1L;
	private static UserInterface singleton;
	private static GroceryStore groceryStore;
	// codes for available functionalities (actions) of the application
	private static final int ENROLL_MEMBER = 1;
	private static final int REMOVE_MEMBER = 2;
	private static final int ADD_PRODUCT = 3;
	private static final int CHECK_OUT = 4;
	private static final int PROCESS_SHIPMENT = 5;
	private static final int CHANGE_PRICE = 6;
	private static final int RETRIEVE_PRODUCT_INFO = 7;
	private static final int RETRIEVE_MEMBER_INFO = 8;
	private static final int PRINT_TRANSACTIONS = 9;
	private static final int LIST_OUTSTANDING_ORDERS = 10;
	private static final int LIST_ALL_MEMBERS = 11;
	private static final int LIST_ALL_PRODUCTS = 12;
	private static final int SAVE_DATA = 13;
	private static final int HELP = 14;
	private static final int EXIT = 0;
	// action labels corresponding to the previous codes
	private static String[] menu = new String[] { "ENROLL A NEW MEMBER", "REMOVE A MEMBER", "ADD A PRODUCT",
			"CHECK OUT MEMBER'S ITEMS", "PROCESS SHIPMENT", "CHANGE PRODUCT PRICE", "RETRIEVE PRODUCT INFO",
			"RETRIEVE MEMBER INFO", "PRINT MEMBER'S TRANSACTIONS", "LIST ALL OUTSTANDING ORDERS", "LIST ALL MEMBERS",
			"LIST ALL PRODUCTS", "SAVE ALL DATA TO DISK", "HELP (DISPLAYS THIS MENU)" };
	private static Scanner input = new Scanner(System.in);
	// predetermined input error messages
	private static String yesNoErrorMessage = "Please answer Y[es] or N[o].";
	private static String emptyEntryErrorMessage = "Empty input is not allowed.";

	private UserInterface() {
		groceryStore = GroceryStore.instance();
	}

	public static UserInterface instance() {
		if (singleton == null) {
			singleton = new UserInterface();
		}
		return singleton;
	}

	// ----------------------------------------------------------------------------
	// ------a set of helper methods collecting and error-checking user input------
	/**
	 * Collects a String input from the user.
	 * 
	 * @param prompt - a custom message prompting for an input
	 * @return String entered by user without leading and trailing spaces
	 */
	public static String getString(String prompt) {
		String read = "";
		boolean error = true;
		while (error) {
			error = false;
			System.out.print(prompt);
			read = input.nextLine().trim();
			if (read.equals("")) {
				error = true;
			}
			if (error) {
				System.out.println(emptyEntryErrorMessage);
			}
		}
		return read;
	}

	/**
	 * Collects a non-negative integer input from the user.
	 * 
	 * @param prompt       - a custom message prompting for an integer input
	 * @param errorMessage - a custom error message in case of invalid input
	 * @return non-negative integer entered by user
	 */
	public static int getInteger(String prompt, String errorMessage) {
		String read = "";
		int value = 0;
		boolean error = true;
		while (error) {
			error = false;
			read = getString(prompt);
			try {
				value = Integer.parseInt(read);
				if (value < 0) {
					error = true;
				}
			} catch (Exception exception) {
				error = true;
			}
			if (error) {
				System.out.println(errorMessage);
			}
		}
		return value;
	}

	/**
	 * Collects a long input from the user. Also suitable for inputting phone
	 * numbers.
	 * 
	 * @param prompt       - a custom message prompting for a number (type long)
	 *                     input
	 * @param errorMessage - a custom error message in case of invalid input
	 * @return number (type long) entered by user
	 */
	public static long getLong(String prompt, String errorMessage) {
		String read = "";
		long value = 0;
		boolean error = true;
		while (error) {
			error = false;
			read = getString(prompt);
			try {
				value = Long.parseLong(read);
			} catch (Exception exception) {
				error = true;
			}
			if (error) {
				System.out.println(errorMessage);
			}
		}
		return value;
	}

	/**
	 * Collects a double input from the user.
	 * 
	 * @param prompt       - a custom message prompting for a number (type double)
	 *                     input
	 * @param errorMessage - a custom error message in case of invalid input
	 * @return number (type double) entered by user
	 */
	public static double getDouble(String prompt, String errorMessage) {
		String read = "";
		double value = 0;
		boolean error = true;
		while (error) {
			error = false;
			read = getString(prompt);
			try {
				value = Double.parseDouble(read);
			} catch (Exception exception) {
				error = true;
			}
			if (error) {
				System.out.println(errorMessage);
			}
		}
		return value;
	}

	/**
	 * Collects a yes-or-no input from the user. If the first letter of the response
	 * (trimmed of the leading and trailing spaces) is "Y" or "N" (case
	 * NON-sensitive) it will return corresponding answer.
	 * 
	 * @param prompt - a custom message prompting for a yes-or-no input
	 * @return TRUE if the user response corresponds to <yes>, FALSE if the response
	 *         corresponds to <no>
	 */
	public static boolean getYesOrNo(String prompt) {
		String read = "";
		char answer = ' ';
		boolean error = true;
		while (error) {
			error = false;
			read = getString(prompt + " (Y/N) ");
			answer = read.toUpperCase().charAt(0);
			if ((answer != 'Y') && (answer != 'N')) {
				error = true;
			}
			if (error) {
				System.out.println(yesNoErrorMessage);
			}
		}
		return (answer == 'Y');
	}
	// -----------------the end of the user input helper methods-------------------
	// ----------------------------------------------------------------------------

	/**
	 * Gets current date and time in the Calendar type.
	 * 
	 * @return date and time of right now
	 */
	public static Calendar getToday() {
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(System.currentTimeMillis());
		return date;
	}

	/**
	 * Displays a menu of action options (functionalities).
	 */
	public void help() {
		String output = "\n Your options:\n================================\n";
		for (int counter = 0; counter < 14; counter++) {
			output += String.format("%2s", (counter + 1)) + " = " + menu[counter] + "\n";
		}
		output += "--------------------------------\n 0 = EXIT PROGRAM";
		System.out.println(output);
	}

	/**
	 * Retrieves the grocery store data from the disk.
	 */
	public static boolean load() throws Exception {
		if (singleton == null) {
			groceryStore = GroceryStore.load();
			if (groceryStore == null) {
				System.out.println("No '" + GroceryStore.BACKUP_FILE_NAME
						+ "' present in your current\nworking folder or the file is unreadable.");
				return false;
			}
			instance();
			return true;
		} else {
			System.out.println("Grocery Store data are already in place and cannot be overwritten.\n"
					+ "If you want to load the data from the disk,\nchoose that option at the start of the program.");
			return false;
		}
	}

	/**
	 * Saves the grocery data to the disk.
	 */
	public void save() throws Exception {
		if (GroceryStore.save(groceryStore)) {
			System.out.println(
					"The Grocery Store data have been saved to a file '" + GroceryStore.BACKUP_FILE_NAME + "'.");
		} else
			System.out.println("Data could not be saved.");
	}

	/**
	 * Runs a test bed with predetermined sets of data applied on the grocery store.
	 */
	public void testBed() {

		new AutomatedTester().testAll();
		System.out.println("Grocery Store updated.");
	}

	/**
	 * Enrolls a new member. Uses Request and Result (data transfer logic) to send
	 * information to, and retrieve it from, the facade.
	 */
	public void enrollMember() {
		String name = getString("\nEnter new member's name: ");
		String address = getString("Enter member's address: ");
		String phoneNumber = String.valueOf(
				getLong("Enter member's phone number (in format 1234567890): ", "You didn't enter a phone number."));
		double feePaid = getDouble("Enter member fee paid: ", "You didn't enter a number.");
		Request.instance().setMemberName(name);
		Request.instance().setMemberAddress(address);
		Request.instance().setMemberPhoneNumber(phoneNumber);
		Request.instance().setMemberFeePaid(feePaid);
		Request.instance().setMemberDateJoined(getToday());
		Result result = groceryStore.enrollMember(Request.instance());
		if (result.getResultCode() == Result.ACTION_SUCCESSFUL) {
			System.out.println("\nMember added. Member ID = " + result.getMemberId() + ".");
		} else {
			System.out.println("\nMember couldn't be added.");
		}
	}

	/**
	 * Removes a member from the database.
	 */
	public void removeMember() {
		Request.instance().setMemberId(getString("\nEnter member's ID to be removed: "));
		if (groceryStore.memberIdExists(Request.instance().getMemberId())) {
			Result result = groceryStore.getMember(Request.instance().getMemberId());
			System.out.println("You are about to remove member " + result.getMemberId() + ", " + result.getMemberName()
					+ ", ph. number " + result.getMemberPhoneNumber() + ", from the system.");
			if (getYesOrNo("Are you sure?")) {
				result = groceryStore.removeMember(Request.instance());
				switch (result.getResultCode()) {
				case Result.ACTION_FAILED:
					System.out.println("Member could not be removed.");
					break;
				case Result.ACTION_SUCCESSFUL:
					System.out.println("Member " + result.getMemberId() + " has been removed.");
					break;
				default:
					System.out.println("An error has occured.");
				}
			} else {
				System.out.println("Member " + result.getMemberId() + " will not be removed.");
			}
		} else {
			System.out.println("No such member with id: " + Request.instance().getMemberId().toUpperCase()
					+ ", at the grocery store.");
		}
	}

	/**
	 * Adds a product to the database if there are no products of the same name or
	 * id already present
	 */
	public void addProduct() {
		String name = getString("\nEnter product's name: ");
		if (groceryStore.productNameExists(name)) {
			System.out.println("Error: product name already exists, a different one must be entered.");
			return;
		}
		String id = getString("Enter product's id: ");
		if (groceryStore.productIdExists(id)) {
			System.out.println("Error: product id already exists, a different one must be entered.");
			return;
		}
		double currentPrice = getDouble("Enter product's current price: ", "A valid number was not entered.");
		int stockOnHand = getInteger("Enter product's stock on hand: ", "A valid integer was not entered.");
		int reorderedLevel = getInteger("Enter product's reorder level: ", "A valid integer was not entered.");
		Request.instance().setProductName(name);
		Request.instance().setProductId(id);
		Request.instance().setProductCurrentPrice(currentPrice);
		Request.instance().setProductStockOnHand(stockOnHand);
		Request.instance().setProductReorderLevel(reorderedLevel);
		Result result = groceryStore.addProduct(Request.instance());
		if (result.getResultCode() == Result.ACTION_SUCCESSFUL) {
			System.out.println(
					"Product " + result.getProductName() + " added. (Order number " + result.getOrderId() + ".)");
		} else {
			System.out.println("Product could not be added");
		}
	}

	/**
	 * Performs a member's checkout. (A member buys items from the grocery store.)
	 */
	public void checkOut() {
		String memberId = getString("\nEnter member's ID (M-<number>): ");
		// next if clause is carried out if the member exists
		if (groceryStore.memberIdExists(memberId)) {
			// opening a checkout
			CheckOut checkOut = groceryStore.new CheckOut(memberId);
			// do the loop until there are no more items to check out
			do {
				System.out.println();
				// loading request (data transfer logic) with relevant information
				String productId = getString("Enter item's product ID: ");
				if (!groceryStore.productIdExists(productId)) {
					System.out.println("Invalid product ID. Unable to check out item.");
					// skip to another item
					continue;
				}
				Request.instance().setProductId(productId);
				Request.instance().setOrderQuantity(getInteger("Enter item quantity: ", "Not a valid number."));
				// carrying out the addItem of CheckOut class (inner class of GroceryStore) and
				// returning the corresponding info (result code, result's product fields, and
				// order quantity, in order to display it
				Result result = checkOut.addItem(Request.instance());
				switch (result.getResultCode()) {
				case Result.ACTION_SUCCESSFUL:
					System.out.println("Item added to checkout:");
					System.out.println(result.getOrderQuantity() + "x " + result.getProductName() + " ("
							+ String.format("$%.2f", result.getProductCurrentPrice()) + "/unit) "
							+ String.format("$%.2f", (result.getProductCurrentPrice() * result.getOrderQuantity())));
					break;
				case Result.ACTION_FAILED:
					System.out.println("Unable to check out item.");
					break;
				case Result.INVALID_ORDER_QUANTITY:
					System.out.println("Invalid item quantity. Unable to check out item.");
					break;
				}
			} while (getYesOrNo("Another item to check out?"));
			// displaying total
			System.out.println("\nYOUR TOTAL IS: " + String.format("$%.2f", checkOut.getTotalPrice()) + "\n");
			// the clerk confirms and collects physical cash OR doesn't confirm and thereby
			// cancels the checkout
			if (getYesOrNo("Transaction confirmed by collecting cash?")) {
				System.out.println("Checkout successful. We thank you.");
				// performing checkout close, which adds a transaction ( = checkout) to member's
				// history and reorders product(s) which got low in supply, if necessary;
				// returned is an iterator over a list of products (stored in a list of results
				// for safety) that were reordered - if the list is empty no product had to be
				// reordered
				Iterator<Result> iterator = checkOut.closeCheckOut();
				if (iterator.hasNext()) {
					System.out.println();
					// for loop prints the reordered products
					for (Iterator<Result> counter = iterator; counter.hasNext();) {
						Result result = counter.next();
						System.out.println("Item '" + result.getProductName() + "' will be reordered. (Order quantity: "
								+ result.getOrderQuantity() + ", order number: " + result.getOrderId() + ".)");
					}
				}
			} else {
				// in case the checkout is cancelled, the cancelCheckOut of CheckOut class
				// (inner class of GroceryStore) is performed; it rolls back any quantities
				// subtracted from the product supplies and deletes the checkout
				Result result = checkOut.cancelCheckOut();
				if (result.getResultCode() == Result.ACTION_SUCCESSFUL) {
					System.out.println("Checkout successfully canceled.");
				} else {
					System.out.println("Checkout not open. Please try again.");
				}
			}
		} else {
			System.out.println("Sorry, no such member.");
		}
	}

	/**
	 * Processes a new shipment to the grocery store fulfilled by a vendor.
	 */
	public void processShipment() {
		do {
			String orderNumber = getString("\nEnter outstanding order number: ");
			if (!groceryStore.orderIdExists(orderNumber)) {
				System.out.println("The order number is not on file.");
				continue;
			}
			if (!groceryStore.orderIsOutstanding(orderNumber)) {
				System.out.println("The order " + orderNumber + " has already been processed.");
				continue;
			}
			// following portion is carried out only if the order number exists and order is
			// outstanding
			// order number is loaded into the request instance
			Request.instance().setOrderId(orderNumber);
			// order is processed by GroceryStore's processOrder() method
			Result result = groceryStore.processShipment(Request.instance());
			if (result.getResultCode() == Result.ACTION_SUCCESSFUL) {
				System.out.println("Order " + orderNumber.toUpperCase() + " successfully processed:");
				System.out.println("Product " + result.getProductId() + ", '" + result.getProductName()
						+ "', has a new stock quantity of " + result.getProductStockOnHand() + " unit(s).");
			} else {
				System.out.println("The processing of order " + orderNumber + " has failed.");
			}
		} while (getYesOrNo("Another order to process?"));
	}

	/**
	 * Changes the price of a Product given an id and prints out product name and
	 * new price.
	 */
	public void changePrice() {
		String id = getString("\nEnter product's id: ");
		if (groceryStore.productIdExists(id)) {
			Request.instance().setProductId(id);
			double currentPrice = getDouble("Enter product's new current price: ", "A valid number was not entered.");
			Request.instance().setProductCurrentPrice(currentPrice);

			Result result = groceryStore.changePrice(Request.instance());
			if (result.getResultCode() != Result.ACTION_SUCCESSFUL) {
				System.out.println("Product's price could not be changed");
			} else {
				System.out.printf("Product: %s, New Price: %.2f", result.getProductName(),
						result.getProductCurrentPrice());
			}
		} else {
			System.out.println("Error: product id does not exist");
			return;
		}

	}

	/**
	 * Lists all products that start with a given string and displays their name,
	 * id, price, stock in hand, and reorder level.
	 */
	public void getProductInfo() {
		String name = getString("Enter product's name: ");
		Iterator<Result> iterator = groceryStore.getProductInfo(name);
		Result result;

		if (!iterator.hasNext()) {
			System.out.println("Error: product does not exist");
		} else {
			while (iterator.hasNext()) {
				// print info for each result object
				result = iterator.next();
				System.out.printf("Product: %s, ID: %s, Price: %.2f, Stock in hand: %d, reorder level: %d\n",
						result.getProductName(), result.getProductId(), result.getProductCurrentPrice(),
						result.getProductStockOnHand(), result.getProductReorderLevel());
			}
		}
	}

	/**
	 * Lists all members that start with a given string and displays their address,
	 * fee paid, and id
	 */
	public void getMemberInfo() {

		String name = getString("Enter Member's name: ");
		Iterator<Result> iterator = groceryStore.getMemberInfo(name);
		Result result;

		if (!iterator.hasNext()) {
			System.out.println("Error: Member does not exist");
		} else {
			while (iterator.hasNext()) {
				// print info for each result object
				result = iterator.next();
				System.out.printf("Member: %s, Address: %s, Fee paid: %.2f, ID: %s\n", result.getMemberName(),
						result.getMemberAddress(), result.getMemberFeePaid(), result.getMemberId());
			}
		}
	}

	/**
	 * Takes from the user a member ID, a starting date, an ending date and finds
	 * all transactions with given member ID between given dates.
	 */
	public void printTransactions() {
		boolean sentinel = false;
		String userInput = "";
		String memberId = "";
		Result member = null;
		Calendar startingDate = Calendar.getInstance();
		Calendar endingDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		// Loop the opportunity to input member ID until valid member ID is found or the
		// user exits.
		do {
			userInput = getString("\nEnter 'END' any time to exit this option. \nInput member ID: ");
			Iterator<Result> iterator = groceryStore.getAllMembers();

			// Find the supposed member given by user input or exit the loop in the event of
			// 'END'
			if (userInput.equalsIgnoreCase("END")) {

				break;
			} else {
				for (Iterator<Result> counter = iterator; counter.hasNext();) {
					Result result = counter.next();

					if (result.getMemberId().equalsIgnoreCase(userInput)) {

						memberId = userInput;
						member = result;
						System.out.println("Member found.");
						// We found a member matching user input, so stop looking for others.
						sentinel = true;
						break;
					}
					if (!counter.hasNext()) {
						// If you've managed to get here, there's no matching member.
						System.out.println(
								"No member with ID " + userInput + " found. Try again or enter 'END' to exit...");
					}
				}
			}

			// Sentinel set to true when a Member ID is input that matches one existing in
			// the database.
		} while (!sentinel);
		// Now get the starting and ending dates...
		if (!userInput.equalsIgnoreCase("END")) {

			sentinel = false;
			// Get starting date. Loops until a parse-able date is input or the user exits.
			do {
				userInput = getString("Input starting date for transactions (mm/dd/yyyy format):");

				// If the user entered 'END' exit the loop, else continue
				if (userInput.equalsIgnoreCase("END")) {

					break;
				} else {
					// Try to parse user input as a Calendar object in the given format
					try {
						startingDate.setTime(dateFormat.parse(userInput));
						// If you made it here, you input a parse-able date and can exit the loop.
						sentinel = true;
						break;
					} catch (Exception e) {
						System.out.println(
								"Cannot parse " + userInput + " as a date. Try again or enter 'END' to exit...");
					}
				}
			} while (!sentinel);
		}

		// Get ending date. Loops until a parse-able date is input or the user exits.
		if (!userInput.toString().equalsIgnoreCase("END")) {
			sentinel = false;
			do {
				userInput = getString(
						"Input ending date. Ending date must not precede starting date chronologically. (mm/dd/yyyy format): ");
				if (userInput.equalsIgnoreCase("END")) {

					break;
				} else {
					// Try to parse user input as a Calendar object in the given format
					try {
						endingDate.setTime(dateFormat.parse(userInput));

						// Check if the ending date is actually after the starting date.
						if (!endingDate.before(startingDate)) {
							sentinel = true;
							break;
						} else {
							System.out.println("Error: Ending date precedes starting date chronologically. Try again.");

						}

					} catch (Exception e) {
						System.out.println(
								"Cannot parse " + userInput + " as a date. Try again or enter 'END' to exit...");
					}
				}
			} while (!sentinel);
		}
		int matchesCount = 0;
		// Now find matching transactions for the given user input
		if (!userInput.toString().equals("END") && sentinel) {
			System.out.println(
					"\nTransactions for member ID " + memberId + " bewteen " + dateFormat.format(startingDate.getTime())
							+ " and " + dateFormat.format(endingDate.getTime()) + ":");

			Iterator<Result> iterator = groceryStore.getMemberTransactions(member, startingDate, endingDate);
			if (iterator.hasNext()) {
				for (Iterator<Result> counter = iterator; counter.hasNext();) {
					Result result = counter.next();
					matchesCount++;
					System.out.println("-".repeat(66) + "\n" + "-".repeat(66));
					System.out.println("Transaction " + matchesCount);
					System.out.println("-".repeat(13));
					System.out
							.println("Date: " + String.format(dateFormat.format(result.getTransactionDate().getTime()))
									+ "\n" + String.format("%-25s", "Product") + "  "
									+ String.format("%-13s", "Quantity") + "  " + String.format("%-13s", "Price"));
					Iterator<Result> itemIterator = groceryStore.getTransactionItems(result);
					if (itemIterator.hasNext()) {
						for (Iterator<Result> itemCounter = itemIterator; itemCounter.hasNext();) {
							Result itemResult = itemCounter.next();
							System.out.println(String.format("%-27s", itemResult.getProductName())
									+ String.format("%-15s", itemResult.getItemQuantity())
									+ String.format("$%.2f%-13s", itemResult.getItemPrice(), ""));
						}
					} else {
						System.out.println("This transaction has no items.");
					}
					System.out
							.println(String.format(String.format("%-25s", fittedString(result.getProductName(), 25))));
					System.out.println(String.format("Total: $%.2f", result.getTotalPrice()));
				}
				if (matchesCount == 0) {
					System.out.println("No matching transactions found.");
				}
				System.out.println("-".repeat(66) + "\n" + "-".repeat(66));
			} else {
				System.out.println("No transactions found in database");
			}
		}
	}

	/**
	 * A helper method which trims a String to a given maximum length. If the given
	 * String is shorter it's returned unchanged. Useful for formatted printing into
	 * tables.
	 * 
	 * @param input     - String being trimmed
	 * @param maxLength - the max length of the returned String
	 * @return the String with the end properly trimmed to a certain length
	 */
	private String fittedString(String input, int maxLength) {
		if (input.length() > maxLength) {
			return input.substring(0, maxLength);
		} else {
			return input;
		}

	}

	/**
	 * Print out order number, product name, product Id, quantity, and date of order
	 * for all outstanding orders
	 */
	public void listOutstandingOrders() {
		Iterator<Result> iterator = groceryStore.getAllOrders();
		if (iterator.hasNext()) {
			System.out.println("\n" + String.format("%-10s", "Order") + "  " + String.format("%-28s", "Product Name")
					+ "  " + String.format("%-17s", "Product ID") + "  " + String.format("%-35s", "Date of Order")
					+ "  " + String.format("%-13s", "Quantity"));
			System.out.println("-".repeat(106));
			for (Iterator<Result> counter = iterator; counter.hasNext();) {
				Result result = counter.next();
				// If the order's isOutstanding is True, print its details
				if (result.getIsOutstanding()) {
					System.out.println(String.format("%-10s", result.getOrderId()) + "  "
							+ String.format("%-25s", fittedString(result.getProductName(), 25)) + "  "
							+ String.format("%13s", result.getProductId()) + "  "
							+ String.format("%35s", result.getDateOfOrder().getTime().toString()) + "  "
							+ String.format("%13s", result.getOrderQuantity()));
				}
			}
		} else {
			// in case the order database is empty
			System.out.println("No orders in the database.");
		}
	}

	/**
	 * Displays all members in the database.
	 */
	public void listMembers() {
		// returns an iterator on the list of members (safely stored in a list of
		// results (data transfer logic)) in order to display it
		Iterator<Result> iterator = groceryStore.getAllMembers();
		// next if clause is carried out if the database of members is non-empty
		if (iterator.hasNext()) {
			// displays the header of the table
			System.out.println("\n" + String.format("%-9s", "Member ID") + "  " + String.format("%-23s", "Name") + "  "
					+ String.format("%-28s", "Address") + "  " + String.format("%-11s", "Ph. number") + "  "
					+ String.format("%-10s", "Joined"));
			System.out.println("-".repeat(89));
			// for loop prints all members
			for (Iterator<Result> counter = iterator; counter.hasNext();) {
				Result result = counter.next();
				System.out.println(String.format("%-9s", result.getMemberId()) + "  "
						+ String.format("%-23s", fittedString(result.getMemberName(), 23)) + "  "
						+ String.format("%-28s", fittedString(result.getMemberAddress(), 28)) + "  "
						+ String.format("%-11s", result.getMemberPhoneNumber()) + "  "
						+ String.format("%1$tm/%1$td/%1$tY", result.getMemberDateJoined()));
			}
		} else {
			// in case the member database is empty
			System.out.println("No members registred.");
		}
	}

	/**
	 * Lists all products in the database.
	 */
	public void listProducts() {
		Iterator<Result> iterator = groceryStore.getAllProducts();
		if (iterator.hasNext()) {
			// displays the header of the table
			System.out.println("\n" + String.format("%-10s", "Product ID") + "  "
					+ String.format("%-25s", "Product Name") + "  " + String.format("%-13s", "Current Price") + "  "
					+ String.format("%-13s", "Stock On Hand") + "  " + String.format("%-13s", "Reorder Level"));
			System.out.println("-".repeat(82));
			// for loop prints all products
			for (Iterator<Result> counter = iterator; counter.hasNext();) {
				Result result = counter.next();
				System.out.println(String.format("%-10s", result.getProductId()) + "  "
						+ String.format("%-25s", fittedString(result.getProductName(), 25)) + "  "
						+ String.format("%13.2f", result.getProductCurrentPrice()) + "  "
						+ String.format("%13s", result.getProductStockOnHand()) + "  "
						+ String.format("%13s", result.getProductReorderLevel()));
			}
		} else {
			// in case the product database is empty
			System.out.println("No products in the database.");
		}
	}

	/**
	 * Performs the main looping around the commands being issued by the user.
	 */
	public void loop() throws Exception {
		int action;
		help();
		do {
			System.out.println();
			action = getInteger("Enter option number (" + HELP + " for help): ", "Not a valid option number.");
			switch (action) {
			case ENROLL_MEMBER:
				enrollMember();
				break;
			case REMOVE_MEMBER:
				removeMember();
				break;
			case ADD_PRODUCT:
				addProduct();
				break;
			case CHECK_OUT:
				checkOut();
				break;
			case PROCESS_SHIPMENT:
				processShipment();
				break;
			case CHANGE_PRICE:
				changePrice();
				break;
			case RETRIEVE_PRODUCT_INFO:
				getProductInfo();
				break;
			case RETRIEVE_MEMBER_INFO:
				getMemberInfo();
				break;
			case PRINT_TRANSACTIONS:
				printTransactions();
				break;
			case LIST_OUTSTANDING_ORDERS:
				listOutstandingOrders();
				break;
			case LIST_ALL_MEMBERS:
				listMembers();
				break;
			case LIST_ALL_PRODUCTS:
				listProducts();
				break;
			case SAVE_DATA:
				save();
				break;
			case HELP:
				help();
				break;
			case EXIT:
				break;
			default:
				System.out.println("Not a valid option number.");
				break;
			}
		} while (action != EXIT);
	}

	/**
	 * Main method. Performs the welcome and good-bye, asks and loads the grocery
	 * store data from the disk, asks and performs test bed, and asks and performs
	 * the final save to the disk.
	 * 
	 * @param args N/A
	 */
	public static void main(String[] args) throws Exception {
		boolean loaded = false;
		boolean wantsToLoad = false;
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		System.out.println("★★★ WELCOME TO OUR GROCERY STORE ★★★");
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n");
		wantsToLoad = getYesOrNo("Would you like to load Store data from the disk?");
		if (wantsToLoad) {
			loaded = load();
		}
		if (loaded) {
			System.out.println("Grocery Store updated.");
		}
		if (!loaded || !wantsToLoad) {
			instance();
			System.out.println();
			if (getYesOrNo("Do you wish to generate a test bed and\ninvoke the functionality using asserts?")) {
				instance().testBed();
			} else {
				System.out.println("\nStarting a new, empty Grocery Store...");
			}
		}
		instance().loop();
		if (getYesOrNo("Would you like to save current Grocery Store data to disk?")) {
			instance().save();
		}
		System.out.println("\nThank you for using our Grocery Store!\nPlease come again soon!\n\nGOOD-BYE.\n");
		input.close();
	}

}
