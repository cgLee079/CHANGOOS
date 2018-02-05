package com.cglee079.portfolio.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.model.ItemVo;
import com.cglee079.portfolio.service.ItemService;
import com.cglee079.portfolio.util.Formatter;
import com.cglee079.portfolio.util.TelegramHandler;

public class IComtNoticeInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TelegramHandler telegramHandler;

	@Autowired
	private ItemService itemService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String name = request.getParameter("name");
		String contents = request.getParameter("contents").replaceAll("<br />", "\n");
		String boardSeq = request.getParameter("boardSeq");
		String parentSeq = request.getParameter("parentSeq");
		
		if (parentSeq == null) {
			ItemVo board = itemService.get(Integer.parseInt(boardSeq));
			String boardTitle = board.getName();

			String msg = "#프로젝트에 댓글이 등록되었습니다.\n";
			msg += "프로젝트 : " + boardTitle + "\n";
			msg += "ID : " + name + "\n";
			msg += "시간 : " + Formatter.toDateTime(new Date()) + "\n";
			msg += "내용 :\n";
			msg += contents;

			telegramHandler.sendMessage(msg);
		}
	}

}
