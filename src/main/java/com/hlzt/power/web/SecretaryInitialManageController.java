	package com.hlzt.power.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.chainsaw.Main;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlzt.commons.helper.SysConfig;
import com.hlzt.commons.helper.UuidHelper;
import com.hlzt.commons.model.BasePage;
import com.hlzt.commons.model.GlobalFinal;
import com.hlzt.power.model.ClassName;
import com.hlzt.power.model.GradeWeight;
import com.hlzt.power.model.Major;
import com.hlzt.power.model.StagePlan;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.TitleForm;
import com.hlzt.power.model.TitleNature;
import com.hlzt.power.model.User;
import com.hlzt.power.model.ZhiCheng;
import com.hlzt.power.service.SecretaryInitialManageSer;

@Controller
@RequestMapping("/secretary")
public class SecretaryInitialManageController {
	@Resource
	private SecretaryInitialManageSer simSer;
	
	
	/**
	 * 查看当前届设置
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findNowTerm.shtm")
	public String findNowTerm(Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		model.addAttribute("term", SysConfig.getValue("term"));
		return "/Secretary/termSet.jsp";
	}
	
	/**
	 * 当前届设置
	 * @param period
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findNowTerm_setNowTerm.shtm")
	public String setNowTerm(String term, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			model.addAttribute("errorMsg","系统异常！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(term)){
			model.addAttribute("errorMsg","请输入当前届！");
			return findNowTerm(model, request, response);
		}
		Pattern p = Pattern.compile("^[2]\\d{3}$");
		Matcher m = p.matcher(term);
		if(m.matches()==false){
			
			model.addAttribute("errorMsg","请输入正确的年份！");
			return findNowTerm(model, request, response);
		}
		SysConfig.updateProperties("term", term);
		SysConfig.save();
		model.addAttribute("term", SysConfig.getValue("term"));
		model.addAttribute("successMsg","设置成功！");
		return findNowTerm(model, request, response);
			
	}

	/**
	 * 跳转到增加专业的页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findMajor_jumpAddMajor.shtm")
	public String jumpAddMajor(Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		return "/Secretary/addMajor.jsp";
	}
	
	/**
	 * 增加专业
	 * @param majorName
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findMajor_addMajor.shtm")
	public String addMajor(String majorName, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		Major major = new Major();
		if(StringUtils.isBlank(majorName)){
			model.addAttribute("errorMsg", "请输入专业！");
			return jumpAddMajor(model, request, response);
		}
		int total = simSer.findMajorByName(majorName);
		if(total!=0){
			model.addAttribute("errorMsg", "该专业已存在！");
			return jumpAddMajor(model, request, response);
		}
		major.setId(UuidHelper.getRandomUUID());
		major.setMajorName(majorName);
		major.setCreateUser(teacher.getTeaName());
		major.setCreateTime(new Date());
		int i = 0;
		try {
			i = simSer.addMajor(major);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return findMajor(model, request, response);
		}
		GradeWeight gw = new GradeWeight();
		gw.setId(UuidHelper.getRandomUUID());
		gw.setMajor(major.getMajorName());
		gw.setPyPingfen((float) 0.25);
		gw.setZdPingfen((float) 0.25);
		gw.setDbPingfen((float) 0.5);
		gw.setCreateUser(teacher.getTeaName());
		gw.setCreateTime(new Date());
		simSer.addGradeWeighr(gw);
		model.addAttribute("successMsg", "增加成功！");
		return findMajor(model, request, response);
	}
	
	
	/**
	 * 查询专业
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findMajor.shtm")
	public String findMajor(Model model, HttpServletRequest request, 
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		List<Major> list = new ArrayList<Major>();
		try {
			list = simSer.findMajor();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("list", list);
		return "/Secretary/majorSet.jsp";
	}
	
	/**
	 * 删除专业
	 * @param ids
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findMajor_deleteMajor.shtm")
	public String deleteMajor(String[] ids, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(ids==null){
			model.addAttribute("errorMsg", "您未选择数据！");
			return findMajor(model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = simSer.deleteMajor(list);	
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return findMajor(model, request, response);
		}	
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return findMajor(model, request, response);
		}
		simSer.deleteGradeWeighr(list);
		model.addAttribute("successMsg", "删除成功！");
		return findMajor(model, request, response);
	}
	
	/**
	 * 跳转到增加班级的页面
	 * @param majorId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findMajor_jumpAddClass.shtm")
	public String jumpAddClass(String majorId, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(majorId)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("majorId", majorId);
		return "/Secretary/addClass.jsp";
	}
	
	/**
	 * 增加班级
	 * @param majorId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findMajor_addclass.shtm")
	public String addClass(String majorId, String cName, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		User loginUser = (User) request.getSession().getAttribute("user");
		ClassName className = new ClassName();
		if(StringUtils.isBlank(majorId)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(cName)){
			model.addAttribute("errorMsg", "请输入班级名！");
			return jumpAddClass(majorId, model, request, response);
		}
		int total = simSer.findClassNum(cName);
		if(total!=0){
			model.addAttribute("errorMsg", "该班级已存在！");
			return jumpAddClass(majorId, model, request, response);
		}
		className.setId(UuidHelper.getRandomUUID());
		className.setcName(cName);
		className.setMajor(majorId);
		className.setCreateTime(new Date());
		className.setCreateUser(loginUser.getRealName());
		
		int i = 0;
		try {
			i = simSer.addClass(className);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}	
		if(i!=1){
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return jumpAddClass(majorId, model, request, response);
		}
		model.addAttribute("successMsg", "增加成功！");
		return findClass(majorId, model, request, response);
	}
	
	/**
	 * 删除班级
	 * @param majorId
	 * @param ids
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findMajor_deleteClass.shtm")
	public String deleteClass(String majorId, String[] ids, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(ids.length==0){
			model.addAttribute("errorMsg", "您未选择数据！");
			return findClass(majorId, model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = simSer.deleteClass(list);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}	
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return findClass(majorId, model, request, response);
		}
		model.addAttribute("successMsg", "删除成功！");
		return findClass(majorId, model, request, response);
	}
	
	/**
	 * 查询班级
	 * @param majorId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findMajor_findClass.shtm")
	public String findClass(String majorId, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(majorId)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		List<ClassName> list = new ArrayList<ClassName>();
		try {
			list = simSer.findClass(majorId);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		model.addAttribute("majorId", majorId);
		model.addAttribute("list", list);
		
		return "/Secretary/classSet.jsp";
	}
	
	/**
	 * 查询课题性质
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTitleNature.shtm")
	public String findTitleNature(Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		List<TitleNature> list = new ArrayList<TitleNature>();
		try {
			list = simSer.findTitleNature(null);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		model.addAttribute("list", list);
		return "/Secretary/titleNature.jsp";
	}
	
	/**
	 * 跳转到增加课题性质的页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findTitleNature_setTitleNature.shtm")
	public String setTitleNature(Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		return "/Secretary/addNature.jsp";
	}
	
	/**
	 * 增加课题性质
	 * @param Major
	 * @param natureName
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTitleNature_addTitleNature.shtm")
	public String addTitleNature(String major, String natureName, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		User loginUser = (User) request.getSession().getAttribute("user");
		if(StringUtils.isBlank(natureName)){
			model.addAttribute("errorMsg", "请输入课题性质！");
			return "/Secretary/addNature.jsp";
		}
		TitleNature tn = new TitleNature();
		tn.setId(UuidHelper.getRandomUUID());
		tn.setNatureName(natureName);
		tn.setCreateUser(loginUser.getRealName());
		tn.setCreateTime(new Date());
		int i = 0;
		try {
			i = simSer.addTitleNature(tn);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return findTitleNature(model, request, response);
		}
		model.addAttribute("successMsg", "添加成功！");
		return findTitleNature(model, request, response);
	}
	
	/**
	 * 删除课题性质
	 * @param ids
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findTitleNature_deleteTitleNature.shtm")
	public String deleteTitleNature(String[] ids, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(ids==null){
			model.addAttribute("errorMsg", "您未选择数据！");
			return findTitleNature(model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = simSer.deleteTitleNature(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return findTitleNature(model, request, response);
		}
		
		return findTitleNature(model, request, response);
	}
	
	/**
	 * 查询完成形式
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findTitleForm.shtm")
	public String findTitleForm(Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		List<TitleForm> list = new ArrayList<TitleForm>();
		try {
			list = simSer.findTitleForm(null);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		model.addAttribute("list", list);
		return "Secretary/titleForm.jsp";
	}
	
	/**
	 * 跳转到增加完成形式的页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findTitleForm_setTitleForm.shtm")
	public String setTitleFrom(Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		return "/Secretary/addForm.jsp";
	}
	
	/**
	 * 增加完成形式
	 * @param Major
	 * @param natureName
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTitleForm_addTitleForm.shtm")
	public String addTitleForm(String major, String formName, BasePage page,
			Model model, HttpServletRequest request, HttpServletResponse response){
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
		if(StringUtils.isBlank(formName)){
			model.addAttribute("errorMsg", "请输入课题性质！");
			return "/Secretary/addForm.jsp";
		}
		TitleForm tf = new TitleForm();
		tf.setId(UuidHelper.getRandomUUID());
		tf.setFormName(formName);
		tf.setCreateUser(loginUser.getRealName());
		tf.setCreateTime(new Date());
		int i = 0;
		try {
			i = simSer.addTitleForm(tf);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return findTitleForm(model, request, response);
		}
		model.addAttribute("successMsg", "添加成功！");
		return findTitleForm(model, request, response);
	}
	
	/**
	 * 删除完成形式
	 * @param ids
	 * @param page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findTitleForm_deleteTitleForm.shtm")
	public String deleteTitleFrorm(String[] ids, BasePage page, Model model,
			HttpServletRequest request, HttpServletResponse response){
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
			return findTitleNature(model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = simSer.deleteTitleForm(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return findTitleNature(model, request, response);
		}
		
		return findTitleForm(model, request, response);
	}
	
	/**
	 * 设置阶段时间安排
	 * @param stageName
	 * @param date
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findStagePlan_setStageDate.shtm")
	public String setStageDate(String stageName, String date, Model model,
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")){
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		User loginUser = (User) request.getSession().getAttribute("user");
		if(StringUtils.isBlank(stageName)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(date)){
			model.addAttribute("errorMsg", "请选择时间！");
			return findStagePlan(model, request, response);
		}
		String[] str = date.split("-");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = sdf.parse(str[0].trim());
			endTime = sdf.parse(str[1].trim());
		} catch (ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "时间输入有误！");
			return findStagePlan(model, request, response);
		}
		//查询数据库中是否已存在阶段安排
		StagePlan stagePlan = null;
		try{
			stagePlan = simSer.findStagePlan(stageName);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//若存在，update
		if(stagePlan!=null){			
			try {
				int i = simSer.setStageTime(stageName, startTime, endTime);
				if(i==0){
					model.addAttribute("errorMsg", "系统异常！");
					return findStagePlan(model, request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMsg", "系统异常！");
				return findStagePlan(model, request, response);
			}
		}else{
			try{
				stagePlan = new StagePlan();
				stagePlan.setId(UuidHelper.getRandomUUID());
				stagePlan.setStageName(stageName);
				stagePlan.setStartTime(startTime);
				stagePlan.setEndTime(endTime);
				int i = simSer.inserStageTime(stagePlan);
				if(i==0){
					model.addAttribute("errorMsg", "系统异常！");
					return findStagePlan(model, request, response);
				}
			}catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMsg", "系统异常！");
				return findStagePlan(model, request, response);
			}
		}			
		return findStagePlan(model, request, response);
	}
	
	/**
	 * 查询阶段时间安排
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findStagePlan.shtm")
	public String findStagePlan(Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		try {
			StagePlan applyTea = simSer.findStagePlan("apply_teacher");
			model.addAttribute("applyTea", applyTea);
			StagePlan applyTitle = simSer.findStagePlan("apply_title");
			model.addAttribute("applyTitle", applyTitle);
			StagePlan taskBook = simSer.findStagePlan("task_book");
			model.addAttribute("taskBook", taskBook);
			StagePlan openingReport = simSer.findStagePlan("opening_report");
			model.addAttribute("openingReport", openingReport);
			StagePlan midCheck = simSer.findStagePlan("mid_check");
			model.addAttribute("midCheck", midCheck);
			StagePlan firstPaper = simSer.findStagePlan("first_paper");
			model.addAttribute("firstPaper", firstPaper);
			StagePlan finalPaper = simSer.findStagePlan("final_paper");
			model.addAttribute("finalPaper", finalPaper);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "/error/error.jsp";
		}
		
		return "/Secretary/closingDate.jsp";
	}
	
	/**
	 * 查询权重评分
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findGradeWeight.shtm")
	public String findGradeWeight(Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		List<GradeWeight> list = null;
		try {
			list = simSer.findGradeWeight();			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		model.addAttribute("list", list);
		
		return "/Secretary/weightSet.jsp";
	}
	
	/**
	 * 给专业设置评分权重
	 * @param zdGrade
	 * @param pyGrade
	 * @param daGrade
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findGradeWeight_setGradeWeight.shtm")
	public String setGradeWeight(String flag, String[] ids, String zdPingfen, String pyPingfen, String dbPingfen, 
			Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		List<String> list = null;
		if("1".equals(flag)){
			if(ids==null){
				model.addAttribute("errorMsg", "请选择数据！");
				return findGradeWeight(model, request, response);
			}			
			list = Arrays.asList(ids);
		}
		if("0".equals(flag)){
			list = null;
		}
		if(StringUtils.isBlank(dbPingfen)||StringUtils.isBlank(pyPingfen)||StringUtils.isBlank(zdPingfen)){
			model.addAttribute("errorMsg", "请输入各项评分所占比重！");
			return findGradeWeight(model, request, response);
		}
		Pattern p = Pattern.compile("1|0|0[.]([0-9]+)");
		Matcher m1 = p.matcher(zdPingfen);
		Matcher m2 = p.matcher(pyPingfen);
		Matcher m3 = p.matcher(dbPingfen);
		if(!m1.matches()||!m2.matches()||!m3.matches()){
			model.addAttribute("errorMsg", "请输入大于0小于1的数字！");
			return findGradeWeight(model, request, response);
		}
		
		int i = 0;
		try {
			float zd = Float.parseFloat(zdPingfen);
			float py = Float.parseFloat(pyPingfen);
			float db = Float.parseFloat(dbPingfen);
			if(zd+py+db!=1){
				model.addAttribute("errorMsg", "各项评分所占比重必须等于1！");
				return findGradeWeight(model, request, response);
			}
			i = simSer.setGradeWeight(list, zd, py, db);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			findGradeWeight(model, request, response);
		}
		
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！");
			return findGradeWeight(model, request, response);
		}
		model.addAttribute("successMsg", "设置成功！");
		return findGradeWeight(model, request, response);
	}
	
	/**
	 * 取消设置评分权重
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findGradeWeight_cancelSetGradeWeight.shtm")
	public String cancelSetGradeWeight(String[] ids, Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(ids==null){
			model.addAttribute("errorMsg", "请选择数据！");
			return findGradeWeight(model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		int i = simSer.cancelSetGradeWeight(list);
		if(i==0){
			model.addAttribute("errorMsg", "");
			return "";
		}
		
		return findGradeWeight(model, request, response);
	}
	
	/**
	 * 系统维护开关
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateSystem.shtm")
	public String updateSystem(Model model, 
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		String flag = SysConfig.getValue("systemUpdate");
		model.addAttribute("flag", flag);
		return "/Secretary/closeSystem.jsp";
	}
	
	/**
	 * 设置系统维护开关
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/setUpdateSystem.shtm")
	public String setUpdateSystem(String flag, Model model, 
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(flag)){
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		SysConfig.updateProperties("systemUpdate", flag);
		SysConfig.save();
		if("0".equals(flag)){
			model.addAttribute("successMsg", "系统维护已关闭！");			
		}
		if("1".equals(flag)){
			model.addAttribute("successMsg", "系统维护已开启！");			
		}
		return updateSystem(model, request, response);
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dataSave.shtm")
	public String dataSave(Model model, 
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		return "/Secretary/fileData.jsp";
	}
	
	/**
	 * 本届数据归档
	 * @param year
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dataSave_setDataSave.shtm")
	public String setDataSave(String year, Model model, 
			HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(StringUtils.isBlank(year)){
			model.addAttribute("errorMsg", "请输入年份！");
			return "/Secretary/fileData.jsp";
		}
		String term = SysConfig.getValue("term");
		if(!year.equals(term)){
			model.addAttribute("errorMsg", "年份输入有误！");
			return "/Secretary/fileData.jsp";
		}
		int i = 0;
		try {
			simSer.dataSave();
			SysConfig.updateProperties("term", "");
			SysConfig.save();
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "/Secretary/fileData.jsp";
		}
		model.addAttribute("successMsg", "本届数据归档成功！");
		return "/Secretary/fileData.jsp";
	}
	

	/**
	 * 跳转到增加职称的页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findZhiCheng_jumpAddZhiCheng.shtm")
	public String jumpAddZhiCheng(Model model, HttpServletRequest request, HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		return "/Secretary/addZhiCheng.jsp";
	}
	
	/**
	 * 增加专业
	 * @param majorName
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findZhiCheng_addZhiCheng.shtm")
	public String addZhiCheng(String zhiChengName, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		ZhiCheng zhiCheng = new ZhiCheng() ;
		if(StringUtils.isBlank(zhiChengName)){
			model.addAttribute("errorMsg", "请输入职称！");
			return jumpAddZhiCheng(model, request, response);
		}
		zhiCheng.setId(UuidHelper.getRandomUUID());
		zhiCheng.setZhiCheng(zhiChengName);
		zhiCheng.setCreateUser(teacher.getTeaName());
		zhiCheng.setCreateTime(new Date());
		int i = 0;
		try {
			i = simSer.addZhiCheng(zhiCheng);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return findZhiCheng(model, request, response);
		}
		model.addAttribute("successMsg", "增加成功！");
		return findZhiCheng(model, request, response);
	}
	
	
	/**
	 * 查询专业
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findZhiCheng.shtm")
	public String findZhiCheng(Model model, HttpServletRequest request, 
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		List<ZhiCheng> list = new ArrayList<ZhiCheng>();
		try {
			list = simSer.findZhiCheng();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("list", list);
		return "/Secretary/zhiChengSet.jsp";
	}
	
	/**
	 * 删除专业
	 * @param ids
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findZhiCheng_deleteZhiCheng.shtm")
	public String deleteZhiCheng(String[] ids, Model model, HttpServletRequest request,
			HttpServletResponse response){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("manager")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if(ids==null){
			model.addAttribute("errorMsg", "您未选择数据！");
			return findZhiCheng(model, request, response);
		}
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = simSer.deleteZhiCheng(list);	
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return findZhiCheng(model, request, response);
		}	
		if(i==0){
			model.addAttribute("errorMsg", "系统异常！操作失败！");
			return findZhiCheng(model, request, response);
		}
		model.addAttribute("successMsg", "删除成功！");
		return findZhiCheng(model, request, response);
	}
	
	
}
