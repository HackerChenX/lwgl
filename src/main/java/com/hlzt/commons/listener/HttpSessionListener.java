package com.hlzt.commons.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Session监听器
 * @author lhy
 * 
 * 
 */
public class HttpSessionListener implements HttpSessionAttributeListener {  
	
	Logger logger=LoggerFactory.getLogger(HttpSessionListener.class);
	/**
	 * 添加Session
	 */
	public void attributeAdded(HttpSessionBindingEvent event) {  
        if ("userInfo".equals(event.getName())) {  
           
        }  
    }  
  
	/**
	 * 替代Session
	 */
    public void attributeReplaced(HttpSessionBindingEvent event) {  
        if ("userInfo".equals(event.getName())) {  
           
        }  
    }

    /**
     * 销毁Session
     */
	public void attributeRemoved(HttpSessionBindingEvent event) {
		if("userInfo".equals(event.getName())){
			
		}  
	}     
}  