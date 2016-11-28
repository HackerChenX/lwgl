package com.hlzt.power.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.service.BaseServiceImpl;
import com.hlzt.power.dao.UserRoleDao;
import com.hlzt.power.model.Role;
import com.hlzt.power.model.UserRole;
import com.hlzt.power.service.UserRoleSer;

@Transactional
@Component
public class UserRoleSerImpl extends BaseServiceImpl<UserRole> implements UserRoleSer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	UserRoleDao userRoleDao;
	@Override
	protected BaseDao<UserRole> getDao() {
		// TODO Auto-generated method stub
		return userRoleDao;
	}
	@Override
	public List<UserRole> findRolesByUserId(String userId) {
		// TODO Auto-generated method stub
		return userRoleDao.findRolesByUserId(userId);
	}
	@Override
	public List<Role> findRolesByManyUserIds(String userIds) {
		// TODO Auto-generated method stub
		return userRoleDao.findRolesByManyUserIds(userIds);
	}
	
	@Override
	public List<UserRole> findRoleByUserId(String userId) {
		List<UserRole> list = new ArrayList<UserRole>();
		list = userRoleDao.findRoleNameListByUserId(userId);
		return list;
	}
	@Override
	public List<UserRole> findByFKUser(String fkUser) {
		 List<UserRole> roles=userRoleDao.findByFKUser(fkUser);
		return roles;
	}



}
