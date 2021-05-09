package com.shopcommercebackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopcommercebackend.Repository.UserRepository;
import com.shopcommercecommon.model.User;


@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	
	public List<User> listUser(){
		
		List<User> users =userRepository.findAll();
		 
		return users;
	}
	
	public void saveUser(User user) {
		
		userRepository.save(user);
	}
}
