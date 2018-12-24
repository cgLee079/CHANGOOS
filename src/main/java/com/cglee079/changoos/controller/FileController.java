package com.cglee079.changoos.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cglee079.changoos.util.MyFilenameUtils;

@Controller
public class FileController {
	@Value("#{servletContext.getRealPath('/')}")
	private String realPath;
	
	@RequestMapping("/file/download.do")
	public void projectDoFiledownload(HttpServletRequest request, HttpServletResponse response,
			String path, String pathname, String filename) throws IOException {
		
		File file = new File(realPath + path, pathname);
		byte fileByte[] = FileUtils.readFileToByteArray(file);

		if (file.exists()) {
			response.setContentType("application/octet-stream");
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + MyFilenameUtils.encodeFilename(request, filename) + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
}
