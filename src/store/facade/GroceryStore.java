package store.facade;

import java.io.Serializable;

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

	public static Result addMember(Request request) {
		Result result = new Result();
		String memberId = "";
		memberId = membersList.addMember(new Member(request.getMemberName(), request.getMemberAddress(),
				request.getMemberPhoneNumber(), request.getMemberDateJoined(), request.getMemberFeePaid()));
		result.setMemberId(memberId);
		if (memberId != "") {
			result.setResultCode(result.ACTION_SUCCESSFUL);
		} else {
			result.setResultCode(result.ACTION_FAILED);
		}
		return result;
	}
}
