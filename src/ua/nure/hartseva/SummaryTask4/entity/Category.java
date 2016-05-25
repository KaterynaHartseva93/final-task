package ua.nure.hartseva.SummaryTask4.entity;

import java.io.Serializable;

public class Category implements Serializable{
	
	private static final long serialVersionUID = -4690334994335413484L;
	
	private int id;
	private String name;
	
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
