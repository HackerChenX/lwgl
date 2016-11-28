package com.hlzt.power.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.service.BaseService;
import com.hlzt.power.model.Role;
import com.hlzt.power.model.UserRole;

public interface UserRoleSer extends BaseService<UserRole>{
	
	/**
	 * 根据用户id查询角色列表
	 * @param userId
	 * @return
	 */
	public List<UserRole> findRolesByUserId(String userId);
	/**
	 * 根据多个userid组成的字符串如(1,2,3)查询所有角色
	 * @param userIds
	 * @return
	 */
	public List<Role> findRolesByManyUserIds(String userIds);
	/**
	 * 根据用户账号查询用户角色
	 * @param userNum
	 * @return
	 */
	public List<UserRole> findRoleByUserId(String userId);
	public List<UserRole> findByFKUser(String fkUser);

}
