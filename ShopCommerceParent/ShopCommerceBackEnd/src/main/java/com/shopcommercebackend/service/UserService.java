package com.shopcommercebackend.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopcommercebackend.Repository.UserRepository;
import com.shopcommercebackend.exception.USerNotfoundException;
import com.shopcommercecommon.model.User;


@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public List<User> listUser(){
		
		List<User> users =userRepository.findAll();
		 
		return users;
	}
	
	public void saveUser(User user) {
		if(user.getId() != null) {
			if(user.getPassword().isEmpty()) {
				user.setPassword(userRepository.findById(user.getId()).get().getPassword());
				userRepository.save(user);
			}
			else {
				 encoderPassword(user);
			     userRepository.save(user);
			}
		}
		else {
	    encoderPassword(user);
		userRepository.save(user);
		}
	}
	
	public User getOne(int id) throws USerNotfoundException {
		try {
			return   userRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new USerNotfoundException("Not found User have " + id);
		}
	}
	
	public void encoderPassword(User user) {
		String passwordEncoded = passwordEncoder.encode(user.getPassword());
		
		user.setPassword(passwordEncoded);
	}
	
	public void deleteUser(Integer id) throws USerNotfoundException {
		Long countUserId = userRepository.countById(id);
		if(countUserId == null || countUserId == 0) {
			throw new USerNotfoundException("Don't have User Have id" + id);
			
		}
		else {
			userRepository.deleteById(id);
		}
	}
}
