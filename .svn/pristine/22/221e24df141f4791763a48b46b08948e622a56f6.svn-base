package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.DbGroup;

public interface DbGroupDao extends BaseDao<DbGroup> {

	/**
	 * 删除答辩小组
	 * 
	 * @param id
	 * @param major
	 * @return
	 */
	public int deleteDbGroup(@Param("id") String id,
			@Param("major") String major);

	/**
	 * 查询答辩小组
	 * 
	 * @param major
	 * @return
	 */
	public List<DbGroup> selectDbGroup(@Param("major") String major);

	/**
	 * 查询答辩小组
	 * 
	 * @param major
	 * @return
	 */
	public int selectDbGroupNum(@Param("major") String major);

	/*
	 * 查询答辩小组信息
	 * @param id
	 * @param major
	 * @return
	 */
	public DbGroup selectByDbGroupInfoById(@Param("id") String id,
			@Param("major") String major);

	/**
	 * 更新答辩小组 
	 * @param dbGroup
	 * @return
	 */
	public int updateDbGroup(@Param("dbGroup") DbGroup dbGroup);



	/**
	 * @Title: findDbGroupByStuId
	 * @Description: 学生查询答辩安排
	 * @param stuId
	 * @return DbGroup
	 * @throws
	 */
	public DbGroup findDbGroupByStuId(@Param("stuId") String stuId);

	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();

	/**
	 * 教师查看答辩组
	 * 
	 * @author gym
	 * @param teaId
	 * @return DbGroup 
	 */
	public List<DbGroup> findDbGroupByTeaId(String teaId);
	/**
	 * 查询自己是否是答辩秘书并返回Dbgroup对象
	 * 
	 * @author gym
	 * @param map.teaId
	 * @return int 
	 */
	public List<DbGroup> checkDbMishu(@Param("map") Map<String, Object> map);
}
