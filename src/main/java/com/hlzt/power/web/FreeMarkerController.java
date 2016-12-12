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

import com.hlzt.commons.helper.SysConfig;
import com.hlzt.power.freemarker.FreeMarkerUtil;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.FirstPaper;
import com.hlzt.power.model.MidCheck;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.service.StudentFlowManageSer;
import com.hlzt.power.service.UserSer;
import com.hlzt.power.service.ZdTeacherFlowManageSer;

import net.sf.json.JSONObject;

@Controller
public class FreeMarkerController {

	@Autowired
	private StudentFlowManageSer studentFlowManageSer;
	@Autowired
	UserSer userSer;

	/**
	 * @Title: service @Description: FreeMarker生成DOC @param map @param
	 * temp @param fileName @param request @param response void @throws
	 */
	@RequestMapping("createDoc.chtm")
	protected void service(Map<String, Object> map, String temp, String fileName, String filePath,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			// 调用工具类FreeMarkerUtil的createDoc方法生成Word文档
			FreeMarkerUtil.createDoc(map, temp, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/** FreeMarker直接生成并下载文件，放弃该思路，现采用前台提交数据后直接生成word存入数据库方式*/
		// InputStream fin = null;
		// ServletOutputStream out = null;
		// try {
		// fin = new FileInputStream(file);
		// } catch (FileNotFoundException e){
		// e.printStackTrace();
		// }
		// final String userAgent = request.getHeader("USER-AGENT");
		// String finalFileName = null;
		// try {
		// if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
		// finalFileName = URLEncoder.encode(fileName,"UTF8");
		// }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
		// finalFileName = new String(fileName.getBytes(), "ISO8859-1");
		// }else{
		// finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
		// }
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		// response.setCharacterEncoding("utf-8");
		// response.setContentType("application/msword");
		// // 设置浏览器以下载的方式处理该文件
		// response.addHeader("Content-Disposition",
		// "attachment;filename="+finalFileName+".doc");
		// try {
		// out = response.getOutputStream();
		// } catch (IOException e){
		// e.printStackTrace();
		// }
		// byte[] buffer = new byte[512]; // 缓冲区
		// int bytesToRead = -1;
		// // 通过循环将读入的Word文件的内容输出到浏览器中
		// try {
		// while((bytesToRead = fin.read(buffer)) != -1) {
		// out.write(buffer, 0, bytesToRead);
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }finally{
		// if(fin != null)
		// try {
		// fin.close();
		// if(out != null) out.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// if(file != null) file.delete(); // 删除临时文件
		// }
	}

	@RequestMapping("/FreeMarker.shtm")
	public String FreeMarker(String stage,String stuId,Model model,Map<String, Object> map,HttpServletRequest request, HttpServletResponse response)
	{
		Student student = null;
		String filePath = null;//生成文件的绝对路径
		String fileName = null;//文件名
		String rootFile = null;//根目录文件夹
		String rootPath = null;//文件最终的绝对路径
		File dir = null;
		if(StringUtils.isNotBlank(stuId))
		{
			try{
				student = userSer.findStudentInfo(stuId);
			}catch(Exception e){
				e.printStackTrace();
			}			
			if(student!=null)
			{
				Teacher zdTea = null;
				if(StringUtils.isNoneBlank(student.getZdTeacher()))
				{
					try {
						zdTea = studentFlowManageSer.findTeacherInfoByTeaId(student.getZdTeacher());
					} catch (Exception e){
						e.printStackTrace();
					}					
				}				
				if(StringUtils.isNotBlank(stage))
				{
					switch(stage){
						case "taskBook":
							map.put("stuName",student.getStuName());
							map.put("stuNum", student.getUserNum());
							map.put("stuDepartment", student.getDepartment());
							map.put("stuMajor", student.getMajor());
							map.put("teaName", zdTea.getTeaName());
							map.put("zhicheng", zdTea.getZhicheng());
							map.put("title", student.getTitle());
							fileName = "任务书"+"-"+student.getStuName()+"-"+student.getUserNum();
							rootFile = SysConfig.getValue("term");//当前届文件夹
							rootPath =request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录           			 
							 dir = new File(rootPath + File.separator+student.getUserNum());			 
							 if (!dir.exists())//若地址文件夹不存在,创建文件夹
				                 dir.mkdirs();
				             //写文件到服务器
				            filePath = dir.getAbsolutePath() + File.separator +fileName+".doc";
							service(map,"taskBook",fileName,filePath,request,response);
						break;	
						
						case "openingReport":
								map.put("stuName",student.getStuName());
								map.put("stuNum", student.getUserNum());
								map.put("stuDepartment", student.getDepartment());
								map.put("stuMajor", student.getMajor());
								map.put("teaName", zdTea.getTeaName());
								map.put("zhicheng", zdTea.getZhicheng());
								map.put("title", student.getTitle());
								fileName = "开题报告"+"-"+student.getStuName()+"-"+student.getUserNum();
								rootFile = SysConfig.getValue("term");//当前届文件夹
								rootPath =request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录           			 
								dir = new File(rootPath + File.separator+student.getUserNum());			 
								if (!dir.exists())//若地址文件夹不存在,创建文件夹
					                 dir.mkdirs();
					             //写文件到服务器
					            filePath = dir.getAbsolutePath() + File.separator +fileName+".doc";
								service(map,"openingReport",fileName,filePath,request,response);
						break;	
						case "midCheck":
									map.put("stuName",student.getStuName());
									map.put("stuDepartment", student.getDepartment());
									map.put("stuMajor", student.getMajor());
									map.put("title", student.getTitle());
									fileName = "中期检查"+"-"+student.getStuName()+"-"+student.getUserNum();
									rootFile = SysConfig.getValue("term");//当前届文件夹
									rootPath =request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录           			 
									dir = new File(rootPath + File.separator+student.getUserNum());			 
									 if (!dir.exists())//若地址文件夹不存在,创建文件夹
						                 dir.mkdirs();
						             //写文件到服务器
						            filePath = dir.getAbsolutePath() + File.separator +fileName+".doc";
									service(map,"midCheck",fileName,filePath,request,response);
						break;
						case "firstPaper":
							map.put("stuName",student.getStuName());
							map.put("stuDepartment", student.getDepartment());
							map.put("stuMajor", student.getMajor());
							map.put("stuClass", student.getStuClass());
							map.put("teaName", zdTea.getTeaName());
							map.put("zhicheng", zdTea.getZhicheng());
							map.put("title", student.getTitle());
							Enumeration<String> paramNames = request.getParameterNames();
							// 通过循环将表单参数放入键值对映射中
							while(paramNames.hasMoreElements()){
								String key = null;
								if((key = paramNames.nextElement()).equals("teaIdea"))
								{
									String value = request.getParameter(key);
									map.put(key, value);
									break;
								}								
							}
							fileName = "指导记录表（初稿）"+"-"+student.getStuName()+"-"+student.getUserNum();
							rootFile = SysConfig.getValue("term");//当前届文件夹
							rootPath =request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录           			 
							dir = new File(rootPath + File.separator+student.getUserNum());			 
							 if (!dir.exists())//若地址文件夹不存在,创建文件夹
				                 dir.mkdirs();
				             //写文件到服务器
				            filePath = dir.getAbsolutePath() + File.separator +fileName+".doc";
							service(map,"firstPaper",fileName,filePath,request,response);
							map.clear();
							map.put("zdTablePath", filePath);
							int j = studentFlowManageSer.updateFirstPaperById(student.getUserId(), map);
			     			if(j==0){
			     				model.addAttribute("errorMsg","系统错误");
			     				return "error/error.jsp";
			     			}
			     			Enumeration<String> param = request.getParameterNames();
			     			// 通过循环将表单参数放入键值对映射中
							while(param.hasMoreElements()){
								String key = null;
								if((key = param.nextElement()).equals("filePathSrc"))
								{
									String value = request.getParameter(key);
									map.put(key, value);
									break;
								}								
							}
			     			//获取项目网络地址
							String path = request.getContextPath();
							String basePath = request.getScheme() + "://"
									+ request.getServerName() + ":" + request.getServerPort()
									+ path ;
							//截取文件地址，拼接文件的网络地址
							String[] filePaths = filePath.split("lwgl");
							String tablePath = basePath+filePaths[1];
							FirstPaper firstPaper = studentFlowManageSer.findFirstPaperByStuId(student.getUserId());
							firstPaper.setZdTablePath(tablePath);//服务器地址
							firstPaper.setZdTabelSrc(tablePath);//网络地址
							model.addAttribute("successMsg","教师指导记录表（初稿）已生成");
			     			model.addAttribute("firstPaper", firstPaper);
			     			model.addAttribute("filePathSrc", map.get("filePathSrc"));
						return "guideTeacher/firstPaperYuLan.jsp";
						
						case "finalPaper":
							map.put("stuName",student.getStuName());
							map.put("stuDepartment", student.getDepartment());
							map.put("stuMajor", student.getMajor());
							map.put("stuClass", student.getStuClass());
							map.put("teaName", zdTea.getTeaName());
							map.put("zhicheng", zdTea.getZhicheng());
							map.put("title", student.getTitle());
							Enumeration<String> paramFinal = request.getParameterNames();
							// 通过循环将表单参数放入键值对映射中
							while(paramFinal.hasMoreElements()){
								String key = null;
								if((key = paramFinal.nextElement()).equals("teaIdea"))
								{
									String value = request.getParameter(key);
									map.put(key, value);
									break;
								}								
							}
							fileName = "指导教师审阅意见表"+"-"+student.getStuName()+"-"+student.getUserNum();
							rootFile = SysConfig.getValue("term");//当前届文件夹
							rootPath =request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录           			 
							dir = new File(rootPath + File.separator+student.getUserNum());			 
							 if (!dir.exists())//若地址文件夹不存在,创建文件夹
				                 dir.mkdirs();
				             //写文件到服务器
				            filePath = dir.getAbsolutePath() + File.separator +fileName+".doc";
							service(map,"finalPaper",fileName,filePath,request,response);
							map.clear();
							map.put("syTablePath", filePath);
							int k = studentFlowManageSer.updateFinalPaperById(student.getUserId(), map);
			     			if(k==0){
			     				model.addAttribute("errorMsg","系统错误");
			     				return "error/error.jsp";
			     			}
			     			Enumeration<String> paramFinalEnd = request.getParameterNames();
			     			// 通过循环将表单参数放入键值对映射中
							while(paramFinalEnd.hasMoreElements()){
								String key = null;
								if((key = paramFinalEnd.nextElement()).equals("filePathSrc"))
								{
									String value = request.getParameter(key);
									map.put(key, value);
									break;
								}								
							}
			     			//获取项目网络地址
							String finalPath = request.getContextPath();
							String finalBasePath = request.getScheme() + "://"
									+ request.getServerName() + ":" + request.getServerPort()
									+ finalPath ;
							//截取文件地址，拼接文件的网络地址
							String[] FinalFilePaths = filePath.split("lwgl");
							String FinalTablePath = finalBasePath+FinalFilePaths[1];
							FinalPaper finalPaper = studentFlowManageSer.findFinalPaperByStuId(student.getUserId());
							finalPaper.setSyTablePath(filePath);
							finalPaper.setSyTableSrc(FinalTablePath);
							model.addAttribute("successMsg","指导教师审阅意见表已生成");
			     			model.addAttribute("finalPaper", finalPaper);
			     			model.addAttribute("filePathSrc", map.get("filePathSrc"));
						return "guideTeacher/finalPaperYuLan.jsp";
						
						case "pyTable":
							Teacher pyTea = null;
							if(StringUtils.isNoneBlank(student.getPyTeacher()))
							{
								try {
									pyTea = studentFlowManageSer.findTeacherInfoByTeaId(student.getPyTeacher());
								} catch (Exception e){
									e.printStackTrace();
								}					
							}
							map.put("stuName",student.getStuName());
							map.put("stuDepartment", student.getDepartment());
							map.put("stuMajor", student.getMajor());
							map.put("stuClass", student.getStuClass());
							map.put("teaName", pyTea.getTeaName());
							map.put("zhicheng", pyTea.getZhicheng());
							map.put("title", student.getTitle());
							Enumeration<String> paramPy = request.getParameterNames();
							// 通过循环将表单参数放入键值对映射中
							while(paramPy.hasMoreElements()){
								String key = null;
								if((key = paramPy.nextElement()).equals("teaIdea"))
								{
									String value = request.getParameter(key);
									map.put(key, value);
									break;
								}								
							}
							fileName = "评阅教师评阅意见表"+"-"+student.getStuName()+"-"+student.getUserNum();
							rootFile = SysConfig.getValue("term");//当前届文件夹
							rootPath =request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录           			 
							dir = new File(rootPath + File.separator+student.getUserNum());			 
							 if (!dir.exists())//若地址文件夹不存在,创建文件夹
				                 dir.mkdirs();
				             //写文件到服务器
				            filePath = dir.getAbsolutePath() + File.separator +fileName+".doc";
							service(map,"pyTable",fileName,filePath,request,response);
							map.clear();
							map.put("pyTablePath", filePath);
							int m = studentFlowManageSer.updateFinalPaperById(student.getUserId(), map);
			     			if(m==0){
			     				model.addAttribute("errorMsg","系统错误");
			     				return "error/error.jsp";
			     			}
			     			Enumeration<String> paramPyEnd = request.getParameterNames();
			     			// 通过循环将表单参数放入键值对映射中
							while(paramPyEnd.hasMoreElements()){
								String key = null;
								if((key = paramPyEnd.nextElement()).equals("filePathSrc"))
								{
									String value = request.getParameter(key);
									map.put(key, value);
									break;
								}								
							}
			     			//获取项目网络地址
							String pyPath = request.getContextPath();
							String pyBasePath = request.getScheme() + "://"
									+ request.getServerName() + ":" + request.getServerPort()
									+ pyPath ;
							//截取文件地址，拼接文件的网络地址
							String[] pyFilePaths = filePath.split("lwgl");
							String pyTablePath = pyBasePath+pyFilePaths[1];
							FinalPaper pyPaper = studentFlowManageSer.findFinalPaperByStuId(student.getUserId());
							pyPaper.setPyTablePath(filePath);
							pyPaper.setPyTableSrc(pyTablePath);
							model.addAttribute("successMsg","评阅教师评阅意见表已生成");
			     			model.addAttribute("finalPaper", pyPaper);
			     			model.addAttribute("filePathSrc", map.get("filePathSrc"));
						return "guideTeacher/pyYuLan.jsp";
					}
				}
			}		
		}
		return filePath;
	}
}
