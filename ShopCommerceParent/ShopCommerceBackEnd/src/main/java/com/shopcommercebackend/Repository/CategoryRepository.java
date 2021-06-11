package com.shopcommercebackend.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.shopcommercecommon.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	@Modifying
	@Transactional
	@Query("UPDATE Category c SET c.enabled = :enabled WHERE c.id = :id")
	public void updateEnable(@Param("enabled") boolean enabled, @Param("id") Integer id);
}
