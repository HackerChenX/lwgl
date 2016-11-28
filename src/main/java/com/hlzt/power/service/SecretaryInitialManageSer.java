package com.hlzt.power.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.GradeWeight;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.StagePlan;
import com.hlzt.power.model.TitleForm;
import com.hlzt.power.model.TitleNature;
import com.hlzt.power.model.ZhiCheng;

/**
 * 教学秘书初始管理
 * @author user
 *
 */
public interface SecretaryInitialManageSer {
	
	/**
	 * 增加专业
	 * @param major
	 * @return
	 */
	public int addMajor(Major major);
	
	/**
	 * 根据名字查询专业
	 * @param majorName
	 * @return
	 */
	public int findMajorByName(String majorName);
	
	/**
	 * 删除专业
	 * @param ids
	 * @return
	 */
	public int deleteMajor(List<String> list);
	
	/**
	 * 查询专业
	 * @return
	 */
	public List<Major> findMajor();
	
	/**
	 * 增加班级
	 * @param className
	 * @return
	 */
	public int addClass(ClassName className);
	
	/**
	 * 根据名字查询班级
	 * @param cName
	 * @return
	 */
	public int findClassNum(String cName);
	
	/**
	 * 删除班级
	 * @param ids
	 * @return
	 */
	public int deleteClass(List<String> list);
	
	/**
	 * 查询班级
	 * @param majorId
	 * @return
	 */
	public List<ClassName> findClass(String majorId);
	
	/**
	 * 查询课题性质
	 * @param page
	 * @param map
	 * @return
	 */
	public List<TitleNature> findTitleNature(Map<String, Object> map);
	
	/**
	 * 增加课题性质
	 * @param titleNature
	 * @return
	 */
	public int addTitleNature(TitleNature titleNature);
	
	/**
	 * 删除课题性质
	 * @return
	 */
	public int deleteTitleNature(List<String> list);
	
	/**
	 * 查询完成形式
	 * @param page
	 * @param map
	 * @return
	 */
	public List<TitleForm> findTitleForm(Map<String, Object> map);
	
	/**
	 * 增加课题性质
	 * @param titleNature
	 * @return
	 */
	public int addTitleForm(TitleForm titleForm);
	
	/**
	 * 删除课题性质
	 * @return
	 */
	public int deleteTitleForm(List<String> list);
	
	/**
	 * 更新阶段时间
	 * @param map
	 * @return
	 */
	public int setStageTime(String stageName, Date startTime, Date endTime);
	
	/**
	 * @Title: inserStageTime
	 * @Description: 插入阶段时间
	 * @param stageName
	 * @param startTime
	 * @param endTime
	 * @return int 
	 * @throws
	 */
	public int inserStageTime(StagePlan stagePlan);
	
	/**
	 * 查询阶段时间安排
	 * @return
	 */
	public StagePlan findStagePlan(String stageName);
	
	/**
	 * 增加一条权重评分记录
	 * @param gradeWerght
	 * @return
	 */
	public int addGradeWeighr(GradeWeight gradeWerght);
	
	/**
	 * 删除一条权重评分记录
	 * @param gradeWerght
	 * @return
	 */
	public int deleteGradeWeighr(List<String> list);
	
	/**
	 * 查询权重评分
	 * @return
	 */
	public List<GradeWeight> findGradeWeight();
	
	/**
	 * 给专业设置评分
	 * @param zdPingfen
	 * @param pyPingfen
	 * @param dbPingfen
	 * @return
	 */
	public int setGradeWeight(List<String> list, float zdPingfen, float pyPingfen, float dbPingfen);
	
	/**
	 * 取消设置评分权重
	 * @return
	 */
	public int cancelSetGradeWeight(List<String> list);
	
	/**
	 * 数据归档
	 * @return
	 */
	public int dataSave();
	
	/**
	 * 增加职称
	 * @param major
	 * @return
	 */
	public int addZhiCheng(ZhiCheng zhiCheng);
	
	
	/**
	 * 删除职称
	 * @param ids
	 * @return
	 */
	public int deleteZhiCheng(List<String> list);
	
	/**
	 * 查询职称
	 * @return
	 */
	public List<ZhiCheng> findZhiCheng();
} 
