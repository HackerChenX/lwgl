package com.hlzt.power.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.service.BaseServiceImpl;
import com.hlzt.power.dao.PermissionDao;
import com.hlzt.power.model.Permission;
import com.hlzt.power.model.Role;
import com.hlzt.power.service.PermissionSer;


@Transactional
@Component
public class PermissionSerImpl extends BaseServiceImpl<Permission> implements PermissionSer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private PermissionDao  permDao;
	@Override
	protected BaseDao<Permission> getDao() {
		// TODO Auto-generated method stub
		return permDao;
	}
	@Override
	public BasePage<Permission> findPagePermissionsByroleId(String roleId,
			BasePage<Permission> page) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fkRole", roleId);
		map.put("OPE_TYPE", 1);//选项用于区分分页操作类型,等于
		return super.findPage(map, page, null);
	}
	@Override
	public BasePage<Permission> findPagePermsRoleNotHave(String roleId,
			BasePage<Permission> page) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fkRole", roleId);
		map.put("OPE_TYPE", 2);//选项用于区分分页操作类型,等于
		return super.findPage(map, page, null);
	}
	
	

}
