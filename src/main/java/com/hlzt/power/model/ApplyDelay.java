package com.hlzt.power.model;

import java.util.Date;

import com.hlzt.commons.model.BaseBean;

/**
 * @ClassName: ApplyDelay
 * @Description: 延期申請
 * @author cxy
 *
 */
public class ApplyDelay extends BaseBean{
	
	private String stuId;//学生ID(对应用户表user_id)
	private String teaId;//教师ID(对应用户表user_id)
	private String delayStage;//延期阶段
	private Date delayTime;//延期截止时间
	private String delayTimeStr;
	private String delayReason;
	private String teaStatus;//指导老师状态(0未审核1通过2驳回)
	private String teaIdea;//指导老师意见
	private String managerStatus;//教学秘书状态(0未审核1通过2驳回)
	private String managerIdea;//教学秘书意见
	

	private String sumStatus;//记录0,1转换的最终状态(临时的)
	
	private Student student;
	

	private String stuNum;
	private String stuName;
	private String teaName;
	

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
	public String getDelayStage() {
		return delayStage;
	}
	public void setDelayStage(String delayStage) {
		this.delayStage = delayStage;
	}
	public Date getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(Date delayTime) {
		this.delayTime = delayTime;
	}
	public String getTeaStatus() {
		return teaStatus;
	}
	public void setTeaStatus(String teaStatus) {
		this.teaStatus = teaStatus;
	}
	public String getManagerStatus() {
		return managerStatus;
	}
	public void setManagerStatus(String managerStatus) {
		this.managerStatus = managerStatus;
	}

	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getSumStatus() {
		return sumStatus;
	}
	public void setSumStatus(String sumStatus) {
		this.sumStatus = sumStatus;
	}

	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getTeaName() {
		return teaName;
	}
	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}
	public String getTeaIdea() {
		return teaIdea;
	}
	public void setTeaIdea(String teaIdea) {
		this.teaIdea = teaIdea;
	}
	public String getManagerIdea() {
		return managerIdea;
	}
	public void setManagerIdea(String managerIdea) {
		this.managerIdea = managerIdea;
	}
	public String getStuNum() {
		return stuNum;
	}
	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}
	public String getDelayReason() {
		return delayReason;
	}
	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}
	public String getDelayTimeStr() {
		return delayTimeStr;
	}
	public void setDelayTimeStr(String delayTimeStr) {
		this.delayTimeStr = delayTimeStr;
	}

	
	
	
}