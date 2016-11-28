package com.hlzt.power.service.impl;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.helper.SysConfig;
import com.hlzt.commons.helper.UuidHelper;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.dao.ApplyDelayDao;
import com.hlzt.power.dao.ApplyTitleDao;
import com.hlzt.power.dao.FinalPaperDao;
import com.hlzt.power.dao.FirstPaperDao;
import com.hlzt.power.dao.GradeDao;
import com.hlzt.power.dao.MidCheckDao;
import com.hlzt.power.dao.OpeningReportDao;
import com.hlzt.power.dao.PaperDao;
import com.hlzt.power.dao.TaskBookDao;
import com.hlzt.power.dao.PublicNoticeDao;
import com.hlzt.power.dao.TeacherDao;
import com.hlzt.power.dao.MajorDao;
import com.hlzt.power.dao.StudentDao;
import com.hlzt.power.dao.TeacherTitleDao;
import com.hlzt.power.model.ApplyDelay;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.FirstPaper;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.GradeAll;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.MidCheck;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.PublicNotice;
import com.hlzt.power.model.StuGraCollect;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.TeacherTitle;
import com.hlzt.power.service.SecretaryFlowManageSer;
@Transactional
@Component
public class SecretaryFlowManageSerImp implements SecretaryFlowManageSer{
	
	@Resource
	StudentDao studentDao;
	@Resource
	MajorDao majorDao;
	@Resource
	GradeDao gradeDao;
	@Resource
	TeacherTitleDao teacherTitleDao;
	@Resource
	TeacherDao teacherDao;
	@Resource
	PaperDao paperDao;
	@Resource
	ApplyTitleDao applyTitleDao;
	@Resource
	ApplyDelayDao applyDelayDao;
	@Resource
	FirstPaperDao firstPaperDao;
	@Resource
	FinalPaperDao finalPaperDao;
	@Resource
	MidCheckDao midCheckDao;
	@Resource
	OpeningReportDao openingReportDao;
	@Resource
	TaskBookDao taskBookDao;
	@Resource
	PublicNoticeDao publicNoticeDao;
	
	
	@Override
	public List<StuGraCollect> findStuGraColllectByCondition(
			Map<String, Object> map, BasePage<StuGraCollect> page) {
		
		List<StuGraCollect> list = new ArrayList<StuGraCollect>();
		list = studentDao.findStuGraColllectByCondition(map, page);
		
		return list;
	}

	@Override
	public List<GradeAll> findGradeAllEvaluate() {
		List<GradeAll> gList = new ArrayList<GradeAll>();
		List<Major> mList = new ArrayList<Major>();
		mList = majorDao.selectMajor();
		for (int i = 0; i < mList.size(); i++) {
			GradeAll gradeAll = new GradeAll();
			String major = mList.get(i).getMajorName();
			int majorStuNum = studentDao.findStuNumByMajor(major);
			int majorValidStuNum = gradeDao.findAllnum(major);
			int excellentNum = gradeDao.findExcellentNum(major);			
			int wellNum = gradeDao.findWellNum(major);
			int mediumNum = gradeDao.findMediumNum(major);
			int passNum = gradeDao.findPassNum(major);
			int notPassNum = gradeDao.findNotPassNum(major);
			float excellentRates = 0;
			float wellRates = 0;
			float mediumRates = 0;
			float notPassRates = 0;
			float passRates = 0;
			if(majorValidStuNum!=0){
				excellentRates = excellentNum/(float)majorValidStuNum;
				wellRates = wellNum/(float)majorValidStuNum;
				mediumRates = mediumNum/(float)majorValidStuNum;
				notPassRates = notPassNum/(float)majorValidStuNum;				
				passRates = passNum/(float)majorValidStuNum;
			}
			NumberFormat nf = java.text.NumberFormat.getPercentInstance();
			nf.setMaximumIntegerDigits(100);
			nf.setMinimumFractionDigits(2);
			String excellentRatesStr = nf.format(excellentRates);
			String wellRatesStr = nf.format(wellRates);
			String mediumRatesStr = nf.format(mediumRates);
			String notPassRatesStr = nf.format(notPassRates);
			String passRatesStr = nf.format(passRates);
			gradeAll.setMajorName(mList.get(i).getMajorName());
			gradeAll.setMajorStuNum(majorStuNum);
			gradeAll.setMajorValidStuNum(majorValidStuNum);
			gradeAll.setExcellentNum(excellentNum);
			gradeAll.setWellNum(wellNum);
			gradeAll.setMediumNum(mediumNum);
			gradeAll.setPassNum(passNum);
			gradeAll.setNotPassNum(notPassNum);
			gradeAll.setExcellentRates(excellentRatesStr);
			gradeAll.setWellRates(wellRatesStr);
			gradeAll.setMediumRates(mediumRatesStr);
			gradeAll.setPassRates(passRatesStr);
			gradeAll.setNotPassRates(notPassRatesStr);
			
			gList.add(gradeAll);			
		}
		
		
		
		
		return gList;
	}

	@Override
	public BasePage<TeacherTitle> findTeacherTitle(String major,
			String teaName, String status, BasePage<TeacherTitle> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(teaName)){
			map.put("teaName", teaName);
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		
		int totalRecord = teacherTitleDao.rowsSize(map, null);
		List<TeacherTitle> results = teacherTitleDao.findPage(map, page, null);
		page.setTotalRecord(totalRecord);
		page.setResults(results);
		
		return page;
	}

	@Override
	public int checkTeacherTitle(List<String> idList, String status, String managerIdea) {
		int i = teacherTitleDao.updateManagerStatus(idList, status, managerIdea);
		return i;
	}

	@Override
	public TeacherTitle findeTeacherTitleById(String teacherTitleId) {
		TeacherTitle teacherTitle = teacherTitleDao.selectById(teacherTitleId);
		return teacherTitle;
	}

	@Override
	public BasePage<Grade> findAllGrade(String major, String className, String zdTeaName, 
			String stuName, String stuNum, int grade, BasePage<Grade> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
		}
		if(StringUtils.isNotBlank(zdTeaName)){
			zdTeaName = "%"+zdTeaName+"%";
			map.put("zdTeaName", zdTeaName);
		}
		if(StringUtils.isNotBlank(stuName)){
			stuName = "%"+stuName+"%";
			map.put("stuName", stuName);
		}
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);
		}
			map.put("grade", grade);
		int totalRecord = gradeDao.rowsSize(map, null);
		page.setTotalRecord(totalRecord);
		List<Grade> list = new ArrayList<Grade>();
		list = gradeDao.findPage(map, page, null);
		for (int i = 0; i < list.size(); i++) {
			String pyTeaId = list.get(i).getPyTeacher();
			Teacher teacher = teacherDao.selectByUserId(pyTeaId);
			if(teacher!=null){
				list.get(i).setPyTeacherName(teacher.getTeaName());				
			}
		}
		page.setResults(list);		
		return page;
	}

	@Override
	public BasePage<Teacher> findTeacher(String teaNum, String teaName,
			String teaZhicheng, BasePage<Teacher> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(teaNum)){
			map.put("teaNum", teaNum);
		}
		if(StringUtils.isNotBlank(teaName)){
			teaName = "%"+teaName+"%";
			map.put("teaName", teaName);
		}
		if(StringUtils.isNotBlank(teaZhicheng)){
			map.put("teaZhicheng", teaZhicheng);
		}
		int totalRecord = teacherDao.rowsSize(map, null);
		List<Teacher> results = teacherDao.findPage(map, page, null);
		page.setTotalRecord(totalRecord);
		page.setResults(results);
		
		return page;
	}

	@Override
	public BasePage<Paper> findStuPaperInfo(String major, String className,
			String endNum, BasePage<Paper> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("term", SysConfig.getValue("term"));
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
		}
		if(StringUtils.isNotBlank(endNum)){
			endNum = endNum + "$";
			map.put("endNum", endNum);
		}
		int totalRecord = paperDao.rowsSize(map, null);
		List<Paper> results = paperDao.findPage(map, page, null);
		page.setTotalRecord(totalRecord);
		page.setResults(results);
		
		return page;
	}

	@Override
	public int recommendYxPaper(List<String> list, String status) {
		int i = gradeDao.updateRecommendYxPaper(list, status);
		return i;
	}

	@Override
	public int checkApplyTitle(List<String> idList, String status,
			String managerIdea) {
		int i = applyTitleDao.managerCheckApplyTitle(idList, status, managerIdea);
		if("1".equals(status)){
			for (int j = 0; j < idList.size(); j++) {
				ApplyTitle at = applyTitleDao.findById(idList.get(j));
				int total = gradeDao.selectByStuId(at.getStuId());
				Student stu = new Student();
				stu = studentDao.selectStuByUserId(at.getStuId());
				Teacher t = teacherDao.findByTeaId(at.getTeaId());
				if(total==0){
					Grade g = new Grade();
					g.setCreateTime(new Date());
					g.setCreateUser("");
					g.setDbGrade(0);
					g.setEvaluate("");
					g.setFinalGrade(0);
					g.setId(UuidHelper.getRandomUUID());
					g.setPyGrade(0);
					g.setZdTeacher(at.getTeaId());
					g.setZdZhiCheng(t.getZhicheng());
					g.setPyTeacher("");
					g.setRecommend("");
					g.setStuMajor(stu.getMajor());
					g.setStuId(at.getStuId());
					g.setStuNum(at.getStuNum());
					g.setSyGrade(0);
					g.setTitle(at.getTitle());
					gradeDao.insert(g);						
				}else{
					Grade g = new Grade();
					g.setZdTeacher(at.getTeaId());
					g.setZdZhiCheng(t.getZhicheng());
					g.setStuId(at.getStuId());
					g.setTitle(at.getTitle());
					gradeDao.updateByStuId(g);
				}
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("teaId", at.getTeaId());
				map.put("title", at.getTitle());
				studentDao.updateTitleByMap(at.getStuId(), map);
				
				Paper p = new Paper();
				p.setTitle(at.getTitle());
				p.setZdTeacher(t.getTeaName());
				p.setZdZhiCheng(t.getZhicheng());
				p.setZdTeaNum(t.getTeaNum());
				p.setStuNum(stu.getUserNum());
				paperDao.updatePaper(p);
			}
			
		}
		return i;
	}

	@Override
	public BasePage<ApplyTitle> findApplyTitle(String major, String stuName,
			String teaName, String status, BasePage<ApplyTitle> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(stuName)){
			stuName = "%"+stuName+"%";
			map.put("stuName", stuName);
		}
		if(StringUtils.isNotBlank(teaName)){
			teaName = "%"+teaName+"%";
			map.put("zdTeaName", teaName);
		}
		if(StringUtils.isNotBlank(status)){
			map.put("managerStatus", status);
		}
		map.put("teaStatus", "1");
		map.put("titleSource","1"); 
		map.put("leaderStatus", "1");
		int totalRecord = applyTitleDao.selectApplyTitleNum(map);
		page.setTotalRecord(totalRecord);
		page.setResults(applyTitleDao.selectApplyTitle(map, page));
		return page;
	}

	@Override
	public ApplyTitle findeApplyTitleById(String id) {
		ApplyTitle at = applyTitleDao.findById(id);
		return at;
	}

	@Override
	public BasePage<ApplyDelay> findApplyDelay(String stuNum, String stuName, 
			String zdTeaName, String status, BasePage<ApplyDelay> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);
		}
		if(StringUtils.isNotBlank(stuName)){
			map.put("stuName", stuName);
		}
		if(StringUtils.isNotBlank(stuName)){
			map.put("zdTeaName", zdTeaName);
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		
		int totalRecord = applyDelayDao.findApplyDelayNum(map);
		page.setTotalRecord(totalRecord);
		List<ApplyDelay> list = new ArrayList<ApplyDelay>();
		list = applyDelayDao.findApplyDelay(map, page);
		for (int i = 0; i < list.size(); i++) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String delayTimeStr = sdf.format(list.get(i).getDelayTime());
			list.get(i).setDelayTimeStr(delayTimeStr);
			if("apply_title".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("申请课题");
			}else if("task_book".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("提交任务书");
			}else if("opening_report".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("提交开题报告");
			}else if("mid_check".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("提交中期检查表");
			}else if("first_paper".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("提交论文初稿");
			}else if("final_paper".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("提交论文定稿");
			}
		}
		page.setResults(list);
		return page;
	}

	@Override
	public int setApplyDelay(List<String> list, String idea, String status) {
		
		return applyDelayDao.updateManagerStatus(list, idea, status);
	}

	@Override
	public ApplyDelay findApplyDelayById(String id) {
		
		return applyDelayDao.selectById(id);
	}

	@Override
	public ApplyTitle findApplyTitleByStuId(String stuId) {
		
		return applyTitleDao.findByStuId(stuId);
	}

	@Override
	public FinalPaper findFinalPaperByStuId(String stuId) {
		
		return finalPaperDao.findFinalPaperByStuId(stuId);
	}

	@Override
	public FirstPaper findFirstPaperByStuId(String stuId) {
		
		return firstPaperDao.findFirstPaperByStuId(stuId);
	}

	@Override
	public MidCheck findMidCheckByStuId(String stuId) {
		
		return midCheckDao.findMidCheckByStuId(stuId);
	}

	@Override
	public OpeningReport findOpeningReportByStuId(String stuId) {
		
		return openingReportDao.findOpeningReportByStuId(stuId);
	}

	@Override
	public TaskBook findTaskBookByStuId(String stuId) {
		
		return taskBookDao.findTaskByStuId(stuId);
	}

	@Override
	public int addApplyTitle(ApplyTitle applyTitle) {
		
		return applyTitleDao.insert(applyTitle);
	}

	@Override
	public int addFinalPaper(FinalPaper finalPaper) {
		
		return finalPaperDao.insert(finalPaper);
	}

	@Override
	public int addFirstPaper(FirstPaper firstPaper) {
		
		return firstPaperDao.insert(firstPaper);
	}

	@Override
	public int addMidCheck(MidCheck midCheck) {
		
		return midCheckDao.insert(midCheck);
	}

	@Override
	public int addOpeningReport(OpeningReport openingReport) {
		
		return openingReportDao.insert(openingReport);
	}

	@Override
	public int addTaskBook(TaskBook taskBook) {
		
		return taskBookDao.insert(taskBook);
	}

	@Override
	public int setApplyTitleLimitTime(Date limitTime, String stuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limitTime", limitTime);
		int i = applyTitleDao.updateApplyTitleById(stuId, map);
		return i;
	}

	@Override
	public int setFinalPaperLimitTime(Date limitTime, String stuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limitTime", limitTime);
		int i = finalPaperDao.updateFinalPaperById(stuId, map);
		return i;
	}

	@Override
	public int setFirstPaperLimitTime(Date limitTime, String stuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limitTime", limitTime);
		int i = firstPaperDao.updateFirstPaperById(stuId, map);
		return i;
	}

	@Override
	public int setMidCheckLimitTime(Date limitTime, String stuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limitTime", limitTime);
		int i = midCheckDao.updateMidCheckById(stuId, map);
		return i;
	}

	@Override
	public int setOpeningReportLimitTime(Date limitTime, String stuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limitTime", limitTime);
		int i = openingReportDao.updateOpeningReportById(stuId, map);
		return i;
	}

	@Override
	public int setTaskBookLimitTime(Date limitTime, String stuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limitTime", limitTime);
		int i = taskBookDao.updateTaskBookById(stuId, map);
		return i;
	}

	@Override
	public BasePage<PublicNotice> findPublicNotice(Map<String, Object> map,BasePage<PublicNotice> page) {
		int total = publicNoticeDao.rowsSize(map, null);
		page.setTotalRecord(total);
		page.setResults(publicNoticeDao.findPage(map, page, null));
		return page;
	}

	@Override
	public int deleteNoticeById(String id) {
		int i = publicNoticeDao.deleteById(id);
		return i;
	}

	@Override
	public int updateNoticeById(String top,String id) {
		int i = publicNoticeDao.updateNoticeById(top,id);
		return i;
	}

	@Override
	public int insertNotice(PublicNotice publicNotice) {
		int i = publicNoticeDao.insert(publicNotice);
		return i;
	}

	@Override
	public PublicNotice findNoticeById(String id) {
		PublicNotice publicNotice = publicNoticeDao.selectById(id);
		return publicNotice;
	}

	@Override
	public BasePage<ApplyDelay> findApplyDelayInit() {
		BasePage<ApplyDelay> page = new BasePage<ApplyDelay>();
		int totalRecord = applyDelayDao.findApplyDelayNumInit();
		page.setTotalRecord(totalRecord);
		List<ApplyDelay> list = new ArrayList<ApplyDelay>();
		list = applyDelayDao.findApplyDelayInit(page);
		Teacher teacher = null;
		for (int i = 0; i < list.size(); i++) {
			if(StringUtils.isNotBlank(list.get(i).getTeaId())){
				teacher = teacherDao.findByTeaId(list.get(i).getTeaId());
			}
			if(teacher!=null){
				list.get(i).setTeaName(teacher.getTeaName());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String delayTimeStr = sdf.format(list.get(i).getDelayTime());
			list.get(i).setDelayTimeStr(delayTimeStr);
			if("apply_title".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("申请课题");
			}else if("task_book".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("提交任务书");
			}else if("opening_report".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("提交开题报告");
			}else if("mid_check".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("提交中期检查表");
			}else if("first_paper".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("提交论文初稿");
			}else if("final_paper".equals(list.get(i).getDelayStage())){
				list.get(i).setDelayStage("提交论文定稿");
			}
		}
		page.setResults(list);
		return page;
	}
	
	
	
	

}
