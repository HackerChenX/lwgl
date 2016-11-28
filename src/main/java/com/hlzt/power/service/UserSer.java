package com.hlzt.power.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.service.BaseService;
import com.hlzt.power.model.Role;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.User;
import com.hlzt.power.model.UserRole;

public interface UserSer extends BaseService<User>{
	
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public int addUser(User user,Object object,String roleName);
	
	/**
	 * 根据用户账号查询用户
	 * @param userNum
	 * @return
	 */
	public User findUserByUserNum(String userNum);
	
	/**
	 * 根据用户id查询用户角色
	 * @param userId
	 * @return
	 */
	public List<UserRole> findUserRoleByUserId(String userId);
	
	/**
	 * 根据角色查询用户角色
	 * @param role
	 * @return
	 */
	public List<UserRole> findUserRoleByRole(String role);
	
	/**
	 * 根据账号查询教师
	 * @param teaNum
	 * @return
	 */
	public Teacher finTeacherByUserNum(String teaNum);
	
	
	/**
	 * 根据账号查询学生
	 * @param teaNum
	 * @return
	 */
	public Student finStudentByUserNum(String stuNum);
	
	/**
	 * 删除用户
	 * @param list
	 * @return
	 */
	public int deleteUser(List<String> list);
	
	/**
	 * 删除学生
	 * @param list
	 * @return
	 */
	public int deleteStudent(List<String> list);
	
	/**
	 * 删除老师
	 * @param list
	 * @return
	 */
	public int deleteTeacher(List<String> list);
	
	/**
	 * 删除用户角色
	 * @param list
	 * @return
	 */
	public int deleteUserRole(List<String> list);
	
	/**
	 * 增加用户角色
	 * @param list
	 * @param role
	 * @return
	 */
	public int addUserRole(UserRole userRole);
	
	/**
	 * 根据用户ID和角色删除用户角色
	 * @param userId
	 * @param role
	 * @return
	 */
	public int deleteByUserIdAndRole(String userId, String role);
	
	/**
	 * 查看学生信息
	 * @param id
	 * @return
	 */
	public Student findStudentInfo(String id);
	
	/**
	 * 查看教师信息
	 * @param id
	 * @return
	 */
	public Teacher findTeacherInfo(String id);
	
	/**
	 * 更改学生
	 * @param userId
	 * @return
	 */
	public int updateStuInfo(Student student);
	
	/**
	 * 更改教师
	 * @param userId
	 * @return
	 */
	public int updateTeaInfo(Teacher teacher);
	
	/**
	 * 更改用户
	 * @param userId
	 * @return
	 */
	public int updateUserInfo(User user);
	
	/**
	 * 更改用户账号密码
	 * @param userId
	 * @param userNum
	 * @param password
	 * @return
	 */
	public int updatePassword(String userId, String userNum, String password);
	
	/**
	 * @Title: findTeacherByPage
	 * @Description: 分页查询已添加教师账号(Teacher表，user_role表)
	 * @param map
	 * @param page
	 * @return BasePage<Teacher> 
	 * @throws
	 */
	public BasePage<Teacher> findTeacherByPage(Map<String, Object> map,BasePage<Teacher> page);
	
	/**
	 * @Title: findStudentByPage
	 * @Description: 分页查询已添加学生账号(Student表) 
	 * @param map
	 * @param page
	 * @return BasePage<Student> 
	 * @throws
	 */
	public BasePage<Student> findStudentByPage(Map<String, Object> map,BasePage<Student> page);
	
	/**
	 * @Title: findTeacherAccountByPage
	 * @Description: 查询教师账号（不查角色表）
	 * @param map
	 * @param page
	 * @return BasePage<Teacher> 
	 * @throws
	 */
	public BasePage<Teacher> findTeacherAccountByPage(Map<String, Object> map,BasePage<Teacher> page);
} 
