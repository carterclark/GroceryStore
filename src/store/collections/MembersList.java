package store.collections;

import java.util.ArrayList;

import store.entities.Member;

public class MembersList {

	private ArrayList<Member> membersList = new ArrayList<Member>();

	public void addMember(Member member) {
		membersList.add(member);
	}

}
