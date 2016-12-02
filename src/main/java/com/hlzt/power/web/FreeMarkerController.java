package com.hlzt.power.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlzt.power.freemarker.FreeMarkerUtil;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.service.StudentFlowManageSer;

import net.sf.json.JSONObject;

@Controller
public class FreeMarkerController{

	@Autowired
	private StudentFlowManageSer studentFlowManageSer;
	
	@RequestMapping("createDoc.chtm")
	protected void service(Map<String, Object> map,String temp,String fileName,HttpServletRequest request, HttpServletResponse response) 
	{
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}	
		File file = null;
		InputStream fin = null;
		ServletOutputStream out = null;
		try {
			// 调用工具类FreeMarkerUtil的createDoc方法生成Word文档
			file = FreeMarkerUtil.createDoc(map, temp);
			try {
				fin = new FileInputStream(file);
			} catch (FileNotFoundException e){
				e.printStackTrace();
			}
			final String userAgent = request.getHeader("USER-AGENT");
			String finalFileName = null;
			try {				
				if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
					finalFileName = URLEncoder.encode(fileName,"UTF8");
				}else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
					finalFileName = new String(fileName.getBytes(), "ISO8859-1");
				}else{
					finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/msword");
			// 设置浏览器以下载的方式处理该文件
			response.addHeader("Content-Disposition", "attachment;filename="+finalFileName+".doc");			
			try {
				out = response.getOutputStream();
			} catch (IOException e){
				e.printStackTrace();
			}
			byte[] buffer = new byte[512];	// 缓冲区
			int bytesToRead = -1;
			// 通过循环将读入的Word文件的内容输出到浏览器中
			try {
				while((bytesToRead = fin.read(buffer)) != -1) {
					out.write(buffer, 0, bytesToRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}finally {
			if(fin != null)
				try {
					fin.close();
					if(out != null) out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}			
			if(file != null) file.delete();	// 删除临时文件
		}
	}	
	
	@RequestMapping("/student/StudentFreeMarker.shtm")
	public void StudentFreeMarker(String stage,Model model,HttpServletRequest request, HttpServletResponse response)
	{
		Student student = (Student) request.getSession().getAttribute("student");
		Teacher zdTea = null;
		if(StringUtils.isNoneBlank(student.getZdTeacher()))
		{
			zdTea = studentFlowManageSer.findTeacherInfoByTeaId(student.getZdTeacher());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		TaskBook taskBook = null;
		if(StringUtils.isNotBlank(stage))
		{
			switch(stage){
				case "taskBook":
					taskBook = studentFlowManageSer.findTaskByStuId(student.getUserId());
					if(taskBook!=null){
						if(StringUtils.isNotBlank(taskBook.getMainTask()))
						{
							map.put("stuName",student.getStuName());
							map.put("stuNum", student.getUserNum());
							map.put("stuDepartment", student.getDepartment());
							map.put("stuMajor", student.getMajor());
							map.put("teaName", zdTea.getTeaName());
							map.put("zhicheng", zdTea.getZhicheng());
							map.put("title", student.getTitle());
							map.put("mainTask",taskBook.getMainTask());
							map.put("zhiBiao",taskBook.getZhiBiao());
							map.put("yaoQiu", taskBook.getYaoQiu());
							map.put("wenXian", taskBook.getWenXian());
							String fileName = "任务书"+"-"+student.getStuName()+"-"+student.getUserNum();
							service(map,"taskBook",fileName,request,response);
						}
					}
				break;
				case "openingReport":
				break;
				case "firsrPaper":
				break;
				case "finalPaper":
				break;
			}
		}
		
		

	}
}
