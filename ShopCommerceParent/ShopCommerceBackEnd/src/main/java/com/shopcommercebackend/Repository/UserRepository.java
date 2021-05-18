package com.shopcommercebackend.Repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopcommercecommon.model.User;



public interface UserRepository extends JpaRepository<User, Integer>{
	Long countById(Integer id);
	
	@Modifying
	@Transactional
	@Query("Update User u SET u.enabled = :enabled WHERE u.id = :id")
	public void updateEnable(@Param("id") Integer id , @Param("enabled") boolean enabled);
	
	@Query("SELECT u FROM User u  WHERE u.firstName LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
}
