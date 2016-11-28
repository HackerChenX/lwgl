package com.hlzt.power.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.Role;
import com.hlzt.power.model.UserRole;

public interface UserRoleDao extends BaseDao<UserRole>{
	
	/**
	 * 根据userId 查询对应的角色列表
	 * @param userId
	 * @return
	 */
	@Select("select * from role r where r.id in ( select ur.fk_role from  user_role ur where ur.fk_user=#{userId}) group by r.id")
	public List<UserRole> findRolesByUserId(String userId);
	
	/**
	 * 根据多个userid组成的字符串如(1,2,3)查询所有角色
	 * @param userIds
	 * @return
	 */
	@Select("select * from role r where r.id in ( select ur.fk_role from  user_role ur where ur.fk_user in #{userIds}) group by r.id")
	public List<Role> findRolesByManyUserIds(String userIds);
	
	/**
	 * 根据用户账号查询用户角色列表
	 * @param userNum
	 * @return
	 */
	public List<UserRole> findRoleNameListByUserId(String userId);
	
	//public BasePage<Role>findPageRolesByUserId(String userId);
	
	/**
	 * 删除用户角色
	 * @param list
	 * @return
	 */
	public int deleteUserRole(@Param("list")List<String> list);
	
	/**
	 * 根据用户ID和角色删除用户角色
	 * @param userId
	 * @param role
	 * @return
	 */
	public int deleteByUserIdAndRole(@Param("userId")String userId, @Param("role")String role);
	
	/**
	 * 根据角色查询用户角色
	 * @param role
	 * @return
	 */
	public List<UserRole> selectUserRoleByRole(@Param("role")String role);
	
	/**
	 * 删除学生角色
	 * @return
	 */
	public int deleteStuRole();

	public List<UserRole> findByFKUser(String fkUser);
	
}
