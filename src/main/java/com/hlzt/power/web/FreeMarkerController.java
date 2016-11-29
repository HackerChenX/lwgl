package com.hlzt.power.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlzt.power.freemarker.FreeMarkerUtil;
import com.hlzt.power.model.Student;
import com.hlzt.power.service.StudentFlowManageSer;

@Controller
public class FreeMarkerController{

	@Autowired
	private StudentFlowManageSer studentFlowManageSer;
	
	@RequestMapping("createDoc.chtm")
	protected void service(HttpServletRequest request, HttpServletResponse response) 
	{
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Enumeration<String> paramNames = request.getParameterNames();
		// 通过循环将表单参数放入键值对映射中
		//前台name需要与模板占位符对应
		while(paramNames.hasMoreElements()) {
			String key = paramNames.nextElement();
			String value = request.getParameter(key);
			map.put(key, value);
		}
	
		// 提示：在调用工具类生成Word文档之前应当检查所有字段是否完整
		// 否则Freemarker的模板引擎在处理时可能会因为找不到值而报错 这里暂时忽略这个步骤了
		File file = null;
		InputStream fin = null;
		ServletOutputStream out = null;
		try {
			// 调用工具类FreeMarkerUtil的createDoc方法生成Word文档
			file = FreeMarkerUtil.createDoc(map, "resume");
			try {
				fin = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/msword");
			// 设置浏览器以下载的方式处理该文件默认名为resume.doc
			response.addHeader("Content-Disposition", "attachment;filename=resume.doc");
			
			try {
				out = response.getOutputStream();
			} catch (IOException e) {
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
		} finally {
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
	
}
