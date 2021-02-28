package store.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import store.entities.Member;

public class MembersList implements Serializable {

	private static final long serialVersionUID = 1L;
	private static MembersList singleton;
	private ArrayList<Member> membersList;

	private MembersList() {
		membersList = new ArrayList<Member>();
	}

	public static MembersList instance() {
		if (singleton == null) {
			singleton = new MembersList();
		}
		return singleton;
	}

	public String addMember(Member member) {
		membersList.add(member);
		return membersList.get(membersList.size() - 1).getId();
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
