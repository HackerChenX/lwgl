package com.hlzt.power.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.model.BasePage;
import com.hlzt.power.dao.DbGroupDao;
import com.hlzt.power.dao.GradeDao;
import com.hlzt.power.dao.GradeWeightDao;
import com.hlzt.power.dao.PaperDao;
import com.hlzt.power.dao.StudentDao;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.GradeWeight;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.Student;
import com.hlzt.power.service.ZdTeacherGradeSer;

@Transactional
@Component
public class ZdTeacherGradeSerImpl implements ZdTeacherGradeSer {
	@Resource
	private GradeDao gradeDao;
	@Resource
	private DbGroupDao dbGroupDao;
	@Resource
	private StudentDao studentDao;
	@Resource
	private PaperDao paperDao;
	@Resource
	private GradeWeightDao gradeWeightDao;

	@Override
	public List<Grade> findGrade(Map<String, Object> map) {		
		List<Grade> grades=gradeDao.findStageGrade(map);	
		return grades;
	}

	@Override
	public List<DbGroup> checkDbMishu(Map<String, Object> map){
		List<DbGroup> dbGroups=dbGroupDao.checkDbMishu(map);
		return dbGroups;
	}

	@Override
	public List<Student> findStuByDbKey(Map<String, Object> map) {
		List<Student> students=studentDao.findStuByDbkey(map);
		return students;
	}

	@Override
	public Grade finStuGradeByStuId(String stuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasePage<Grade> findDbGrade(Map<String, Object> map,
			BasePage<Grade> page) {
		int totalRecord = gradeDao.findDbGradeNum(map);
		page.setTotalRecord(totalRecord);
		page.setResults(gradeDao.findDbGrade(map, page));
		return page;
	}

	@Override
	public BasePage<Grade> findPyGrade(Map<String, Object> map,
			BasePage<Grade> page) {
		int totalRecord = gradeDao.findPyGradeNum(map);
		page.setTotalRecord(totalRecord);
		page.setResults(gradeDao.findPyGrade(map, page));
		return page;
	}

	@Override
	public BasePage<Grade> findSyGrade(Map<String, Object> map,
			BasePage<Grade> page) {
		int totalRecord = gradeDao.findSyGradeNum(map);
		page.setTotalRecord(totalRecord);
		page.setResults(gradeDao.findSyGrade(map, page));
		return page;
	}

	@Override
	public int setDbGrade(float dbGrade, String stuNum) {
		Grade grade = gradeDao.selectGradeByStuNum(stuNum);
		Grade g = new Grade();
		GradeWeight gw = new GradeWeight();
		gw = gradeWeightDao.findGradeWeightByMajor(grade.getStuMajor());
		float syWeight = gw.getZdPingfen();
		float pyWeight = gw.getPyPingfen();
		float dbWeight = gw.getDbPingfen();
		float finalGrade = syWeight*grade.getSyGrade()+pyWeight*grade.getPyGrade()+dbWeight*dbGrade;
		g.setStuNum(stuNum);
		g.setSyGrade(grade.getPyGrade());
		g.setPyGrade(grade.getPyGrade());
		g.setDbGrade(dbGrade);
		g.setFinalGrade(finalGrade);
		if(finalGrade>=85){
			g.setEvaluate("优秀");
		}else if(70<=finalGrade&&finalGrade<85){
			g.setEvaluate("良好");
		}else if(60<=finalGrade&&finalGrade<70){
			g.setEvaluate("及格");
		}else{
			g.setEvaluate("不及格");
		}
		int i = gradeDao.updateByStuId(g);
		if(i!=0){
			Paper p = new Paper();
			p.setStuNum(stuNum);
			p.setSyGrade(grade.getPyGrade());
			p.setPyGrade(grade.getPyGrade());
			p.setDbGrade(dbGrade);
			p.setFinalGrade(finalGrade);
			if(finalGrade>=85){
				p.setEvaluate("优秀");
			}else if(70<=finalGrade&&finalGrade<85){
				p.setEvaluate("良好");
			}else if(60<=finalGrade&&finalGrade<70){
				p.setEvaluate("及格");
			}else{
				p.setEvaluate("不及格");
			}
			paperDao.updatePaper(p);
		}
		return i;
	}

	@Override
	public int setPyGrade(float pyGrade, String stuNum) {
		Grade grade = gradeDao.selectGradeByStuNum(stuNum);
		Grade g = new Grade();
		g.setStuNum(stuNum);
		g.setSyGrade(grade.getPyGrade());
		g.setPyGrade(pyGrade);
		g.setDbGrade(grade.getDbGrade());
		int i = gradeDao.updateByStuId(g);
		if(i!=0){
			Paper paper = paperDao.selectByStuNum(stuNum);
			Paper p = new Paper();
			p.setStuNum(stuNum);
			p.setSyGrade(grade.getPyGrade());
			p.setPyGrade(pyGrade);
			p.setDbGrade(paper.getDbGrade());
			p.setFinalGrade(paper.getFinalGrade());
			paperDao.updatePaper(p);
		}
		return i;
	}

	@Override
	public int setSyGrade(float syGrade, String stuNum) {
		Grade grade = gradeDao.selectGradeByStuNum(stuNum);
		Grade g = new Grade();
		g.setStuNum(stuNum);
		g.setSyGrade(syGrade);
		g.setPyGrade(grade.getPyGrade());
		g.setDbGrade(grade.getDbGrade());
		int i = gradeDao.updateByStuId(g);
		if(i!=0){
			Paper paper = paperDao.selectByStuNum(stuNum);
			Paper p = new Paper();
			p.setStuNum(stuNum);
			p.setSyGrade(syGrade);
			p.setPyGrade(paper.getPyGrade());
			p.setDbGrade(paper.getDbGrade());
			p.setFinalGrade(paper.getFinalGrade());
			paperDao.updatePaper(p);
		}
		return i;
	}

}
