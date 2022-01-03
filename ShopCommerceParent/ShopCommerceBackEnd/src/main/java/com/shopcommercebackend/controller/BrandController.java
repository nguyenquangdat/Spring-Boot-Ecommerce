package com.shopcommercebackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopcommercebackend.service.BrandService;
import com.shopcommercecommon.model.Brand;
import com.shopcommercecommon.model.Category;

@Controller
public class BrandController {

	@Autowired
	BrandService brandService;
	
	@GetMapping("/brands")
	public String ListBrand(Model model) {
		
		List<Brand> brands = brandService.getAllBrand();
		model.addAttribute("brands", brands);
		return "brand";
	}
	
	@GetMapping("/brand/new")
	public String createBrand(Model model) {
		Brand brand = new Brand();
		List<Category> categories = brandService.getAllCategoryForm();
		for (Category category : categories) {
			System.out.println(category.getName());
		}
		model.addAttribute("brand", brand);
		model.addAttribute("categories", categories);
		return "brand_form";
	}
}
