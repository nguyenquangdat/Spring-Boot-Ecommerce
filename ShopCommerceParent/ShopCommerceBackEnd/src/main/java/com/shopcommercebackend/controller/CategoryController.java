package com.shopcommercebackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopcommercebackend.service.CategoryService;
import com.shopcommercecommon.model.Category;

@Controller
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/categories")
	public String listCategories(Model model) {
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "Categories";
	}
	
	@GetMapping("/category/{id}/enable/{state}")
	public String updateEnable(@PathVariable("id") Integer id , @PathVariable("state") boolean state
			, RedirectAttributes redirectAttributes) {
		categoryService.updateEnable(id, state);
		
		if(state == true) {
			redirectAttributes.addFlashAttribute("message", "change to true");
		}
		else redirectAttributes.addFlashAttribute("message", "change to false");
		
		return "redirect:/categories";
		
	}
}
