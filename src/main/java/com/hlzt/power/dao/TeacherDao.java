package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.TeacherTitle;


public interface TeacherDao extends BaseDao<Teacher>{
    
	/**
	 * 根据教师账号查询教师
	 * @param teaNum
	 * @return
	 */
	public Teacher selectTeacherByTeaNum(@Param("teaNum")String teaNum);
	
	/**
	 * 根据答辩小组查询副高职称教师
	 * @return
	 */
	public List<Teacher> selectSuperTeacher(@Param("major")String major, @Param("groupId")String groupId);
	
	/**
	 * 查询全部副高职称教师
	 * @param major
	 * @return
	 */
	public List<Teacher> selectAllSuperTeacher(@Param("major")String major);
	
	/**
	 * 查询普通教师
	 * @param major
	 * @return
	 */
	public List<Teacher> selectGeneralTeacher(@Param("major")String major, @Param("groupId")String groupId);
	
	/**
	 * @Title: findByTeaId
	 * @Description: 根据教师ID查询(对应用户表中ID)
	 * @param teaId
	 * @return Teacher 
	 * @throws
	 */
	public Teacher findByTeaId(@Param("teaId")String teaId);
	/**
	 * @Title: updateTeacherNowStuNum
	 * @Description: 更新教师当前所带学生数
	 * @param teaId
	 * @param Stutas
	 * @return int 
	 * @throws
	 */
	public int updateTeacherNowStuNum(@Param("teaId")String teaId,@Param("Status")String Status);
	/**
	 * 给自己专业教师统一设置所带学生数量
	 * @param num
	 * @param major
	 * @return
	 */
	public int updateAllTeaStuNum(@Param("num")int num, @Param("major")String major);
	
	/**
	 * 根据ID给教师设置所带学生数
	 * @param list
	 * @param num
	 * @param major
	 * @return
	 */
	public int updateTeaStuNumByIds(@Param("list")List<String> list, @Param("num")int num, @Param("major")String major);
	
	/**
	 * 删除老师
	 * @param list
	 * @return
	 */
	public int deleteTeacher(@Param("list")List<String> list);
	
	/**
	 * 查询教师
	 * @param userId
	 * @return
	 */
	public Teacher selectByUserId(@Param("userId")String userId);
	
	/**
	 * 更改教师
	 * @param userId
	 * @return
	 */
	public int updateTeacher(@Param("teacher")Teacher teacher);
	
	/**
	 * 给教师设置答辩小组
	 * @param list
	 * @param dbGroupId
	 * @return
	 */
	public int setDbGroupForTea(@Param("list")List<String> list, @Param("dbGroupId")String dbGroupId);
	
	/**
	 * 根据小组id查询教师
	 * @param groupId
	 * @return
	 */
	public List<Teacher> selectTeaByGroupId(@Param("groupId")String groupId, @Param("major")String major);
	
	/**
	 * 根据一组教师id查询教师
	 * @param groupId
	 * @return
	 */
	public List<Teacher> selectTeaByIdList(@Param("list")List<String> list);
/**
 * 通过答辩小组Id查询教师
 * @author gym
 * @param dbId
 * @return
 * List<Teacher> 
 */
	public List<Teacher> findTeaByDbId(String dbId);
	
	/**
	 * 重置教师
	 * @return
	 */
	public int resetTea();
	
	/**
	 * @Title: findUserByPage
	 * @Description: 教学秘书分页查询教师（多个角色，查角色表）
	 * @param map
	 * @param page
	 * @return BasePage<Teacher> 
	 * @throws
	 */
	public List<Teacher> findUserByPage(@Param("map") Map<String,Object> map,
			@Param("page") BasePage<Teacher> page);
	
	/**
	 * @Title: rowsSizeOfFindUser
	 * @Description: 教学秘书多条件查询教师数量（多个角色，查角色表）
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int rowsSizeOfFindUser(@Param("map") Map<String,Object> map);
	
	/**
	 * @Title: findTeaAccountByPage
	 * @Description: 教学秘书分页查询教师账号(不查角色表)
	 * @param map
	 * @param page
	 * @return BasePage<Teacher> 
	 * @throws
	 */
	public List<Teacher> findTeaAccountByPage(@Param("map") Map<String,Object> map,
			@Param("page") BasePage<Teacher> page);
	
	/**
	 * @Title: rowsSizeOfFindTeaAccount
	 * @Description: 教学秘书多条件查询教师数量(不查角色表)
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int rowsSizeOfFindTeaAccount(@Param("map") Map<String,Object> map);
	/**
	 * @Title: findTeacherInfoHaveTitleSize
	 * @Description: 查询拥有教师课题的教师数量
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int findTeacherInfoHaveTitleSize(@Param("map")Map<String, Object> map);
	
	/**
	 * @Title: findTeacherInfoHaveTitle
	 * @Description: 查询拥有教师课题的教师信息
	 * @param map
	 * @param page
	 * @return List<Teacher> 
	 * @throws
	 */
	public List<Teacher> findTeacherInfoHaveTitle(@Param("map")Map<String, Object> map,
			@Param("page")BasePage<Teacher> page);
}