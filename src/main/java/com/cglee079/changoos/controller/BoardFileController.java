package com.cglee079.changoos.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.service.BoardFileService;
import com.cglee079.changoos.util.MyFilenameUtils;

@Controller
public class BoardFileController {
	@Autowired
	private BoardFileService fileService;
	
	@Value("#{servletContext.getRealPath('/')}")
	private String realPath;
	
	@RequestMapping(value = "/board/file", method=RequestMethod.GET)
	public void fileDoDownload(HttpServletRequest request, HttpServletResponse response,
			String dir, String pathname, String filename) throws IOException {
		
		File file = new File(realPath + dir, pathname);
		byte fileByte[] = FileUtils.readFileToByteArray(file);

		if (file.exists()) {
			response.setContentType("application/octet-stream");
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + MyFilenameUtils.encodeFilename(request, filename) + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/board/post/file" , method=RequestMethod.POST)
	public String fileDoUpload(HttpSession session, MultipartFile file) throws IllegalStateException, IOException {
		String pathname= fileService.saveFile(file);
		
		JSONObject result = new JSONObject();
		result.put("pathname", pathname);
		
		return result.toString();
	}
}
