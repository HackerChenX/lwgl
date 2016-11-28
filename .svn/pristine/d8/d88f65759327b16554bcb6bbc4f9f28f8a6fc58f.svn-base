package com.hlzt.power.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlzt.commons.context.AppContext;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.Role;
import com.hlzt.power.model.User;
import com.hlzt.power.service.RoleSer;
import com.hlzt.power.service.UserRoleSer;

@Controller
@RequestMapping("/power/role")
public class RoleController {
	
	final String REQ_URL="power/role/";
	@Resource
	private  UserRoleSer userRoleSer;
	@Resource
	private RoleSer roleSer;
	
	@RequestMapping("/beforeAdd.shtm")
	public String befareAdd(Model model,HttpServletRequest request) throws IOException {
		return "power/roleAdd.jsp";
		
	}
	/**
	 * 添加
	 * @param model
	 * @param request
	 * @param role
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/add.shtm")
	public String add(Model model,HttpServletRequest request,Role role) throws IOException {
	
	      Map<String, Object> map=new HashMap<String, Object>();
	      map.put("roleName", role.getRoleName());
	      List list=roleSer.list(map, null);
	      if(list.size()>0)
	      {
	    	  
	    	  model.addAttribute("errorMsg", "角色名称不能重复，您添加的角色名称已经存在");
	    	  return "error/error.jsp";
	      }
		  roleSer.add(role);

		return showPageRoles(model, request, new BasePage());
		
	}
	
	/**
	 * 分页显示全部角色
	 * @param model
	 * @param request
	 * @param page
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/showPageRoles.shtm")
	public String showPageRoles(Model model,HttpServletRequest request,BasePage page) throws IOException {
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		page.setPageUrl(REQ_URL+"showPageRoles.shtm");
		page=roleSer.findPage(new HashMap(), page, null);
		model.addAttribute("page", page);
		return "power/roleList.jsp";
		
	}
	
	
	@RequestMapping("/remove.shtm")
	public String remove(Model model,HttpServletRequest request,HttpServletResponse response,String[]ids) throws IOException {
		List list=Arrays.asList(ids);
		try{
		roleSer.removeBySomeId(new HashMap(), null, list);
		}
		catch (Exception e) {
			model.addAttribute("errorMsg", "有用户用到此角色，不能删除!!");
			return "error/error.jsp";
		}
		return showPageRoles(model, request, new BasePage());
	}
	
	

}
