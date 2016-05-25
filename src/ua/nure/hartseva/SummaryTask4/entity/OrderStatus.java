package ua.nure.hartseva.SummaryTask4.entity;

public enum OrderStatus {

	REGISTERED("registered"), PAID("paid"), CANCELLED("cancelled");
	
	private final String value;
	
	OrderStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
