package com.hlzt.power.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hlzt.commons.helper.ImportExcelHelper;
import com.hlzt.commons.helper.StringHelper;
import com.hlzt.commons.helper.UuidHelper;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.User;
import com.hlzt.power.model.UserRole;
import com.hlzt.power.model.ZhiCheng;
import com.hlzt.power.service.PublicSer;
import com.hlzt.power.service.SecretaryFlowInfoStatisticsSer;
import com.hlzt.power.service.UserRoleSer;
import com.hlzt.power.service.UserSer;



@Controller
public class UserController {
	Logger log = LoggerFactory.getLogger(UserController.class);
	
	final String REQ_URL="/user";
	 @Autowired
	 private UserSer userSer;
	 @Autowired
	 private PublicSer publicSer;
	 @Autowired
	 private UserRoleSer userRoleSer;
	 
	 /**
	  * 跳转到增加学生的页面并查询已增加学生
	  * @param model
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("/user/findStudent.shtm")
	 @RequiresRoles("manager")
	 public String findStudent(Model model, String userNum, String userName,
			 BasePage<Student> page, HttpServletRequest request, HttpServletResponse response){
		 Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.hasRole("manager")) {
				model.addAttribute("errorMsg", "无权限！");
				return "error/error.jsp";
			}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		 page.setPageNo(page.getPageNo());
		 page.setPageUrl("user/findStudent.shtm");
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(StringUtils.isNotBlank(userNum)){
			model.addAttribute("userNum", userNum);
			map.put("userNum", userNum);
			}
			if(StringUtils.isNotBlank(userName)){
				model.addAttribute("userName", userName);
				map.put("stuName", userName);
			}
			page = 	userSer.findStudentByPage(map, page); 			
			model.addAttribute("page", page);		 
		 return "Secretary/studentAccount.jsp";
	 }
	 
	 	/**
		 * 删除学生
		 * @param model
		 * @param ids
		 * @param roleName
		 * @param page
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("/user/findStudent_removeStudent.shtm")
		public String removeStudent(Model model, String[] ids, BasePage page,
				HttpServletRequest request,HttpServletResponse response){
			/**
			 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
			 */
			page.setPageNo(page.getPageNo());
			Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.hasRole("manager")) {
				model.addAttribute("errorMsg", "无权限！");
				return "error/error.jsp";
			}
			if(ids==null){
				model.addAttribute("errorMsg", "您未选择数据！");
				return findStudent(model, null, null, page, request, response);
			}
			List<String> list = Arrays.asList(ids);
			try {
				userSer.deleteUser(list);
				userSer.deleteStudent(list);
				userSer.deleteUserRole(list);				
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMsg", "系统异常！");
			}
			model.addAttribute("successMsg", "删除成功！");
			return findStudent(model, null, null, page, request, response);	
		}
	 
	 /**
	  * 查询学生信息
	  * @param id
	  * @param model
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("/user/findStudent_findStudentInfo.shtm")
	 public String findStudentInfo(String id, Model model,
			 HttpServletRequest request, HttpServletResponse response){
		 Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.hasRole("manager")) { 
				model.addAttribute("errorMsg", "无权限！");
				return "error/error.jsp";
			}
		 if(id==null){
			 model.addAttribute("errorMsg", "系统异常！");
			 return "error/error.jsp";
		 }
		 Student stu = new Student();
		 
		 try {
			stu = userSer.findStudentInfo(id);
		} catch (Exception e){
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		List<Major> majorList = publicSer.findMajor();
		List<ClassName> classList = publicSer.findClass(null);
		model.addAttribute("student", stu);
		model.addAttribute("majorList", majorList);
		model.addAttribute("classList", classList);
		return "Secretary/studentAccountInfo.jsp";
	 }
	 
	 /**
	  * 更改学生
	  * @param userNum
	  * @param stuName
	  * @param sex
	  * @param password
	  * @param tel
	  * @param mail
	  * @param major
	  * @param stuClass
	  * @param model
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("/user/findStudent_updateStuInfo.shtm")
	 public String updateStuInfo(String stuName, String sex,
			 String password, String tel, String mail, String major, String stuClass, String userId,
			 BasePage page, Model model,  HttpServletRequest request, HttpServletResponse response){
		 /**
			 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
			 */
			page.setPageNo(page.getPageNo());
		 Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.hasRole("manager")) {
				model.addAttribute("errorMsg", "无权限！");
				return "error/error.jsp";
			}
		 if(StringUtils.isBlank(stuName)){
			 model.addAttribute("errorMsg", "姓名不能为空！");
			 return findStudentInfo(userId, model, request, response);
		 }
		 User user = new User();
		 user.setId(userId);
		 if(StringUtils.isNotBlank(password)){
			 user.setPassword(StringHelper.getMD5(String.valueOf(password)));
		 }			 
		 user.setRealName(stuName);
		 user.setSex(sex);
		 
		 Student stu = new Student();
		 stu.setStuName(stuName);
		 stu.setSex(sex);
		 stu.setTel(tel);
		 stu.setMail(mail);
		 if(!"0".equals(major)){
			 stu.setMajor(major);			 
		 }
		 if(!"0".equals(stuClass)){
			 stu.setStuClass(stuClass);			 
		 }
		 stu.setUserId(userId);
		 
		 int i = 0,j = 0;
		 try {
			 i = userSer.updateUserInfo(user);
			 if(i!=0){
				 j = userSer.updateStuInfo(stu);				 
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		if(i!=0&&j!=0){
			model.addAttribute("student", stu);
			model.addAttribute("successMsg", "修改成功！");			
		}
		return findStudent(model, null, null, page, request, response);
	 }	
	 
	 /**
	  * 跳转到增加学生的页面
	  * @param model
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("user/findStudent_jumpAddStudent.shtm")
	 public String jumpAddStudent(Model model, 
			 HttpServletRequest request, HttpServletResponse response){
		 Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.hasRole("manager")) {
				model.addAttribute("errorMsg", "无权限！");
				return "error/error.jsp";
			}
			List<Major> majorList = publicSer.findMajor();
			List<ClassName> classList = publicSer.findClass(null);
			model.addAttribute("majorList", majorList);
			model.addAttribute("classList", classList);
		 return "Secretary/addStudent.jsp";
	 }
	 
	 /**
	  * 增加学生
	  * @param stuNum
	  * @param stuName
	  * @param roleName
	  * @param model
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("/user/findStudent_addStudent.shtm")
	 public String addStudent(String stuNum, String stuName, String sex, String roleName,String password,
			 String department,String stuClass,String major,BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		 /**
			 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
			 */
			page.setPageNo(page.getPageNo());
		 Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.hasRole("manager")) {
				model.addAttribute("errorMsg", "无权限！");
				return "error/error.jsp";
			}
		User loginUser = (User) request.getSession().getAttribute("user");
		User a = userSer.findUserByUserNum(stuNum);
		String id = UuidHelper.getRandomUUID();
     	if(a==null){
     		User u = new User();
        	u.setUserNum(stuNum);
        	u.setRealName(stuName);
        	u.setSex(sex);
        	if(StringUtils.isNotBlank(password)){
        		u.setPassword(password);
        	}else{
        		u.setPassword(StringHelper.getMD5(stuNum));
        	}    	
        	u.setRoleName(roleName);
        	u.setId(id);
        	u.setCreateTime(new Date());
        	u.setLocked(false);
        	u.setSuperAdmin(false);
        	u.setCreateUser(loginUser.getRealName());
        	//查询账号在student表是否存在
			Student student = null;
			try{
				student = userSer.finStudentByUserNum(stuNum);
			}catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMsg", "系统异常！");
				return "error/error.jsp";
			}
			if(student == null)
			{
				student = new Student();
				student.setId(UuidHelper.getRandomUUID());
				student.setUserId(id);
				student.setUserNum(stuNum);
				student.setStuName(stuName);
				student.setSex(sex);
				student.setDepartment(department);
				student.setMajor(major);
				student.setStuClass(stuClass);
				try {
					userSer.addUser(u,student,roleName);	
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("errorMsg", "系统异常！");
					return "error/error.jsp";
				}      					
			}			      	
     	}else{
     		model.addAttribute("errorMsg", "账号已存在！");
			return "Secretary/addStudent.jsp";
     	}
     	model.addAttribute("successMsg", "添加成功！");
		return findStudent(model, null, null, page, request, response);
	 }	
		
	 /**
	  * 查询已增加老师
	  * @param model
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("/user/findTeacher.shtm")
	 @RequiresRoles("manager")
	 public String findTeacher(Model model, String userNum, String userName, String role,
			 BasePage<Teacher> page, HttpServletRequest request, HttpServletResponse response){
		 /**
			 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
			 */
			page.setPageNo(page.getPageNo());
		 Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.hasRole("manager")) {
				model.addAttribute("errorMsg", "无权限！");
				return "error/error.jsp";
			}
		 page.setPageUrl("user/findTeacher.shtm");
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(StringUtils.isNotBlank(userNum)){
				map.put("teaNum", userNum);
				model.addAttribute("userNum", userNum);
			}
			if(StringUtils.isNotBlank(userName)){
				model.addAttribute("userName", userName);
				userName = "%" + userName + "%";
				map.put("teaName", userName);
			}
			if(StringUtils.isBlank(role)){
				role = "5";
			}
			model.addAttribute("role", role);
			map.put(role, role);
			try {
				page = userSer.findTeacherAccountByPage(map, page);				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			model.addAttribute("page", page);		 
		 return "Secretary/teacherAccount.jsp";
	 }
	 
	 	/**
		 * 删除老师
		 * @param model
		 * @param request
		 * @param response
		 * @param id
		 * @return
		 * @throws IOException
		 */
		@RequestMapping("user/findTeacher_removeTeacher.shtm")
		public String removeTeacher(Model model, String[] ids, BasePage page,
				HttpServletRequest request,HttpServletResponse response){
			/**
			 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
			 */
			page.setPageNo(page.getPageNo());
			Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.hasRole("manager")) {
				model.addAttribute("errorMsg", "无权限！");
				return "error/error.jsp";
			}
			if(ids==null){
				model.addAttribute("errorMsg", "您未选择数据！");
				return findTeacher(model, null, null, null, page, request, response);
			}
			List<String> list = Arrays.asList(ids);
			try {
				userSer.deleteUser(list);
				userSer.deleteTeacher(list);
				userSer.deleteUserRole(list);				
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMsg", "系统异常！");
			}
			model.addAttribute("successMsg", "删除成功！");
			return findTeacher(model, null, null, null, page, request, response);
			
		}
	 
		
		 /**
		  * 查询教师信息
		  * @param id
		  * @param model
		  * @param request
		  * @param response
		  * @return
		  */
		 @RequestMapping("/user/findTeacher_findTeacherInfo.shtm")
		 public String findTeacherInfo(String id, Model model,
				 HttpServletRequest request, HttpServletResponse response){
			 Subject currentUser = SecurityUtils.getSubject();
				if (!currentUser.hasRole("manager")) {
					model.addAttribute("errorMsg", "无权限！");
					return "error/error.jsp";
				}
			 if(id==null){
				 model.addAttribute("errorMsg", "系统异常！");
				 return "error/error.jsp";
			 }
			 Teacher tea = new Teacher();
			 
			 try {
				 tea = userSer.findTeacherInfo(id);
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMsg", "系统异常！");
			}
			List<Major> majorList = publicSer.findMajor();
			List<ZhiCheng> zhiChenglist = new ArrayList<ZhiCheng>();
			zhiChenglist = publicSer.findZhiCheng();
			model.addAttribute("zhiChengList", zhiChenglist);
			model.addAttribute("majorList", majorList);
			model.addAttribute("tea", tea);
			return "Secretary/teacherAccountInfo.jsp";
		 }
		 
		 /**
		  * 更改教师
		  * @param userNum
		  * @param stuName
		  * @param sex
		  * @param password
		  * @param tel
		  * @param mail
		  * @param major
		  * @param stuClass
		  * @param model
		  * @param request
		  * @param response
		  * @return
		  */
		 @RequestMapping("/user/findTeacher_updateTeaInfo.shtm")
		 public String updateTeaInfo(String teaName, String sex,String zhicheng,
				 String password, String tel, String mail, String major, String userId,
				 BasePage page, Model model,  HttpServletRequest request, HttpServletResponse response){
			 /**
				 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
				 */
				page.setPageNo(page.getPageNo());
			 Subject currentUser = SecurityUtils.getSubject();
				if (!currentUser.hasRole("manager")) {
					model.addAttribute("errorMsg", "无权限！");
					return "error/error.jsp";
				}
			 if(StringUtils.isBlank(teaName)){
				 model.addAttribute("errorMsg", "姓名不能为空！");
				 return findTeacherInfo(userId, model, request, response);
			 }
			 User user = new User();
			 user.setId(userId);
			 if(StringUtils.isNotBlank(password)){
				 user.setPassword(StringHelper.getMD5(String.valueOf(password)));
			 }			 
			 user.setRealName(teaName);
			 user.setSex(sex);			 
			 Teacher tea = new Teacher();
			 tea.setTeaName(teaName);
			 tea.setSex(sex);
			 tea.setZhicheng(zhicheng);
			 tea.setTel(tel);
			 tea.setMail(mail);
			 if(!"0".equals(major)){
				 tea.setMajor(major);			 
			 }
			 tea.setUserId(userId);
			 
			 int i = 0,j = 0;
			 try {
				 i = userSer.updateUserInfo(user);
				 if(i!=0){
					 j = userSer.updateTeaInfo(tea);				 
				 }
				
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMsg", "系统异常！");
				return "error/error.jsp";
			}
			if(i!=0&&j!=0){
				model.addAttribute("successMsg", "修改成功！");			
			}
			return findTeacher(model, null, null, null, page, request, response);
		 }
		 
		 /**
		  * 跳转到增加老师的界面
		  * @param model
		  * @param request
		  * @param response
		  * @return
		  */
		 @RequestMapping("/user/findTeacher_jumpAddTeacher.shtm")
		 public String jumpAddTeacher(Model model, 
				 HttpServletRequest request, HttpServletResponse response){
			 Subject currentUser = SecurityUtils.getSubject();
				if (!currentUser.hasRole("manager")){
					model.addAttribute("errorMsg", "无权限！");
					return "error/error.jsp";
				}
				List<Major> majorList = publicSer.findMajor();
				List<ZhiCheng> zhiChenglist = new ArrayList<ZhiCheng>();
				zhiChenglist = publicSer.findZhiCheng();
				model.addAttribute("zhiChengList", zhiChenglist);
				model.addAttribute("majorList", majorList);
			    return "Secretary/addTeacher.jsp";
		 }
		 
		 /**
		  * 增加老师
		  * @param stuNum
		  * @param stuName
		  * @param sex
		  * @param roleName
		  * @param model
		  * @param request
		  * @param response
		  * @return
		  */
		 @RequestMapping("/user/findTeacher_addTeacher.shtm")
		 public String addTeacher(String teaNum, String teaName, String sex, String roleName,String password,
				String department,String zhiCheng,String major,BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
			 /**
				 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
				 */
				page.setPageNo(page.getPageNo());
			 Subject currentUser = SecurityUtils.getSubject();
				if (!currentUser.hasRole("manager")) {
					model.addAttribute("errorMsg", "无权限！");
					return "error/error.jsp";
				}
			User loginUser = (User) request.getSession().getAttribute("user");
			User u = userSer.findUserByUserNum(teaNum);
			String id = UuidHelper.getRandomUUID();
	     	if(u==null){
	     		//添加用户表	 
	     		u = new User();
	        	u.setUserNum(teaNum);
	        	u.setRealName(teaName);
	        	u.setSex(sex);
	        	if(StringUtils.isNotBlank(password)){
	        		u.setPassword(password);
	        	}else{
	        		u.setPassword(StringHelper.getMD5(String.valueOf(teaNum)));
	        	}	    	
	        	u.setRoleName(roleName);
	        	u.setId(id);
	        	u.setCreateTime(new Date());
	        	u.setLocked(false);
	        	u.setSuperAdmin(false);
	        	u.setCreateUser(loginUser.getRealName());	
	        	//添加教师表信息(Teacher表)
				Teacher teacher = null;
				try{
					teacher = userSer.finTeacherByUserNum(teaNum);
				}catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("errorMsg", "系统异常！");
					return "error/error.jsp";
				}
				if(teacher==null)
				{
					teacher = new Teacher();
					teacher.setId(UuidHelper.getRandomUUID());
					teacher.setTeaNum(teaNum);
					teacher.setTeaName(teaName);
					teacher.setSex(sex);
					teacher.setZhicheng(zhiCheng);
					teacher.setDepartment(department);
					teacher.setMajor(major);
					teacher.setUserId(id);
					teacher.setAllStunum(8);
					try {
						userSer.addUser(u,teacher,roleName);	
					} catch (Exception e) {
						e.printStackTrace();
    					model.addAttribute("errorMsg", "系统异常！");
    					return "error/error.jsp";
					}
				}
				UserRole ur = new UserRole();
				ur.setId(UuidHelper.getRandomUUID());
				ur.setFkUser(id);
				ur.setFkRole("3");
				ur.setCreateUser(loginUser.getRealName());
				ur.setCreateTime(new Date());				
				try {
					userSer.addUserRole(ur);
				}catch (Exception e){
					e.printStackTrace();
					model.addAttribute("errorMsg", "系统异常！");
					return "error/error.jsp";
				}
		     	model.addAttribute("successMsg", "增加成功！");
	     	}else{
	     		model.addAttribute("errorMsg", "账号已存在！");
	     		return "Secretary/addTeacher.jsp";
	     	}	     	
			return findTeacher(model, null, null, null, page, request, response);
		 }
		 
	
	 /**
	  * 查询已增加老师的角色
	  * @param model
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("/user/findTeaRole.shtm")
	 public String findTeaRole(Model model, String userNum, String userName, String role,
			 BasePage<Teacher> page, HttpServletRequest request, HttpServletResponse response){
		 /**
			 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
			 */
			page.setPageNo(page.getPageNo());
		    Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.hasRole("manager")) {
				model.addAttribute("errorMsg", "无权限！");
				return "error/error.jsp";
			}
		 page.setPageUrl("user/findTeacher.shtm");
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(StringUtils.isNotBlank(userNum)){
				map.put("teaNum", userNum);
				model.addAttribute("userNum", userNum);
			}
			if(StringUtils.isNotBlank(userName)){
				model.addAttribute("userName", userName);
				userName = "%" + userName + "%";
				map.put("teaName", userName);
			}
			if(StringUtils.isBlank(role)){
				role = "5";
			}
			map.put("role", role);
			if(role.equals("5")){
				try {
					//查询教师信息
					page = userSer.findTeacherByPage(map, page);
					if(page.getResults().size()!=0){//结果集不空						
						for(int i=0;i<page.getResults().size();i++){//对每个教师进行角色至string							
							String fkUser= ((Teacher) page.getResults().get(i)).getUserId();//获取用户ID
							//根据ID查询角色信息
							List<UserRole> roles=new ArrayList<UserRole>();
							roles=userRoleSer.findByFKUser(fkUser);//通过用户Id获取拥有的多个角色													
							String roleString="";
							if(roles.size()==1){			
									roleString+="未设置角色";															
							}else{
								for(int j=0;j<roles.size();j++){													
									if(roles.get(j).getFkRole().equals("1")){
										roleString+="教学秘书;";
									}else if(roles.get(j).getFkRole().equals("2")){
										roleString+="专业负责人;";
									}else if (roles.get(j).getFkRole().equals("3")) {
										roleString+="指导老师;";
									}
								}
							}							
						 page.getResults().get(i).setUserHasRole(roleString);							
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("errorMsg", "系统错误！");
					return "error/error.jsp";
				}				
			}else{
				try {
					page = userSer.findTeacherByPage(map, page);
					for(int j=0;j<page.getResults().size();j++){
						String sbString="";
						if(role.equals("1")){
							sbString+="教学秘书";
						}else if(role.equals("2")){
							sbString+="专业负责人";
						}else if (role.equals("3")){
							sbString+="指导老师";
						}
						page.getResults().get(j).setUserHasRole(sbString);										
					}	
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("errorMsg", "系统错误！");
					return "error/error.jsp";
				}
			}		 
		 model.addAttribute("role", role);
		 model.addAttribute("page", page);		 
		 return "Secretary/setRole.jsp";
	 }
		 
	
	 /**
	  * 给教师设置角色
	  * @param ids
	  * @param role
	  * @param model
	  * @param page
	  * @param reqst
	  * @param response
	  * @return
	  */
	 @RequestMapping("/user/findTeaRole_setRoleForTea.shtm")
	 public String setRoleForTea(String[] ids, String role, String userNum, String userName, String findRole, 
			 Model model, BasePage page, HttpServletRequest request, HttpServletResponse response){
		 /**
			 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
			 */
			page.setPageNo(page.getPageNo());
		 Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.hasRole("manager")) {
				model.addAttribute("errorMsg", "无权限！");
				return "error/error.jsp";
			}
		 User loginUser = (User) request.getSession().getAttribute("user");
		 if(ids==null){
			 model.addAttribute("errorMsg", "您未选择数据！");
			 return findTeaRole(model, userNum, userName, findRole, page, request, response);
		 }
		 if(StringUtils.isBlank(role)){
			 model.addAttribute("errorMsg", "系统异常！");
			 return findTeaRole(model, userNum, userName, findRole, page, request, response);
		 }
		 
		 List<String> list = Arrays.asList(ids);
		 for (int i = 0; i < list.size(); i++) {
			List<UserRole> urList = userSer.findUserRoleByUserId(list.get(i));
			List<String> roleList = new ArrayList<String>();
			for (int j = 0; j < urList.size(); j++) {
				roleList.add(urList.get(j).getFkRole());
			}
			if(!roleList.contains(role)){
				UserRole ur = new UserRole();
				ur.setId(UuidHelper.getRandomUUID());
				ur.setFkUser(list.get(i));
				ur.setFkRole(role);
				ur.setCreateUser(loginUser.getRealName());
				ur.setCreateTime(new Date());				
				try {
					userSer.addUserRole(ur);
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("errorMsg", "系统异常！");
					return "error/error.jsp";
				}
			}
		}
		return findTeaRole(model, userNum, userName, role, page, request, response);
	 }
	 
	 /**
	  * 删除教师角色
	  * @param id
	  * @param model
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("/user/findTeaRole_deleteTeaRole.shtm")
	 public String deleteTeaRole(String userId, String role, Model model,
			BasePage page, HttpServletRequest request, HttpServletResponse response){
		 /**
			 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
			 */
			page.setPageNo(page.getPageNo());
			
		 Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.hasRole("manager")) {
				model.addAttribute("errorMsg", "无权限！");
				return "error/error.jsp";
			}	
		 if(StringUtils.isBlank(userId)){
			 model.addAttribute("errorMsg", "系统异常！");
			 return "error/error.jsp";
		 }
		 if(StringUtils.isBlank(role)){
			 model.addAttribute("errorMsg", "系统异常！");
			 return "error/error.jsp";
		 }
		 String method = request.getMethod(); 
        if("GET".equalsIgnoreCase(method)){ 
        	if(StringUtils.isNotBlank(role)){
        		try {
					role = new String(role.getBytes("iso8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
        	}    		
        }
	     if(role.equals("教学秘书")){
	    	 role="1";
	     }else if(role.equals("专业负责人")){
	    	 role="2";
	     }else if(role.equals("指导老师")){
	    	 role="3";
	     }else if(role.equals("学生")){
	    	 role="4";
	     }else if(role.equals("教师")){
	    	 role="5";
	     }
		 if("4".equals(role)||"5".equals(role)){
			 model.addAttribute("errorMsg", "用户的基本角色不能删除！");
			 return findTeaRole(model, null, null, role, page, request, response);
		 }
		 if("1".equals(role)){
			 List<UserRole> urList = new ArrayList<UserRole>();
			 urList = userSer.findUserRoleByRole(role);
			 if(urList.size()==1){
				 model.addAttribute("errorMsg", "系统唯一的教学秘书不能删除！");
				 return findTeaRole(model, null, null, role, page, request, response);
			 }
		 }
		 int i = 0;
		 try {
			i = userSer.deleteByUserIdAndRole(userId, role);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(i!=0){
			model.addAttribute("successMsg", "删除成功！");
		}
		return findTeaRole(model, null, null, role, page, request, response);
	 }
	 
		 
	/**
	 * 导入Excel用户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/user/uploadExcel.shtm")
	@RequiresRoles("manager")
	public String uploadExcel(Model model, HttpServletRequest request, BasePage page,
			HttpServletResponse response, String roleName) throws Exception{
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")){
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		User loginUser = (User) request.getSession().getAttribute("user");
		StringBuffer logInfo = new StringBuffer();
		logInfo.append("operator:").append(loginUser.getRealName()).append(",operation:/user/uploadExcel.shtm");
		logInfo.toString();
		MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest) request;
		InputStream in =null;  
	    List<List<Object>> listob = null; 
		MultipartFile file = multipartRequest.getFile("upfile");
		if(file.isEmpty()){  
			model.addAttribute("errorMsg", "文件不存在！");
			return "error/error.jsp";
        } 
		in = file.getInputStream();  
        listob = new ImportExcelHelper().getBankListByExcel(in,file.getOriginalFilename());  
        //添加之前需要先查询用户账号是否已存在
        for (int i = 0; i < listob.size(); i++){
			List<Object> lo = listob.get(i);
        	User u = new User();
        	User a = userSer.findUserByUserNum(String.valueOf(lo.get(0)));
        	String id = null;
        	if(a==null){
        		try {
        			//判断账号类型(教师 or 学生)
        			if("teacher".equals(roleName)){
        				//添加User表数据
        				id = UuidHelper.getRandomUUID();
			        	u.setUserNum(String.valueOf(lo.get(0)));
			        	u.setRealName(String.valueOf(lo.get(1)));
			        	u.setSex(String.valueOf(lo.get(2)));
			        	u.setPassword(StringHelper.getMD5(String.valueOf(lo.get(0))));
			        	u.setRoleName(roleName);
			        	u.setId(id);
			        	u.setCreateTime(new Date());
			        	u.setLocked(false);
			        	u.setSuperAdmin(false);
			        	u.setCreateUser(loginUser.getRealName());		        			        		
        				//添加教师表信息(Teacher表)
        				Teacher teacher = null;
        				try{
        					teacher = userSer.finTeacherByUserNum(String.valueOf(lo.get(0)));
        				}catch (Exception e) {
        					e.printStackTrace();
        					model.addAttribute("errorMsg", "系统异常！");
        					return "error/error.jsp";
						}
        				if(teacher==null)
        				{
        					teacher = new Teacher();
        					teacher.setId(UuidHelper.getRandomUUID());
        					teacher.setTeaNum(String.valueOf(lo.get(0)));
        					teacher.setTeaName(String.valueOf(lo.get(1)));
        					teacher.setSex(String.valueOf(lo.get(2)));
        					teacher.setZhicheng(String.valueOf(lo.get(3)));
        					teacher.setDepartment(String.valueOf(lo.get(4)));
        					teacher.setMajor(String.valueOf(lo.get(5)));
        					teacher.setUserId(id);
        					teacher.setAllStunum(8);
        					try {
        						userSer.addUser(u,teacher,roleName);	
							} catch (Exception e) {
								e.printStackTrace();
	        					model.addAttribute("errorMsg", "系统异常！");
	        					return "error/error.jsp";
							}
        				}
        				//添加权限(User_role表)
		        		UserRole ur = new UserRole();
        				ur.setId(UuidHelper.getRandomUUID());
        				ur.setFkUser(id);
        				ur.setFkRole("3");//默认设置为指导老师
        				ur.setCreateUser(loginUser.getRealName());
        				ur.setCreateTime(new Date());				
        				try {
        					userSer.addUserRole(ur);
        				} catch (Exception e){
        					e.printStackTrace();
        					model.addAttribute("errorMsg", "系统异常！");
        					return "error/error.jsp";
        				}
        			}
        			//账号类型--学生
        			else if("student".equals(roleName)){
        				//添加User表数据
        				id = UuidHelper.getRandomUUID();
			        	u.setUserNum(String.valueOf(lo.get(0)));
			        	u.setRealName(String.valueOf(lo.get(1)));
			        	u.setSex(String.valueOf(lo.get(2)));
			        	u.setPassword(StringHelper.getMD5(String.valueOf(lo.get(0))));
			        	u.setRoleName(roleName);
			        	u.setId(id);
			        	u.setCreateTime(new Date());
			        	u.setLocked(false);
			        	u.setSuperAdmin(false);
			        	u.setCreateUser(loginUser.getRealName());		        		
        				//查询账号在student表是否存在
        				Student student = null;
        				try{
        					student = userSer.finStudentByUserNum(String.valueOf(lo.get(0)));
        				}catch (Exception e) {
        					e.printStackTrace();
        					model.addAttribute("errorMsg", "系统异常！");
        					return "error/error.jsp";
						}
        				if(student == null)
        				{
        					student = new Student();
        					student.setId(UuidHelper.getRandomUUID());
        					student.setUserId(id);
        					student.setUserNum(String.valueOf(lo.get(0)));
        					student.setStuName(String.valueOf(lo.get(1)));
        					student.setSex(String.valueOf(lo.get(2)));
        					student.setDepartment(String.valueOf(lo.get(3)));
        					student.setMajor(String.valueOf(lo.get(4)));
        					student.setStuClass(String.valueOf(lo.get(5)));
        					try {
        						userSer.addUser(u,student,roleName);	
							} catch (Exception e) {
								e.printStackTrace();
	        					model.addAttribute("errorMsg", "系统异常！");
	        					return "error/error.jsp";
							}      					
        				}      				
	        	    }
				} catch (Exception e) {
					e.printStackTrace();
					if("student".equals(roleName)){
						model.addAttribute("errorMsg", "请检查Excel表格格式！");
						return findStudent(model, null, null, page, multipartRequest, response);
					}else if("teacher".equals(roleName)){
						model.addAttribute("errorMsg", "请检查Excel表格格式！");
						return findStudent(model, null, null, page, multipartRequest, response);
					}else{
						model.addAttribute("errorMsg", "系统异常！");
						return "error/error.jsp";
					}
				}
				
        	}
		}	      
        model.addAttribute("successMsg", "导入成功！");
        if("student".equals(roleName)){
			return "redirect:/user/findStudent.shtm";
		}else if("teacher".equals(roleName)){
			return "redirect:/user/findTeacher.shtm";
		}else{
			return "error/error.jsp";
		}
	}	  
	
	
}
