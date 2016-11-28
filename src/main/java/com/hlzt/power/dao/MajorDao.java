package com.hlzt.power.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.Major;

public interface MajorDao extends BaseDao<Major>{
	
	/**
	 * 查询专业
	 * @return
	 */
	public List<Major> selectMajor();
	
	/**
	 * 删除专业
	 * @param list
	 * @return
	 */
	public int deleteByIds(@Param("list")List<String> list);
	
	/**
	 * 根据专业名字查询专业数量
	 * @param majorName
	 * @return
	 */
	public int selectMajorByName(@Param("majorName")String majorName);
	
	/**
	 * 根据专业名字查询专业
	 * @param majorName
	 * @return
	 */
	public Major selectByName(@Param("majorName")String majorName);
	
	/**
	 * 根据专业id查询专业
	 * @param id
	 * @return
	 */
	public Major selectMajorById(@Param("id")String id);
	
	
	
	
	
	
	
}
