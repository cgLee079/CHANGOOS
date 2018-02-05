package com.cglee079.portfolio.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.model.FileVo;
import com.cglee079.portfolio.service.BComtService;
import com.cglee079.portfolio.service.BoardService;
import com.cglee079.portfolio.util.ImageManager;
import com.cglee079.portfolio.util.TimeStamper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@Controller
public class BoardController {
	final static String CONTENTS_PATH	= "/uploaded/boards/contents/";
	final static String FILE_PATH 		= "/uploaded/boards/files/";
	
	@Autowired
	private BoardService boardService;
	
	@Autowired 
	private BComtService bcomtService;
	
	@RequestMapping("/board")
	public String board(Model model) throws SQLException, JsonProcessingException{
		List<BoardVo> notices =  boardService.list("NOTICE");
		int count = boardService.count("BASIC", null, null);
		model.addAttribute("count", count);
		model.addAttribute("notices", notices);
		return "board/board_list";
	}
		
	@ResponseBody
	@RequestMapping("/board/board_paging.do")
	public String doPaging(int page, int perPgLine, String searchType, String searchValue) throws SQLException, JsonProcessingException{
		List<BoardVo> boards= boardService.paging(page, perPgLine, "BASIC", searchType, searchValue);
		int count = boardService.count("BASIC", searchType, searchValue);
		
		JSONObject result = new JSONObject();
		Gson gson = new Gson();
		String data = gson.toJson(boards);

		JSONArray dataJson = new JSONArray(data);
		JSONObject datum;
		
		String contents;
		Document doc;
		for(int i = 0; i < dataJson.length(); i++){
			datum = dataJson.getJSONObject(i);
			contents = datum.getString("contents");
			doc = Jsoup.parse(contents);
			contents = doc.getAllElements().text();
			datum.put("comtCnt", bcomtService.count(datum.getInt("seq")));
			datum.put("contents", contents.substring(0, 300));
		}
		
		result.put("count", count);
		result.put("data", dataJson);
		
		return result.toString();
	}
	
	@RequestMapping("/board/view")
	public String boardView(Model model, int seq, Integer page) throws SQLException, JsonProcessingException{
		BoardVo board = boardService.doView(seq);
		BoardVo beforeBoard = boardService.getBefore(seq, board.getType());
		BoardVo afterBoard = boardService.getAfter(seq, board.getType());
		model.addAttribute("page", page);
		model.addAttribute("beforeBoard", beforeBoard);
		model.addAttribute("board", board);
		model.addAttribute("afterBoard", afterBoard);
		
		int comtCnt = bcomtService.count(seq);
		model.addAttribute("comtCnt", comtCnt);
		
		List<FileVo> files = boardService.getFiles(seq);
		model.addAttribute("files", files);
		
		return "board/board_view";
	}
	
	@RequestMapping("/board/download.do")
	public void  download(HttpSession session, HttpServletResponse response, String filename) throws IOException{
		String rootPath = session.getServletContext().getRealPath("");
		FileVo boardFile = boardService.getFile(filename);
		
		File file = new File(rootPath + FILE_PATH, boardFile.getPathNm());
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
	
	@RequestMapping(value = "/admin/board/upload", params = "!seq")
	public String boardUpload(Model model)throws SQLException, JsonProcessingException{
		return "board/board_upload";
	}
	
	@RequestMapping(value = "/admin/board/upload", params = "seq")
	public String boardModify(Model model, int seq)throws SQLException, JsonProcessingException{
		BoardVo board = boardService.get(seq);
		board.setContents(board.getContents().replace("&", "&amp;"));
		model.addAttribute("board", board);
		
		List<FileVo> files = boardService.getFiles(seq);
		model.addAttribute("files", files);
		
		return "board/board_upload";
	}
	
	 
	@RequestMapping(value = "/admin/board/upload.do", params = "!seq")
	public String boardDoUpload(HttpSession session, Model model, BoardVo board, @RequestParam("file")List<MultipartFile> files) throws SQLException, IllegalStateException, IOException{
		int seq = boardService.insert(board);
		
		String rootPath = session.getServletContext().getRealPath("");
		String path = "/resources/file/board/";
		
		File file = null;
		MultipartFile multipartFile = null;
		FileVo boardFile = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		
		int length = files.size();
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm 	= multipartFile.getOriginalFilename();
			pathNm	= "board" + seq + "_" + TimeStamper.stamp() + "_" + realNm;
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(rootPath + path, pathNm);
				multipartFile.transferTo(file);
				
				boardFile = new FileVo();
				boardFile.setPathNm(pathNm);
				boardFile.setRealNm(realNm);
				boardFile.setSize(size);
				boardFile.setBoardSeq(seq);
				boardService.saveFile(boardFile);
			}
		}
		
		return "redirect:" + "/board/view?seq=" + seq;
	}
	
	@RequestMapping(value = "/admin/board/upload.do", params = "seq")
	public String boardDoModify(HttpSession session, Model model, BoardVo board, @RequestParam("file")List<MultipartFile> files) throws SQLException, IllegalStateException, IOException{
		boardService.update(board);
		
		String rootPath = session.getServletContext().getRealPath("");

		File file = null;
		MultipartFile multipartFile = null;
		FileVo boardFile = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		int length = files.size();
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm 	= multipartFile.getOriginalFilename();
			pathNm	= "board" + board.getSeq() + "_" + TimeStamper.stamp() + "_" + realNm;
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(rootPath + FILE_PATH, pathNm);
				multipartFile.transferTo(file);
				
				boardFile = new FileVo();
				boardFile.setPathNm(pathNm);
				boardFile.setRealNm(realNm);
				boardFile.setSize(size);
				boardFile.setBoardSeq(board.getSeq());
				boardService.saveFile(boardFile);
			}
		}
		
		return "redirect:" + "/board/view?seq=" + board.getSeq();
	}
	
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
		
		//File 삭제
		List<FileVo> files = boardService.getFiles(seq);
		FileVo file = null;
		int fileLength = files.size();
		for(int i = 0 ;  i < fileLength; i++){
			file = files.get(i);
			existFile = new File(rootPath + FILE_PATH, file.getPathNm());
			if(existFile.exists()){
				existFile.delete();
			}
		}
		
		boardService.delete(seq);
		return "redirect:" + "/board";
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/board/deleteFile.do")
	public String deleteFile(HttpSession session, int seq){
		JSONObject data = new JSONObject();
		data.put("result", false);
		
		String rootPath = session.getServletContext().getRealPath("");
		String filePath = "/resources/file/board/";
		FileVo boardFile = boardService.getFile(seq);
		File file = new File(rootPath + filePath, boardFile.getPathNm());
		if(file.exists()){
			if(file.delete()){
				if(boardService.deleteFile(seq)){
					data.put("result", true);
				};
			};
		}
		
		return data.toString();
	}
	
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
		
		response.setHeader("x-frame-options", "SAMEORIGIN");
		model.addAttribute("path", request.getContextPath() + CONTENTS_PATH + filename);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		
		return "board/board_imgupload";
	}
	
}
