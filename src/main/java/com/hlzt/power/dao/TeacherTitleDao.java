package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.TeacherTitle;

public interface TeacherTitleDao extends BaseDao<TeacherTitle>{
	
	/**
	 * 审核教师发布的课题更改教学秘书审核状态
	 * @param ids
	 * @param status
	 * @return
	 */
	public int updateManagerStatus(@Param("list")List<String> list, 
			@Param("status")String status, @Param("managerIdea") String managerIdea);
	
	/**
	 * 查询教师发布的课题
	 * @param map
	 * @param page
	 * @return
	 */
	public List<TeacherTitle> selectTeacherTitle(@Param("map")Map<String, Object> map, @Param("page")BasePage<TeacherTitle> page);
	
	/**
	 * @Title: selectBytitle
	 * @Description: 根据课题名称查询课题信息
	 * @param title
	 * @return TeacherTitle 
	 * @throws
	 */
	public TeacherTitle selectByTitle(@Param("title")String title);
	
	/**
	 * @Title: updateTitleChoose
	 * @Description: 学生选题更改课题被选状态
	 * @param choose
	 * @return int 
	 * @throws
	 */
	public int updateTitleChoose(@Param("map")Map<String, Object> map);
	
	/**
	 * 专业负责人根据ID查询教师发布的课题
	 * @param id
	 * @param Major
	 * @return
	 */
	public TeacherTitle selectTeaTitleById(@Param("id")String id, @Param("major")String major);
	
	/**
	 * 查询教师发布的课题数量
	 * @param map
	 * @return
	 */
	public int selectTeacherTitleNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 审核教师发布的课题更改专业负责人审核状态
	 * @param ids
	 * @param status
	 * @return
	 */
	public int updateMajorLeaderStatus(@Param("list")List<String> list, 
			@Param("status")String status, @Param("leaderIdea") String leaderIdea);



	
	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();
/**
 * 指导老师查看自己的课题
 * @author gym
 * @param TeaId
 * @param page
 * @return
 * List<TeacherTitle> 
 * @date 2016-9-9 下午2:27:12
 */
public List<TeacherTitle> findTeaTitleByTeaId (@Param("teaId")String teaId);
/**
 * 查询数据库中该题目的条数
 * @author gym
 * @param titleName
 * @return
 * int 
 */
	public int selectTeaTitleByName(@Param("titleName")String titleName);

}
