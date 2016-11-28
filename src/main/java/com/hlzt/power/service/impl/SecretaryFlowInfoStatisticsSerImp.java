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
import com.hlzt.power.dao.DbGroupDao;
import com.hlzt.power.dao.FinalPaperDao;
import com.hlzt.power.dao.FirstPaperDao;
import com.hlzt.power.dao.MajorDao;
import com.hlzt.power.dao.MidCheckDao;
import com.hlzt.power.dao.OpeningReportDao;
import com.hlzt.power.dao.StudentDao;
import com.hlzt.power.dao.TaskBookDao;
import com.hlzt.power.dao.TeacherDao;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.SubmitStatistics;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.service.SecretaryFlowInfoStatisticsSer;
@Transactional
@Component
public class SecretaryFlowInfoStatisticsSerImp implements SecretaryFlowInfoStatisticsSer{
	
	
	@Resource
	private StudentDao studentDao;
	@Resource
	private MajorDao majorDao;
	@Resource
	private ClassNameDao classNameDao;
	@Resource
	private TaskBookDao taskBookDao;
	@Resource
	private OpeningReportDao openingReportDao;
	@Resource
	private MidCheckDao midCheckDao;
	@Resource
	private FirstPaperDao firstPaperDao;
	@Resource
	private FinalPaperDao finalPaperDao;
	@Resource
	private DbGroupDao dbGroupDao;
	@Resource
	private TeacherDao teacherDao;
	

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
			String className) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> majorMap = new HashMap<String, Object>();
		List<ClassName> classNameList = new ArrayList<ClassName>();
		List<Major> majorList = new ArrayList<Major>();
		List<SubmitStatistics> result = new ArrayList<SubmitStatistics>();
		
		if(StringUtils.isBlank(major)&&StringUtils.isBlank(className)){
			majorList = majorDao.selectMajor();
			for (int i = 0; i < majorList.size(); i++) {
				majorMap.put("majorId", majorList.get(i).getId());
				classNameList = classNameDao.selectClass(majorMap);
				for (int j = 0; j < classNameList.size(); j++) {
					SubmitStatistics ss = new SubmitStatistics();
					String m = majorList.get(i).getMajorName();//专业
					ss.setMajor(m);
					map.put("major", m);
					String cn = classNameList.get(j).getcName();//班级
					ss.setClassName(cn);
					map.put("className", cn);
					int stuNum = studentDao.findStuNumByClass(cn);//该班级学生总数
					ss.setStuAllNum(stuNum);
					int notSubmitNum = taskBookDao.selectNotSubmitNum(map);//未提交学生数量
					ss.setNotSubmitNum(notSubmitNum);
					int checkingNum = taskBookDao.selectCheckingNum(map);//审核中学生数量
					ss.setCheckingNum(checkingNum);
					int checkNotNum =taskBookDao.selectCheckNotNum(map);//审核未通过学生数量
					ss.setCheckNotNum(checkNotNum);
					int passNum = taskBookDao.selectCheckPassNum(map);//审核通过学生数量
					ss.setCheckPassNum(passNum);
					result.add(ss);//将该班级统计结果放入result	
				}
			}
		}
		if(StringUtils.isNotBlank(major)&&StringUtils.isBlank(className)){
			Major m = majorDao.selectByName(major);
			majorMap.put("majorId", m.getId());
			classNameList = classNameDao.selectClass(majorMap);
			for (int i = 0; i < classNameList.size(); i++) {
				SubmitStatistics ss = new SubmitStatistics();
				map.put("major", major);
				ss.setMajor(major);
				map.put("className", classNameList.get(i).getcName());
				ss.setClassName(classNameList.get(i).getcName());
				int stuNum = studentDao.findStuNumByClass(classNameList.get(i).getcName());//该班级学生总数
				ss.setStuAllNum(stuNum);
				int notSubmitNum = taskBookDao.selectNotSubmitNum(map);//未提交学生数量
				ss.setNotSubmitNum(notSubmitNum);
				int checkingNum = taskBookDao.selectCheckingNum(map);//审核中学生数量
				ss.setCheckingNum(checkingNum);
				int checkNotNum =taskBookDao.selectCheckNotNum(map);//审核未通过学生数量
				ss.setCheckNotNum(checkNotNum);
				int passNum = taskBookDao.selectCheckPassNum(map);//审核通过学生数量
				ss.setCheckPassNum(passNum);
				result.add(ss);//将该班级统计结果放入result	
			}
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
			SubmitStatistics ss = new SubmitStatistics();
			if(StringUtils.isBlank(major)){
				ClassName cn = classNameDao.findClassByName(className);
				Major m = majorDao.selectMajorById(cn.getMajor());
				ss.setMajor(m.getMajorName());
				map.put("major", m.getMajorName());				
			}else{
				ss.setMajor(major);
				map.put("major", major);	
			}
			ss.setClassName(className);
			int stuNum = studentDao.findStuNumByClass(className);//该班级学生总数
			ss.setStuAllNum(stuNum);
			int notSubmitNum = taskBookDao.selectNotSubmitNum(map);//未提交学生数量
			ss.setNotSubmitNum(notSubmitNum);
			int checkingNum = taskBookDao.selectCheckingNum(map);//审核中学生数量
			ss.setCheckingNum(checkingNum);
			int checkNotNum =taskBookDao.selectCheckNotNum(map);//审核未通过学生数量
			ss.setCheckNotNum(checkNotNum);
			int passNum = taskBookDao.selectCheckPassNum(map);//审核通过学生数量
			ss.setCheckPassNum(passNum);
			result.add(ss);//将该班级统计结果放入result	
		}
					
		return result;
	}



	@Override
	public List<SubmitStatistics> findOpeningReportSubmit(String major,
			String className) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> majorMap = new HashMap<String, Object>();
		List<ClassName> classNameList = new ArrayList<ClassName>();
		List<Major> majorList = new ArrayList<Major>();
		List<SubmitStatistics> result = new ArrayList<SubmitStatistics>();
		
		if(StringUtils.isBlank(major)&&StringUtils.isBlank(className)){
			majorList = majorDao.selectMajor();
			for (int i = 0; i < majorList.size(); i++) {
				majorMap.put("majorId", majorList.get(i).getId());
				classNameList = classNameDao.selectClass(majorMap);
				for (int j = 0; j < classNameList.size(); j++) {
					SubmitStatistics ss = new SubmitStatistics();
					String m = majorList.get(i).getMajorName();//专业
					ss.setMajor(m);
					map.put("major", m);
					String cn = classNameList.get(j).getcName();//班级
					ss.setClassName(cn);
					map.put("className", cn);
					int stuNum = studentDao.findStuNumByClass(cn);//该班级学生总数
					ss.setStuAllNum(stuNum);
					int notSubmitNum = openingReportDao.selectNotSubmitNum(map);//未提交学生数量
					ss.setNotSubmitNum(notSubmitNum);
					int checkingNum = openingReportDao.selectCheckingNum(map);//审核中学生数量
					ss.setCheckingNum(checkingNum);
					int checkNotNum =openingReportDao.selectCheckNotNum(map);//审核未通过学生数量
					ss.setCheckNotNum(checkNotNum);
					int passNum = openingReportDao.selectCheckPassNum(map);//审核通过学生数量
					ss.setCheckPassNum(passNum);
					result.add(ss);//将该班级统计结果放入result	
				}
			}
		}
		if(StringUtils.isNotBlank(major)&&StringUtils.isBlank(className)){
			Major m = majorDao.selectByName(major);
			majorMap.put("majorId", m.getId());
			classNameList = classNameDao.selectClass(majorMap);
			for (int i = 0; i < classNameList.size(); i++) {
				SubmitStatistics ss = new SubmitStatistics();
				map.put("major", major);
				ss.setMajor(major);
				map.put("className", classNameList.get(i).getcName());
				ss.setClassName(classNameList.get(i).getcName());
				int stuNum = studentDao.findStuNumByClass(classNameList.get(i).getcName());//该班级学生总数
				ss.setStuAllNum(stuNum);
				int notSubmitNum = openingReportDao.selectNotSubmitNum(map);//未提交学生数量
				ss.setNotSubmitNum(notSubmitNum);
				int checkingNum = openingReportDao.selectCheckingNum(map);//审核中学生数量
				ss.setCheckingNum(checkingNum);
				int checkNotNum =openingReportDao.selectCheckNotNum(map);//审核未通过学生数量
				ss.setCheckNotNum(checkNotNum);
				int passNum = openingReportDao.selectCheckPassNum(map);//审核通过学生数量
				ss.setCheckPassNum(passNum);
				result.add(ss);//将该班级统计结果放入result	
			}
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
			SubmitStatistics ss = new SubmitStatistics();
			if(StringUtils.isBlank(major)){
				ClassName cn = classNameDao.findClassByName(className);
				Major m = majorDao.selectMajorById(cn.getMajor());
				ss.setMajor(m.getMajorName());
				map.put("major", m.getMajorName());				
			}else{
				ss.setMajor(major);
				map.put("major", major);	
			}
			ss.setClassName(className);
			int stuNum = studentDao.findStuNumByClass(className);//该班级学生总数
			ss.setStuAllNum(stuNum);
			int notSubmitNum = openingReportDao.selectNotSubmitNum(map);//未提交学生数量
			ss.setNotSubmitNum(notSubmitNum);
			int checkingNum = openingReportDao.selectCheckingNum(map);//审核中学生数量
			ss.setCheckingNum(checkingNum);
			int checkNotNum =openingReportDao.selectCheckNotNum(map);//审核未通过学生数量
			ss.setCheckNotNum(checkNotNum);
			int passNum = openingReportDao.selectCheckPassNum(map);//审核通过学生数量
			ss.setCheckPassNum(passNum);
			result.add(ss);//将该班级统计结果放入result	
		}
					
		return result;
	}



	@Override
	public List<SubmitStatistics> findMidCheckSubmit(String major,
			String className) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> majorMap = new HashMap<String, Object>();
		List<ClassName> classNameList = new ArrayList<ClassName>();
		List<Major> majorList = new ArrayList<Major>();
		List<SubmitStatistics> result = new ArrayList<SubmitStatistics>();
		
		if(StringUtils.isBlank(major)&&StringUtils.isBlank(className)){
			majorList = majorDao.selectMajor();
			for (int i = 0; i < majorList.size(); i++) {
				majorMap.put("majorId", majorList.get(i).getId());
				classNameList = classNameDao.selectClass(majorMap);
				for (int j = 0; j < classNameList.size(); j++) {
					SubmitStatistics ss = new SubmitStatistics();
					String m = majorList.get(i).getMajorName();//专业
					ss.setMajor(m);
					map.put("major", m);
					String cn = classNameList.get(j).getcName();//班级
					ss.setClassName(cn);
					map.put("className", cn);
					int stuNum = studentDao.findStuNumByClass(cn);//该班级学生总数
					ss.setStuAllNum(stuNum);
					int notSubmitNum = midCheckDao.selectNotSubmitNum(map);//未提交学生数量
					ss.setNotSubmitNum(notSubmitNum);
					int checkingNum = midCheckDao.selectCheckingNum(map);//审核中学生数量
					ss.setCheckingNum(checkingNum);
					int checkNotNum =midCheckDao.selectCheckNotNum(map);//审核未通过学生数量
					ss.setCheckNotNum(checkNotNum);
					int passNum = midCheckDao.selectCheckPassNum(map);//审核通过学生数量
					ss.setCheckPassNum(passNum);
					result.add(ss);//将该班级统计结果放入result	
				}
			}
		}
		if(StringUtils.isNotBlank(major)&&StringUtils.isBlank(className)){
			Major m = majorDao.selectByName(major);
			majorMap.put("majorId", m.getId());
			classNameList = classNameDao.selectClass(majorMap);
			for (int i = 0; i < classNameList.size(); i++) {
				SubmitStatistics ss = new SubmitStatistics();
				map.put("major", major);
				ss.setMajor(major);
				map.put("className", classNameList.get(i).getcName());
				ss.setClassName(classNameList.get(i).getcName());
				int stuNum = studentDao.findStuNumByClass(classNameList.get(i).getcName());//该班级学生总数
				ss.setStuAllNum(stuNum);
				int notSubmitNum = midCheckDao.selectNotSubmitNum(map);//未提交学生数量
				ss.setNotSubmitNum(notSubmitNum);
				int checkingNum = midCheckDao.selectCheckingNum(map);//审核中学生数量
				ss.setCheckingNum(checkingNum);
				int checkNotNum =midCheckDao.selectCheckNotNum(map);//审核未通过学生数量
				ss.setCheckNotNum(checkNotNum);
				int passNum = midCheckDao.selectCheckPassNum(map);//审核通过学生数量
				ss.setCheckPassNum(passNum);
				result.add(ss);//将该班级统计结果放入result	
			}
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
			SubmitStatistics ss = new SubmitStatistics();
			if(StringUtils.isBlank(major)){
				ClassName cn = classNameDao.findClassByName(className);
				Major m = majorDao.selectMajorById(cn.getMajor());
				ss.setMajor(m.getMajorName());
				map.put("major", m.getMajorName());				
			}else{
				ss.setMajor(major);
				map.put("major", major);	
			}
			ss.setClassName(className);
			int stuNum = studentDao.findStuNumByClass(className);//该班级学生总数
			ss.setStuAllNum(stuNum);
			int notSubmitNum = midCheckDao.selectNotSubmitNum(map);//未提交学生数量
			ss.setNotSubmitNum(notSubmitNum);
			int checkingNum = midCheckDao.selectCheckingNum(map);//审核中学生数量
			ss.setCheckingNum(checkingNum);
			int checkNotNum =midCheckDao.selectCheckNotNum(map);//审核未通过学生数量
			ss.setCheckNotNum(checkNotNum);
			int passNum = midCheckDao.selectCheckPassNum(map);//审核通过学生数量
			ss.setCheckPassNum(passNum);
			result.add(ss);//将该班级统计结果放入result	
		}
					
		return result;
	}



	@Override
	public List<SubmitStatistics> findFirstPaperSubmit(String major,
			String className) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> majorMap = new HashMap<String, Object>();
		List<ClassName> classNameList = new ArrayList<ClassName>();
		List<Major> majorList = new ArrayList<Major>();
		List<SubmitStatistics> result = new ArrayList<SubmitStatistics>();
		
		if(StringUtils.isBlank(major)&&StringUtils.isBlank(className)){
			majorList = majorDao.selectMajor();
			for (int i = 0; i < majorList.size(); i++) {
				majorMap.put("majorId", majorList.get(i).getId());
				classNameList = classNameDao.selectClass(majorMap);
				for (int j = 0; j < classNameList.size(); j++) {
					SubmitStatistics ss = new SubmitStatistics();
					String m = majorList.get(i).getMajorName();//专业
					ss.setMajor(m);
					map.put("major", m);
					String cn = classNameList.get(j).getcName();//班级
					ss.setClassName(cn);
					map.put("className", cn);
					int stuNum = studentDao.findStuNumByClass(cn);//该班级学生总数
					ss.setStuAllNum(stuNum);
					int notSubmitNum = firstPaperDao.selectNotSubmitNum(map);//未提交学生数量
					ss.setNotSubmitNum(notSubmitNum);
					int checkingNum = firstPaperDao.selectCheckingNum(map);//审核中学生数量
					ss.setCheckingNum(checkingNum);
					int checkNotNum =firstPaperDao.selectCheckNotNum(map);//审核未通过学生数量
					ss.setCheckNotNum(checkNotNum);
					int passNum = firstPaperDao.selectCheckPassNum(map);//审核通过学生数量
					ss.setCheckPassNum(passNum);
					result.add(ss);//将该班级统计结果放入result	
				}
			}
		}
		if(StringUtils.isNotBlank(major)&&StringUtils.isBlank(className)){
			Major m = majorDao.selectByName(major);
			majorMap.put("majorId", m.getId());
			classNameList = classNameDao.selectClass(majorMap);
			for (int i = 0; i < classNameList.size(); i++) {
				SubmitStatistics ss = new SubmitStatistics();
				map.put("major", major);
				ss.setMajor(major);
				map.put("className", classNameList.get(i).getcName());
				ss.setClassName(classNameList.get(i).getcName());
				int stuNum = studentDao.findStuNumByClass(classNameList.get(i).getcName());//该班级学生总数
				ss.setStuAllNum(stuNum);
				int notSubmitNum = firstPaperDao.selectNotSubmitNum(map);//未提交学生数量
				ss.setNotSubmitNum(notSubmitNum);
				int checkingNum = firstPaperDao.selectCheckingNum(map);//审核中学生数量
				ss.setCheckingNum(checkingNum);
				int checkNotNum =firstPaperDao.selectCheckNotNum(map);//审核未通过学生数量
				ss.setCheckNotNum(checkNotNum);
				int passNum = firstPaperDao.selectCheckPassNum(map);//审核通过学生数量
				ss.setCheckPassNum(passNum);
				result.add(ss);//将该班级统计结果放入result	
			}
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
			SubmitStatistics ss = new SubmitStatistics();
			if(StringUtils.isBlank(major)){
				ClassName cn = classNameDao.findClassByName(className);
				Major m = majorDao.selectMajorById(cn.getMajor());
				ss.setMajor(m.getMajorName());
				map.put("major", m.getMajorName());				
			}else{
				ss.setMajor(major);
				map.put("major", major);	
			}
			ss.setClassName(className);
			int stuNum = studentDao.findStuNumByClass(className);//该班级学生总数
			ss.setStuAllNum(stuNum);
			int notSubmitNum = firstPaperDao.selectNotSubmitNum(map);//未提交学生数量
			ss.setNotSubmitNum(notSubmitNum);
			int checkingNum = firstPaperDao.selectCheckingNum(map);//审核中学生数量
			ss.setCheckingNum(checkingNum);
			int checkNotNum =firstPaperDao.selectCheckNotNum(map);//审核未通过学生数量
			ss.setCheckNotNum(checkNotNum);
			int passNum = firstPaperDao.selectCheckPassNum(map);//审核通过学生数量
			ss.setCheckPassNum(passNum);
			result.add(ss);//将该班级统计结果放入result	
		}
					
		return result;
	}
	

	@Override
	public List<SubmitStatistics> findFinalPaperSubmit(String major,
			String className) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> majorMap = new HashMap<String, Object>();
		List<ClassName> classNameList = new ArrayList<ClassName>();
		List<Major> majorList = new ArrayList<Major>();
		List<SubmitStatistics> result = new ArrayList<SubmitStatistics>();
		
		if(StringUtils.isBlank(major)&&StringUtils.isBlank(className)){
			majorList = majorDao.selectMajor();
			for (int i = 0; i < majorList.size(); i++) {
				majorMap.put("majorId", majorList.get(i).getId());
				classNameList = classNameDao.selectClass(majorMap);
				for (int j = 0; j < classNameList.size(); j++) {
					SubmitStatistics ss = new SubmitStatistics();
					String m = majorList.get(i).getMajorName();//专业
					ss.setMajor(m);
					map.put("major", m);
					String cn = classNameList.get(j).getcName();//班级
					ss.setClassName(cn);
					map.put("className", cn);
					int stuNum = studentDao.findStuNumByClass(cn);//该班级学生总数
					ss.setStuAllNum(stuNum);
					int notSubmitNum = finalPaperDao.selectNotSubmitNum(map);//未提交学生数量
					ss.setNotSubmitNum(notSubmitNum);
					int checkingNum = finalPaperDao.selectCheckingNum(map);//审核中学生数量
					ss.setCheckingNum(checkingNum);
					int checkNotNum =finalPaperDao.selectCheckNotNum(map);//审核未通过学生数量
					ss.setCheckNotNum(checkNotNum);
					int passNum = finalPaperDao.selectCheckPassNum(map);//审核通过学生数量
					ss.setCheckPassNum(passNum);
					result.add(ss);//将该班级统计结果放入result	
				}
			}
		}
		if(StringUtils.isNotBlank(major)&&StringUtils.isBlank(className)){
			Major m = majorDao.selectByName(major);
			majorMap.put("majorId", m.getId());
			classNameList = classNameDao.selectClass(majorMap);
			for (int i = 0; i < classNameList.size(); i++) {
				SubmitStatistics ss = new SubmitStatistics();
				map.put("major", major);
				ss.setMajor(major);
				map.put("className", classNameList.get(i).getcName());
				ss.setClassName(classNameList.get(i).getcName());
				int stuNum = studentDao.findStuNumByClass(classNameList.get(i).getcName());//该班级学生总数
				ss.setStuAllNum(stuNum);
				int notSubmitNum = finalPaperDao.selectNotSubmitNum(map);//未提交学生数量
				ss.setNotSubmitNum(notSubmitNum);
				int checkingNum = finalPaperDao.selectCheckingNum(map);//审核中学生数量
				ss.setCheckingNum(checkingNum);
				int checkNotNum =finalPaperDao.selectCheckNotNum(map);//审核未通过学生数量
				ss.setCheckNotNum(checkNotNum);
				int passNum = finalPaperDao.selectCheckPassNum(map);//审核通过学生数量
				ss.setCheckPassNum(passNum);
				result.add(ss);//将该班级统计结果放入result	
			}
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
			SubmitStatistics ss = new SubmitStatistics();
			if(StringUtils.isBlank(major)){
				ClassName cn = classNameDao.findClassByName(className);
				Major m = majorDao.selectMajorById(cn.getMajor());
				ss.setMajor(m.getMajorName());
				map.put("major", m.getMajorName());				
			}else{
				ss.setMajor(major);
				map.put("major", major);	
			}
			ss.setClassName(className);
			int stuNum = studentDao.findStuNumByClass(className);//该班级学生总数
			ss.setStuAllNum(stuNum);
			int notSubmitNum = finalPaperDao.selectNotSubmitNum(map);//未提交学生数量
			ss.setNotSubmitNum(notSubmitNum);
			int checkingNum = finalPaperDao.selectCheckingNum(map);//审核中学生数量
			ss.setCheckingNum(checkingNum);
			int checkNotNum =finalPaperDao.selectCheckNotNum(map);//审核未通过学生数量
			ss.setCheckNotNum(checkNotNum);
			int passNum = finalPaperDao.selectCheckPassNum(map);//审核通过学生数量
			ss.setCheckPassNum(passNum);
			result.add(ss);//将该班级统计结果放入result	
		}
					
		return result;
	}



	@Override
	public BasePage<Student> findChecking(String stageName,
			String leaderStatus, String major, String className,
			BasePage<Student> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("leaderStatus", leaderStatus);
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
		}
		int totalRecord = 0;
		List<Student> list = new ArrayList<Student>();
		if("task_book".equals(stageName)){
			totalRecord = taskBookDao.selectCheckingNum(map);
			list = studentDao.findTaskBookByLeaderStatus(map,page);
		}else if("opening_report".equals(stageName)){
			totalRecord = openingReportDao.selectCheckingNum(map);
			list = studentDao.findOpeningReportByLeaderStatus(map,page);
		}else if("mid_check".equals(stageName)){
			totalRecord = midCheckDao.selectCheckingNum(map);
			list = studentDao.findMidCheckByLeaderStatus(map,page);
		}else if("first_paper".equals(stageName)){
			totalRecord = firstPaperDao.selectCheckingNum(map);
			list = studentDao.findFirstPaperByLeaderStatus(map,page);
		}else if("final_paper".equals(stageName)){
			totalRecord = finalPaperDao.selectCheckingNum(map);
			list = studentDao.findFinalPaperByLeaderStatus(map,page);
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
		}else if("mid_check".equals(stageName)){
			totalRecord = midCheckDao.selectNotSubmitNum(map);
			list = studentDao.findMidCheckNotSubmit(map,page);
		}else if("first_paper".equals(stageName)){
			totalRecord = firstPaperDao.selectNotSubmitNum(map);
			list = studentDao.findFirstPaperNotSubmit(map,page);
		}else if("final_paper".equals(stageName)){
			totalRecord = finalPaperDao.selectNotSubmitNum(map);
			list = studentDao.findFinalPaperNotSubmit(map,page);
		}
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		
		return page;
	}



	@Override
	public BasePage<Student> findNotPass(String stageName, String leaderStatus,
			String major, String className, BasePage<Student> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("leaderStatus", leaderStatus);
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
		}
		int totalRecord = 0;
		List<Student> list = new ArrayList<Student>();
		if("task_book".equals(stageName)){
			totalRecord = taskBookDao.selectCheckNotNum(map);
			list = studentDao.findTaskBookByLeaderStatus(map,page);
		}else if("opening_report".equals(stageName)){
			totalRecord = openingReportDao.selectCheckNotNum(map);
			list = studentDao.findOpeningReportByLeaderStatus(map,page);
		}else if("mid_check".equals(stageName)){
			totalRecord = midCheckDao.selectCheckNotNum(map);
			list = studentDao.findMidCheckByLeaderStatus(map,page);
		}else if("first_paper".equals(stageName)){
			totalRecord = firstPaperDao.selectCheckNotNum(map);
			list = studentDao.findFirstPaperByLeaderStatus(map,page);
		}else if("final_paper".equals(stageName)){
			totalRecord = finalPaperDao.selectCheckNotNum(map);
			list = studentDao.findFinalPaperByLeaderStatus(map,page);
		}
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		
		return page;
	}



	@Override
	public BasePage<Student> findPass(String stageName, String leaderStatus,
			String major, String className, BasePage<Student> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("leaderStatus", leaderStatus);
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(className)){
			map.put("className", className);
		}
		int totalRecord = 0;
		List<Student> list = new ArrayList<Student>();
		if("task_book".equals(stageName)){
			totalRecord = taskBookDao.selectCheckPassNum(map);
			list = studentDao.findTaskBookByLeaderStatus(map,page);
		}else if("opening_report".equals(stageName)){
			totalRecord = openingReportDao.selectCheckPassNum(map);
			list = studentDao.findOpeningReportByLeaderStatus(map,page);
		}else if("mid_check".equals(stageName)){
			totalRecord = midCheckDao.selectCheckPassNum(map);
			list = studentDao.findMidCheckByLeaderStatus(map,page);
		}else if("first_paper".equals(stageName)){
			totalRecord = firstPaperDao.selectCheckPassNum(map);
			list = studentDao.findFirstPaperByLeaderStatus(map,page);
		}else if("final_paper".equals(stageName)){
			totalRecord = finalPaperDao.selectCheckPassNum(map);
			list = studentDao.findFinalPaperByLeaderStatus(map,page);
		}
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		
		return page;
	}



	@Override
	public BasePage<DbGroup> findDbGroup(String major, BasePage<DbGroup> page){
		
		int totalRecord = dbGroupDao.selectDbGroupNum(major);
		page.setTotalRecord(totalRecord);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("major", major);
		List<DbGroup> list = dbGroupDao.findPage(map, page, null);
		for (int i = 0; i < list.size(); i++) {
			Teacher tea = teacherDao.selectByUserId(list.get(i).getGroupSecretary());
		if(tea!=null){
			list.get(i).setGroupSecretaryName(tea.getTeaName());
		}
			
			int stuNum = studentDao.findStuNumByDbGroup(list.get(i).getId(), major);
			list.get(i).setGroupStuNum(stuNum);
			List<Teacher> teaList = new ArrayList<Teacher>();
			teaList = teacherDao.selectGeneralTeacher(major, list.get(i).getId());
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < teaList.size(); j++) {
				sb.append(teaList.get(j).getTeaName()).append("; ");
			}
			list.get(i).setGroupMemberName(sb.toString());
		}
		page.setResults(list);
		return page;
	}



	@Override
	public BasePage<Student> findDbGroupStu(String dbGroupId,
			BasePage<Student> page) {
		int totalRecord = studentDao.selectStuNumByDbGroup(dbGroupId);
		page.setTotalRecord(totalRecord);
		page.setResults(studentDao.selectStuByDbGroup(dbGroupId, page));
		return page;
	}
	
	
}
