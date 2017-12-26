package com.cglee079.portfolio.cotroller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.service.BoardService;
import com.cglee079.portfolio.service.ComtService;
import com.cglee079.portfolio.util.ImageManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@Autowired 
	private ComtService comtService;
	
	@RequestMapping("/board")
	public String board(Model model) throws SQLException, JsonProcessingException{
		List<BoardVo> notices =  boardService.list("공지사항");
		int count = boardService.count();
		model.addAttribute("count", count);
		model.addAttribute("notices", notices);
		return "board/board_list";
	}
		
	@ResponseBody
	@RequestMapping("/board/board_paging.do")
	public String doPaging(int page, int perPgLine) throws SQLException, JsonProcessingException{
		List<BoardVo> boards= boardService.paging(page, perPgLine, "기본");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(boards);
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
		
		int comtCnt = comtService.count("BOARD", seq);
		model.addAttribute("comtCnt", comtCnt);
		
		return "board/board_view";
	}
	
	@RequestMapping(value = "/admin/board/upload", params = "!seq")
	public String boardUpload(Model model)throws SQLException, JsonProcessingException{
		return "board/board_upload";
	}
	
	@RequestMapping(value = "/admin/board/upload", params = "seq")
	public String boardModify(Model model, int seq)throws SQLException, JsonProcessingException{
		BoardVo board = boardService.get(seq);
		model.addAttribute("board", board);
		return "board/board_upload";
	}
	
	 
	@RequestMapping(value = "/admin/board/upload.do", params = "!seq")
	public String boardDoUpload(Model model, BoardVo board) throws SQLException, JsonProcessingException{
		int seq = boardService.insert(board);
		return "redirect:" + "/board/view?seq=" + seq;
	}
	
	@RequestMapping(value = "/admin/board/upload.do", params = "seq")
	public String boardDoModify(Model model, BoardVo board) throws SQLException, JsonProcessingException{
		boardService.update(board);
		return "redirect:" + "/board/view?seq=" + board.getSeq();
	}
	
	@RequestMapping("/admin/board/delete.do")
	public String boardDoDelete(HttpSession session, Model model, int seq) throws SQLException, JsonProcessingException{
		String rootPath = session.getServletContext().getRealPath("");
		String path = "/resources/image/board/contents/";
		
		List<String> imgPaths = boardService.getContentImgPath(seq, path);
		int imgPathsLength = imgPaths.size();
		File existFile = null;
		
		for (int i = 0; i < imgPathsLength; i++){
			existFile = new File (rootPath + imgPaths.get(i));
			if(existFile.exists()){
				existFile.delete();
			}
		}
		
		boardService.delete(seq);
		comtService.deleteCasecade("BOARD", seq);
		return "redirect:" + "/board";
	}
	
	@RequestMapping(value = "/admin/board/imgUpload.do", method = RequestMethod.POST)
	public String boardDoImgUpload(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("upload")MultipartFile multiFile, String CKEditorFuncNum) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String imgPath	= "/resources/image/board/contents/";
		String filename	= "content_" + new SimpleDateFormat("YYMMdd_HHmmss").format(new Date()) + "_";
		String imgExt 	= null;
		
		if(multiFile != null){
			filename += multiFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(rootPath + imgPath + filename);
			multiFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
		}
		
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		model.addAttribute("path", request.getContextPath() + imgPath + filename);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		
		return "board/board_imgupload";
	}

}
