package com.hlzt.power.service;

import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.service.BaseService;
import com.hlzt.power.model.Role;

public interface RoleSer extends BaseService<Role>{
	
	/**
	 * 根据用户的id分页查询用户所有的角色
	 * @param userId
	 * @param page
	 * @return
	 */
	public BasePage<Role>findPageRolesByuserId(String userId,BasePage<Role> page);
	
	/**
	 * 根据用户的id分页查询用户所没有的角色
	 * @param userId
	 * @param page
	 * @return
	 */
	public BasePage<Role>findPageRolesUserNotHave(String userId,BasePage<Role> page);
	

}
