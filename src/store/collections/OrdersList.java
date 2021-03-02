package store.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import store.entities.Order;

public class OrdersList implements Serializable {

	private static final long serialVersionUID = 1L;
	private static OrdersList singleton;
	private ArrayList<Order> ordersList;

	private OrdersList() {
		ordersList = new ArrayList<Order>();
	}

	public static OrdersList instance() {
		if (singleton == null) {
			singleton = new OrdersList();
		}
		return singleton;
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
