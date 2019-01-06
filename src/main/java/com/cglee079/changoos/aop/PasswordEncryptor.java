package com.cglee079.changoos.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.model.PhotoComtVo;

@Aspect
public class PasswordEncryptor {

	@Autowired
	private ShaPasswordEncoder passwordEncoder;
	
	private Object salt = null;
	
	@Before("execution(* com.cglee079.changoos.controller.BoardComtController.*(.., com.cglee079.changoos.model.BoardComtVo, ..))")
	public void encryptBoardComtPassword(JoinPoint joinPoint) {
		Object[] args  = joinPoint.getArgs();
		for(int i = 0; i < args.length; i++) {
			if( args[i] instanceof  BoardComtVo) {
				BoardComtVo comt = (BoardComtVo)args[i];
				String password = comt.getPassword();
				comt.setPassword(passwordEncoder.encodePassword(password, salt));
			}
		}
	}
	
	@Before("execution(* com.cglee079.changoos.controller.PhotoComtController.*(.., com.cglee079.changoos.model.PhotoComtVo, ..))")
	public void encryptPhotoComtPassword(JoinPoint joinPoint) {
		Object[] args  = joinPoint.getArgs();
		for(int i = 0; i < args.length; i++) {
			if( args[i] instanceof  PhotoComtVo) {
				PhotoComtVo comt = (PhotoComtVo)args[i];
				String password = comt.getPassword();
				comt.setPassword(passwordEncoder.encodePassword(password, salt));
			}
		}
	}
	
	
}
