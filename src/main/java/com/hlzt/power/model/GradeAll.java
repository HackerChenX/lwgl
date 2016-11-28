package com.hlzt.power.model;
/**
 * 成绩总评
 * @author user
 *
 */
public class GradeAll {
	private String majorName;//专业名字
	private int majorStuNum;//专业学生数量
	private int majorValidStuNum;//专业有效学生数
	private int excellentNum;//优秀学生数
	private int wellNum;//良好学生数
	private int mediumNum;//中等学生数
	private int passNum;//及格学生数
	private int notPassNum;//不及格学生数
	private String excellentRates;//>90百分比
	private String wellRates;//80-89百分比
	private String mediumRates;//70-79百分比
	private String passRates;//60-69百分比
	private String notPassRates;//不及格率
	
	
	
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public int getMajorStuNum() {
		return majorStuNum;
	}
	public void setMajorStuNum(int majorStuNum) {
		this.majorStuNum = majorStuNum;
	}
	public int getMajorValidStuNum() {
		return majorValidStuNum;
	}
	public void setMajorValidStuNum(int majorValidStuNum) {
		this.majorValidStuNum = majorValidStuNum;
	}
	public int getExcellentNum() {
		return excellentNum;
	}
	public void setExcellentNum(int excellentNum) {
		this.excellentNum = excellentNum;
	}
	public int getWellNum() {
		return wellNum;
	}
	public void setWellNum(int wellNum) {
		this.wellNum = wellNum;
	}
	public int getMediumNum() {
		return mediumNum;
	}
	public void setMediumNum(int mediumNum) {
		this.mediumNum = mediumNum;
	}
	public int getPassNum() {
		return passNum;
	}
	public void setPassNum(int passNum) {
		this.passNum = passNum;
	}
	public int getNotPassNum() {
		return notPassNum;
	}
	public void setNotPassNum(int notPassNum) {
		this.notPassNum = notPassNum;
	}
	public String getExcellentRates() {
		return excellentRates;
	}
	public void setExcellentRates(String excellentRates) {
		this.excellentRates = excellentRates;
	}
	public String getPassRates() {
		return passRates;
	}
	public void setPassRates(String passRates) {
		this.passRates = passRates;
	}
	public String getNotPassRates() {
		return notPassRates;
	}
	public void setNotPassRates(String notPassRates) {
		this.notPassRates = notPassRates;
	}
	public String getMediumRates() {
		return mediumRates;
	}
	public void setMediumRates(String mediumRates) {
		this.mediumRates = mediumRates;
	}
	public String getWellRates() {
		return wellRates;
	}
	public void setWellRates(String wellRates) {
		this.wellRates = wellRates;
	}

	
	
	
	
	
	
	
	
}
