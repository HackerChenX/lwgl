package com.hlzt.power.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.hlzt.power.dao.DbGroupDao;
import com.hlzt.power.dao.FinalPaperDao;
import com.hlzt.power.dao.FirstPaperDao;
import com.hlzt.power.dao.GradeDao;
import com.hlzt.power.dao.MidCheckDao;
import com.hlzt.power.dao.OpeningReportDao;
import com.hlzt.power.dao.PaperDao;
import com.hlzt.power.dao.ReplyNoteDao;
import com.hlzt.power.dao.StudentDao;
import com.hlzt.power.dao.TaskBookDao;
import com.hlzt.power.dao.TeacherDao;
import com.hlzt.power.dao.TeacherTitleDao;
import com.hlzt.power.dao.TitleFormDao;
import com.hlzt.power.dao.TitleNatureDao;
import com.hlzt.power.model.ApplyDelay;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.BackLog;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.FirstPaper;
import com.hlzt.power.model.Grade;
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
import com.hlzt.power.service.PublicSer;
import com.hlzt.power.service.ZdTeacherFlowManageSer;
@Transactional
@Component
public class ZdTeacherFlowManageSerImp implements ZdTeacherFlowManageSer{
	@Resource
	StudentDao studentDao;
	@Resource
	ApplyTitleDao applytitleDao;
	@Resource
	OpeningReportDao openingReportDao;
	@Resource
	TaskBookDao taskBookDao;
	@Resource
	MidCheckDao midCheckDao;
	@Resource
	FirstPaperDao firstPaperDao;
	@Resource
	FinalPaperDao finalPaperDao;
	@Resource
	ReplyNoteDao replyNoteDao;
	@Resource
	TeacherTitleDao teacherTitleDao;	
	@Resource
	TitleNatureDao titleNatureDao;
	@Resource
	TitleFormDao titleFormDao;
	@Resource
	 DbGroupDao dbGroupDao;
	@Resource
	TeacherDao teacherDao;
	@Resource
	GradeDao gradeDao;
	@Resource
	PaperDao paperDao;
	@Resource
	private PublicSer publicSer;
	@Override
	public BasePage<Student> findStuForTeacher (Map<String, Object> map, BasePage<Student> page) {		
		page.setResults(studentDao.findStuByZdTeacherId(map,page));
		int totalRecord = studentDao.findStuNumByZdTeacherId(map, page);
		page.setTotalRecord(totalRecord);
		return page;
	}
	
	@Override
	public int shStuFinalPaper(String teaId, String[] stuIds, String status) {
		List<String> list = Arrays.asList(stuIds);
		int i = finalPaperDao.updateStatus(list, status);		
		return i;
	}

	@Override
	public int shStuFirstPaper(String teaId, String[] stuIds, String status) {
		List<String> list = Arrays.asList(stuIds);
		int i = firstPaperDao.updateStatus(list, status);		
		return i;
	}

	@Override
	public int shStuMidCheck(String teaId, String[] stuIds, String status) {
		List<String> list = Arrays.asList(stuIds);
		int i = midCheckDao.updateStatus(list, status);		
		return i;
	}

	@Override
	public int shStuOpeningReport(String teaId, String[] stuIds, String status) {
		List<String> list = Arrays.asList(stuIds);
		int i = openingReportDao.updateStatus(list, status);		
		return i;
	}

	@Override
	public int shStuReplyNote(String teaId, String[] stuIds, String status) {
		List<String> list = Arrays.asList(stuIds);
		int i = replyNoteDao.updateStatus(list, status);		
		return i;
	}

	@Override
	public int shStuTaskBook(String teaId, String[] stuIds, String status) {
		List<String> list = Arrays.asList(stuIds);
		int i = taskBookDao.updateStatus(list, status);		
		return i;
	}


	@Override
	public BasePage<StuStageFile> findStuFinalPaper(String teaId,
			String stuNum, String stuName, String status, BasePage<StuStageFile> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(teaId)){
			map.put("teaId", teaId);			
		}
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);			
		}
		if(StringUtils.isNotBlank(stuName)){
			map.put("stuName", stuName);			
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		int totalRecord = finalPaperDao.rowsSize(map, null);
		page.setTotalRecord(totalRecord);
		page.setResults(finalPaperDao.findByMap(map, page, null));
		return page;
	}

	@Override
	public BasePage<StuStageFile> findStuFirstPaper(String teaId,
			String stuNum, String stuName, String status, BasePage<StuStageFile> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(teaId)){
			map.put("teaId", teaId);			
		}
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);			
		}
		if(StringUtils.isNotBlank(stuName)){
			map.put("stuName", stuName);			
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		int totalPage = firstPaperDao.rowsSize(map, null);
		page.setTotalPage(totalPage);
		page.setResults(firstPaperDao.findByMap(map, page, null));
		return page;
	}

	@Override
	public BasePage<StuStageFile> findStuMidCheck(String teaId, String stuNum,
			String stuName, String status, BasePage<StuStageFile> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(teaId)){
			map.put("teaId", teaId);			
		}
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);			
		}
		if(StringUtils.isNotBlank(stuName)){
			map.put("stuName", stuName);			
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		
		int totalRecord = midCheckDao.rowsSize(map, null);
		page.setTotalRecord(totalRecord);
		page.setResults(midCheckDao.findByMap(map, page, null));
		return page;
	}

	@Override
	public BasePage<StuStageFile> findStuOpeningReport(String teaId,
			String stuNum, String stuName, String status, BasePage<StuStageFile> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(teaId)){
			map.put("teaId", teaId);			
		}
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);			
		}
		if(StringUtils.isNotBlank(stuName)){
			map.put("stuName", stuName);			
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		
		int totalRecord = openingReportDao.rowsSize(map, null);
		page.setTotalRecord(totalRecord);
		page.setResults(openingReportDao.findByMap(map, page, null));
		return page;
	}

	@Override
	public BasePage<StuStageFile> findStuReplyNote(String teaId,
			String stuNum, String stuName, String status, BasePage<StuStageFile> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(teaId)){
			map.put("teaId", teaId);			
		}
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);			
		}
		if(StringUtils.isNotBlank(stuName)){
			map.put("stuName", stuName);			
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		
		int totalRecord = replyNoteDao.rowsSize(map, null);
		page.setTotalRecord(totalRecord);
		page.setResults(replyNoteDao.findByMap(map, page, null));
		return page;
	}

	@Override
	public BasePage<StuStageFile> findStuTaskBook(String teaId, String stuNum,
			String stuName, String status, BasePage<StuStageFile> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(teaId)){
			map.put("teaId", teaId);			
		}
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);			
		}
		if(StringUtils.isNotBlank(stuName)){
			map.put("stuName", stuName);			
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		
		int totalRecord = taskBookDao.zdTeaFindNumByMap(map);
		page.setTotalRecord(totalRecord);
		page.setResults(taskBookDao.zdTeaFindByMap(map, page));
		return page;
	}

	@Override
	public BasePage<StuStageFile> findStuTitle(String teaId, String stuNum,
			String stuName, String status, BasePage<StuStageFile> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(teaId)){
			map.put("teaId", teaId);			
		}
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);			
		}
		if(StringUtils.isNotBlank(stuName)){
			map.put("stuName", stuName);			
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		
		int totalRecord = applytitleDao.rowsSize(map, null);
		page.setTotalRecord(totalRecord);
		page.setResults(applytitleDao.findStuTitleByMap(map, page, null));
		return page;
	}

	@Override
	public BasePage<Student> checkStudentReqPage(Map<String, Object> map,
			BasePage<Student> page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeacherTitle> findTeaTitleByTeaId(String teaId) {
		List<TeacherTitle> teacherTitles=teacherTitleDao.findTeaTitleByTeaId(teaId);
		return teacherTitles;
	}




	@Override
	public BasePage<TeacherTitle> findTeaTitle(Map<String, Object> map,
			BasePage<TeacherTitle> page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delTeaTitle(String id) {
		int i = teacherTitleDao.deleteById(id);
		return i;
	}
	
//添加教师课题
	@Override
	public int addTeaTitle(TeacherTitle teacherTitle) {
		int i=teacherTitleDao.insert(teacherTitle);
		return i;
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
	public int checkTeaTitle(String titleName) {
		int i =teacherTitleDao.selectTeaTitleByName(titleName);
		return i;
	}

	@Override
	public TeacherTitle findTeaTitleByTitleid(String titleid) {
		TeacherTitle teacherTitle=teacherTitleDao.selectById(titleid);
		return teacherTitle;
	}

	@Override
	public BasePage<Student> findStuTitle(Map<String, Object> map,BasePage<Student> page) {
		
		int totalRecord = studentDao.findStuTitleNum(map);		
		page.setTotalRecord(totalRecord);
		page.setResults(studentDao.findStuTitle(map,page));		
		return page;
	}

	@Override
	public Student findStuById(String stuId) {
	Student student=studentDao.findStuByStuId(stuId);
		return student;
	}

	@Override
	public ApplyTitle findApplyTitleById(String applyTitleId) {
		ApplyTitle applyTitle=applytitleDao.findById(applyTitleId);
		return applyTitle;
	}

	@Override
	public int checkApplyTitle(List<String> idList, String status, String teaIdea) {
		for (int i = 0; i < idList.size(); i++) {
			ApplyTitle at = applytitleDao.findById(idList.get(i));
			Teacher t = teacherDao.findByTeaId(at.getTeaId());
			Student stu = studentDao.selectStuByUserId(at.getStuId());	
			if("0".equals(at.getTitleSource())){//0：教师课题
				if("1".equals(status)){//教师课题，通过
					int total = gradeDao.selectByStuId(at.getStuId());//成绩表中有无学生			
					if(total==0){//成绩表中没有该学生，创建
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
					studentDao.updateTitleByMap(at.getStuId(), map);//把课题放入学生表中
					
					Paper p = new Paper();
					p.setTitle(at.getTitle());
					p.setTitleForm(at.getTitleForm());
					p.setTitleNature(at.getNature());
					p.setTitleReason(at.getTitleReason());
					p.setZdTeacher(t.getTeaName());
					p.setZdTeaNum(t.getTeaNum());
					p.setZdZhiCheng(t.getZhicheng());
					p.setStuNum(stu.getUserNum());
					paperDao.updatePaper(p);				
				}	
				if("2".equals(status)){					
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("applyTitleName", at.getTitle());
					teacherTitleDao.updateTitleChoose(m);
				}
			}else{
				//指导老师审核通过，添加专业负责人的待办事项提示
				if("1".equals(status)){//教师课题，通过					
						List<BackLog> backLogs = publicSer.findBackLog(null,stu.getMajor(),"leader");
						Boolean bool = false;
						if(!backLogs.isEmpty()){
							for(int q=0;q<backLogs.size();q++){
								if(backLogs.get(q).getBackLog().equals("stuApplyTitle")){
									int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
									bool=true;						
									break;
								}
							}		
						}
						if(!bool){		
							BackLog backLog = new BackLog();
							backLog.setId(UuidHelper.getRandomUUID());
							backLog.setBackLog("stuApplyTitle");
							backLog.setMajor(stu.getMajor());
							backLog.setTeaStatus("1");
							backLog.setLeaderStatus("0");
							backLog.setCreateTime(new Date());
							backLog.setCreateUser(t.getTeaName());
							int b = publicSer.insertBackLog(backLog);
							int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
						}					
				}
			}
			if("2".equals(status)){	
				String Status = "unApply";
				int m = teacherDao.updateTeacherNowStuNum(at.getTeaId(),Status);
			}
			//添加待办事项，通知学生进度更新
			List<BackLog> backLogs = publicSer.findBackLog(at.getStuId(),null,"student");
			Boolean bool = false;
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("reStuApplyTitle")){
						int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
						bool=true;						
						break;
					}
				}		
			}
			if(!bool){		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setStuId(at.getStuId());
				backLog.setBackLog("reStuApplyTitle");
				backLog.setStuStatus("0");
				backLog.setCreateTime(new Date());
				backLog.setCreateUser(at.getTeaName());
				int b = publicSer.insertBackLog(backLog);
				int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
			}
		}
		int i = applytitleDao.zdTeacherCheckApplyTitle(idList, status, teaIdea);
		
		return i;
	}

	@Override
	public List<DbGroup> findDbGroupByTeaId(String teaId) {
		 List<DbGroup> dbGroups =dbGroupDao.findDbGroupByTeaId(teaId);
		 for(int i=0;i<dbGroups.size();i++){
			 Teacher tea = teacherDao.selectByUserId(dbGroups.get(i).getGroupSecretary());
			 dbGroups.get(i).setGroupSecretaryName(tea.getTeaName());//设置答辩秘书姓名
			 
			 tea=teacherDao.selectByUserId(dbGroups.get(i).getGroupLeader());
			 dbGroups.get(i).setGroupLeaderName(tea.getTeaName());//设置答辩组长姓名
		 }	 
		return dbGroups;
		}

	@Override
	public List<Student> findStuByDbId(String dbId) {
		List<Student> students=studentDao.findStuByDbId(dbId);
		return students;
	}

	@Override
	public List<Teacher> findTeaByDbId(String dbId) {
		List<Teacher> teachers=teacherDao.findTeaByDbId(dbId);
		return teachers;
	}

	@Override
	public List<Student> findStuByKey(Map<String, Object> map) {
		List<Student> students=studentDao.findStuByKey(map);
		return students;
	}

	@Override
	public TaskBook findTaskBookbyStuId(String stuId) {
		TaskBook taskBook=taskBookDao.findTaskByStuId(stuId);
		return taskBook;
	}

	@Override
	public List<OpeningReport> findOpeningReportByKey(String teaId,
			String status) {
		List<OpeningReport> openingReports=openingReportDao.findOpeningReportByKey(teaId,status);
		
		return openingReports;
	}

	@Override
	public Student findStuForTeacher(Map<String, Object> map) {
		Student student=studentDao.findStuForTeacher(map);
		return student;
	}

	@Override
	public List<TaskBook> findTaskbookByKey(String teaId, String status) {
		 List<TaskBook> taskBooks=taskBookDao.findTaskbookByKey(teaId,status);
		return taskBooks;
	}

	@Override
	public Student findStuForTaskBook(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MidCheck> findmidCheckByKey(String teaId, String status) {
		List<MidCheck> midChecks=midCheckDao.findMidCheckByKey(teaId,status);
		return midChecks;
	}

	@Override
	public List<FirstPaper> findFirstPaperByKey(String teaId, String status) {
		List<FirstPaper> firstPapers=firstPaperDao.findFirstPaperByKey(teaId,status);
		return firstPapers;
	}

	@Override
	public List<FinalPaper> findFinalPapersByKey(String teaId, String status) {
		List<FinalPaper> finalPapers=finalPaperDao.findFinalPapersByKey(teaId,status);
		return finalPapers;
	}

	@Override
	public int updataPaper(Paper paper) {
		int i = paperDao.updatePaper(paper);
		return i;
	}

	@Override
	public int updateFinalPaperZdStatus(List<String> list, String status) {
		int i = finalPaperDao.updateStatus(list, status);
		for (int j = 0; j < list.size(); j++) {
			FinalPaper fp = new FinalPaper();
			fp = finalPaperDao.selectById(list.get(j));
			Student stu = new Student();
			stu = studentDao.selectStuByUserId(fp.getStuId());
			Paper p = new Paper();
			p.setFinalPaper(fp.getFinalPaperPath());
			p.setStuNum(stu.getUserNum());
			paperDao.updatePaper(p);
		}
		return i;
	}

	@Override
	public int updateFirstPaperZdStatus(List<String> list, String status) {
		int i = firstPaperDao.updateStatus(list, status);
		for (int j = 0; j < list.size(); j++) {
			FirstPaper fp = new FirstPaper();
			fp = firstPaperDao.selectById(list.get(j));
			Student stu = new Student();
			stu = studentDao.selectStuByUserId(fp.getStuId());
			Paper p = new Paper();
			p.setFirstPaper(fp.getFirstPaperPath());
			p.setStuNum(stu.getUserNum());
			paperDao.updatePaper(p);
		}
		return i;
	}

	@Override
	public int updateMidCheckZdStatus(List<String> list, String status) {
		int i = midCheckDao.updateStatus(list, status);
		for (int j = 0; j < list.size(); j++) {
			MidCheck mc = new MidCheck();
			mc = midCheckDao.selectById(list.get(j));
			Student stu = new Student();
			stu = studentDao.selectStuByUserId(mc.getStuId());
			Paper p = new Paper();
			p.setMidCheck(mc.getMidCheckPath());
			p.setStuNum(stu.getUserNum());
			paperDao.updatePaper(p);
		}
		return i;
	}

	@Override
	public int updateOpeningReportZdStatus(List<String> list, String status) {
		int i = openingReportDao.updateStatus(list, status);
		return i;
	}

	@Override
	public int updateTaskBookZdStatus(List<String> list, String status) {
		int i = taskBookDao.updateStatus(list, status);
		return i;
	}

	@Override
	public BasePage<Student> zdTeaFindPyStu(String pyTeaId, BasePage<Student> page) {
		int totalRecord = studentDao.zdTeaFindPyStuNum(pyTeaId);
		page.setTotalRecord(totalRecord);
		page.setResults(studentDao.zdTeaFindPyStu(pyTeaId, page));
		return page;
	}
	
	@Override
	public BasePage<Paper> findStuPaperInfo(String stuNum, String stuName, String zdTeaNum, BasePage<Paper> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("term", SysConfig.getValue("term"));
		if(StringUtils.isNotBlank(stuNum)){
			map.put("stuNum", stuNum);
		}
		if(StringUtils.isNotBlank(stuName)){
			map.put("stuName", stuName);
		}
		if(StringUtils.isNotBlank(zdTeaNum)){
			map.put("zdTeaNum", zdTeaNum);
		}
		int totalRecord = paperDao.rowsSize(map, null);
		List<Paper> results = paperDao.findPage(map, page, null);
		page.setTotalRecord(totalRecord);
		page.setResults(results);
		
		return page;
	}

	@Override
	public List<Paper> findStuAllFile(Map<String, Object> map) {
		 List<Paper> papers=paperDao.findStuFileForteacher(map);
		return papers;
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

	@Override
	public MidCheck findMidCheckById(String id) {
		MidCheck midCheck = midCheckDao.selectById(id);
		return midCheck;
	}

	@Override
	public FirstPaper findFirstPaperById(String id) {
		FirstPaper firstPaper = firstPaperDao.selectById(id);
		return firstPaper;
	}

	@Override
	public FinalPaper findFinalPaperById(String id) {
		FinalPaper finalPaper = finalPaperDao.selectById(id);
		return finalPaper;
	}

	
}
