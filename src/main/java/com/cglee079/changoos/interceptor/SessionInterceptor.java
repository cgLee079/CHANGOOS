package com.cglee079.changoos.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Set;
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
			
			session.setAttribute("visitStudies", new HashSet<Integer>());
			session.setAttribute("visitBlogs", new HashSet<Integer>());
			session.setAttribute("visitProjects", new HashSet<Integer>());
			session.setAttribute("likePhotos", new HashMap<Integer, Boolean>());
		} else {
			if(session.getAttribute("visitStudies") == null) { session.setAttribute("visitStudies", new HashSet<Integer>());}
			if(session.getAttribute("visitBlogs") == null) { session.setAttribute("visitBlogs", new HashSet<Integer>());}
			if(session.getAttribute("visitProjects") == null) { session.setAttribute("visitProjects", new HashSet<Integer>());}
			if(session.getAttribute("likePhotos") == null) { session.setAttribute("likePhotos", new HashMap<Integer, Boolean>());}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

}
