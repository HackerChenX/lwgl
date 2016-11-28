package com.hlzt.power.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.model.BasePage;
import com.hlzt.power.dao.ClassNameDao;
import com.hlzt.power.dao.GradeDao;
import com.hlzt.power.dao.MajorDao;
import com.hlzt.power.dao.OpeningReportDao;
import com.hlzt.power.dao.StudentDao;
import com.hlzt.power.dao.TaskBookDao;
import com.hlzt.power.dao.TeacherDao;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.SubmitStatistics;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.service.MajorLeaderFlowInfoStatisticsSer;
@Transactional
@Component
public class MajorLeaderFlowInfoStatisticsSerImp implements MajorLeaderFlowInfoStatisticsSer{
	@Resource
	StudentDao studentDao;
	@Resource
	TaskBookDao taskBookDao;
	@Resource
	ClassNameDao classNameDao;
	@Resource
	OpeningReportDao openingReportDao;
	@Resource
	GradeDao gradeDao;
	@Resource
	TeacherDao teacherDao;
	@Resource
	MajorDao majorDao;
	
	
	@Override
	public BasePage<Grade> findAllGrade(String className, String zdTeaName,
			String stuName, String stuNum, String major, BasePage<Grade> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("major", major);
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
		int totalRecord = gradeDao.rowsSize(map, null);
		page.setTotalRecord(totalRecord);
		List<Grade> list = new ArrayList<Grade>();
		list = gradeDao.findPage(map, page, null);
		for (int i = 0; i < list.size(); i++) {
			String pyTeaId = list.get(i).getPyTeacher();
			Teacher teacher = null;
			try{
				teacher = teacherDao.selectById(pyTeaId);
			}catch (Exception e) {
				e.printStackTrace();
			}
			if(teacher!=null)
			list.get(i).setPyTeacherName(teacher.getTeaName());
		}
		page.setResults(list);
		
		return page;
	}

	@Override
	public BasePage<Student> findChecking(String stageName,
			String zdTeaStatus, String major, String className,
			BasePage<Student> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("zdTeaStatus", zdTeaStatus);
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
		}
		int totalRecord = 0;
		List<Student> list = new ArrayList<Student>();
		if("task_book".equals(stageName)){
			totalRecord = taskBookDao.selectZdCheckingNum(map);
			list = studentDao.findTaskBookByLeaderStatus(map,page);
		}else if("opening_report".equals(stageName)){
			totalRecord = openingReportDao.selectZdCheckingNum(map);
			list = studentDao.findOpeningReportByLeaderStatus(map,page);
		}
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		
		return page;
	}

	@Override
	public BasePage<Student> findNotPass(String stageName, String zdTeaStatus,
			String major, String className, BasePage<Student> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("zdTeaStatus", zdTeaStatus);
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
		}
		int totalRecord = 0;
		List<Student> list = new ArrayList<Student>();
		if("task_book".equals(stageName)){
			totalRecord = taskBookDao.selectZdCheckNotNum(map);
			list = studentDao.findTaskBookByLeaderStatus(map,page);
		}else if("opening_report".equals(stageName)){
			totalRecord = openingReportDao.selectZdCheckNotNum(map);
			list = studentDao.findOpeningReportByLeaderStatus(map,page);
		}
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public BasePage<Student> findNotSubmit(String stageName, String major,
			String className, BasePage<Student> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
		}
		int totalRecord = 0;
		List<Student> list = new ArrayList<Student>();
		if("task_book".equals(stageName)){
			totalRecord = taskBookDao.selectNotSubmitNum(map);
			list = studentDao.findTaskBookNotSubmit(map,page);
		}else if("opening_report".equals(stageName)){
			totalRecord = openingReportDao.selectNotSubmitNum(map);
			list = studentDao.findOpeningReportNotSubmit(map,page);
		}
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public List<SubmitStatistics> findOpeningReportSubmit(String major,
			String className, String zdTeaName) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ClassName> classNameList = new ArrayList<ClassName>();
		List<SubmitStatistics> result = new ArrayList<SubmitStatistics>();
		Major ma = majorDao.selectByName(major);
		if(ma!=null){
			map.put("majorId", ma.getId());
		}
		classNameList = classNameDao.selectClass(map);//查询全部班级			
		if(StringUtils.isNotBlank(zdTeaName)){
			map.put("zdTeaName", zdTeaName);
		}
		if(StringUtils.isBlank(className)){
			//根据班级名循环统计各班提交情况
			for (int i = 0; i < classNameList.size(); i++) {
				map.put("major", major);
				SubmitStatistics ss = new SubmitStatistics();
				String m = classNameList.get(i).getMajor();//专业
				ss.setMajor(m);
				String cn = classNameList.get(i).getcName();//班级
				ss.setClassName(cn);
				map.put("className",cn);
				int stuNum = studentDao.findStuNumByClass(cn);//该班级学生总数
				ss.setStuAllNum(stuNum);
				int notSubmitNum = openingReportDao.selectNotSubmitNum(map);//未提交学生数量
				ss.setNotSubmitNum(notSubmitNum);
				int checkingNum = openingReportDao.selectZdCheckingNum(map);//审核中学生数量
				ss.setCheckingNum(checkingNum);
				int checkNotNum =openingReportDao.selectZdCheckNotNum(map);//审核未通过学生数量
				ss.setCheckNotNum(checkNotNum);
				int passNum = openingReportDao.selectCheckPassNum(map);//审核通过学生数量
				ss.setCheckPassNum(passNum);
				result.add(ss);//将该班级统计结果放入result	
				map.clear();
			}
		}else{
			map.put("className", className);
			SubmitStatistics ss = new SubmitStatistics();
			ss.setMajor(major);
			ss.setClassName(className);
			int stuNum = studentDao.findStuNumByClass(className);//该班级学生总数
			ss.setStuAllNum(stuNum);
			int notSubmitNum = openingReportDao.selectNotSubmitNum(map);//未提交学生数量
			ss.setNotSubmitNum(notSubmitNum);
			int checkingNum = openingReportDao.selectZdCheckingNum(map);//审核中学生数量
			ss.setCheckingNum(checkingNum);
			int checkNotNum =openingReportDao.selectZdCheckNotNum(map);//审核未通过学生数量
			ss.setCheckNotNum(checkNotNum);
			int passNum = openingReportDao.selectZdCheckPassNum(map);//审核通过学生数量
			ss.setCheckPassNum(passNum);
			result.add(ss);//将该班级统计结果放入result	
		}
					
		return result;
	}

	@Override
	public BasePage<Student> findPass(String stageName, String zdTeaStatus,
			String major, String className, BasePage<Student> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("zdTeaStatus", zdTeaStatus);
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
		}
		int totalRecord = 0;
		List<Student> list = new ArrayList<Student>();
		if("task_book".equals(stageName)){
			totalRecord = taskBookDao.selectZdCheckPassNum(map);
			list = studentDao.findTaskBookByLeaderStatus(map,page);
		}else if("opening_report".equals(stageName)){
			totalRecord = openingReportDao.selectZdCheckPassNum(map);
			list = studentDao.findOpeningReportByLeaderStatus(map,page);
		}
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		
		return page;
	}

	@Override
	public BasePage<Student> findStudentByCondition(String major,
			String className, String zdTeaName, String stuName, String stuNum,
			BasePage<Student> page) {
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
		
		int totalRecord = studentDao.rowsSize(map, null);
		List<Student> results = studentDao.findPage(map, page, null);
		page.setTotalRecord(totalRecord);
		page.setResults(results);
		return page;
	}

	@Override
	public List<SubmitStatistics> findTaskBookSubmit(String major,
			String className, String zdTeaName) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ClassName> classNameList = new ArrayList<ClassName>();
		List<SubmitStatistics> result = new ArrayList<SubmitStatistics>();
		Major ma = majorDao.selectByName(major);
		if(ma!=null){
			map.put("majorId", ma.getId());
		}
		if(StringUtils.isNotBlank(zdTeaName)){
			map.put("zdTeaName", zdTeaName);
		}
		classNameList = classNameDao.selectClass(map);//查询全部班级
		map.put("major", major);
		if(StringUtils.isBlank(className)){
			//根据班级名循环统计各班提交情况
			for (int i = 0; i < classNameList.size(); i++) {
				SubmitStatistics ss = new SubmitStatistics();
				String m = classNameList.get(i).getMajor();//专业
				ss.setMajor(m);
				String cn = classNameList.get(i).getcName();//班级
				map.put("className", cn);
				ss.setClassName(cn);
				int stuNum = studentDao.findStuNumByClass(cn);//该班级学生总数
				ss.setStuAllNum(stuNum);
				int notSubmitNum = taskBookDao.selectNotSubmitNum(map);//未提交学生数量
				ss.setNotSubmitNum(notSubmitNum);
				int checkingNum = taskBookDao.selectZdCheckingNum(map);//审核中学生数量
				ss.setCheckingNum(checkingNum);
				int checkNotNum =taskBookDao.selectZdCheckNotNum(map);//审核未通过学生数量
				ss.setCheckNotNum(checkNotNum);
				int passNum = taskBookDao.selectZdCheckPassNum(map);//审核通过学生数量
				ss.setCheckPassNum(passNum);
				result.add(ss);//将该班级统计结果放入result			
			}
		}else{
			map.put("className", className);
			SubmitStatistics ss = new SubmitStatistics();
			ss.setMajor(major);
			ss.setClassName(className);
			int stuNum = studentDao.findStuNumByClass(className);//该班级学生总数
			ss.setStuAllNum(stuNum);
			int notSubmitNum = taskBookDao.selectNotSubmitNum(map);//未提交学生数量
			ss.setNotSubmitNum(notSubmitNum);
			int checkingNum = taskBookDao.selectZdCheckingNum(map);//审核中学生数量
			ss.setCheckingNum(checkingNum);
			int checkNotNum =taskBookDao.selectZdCheckNotNum(map);//审核未通过学生数量
			ss.setCheckNotNum(checkNotNum);
			int passNum = taskBookDao.selectZdCheckPassNum(map);//审核通过学生数量
			ss.setCheckPassNum(passNum);
			result.add(ss);//将该班级统计结果放入result	
		}
					
		return result;
	}

}
