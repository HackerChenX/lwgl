package com.hlzt.power.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.hlzt.commons.helper.SysConfig;
import com.hlzt.commons.model.GlobalVar;
import com.hlzt.power.model.BackLog;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.PublicNotice;
import com.hlzt.power.service.PublicSer;

@Controller
public class ConfigController {
	@Autowired
	private PublicSer publiSer;
	
	/**
	 * 重新设置每页多少条
	 * @param model
	 * @param pageUrl
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/resetPageSize.shtm")
	@ResponseBody
	public String resetPageSize(Model model, String pageSize,
			HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(pageSize)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		GlobalVar.setPageSize(Integer.parseInt(pageSize));
		return "success";
	}
	
	/**
	 * 根据专业查询班级
	 * @param majorId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findClassByMajor.shtm")
	@ResponseBody
	public String findClassByMajor(String majorName, Model model, 
			HttpServletRequest request, HttpServletResponse response){
		try {
			majorName = new String(majorName.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		} 
		List<ClassName> classList = new ArrayList<ClassName>();
		if("0".equals(majorName)){
			majorName = null;
			classList = publiSer.findClass(null);
		}else{
			Major major = publiSer.findMajorByName(majorName);
			classList = publiSer.findClass(major.getId());			
		}
		return JSONArray.fromObject(classList).toString();
	}
	
	/**
	 * 查询公告
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findNotice.chtm")
	public String findNotice(Model model, HttpServletRequest request, HttpServletResponse response){
		List<PublicNotice> paperList = new ArrayList<PublicNotice>();
		List<PublicNotice> tableList = new ArrayList<PublicNotice>();
		List<PublicNotice> noticeList = new ArrayList<PublicNotice>();
		try {
			paperList = publiSer.findNotice("paper");
			tableList = publiSer.findNotice("table");
			noticeList = publiSer.findNotice("notice");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("paperList", paperList);
		model.addAttribute("tableList", tableList);
		model.addAttribute("noticeList", noticeList);
		return "";
	}
	
	
	
	/**
	 * 系统维护中
	 * @return
	 */
	@RequestMapping("systemUpdatIng.shtm")
	public String systemUpdatIngUpdatIng(){
		
		return  "closeSystemInfo.jsp";
	}
	/**
	 * @Title: downloadFile
	 * @Description: 下载
	 * @param model
	 * @param filePath
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException ResponseEntity<byte[]> 
	 * @throws
	 */
	@RequestMapping("downloadFile.chtm")    
    public ResponseEntity<byte[]> downloadFile(Model model,String filePath,
			HttpServletRequest request, HttpServletResponse response){     
            	
		try {
			String path = new String(filePath.getBytes("iso8859-1"),"UTF-8");
			String name = path.substring(path.lastIndexOf(File.separator)+1,path.length());		   	   	  
	        HttpHeaders headers = new HttpHeaders();    
	        String fileName;	
			fileName = new String(name.getBytes("UTF-8"),"iso-8859-1");
			headers.setContentDispositionFormData("attachment", fileName);   
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);		          
	        File file=new File(path);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }   
    
    /**
     * @Title: noticePage
     * @Description: 公告展示页面
     * @param model
     * @param id
     * @param request
     * @param response
     * @return String 
     * @throws
     */
    @RequestMapping("/notice.chtm")
    public String noticePage(Model model,String id, HttpServletRequest request, HttpServletResponse response){
    	
    	PublicNotice publicNotice = publiSer.findNoticeById(id);
    	model.addAttribute("publicNotice",publicNotice);
    	return "Public_Page/notice.jsp";
    }
    
}
