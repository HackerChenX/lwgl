package com.hlzt.commons.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.model.BasePage;


public interface BaseDao<T> {
	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(@Param("id")String id);
	
	/**
	 * 根据一些id删除
	 * @param map
	 * @param sqlData
	 * @param idList
	 */
	public void deleteBySomeId(@Param("map")Map map,@Param("sqlData") String sqlData, @Param("list")List list);

	/**
	 * 添加
	 * 
	 * @param t
	 * @return
	 */
	public int insert(T t);

	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public T selectById(@Param("id")String id);
	

	/**
	 * 查询 不分页
	 * 
	 * @param map
	 *            参数map
	 * @param sqlData
	 *            sql语句
	 * @return
	 */
	public List<T> list(@Param("map") Map<String,Object> map, @Param("sqlData") String sqlData);

	/**
	 * 更新
	 * 
	 * @param t
	 * @return
	 */
	public int updateById(T t);

	/**
	 * 分页查找
	 * 
	 * @param map
	 * @param page
	 * @param sqlData
	 * @return
	 */
	public List<T> findPage(@Param("map") Map<String,Object> map,
			@Param("page") BasePage<T> page, @Param("sqlData") String sqlData);

	/**
	 * 总条数
	 * 
	 * @param map
	 * @param sqlData
	 * @return
	 */
	public int rowsSize(@Param("map") Map<String,Object> map, @Param("sqlData") String sqlData);

	/**
	 * 用于in查询
	 * 
	 * @param map
	 * @param sqlData
	 * @param list
	 * @return
	 */
	public List<T> listBySomeId(@Param("map") Map<String,Object> map,
			@Param("sqlData") String sqlData, @Param("list") List<String> list);
}


