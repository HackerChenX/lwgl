package com.hlzt.power.model;

import java.util.Date;

import com.hlzt.commons.model.BaseBean;

/**
 * 中期检查
 * @author user
 *
 */
public class MidCheck extends BaseBean{
	private String stuId;//学生ID
	private String teaId;//教师ID
	private String teaStatus;//教师审核状态（0待审核  1通过  2驳回）
	private String leaderStatus;//指导老师审核状态（0待审核  1通过  2驳回）
	private String midCheckPath;//中期检查表路径
	private Date limitTime;//限时
	
	private Student student;
	
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getTeaId() {
		return teaId;
	}
	public void setTeaId(String teaId) {
		this.teaId = teaId;
	}
	public String getTeaStatus() {
		return teaStatus;
	}
	public void setTeaStatus(String teaStatus) {
		this.teaStatus = teaStatus;
	}
	public String getLeaderStatus() {
		return leaderStatus;
	}
	public void setLeaderStatus(String leaderStatus) {
		this.leaderStatus = leaderStatus;
	}
	public String getMidCheckPath() {
		return midCheckPath;
	}
	public void setMidCheckPath(String midCheckPath) {
		this.midCheckPath = midCheckPath;
	}
	public Date getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	
}
