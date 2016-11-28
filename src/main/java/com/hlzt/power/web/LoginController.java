package com.hlzt.power.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hlzt.commons.helper.RandomValidateCodeHelper;
import com.hlzt.power.model.BackLog;
import com.hlzt.power.model.PublicNotice;
import com.hlzt.power.model.Role;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.Teacher;
import com.hlzt.power.model.User;
import com.hlzt.power.model.UserRole;
import com.hlzt.power.service.PublicSer;
import com.hlzt.power.service.UserRoleSer;
import com.hlzt.power.service.UserSer;

@Controller
public class LoginController {
	
	private static final Log log = LogFactory.getLog(LoginController.class);
	
	@Resource
	private UserSer userSer;
	@Resource
	private UserRoleSer userRoleSer;
	@Resource
	private PublicSer publiSer;
	
	 /**
     * 登录处理
     * @param model 模型对象
     * @param req 请求对象
     * @param username 用户名
     * @return 目标view
     */
    @RequestMapping("/login.shtm")
    public String login(Model model, 
    		HttpServletRequest req, HttpServletResponse response) {
    	try{
	        if(SecurityUtils.getSubject().isAuthenticated()&& !response.isCommitted()){
	            return index(model, req, response);
	        }
    	}catch (Exception e) {
			e.printStackTrace();
		}
		List<PublicNotice> tableList = new ArrayList<PublicNotice>();
		List<PublicNotice> noticeList = new ArrayList<PublicNotice>();
		try {
			tableList = publiSer.findNotice("table");
			noticeList = publiSer.findNotice("notice");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("tableList", tableList);
		model.addAttribute("noticeList", noticeList);
    	return "login.jsp";
        
    }
    
    /**
     * 用于生成验证码
     * @param model
     * @param req
     * @param response
     * @return
     */
    @RequestMapping("/randomValidateCode.chtm")
    public void randomValidateCode(Model model, HttpServletRequest req, HttpServletResponse response){
    	response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCodeHelper randomValidateCode = new RandomValidateCodeHelper();
        try {
            randomValidateCode.getRandcode(req, response);//输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    /**
     * 主页
     * @param model
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/index.shtm")
	public String index(Model model,HttpServletRequest request,HttpServletResponse response) {
    	User user = (User) request.getSession().getAttribute("user");
		List<UserRole> list = null;
		list = userRoleSer.findRoleByUserId(user.getId());
		
		if(null!=list){
			String role = null;
			if(list.get(0).getFkRole().equals("5")){
				role = list.get(1).getRoleName();
			}else{
				role = list.get(0).getRoleName();
			}		
			model.addAttribute("roleList",list);
			if("manager".equals(role)){				
				return "redirect:/secretary.shtm";				
			}else if("major_leader".equals(role)){
				return "redirect:/majorLeader.shtm";
			}else if("zd_teacher".equals(role)){
				return "redirect:/zdTeacher.shtm";
			}else if("student".equals(role)){
				return "redirect:/student.shtm";
			}					
		}
		model.addAttribute("errorMsg","您暂无权限");
		return "error/error.jsp";
	}
    
    /**
     * 教学秘书登录跳转页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/secretary.shtm")
    public String secretary(Model model, HttpServletRequest request, HttpServletResponse response){
    	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
    	List<PublicNotice> paperList = new ArrayList<PublicNotice>();
		List<PublicNotice> tableList = new ArrayList<PublicNotice>();
		List<PublicNotice> noticeList = new ArrayList<PublicNotice>();
		List<BackLog> backLogLists = new ArrayList<BackLog>();
		List<BackLog> backLogList = new ArrayList<BackLog>();
		if(StringUtils.isBlank(teacher.getTel())||StringUtils.isBlank(teacher.getMail())){
    		model.addAttribute("successMsg", "请完善个人联系方式");
    		return "forward:/secretary/findInfo.shtm";
    	}
		try {
			paperList = publiSer.findNotice("paper");
			tableList = publiSer.findNotice("table");
			noticeList = publiSer.findNotice("notice");
			backLogLists = publiSer.findBackLog(teacher.getUserId(), null, "manager");			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		for(int i=0;i<backLogLists.size();i++){
			BackLog backLog = backLogLists.get(i);
			if(backLog.getTeaStatus()!=null&&(!backLog.getTeaStatus().equals(""))){
				if(backLog.getTeaStatus().equals("1"))
				{
					if(backLog.getLeaderStatus()!=null&&backLog.getLeaderStatus()!="")
					{
						if(backLog.getLeaderStatus().equals("1")){
							backLogList.add(backLog);
						}
					}
				}	
			}else if(backLog.getLeaderStatus()!=null&&(!backLog.getLeaderStatus().equals("")))
			{
				if(backLog.getLeaderStatus().equals("1")){
					backLogList.add(backLog);
				}
			}else{
				backLogList.add(backLog);
			}		
		}
		model.addAttribute("paperList", paperList);
		model.addAttribute("tableList", tableList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("backLogList", backLogList);
		model.addAttribute("baseUrl", "/secretary.shtm");
    	return "Public_Page/homePage.jsp";
    }
    
    /**
     * 专业负责人登录跳转页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/majorLeader.shtm")
    public String zdTeacher(Model model, HttpServletRequest request, HttpServletResponse response){
    	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
    	if(StringUtils.isBlank(teacher.getTel())||StringUtils.isBlank(teacher.getMail())){
    		model.addAttribute("successMsg", "请完善个人联系方式");
    		return "forward:/majorLeader/findInfo.shtm";
    	}
    	List<PublicNotice> paperList = new ArrayList<PublicNotice>();
		List<PublicNotice> tableList = new ArrayList<PublicNotice>();
		List<PublicNotice> noticeList = new ArrayList<PublicNotice>();
		List<BackLog> backLogLists = new ArrayList<BackLog>();
		List<BackLog> backLogList = new ArrayList<BackLog>();
		try {
			paperList = publiSer.findNotice("paper");
			tableList = publiSer.findNotice("table");
			noticeList = publiSer.findNotice("notice");
			backLogLists = publiSer.findBackLog(teacher.getUserId(), teacher.getMajor(), "leader");			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		for(int i=0;i<backLogLists.size();i++){
			BackLog backLog = backLogLists.get(i);
			if(backLog.getTeaStatus()!=null&&(!backLog.getTeaStatus().equals(""))){
				if(backLog.getTeaStatus().equals("1")){
					backLogList.add(backLog);
				}
			}else{
				backLogList.add(backLog);
			}
		}
		model.addAttribute("paperList", paperList);
		model.addAttribute("tableList", tableList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("backLogList", backLogList);
		model.addAttribute("baseUrl", "/majorLeader.shtm");
    	return "Public_Page/homePage.jsp";
    }
    
    /**
     * 指导老师登录跳转页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/zdTeacher.shtm")
    public String majorLeader(Model model,HttpServletRequest request, HttpServletResponse response){
    	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
    	if(StringUtils.isBlank(teacher.getTel())||StringUtils.isBlank(teacher.getMail())){
    		model.addAttribute("successMsg", "请完善个人联系方式");
    		return "forward:/guideTeacher/findInfo.shtm";
    	}
    	List<PublicNotice> paperList = new ArrayList<PublicNotice>();
		List<PublicNotice> tableList = new ArrayList<PublicNotice>();
		List<PublicNotice> noticeList = new ArrayList<PublicNotice>();
		List<BackLog> backLogList = new ArrayList<BackLog>();
		try {
			paperList = publiSer.findNotice("paper");
			tableList = publiSer.findNotice("table");
			noticeList = publiSer.findNotice("notice");
			backLogList = publiSer.findBackLog(teacher.getUserId(),teacher.getMajor(),"teacher");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("paperList", paperList);
		model.addAttribute("tableList", tableList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("backLogList", backLogList);
		model.addAttribute("baseUrl", "/zdTeacher.shtm");
    	return "Public_Page/homePage.jsp";
    }
    
    /**
     * 学生登录跳转页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/student.shtm")
    public String student(Model model, HttpServletRequest request, HttpServletResponse response){
    	Student stu = (Student) request.getSession().getAttribute("student");
    	if(StringUtils.isBlank(stu.getTel())||StringUtils.isBlank(stu.getMail())){
    		model.addAttribute("successMsg", "请完善个人联系方式");
    		return "forward:/student/findInfo.shtm";
    	}
    	List<PublicNotice> paperList = new ArrayList<PublicNotice>();
		List<PublicNotice> tableList = new ArrayList<PublicNotice>();
		List<PublicNotice> noticeList = new ArrayList<PublicNotice>();
		List<BackLog> backLogList = new ArrayList<BackLog>();
		try {
			paperList = publiSer.findNotice("paper");
			tableList = publiSer.findNotice("table");
			noticeList = publiSer.findNotice("notice");
			backLogList = publiSer.findBackLog(stu.getUserId(),stu.getMajor(),"student");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}
		model.addAttribute("paperList", paperList);
		model.addAttribute("tableList", tableList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("backLogList", backLogList);
		model.addAttribute("baseUrl","/student.shtm");
    	return "Public_Page/homePage.jsp";
    }
    
    /**
     * 用户退出
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout.shtm")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response){
    	
    	return "login.jsp";
    }
    
    /**
     * 返回首页
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/backHomePage.shtm")
    public String backHomePage(Model model, HttpServletRequest request, HttpServletResponse response){
    	
    	return "Public_Page/homePage.jsp";
    }
    
    /**
     * @Title: removeBackLog
     * @Description: 删除待办事项
     * @param model
     * @param id
     * @param baseUrl
     * @param request
     * @param response
     * @return String 
     * @throws
     */
    @RequestMapping("/removeBackLog.shtm")
    public String removeBackLog(Model model,String id,String baseUrl,HttpServletRequest request, HttpServletResponse response)
    {
    	String url = "redirect:"+baseUrl;
    	try{
    		int i = publiSer.removeBackLog(id); 
    	}catch (Exception e) {
    		e.printStackTrace();
			model.addAttribute("errorMsg", "系统异常！");
			return "error/error.jsp";
		}      
    	return url;
    }
    
}
