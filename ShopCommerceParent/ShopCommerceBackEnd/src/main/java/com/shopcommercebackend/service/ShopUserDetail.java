package com.shopcommercebackend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shopcommercecommon.model.Role;
import com.shopcommercecommon.model.User;

public class ShopUserDetail implements UserDetails{
	
	private User user;	
		
	public ShopUserDetail(User user) {
		super();
		this.user = user;
	}

	//trả về danh sách các quyền của người dùng
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 Set<Role> roles = user.getRoles();
		 List<GrantedAuthority> authorities = new ArrayList();
		 
		 // tim grantedAuthorities 
		 for(Role role : roles) {
			 authorities.add(new SimpleGrantedAuthority(getUsername()));
		 }
		return authorities;
	}

	//trả về password đã dùng trong qúa trình xác thực
	@Override
	public String getPassword() {
	
		return user.getPassword();
	}
	
	// trả về username đã dùng trong qúa trình xác thực
	@Override
	public String getUsername() {
		return user.getPassword();
	}
	
	//trả về true nếu tài khoản của người dùng chưa hết hạn
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}
	//trả về true nếu người dùng chưa bị khóa
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}
	//trả về true nếu chứng thực (mật khẩu) của người dùng chưa hết hạn
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	//trả về true nếu người dùng đã được kích hoạt
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
