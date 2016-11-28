package com.hlzt.power.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.TitleForm;

/**
 * @ClassName: TitleFormDao
 * @Description: 完成形式
 * @author cxy
 *
 */
public interface TitleFormDao extends BaseDao<TitleForm>{
	
	/**
	 * @Title: selectForm
	 * @Description: 查询课题完成形式
	 * @param major
	 * @return TitleForm 
	 * @throws
	 */
	public List<TitleForm> selectForm();
	
	/**
	 * 删除
	 * @param list
	 * @return
	 */
	public int deleteFrom(List<String> list);
	

	
}
