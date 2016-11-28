package com.hlzt.power.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stagePaper")
public class YuLanController {

	@RequestMapping("/yulan.shtm")
	public String yulan (String path, Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("----预览----");	
//		if(path.equals("")){	//文件路径为空时报溢出错误
//			model.addAttribute("errorMsg", "预览文件不存在！");
//			return "error/error.jsp";
//		}
		String formatPath = new String(path.getBytes("iso8859-1"), "UTF-8");// get数据中文转码
		String yulanPath=null;
		try {
			String innerPath=null;
			innerPath = formatPath.substring(formatPath.indexOf("lwgl"));// 截取文件路径,如果path为空此处无法截取路径，抛出catch。
			System.out.println(innerPath);
			String fuwu = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/";
			yulanPath = fuwu + innerPath;
			System.out.println(yulanPath);
			
		} catch (Exception e) {
			return "office/xx.jsp";
		}
		
		File f=new File(yulanPath);//如果数据库中存在路径，源文件可能不存在，此处检测文件是否存在。
		if(f.exists()){
			model.addAttribute("filePath", yulanPath);
			return "office/xx.jsp";
		}else {
			return "office/xx.jsp";
		}		
	}

	@RequestMapping("/downloadFile.shtm")
	public ResponseEntity<byte[]> download(Model model, String path,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String filePath = new String(path.getBytes("iso8859-1"), "UTF-8");
		String name = path.substring(path.lastIndexOf(File.separator) + 1,
				path.length());
		File file = new File(filePath);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);
	}
}
