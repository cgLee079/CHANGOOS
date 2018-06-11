 package com.cglee079.changoos.controller;

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
import com.cglee079.changoos.model.StudyComtVo;
import com.cglee079.changoos.service.StudyComtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class StudyComtController {
	@Autowired
	private StudyComtService scomtService;
	
	/** 게시판 댓글 페이징 **/
	@ResponseBody
	@RequestMapping("study/comment/paging.do")
	public String doPaging(int studySeq, int page, int perPgLine) throws SQLException, JsonProcessingException{
		List<StudyComtVo> bcomts= scomtService.paging(studySeq, page, perPgLine);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(bcomts);
	}
	
	/** 게시판 댓글 등록 **/
	@ResponseBody
	@RequestMapping("study/comment/submit.do")
	public String doSubmit(StudyComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = scomtService.insert(comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 댓글 삭제 **/
	@ResponseBody
	@RequestMapping("study/comment/delete.do")
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
		
		boolean result = scomtService.delete(seq, password, isAdmin);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 비밀번호 체크 **/
	@ResponseBody
	@RequestMapping("study/comment/checkPwd.do")
	public String doCheckPwd(Authentication auth, int seq, String password) throws SQLException, JsonProcessingException{
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
		
		boolean result = scomtService.checkPwd(seq, password, isAdmin);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 댓글 수정 **/
	@ResponseBody
	@RequestMapping("study/comment/update.do")
	public String doUpdate(Authentication auth, int seq, String contents) throws SQLException, JsonProcessingException{
		boolean result = scomtService.update(seq, contents);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
}
