package com.shopcommercebackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopcommercecommon.model.User;



public interface UserRepository extends JpaRepository<User, Integer>{

}
