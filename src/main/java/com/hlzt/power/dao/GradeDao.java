package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.Grade;

public interface GradeDao extends BaseDao<Grade>{
	/**
	 * 查询所有有效的学生数
	 * @return
	 */
	public int findAllnum(@Param("major")String major);
   
	/**
	 * 查询>学生数量
	 * @return
	 */
	public int findExcellentNum(@Param("major")String major);
	
	/**
	 * 查询80-89学生数
	 * @return
	 */
	public int findWellNum(@Param("major")String major);
	
	/**
	 * 查询70-79学生数
	 * @return
	 */
	public int findMediumNum(@Param("major")String major);
	
	/**
	 * 查询60-69学生数
	 * @return
	 */
	public int  findPassNum(@Param("major")String major);
	
	/**
	 * 查询<60学生数
	 * @return
	 */
	public int findNotPassNum(@Param("major")String major);
	
	/**
	 * 更改论文推荐状态
	 * @param list
	 * @param status
	 * @return
	 */
	public int updateRecommendYxPaper(@Param("list")List<String> list, @Param("status")String status);
	
	/**
	 * 根据学生ID更新
	 * @param stuId
	 * @return
	 */
	public int updateByStuId(@Param("grade")Grade grade);
	
	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();
	
	/**
	 * 
	 * @author gym
	 * @param map
	 * @param page
	 * @return
	 * BasePage 
	 */
	public List<Grade> findStageGrade(@Param("map")Map<String, Object> map);
	
	/**
	 * 根据学生ID查询
	 * @param stuId
	 * @return
	 */
	public int selectByStuId(@Param("stuId")String stuId);
	
	public Grade selectGradeByStuNum(@Param("stuNum")String stuNum);
	
	
	public List<Grade> findPyGrade(@Param("map")Map<String, Object> map, @Param("page")BasePage<Grade> page);
	
	public int findPyGradeNum(@Param("map")Map<String, Object> map);
	
	public List<Grade> findSyGrade(@Param("map")Map<String, Object> map, @Param("page")BasePage<Grade> page);
	
	public int findSyGradeNum(@Param("map")Map<String, Object> map);

	public List<Grade> findDbGrade(@Param("map")Map<String, Object> map, @Param("page")BasePage<Grade> page);
	
	public int findDbGradeNum(@Param("map")Map<String, Object> map);
	
	
	
}