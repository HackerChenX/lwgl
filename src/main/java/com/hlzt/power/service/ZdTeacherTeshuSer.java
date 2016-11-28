package com.hlzt.power.service;

import java.util.List;
import java.util.Map;

import com.hlzt.power.model.ApplyDelay;


public interface ZdTeacherTeshuSer {	
	/**
	 * 学生延期申请
	 * @author gym
	 * @param teaId
	 * @return
	 * Student 
	 * @date 2016-9-10 下午3:59:43
	 */
	public List<ApplyDelay> findStuDelayReq(Map<String, Object> map);

	public int optStuDelay(List<String> list, String status, String teaIdea);
	
	/**
	 * @Title: findApplyDelayById
	 * @Description: 根据id查询延期申请
	 * @param id
	 * @return ApplyDelay 
	 * @throws
	 */
	public ApplyDelay findApplyDelayById(String id);
}
