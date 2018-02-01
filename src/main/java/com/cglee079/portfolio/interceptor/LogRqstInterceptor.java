package com.cglee079.portfolio.interceptor;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cglee079.portfolio.model.LogRqstVo;
import com.cglee079.portfolio.service.LogRqstService;
import com.cglee079.portfolio.util.Formatter;

public class LogRqstInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private LogRqstService logRqstService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LogRqstVo logRqst = new LogRqstVo();
		logRqst.setIp(request.getRemoteAddr());
		String page = request.getRequestURI().toString();
		if (request.getQueryString() != null){
			page = page +  "?" + request.getQueryString(); 
		}
		logRqst.setPage(page);
		logRqst.setAgnt(request.getHeader("User-Agent"));
		logRqst.setDate(Formatter.toDateTime(new Date()));
		logRqstService.insert(logRqst);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
	
}
