package com.hlzt.commons.service;

import java.util.List;
import java.util.Map;

import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.model.GlobalFinal;

/**
 * 
 * @author Administrator
 * 
 *         基础服务类
 * 
 * @param <T>
 */
public interface BaseService<T> extends GlobalFinal {
	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	public int removeById(String id);
	
	/**
	 * 根据一些id删除
	 * @param map
	 * @param sql
	 * @param idList
	 */
	public void removeBySomeId(Map map, String sql, List idList);

	/**
	 * 添加
	 * 
	 * @param t
	 * @return
	 */
	public int add(T t);

	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public T findById(String id);
	

	/**
	 * 根据 sql 获得参数map查找 不分页
	 * 
	 * @param map
	 * @param sql
	 * @return
	 */
	public List<T> list(Map<String,Object> map, String sql);

	/**
	 * 修改
	 * 
	 * @param t
	 * @return
	 */
	public int modifyById(T t);

	/**
	 * 分页查找
	 * 
	 * @param map
	 *            参数
	 * @param page
	 *            分页对象
	 * @param sql
	 *            sql查找
	 * @return
	 */
	public BasePage<T> findPage(Map<String,Object> map, BasePage<T> page, String sql);

	/**
	 * 用于 in 查询
	 * 
	 * @param map
	 * @param sqlData
	 * @param list
	 * @return
	 */
	public List<T> listBySomeId(Map <String,Object>map, String sqlData, List<String> list);

	/**
	 * 根据条件查询有几条记录
	 * 
	 * @param map
	 * @param sql
	 * @return
	 */
	public int rowsSize(Map<String,Object> map, String sql);

}
