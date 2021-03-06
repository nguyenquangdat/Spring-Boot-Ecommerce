package com.shopcommercebackend.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.shopcommercebackend.Repository.CategoryRepository;
import com.shopcommercecommon.model.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryTest {

	@Autowired
	CategoryRepository categoryRepository;

	@Test
	public void createRootCategory() {
		Category category = new Category("Iphone");

		Category category2 = categoryRepository.save(category);
		assertThat(category2.getId()).isGreaterThan(0);
	}

	@Test
	public void testSubCategory() {
		Category parentCategory = new Category(5);
		Category category = new Category("Memory", parentCategory);

		categoryRepository.save(category);

	}

	@Test
	public void testGetCategory() {
		Category category = categoryRepository.findById(1).get();

		Set<Category> childCategories = category.getChildren();
		for (Category subcategory : childCategories) {
			System.out.println(subcategory.getName());
		}
	}

	@Test
	public void testHierarchicalCategory() {
		Iterable<Category> categories = categoryRepository.findAll();

		for (Category category : categories) {
			if (category.getParent() == null) {
				System.out.println(category.getName());
				
				printfChildrenCategory(category , 0);
				
			}
		}
	}

	private void printfChildrenCategory(Category parent, int sublevel) {

		int newSublevel = sublevel + 1;
		Set<Category> childrenCategories = parent.getChildren();
		for (Category subcategory : childrenCategories) {
			for (int i = 0; i < newSublevel; i++) {
				System.out.print("--");
			}
			System.out.println(subcategory.getName());
			
			printfChildrenCategory(subcategory,newSublevel);
		}
	}
	
	@Test
	public void findRootCategory() {
	List<Category> categories= 	categoryRepository.findRootCategory(Sort.by("name").ascending());
		categories.forEach(x -> System.out.println(x));
	}
	
	@Test
	public void findRootCategoryByKey(){
		List<Category> categories = categoryRepository.findRootCategoryByKeyword("c");
		categories.forEach(x -> System.out.println(x));
	}
	
	@Test
	public void findByName() {
		Category category = categoryRepository.findByName("Memory");
		
		assertThat(category.getId()).isGreaterThan(0);
		assertThat(category.getName()).isEqualTo("Memory");
	}
	
	@Test
	public void findByAlias() {
		Category category = categoryRepository.findByAlias("Memory");
		assertThat(category.getId()).isGreaterThan(0);
		assertThat(category.getAlias()).isEqualTo("Memory");
	}
}
