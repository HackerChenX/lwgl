package com.hlzt.power.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.service.BaseServiceImpl;
import com.hlzt.power.dao.ApplyDelayDao;
import com.hlzt.power.dao.ApplyTitleDao;
import com.hlzt.power.dao.DbGroupDao;
import com.hlzt.power.dao.FinalPaperDao;
import com.hlzt.power.dao.FirstPaperDao;
import com.hlzt.power.dao.GradeDao;
import com.hlzt.power.dao.GradeWeightDao;
import com.hlzt.power.dao.MidCheckDao;
import com.hlzt.power.dao.OpeningReportDao;
import com.hlzt.power.dao.PaperDao;
import com.hlzt.power.dao.StagePlanDao;
import com.hlzt.power.dao.StudentDao;
import com.hlzt.power.dao.TaskBookDao;
import com.hlzt.power.dao.TeacherDao;
import com.hlzt.power.dao.TeacherTitleDao;
import com.hlzt.power.dao.TitleFormDao;
import com.hlzt.power.dao.TitleNatureDao;
import com.hlzt.power.model.ApplyDelay;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.FirstPaper;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.GradeWeight;
import com.hlzt.power.model.MidCheck;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.StagePlan;
import com.hlzt.power.model.StuGraCollect;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.TeacherTitle;
import com.hlzt.power.model.TitleForm;
import com.hlzt.power.model.TitleNature;
import com.hlzt.power.model.UserRole;
import com.hlzt.power.service.StudentFlowManageSer;
@Transactional
@Component
public class StudentFlowManageSerImp extends BaseServiceImpl<Student> implements StudentFlowManageSer{
	@Resource
	FinalPaperDao finalPaperDao;
	
	@Resource
	FirstPaperDao firstPaperDao;
	
	@Resource
	MidCheckDao midCheckDao;
	
	@Resource
	OpeningReportDao openingReportDao;
	
	@Resource
	TaskBookDao taskBookDao;
	
	@Resource
	GradeDao gradeDao;
	
	@Resource
	GradeWeightDao gradeWeightDao;
	
	@Resource
	StagePlanDao stagePlanDao;
	
	@Resource
	TeacherTitleDao teacherTitleDao;
	
	@Resource
	ApplyTitleDao applyTitleDao;

	@Resource
	StudentDao studentDao;
	
	@Resource
	TeacherDao  teacherDao;
	
	@Resource
	TitleFormDao titleFormDao;
	
	@Resource
	TitleNatureDao titleNatureDao;
	
	@Resource
	DbGroupDao dbGroupDao;
	
	@Resource
	ApplyDelayDao applyDelayDao;
	
	@Resource
	PaperDao paperDao;
	
	@Override
	protected BaseDao<Student> getDao() {
		return studentDao;
	}
	@Override
	public int addFinalPaper(FinalPaper finalPaper) {
		
		int i = finalPaperDao.insert(finalPaper);
		
		return i;
	}

	@Override
	public int addFirstPaper(FirstPaper firstPaper) {
		int i  = firstPaperDao.insert(firstPaper);
		return i;
	}

	@Override
	public int addMidCheck(MidCheck midCheck) {
		int i = midCheckDao.insert(midCheck);
		return i;
	}

	@Override
	public int addOpeningReport(OpeningReport openingReport) {
		int i = openingReportDao.insert(openingReport);
		return i;
	}

	@Override
	public int addTaskBook(TaskBook taskBook) {
		int i = taskBookDao.insert(taskBook);
		return i;
	}

	@Override
	public Grade findFinalGrade(String stuId) {
		Grade grade = new Grade();
		grade = gradeDao.selectById(stuId);
		return grade;
	}

	@Override
	public StagePlan findStagePlan(String stageName) {
		StagePlan stagePlan = stagePlanDao.findStagePlanByStageName(stageName);
		return stagePlan;
	}

	@Override
	public BasePage<TeacherTitle> findTeaTitle(Map<String, Object> map, BasePage<TeacherTitle> page) {
		int total = teacherTitleDao.rowsSize(map, null);
		page.setTotalRecord(total);
		page.setResults(teacherTitleDao.findPage(map, page, null));
		return page;
	}
	
	@Override
	public TeacherTitle findTeaTitleById(String id){
		TeacherTitle teaTitle = new TeacherTitle();
		teaTitle = teacherTitleDao.selectById(id);
		
		return teaTitle;
	}

	@Override
	public int addApplyTitle(ApplyTitle applyTitle) {
		int i = applyTitleDao.insert(applyTitle);
		return i;
	}

	@Override
	public ApplyTitle searchApplyTitleById(String stuId) {
		ApplyTitle applyTitle = applyTitleDao.findByStuId(stuId);
		return applyTitle;
	}

	@Override
	public Teacher findTeacherInfoByTeaId(String teaId) {
		Teacher teacher = teacherDao.findByTeaId(teaId);
		return teacher;
	}

	@Override
	public int updateTitleChoose(Map<String, Object> map) {
		int i = teacherTitleDao.updateTitleChoose(map);
		return i;
	}

	@Override
	public int removeApplyTitle(String id) {
		int i = applyTitleDao.deleteById(id);
		
		return i;
	}
	
	@Override
	public ApplyTitle searchApplyTitleByTitleId(String id) {
		ApplyTitle applyTitle = applyTitleDao.selectById(id);
		return applyTitle;
	}

	@Override
	public int removeStudentTitle(String stuId) {
		int a = studentDao.deleteTitleById(stuId);
		return a;
	}

	@Override
	public int updateApplyTitleById(String stuId,Map<String,Object> map) {
		int i = applyTitleDao.updateApplyTitleById(stuId,map);
		return i;
	}

	@Override
	public int updateStudentTitle(String stuId,Map<String,Object> map) {
		int a = studentDao.updateTitleByMap(stuId,map);
		return a;
	}

	@Override
	public int updateTeacherNowStuNum(String teaId,String Status) {
		int j = teacherDao.updateTeacherNowStuNum(teaId,Status);
		return j;
	}

	@Override
	public List<TitleNature> selectNature() {
		List<TitleNature> titleNatures = titleNatureDao.selectNature();
		return titleNatures;
	}

	@Override
	public List<TitleForm> selectForm() {
		List<TitleForm> titleForms = titleFormDao.selectForm();
		return titleForms;
	}

	@Override
	public BasePage<Teacher> findTeacherInfoByMajor(Map<String, Object> map, BasePage<Teacher> page) {
		int total = teacherDao.rowsSize(map, null);
		page.setTotalRecord(total);
		page.setResults(teacherDao.findPage(map, page, null));
		return page;
	}

	@Override
	public Teacher findTeacherById(String id) {
		Teacher teacher=teacherDao.selectById(id);
		return teacher;
	}

	@Override
	public TeacherTitle findTeaTitleByTitle(String title) {
		TeacherTitle teacherTitle = teacherTitleDao.selectByTitle(title);
		return teacherTitle;
	}

	@Override
	public TaskBook findTaskByStuId(String stuId) {
		TaskBook taskBook = taskBookDao.findTaskByStuId(stuId);
		return taskBook;
	}

	@Override
	public int updateTaskBookById(String stuId, Map<String, Object> map) {
		int i = taskBookDao.updateTaskBookById(stuId,map);
		return i;
	}

	@Override
	public int deleteTaskBookById(String id) {
		int i = taskBookDao.deleteById(id);
		return i;
	}

	@Override
	public int updateOpeningReportById(String stuId, Map<String, Object> map) {
		int i = openingReportDao.updateOpeningReportById(stuId,map);
		return i;
	}

	@Override
	public OpeningReport findReportByStuId(String stuId) {
		OpeningReport openingReport = openingReportDao.findOpeningReportByStuId(stuId);
		return openingReport;
	}

	@Override
	public int deleteOpeningReportById(String id) {
		int i = openingReportDao.deleteById(id);
		return i;
	}

	@Override
	public int updateMidCheckById(String stuId, Map<String, Object> map) {
		int i =midCheckDao.updateMidCheckById(stuId,map);
		return i;
	}

	@Override
	public MidCheck findMidCheckByStuId(String stuId) {
		MidCheck midCheck = midCheckDao.findMidCheckByStuId(stuId);
		return midCheck;
	}

	@Override
	public int deleteMidCheckById(String id) {
		int i = midCheckDao.deleteById(id);
		return i;
	}

	@Override
	public int updateFirstPaperById(String stuId, Map<String, Object> map) {
		int i = firstPaperDao.updateFirstPaperById(stuId,map);
		return i;
	}

	@Override
	public FirstPaper findFirstPaperByStuId(String stuId) {
		FirstPaper firstPaper = firstPaperDao.findFirstPaperByStuId(stuId);
		return firstPaper;
	}

	@Override
	public int deleteFirstPaperById(String id) {
		int i = firstPaperDao.deleteById(id);
		return i;
	}

	@Override
	public int updateFinalPaperById(String stuId, Map<String, Object> map) {
		int  i = finalPaperDao.updateFinalPaperById(stuId,map);
		return i;
	}

	@Override
	public FinalPaper findFinalPaperByStuId(String stuId) {
		FinalPaper finalPaper = finalPaperDao.findFinalPaperByStuId(stuId);
		return finalPaper;
	}

	@Override
	public int deleteFinalPaperById(String id) {
		int i = finalPaperDao.deleteById(id);
		return i;
	}

	@Override
	public DbGroup findDbGroupByStuId(String stuId) {
		DbGroup dbGroup =dbGroupDao.findDbGroupByStuId(stuId);
		return dbGroup;
	}

	@Override
	public Grade findGradeByStuId(String id) {
		Grade grade =gradeDao.selectById(id);
		return grade;
	}

	@Override
	public GradeWeight findGradeWeightByMajor(String major) {
		GradeWeight gradeWeight = gradeWeightDao.findGradeWeightByMajor(major);
		return gradeWeight;
	}

	@Override
	public List<StagePlan> findStagePlans() {
		List<StagePlan> list = stagePlanDao.findStagePlan();
		return list;
	}

	@Override
	public List<ApplyDelay> findApplyDelaysByStuId(String stuId) {
		List<ApplyDelay> list = applyDelayDao.findApplyDelayByStuId(stuId);
		return list;
	}

	@Override
	public int insertApplyDelay(ApplyDelay applyDelay) {
		int i = applyDelayDao.insert(applyDelay);
		return i;
	}

	@Override
	public ApplyDelay findApplyDelaysById(String id) {
		ApplyDelay applyDelay = applyDelayDao.selectById(id);
		return applyDelay;
	}

	@Override
	public int deleteApplyDelay(String id) {
		int i = applyDelayDao.deleteById(id);
		return i;
	}

	@Override
	public int submitAllFilePaper(Paper paper) {
		int i = paperDao.updatePaper(paper);
		return i;
	}

	@Override
	public Paper selectPaperByStuNum(String stuNum) {
		Paper paper = paperDao.selectByStuNum(stuNum);
		return paper;
	}
	@Override
	public BasePage<Teacher> findTeacherInfoHaveTitle(Map<String, Object> map,
			BasePage<Teacher> page) {
		int total = teacherDao.findTeacherInfoHaveTitleSize(map);
		page.setTotalRecord(total);
		if(total==0){
			page.setResults(null);
		}else{
			page.setResults(teacherDao.findTeacherInfoHaveTitle(map, page));
		}
		return page;
	}

	
	
}
