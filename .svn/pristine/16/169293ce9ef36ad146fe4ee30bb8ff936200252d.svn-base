package com.hlzt.power.service;

import java.util.List;
import java.util.Map;

import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.Student;

public interface ZdTeacherGradeSer {

	public	List<Grade> findGrade(Map<String, Object> map);
	
	
	
	public List<DbGroup> checkDbMishu(Map<String, Object> map);
	
	
	
	
	public List<Student> findStuByDbKey(Map<String, Object> map);
	
	/**
	 * //通过学生userId获取他的成绩
	 * @author gym
	 * @param stuId
	 * @return
	 * Grade 
	 * @date 2016-9-12 上午10:53:31
	 */
	
	public Grade finStuGradeByStuId(String stuId);
	
	
	public BasePage<Grade> findPyGrade(Map<String, Object> map, BasePage<Grade> page);
	
	public int setPyGrade(float pyGrade, String stuNum);
	
	public BasePage<Grade> findSyGrade(Map<String, Object> map, BasePage<Grade> page);
	
	public int setSyGrade(float syGrade, String stuNum);

	public BasePage<Grade> findDbGrade(Map<String, Object> map, BasePage<Grade> page);
	
	public int setDbGrade(float dbGrade, String stuNum);

	
	
}
