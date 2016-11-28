package com.hlzt.power.web;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlzt.commons.context.AppContext;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.Role;
import com.hlzt.power.model.RolePermission;
import com.hlzt.power.model.UserRole;
import com.hlzt.power.service.PermissionSer;
import com.hlzt.power.service.RolePermissionSer;
import com.hlzt.power.service.RoleSer;
import com.hlzt.power.service.UserRoleSer;

@Controller
@RequestMapping("/power/grant")
public class GrantController {
	//public String addUserRoles(String userId,String roleIds[],Model model, HttpServletRequest req,HttpServletResponse res){
	final String REQ_URL="power/grant/";
	@Resource
	private UserRoleSer userRoleSer;
	@Resource
	private RolePermissionSer rolePermSer;
	@Resource
	private RoleSer roleSer;
	@Resource
	private PermissionSer permissionSer;
	/**
	 * 给用户添加角色	
	 * @param userId
	 * @param roleIds
	 * @param model
	 * @return
	 */
	@RequestMapping("/addUserRoles.shtm")
	public String addUserRoles(String userId,String ids[],Model model){
		
		if(null!=ids)
		for(String roleId:ids)
		{
			UserRole userRole=new UserRole();
			userRole.setFkUser(userId);
			userRole.setFkRole(roleId);
			userRoleSer.add(userRole);
		}
		return "redirect:/power/grant/showPageRolesByUserId.shtm?userId="+userId;
			
	}
	
	/**
	 * 给角色添加权限
	 * @param roleId
	 * @param permIds
	 * @param model
	 * @return
	 */
	@RequestMapping("/addRolePermissions.shtm")
	public String addRolePermissions(String roleId,String permIds[],Model model){
		for(String permId:permIds)
		{
			RolePermission rp=new RolePermission();
			rp.setFkRole(roleId);
			rp.setFkPermission(permId);
			rolePermSer.add(rp);
		}
		return "power/roleAddPermissionList.jsp";
		
	}
	
	
	/**
	 * 给用户删除角色
	 * @param userRoleIds
	 * @param model
	 * @return
	 */
	@RequestMapping("/removeUserRoles.shtm")
	public String removeUserRoles(String userId,BasePage page,String ids[],Model model){
		List list=Arrays.asList(ids);
		Map map=new HashMap();

		userRoleSer.removeBySomeId(new HashMap(), null, list);
		return "redirect:/power/grant/showPageRolesByUserId.shtm?userId="+userId;
		
	}
	
	/**
	 * 给角色删除权限
	 * @param roleId
	 * @param rolePermIds
	 * @param model
	 * @return
	 */
	@RequestMapping("/removeRolePermissions.shtm")
	public String removeRolePermissions(String roleId,BasePage page,String rolePermIds[],Model model){
		List list=Arrays.asList(rolePermIds);
		rolePermSer.removeBySomeId(new HashMap(), null, list);
		return showPageRolePermissions(roleId, page, model);
		
	}
	
	
	
/*	*//**
	 * 显示用户下的角色
	 * @param userId
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/showPageUserRoles.shtm")
	public String showPageUserRoles(String userId,BasePage page,Model model){
        page.setPageUrl(REQ_URL+"showPageUserRoles.shtm");
		page= roleSer.findPageRolesByuserId(userId, page);
		model.addAttribute("page", page);
		return "power/userRoleList.jsp";
	}*/
	
	
	/**
	 * 分页显示角色下的权限
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping("/showPageRolePermissions.shtm")
	public String showPageRolePermissions(String roleId,BasePage page,Model model){
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		page.setPageUrl(REQ_URL+"showPageRolePermissions.shtm");
		page=permissionSer.findPagePermissionsByroleId(roleId, page);
		
		model.addAttribute("page", page);
		return "power/permissionList.jsp";
	}
	

	/**
	 * 分页显示用户拥有的角色
	 * @param model
	 * @param request
	 * @param page
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/showPageRolesByUserId.shtm")
	public String showPageRolesByUserId(Model model,BasePage page,String userId) {
		page.setPageUrl(REQ_URL+"showPageRolesByUserId.shtm");
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		page.setParamsMap(paramsMap);
		page=roleSer.findPageRolesByuserId(userId, page);
		model.addAttribute("page", page);
		model.addAttribute("userId", userId);
		return "power/userRoleList.jsp";
	}
	
	/**
	 * 分页显示用户没有的角色
	 * @param model
	 * @param request
	 * @param page
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/showPageRolesUserNotHave.shtm")
	public String showPageRolesUserNotHave(Model model,HttpServletRequest request,BasePage<Role> page,String userId) throws IOException {

		page.setPageUrl(REQ_URL+"showPageRolesUserNotHave.shtm");
		page=roleSer.findPageRolesUserNotHave(userId, page);
		model.addAttribute("userId", userId);

		model.addAttribute("page", page);
		return "power/userAddRoleList.jsp";
	}
	
	
	
	
	

}
