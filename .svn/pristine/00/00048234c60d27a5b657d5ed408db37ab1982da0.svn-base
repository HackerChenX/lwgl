package com.hlzt.power.service;

import java.util.List;
import java.util.Map;

import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.SubmitStatistics;

/**
 * 教学秘书过程信息统计
 * @author user
 *
 */
public interface SecretaryFlowInfoStatisticsSer {
	
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
	public List<SubmitStatistics> findTaskBookSubmit(String major, String className);
	
	
	/**
	 * 查询开题报告提交情况
	 * @param major
	 * @param className
	 * @return
	 */
	public List<SubmitStatistics> findOpeningReportSubmit(String major, String className);
	
	
	/**
	 * 查询中期检查表提交情况
	 * @param major
	 * @param className
	 * @return
	 */
	public List<SubmitStatistics> findMidCheckSubmit(String major, String className);
	
	/**
	 * 查询论文初稿提交情况
	 * @param major
	 * @param className
	 * @return
	 */
	public List<SubmitStatistics> findFirstPaperSubmit(String major, String className);
	
	
	/**
	 * 查询论文定稿提交情况
	 * @param major
	 * @param className
	 * @return
	 */
	public List<SubmitStatistics> findFinalPaperSubmit(String major, String className);
	
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
	public BasePage<Student> findChecking(String stageName, String leaderStatus, String major, String className, BasePage<Student> page);
	
	/**
	 * 查询当前阶段专业退回的学生
	 * @param stageName
	 * @param leaderStatus
	 * @param major
	 * @param className
	 * @param page
	 * @return
	 */
	public BasePage<Student> findNotPass(String stageName, String leaderStatus, String major, String className, BasePage<Student> page);
	
	/**
	 * 查询当前阶段审核通过的学生
	 * @param stageName
	 * @param leaderStatus
	 * @param major
	 * @param className
	 * @param page
	 * @return
	 */
	public BasePage<Student> findPass(String stageName, String leaderStatus, String major, String className, BasePage<Student> page);
	
	/**
	 * 查询答辩小组
	 * @param major
	 * @return
	 */
	public BasePage<DbGroup> findDbGroup(String major, BasePage<DbGroup> page);
	
	/**
	 * 根据答辩小组查询答辩组学生
	 * @param dbGroupId
	 * @param page
	 * @return
	 */
	public BasePage<Student> findDbGroupStu(String dbGroupId, BasePage<Student> page);
	
		
	
}
