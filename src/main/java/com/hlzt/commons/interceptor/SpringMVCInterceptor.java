package com.hlzt.commons.interceptor;
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;  
import org.springframework.web.servlet.ModelAndView;  

import com.hlzt.commons.context.AppContext;
import com.hlzt.power.model.User;
  
public class SpringMVCInterceptor implements HandlerInterceptor {  
  Logger logger=LoggerFactory.getLogger(SpringMVCInterceptor.class);

    @Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {    
    	
	    User user=(User)request.getSession().getAttribute("user");
		AppContext.setUser(user);//存储上下文用户对象
		//logger.info("##############更新用户上下文");
        return true;  
    }  
      
  
    @Override  
    public void postHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler,  
            ModelAndView modelAndView) throws Exception {  
    	//logger.info("######################postHandle");
        // TODO Auto-generated method stub  
          
    }  
 
    @Override  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex)  
    throws Exception {  
        // TODO Auto-generated method stub  
          
    }  
      
}  