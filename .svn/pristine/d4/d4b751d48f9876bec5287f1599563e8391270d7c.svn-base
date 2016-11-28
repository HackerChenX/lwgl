package com.hlzt.power.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.GradeWeight;

public interface GradeWeightDao extends BaseDao<GradeWeight>{
    
	/**
	 * 查询权重评分
	 * @return
	 */
	public List<GradeWeight> findGradeWeight();
	
	/**
	 * 删除
	 * @param major
	 * @return
	 */
	public int deleteGradeWeight(@Param("majorName")String majorName);
	
	/**
	 * 全部设置评分权重
	 * @param zdPingfen
	 * @param pyPingfen
	 * @param dbPingfen
	 * @return
	 */
	public int setGradeWeight(@Param("list")List<String> list, @Param("zdPingfen")float zdPingfen, 
			@Param("pyPingfen")float pyPingfen, @Param("dbPingfen")float dbPingfen);
	
	/**
	 * 取消评分权重设置
	 * @return
	 */
	public int cancelSetGradeWeight(@Param("list")List<String> list);
	
	/**
	 * @Title: findGradeWeightByMajor
	 * @Description: 根据专业查询评分权重
	 * @param major
	 * @return GradeWeight 
	 * @throws
	 */
	public GradeWeight findGradeWeightByMajor(@Param("major")String major);
}