package com.shopcommercebackend.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodeTest {

	@Test
	public void testEncodePassword() {
		PasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
		String rawpassword = "hihi1234";
		String encodePaworrd = cryptPasswordEncoder.encode(rawpassword);
		System.out.println(encodePaworrd);
				
	}
}
