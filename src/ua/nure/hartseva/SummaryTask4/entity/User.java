package ua.nure.hartseva.SummaryTask4.entity;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 2857101978312626636L;
	
	private int id;
	private Role role;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private Status status;
	private Gender gender;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Role getRole() {
		return role;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [role=" + role + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", status=" + status + ", gender=" + gender + "]";
	}
}
