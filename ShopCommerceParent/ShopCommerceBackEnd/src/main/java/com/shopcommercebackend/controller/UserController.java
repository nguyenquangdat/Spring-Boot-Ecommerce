package com.shopcommercebackend.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopcommercebackend.service.RoleService;
import com.shopcommercebackend.service.UserService;
import com.shopcommercecommon.model.Role;
import com.shopcommercecommon.model.User;



@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping(value = "/users")
	public String listUser(Model model ) {
		List<User> listUsers = userService.listUser();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}
	
	@GetMapping(value = "/user/new")
	public String userForm(Model model) {
		User user = new User();
		List<Role> listRoles = roleService.listRoles();
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles",listRoles);
		
		return "user_form";
	}
	
	@PostMapping(value = "/user/create")
	public String createUser(User user , RedirectAttributes redirectAttributes) {
		
		encoderPassword(user);
		userService.saveUser(user);
		
		redirectAttributes.addFlashAttribute("message", "Add a user sucessful");
		return "redirect:/users";
	}
	
	public void encoderPassword(User user) {
		String passwordEncoded = passwordEncoder.encode(user.getPassword());
		
		user.setPassword(passwordEncoded);
	}
}
