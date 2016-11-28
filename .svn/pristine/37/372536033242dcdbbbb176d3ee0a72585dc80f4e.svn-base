package com.hlzt.power.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.model.BasePage;

import com.hlzt.power.model.ApplyDelay;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.FirstPaper;
import com.hlzt.power.model.MidCheck;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.StuStageFile;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.TeacherTitle;
import com.hlzt.power.model.TitleForm;
import com.hlzt.power.model.TitleNature;

public interface ZdTeacherFlowManageSer {
	/**
	 * 指导老师审核学生申报的课题
	 * ——审核课题申报
	 * @param zdTeacher
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @param page
	 * @return
	 */
	
	public BasePage<Student> checkStudentReqPage(Map<String, Object> map,
			BasePage<Student> page);
	
	/**
	 * 指导老师查询自己的学生
	 * ——查看学生信息
	 * @author gym
	 * @param map
	 * @param page
	 * @return
	 * BasePage<Student> 
	 * @date 2016-9-9 下午12:12:23
	 */
	public BasePage<Student> findStuForTeacher(@Param("map")Map<String, Object> map, BasePage<Student> page);
	
	/**
	 * 指导老师查询自己的学生题目
	 * @param zdTeacher
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	public BasePage<StuStageFile> findStuTitle(String teaId, String stuNum, String stuName, String status, BasePage<StuStageFile> page);
	
	/**
	 * 指导老师查询自己的学生任务书
	 * @param zdTeacher
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	public BasePage<StuStageFile> findStuTaskBook(String teaId, String stuNum, String stuName, String status, BasePage<StuStageFile> page);
	
	/**
	 * 审核任务书
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int shStuTaskBook(String teaId, String[] stuIds, String status);
	
	/**
	 * 指导老师查询自己学生的开题报告
	 * @param zdTeacher
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	public BasePage<StuStageFile> findStuOpeningReport(String teaId, String stuNum, String stuName, String status, BasePage<StuStageFile> page);
	
	/**
	 * 审核开题报告
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int shStuOpeningReport(String teaId, String[] stuIds, String status);
	
	/**
	 * 指导老师查询自己学生的中期检查表
	 * @param zdTeacher
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	public BasePage<StuStageFile> findStuMidCheck(String teaId, String stuNum, String stuName, String status, BasePage<StuStageFile> page);
	
	/**
	 * 审核中期检查表
	 * @param stuIds
	 * @param status
	 * @return
	 */

	public int shStuMidCheck(String teaId, String[] stuIds, String status);

	/**
	 * 指导老师查询自己学生的论文初稿
	 * @param zdTeacher
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	public BasePage<StuStageFile> findStuFirstPaper(String teaId, String stuNum, String stuName, String status, BasePage<StuStageFile> page);
	
	/**
	 * 审核论文初稿
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int shStuFirstPaper(String teaId, String[] stuIds, String status);
	
	/**
	 * 指导老师查询自己学生的论文定稿
	 * @param zdTeacher
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	public BasePage<StuStageFile> findStuFinalPaper(String teaId, String stuNum, String stuName, String status, BasePage<StuStageFile> page);
	
	/**
	 * 审核论文定稿
	 * @param stuIds
	 * @param status
	 * @return
	 */

	public int shStuFinalPaper(String teaId, String[] stuIds, String status);
	

	/**
	 * 指导老师查询自己学生的答辩记录表
	 * @param zdTeacher
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	public BasePage<StuStageFile> findStuReplyNote(String teaId, String stuNum, String stuName, String status, BasePage<StuStageFile> page);
	
	/**
	 * 审核答辩记录表
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int shStuReplyNote(String teaId, String[] stuIds, String status);


/**
 * 申报课题
 * @param map
 * @param page
 * @return
 */
public BasePage<TeacherTitle> findTeaTitle(Map<String, Object> map,
		BasePage<TeacherTitle> page);




 /**
  * 指导老师查看自己申报的课题
  * @author gym
  * @param TeaId
  * @param page
  * @return
  * BasePage<TeacherTitle> 
  * @date 2016-9-9 下午1:56:44
  */

public List<TeacherTitle> findTeaTitleByTeaId(String teaId);
/**
 * 删除教师课题
 * @author gym
 * @param id
 * @return
 * int 
 * @date 2016-9-9 下午5:36:50
 */
public int delTeaTitle(String id);

/**
 * 教师申报课题
 * @author gym
 * @param teacherTitle
 * @return
 * int 
 * @date 2016-9-10 上午10:54:07
 */
public int addTeaTitle(TeacherTitle teacherTitle);

/**
 * @Title: selectNature
 * @Description: 查询课题性质
 * @param major
 * @return TitleNature 
 * @throws
 */
public List<TitleNature> selectNature();
/**
 * 查询课题完成形式
 * @author gym
 * @return
 * List<TitleForm> 
 * @date 2016-9-14 下午6:43:39
 */
public List<TitleForm> selectForm();
/**
 * 判断教师申报题目是否已被申报
 * @author gym
 * @param titleName
 * @return
 * List<TeacherTitle> 
 * @date 2016-9-16 上午8:13:25
 */
public int checkTeaTitle(String titleName);
/**
 *通过 课题Id查课题
 * @author gym
 * @param titleid
 * @return
 * TeacherTitle 
 */
public TeacherTitle findTeaTitleByTitleid(String titleid);

/**
 * 
 * @author gym
 * @param map
 * @return
 * BasePage<StuStageFile> 
 */
public List<Student> findStuTitle(Map<String, Object> map);
/**
 * 通过学生id查学生
 * @author gym
 * @param stuId
 * @return
 * Student 
 */
public Student findStuById(String stuId);
/**
 * 通过id查找applyTitle
 * @author gym
 * @param applyTitleId
 * @return
 * ApplyTitle 
 */
public ApplyTitle findApplyTitleById(String applyTitleId);
/**
 * 审核学生申报
 * @author gym
 * @param list
 * @param status
 * @param teaIdea
 * @return
 * int 
 * @date 2016-9-17 上午10:18:27
 */
public int checkApplyTitle(List<String> list, String status, String teaIdea);
/**
 * 教师查看自己的答辩组
 * @author gym
 * @param userId
 * @return
 * DbGroup 
 * @date 2016-9-18 上午9:40:34
 */
public List<DbGroup> findDbGroupByTeaId(String teaId);
/**
 * 通过答辩小组Id查询学生
 * @author gym
 * @param id
 * @return
 * List<Student> 
 */
public List<Student> findStuByDbId(String dbId);
/**
 * 通过答辩小组Id查询教师
 * @author gym
 * @param id
 * @return
 * List<Teacher> 
 * @date 2016-9-18 上午10:55:46
 */
public List<Teacher> findTeaByDbId(String dbId);
/**
 * 通过条件查找学生
 * @author gym
 * @param map
 * @return
 * List<Student> 
 */
public List<Student> findStuByKey(Map<String, Object> map);
/**
 * 通过学生的id查找taskbook
 * @author gym
 * @param userId
 * @return
 * TaskBook 
 * @date 2016-9-18 下午2:27:46
 */
public TaskBook findTaskBookbyStuId(String stuId);

/**
 * 通过教师Id和审核状态查找开题报告
 * @author gym
 * @param teaId
 * @param status
 * @return
 * List<OpeningReport> 
 */
public List<OpeningReport> findOpeningReportByKey(String teaId, String status);
/**
 * 通过stuNum，stuName/stuId,查找学生
 * @author gym
 * @param map
 * @return
 * Student 
 * @date 2016-9-18 下午5:53:01
 */
public Student findStuForTeacher(Map<String, Object> map);
/**
 * 通过教师Id和审核状态查找任务书
 * @author gym
 * @param teaId
 * @param status
 * @return
 * List<OpeningReport> 
 */
public List<TaskBook> findTaskbookByKey(String teaId, String status);
/**
 * 通过stuNum，stuName/stuId,查找学生
 * @author gym
 * @param map
 * @return
 * Student 
 * @date 2016-9-18 下午5:53:01
 */
public Student findStuForTaskBook(Map<String, Object> map);

/**
 * 往论文表中同步数据
 * @param paper
 * @return
 */
public int updataPaper(Paper paper);


public List<MidCheck> findmidCheckByKey(String teaId, String status);

public List<FirstPaper> findFirstPaperByKey(String teaId, String status);

public List<FinalPaper> findFinalPapersByKey(String teaId, String status);

/**
 * 更新任务书指导老师审核状态
 * @param list
 * @param status
 * @return
 */
public int updateTaskBookZdStatus(List<String> list, String status);

/**
 * 更新开题报告指导老师审核状态
 * @param list
 * @param status
 * @return
 */
public int updateOpeningReportZdStatus(List<String> list, String status);

/**
 * 更新中期检查表指导老师审核状态
 * @param list
 * @param status
 * @return
 */
public int updateMidCheckZdStatus(List<String> list, String status);

/**
 * 更新论文初稿指导老师审核状态
 * @param list
 * @param status
 * @return
 */
public int updateFirstPaperZdStatus(List<String> list, String status);

/**
 * 更新定稿指导老师审核状态
 * @param list
 * @param status
 * @return
 */
public int updateFinalPaperZdStatus(List<String> list, String status);

/**
 * 评阅老师查询评阅学生
 * @param pyTeaId
 * @return
 */
public BasePage<Student> zdTeaFindPyStu(String pyTeaId, BasePage<Student> page);

public BasePage<Paper> findStuPaperInfo(String stuNum, String stuName, String zdTeaNum, BasePage<Paper> page);

public List<Paper> findStuAllFile(Map<String, Object> map);

/**
 * @Title: findTaskBookById
 * @Description: 根据ID查找任务书
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

/**
 * @Title: findMidCheckById
 * @Description: 根据id查询中期检查
 * @param id
 * @return MidCheck 
 * @throws
 */
public MidCheck findMidCheckById(String id);

/**
 * @Title: findFirstPaperById
 * @Description: 根据id查询初稿
 * @param id
 * @return FirstPaper 
 * @throws
 */
public FirstPaper findFirstPaperById(String id);

/**
 * @Title: findFinalPaperById
 * @Description: 根据id查询定稿
 * @param id
 * @return FinalPaper 
 * @throws
 */
public FinalPaper findFinalPaperById(String id);

}
