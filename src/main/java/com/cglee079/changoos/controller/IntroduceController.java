package com.cglee079.changoos.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cglee079.changoos.service.CommonStrService;
import com.cglee079.changoos.util.TimeStamper;

@Controller
public class IntroduceController {
	@Autowired
	private CommonStrService commonStringService;
	

	@RequestMapping(value = "/introduce")
	public String introduce(Model model) {
		String intro001 = commonStringService.get("INTRO", "001"); // 자기소개
		String intro002 = commonStringService.get("INTRO", "002"); // 이력내용
		
		model.addAttribute("intro001", intro001);
		model.addAttribute("intro002", intro002);
		
		return "introduce/introduce_view";
	}
	
	
	@RequestMapping(value = "/introduce/resume")
	public void downloadResume(HttpSession session, HttpServletResponse response) throws IOException {
		String rootPath = session.getServletContext().getRealPath("");
		
		File file = new File(rootPath + "/resources/file/resume.pdf");
		byte fileByte[] = FileUtils.readFileToByteArray(file);
		
		String filename = TimeStamper.stamp("yyyyMMdd") + "_LEECHANGOO.pdf";
		
		if(file.exists()){
			response.setContentType("application/octet-stream");
		    response.setContentLength(fileByte.length);
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" +  filename +"\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    response.getOutputStream().write(fileByte);
		     
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		}
	}
}
