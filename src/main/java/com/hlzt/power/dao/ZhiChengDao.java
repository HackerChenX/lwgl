package com.hlzt.power.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.ZhiCheng;

public interface ZhiChengDao extends BaseDao<ZhiCheng> {

	/**
	 * 根据ID删除
	 * @param list
	 * @return
	 */
	public int deleteByIds(@Param("list")List<String> list);
	
	/**
	 * 查询职称
	 * @return
	 */
	public List<ZhiCheng> selectZhiCheng();
	
	/**
	 * 根据专业名字查询专业
	 * @param majorName
	 * @return
	 */
	public ZhiCheng selectByName(@Param("zhiChengName")String zhiChengName);
	
	/**
	 * 根据专业id查询专业
	 * @param id
	 * @return
	 */
	public ZhiCheng selectZhiChengById(@Param("id")String id);

	/**
	 * @Title: selectZhiChengByName
	 * @Description: 查询职称是否已存在
	 * @param zhiChengName
	 * @return int 
	 * @throws
	 */
	public int selectZhiChengByName(@Param("zhiChengName")String zhiChengName);
		
	
}
