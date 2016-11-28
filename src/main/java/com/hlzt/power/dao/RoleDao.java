package com.hlzt.power.dao;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.Role;

public interface RoleDao extends BaseDao<Role>{
	
	public Role findRoleByRoleName(String roleName);
}
