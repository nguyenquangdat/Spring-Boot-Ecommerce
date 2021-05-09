package com.shopcommercebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shopcommercecommon.model", "com.shopcommercebackend.repository"})
public class ShopCommerceBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopCommerceBackEndApplication.class, args);
	}
	
	
}
