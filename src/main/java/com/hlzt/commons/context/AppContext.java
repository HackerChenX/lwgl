package com.hlzt.commons.context;

import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.hlzt.power.model.User;


/**
 * 项目上下文
 * @author lhy 
 *
 *
 */
public class AppContext {
	/**
	 * 系统用户
	 */
	private static ThreadLocal<User> userLocal = new ThreadLocal<User>();  
	
	private static ThreadLocal<HttpSession> sessionLocal = new ThreadLocal<HttpSession>();
    
	private static ThreadLocal<String> wxOpenIdLocal = new ThreadLocal<String>();
	/**
	 * 微信用户
	 *//*
	private static ThreadLocal<WxUser> wxUserLocal = new ThreadLocal<WxUser>();*/
	
	
	/**
	 * 赋值
	 * @param session
	 */
    public static void setUser(User user) {  
   
    	userLocal.set(user);  
    }  
    /**
     * 取值
     * @return
     */
    public static User getUser() {  
        return userLocal.get();  
    }    
    /**
     * 移除
     */
    public static void removeUser(){
    	userLocal.remove();
    }
    
  
	
	
	public static String getWxOpenId() {
     
		 Thread t = Thread.currentThread();
         String name = t.getName();
        System.out.println("当前线程##############"+name);
		return wxOpenIdLocal.get();
	}
	public static void setWxOpenId(String wxOpenId) {
		AppContext.wxOpenIdLocal.set(wxOpenId);
	}
	
	
    

    

    
    

    
    
}