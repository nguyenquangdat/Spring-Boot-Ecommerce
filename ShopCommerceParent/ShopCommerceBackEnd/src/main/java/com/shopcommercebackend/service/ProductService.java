package com.shopcommercebackend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopcommercebackend.Repository.ProductRepository;
import com.shopcommercecommon.model.Product;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getAllProduct() {
		List<Product> products = productRepository.findAll();
		return products;
	}
	
	public Product saveProduct (Product product){
		if(product.getId() == null) {
		  product.setCreatedTime(new Date());
		}
		if (product.getAlias() == null || product.getAlias().isEmpty()) {
			product.setAlias(product.getName());
		}else {
			product.setAlias(product.getAlias());
		}
		
		product.setUpdatedTime(new Date());
		return productRepository.save(product);
	}
	
	public void updateEnableProduct(Integer id , boolean enable) {
		if(id != null) {
			productRepository.updateEnableProduct(enable, id);
		}
	}
	
	public void deleteProduct(Integer id) {
		productRepository.deleteById(id);
	}
	
	public Product getProductById(Integer id) {
		return productRepository.findById(id).get();
	}
}
