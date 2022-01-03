package com.shopcommercebackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shopcommercebackend.service.BrandService;
import com.shopcommercecommon.model.Brand;
import com.shopcommercecommon.model.Category;
import com.shopcommercecommon.model.CategoryDTO;

@RestController
public class BrandRestController {

	@Autowired
	private BrandService brandService;
	
	@GetMapping("brand/{id}/categories")
	public List<CategoryDTO> listCategoryByBrand(@PathVariable(name = "id") Integer brandId) throws BrandNotFoundExeption{
		List<CategoryDTO> listCategories = new ArrayList<>();
		try {
			Brand brand = brandService.findById(brandId);
			Set<Category> categories = brand.getCategories();
			for (Category category : categories) {
				CategoryDTO categoryDTO = new CategoryDTO(category.getId(),category.getName());
				listCategories.add(categoryDTO);
			}
			return listCategories;
		} catch (Exception e) {
			throw new BrandNotFoundExeption();
		}
	}
}
