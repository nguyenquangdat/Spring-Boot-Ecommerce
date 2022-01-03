package com.shopcommercebackend.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopcommercebackend.Repository.ProductRepository;
import com.shopcommercecommon.model.Brand;
import com.shopcommercecommon.model.Category;
import com.shopcommercecommon.model.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductTest {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void testCreateProduct() {
		Brand brand = entityManager.find(Brand.class, 9);
		Category category = entityManager.find(Category.class, 2);
		Product product = new Product();
		product.setName("IPhone12ProMax");
		product.setAlias("Alias IPhone12Product");
		product.setShortDescription("this is shortDescription");
		product.setFullDescription("this is full description");
		product.setPrice(456);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		product.setCategory(category);
		product.setBrand(brand);
		productRepository.save(product);
	}

	@Test
	public void testCreateProduct2() {
		Brand brand = entityManager.find(Brand.class, 8);
		Category category = entityManager.find(Category.class, 4);
		Product product = new Product();
		product.setName("HeadPhonePro");
		product.setAlias("Alias HeadPhonePro");
		product.setShortDescription("this is shortDescription");
		product.setFullDescription("this is full description");
		product.setPrice(789);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		product.setCategory(category);
		product.setBrand(brand);
		productRepository.save(product);
	}

	@Test
	public void testSaveProductWithImages() {
		Integer productId = 15;
		Product product = productRepository.findById(productId).get();

		product.setMainImage("main.image.jpg");
		product.addExtraImage("extra image1.png");
		product.addExtraImage("extra image2.png");
		product.addExtraImage("extra image3.png");

		Product saveProduct = productRepository.save(product);
		assertThat(saveProduct.getImages().size()).isEqualTo(3);
	}

	@Test
	public void testSaveProductDetail() {
		Integer productId = 15;
		Product product = productRepository.findById(productId).get();

		product.addProductDetial("test", "11111");
		product.addProductDetial("test1", "22222");
		Product saveProduct = productRepository.save(product);
		assertThat(saveProduct.getProductDetails().size()).isEqualTo(2);
	}
}
