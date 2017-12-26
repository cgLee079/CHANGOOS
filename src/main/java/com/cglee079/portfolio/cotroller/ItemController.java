package com.cglee079.portfolio.cotroller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.portfolio.model.ItemVo;
import com.cglee079.portfolio.service.ItemService;
import com.cglee079.portfolio.service.ComtService;
import com.cglee079.portfolio.util.ImageManager;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@Autowired 
	private ComtService comtService;
	
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
		
		int comtCnt = comtService.count("ITEM", seq);
		model.addAttribute("comtCnt", comtCnt);
		
		return "item/item_view";
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
		String contentFolderPath = "/resources/image/item/contents/";
		ItemVo item = itemService.get(seq);
		File existFile = null;
		
		existFile = new File (rootPath + item.getSnapsht());
		if(existFile.exists()){
			existFile.delete();
		}
		
		List<String> imgPaths = itemService.getContentImgPath(seq, contentFolderPath);
		int imgPathsLength = imgPaths.size();
		existFile = null;
		
		for (int i = 0; i < imgPathsLength; i++){
			existFile = new File (rootPath + imgPaths.get(i));
			if(existFile.exists()){
				existFile.delete();
			}
		}
		itemService.delete(seq);
		comtService.deleteCasecade("ITEM", seq);
		return "redirect:" + "/admin/item/manage";
	}
	
	@RequestMapping(value = "/admin/item/upload", params = "!seq")
	public String itemUpload() {
		return "item/item_upload";
	}
	
	@RequestMapping(value = "/admin/item/upload", params = "seq")
	public String itemModify(Model model, int seq) {
		ItemVo item = itemService.get(seq);
		item.setContent(item.getContent().replace("&", "&amp;"));
		model.addAttribute("item", item);
		return "item/item_upload";
	}
	
	@RequestMapping(value = "/admin/item/upload.do", method = RequestMethod.POST, params = "!seq")
	public String itemDoUpload(HttpServletRequest request, ItemVo item, MultipartFile snapshtFile) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String imgPath	= "/resources/image/item/snapshot/";
		String filename	= "snapshot_" + item.getName() + "_";
		String imgExt	= null;
		
		if(snapshtFile.getSize() != 0){
			filename += snapshtFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(rootPath + imgPath + filename);
			snapshtFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
			
			item.setSnapsht(imgPath + filename);
		} else{
			item.setSnapsht(imgPath + "default.jpg");
		}
		
		itemService.insert(item);
		
		return "redirect:" + "/admin/item/manage";
	}
	
	@RequestMapping(value = "/admin/item/upload.do", method = RequestMethod.POST, params = "seq")
	public String itemDoModify(HttpServletRequest request, ItemVo item, MultipartFile snapshtFile) throws IllegalStateException, IOException{
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String imgPath	= "/resources/image/item/snapshot/";
		String filename	= "snapshot_" + item.getName() + "_";
		String imgExt	= null;
		if(snapshtFile.getSize() != 0){
			File existFile = new File (rootPath + item.getSnapsht());
			if(existFile.exists()){
				existFile.delete();
			}
			
			filename += snapshtFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(rootPath + imgPath + filename);
			snapshtFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
			
			item.setSnapsht(imgPath + filename);
		} 
		
		itemService.update(item);
		
		return "redirect:" + "/admin/item/manage";
	}
	
	@RequestMapping(value = "/admin/item/imgUpload.do")
	public String itemImgUpload(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("upload")MultipartFile multiFile, String CKEditorFuncNum) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String imgPath	= "/resources/image/item/contents/";
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
		
		response.setHeader("x-frame-options", "SAMEORIGIN");
		model.addAttribute("path", request.getContextPath() + imgPath + filename);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		
		return "item/item_imgupload";
	}

}
