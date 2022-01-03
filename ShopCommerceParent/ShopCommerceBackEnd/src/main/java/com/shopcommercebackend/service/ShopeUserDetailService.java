package com.shopcommercebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopcommercebackend.Repository.UserRepository;
import com.shopcommercecommon.model.User;


public class ShopeUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	// để kiểm tra thông tin xác thực có tồn tại trong hệ thông không. 
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user =userRepository.getUserByEmail(email);
		if(user != null ) {
			return new ShopUserDetail(user);
			
		}
		throw new UsernameNotFoundException("not found email : " + email); 
		
	}

}
