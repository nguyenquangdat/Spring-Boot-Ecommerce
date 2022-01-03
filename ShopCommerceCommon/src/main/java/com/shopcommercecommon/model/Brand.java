package com.shopcommercecommon.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "brand")
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
	@Column(nullable = false , length = 64 , unique = true)
 private String name;
	@Column(nullable = false , length = 64)
 private String logo;
 
	@ManyToMany
	@JoinTable(
			name = "brand_category",
		    joinColumns = @JoinColumn(name ="brand_id"),
			inverseJoinColumns = @JoinColumn(name = " category_id")
			)
 private Set<Category> categories = new HashSet();

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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Brand(String name, Set<Category> categories) {
		super();
		this.name = name;
		this.logo = "brand-logo.png";
		this.categories = categories;
	}

	public Brand() {
		super();
	}
	
	@Transient 
	String getLogoPath() {
		if(this.id == null) {
			return "/image/image-thumbnail.png";
		}
		return "/brand-logos" + this.id + "/" + this.logo;
	}

}
