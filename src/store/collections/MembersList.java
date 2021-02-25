package store.collections;

import java.util.ArrayList;
import java.util.Iterator;

import store.entities.Member;

public class MembersList {

	private static ArrayList<Member> membersList;

	private MembersList() {
	}

	public static ArrayList<Member> instance() {
		if (membersList == null) {
			membersList = new ArrayList<Member>();
		}
		return membersList;
	}

	public void addMember(Member member) {
		membersList.add(member);
	}

	public Member search(String memberId) {
		for (Iterator<Member> iterator = membersList.iterator(); iterator.hasNext();) {
			Member next = iterator.next();
			if (next.getId().equals(memberId)) {
				return next;
			}
		}
		return null;
	}

}
