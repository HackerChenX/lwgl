package com.hlzt.power.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hlzt.commons.helper.*;

import org.apache.commons.lang3.StringUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.PathVariable;

import com.hlzt.commons.model.BasePage;

import com.hlzt.power.dao.TaskBookDao;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.BackLog;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.FirstPaper;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.MidCheck;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.TeacherTitle;
import com.hlzt.power.model.TitleForm;
import com.hlzt.power.model.TitleNature;
import com.hlzt.power.model.User;

import com.hlzt.power.service.PublicSer;
import com.hlzt.power.service.StudentFlowManageSer;
import com.hlzt.power.service.UserSer;
import com.hlzt.power.service.ZdTeacherFlowManageSer;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * @ClassName: ZdTeacherFlowManageController
 * @Description:指导老师流程管理
 * @author gym
 * @date 2016-8-20 上午7:46:52
 */
@Controller
@RequestMapping("/guideTeacher")
public class ZdTeacherFlowManageController {
	@Resource
	private ZdTeacherFlowManageSer ZdTeacherFlowManageSer;
	@Resource
	private PublicSer publicSer;
	@Resource
	private UserSer userSer;
	@Resource
	private StudentFlowManageSer studentFlowManageSer;

	/**
	 * 指导老师已申报的课题
	 * @author gym
	 * @param teaId
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return String 
	 */
	@RequestMapping("/findTeaTitle.shtm")
	public String findTeaTitle(Model model, HttpServletRequest request,	HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")){
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		String teaId = teacher.getUserId();//从session中获取教师userId
		
		List<TeacherTitle> teacherTitles = ZdTeacherFlowManageSer.findTeaTitleByTeaId(teaId);//通过教师Id获取自已申报的课题
		
		if (teacherTitles != null) {
			for (int i = 0; i < teacherTitles.size(); i++) {
				if (teacherTitles.get(i).getLeaderStatus().equals("0")) {
					teacherTitles.get(i).setSumStatus("专业负责人审核中");
				} else if (teacherTitles.get(i).getLeaderStatus().equals("1")
						&& teacherTitles.get(i).getManagerStatus().equals("0")) {
					teacherTitles.get(i).setSumStatus("教学秘书审核中");
				} else if (teacherTitles.get(i).getLeaderStatus().equals("1")
						&& teacherTitles.get(i).getManagerStatus().equals("1")) {
					teacherTitles.get(i).setSumStatus("审核通过");
				} else {
					teacherTitles.get(i).setSumStatus("审核未通过");
				}
			}
		} // 将数据库中获取的leader，manager的0,1转换为表述文字，也可以在前台判断		
		model.addAttribute("teacherTitles", teacherTitles);
		//查询是否有关于课题申请更新的待办事项提示,如果有,删除此条提示
		List<BackLog> backLogs = publicSer.findBackLog(teaId,null,"teacher");
		if(!backLogs.isEmpty()){
			for(int i=0;i<backLogs.size();i++){
				BackLog backLog = backLogs.get(i);
				if(backLog.getBackLog().equals("reTeaApplyTitle")){
					int j = publicSer.removeBackLog(backLog.getId());
					if(j==0){
						model.addAttribute("errorMsg","系统错误");
						return "error/error.jsp";
					}
				}
			}
		}
		return "guideTeacher/declareProject.jsp";
	}

	/**
	 * 教师申报课题
	 * @author gym
	 * @return String 
	 */
	@RequestMapping("/findTeaTitle_applyProject.shtm")	
	public String applyProject(Model model) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		List<TitleNature> titleNatures = ZdTeacherFlowManageSer.selectNature();//题目性质
		
		List<TitleForm> titleForms = ZdTeacherFlowManageSer.selectForm();// 题目完成形式
		
		model.addAttribute("titleNatures", titleNatures);
		model.addAttribute("titleForms", titleForms);// 带有课题性质和完成形式的model
		
		return "guideTeacher/applyProject.jsp";//申请课题填写表格页面
	}

	/**
	 * 修改教师课题
	 * @author gym
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return String 
	 */
	@RequestMapping("/findTeaTitle_updataTeaTitle/{titleId}.shtm")
	public String updataTeaTitle(@PathVariable("titleId") String titleId,Model model, HttpServletRequest request,HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if (titleId == null) { 
			model.addAttribute("errorMsg", "操作错误！");
			return "error/error.jsp";
		}//关键字无参错误
		
		TeacherTitle teacherTitle = ZdTeacherFlowManageSer.findTeaTitleByTitleid(titleId);
		model.addAttribute("teacherTitle", teacherTitle);//通过课题Id查询该课题信息
		
		if (teacherTitle.getChoose().equals("1")) {
			model.addAttribute("errorMsg", "该课题已被学生选报，不能进行修改操作！！");
			return "error/error.jsp";
		} //判断课题是否已被学生选择
		
		List<TitleNature> titleNatures = ZdTeacherFlowManageSer.selectNature();// 获取题目性质
		
		List<TitleForm> titleForms = ZdTeacherFlowManageSer.selectForm();// 获取题目完成形式
		
		model.addAttribute("titleNatures", titleNatures);
		model.addAttribute("titleForms", titleForms);// 带有课题性质和完成形式的model
		
		
		return "guideTeacher/applyProject.jsp";
	}

	/**
	 * 教师申报课题,修改课题
	 * @param teaId
	 * @param titleName
	 * @param titleForm
	 * @param titleNature
	 * @param titleReason
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return String 
	 */
	@RequestMapping("/findTeaTitle_applyTeaTitle.shtm")
	public String applyTeaTitle(String titleId, String oldTitle, String teaId,
			String titleName, String titleForm, String titleNature,
			String titleReason, Model model, BasePage<TeacherTitle> page,
			HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Date nowDate = new Date();// 获取当前时间,create_time
		
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");// 获取当前教师
		
		TeacherTitle teacherTitle = new TeacherTitle();// 创建要插入的教师课题

		if (!oldTitle.equals(titleName)) {//当旧的题目和新的题目不同名时需要验证是否已存在同名课题，当旧的题目合新的题目同名时自己有权使用旧题目
			int i = ZdTeacherFlowManageSer.checkTeaTitle(titleName);// 返回数据库中该课题的条数,判断课题是否已有人选
			if (i != 0) {
				model.addAttribute("errorMsg", "课题名重复！");
				return "error/error.jsp";
			}
		}
		
		if (StringUtils.isNotBlank(titleId)) {// 课题Id有值，删除对应的TeacherTitle
			int j = ZdTeacherFlowManageSer.delTeaTitle(titleId);
			if (j == 0) {
				model.addAttribute("errorMsg", "操作错误!!");
				return "error/error.jsp";// 删除课题失败
			}
		}
		
		if (StringUtils.isBlank(titleName)) {// 课题不能为空
			model.addAttribute("errorMsg", "课题名不能为空！");
			return "error/error.jsp";
		}
		
		teacherTitle.setId(UuidHelper.getRandomUUID());// 课题ID
		teacherTitle.setTeacherId(teacher.getUserId());// 教师ID
		teacherTitle.setTitle(titleName);// 题目
		teacherTitle.setNature(titleNature);// 题目性质
		teacherTitle.setTitleForm(titleForm);// 完成形式
		teacherTitle.setTitleReason(titleReason);// 立题理由
		teacherTitle.setLeaderStatus("0");// 专业负责人状态审核中
		teacherTitle.setManagerStatus("0");// 教学秘书状态审核中
		teacherTitle.setChoose("0");// 0未选1已选
		teacherTitle.setCreateTime(nowDate);// 创建时间
		teacherTitle.setCreateUser(teacher.getTeaName());// 创建者
		
		int k=0;
		k = ZdTeacherFlowManageSer.addTeaTitle(teacherTitle);// 插入TeaTitle
		
		if (k == 0) {// 数据插入失败
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return "error/error.jsp";
		}
		//添加代办事项记录
		List<BackLog> backLogs = publicSer.findBackLog(null,teacher.getMajor(),"leader");
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
			backLog.setMajor(teacher.getMajor());
			backLog.setLeaderStatus("0");
			backLog.setCreateTime(nowDate);
			backLog.setCreateUser(teacher.getTeaName());
			int b = publicSer.insertBackLog(backLog);
			int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
		}
		return findTeaTitle(model, request, response);
	}

	/**
	 * 删除教师课题
	 * @author gym
	 * @param id
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return String 
	 */
	@RequestMapping("/findTeaTitle_deleteTeaTitle/{titleid}.shtm")
	public String deleteTeaTitle(@PathVariable("titleid") String titleid,
			Model model, BasePage<TeacherTitle> page,
			HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if (titleid == null) {// 无参错误
			model.addAttribute("errorMsg", "操作错误！");
			System.out.println("删除教师课题时，没有参数");
			return "error/error.jsp";
		}
		
		TeacherTitle teacherTitle = ZdTeacherFlowManageSer.findTeaTitleByTitleid(titleid);
		
		if (teacherTitle.getChoose().equals("1")) {
			model.addAttribute("errorMsg", "该课题已被学生选报，不能进行删除操作！！");
			return "error/error.jsp";
		} // 该题如果被学生选择，不能进行删除操作

		int i = 0;
		try {
			i = ZdTeacherFlowManageSer.delTeaTitle(titleid);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if (i == 0) {
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return "error/error.jsp";
		}
		//删除待办事项记录
		List<BackLog> backLogs = publicSer.findBackLog(null,teacherTitle.getTeacher().getMajor(),"leader");
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("teaApplyTitle")){
					if(backLogs.get(q).getNumbers()==1){
						int n = publicSer.removeBackLog(backLogs.get(q).getId());
					}else{
						int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
					}
				}
			}		
		}
		return findTeaTitle(model, request, response);
	}

	// ///////////////////////////////////////////////////////////////////////////
	/**
	 * 指导老师查看自己所带学生信息
	 * @author gym
	 * @param map
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return String 
	 */
	@RequestMapping("/findStudentInfo.shtm")
	public String findStudentInfo(Map<String, Object> map,
			BasePage<Student> page, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		map.put("teaId", teacher.getUserId());
		try {
			page = ZdTeacherFlowManageSer.findStuForTeacher(map, page);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("page", page);
		return "guideTeacher/look_stu_info.jsp";
	}

	/**
	 * 导出学生信息
	 * @author gym
	 * @return
	 */
	@RequestMapping("/exportStuInfo.shtm")
	public void exportStuInfo(Map<String, Object> map, BasePage<Student> page,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		map.put("teaId", teacher.getUserId());
		page.setPageSize(0);
		try {
			page = ZdTeacherFlowManageSer.findStuForTeacher(map, page);// 查询出学生信息
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		HSSFWorkbook wb = writeOrderInfoExcelOutputStreamForStuInfo(
				page.getResults(), response);
		String filename = SysConfig.getValue("term") + "学生信息" + ".xls";
		downloadExcel(wb, filename, request, response);
	}

	// 导出学生信息创建工作量excel文件
	@SuppressWarnings("deprecation")
	private HSSFWorkbook writeOrderInfoExcelOutputStreamForStuInfo(
			List<Student> list, HttpServletResponse response) {
		/**
		 * List<Map<String, Object>>
		 */
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle numStyle = wb.createCellStyle();
		numStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
		int size = list == null ? 0 : list.size();
		if (size > 0) {
			int sheetSize = 5000;
			int sheetCount = (int) Math.ceil(size / sheetSize + 1);

			for (int n = 0; n < sheetCount; n++) {
				HSSFSheet sheet = wb.createSheet("表" + (n + 1));
				sheet.setDefaultColumnWidth((short) 8);
				HSSFRow rowHead = sheet.createRow(0);

				rowHead.createCell((short) 0).setCellValue(
						new HSSFRichTextString("姓名"));
				rowHead.createCell((short) 1).setCellValue(
						new HSSFRichTextString("性别"));
				rowHead.createCell((short) 2).setCellValue(
						new HSSFRichTextString("学号"));
				rowHead.createCell((short) 3).setCellValue(
						new HSSFRichTextString("课题名"));
				rowHead.createCell((short) 4).setCellValue(
						new HSSFRichTextString("班级"));
				rowHead.createCell((short) 5).setCellValue(
						new HSSFRichTextString("电话"));
				rowHead.createCell((short) 6).setCellValue(
						new HSSFRichTextString("邮箱"));
				// 10001: 0-4999, 5000-9999, 10000-10001
				for (int i = n * sheetSize; i < list.size()
						&& i < (n + 1) * sheetSize; i++) {
					HSSFRow rowBody = sheet.createRow(i % sheetSize + 1);
					Student stu = list.get(i);
					rowBody.createCell((short) 0).setCellValue(
							new HSSFRichTextString(stu.getStuName())); // 学生姓名
					rowBody.createCell((short) 1).setCellValue(
							new HSSFRichTextString(stu.getSex())); // 性别
					rowBody.createCell((short) 2).setCellValue(
							new HSSFRichTextString(stu.getUserNum()));// 学号
					rowBody.createCell((short) 3).setCellValue(
							new HSSFRichTextString(
									getNullString(stu.getTitle())));// 课题
					rowBody.createCell((short) 4).setCellValue(
							new HSSFRichTextString(getNullString(stu
									.getStuClass())));// 班级
					rowBody.createCell((short) 5)
							.setCellValue(
									new HSSFRichTextString(getNullString(stu
											.getTel())));// 电话
					rowBody.createCell((short) 6)
							.setCellValue(
									new HSSFRichTextString(getNullString(stu
											.getMail())));// 邮箱
				}
			}
		}
		return wb;
	}

	/**
	 * @param wb
	 * @param filename
	 * @param response
	 */
	private void downloadExcel(HSSFWorkbook wb, String filename,HttpServletRequest request,
			HttpServletResponse response) {

		ServletOutputStream out = null;
		String agent = request.getHeader("user-agent");
		try {
			out = response.getOutputStream();
			response.setHeader("Content-Type", "application/force-download");
			if(agent.contains("Firefox"))
				response.addHeader("Content-Disposition","attachment;filename="+ new String(filename.getBytes("GB2312"),"ISO-8859-1"));
			else
			response.setHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(filename, "UTF-8"));
			wb.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getNullString(Object data) {
		if (data == null) {
			return "";
		}
		String dataStr = String.valueOf(data);
		if ("null".equalsIgnoreCase(dataStr)) {
			return "";
		}
		return dataStr;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 指导老师审核课题申报
	 * @param zdTeacher
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findStuTitle.shtm")
	public String findStuTitle(String stuNum, String stuName,
			Map<String, Object> map, String status, Model model,BasePage<Student> page,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		if (status == null)
			status = "0";// 默认显示未审核记录

		String teaId = teacher.getUserId();
		map.put("teaId", teaId);
		if (StringUtils.isNotBlank(stuNum))
			map.put("stuNum", StringHelper.formatLike(stuNum));
		if (StringUtils.isNotBlank(stuName))
			map.put("stuName", StringHelper.formatLike(stuName));
		if (StringUtils.isNotBlank(status)) {
			if (status.equals("3"))
				status = null;
			map.put("teaStatus", status);
		} // 查询条件
		try{
			page = ZdTeacherFlowManageSer.findStuTitle(map,page);
		}catch(Exception e){
			e.printStackTrace();
		}
		//获取当前教师状态（session更新不及时）
		Teacher tea = userSer.finTeacherByUserNum(teacher.getTeaNum());
		model.addAttribute("page", page);// 查询结果带回
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		model.addAttribute("status", status);// 查询关键词带回前台
		model.addAttribute("tea",tea);
		return "guideTeacher/check_stu_req.jsp";
	}

	/**
	 * 审核学生申报
	 * @author gym
	 * @param applyTitleId
	 * @param model
	 * @param request
	 * @param response
	 * @return String 
	 */
	@RequestMapping("/findStuTitle_shStuTitle/{applyTitleId}.shtm")
	public String shStuTitle(@PathVariable("applyTitleId") String applyTitleId,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		ApplyTitle applyTitle = ZdTeacherFlowManageSer
				.findApplyTitleById(applyTitleId);
		model.addAttribute("applyTitle", applyTitle);

		return "guideTeacher/stuProjectInfo.jsp";
	}

	/**
	 * 对课题进行批准驳回操作
	 * @author gym
	 * @param ids
	 * @param status
	 * @param teaIdea
	 * @param stuName
	 * @param major
	 * @param teaName
	 * @param map
	 * @param findStatus
	 * @param model
	 * @param request
	 * @param response
	 * @return String 
	 */
	@RequestMapping("/findStuTitle_checkStuTitile.shtm")
	public String checkStuTitile(String[] ids, String status,String applyTitleId, String teaIdea,String titleSourse,
			String stuName, String major, String teaName,BasePage<Student> page,
			Map<String, Object> map, String findStatus, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if (StringUtils.isBlank(status)) {
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		} // status无参错误
		if (ids == null) {
			model.addAttribute("errorMsg", "您未选择数据！");
			return findStuTitle(null, null, map, findStatus, model,page,request,
					response);
		}
		if(teaIdea==null){
			teaIdea="";
		}
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = ZdTeacherFlowManageSer.checkApplyTitle(list, status, teaIdea);//设置教师status			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if (i == 0) {
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}		
		if(status.equals("1")||status.equals("2")){
			//删除待办事项记录
			List<BackLog> backLogs = publicSer.findBackLog(teacher.getUserId(),null,"teacher");
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
		}
		return findStuTitle(null, null, map, findStatus, model, page,request,response);
	}

	// ////////////////////////////////////////////////////////////////////

	/**
	 * 指导老师查询自己的学生任务书
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	@RequestMapping("/taskbook.shtm")
	public String taskbook(String stuNum,String stuName, String status, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");	
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		String teaId= teacher.getUserId();
		
		if(status == null)
			status="0";//默认为未审核
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("zdTeacherId", teaId);
		if (StringUtils.isNotBlank(stuNum))
			map.put("stuNum", StringHelper.formatLike(stuNum));
		if (StringUtils.isNotBlank(stuName))
			map.put("stuName", StringHelper.formatLike(stuName));
		model.addAttribute("status", status);//查询条件回显
		if (StringUtils.isNotBlank(status)){
			if (status.equals("3"))
				status = null;
			map.put("status", status);// 查询条件
		}
		
		//根据教师userId和status查询任务书
		List<TaskBook> taskBooks=ZdTeacherFlowManageSer.findTaskbookByKey(teaId,status);//map.teaId,map.status
		List<TaskBook> reList=new ArrayList<TaskBook>();//创建用于存放符合条件的结果
		for(int i=0;i<taskBooks.size();i++){
			//任务书中获取学生Id，查询学生
			String stuId=taskBooks.get(i).getStuId();//获取任务书中的stuId
			map.put("stuId", stuId);
			Student student=ZdTeacherFlowManageSer.findStuForTeacher(map);
			if(StringUtils.isNotBlank(taskBooks.get(i).getTaskBookPath()))
			{
				//获取项目网络地址
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":" + request.getServerPort()
						+ path ;
				//截取文件地址，拼接文件的网络地址
				String[] filePaths = taskBooks.get(i).getTaskBookPath().split("lwgl");
				taskBooks.get(i).setTaskBookSrc(basePath+filePaths[1]);
			}
			if(student!=null){				
				taskBooks.get(i).setStudent(student);//把学生放入
				reList.add(taskBooks.get(i));
			}	
			
		}
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		
		
		model.addAttribute("result", reList);
		return "guideTeacher/check_stu_task.jsp";		
	}
	
	/**
	 * 审核任务书
	 * @param ids
	 * @param status
	 * @param model
	 * @param stuNum
	 * @param stuName
	 * @param findStatus
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("taskbook_checkTaskBook.shtm")
	public String checkTaskBook(String[] ids, String status, Model model,
			String stuNum, String stuName, String findStatus,
			HttpServletRequest request, HttpServletResponse response){
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		int i = 0;
		if(ids==null){
			model.addAttribute("errorMsg", "您未选择数据！");
			return taskbook(stuNum, stuName, findStatus, model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		try {
			i = ZdTeacherFlowManageSer.updateTaskBookZdStatus(list, status);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(i==0){
			model.addAttribute("errorMsg", "审核失败！");
			return taskbook(stuNum, stuName, findStatus, model, request, response);
		}
		if(status.equals("1")){
			for(int k=0;k<list.size();k++){
			List<BackLog> backLogs = publicSer.findBackLog(null,teacher.getMajor(),"leader");
			Boolean bool = false;
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("taskBook")){
						int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
						bool=true;						
						break;
					}
				}		
			}
			if(!bool){		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setBackLog("taskBook");
				backLog.setMajor(teacher.getMajor());
				backLog.setTeaStatus("1");
				backLog.setLeaderStatus("0");
				backLog.setCreateTime(new Date());
				backLog.setCreateUser(teacher.getTeaName());
				int b = publicSer.insertBackLog(backLog);
				int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
			}
			}
		}
		if(status.equals("1")||status.equals("2")){
			//删除待办事项记录
			List<BackLog> backLogs = publicSer.findBackLog(teacher.getUserId(),null,"teacher");
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
				TaskBook taskBook = ZdTeacherFlowManageSer.findTaskBookById(list.get(k));
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
		return taskbook(stuNum, stuName, findStatus, model, request, response);
	}
	
	/**
	 * 指导老师查询自己的学生开题报告
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	@RequestMapping("/openingReport.shtm")
	public String openingReport(Map<String, Object> map, String stuNum,
			String stuName, String status, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");	
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		String teaId= teacher.getUserId();
		
		if(status == null)
			status="0";//默认为未审核
		
		map.put("zdTeacherId", teaId);
		if (StringUtils.isNotBlank(stuNum))
			map.put("stuNum", StringHelper.formatLike(stuNum));
		if (StringUtils.isNotBlank(stuName))
			map.put("stuName", StringHelper.formatLike(stuName));
		if (StringUtils.isNotBlank(status)){
			if (status.equals("3"))
				status = null;
			map.put("status", status);// 查询条件
		}
		//根据教师userId和status查询开题报告
		List<OpeningReport> openingReports=ZdTeacherFlowManageSer.findOpeningReportByKey(teaId,status);//map.teaId,map.status
		List<OpeningReport> reList=new ArrayList<OpeningReport>();//创建用于存放符合条件的结果
		for(int i=0;i<openingReports.size();i++){
			//开题报告中获取学生Id，查询学生
			String stuId=openingReports.get(i).getStuId();//获取开题报告中的stuId
			map.put("stuId", stuId);
			Student student=ZdTeacherFlowManageSer.findStuForTeacher(map);
			if(StringUtils.isNotBlank(openingReports.get(i).getOpeningReportPath()))
			{
				//获取项目网络地址
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":" + request.getServerPort()
						+ path ;
				//截取文件地址，拼接文件的网络地址
				String[] filePaths = openingReports.get(i).getOpeningReportPath().split("lwgl");
				openingReports.get(i).setOpeningReportSrc(basePath+filePaths[1]);
			}
			if(student!=null){					
				openingReports.get(i).setStudent(student);//把学生放入
				reList.add(openingReports.get(i));
			}	
			
			
		}
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		//model.addAttribute("status", status);//查询条件回显
		
		model.addAttribute("result", reList);
		return "guideTeacher/check_opening_report.jsp";
	}
	
	/**
	 * 审核开题报告
	 * @param ids
	 * @param status
	 * @param model
	 * @param stuNum
	 * @param stuName
	 * @param findStatus
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/openingReport_checkOpeningReport.shtm")
	public String checkOpeningReport(String[] ids, String status, Model model,
			String stuNum, String stuName, String findStatus,
			HttpServletRequest request, HttpServletResponse response){
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		int i = 0;
		if(ids==null){
			model.addAttribute("errorMsg", "您未选择数据！");
			return openingReport(map, stuNum, stuName, findStatus, model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		try {
			i = ZdTeacherFlowManageSer.updateOpeningReportZdStatus(list, status);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(i==0){
			model.addAttribute("errorMsg", "审核失败！");
			return openingReport(map, stuNum, stuName, findStatus, model, request, response);
		}
		if(status.equals("1")){
			for(int k=0;k<list.size();k++){
			List<BackLog> backLogs = publicSer.findBackLog(null,teacher.getMajor(),"leader");
			Boolean bool = false;
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("openingReport")){
						int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
						bool=true;						
						break;
					}
				}		
			}
			if(!bool){ 		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setBackLog("openingReport");
				backLog.setMajor(teacher.getMajor());
				backLog.setTeaStatus("1");
				backLog.setLeaderStatus("0");
				backLog.setCreateTime(new Date());
				backLog.setCreateUser(teacher.getTeaName());
				int b = publicSer.insertBackLog(backLog);
				int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
			}
		  }
		}
		if(status.equals("1")||status.equals("2")){
			//删除待办事项记录
			List<BackLog> backLogs = publicSer.findBackLog(teacher.getUserId(),null,"teacher");
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
				OpeningReport openingReport = ZdTeacherFlowManageSer.findOpeningReportById(list.get(k));
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
		return openingReport(map, stuNum, stuName, findStatus, model, request, response);
	}
	
	/**
	 * 指导老师查询自己的学生中期检查表
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	@RequestMapping("/midCheck.shtm")
	public String midCheck(Map<String, Object> map, String stuNum,
			String stuName, String status,  Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");		
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		String teaId= teacher.getUserId();
		
		if(status == null)
			status="0";//默认为未审核
		
		map.put("zdTeacherId", teaId);
		if (StringUtils.isNotBlank(stuNum))
			map.put("stuNum", StringHelper.formatLike(stuNum));
		if (StringUtils.isNotBlank(stuName))
			map.put("stuName", StringHelper.formatLike(stuName));
		if (StringUtils.isNotBlank(status)){
			if (status.equals("3"))
				status = null;
			map.put("status", status);// 查询条件
		}
		//根据教师userId和status查询中期检查
		List<MidCheck> midChecks=ZdTeacherFlowManageSer.findmidCheckByKey(teaId,status);//map.teaId,map.status
		List<MidCheck> reList=new ArrayList<MidCheck>();//创建用于存放符合条件的结果
		for(int i=0;i<midChecks.size();i++){
			//任务书中获取学生Id，查询学生
			String stuId=midChecks.get(i).getStuId();//获取任务书中的stuId
			map.put("stuId", stuId);
			Student student=ZdTeacherFlowManageSer.findStuForTeacher(map);
			if(StringUtils.isNotBlank(midChecks.get(i).getMidCheckPath()))
			{
				//获取项目网络地址
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":" + request.getServerPort()
						+ path ;
				//截取文件地址，拼接文件的网络地址
				String[] filePaths = midChecks.get(i).getMidCheckPath().split("lwgl");
				midChecks.get(i).setMidCheckSrc(basePath+filePaths[1]);
			}
			if(student!=null){					
				midChecks.get(i).setStudent(student);//把学生放入
				reList.add(midChecks.get(i));
			}	
			
		}
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		model.addAttribute("status", status);//查询条件回显
		
		model.addAttribute("result", reList);
		return "guideTeacher/check_mid_paper.jsp";		
	}
	
	/**
	 * 审核中期检查表
	 * @param ids
	 * @param status
	 * @param model
	 * @param stuNum
	 * @param stuName
	 * @param findStatus
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/midCheck_checkMidCheck.shtm")
	public String checkMidCheck(String[] ids, String status, Model model,
			String stuNum, String stuName, String findStatus,
			HttpServletRequest request, HttpServletResponse response){
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		int i = 0;
		if(ids==null){
			model.addAttribute("errorMsg", "您未选择数据！");
			return midCheck(map, stuNum, stuName, findStatus, model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		try {
			i = ZdTeacherFlowManageSer.updateMidCheckZdStatus(list, status);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(i==0){
			model.addAttribute("errorMsg", "审核失败！");
			return midCheck(map, stuNum, stuName, findStatus, model, request, response);
		}
		//删除待办事项记录
		List<BackLog> backLogs = publicSer.findBackLog(teacher.getUserId(),null,"teacher");
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("midCheck")){
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
			MidCheck midCheck = ZdTeacherFlowManageSer.findMidCheckById(list.get(k));
			List<BackLog> backLogList = publicSer.findBackLog(midCheck.getStuId(),null,"student");
			Boolean bool = false;
			if(!backLogList.isEmpty()){
				for(int q=0;q<backLogList.size();q++){
					if(backLogList.get(q).getBackLog().equals("reMidCheck")){
						int m = publicSer.updateBackLogNumById(backLogList.get(q).getId(),"add");
						bool=true;						
						break;
					}
				}		
			}
			if(!bool){		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setStuId(midCheck.getStuId());
				backLog.setBackLog("reMidCheck");
				backLog.setStuStatus("0");
				backLog.setCreateTime(new Date());
				backLog.setCreateUser(teacher.getTeaName());
				int b = publicSer.insertBackLog(backLog);
				int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
			}
		}
		return midCheck(map, stuNum, stuName, findStatus, model, request, response);
	}
	
	/**
	 * 指导老师查询自己的学生初稿
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	@RequestMapping("/paperDraft.shtm")
	public String paperDraft(Map<String, Object> map, String stuNum,
			String stuName, String status, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		String teaId= teacher.getUserId();
		
		if(status == null)
			status="0";//默认为未审核
		
		map.put("zdTeacherId", teaId);
		if (StringUtils.isNotBlank(stuNum))
			map.put("stuNum", StringHelper.formatLike(stuNum));
		if (StringUtils.isNotBlank(stuName))
			map.put("stuName", StringHelper.formatLike(stuName));
		if (StringUtils.isNotBlank(status)){
			if (status.equals("3"))
				status = null;
			map.put("status", status);// 查询条件
		}
		//根据教师userId和status查询中期检查
		List<FirstPaper> firstPapers=ZdTeacherFlowManageSer.findFirstPaperByKey(teaId,status);//map.teaId,map.status
		List<FirstPaper> reList=new ArrayList<FirstPaper>();//创建用于存放符合条件的结果
		for(int i=0;i<firstPapers.size();i++){
			//任务书中获取学生Id，查询学生
			String stuId=firstPapers.get(i).getStuId();//获取任务书中的stuId
			map.put("stuId", stuId);
			Student student=ZdTeacherFlowManageSer.findStuForTeacher(map);
			if(StringUtils.isNotBlank(firstPapers.get(i).getFirstPaperPath()))
			{
				//获取项目网络地址
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":" + request.getServerPort()
						+ path ;
				//截取文件地址，拼接文件的网络地址
				String[] filePaths = firstPapers.get(i).getFirstPaperPath().split("lwgl");
				firstPapers.get(i).setFirstPaperSrc(basePath+filePaths[1]);
			}
			if(student!=null){				
				firstPapers.get(i).setStudent(student);//把学生放入
				reList.add(firstPapers.get(i));
			}	
			
		}
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		model.addAttribute("status", status);//查询条件回显
		
		model.addAttribute("result", reList);
		return "guideTeacher/check_paper_draft.jsp";		
	}
	
	/**
	 * 审核论文初稿
	 * @param ids
	 * @param status
	 * @param model
	 * @param stuNum
	 * @param stuName
	 * @param findStatus
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/paperDraft_checkPaperDraft.shtm")
	public String checkPaperDraft(String[] ids, String status, Model model,
			String stuNum, String stuName, String findStatus,
			HttpServletRequest request, HttpServletResponse response){
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		int i = 0;
		if(ids==null){
			model.addAttribute("errorMsg", "您未选择数据！");
			return paperDraft(map, stuNum, stuName, findStatus, model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		try {
			i = ZdTeacherFlowManageSer.updateFirstPaperZdStatus(list, status);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(i==0){
			model.addAttribute("errorMsg", "审核失败！");
			return paperDraft(map, stuNum, stuName, findStatus, model, request, response);
		}
		//删除待办事项记录
		List<BackLog> backLogs = publicSer.findBackLog(teacher.getUserId(),null,"teacher");
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("firstPaper")){
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
			FirstPaper firstPaper = ZdTeacherFlowManageSer.findFirstPaperById(list.get(k));
			List<BackLog> backLogList = publicSer.findBackLog(firstPaper.getStuId(),null,"student");
			Boolean bool = false;
			if(!backLogList.isEmpty()){
				for(int q=0;q<backLogList.size();q++){
					if(backLogList.get(q).getBackLog().equals("reFirstPaper")){
						int m = publicSer.updateBackLogNumById(backLogList.get(q).getId(),"add");
						bool=true;						
						break;
					}
				}		
			}
			if(!bool){		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setStuId(firstPaper.getStuId());
				backLog.setBackLog("reFirstPaper");
				backLog.setStuStatus("0");
				backLog.setCreateTime(new Date());
				backLog.setCreateUser(teacher.getTeaName());
				int b = publicSer.insertBackLog(backLog);
				int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
			}
		}
		return paperDraft(map, stuNum, stuName, findStatus, model, request, response);
	}
	
	/**
	 * 指导老师查询自己的学生定稿
	 * @param stuNum
	 * @param stuName
	 * @param status
	 * @return
	 */
	@RequestMapping("/paperFinal.shtm")
	public String paperFinal(Map<String, Object> map, String stuNum,
			String stuName, String status, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		String teaId= teacher.getUserId();
		
		if(status == null)
			status="0";//默认为未审核
		
		map.put("zdTeacherId", teaId);
		if (StringUtils.isNotBlank(stuNum))
			map.put("stuNum", StringHelper.formatLike(stuNum));
		if (StringUtils.isNotBlank(stuName))
			map.put("stuName", StringHelper.formatLike(stuName));
		if (StringUtils.isNotBlank(status)){
			if (status.equals("3"))
				status = null;
			map.put("status", status);// 查询条件
		}
		List<FinalPaper> finalPapers=ZdTeacherFlowManageSer.findFinalPapersByKey(teaId,status);//map.teaId,map.status
		List<FinalPaper> reList=new ArrayList<FinalPaper>();//创建用于存放符合条件的结果
		for(int i=0;i<finalPapers.size();i++){
			//任务书中获取学生Id，查询学生
			String stuId=finalPapers.get(i).getStuId();//获取任务书中的stuId
			map.put("stuId", stuId);
			Student student=ZdTeacherFlowManageSer.findStuForTeacher(map);
			if(StringUtils.isNotBlank(finalPapers.get(i).getFinalPaperPath()))
			{
				//获取项目网络地址
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":" + request.getServerPort()
						+ path ;
				//截取文件地址，拼接文件的网络地址
				String[] filePaths = finalPapers.get(i).getFinalPaperPath().split("lwgl");
				finalPapers.get(i).setFinalPaperSrc(basePath+filePaths[1]);
			}
			if(student!=null){				
				finalPapers.get(i).setStudent(student);//把学生放入
				reList.add(finalPapers.get(i));
			}	
			
		}
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		model.addAttribute("status", status);//查询条件回显
		
		model.addAttribute("result", reList);
		return "guideTeacher/check_paper_final.jsp";		
	}

	/**
	 * 审核论文定稿
	 * @param ids
	 * @param status
	 * @param model
	 * @param stuNum
	 * @param stuName
	 * @param findStatus
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/paperFinal_checkPaperFinal.shtm")
	public String checkPaperFinal(String[] ids, String status, Model model,
			String stuNum, String stuName, String findStatus,
			HttpServletRequest request, HttpServletResponse response){
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		int i = 0;
		if(ids==null){
			model.addAttribute("errorMsg", "您未选择数据！");
			return paperFinal(map, stuNum, stuName, findStatus, model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		try {
			i = ZdTeacherFlowManageSer.updateFinalPaperZdStatus(list, status);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(i==0){
			model.addAttribute("errorMsg", "审核失败！");
			return paperFinal(map, stuNum, stuName, findStatus, model, request, response);
		}
		//删除待办事项记录
		List<BackLog> backLogs = publicSer.findBackLog(teacher.getUserId(),null,"teacher");
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("finalPaper")){
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
			FinalPaper finalPaper = ZdTeacherFlowManageSer.findFinalPaperById(list.get(k));
			List<BackLog> backLogList = publicSer.findBackLog(finalPaper.getStuId(),null,"student");
			Boolean bool = false;
			if(!backLogList.isEmpty()){
				for(int q=0;q<backLogList.size();q++){
					if(backLogList.get(q).getBackLog().equals("reFinalPaper")){
						int m = publicSer.updateBackLogNumById(backLogList.get(q).getId(),"add");
						bool=true;						
						break;
					}
				}		
			}
			if(!bool){		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setStuId(finalPaper.getStuId());
				backLog.setBackLog("reFinalPaper");
				backLog.setStuStatus("0");
				backLog.setCreateTime(new Date());
				backLog.setCreateUser(teacher.getTeaName());
				int b = publicSer.insertBackLog(backLog);
				int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
			}
		}
		return paperFinal(map, stuNum, stuName, findStatus, model, request, response);
	}
	
	
	
////////////////////////////////////////////////
	/**
	 * 答辩小组安排
	 * @author gym
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 * String 
	 */
	@RequestMapping("/dbGroupInfo.shtm")
	public String dbGroupInfo(Model model,Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	 
		List<DbGroup> dbGroups = ZdTeacherFlowManageSer.findDbGroupByTeaId(teacher.getUserId());
		 

		if(dbGroups!=null){
			for(int i=0;i<dbGroups.size();i++){//其实教师只属于一个答辩小组，				
			Date date = dbGroups.get(i).getDbTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String dbTime = formatter.format(date);
			dbGroups.get(i).setDateTimeStr(dbTime);//时间格式转换	
			
			List<Student> students=ZdTeacherFlowManageSer.findStuByDbId(dbGroups.get(i).getId());//通过答辩小组Id查询学生
			model.addAttribute("students", students);//该答辩小组的学生
			
			List<Teacher> teachers=ZdTeacherFlowManageSer.findTeaByDbId(dbGroups.get(i).getId());
			model.addAttribute("teachers", teachers);//该答辩小组的教师
			}
		}				
		model.addAttribute("dbGroups", dbGroups);
		return "guideTeacher/dbGroupInfo.jsp";
	}
	
	/**
	 * 指导老师查询评阅学生
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findPyStu.shtm")
	public String findPyStu(Model model, BasePage<Student> page,
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
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		try {
			page = ZdTeacherFlowManageSer.zdTeaFindPyStu(teacher.getUserId(), page);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		//获取项目网络地址
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path ;
		for(int i = 0;i<page.getResults().size();i++)
		{
			FinalPaper finalPaper = studentFlowManageSer.findFinalPaperByStuId(page.getResults().get(i).getUserId());		
			//截取文件地址，拼接文件的网络地址
			String[] filePaths = finalPaper.getFinalPaperPath().split("lwgl");
			finalPaper.setFinalPaperSrc(basePath+filePaths[1]);
			page.getResults().get(i).setFinalPaper(finalPaper);
		}
			
		model.addAttribute("page", page);
		return "guideTeacher/look_py_stu.jsp";
	}
	
	
	/**
	 * 按条件查询学生成绩
	 * @param major
	 * @param className
	 * @param endNum
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findStuPaper.shtm")
	public String findStuPaper(String stuName, String stuNum, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String pageUrl = "/secretary/findStuPaperInfo.shtm";
		User user = (User) request.getSession().getAttribute("user");
		if(StringUtils.isBlank(SysConfig.getValue("term"))){
			model.addAttribute("error", "教学秘书暂未设置当前届！");
			return "Secretary/spotChecks.jsp";
		}
		try {
			page = ZdTeacherFlowManageSer.findStuPaperInfo(stuNum, stuName, user.getUserNum(), page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		page.setPageUrl(pageUrl);
		
		model.addAttribute("page", page);
		model.addAttribute("stuName", stuName);
		model.addAttribute("stuNum", stuNum);
		return "guideTeacher/stu_grade.jsp";
	}
	
	/**
	 * @Title: paperInfo
	 * @Description: 查询学生详细信息
	 * @param id
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/findStuPaperInfo.shtm")
	public String findStuPaperInfo(String id,Model model,Map<String, Object> map,HttpServletRequest request, HttpServletResponse response)
	{	
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(id)){
			model.addAttribute("error", "系统异常！");
			return "error/error.jsp";
		}
		Paper paper = new Paper();
		try {
			paper = publicSer.findFormTermById(id);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("paper", paper);
		return "Public_Page/formerTermInfo.jsp";
	}
	
	/**
	 * 导出学生论文
	 * @param major
	 * @param className
	 * @param endNum
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping("/findStuPaper_exportStuPaper.shtm")
	public void exportStuPaper(String stuName, String stuNum, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response){
		page.setPageSize(0);
		String pageUrl = "/secretary/findStuPaperInfo.shtm";
		User user = (User) request.getSession().getAttribute("user");
		try {
			page = ZdTeacherFlowManageSer.findStuPaperInfo(stuNum, stuName, user.getUserNum(), page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Paper> list = new ArrayList<Paper>();
		list = page.getResults();
		HSSFWorkbook wb = writeOrderInfo2ExcelOutputStreamForStu(list, response);
			String filename = SysConfig.getValue("term")+"学生成绩"+".xls";
			downloadExcel(wb, filename, request, response);
	}
	
	// 导出抽检学生信息 创建工作量excel文件
  	private HSSFWorkbook writeOrderInfo2ExcelOutputStreamForStu(
  			List<Paper> list, HttpServletResponse response) {
  		/**
  		 * List<Map<String, Object>>
  		 */
  		HSSFWorkbook wb = new HSSFWorkbook();
  		HSSFCellStyle numStyle = wb.createCellStyle();
  		numStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
  		int size = list == null ? 0 : list.size();
  		if (size > 0) {
  			int sheetSize = 5000;
  			int sheetCount = (int) Math.ceil(size / sheetSize + 1);
  			
  			for (int n = 0; n < sheetCount; n++) {
  				HSSFSheet sheet = wb.createSheet("表" + (n + 1));
  				sheet.setDefaultColumnWidth((short) 7);
  				HSSFRow rowHead = sheet.createRow(0);

  				rowHead.createCell((short) 0).setCellValue(
  						new HSSFRichTextString("学号"));
  				rowHead.createCell((short) 1).setCellValue(
  						new HSSFRichTextString("姓名"));
  				rowHead.createCell((short) 2).setCellValue(
  						new HSSFRichTextString("班级"));
  				rowHead.createCell((short) 3).setCellValue(
  						new HSSFRichTextString("课题名"));
  				rowHead.createCell((short) 4).setCellValue(
  						new HSSFRichTextString("审阅成绩"));
  				rowHead.createCell((short) 5).setCellValue(
  						new HSSFRichTextString("评阅成绩"));
  				rowHead.createCell((short) 6).setCellValue(
  						new HSSFRichTextString("答辩成绩"));
  				rowHead.createCell((short) 7).setCellValue(
  						new HSSFRichTextString("最终成绩"));
  				// 10001: 0-4999, 5000-9999, 10000-10001
  				for (int i = n * sheetSize; i < list.size()
  						&& i < (n + 1) * sheetSize; i++) {
  					HSSFRow rowBody = sheet.createRow(i % sheetSize + 1);
  					Paper paper = list.get(i);
  					rowBody.createCell((short) 0).setCellValue(
  							new HSSFRichTextString(paper.getStuNum()));
  					rowBody.createCell((short) 1).setCellValue(
  							new HSSFRichTextString(paper.getStuName()));				
  					rowBody.createCell((short) 2).setCellValue(
  							new HSSFRichTextString(paper.getStuClass()));
  					rowBody.createCell((short) 3).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getTitle())));
  					rowBody.createCell((short) 4).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getSyGrade())));
  					rowBody.createCell((short) 5).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getPyGrade())));
  					rowBody.createCell((short) 6).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getDbGrade())));
  					rowBody.createCell((short) 7).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getFinalGrade())));
  				}
  			}
  		}
  		return wb;
  	}

	/**
	 * @Title: formerTerm
	 * @Description: 往届信息查询页面
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/formerTerm.shtm")
	public String formerTerm(String formerTerm,String teacherName,String title,String studentName,
			BasePage page, Model model,Map<String, Object> map,HttpServletRequest request, HttpServletResponse response)
	{	
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String url = "guideTeacher";
		model.addAttribute("url", url);
		if(StringUtils.isNotBlank(formerTerm)){
			map.put("term", formerTerm);
			model.addAttribute("formerTerm", formerTerm);
		}if(StringUtils.isNotBlank(teacherName)){
			map.put("teaName", teacherName);
			model.addAttribute("teacherName", teacherName);
		}if(StringUtils.isNotBlank(title)){
			map.put("title", title);
			model.addAttribute("title", title);
		}if(StringUtils.isNotBlank(studentName)){
			map.put("stuName", studentName);
			model.addAttribute("studentName", studentName);
		}
		page=publicSer.findFormerTerm(map,page);
		model.addAttribute("page",page);
		return "guideTeacher/formerTerm.jsp";
	}

	/**
	 * @Title: stuAllFile
	 * @Description: 学生课题文件页面
	 * @param stuNum
	 * @param stuName
	 * @param map
	 * @param model
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
  	@RequestMapping("/stuAllFile.shtm")
  	public String stuAllFile(String stuNum,String stuName,Map<String, Object> map, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(StringUtils.isBlank(teacher.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		map.put("teaNum", teacher.getTeaNum());
		if (StringUtils.isNotBlank(stuNum))
			map.put("stuNum", StringHelper.formatLike(stuNum));
		if (StringUtils.isNotBlank(stuName))
			map.put("stuName", StringHelper.formatLike(stuName));
		String rootFile = SysConfig.getValue("term");
		map.put("term", rootFile);//查询条件
		
		List<Paper> papers=new ArrayList<Paper>();		
		try {
		papers = ZdTeacherFlowManageSer.findStuAllFile(map);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("papers",papers);
		//删除待办事项记录
		List<BackLog> backLogs = publicSer.findBackLog(teacher.getUserId(),null,"teacher");
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("allFile")){					
					int n = publicSer.removeBackLog(backLogs.get(q).getId());								
				}
			}		
		}
		return "guideTeacher/stu_project_file.jsp";
	}
 

	/**
	 * @Title: formTermInfo
	 * @Description: 往届学生详细信息
	 * @param id
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/formerTermInfo.shtm")
	public String formerTermInfo(String id,Model model,Map<String, Object> map,HttpServletRequest request, HttpServletResponse response)
	{	
		Paper paper = publicSer.findFormTermById(id);
		model.addAttribute("paper", paper);
		return "Public_Page/formerTermInfo.jsp";
	}
	
	/**
	 * 导出往届学生信息
	 * @param major
	 * @param className
	 * @param endNum
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportFormerTermInfo.shtm")
	public void exportFormerTermInfo(String formerTerm, String studentNum, String teacherName,String studentName, BasePage page,
			Model model,Map<String, Object> map, HttpServletRequest request, HttpServletResponse response){
		page.setPageSize(0);
		if(StringUtils.isNotBlank(formerTerm)){
			map.put("term", formerTerm);
			model.addAttribute("formerTerm", formerTerm);
		}if(StringUtils.isNotBlank(teacherName)){
			map.put("teaName", teacherName);
			model.addAttribute("teacherName", teacherName);
		}if(StringUtils.isNotBlank(studentNum)){
			map.put("stuNum", studentNum);
			model.addAttribute("studentNum", studentNum);
		}if(StringUtils.isNotBlank(studentName)){
			map.put("stuName", studentName);
			model.addAttribute("studentName", studentName);
		}
		try {
			page = publicSer.findFormerTerm(map,page);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Paper> list = new ArrayList<Paper>();
		list = page.getResults();
		    HSSFWorkbook wb = writeOrderInfo2ExcelOutputStreamForFormerTerm(list, response);
			String filename = "往届学生信息"+".xls";
			downloadExcel(wb, filename,request,response);
	}
	
	// 导出往届学生信息 创建工作量excel文件
  	private HSSFWorkbook writeOrderInfo2ExcelOutputStreamForFormerTerm(
  			List<Paper> list, HttpServletResponse response) {
  		/**
  		 * List<Map<String, Object>>
  		 */
  		HSSFWorkbook wb = new HSSFWorkbook();
  		HSSFCellStyle numStyle = wb.createCellStyle();
  		numStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
  		int size = list == null ? 0 : list.size();
  		if (size > 0) {
  			int sheetSize = 5000;
  			int sheetCount = (int) Math.ceil(size / sheetSize + 1);
  			
  			for (int n = 0; n < sheetCount; n++) {
  				HSSFSheet sheet = wb.createSheet("表" + (n + 1));
  				sheet.setDefaultColumnWidth((short) 6);
  				HSSFRow rowHead = sheet.createRow(0);

  				rowHead.createCell((short) 0).setCellValue(
  						new HSSFRichTextString("学号"));
  				rowHead.createCell((short) 1).setCellValue(
  						new HSSFRichTextString("姓名"));
  				rowHead.createCell((short) 2).setCellValue(
  						new HSSFRichTextString("专业"));
  				rowHead.createCell((short) 3).setCellValue(
  						new HSSFRichTextString("班级"));
  				rowHead.createCell((short) 4).setCellValue(
  						new HSSFRichTextString("毕业年级"));
  				rowHead.createCell((short) 5).setCellValue(
  						new HSSFRichTextString("课题名称"));
  				rowHead.createCell((short) 6).setCellValue(
  						new HSSFRichTextString("最终成绩"));
  				rowHead.createCell((short) 7).setCellValue(
  						new HSSFRichTextString("指导老师"));
  				rowHead.createCell((short) 8).setCellValue(
  						new HSSFRichTextString("评阅老师"));

  				// 10001: 0-4999, 5000-9999, 10000-10001
  				for (int i = n * sheetSize; i < list.size()
  						&& i < (n + 1) * sheetSize; i++) {
  					HSSFRow rowBody = sheet.createRow(i % sheetSize + 1);
  					Paper paper = list.get(i);
  					rowBody.createCell((short) 0).setCellValue(
  							new HSSFRichTextString(paper.getStuNum()));
  					rowBody.createCell((short) 1).setCellValue(
  							new HSSFRichTextString(paper.getStuName()));				
  					rowBody.createCell((short) 2).setCellValue(
  							new HSSFRichTextString(paper.getStuMajor()));
  					rowBody.createCell((short) 3).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getStuClass())));
  					rowBody.createCell((short) 4).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getTerm())));
  					rowBody.createCell((short) 5).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getTitle())));
  					rowBody.createCell((short) 6).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getFinalGrade())));
  					rowBody.createCell((short) 7).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getZdTeacher())));
  					rowBody.createCell((short) 8).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getPyTeacher())));
  				}
  			}
  		}
  		return wb;
  	}
		
}
