package com.cglee079.changoos.config.listener;

import java.io.File;
import java.util.HashSet;
import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cglee079.changoos.util.FileHandler;

public class MySessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		session.setAttribute("visitStudies", new HashSet<Integer>());
		session.setAttribute("visitBlogs", new HashSet<Integer>());
		session.setAttribute("visitProjects", new HashSet<Integer>());
		session.setAttribute("likePhotos", new HashSet<Integer>());
		session.setAttribute("tempDirId", RandomStringUtils.randomAlphanumeric(32) + "/");
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		
		WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		Properties props = appContext.getBean("location", Properties.class);
		
		String realPath = session.getServletContext().getRealPath("/");
		String tempDir = props.getProperty("temp.dir.url");
		String tempDirId = (String)session.getAttribute("tempDirId");
		
		
		FileHandler fileHandler = appContext.getBean("fileHandler", FileHandler.class);
		File dir = new File(realPath + tempDir + tempDirId);
		fileHandler.emptyDir(dir.getPath());
		fileHandler.delete(dir.getPath());
	}

}

