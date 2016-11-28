package com.hlzt.power.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.service.BaseServiceImpl;
import com.hlzt.power.dao.RolePermissionDao;
import com.hlzt.power.model.Permission;
import com.hlzt.power.model.RolePermission;
import com.hlzt.power.service.RolePermissionSer;

@Transactional
@Component
public class RolePermissionImpl extends BaseServiceImpl<RolePermission> implements RolePermissionSer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	RolePermissionDao rDao;
	@Override
	protected BaseDao<RolePermission> getDao() {
		// TODO Auto-generated method stub
		return rDao;
	}
	@Override
	public List<Permission> findPermissionsByRoleId(String roleId) {
		// TODO Auto-generated method stub
		return rDao.findPermissionsByRoleId(roleId);
	}
	@Override
	public List<Permission> findPermissionsByManyRoleIds(String roleIds) {
		// TODO Auto-generated method stub
		return rDao.findPermissionsByManyRoleIds(roleIds);
	}

}
