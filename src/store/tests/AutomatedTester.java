package store.tests;

import java.util.Calendar;

import store.entities.Member;
import store.facade.GroceryStore;
import store.facade.Request;
import store.facade.Result;

public class AutomatedTester {

	private static GroceryStore groceryStore;
	private String[] names = { "n1", "n2", "n3" };
	private String[] addresses = { "a1", "a2", "a3" };
	private String[] phones = { "p1", "p2", "p3" };
	private Calendar calendar = Calendar.getInstance();
	private Calendar[] dates = new Calendar[3];
	private double[] feesPaid = { 12, 13.67, 14.90 };
	private Member[] members = new Member[3];

	public void testEnrollMember() {
		makeDates();
		for (int index = 0; index < members.length; index++) {
			Request.instance().setMemberName(names[index]);
			Request.instance().setMemberAddress(addresses[index]);
			Request.instance().setMemberPhoneNumber(phones[index]);
			Request.instance().setMemberDateJoined(dates[index]);
			Request.instance().setMemberFeePaid(feesPaid[index]);

			Result result = GroceryStore.instance().enrollMember(Request.instance());

			assert result.getResultCode() == Result.ACTION_SUCCESSFUL;
			assert result.getMemberName().equalsIgnoreCase(names[index]);
			assert result.getMemberPhoneNumber().equals(phones[index]);
			assert result.getMemberDateJoined().equals(dates[index]);
			assert result.getMemberFeePaid() == feesPaid[index];
		}
	}

	public void testRemoveMember() {

	}

	public void testAddProduct() {

	}

	public void testAll() {
		testEnrollMember();

		System.out.println("Testing was successful!");
	}

	public static void main(String[] args) {
		new AutomatedTester().testAll();

	}

	private void makeDates() {
		for (int i = 0; i < 3; i++) {
			calendar.add(Calendar.DATE, (-15 * i));
			dates[i] = calendar;
		}
	}

	private void makeAttributes() {

		String string = "{";

	}

}
