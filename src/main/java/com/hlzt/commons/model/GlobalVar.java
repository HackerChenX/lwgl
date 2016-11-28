package com.hlzt.commons.model;

import com.hlzt.commons.helper.SysConfig;

/**
 * 全局变量
 * @author Administrator
 *
 */
public class GlobalVar {
	
/**
 * 每页显示的记录条数
 */
 private static Integer pageSize=Integer.valueOf(SysConfig.getValue("page_size"));

public static Integer getPageSize() {
	return pageSize;
}

public static void setPageSize(Integer pageSize) {
	GlobalVar.pageSize = pageSize;
	SysConfig.updateProperties("page_size", String.valueOf(pageSize));
	SysConfig.save();
}
 
 

	
	
	
	
	
}
