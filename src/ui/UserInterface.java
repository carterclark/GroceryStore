package ui;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Scanner;

import store.facade.GroceryStore;
import store.facade.GroceryStore.CheckOut;
import store.facade.Request;
import store.facade.Result;

/**
 * Class UserInterface represents the front end of the house, interacting
 * directly with the user (grocery store clerk or even a customer). It also
 * interacts with the business logic of the back end via data transfer pattern,
 * which is free of any business logic itself, to keep the safety at maximum.
 * This class is a singleton.
 * 
 * @author
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
	 * Collects a date input from the user. Requires an input in format MMDDYYYY.
	 * 
	 * @param prompt       - a custom message prompting for a date input
	 * @param errorMessage - a custom error message in case of invalid input
	 * @return date entered by user (type Calendar)
	 */
	public static Calendar getDate(String prompt, String errorMessage) {
		String read = "";
		boolean error = true;
		Calendar date = new GregorianCalendar();
		int month = 0;
		int day = 0;
		int year = 0;
		while (error) {
			error = false;
			read = getString(prompt + " (MMDDYYYY) ");
			if (read.length() < 8) {
				error = true;
			} else {
				try {
					month = Integer.parseInt(read.substring(0, 2));
					day = Integer.parseInt(read.substring(2, 4));
					year = Integer.parseInt(read.substring(4, 8));
				} catch (Exception exception) {
					error = true;
				}
			}
			if (month < 1 || month > 12 || day < 1 || day > 31 || year < 1800) {
				error = true;
			}
			if (error) {
				System.out.println(errorMessage);
			}
		}
		date.set(year, month - 1, day);
		return date;
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
	public static void load() {
	}

	/**
	 * Saves the grocery data to the disk.
	 */
	public void save() {
	}

	/**
	 * Runs a test bed with predetermined sets of data applied on the grocery store.
	 */
	public void testBed() {
	}

	/**
	 * Enrolls a new member. Uses Request and Result (data transfer logic) to send
	 * information to, and retrieve it from, the facade.
	 */
	public void enrollMember() {
		String name = getString("Enter new member's name: ");
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
		Request.instance().setMemberId(getString("Enter member's ID to be removed: "));
		Result result = groceryStore.removeMember(Request.instance());
		switch (result.getResultCode()) {
		case Result.INVALID_MEMBER_ID:
			System.out
					.println("No such member with id: " + Request.instance().getMemberId() + ", at the grocery store.");
			break;
		case Result.ACTION_FAILED:
			System.out.println("Member could not be removed.");
			break;
		case Result.ACTION_SUCCESSFUL:
			System.out.println("Member has been removed.");
			break;
		default:
			System.out.println("An error has occured.");
		}
	}

	/**
	 * Adds a product to the database if there are no products of the same name or
	 * id already present
	 */
	public void addProduct() {

		do {
			String name = getString("Enter product's name: ");
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
			if (result.getResultCode() != Result.ACTION_SUCCESSFUL) {
				System.out.println("Product could not be added");
			} else {
				System.out.println("Product " + result.getProductName() + " added.");
			}
		} while (getYesOrNo("Add another product?"));

	}

	/**
	 * Performs a member's checkout. ("Buying items".)
	 */
	public void checkOut() {
		String memberId = getString("Enter member's ID (M-<number>): ");
		// next if clause is carried out if the member exists
		if (groceryStore.memberIdExists(memberId)) {
			// opening a checkout
			CheckOut checkOut = groceryStore.new CheckOut(memberId);
			// do the loop until there are no more items to check out
			do {
				System.out.println();
				// loading request (data transfer logic) with relevant information
				Request.instance().setProductId(getString("Enter item's product ID: "));
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
				case Result.INVALID_PRODUCT_ID:
					System.out.println("Invalid product ID. Unable to check out item.");
					break;
				case Result.INVALID_ORDER_QUANTITY:
					System.out.println("Invalid item quantity. Unable to check out item.");
					break;
				}
			} while (getYesOrNo("More items to check out?"));
			// displaying total
			System.out.println("\nYOUR TOTAL IS: " + String.format("$%.2f", checkOut.getTotalPrice()) + "\n");
			// the clerk confirms and collects physical cash OR doesn't confirm and thereby
			// cancels the checkout
			if (getYesOrNo("Transaction confirmed by collecting cash?")) {
				System.out.println("Checkout successful. We thank you.");
				// performing checkout close, which adds a transaction ( = checkout) to member's
				// history and reorders product(s) which got low in supply if necessary;
				// returned is an iterator over a list of products (stored in a list of results
				// for safety) that were reordered (to display) - if the list is empty no
				// product had to be reordered
				Iterator<Result> iterator = checkOut.closeCheckOut();
				if (iterator.hasNext()) {
					System.out.println();
					// for loop prints the reordered products
					for (Iterator<Result> counter = iterator; counter.hasNext();) {
						Result result = counter.next();
						System.out.println("Item " + result.getProductName() + " will be reordered. (Order quantity: "
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

	public void processShipment() {
	}

	public void changePrice() {
	}

	public void getProductInfo() {
	}

	public void getMemberInfo() {
	}

	public void printTransactions() {
	}

	public void listOutstandingOrders() {
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
			System.out.println(String.format("%-9s", "Member ID") + ", " + String.format("%-23s", "Name") + ", "
					+ String.format("%-28s", "Address") + ", " + String.format("%-11s", "Ph. number") + ", "
					+ String.format("%-10s", "Joined"));
			System.out.println("-".repeat(89));
			// for loop prints all members
			for (Iterator<Result> counter = iterator; counter.hasNext();) {
				Result result = counter.next();
				// fields fittedName and fittedAddress store trimmed off versions of name and
				// address to fit in the table in case they're too long
				String fittedName, fittedAddress;
				if (result.getMemberName().length() > 23) {
					fittedName = result.getMemberName().substring(0, 23);
				} else {
					fittedName = result.getMemberName();
				}
				if (result.getMemberAddress().length() > 28) {
					fittedAddress = result.getMemberAddress().substring(0, 28);
				} else {
					fittedAddress = result.getMemberAddress();
				}
				System.out.println(String.format("%-9s", result.getMemberId()) + ", "
						+ String.format("%-23s", fittedName) + ", " + String.format("%-28s", fittedAddress) + ", "
						+ String.format("%-11s", result.getMemberPhoneNumber()) + ", "
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
	}

	/**
	 * Performs the main looping around the commands being issued by the user.
	 */
	public void loop() {
		int action;
		help();
		do {
			System.out.println();
			action = getInteger("Enter option number (" + HELP + " for help): ", "Not a valid option number.");
			System.out.println();
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
		} while (action != 0);
	}

	/**
	 * Main method. Performs the welcome and good-bye, asks and loads the grocery
	 * store data from the disk, asks and performs test bed, and asks and performs
	 * the final save to the disk.
	 * 
	 * @param args N/A
	 */
	public static void main(String[] args) {
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		System.out.println("★★★ WELCOME TO OUR GROCERY STORE ★★★");
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n");
		if (getYesOrNo("Would you like to load Store data from the disk?")) {
			load();
		} else {
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
