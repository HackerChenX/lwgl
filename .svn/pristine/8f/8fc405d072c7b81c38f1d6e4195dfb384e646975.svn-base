package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.StuStageFile;


public interface ApplyTitleDao extends BaseDao<ApplyTitle>{
  	
	/**
	 * @Description: 查询申报课题状态
	 * @param map
	 * @return List<StuStageFile>    
	 * @throws
	 */
	public List<ApplyTitle> findByMap(@Param("map") Map<String,Object> map);
	
	/**
	 * 查询学生申请的题目
	 * @param map
	 * @param page
	 * @param sql
	 * @return
	 */
	public List<StuStageFile> findStuTitleByMap(@Param("map") Map<String,Object> map, @Param("page")BasePage<StuStageFile> page, @Param("sql")String sql);	
	
	/**
	 * @Title: findByStuId
	 * @Description: 根据学号查询申报课题状态
	 * @param stuId
	 * @return List<ApplyTitle> 
	 * @throws
	 */
	public ApplyTitle findByStuId(@Param("stuId") String stuId);

	/**
	 * @Title: updateApplyTitleById
	 * @Description: 学生重新申报/修改课题,在原有记录上更新课题信息
	 * @param stuId
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int updateApplyTitleById(@Param("stuId") String stuId,@Param("map") Map<String,Object> map);
	/**
	 * 更改指导老师审核状态
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("list")List<String> list, @Param("status")String status);
	
	/**
	 * 分页查询申请课题
	 * @param map
	 * @param page
	 * @return
	 */
	public List<ApplyTitle> selectApplyTitle(@Param("map")Map<String, Object> map, @Param("page")BasePage<ApplyTitle> page);
	
	/**
	 * 分页查询申请课题数量
	 * @param map
	 * @param page
	 * @return
	 */
	public int selectApplyTitleNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 专业负责人审核审核申请课题
	 * @param list
	 * @param status
	 * @param major
	 * @return
	 */
	public int mlCheckApplyTitle(@Param("list")List<String> list, 
			@Param("status")String status, @Param("major")String major);
	
	/**
	 * 教学秘书审核学生申请课题
	 * @param list
	 * @param status
	 * @return
	 */
	public int managerCheckApplyTitle(@Param("list")List<String> list, 
			@Param("status")String status, @Param("managerIdea")String managerIdea);
	
	/**
	 * 查看学生课题信息
	 * @param id
	 * @return
	 */
	public ApplyTitle findById(@Param("id")String id);
	
	/**
	 * 指导老师审核学生申请课题
	 * @author gym
	 * @param idList
	 * @param status
	 * @param teaIdea
	 * @return
	 * int 
	 */
	public int zdTeacherCheckApplyTitle(@Param("list")List<String> list, 
			@Param("status")String status, @Param("teaIdea")String teaIdea);
	
	
	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();
	
	
}