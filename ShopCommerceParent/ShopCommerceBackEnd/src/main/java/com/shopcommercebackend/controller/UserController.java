package com.shopcommercebackend.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopcommercebackend.exception.USerNotfoundException;
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
		
		userService.saveUser(user);
		
		redirectAttributes.addFlashAttribute("message", "Add a user sucessful");
		return "redirect:/users";
	}
	
	
	@GetMapping("/user/edit/{id}")
	public String editUSer(@PathVariable("id") Integer id,Model model,RedirectAttributes redirectAttributes) {
		try {
			User user = userService.getOne(id);
			List<Role> listRoles = roleService.listRoles();
			model.addAttribute("user", user);
            model.addAttribute("listRoles",listRoles);
			return "user_form";
		} catch (USerNotfoundException e) {
			redirectAttributes.addFlashAttribute("meassge", e.getMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		
		try {
			userService.deleteUser(id);
			redirectAttributes.addFlashAttribute("message", "delete sucessfull");
			return "redirect:/users";
		} catch (USerNotfoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/users";
		}
	}
	
	// update enable
	@GetMapping(value = "user/{id}/enable/{state}")
	public String update(@PathVariable("id") Integer id , @PathVariable("state") boolean state , RedirectAttributes redirectAttributes) {
		userService.updateEnable(id, state);
		if(state == true) {
			redirectAttributes.addFlashAttribute("message", "changed to true");
		}
		else {
			redirectAttributes.addFlashAttribute("message", "changed to false");
		}
		return "redirect:/users";
	}
	
}

