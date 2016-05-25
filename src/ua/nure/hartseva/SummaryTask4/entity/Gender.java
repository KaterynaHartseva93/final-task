package ua.nure.hartseva.SummaryTask4.entity;

public enum Gender {
	MALE("male"), FEMALE("female");

	private final String value;

	Gender(String value) {
		this.value = value;
	}

	public static Gender fromValue(String v) {
		for (Gender gender : values()) {
			if (gender.value.equalsIgnoreCase(v)) {
				return gender;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return value;
	}
}
