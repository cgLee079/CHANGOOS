package com.cglee079.changoos.util;

import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthManager {
	public static boolean isAdmin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = false;

		if (auth != null) {
			Iterator<? extends GrantedAuthority> iter = auth.getAuthorities().iterator();
			while (iter.hasNext()) {
				GrantedAuthority ga = iter.next();
				if (ga.getAuthority().equals("ROLE_ADMIN")) {
					isAdmin = true;
					break;
				}
			}
		}
		return isAdmin;

	}
}
