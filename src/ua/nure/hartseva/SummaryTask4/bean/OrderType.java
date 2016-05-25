package ua.nure.hartseva.SummaryTask4.bean;

public enum OrderType {
	ASC("asc"), DESC("desc");

	private final String value;

	OrderType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static OrderType fromValue(String v) {
		return valueOf(v.toUpperCase());
	}
}
