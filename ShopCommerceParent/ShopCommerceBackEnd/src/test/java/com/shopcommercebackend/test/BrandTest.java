package com.shopcommercebackend.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopcommercebackend.Repository.BrandRepository;
import com.shopcommercebackend.Repository.CategoryRepository;
import com.shopcommercecommon.model.Brand;
import com.shopcommercecommon.model.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandTest {

	@Autowired
	BrandRepository brandRepository;
	@Autowired
	CategoryRepository categoriRepository;
	
	@Test
	public void  createBrand () {
		Category category = categoriRepository.getOne(7);
		Set<Category> categories = new HashSet<>();
		categories.add(category);
		Brand brand = new Brand("Acerr",categories);
		Brand brandDB = brandRepository.save(brand);
		assertThat(brandDB.getId()).isGreaterThanOrEqualTo(0);
	}
	
	@Test
	public void  createBrand2 () {
		Category category = categoriRepository.getOne(7);
		Set<Category> categories = new HashSet<>();
		categories.add(category);
		Brand brand = new Brand("Apple",categories);
		Brand brandDB = brandRepository.save(brand);
		assertThat(brandDB.getId()).isGreaterThanOrEqualTo(0);
	}
	
	@Test
	public void  updateBrand2 () {
		Category category = categoriRepository.getOne(7);
		Category category2 = categoriRepository.getOne(2);
		Set<Category> categories = new HashSet<>();
		categories.add(category);
		categories.add(category2);
		Brand brand = brandRepository.findById(9).get();
		brand.setCategories(categories);
		Brand brandDB = brandRepository.save(brand);
		assertThat(brandDB.getId()).isGreaterThanOrEqualTo(0);
	}
	
	@Test
	public void getBrand() {
		Brand brand = brandRepository.findById(7).get();
		System.out.println(brand);
		assertThat(brand.getId()).isEqualTo(7);
	}
}
