package com.shopcommercebackend.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopcommercecommon.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Modifying
	@Transactional
	@Query("Update Product p set p.enabled = :enabled Where p.id = :id")
	public void updateEnableProduct(@Param("enabled") boolean enable , @Param("id") Integer id);
}

