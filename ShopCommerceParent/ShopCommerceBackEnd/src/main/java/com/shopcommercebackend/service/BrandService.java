package com.shopcommercebackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopcommercebackend.Repository.BrandRepository;
import com.shopcommercebackend.Repository.CategoryRepository;
import com.shopcommercecommon.model.Brand;
import com.shopcommercecommon.model.Category;

@Service
public class BrandService {

	@Autowired
	BrandRepository brandRepository;
	@Autowired
	CategoryRepository categoryRepository;
	
	public List<Brand> getAllBrand(){
		
	List<Brand> brands = 	brandRepository.findAll();
	return brands;
	}
	
	public List<Category> getAllCategoryForm(){
		Sort sort = Sort.by("name");
		sort.ascending();
		List<Category> categories = categoryRepository.findRootCategory(sort);
		List<Category> listCategory = new ArrayList<>();
		for (Category category : categories) {
			listCategory.add(category);
			printfChildrenCategory(listCategory,category,0);
		}
		return listCategory;
	}

	private void printfChildrenCategory(List<Category> listCategory, Category category, int level) {
		int level1 =level +1;
		Set<Category> childrenCategory = category.getChildren();
		for (Category category1 : childrenCategory) {
			String  fakename="";
			for(int i =0 ; i<level1 ; i++) {
				fakename = fakename +"--";
			}
			category1.setName(fakename + category1.getName());
			listCategory.add(category1);
			printfChildrenCategory(listCategory,category1,level1);
		}
	} 
	
	public Brand findById(Integer id){
		Brand brand = brandRepository.findById(id).get();
		return brand;
	}
}
