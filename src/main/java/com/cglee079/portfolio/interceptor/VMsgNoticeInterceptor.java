package com.cglee079.portfolio.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.util.Formatter;
import com.cglee079.portfolio.util.TelegramHandler;

public class VMsgNoticeInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TelegramHandler telegramHandler;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		String contents = request.getParameter("contents");
		String msg = "#방명록이 등록되었습니다.\n";
		msg += "시간 : " + Formatter.toDateTime(new Date()) + "\n";
		msg += "내용 :\n";
		msg += contents;
		telegramHandler.sendMessage(msg);

		super.afterCompletion(request, response, handler, ex);
	}
}
