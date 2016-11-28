package com.hlzt.power.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.awt.ModalExclude;

import com.hlzt.commons.helper.StringHelper;
import com.hlzt.commons.helper.UuidHelper;
import com.hlzt.power.model.*;
import com.hlzt.power.service.PublicSer;
import com.hlzt.power.service.UserSer;
import com.hlzt.power.service.ZdTeacherTeshuSer;

@Controller
@RequestMapping("/guideTeacher")
public class ZdTeacherTeshuController {

	@Resource
	private ZdTeacherTeshuSer zdTeacherTeshuSer;
	@Resource
	private UserSer userSer;
	@Resource
	private PublicSer publicSer;

	@RequestMapping("/checkStuDelay.shtm")
	public String checkStuDelay (String stuNum,String stuName,String status,Model model, 
			Map<String, Object> map,HttpServletResponse response,HttpServletRequest request){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.hasRole("zd_teacher")) {
			model.addAttribute("errorMsg", "无权限！");
			return "error/error.jsp";
		}
		if (status == null)
			status = "0";// 默认显示未审核记录
		
		Teacher teacher =(Teacher) request.getSession().getAttribute("teacher");		
		map.put("teaId", teacher.getUserId());
		if (StringUtils.isNotBlank(stuNum))
			map.put("stuNum", StringHelper.formatLike(stuNum));
		if (StringUtils.isNotBlank(stuName))
			map.put("stuName", StringHelper.formatLike(stuName));
		if (StringUtils.isNotBlank(status)){
			if (status.equals("3"))
				status = null;
			map.put("status", status);// 查询条件
		}
		
		List<ApplyDelay> applyDelaysinfo=zdTeacherTeshuSer.findStuDelayReq(map);//通过教师Id查询学生延期请求
		if(applyDelaysinfo!=null){
			for(int i=0;i<applyDelaysinfo.size();i++){//其实教师只属于一个答辩小组，				
				Date date = applyDelaysinfo.get(i).getDelayTime();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
				String dltime = formatter.format(date);
				applyDelaysinfo.get(i).setDateTimeStr(dltime);//时间格式转换	
			}
		}
		
		if(applyDelaysinfo!=null){
			
			for (int i = 0; i < applyDelaysinfo.size(); i++) {
				if (applyDelaysinfo.get(i).getTeaStatus().equals("0")) {
					applyDelaysinfo.get(i).setSumStatus("未审核");
				} else if (applyDelaysinfo.get(i).getTeaStatus().equals("1")
						&& applyDelaysinfo.get(i).getManagerStatus().equals("0")) {
					applyDelaysinfo.get(i).setSumStatus("秘书审核中");
				} else if (applyDelaysinfo.get(i).getTeaStatus().equals("1")
						&& applyDelaysinfo.get(i).getManagerStatus().equals("1")) {
					applyDelaysinfo.get(i).setSumStatus("审核通过");
				} else {
					applyDelaysinfo.get(i).setSumStatus("审核未通过");
				}
			}
		}//此方法也可以在前台做
		
		model.addAttribute("stuNum", stuNum);
		model.addAttribute("stuName", stuName);
		model.addAttribute("status", status);//查询条件回显
		
		model.addAttribute("applyDelaysinfo",applyDelaysinfo);
		return "guideTeacher/modify_date.jsp";	
	}

	@RequestMapping("/checkStuDelay_opt.shtm")
	public String optStuDelay(String[] ids, String status,String teaIdea,Map<String, Object> map,Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
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
			return checkStuDelay(null, null, null, model, map, response,
					request);
		}
		
		if(teaIdea!=null){
			teaIdea = new String(teaIdea.getBytes("iso8859-1"), "UTF-8");	// get数据中文转码
		}
		
		List<String> list = Arrays.asList(ids);
		int i = 0;
		try {
			i = zdTeacherTeshuSer.optStuDelay(list, status, teaIdea);// 设置教师status
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if (i == 0) {
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		if(status.equals("1")){
			for(int k=0;k<list.size();k++){
			List<BackLog> backLogs = publicSer.findBackLog(null,null,"manager");
			Boolean bool = false;
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("delayApply")){
						int m = publicSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
						bool=true;						
						break;
					}
				}		
			}
			if(!bool){		
				BackLog backLog = new BackLog();
				backLog.setId(UuidHelper.getRandomUUID());
				backLog.setBackLog("delayApply");
				backLog.setTeaStatus("1");
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
			List<BackLog> backLogs = publicSer.findBackLog(teacher.getUserId(),null,"teacher");
			if(!backLogs.isEmpty()){
				for(int q=0;q<backLogs.size();q++){
					if(backLogs.get(q).getBackLog().equals("delayApply")){
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
				ApplyDelay applyDelay = zdTeacherTeshuSer.findApplyDelayById(list.get(k));
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
					backLog.setCreateUser(teacher.getTeaName());
					int b = publicSer.insertBackLog(backLog);
					int m = publicSer.updateBackLogNumById(backLog.getId(),"add");			
				}
			}
		}
		return checkStuDelay(null, null, null, model, map, response, request);
	}
	
	/**
	  * 查询个人信息
	  * @param id
	  * @param model
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("/findInfo.shtm")
	 public String findTeacherInfo(Model model,
			 HttpServletRequest request, HttpServletResponse response){
		 User loginUser = (User) request.getSession().getAttribute("user");
		 Teacher tea = new Teacher();		 
		 try {
			 tea = userSer.findTeacherInfo(loginUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
		}
		List<Major> list = new ArrayList<Major>();		
		list = publicSer.findMajor();
		List<ZhiCheng> zhiChenglist = new ArrayList<ZhiCheng>();
		zhiChenglist = publicSer.findZhiCheng();
		model.addAttribute("zhiChengList", zhiChenglist);
		model.addAttribute("majorList", list);
		model.addAttribute("tea", tea);
		return "guideTeacher/guideTeacherInfo.jsp";
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
	 @RequestMapping("/findInfo_updateInfo.shtm")
	 public String updateInfo(String teaName, String sex, String zhicheng,
			 String password, String tel, String mail, String major, String userId,
			 Model model,  HttpServletRequest request, HttpServletResponse response){
		 if(StringUtils.isBlank(teaName)){
			 model.addAttribute("errorMsg", "姓名不能为空！");
			 return findTeacherInfo(model, request, response);
		 }
		 if(StringUtils.isBlank(zhicheng)){
			 model.addAttribute("errorMsg", "职称不能为空！");
			 return findTeacherInfo(model, request, response);
		 }
		 if("0".equals(major)){
			 model.addAttribute("errorMsg", "专业不能为空！");
			 return findTeacherInfo(model, request, response);			 
		 }
		 
		 User user = (User) request.getSession().getAttribute("user");
		 user.setId(userId);
		 if(StringUtils.isNotBlank(password)){
			 user.setPassword(StringHelper.getMD5(String.valueOf(password)));
		 }			 
		 user.setRealName(teaName);
		 user.setSex(sex);
		 
		 Teacher tea = (Teacher) request.getSession().getAttribute("teacher");
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
		request.getSession().setAttribute("user", user);
		request.getSession().setAttribute("teacher", tea);
		return findTeacherInfo(model, request, response);
	 }
	
}
