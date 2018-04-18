package com.cglee079.changoos.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cglee079.changoos.model.BoardVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.service.ProjectService;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.TelegramHandler;

public class PComtNoticeInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TelegramHandler telegramHandler;

	@Autowired
	private ProjectService projectService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String name = request.getParameter("name");
		String contents = request.getParameter("contents").replaceAll("<br />", "\n");
		String boardSeq = request.getParameter("boardSeq");
		String parentSeq = request.getParameter("parentSeq");
		
		if (parentSeq == null) {
			ProjectVo project = projectService.get(Integer.parseInt(boardSeq));
			String boardTitle = project.getTitle();

			String msg = "#프로젝트에 댓글이 등록되었습니다.\n";
			msg += "프로젝트 : " + boardTitle + "\n";
			msg += "이름 : " + name + "\n";
			msg += "시간 : " + Formatter.toDateTime(new Date()) + "\n";
			msg += "내용 :\n";
			msg += contents;

			telegramHandler.sendMessage(msg);
		}
		
		super.afterCompletion(request, response, handler, ex);
	}

}
