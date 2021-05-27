package com.shopcommercebackend.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopcommercebackend.Repository.UserRepository;
import com.shopcommercebackend.exception.USerNotfoundException;
import com.shopcommercecommon.model.User;


@Service
public class UserService {
	
	public static final int PAGE_SIZE =2;

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
	
	public Page<User> getListbyPage(int PageNumber, String sortField, String sortDriect,String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDriect.equals("asc") ? sort.ascending() : sort.descending();
		Pageable page = PageRequest.of(PageNumber-1, PAGE_SIZE,sort);
		if(keyword == null) {
		return userRepository.findAll(page);
		}
		else {
			return userRepository.findAll(keyword, page);
		}
		
	} 
	
	// export file CSV 
	
	public void exportCSV( HttpServletResponse httpServletResponse) throws IOException {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String time = dateFormat.format(new Date());
		
		String fileName="user_"+ time + ".csv";
		// dinh nghia kieu type cho no
		httpServletResponse.setContentType("text/csv");
		// respone download xuong -_- 
		String name = "Content-Disposition";	
		//kem theo tep
		String value  = "attachment; fileName="+fileName;
		
		httpServletResponse.setHeader(name, value);
		
		ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(httpServletResponse.getWriter(), 
				CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"ID" , "Email", "First Name ", " Last Name" , "Roles", "Enables"};
		//have to mapping with filed of entity
		String[] fileMapping = {"id","email","firstName","lastName","roles","enabled"};
		
		List<User> users =userRepository.findAll();
		//wirte the head 
		csvBeanWriter.writeHeader(csvHeader);
		
		// write data into file csv
		for(User user : users) {
			csvBeanWriter.write(user, fileMapping);
		}
		
		csvBeanWriter.close();
	}

	
	
}
