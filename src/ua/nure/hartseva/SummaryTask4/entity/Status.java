package ua.nure.hartseva.SummaryTask4.entity;

public enum Status {
	ACTIVE("active"), BLOCKED("blocked");

	private final String value;

	Status(String value) {
		this.value = value;
	}

	public static Status fromValue(String v) {
		return valueOf(v);
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return value;
	}

	public boolean is(Status status) {
		return this == status;
	}
	
	public static Status getOppositeStatus(Status currentStatus) {
		if (currentStatus.equals(Status.ACTIVE)) {
			return Status.BLOCKED;
		}
		return Status.ACTIVE;
	}
}
