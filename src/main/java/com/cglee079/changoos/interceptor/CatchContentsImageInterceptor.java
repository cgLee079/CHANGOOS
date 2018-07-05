package com.cglee079.changoos.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CatchContentsImageInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession(true);
		
		List<String> contentImages = (List<String>)session.getAttribute("contentImages");
		if(contentImages == null) {
			contentImages = new ArrayList<String>();
			session.setAttribute("contentImages", contentImages);
		}
		
		System.out.println(request.getAttribute("path"));
		if(request.getAttribute("path") != null) {
			contentImages.add((String)request.getAttribute("path"));
		}
		
		if(modelAndView != null) {
			String path = (String)modelAndView.getModel().get("path");
			if(path != null) {
				contentImages.add(path);
			}
		}
	}

}
