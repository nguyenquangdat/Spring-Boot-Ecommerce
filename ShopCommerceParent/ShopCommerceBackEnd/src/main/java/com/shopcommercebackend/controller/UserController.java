package com.shopcommercebackend.controller;

import java.io.IOException;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopcommercebackend.FileUploadUtil;
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
		
		return listUser(1,model);
	}
	
	@GetMapping(value = "/users/page/{Pagenumber}")
	public String listUser(@PathVariable("Pagenumber") int pagenumber , Model model) {
		
		Page<User> page = userService.getListbyPage(pagenumber);
		List<User> listUsers = page.getContent();
		
		int currentPage = pagenumber;
		int totalPage = page.getTotalPages();
		model.addAttribute("currentPage",currentPage) ;// de xu ly cho nut next va previous
		model.addAttribute("totalPage", totalPage); // xu ly nut last , duyet cac element
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
	public String createUser(User user , RedirectAttributes redirectAttributes , @RequestParam("image") MultipartFile multipartFile  ) throws IOException {
		
	//	System.out.println(multipartFile.getOriginalFilename()); //check xem nhan name k :(
		
		if(! multipartFile.isEmpty()) {
			String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(filename);
			User user1 = userService.saveUser(user);
			
			String uploadDir = "user-photos/" + user1.getId();
			
			FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
		}
		
	//	userService.saveUser(user);
		
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

