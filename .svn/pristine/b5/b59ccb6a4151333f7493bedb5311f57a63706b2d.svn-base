package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.StuStageFile;


public interface FinalPaperDao extends BaseDao<FinalPaper>{
    
	/**
	 * 分页查找
	 * 
	 * @param map
	 * @param page
	 * @param sqlData
	 * @return
	 */
	public List<StuStageFile> findByMap(@Param("map") Map<String,Object> map,
			@Param("page") BasePage<StuStageFile> page, @Param("sqlData") String sqlData);
	
	/**
	 * @Title: findFinalPaperByStuId
	 * @Description: 根据学生ID查询定稿
	 * @param stuId
	 * @return FinalPaper 
	 * @throws
	 */
	public FinalPaper findFinalPaperByStuId(@Param("stuId")String stuId);
	
	/**
	 * @Title: updateFinalPaperById
	 * @Description: 重新提交定稿,在原有记录上修改
	 * @param stuId
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int updateFinalPaperById(@Param("stuId")String stuId,@Param("map")Map<String,Object> map);
	
	/**
	 * 更改指导老师审核状态
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("list")List<String> list, @Param("status")String status);
	
	
	/**
	 * 根据学生Id查询指导老师
	 */
	
	
	
	/**
	 * 查询未提交论文终稿学生数量
	 * @param map
	 * @return
	 */
	public int selectNotSubmitNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询论文终稿审核中学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckingNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询论文终稿审核未通过学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckNotNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询论文终稿审核通过学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckPassNum(@Param("map")Map<String, Object> map);

	public List<FinalPaper> findFinalPapersByKey(@Param("teaId")String teaId, @Param("status")String status);
	
	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();
	
	
}