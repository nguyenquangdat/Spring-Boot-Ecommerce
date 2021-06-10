package com.shopcommercebackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopcommercecommon.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
