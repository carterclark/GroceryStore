package store.collections;

import java.util.ArrayList;
import java.util.Iterator;

import store.entities.Order;

public class OrdersList {

	private static ArrayList<Order> ordersList;

	private OrdersList() {
	}

	public static ArrayList<Order> instance() {
		if (ordersList == null) {
			ordersList = new ArrayList<Order>();
		}
		return ordersList;
	}

	public void addOrder(Order order) {
		ordersList.add(order);
	}

	public Iterator<Order> outstanding() {
		ArrayList<Order> result = new ArrayList<Order>();
		for (Iterator<Order> iterator = ordersList.iterator(); iterator.hasNext();) {
			Order order = iterator.next();
			if (!order.isFulfilled()) {
				result.add(order);
			}
		}
		return result.iterator();
	}

}
