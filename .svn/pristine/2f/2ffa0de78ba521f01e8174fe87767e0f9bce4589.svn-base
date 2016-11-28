package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.ClassName;
public interface ClassNameDao extends BaseDao<ClassName>{
	
	/**
	 * 根据专业查询班级
	 * @param major
	 * @return
	 */
	public List<ClassName> selectClass(@Param("map")Map<String, Object> map);
	
	/**
	 * 根据ID删除
	 * @param list
	 * @return
	 */
	public int deleteByIds(@Param("list")List<String> list);
	
	
	/**
	 * 根据名字查询班级数量
	 * @param cName
	 * @return
	 */
	public int findClassNum(@Param("cName")String cName);
	
	/**
	 * 根据名字查询班级
	 * @param cName
	 * @return
	 */
	public ClassName findClassByName(@Param("cName")String cName);
	
}
