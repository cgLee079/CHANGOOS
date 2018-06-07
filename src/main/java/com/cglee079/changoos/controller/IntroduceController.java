package com.cglee079.changoos.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.changoos.model.ProjectFileVo;
import com.cglee079.changoos.model.VisitMsgVo;
import com.cglee079.changoos.service.ProjectFileService;
import com.cglee079.changoos.service.VisitMsgService;
import com.cglee079.changoos.util.Formatter;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IntroduceController {

	@Autowired
	private VisitMsgService visitMsgService;

	@RequestMapping(value = "/introduce")
	public String introduce() {
		return "introduce/introduce_view";
	}


	@RequestMapping(value = "/introduce/downloadResume.do")
	public void doDownloadResume(HttpSession session, HttpServletResponse response) throws IOException {
		String rootPath = session.getServletContext().getRealPath("");
		
		File file = new File(rootPath + "/resources/file/resume.pdf");
		byte fileByte[] = FileUtils.readFileToByteArray(file);
		
		if(file.exists()){
			response.setContentType("application/octet-stream");
		    response.setContentLength(fileByte.length);
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + "LeeChangoo_resume.pdf" +"\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    response.getOutputStream().write(fileByte);
		     
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		}
	}
	
	/**방명록 등록 **/
	@ResponseBody
	@RequestMapping(value = "/introduce/remain_message.do")
	public String doRemainMessage(String contents) {
		VisitMsgVo visitMsg = new VisitMsgVo();
		visitMsg.setContents(contents);
		visitMsg.setDate(Formatter.toDateTime(new Date()));

		boolean result = visitMsgService.insert(visitMsg);

		JSONObject re = new JSONObject();
		re.put("result", result);
		return re.toString();
	}

}
