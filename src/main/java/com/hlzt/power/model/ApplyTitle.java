package com.hlzt.power.model;

import java.util.Date;

import com.hlzt.commons.model.BaseBean;

/**
 * 申报题目
 * @author user
 *
 */
public class ApplyTitle extends BaseBean{
	private String stuId;//学生ID(对应用户表ID)
	private String teaId;//教师ID
	private String title;//题目
	private String nature;//题目性质
	private String titleForm;//完成形式
	private String titleSource;//课题来源(0教师课题 1学生自拟课题)
	private String titleReason;//立题理由
	private String teaStatus;//指导老师审批状态（0待审核  1通过  2驳回）
	private String teaIdea;
	private String leaderStatus;//专业负责人审批状态（0待审核  1通过  2驳回）
	private String leaderIdea;
	private String managerStatus;//教学秘书审批状态(0待审核  1通过  2驳回)
	private String managerIdea;
	private Date limitTime;//限时
	
	
	private String stuName; //学生姓名
	private String stuNum;  //学号
	private String teaName; //导师姓名
	private String teaMajor;//导师专业
	private String stuMajor;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getTitleForm() {
		return titleForm;
	}
	public void setTitleForm(String titleForm) {
		this.titleForm = titleForm;
	}
	public String getTitleSource() {
		return titleSource;
	}
	public void setTitleSource(String titleSource) {
		this.titleSource = titleSource;
	}
	public String getTitleReason() {
		return titleReason;
	}
	public void setTitleReason(String titleReason) {
		this.titleReason = titleReason;
	}
	public String getTeaStatus() {
		return teaStatus;
	}
	public void setTeaStatus(String teaStatus) {
		this.teaStatus = teaStatus;
	}
	public String getTeaIdea() {
		return teaIdea;
	}
	public void setTeaIdea(String teaIdea) {
		this.teaIdea = teaIdea;
	}
	public String getLeaderStatus() {
		return leaderStatus;
	}
	public void setLeaderStatus(String leaderStatus) {
		this.leaderStatus = leaderStatus;
	}
	public String getLeaderIdea() {
		return leaderIdea;
	}
	public void setLeaderIdea(String leaderIdea) {
		this.leaderIdea = leaderIdea;
	}
	public String getManagerStatus() {
		return managerStatus;
	}
	public void setManagerStatus(String managerStatus) {
		this.managerStatus = managerStatus;
	}
	public String getManagerIdea() {
		return managerIdea;
	}
	public void setManagerIdea(String managerIdea) {
		this.managerIdea = managerIdea;
	}
	public Date getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getStuNum() {
		return stuNum;
	}
	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}
	public String getTeaName() {
		return teaName;
	}
	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}
	public String getStuMajor() {
		return stuMajor;
	}
	public void setStuMajor(String stuMajor) {
		this.stuMajor = stuMajor;
	}
	public String getTeaMajor() {
		return teaMajor;
	}
	public void setTeaMajor(String teaMajor) {
		this.teaMajor = teaMajor;
	}
	
	
		
}
