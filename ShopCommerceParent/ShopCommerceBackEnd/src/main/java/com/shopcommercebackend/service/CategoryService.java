package com.shopcommercebackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	
	public List<Category> getAllCategoriesForForm(){	
		Iterable<Category> categories =  categoryRepository.findAll();
		List<Category> listCategories = new ArrayList<>();
		for (Category category : categories) {
			if (category.getParent() == null) {
				
				listCategories.add(new Category(category.getName(),category.getId()));
				printfChildrenCategory(listCategories,category , 0);
				
			}
		}
		return listCategories;
	}
	
	private void printfChildrenCategory(List<Category> listCategories, Category parent, int sublevel) {

		int newSublevel = sublevel + 1;
		Set<Category> childrenCategories = parent.getChildren();
	
		for (Category subcategory : childrenCategories) {
			String name ="";
			for (int i = 0; i < newSublevel; i++) {
				name = name + "--";
			}
			 name = name + subcategory.getName();
			 listCategories.add(new Category(name,subcategory.getId()));
			printfChildrenCategory(listCategories ,subcategory,newSublevel);
			
		}
	}
	
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	
	}
}
