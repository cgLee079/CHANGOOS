package com.cglee079.changoos.controller;

import java.awt.GradientPaint;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.changoos.model.AdminVo;
import com.cglee079.changoos.model.PhotoComtVo;
import com.cglee079.changoos.service.PhotoComtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PhotoComtController {
	
	@Autowired
	private PhotoComtService photoComtService;
	
	@ResponseBody
	@RequestMapping("/photo/comment/list.do")
	public String doPaging(int photoSeq) throws SQLException, JsonProcessingException{
		List<PhotoComtVo> comts= photoComtService.list(photoSeq);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(comts);
	}
	
	/** 사진 댓글 삽입 **/
	@ResponseBody
	@RequestMapping("/photo/comment/submit.do")
	public String doSubmit(PhotoComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = photoComtService.insert(comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 사진 댓글 삭제 **/
	@ResponseBody
	@RequestMapping("/photo/comment/delete.do")
	public String doDelete(Authentication auth, int seq, String password) throws SQLException, JsonProcessingException{
		boolean isAdmin = false;
		
		if(auth != null) {
			AdminVo vo = (AdminVo) auth.getPrincipal();
			Iterator<? extends GrantedAuthority> iter = vo.getAuthorities().iterator();
			while(iter.hasNext()) {
				GrantedAuthority ga = iter.next();
				if(ga.getAuthority().equals("ROLE_ADMIN")) {
					isAdmin = true;
					break;
				}
			}
		}
		
		boolean result = photoComtService.delete(seq, password,isAdmin);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 사진 댓글 비밀번호 확인 **/
	@ResponseBody
	@RequestMapping("/photo/comment/checkPwd.do")
	public String doCheckPwd(int seq, String password, boolean isAdmin) throws SQLException, JsonProcessingException{
		boolean result = photoComtService.checkPwd(seq, password, isAdmin);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 사진 댓글 수정 **/
	@ResponseBody
	@RequestMapping("/photo/comment/update.do")
	public String doUpdate(int seq, String contents) throws SQLException, JsonProcessingException{
		boolean result = photoComtService.update(seq, contents);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
}
