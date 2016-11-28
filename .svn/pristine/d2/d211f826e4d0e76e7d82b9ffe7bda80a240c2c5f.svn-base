package com.hlzt.commons.interceptor;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hlzt.commons.context.AppContext;

/**
 * 微信oauth2拦截器 只拦截htm后缀
 * 
 * @author Administrator
 * 
 */

public class WxInterceptor implements HandlerInterceptor {
	Logger logger = LoggerFactory.getLogger(WxInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		
		
				response.sendRedirect(request.getContextPath()
						+ "/resource/wx/html/index.jsp");
			return false;


	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// logger.info("######################postHandle");
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}