package com.cglee079.portfolio.cotroller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.portfolio.model.Item;
import com.cglee079.portfolio.service.ItemService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private ItemService itemService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/")
	public String home(Locale locale, Model model) {
		List<Item> items = itemService.list();
		model.addAttribute("items", items);
		return "home";
	}

	@RequestMapping(value = "/item")
	public String item(Model model) {
		List<Item> items = itemService.list();
		model.addAttribute("items", items);
		return "item";
	}
	
	@RequestMapping(value = "/item/delete")
	public String itemDelete(int seq) {
		boolean result = itemService.delete(seq);
		return "redirect:" + "/item";
	}
	
	@RequestMapping(value = "/item/upload", params = "!seq")
	public String itemUpload() {
		return "itemUpload";
	}
	
	@RequestMapping(value = "/item/upload", params = "seq")
	public String itemModify(Model model, int seq) {
		Item item = itemService.get(seq);
		model.addAttribute("item", item);
		return "itemUpload";
	}
	
	@RequestMapping(value = "/item/upload.do", method = RequestMethod.POST, params = "!seq")
	public String itemDoUpload(HttpServletRequest request, Item item, MultipartFile snapshtFile) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String imgPath	= "/resources/image/snapshot/";
		String filename	= "snapshot_" + item.getName() + "_";
		
		if(snapshtFile.getSize() != 0){
			filename += snapshtFile.getOriginalFilename();
			File file = new File(rootPath + imgPath + filename);
			snapshtFile.transferTo(file);
			
			item.setSnapsht(imgPath + filename);
		} else{
			item.setSnapsht(imgPath + "default.jpg");
		}
		
		itemService.insert(item);
		
		return "redirect:" + "/item";
	}
	
	@RequestMapping(value = "/item/upload.do", method = RequestMethod.POST, params = "seq")
	public String itemDoModify(HttpServletRequest request, Item item, MultipartFile snapshtFile) throws IllegalStateException, IOException{
		System.out.println("itemDoModify");
		
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String imgPath	= "/resources/image/snapshot/";
		String filename	= "snapshot_" + item.getName() + "_";
		
		if(snapshtFile.getSize() != 0){
			File existFile = new File (rootPath + item.getSnapsht());
			if(existFile.exists()){
				existFile.delete();
			}
			
			filename += snapshtFile.getOriginalFilename();
			File file = new File(rootPath + imgPath + filename);
			snapshtFile.transferTo(file);
			
			item.setSnapsht(imgPath + filename);
		} 
		
		itemService.update(item);
		
		return "redirect:" + "/item";
	}
	
	@RequestMapping(value = "/item/imgUpload.do", method = RequestMethod.POST)
	public String itemImgUpload(HttpServletRequest request, Model model,
			@RequestParam("upload")MultipartFile multiFile, String CKEditorFuncNum) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String imgPath	= "/resources/image/contents/";
		String filename	= "content_" + new SimpleDateFormat("YYMMdd_HHmmss").format(new Date()) + "_";
		
		if(multiFile != null){
			filename += multiFile.getOriginalFilename();
			File file = new File(rootPath + imgPath + filename);
			multiFile.transferTo(file);
		}
		
		model.addAttribute("path", request.getContextPath() + imgPath + filename);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		
		return "item.imgupload";
	}

}
