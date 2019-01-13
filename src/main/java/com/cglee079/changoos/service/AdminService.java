package com.cglee079.changoos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.AdminDao;
import com.cglee079.changoos.model.AdminVo;
import com.cglee079.changoos.model.Role;

@Service
public class AdminService implements UserDetailsService {

	@Autowired
	AdminDao adminDao;

	public UserDetails loadUserByUsername(String username){
		AdminVo admin = adminDao.get(username);

		if (admin != null) {
			List<Role> adminAuths = adminDao.getAuths(username);
			admin.setAuthorities(adminAuths);
		} else{
			throw new UsernameNotFoundException(username);
		}
		
		return admin;
	}

}
