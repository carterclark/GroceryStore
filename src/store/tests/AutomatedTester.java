package store.tests;

import java.util.Calendar;
import java.util.Iterator;

import store.facade.GroceryStore;
import store.facade.GroceryStore.CheckOut;
import store.facade.Request;
import store.facade.Result;

/**
 * Automated unit testing for grocery store program
 * 
 * @author Ben Hines, Carter Clark, Chris Lara-Batencourt, Pavel Danek, Ricky
 *         Nguyen
 *
 */
public class AutomatedTester {

	private static GroceryStore groceryStore = GroceryStore.instance();

	private int memberCount = 7;
	private int removeMemberCount = 2;

	private String[] names = { "Paul", "George", "John", "Ringo", "Elton", "Elvis", "Pink" };
	private String[] addresses = { "123 Fair Ave.", "555 Ocean Front Pkwy.", "147 W 5th St.", "10 Downing St.",
			"1600 Pennsylvania Ave. NW", "25410 Sunset Blvd.", "211 Sandy Ridge Rd." };
	private String[] phones = { "3125559876", "3105551245", "6515552045", "02055590000", "2025551000", "2135558548",
			"2675551795" };
	private Calendar calendar = Calendar.getInstance();
	private Calendar[] dates = new Calendar[memberCount];
	private double[] feesPaid = { 12, 13.67, 14.90, 17, 20, 4.60, 5.50 };

	private int productCount = 20;
	private String[] productNames = { "Milk Whole 1qt", "Milk 2% 1gal", "Milk 2% 1qt", "Milk Skim 1gal",
			"Milk Skim 1qt", "Bread Italian 1lb", "Bread French 1pc", "Eggs Fresh 12pcs", "Juice Orange 1gal",
			"Juice Apple 1gal", "Water Distilled 1gal", "Water Sparkling 1l", "Cola 2l", "Cola Can 12oz",
			"Lemon/Lime Soda 2l", "Lemon/Lime Soda Can 12oz", "Root Beer 2l", "Root Beer Can 12oz",
			"Ice Cream Vanilla 1qt", "Ice Cream Chocolate 1qt" };
	private String[] productIds = { "P-1", "P-2", "P-3", "P-4", "P-5", "P-6", "P-7", "P-8", "P-9", "P-10", "P-11",
			"P-12", "P-13", "P-14", "P-15", "P-16", "P-17", "P-18", "P-19", "P-20" };
	private int[] reorderLevel = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	private double[] currentPrice = { 1.99, 3.79, 1.99, 3.79, 1.99, 4.5, 3.75, 2.29, 4.99, 3.99, 0.89, 1.49, 1.89, 0.6,
			1.89, 0.6, 1.89, 0.6, 3.97, 3.97 };

	private int orderCount = 20;
	private String[] orderNumbers = { "O-1", "O-2", "O-3", "O-4", "O-5", "O-6", "O-7", "O-8", "O-9", "O-10", "O-11",
			"O-12", "O-13", "O-14", "O-15", "O-16", "O-17", "O-18", "O-19", "O-20" };
	private String checkedOutMemberId = "M-4";
	private int[] checkedOutProductIndexes = { 4, 9, 14, 19 };
	private int checkedOutQuantity = 10;

	public void testEnrollMember() {

		makeDates();
		for (int index = 0; index < memberCount; index++) {

			Request.instance().setMemberName(names[index]);
			Request.instance().setMemberAddress(addresses[index]);
			Request.instance().setMemberPhoneNumber(phones[index]);
			Request.instance().setMemberDateJoined(dates[index]);
			Request.instance().setMemberFeePaid(feesPaid[index]);

			Result result = groceryStore.enrollMember(Request.instance());

			assert result.getResultCode() == Result.ACTION_SUCCESSFUL;
			assert result.getMemberName().equalsIgnoreCase(names[index]);
			assert result.getMemberPhoneNumber().equals(phones[index]);
			assert result.getMemberDateJoined().equals(dates[index]);
			assert result.getMemberFeePaid() == feesPaid[index];
		}
	}

	public void testRemoveMember() {

		for (int index = 0; index < removeMemberCount; index++) {

			Request.instance().setMemberId("M-" + (index + 1));

			Result result = groceryStore.removeMember(Request.instance());

			assert result.getResultCode() == Result.ACTION_SUCCESSFUL;
			assert result.getMemberName().equalsIgnoreCase(names[index]);
			assert result.getMemberPhoneNumber().equals(phones[index]);
			assert result.getMemberDateJoined().equals(dates[index]);
			assert result.getMemberFeePaid() == feesPaid[index];

			result = groceryStore.removeMember(Request.instance());

			assert result.getResultCode() == Result.INVALID_MEMBER_ID;
		}
	}

	public void testAddProduct() {

		for (int index = 0; index < productCount; index++) {

			Request.instance().setProductName(productNames[index]);
			Request.instance().setProductId(productIds[index]);
			Request.instance().setProductStockOnHand(0);
			Request.instance().setProductReorderLevel(reorderLevel[index]);
			Request.instance().setProductCurrentPrice(currentPrice[index]);

			Result result = groceryStore.addProduct(Request.instance());

			assert result.getResultCode() == Result.ACTION_SUCCESSFUL;
			assert result.getProductName().equalsIgnoreCase(productNames[index]);
			assert result.getProductId().equalsIgnoreCase(productIds[index]);
			assert result.getProductStockOnHand() == 0;
			assert result.getProductReorderLevel() == reorderLevel[index];
			assert result.getProductCurrentPrice() == currentPrice[index];
		}
	}

	public void testProcessShipment() {
		for (int index = 0; index < orderCount; index++) {
			Request.instance().setOrderId(orderNumbers[index]);

			Result result = groceryStore.processShipment(Request.instance());

			assert result.getResultCode() == Result.ACTION_SUCCESSFUL;
			assert result.getOrderId().equalsIgnoreCase(orderNumbers[index]);
			assert result.getProductName().equalsIgnoreCase(productNames[index]);
			assert result.getProductId().equalsIgnoreCase(productIds[index]);
			assert result.getProductStockOnHand() == reorderLevel[index] * 2;
		}
	}

	public void testCheckOut() {

		CheckOut checkOut = groceryStore.new CheckOut(checkedOutMemberId);
		for (int index : checkedOutProductIndexes) {

			Request.instance().setProductId(productIds[index]);
			Request.instance().setOrderQuantity(checkedOutQuantity);
			Result result = checkOut.addItem(Request.instance());
			// testing addItem from CheckOut (inner class of GroceryStore)
			assert result.getResultCode() == Result.ACTION_SUCCESSFUL;
		}

		Iterator<Result> returnedIterator = checkOut.closeCheckOut();
		int counter = 0;
		// testing closeCheckout from CheckOut (returning list of reordered products)
		for (Iterator<Result> iterator = returnedIterator; iterator.hasNext();) {

			Result result = iterator.next();
			assert result.getProductId().equalsIgnoreCase(productIds[checkedOutProductIndexes[counter]]);
			assert result.getOrderQuantity() == reorderLevel[checkedOutProductIndexes[counter]] * 2;
			// these order numbers start at orderCount + 1; the first orderCount orders were
			// creating when adding new products
			assert result.getOrderId().equalsIgnoreCase("O-" + (counter + orderCount + 1));
			counter++;
		}
	}

	public void testChangePrice() {

		double dollar = 1.00;

		for (int index = 0; index < productCount; index++) {
			// getting id and new price
			Request.instance().setProductId(productIds[index]);
			Request.instance().setProductCurrentPrice(currentPrice[index] + dollar);

			// changing the price and returning product info
			Result result = groceryStore.changePrice(Request.instance());

			assert result.getResultCode() == Result.ACTION_SUCCESSFUL;
			assert result.getProductId().equalsIgnoreCase(productIds[index]);
			assert result.getProductCurrentPrice() == currentPrice[index] + dollar;
		}

	}

	public void testAll() {

		testEnrollMember();

		testRemoveMember();

		testAddProduct();

		testProcessShipment();

		testCheckOut();

		testChangePrice();

		System.out.println("Automated testing was successful!");

//		UserInterface.instance().listMembers();
//		UserInterface.instance().listProducts();
	}

	public static void main(String[] args) {

		new AutomatedTester().testAll();

	}

	private void makeDates() {
		for (int i = 0; i < memberCount; i++) {
			calendar.add(Calendar.DATE, (-15 * i));
			dates[i] = calendar;
		}
	}
}
