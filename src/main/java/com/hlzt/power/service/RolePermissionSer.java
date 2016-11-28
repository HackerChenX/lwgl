package com.hlzt.power.service;

import java.util.List;


import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.service.BaseService;
import com.hlzt.power.model.Permission;
import com.hlzt.power.model.RolePermission;

public interface RolePermissionSer extends BaseService<RolePermission>{
	
	/**
	 * 根据roleid查询权限
	 * @param roleId
	 * @return
	 */
	public List<Permission> findPermissionsByRoleId(String roleId);
	
	/**
	 * 根据多个roleid组成的字符串如(1,2,3)查询所有权限
	 * @param roleIds
	 * @return
	 */
	public List<Permission> findPermissionsByManyRoleIds(String roleIds);
	
	


}
