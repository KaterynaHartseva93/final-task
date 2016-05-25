package ua.nure.hartseva.SummaryTask4.entity;

import java.io.Serializable;

public class Brand implements Serializable{

	private static final long serialVersionUID = -8817986852784162544L;
	
	private int id;
	private String name;

	public Brand(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
