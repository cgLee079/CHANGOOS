package com.cglee079.changoos.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.service.StudyService;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.TelegramHandler;

public class StudyComtNoticeInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TelegramHandler telegramHandler;

	@Autowired
	private StudyService studyService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String name = request.getParameter("name");
		String contents = request.getParameter("contents").replaceAll("<br />", "\n");
		String studySeq = request.getParameter("studySeq");
		String parentSeq = request.getParameter("parentSeq");
		if (parentSeq == null) {
			StudyVo study = studyService.get(Integer.parseInt(studySeq));
			String studyTitle = study.getTitle();

			String msg = "#게시판에 댓글이 등록되었습니다.\n";
			msg += "게시글	 : " + studyTitle + "\n";
			msg += "이름 : " + name + "\n";
			msg += "시간 : " + Formatter.toDateTime(new Date()) + "\n";
			msg += "내용 :\n";
			msg += contents;

			telegramHandler.sendMessage(msg);
		}
		
		super.afterCompletion(request, response, handler, ex);
	}

}
