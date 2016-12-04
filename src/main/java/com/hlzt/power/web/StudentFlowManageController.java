package com.hlzt.power.web;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor.BLACK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlzt.commons.helper.UuidHelper;
import com.hlzt.commons.model.BasePage;
import com.hlzt.power.model.ApplyDelay;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.BackLog;
import com.hlzt.power.model.DbGroup;
import com.hlzt.power.model.Grade;
import com.hlzt.power.model.GradeWeight;
import com.hlzt.power.model.StagePlan;
import com.hlzt.power.model.StuGraCollect;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.TeacherTitle;
import com.hlzt.power.model.TitleForm;
import com.hlzt.power.model.TitleNature;
import com.hlzt.power.service.PublicSer;
import com.hlzt.power.service.StudentFlowManageSer;
import com.mysql.fabric.xmlrpc.base.Array;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 学生流程管理
 * @author user
 *
 */
@Controller
@RequestMapping("/student")
public class StudentFlowManageController {
	
	@Autowired
	private StudentFlowManageSer studentFlowManageSer;
	@Autowired
	private PublicSer publiSer;
	/**
	 * @Title: applyTitle
	 * @Description: 申报课题初始化
	 * @param model
	 * @param map
	 * @param page
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/initApplyTitle.shtm")
    public String initApplyTitle(Model model,Map<String, Object> map,String teaName,BasePage page,
    		HttpServletRequest request, HttpServletResponse response){
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Student student = (Student) request.getSession().getAttribute("student");
    	if(StringUtils.isBlank(student.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}		
		//获取教师基本信息(有教师课题的教师)
    	if(StringUtils.isNotBlank(teaName)){//根据姓名查询
    		findTeaInfo(model,map,teaName, page,request, response);
    	}else if(!StringUtils.isNotBlank(teaName)){//查询所有教师
    		findTeaInfo(model,map,"", page,request, response);
    	}
		//获取当前学生课题申报状态 
    	ApplyTitle applyTitle = null;
    	List<BackLog> backLogs = null;
    	try{
    		applyTitle = studentFlowManageSer.searchApplyTitleById(student.getUserId());
    	}catch (Exception e){
			e.printStackTrace();
		}		
		Teacher teacher = new Teacher();
		//返回当前用户对象
		if(applyTitle!=null){			
			//查询已选教师基本信息
			try{		
				teacher = studentFlowManageSer.findTeacherInfoByTeaId(applyTitle.getTeaId());
				//查询是否有关于课题申请更新的待办事项提示,如果有,删除此条提示
				backLogs = publiSer.findBackLog(student.getUserId(), student.getMajor(),"student");
				if(!backLogs.isEmpty()){
					for(int i=0;i<backLogs.size();i++){
						BackLog backLog = backLogs.get(i);
						if(backLog.getBackLog().equals("reStuApplyTitle")){
							int j = publiSer.removeBackLog(backLog.getId());
							if(j==0){
								model.addAttribute("errorMsg","系统错误");
								return "error/error.jsp";
							}
						}
					}
				}
				model.addAttribute("teacher",teacher);
				model.addAttribute("applyTitle",applyTitle);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return "Student/applyForTeacher.jsp";
    }
	
	/**
	 * @Title: findTeaInfo
	 * @Description: 查询教师信息
	 * @param model
	 * @param map
	 * @param teaName
	 * @param page
	 * @param request
	 * @param response void 
	 * @throws
	 */
	public void findTeaInfo(Model model, Map<String, Object> map,String teaName,BasePage page,
			HttpServletRequest request, HttpServletResponse response)
	{
		/**
		 * 学生可以跨专业申报指导教师
		 * 查询拥有教师课题的教师信息
		 */
		page.setPageNo(page.getPageNo());
		Student student = (Student) request.getSession().getAttribute("student");
		if(StringUtils.isNotBlank(student.getMajor())){
			if(StringUtils.isNotBlank(teaName)){
				map.put("teaName", teaName);
				model.addAttribute("teaName", teaName);
			}
			try{
				page =studentFlowManageSer.findTeacherInfoHaveTitle(map,page);
				model.addAttribute("page", page);
			}catch (Exception e){
				e.printStackTrace();
			}
		}else{
			model.addAttribute("errorMsg","请完善专业信息");
		}
		
	}
	
	/**
	 * @throws Exception 
	 * @Title: initApplyTeaProject
	 * @Description: 查看该教师课题
	 * @param model
	 * @param map
	 * @param teaName
	 * @param page
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/initApplyTitle_ApplyTeaProject.shtm")
	public String initApplyTeaProject(Model model, Map<String, Object> map,String teaName,String teaId, BasePage page,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String Source = (String)request.getAttribute("Source");
		//申请教师课题如果出现错误,传过来Source参数标识,teaId通过request传递
		if(StringUtils.isNotBlank(Source)){
			if(Source.equals("applyError")){
				teaId=(String)request.getAttribute("teaId");
			}
		}										
		if(StringUtils.isNotBlank(teaId)){//根据姓名查询
    		findTeaTitle(model, map,teaId, page, request, response);
    	}else if(!StringUtils.isNotBlank(teaId)){//查询全部教师课题
    		findTeaTitle(model, map,"", page, request, response);
    	}
		return "Student/applyForTeaProject.jsp";
	}
	
	/**
	 * 查询教师课题
	 * @param model
	 * @param teaName
	 * @param request
	 * @param response
	 * @return
	 */
	public void findTeaTitle(Model model, Map<String, Object> map,String teaId, BasePage page,
			HttpServletRequest request, HttpServletResponse response){
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Student student = (Student) request.getSession().getAttribute("student");
		if(StringUtils.isNotBlank(student.getMajor())){
			if(StringUtils.isNotBlank(teaId)){
				map.put("teaId", teaId);
				model.addAttribute("teaId", teaId);
			}
			map.put("role", "student");//向后台传入执行查询的角色
			try{
				page = studentFlowManageSer.findTeaTitle(map, page);
				model.addAttribute("page", page);
			}catch (Exception e) {
				e.printStackTrace();
			}					
		}
	}
	
	/**
	 * @Title: titleInfo
	 * @Description: 查看/修改单一课题详细信息
	 * @param model
	 * @param id
	 * @param Source来源(0教师课题表,teacherTitle表;1已选课题表,applyTitle表)
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception String 
	 * @throws
	 */
	@RequestMapping("/initApplyTitle_title.shtm")
	public String titleInfo(Model model, String id,String Source,String readOnly,Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		TeacherTitle teaTitle=null;//教师课题(teacherTitle表)
		ApplyTitle applyTitle=null;//已选课题(applyTitle表)	
		try{
			if(Source.equals("0")){//查看教师课题信息
				 teaTitle =studentFlowManageSer.findTeaTitleById(id);
	        }else if(Source.equals("1")){//查看已选的课题
	        	applyTitle = studentFlowManageSer.searchApplyTitleById(id);
	        	if(readOnly.equals("0")){
	        		if(applyTitle.getTitleSource().equals("0")){//判断课题来源(0教师课题1自拟课题)    		
	        				model.addAttribute("errorMsg","不能修改教师课题");
	        				return "forward:ApplyTitleSelf.shtm";        			
	        		}else{
	        			if((!applyTitle.getTeaStatus().equals("0"))||(!applyTitle.getLeaderStatus().equals("0"))||(!applyTitle.getManagerStatus().equals("0"))){
	        				model.addAttribute("errorMsg","仅限修改审核中的课题");
	        				return "forward:ApplyTitleSelf.shtm";
	        			}
	        			//课题在审核中,判断是否在时间限制内
	        			Date nowDate = new Date();//当前时间	
	        			Date limitDate = applyTitle.getLimitTime();//特殊情况延期时间
	        			Date endDate = null;
	        			//查询阶段安排时间
	        			StagePlan stagePlan = new StagePlan();
	        			stagePlan =	studentFlowManageSer.findStagePlan("apply_title");
	        			if(stagePlan!=null){
	        				endDate = stagePlan.getEndTime();//阶段安排结束时间
	        			}else{
	        				model.addAttribute("errorMsg","未查询到阶段时间安排，系统错误");
	        				return "forward:ApplyTitleSelf.shtm";
	        			}
	        			//判断时间限制    
	        			if(nowDate.getTime()>endDate.getTime()){//比较结束时间
	        				if(limitDate!=null){//判断是否存在特殊延期时间
	        					if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
	        						model.addAttribute("errorMsg","课题申报延期已结束");
	        						return "forward:ApplyTitleSelf.shtm";
	        					}
	        				}else{//无,直接返回超时提示
	        					model.addAttribute("errorMsg","课题申报已结束,不能修改课题");
	        					return "forward:ApplyTitleSelf.shtm";
	        				}
	        			}
	        			//获取课题性质
	        			List<TitleNature> titleNatures = studentFlowManageSer.selectNature();
	        			//获取完成形式
	        			List<TitleForm> titleForms = studentFlowManageSer.selectForm();		
	        			model.addAttribute("titleNatures", titleNatures);
	        			model.addAttribute("titleForms", titleForms);
	        		}      		
	    		}
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
        if(teaTitle!=null){
        	model.addAttribute("Title", teaTitle);
        	model.addAttribute("teaTitle", "1");
        }else if(applyTitle!=null){
        	model.addAttribute("Title", applyTitle);
        	model.addAttribute("teaTitle", "0");
        }else{
        	model.addAttribute("errorMsg","系统错误");
			return "error/error.jsp";
        }
        if(readOnly.equals("0")){//是否为只读(0否1是)
        	return  "Public_Page/changeTitleInfo.jsp";//文本框可编辑
        }else if(readOnly.equals("1")){        	
        	return "Public_Page/titleInfo.jsp";//文本框不可编辑
        }
        model.addAttribute("errorMsg","系统错误");
		return "error/error.jsp";
	}
	
	/**
	 * 申请教师课题
	 * @param model
	 * @param teaTitleId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/initApplyTitle_ApplyTitle.shtm")
	public String ApplyTitle(Model model, String teaTitleId,Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response){
		//根据ID获得课题对象
		TeacherTitle teaTitle = null;
		try{
			teaTitle =studentFlowManageSer.findTeaTitleById(teaTitleId);
		}catch (Exception e) {
			e.printStackTrace();
		}		
		if(teaTitle==null){
			model.addAttribute("errorMsg","系统错误");
			return "error/error.jsp";
		}
		//将教师信息存入request中,出现错误,forward页面后能保持该教师信息的显示
		request.setAttribute("teaId",teaTitle.getTeacher().getUserId());//出现错误时,通过request将userId传递过去
		request.setAttribute("Source","applyError");//出现错误后,转发到其他页面用于标识来源
		if(!StringUtils.isNotBlank(teaTitleId)){
			model.addAttribute("errorMsg","请选择一个课题!");
			return "forward:initApplyTitle_ApplyTeaProject.shtm";
		}
		//获取时间
		Date nowDate = new Date();//当前时间	
		Date limitDate = null;//特殊情况延期时间
		Student student = (Student) request.getSession().getAttribute("student");
		//返回当前用户applyTitle对象
		ApplyTitle applyTitle = null;
		try{
			applyTitle = studentFlowManageSer.searchApplyTitleById(student.getUserId());
		}catch (Exception e){
			e.printStackTrace();
		}
		 
		//判断是否存在记录
		if(applyTitle!=null){
			//判断是否已选课题,教师审核状态是否为驳回
			if(StringUtils.isNotBlank(applyTitle.getTitle())&&(!applyTitle.getTeaStatus().equals("2"))){
				model.addAttribute("errorMsg","仅限申报一个课题");
				return "forward:initApplyTitle_ApplyTeaProject.shtm";
			}			
			//获取特殊情况延期时间
			limitDate = applyTitle.getLimitTime();
		}			
		//查询阶段安排时间
		StagePlan stagePlan = new StagePlan();
		try{
			stagePlan =	studentFlowManageSer.findStagePlan("apply_title");	
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(stagePlan!=null){					
			Date startDate = stagePlan.getStartTime();//阶段安排开始时间
			Date endDate = stagePlan.getEndTime();//阶段安排结束时间
			//判断时间限制
			if(nowDate.getTime()<startDate.getTime()){//比较开始时间
				model.addAttribute("errorMsg","课题申报尚未开始");
				return "forward:initApplyTitle.shtm";
			}
			if(nowDate.getTime()>endDate.getTime()){//比较结束时间
				if(limitDate!=null){//判断是否存在特殊延期时间
					if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
						model.addAttribute("errorMsg","课题申报延期已结束");
						return "forward:initApplyTitle.shtm";
					}
				}else{//无,直接返回超时提示
					model.addAttribute("errorMsg","课题申报已结束");
					return "forward:initApplyTitle.shtm";
				}
			}
		}else{
			model.addAttribute("errorMsg","未查询到阶段时间安排，系统错误");
			return "forward:ApplyTitleSelf.shtm";
		}
		if(applyTitle!=null){				
			if(applyTitle.getTitle().equals(teaTitle.getTitle())){
				model.addAttribute("errorMsg","该课题已被驳回,请选择其他课题");
				return "forward:initApplyTitle_ApplyTeaProject.shtm";
			}				
		}
		if("1".equals(teaTitle.getChoose())){
			model.addAttribute("errorMsg","课题已经被选用,请选择其他课题");
			return "forward:initApplyTitle_ApplyTeaProject.shtm";
		}
		map.put("teaTitleId", teaTitleId);
		try{
			int k = studentFlowManageSer.updateTitleChoose(map);//更改课题被选状态
			if(k==0){
				model.addAttribute("errorMsg","系统错误");
				return "error/error.jsp";
			}
		}catch (Exception e){
			e.printStackTrace();
		}		
		if(applyTitle!=null){//已申报过课题被驳回,重新申报在原有记录上修改
			map.put("teaId", teaTitle.getTeacherId());
			map.put("title", teaTitle.getTitle());
			map.put("nature", teaTitle.getNature());
			map.put("titleForm",teaTitle.getTitleForm());
			map.put("titleSource", "0");
			map.put("titleReason", teaTitle.getTitleReason());
			map.put("teaStatus", "0");
			map.put("leaderStatus", "1");
			map.put("managerStatus", "1");
			map.put("createTime", nowDate);
			try{							
				//更新apply_title表
				int i =studentFlowManageSer.updateApplyTitleById(student.getUserId(),map);
				//更新teacher表当前学生数
				String Status = "Apply";
				int j = studentFlowManageSer.updateTeacherNowStuNum(teaTitle.getTeacherId(),Status);			
				if((i*j)==0){
					model.addAttribute("errorMsg","系统错误");
					return "error/error.jsp";
				}
			}catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMsg","系统错误");
				return "error/error.jsp";
			}
			//添加代办事项记录
			List<BackLog> backLogs = publiSer.findBackLog(teaTitle.getTeacherId(),null,"teacher");
			Boolean bool = false;
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("stuApplyTitle")){
						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
						bool=true;						
						break;
					}
				}		
			}
			if(!bool){		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setBackLog("stuApplyTitle");
				backLog.setTeaId(teaTitle.getTeacherId());
				backLog.setTeaStatus("0");
				backLog.setCreateTime(nowDate);
				backLog.setCreateUser(student.getStuName());
				int b = publiSer.insertBackLog(backLog);
				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
			}
			
			return "redirect:initApplyTitle.shtm";
		}
		applyTitle = new ApplyTitle();
		applyTitle.setId(UuidHelper.getRandomUUID());
		applyTitle.setStuId(student.getUserId());//学生ID(对应用户表中ID)
		applyTitle.setTeaId(teaTitle.getTeacherId());//教师ID
		applyTitle.setTitle(teaTitle.getTitle());//题目
		applyTitle.setNature(teaTitle.getNature());//题目性质
		applyTitle.setTitleForm(teaTitle.getTitleForm());//完成形式
		applyTitle.setTitleSource("0");//课题来源:教师
		applyTitle.setTitleReason(teaTitle.getTitleReason());
		applyTitle.setTeaStatus("0");	
		applyTitle.setLeaderStatus("1");
		applyTitle.setManagerStatus("1");
		applyTitle.setCreateTime(nowDate);
		applyTitle.setCreateUser(student.getStuName());
		//添加代办事项记录
		List<BackLog> backLogs = publiSer.findBackLog(teaTitle.getTeacherId(),null,"teacher");
		Boolean bool = false;
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("stuApplyTitle")){
					int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
					bool=true;						
					break;
				}
			}		
		}
		if(!bool){		
			BackLog backLog = new BackLog();
			backLog.setId(UuidHelper.getRandomUUID());
			backLog.setBackLog("stuApplyTitle");
			backLog.setTeaId(teaTitle.getTeacherId());
			backLog.setTeaStatus("0");
			backLog.setCreateTime(nowDate);
			backLog.setCreateUser(student.getStuName());
			int b = publiSer.insertBackLog(backLog);
			int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
		}
		//更新学生表中课题信息,利用map传递
		map.put("title", teaTitle.getTitle());
		map.put("teaId", teaTitle.getTeacherId());
		try{		
			//添加apply_title表记录
			int i = studentFlowManageSer.addApplyTitle(applyTitle);
			//更新teacher表当前学生数
			String Status = "Apply";
			int j = studentFlowManageSer.updateTeacherNowStuNum(teaTitle.getTeacherId(),Status);		
			if((i*j)==0){
				model.addAttribute("errorMsg","系统错误");
				return "error/error.jsp";
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "redirect:initApplyTitle.shtm";				
	}
	
	/**
	 * @Title: unApplyTitle
	 * @Description: 退选课题(可退选状态为审核中的课题)
	 * @param model
	 * @param id
	 * @param TitleName
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception String 
	 * @throws
	 */
	@RequestMapping("/initApplyTitle_unApplyTitle.shtm")
	public String unApplyTitle(Model model, String id,Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response){
		ApplyTitle applyTitle = null;
		try{
			applyTitle= studentFlowManageSer.searchApplyTitleByTitleId(id);	
		}catch (Exception e){
			e.printStackTrace();
		}
		if(applyTitle!=null){
			if(applyTitle.getTitleSource().equals("0"))//退选教师课题
			{
				if(applyTitle.getTeaStatus().equals("0")){//审核状态下的课题可以退选
					map.put("applyTitleName",applyTitle.getTitle());//课题名称
					try{
						int k = studentFlowManageSer.updateTitleChoose(map);//更改课题被选状态
						if(k==0){
							model.addAttribute("errorMsg","系统错误");
							return "error/error.jsp";
						}
					}catch (Exception e) {
						e.printStackTrace();
						model.addAttribute("errorMsg","系统错误");
						return "error/error.jsp";
					}					
					//删除待办事项记录
					List<BackLog> backLogs = publiSer.findBackLog(applyTitle.getTeaId(),null,"teacher");
					if(!backLogs.isEmpty()){
						for(int q=0;q<backLogs.size();q++){
							if(backLogs.get(q).getBackLog().equals("stuApplyTitle")){
								if(backLogs.get(q).getNumbers()==1){
									int n = publiSer.removeBackLog(backLogs.get(q).getId());
								}else{
									int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
								}
							}
						}		
					}
					try{									
						int i = studentFlowManageSer.removeApplyTitle(id);//删除申请课题表信息
						//更新teacher表当前学生数
						String Status = "unApply";
						int j = studentFlowManageSer.updateTeacherNowStuNum( applyTitle.getTeaId(),Status);
						if((i*j)==0){
							model.addAttribute("errorMsg","系统错误");
							return "error/error.jsp";
						}
					}catch (Exception e){
						e.printStackTrace();
						model.addAttribute("errorMsg","系统错误");
						return "error/error.jsp";
					}
				}else{//课题状态不在审核中
					model.addAttribute("errorMsg","课题未在审核状态中,不能退选");
					return "forward:initApplyTitle.shtm";
				}
				return "redirect:initApplyTitle.shtm";
			}
			if(applyTitle.getTitleSource().equals("1"))//退选自拟课题
			{
				if(applyTitle.getTeaStatus().equals("0")&&applyTitle.getManagerStatus().equals("0")&&applyTitle.getLeaderStatus().equals("0")){//审核状态下的课题可以退选
					//删除待办事项记录
					List<BackLog> backLogs = publiSer.findBackLog(applyTitle.getTeaId(),null,"teacher");
					if(!backLogs.isEmpty()){
						for(int q=0;q<backLogs.size();q++){
							if(backLogs.get(q).getBackLog().equals("stuApplyTitle")){
								if(backLogs.get(q).getNumbers()==1){
									int n = publiSer.removeBackLog(backLogs.get(q).getId());
								}else{
									int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
								}
							}
						}		
					}
					map.put("applyTitleName",applyTitle.getTitle());//课题名称
					try{
						int i = studentFlowManageSer.removeApplyTitle(id);//删除申请课题表信息
						//更新teacher表当前学生数
						String Status = "unApply";
						int j = studentFlowManageSer.updateTeacherNowStuNum( applyTitle.getTeaId(),Status);		
						if((i*j)==0){
							model.addAttribute("errorMsg","系统错误");
							return "error/error.jsp";
						}
					}catch (Exception e){
						e.printStackTrace();
						model.addAttribute("errorMsg","系统错误");
						return "error/error.jsp";
					}				
				}else{//课题状态不在审核中
					model.addAttribute("errorMsg","课题已审核,不能退选");
					return "forward:initApplyTitle.shtm";
				}
				return "redirect:ApplyTitleSelf.shtm";
			}
		}else{
			model.addAttribute("errorMsg","未查询到课题信息，系统错误");
			return "forward:initApplyTitle.shtm";
		}
		model.addAttribute("errorMsg","系统错误");
		return "error/error.jsp";		
	}
	
	/**
	 * @Title: applyTitleSelf
	 * @Description: 自拟课题页面初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/ApplyTitleSelf.shtm")
	public String applyTitleSelf(Model model,
			HttpServletRequest request, HttpServletResponse response)
	{
		Student student = (Student) request.getSession().getAttribute("student");
		if(StringUtils.isBlank(student.getMajor()))
		{
    		model.addAttribute("errorMsg","请完善个人信息！");
			return "error/error.jsp";
		}	
		ApplyTitle applyTitle = null;
		try{
			applyTitle = studentFlowManageSer.searchApplyTitleById(student.getUserId());
		}catch (Exception e){
			e.printStackTrace();
		}		
		Teacher teacher = new Teacher();
		if(applyTitle!=null){
			try{
				teacher = studentFlowManageSer.findTeacherInfoByTeaId(applyTitle.getTeaId());
			}catch (Exception e){
				e.printStackTrace();
			}			
			//查询是否有关于课题申请更新的待办事项提示,如果有,删除此条提示
			List<BackLog> backLogs = publiSer.findBackLog(student.getUserId(), student.getMajor(),"student");
			if(!backLogs.isEmpty()){
				for(int i=0;i<backLogs.size();i++){
					BackLog backLog = backLogs.get(i);
					if(backLog.getBackLog().equals("reStuApplyTitle")){
						int j = publiSer.removeBackLog(backLog.getId());
						if(j==0){
							model.addAttribute("errorMsg","系统错误");
							return "error/error.jsp";
						}
					}
				}
			}
		}
		//返回当前用户对象
		model.addAttribute("teacher",teacher);
		model.addAttribute("applyTitle",applyTitle);
					
		return "Student/applyForProjectSelf.jsp";
	}
	
	/**
	 * @Title: applyForProject
	 * @Description:自拟课题填写表单
	 * @param model
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/ApplyTitleSelf_applyForProject.shtm")
	public String applyForProject(Model model,BasePage page,Map<String, Object> map,HttpServletRequest request, HttpServletResponse response)
	{
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		Student student = (Student) request.getSession().getAttribute("student");
		//获取时间
		Date nowDate = new Date();//当前时间	
		Date limitDate = null;//特殊情况延期时间
		//返回当前用户applyTitle对象
		ApplyTitle applyTitle = null;
		try{
			applyTitle = studentFlowManageSer.searchApplyTitleById(student.getUserId());
		}catch (Exception e) {
			e.printStackTrace();
		}		
		//判断是否存在记录
		if(applyTitle!=null){
			//判断是否已选课题,教师审核状态是否为驳回
			if(StringUtils.isNotBlank(applyTitle.getTitle())&&(!applyTitle.getTeaStatus().equals("2"))&&(!applyTitle.getLeaderStatus().equals("2"))&&(!applyTitle.getManagerStatus().equals("2"))){
				model.addAttribute("errorMsg","仅限申报一个课题");
				return "forward:ApplyTitleSelf.shtm";
			}			
			//获取特殊情况延期时间
			limitDate = applyTitle.getLimitTime();
		}			
		//查询阶段安排时间
		StagePlan stagePlan = null;
		try{
			stagePlan =	studentFlowManageSer.findStagePlan("apply_title");
		}catch (Exception e) {
			e.printStackTrace();
		}
	  if(stagePlan!=null){
			Date startDate = stagePlan.getStartTime();//阶段安排开始时间
			Date endDate = stagePlan.getEndTime();//阶段安排结束时间
			//判断时间限制
			if(nowDate.getTime()<startDate.getTime()){//比较开始时间
				model.addAttribute("errorMsg","课题申报尚未开始");
				return "forward:ApplyTitleSelf.shtm";
			}
			if(nowDate.getTime()>endDate.getTime()){//比较结束时间
				if(limitDate!=null){//判断是否存在特殊延期时间
					if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
						model.addAttribute("errorMsg","课题申报延期已结束");
						return "forward:ApplyTitleSelf.shtm";
					}
				}else{//无,直接返回超时提示
					model.addAttribute("errorMsg","课题申报已结束");
					return "forward:ApplyTitleSelf.shtm";
				}
			}
	    }else{
	    	model.addAttribute("errorMsg","未查询到阶段时间安排,尚未开始");
			return "forward:ApplyTitleSelf.shtm";
	    }
		//分页查找教师信息
		try{
			page = studentFlowManageSer.findTeacherInfoByMajor(map,page);
		}catch (Exception e) {
			e.printStackTrace();
		}
		try{
			//获取课题性质
			List<TitleNature> titleNatures = studentFlowManageSer.selectNature();
			//获取完成形式
			List<TitleForm> titleForms = studentFlowManageSer.selectForm();
			model.addAttribute("titleNatures", titleNatures);
			model.addAttribute("titleForms", titleForms);
		}catch (Exception e) {
			e.printStackTrace();
		}				
		model.addAttribute("page", page);		
		return "Student/applyForProject.jsp";
	}
	
	/**
	 * @Title: applyTitleSelf
	 * @Description: 申请自拟课题
	 * @param model
	 * @param map
	 * @param teaId
	 * @param titleName
	 * @param titleNature
	 * @param titleForm
	 * @param titleReason
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/ApplyTitleSelf_applyTitle.shtm")
	public String applyTitleSelf(Model model,Map<String, Object> map,String id,String titleName,String titleNature,String titleForm,
			String titleReason,HttpServletRequest request, HttpServletResponse response)
	{
		Student student = (Student) request.getSession().getAttribute("student");
		Date nowDate = new Date();
		//查询已选教师基本信息
		Teacher teacher = null;
		try{
			teacher = studentFlowManageSer.findTeacherById(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		ApplyTitle applyTitle = null;
		try{
			applyTitle = studentFlowManageSer.searchApplyTitleById(student.getUserId());
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(applyTitle!=null){//已申报过课题被驳回,重新申报在原有记录上修改
			map.put("teaId", teacher.getUserId());
			map.put("title", titleName);
			map.put("nature", titleNature);
			map.put("titleSource", "1");
			map.put("titleReason",titleReason);
			map.put("titleForm", titleForm);
			map.put("teaStatus", "0");
			map.put("leaderStatus", "0");
			map.put("managerStatus", "0");
			map.put("createTime", nowDate);
			try{			
				//更新apply_title表
				int i =studentFlowManageSer.updateApplyTitleById(student.getUserId(),map);
				//更新teacher表当前学生数
				String Status = "Apply";
				int j = studentFlowManageSer.updateTeacherNowStuNum(teacher.getUserId(),Status);
				if((i*j)==0){
					model.addAttribute("errorMsg","系统错误");
					return "error/error.jsp";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			//添加代办事项记录
			List<BackLog> backLogs = publiSer.findBackLog(teacher.getUserId(),null,"teacher");
			Boolean bool = false;
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("stuApplyTitle")){
						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
						bool=true;						
						break;
					}
				}		
			}
			if(!bool){		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setBackLog("stuApplyTitle");
				backLog.setTeaId(teacher.getUserId());
				backLog.setTeaStatus("0");
				backLog.setCreateTime(nowDate);
				backLog.setCreateUser(student.getStuName());
				int b = publiSer.insertBackLog(backLog);
				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");
								
			}						
			return "redirect:ApplyTitleSelf.shtm";
		}
		//将信息添加到applytitle对象中
		applyTitle = new ApplyTitle();
		applyTitle.setId(UuidHelper.getRandomUUID());
		applyTitle.setStuId(student.getUserId());//学生ID(对应用户表中ID)
		applyTitle.setTeaId(teacher.getUserId());//教师ID
		applyTitle.setTitle(titleName);//题目
		applyTitle.setNature(titleNature);//题目性质
		applyTitle.setTitleForm(titleForm);//完成形式
		applyTitle.setTitleSource("1");//课题来源:学生
		applyTitle.setTitleReason(titleReason);
		applyTitle.setTeaStatus("0");//指导老师状态审核中	
		applyTitle.setLeaderStatus("0");//专业负责人状态审核中
		applyTitle.setManagerStatus("0");//教学秘书状态审核中
		applyTitle.setCreateTime(nowDate);
		applyTitle.setCreateUser(student.getStuName());
		//更新学生表中课题信息,利用map传递
		map.put("title", titleName);
		map.put("teaId", teacher.getUserId());
		try{					
			//添加apply_title表记录
			int i = studentFlowManageSer.addApplyTitle(applyTitle);
			//更新teacher表当前学生数
			String Status = "Apply";//当前状态为申请课题,SQL执行加一
			int j = studentFlowManageSer.updateTeacherNowStuNum(teacher.getUserId(),Status);		
			if((i*j)==0){
			    model.addAttribute("errorMsg","系统错误");
				return "error/error.jsp";
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		//添加代办事项记录
		List<BackLog> backLogs = publiSer.findBackLog(teacher.getUserId(),null,"teacher");
		Boolean bool = false;
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("stuApplyTitle")){
					int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
					bool=true;						
					break;
				}
			}		
		}
		if(!bool){		
			BackLog backLog = new BackLog();
			backLog.setId(UuidHelper.getRandomUUID());
			backLog.setBackLog("stuApplyTitle");
			backLog.setTeaId(teacher.getUserId());
			backLog.setTeaStatus("0");
			backLog.setCreateTime(nowDate);
			backLog.setCreateUser(student.getStuName());
			int b = publiSer.insertBackLog(backLog);
			int m = publiSer.updateBackLogNumById(backLog.getId(),"add");							
		}		
		return "redirect:ApplyTitleSelf.shtm";
	}
	
	/**
	 * @Title: changeTitleInfo
	 * @Description: 修改课题信息
	 * @param model
	 * @param map
	 * @param titleId
	 * @param titleName
	 * @param titleNature
	 * @param titleForm
	 * @param titleReason
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/changeTitleInfo.shtm")
	public String changeTitleInfo(Model model,Map<String, Object> map,String titleId,String titleName,String titleNature,
			String titleForm,String titleReason,HttpServletRequest request, HttpServletResponse response){
		
		Student student = (Student) request.getSession().getAttribute("student");
		map.put("title",titleName);
		map.put("nature",titleNature);
		map.put("titleForm",titleForm);
		map.put("titleReason",titleReason);
		try{
			//更新ApplyTitle表课题信息
			int i =studentFlowManageSer.updateApplyTitleById(student.getUserId(),map);
			if(i==0){
				model.addAttribute("errorMsg","系统错误");
				return "error/error.jsp";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	    return "redirect:ApplyTitleSelf.shtm";
	}
	
	/**
	 * @Title: dbGroupInfo
	 * @Description: 查询答辩安排
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/dbGroupInfo.shtm")
	public String dbGroupInfo(Model model,Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response){
		
		Student student = (Student) request.getSession().getAttribute("student");
		DbGroup dbGroup = null;
		try{
			dbGroup = studentFlowManageSer.findDbGroupByStuId(student.getUserId());
		}catch (Exception e) {
			e.printStackTrace();
		}
		//时间格式转换
		if(dbGroup!=null){
			Date date = dbGroup.getDbTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String dbTime = formatter.format(date);
			model.addAttribute("dbTime", dbTime);
		}		
		model.addAttribute("dbGroup", dbGroup);
		return "Student/dbGroupInfo.jsp";
	}
	
	/**
	 * @Title: finalScoreInfo
	 * @Description: 最终成绩汇总
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/finalScoreInfo.shtm")
	public String finalScoreInfo(Model model,Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response){
		
		Student student = (Student) request.getSession().getAttribute("student");
		Grade grade = null;
		try{
			grade = studentFlowManageSer.findFinalGrade(student.getUserId());
		}catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("grade", grade);
		return "Student/finalScoreInfo.jsp";
	}
	
	/**
	 * @Title: initApplyForDelay
	 * @Description: 延期申请页面初始化
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/initApplyForDelay.shtm")
	public String initApplyForDelay(Model model,Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response){
		
		Student student = (Student) request.getSession().getAttribute("student");
		List<ApplyDelay> applyDelays = null;
		try{
			applyDelays = studentFlowManageSer.findApplyDelaysByStuId(student.getUserId());
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(!applyDelays.isEmpty()){
			for(int i=0;i<applyDelays.size();i++){
				if(applyDelays.get(i).getDelayStage().equals("apply_title")){
					applyDelays.get(i).setDelayStage("课题申请");
				}else if(applyDelays.get(i).getDelayStage().equals("task_book")){
					applyDelays.get(i).setDelayStage("任务书");
				}else if(applyDelays.get(i).getDelayStage().equals("opening_report")){
					applyDelays.get(i).setDelayStage("开题报告");
				}else if(applyDelays.get(i).getDelayStage().equals("mid_check")){
					applyDelays.get(i).setDelayStage("中期检查");
				}else if(applyDelays.get(i).getDelayStage().equals("first_paper")){
					applyDelays.get(i).setDelayStage("论文初稿");
				}else if(applyDelays.get(i).getDelayStage().equals("final_paper")){
					applyDelays.get(i).setDelayStage("论文定稿");
				}
				Date date = applyDelays.get(i).getDelayTime();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:MM");
				applyDelays.get(i).setDateTimeStr(formatter.format(date));				
			}
		}
		List<BackLog> backLogs = null;
		try{
			//查询是否有关于延期更新的待办事项提示,如果有,删除此条提示
			backLogs = publiSer.findBackLog(student.getUserId(), student.getMajor(),"student");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(!backLogs.isEmpty()){
			for(int i=0;i<backLogs.size();i++){
				BackLog backLog = backLogs.get(i);
				if(backLog.getBackLog().equals("reDelayApply")){
					int j = publiSer.removeBackLog(backLog.getId());
					if(j==0){
						model.addAttribute("errorMsg","系统错误");
						return "error/error.jsp";
					}
				}
			}
		}
		model.addAttribute("applyDelays", applyDelays);
		return "Student/applyForDelay.jsp";
	}
	
	/**
	 * @Title: applyForDelay
	 * @Description: 提交延期申请
	 * @param model
	 * @param delayStage
	 * @param delayTime
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/initApplyForDelay_applyForDelay.shtm")
	public String applyForDelay(Model model,String delayStage,String delayTime,String delayReason,
			HttpServletRequest request, HttpServletResponse response){
		
		Student student = (Student) request.getSession().getAttribute("student");
		StagePlan plan = null;
		List<ApplyDelay> applyDelays = null;
		Date delayDate = null;
		try{
			plan = studentFlowManageSer.findStagePlan(delayStage);
			applyDelays = studentFlowManageSer.findApplyDelaysByStuId(student.getUserId());
		}catch (Exception e){
			e.printStackTrace();
		}
		if(plan!=null){
			Date endDate = plan.getEndTime();//阶段安排结束时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:MM");			
			try {
				delayDate = sdf.parse(delayTime.trim());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for(int i = 0;i<applyDelays.size();i++){
				if(applyDelays.get(i).getDelayStage().equals(delayStage)){
					model.addAttribute("errorMsg","已申请过该阶段延期,不能重复申请");
					return "forward:initApplyForDelay.shtm";
				}
			}
			if(delayDate.getTime()<endDate.getTime()){//判断延期是否在合理范围内
				model.addAttribute("errorMsg","时间设置不合理");
				return "forward:initApplyForDelay.shtm";
			}
		}else{
			model.addAttribute("errorMsg","未查询到阶段时间安排,系统错误");
			return "forward:initApplyForDelay.shtm";
		}
		ApplyDelay applyDelay = new ApplyDelay();
		applyDelay.setId(UuidHelper.getRandomUUID());
		applyDelay.setStuId(student.getUserId());
		if(StringUtils.isNotBlank(student.getZdTeacher())){
			applyDelay.setTeaId(student.getZdTeacher());
			applyDelay.setTeaStatus("0");
		}else{
			applyDelay.setTeaStatus("1");
		}
		applyDelay.setDelayStage(delayStage);
		applyDelay.setDelayTime(delayDate);
		applyDelay.setDelayReason(delayReason);		
		applyDelay.setManagerStatus("0");
		applyDelay.setCreateUser(student.getStuName());
		applyDelay.setCreateTime(new Date());
		try{
			int i = studentFlowManageSer.insertApplyDelay(applyDelay);
			if(i==0){
				model.addAttribute("errorMsg","系统错误");
				return "error/error.jsp";
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg","系统错误");
			return "error/error.jsp";
		}
		//添加代办事项记录
		List<BackLog> backLogs = new ArrayList<BackLog>();
		if(StringUtils.isNotBlank(student.getZdTeacher())){
			backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
		}else{//未选指导老师
			backLogs = publiSer.findBackLogByType("delayApply");
		}
		Boolean bool = false;
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("delayApply")){
					int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
					bool=true;						
					break;
				}
			}		
		}
		if(!bool){		
			BackLog backLog = new BackLog();
			backLog.setId(UuidHelper.getRandomUUID());
			backLog.setBackLog("delayApply");
			if(StringUtils.isNotBlank(student.getZdTeacher())){
				backLog.setTeaId(student.getZdTeacher());
				backLog.setTeaStatus("0");
			}else{
				backLog.setManagerStatus("0");
			}			
			backLog.setCreateTime(new Date());
			backLog.setCreateUser(student.getStuName());
			int b = publiSer.insertBackLog(backLog);
			int m = publiSer.updateBackLogNumById(backLog.getId(),"add");
							
		}		
		return "redirect:initApplyForDelay.shtm";
	}
	
	@RequestMapping("/initApplyForDelay_cancel.shtm")
	public String applyForDelay_cancel(Model model,String id,
			HttpServletRequest request, HttpServletResponse response){
		
		Student student = (Student) request.getSession().getAttribute("student");
		ApplyDelay applyDelay = null;
		try{
			applyDelay = studentFlowManageSer.findApplyDelaysById(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(applyDelay==null){
			model.addAttribute("errorMsg","系统错误");
			return "error/error.jsp";
		}
		if(StringUtils.isNotBlank(student.getZdTeacher())){
			if(!applyDelay.getTeaStatus().equals("0")||!applyDelay.getManagerStatus().equals("0")){
				model.addAttribute("errorMsg","延期申请已审核,不能撤回");
				return "forward:initApplyForDelay.shtm";
			}
		}else{
			if(!applyDelay.getManagerStatus().equals("0")){
				model.addAttribute("errorMsg","延期申请已审核,不能撤回");
				return "forward:initApplyForDelay.shtm";
			}
		}
		//删除待办事项记录
		List<BackLog> backLogs = new ArrayList<BackLog>();
		if(student.getZdTeacher()!=null){
			backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
		}else{//未选指导老师
			backLogs = publiSer.findBackLogByType("delayApply");
		}
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("delayApply")){
					if(backLogs.get(q).getNumbers()==1){
						int n = publiSer.removeBackLog(backLogs.get(q).getId());
					}else{
						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
					}
				}
			}		
		}
		try{
			int i = studentFlowManageSer.deleteApplyDelay(id);
			if(i==0){
				model.addAttribute("errorMsg","系统错误");
				return "error/error.jsp";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:initApplyForDelay.shtm";
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
	public String formerTerm(String formerTerm,String title,
			BasePage page, Model model,Map<String, Object> map,HttpServletRequest request, HttpServletResponse response)
	{	
		/**
		 * 此处重新调用setPageNo方法为了正确接收pageSize后设置RecordNo
		 */
		page.setPageNo(page.getPageNo());
		String url = "student";
		model.addAttribute("url", url);
		if(StringUtils.isNotBlank(formerTerm)){
			map.put("term", formerTerm);
			model.addAttribute("formerTerm", formerTerm);
		}if(StringUtils.isNotBlank(title)){
			map.put("title", title);
			model.addAttribute("title", title);
		}
		try{
			page=publiSer.findFormerTerm(map,page);
		}catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page",page);
		return "Student/formerTerm.jsp";
	}

}
