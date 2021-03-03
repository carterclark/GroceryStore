package store.facade;

import java.io.Serializable;
import java.util.Calendar;

import store.collections.MembersList;
import store.collections.OrdersList;
import store.collections.ProductsList;
import store.entities.Member;

public class GroceryStore implements Serializable {

	private static final long serialVersionUID = 1L;
	private static GroceryStore singleton;
	private static MembersList membersList;
	private static ProductsList productsList;
	private static OrdersList ordersList;

	private GroceryStore() {
		membersList = MembersList.instance();
		productsList = ProductsList.instance();
		ordersList = OrdersList.instance();
	}

	public static GroceryStore instance() {
		if (singleton == null) {
			singleton = new GroceryStore();
		}
		return singleton;
	}

	public static String addMember(String name, String address, String phoneNumber, Calendar dateJoined,
			double feePaid) {
		String memberId = "";
		memberId = membersList.addMemberUtil(new Member(name, address, phoneNumber, dateJoined, feePaid));
		return memberId;
	}
}
