package com.hlzt.power.service;

import java.util.List;

import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ApplyDelay;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.StuStageFile;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.TeacherTitle;

/**
 * 专业负责人流程管理
 * @author user
 *
 */
public interface MajorLeaderFlowManageSer {
	
	/**
	 * 根据条件查询所负责专业的教师
	 * @param major
	 * @param teaNum
	 * @param teaName
	 * @param zhichen
	 * @return
	 */
	public List<Teacher> findTeacherByCondition(String major, String teaNum, String teaName, String zhicheng);
	
	/**
	 * 查询副高职称教师
	 * @param major
	 * @return
	 */
	public List<Teacher> findSuperTeacher(String major, String groupId);
	
	/**
	 * 查询副普通教师
	 * @param major
	 * @return
	 */
	public List<Teacher> findGeneralTeacher(String major, String groupId);
	
	/**
	 * 给所负责教师统一设置所带学生数量
	 * @param num
	 * @return
	 */
	public int setAllTeaStuNum(int num, String major);
	
	/**
	 * 批量给所负责教师设置带学生的数量
	 * @param teaIds
	 * @param num
	 * @return
	 */
	public int setTeacherManageStuNum(List<String> list, int num, String major);
	
	/**
	 * 审核任务书
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int shStuTaskBook(String[] stuIds, String status);
	
	/**
	 * 查询所负责专业教师发布的课题
	 * @param teaName
	 * @param status
	 * @return
	 */
	public BasePage<TeacherTitle> findTeaTitle(String major, String teaName, String status, BasePage<TeacherTitle> page);
	
	/**
	 * 根据ID查询教师发布的课题
	 * @param id
	 * @param major
	 * @return
	 */
	public TeacherTitle findTeaTitleById(String id, String major);
	
	/**
	 * 审核所负责专业教师发布的课题
	 * @param list
	 * @param status
	 * @return
	 */
	public int checkTeaTitle(List<String> list, String status, String leaderIdea);
	
	/**
	 * 专业负责人查询学生提交的任务书
	 * @param stuNum
	 * @param StuName
	 * @param titleName
	 * @param status
	 * @param major
	 * @return
	 */
	public BasePage<StuStageFile> findTaskBook(String stuNum, String stuName, 
			String titleName, String status, String major, BasePage<StuStageFile> page);
	
	/**
	 * 专业负责人审核本专业任务书
	 * @param list
	 * @param major
	 * @return
	 */
	public int checkTaskBook(List<String> list, String major, String status);
	
	/**
	 * 专业负责人查询学生提交的任务书
	 * @param stuNum
	 * @param StuName
	 * @param titleName
	 * @param status
	 * @param major
	 * @return
	 */
	public BasePage<StuStageFile> findOpneingReport(String stuNum, String stuName, 
			String titleName, String status, String major, BasePage<StuStageFile> page);
	
	/**
	 * 专业负责人审核本专业任务书
	 * @param list
	 * @param major
	 * @return
	 */
	public int checkOpeningReport(List<String> list, String major, String status);
	
	/**
	 * 查询答辩小组
	 * @param groupName
	 * @param groupLeaderName
	 * @return
	 */
	public List<DbGroup> findDbGroup(String major);
	
	/**
	 * 增加答辩小组
	 * @param dbGroup
	 * @return
	 */
	public int addDbGroup(DbGroup dbGroup);
	
	/**
	 * 删除答辩小组
	 * @param id
	 * @param major
	 * @return
	 */
	public int deleteDbGroup(String id, String major);
	
	/**
	 * 查询答辩小组信息
	 * @param id
	 * @param major
	 * @return
	 */
	public DbGroup findDbGroupInfoById(String id, String major);
	
	/**
	 * 查询申请课题
	 * @param major
	 * @param stuNum
	 * @param stuName
	 * @param zdTeaName
	 * @param status
	 * @return
	 */
	public BasePage<ApplyTitle> findApplyTitle(String major, String stuNum, 
			String stuName, String zdTeaName, String status, BasePage<ApplyTitle> page);
	
	/**
	 * 审核申请课题
	 * @param list
	 * @param status
	 * @param major
	 * @return
	 */
	public int checkApplyTitle(List<String> list, String status, String major);
	
	/**
	 * 查询评阅学生
	 * @param teaId
	 * @param major
	 * @param page
	 * @return
	 */
	public BasePage<Student> findAllPyStu(String teaId, String major, BasePage<Student> page);
	
	/**
	 * 根据评阅教师查询学生
	 * @param pyTeaId
	 * @param major
	 * @return
	 */
	public List<Student> findStuByPyTea(String pyTeaId, String major);
	
	/**
	 * 给老师设置评阅学生
	 * @param list
	 * @param teaId
	 * @param major
	 * @return
	 */
	public int setPyStu(List<String> list, String teaId, String major);
	
	/**
	 * 给教师设置答辩小组
	 * @param list
	 * @param dbGroupId
	 * @return
	 */
	public int setDbGroupForTea(List<String> list, String dbGroupId);
	
	/**
	 * 根据小组id查询教师
	 * @param groupId
	 * @return
	 */
	public List<Teacher> findTeaByGroupId(String groupId, String major);
	
	/**
	 * 查询教师根据一组教师id
	 * @param list
	 * @return
	 */
	public List<Teacher> findTeaByUserIdList(List<String> list);
	
	/**
	 * 查询已分配答辩小组的学生
	 * @param dbGroup
	 * @param major
	 * @return
	 */
	public List<Student> findStuByDbGroup(String dbGroup, String major);
	
	/**
	 * 查询未分配答辩小组的学生
	 * @param dbGroup
	 * @param major
	 * @return
	 */
	public BasePage<Student> findNotDbGroup(String major, BasePage<Student> page);
	
	/**
	 * 给学生设置答辩小组
	 * @param list
	 * @param dbGroupId
	 * @return
	 */
	public int setDbGroupForStu(List<String> list, String dbGroupId);
	
	/**
	 * 根据用户id查询教师
	 * @param userId
	 * @return
	 */
	public Teacher findTeaByUserId(String userId);
	
	/**
	 * 更改答辩小组
	 * @param dbGroup
	 * @return
	 */
	public int updateDbGroup(DbGroup dbGroup);
	
	/**
	 * 根据id查询学生课题
	 * @param id
	 * @param major
	 * @return
	 */
	public ApplyTitle findStuTitleById(String id, String major);
	
	/**
	 * 查询全部副高职称的教师
	 * @param major
	 * @return
	 */
	public List<Teacher> findAllSuperTea(String major);
	
	/**
	 * @Title: findTaskBookById
	 * @Description: 根据id查询任务书
	 * @param id
	 * @return TaskBook 
	 * @throws
	 */
	public TaskBook findTaskBookById(String id);

	/**
	 * @Title: findOpeningReportById
	 * @Description: 根据id查询开题报告
	 * @param id
	 * @return OpeningReport 
	 * @throws
	 */
	public OpeningReport findOpeningReportById(String id);
	
}
