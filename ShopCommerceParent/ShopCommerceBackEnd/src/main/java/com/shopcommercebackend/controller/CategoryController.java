package com.shopcommercebackend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopcommercebackend.FileUploadUtil;
import com.shopcommercebackend.Repository.CategoryRepository;
import com.shopcommercebackend.exception.CategoryNotFoundException;
import com.shopcommercebackend.service.CategoryService;
import com.shopcommercecommon.model.Category;

@Controller
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("/categories")
	public String listCategories(Model model, @Param("sortDirect") String sortDirect) {
		if (sortDirect ==null || sortDirect.isEmpty()) {
			sortDirect = "asc";
		}
		String reversesortDirect = sortDirect.equals("asc") ? "desc" : "asc";
		List<Category> categories = categoryService.getAllCategories(sortDirect);
		model.addAttribute("categories", categories);
		model.addAttribute("reversesortDirect", reversesortDirect);
		return "Categories";
	}

	@GetMapping("/category/{id}/enable/{state}")
	public String updateEnable(@PathVariable("id") Integer id, @PathVariable("state") boolean state,
			RedirectAttributes redirectAttributes) {
		categoryService.updateEnable(id, state);

		if (state == true) {
			redirectAttributes.addFlashAttribute("message", "change to true");
		} else
			redirectAttributes.addFlashAttribute("message", "change to false");

		return "redirect:/categories";

	}

	@GetMapping("/category/new")
	public String categoryForm(Model model) {
		Category category = new Category();
		List<Category> categories = categoryService.getAllCategoriesForForm();
		model.addAttribute("category", category);
		model.addAttribute("categories", categories);
		return "category_form";
	}

	@PostMapping("/category/create")
	public String saveCategory(Category category, @RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) throws IOException {
		
			String fileName = multipartFile.getOriginalFilename();
			category.setImage(fileName);
			categoryService.saveCategory(category);
			// de cung hang vs shoppmeBackend and fontEnd vi ca 2 cung dung
			String uploadDri = "../category-images/"+category.getId(); 
			FileUploadUtil.cleanFile(uploadDri);
			FileUploadUtil.saveFile(uploadDri, fileName, multipartFile);
			redirectAttributes.addFlashAttribute("message", "add sucessfull");
		return "redirect:/categories";
	}
	
	@GetMapping("/category/edit/{id}")
	public String editCategory(@PathVariable("id") Integer id, Model model) {
		Category category = categoryService.getOneCategoy(id);
		List<Category> categories = categoryService.getAllCategoriesForForm();
		model.addAttribute("category", category);
		model.addAttribute("categories",categories);
		return "category_form";
	}
	
	
	@GetMapping("/category/delete/{id}")
	public String deleteCategory(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes) {
		try {
			categoryService.deleteCategory(id);
			String categoryDriect = "../category-images/"+id;
			FileUploadUtil.deleteCategoryDriect(categoryDriect);
			redirectAttributes.addFlashAttribute("message", "delete sucessfull");
		} catch (CategoryNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/categories";
	}

}
