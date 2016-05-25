package ua.nure.hartseva.SummaryTask4.entity;

public enum Role {
	GUEST("guest"), CLIENT("client"), ADMIN("admin");

	private final String value;

	Role(String value) {
		this.value = value;
	}

	public static Role fromValue(String v) {
		return valueOf(v);
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return value;
	}
}
