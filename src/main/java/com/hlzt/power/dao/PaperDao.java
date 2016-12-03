package com.hlzt.power.dao;



import java.util.List;
import java.util.Map;

import org.ansj.domain.Result;
import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.Paper;


public interface PaperDao extends BaseDao<Paper>{
    
	/**
	 * 更新paper
	 * @param paper
	 * @return
	 */
	public int updatePaper(@Param("paper")Paper paper);
	
	/**
	 * 根据学号查询
	 * @param stuNum
	 * @return
	 */
	public Paper selectByStuNum(@Param("stuNum")String stuNum);
	
	/**
	 * 根据学号删除
	 * @param stuNum
	 * @return
	 */
	public int deleteByStuNum(@Param("stuNum")String stuNum);
	
	/**
	 * @Title: findStuFileForteacher
	 * @Description: 查找学生课题源码
	 * @param map
	 * @return List<Paper> 
	 * @throws
	 */
	public List<Paper> findStuFileForteacher(@Param("map")Map<String, Object> map);
	
	/**
	 * @Title: findRepeatByAnsj
	 * @Description: 根据Ansj提取的关键词查询重复课题
	 * @param splitWords
	 * @return List<Paper> 
	 * @throws
	 */
	public List<Paper> findRepeatByAnsj(@Param("keyWords")List<String> keyWords,@Param("nowTerm")String nowTerm,@Param("threeTerm")String threeTerm);

	/**
	 * @Title: formTermByPage
	 * @Description: 分页查询往届信息
	 * @param map
	 * @param page
	 * @return List<Paper> 
	 * @throws
	 */
	public List<Paper> formTermByPage(@Param("map")Map<String, Object> map,@Param("page") BasePage<Paper> page);

	/**
	 * @Title: formTermOfSize
	 * @Description: 往届信息数量
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int formTermOfSize(@Param("map")Map<String, Object> map);

}