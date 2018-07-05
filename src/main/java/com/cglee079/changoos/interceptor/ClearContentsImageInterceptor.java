package com.cglee079.changoos.interceptor;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ClearContentsImageInterceptor extends HandlerInterceptorAdapter {

	@Override
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession(true);
		String realPath 	= session.getServletContext().getRealPath("");
		List<String> contentImages = (List<String>)session.getAttribute("contentImages");
		if(contentImages != null) {
			int length = contentImages.size();
			for(int i = 0; i < length; i++) {
				String path = contentImages.get(i);
				File file = new File(realPath + path);
				System.out.println("## catch : " + path);
				if(file.exists()) {
					file.delete();
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

}
