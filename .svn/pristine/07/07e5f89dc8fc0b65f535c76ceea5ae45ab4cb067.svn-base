package com.hlzt.power.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlzt.commons.helper.SysConfig;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.GradeAll;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.SubmitStatistics;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.service.PublicSer;
import com.hlzt.power.service.SecretaryFlowInfoStatisticsSer;

/**
 * 教学秘书过程信息统计
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/secretary")
public class SecretaryFlowInfoStatisticsController {
	
	@Resource
	private SecretaryFlowInfoStatisticsSer sfisSer;
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
	public String findStuInfo(String major, String className, String zdTeaName, String stuName, String stuNum, 
			BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String pageUrl = "/secretary/findStuInfo.shtm";
		page.setPageUrl(pageUrl);
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		if("0".equals(major)){
			major = null;
		}
		if("0".equals(className)){
			className = null;
		}
		try {
			page = sfisSer.findStudentByCondition(major, className, zdTeaName, stuName, stuNum, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Major> majorList = new ArrayList<Major>();
		majorList = publicSer.findMajor();
		model.addAttribute("majorList", majorList);
		List<ClassName> cList = new ArrayList<ClassName>();
		cList = publicSer.findClass(null);
		model.addAttribute("classList", cList);
		
		model.addAttribute("major", major);
		model.addAttribute("className", className);
		model.addAttribute("zdTeaName", zdTeaName);
		model.addAttribute("stuName", stuName);
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("page", page);
		return "Secretary/studentInfo.jsp";
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
	public void exportStuInfo(String major, String className, String zdTeaName, String stuName, String stuNum, 
			BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		if("0".equals(major)){
			major = null;
		}
		if("0".equals(className)){
			className = null;
		}
		page.setPageSize(0);
		try {
			page = sfisSer.findStudentByCondition(major, className, zdTeaName, stuName, stuNum, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb = writeOrderInfoExcelOutputStreamForStuInfo(page.getResults(), response);
		String filename = SysConfig.getValue("term")+"毕设学生信息"+".xls";
		downloadExcel(wb, filename,request,response);
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
	 * 查询学生任务书提交情况
	 * @param major
	 * @param classNmae
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTaskBookSubmit.shtm")
	public String findTaskBookSubmit(String stageName,String major, String className, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(stageName)){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		if(StringUtils.isNotBlank(major)){
			if("0".equals(major)){
				major = null;
			}
		}
		if(StringUtils.isNotBlank(className)){
			if("0".equals(className)){
				className = null;
			}
		}
		List<SubmitStatistics> list = new ArrayList<SubmitStatistics>();
		try {
			if("task_book".equals(stageName)){//任务书
				list = sfisSer.findTaskBookSubmit(major, className);
			}else if("opening_report".equals(stageName)){//开题报告
				list = sfisSer.findOpeningReportSubmit(major, className);
			}else if("mid_check".equals(stageName)){//中期检查表
				list = sfisSer.findMidCheckSubmit(major, className);
			}else if("first_paper".equals(stageName)){//初稿
				list = sfisSer.findFirstPaperSubmit(major, className);
			}else if("final_paper".equals(stageName)){//定稿
				list = sfisSer.findFinalPaperSubmit(major, className);
			}else{
				model.addAttribute("errorMsg","系统异常！");
				return "error/error.jsp";
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Major> majorList = new ArrayList<Major>();
		majorList = publicSer.findMajor();
		model.addAttribute("majorList", majorList);
		List<ClassName> cList = new ArrayList<ClassName>();
		cList = publicSer.findClass(null);
		model.addAttribute("classList", cList);
		model.addAttribute("stageName", stageName);
		model.addAttribute("major", major);
		model.addAttribute("className", className);
		model.addAttribute("list", list);
		model.addAttribute("url", "findTaskBookSubmit.shtm");
		return "Secretary/stuSubmitInfo.jsp";
	}
	
	/**
	 * 查询学生开题报告提交情况
	 * @param major
	 * @param classNmae
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findOpeningReportSubmit.shtm")
	public String findOpeningReportSubmit(String stageName,String major, String className, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(stageName)){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		if(StringUtils.isNotBlank(major)){
			if("0".equals(major)){
				major = null;
			}
		}
		if(StringUtils.isNotBlank(className)){
			if("0".equals(className)){
				className = null;
			}
		}
		List<SubmitStatistics> list = new ArrayList<SubmitStatistics>();
		try {
			if("task_book".equals(stageName)){//任务书
				list = sfisSer.findTaskBookSubmit(major, className);
			}else if("opening_report".equals(stageName)){//开题报告
				list = sfisSer.findOpeningReportSubmit(major, className);
			}else if("mid_check".equals(stageName)){//中期检查表
				list = sfisSer.findMidCheckSubmit(major, className);
			}else if("first_paper".equals(stageName)){//初稿
				list = sfisSer.findFirstPaperSubmit(major, className);
			}else if("final_paper".equals(stageName)){//定稿
				list = sfisSer.findFinalPaperSubmit(major, className);
			}else{
				model.addAttribute("errorMsg","系统异常！");
				return "error/error.jsp";
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Major> majorList = new ArrayList<Major>();
		majorList = publicSer.findMajor();
		model.addAttribute("majorList", majorList);
		List<ClassName> cList = new ArrayList<ClassName>();
		cList = publicSer.findClass(null);
		model.addAttribute("classList", cList);
		model.addAttribute("stageName", stageName);
		model.addAttribute("major", major);
		model.addAttribute("className", className);
		model.addAttribute("list", list);
		model.addAttribute("url", "findOpeningReportSubmit.shtm");
		return "Secretary/stuSubmitInfo.jsp";
	}
	
	/**
	 * 查询学生中期检查表提交情况
	 * @param major
	 * @param classNmae
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findMidCheckSubmit.shtm")
	public String findMidCheckSubmit(String stageName,String major, String className, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(stageName)){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		if(StringUtils.isNotBlank(major)){
			if("0".equals(major)){
				major = null;
			}
		}
		if(StringUtils.isNotBlank(className)){
			if("0".equals(className)){
				className = null;
			}
		}
		List<SubmitStatistics> list = new ArrayList<SubmitStatistics>();
		try {
			if("task_book".equals(stageName)){//任务书
				list = sfisSer.findTaskBookSubmit(major, className);
			}else if("opening_report".equals(stageName)){//开题报告
				list = sfisSer.findOpeningReportSubmit(major, className);
			}else if("mid_check".equals(stageName)){//中期检查表
				list = sfisSer.findMidCheckSubmit(major, className);
			}else if("first_paper".equals(stageName)){//初稿
				list = sfisSer.findFirstPaperSubmit(major, className);
			}else if("final_paper".equals(stageName)){//定稿
				list = sfisSer.findFinalPaperSubmit(major, className);
			}else{
				model.addAttribute("errorMsg","系统异常！");
				return "error/error.jsp";
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Major> majorList = new ArrayList<Major>();
		majorList = publicSer.findMajor();
		model.addAttribute("majorList", majorList);
		List<ClassName> cList = new ArrayList<ClassName>();
		cList = publicSer.findClass(null);
		model.addAttribute("classList", cList);
		model.addAttribute("stageName", stageName);
		model.addAttribute("major", major);
		model.addAttribute("className", className);
		model.addAttribute("list", list);
		model.addAttribute("url", "findMidCheckSubmit.shtm");
		return "Secretary/stuSubmitInfo.jsp";
	}
	
	/**
	 * 查询学生定稿提交情况
	 * @param major
	 * @param classNmae
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findFinalPaperSubmit.shtm")
	public String findFinalPaperSubmit(String stageName,String major, String className, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(stageName)){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		if(StringUtils.isNotBlank(major)){
			if("0".equals(major)){
				major = null;
			}
		}
		if(StringUtils.isNotBlank(className)){
			if("0".equals(className)){
				className = null;
			}
		}
		List<SubmitStatistics> list = new ArrayList<SubmitStatistics>();
		try {
			if("task_book".equals(stageName)){//任务书
				list = sfisSer.findTaskBookSubmit(major, className);
			}else if("opening_report".equals(stageName)){//开题报告
				list = sfisSer.findOpeningReportSubmit(major, className);
			}else if("mid_check".equals(stageName)){//中期检查表
				list = sfisSer.findMidCheckSubmit(major, className);
			}else if("first_paper".equals(stageName)){//初稿
				list = sfisSer.findFirstPaperSubmit(major, className);
			}else if("final_paper".equals(stageName)){//定稿
				list = sfisSer.findFinalPaperSubmit(major, className);
			}else{
				model.addAttribute("errorMsg","系统异常！");
				return "error/error.jsp";
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Major> majorList = new ArrayList<Major>();
		majorList = publicSer.findMajor();
		model.addAttribute("majorList", majorList);
		List<ClassName> cList = new ArrayList<ClassName>();
		cList = publicSer.findClass(null);
		model.addAttribute("classList", cList);
		model.addAttribute("stageName", stageName);
		model.addAttribute("major", major);
		model.addAttribute("className", className);
		model.addAttribute("list", list);
		model.addAttribute("url", "findFinalPaperSubmit.shtm");
		return "Secretary/stuSubmitInfo.jsp";
	}
	
	/**
	 * 根据当前阶段专业审核状态查询学生
	 * @param leaderStatus
	 * @param major
	 * @param className
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findStuSubmit_findStuBySubStatus.shtm")
	public String findStuBySubStatus(String stageName, String leaderStatus, String major, String className, 
			BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}		
		if(StringUtils.isNotBlank(major)){
			if("0".equals(major)){
				major = null;
			}
			String method = request.getMethod();
			if("GET".equals(method)){
				try {
					major = new String(major.getBytes("ISO-8859-1"),"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		if(StringUtils.isNotBlank(className)){
			if("0".equals(className)){
				className = null;
			}
			String method = request.getMethod();
			if("GET".equals(method)){
				try {
					className = new String(className.getBytes("ISO-8859-1"),"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		if(StringUtils.isBlank(stageName)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}else if(!"task_book".equals(stageName)&&!"opening_report".equals(stageName)&&!"mid_check".equals(stageName)&&
				!"first_paper".equals(stageName)&&!"final_paper".equals(stageName)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(leaderStatus)){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		if("0".equals(leaderStatus)){
			page = sfisSer.findChecking(stageName, leaderStatus, major, className, page);
		}else if("1".equals(leaderStatus)){
			page = sfisSer.findPass(stageName, leaderStatus, major, className, page);
		}else if("2".equals(leaderStatus)){
			page = sfisSer.findNotPass(stageName, leaderStatus, major, className, page);
		}else if("3".equals(leaderStatus)){
			page = sfisSer.findNotSubmit(stageName, major, className, page);
		}else{
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}

		model.addAttribute("stageName", stageName);
		model.addAttribute("leaderStatus", leaderStatus);
		model.addAttribute("major", major);
		model.addAttribute("className", className);
		model.addAttribute("page", page);
		
		return "Public_Page/stu_sub_list.jsp";
	}
	
	/**
	 * 导出根据当前阶段专业审核状态查询学生
	 * @param leaderStatus
	 * @param major
	 * @param className
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportStuBySubStatus.shtm")
	public void exportStuBySubStatus(String stageName, String leaderStatus, String major, String className, 
			BasePage<Student> page, Model model, HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isNotBlank(major)){
			if("0".equals(major)){
				major = null;
			}
		}
		if(StringUtils.isNotBlank(className)){
			if("0".equals(className)){
				className = null;
			}
		}
		page.setPageSize(0);
		if("0".equals(leaderStatus)){
			page = sfisSer.findChecking(stageName, leaderStatus, major, className, page);
		}else if("1".equals(leaderStatus)){
			page = sfisSer.findPass(stageName, leaderStatus, major, className, page);
		}else if("2".equals(leaderStatus)){
			page = sfisSer.findNotPass(stageName, leaderStatus, major, className, page);
		}else if("3".equals(leaderStatus)){
			page = sfisSer.findNotSubmit(stageName, major, className, page);
		}
		String sn = null;
		if("task_book".equals(stageName)){
			sn = "任务书";
		}
		if("opening_report".equals(stageName)){
			sn = "开题报告";
		}
		if("mid_check".equals(stageName)){
			sn = "中期检查表";
		}
		if("first_paper".equals(stageName)){
			sn = "初稿";
		}
		if("final_paper".equals(stageName)){
			sn = "定稿";
		}
		String ls = null;
		if("0".equals(leaderStatus)){
			ls = "待审核";
		}
		if("1".equals(leaderStatus)){
			ls = "审核通过";
		}
		if("2".equals(leaderStatus)){
			ls = "被退回";
		}
		if("3".equals(leaderStatus)){
			ls = "未提交";
		}
		HSSFWorkbook wb = writeOrderInfoExcelOutputStreamForStuInfo(page.getResults(), response);
		String filename = SysConfig.getValue("term")+sn +ls +"学生名单.xls";
		downloadExcel(wb, filename,request, response);
	}
	
	/**
	 * 查看答辩小组安排
	 * @param major
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findDbGroup.shtm")
	public String findDbGroup(String major, BasePage<DbGroup> page, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		page.setPageUrl("/secretary/findDbGroup.shtm");
		if(StringUtils.isNotBlank(major)){
			if("0".equals(major)){
				major = null;
			}
		}
		try {
			page = sfisSer.findDbGroup(major, page);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		if(page!=null){	
		List<DbGroup> dbGrouplist = page.getResults();
		DbGroup dbGroup = null;
		
		for(int i=0;i<dbGrouplist.size();i++)
		{
			dbGroup = dbGrouplist.get(0);
			Date date = dbGroup.getDbTime();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:MM");
			dbGroup.setDateTimeStr(sdf.format(date));
		}
		List<Major> majorList = new ArrayList<Major>();
		majorList = publicSer.findMajor();
		model.addAttribute("majorList", majorList);
		}
		model.addAttribute("page", page);
		model.addAttribute("major", major);
		return "Secretary/defendGroupInfo.jsp";
	}

	/**
	 * 查看答辩小组学生名单
	 * @param dbGroupId
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findDbGroup_findStuByDbgroup.shtm")
	public String findStuByDbgroup(String dbGroupId, BasePage page, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
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
		if(StringUtils.isBlank(dbGroupId)){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		try {
			page = sfisSer.findDbGroupStu(dbGroupId, page);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		model.addAttribute("dbGroupId", dbGroupId);
		model.addAttribute("page", page);
		return "Secretary/db_group_stu.jsp";
	}
	
	/**
	 * 导出答辩小组学生名单
	 * @param dbGroupId
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportStuByDbgroup.shtm")
	public void exportStuByDbgroup(String dbGroupId, BasePage<Student> page, Model model,
			HttpServletRequest request, HttpServletResponse response){
		page.setPageSize(0);
		try {
			page = sfisSer.findDbGroupStu(dbGroupId, page);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}		
		HSSFWorkbook wb = writeOrderInfoExcelOutputStreamForStuInfo(page.getResults(), response);
		String filename = SysConfig.getValue("term") + "答辩小组名单.xls";
		downloadExcel(wb, filename,request, response);
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
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")){
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		String url = "secretary";
		model.addAttribute("url", url);
		if(StringUtils.isNotBlank(formerTerm)){
			map.put("term", formerTerm);
			model.addAttribute("formerTerm", formerTerm);
		}if(StringUtils.isNotBlank(teacherName)){
			map.put("teaName", teacherName);
			model.addAttribute("teacherName", teacherName);
		}if(StringUtils.isNotBlank(studentName)){
			map.put("stuName", studentName);
			model.addAttribute("studentName", studentName);
		}if(StringUtils.isNotBlank(title)){
			map.put("title", title);
			model.addAttribute("title", title);
		}
		try{
			page=publicSer.findFormerTerm(map,page);
		}catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page",page);
		return "Secretary/formerTerm.jsp";
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
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Paper paper = publicSer.findFormTermById(id);
		model.addAttribute("paper", paper);
		return "Public_Page/formerTermInfo.jsp";
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
	
  	/**
  	 * @Title: exportDbGroup
  	 * @Description: 导出答辩安排
  	 * @param model
  	 * @param major
  	 * @param page
  	 * @param request
  	 * @param response void 
  	 * @throws
  	 */
	@RequestMapping("/exportDbGroup.shtm")
  	public void exportDbGroup(Model model,String major,BasePage<DbGroup> page,HttpServletRequest request, HttpServletResponse response){
		//默认导出全部专业答辩安排	
		major = null;		
		page.setPageSize(0);
		try {
			page = sfisSer.findDbGroup(major, page);
		} catch (Exception e){
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		if(page!=null){	
			List<DbGroup> dbGrouplist = page.getResults();
			DbGroup dbGroup = null;		
			for(int i=0;i<dbGrouplist.size();i++)
			{
				dbGroup = dbGrouplist.get(0);
				Date date = dbGroup.getDbTime();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:MM");
				dbGroup.setDateTimeStr(sdf.format(date));
			}
		}
		HSSFWorkbook wb = writeOrderInfoExcelOutputStreamForDbGroup(page.getResults(),response);
		String filename = SysConfig.getValue("term")+"答辩安排"+".xls";
		downloadExcel(wb, filename,request, response);

	}
	//导出答辩安排Excel
	private HSSFWorkbook writeOrderInfoExcelOutputStreamForDbGroup(
  			List<DbGroup> list, HttpServletResponse response) {
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
  						new HSSFRichTextString("专业名称"));
  				rowHead.createCell((short) 1).setCellValue(
  						new HSSFRichTextString("组名"));
  				rowHead.createCell((short) 2).setCellValue(
  						new HSSFRichTextString("答辩组组长"));
  				rowHead.createCell((short) 3).setCellValue(
  						new HSSFRichTextString("答辩组成员"));
  				rowHead.createCell((short) 4).setCellValue(
  						new HSSFRichTextString("答辩秘书"));		
  				rowHead.createCell((short) 5).setCellValue(
  						new HSSFRichTextString("答辩日期"));
  				rowHead.createCell((short) 6).setCellValue(
  						new HSSFRichTextString("答辩地点"));
  				// 10001: 0-4999, 5000-9999, 10000-10001
  				for (int i = n * sheetSize; i < list.size()
  						&& i < (n + 1) * sheetSize; i++) {
  					HSSFRow rowBody = sheet.createRow(i % sheetSize + 1);
  					DbGroup dbGroup = list.get(i);
  					rowBody.createCell((short) 0).setCellValue(
  							new HSSFRichTextString(dbGroup.getMajor()));
  					rowBody.createCell((short) 1).setCellValue(
  							new HSSFRichTextString(getNullString(dbGroup.getGroupName())));				
  					rowBody.createCell((short) 2).setCellValue(
  							new HSSFRichTextString(getNullString(dbGroup.getGroupLeaderName())));
  					rowBody.createCell((short) 3).setCellValue(
  							new HSSFRichTextString(getNullString(dbGroup.getGroupMemberName())));
  					rowBody.createCell((short) 4).setCellValue(
  							new HSSFRichTextString(getNullString(dbGroup.getGroupSecretaryName())));
  					rowBody.createCell((short) 5).setCellValue(
  							new HSSFRichTextString(getNullString(dbGroup.getDateTimeStr())));
  					rowBody.createCell((short) 6).setCellValue(
  							new HSSFRichTextString(getNullString(dbGroup.getGroupSite()))); 					
  				}
  			}
  		}
  		return wb;
  	}
  	
	
}
