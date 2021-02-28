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
			read = input.nextLine();
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
			read = input.nextLine();
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
			read = input.nextLine();
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
			read = input.nextLine();
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
			read = input.nextLine();
			if (read.equals("")) {
				error = true;
			} else {
				read = read.trim();
				if (read.length() == 0) {
					read = " ";
				}
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

	// public static void main(String[] args) {
	// }

}