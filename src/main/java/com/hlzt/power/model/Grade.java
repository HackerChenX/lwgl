package com.hlzt.power.model;

import com.hlzt.commons.model.BaseBean;

/**
 * 成绩表
 * @author user
 *
 */
public class Grade extends BaseBean{
	private String stuId;//学生ID
	private String stuName;
	private String stuSex;
	private String zdTeacher;//指导老师
	private String zdZhiCheng;//指导老师职称
	private String zdTeacherName;
	private String pyTeacher;//评阅老师
	private String pyZhiCheng;//评阅老师职称
	private String pyTeacherName;
	private String stuNum;//学生学号
	private String stuMajor;//学生专业
	private String stuClass;//学生班级
	private String title;//题目
	private float pyGrade;//评阅成绩
	private float syGrade;//审阅成绩
	private float dbGrade;//答辩成绩
	private float finalGrade;//最终成绩
	private String evaluate;//评价
	private String recommend;//是否推荐为优秀（0、否  1、是）
	
	private Student student;
	
	
	public String getZdTeacher() {
		return zdTeacher;
	}
	public void setZdTeacher(String zdTeacher) {
		this.zdTeacher = zdTeacher;
	}
	public String getZdTeacherName() {
		return zdTeacherName;
	}
	public void setZdTeacherName(String zdTeacherName) {
		this.zdTeacherName = zdTeacherName;
	}
	public String getStuNum() {
		return stuNum;
	}
	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}
	public String getStuMajor() {
		return stuMajor;
	}
	public void setStuMajor(String stuMajor) {
		this.stuMajor = stuMajor;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	
	public float getPyGrade() {
		return pyGrade;
	}
	public void setPyGrade(float pyGrade) {
		this.pyGrade = pyGrade;
	}
	public float getSyGrade() {
		return syGrade;
	}
	public void setSyGrade(float syGrade) {
		this.syGrade = syGrade;
	}
	public float getDbGrade() {
		return dbGrade;
	}
	public void setDbGrade(float dbGrade) {
		this.dbGrade = dbGrade;
	}
	public float getFinalGrade() {
		return finalGrade;
	}
	public void setFinalGrade(float finalGrade) {
		this.finalGrade = finalGrade;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getPyTeacher() {
		return pyTeacher;
	}
	public void setPyTeacher(String pyTeacher) {
		this.pyTeacher = pyTeacher;
	}
	public String getPyTeacherName() {
		return pyTeacherName;
	}
	public void setPyTeacherName(String pyTeacherName) {
		this.pyTeacherName = pyTeacherName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStuClass() {
		return stuClass;
	}
	public void setStuClass(String stuClass) {
		this.stuClass = stuClass;
	}
	public String getStuSex() {
		return stuSex;
	}
	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getZdZhiCheng() {
		return zdZhiCheng;
	}
	public void setZdZhiCheng(String zdZhiCheng) {
		this.zdZhiCheng = zdZhiCheng;
	}
	public String getPyZhiCheng() {
		return pyZhiCheng;
	}
	public void setPyZhiCheng(String pyZhiCheng) {
		this.pyZhiCheng = pyZhiCheng;
	}
	
	
	
	
}
