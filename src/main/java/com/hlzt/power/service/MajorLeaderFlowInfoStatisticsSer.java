package com.hlzt.power.service;

import java.util.List;

import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.SubmitStatistics;

/**
 * 专业负责人过程信息统计
 * @author Administrator
 *
 */
public interface MajorLeaderFlowInfoStatisticsSer {
	
	/**
	 * 多条件查询毕设学生信息
	 * @param map
	 * @param page
	 * @return
	 */
	public BasePage<Student> findStudentByCondition(String major, String className, String zdTeaName, 
			String stuName, String stuNum, BasePage<Student> page);
	
	/**
	 * 查询任务书提交情况
	 * @param major
	 * @param className
	 * @return
	 */
	public List<SubmitStatistics> findTaskBookSubmit(String major, String className, String zdTeaName);
	
	
	/**
	 * 查询开题报告提交情况
	 * @param major
	 * @param className
	 * @return
	 */
	public List<SubmitStatistics> findOpeningReportSubmit(String major, String className, String zdTeaName);
	
	/**
	 * 查询当前阶段未提交学生
	 * @param stageName
	 * @param major
	 * @param className
	 * @return
	 */
	public BasePage<Student> findNotSubmit(String stageName, String major, String className, BasePage<Student> page);
	
	/**
	 * 查询当前阶段专业审核中的学生
	 * @param stageName
	 * @param leaderStatus
	 * @param major
	 * @param className
	 * @param page
	 * @return
	 */
	public BasePage<Student> findChecking(String stageName, String zdTeaStatus, String major, String className, BasePage<Student> page);
	
	/**
	 * 查询当前阶段专业退回的学生
	 * @param stageName
	 * @param leaderStatus
	 * @param major
	 * @param className
	 * @param page
	 * @return
	 */
	public BasePage<Student> findNotPass(String stageName, String zdTeaStatus, String major, String className, BasePage<Student> page);
	
	/**
	 * 查询当前阶段审核通过的学生
	 * @param stageName
	 * @param leaderStatus
	 * @param major
	 * @param className
	 * @param page
	 * @return
	 */
	public BasePage<Student> findPass(String stageName, String zdTeaStatus, String major, String className, BasePage<Student> page);
	
	/**
	 * 学生成绩汇总
	 * @param className
	 * @param zdTeaName
	 * @param stuName
	 * @param stuNum
	 * @param major
	 * @return
	 */
	public BasePage<Grade> findAllGrade(String className, String zdTeaName, String stuName, String stuNum, String major, BasePage<Grade> page);
	
	
	
}
