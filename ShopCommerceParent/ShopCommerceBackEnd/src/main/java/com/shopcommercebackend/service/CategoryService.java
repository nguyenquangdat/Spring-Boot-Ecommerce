package com.shopcommercebackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopcommercebackend.Repository.CategoryRepository;
import com.shopcommercecommon.model.Category;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	public void updateEnable(Integer id , boolean enabled) {
		categoryRepository.updateEnable(enabled, id);
	}
}
