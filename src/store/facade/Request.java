package store.facade;

public class Request extends DataTransfer {

	private static Request request;

	private Request() {
	}

	public static Request instance() {
		if (request == null) {
			request = new Request();
		}
		return request;
	}

}
