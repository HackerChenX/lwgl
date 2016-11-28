package com.hlzt.power.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ansj.domain.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.model.BasePage;
import com.hlzt.power.dao.BackLogDao;
import com.hlzt.power.dao.ClassNameDao;
import com.hlzt.power.dao.MajorDao;
import com.hlzt.power.dao.PaperDao;
import com.hlzt.power.dao.PublicNoticeDao;
import com.hlzt.power.dao.ZhiChengDao;
import com.hlzt.power.model.BackLog;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.PublicNotice;
import com.hlzt.power.model.ZhiCheng;
import com.hlzt.power.service.PublicSer;
@Transactional
@Component
public class PublicSerImpl implements PublicSer {
	
	@Resource
	ClassNameDao classNameDao;
	@Resource
	MajorDao majorDao;
	@Resource
	PaperDao paperDao;
	@Resource
	PublicNoticeDao publicNoticeDao;
	@Resource
	BackLogDao backLogDao;
	@Resource
	ZhiChengDao zhiChengDao;
	
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
	public List<Major> findMajor() {
		List<Major> list = new ArrayList<Major>();
		list = majorDao.selectMajor();
		return list;
	}

	@Override
	public Major findMajorByName(String majorName) {
		Major major = majorDao.selectByName(majorName);
		return major;
	}

	@Override
	public Major findMajorById(String id) {
		
		return majorDao.selectMajorById(id);
	}

	@Override
	public BasePage<Paper> findFormerTerm(Map<String, Object> map,
			BasePage<Paper> page) {
		
		int total = paperDao.rowsSize(map, null);
 		page.setTotalRecord(total);
		List<Paper> list = null;
		List<Paper> pageList = new ArrayList<Paper>();		
		try{
			list  = paperDao.findPage(map, page,null);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(!(list.isEmpty())){
			for(int i=0;i<list.size();i++){
				if(StringUtils.isNotBlank(list.get(i).getTitle())){
					pageList.add(list.get(i));
				}
			}
		}	
		if(pageList.size()<1){
			page.setTotalRecord(0);
		}
		page.setResults(pageList);		
		return page;
	}

	@Override
	public Paper findFormTermById(String id) {
		Paper paper = paperDao.selectById(id);
		return paper;
	}

	@Override
	public List<PublicNotice> findNotice(String noticeType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("noticeType", noticeType);
		List<PublicNotice> list = publicNoticeDao.selectNotice(map);
		return list;
	}

	@Override
	public PublicNotice findNoticeById(String id) {
		PublicNotice publicNotice = publicNoticeDao.selectById(id);
		return publicNotice;
	}

	@Override
	public int insertBackLog(BackLog backLog) {
		int i =backLogDao.insert(backLog);
		return i;
	}

	@Override
	public List<BackLog> findBackLog(String userId,String major,String role) {
		List<BackLog> list = backLogDao.selectBackLog(userId,major,role);
		return list;
	}

	@Override
	public int removeBackLog(String id) {
		int i = backLogDao.deleteById(id);
		return i;
	}
	
	@Override
	public List<BackLog> findBackLogOfDelay(String stuId,String teaId,String backLogInfo){
		List<BackLog> list = backLogDao.selectBackLogOfDelay(stuId,teaId,backLogInfo);
		return list;
	}

	@Override
	public int updateBackLogNumById(String id,String status) {
		int i = backLogDao.updateBackLogNumById(id,status);
		return i;
	}

	@Override
	public List<BackLog> findBackLogByType(String status) {
		List<BackLog> list = backLogDao.findBackLogByType(status);
		return list;
	}

	@Override
	public List<ZhiCheng> findZhiCheng() {
		List<ZhiCheng> list = new ArrayList<ZhiCheng>();
		list = zhiChengDao.selectZhiCheng();
		return list;
	}

	@Override
	public List<Paper> findRepeatByAnsj(List<String> keyWords,String nowTerm,String threeTerm){
		List<Paper> papers = paperDao.findRepeatByAnsj(keyWords,nowTerm,threeTerm);			
		return papers;
	}

}
