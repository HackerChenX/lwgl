package com.hlzt.power.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ApplyDelay;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.FirstPaper;
import com.hlzt.power.model.GradeAll;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.MidCheck;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.model.PublicNotice;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.StuGraCollect;
import com.hlzt.power.model.TeacherTitle;

/**
 * 教学秘书流程管理
 * @author user
 *
 */
public interface SecretaryFlowManageSer {
	
	/**
	 * 多条件成绩汇总查询（分页）
	 * @param map
	 * @param page
	 * @return
	 */
	public List<StuGraCollect> findStuGraColllectByCondition(Map<String,Object>  map, BasePage<StuGraCollect> page);
	
	/**
	 * 成绩总评
	 * @param map
	 * @param page
	 * @return
	 */
	public List<GradeAll> findGradeAllEvaluate();
	
	/**
	 * 多条件查询教师发布的课题
	 * @param major
	 * @param teaName
	 * @param status
	 * @param page
	 * @return
	 */
	public BasePage<TeacherTitle> findTeacherTitle(String major, String teaName, 
			String status, BasePage<TeacherTitle> page);
	
	/**
	 * 根据ID查询教师发布的课题
	 * @param teacherTitleId
	 * @return
	 */
	public TeacherTitle findeTeacherTitleById(String teacherTitleId);
	
	/**
	 * 审核教师发布的课题
	 * @param ids
	 * @param status
	 * @return
	 */
	public int checkTeacherTitle(List<String> idList, String status, String managerIdea);
	
	/**
	 * 成绩汇总查询
	 * @param major
	 * @param className
	 * @param zdTeaName
	 * @param stuName
	 * @param stuNum
	 * @return
	 */
	public BasePage<Grade> findAllGrade(String major, String className, String zdTeaName, 
			String stuName, String stuNum, int grade, BasePage<Grade> page);
	
	/**
	 * 查询教师
	 * @param teaNum
	 * @param teaName
	 * @param teaZhicheng
	 * @param page
	 * @return
	 */
	public BasePage<Teacher> findTeacher(String teaNum, String teaName, String teaZhicheng, BasePage<Teacher> page);
	
	
	/**
	 * 查询论文信息
	 * @param major
	 * @param className
	 * @param endNum
	 * @return
	 */
	public BasePage<Paper> findStuPaperInfo(String major, String className, String endNum, BasePage<Paper> page);
	
	/**
	 * 推荐（取消）优秀论文
	 * @param list
	 * @param status
	 * @return
	 */
	public int recommendYxPaper(List<String> list, String status);
	
	/**
	 * 查询学生自拟课题
	 * @param stuNum
	 * @param stuName
	 * @param teaName
	 * @param status
	 * @param page
	 * @return
	 */
	public BasePage<ApplyTitle> findApplyTitle(String major, String stuName, 
			String teaName, String status, BasePage<ApplyTitle> page);
	
	/**
	 * 审核学生自拟课题
	 * @param idList
	 * @param status
	 * @param managerIdea
	 * @return
	 */
	public int checkApplyTitle(List<String> idList, String status, String managerIdea);
	
	/**
	 * 根据学生ID查询学生自拟课题
	 * @param stuId
	 * @return
	 */
	public ApplyTitle findeApplyTitleById(String id);
	
	/**
	 * 查询特殊情况处理
	 * @param major
	 * @param stuNum
	 * @param stuName
	 * @param zdTeaName
	 * @param states
	 * @return
	 */
	public BasePage<ApplyDelay> findApplyDelay(String stuNum, String stuName, 
			String zdTeaName, String status, BasePage<ApplyDelay> page);
	/**
	 * @Title: insertNotice
	 * @Description: 添加公告
	 * @param publicNotice
	 * @return int 
	 * @throws
	 */
	public int insertNotice(PublicNotice publicNotice);
	
	/**
	 * 根据ID查询课题申请
	 * @param id
	 * @return
	 */
	public ApplyTitle findApplyTitleByStuId(String stuId);
	
	/**
	 * 增加一条课题申请
	 * @param applyTitle
	 * @return
	 */
	public int addApplyTitle(ApplyTitle applyTitle);
	
	/**
	 * 给学生设置课题申请时间
	 * @param limitTime
	 * @return
	 */
	public int setApplyTitleLimitTime(Date limitTime, String stuId);
	
	/**
	 * 根据ID查询任务书
	 * @param id
	 * @return
	 */
	public TaskBook findTaskBookByStuId(String stuId);
	
	/**
	 * 增加一条任务书
	 * @param applyTitle
	 * @return
	 */
	public int addTaskBook(TaskBook taskBook);
	
	/**
	 * 给学生设置提交任务书时间
	 * @param limitTime
	 * @return
	 */
	public int setTaskBookLimitTime(Date limitTime, String stuId);
	
	/**
	 * 根据ID查询开题报告
	 * @param id
	 * @return
	 */
	public OpeningReport findOpeningReportByStuId(String stuId);
	
	/**
	 * 增加一条任务书
	 * @param applyTitle
	 * @return
	 */
	public int addOpeningReport(OpeningReport openingReport);
	
	/**
	 * 给学生设置提交任务书时间
	 * @param limitTime
	 * @return
	 */
	public int setOpeningReportLimitTime(Date limitTime, String stuId);
	
	/**
	 * 根据ID查询中期检查
	 * @param id
	 * @return
	 */
	public MidCheck findMidCheckByStuId(String stuId);
	
	/**
	 * 增加一条中期检查
	 * @param applyTitle
	 * @return
	 */
	public int addMidCheck(MidCheck midCheck);
	
	/**
	 * 给学生设置提交中期检查时间
	 * @param limitTime
	 * @return
	 */
	public int setMidCheckLimitTime(Date limitTime, String stuId);
	
	/**
	 * 根据ID查询初稿
	 * @param id
	 * @return
	 */
	public FirstPaper findFirstPaperByStuId(String stuId);
	
	/**
	 * 增加一条论文初稿
	 * @param applyTitle
	 * @return
	 */
	public int addFirstPaper(FirstPaper firstPaper);
	
	/**
	 * 给学生设置提交论文初稿时间
	 * @param limitTime
	 * @return
	 */
	public int setFirstPaperLimitTime(Date limitTime, String stuId);
	
	/**
	 * 根据ID查询定稿
	 * @param id
	 * @return
	 */
	public FinalPaper findFinalPaperByStuId(String stuId);
	
	/**
	 * 增加一条论文定稿
	 * @param applyTitle
	 * @return
	 */
	public int addFinalPaper(FinalPaper finalPaper);
	
	/**
	 * 给学生设置提交论文定稿时间
	 * @param limitTime
	 * @return
	 */
	public int setFinalPaperLimitTime(Date limitTime, String stuId);
	
	/**
	 * 根据ID查询延期申请
	 * @param id
	 * @return
	 */
	public ApplyDelay findApplyDelayById(String id);
	
	/**
	 * 处理特殊情况
	 * @param list
	 * @param status
	 * @return
	 */
	public int setApplyDelay(List<String> list, String idea, String status);
	
	
	/**
	 * @Title: findPublicNotice
	 * @Description: 查询公告列表
	 * @return BasePage<PublicNotice> 
	 * @throws
	 */
	public BasePage<PublicNotice> findPublicNotice(Map<String,Object>  map,BasePage<PublicNotice> page);
	
	/**
	 * @Title: deleteNoticeById
	 * @Description: 删除公告
	 * @param id
	 * @return int 
	 * @throws
	 */
	public int deleteNoticeById(String id);
	
	/**
	 * @Title: updateNoticeById
	 * @Description: 根据id更新公告状态
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int updateNoticeById(String top,String id);
	
	/**
	 * @Title: findNoticeById
	 * @Description: 根据ID查找公告
	 * @param id
	 * @return PublicNotice 
	 * @throws
	 */
	public PublicNotice findNoticeById(String id);
	
	/**
	 * @Title: findApplyDelayInit
	 * @Description: 初始化查询延期申请，没有连表查询，设置初始化查询主要是为了没有申请指导老师的特殊情况
	 * @return BasePage<ApplyDelay> 
	 * @throws
	 */
	public BasePage<ApplyDelay> findApplyDelayInit();
}

   