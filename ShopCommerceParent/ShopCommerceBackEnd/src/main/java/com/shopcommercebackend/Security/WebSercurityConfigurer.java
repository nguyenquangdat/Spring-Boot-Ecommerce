package com.shopcommercebackend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.shopcommercebackend.service.ShopeUserDetailService;

@Configuration
@EnableWebSecurity
public class WebSercurityConfigurer extends WebSecurityConfigurerAdapter{
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new ShopeUserDetailService();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/users/**").hasAuthority("Admin") // tao quyen truy cap
		.antMatchers("/categories/**").hasAnyAuthority("Admin", "Editor")
		.antMatchers("/brands/**").hasAnyAuthority("Admin", "Editor")
		.antMatchers("/products/**").hasAnyAuthority("Admin", "Editor","Salesperson","Shipper")
		.antMatchers("/articles/**").hasAnyAuthority("Admin", "Editor")
		.antMatchers("/menu/**").hasAnyAuthority("Admin", "Editor")
		.anyRequest().authenticated()// se bat login , tao ra quyen try cap
		.and()
		.formLogin()
			.loginPage("/login") // custom login page voi url "/login"
			.usernameParameter("email")//cho  WebSercurityConfigurer biet email la 1 tham so chinh
			.permitAll()
		.and().logout().permitAll()
		.and()
		      .rememberMe()
		        .key("12345678_ASDFGHJKLMNBCZ")// fix key vi khi restart lai thi no se random  1 key khac , dieu nay se khien ko the tu dong dang nhap duoc
					.tokenValiditySeconds(7*24*60*60);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// dung de cho phep su dung hinh anh , bootstrap , js ... 
	@Override
	public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/images/**","/webfonts/**","/webjars/**","/js/**");
	}
	
	// dinh danh nguoi dung thong qua email va password (xem co ton tai tren he thong k)
	// DaoAuthenticationProvider ket hop userDetailService va AuthenticationProvider
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	// quan ly cac authenticationProvider
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		
	}
	
	
}
