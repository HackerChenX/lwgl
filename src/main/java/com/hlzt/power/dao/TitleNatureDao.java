package com.hlzt.power.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.TitleNature;

public interface TitleNatureDao extends BaseDao<TitleNature>{
	/**
	 * 根据ID删除
	 * @param list
	 * @return
	 */
	public int deleteByIds(List<String> list);
	
	/**
	 * @Title: selectNature
	 * @Description: 查询课题性质
	 * @param major
	 * @return TitleNature 
	 * @throws
	 */
	public List<TitleNature> selectNature();
	
	
}
