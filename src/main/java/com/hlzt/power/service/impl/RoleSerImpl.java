package com.hlzt.power.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.service.BaseServiceImpl;
import com.hlzt.power.dao.RoleDao;
import com.hlzt.power.model.Role;
import com.hlzt.power.service.RoleSer;
import com.hlzt.power.service.UserRoleSer;

@Transactional
@Component
public class RoleSerImpl extends BaseServiceImpl<Role> implements RoleSer{

	/**
	 * 
	 */
	@Resource
	UserRoleSer userRoleSer;
	
	private static final long serialVersionUID = 1L;
	@Resource
	RoleDao rdao;
	@Override
	protected BaseDao<Role> getDao() {
		// TODO Auto-generated method stub
		return rdao;
	}
	@Override
	public BasePage<Role> findPageRolesByuserId(String userId,
			BasePage<Role> page) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fkUser", userId);
		map.put("OPE_TYPE", 1);//选项用于区分分页操作类型，等于
		return super.findPage(map, page, null);
	}
	@Override
	public BasePage<Role> findPageRolesUserNotHave(String userId,
			BasePage<Role> page) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fkUser", userId);
		map.put("OPE_TYPE", 2);//选项用于区分分页操作类型，不等于
		return super.findPage(map, page, null);
	}

}
