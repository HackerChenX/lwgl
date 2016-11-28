package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ReplyNote;
import com.hlzt.power.model.StuStageFile;


public interface ReplyNoteDao extends BaseDao<ReplyNote>{
    
	
	/**
	 * 分页查找
	 * 
	 * @param map
	 * @param page
	 * @param sqlData
	 * @return
	 */
	public List<StuStageFile> findByMap(@Param("map") Map<String,Object> map,
			@Param("page") BasePage<StuStageFile> page, @Param("sqlData") String sqlData);
	
	
	/**
	 * 更改指导老师审核状态
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("list")List<String> list, @Param("status")String status);
	
	
	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();
	
	
}