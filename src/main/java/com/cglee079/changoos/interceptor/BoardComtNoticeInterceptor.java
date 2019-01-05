package com.cglee079.changoos.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.service.BlogService;
import com.cglee079.changoos.service.ProjectService;
import com.cglee079.changoos.service.StudyService;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.TelegramHandler;

public class BoardComtNoticeInterceptor extends HandlerInterceptorAdapter {

	@Autowired private TelegramHandler telegramHandler;
	@Autowired private StudyService studyService;
	@Autowired private ProjectService projectService;
	@Autowired private BlogService blogService;
	
	@Value("#{constant['board.type.id.project']}") 	private String projectID;
	@Value("#{constant['board.type.id.study']}") 	private String studyID;
	@Value("#{constant['board.type.id.blog']}")		private String blogID;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		String boardType = request.getParameter("boardType");
		String name = request.getParameter("username");
		String contents = request.getParameter("contents").replaceAll("<br />", "\n");
		String boardSeq = request.getParameter("boardSeq");
		String parentComt = request.getParameter("parentComt");
		
		
		StringBuilder msg = new StringBuilder();
		
		//답글은 관리자밖에 못달기 때문에, 관리지가 작성하지아니한 댓글에 대해서 알림
		if (parentComt == null) {
			if (boardType.equals(projectID)) {
				ProjectVo project = projectService.get(Integer.parseInt(boardSeq));
				String projectTitle = project.getTitle();

				msg.append("#프로젝트에 댓글이 등록되었습니다.\n");
				msg.append("프로젝트 : " + projectTitle + "\n");
				
			} else if(boardType.equals(studyID)) {
				StudyVo study = studyService.get(Integer.parseInt(boardSeq));
				String studyTitle = study.getTitle();
				msg.append("#공부글에 댓글이 등록되었습니다.\n");
				msg.append("공부글	 : " + studyTitle + "\n");
			} else if(boardType.equals(blogID)) {
				BlogVo blog = blogService.get(Integer.parseInt(boardSeq));
				String blogTitle = blog.getTitle();

				msg.append("#블로그에 댓글이 등록되었습니다.\n");
				msg.append("블로그	 : " + blogTitle + "\n");
			}
			
			msg.append("이름 : " + name + "\n");
			msg.append("시간 : " + Formatter.toDateTime(new Date()) + "\n");
			msg.append("내용 :\n");
			msg.append(contents);

			telegramHandler.sendMessage(msg.toString());
		}
		
		
		
		
		super.afterCompletion(request, response, handler, ex);
	}

}
