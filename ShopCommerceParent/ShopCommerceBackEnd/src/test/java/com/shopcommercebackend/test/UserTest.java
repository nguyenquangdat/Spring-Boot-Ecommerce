package com.shopcommercebackend.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopcommercebackend.Repository.UserRepository;
import com.shopcommercecommon.model.Role;
import com.shopcommercecommon.model.User;



@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManger;
	
	
	@Test
	public void createUser() {
		Role role = entityManger.find(Role.class, 1); // find in the role have id as 1 , return object
		User user = new User("datnqhe130421@fpt.edu.vn", "123", "Dat", "Nguyen");
		user.getRoles().add(role);
			
		User user1 =userRepository.save(user);
		assertThat(user1.getId()).isGreaterThan(0);
	}
	
	
	//create many role
	@Test
	public void createListUser() {
		Role role1 = new Role(2);
		Role role2 = new Role(3);
		
		User user = new User("nguyenquangdat199999@gmail.com","123","Dat","Quang");
		user.getRoles().add(role1);
		user.getRoles().add(role2);
		
		User user1 =userRepository.save(user);
		
		assertThat(user1.getId()).isGreaterThan(0);
	}
	
	//test FindAll of Jpa 
	@Test
	public void testFindAll() {
		
		List<User> users = userRepository.findAll();
		users.forEach(user -> System.out.println(user));
		
		assertThat(users.size()).isGreaterThan(0);
	}
	
	//test findById of Jpa
	@Test
	public void testFindById() {
		User user = userRepository.findById(1).get();
		
		System.out.println(user);
		
		assertThat(user.getId()).isNotNull();
	}
	
	//Test update Enable
	@Test
	public void testUpdateEnable() {
		Integer id =1 ;
		userRepository.updateEnable(id, false);
	}
	
	@Test
	public void TestSearch() {
		String keyword = "a";
		int number =0;
		int pagesize=4;
		Pageable page = PageRequest.of(number, pagesize);
		
	Page<User> pageUsers=userRepository.findAll(keyword, page);
	
	  List<User> user = pageUsers.getContent();
	  user.forEach(x -> System.out.println(x));
		
	}
}
