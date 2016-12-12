package com.hlzt.power.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContext;

import com.hlzt.commons.helper.UuidHelper;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.BackLog;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.TeacherTitle;
import com.hlzt.power.model.ZhiCheng;
import com.hlzt.power.service.MajorLeaderFlowManageSer;
import com.hlzt.power.service.PublicSer;

/**
 * 专业负责人流程管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/majorLeader")
public class MajorLeaderFlowManageController {
	
	@Resource
	private MajorLeaderFlowManageSer majorLeaderFlowManageSer;
	@Resource
	private PublicSer publicSer;
	
	/**
	 * 专业负责人查询本专业的教师
	 * @param teaNum
	 * @param teaName
	 * @param zhicheng
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTeacher.shtm")
	public String findTeacher(String teaNum, String teaName, String zhicheng, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if("0".equals(zhicheng)){
			zhicheng = null;
		}
		List<Teacher> list = new ArrayList<Teacher>();
		try {		
			list = majorLeaderFlowManageSer.findTeacherByCondition(major, teaNum, teaName, zhicheng);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ZhiCheng> zhiChenglist = new ArrayList<ZhiCheng>();
		zhiChenglist = publicSer.findZhiCheng();
		model.addAttribute("zhiChengList", zhiChenglist);
		model.addAttribute("teaNum", teaNum);
		model.addAttribute("teaName", teaName);
		model.addAttribute("zhicheng", zhicheng);
		model.addAttribute("list",list);
		
		return "Director/teacher_stu_num.jsp";
	}
	
	/**
	 * 专业负责人给自己专业的教师统一设置所带学生数
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTeacher_setAllTeaStuNum.shtm")
	public String setAllTeaStuNum(String number, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(number)){
			model.addAttribute("errorMsg","请输入教师所带学生数");
			return findTeacher(null, null, null, model, request, response);
		}
		Pattern p = Pattern.compile("^[0-9]*[1-9][0-9]*$");
		Matcher m = p.matcher(number);
		if(m.matches()==false){
			model.addAttribute("errorMsg","请输入数字");
			return findTeacher(null, null, null, model, request, response);
		}
		int num = 0;
		try {
			num = Integer.parseInt(number);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		int i = 0;
		try {
			i = majorLeaderFlowManageSer.setAllTeaStuNum(num, major);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0){
			model.addAttribute("errorMsg","系统异常！操作失败！");
			return findTeacher(null, null, null, model, request, response);
		}
		model.addAttribute("successMsg", "操作成功！");
		return findTeacher(null, null, null, model, request, response);
	}
	
	/**
	 * 专业负责人批量给自己专业教师设置所带学生数
	 * @param ids
	 * @param number
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findTeacher_setTeaStuNum.shtm")
	public String setTeaStuNum(String teaNum, String teaName, String zhicheng, String[] ids, 
			String number, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		RequestContext requestContext = new RequestContext(request);
		if(ids==null||"".equals(ids)){
			model.addAttribute("errorMsg",requestContext.getMessage("check.teacher.error"));
			return findTeacher(teaNum, teaName, zhicheng, model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(number)){
			model.addAttribute("errorMsg","请输入教师所带学生数！");
			return findTeacher(teaNum, teaName, zhicheng, model, request, response);
		}
		Pattern p = Pattern.compile("^([1-9]\\d*|0)$");
		Matcher m = p.matcher(number);
		if(m.matches()==false){
			model.addAttribute("errorMsg","请输入整数！");
			return findTeacher(teaNum, teaName, zhicheng, model, request, response);
		}
		int num = 0;
		try {
			num = Integer.parseInt(number);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int i = 0;
		try {
			i = majorLeaderFlowManageSer.setTeacherManageStuNum(list, num, major);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0){
			model.addAttribute("errorMsg","系统异常！操作失败！");
		}
		
		return findTeacher(teaNum, teaName, zhicheng, model, request, response);
	}
	
	/**
	 * 查询本专业教师发布的课题
	 * @param teaName
	 * @param status
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTeaTitle.shtm")
	public String findTeaTitle(String teaName, String status, BasePage page, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		page.setPageUrl("majorLeader/findTeaTitle.shtm");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if("3".equals(status)){
			status = null;
		}
		try {
			page = majorLeaderFlowManageSer.findTeaTitle(major, teaName, status, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("teaName", teaName);
		model.addAttribute("status", status);
		model.addAttribute("page", page);
		
		return "/Director/teacher_apply_project.jsp";
	}
	
	/**
	 * 根据ID查询教师发布的课题详情
	 * @param id
	 * @param model
	 * @param reqest
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTeaTitle_findTeaTitleById.shtm")
	public String findTeaTitleById(String id, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(id)){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		TeacherTitle teacherTitle = new TeacherTitle();
		try {
			teacherTitle = majorLeaderFlowManageSer.findTeaTitleById(id, major);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("teacherTitle", teacherTitle);
		return "/Director/project_info.jsp";
	}
	
	/**
	 * 审核教师发布的课题
	 * @param ids
	 * @param leaderIdea
	 * @param teaName
	 * @param status
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTeaTitle_checkTeaTitle.shtm")
	public String checkTeaTitle(String[] ids, String status, String leaderIdea, String teaName, String findStatus, 
			BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(ids==null){
			model.addAttribute("errorMsg","您未选择数据！");
			return findTeaTitle(teaName, findStatus, page, model, request, response);
		}
		if(StringUtils.isBlank(status)){
			model.addAttribute("errorMsg","审核失败！系统异常！");
			return findTeaTitle(teaName, findStatus, page, model, request, response);
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = majorLeaderFlowManageSer.checkTeaTitle(list, status, leaderIdea);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0){
			model.addAttribute("errorMsg","审核失败！系统异常！");
			return findTeaTitle(teaName, findStatus, page, model, request, response);
		}
		model.addAttribute("teaName", teaName);
		model.addAttribute("status", findStatus);
		//专业负责人审核通过，添加教学秘书的待办事项提示
		if(status.equals("1")){
			for(int f =0;f<list.size();f++){
			List<BackLog> backLogs = publicSer.findBackLog(null,null,"manager");
			Boolean bool = false;
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("teaApplyTitle")){
						int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
						bool=true;						
						break;
					}
				}		
			}
			if(!bool){		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setBackLog("teaApplyTitle");
				backLog.setTeaStatus("1");
				backLog.setLeaderStatus("1");
				backLog.setManagerStatus("0");
				backLog.setCreateTime(new Date());
				backLog.setCreateUser(teacher.getTeaName());
				int b = publicSer.insertBackLog(backLog);
				int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
			}
			}
		}
		if(status.equals("1")||status.equals("2")){
			//删除待办事项记录
			List<BackLog> backLogs = publicSer.findBackLog(null,teacher.getMajor(),"leader");
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("teaApplyTitle")){
						if(backLogs.get(q).getNumbers()==1||backLogs.get(q).getNumbers()==i){
							int n = publicSer.removeBackLog(backLogs.get(q).getId());
						}else{
							for(int j = 0;j<i;j++){
							int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
						   }
						}
					}
				}		
			}
			//添加待办事项，通知教师进度更新
			for(int k=0;k<list.size();k++){
				TeacherTitle teacherTitle = majorLeaderFlowManageSer.findTeaTitleById(list.get(k),teacher.getMajor());
				List<BackLog> backLogList = publicSer.findBackLog(teacherTitle.getTeacherId(),null,"teacher");
				Boolean bool = false;
				if(!backLogList.isEmpty()){
					for(int q=0;q<backLogList.size();q++){
						if(backLogList.get(q).getBackLog().equals("reTeaApplyTitle")){
							int m = publicSer.updateBackLogNumById(backLogList.get(q).getId(),"add");
							bool=true;						
							break;
						}
					}		
				}
				if(!bool){		
					BackLog backLog = new BackLog();
					backLog.setId(UuidHelper.getRandomUUID());
					backLog.setTeaId(teacherTitle.getTeacherId());
					backLog.setBackLog("reTeaApplyTitle");
					backLog.setTeaStatus("0");
					backLog.setCreateTime(new Date());
					backLog.setCreateUser(teacher.getTeaName());
					int b = publicSer.insertBackLog(backLog);
					int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
				}
			}
		}
		return findTeaTitle(teaName, findStatus, page, model, request, response);
	}
	
	/**
	 * 查询学生自拟课题
	 * @param stuNum
	 * @param stuName
	 * @param teaName
	 * @param status
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findApplyTitle.shtm")
	public String findApplyTitle(String stuNum, String stuName, String teaName, String status, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		page.setPageUrl("/majorLeader/findApplyTitle.shtm");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		model.addAttribute("status", status);
		if("3".equals(status)){
			status = null;
		}
		try {
			page = majorLeaderFlowManageSer.findApplyTitle(major, stuNum, stuName, teaName, status, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		model.addAttribute("teaName", teaName);
		model.addAttribute("page", page);
		
		return "/Director/check_double_final.jsp";
	}
	
	/**
	 * 根据ID查询学生自拟课题详情
	 * @param id
	 * @param model
	 * @param reqest
	 * @param response
	 * @return
	 */
	@RequestMapping("/findApplyTitle_findStuTitleById.shtm")
	public String findStuTitleById(String id, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(id)){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		ApplyTitle at = new ApplyTitle();
		try {
			at = majorLeaderFlowManageSer.findStuTitleById(id, major);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("applyTitle", at);
		return "/Director/stu_title_info.jsp";
	}
	
	/**
	 * 审核学生课题
	 * @param ids
	 * @param status
	 * @param stuNum
	 * @param stuName
	 * @param teaName
	 * @param findStatus
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findApplyTitle_checkApplyTitle.shtm")
	public String checkApplyTitle(String[] ids, String status, String stuNum, String stuName, String teaName, 
			String findStatus, BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if(ids==null){
			model.addAttribute("errorMsg","您未选择数据！");
			return findApplyTitle(stuNum, stuName, teaName, findStatus, page, model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {		
			i = majorLeaderFlowManageSer.checkApplyTitle(list, status, major);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0){
			model.addAttribute("errorMsg","系统异常！审核失败");
			return findApplyTitle(stuNum, stuName, teaName, findStatus, page, model, request, response);
		}
		//专业负责人审核通过，添加教学秘书的待办事项提示
		if(status.equals("1")){
			for(int f =0;f<list.size();f++){
			List<BackLog> backLogs = publicSer.findBackLog(null,null,"manager");
			Boolean bool = false;
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("stuApplyTitle")){
						int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
						bool=true;						
					}
				}		
			}
			if(!bool){		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setBackLog("stuApplyTitle");
				backLog.setTeaStatus("1");
				backLog.setLeaderStatus("1");
				backLog.setManagerStatus("0");
				backLog.setCreateTime(new Date());
				backLog.setCreateUser(teacher.getTeaName());
				int b = publicSer.insertBackLog(backLog);
				int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
			}
		 }
		}
		if(status.equals("1")||status.equals("2")){
			//删除待办事项记录
			List<BackLog> backLogs = publicSer.findBackLog(null,teacher.getMajor(),"leader");
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("stuApplyTitle")){
						if(backLogs.get(q).getNumbers()==1||backLogs.get(q).getNumbers()==i){
							int n = publicSer.removeBackLog(backLogs.get(q).getId());
						}else{
							for(int j = 0;j<i;j++){
							int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
						   }
						}
					}
				}		
			}
			//添加待办事项，通知学生进度更新
			for(int k=0;k<list.size();k++){
				ApplyTitle applyTitle = majorLeaderFlowManageSer.findStuTitleById(list.get(k),teacher.getMajor());
				List<BackLog> backLogList = publicSer.findBackLog(applyTitle.getStuId(),null,"student");
				Boolean bool = false;
				if(!backLogList.isEmpty()){
					for(int q=0;q<backLogList.size();q++){
						if(backLogList.get(q).getBackLog().equals("reStuApplyTitle")){
							int m = publicSer.updateBackLogNumById(backLogList.get(q).getId(),"add");
							bool=true;						
							break;
						}
					}		
				}
				if(!bool){		
					BackLog backLog = new BackLog();
					backLog.setId(UuidHelper.getRandomUUID());
					backLog.setStuId(applyTitle.getStuId());
					backLog.setBackLog("reStuApplyTitle");
					backLog.setStuStatus("0");
					backLog.setCreateTime(new Date());
					backLog.setCreateUser(teacher.getTeaName());
					int b = publicSer.insertBackLog(backLog);
					int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
				}
			}
		}
		model.addAttribute("successMsg", "审核成功！");
		return findApplyTitle(stuNum, stuName, teaName, findStatus, page, model, request, response);
	}
	
	/**
	 * 指导老师查询本专业任务书
	 * @param stuNum
	 * @param StuName
	 * @param titleName
	 * @param status
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTaskBook.shtm")
	public String findTaskBook(String stuNum, String stuName, String titleName, String status,
			Model model,BasePage page, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		page.setPageUrl("/majorLeader/findTaskBook.shtm");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if("3".equals(status)){
			status = null;
		}
		try {
			page = majorLeaderFlowManageSer.findTaskBook(stuNum, stuName, titleName, status, major, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		model.addAttribute("titleName", titleName);
		model.addAttribute("status", status);
		model.addAttribute("page", page);
		return "/Director/check_stu_task.jsp";
	}
	
	/**
	 * 审核任务书
	 * @param ids
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTaskBook_checkTaskBook.shtm")
	public String checkTaskBook(String stuNum, String stuName, String titleName, String findStatus, String[] ids, 
			String status, BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if(ids==null){
			model.addAttribute("errorMsg","您未选择数据！");
			return findTaskBook(stuNum, stuName, titleName, findStatus, model, page, request, response);
		}
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = majorLeaderFlowManageSer.checkTaskBook(list, major, status);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0){
			model.addAttribute("errorMsg","审核失败！系统异常！");
			return findTaskBook(stuNum, stuName, titleName, findStatus, model, page, request, response);
		}		
		if(status.equals("1")||status.equals("2")){
			//删除待办事项记录
			List<BackLog> backLogs = publicSer.findBackLog(null,teacher.getMajor(),"leader");
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("taskBook")){
						if(backLogs.get(q).getNumbers()==1||backLogs.get(q).getNumbers()==i){
							int n = publicSer.removeBackLog(backLogs.get(q).getId());
						}else{
							for(int j = 0;j<i;j++){
							int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
						   }
						}
					}
				}		
			}
			//添加待办事项，通知学生进度更新
			for(int k=0;k<list.size();k++){
				TaskBook taskBook = majorLeaderFlowManageSer.findTaskBookById(list.get(k));
				List<BackLog> backLogList = publicSer.findBackLog(taskBook.getStuId(),null,"student");
				Boolean bool = false;
				if(!backLogList.isEmpty()){
					for(int q=0;q<backLogList.size();q++){
						if(backLogList.get(q).getBackLog().equals("reTaskBook")){
							int m = publicSer.updateBackLogNumById(backLogList.get(q).getId(),"add");
							bool=true;						
							break;
						}
					}		
				}
				if(!bool){		
					BackLog backLog = new BackLog();
					backLog.setId(UuidHelper.getRandomUUID());
					backLog.setStuId(taskBook.getStuId());
					backLog.setBackLog("reTaskBook");
					backLog.setStuStatus("0");
					backLog.setCreateTime(new Date());
					backLog.setCreateUser(teacher.getTeaName());
					int b = publicSer.insertBackLog(backLog);
					int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
				}
			}
		}
		return findTaskBook(stuNum, stuName, titleName, findStatus, model, page, request, response);
	}
	
	/**
	 * 指导老师查询本专业开题报告
	 * @param stuNum
	 * @param StuName
	 * @param titleName
	 * @param status
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findOpeningReport.shtm")
	public String findOpeningReport(String stuNum, String stuName, String titleName, String status,
			Model model,BasePage page, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		page.setPageUrl("/majorLeader/findOpeningReport.shtm");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if("3".equals(status)){
			status = null;
		}
		try {
			page = majorLeaderFlowManageSer.findOpneingReport(stuNum, stuName, titleName, status, major, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		model.addAttribute("titleName", titleName);
		model.addAttribute("status", status);
		model.addAttribute("page", page);
		return "/Director/check_opening_report.jsp";
	}
	
	/**
	 * 审核开题报告
	 * @param ids
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findOpeningReport_checkOpeningReport.shtm")
	public String checkOpeningReport(String stuNum, String stuName, String titleName, String findStatus, String[] ids, 
			String status, BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if(ids==null){
			model.addAttribute("errorMsg","您未选择数据！");
			return findOpeningReport(stuNum, stuName, titleName, status, model, page, request, response);
		}
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = majorLeaderFlowManageSer.checkOpeningReport(list, major, status);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0){
			model.addAttribute("errorMsg","系统异常！审核失败！");
			return findOpeningReport(stuNum, stuName, titleName, status, model, page, request, response);
		}
		if(status.equals("1")||status.equals("2")){
			//删除待办事项记录
			List<BackLog> backLogs = publicSer.findBackLog(null,teacher.getMajor(),"leader");
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("openingReport")){
						if(backLogs.get(q).getNumbers()==1||backLogs.get(q).getNumbers()==i){
							int n = publicSer.removeBackLog(backLogs.get(q).getId());
						}else{
							for(int j = 0;j<i;j++){
							int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
						   }
						}
					}
				}		
			}
			//添加待办事项，通知学生进度更新
			for(int k=0;k<list.size();k++){
				OpeningReport openingReport = majorLeaderFlowManageSer.findOpeningReportById(list.get(k));
				List<BackLog> backLogList = publicSer.findBackLog(openingReport.getStuId(),null,"student");
				Boolean bool = false;
				if(!backLogList.isEmpty()){
					for(int q=0;q<backLogList.size();q++){
						if(backLogList.get(q).getBackLog().equals("reOpeningReport")){
							int m = publicSer.updateBackLogNumById(backLogList.get(q).getId(),"add");
							bool=true;						
							break;
						}
					}		
				}
				if(!bool){		
					BackLog backLog = new BackLog();
					backLog.setId(UuidHelper.getRandomUUID());
					backLog.setStuId(openingReport.getStuId());
					backLog.setBackLog("reOpeningReport");
					backLog.setStuStatus("0");
					backLog.setCreateTime(new Date());
					backLog.setCreateUser(teacher.getTeaName());
					int b = publicSer.insertBackLog(backLog);
					int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
				}
			}
		}
		return findOpeningReport(stuNum, stuName, titleName, status, model, page, request, response);
	}
	
	/**
	 * 查询评阅老师
	 * @param teaNum
	 * @param teaName
	 * @param zhicheng
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findPyTea.shtm")
	public String findPyTea(String teaNum, String teaName, String zhicheng, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if(StringUtils.isNotBlank(zhicheng)){
			if("0".equals(zhicheng)){
				zhicheng = null;
			}
		}
		List<Teacher> list = new ArrayList<Teacher>();
		try {		
			list = majorLeaderFlowManageSer.findTeacherByCondition(major, teaNum, teaName, zhicheng);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ZhiCheng> zhiChenglist = new ArrayList<ZhiCheng>();
		zhiChenglist = publicSer.findZhiCheng();
		model.addAttribute("zhiChengList", zhiChenglist);
		model.addAttribute("teaNum", teaNum);
		model.addAttribute("teaName", teaName);
		model.addAttribute("zhicheng", zhicheng);
		model.addAttribute("list",list);
		
		return "/Director/arr_read_teacher.jsp";
	}
	
	/**
	 * 进入给老师分配评阅学生的界面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findPyTea_jumpSelectPyStu.shtm")
	public String jumpSelectPyStu(String teaId, Model model, BasePage page,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(teaId)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		
		try {
			page = majorLeaderFlowManageSer.findAllPyStu(teaId, major, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		model.addAttribute("teaId", teaId);
		return "/Director/select_read_stu.jsp";
	}
	
	/**
	 * 进入老师已分配评阅学生的界面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findPyTea_jumpPyStu.shtm")
	public String jumpPyStu(String teaId, Model model, BasePage page,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String major = teacher.getMajor();
		if(StringUtils.isBlank(teaId)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		List<Student> list = new ArrayList<Student>();
		try {
			list = majorLeaderFlowManageSer.findStuByPyTea(teaId, major);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("list", list);
		model.addAttribute("teaId", teaId);
		return "/Director/py_stu.jsp";
	}
	
	/**
	 * 给老师设置评阅学生
	 * @param stuIds
	 * @param teaId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findPyTea_setPyStu.shtm")
	public String setPyStu(String[] stuIds, String teaId, Model model, 
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		if(stuIds==null){
			model.addAttribute("errorMsg", "您未选择学生！");
			return findPyTea(null, null, null, model, request, response);
		}
		if(StringUtils.isBlank(teaId)){
			teaId = "";
		}
		List<String> list = Arrays.asList(stuIds);
		
		try {
			majorLeaderFlowManageSer.setPyStu(list, teaId, major);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		
		return findPyTea(null, null, null, model, request, response);
	}
	
	/**
	 * 查询答辩小组
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findDbGroup.shtm")
	public String findDbGroup(Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		List<DbGroup> list = new ArrayList<DbGroup>();
		try {
			list = 	majorLeaderFlowManageSer.findDbGroup(major);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("list", list);
		return "/Director/arr_but_group.jsp";
	}
	
	
	
	/**
	 * 跳转到设置答辩小组页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findDbGroup_jumpSetDbGroup.shtm")
	public String jumpSetDbGroup(String id, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		
		List<Teacher> superTeaList = new ArrayList<Teacher>();
		try {
			superTeaList = majorLeaderFlowManageSer.findAllSuperTea(major);
			if(StringUtils.isNotBlank(id)){
				DbGroup dbGroup = majorLeaderFlowManageSer.findDbGroupInfoById(id, major);
				model.addAttribute("dbGroup", dbGroup);
				for (int i = 0; i < superTeaList.size(); i++) {
					if(superTeaList.get(i).getUserId().equals(dbGroup.getGroupLeader())){
						superTeaList.get(i).setFlag("1");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		
		model.addAttribute("leaderTea", superTeaList);
		return "/Director/create_reply_group.jsp";
	}
	
	/**
	 * 设置答辩小组
	 * @param groupNum
	 * @param groupName
	 * @param groupSite
	 * @param daTime
	 * @param groupLeader
	 * @param groupMember
	 * @param groupSecretary
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/findDbGroup_setDbGroup.shtm")
	public String addDbGroup(String id, String groupNum, String groupName, String groupSite, String dbTime,
			String groupLeader,Model model ,HttpServletRequest request ,HttpServletResponse response) throws ParseException{
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return jumpSetDbGroup(id, model, request, response);
		}
		if(StringUtils.isBlank(groupNum)){
			model.addAttribute("errorMsg", "小组编号不能为空");
			return jumpSetDbGroup(id, model, request, response);
		}
		if(StringUtils.isBlank(groupName)){
			model.addAttribute("errorMsg", "组名不能为空");
			return jumpSetDbGroup(id, model, request, response);
		}
		if(StringUtils.isBlank(groupSite)){
			model.addAttribute("errorMsg", "答辩地点不能为空");
			return jumpSetDbGroup(id, model, request, response);
		}
		if(StringUtils.isBlank(dbTime)){
			model.addAttribute("errorMsg", "答辩时间不能为空");
			return jumpSetDbGroup(id, model, request, response);
		}
		if(StringUtils.isBlank(groupLeader)){
			model.addAttribute("errorMsg", "请选择答辩组组长");
			return jumpSetDbGroup(id, model, request, response);
		}
		
		
		DbGroup dbGroup = new DbGroup();
		
		dbGroup.setMajor(teacher.getMajor());
		dbGroup.setGroupNum(groupNum);
		dbGroup.setGroupName(groupName);
		dbGroup.setGroupSite(groupSite);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		dbGroup.setDbTime(sdf.parse(dbTime));
		dbGroup.setCreateTime(new Date());
		dbGroup.setGroupLeader(groupLeader);
		dbGroup.setCreateUser(teacher.getTeaName());	
		//如果小组id不为空 则update
		int i = 0;
		try {
			if(StringUtils.isNotBlank(id)){
				dbGroup.setId(id);
				i = majorLeaderFlowManageSer.updateDbGroup(dbGroup);
			}else{
				dbGroup.setId(UuidHelper.getRandomUUID());			
				i = majorLeaderFlowManageSer.addDbGroup(dbGroup);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0){
			model.addAttribute("errorMsg", "操作失败！");
			return jumpSetDbGroup(id, model, request, response);
		}
		
		return findDbGroup(model, request, response);
	}
	
	/**
	 * 删除答辩小组
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findDbGroup_deleteDbGroup.shtm")
	public String deleteDbGroup(String id, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(id)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		List<Teacher> teaList = majorLeaderFlowManageSer.findTeaByGroupId(id, major);
		List<String> teaIdList = new ArrayList<String>();
		for (int i = 0; i < teaList.size(); i++) {
			teaIdList.add(teaList.get(i).getUserId());
		}
		List<Student> stuList = majorLeaderFlowManageSer.findStuByDbGroup(id, major);
		List<String> stuIdList = new ArrayList<String>();
		for (int i = 0; i < stuList.size(); i++) {
			stuIdList.add(stuList.get(i).getUserId());
		}
		DbGroup dbGroup = majorLeaderFlowManageSer.findDbGroupInfoById(id, major);
		if(dbGroup==null){
			model.addAttribute("errorMsg", "该答辩小组已不存在！");
			return findDbGroup(model, request, response);
		}
		int i = 0;
		int j = 0;
		int k = 0;
		try {
			i = majorLeaderFlowManageSer.deleteDbGroup(id, major);
			if(teaIdList.size()!=0){
				j = majorLeaderFlowManageSer.setDbGroupForTea(teaIdList, "");				
			}
			if(stuIdList.size()!=0){
				k = majorLeaderFlowManageSer.setDbGroupForStu(stuIdList, "");				
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！删除失败！");
			return findDbGroup(model, request, response);
		}
		
		model.addAttribute("successMsg","删除成功！");
		return findDbGroup(model,  request, response);
	}
	
	/**
	 * 根据id查询答辩小组信息
	 * @param id
	 * @param moedel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findGroupInfoById.shtm")
	public String findGroupInfoById(String id, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(id)){
			model.addAttribute("errorMsg", "系统异常！");
			return "";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		if(StringUtils.isBlank(major)){
			model.addAttribute("errorMsg","请完善专业信息！");
			return "error/error.jsp";
		}
		DbGroup dbGroup = new DbGroup();
		try {
			dbGroup = majorLeaderFlowManageSer.findDbGroupInfoById(id, major);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("dbGroup", dbGroup);
		return "/Director/create_reply_group.jsp";
	}
	
	/**
	 * 跳转到给答辩小组安排教师的页面
	 * @param dbGroupId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findDbGroup_jumpSetDbGroupForTea.shtm")
	public String jumpSetDbGroupForTea(String dbGroupId, String flag, Model model, BasePage<Student> page,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")){
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String major = teacher.getMajor();
		if(StringUtils.isBlank(flag)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(dbGroupId)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		DbGroup dbGroup = new DbGroup();
		dbGroup = majorLeaderFlowManageSer.findDbGroupInfoById(dbGroupId, major);
		List<Teacher> nowTeacherList = new ArrayList<Teacher>();
		List<Teacher> allTeacherList = new ArrayList<Teacher>();
		Teacher leaderTea = new Teacher();
		Teacher secretaryTea = new Teacher();
		try {
			//组长
			if("groupLeader".equals(flag)){
				leaderTea = majorLeaderFlowManageSer.findTeaByUserId(dbGroup.getGroupLeader());
				allTeacherList = majorLeaderFlowManageSer.findSuperTeacher(major, null);
				model.addAttribute("leaderTea", leaderTea);
			}else if("memberTea".equals(flag)){//组员
				nowTeacherList = majorLeaderFlowManageSer.findGeneralTeacher(major, dbGroupId);
				allTeacherList = majorLeaderFlowManageSer.findGeneralTeacher(major, null);
				model.addAttribute("nowTeacherList", nowTeacherList);
			}else if("secretaryTea".equals(flag)){//秘书
				secretaryTea = majorLeaderFlowManageSer.findTeaByUserId(dbGroup.getGroupSecretary());
				allTeacherList = majorLeaderFlowManageSer.findTeaByGroupId(null, major);
				model.addAttribute("secretaryTea", secretaryTea);
			}else{
				model.addAttribute("errorMsg","系统异常！");
				return "error/error.jsp";
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("flag", flag);
		model.addAttribute("dbGroupId", dbGroupId);
		model.addAttribute("allTeacherList", allTeacherList);
		return "/Director/select_group_tea.jsp";
	}
	
	/**
	 * 给教师设置答辩小组
	 * @param dbGroupId
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findDbGroup_setTeaForDbGroup.shtm")
	public String setTeaForDbGroup(String dbGroupId, String[] teaIds,String[] removeTeaIds, String flag, String action, Model model, BasePage<Student> page,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String major = teacher.getMajor();
		if(teaIds==null&&removeTeaIds==null){
			model.addAttribute("errorMsg", "您未选择数据");
			return jumpSetDbGroupForTea(dbGroupId, flag, model, page, request, response);
		}
		if(StringUtils.isBlank(dbGroupId)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		DbGroup db = majorLeaderFlowManageSer.findDbGroupInfoById(dbGroupId, major);
		List<String> li = new ArrayList<String>();
		if("groupLeader".equals(flag)){
			li.add(db.getGroupLeader());			
		}
		if("secretaryTea".equals(flag)){
			li.add(db.getGroupSecretary());
		}
		if("memberTea".equals(flag)){
			li.add(db.getGroupMember());
		}
		List<String> list = null;
		List<String> removeList = null;
		if(teaIds!=null){
			list = Arrays.asList(teaIds);
		}
		if(removeTeaIds!=null){
			removeList = Arrays.asList(removeTeaIds);
		}
		
		int i = 0;
		DbGroup dbGroup = new DbGroup();
		try {
			if("groupLeader".equals(flag)){
				if("add".equals(action)){
					dbGroup.setId(dbGroupId);
					dbGroup.setGroupLeader(list.get(0));
					majorLeaderFlowManageSer.updateDbGroup(dbGroup);
					majorLeaderFlowManageSer.setDbGroupForTea(list, dbGroupId);
				}else if("remove".equals(action)){
					dbGroup.setId(dbGroupId);
					dbGroup.setGroupLeader("");
					majorLeaderFlowManageSer.updateDbGroup(dbGroup);
					majorLeaderFlowManageSer.setDbGroupForTea(removeList,"");
				}
			}else if("memberTea".equals(flag)){
				/*List<Teacher> teaList = new ArrayList<Teacher>();
				teaList = majorLeaderFlowManageSer.findTeaByUserIdList(list);
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < teaList.size(); j++) {
					sb.append(teaList.get(j).getTeaName()).append(";");
				}*/
				if("add".equals(action)){
					majorLeaderFlowManageSer.setDbGroupForTea(list, dbGroupId);
				}else if("remove".equals(action)){
					majorLeaderFlowManageSer.setDbGroupForTea(removeList, "");
				}
			}else if("secretaryTea".equals(flag)){
				if("add".equals(action)){
					dbGroup.setId(dbGroupId);
					dbGroup.setGroupSecretary(list.get(0));
					majorLeaderFlowManageSer.updateDbGroup(dbGroup);
					majorLeaderFlowManageSer.setDbGroupForTea(list, dbGroupId);
				}else if("remove".equals(action)){
					dbGroup.setId(dbGroupId);
					dbGroup.setGroupSecretary("");
					majorLeaderFlowManageSer.updateDbGroup(dbGroup);
					majorLeaderFlowManageSer.setDbGroupForTea(removeList,"");
				}
			}else{
				model.addAttribute("errorMsg", "系统异常！");
				return "error/error.jsp";
			}
		} catch (Exception e){
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		
		return jumpSetDbGroupForTea(dbGroupId, flag, model, page, request, response);
	}
	
	/**
	 * 跳转到给答辩小组安排学生的页面
	 * @param dbGroupId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findDbGroup_jumpSetDbGroupForStu.shtm")
	public String jumpSetDbGroupForStu(String dbGroupId, Model model, BasePage<Student> page,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		page.setPageUrl("/majorLeader/findDbGroup_jumpSetDbGroupForStu.shtm");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String major = teacher.getMajor();
		List<Student> groupList = new ArrayList<Student>();
		
		try {
			groupList = majorLeaderFlowManageSer.findStuByDbGroup(dbGroupId, major);
			page = majorLeaderFlowManageSer.findNotDbGroup(major, page);
		} catch (Exception e){
			e.printStackTrace();
		}
		model.addAttribute("dbGroupId", dbGroupId);
		model.addAttribute("groupList", groupList);
		model.addAttribute("page", page);
		return "/Director/select_group_stu.jsp";
	}
	
	/**
	 * 给学生设置答辩小组
	 * @param dbGroupId
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findDbGroup_setStuForDbGroup.shtm")
	public String setStuForDbGroup(String dbGroupId, String[] stuIds,String[] removeStuIds ,String flag, Model model, BasePage<Student> page,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(removeStuIds==null&&stuIds==null){
			model.addAttribute("errorMsg", "您未选择数据");
			return jumpSetDbGroupForStu(dbGroupId, model, page, request, response);
		}
		if(StringUtils.isBlank(dbGroupId)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		List<String> stuList =null;
		List<String> removeList = null;
		if(stuIds!=null){
			stuList = Arrays.asList(stuIds);
		}
		if(removeStuIds!=null){
			removeList = Arrays.asList(removeStuIds);
		}		
		int i = 0;
		try {
			if("remove".equals(flag)){
				i = majorLeaderFlowManageSer.setDbGroupForStu(removeList, "");				
			}else if("add".equals(flag)){
				i = majorLeaderFlowManageSer.setDbGroupForStu(stuList, dbGroupId);
			}else{
				model.addAttribute("errorMsg", "系统异常！");
				return "error/error.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		
		return jumpSetDbGroupForStu(dbGroupId, model, page, request, response);
	}
	
}
