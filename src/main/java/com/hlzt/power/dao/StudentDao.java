package com.hlzt.power.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.StuGraCollect;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.Teacher;

/**
 * 学生
 * @author Administrator
 *
 */
public interface StudentDao extends BaseDao<Student>{
    
	/**
	 * 根据学生账号查询学生
	 * @param stuNum
	 * @return
	 */
	public Student selectStuByStuNum(@Param("stuNum")String stuNum);
	
	/**
	 * 查询学生
	 * @param userId
	 * @return
	 */
	public Student selectStuByUserId(@Param("userId")String userId);
	
	/**
	 * 删除学生
	 * @param list
	 * @return
	 */
	public int deleteStudent(@Param("list")List<String> list);
	
	/**
	 * 更改学生信息
	 * @param userId
	 * @return
	 */
	public int updateStuInfo(@Param("student")Student student);
	
	/**
	 * @Title: updateTitleByObject
	 * @Description: 添加学生表中课题记录
	 * @param applyTitle
	 * @return int 
	 * @throws
	 */
	public int updateTitleByMap(@Param("stuId")String stuId,@Param("map")Map<String,Object> map);
	
	/**
	 * @Title: deleteTitleById
	 * @Description: 删除学生表中课题记录
	 * @param id
	 * @return int 
	 * @throws
	 */
	public int deleteTitleById(@Param("stuId")String stuId);
		
	/**
	 * 多条件成绩汇总查询
	 * @param map
	 * @param page
	 * @return
	 */
	public List<StuGraCollect> findStuGraColllectByCondition(@Param("map")Map<String,Object>  map, @Param("page")BasePage<StuGraCollect> page);
	
	/**
	 * 根据专业查询学生数量
	 * @param majorId
	 * @return
	 */
	public int findStuNumByMajor(@Param("majorName")String majorName);
	
	/**
	 * 根据班级查询学生数量
	 * @param className
	 * @return
	 */
	public int findStuNumByClass(@Param("className")String className);
	
	/**
	 * 查询未提交任务书的学生
	 * @param map
	 * @param page 
	 * @return
	 */
	public List<Student> findTaskBookNotSubmit(@Param("map")Map<String, Object> map, @Param("page")BasePage<Student> page);
	
	/**
	 * 根据任务书审核状态查询学生
	 * @param map
	 * @return
	 */
	public List<Student> findTaskBookByLeaderStatus(@Param("map")Map<String, Object> map,@Param("page")BasePage<Student> page);
	
	/**
	 * 查询开题报告未提交的学生
	 * @param map
	 * @return
	 */
	public List<Student> findOpeningReportNotSubmit(@Param("map")Map<String, Object> map, @Param("page")BasePage<Student> page);
	
	/**
	 * 根据开题报告审核状态查询学生
	 * @param map
	 * @param page 
	 * @return
	 */
	public List<Student> findOpeningReportByLeaderStatus(@Param("map")Map<String, Object> map,@Param("page")BasePage<Student> page);
	
	/**
	 * 查询中期检查表未提交的学生
	 * @param map
	 * @return
	 */
	public List<Student> findMidCheckNotSubmit(@Param("map")Map<String, Object> map, @Param("page")BasePage<Student> page);
	
	/**
	 * 根据中期检查表专业审核状态查询学生
	 * @param map
	 * @return
	 */
	public List<Student> findMidCheckByLeaderStatus(@Param("map")Map<String, Object> map,@Param("page")BasePage<Student> page);
	
	/**
	 * 查询论文初稿未提交的学生
	 * @param map
	 * @return
	 */
	public List<Student> findFirstPaperNotSubmit(@Param("map")Map<String, Object> map, @Param("page")BasePage<Student> page);
	
	/**
	 * 根据论文初稿专业审核状态查询学生
	 * @param map
	 * @return
	 */
	public List<Student> findFirstPaperByLeaderStatus(@Param("map")Map<String, Object> map,@Param("page")BasePage<Student> page);
	
	/**
	 * 查询论文定稿未提交的学生
	 * @param map
	 * @param page 
	 * @return
	 */
	public List<Student> findFinalPaperNotSubmit(@Param("map")Map<String, Object> map, @Param("page")BasePage<Student> page);
	
	/**
	 * 根据论文定稿专业审核状态查询学生
	 * @param map
	 * @return
	 */
	public List<Student> findFinalPaperByLeaderStatus(@Param("map")Map<String, Object> map,@Param("page")BasePage<Student> page);
	
	/**
	 * 指导老师查询评阅学生
	 * @param pyTeaId
	 * @return
	 */
	public List<Student> zdTeaFindPyStu(@Param("pyTeaId")String pyTeaId, @Param("page")BasePage<Student> page);
	
	/**
	 * 指导老师查询评阅学生数量
	 * @param pyTeaId
	 * @return
	 */
	public int zdTeaFindPyStuNum(@Param("pyTeaId")String pyTeaId);
	
	
	/**
	 * 查询所有评阅学生
	 * @param teaId
	 * @param major
	 * @param page
	 * @return
	 */
	public List<Student> selectAllPyStu(@Param("teaId")String teaId, 
			@Param("major")String major, @Param("page")BasePage<Student> page);
	
	/**
	 * 查询所有评阅学生数量
	 * @param teaId
	 * @param major
	 * @param page
	 * @return
	 */
	public int selectAllPyStuNum(@Param("teaId")String teaId, 
			@Param("major")String major, @Param("page")BasePage<Student> page);
	
	/**
	 * 给学生设置评阅教师
	 * @param list
	 * @param teaId
	 * @param major
	 * @return
	 */
	public int setPyTea(@Param("list")List<String> list, @Param("teaId")String teaId, 
			@Param("major")String major);
	
	/**
	 * 根据答辩小组查询学生
	 * @param dbGroup
	 * @param page
	 * @return
	 */
	public List<Student> selectStuByDbGroup(@Param("dbGroup")String dbGroup, 
			@Param("page")BasePage<Student> page);
	
	/**
	 * 根据答辩小组查询学生数量
	 * @param dbGroup
	 * @param page
	 * @return
	 */
	public int selectStuNumByDbGroup(@Param("dbGroup")String dbGroup);
	

	/**
	 * 根据答辩小组查询学生
	 * @param dbGroup
	 * @param major
	 * @param page
	 * @return
	 */
	public List<Student> findStuByDbGroup(@Param("dbGroup")String dbGroup, 
			@Param("major")String major);
	
	/**
	 * 根据答辩小组查询学生数量
	 * @param dbGroup
	 * @param major
	 * @return
	 */
	public int findStuNumByDbGroup(@Param("dbGroup")String dbGroup, 
			@Param("major")String major);
	
	/**
	 * 给学生设置答辩小组
	 * @param list
	 * @param dbGroupId
	 * @return
	 */
	public int setDbGroupForStu(@Param("list")List<String> list, @Param("dbGroupId")String dbGroupId);
	
	/**
	 * 根据评阅老师查询学生
	 * @param pyTeaId
	 * @param major
	 * @return
	 */
	public List<Student> findStuByPyTea(@Param("pyTeaId")String pyTeaId, @Param("major")String major);
	/**
	 * 根据评阅老师查询学生数量
	 * @param pyTeaId
	 * @param major
	 * @return
	 */
	public int findStuNumByPyTea(@Param("pyTeaId")String pyTeaId, @Param("major")String major);
	



	/**
	 * 删除表中数据
	 * @return
	 */
	public int deleteAll();
	/**
	 * 指导教师查询所带学生
	 * @author gym
	 * @param map
	 * @param page
	 * @return
	 * List<Student> 
	 */
	public  List<Student> findStuByZdTeacherId(@Param("map")Map<String, Object> map,
			@Param("page")BasePage<Student> page);
	
	/**
	 * 指导教师查询所带学生数量
	 * @author gym
	 * @param map
	 * @param page
	 * @return
	 * List<Student> 
	 */
	public  int findStuNumByZdTeacherId(@Param("map")Map<String, Object> map,
			@Param("page")BasePage<Student> page);
	
	public Student findStuForTeacher(@Param("map")Map<String, Object> map);
	/**
	 * 
	 * @author gym
	 * @param teaId
	 * @return
	 * Student 
	 */
	public Student findStuByStuId(@Param("stuId")String stuId);

	/**
	 * @author gym
	 * @param map
	 * @return
	 * Student 
	 */
	public List<Student> findStuTitle(@Param("map")Map<String, Object> map,@Param("page") BasePage<Student> page);
	
	/**
	 * @Title: findStuTitleNum
	 * @Description: 查询学生申报数量
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int findStuTitleNum(@Param("map")Map<String, Object> map);
	
	/**
	 * 通过教师Id查学生
	 * @author gym
	 * @param zdTeacherId
	 * @return
	 * List<Student> 
	 * @date 2016-9-17 下午8:15:09
	 */
	public List<Student> findStuByZdTeacherId(@Param("zdTeacherId")String zdTeacherId);
	/**
	 * 通过答辩组id查学生
	 * @author gym
	 * @param map
	 * @return
	 * List<Student> 
	 */
	public List<Student> findStuByDbkey(Map<String, Object> map);
	/**
	 * 通过答辩小组Id查学生
	 * @author gym
	 * @param dbId
	 * @return
	 * List<Student> 
	 */
	public List<Student> findStuByDbId(@Param("dbId")String dbId);
	/**
	 * 通过关键词获取student
	 * @author gym
	 * @param map
	 * @return
	 * List<Student> 
	 */
	public List<Student> findStuByKey(@Param("map")Map<String, Object> map);

	/**
	 * @Title: rowsSizeOfFindUser
	 * @Description: 教学秘书分页查询学生数量
	 * @param map
	 * @return int 
	 * @throws
	 */
	public int rowsSizeOfFindUser(@Param("map")Map<String, Object> map);
	
	/**
	 * @Title: findUserByPage
	 * @Description: 教学秘书分页查询学生信息
	 * @param map
	 * @param page
	 * @return List<Student> 
	 * @throws
	 */
	public List<Student> findUserByPage(@Param("map")Map<String, Object> map, @Param("page") BasePage<Student> page);
}