package com.shopcommercecommon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name = "roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 50 , nullable = false , unique = true)
	private String name;
	@Column(length = 255, nullable = false )
	private String desciption;
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
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	public Role() {
		super();
	}
	public Role(String name, String desciption) {
		super();
		this.name = name;
		this.desciption = desciption;
	}
	public Role(int id) {
		super();
		this.id = id;
	}
	@Override
	public String toString() {
		return this.name;
	}
	
	
	
	
}
