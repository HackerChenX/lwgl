package com.hlzt.power.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.StagePlan;


public interface StagePlanDao extends BaseDao<StagePlan>{
	
	/**
	 * 查询全部阶段时间安排
	 * @return
	 */
	public List<StagePlan> findStagePlan();
	
    /**
     * 根据阶段名称查询阶段时间安排
     * @param stageName
     * @return
     */
	public StagePlan findStagePlanByStageName(@Param("stageName")String stageName);
	
	/**
	 * 更新阶段时间
	 * @param map
	 * @return
	 */
	public int updateStagePlan(@Param("stageName")String stageName, @Param("startTime")Date startTime, 
			@Param("endTime")Date endTime);

	/**
	 * @Title: insertByName
	 * @Description: 插入阶段时间
	 * @param stageName
	 * @param startTime
	 * @param endTime
	 * @return int 
	 * @throws
	 */
	public int insertByName(@Param("stageName")String stageName, @Param("startTime")Date startTime, 
			@Param("endTime")Date endTime);
}