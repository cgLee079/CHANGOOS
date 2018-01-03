package com.cglee079.portfolio.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.portfolio.model.FileVo;
import com.cglee079.portfolio.model.ItemVo;
import com.cglee079.portfolio.service.IComtService;
import com.cglee079.portfolio.service.ItemService;
import com.cglee079.portfolio.util.ImageManager;


@Controller
public class ItemController {
	final static String FILE_PATH 		= "/resources/file/item/";
	final static String SNAPSHT_PATH	= "/resources/image/item/snapshot/";
	final static String CONTENT_PATH	= "/resources/image/item/contents/";
	
	@Autowired
	private ItemService itemService;

	@Autowired 
	private IComtService icomtService;
	
	@RequestMapping(value = "item")
	public String itemList(Model model) {
		List<ItemVo> items = itemService.list();
		model.addAttribute("items", items);
		return "item/item_list";
	}
	
	@RequestMapping(value = "/item/view")
	public String itemView(Model model, int seq){
		List<ItemVo> items = itemService.list();
		ItemVo item 		= null;
		ItemVo beforeItem	= null;
		ItemVo afterItem	= null;
		
		int size = items.size();
		int index = 0;
		for(index = 0; index < size; index++){
			item = items.get(index);
			if(item.getSeq() == seq){
				break;
			}
		}
		
		if(index -1 >= 0 ) { beforeItem = items.get(index - 1); }
		if(index +1 < size) { afterItem = items.get(index + 1); }
		
		int hits = item.getHits();
		item.setHits(hits + 1);
		itemService.update(item);
				
		model.addAttribute("item", item);
		model.addAttribute("beforeItem", beforeItem);
		model.addAttribute("afterItem", afterItem);
		
		int comtCnt = icomtService.count(seq);
		model.addAttribute("comtCnt", comtCnt);
		
		List<FileVo> files = itemService.getFiles(seq);
		model.addAttribute("files", files);
		
		return "item/item_view";
	}
	
	@RequestMapping("/item/download.do")
	public void  download(HttpSession session, HttpServletResponse response, String filename) throws IOException{
		String rootPath = session.getServletContext().getRealPath("");
		FileVo itemFile = itemService.getFile(filename);
		
		File file = new File(rootPath + FILE_PATH, itemFile.getPathNm());
		byte fileByte[] = FileUtils.readFileToByteArray(file);
		
		if(file.exists()){
			response.setContentType("application/octet-stream");
		    response.setContentLength(fileByte.length);
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(itemFile.getRealNm(),"UTF-8")+"\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    response.getOutputStream().write(fileByte);
		     
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		}
	}
	
	@RequestMapping(value = "/admin/item/manage")
	public String itemManage(Model model) {
		List<ItemVo> items = itemService.list();
		model.addAttribute("items", items);
		return "item/item_manage";
	}
	
	@RequestMapping(value = "/admin/item/delete.do")
	public String itemDelete(HttpSession session, int seq) {
		String rootPath = session.getServletContext().getRealPath("");
		
		ItemVo item = itemService.get(seq);
		File existFile = null;
		
		existFile = new File (rootPath + item.getSnapsht());
		if(existFile.exists()){
			existFile.delete();
		}
		
		List<String> imgPaths = itemService.getContentImgPath(seq, CONTENT_PATH);
		int imgPathsLength = imgPaths.size();
		existFile = null;
		
		for (int i = 0; i < imgPathsLength; i++){
			existFile = new File (rootPath + imgPaths.get(i));
			if(existFile.exists()){
				existFile.delete();
			}
		}
		
		//File 삭제
		List<FileVo> files = itemService.getFiles(seq);
		FileVo file = null;
		int fileLength = files.size();
		for(int i = 0 ;  i < fileLength; i++){
			file = files.get(i);
			existFile = new File(rootPath + FILE_PATH, file.getPathNm());
			if(existFile.exists()){
				existFile.delete();
			}
		}
				
				
		itemService.delete(seq);
		return "redirect:" + "/admin/item/manage";
	}
	
	@RequestMapping(value = "/admin/item/upload", params = "!seq")
	public String itemUpload() {
		return "item/item_upload";
	}
	
	@RequestMapping(value = "/admin/item/upload", params = "seq")
	public String itemModify(Model model, int seq) {
		ItemVo item = itemService.get(seq);
		if(item.getContent() != null){
			item.setContent(item.getContent().replace("&", "&amp;"));
		}
		
		model.addAttribute("item", item);
		
		List<FileVo> files = itemService.getFiles(seq);
		model.addAttribute("files", files);
		
		return "item/item_upload";
	}
	
	@RequestMapping(value = "/admin/item/upload.do", method = RequestMethod.POST, params = "!seq")
	public String itemDoUpload(HttpServletRequest request, ItemVo item, MultipartFile snapshtFile, @RequestParam("file")List<MultipartFile> files) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String filename	= "snapshot_" + item.getName() + "_";
		String imgExt	= null;
		int seq = -1;
		
		if(snapshtFile.getSize() != 0){
			filename += snapshtFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(rootPath + SNAPSHT_PATH + filename);
			snapshtFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
			
			item.setSnapsht(SNAPSHT_PATH + filename);
		} else{
			item.setSnapsht(SNAPSHT_PATH + "default.jpg");
		}
		
		seq = itemService.insert(item);
		
		//파일 업로드
		File file = null;
		MultipartFile multipartFile = null;
		FileVo itemFile = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		
		int length = files.size();
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm 	= multipartFile.getOriginalFilename();
			pathNm	= "item" + seq + "_" + new SimpleDateFormat("YYMMdd_HHmmss").format(new Date()) + "_" + realNm;
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(rootPath + FILE_PATH, pathNm);
				multipartFile.transferTo(file);
				
				itemFile = new FileVo();
				itemFile.setPathNm(pathNm);
				itemFile.setRealNm(realNm);
				itemFile.setSize(size);
				itemFile.setBoardSeq(seq);
				itemService.saveFile(itemFile);
			}
		}
		
		return "redirect:" + "/admin/item/manage";
	}
	
	@RequestMapping(value = "/admin/item/upload.do", method = RequestMethod.POST, params = "seq")
	public String itemDoModify(HttpServletRequest request, ItemVo item, MultipartFile snapshtFile, @RequestParam("file")List<MultipartFile> files) throws IllegalStateException, IOException{
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String filename	= "snapshot_" + item.getName() + "_";
		String imgExt	= null;
		int seq = item.getSeq();
		
		if(snapshtFile.getSize() != 0){
			File existFile = new File (rootPath + item.getSnapsht());
			if(existFile.exists()){
				existFile.delete();
			}
			
			filename += snapshtFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(rootPath + SNAPSHT_PATH, filename);
			snapshtFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
			
			item.setSnapsht(SNAPSHT_PATH + filename);
		} 
		
		itemService.update(item);
		
		File file = null;
		MultipartFile multipartFile = null;
		FileVo itemFile = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		int length = files.size();
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm 	= multipartFile.getOriginalFilename();
			pathNm	= "item" + seq + "_" + new SimpleDateFormat("YYMMdd_HHmmss").format(new Date()) + "_" + realNm;
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(rootPath + FILE_PATH, pathNm);
				multipartFile.transferTo(file);
				
				itemFile = new FileVo();
				itemFile.setPathNm(pathNm);
				itemFile.setRealNm(realNm);
				itemFile.setSize(size);
				itemFile.setBoardSeq(seq);
				itemService.saveFile(itemFile);
			}
		}
		
		return "redirect:" + "/admin/item/manage";
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/item/deleteFile.do")
	public String deleteFile(HttpSession session, int seq){
		JSONObject data = new JSONObject();
		data.put("result", false);
		
		String rootPath = session.getServletContext().getRealPath("");
		
		FileVo itemFile = itemService.getFile(seq);
		File file = new File(rootPath + FILE_PATH, itemFile.getPathNm());
		if(file.exists()){
			if(file.delete()){
				if(itemService.deleteFile(seq)){
					data.put("result", true);
				};
			};
		}
		
		return data.toString();
	}
	
	@RequestMapping(value = "/admin/item/imgUpload.do")
	public String itemImgUpload(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("upload")MultipartFile multiFile, String CKEditorFuncNum) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String filename	= "content_" + new SimpleDateFormat("YYMMdd_HHmmss").format(new Date()) + "_";
		String imgExt 	= null;
		
		if(multiFile != null){
			filename += multiFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(rootPath + CONTENT_PATH + filename);
			multiFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
		}
		
		response.setHeader("x-frame-options", "SAMEORIGIN");
		model.addAttribute("path", request.getContextPath() + CONTENT_PATH + filename);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		
		return "item/item_imgupload";
	}

}
