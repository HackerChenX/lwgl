package com.hlzt.power.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.model.BasePage;
import com.hlzt.power.dao.ApplyTitleDao;
import com.hlzt.power.dao.DbGroupDao;
import com.hlzt.power.dao.GradeDao;
import com.hlzt.power.dao.OpeningReportDao;
import com.hlzt.power.dao.PaperDao;
import com.hlzt.power.dao.StudentDao;
import com.hlzt.power.dao.TaskBookDao;
import com.hlzt.power.dao.TeacherDao;
import com.hlzt.power.dao.TeacherTitleDao;
import com.hlzt.power.model.ApplyDelay;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.StuStageFile;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.TeacherTitle;
import com.hlzt.power.service.MajorLeaderFlowManageSer;
@Transactional
@Component
public class MajorLeaderFlowManageSerImp implements MajorLeaderFlowManageSer {
	@Resource
	TeacherDao teacherDao;
	@Resource
	TeacherTitleDao teacherTitleDao;
	@Resource
	TaskBookDao taskBookDao;
	@Resource
	OpeningReportDao openingReportDao;
	@Resource
	DbGroupDao dbGroupDao;
	@Resource
	ApplyTitleDao applyTitleDao;
	@Resource
	StudentDao studentDao;
	@Resource
	PaperDao paperDao;
	@Resource
	GradeDao gradeDao;


	@Override
	public List<Teacher> findTeacherByCondition(String major, String teaNum,
			String teaName, String zhicheng) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		if(StringUtils.isNotBlank(teaNum)){
			map.put("teaNum", teaNum);
		}
		if(StringUtils.isNotBlank(teaName)){
			teaName = "%"+teaName+"%";
			map.put("teaName", teaName);
		}
		if(StringUtils.isNotBlank(zhicheng)){
			map.put("zhicheng", zhicheng);
		}
		List<Teacher> list = new ArrayList<Teacher>();
		list = teacherDao.list(map, null);
		for (int i = 0; i < list.size(); i++) {
			int pyNowStunum = studentDao.findStuNumByPyTea(list.get(i).getUserId(), major);
			list.get(i).setPyNowStunum(pyNowStunum);
		}
		
		return list;
	}

	@Override
	public int setTeacherManageStuNum(List<String> list, int num, String major) {
		int i = teacherDao.updateTeaStuNumByIds(list, num, major);
		return i;
	}

	@Override
	public int shStuTaskBook(String[] stuIds, String status) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setAllTeaStuNum(int num, String major) {
		int i = teacherDao.updateAllTeaStuNum(num, major);		
		return i;
	}

	@Override
	public BasePage<TeacherTitle> findTeaTitle(String major, String teaName, String status, BasePage<TeacherTitle> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("major", major);
		if(StringUtils.isNotBlank(teaName)){
			map.put("teaName", teaName);
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		int totalRecord = teacherTitleDao.selectTeacherTitleNum(map);
		page.setTotalRecord(totalRecord);
		page.setResults(teacherTitleDao.selectTeacherTitle(map, page));
		
		return page;
	}

	@Override
	public int checkTeaTitle(List<String> list, String status, String leaderIdea) {
		int i = teacherTitleDao.updateMajorLeaderStatus(list, status, leaderIdea);
		return i;
	}

	@Override
	public TeacherTitle findTeaTitleById(String id, String major) {
		TeacherTitle tt = new TeacherTitle();
		tt = teacherTitleDao.selectTeaTitleById(id, major);
		return tt;
	}

	@Override
	public BasePage<StuStageFile> findTaskBook(String stuNum, String stuName,
			String titleName, String status, String major, BasePage<StuStageFile> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);
		}
		if(StringUtils.isNotBlank(stuName)){
			stuName = "%"+stuName+"%";
			map.put("stuName", stuName);
		}
		if(StringUtils.isNotBlank(titleName)){
			titleName = "%"+titleName+"%";
			map.put("titleName", titleName);
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		int totalRecord = taskBookDao.mlFindTaskBookNum(map);
		page.setTotalRecord(totalRecord);
		page.setResults(taskBookDao.mlFindTaskBook(map, page));
		return page;
	}

	@Override
	public int checkTaskBook(List<String> list, String major, String status) {
		int i = taskBookDao.updateTaskBookMlStatus(list, major, status);
		for (int j = 0; j < list.size(); j++) {
			TaskBook tb = new TaskBook();
			tb = taskBookDao.selectById(list.get(j));
			Student stu = new Student();
			stu = studentDao.selectStuByUserId(tb.getStuId());
			Paper p = new Paper();
			p.setTaskBook(tb.getTaskBookPath());
			p.setStuNum(stu.getUserNum());
			paperDao.updatePaper(p);
		}
		return i;
	}

	@Override
	public BasePage<StuStageFile> findOpneingReport(String stuNum,
			String stuName, String titleName, String status, String major,
			BasePage<StuStageFile> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);
		}
		if(StringUtils.isNotBlank(stuName)){
			map.put("stuName", stuName);
		}
		if(StringUtils.isNotBlank(titleName)){
			map.put("titleName", titleName);
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		if(StringUtils.isNotBlank(major)){
			map.put("major", major);
		}
		int totalRecord = openingReportDao.mLSelectOpeningReportNum(map);
		page.setTotalRecord(totalRecord);
		page.setResults(openingReportDao.mLSelectOpeningReport(map, page));
		return page;
	}

	@Override
	public int checkOpeningReport(List<String> list, String major, String status){
		int i = openingReportDao.mLCheckOpeningReport(list, major, status);
		if(status.equals("1")){
			for (int j = 0; j < list.size(); j++) {
				OpeningReport op = new OpeningReport();
				op = openingReportDao.selectById(list.get(j));
				Student stu = new Student();
				stu = studentDao.selectStuByUserId(op.getStuId());
				Paper p = new Paper();
				p.setOpeningReport(op.getOpeningReportPath());
				p.setStuNum(stu.getUserNum());
				paperDao.updatePaper(p);
			}
		}		
		return i;
	}

	@Override
	public List<DbGroup> findDbGroup(String major) {
		List<DbGroup> list = dbGroupDao.selectDbGroup(major);
		for (int i = 0; i < list.size(); i++) {
			Teacher tea = teacherDao.selectByUserId(list.get(i).getGroupSecretary());
			if(tea!=null){
				list.get(i).setGroupSecretaryName(tea.getTeaName());				
			}
			int stuNum = studentDao.findStuNumByDbGroup(list.get(i).getId(), major);
			list.get(i).setGroupStuNum(stuNum);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String dateTimeStr = sdf.format(list.get(i).getDbTime());
			list.get(i).setDateTimeStr(dateTimeStr);
			List<Teacher> teaList = new ArrayList<Teacher>();
			teaList = teacherDao.selectGeneralTeacher(major, list.get(i).getId());
			if(teaList.size()!=0){
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < teaList.size(); j++) {
					sb.append(teaList.get(j).getTeaName()).append("; ");
				}
				list.get(i).setGroupMemberName(sb.toString());				
			}
		}
		return list;
	}

	@Override
	public int addDbGroup(DbGroup dbGroup) {
		int i = dbGroupDao.insert(dbGroup);
		return i;
	}

	@Override
	public int deleteDbGroup(String id, String major) {
		int i = dbGroupDao.deleteDbGroup(id, major);
		return i;
	}

	@Override
	public BasePage<ApplyTitle> findApplyTitle(String major, String stuNum, String stuName, 
			String zdTeaName, String status, BasePage<ApplyTitle> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("major", major);
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);
		}
		if(StringUtils.isNotBlank(stuName)){
			stuName = "%"+stuName+"%";
			map.put("stuName", stuName);
		}
		if(StringUtils.isNotBlank(zdTeaName)){
			zdTeaName = "%"+zdTeaName+"%";
			map.put("zdTeaName", zdTeaName);
		}
		if(StringUtils.isNotBlank(status)){
			map.put("leaderStatus", status);
		}	
		map.put("teaStatus", "1");			
  	 	map.put("titleSource","1");       	 
		int totalRecord = applyTitleDao.selectApplyTitleNum(map);
		page.setTotalRecord(totalRecord);
		page.setResults(applyTitleDao.selectApplyTitle(map, page));
		return page;
	}

	@Override
	public int checkApplyTitle(List<String> list, String status, String major) {
		
		return applyTitleDao.mlCheckApplyTitle(list, status, major);
	}

	@Override
	public DbGroup findDbGroupInfoById(String id, String major) {
		DbGroup db = dbGroupDao.selectByDbGroupInfoById(id, major);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		db.setDateTimeStr(sdf.format(db.getDbTime()));
		return db;
	}

	@Override
	public BasePage<Student> findAllPyStu(String teaId, String major,
			BasePage<Student> page) {
		int totalRecord = studentDao.selectAllPyStuNum(teaId, major, page);
		page.setTotalRecord(totalRecord);
		List<Student> stuList = studentDao.selectAllPyStu(teaId, major, page);
		for(int i = 0;i<stuList.size();i++)
		{
			Student student = stuList.get(i);
			Teacher teacher = null;
			try {
				teacher = teacherDao.findByTeaId(student.getZdTeacher());				
			} catch (Exception e){
				e.printStackTrace();
			}
			if(teacher != null){
				stuList.get(i).setZdTeaName(teacher.getTeaName());
				stuList.get(i).setZdTeaZhiCheng(teacher.getZhicheng());
			}
			
		}
		page.setResults(stuList);
		return page;
	}

	@Override
	public int setPyStu(List<String> list, String teaId, String major) {
		int i = studentDao.setPyTea(list, teaId, major);
		for (int j = 0; j < list.size(); j++) {
			Student stu = studentDao.selectStuByUserId(list.get(j));
			if(StringUtils.isNotBlank(teaId)){
				Teacher tea = teacherDao.selectByUserId(teaId);
				Paper paper = new Paper();
				paper.setStuNum(stu.getUserNum());
				paper.setPyTeacher(tea.getTeaName());
				paper.setPyZhiCheng(tea.getZhicheng());
				paperDao.updatePaper(paper);	
				Grade grade = new Grade();
				grade.setPyTeacher(teaId);
				grade.setPyZhiCheng(tea.getZhicheng());
				grade.setStuId(stu.getUserId());
				gradeDao.updateByStuId(grade);
			}else{
				Paper paper = new Paper();
				paper.setStuNum(stu.getUserNum());
				paper.setPyTeacher(teaId);
				paper.setPyZhiCheng(null);
				paperDao.updatePaper(paper);
				Grade grade = new Grade();
				grade.setPyTeacher(teaId);
				grade.setPyZhiCheng(null);
				grade.setStuId(stu.getUserId());
				gradeDao.updateByStuId(grade);
			}			
		}
		return i;
	}

	@Override
	public List<Teacher> findSuperTeacher(String major, String groupId) {
		List<Teacher> list = teacherDao.selectSuperTeacher(major ,groupId);
		return list;
	}

	@Override
	public List<Teacher> findGeneralTeacher(String major, String groupId) {
		List<Teacher> list = teacherDao.selectGeneralTeacher(major, groupId);
		return list;
	}

	@Override
	public int setDbGroupForTea(List<String> list, String dbGroupId) {
		int i = teacherDao.setDbGroupForTea(list, dbGroupId);
		return i;
	}

	@Override
	public List<Teacher> findTeaByGroupId(String groupId ,String major) {
		List<Teacher> list = teacherDao.selectTeaByGroupId(groupId, major);
		return list;
	}

	@Override
	public List<Teacher> findTeaByUserIdList(List<String> list) {
		List<Teacher> teaList = teacherDao.selectTeaByIdList(list);
		return teaList;
	}

	@Override
	public List<Student> findStuByDbGroup(String dbGroup, String major) {

		return studentDao.findStuByDbGroup(dbGroup, major);
	}

	@Override
	public BasePage<Student> findNotDbGroup(String major, BasePage<Student> page) {
		int totalRecord = studentDao.findStuNumByDbGroup(null, major);//查询出条目数
		page.setTotalRecord(totalRecord);
		page.setResults(studentDao.findStuByDbGroup(null, major));
		return page;
	}

	@Override
	public int setDbGroupForStu(List<String> list, String dbGroupId) {
		int i = studentDao.setDbGroupForStu(list, dbGroupId);
		return i;
	}

	@Override
	public Teacher findTeaByUserId(String userId) {
		
		return teacherDao.selectByUserId(userId);
	}

	@Override
	public int updateDbGroup(DbGroup dbGroup) {
		int i = dbGroupDao.updateDbGroup(dbGroup);
		return i;
	}

	@Override
	public List<Student> findStuByPyTea(String pyTeaId, String major) {
		List<Student> stuList = studentDao.findStuByPyTea(pyTeaId, major);
		for(int i = 0;i<stuList.size();i++)
		{
			Student student = stuList.get(i);
			Teacher teacher = null;
			try {
				teacher = teacherDao.findByTeaId(student.getZdTeacher());				
			} catch (Exception e){
				e.printStackTrace();
			}
			if(teacher != null){
				stuList.get(i).setZdTeaName(teacher.getTeaName());
				stuList.get(i).setZdTeaZhiCheng(teacher.getZhicheng());
			}
		}
		return stuList;
	}

	@Override
	public ApplyTitle findStuTitleById(String id, String major) {
		
		return applyTitleDao.findById(id);
	}

	@Override
	public List<Teacher> findAllSuperTea(String major) {
		
		return teacherDao.selectAllSuperTeacher(major);
	}

	@Override
	public TaskBook findTaskBookById(String id) {
		TaskBook taskBook = taskBookDao.selectById(id);
		return taskBook;
	}

	@Override
	public OpeningReport findOpeningReportById(String id) {
		OpeningReport openingReport = openingReportDao.selectById(id);
		return openingReport;
	}


	
	
	

}
