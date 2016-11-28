package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.StuStageFile;


public interface OpeningReportDao extends BaseDao<OpeningReport>{
    
	
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
	 * @Title: findOpeningReportByStuId
	 * @Description: 根据学生ID查询开题报告
	 * @param stuId
	 * @return FinalPaper 
	 * @throws
	 */
	public OpeningReport findOpeningReportByStuId(@Param("stuId")String stuId);
	
	/**
	 * @Title: updateOpeningReportById
	 * @Description: 重新提交开题报告,在原有记录上修改
	 * @param stuId
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int updateOpeningReportById(@Param("stuId")String stuId,@Param("map")Map<String,Object> map);
	
	
	/**
	 * 更改指导老师审核状态
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("list")List<String> list, @Param("status")String status);
	
	
	
	/**
	 * 查询未提交开题报告学生数量
	 * @param map
	 * @return
	 */
	public int selectNotSubmitNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询开题报告专业负责人审核中学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckingNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询开题报告专业负责人审核未通过学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckNotNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询开题报告专业负责人审核通过学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckPassNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询开题报告指导老师审核中学生数量
	 * @param map
	 * @return
	 */
	public int selectZdCheckingNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询开题报告指导老师审核未通过学生数量
	 * @param map
	 * @return
	 */
	public int selectZdCheckNotNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询开题报告指导老师审核通过学生数量
	 * @param map
	 * @return
	 */
	public int selectZdCheckPassNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 专业负责人查询本专业开题报告
	 * @param map
	 * @param page
	 * @return
	 */
	public List<StuStageFile> mLSelectOpeningReport(@Param("map")Map<String, Object> map, @Param("page")BasePage<StuStageFile> page);
	
	/**
	 * 专业负责人查询本专业开题报告数量
	 * @param map
	 * @return
	 */
	public int mLSelectOpeningReportNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 专业负责人审核本专业开题报告
	 * @param list
	 * @param major
	 * @param status
	 * @return
	 */
	public int mLCheckOpeningReport(@Param("list")List<String> list, @Param("major")String major, @Param("status")String status);

/**
 * 通过教师Id和状态查找开题报告
 * @author gym
 * @param teaId
 * @param status
 * @return
 * List<OpeningReport> 
 * @date 2016-9-18 下午5:24:44
 */
public List<OpeningReport> findOpeningReportByKey(@Param("teaId")String teaId, @Param("status")String status);
	
	
	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();
	
}