package ua.nure.hartseva.SummaryTask4.bean;

public enum OrderName {
	NAME("name"), PRICE("price"), DATE("creation_timestamp");

	private final String value;

	OrderName(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static OrderName fromValue(String v) {
		return valueOf(v.toUpperCase());
	}
}
