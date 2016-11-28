package com.hlzt.power.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.power.dao.ApplyDelayDao;
import com.hlzt.power.dao.StudentDao;
import com.hlzt.power.model.ApplyDelay;

import com.hlzt.power.model.Student;
import com.hlzt.power.service.ZdTeacherTeshuSer;

@Transactional
@Component
public class ZdTeacherTeshuSerImpl implements ZdTeacherTeshuSer {
	@Resource
	private ApplyDelayDao applyDelayDao;
	@Resource
	private StudentDao studentDao;
	
	@Override
	public List<ApplyDelay> findStuDelayReq(Map<String, Object> map) {
		List<ApplyDelay> applyDelays = applyDelayDao.findByTeaId(map);//通过map.教师userId获取属于他的学生的延期申请
		
		return applyDelays;
	}

	@Override
	public int optStuDelay(List<String> list, String status, String teaIdea) {
		int i = applyDelayDao.zdTeacherOptStuDelay(list,status, teaIdea);
		return i; 
	}

	@Override
	public ApplyDelay findApplyDelayById(String id) {
		ApplyDelay applyDelay = applyDelayDao.selectById(id); 
		return applyDelay;
	}
	
}
