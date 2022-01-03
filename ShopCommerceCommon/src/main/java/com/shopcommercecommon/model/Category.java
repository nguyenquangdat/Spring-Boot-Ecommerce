package com.shopcommercecommon.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 125, nullable = false, unique = true)
	private String name;
	@Column(length = 125, nullable = false, unique = true)
	private String alias;
	@Column(length = 125, nullable = false)
	private String image;

	private boolean enabled;
	@OneToOne
	@JoinColumn(name = "parent_id")
	private Category parent;

	@OneToMany(mappedBy = "parent")
	private Set<Category> children = new HashSet<>();

	public Category() {
		super();
	}

	public Category(String name, Integer id) {
		super();
		this.id = id;
		this.name = name;
		this.alias = name;
		this.image = "default.png";
	}
	public static Category coppyFull(Category category) {
		Category categoryCoppy = new Category();
		categoryCoppy.setName(category.getName());
		categoryCoppy.setId(category.getId());
		categoryCoppy.setAlias(category.getAlias());
		categoryCoppy.setImage(category.getImage());
		categoryCoppy.setEnabled(category.isEnabled());
		categoryCoppy.setHasChild(category.getChildren().size()>0);
		return categoryCoppy;
	}
	
	
	public Category(Integer id, String name, String alias) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
	}

	public Category(String name, Category category) {
		super();
		this.name = name;
		this.alias = name;
		this.image = "default.png";
		this.parent = category;
	}

	public Category(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

	@Transient
	public String getPhotosImagePath() {
		if (id == null || image == null)
			return "images/avatar.jpg";
		return "/category-images/" + this.id + "/" + this.image;
	}

	
	
	@Override
	public String toString() {
		return this.name;
	}



	@Transient
	public Boolean hasChild;

	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}

	public Category(String name) {
		super();
		this.id = id;
		this.name = name;
		this.alias = name;
		this.image = "default.png";
	}
	
}
