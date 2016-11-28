package com.hlzt.commons.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.helper.UuidHelper;
import com.hlzt.commons.model.BaseBean;
import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.model.GlobalFinal;
import com.mysql.fabric.xmlrpc.base.Array;


public abstract   class BaseServiceImpl<T> implements BaseService<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int removeById(String id) {
		// TODO Auto-generated method stub
		return getDao().deleteById(id);
	}
	

	public void removeBySomeId(Map map, String sql, List list) {
		if(null!=list&&list.size()>0)
		  getDao().deleteBySomeId(map, sql, list);
	}
	
	public int add(T t) {

		((BaseBean)t).setId(UuidHelper.getRandomUUID());
		((BaseBean)t).setCreateTime(new Date()); 
		return getDao().insert(t);
	}

	public T findById(String id) {
		// TODO Auto-generated method stub
		return getDao().selectById(id);
	}

	public List<T> list(Map<String,Object> map, String sql) {
		// TODO Auto-generated method stub
		System.out.println("##################");
		return getDao().list(map, sql);
	}

	public int modifyById(T t) {
		// TODO Auto-generated method stub
		return getDao().updateById(t);
	}

	public BasePage<T> findPage(Map<String,Object>  map, BasePage<T> page, String sql) {

		int totalRecord = getDao().rowsSize(map, sql);
		page.setTotalRecord(totalRecord);
		page.setResults(getDao().findPage(map, page, sql));
		return page;
	}

	public List<T> listBySomeId(Map<String,Object> map, String sqlData, List <String>list) {
		// TODO Auto-generated method stub
		if(list.size()<=0)
			return new ArrayList<T>();
		return getDao().listBySomeId(map, sqlData, list);
	}

	public int rowsSize(Map<String,Object> map, String sql) {
		return getDao().rowsSize(map, sql);
	}

	

	
	
	protected abstract BaseDao<T> getDao();

}
