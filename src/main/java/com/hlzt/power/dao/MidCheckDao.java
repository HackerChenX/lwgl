package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.MidCheck;
import com.hlzt.power.model.StuStageFile;


public interface MidCheckDao extends BaseDao<MidCheck>{
    
	
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
	 * @Title: findMidCheckByStuId
	 * @Description: 根据学生ID查询中期检查
	 * @param stuId
	 * @return FinalPaper 
	 * @throws
	 */
	public MidCheck findMidCheckByStuId(@Param("stuId")String stuId);
	
	/**
	 * @Title: updateMidCheckById
	 * @Description: 重新提交中期检查,在原有记录上修改
	 * @param stuId
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int updateMidCheckById(@Param("stuId")String stuId,@Param("map")Map<String,Object> map);
	
	
	/**
	 * 更改指导老师审核状态
	 * @param stuIds
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("list")List<String> list, @Param("status")String status);
	
	/**
	 * 查询未提交中期检查表学生数量
	 * @param map
	 * @return
	 */
	public int selectNotSubmitNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询中期检查表审核中学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckingNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询中期检查表审核未通过学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckNotNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 查询中期检查表审核通过学生数量
	 * @param map
	 * @return
	 */
	public int selectCheckPassNum(@Param("map")Map<String, Object> map);


	public List<MidCheck> findMidCheckByKey(@Param("teaId")String teaId, @Param("status")String status);
	
	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();
	
}