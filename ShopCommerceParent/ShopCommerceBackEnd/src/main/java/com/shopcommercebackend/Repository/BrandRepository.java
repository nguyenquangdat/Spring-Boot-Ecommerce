package com.shopcommercebackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopcommercecommon.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

}
