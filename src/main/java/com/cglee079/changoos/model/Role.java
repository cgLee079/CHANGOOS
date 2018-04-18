package com.cglee079.changoos.model;

import org.springframework.security.core.GrantedAuthority;

/*계정이 가지는 권한*/
/*implements GrantedAuthority  <<== Spring Security */
public class Role implements GrantedAuthority {
	private String name;

	public Role() {
	}
	
	public Role(String name) {
		this.name = name;
	}

	public void setAuthority(String name) {
		this.name = name;
	}

	@Override
	public String getAuthority() { //권한명 리턴 ex.ROLE_USER, ROLE_ADMIN (prefix=ROLE_)
		return this.name;
	}

}