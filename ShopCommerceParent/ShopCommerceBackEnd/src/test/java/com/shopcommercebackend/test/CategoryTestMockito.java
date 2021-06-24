package com.shopcommercebackend.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopcommercebackend.Repository.CategoryRepository;
import com.shopcommercebackend.service.CategoryService;
import com.shopcommercecommon.model.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryTestMockito {

	@MockBean
	CategoryRepository categoryRepository;
	
	@InjectMocks
	CategoryService categoryService;
	
	@Test
	public void testUniqueName() {
		Integer id = null;
		String name = "Memory";
		String alias = "Alias";
		
		Category category = new Category(id, name, alias);
		Mockito.when(categoryRepository.findByName(name)).thenReturn(category);
		Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(category);
		String result = categoryService.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("DuplicateName");
	}
	
	@Test
	public void testUniqueAlias() {
		Integer id = null;
		String name = "Memory";
		String alias = "Alias";
		
		Category category = new Category(id, name, alias);
		Mockito.when(categoryRepository.findByName(name)).thenReturn(null);
		Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(category);
		String result = categoryService.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("DuplicateAlias");
	}
	
	@Test
	public void testUniqueAliasEdit() {
		Integer id = 1;
		String name = "Memory";
		String alias = "Memory";
		
		Category category = new Category(2, name, alias);
		Mockito.when(categoryRepository.findByName(name)).thenReturn(null);
		Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(category);
		String result = categoryService.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("DuplicateAlias");
	}
	
	@Test
	public void testUniqueNameEdit() {
		Integer id = 1;
		String name = "Memory";
		String alias = "Memory";
		
		Category category = new Category(2, name, alias);
		Mockito.when(categoryRepository.findByName(name)).thenReturn(category);
		Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(category);
		String result = categoryService.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("DuplicateName");
	}
}
