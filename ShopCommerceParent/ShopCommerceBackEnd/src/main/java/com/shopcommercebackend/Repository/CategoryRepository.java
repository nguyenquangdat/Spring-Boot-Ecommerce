package com.shopcommercebackend.Repository;



import java.util.List;

import org.springframework.data.domain.Sort;
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
	
	@Query("SELECT c FROM Category c WHERE c.parent is NULL")
	public List<Category> findRootCategory(Sort sort);
	
	@Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword% "
			+ "AND c.parent is NULL ")
	public List<Category> findRootCategoryByKeyword(@Param("keyword") String keyword);
	
	Category findByName(String name);
	
	Category findByAlias(String alias);
	
	Long countById(Integer id);
	
	
}
