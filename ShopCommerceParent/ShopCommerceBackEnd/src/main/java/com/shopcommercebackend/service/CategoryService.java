package com.shopcommercebackend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopcommercebackend.Repository.CategoryRepository;
import com.shopcommercebackend.exception.CategoryNotFoundException;
import com.shopcommercecommon.model.Category;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	public List<Category> getAllCategories(String sortDirect) {
		Sort sort = Sort.by("name");
		
	    if  (sortDirect.equals("asc")) {	
			sort = sort.ascending();
		}else if (sortDirect.equals("desc")) {
			sort =sort.descending();
		}
		
		List<Category> rootCategory = categoryRepository.findRootCategory(sort);
		List<Category> categories = hirarchicalCategory(rootCategory,sortDirect);

		return categories;
	}

	private List<Category> hirarchicalCategory(List<Category> rootCategories,String sortDirect) {
		List<Category> categories = new ArrayList();
		for (Category rootCategory : rootCategories) {
			Category category = Category.coppyFull(rootCategory);
			categories.add(category);
			childCategory(categories, rootCategory, 0,sortDirect);
		}
		return categories;
	}

	private void childCategory(List<Category> categories, Category root, int level,String sortDirect) {
		int currentlever = level + 1;
		Set<Category> childrenCategories = sortedCategories(root.getChildren(),sortDirect);
		for (Category childCategory : childrenCategories) {
			String name = "";
			for (int i = 0; i < currentlever; i++) {
				name = name + "--";
			}
			name = name + childCategory.getName();
			childCategory.setName(name);
			Category category = Category.coppyFull(childCategory);
			categories.add(category);
			childCategory(categories, childCategory, currentlever,sortDirect);
		}

	}

	public void updateEnable(Integer id, boolean enabled) {
		categoryRepository.updateEnable(enabled, id);
	}

	public List<Category> getAllCategoriesForForm() {
		Sort sort = Sort.by("name");
		sort.ascending();
		Iterable<Category> categories = categoryRepository.findRootCategory(sort);
		List<Category> listCategories = new ArrayList<>();
		for (Category category : categories) {
			if (category.getParent() == null) {

				listCategories.add(new Category(category.getName(), category.getId()));
				Set<Category> childrenCategories = category.getChildren();
				printfChildrenCategory(listCategories, category, 0);

			}
		}
		return listCategories;
	}

	private void printfChildrenCategory(List<Category> listCategories, Category parent, int sublevel) {

		int newSublevel = sublevel + 1;
		Set<Category> childrenCategories = sortedCategories(parent.getChildren());

		for (Category subcategory : childrenCategories) {
			String name = "";
			for (int i = 0; i < newSublevel; i++) {
				name = name + "--";
			}
			name = name + subcategory.getName();
			listCategories.add(new Category(name, subcategory.getId()));
			printfChildrenCategory(listCategories, subcategory, newSublevel);

		}
	}

	public Category saveCategory(Category category) {
		return categoryRepository.save(category);

	}
	
	public Category getOneCategoy (Integer id) {
		Category category = categoryRepository.findById(id).get();
		return category;
	}
	
	public String checkUnique(Integer id , String name , String alias) {
		boolean isUnique = (id ==null || id ==0);
		Category category = categoryRepository.findByName(name);
		if(isUnique) {
			if(category != null) {
				return "DuplicateName";
			}
			else {
				Category categoryByAlias = categoryRepository.findByAlias(alias);
				if(categoryByAlias != null) {
					return "DuplicateAlias";
				}
			}
		}
		// update 
		else {
			if ( category != null && category.getId() != id) {
				return "DuplicateName";
			}
			else {
				Category categoryByAliasEdit = categoryRepository.findByAlias(alias);
				if(categoryByAliasEdit != null && categoryByAliasEdit.getId() != id) {
					return "DuplicateAlias";
				}
			}
		}
		return "OK";
	}
	
	public  Set<Category> sortedCategories(Set<Category> categories,String sort ) {
		
		Set<Category> sortedCategories = new TreeSet<>(new Comparator<Category>() {

			@Override
			public int compare(Category category1, Category category2) {
					if(sort.equals("desc")) {
						return category2.getName().compareTo(category1.getName());
					}
					else {
						return category1.getName().compareTo(category2.getName());
					}
				
			}
		});
		sortedCategories.addAll(categories);
		return sortedCategories;
		
	}
	
	public  Set<Category> sortedCategories(Set<Category> categories){
		return sortedCategories(categories,"asc");
	}
	
	public void deleteCategory(Integer id) throws CategoryNotFoundException {
		Long countCategory = categoryRepository.countById(id);
		if(countCategory == null || countCategory ==0) {
			throw new CategoryNotFoundException("Can't find category by id : " + id);
		}
		else {
			categoryRepository.deleteById(id);
		}
	}
}


	
