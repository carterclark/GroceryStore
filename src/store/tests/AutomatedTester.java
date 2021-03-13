package store.tests;

import java.util.Calendar;

import store.facade.GroceryStore;
import store.facade.Request;
import store.facade.Result;

public class AutomatedTester {

	private static GroceryStore groceryStore = GroceryStore.instance();

	private int memberCount = 7;
	private int removeMemberCount = 2;
	private String[] names = { "Paul", "George", "John", "Ringo", "Elton", "n1", "n2" };
	private String[] addresses = { "123 Fair Ave.", "555 Ocean Front Pkwy.", "147 W 5th St.", "10 Downing St.",
			"1600 Pennsylvania Ave. NW", "a1", "a2" };
	private String[] phones = { "3125559876", "3105551245", "6515552045", "02055590000", "2025551000", "p1", "p2" };
	private Calendar calendar = Calendar.getInstance();
	private Calendar[] dates = new Calendar[7];
	private double[] feesPaid = { 12, 13.67, 14.90, 17, 20, 4.60, 5.50 };

	private int productCount = 20;
	private String[] productNames = { "Milk Whole 1qt", "Milk 2% 1gal", "Milk 2% 1qt", "Milk Skim 1gal",
			"Milk Skim 1gal", "Bread Italian 1lb", "Bread French 1pc", "Eggs Fresh 12pcs", "Juice Orange 1gal",
			"Juice Apple 1gal", "Water Distilled 1gal", "Water Sparkling 1l", "Cola 2l", "Cola Can 12oz",
			"Lemon/Lime Soda 2l", "Lemon/Lime Soda Can 12oz", "Root Beer 2l", "Root Beer Can 12oz",
			"Ice Cream Vanilla 1qt", "Ice Cream Chocolate 1qt" };
	private String[] productIds = { "P-1", "P-2", "P-3", "P-4", "P-5", "P-6", "P-7", "P-8", "P-9", "P-10", "P-11",
			"P-12", "P-13", "P-14", "P-15", "P-16", "P-17", "P-18", "P-19", "P-20" };
	private int[] stockOnHand = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	private int[] reorderLevel = { 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
	private double[] currentPrice = { 1.99, 3.79, 1.99, 3.79, 3.79, 4.5, 3.75, 2.29, 4.99, 3.99, 0.89, 1.49, 1.89, 0.6,
			1.89, 0.6, 1.89, 0.6, 3.97, 3.97 };

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

		for (int index = memberCount; index < (memberCount - removeMemberCount); index--) {

			Request.instance().setMemberId("M-" + index);

			Result result = groceryStore.removeMember(Request.instance());

			assert result.getResultCode() == Result.ACTION_SUCCESSFUL;
			assert result.getMemberName().equalsIgnoreCase(names[index]);
			assert result.getMemberPhoneNumber().equals(phones[index]);
			assert result.getMemberDateJoined().equals(dates[index]);
			assert result.getMemberFeePaid() == feesPaid[index];

			result = groceryStore.removeMember(Request.instance());

			assert result.getResultCode() == Result.ACTION_FAILED;
		}
	}

	public void testAddProduct() {

		for (int index = 0; index < productCount; index++) {

			Request.instance().setProductName(productNames[index]);
			Request.instance().setProductId(productIds[index]);
			Request.instance().setProductStockOnHand(stockOnHand[index]);
			Request.instance().setProductReorderLevel(reorderLevel[index]);
			Request.instance().setProductCurrentPrice(currentPrice[index]);

			Result result = groceryStore.addProduct(Request.instance());

			assert result.getResultCode() == Result.ACTION_SUCCESSFUL;
			assert result.getProductName().equalsIgnoreCase(productNames[index]);
			assert result.getProductId().equalsIgnoreCase(productIds[index]);
			assert result.getProductStockOnHand() == stockOnHand[index];
			assert result.getProductReorderLevel() == reorderLevel[index];
			assert result.getProductCurrentPrice() == currentPrice[index];
		}
	}

	public void testAll() {

		testEnrollMember();

		testRemoveMember();

		testAddProduct();

//		UserInterface.instance().listMembers();
//		UserInterface.instance().listProducts();
	}

	public static void main(String[] args) {

		new AutomatedTester().testAll();

		System.out.println("Automated testing was successful!");

	}

	private void makeDates() {
		for (int i = 0; i < memberCount; i++) {
			calendar.add(Calendar.DATE, (-15 * i));
			dates[i] = calendar;
		}
	}
}
