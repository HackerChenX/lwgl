package com.hlzt.power.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.User;



public interface UserDao extends BaseDao<User>{
	
	/**
	 * 根据用户账号查询用户
	 * @param userNum
	 * @return
	 */
	public User selectUserByUserNum(@Param("userNum")String userNum);
	
	/**
	 * 删除用户
	 * @param list
	 * @return
	 */
	public int deleteUser(@Param("list")List<String> list);
	
	/**
	 * 更改用户
	 * @param user
	 * @return
	 */
	public int updateUser(@Param("user")User user);
	
	/**
	 * 删除学生用户
	 * @return
	 */
	public int deleteStuUser();
	
	
}
