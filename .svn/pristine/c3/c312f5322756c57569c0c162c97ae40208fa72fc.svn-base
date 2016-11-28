package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.StuStageFile;
import com.hlzt.power.model.TaskBook;


public interface TaskBookDao extends BaseDao<TaskBook>{
    
	/**
	 * @Title: findTaskByStuId
	 * @Description: 根据学生ID查询任务书
	 * @param stuId
	 * @return TaskBook 
	 * @throws
	 */
	public TaskBook findTaskByStuId(@Param("stuId")String stuId);
	
	/**
	 * @Title: updateTaskBookById
	 * @Description: 学生重新提交任务书,在原有记录上更新
	 * @param stuId
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int updateTaskBookById(@Param("stuId")String stuId,@Param("map")Map<String,Object> map);
	
	/**
	 * 指导老师分页查找
	 * 
	 * @param map
	 * @param page
	 * @param sqlData
	 * @return
	 */
	public List<StuStageFile> zdTeaFindByMap(@Param("map") Map<String,Object> map,
			@Param("page") BasePage<StuStageFile> page);
	
	/**
	 * 指导老师分页查找数量
	 * 
	 * @param map
	 * @param page
	 * @param sqlData
	 * @return
	 */
	public int zdTeaFindNumByMap(@Param("map") Map<String,Object> map);
	
	/**
	 * 更改指导老师审核状态
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("list")List<String> list, @Param("status")String status);
	
	/**
	 * 查询未提交任务书学生数量
	 * @param map
	 * @return
	 */
	public int selectNotSubmitNum(@Param("map")Map<String, Object> map);
	
	
	/**
	 * 查询任务书专业负责人审核中学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckingNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询任务书专业负责人审核未通过学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckNotNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询任务书专业负责人审核通过学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckPassNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询任务书指导老师审核中学生数量
	 * @param map
	 * @return
	 */
	public int selectZdCheckingNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询任务书指导老师审核未通过学生数量
	 * @param map
	 * @return
	 */
	public int selectZdCheckNotNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询任务书指导老师审核通过学生数量
	 * @param map
	 * @return
	 */
	public int selectZdCheckPassNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 专业负责人查询本专业任务书
	 * @param map
	 * @param page
	 * @return
	 */
	public List<StuStageFile> mlFindTaskBook(@Param("map") Map<String,Object> map,
			@Param("page") BasePage<StuStageFile> page);
	
	/**
	 * 专业负责人查询本专业任务书数量
	 * @param map
	 * @param page
	 * @return
	 */
	public int mlFindTaskBookNum(@Param("map") Map<String,Object> map);
	
	/**
	 * 专业负责人审核本专业任务书
	 * @param list
	 * @param major
	 * @return
	 */
	public int updateTaskBookMlStatus(@Param("list")List<String> list, 
			@Param("major")String major, @Param("status")String status);

	public List<TaskBook> findTaskbookByKey(@Param("teaId")String teaId, @Param("status")String status);
	
	
	
	
	
	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();
	
	
	
}