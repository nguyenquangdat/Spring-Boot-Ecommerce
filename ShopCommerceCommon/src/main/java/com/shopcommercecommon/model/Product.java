package com.shopcommercecommon.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false , length = 256 , unique = true)
	private String name;
	@Column(nullable = false , length = 256 , unique = true)
	private String alias;
	@Column(name="short_description",length = 512 , nullable = false)
	private String shortDescription;
	@Column(name = "full_description" ,length = 512 , nullable = false)
	private String fullDescription;
	@Column(name = "created_time")
	private Date createdTime;;
	@Column(name ="update_time")
	private Date updatedTime;
	@Column
	private boolean enabled;
	@Column
	private boolean inStock;
	@Column
	private float cost;
	@Column
	private float price;
	@Column(name  = "discount_percent")
	private float discountPercent;
	@Column
	private float length;
	@Column
	private float width;
	@Column
	private float height;
	@Column
	private float  weight;
	@ManyToOne
	@JoinColumn(name ="category_id")
	private Category category;
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@Column(name = "main_image" , nullable = false)
	private String mainImage;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private Set<ProductImage> images = new HashSet<>();
	
	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
	private Set<ProductDetail> productDetails = new HashSet<>();
	public Product() {
		super();
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
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getFullDescription() {
		return fullDescription;
	}
	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isInStock() {
		return inStock;
	}
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public String getMainImage() {
		return mainImage;
	}
	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}
	public Set<ProductImage> getImages() {
		return images;
	}
	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}
	
	
	public Set<ProductDetail> getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(Set<ProductDetail> productDetails) {
		this.productDetails = productDetails;
	}
	
	@Transient
	public String getMainImagePath() {
		if(id == null || mainImage == null) return "/images/images.jpg";
		return "/product-images/" + this.id + "/" + this.mainImage;
	}
	
	public void  addExtraImage(String imageName) {
		this.images.add(new ProductImage(imageName , this));
	}
	
	public void addProductDetial (String name, String value) {
		this.productDetails.add(new ProductDetail(name, value, this));
	}
	
}
