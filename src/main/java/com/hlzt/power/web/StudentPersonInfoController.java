package com.hlzt.power.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlzt.commons.helper.StringHelper;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.User;
import com.hlzt.power.service.PublicSer;
import com.hlzt.power.service.UserSer;
@Controller
@RequestMapping("/student")
public class StudentPersonInfoController {
	@Autowired
	private UserSer userSer;
	@Autowired
	private PublicSer publicSer;
	
	
	/**
	  * 查询学生信息
	  * @param id
	  * @param model
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("/findInfo.shtm")
	 public String findStudentInfo(Model model,
			 HttpServletRequest request, HttpServletResponse response){
		 User loginUser = (User) request.getSession().getAttribute("user");
		 Student stu = new Student();
		 
		 try {
			stu = userSer.findStudentInfo(loginUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		List<Major> majorList = publicSer.findMajor();
		List<ClassName> classList = publicSer.findClass(null);
		model.addAttribute("student", stu);
		model.addAttribute("majorList", majorList);
		model.addAttribute("classList", classList);
		return "Student/studentAccountInfo.jsp";
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
	 @RequestMapping("/findInfo_updateStuInfo.shtm")
	 public String updateStuInfo(String password, String tel, String mail, String userId,
			 BasePage page, Model model,  HttpServletRequest request, HttpServletResponse response){
	    /**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		 page.setPageNo(page.getPageNo());

		 if(StringUtils.isBlank(tel)){
			 model.addAttribute("errorMsg", "手机号不能为空！");
			 return findStudentInfo(model, request, response);
		 }
		 if(StringUtils.isBlank(mail)){
			 model.addAttribute("errorMsg", "电子邮箱不能为空！");
			 return findStudentInfo(model, request, response);
		 }
		 User user = (User) request.getSession().getAttribute("user");
		 user.setId(userId);
		 if(StringUtils.isNotBlank(password)){
			 user.setPassword(StringHelper.getMD5(String.valueOf(password)));
		 }			 
		 
		 Student stu = (Student) request.getSession().getAttribute("student");
		 stu.setTel(tel);
		 stu.setMail(mail);
		 stu.setUserId(userId);
		 
		 int i = 0,j = 0;
		 try {
			 i = userSer.updateUserInfo(user);
			 if(i!=0){
				 j = userSer.updateStuInfo(stu);				 
			 }
			
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		if(i!=0&&j!=0){
			model.addAttribute("student", stu);
			model.addAttribute("successMsg", "修改成功！");			
		}
		request.getSession().setAttribute("user", user);
		request.getSession().setAttribute("student", stu);
		return findStudentInfo(model, request, response);
	 }
	
}
