package com.cglee079.portfolio.interceptor;

import java.util.Date;

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
		logRqst.setIp(request.getLocalAddr());
		logRqst.setPage(request.getRequestURL().toString());
		logRqst.setAgnt(request.getHeader("User-Agent"));
		logRqst.setDate(Formatter.toDate(new Date()));
		logRqstService.insert(logRqst);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("######### postHandle()");
	}
	
}
