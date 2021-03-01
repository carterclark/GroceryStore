package ui;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import store.facade.GroceryStore;

public class UserInterface implements Serializable {

	private static final long serialVersionUID = 1L;
	private static UserInterface singleton;
	private static GroceryStore groceryStore;

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
	private static String[] menu = new String[] { "ENROLL A NEW MEMBER", "REMOVE A MEMBER", "ADD A PRODUCT",
			"CHECK OUT MEMBER'S ITEMS", "PROCESS SHIPMENT", "CHANGE PRODUCT PRICE", "RETRIEVE PRODUCT INFO",
			"RETRIEVE MEMBER INFO", "PRINT MEMBER'S TRANSACTIONS", "LIST ALL OUTSTANDING ORDERS", "LIST ALL MEMBERS",
			"LIST ALL PRODUCTS", "SAVE ALL DATA TO DISK", "HELP (DISPLAYS THIS MENU)" };

	private UserInterface() {
		groceryStore = GroceryStore.instance();
	}

	public static UserInterface instance() {
		if (singleton == null) {
			singleton = new UserInterface();
		}
		return singleton;
	}

	public static String getString(String prompt, String errorMessage) {
		Scanner input = new Scanner(System.in);
		String read = "";
		boolean error = true;
		while (error) {
			error = false;
			System.out.print(prompt + " ");
			read = input.nextLine().trim();
			if (read.equals("")) {
				error = true;
			}
			if (error) {
				System.out.println(errorMessage);
			}
		}
		return read;
	}

	public static int getInt(String prompt, String errorMessage) {
		Scanner input = new Scanner(System.in);
		String read = "";
		int value = 0;
		boolean error = true;
		while (error) {
			error = false;
			System.out.print(prompt + " ");
			read = input.nextLine().trim();
			if (read.equals("")) {
				error = true;
			} else {
				try {
					value = Integer.parseInt(read);
				} catch (Exception exception) {
					error = true;
				}
			}
			if (error) {
				System.out.println(errorMessage);
			}
		}
		return value;
	}

	public static double getDouble(String prompt, String errorMessage) {
		Scanner input = new Scanner(System.in);
		String read = "";
		double value = 0;
		boolean error = true;
		while (error) {
			error = false;
			System.out.print(prompt + " ");
			read = input.nextLine().trim();
			if (read.equals("")) {
				error = true;
			} else {
				try {
					value = Double.parseDouble(read);
				} catch (Exception exception) {
					error = true;
				}
			}
			if (error) {
				System.out.println(errorMessage);
			}
		}
		return value;
	}

	public static Calendar getDate(String prompt, String errorMessage) {
		Scanner input = new Scanner(System.in);
		String read = "";
		boolean error = true;
		Calendar date = new GregorianCalendar();
		int month = 0;
		int day = 0;
		int year = 0;
		while (error) {
			error = false;
			System.out.print(prompt + " (MMDDYYYY) ");
			read = input.nextLine().trim();
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

	public static boolean getYesOrNo(String prompt, String errorMessage) {
		Scanner input = new Scanner(System.in);
		String read = "";
		char answer = ' ';
		boolean error = true;
		while (error) {
			error = false;
			System.out.print(prompt + " (Y/N) ");
			read = input.nextLine().trim();
			if (read.equals("")) {
				error = true;
			} else {
				answer = read.toUpperCase().charAt(0);
				if ((answer != 'Y') && (answer != 'N')) {
					error = true;
				}
			}
			if (error) {
				System.out.println(errorMessage);
			}
		}
		return (answer == 'Y');
	}

	public static Calendar getToday() {
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(System.currentTimeMillis());
		return date;
	}

	public static void help() {
		String output = "\n Your options:\n================================\n";
		for (int counter = 0; counter < 14; counter++) {
			output += String.format("%2s", (counter + 1)) + " = " + menu[counter] + "\n";
		}
		output += "--------------------------------\n 0 = EXIT PROGRAM\n";
		System.out.println(output);
	}

	public static void load() {
	}

	public static void save() {
	}

	public static void testBed() {
	}

	public static void addMember() {
	}

	public static void removeMember() {
	}

	public static void addProduct() {
	}

	public static void checkOut() {
	}

	public static void processShipment() {
	}

	public static void changePrice() {
	}

	public static void getProductInfo() {
	}

	public static void getMemberInfo() {
	}

	public static void printTransactions() {
	}

	public static void listOutstandingOrders() {
	}

	public static void listMembers() {
	}

	public static void listProducts() {
	}

	public static void loop() {
		int action;
		instance();
		do {
			help();
			action = getInt("Enter option number:", "Not a valid option number.");
			switch (action) {
			case ENROLL_MEMBER:
				addMember();
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
				break;
			case EXIT:
				break;
			default:
				System.out.println("Not a valid option number.");
				break;
			}
		} while (action != 0);
	}

	public static void main(String[] args) {
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		System.out.println("★★★ WELCOME TO GROCERY STORE ★★★");
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n");
		if (getYesOrNo("Would you like to load Store data from the disk?", "Please answer Y[es] or N[o].")) {
			load();
		} else {
			System.out.println();
			if (getYesOrNo("Do you wish to generate a test bed and\ninvoke the functionality using asserts?",
					"Please answer Y[es] or N[o].")) {
				testBed();
			} else {
				System.out.println("\nStarting a new, empty Grocery Store...");
			}
		}
		loop();
		System.out.println();
		if (getYesOrNo("Would you like to save current Store data to disk?", "Please answer Y[es] or N[o].")) {
			save();
		}
		System.out.println("\nThank you for using our Grocery Store!\nPlease come again soon!\n\nGOOD-BYE.\n");
	}

}
