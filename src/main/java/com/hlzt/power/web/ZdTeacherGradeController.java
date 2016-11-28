package com.hlzt.power.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlzt.commons.helper.StringHelper;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.dao.StudentDao;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.service.ZdTeacherGradeSer;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;
@Controller
@RequestMapping("/guideTeacher")
public class ZdTeacherGradeController {

	@Resource
	private ZdTeacherGradeSer zdTeacherGradeSer;
	
	/**
	 * 查看学生审阅成绩
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/syGrade.shtm")
	public String syGrade(String stuNum,String stuName, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Map<String ,Object> map = new HashMap<String, Object>();
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		map.put("teaId", teacher.getUserId());// 教师ID

		if (StringUtils.isNotBlank(stuNum))
			map.put("stuNum", stuNum);
		if (StringUtils.isNotBlank(stuName)){
			model.addAttribute("stuName", stuName);
			map.put("stuName", StringHelper.formatLike(stuName));			
		}
		
		try {
			page = zdTeacherGradeSer.findSyGrade(map, page);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("page", page);
		model.addAttribute("stuNum", stuNum);	
		return "guideTeacher/syGrade.jsp";
		//如果是审阅阶段则sy_teacher=teaId	
	}
	
	/**
	 * 录入审阅成绩
	 * @param syGrade
	 * @param stuId
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/syGrade_setSyGrade.shtm")
	public String setSyGrade(String syGrade, String stuNum, Model model, BasePage page,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		if(StringUtils.isBlank(syGrade)){
			model.addAttribute("errorMsg", "请输入成绩！");
			return syGrade(null, null, page, model, request, response);
		}
		if(StringUtils.isBlank(stuNum)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		Pattern p = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
		Matcher m = p.matcher(syGrade);
		if(m.matches()==false){
			model.addAttribute("errorMsg","请输入数字！");
			return syGrade(null, null, page, model, request, response);
		}
		float num = 0;
		try {
			num = Float.parseFloat(syGrade);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg","请输入数字！");
			return syGrade(null, null, page, model, request, response);
		}
		int i = 0;
		try {
			i = zdTeacherGradeSer.setSyGrade(num, stuNum);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		return syGrade(null, null, page, model, request, response);
	}
	
	/**
	 * 查看评阅学生成绩
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pyGrade.shtm")
	public String pyGrade(String stuNum,String stuName, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Map<String ,Object> map = new HashMap<String, Object>();
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		map.put("teaId", teacher.getUserId());// 教师ID

		if (StringUtils.isNotBlank(stuNum))
			map.put("stuNum", stuNum);
		if (StringUtils.isNotBlank(stuName)){
			model.addAttribute("stuName", stuName);
			map.put("stuName", StringHelper.formatLike(stuName));			
		}
		
		try {
			page = zdTeacherGradeSer.findPyGrade(map, page);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}			
		model.addAttribute("page", page);
		model.addAttribute("stuNum", stuNum);
	
		return "guideTeacher/pyGrade.jsp";
	}	
	
	/**
	 * 录入评阅成绩
	 * @param syGrade
	 * @param stuId
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pyGrade_setPyGrade.shtm")
	public String setPyGrade(String pyGrade, String stuNum, Model model, BasePage page,
			HttpServletRequest request, HttpServletResponse response){
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(pyGrade)){
			model.addAttribute("errorMsg", "请输入成绩！");
			return pyGrade(null, null, page, model, request, response);
		}
		if(StringUtils.isBlank(stuNum)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		Pattern p = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
		Matcher m = p.matcher(pyGrade);
		if(m.matches()==false){
			model.addAttribute("errorMsg","请输入数字！");
			return pyGrade(null, null, page, model, request, response);
		}
		float num = 0;
		try {
			num = Float.parseFloat(pyGrade);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg","请输入数字！");
			return pyGrade(null, null, page, model, request, response);
		}
		int i = 0;
		try {
			i = zdTeacherGradeSer.setPyGrade(num, stuNum);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		return pyGrade(null, null, page, model, request, response);
	}
	
	/**
	 * 查看学生答辩成绩
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dbGrade.shtm")
	public String dbGrade(String stuNum,String stuName, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Map<String ,Object> map = new HashMap<String, Object>();
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if (StringUtils.isNotBlank(stuNum))
			map.put("stuNum", stuNum);
		if (StringUtils.isNotBlank(stuName)){
			model.addAttribute("stuName", stuName);
			map.put("stuName", StringHelper.formatLike(stuName));			
		}
		
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("teaId", teacher.getUserId());
		List<DbGroup> dbGroups=zdTeacherGradeSer.checkDbMishu(m);//检查自己是否是答辩秘书map.teaId
		if(dbGroups.isEmpty()){ //数据库中不存在记录teaid=group_secreatary,即该教师不是答辩秘书
			model.addAttribute("errorMsg", "您不存在需要录入答辩成绩的学生！");
			return "guideTeacher/dbGrade.jsp";
		}
		try {
			map.put("dbGroup", dbGroups.get(0));
			page = zdTeacherGradeSer.findDbGrade(map, page);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("page", page);
		return "guideTeacher/dbGrade.jsp";
	}
	
	/**
	 * 录入答辩成绩
	 * @param syGrade
	 * @param stuId
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dbGrade_setDbGrade.shtm")
	public String setDbGrade(String dbGrade, String stuNum, Model model, BasePage page,
			HttpServletRequest request, HttpServletResponse response){
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(dbGrade)){
			model.addAttribute("errorMsg", "请输入成绩！");
			return dbGrade(null, null, page, model, request, response);
		}
		if(StringUtils.isBlank(stuNum)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		Pattern p = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
		Matcher m = p.matcher(dbGrade);
		if(m.matches()==false){
			model.addAttribute("errorMsg","请输入数字！");
			return dbGrade(null, null, page, model, request, response);
		}
		float num = 0;
		try {
			num = Float.parseFloat(dbGrade);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg","请输入数字！");
			return dbGrade(null, null, page, model, request, response);
		}
		int i = 0;
		try {
			i = zdTeacherGradeSer.setDbGrade(num, stuNum);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		return dbGrade(null, null, page, model, request, response);
	}
	
		
	
}
