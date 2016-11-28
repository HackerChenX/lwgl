package com.hlzt.power.service;

import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.service.BaseService;
import com.hlzt.power.model.Permission;
import com.hlzt.power.model.Role;


public interface PermissionSer extends BaseService<Permission>{
	
	/**
	 * 根据角色id分页查询下面的权限
	 * @param roleId
	 * @param page
	 * @return
	 */
	public BasePage<Permission>findPagePermissionsByroleId(String roleId,BasePage<Permission> page);
	
	/**
	 * 根据用户的id分页查询角色所没有的权限
	 * @param userId
	 * @param page
	 * @return
	 */
	public BasePage<Permission>findPagePermsRoleNotHave(String roleId,BasePage<Permission> page);
	

}
