package com.hlzt.power.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.helper.UuidHelper;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.dao.ApplyDelayDao;
import com.hlzt.power.dao.ApplyTitleDao;
import com.hlzt.power.dao.BackLogDao;
import com.hlzt.power.dao.ClassNameDao;
import com.hlzt.power.dao.DbGroupDao;
import com.hlzt.power.dao.FinalPaperDao;
import com.hlzt.power.dao.FirstPaperDao;
import com.hlzt.power.dao.GradeDao;
import com.hlzt.power.dao.GradeWeightDao;
import com.hlzt.power.dao.MajorDao;
import com.hlzt.power.dao.MidCheckDao;
import com.hlzt.power.dao.OpeningReportDao;
import com.hlzt.power.dao.PublicNoticeDao;
import com.hlzt.power.dao.ReplyNoteDao;
import com.hlzt.power.dao.StagePlanDao;
import com.hlzt.power.dao.StudentDao;
import com.hlzt.power.dao.TaskBookDao;
import com.hlzt.power.dao.TeacherDao;
import com.hlzt.power.dao.TeacherTitleDao;
import com.hlzt.power.dao.TitleFormDao;
import com.hlzt.power.dao.TitleNatureDao;
import com.hlzt.power.dao.UserDao;
import com.hlzt.power.dao.UserRoleDao;
import com.hlzt.power.dao.ZhiChengDao;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.GradeWeight;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.StagePlan;
import com.hlzt.power.model.TitleForm;
import com.hlzt.power.model.TitleNature;
import com.hlzt.power.model.ZhiCheng;
import com.hlzt.power.service.SecretaryInitialManageSer;
@Transactional
@Component
public class SecretaryInitialManageSerImp implements SecretaryInitialManageSer {
	@Resource
	ClassNameDao classNameDao;
	@Resource
	MajorDao majorDao;
	@Resource
	TitleNatureDao titleNatureDao;
	@Resource
	StagePlanDao stagePlanDao;
	@Resource
	GradeWeightDao gradeWeightDao;
	@Resource
	TitleFormDao titleFormDao;
	@Resource
	ApplyDelayDao applyDelayDao;
	@Resource
	ApplyTitleDao applyTitleDao;
	@Resource
	DbGroupDao dbGroupDao;
	@Resource
	FinalPaperDao finalPaperDao;
	@Resource
	FirstPaperDao firstPaperDao;
	@Resource
	GradeDao gradeDao;
	@Resource
	MidCheckDao midCheckDao;
	@Resource
	OpeningReportDao openingReportDao;
	@Resource
	ReplyNoteDao replyNotDao;
	@Resource
	StudentDao studentDao;
	@Resource
	TaskBookDao taskBookDao;
	@Resource
	TeacherDao teacherDao;
	@Resource
	TeacherTitleDao teacherTitleDao;
	@Resource
	UserDao userDao;
	@Resource
	UserRoleDao userRoleDao;
	@Resource
	ZhiChengDao zhiChengDao;
	@Resource
	PublicNoticeDao publicNoticeDao;
	@Resource
	BackLogDao backLogDao;
	@Override
	public int addClass(ClassName className) {
		int i = classNameDao.insert(className);
		return i;
	}

	@Override
	public int addMajor(Major major) {
		int i = majorDao.insert(major);
		return i;
	}

	@Override
	public List<Major> findMajor() {
		List<Major> list = new ArrayList<Major>();
		list = majorDao.selectMajor();
		return list;
	}

	@Override
	public List<ClassName> findClass(String majorId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(majorId)){
			map.put("majorId", majorId);
		}
		List<ClassName> list = new ArrayList<ClassName>();
		list = classNameDao.selectClass(map);
		return list;
	}

	@Override
	public int deleteClass(List<String> list) {
		int i = classNameDao.deleteByIds(list);
		return i;
	}

	@Override
	public int deleteMajor(List<String> list) {
		for (int j = 0; j < list.size(); j++) {
			Major major = majorDao.selectMajorById(list.get(j));
			gradeWeightDao.deleteGradeWeight(major.getMajorName());
		}
		int i = majorDao.deleteByIds(list);
		return i;
	}
	
	@Override
	public int addTitleNature(TitleNature titleNature) {
		int i = titleNatureDao.insert(titleNature);
		return i;
	}

	@Override
	public List<TitleNature> findTitleNature(Map<String, Object> map) {
		
		List<TitleNature> list = new ArrayList<TitleNature>();
		list = titleNatureDao.selectNature();
		return list;
	}

	@Override
	public int deleteTitleNature(List<String> list) {
		int i = titleNatureDao.deleteByIds(list);
		return i;
	}

	@Override
	public int setStageTime(String stageName, Date startTime, Date endTime) {
		int i = stagePlanDao.updateStagePlan(stageName, startTime, endTime);
		
		return i;
	}

	@Override
	public StagePlan findStagePlan(String stageName) {
		StagePlan stagePlan = stagePlanDao.findStagePlanByStageName(stageName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		if(stagePlan!=null){
			if(stagePlan.getStartTime()!=null){
				String startTimeStr = sdf.format(stagePlan.getStartTime());
				stagePlan.setStartTimeStr(startTimeStr);
			}
			if(stagePlan.getEndTime()!=null){
				String endTimeStr = sdf.format(stagePlan.getEndTime());
				stagePlan.setEndTimeStr(endTimeStr);
			}
		}
		
		return stagePlan;
	}

	@Override
	public List<GradeWeight> findGradeWeight() {
		List<GradeWeight> list = gradeWeightDao.findGradeWeight();
		return list;
	}

	@Override
	public int setGradeWeight(List<String> list, float zdPingfen, float pyPingfen, float dbPingfen) {
		int i = gradeWeightDao.setGradeWeight(list, zdPingfen, pyPingfen, dbPingfen);
		return i;
	}

	@Override
	public int cancelSetGradeWeight(List<String> list) {
		int i = gradeWeightDao.cancelSetGradeWeight(list);
		return i;
	}

	@Override
	public int addGradeWeighr(GradeWeight gradeWerght) {
		int i = gradeWeightDao.insert(gradeWerght);
		return i;
	}

	@Override
	public int deleteGradeWeighr(List<String> list) {
		//int i = gradeWeightDao.deleteGradeWeight(list);
		return 0;
	}

	@Override
	public int findMajorByName(String majorName) {
		int i = majorDao.selectMajorByName(majorName);
		return i;
	}

	@Override
	public int findClassNum(String cName) {
		int i = classNameDao.findClassNum(cName);
		return i;
	}

	@Override
	public int addTitleForm(TitleForm titleForm) {
		int i = titleFormDao.insert(titleForm);
		return i;
	}

	@Override
	public int deleteTitleForm(List<String> list) {
		int i = titleFormDao.deleteFrom(list);
		return i;
	}

	@Override
	public List<TitleForm> findTitleForm(Map<String, Object> map) {
		List<TitleForm> list = new ArrayList<TitleForm>();
		list = titleFormDao.selectForm();
		return list;
	}

	@Override
	public int dataSave() {
		int i = 0;
		applyDelayDao.deleteAll();
		applyTitleDao.deleteAll();
		dbGroupDao.deleteAll();
		finalPaperDao.deleteAll();
		firstPaperDao.deleteAll();
		gradeDao.deleteAll();
		midCheckDao.deleteAll();
		openingReportDao.deleteAll();
		replyNotDao.deleteAll();
		taskBookDao.deleteAll();
		teacherTitleDao.deleteAll();
		teacherDao.resetTea();
		studentDao.deleteAll();
		userRoleDao.deleteStuRole();
		publicNoticeDao.deleteAll();
		backLogDao.deleteAll();
		i = userDao.deleteStuUser();
		
		return i;
	}
	
	@Override
	public int addZhiCheng(ZhiCheng zhiCheng) {
		int i = zhiChengDao.insert(zhiCheng);
		return i;
	}

	@Override
	public List<ZhiCheng> findZhiCheng() {
		List<ZhiCheng> list = new ArrayList<ZhiCheng>();
		list = zhiChengDao.selectZhiCheng();
		return list;
	}
	
	@Override
	public int deleteZhiCheng(List<String> list) {
		int i = zhiChengDao.deleteByIds(list);
		return i;
	}

	@Override
	public int inserStageTime(StagePlan stagePlan){
		int i = stagePlanDao.insert(stagePlan);
		return i;
	}	
	
}
