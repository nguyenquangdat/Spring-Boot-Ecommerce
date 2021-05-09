package com.shopcommercebackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopcommercebackend.Repository.RoleRepository;
import com.shopcommercecommon.model.Role;



@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public List<Role> listRoles(){
		List<Role> roles = roleRepository.findAll();
		return roles;
	}
}
