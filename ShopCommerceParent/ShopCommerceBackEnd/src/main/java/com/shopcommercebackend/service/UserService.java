package com.shopcommercebackend.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopcommercebackend.Repository.UserRepository;
import com.shopcommercebackend.exception.USerNotfoundException;
import com.shopcommercecommon.model.User;


@Service
public class UserService {
	
	public static final int PAGE_SIZE =4;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public List<User> listUser(){
		
		List<User> users =userRepository.findAll();
		 
		return users;
	}
	
	public User saveUser(User user) {
		if(user.getId() != null) {
			if(user.getPassword().isEmpty()) {
				user.setPassword(userRepository.findById(user.getId()).get().getPassword());
				
			}
			else {
				 encoderPassword(user);
			   
			}
		}
		else {
	    encoderPassword(user);
		
		}
		return userRepository.save(user);
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
	
	public void updateEnable(Integer id , boolean enable) {
		userRepository.updateEnable(id, enable);
	}
	
	public Page<User> getListbyPage(int PageNumber, String sortField, String sortDriect){
		Sort sort = Sort.by(sortField);
		sort = sortDriect.equals("asc") ? sort.ascending() : sort.descending();
		Pageable page = PageRequest.of(PageNumber-1, PAGE_SIZE,sort);
		Page<User> Listpages=userRepository.findAll(page);
		return Listpages;
	} 
}
