package com.shopcommercebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopcommercebackend.service.CategoryService;

@RestController
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/categories/checkUnique")
	public String checkUnque(@Param("id") Integer id , @Param("name") String name,
			@Param("alias") String alias) {
		return categoryService.checkUnique(id, name, alias);
	}
}
