package com.shopcommercebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopcommercebackend.service.ShopUserDetail;
import com.shopcommercebackend.service.UserService;
import com.shopcommercecommon.model.User;

@Controller
public class AccountController {
	
	@Autowired
	UserService service ;
	
	//  lay object dai dien hien tai  da duoc log @AuthenticationPrincipal 
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal ShopUserDetail loggedUser, Model model ) {
			String email =loggedUser.getUsername();
			User user =service.getByEmail(email);
			
			model.addAttribute("user", user);
			
			return "account_form";
	}
	
}
