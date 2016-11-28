package com.hlzt.power.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.BackLog;


public interface BackLogDao extends BaseDao<BackLog>{
	
	/**
	 * @Title: selectBackLogByUserId
	 * @Description: 根据用户ID查询代办事项
	 * @param userId
	 * @return List<BackLog> 
	 * @throws
	 */
	public List<BackLog> selectBackLog(@Param("userId")String userId,@Param("major")String major,@Param("role")String role);
	
	/**
	 * @Title: selectBackLogOfDelay
	 * @Description: 根据类型查询待办事项
	 * @param stuId
	 * @param teaId
	 * @param backLogInfo
	 * @return List<BackLog> 
	 * @throws
	 */
	public List<BackLog> selectBackLogOfDelay(@Param("stuId")String stuId,@Param("teaId")String teaId,@Param("backLogInfo")String backLogInfo);

	/**
	 * @Title: updateBackLogNumById
	 * @Description: 根据ID改变待办事项次数
	 * @return int 
	 * @throws
	 */
	public int updateBackLogNumById(@Param("id")String id,@Param("status")String status);
	
	/**
	 * @Title: findBackLogByType
	 * @Description: 根据类型查询
	 * @param status
	 * @return List<BackLog> 
	 * @throws
	 */
	public List<BackLog> findBackLogByType(@Param("status")String status);
	
	/**
	 * @Title: deleteAll
	 * @Description: 删除数据
	 * @return int 
	 * @throws
	 */
	public int deleteAll();

}