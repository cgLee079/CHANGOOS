package com.cglee079.changoos.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardVo;
import com.cglee079.changoos.model.PhotoVo;
import com.cglee079.changoos.service.BoardFileService;
import com.cglee079.changoos.service.BoardService;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.TimeStamper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@Controller
public class BoardController {
	public static final String CONTENTS_PATH	= "/uploaded/boards/contents/";
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardFileService boardFileService;
	
	/** 게시글 리스트로 이동 **/
	@RequestMapping("/board")
	public String board(Model model, @RequestParam Map<String, Object> params) throws SQLException, JsonProcessingException{
		List<String> sects = boardService.getSects();
		int count = boardService.count(params);
		
		model.addAttribute("count", count);
		model.addAttribute("sects", sects);
		return "board/board_list";
	}
		
	/** 게시글 페이징 **/
	@ResponseBody
	@RequestMapping("/board/board_paging.do")
	public String doPaging(@RequestParam Map<String, Object> params) throws SQLException, JsonProcessingException{
		List<BoardVo> boards = boardService.paging(params);
		int count = boardService.count(params);
		
		String data = new Gson().toJson(boards);
		JSONArray dataJson = new JSONArray(data);
			
		JSONObject result = new JSONObject();
		result.put("count", count);
		result.put("data", dataJson);
		
		return result.toString();
	}
	
	/** 게시글로 이동 **/
	@RequestMapping("/board/view")
	public String boardView(Model model, int seq, String sect, Integer page) throws SQLException, JsonProcessingException{
		BoardVo board 		= boardService.doView(seq);
		BoardVo beforeBoard = boardService.getBefore(seq, sect);
		BoardVo afterBoard 	= boardService.getAfter(seq, sect);
		model.addAttribute("sect", sect);
		model.addAttribute("page", page);
		model.addAttribute("beforeBoard", beforeBoard);
		model.addAttribute("board", board);
		model.addAttribute("afterBoard", afterBoard);
		
		List<BoardFileVo> files = boardFileService.list(seq);
		model.addAttribute("files", files);
		
		return "board/board_view";
	}
	
	/** 파일 다운로드 **/
	@RequestMapping("/board/download.do")
	public void  download(HttpSession session, HttpServletResponse response, String filename) throws IOException{
		String rootPath = session.getServletContext().getRealPath("");
		BoardFileVo boardFile = boardFileService.get(filename);
		
		File file = new File(rootPath + BoardFileService.FILE_PATH, boardFile.getPathNm());
		byte fileByte[] = FileUtils.readFileToByteArray(file);
		
		if(file.exists()){
			response.setContentType("application/octet-stream");
		    response.setContentLength(fileByte.length);
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(boardFile.getRealNm(),"UTF-8")+"\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    response.getOutputStream().write(fileByte);
		     
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		}
	}
	
	/** 게시글 관리 페이지로 이동 **/
	@RequestMapping(value = "/admin/board/manage")
	public String photoManage(Model model) {
		return "board/board_manage";
	}
	
	/** 게시글 관리 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/admin/board/manageList.do")
	public String DoPhotoManageList(@RequestParam Map<String, Object> map) {
		List<BoardVo> photos = boardService.list(map);
		Gson gson = new Gson();
		return gson.toJson(photos).toString();
	}
	
	/** 게시글 업로드 페이지로 이동 **/
	@RequestMapping(value = "/admin/board/upload", params = "!seq")
	public String boardUpload(Model model)throws SQLException, JsonProcessingException{
		return "board/board_upload";
	}
	
	/** 게시글 수정 페이지로 이동 **/
	@RequestMapping(value = "/admin/board/upload", params = "seq")
	public String boardModify(Model model, int seq)throws SQLException, JsonProcessingException{
		BoardVo board = boardService.get(seq);
		board.setContents(board.getContents().replace("&", "&amp;"));
		model.addAttribute("board", board);
		
		List<BoardFileVo> files = boardFileService.list(seq);
		model.addAttribute("files", files);
		
		return "board/board_upload";
	}
	
	 
	/** 게시글 업로드  **/
	@RequestMapping(value = "/admin/board/upload.do", params = "!seq")
	public String boardDoUpload(HttpSession session, Model model, BoardVo board, @RequestParam("file")List<MultipartFile> files) throws SQLException, IllegalStateException, IOException{
		int seq = boardService.insert(board);
		String rootPath = session.getServletContext().getRealPath("");
		
		//파일저장
		boardFileService.saveFiles(rootPath, board.getSeq(), files);
		
		return "redirect:" + "/board/view?seq=" + seq;
	}
	
	/** 게시글 수정 **/
	@RequestMapping(value = "/admin/board/upload.do", params = "seq")
	public String boardDoModify(HttpSession session, Model model, BoardVo board, @RequestParam("file")List<MultipartFile> files) throws SQLException, IllegalStateException, IOException{
		boardService.update(board);
		String rootPath = session.getServletContext().getRealPath("");
		
		//파일저장
		boardFileService.saveFiles(rootPath, board.getSeq(), files);
		
		return "redirect:" + "/board/view?seq=" + board.getSeq();
	}
	
	/** 게시글 삭제 **/
	@RequestMapping("/admin/board/delete.do")
	public String boardDoDelete(HttpSession session, Model model, int seq) throws SQLException, JsonProcessingException{
		String rootPath = session.getServletContext().getRealPath("");
		File existFile = null;
		
		//Content Img 삭제
		List<String> imgPaths = boardService.getContentImgPath(seq, CONTENTS_PATH);
		int imgPathsLength = imgPaths.size();
		for (int i = 0; i < imgPathsLength; i++){
			existFile = new File (rootPath + imgPaths.get(i));
			if(existFile.exists()){
				existFile.delete();
			}
		}
		
		boardFileService.deleteFiles(rootPath, seq);
		
		boardService.delete(seq);
		return "redirect:" + "/board";
	}
	
	/** 게시글 파일 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/admin/board/deleteFile.do")
	public String deleteFile(HttpSession session, int seq){
		boolean result = false;
		String rootPath = session.getServletContext().getRealPath("");
		
		result = boardFileService.deleteFile(rootPath, seq);
		JSONObject data = new JSONObject();
		data.put("result", result);
		
		return data.toString();
	}
	
	/** 게시글 CKEditor 사진 업로드  **/
	@RequestMapping(value = "/admin/board/imgUpload.do")
	public String boardDoImgUpload(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("upload")MultipartFile multiFile, String CKEditorFuncNum) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String filename	= "content_" + TimeStamper.stamp() + "_";
		String imgExt 	= null;
		
		if(multiFile != null){
			filename += multiFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(rootPath + CONTENTS_PATH, filename);
			multiFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
		}
		
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		model.addAttribute("path", request.getContextPath() + CONTENTS_PATH + filename);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		
		return "board/board_imgupload";
	}
	
}
