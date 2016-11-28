package com.hlzt.power.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hlzt.commons.dao.BaseDao;
import com.hlzt.commons.helper.StringHelper;
import com.hlzt.commons.helper.SysConfig;
import com.hlzt.commons.helper.UuidHelper;
import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.service.BaseServiceImpl;
import com.hlzt.power.dao.PaperDao;
import com.hlzt.power.dao.RoleDao;
import com.hlzt.power.dao.StudentDao;
import com.hlzt.power.dao.TeacherDao;
import com.hlzt.power.dao.UserDao;
import com.hlzt.power.dao.UserRoleDao;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.Role;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.User;
import com.hlzt.power.model.UserRole;
import com.hlzt.power.service.UserRoleSer;
import com.hlzt.power.service.UserSer;

@Transactional
@Component
public class UserSerImpl extends BaseServiceImpl<User> implements UserSer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	UserDao udao;
	
	@Resource
	UserRoleSer userRoleSer;
	
	@Resource
	RoleDao roleDao;
	
	@Resource
	UserRoleDao userRoleDao;
	
	@Resource
	TeacherDao teacherDao;
	
	@Resource
	StudentDao studentDao;
	
	@Resource
	PaperDao paperDao;
	
	@Override
	protected BaseDao<User> getDao() {
		// TODO Auto-generated method stub
		return udao;
	}
	
	@Override
	public int add(User u) {
		// TODO Auto-generated method stub
		u.setPassword(StringHelper.getMD5(u.getPassword()));
		return super.add(u);
	}
	
	@Override
	public int removeById(String id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fkUser", id);
		List<UserRole> list=userRoleSer.list(map, null);
		List <String> urIdList=new ArrayList<String>();
		for(UserRole ur:list)
			urIdList.add(ur.getId());
		userRoleSer.removeBySomeId(new HashMap(), null, urIdList);
		return super.removeById(id);
	}
	
	
	@Override
	public int addUser(User user,Object object,String roleName) {
		
		int i = udao.insert(user);
		if(i!=0){
			if("student".equals(roleName)){
				Student student  = (Student)object;
				studentDao.insert(student);			
			}else if("teacher".equals(roleName)){
				Teacher teacher = (Teacher)object;
				teacherDao.insert(teacher);
			}						
		}
		Role role = roleDao.findRoleByRoleName(roleName);
		UserRole userRole = new UserRole();
		userRole.setId(UuidHelper.getRandomUUID());
		userRole.setFkUser(user.getId());
		userRole.setFkRole(role.getId());
		userRole.setCreateTime(new Date());
		userRole.setCreateUser(user.getCreateUser());				
		userRoleDao.insert(userRole);
		return i;
	}
	
	@Override
	public User findUserByUserNum(String userNum) {
		User user = udao.selectUserByUserNum(userNum);
		return user;
	}

	@Override
	public Student finStudentByUserNum(String stuNum) {
		Student stu = studentDao.selectStuByStuNum(stuNum);
		return stu;
	}

	@Override
	public Teacher finTeacherByUserNum(String teaNum) {
		Teacher tea = teacherDao.selectTeacherByTeaNum(teaNum);
		return tea;
	}

	@Override
	public int deleteStudent(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			Student stu = studentDao.selectStuByUserId(list.get(i));
			paperDao.deleteByStuNum(stu.getUserNum());
		}
		int i = studentDao.deleteStudent(list);
		return i;
	}

	@Override
	public int deleteTeacher(List<String> list) {
		int i = teacherDao.deleteTeacher(list);
		return i;
	}

	@Override
	public int deleteUser(List<String> list) {
		int i = udao.deleteUser(list);
		return i;
	}

	@Override
	public int deleteUserRole(List<String> list) {
		int i = userRoleDao.deleteUserRole(list);
		return i;
	}

	@Override
	public Student findStudentInfo(String id) {
		Student stu = studentDao.selectStuByUserId(id);
		return stu;
	}

	@Override
	public Teacher findTeacherInfo(String id) {
		Teacher tea = teacherDao.selectByUserId(id);
		return tea;
	}

	@Override
	public int updateStuInfo(Student student) {
		int i = studentDao.updateStuInfo(student);
		if(i!=0){
			Paper paper = paperDao.selectByStuNum(student.getUserNum());
			if(paper==null){
				Paper p = new Paper();
				p.setId(UuidHelper.getRandomUUID());
				p.setTerm(SysConfig.getValue("term"));
				p.setStuNum(student.getUserNum());
				p.setStuName(student.getStuName());
				p.setStuMajor(student.getMajor());
				p.setStuClass(student.getStuClass());
				p.setCreateTime(new Date());
				p.setCreateUser("");
				paperDao.insert(p);
			}else{
				Paper p = new Paper();
				p.setStuNum(student.getUserNum());
				p.setStuName(student.getStuName());
				p.setStuMajor(student.getMajor());
				p.setStuClass(student.getStuClass());
				paperDao.updatePaper(p);				
			}
		}
		return i;
	}

	@Override
	public int updateTeaInfo(Teacher teacher) {
		int i = teacherDao.updateTeacher(teacher);
		return i;
	}

	@Override
	public int updateUserInfo(User user) {
		int i = udao.updateUser(user);
		return i;
	}

	@Override
	public List<UserRole> findUserRoleByUserId(String userId) {

		return userRoleDao.findRoleNameListByUserId(userId);
	}

	@Override
	public int addUserRole(UserRole userRole) {
		
		return userRoleDao.insert(userRole);
	}

	@Override
	public int deleteByUserIdAndRole(String userId, String role) {

		return userRoleDao.deleteByUserIdAndRole(userId, role);
	}

	@Override
	public int updatePassword(String userId, String userNum, String password) {
		
		return 0;
	}

	@Override
	public List<UserRole> findUserRoleByRole(String role) {
		
		return userRoleDao.selectUserRoleByRole(role);
	}

	@Override
	public BasePage<Teacher> findTeacherByPage(Map<String, Object> map,BasePage<Teacher> page){
		int totalRecord = teacherDao.rowsSizeOfFindUser(map);
		page.setTotalRecord(totalRecord);
		page.setResults(teacherDao.findUserByPage(map, page));
		return page;
	}

	@Override
	public BasePage<Student> findStudentByPage(Map<String, Object> map,
			BasePage<Student> page) {
		int totalRecord = studentDao.rowsSizeOfFindUser(map);
		page.setTotalRecord(totalRecord);
		page.setResults(studentDao.findUserByPage(map, page));
		return page;
	}

	@Override
	public BasePage<Teacher> findTeacherAccountByPage(Map<String, Object> map,
			BasePage<Teacher> page) {
		int totalRecord = teacherDao.rowsSizeOfFindTeaAccount(map);
		page.setTotalRecord(totalRecord);
		page.setResults(teacherDao.findTeaAccountByPage(map, page));
		return page;
	}

}
