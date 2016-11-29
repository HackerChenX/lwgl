package com.hlzt.power.model;

import java.util.Date;

import com.hlzt.commons.model.BaseBean;

/**
 * 任务书
 * @author user
 *
 */
public class TaskBook extends BaseBean{
	private String stuId;//学生ID
	private String teaId;//教师ID
	private String mainTask;//毕业论文（设计）要求完成的主要任务及其时间安排
	private String zhiBiao;//毕业论文（设计）的主要技术指标
	private String yaoQiu;//毕业论文（设计）的基本要求及应完成的成果形式
	private String wenXian;//毕业论文（设计）应收集的资料及主要参考文献
	private String teaStatus;//教师审核状态（0待审核  1通过 2驳回）
	private String leaderStatus;//专业负责人审核状态（0待审核 1通过 2驳回）
	private String taskBookPath;//任务书路径
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
	public String getTaskBookPath() {
		return taskBookPath;
	}
	public void setTaskBookPath(String taskBookPath) {
		this.taskBookPath = taskBookPath;
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
	public String getMainTask() {
		return mainTask;
	}
	public void setMainTask(String mainTask) {
		this.mainTask = mainTask;
	}
	public String getZhiBiao() {
		return zhiBiao;
	}
	public void setZhiBiao(String zhiBiao) {
		this.zhiBiao = zhiBiao;
	}
	public String getYaoQiu() {
		return yaoQiu;
	}
	public void setYaoQiu(String yaoQiu) {
		this.yaoQiu = yaoQiu;
	}
	public String getWenXian() {
		return wenXian;
	}
	public void setWenXian(String wenXian) {
		this.wenXian = wenXian;
	}
	
	
}

