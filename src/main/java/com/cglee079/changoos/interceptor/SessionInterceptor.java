package com.cglee079.changoos.interceptor;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cglee079.changoos.model.SessionLogVo;
import com.cglee079.changoos.service.SessionLogService;
import com.cglee079.changoos.util.Formatter;

public class SessionInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private SessionLogService sessionLogService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession(true);
		if(session.isNew()) {
			SessionLogVo sessionLog = new SessionLogVo();
			sessionLog.setIp(request.getRemoteAddr());
			sessionLog.setAgnt(request.getHeader("User-Agent"));
			sessionLog.setCreateDate(Formatter.toDateTime(new Date()));
			sessionLogService.insert(sessionLog);
			
			session.setAttribute("visitBoards", new ArrayList<Integer>());
			session.setAttribute("visitProjects", new ArrayList<Integer>());
		} 
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

}
