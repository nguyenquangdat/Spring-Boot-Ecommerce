package com.shopcommercebackend.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopcommercebackend.Repository.RoleRepository;
import com.shopcommercecommon.model.Role;





@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleTest {

	@Autowired
	RoleRepository roleRepository;
	
	@Test
	public void createRole() {
		
		Role role=roleRepository.save(new Role("Admin","Manager every thing"));
		assertThat(role.getId()).isGreaterThan(0);
	}
	@Test
	public void createRoles() {
		
		Role roleSalesperson=new Role("Salesperson","Manager product price ...");
		
		Role roleEditor= new Role("Editor","Manager categories , brands ...");
		
		Role roleShipper= new Role("Shipper","view product , view orders, update order and status");
		
		Role roleAssistant = new Role("Assistant", "manage requestion and reviews");
		
				roleRepository.saveAll(List.of(roleSalesperson,roleEditor,roleShipper,roleAssistant));
	}
}
