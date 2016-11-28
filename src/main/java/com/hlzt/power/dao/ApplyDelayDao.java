package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ApplyDelay;

public interface ApplyDelayDao extends BaseDao<ApplyDelay>{
     
	/**
	 * @Title: findApplyDelayByStuId
	 * @Description: 学生查询所有延期申请
	 * @param stuId
	 * @return List<ApplyDelay> 
	 * @throws
	 */
	public List<ApplyDelay> findApplyDelayByStuId(@Param("stuId")String stuId);
	/**
	 * 通过教师usrId查看学生延期申请
	 * @author gym
	 * @param map
	 * @return
	 * List<ApplyDelay> 
	 */
	public List<ApplyDelay> findByTeaId(@Param("map")Map<String, Object> map);
	
	/**
	 * 教学秘书查询延期申请
	 * @param map
	 * @return
	 */
	public List<ApplyDelay> findApplyDelay(@Param("map")Map<String, Object> map, @Param("page")BasePage<ApplyDelay> page);
	
	/**
	 * 教学秘书查询延期申请数量
	 * @param map
	 * @return
	 */
	public int findApplyDelayNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 教学秘书处理延期申请
	 * @param list
	 * @param status
	 * @return
	 */
	public int updateManagerStatus(@Param("list")List<String> list, 
			@Param("idea")String idea, @Param("status")String status);
	
	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();
	
	public int zdTeacherOptStuDelay(@Param("list")List<String> list, 
			@Param("status")String status, @Param("teaIdea")String teaIdea);
	/**
	 * @Title: findApplyDelayNumInit
	 * @Description: 初始化查询延期申请数量(没有连表查询)
	 * @return int 
	 * @throws
	 */
	public int findApplyDelayNumInit();
	
	/**
	 * @Title: findApplyDelayInit
	 * @Description: 初始化查询延期申请（没有连表查询，设置初始化查询主要是为了没有申请指导老师的特殊情况）
	 * @param page
	 * @return List<ApplyDelay> 
	 * @throws
	 */
	public List<ApplyDelay> findApplyDelayInit(@Param("page")BasePage<ApplyDelay> page);
	
}