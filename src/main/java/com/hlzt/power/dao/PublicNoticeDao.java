package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.power.model.PublicNotice;


public interface PublicNoticeDao extends BaseDao<PublicNotice>{
    
	/**
	 * @Title: updateNoticeById
	 * @Description: 根据ID更新公告
	 * @param id
	 * @return int 
	 * @throws
	 */
    public int updateNoticeById(@Param("top")String top,@Param("id")String id);
    
    /**
     * 查询公告列表
     * @param map
     * @return
     */
    public List<PublicNotice> selectNotice(@Param("map")Map<String, Object> map);

    /**
     * @Title: deleteAll
     * @Description: 删除表中数据
     * @return int 
     * @throws
     */
	public int deleteAll();
    
}