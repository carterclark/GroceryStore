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

	// WE WILL RESOLVE THE DIFFERENCES BETWEEN addMember() METHODS ABOVE AND BELOW
	// LATER.

	/**
	 * This method takes member parameters, creates a new member object, and adds
	 * that member object to the Member array
	 * 
	 * @param name        the name of the member
	 * @param address     the address of the member
	 * @param phoneNumber the phone number of the member
	 * @param dateJoined  the date the member joined
	 * @param feePaid     amount paid by the member to the grocery store
	 */
	public void addMemember(String name, String address, String phoneNumber, String dateJoined, double feePaid) {

		membersList.add(new Member(name, address, phoneNumber, dateJoined, feePaid));

	}

	/**
	 * This method removes a single member from the members list array
	 * 
	 * @param id the unique id of the member to be removed
	 * @return true of the member was removed, false if the member was not removed
	 */
	public boolean removeMember(String id) {
		for (Member member : membersList) {
			if (member.getId().equalsIgnoreCase(id)) {
				membersList.remove(member);
				return true;
			}
		}
		return false;
	}

}
