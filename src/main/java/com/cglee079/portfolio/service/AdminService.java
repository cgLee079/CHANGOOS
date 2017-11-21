package com.cglee079.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.AdminDao;
import com.cglee079.portfolio.model.AdminVo;
import com.cglee079.portfolio.model.Role;

@Service
public class AdminService implements UserDetailsService {

	@Autowired
	AdminDao adminDao;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AdminVo admin = adminDao.get(username);

		if (admin != null) {
			List<Role> adminAuths = adminDao.getAuths(username);
			System.out.println(adminAuths.get(0).getAuthority());
			admin.setAuthorities(adminAuths);
		} else{
			new UsernameNotFoundException(username);
		}
		
		return admin;
	}

}
