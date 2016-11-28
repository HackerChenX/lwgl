package com.hlzt.power.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.Permission;
import com.hlzt.power.model.Role;
import com.hlzt.power.model.RolePermission;

public interface RolePermissionDao extends BaseDao<RolePermission>{
	
	/**
	 * 根据roleid查询权限
	 * @param roleId
	 * @return
	 */
	@Select("select * from permission p where p.id in ( select rp.fk_permission from  role_permission rp where rp.fk_role=#{roleId}) group by p.id")
	public List<Permission> findPermissionsByRoleId(String roleId);
	/**
	 * 根据多个roleid组成的字符串如(1,2,3)查询所有权限
	 * @param roleIds
	 * @return
	 */
	@Select("select * from permission p where p.id in ( select rp.fk_permission from  role_permission rp where rp.fk_role in #{roleIds}) group by p.id")
	public List<Permission> findPermissionsByManyRoleIds(String roleIds);
	
	

}
