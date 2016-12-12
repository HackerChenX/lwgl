package com.hlzt.power.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlzt.power.jacob.WordBean;

@Controller
public class WordJacodController{
	
	@RequestMapping("wordJacod.shtm")
	public String wordJacod(String filePath,String filePathSrc,String fileType,Model model,Map<String, Object> map,HttpServletRequest request, HttpServletResponse response){

		WordBean word = new WordBean();
		String path = null;
		try {
			path = new String(filePath.getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(path)){
			File file=new File(path); 
			if(file.exists()){
				try{
					word.openDocument(path);
				}catch(Exception e){
					e.printStackTrace();
					word.close();
				}
			}else{
				model.addAttribute("errorMsg", "服务器异常,文件不存在！");
				return "error/error.jsp";
			}			
		}else{
			model.addAttribute("errorMsg", "系统异常");
			return "error/error.jsp";
		}
		Enumeration<String> paramNames = request.getParameterNames();
		// 通过循环将表单参数放入键值对映射中
		while(paramNames.hasMoreElements()){
			String key = paramNames.nextElement();
			String value = request.getParameter(key);
			map.put(key, value);
		}
		switch (fileType) {
		case "openingReport":
			try{
				Object object = new Object();
				object = map.get("teaIdea");
				String toFindText = "指导教师对开题报告的意见";
				String newText = toFindText +"\r\n"+(String)object+"\r\r\r\r\r\r";
				word.setTableCellSelected(1,12,1);
				String texts = word.getTxtFromCell(1,12,1).split("指导教师签名")[0];
				word.find(texts);
				word.insertText(newText);
				//重新查询并选定插入值，设置行距
				word.find((String)object);
				//设置行距，4代表固定值，方法中已设置为18磅固定值
				word.setParaFormat(4);
				word.save(path);
				word.closeDocument();
				word.close();
				model.addAttribute("filePath", filePath);
				model.addAttribute("filePathSrc", filePathSrc);
			}catch (Exception e){
				word.closeDocument();
				word.close();
				e.printStackTrace();
			}
			return "guideTeacher/openingReportYuLan.jsp";
		case "midCheck":
			try{
				//填充数据
				word.putTxtToCell(1,6,3,(String)map.get("adjust"));
				word.putTxtToCell(1,7,3,(String)map.get("achievement"));
				word.putTxtToCell(1,8,3,(String)map.get("attitude"));
				word.putTxtToCell(1,9,3,(String)map.get("time"));
				word.putTxtToCell(1,10,3,(String)map.get("assess"));
				word.putTxtToCell(1,11,3,(String)map.get("save"));
				//填充教师评语
				String toFindText = "指导教师目前对论文进展情况的意见：";
				String newText = toFindText +"\r\n"+(String)map.get("teaIdea")+"\r\r\r";
				String texts = word.getTxtFromCell(1,12,2).split("指导教师签字：")[0];
				word.find(texts);
				word.insertText(newText);
				//重新查询并选定插入值，设置行距
				word.find((String)map.get("teaIdea"));
				//设置行距，4代表固定值，方法中已设置为18磅固定值
				word.setParaFormat(4);
				word.setAlignment(0);
				word.save(path);
				word.closeDocument();
				word.close();
				model.addAttribute("filePath", filePath);
				model.addAttribute("filePathSrc", filePathSrc);
			}catch (Exception e){
				word.closeDocument();
				word.close();
				e.printStackTrace();
			}
			return "guideTeacher/midCheckYuLan.jsp";
		}
		
	return null;	
}
}