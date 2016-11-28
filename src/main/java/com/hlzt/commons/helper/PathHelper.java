package com.hlzt.commons.helper;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathHelper {
	
	Logger logger=LoggerFactory.getLogger(PathHelper.class);
	/**
	 * 获得前服务器根路径下bin的绝对路径
	 * 
	 * @return tomcat-8.0.30/bin
	 */
	public String getServerRootRealPath()
	{
		String uploadPath=null;
		 File file = new File("."); 
         try {
			uploadPath=file.getCanonicalPath() ;
		} catch (IOException e) {
			logger.error("##############当前服务器根路径的绝对路径获得失败");
			
		}
         return uploadPath;
	}
	
	/**
	 * 获得相对 tomcat 的bin的绝对路径
	 * @param childPath /aa/   前面要加 "/"
	 * @return tomcat-8.0.30/webapps/ROOT/aa/ 
	 */
	public String getServerRootRealPath(String childPath)
	{
		  
         return getServerRootRealPath()+childPath;
	}
	
	/**
	 * 获得项目所在的对对路径  tomcat-8.0.30/webapps/ROOT/ 
	 * @param request
	 * @return
	 */
	public String getProjectRealPath(HttpServletRequest request)
	{
		return request.getServletContext().getRealPath("/");
	}
	
	/**
	 * 获得某目录下的绝对路径
	 * @param request  
	 * @param childPath  参数格式 /aa/    前面要加 "/" 
	 * @return tomcat-8.0.30/webapps/ROOT/aa/ 
	 */
	public String getProjectRealPath(HttpServletRequest request,String childPath)
	{
		return request.getServletContext().getRealPath("/");
	}

}
