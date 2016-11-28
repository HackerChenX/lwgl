package com.hlzt.power.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlzt.commons.helper.SysConfig;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.SubmitStatistics;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.service.MajorLeaderFlowInfoStatisticsSer;
import com.hlzt.power.service.PublicSer;

/**
 * 专业负责人过程信息统计
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/majorLeader")
public class MajorLeaderFlowInfoStatisticsController {
	
	@Resource
	private MajorLeaderFlowInfoStatisticsSer mlfisSer;
	@Resource
	private PublicSer publicSer;
	
	/**
	 * 查询毕设学生信息
	 * @param major
	 * @param className
	 * @param zdTeaName
	 * @param teaName
	 * @param stuNum
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findStuInfo.shtm")
	public String findStuInfo(String className, String zdTeaName, String stuName, String stuNum, 
			BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
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
		if("0".equals(className)){
			className = null;
		}
		List<ClassName> cnList = new ArrayList<ClassName>();
		Major m = publicSer.findMajorByName(major);
		cnList = publicSer.findClass(m.getId());
		model.addAttribute("cnList", cnList);
		try {
			page = mlfisSer.findStudentByCondition(major, className, zdTeaName, stuName, stuNum, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("className", className);
		model.addAttribute("zdTeaName", zdTeaName);
		model.addAttribute("stuName", stuName);
		model.addAttribute("stuNum", stuNum);
		
		model.addAttribute("page", page);
		return "Director/grad_stu_info.jsp";
	}
	
	/**
	 * 导出毕设学生信息
	 * @param major
	 * @param className
	 * @param zdTeaName
	 * @param teaName
	 * @param stuNum
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportStuInfo.shtm")
	public void exportStuInfo(String className, String zdTeaName, String stuName, String stuNum, 
			BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		String major = teacher.getMajor();
		if("0".equals(className)){
			className = null;
		}
		page.setPageSize(0);
		List<ClassName> cnList = new ArrayList<ClassName>();
		Major m = publicSer.findMajorByName(major);
		cnList = publicSer.findClass(m.getId());
		model.addAttribute("cnList", cnList);		
		try {
			page = mlfisSer.findStudentByCondition(major, className, zdTeaName, stuName, stuNum, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb = writeOrderInfoExcelOutputStreamForStuInfo(page.getResults(), response);
		String filename = SysConfig.getValue("term")+major+"毕设学生信息"+".xls";
		downloadExcel(wb, filename, response);
	}
	
	// 导出毕设学生信息创建工作量excel文件
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
  						new HSSFRichTextString("学号"));
  				rowHead.createCell((short) 1).setCellValue(
  						new HSSFRichTextString("姓名"));
  				rowHead.createCell((short) 2).setCellValue(
  						new HSSFRichTextString("性别"));
  				rowHead.createCell((short) 3).setCellValue(
  						new HSSFRichTextString("专业"));
  				rowHead.createCell((short) 4).setCellValue(
  						new HSSFRichTextString("班级"));
  				rowHead.createCell((short) 5).setCellValue(
  						new HSSFRichTextString("课题"));
  				rowHead.createCell((short) 6).setCellValue(
  						new HSSFRichTextString("指导老师"));
  				rowHead.createCell((short) 7).setCellValue(
  						new HSSFRichTextString("联系方式"));
  				rowHead.createCell((short) 8).setCellValue(
  						new HSSFRichTextString("电子邮箱"));

  				// 10001: 0-4999, 5000-9999, 10000-10001
  				for (int i = n * sheetSize; i < list.size()
  						&& i < (n + 1) * sheetSize; i++) {
  					HSSFRow rowBody = sheet.createRow(i % sheetSize + 1);
  					Student stu = list.get(i);
  					rowBody.createCell((short) 0).setCellValue(
  							new HSSFRichTextString(stu.getUserNum()));					
  					rowBody.createCell((short) 1).setCellValue(
  							new HSSFRichTextString(stu.getStuName())); 					
  					rowBody.createCell((short) 2).setCellValue(
  							new HSSFRichTextString(stu.getSex()));
  					rowBody.createCell((short) 3).setCellValue(
  							new HSSFRichTextString(getNullString(stu.getMajor())));
  					rowBody.createCell((short) 4).setCellValue(
  							new HSSFRichTextString(getNullString(stu.getStuClass())));
  					rowBody.createCell((short) 5).setCellValue(
  							new HSSFRichTextString(getNullString(stu.getTitle())));
  					rowBody.createCell((short) 6).setCellValue(
  							new HSSFRichTextString(getNullString(stu.getZdTeaName())));
  					rowBody.createCell((short) 7).setCellValue(
  							new HSSFRichTextString(getNullString(stu.getTel())));
  					rowBody.createCell((short) 8).setCellValue(
  							new HSSFRichTextString(getNullString(stu.getMail())));
  				}
  			}
  		}
  		return wb;
  	}
	
	/**
	 * 查询任务书信息
	 * @param className
	 * @param zdTeaName
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/TaskBookInfo.shtm")
	public String TaskBookInfo(String className, String zdTeaName, Model model, 
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
		if(StringUtils.isNotBlank(className)){
			if("0".equals(className)){
				className = null;
			}
		}
		List<ClassName> cnList = new ArrayList<ClassName>();
		Major m = publicSer.findMajorByName(major);
		cnList = publicSer.findClass(m.getId());
		model.addAttribute("cnList", cnList);
		List<SubmitStatistics> list = new ArrayList<SubmitStatistics>();
		try {
			list = mlfisSer.findTaskBookSubmit(major, className, zdTeaName);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("list", list);
		return "Director/stu_task_info.jsp";
	}
	
	/**
	 * 查询任务书审核状态下学生名单
	 * @param stageName
	 * @param zdTeaStatus
	 * @param major
	 * @param className
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectTB_findStuList.shtm")
	public String findTbStuList(String stageName, String zdTeaStatus, 
			String className, BasePage<Student> page, Model model, 
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		page.setPageUrl("/majorLeader/findStuList.shtm");
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
		if(StringUtils.isBlank(stageName)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(zdTeaStatus)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(className)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String method = request.getMethod();
		if("GET".equals(method)){
			try {
				className = new String(className.getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e1) {			
				e1.printStackTrace();
				model.addAttribute("errorMsg","系统异常！");
				return "error/error.jsp";
			}
		}
		try {
			if("0".equals(zdTeaStatus)){
				page = mlfisSer.findChecking(stageName, zdTeaStatus, major, className, page);
			}else if("1".equals(zdTeaStatus)){
				page = mlfisSer.findPass(stageName, zdTeaStatus, major, className, page);
			}else if("2".equals(zdTeaStatus)){
				page = mlfisSer.findNotPass(stageName, zdTeaStatus, major, className, page);
			}else if("3".equals(zdTeaStatus)){
				page = mlfisSer.findNotSubmit(stageName, major, className, page);
			}else{
				model.addAttribute("errorMsg","系统异常！");
				return "error/error.jsp";
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		model.addAttribute("stageName", stageName);
		model.addAttribute("zdTeaStatus", zdTeaStatus);
		model.addAttribute("className", className);
		return "Director/task_sub_stu.jsp";
	}
	
	/**
	 * 查询开题报告信息
	 * @param className
	 * @param zdTeaName
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/OpeningReportInfo.shtm")
	public String OpeningReportInfo(String className, String zdTeaName, Model model, 
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
		if(StringUtils.isNotBlank(className)){
			if("0".equals(className)){
				className = null;
			}
		}
		List<ClassName> cnList = new ArrayList<ClassName>();
		Major m = publicSer.findMajorByName(major);
		cnList = publicSer.findClass(m.getId());
		model.addAttribute("cnList", cnList);
		List<SubmitStatistics> list = new ArrayList<SubmitStatistics>();
		try {
			list = mlfisSer.findOpeningReportSubmit(major, className, zdTeaName);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("list", list);
		return "Director/opening_report_info.jsp";
	}
	
	/**
	 * 查询开题报告审核状态下学生名单
	 * @param stageName
	 * @param zdTeaStatus
	 * @param major
	 * @param className
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectOR_findStuList.shtm")
	public String findOrStuList(String stageName, String zdTeaStatus, 
			String className, BasePage<Student> page, Model model, 
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		page.setPageUrl("/majorLeader/findStuList.shtm");
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
		if(StringUtils.isBlank(stageName)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(zdTeaStatus)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(className)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		String method = request.getMethod();
		if("GET".equals(method)){
		try {
			className = new String(className.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e1) {			
			e1.printStackTrace();
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		}
		try {
			if("0".equals(zdTeaStatus)){
				page = mlfisSer.findChecking(stageName, zdTeaStatus, major, className, page);
			}else if("1".equals(zdTeaStatus)){
				page = mlfisSer.findPass(stageName, zdTeaStatus, major, className, page);
			}else if("2".equals(zdTeaStatus)){
				page = mlfisSer.findNotPass(stageName, zdTeaStatus, major, className, page);
			}else if("3".equals(zdTeaStatus)){
				page = mlfisSer.findNotSubmit(stageName, major, className, page);
			}else{
				model.addAttribute("errorMsg","系统异常！");
				return "error/error.jsp";
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		model.addAttribute("stageName", stageName);
		model.addAttribute("zdTeaStatus", zdTeaStatus);
		model.addAttribute("className", className);
		return "Director/report_sub_stu.jsp";
	}
	
	/**
	 * 导出各阶段审核状态下学生名单
	 * @param stageName
	 * @param zdTeaStatus
	 * @param major
	 * @param className
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportStuList.shtm")
	public void exportStuList(String stageName, String zdTeaStatus, 
			String className, BasePage<Student> page, Model model, 
			HttpServletRequest request, HttpServletResponse response){
		page.setPageUrl("/majorLeader/findStuList.shtm");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		String major = teacher.getMajor();
		page.setPageSize(0);
		try {
			if("0".equals(zdTeaStatus)){
				page = mlfisSer.findChecking(stageName, zdTeaStatus, major, className, page);
			}else if("1".equals(zdTeaStatus)){
				page = mlfisSer.findPass(stageName, zdTeaStatus, major, className, page);
			}else if("2".equals(zdTeaStatus)){
				page = mlfisSer.findNotPass(stageName, zdTeaStatus, major, className, page);
			}else if("3".equals(zdTeaStatus)){
				page = mlfisSer.findNotSubmit(stageName, major, className, page);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("task_book".equals(stageName)){
			stageName = "任务书";
		}
		if("opening_report".equals(stageName)){
			stageName = "开题报告";
		}
		if("0".equals(zdTeaStatus)){
			zdTeaStatus = "指导老师待审核";
		}
		if("1".equals(zdTeaStatus)){
			zdTeaStatus = "指导审核通过";
		}
		if("2".equals(zdTeaStatus)){
			zdTeaStatus = "指导老师驳回";
		}
		if("3".equals(zdTeaStatus)){
			zdTeaStatus = "未提交";
		}
		HSSFWorkbook wb = writeOrderInfoExcelOutputStreamForStuInfo(page.getResults(), response);
		String filename = SysConfig.getValue("term")+stageName+zdTeaStatus+"学生名单"+".xls";
		downloadExcel(wb, filename, response);
	}
	
		
	
	/**
	 * 学生最终成绩查询
	 * @param className
	 * @param zdTeaName
	 * @param stuName
	 * @param stuNum
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findStuFinalGrade.shtm")
	public String findStuFinalGrade(String className, String zdTeaName, String stuName, String stuNum,
			BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("major_leader")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
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
		if("0".equals(className)){
			className = null;
		}
		try {
			page = mlfisSer.findAllGrade(className, zdTeaName, stuName, stuNum, major, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ClassName> cnList = new ArrayList<ClassName>();
		Major m = publicSer.findMajorByName(major);
		cnList = publicSer.findClass(m.getId());
		model.addAttribute("cnList", cnList);
		
		model.addAttribute("className", className);
		model.addAttribute("zdTeaName", zdTeaName);
		model.addAttribute("stuName", stuName);
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("page", page);
		return "Director/stu_score_pool.jsp";
	}
	
	
	/**
	 * 导出学生最终成绩
	 * @param className
	 * @param zdTeaName
	 * @param stuName
	 * @param stuNum
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportStuFinalGrade.shtm")
	public void exportStuFinalGrade(String className, String zdTeaName, String stuName, String stuNum,
			BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		String major = teacher.getMajor();
		page.setPageSize(0);
		if("0".equals(className)){
			className = null;
		}
		try {
			page = mlfisSer.findAllGrade(className, zdTeaName, stuName, stuNum, major, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb = writeOrderInfoExcelOutputStreamForGrade(page.getResults(), response);
		String filename = SysConfig.getValue("term")+major+"学生毕业论文成绩"+".xls";
		downloadExcel(wb, filename, response);
	}
	
	
	
	// 创建工作量excel文件
 	@SuppressWarnings("deprecation")
	private HSSFWorkbook writeOrderInfoExcelOutputStreamForGrade(
 			List<Grade> list, HttpServletResponse response) {
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
 				sheet.setDefaultColumnWidth((short) 12);
 				HSSFRow rowHead = sheet.createRow(0);

 				rowHead.createCell((short) 0).setCellValue(
 						new HSSFRichTextString("学号"));
 				rowHead.createCell((short) 1).setCellValue(
 						new HSSFRichTextString("姓名"));
 				rowHead.createCell((short) 2).setCellValue(
 						new HSSFRichTextString("性别"));
 				rowHead.createCell((short) 3).setCellValue(
 						new HSSFRichTextString("专业"));
 				rowHead.createCell((short) 4).setCellValue(
 						new HSSFRichTextString("班级"));
 				rowHead.createCell((short) 5).setCellValue(
 						new HSSFRichTextString("课题名"));
 				rowHead.createCell((short) 6).setCellValue(
 						new HSSFRichTextString("审阅成绩"));
 				rowHead.createCell((short) 7).setCellValue(
 						new HSSFRichTextString("评阅成绩"));
 				rowHead.createCell((short) 8).setCellValue(
 						new HSSFRichTextString("答辩成绩"));
 				rowHead.createCell((short) 9).setCellValue(
 						new HSSFRichTextString("最终成绩"));
 				rowHead.createCell((short) 10).setCellValue(
 						new HSSFRichTextString("评价"));
 				rowHead.createCell((short) 11).setCellValue(
 						new HSSFRichTextString("指导老师"));
 				rowHead.createCell((short) 12).setCellValue(
 						new HSSFRichTextString("评阅老师"));

 				// 10001: 0-4999, 5000-9999, 10000-10001
 				for (int i = n * sheetSize; i < list.size()
 						&& i < (n + 1) * sheetSize; i++) {
 					HSSFRow rowBody = sheet.createRow(i % sheetSize + 1);
 					Grade grade = list.get(i);
 					rowBody.createCell((short) 0).setCellValue(
 							new HSSFRichTextString(getNullString(grade.getStuNum())));
 					rowBody.createCell((short) 1).setCellValue(
 							new HSSFRichTextString(getNullString(grade.getStuName())));// 所属省份
 					rowBody.createCell((short) 2).setCellValue(
 							new HSSFRichTextString(getNullString(grade.getStuSex())));
					rowBody.createCell((short) 3).setCellValue(
 							new HSSFRichTextString(getNullString(grade.getStuMajor())));
					rowBody.createCell((short) 4).setCellValue(
 							new HSSFRichTextString(getNullString(grade.getStuClass())));
					rowBody.createCell((short) 5).setCellValue(
 							new HSSFRichTextString(getNullString(grade.getTitle())));
					rowBody.createCell((short) 6).setCellValue(
 							new HSSFRichTextString(getNullString(grade.getSyGrade())));
					rowBody.createCell((short) 7).setCellValue(
 							new HSSFRichTextString(getNullString(grade.getPyGrade())));
					rowBody.createCell((short) 8).setCellValue(
 							new HSSFRichTextString(getNullString(grade.getDbGrade())));
					rowBody.createCell((short) 9).setCellValue(
							new HSSFRichTextString(getNullString(grade.getFinalGrade())));
					rowBody.createCell((short) 10).setCellValue(
							new HSSFRichTextString(getNullString(grade.getZdTeacherName())));
					rowBody.createCell((short) 11).setCellValue(
							new HSSFRichTextString(getNullString(grade.getEvaluate())));
					rowBody.createCell((short) 12).setCellValue(
							new HSSFRichTextString(getNullString(grade.getPyTeacherName())));
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
	private void downloadExcel(HSSFWorkbook wb, String filename,
			HttpServletResponse response) {

		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			response.setHeader("Content-Type", "application/force-download");
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
	
	private String getNullString(Object data){
		if(data == null){
			return "";
		}
		String dataStr = String.valueOf(data);
		if("null".equalsIgnoreCase(dataStr)){
			return "";
		}
		return dataStr;
	}
	
	
	
	
	
	
	
	
	
}
