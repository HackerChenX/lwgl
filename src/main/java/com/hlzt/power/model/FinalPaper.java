package com.hlzt.power.model;

import java.util.Date;

import com.hlzt.commons.model.BaseBean;

/**
 * 定稿
 * @author user
 *
 */
public class FinalPaper extends BaseBean{
	private String stuId;//学生ID
	private String teaId;//教师ID
	private String teaStatus;//教师审批状态（0待审核  1通过  2驳回）
	private String leaderStatus;//专业负责人审批状态（0待审核  1通过  2驳回）
	private String finalPaperPath;//定稿路径
	private String finalPaperSrc;//定稿网络路径
	private String pyTablePath;//评阅意见表路径
	private String pyTableSrc;//评阅意见表网络路径
	private String syTablePath;//审阅意见表路径
	private String syTableSrc;//审阅意见表网络路径
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
	public Date getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}
	public String getFinalPaperPath() {
		return finalPaperPath;
	}
	public void setFinalPaperPath(String finalPaperPath) {
		this.finalPaperPath = finalPaperPath;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getFinalPaperSrc() {
		return finalPaperSrc;
	}
	public void setFinalPaperSrc(String finalPaperSrc) {
		this.finalPaperSrc = finalPaperSrc;
	}
	public String getPyTablePath() {
		return pyTablePath;
	}
	public void setPyTablePath(String pyTablePath) {
		this.pyTablePath = pyTablePath;
	}
	public String getPyTableSrc() {
		return pyTableSrc;
	}
	public void setPyTableSrc(String pyTableSrc) {
		this.pyTableSrc = pyTableSrc;
	}
	public String getSyTablePath() {
		return syTablePath;
	}
	public void setSyTablePath(String syTablePath) {
		this.syTablePath = syTablePath;
	}
	public String getSyTableSrc() {
		return syTableSrc;
	}
	public void setSyTableSrc(String syTableSrc) {
		this.syTableSrc = syTableSrc;
	}
	
	
}
