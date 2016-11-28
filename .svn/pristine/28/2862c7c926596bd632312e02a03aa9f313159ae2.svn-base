package com.hlzt.power.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hlzt.commons.helper.SysConfig;
import com.hlzt.commons.helper.UuidHelper;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ApplyDelay;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.BackLog;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.FirstPaper;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.GradeAll;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.MidCheck;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.PublicNotice;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.TeacherTitle;
import com.hlzt.power.model.User;
import com.hlzt.power.service.PublicSer;
import com.hlzt.power.service.SecretaryFlowManageSer;

@Controller
@RequestMapping("/secretary")
public class SecretaryFlowController {
	@Resource
	private SecretaryFlowManageSer secretaryFlowManageSer;
	@Resource
	private PublicSer publicSer;
	
	/**
	 * 查看单个教师发布的课题
	 * @param TeacherTitleId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTeaTitle_findTeaTitleById.shtm")
	public String findTeacherTitleById(String id, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		
		if(StringUtils.isBlank(id)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		TeacherTitle tt = new TeacherTitle();
		
		try {
			tt = secretaryFlowManageSer.findeTeacherTitleById(id);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("teaTitle", tt);
		return "Secretary/projectInfo.jsp";
	}
	
	/**
	 * 按条件查询教师发布的课题
	 * @param major
	 * @param teaName
	 * @param status
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findTeaTitle.shtm")
	public String findTeacherTitle(String major, String teaName, String status,
			BasePage page, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		if("0".equals(major)){
			major = null;
		}
		if("3".equals(status)){
			status = null;
		}
		List<Major> majorList = new ArrayList<Major>();
		majorList = publicSer.findMajor();
		model.addAttribute("majorList", majorList);
		try {
			page = secretaryFlowManageSer.findTeacherTitle(major, teaName, status, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);	
		model.addAttribute("major", major);
		model.addAttribute("teaName", teaName);
		model.addAttribute("status", status);
		return "Secretary/teacherApplyProject.jsp";
	}
	
	
	/**
	 * 审核教师发布的课题
	 * @param ids
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTeaTitle_checkTeaTitile.shtm")
	public String checkTeacherTitile(String[] ids, String status, String managerIdea, String major, BasePage page,
			String teaName, String findStatus, Model model,HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		
		if(StringUtils.isNotBlank(major)){
			if("0".equals(major)){
				major = null;
			}
		}
		if(ids==null||"".equals(ids)){
			model.addAttribute("errorMsg", "您未选择数据！");
			return findTeacherTitle(major, teaName, findStatus, page, model, request, response);
		}
		if(status==null||"".equals(status)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = secretaryFlowManageSer.checkTeacherTitle(list, status, managerIdea);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！审核失败！");
		}
		model.addAttribute("page", page);	
		model.addAttribute("major", major);
		model.addAttribute("teaName", teaName);
		model.addAttribute("status", findStatus);
		if(status.equals("1")||status.equals("2")){
			//删除待办事项记录
			List<BackLog> backLogs = publicSer.findBackLog(null,null,"manager");
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
				TeacherTitle teacherTitle = secretaryFlowManageSer.findeTeacherTitleById(list.get(k));
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
					int b = publicSer.insertBackLog(backLog);
					int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
				}
			}
		}
		return findTeacherTitle(major, teaName, findStatus, page, model, request, response);
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
	public String findApplyTitle(String major, String stuName, String teaName, String status, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response){
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		model.addAttribute("major", major);
		if("0".equals(major)){
			major = null;
		}
		model.addAttribute("status", status);
		if("3".equals(status)){
			status = null;
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		List<Major> majorList = null;
		try {
			page = 	secretaryFlowManageSer.findApplyTitle(major, stuName, teaName, status, page);
			majorList = publicSer.findMajor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("stuName", stuName);
		model.addAttribute("teaName", teaName);
		model.addAttribute("page", page);
		model.addAttribute("majorList", majorList);
		return "/Secretary/check_stuTitle.jsp";
	}
	
	/**
	 * 根据id查询学生课题
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findApplyTitle_findStuTitleInfo.shtm")
	public String findStuTitleInfo(String id, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(id)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		ApplyTitle at = new ApplyTitle();
		try {
			at = secretaryFlowManageSer.findeApplyTitleById(id);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("applyTitle", at);
		return "Public_Page/stuProjectInfo.jsp";
	}
	
	/**
	 * 审核学生申报的课题
	 * @param ids
	 * @param status
	 * @param managerIdea
	 * @param major
	 * @param page
	 * @param teaName
	 * @param findStatus
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findApplyTitle_checkStuTitile.shtm")
	public String checkStuTitile(String[] ids, String status, String managerIdea, String stuName, String major, BasePage page,
			String teaName, String findStatus, Model model,HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(status)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(ids==null){
			model.addAttribute("errorMsg", "您未选择数据！");
			return findApplyTitle(major, stuName, teaName, findStatus, page, model, request, response);
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = secretaryFlowManageSer.checkApplyTitle(list, status, managerIdea);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(status.equals("1")||status.equals("2")){
			//删除待办事项记录
			List<BackLog> backLogs = publicSer.findBackLog(null,null,"manager");
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
				ApplyTitle applyTitle = secretaryFlowManageSer.findeApplyTitleById(list.get(k));
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
					int b = publicSer.insertBackLog(backLog);
					int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
				}
			}
		}
		return findApplyTitle(major, stuName, teaName, findStatus, page, model, request, response);
	}
	
	/**
	 * 成绩汇总查询
	 * @param major
	 * @param className
	 * @param zdTeaName
	 * @param stuName
	 * @param page
	 * @param stuNum
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findAllGrade.shtm")
	public String findAllGrade(String major, String className, String zdTeaName, String stuName, BasePage<Grade> page,
			String stuNum, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String pageUrl = "/secretary/findAllGrade.shtm";
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
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
		List<Major> majorList = new ArrayList<Major>();
		majorList = publicSer.findMajor();
		model.addAttribute("majorList", majorList);
		List<ClassName> cList = new ArrayList<ClassName>();
		cList = publicSer.findClass(null);
		model.addAttribute("classList", cList);
		try {
			page = secretaryFlowManageSer.findAllGrade(major, className, zdTeaName, stuName, stuNum, 0, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		page.setPageUrl(pageUrl);
		model.addAttribute("page", page);

		model.addAttribute("major", major);
		model.addAttribute("className", className);
		model.addAttribute("zdTeaName", zdTeaName);
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		return "Secretary/scoreSummary.jsp";
	}
	
	
	/**
	 * 导出成绩汇总查询结果
	 * @param major
	 * @param className
	 * @param zdTeaName
	 * @param stuName
	 * @param page
	 * @param stuNum
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportAllGrade.shtm")
	public void exportAllGrade(String major, String className, String zdTeaName, String stuName, BasePage<Grade> page,
			String stuNum, Model model, HttpServletRequest request, HttpServletResponse response){	
		String pageUrl = "/secretary/findAllGrade.shtm";
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
		List<Major> majorList = new ArrayList<Major>();
		majorList = publicSer.findMajor();
		model.addAttribute("majorList", majorList);
		List<ClassName> cList = new ArrayList<ClassName>();
		cList = publicSer.findClass(null);
		model.addAttribute("classList", cList);
		page.setPageSize(0);
		try {
			page = secretaryFlowManageSer.findAllGrade(major, className, zdTeaName, stuName, stuNum, 0, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb = writeOrderInfoExcelOutputStreamForGrade(page.getResults(), response);
		String filename = SysConfig.getValue("term")+"成绩汇总"+".xls";
		downloadExcel(wb, filename,request, response);

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
 				sheet.setDefaultColumnWidth((short) 14);
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
 						new HSSFRichTextString("指导老师职称"));
 				rowHead.createCell((short) 13).setCellValue(
 						new HSSFRichTextString("评阅老师"));
 				rowHead.createCell((short) 14).setCellValue(
 						new HSSFRichTextString("评阅老师职称"));

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
							new HSSFRichTextString(getNullString(grade.getEvaluate())));
					rowBody.createCell((short) 11).setCellValue(
							new HSSFRichTextString(getNullString(grade.getZdTeacherName())));
					rowBody.createCell((short) 12).setCellValue(
							new HSSFRichTextString(getNullString(grade.getZdZhiCheng())));
					rowBody.createCell((short) 13).setCellValue(
							new HSSFRichTextString(getNullString(grade.getPyTeacherName())));
					rowBody.createCell((short) 14).setCellValue(
							new HSSFRichTextString(getNullString(grade.getPyZhiCheng())));
 				}
 			}
 		}
 		return wb;
 	}
	
	
	/**
	 * 成绩总评
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findGradeAllEvaluate.shtm")
	public String findGradeAllEvaluate(Model model, HttpServletRequest request,
			HttpServletResponse response){
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")){
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常");
			return "error/error.jsp";
		}
		List<GradeAll> list = new ArrayList<GradeAll>();
		try {
			list = secretaryFlowManageSer.findGradeAllEvaluate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("list", list);
		return "Secretary/scoreGrade.jsp";
	}
	
	/**
	 * 查询优秀论文
	 * @param major
	 * @param className
	 * @param grade
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("findYxPaper.shtm")
	public String findYxPaper(String major, String className, String grade, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String pageUrl = "/secretary/findAllGrade.shtm";
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
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
		Pattern p = Pattern.compile("^[0-9]*[1-9][0-9]*$");
		int num = 0;
		if(StringUtils.isNotBlank(grade)){
			Matcher m = p.matcher(grade);
			if(m.matches()==false){
				model.addAttribute("errorMsg","请输入数字！");
				return "Secretary/recommendThesis.jsp";
			}			
			try {
				num = Integer.parseInt(grade);				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			page = secretaryFlowManageSer.findAllGrade(major, className, null, null, null, num, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Major> majorList = new ArrayList<Major>();
		majorList = publicSer.findMajor();
		model.addAttribute("majorList", majorList);
		List<ClassName> cList = new ArrayList<ClassName>();
		cList = publicSer.findClass(null);
		model.addAttribute("classList", cList);
		page.setPageUrl(pageUrl);
		model.addAttribute("page", page);

		model.addAttribute("major", major);
		model.addAttribute("className", className);
		model.addAttribute("grade", grade);
		return "Secretary/recommendThesis.jsp";
	}
	
	
	/**
	 * 导出优秀论文
	 * @param major
	 * @param className
	 * @param grade
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportYxPaper.shtm")
	public void exportYxPaper(String major, String className, String grade, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response){
		
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
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
		int num = 0;
		if(StringUtils.isNotBlank(grade)){
			num = Integer.parseInt(grade);				
		}
		page.setPageSize(0);
		try {
			page = secretaryFlowManageSer.findAllGrade(major, className, null, null, null, num, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb = writeOrderInfoExcelOutputStreamForGrade(page.getResults(), response);
		String filename = SysConfig.getValue("term")+"优秀论文学生"+".xls";
		downloadExcel(wb, filename,request,response);
	}
	
	
	/**
	 * 推荐优秀论文
	 * @param ids
	 * @param status
	 * @param major
	 * @param className
	 * @param grade
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findYxPaper_recommentYxPaper.shtm")
	public String recommentYxPaper(String[] ids, String status, String major, String className, String grade,
			BasePage page, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(ids==null||"".equals(ids)){
			model.addAttribute("errorMsg", "您未选择数据！");
			return findYxPaper(major, className, grade, page, model, request, response);
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		List<String> list = Arrays.asList(ids);
		if(status==null||"".equals(status)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		int i = 0;
		try {
			i = secretaryFlowManageSer.recommendYxPaper(list, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！操作失败！");			
		}
		
		return findYxPaper(major, className, grade, page, model, request, response);
	}
	
	/**
	 * 按条件查询学生论文信息
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
	public String findStuPaper(String major, String className, String endNum, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String pageUrl = "/secretary/findStuPaperInfo.shtm";
		if("0".equals(major)){
			major = null;
		}
		if("0".equals(className)){
			className = null;
		}
		if(StringUtils.isBlank(SysConfig.getValue("term"))){
			model.addAttribute("error", "请设置当前届！");
			return "Secretary/spotChecks.jsp";
		}
		try {
			page = secretaryFlowManageSer.findStuPaperInfo(major, className, endNum, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		page.setPageUrl(pageUrl);
		List<Major> majorList = new ArrayList<Major>();
		majorList = publicSer.findMajor();
		model.addAttribute("majorList", majorList);
		List<ClassName> cList = new ArrayList<ClassName>();
		cList = publicSer.findClass(null);
		model.addAttribute("classList", cList);
		
		model.addAttribute("page", page);
		model.addAttribute("major", major);
		model.addAttribute("className", className);
		model.addAttribute("endNum", endNum);
		return "Secretary/spotChecks.jsp";
	}
	
	/**
	 * @Title: paperInfo
	 * @Description: 查看学生论文详细信息
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
		if (!currentUser.hasRole("manager")) {
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
	 * 导出按条件查询学生论文信息
	 * @param major
	 * @param className
	 * @param endNum
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportStuPaperInfo.shtm")
	public void exportStuPaperInfo(String major, String className, String endNum, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response){
		page.setPageSize(0);
		try {
			page = secretaryFlowManageSer.findStuPaperInfo(major, className, endNum, page);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Paper> list = new ArrayList<Paper>();
		list = page.getResults();
		 HSSFWorkbook wb = writeOrderInfo2ExcelOutputStreamForSaleMan(list, response);
			String filename = SysConfig.getValue("term")+"抽检学生信息"+".xls";
			downloadExcel(wb, filename,request,response);
	}
	
	// 导出抽检学生信息 创建工作量excel文件
  	private HSSFWorkbook writeOrderInfo2ExcelOutputStreamForSaleMan(
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
  						new HSSFRichTextString("指导老师"));
  				rowHead.createCell((short) 5).setCellValue(
  						new HSSFRichTextString("课题名"));
  				rowHead.createCell((short) 6).setCellValue(
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
  							new HSSFRichTextString(paper.getStuMajor()));
  					rowBody.createCell((short) 3).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getStuClass())));
  					rowBody.createCell((short) 4).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getZdTeacher())));
  					rowBody.createCell((short) 5).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getTitle())));
  					rowBody.createCell((short) 6).setCellValue(
  							new HSSFRichTextString(getNullString(paper.getFinalGrade())));
  				}
  			}
  		}
  		return wb;
  	}
	
	/**
	 * 添加公告页面
	 * @param publicNotice
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("/noticeList_addNotice.shtm")
	public String addNotice(PublicNotice publicNotice,
			Model model, HttpServletRequest request,HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		return "Secretary/addNotice.jsp";
	}

	/**
	 * 公告列表
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("/noticeList.shtm")
	public String noticeList(Model model,Map<String, Object> map,String title,String noticeType, BasePage page,HttpServletRequest request,HttpServletResponse response){
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String pageUrl = "/secretary/noticeList.shtm";
		if(StringUtils.isNotBlank(title)){
			map.put("title", title);
			model.addAttribute("title", title);
		}
		if(StringUtils.isNotBlank(noticeType)&&!noticeType.equals("all")){
			map.put("noticeType",noticeType);
			model.addAttribute("noticeType", noticeType);
		}
			
		page = secretaryFlowManageSer.findPublicNotice(map,page);		
		page.setPageUrl(pageUrl);		
		model.addAttribute("page", page);
		return "Secretary/noticeList.jsp";
	}
	
	/**
	 * @Title: findSpecialInit
	 * @Description: 查看延期申请页面初始化
	 * @param page
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/findSpecialInit.shtm")
	public String findSpecialInit(Model model,BasePage<ApplyDelay> page, HttpServletRequest request, HttpServletResponse response)
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
		try{
			page = secretaryFlowManageSer.findApplyDelayInit();
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("page", page);
		model.addAttribute("status", "0");
		return "Secretary/major_modify_date.jsp";
	}
	
	/**
	 * 按条件查询延期申请
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findSpecialInit_findSpecial.shtm")
	public String findSpecial(String stuNum, String stuName, String zdTeaName, String status, Model model, 
			BasePage<ApplyDelay> page, HttpServletRequest request, HttpServletResponse response){
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if("0".equals(status)){
			return findSpecialInit(model,page, request, response);
		}else if("3".equals(status)){
			status = null;
		}		
		try {
			page = secretaryFlowManageSer.findApplyDelay(stuNum, stuName, zdTeaName, status, page);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		model.addAttribute("zdTeaName", zdTeaName);
		model.addAttribute("status", status);
		model.addAttribute("page", page);
		return "Secretary/major_modify_date.jsp";
	}
	
	/**
	 * 处理延期申请
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findSpecialInit_setSpecial.shtm")
	public String setSpecial(String[] ids, String idea, String status, String stuNum, 
			String stuName, String zdTeaName, String findStatus, Model model, BasePage page, 
			HttpServletRequest request, HttpServletResponse response){
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		Subject currentUser = SecurityUtils.getSubject();
		if(StringUtils.isNotBlank(idea)){
			try {
				idea = new String(idea.getBytes("iso8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {				
				e.printStackTrace();
			}	// get数据中文转码
		}
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(ids==null){
			model.addAttribute("errorMsg", "您未选择数据！");
			return findSpecial(stuNum, stuName, zdTeaName, findStatus, model, page, request, response);
		}
		List<String> list = Arrays.asList(ids);
		try {
			if("1".equals(status)){
				for (int i = 0; i < list.size(); i++) {
					ApplyDelay ad = secretaryFlowManageSer.findApplyDelayById(list.get(i));
					String stageName = ad.getDelayStage();
					if("apply_title".equals(stageName)){
						ApplyTitle applyTitle = secretaryFlowManageSer.findApplyTitleByStuId(ad.getStuId());
						if(applyTitle==null){
							ApplyTitle at = new ApplyTitle();
							at.setId(UuidHelper.getRandomUUID());
							at.setStuId(ad.getStuId());
							at.setTeaId("");
							at.setLimitTime(ad.getDelayTime());
							at.setCreateTime(new Date());
							at.setCreateUser(teacher.getTeaName());
							at.setTeaStatus("");
							at.setTeaIdea("");
							at.setLeaderIdea("");
							at.setLeaderStatus("");
							at.setManagerIdea("");
							at.setManagerStatus("");
							at.setNature("");
							at.setTitle("");
							at.setTitleForm("");
							at.setTitleReason("");
							at.setTitleSource("");
							secretaryFlowManageSer.addApplyTitle(at);
						}
						secretaryFlowManageSer.setApplyTitleLimitTime(ad.getDelayTime(), ad.getStuId());						
					}else if("task_book".equals(stageName)){
						TaskBook taskBook = secretaryFlowManageSer.findTaskBookByStuId(ad.getStuId());
						if(taskBook==null){
							TaskBook tb = new TaskBook();
							tb.setCreateTime(new Date());
							tb.setCreateUser(teacher.getTeaName());
							tb.setId(UuidHelper.getRandomUUID());
							tb.setLeaderStatus("");
							tb.setLimitTime(ad.getDelayTime());
							tb.setStuId(ad.getStuId());
							tb.setTaskBookPath("");
							tb.setTeaId("");
							tb.setTeaStatus("");
							secretaryFlowManageSer.addTaskBook(tb);
						}
						secretaryFlowManageSer.setTaskBookLimitTime(ad.getDelayTime(), ad.getStuId());
						
					}else if("opening_report".equals(stageName)){
						OpeningReport openingReport = secretaryFlowManageSer.findOpeningReportByStuId(ad.getStuId());
						if(openingReport==null){
							OpeningReport or = new OpeningReport();
							or.setCreateTime(new Date());
							or.setCreateUser(teacher.getTeaName());
							or.setId(UuidHelper.getRandomUUID());
							or.setLeaderStatus("");
							or.setLimitTime(ad.getDelayTime());
							or.setOpeningReportPath("");
							or.setStuId(ad.getStuId());
							or.setTeaId("");
							or.setTeaStatus("");
							secretaryFlowManageSer.addOpeningReport(or);
						}
						secretaryFlowManageSer.setOpeningReportLimitTime(ad.getDelayTime(), ad.getStuId());
						
					}else if("mid_check".equals(stageName)){
						MidCheck midCheck = secretaryFlowManageSer.findMidCheckByStuId(ad.getStuId());
						if(midCheck==null){
							MidCheck mc = new MidCheck();
							mc.setCreateTime(new Date());
							mc.setCreateUser(teacher.getTeaName());
							mc.setId(UuidHelper.getRandomUUID());
							mc.setLeaderStatus("");
							mc.setLimitTime(ad.getDelayTime());
							mc.setMidCheckPath("");
							mc.setStuId(ad.getStuId());
							mc.setTeaId("");
							mc.setTeaStatus("");
							secretaryFlowManageSer.addMidCheck(mc);
						}
						secretaryFlowManageSer.setMidCheckLimitTime(ad.getDelayTime(), ad.getStuId());
						
					}else if("first_paper".equals(stageName)){
						FirstPaper firstPaper = secretaryFlowManageSer.findFirstPaperByStuId(ad.getStuId());
						if(firstPaper==null){
							FirstPaper fp = new FirstPaper();
							fp.setCreateTime(new Date());
							fp.setCreateUser(teacher.getTeaName());
							fp.setFirstPaperPath("");
							fp.setId(UuidHelper.getRandomUUID());
							fp.setLeaderStatus("");
							fp.setLimitTime(ad.getDelayTime());
							fp.setStuId(ad.getStuId());
							fp.setTeaId("");
							fp.setTeaStatus("");
							secretaryFlowManageSer.addFirstPaper(fp);
						}
						secretaryFlowManageSer.setFirstPaperLimitTime(ad.getDelayTime(), ad.getStuId());
						
					}else if("final_paper".equals(stageName)){
						FinalPaper finalPaper = secretaryFlowManageSer.findFinalPaperByStuId(ad.getStuId());
						if(finalPaper==null){
							FinalPaper fp = new FinalPaper();
							fp.setCreateTime(new Date());
							fp.setCreateUser(teacher.getTeaName());
							fp.setFinalPaperPath("");
							fp.setId(UuidHelper.getRandomUUID());
							fp.setLeaderStatus("");
							fp.setLimitTime(ad.getDelayTime());
							fp.setStuId(ad.getStuId());
							fp.setTeaId("");
							fp.setTeaStatus("");
							secretaryFlowManageSer.addFinalPaper(fp);
						}
						secretaryFlowManageSer.setFinalPaperLimitTime(ad.getDelayTime(), ad.getStuId());
						
					}else{
						model.addAttribute("errorMsg", "系统异常！");
						return "error/error.jsp";
					}					
				}
			}
			secretaryFlowManageSer.setApplyDelay(list, idea, status);		
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		//添加待办事项，通知学生进度更新	
		for (int i = 0; i < list.size(); i++){
			ApplyDelay applyDelay = secretaryFlowManageSer.findApplyDelayById(list.get(i));
			List<BackLog> backLogList = publicSer.findBackLog(applyDelay.getStuId(),null,"student");
			Boolean bool = false;
			if(!backLogList.isEmpty()){
				for(int q=0;q<backLogList.size();q++){
					if(backLogList.get(q).getBackLog().equals("reDelayApply")){
						int m = publicSer.updateBackLogNumById(backLogList.get(q).getId(),"add");
						bool=true;						
						break;
					}
				}		
			}
			if(!bool){		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setStuId(applyDelay.getStuId());
				backLog.setBackLog("reDelayApply");
				backLog.setStuStatus("0");
				backLog.setCreateTime(new Date());
				int b = publicSer.insertBackLog(backLog);
				int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
			}
		}
		model.addAttribute("page", page);
		//删除待办事项记录
		List<BackLog> backLogs = publicSer.findBackLog(null,null,"manager");
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("delayApply")){
					int i = list.size();
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
				
		return findSpecial(stuNum, stuName, zdTeaName, status, model, page, request, response);
	}
	
	/**
	 * @Title: updateNotice
	 * @Description: 公告置顶/删除
	 * @param model
	 * @param map
	 * @param Status
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/updateNotice.shtm")
	public String updateNotice(Model model,Map<String, Object> map,String id,String top,String remove,HttpServletRequest request,HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isNotBlank(remove)){//删除
			try{
				PublicNotice publicNotice = secretaryFlowManageSer.findNoticeById(id);
				if(StringUtils.isNotBlank(publicNotice.getFilePath()))
				{
					File delfile = new File(publicNotice.getFilePath());
					if (!delfile.exists()) {  // 不存在返回 false  
						model.addAttribute("errorMsg", "服务器中未查询到附件，已删除记录");
					}else{  	        
				        if (delfile.isFile() && delfile.exists())// 判断是否为文件  
				        	delfile.delete();// 删除公告附件  				         
				      }
				}
			int i = secretaryFlowManageSer.deleteNoticeById(id);
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		if(StringUtils.isNotBlank(top)){//置顶/取消置顶
			try{
				int j = secretaryFlowManageSer.updateNoticeById(top,id);
			}catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return "redirect:noticeList.shtm";
	}
	

	/**
	 * @Title: uploadNotice
	 * @Description: 上传公告及附件
	 * @param model
	 * @param map
	 * @param noticeTitle
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/uploadNotice.shtm")
	public String uploadNotice(Model model,Map<String, Object> map,String content,String upTable,String upPaper, String noticeTitle,HttpServletRequest request,HttpServletResponse response)
	{	
		if(StringUtils.isNotBlank(noticeTitle))
		{
			if(StringUtils.isNotBlank(upTable))
			{
				MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile file = multipartRequest.getFile("upfile");
				if(!file.isEmpty())
				{ 	  
					 //文件服务器存储地址
					 String rootFile = SysConfig.getValue("term");//当前届文件夹
					 String rootPath = request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile;//盘符及根目录
		             File dir = new File(rootPath + File.separator +"表格模板");
		             if (!dir.exists())//若地址文件夹不存在,创建文件夹
		                 dir.mkdirs();
		             //写文件到服务器
		             String filePath = dir.getAbsolutePath() + File.separator +file.getOriginalFilename();
		             File serverFile = new File(filePath);
		             try {
						file.transferTo(serverFile);
					} catch (IllegalStateException e)
					{					
						e.printStackTrace();
					} catch (IOException e)
					{						
						e.printStackTrace();
					}	
		            //将数据存入publicNotice对象
		            PublicNotice publicNotice = new PublicNotice();
		            publicNotice.setId(UuidHelper.getRandomUUID());//随机ID
		            publicNotice.setTitle(noticeTitle);//公告标题
		            publicNotice.setNoticeType("table");//公告类型
		            publicNotice.setRealName(file.getOriginalFilename());//附件真实名称
		            publicNotice.setFilePath(filePath);//附件服务器地址
		            publicNotice.setTop("0");//是否置顶(0否1是)
		            publicNotice.setCreateTime(new Date());//创建时间
		            //添加记录到数据库            
		            try{
		 				int j = secretaryFlowManageSer.insertNotice(publicNotice);
		 			}catch (Exception e) {
		 				e.printStackTrace();
		 			}		          
		     		return "redirect:noticeList.shtm";
		       }          
			}
			if(StringUtils.isNotBlank(upPaper))
			{
				MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile file = multipartRequest.getFile("upfile");
				if(!file.isEmpty())
				{ 	  
					//文件服务器存储地址
					 String rootFile = SysConfig.getValue("term");//当前届文件夹
					 String rootPath = request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile;//盘符及根目录
		             File dir = new File(rootPath + File.separator +"优秀论文");
		             if (!dir.exists())//若地址文件夹不存在,创建文件夹
		                 dir.mkdirs();
		             //写文件到服务器
		             String filePath = dir.getAbsolutePath() + File.separator +file.getOriginalFilename();
		             File serverFile = new File(filePath);
		             try {
						file.transferTo(serverFile);
					} catch (IllegalStateException e) {					
						e.printStackTrace();
					} catch (IOException e) {						
						e.printStackTrace();
					}	
	                //将数据存入publicNotice对象
		            PublicNotice publicNotice = new PublicNotice();
		            publicNotice.setId(UuidHelper.getRandomUUID());//随机ID
		            publicNotice.setTitle(noticeTitle);//公告标题
		            publicNotice.setNoticeType("paper");//公告类型
		            publicNotice.setRealName(file.getOriginalFilename());//附件真实名称
		            publicNotice.setFilePath(filePath);//附件服务器地址
		            publicNotice.setTop("0");//是否置顶(0否1是)
		            publicNotice.setCreateTime(new Date());//创建时间
		            //添加记录到数据库            
		            try{
		 				int j = secretaryFlowManageSer.insertNotice(publicNotice);
		 			}catch (Exception e){
		 				e.printStackTrace();
		 			} 
		     		return "redirect:noticeList.shtm";
		       }          
			}
			if(StringUtils.isNotBlank(content))
			{
				MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile file = multipartRequest.getFile("upfile");
				String filePath=null;
				if(!file.isEmpty())
				{ 	  
					 //文件服务器存储地址
					 String rootFile = SysConfig.getValue("term");//当前届文件夹
					 String rootPath = request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile;//盘符及根目录
		             File dir = new File(rootPath + File.separator +"公告附件");
		             if (!dir.exists())//若地址文件夹不存在,创建文件夹
		                 dir.mkdirs();
		             //写文件到服务器
		             filePath = dir.getAbsolutePath() + File.separator +file.getOriginalFilename();
		             File serverFile = new File(filePath);
		             try {
						file.transferTo(serverFile);
					} catch (IllegalStateException e) {					
						e.printStackTrace();
					} catch (IOException e) {						
						e.printStackTrace();
					}
				}
	                //将数据存入publicNotice对象
		            PublicNotice publicNotice = new PublicNotice();
		            publicNotice.setId(UuidHelper.getRandomUUID());//随机ID
		            publicNotice.setTitle(noticeTitle);//公告标题
		            publicNotice.setNoticeType("notice");//公告类型
		            publicNotice.setRealName(file.getOriginalFilename());//附件真实名称
		            publicNotice.setFilePath(filePath);//附件服务器地址
		            publicNotice.setContent(content);//公告内容
		            publicNotice.setTop("0");//是否置顶(0否1是)
		            publicNotice.setCreateTime(new Date());//创建时间
		            //添加记录到数据库            
		            try{
		 				int j = secretaryFlowManageSer.insertNotice(publicNotice);
		 			}catch (Exception e){
		 				e.printStackTrace();
		 			} 
		            return "redirect:noticeList.shtm";         		
			}else{
			   model.addAttribute("errorMsg", "请设置公告内容！");
			   return "Secretary/addNotice.jsp";
			}
		}else{
			model.addAttribute("errorMsg", "请设置公告标题！");
		}
		return "forward:noticeList.shtm";
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
	 * @Title: exportGradeAllEvaluate
	 * @Description: 导出成绩总评Excel
	 * @param page
	 * @param model
	 * @param request
	 * @param response void 
	 * @throws
	 */
	@RequestMapping("/exportGradeAllEvaluate.shtm")
	public void exportGradeAllEvaluate(HttpServletRequest request, HttpServletResponse response){
				
		List<GradeAll> list = new ArrayList<GradeAll>();
		try {
			list = secretaryFlowManageSer.findGradeAllEvaluate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb = writeOrderInfoExcelOutputStreamForGradeAllEvaluate(list,response);
		String filename = SysConfig.getValue("term")+"成绩总评"+".xls";
		downloadExcel(wb, filename,request, response);

	}
	
	    // 导出成绩总评信息 创建工作量excel文件
	  	private HSSFWorkbook writeOrderInfoExcelOutputStreamForGradeAllEvaluate(
	  			List<GradeAll> list, HttpServletResponse response) {
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
	  				sheet.setDefaultColumnWidth((short) 9);
	  				HSSFRow rowHead = sheet.createRow(0);

	  				rowHead.createCell((short) 0).setCellValue(
	  						new HSSFRichTextString("专业名称"));	  				
	  				rowHead.createCell((short) 1).setCellValue(
	  						new HSSFRichTextString(">90分"));
	  				rowHead.createCell((short) 2).setCellValue(
	  						new HSSFRichTextString("80-89分"));
	  				rowHead.createCell((short) 3).setCellValue(
	  						new HSSFRichTextString("70-79分"));
	  				rowHead.createCell((short) 4).setCellValue(
	  						new HSSFRichTextString("60-69分"));
	  				rowHead.createCell((short) 5).setCellValue(
	  						new HSSFRichTextString("<60分"));	
	  				rowHead.createCell((short) 6).setCellValue(
	  						new HSSFRichTextString("专业学生数"));
	  				rowHead.createCell((short) 7).setCellValue(
	  						new HSSFRichTextString("有效学生数"));
	  				// 10001: 0-4999, 5000-9999, 10000-10001
	  				for (int i = n * sheetSize,j=0; j< list.size()
	  						&& i < (n + 1) * sheetSize; j++,i++) {
	  					HSSFRow rowBody = sheet.createRow(i% sheetSize + 1);
	  					GradeAll gradeAll = list.get(j);
	  					rowBody.createCell((short) 0).setCellValue(
	  							new HSSFRichTextString(gradeAll.getMajorName()));	  					
	  					rowBody.createCell((short) 1).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getExcellentNum())));
	  					rowBody.createCell((short) 2).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getWellNum())));
	  					rowBody.createCell((short) 3).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getMediumNum())));
	  					rowBody.createCell((short) 4).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getPassNum())));	  
	  					rowBody.createCell((short) 5).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getNotPassNum())));	
	  					rowBody.createCell((short) 6).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getMajorStuNum())));				
	  					rowBody.createCell((short) 7).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getMajorValidStuNum())));
	  					HSSFRow rowBodyRates = null;	  			
	  					rowBodyRates = sheet.createRow(i % sheetSize + 2);	
	  					i++;
	  					rowBodyRates.createCell((short) 0).setCellValue(
	  							new HSSFRichTextString("百分比"));	  					
	  					rowBodyRates.createCell((short) 1).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getExcellentRates())));
	  					rowBodyRates.createCell((short) 2).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getWellRates())));
	  					rowBodyRates.createCell((short) 3).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getMediumRates())));
	  					rowBodyRates.createCell((short) 4).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getPassRates())));	  
	  					rowBodyRates.createCell((short) 5).setCellValue(
	  							new HSSFRichTextString(getNullString(gradeAll.getNotPassRates())));	
	  				}
	  			}
	  		}
	  		return wb;
	  	}
	  	  
	  	
	
}
