package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.FirstPaper;
import com.hlzt.power.model.StuStageFile;



public interface FirstPaperDao extends BaseDao<FirstPaper>{
    
	/**
	 * 分页查找
	 * 
	 * @param map
	 * @param page
	 * @param sqlData
	 * @return
	 */
	public List<StuStageFile> findByMap(@Param("map")Map<String,Object> map,
			@Param("page") BasePage<StuStageFile> page, @Param("sqlData") String sqlData);
	
	/**
	 * @Title: findFirstPaperByStuId
	 * @Description: 根据学生ID查询初稿
	 * @param stuId
	 * @return FinalPaper 
	 * @throws
	 */
	public FirstPaper findFirstPaperByStuId(@Param("stuId")String stuId);
	
	/**
	 * @Title: updateFirstPaperById
	 * @Description: 重新提交初稿,在原有记录上修改
	 * @param stuId
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int updateFirstPaperById(@Param("stuId")String stuId,@Param("map")Map<String,Object> map);
	
	/**
	 * 更改指导老师审核状态
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("list")List<String> list, @Param("status")String status);
	
	
	
	/**
	 * 查询未提交论文初稿学生数量
	 * @param map
	 * @return
	 */
	public int selectNotSubmitNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询论文初稿审核中学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckingNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询论文初稿审核未通过学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckNotNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询论文初稿审核通过学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckPassNum(@Param("map")Map<String, Object> map);

	public List<FirstPaper> findFirstPaperByKey(@Param("teaId")String teaId, @Param("status")String status);
	
	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();
	
	
}