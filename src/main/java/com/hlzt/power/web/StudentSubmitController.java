package com.hlzt.power.web;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hlzt.commons.helper.SysConfig;
import com.hlzt.commons.helper.UuidHelper;
import com.hlzt.power.model.ApplyTitle;
import com.hlzt.power.model.BackLog;
import com.hlzt.power.model.FinalPaper;
import com.hlzt.power.model.FirstPaper;
import com.hlzt.power.model.MidCheck;
import com.hlzt.power.model.OpeningReport;
import com.hlzt.power.model.Paper;
import com.hlzt.power.model.StagePlan;
import com.hlzt.power.model.Student;
import com.hlzt.power.model.TaskBook;
import com.hlzt.power.service.PublicSer;
import com.hlzt.power.service.StudentFlowManageSer;

/**
 * @ClassName: StudentSubmitController
 * @Description: 学生提交实体管理
 * @author cxy
 *
 */
@Controller
@RequestMapping("/student")
public class StudentSubmitController {
	
	 
	@Autowired
	private StudentFlowManageSer studentFlowManageSer;	
	@Autowired
	private PublicSer publiSer;
	
	//******任务书**********
	/**
	 * @Title: taskBook
	 * @Description: 提交任务书页面初始化
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/taskBook.shtm")
	public String taskBook(Model model,Map<String, Object> map, 
    		HttpServletRequest request, HttpServletResponse response){
		
		Student student = (Student) request.getSession().getAttribute("student");
		StagePlan stagePlan = null;
		//获取阶段安排时间
		try{
			stagePlan = studentFlowManageSer.findStagePlan("task_book");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(stagePlan!=null){
			Date start = stagePlan.getStartTime();//阶段安排开始时间
			Date end = stagePlan.getEndTime();//阶段安排结束时间
			//时间转换中文
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String startDate = formatter.format(start);
			String endDate = formatter.format(end);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
		}else{
			model.addAttribute("errorMsg","该阶段尚未开始");
			model.addAttribute("startDate", "");
			model.addAttribute("endDate", "");			
		}
		TaskBook taskBook = null;
		try{
			//获取任务书信息
			taskBook = studentFlowManageSer.findTaskByStuId(student.getUserId());
		}catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("taskBook",taskBook);
		//查询是否有关于任务书更新的待办事项提示,如果有,删除此条提示
		List<BackLog> backLogs = publiSer.findBackLog(student.getUserId(), student.getMajor(),"student");
		if(!backLogs.isEmpty()){
			for(int i=0;i<backLogs.size();i++){
				BackLog backLog = backLogs.get(i);
				if(backLog.getBackLog().equals("reTaskBook")){
					int j = publiSer.removeBackLog(backLog.getId());
					if(j==0){
						model.addAttribute("errorMsg","系统错误");
						return "error/error.jsp";
					}
				}
			}
		}
		return "Student/taskBookSubmit.jsp";
	}
	
	/**
	 * @Title: submitObject
	 * @Description: 接收任务书文件
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception String 
	 * @throws
	 */
	@RequestMapping("/taskBook_submitTask.shtm")
	public String submitObject(Model model,Map<String, Object> map, 
    		HttpServletRequest request, HttpServletResponse response){		
		
		Student student = (Student) request.getSession().getAttribute("student");
		if(StringUtils.isBlank(student.getZdTeacher())){
			model.addAttribute("errorMsg","尚未选择导师,若确认已选导师,请尝试重新登录");
			return "forward:taskBook.shtm";
		}
		//获取阶段安排时间
		StagePlan stagePlan = null;
		TaskBook taskBook = null;
		try{
			stagePlan =	studentFlowManageSer.findStagePlan("task_book");	
			taskBook = studentFlowManageSer.findTaskByStuId(student.getUserId());
		}catch (Exception e){
			e.printStackTrace();
		}
		Date limitDate =null;
		Date nowDate = new Date();//当前时间
		if(stagePlan!=null){
			Date startDate = stagePlan.getStartTime();//阶段安排开始时间
			Date endDate = stagePlan.getEndTime();//阶段安排结束时间
			//判断是否重复提交
			if(taskBook!=null){
				if((!taskBook.getTeaStatus().equals("2"))&&(!taskBook.getLeaderStatus().equals("2"))){
					model.addAttribute("errorMsg","已提交过任务书,不能重复提交");
					return "forward:taskBook.shtm";
				}
				limitDate=taskBook.getLimitTime();
			}
			//判断时间限制
			if(nowDate.getTime()<startDate.getTime()){//比较开始时间
				model.addAttribute("errorMsg","任务书提交尚未开始");
				return "forward:taskBook.shtm";
			}
			if(nowDate.getTime()>endDate.getTime()){//比较结束时间
				if(limitDate!=null){//判断是否存在特殊延期时间
					if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
						model.addAttribute("errorMsg","任务书提交延期已结束");
						return "forward:taskBook.shtm";
					}
				}else{//无,直接返回超时提示
					model.addAttribute("errorMsg","任务书提交已结束");
					return "forward:taskBook.shtm";
				}
			}
		}else{
			model.addAttribute("errorMsg","未查询到阶段时间安排,该阶段尚未开始");
			return "forward:taskBook.shtm";
		}
		//获取文件流
		MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("taskFile");
		if(!file.isEmpty()){
			//重命名文件(taskBook-学号)
			String fileOldName = file.getOriginalFilename();
			String fileName = "taskBook"+"-"+student.getUserNum();
			if (fileOldName.indexOf(".") != -1) {  
	            fileName += fileOldName.substring(fileOldName.lastIndexOf("."));  
			} 
			String filePath = null;
			try{
				//文件服务器存储地址
				 String rootFile = SysConfig.getValue("term");//当前届文件夹
				 String rootPath =request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录           			 
				 File dir = new File(rootPath + File.separator+student.getUserNum());			 
				 if (!dir.exists())//若地址文件夹不存在,创建文件夹
	                 dir.mkdirs();
	             //写文件到服务器
	             filePath = dir.getAbsolutePath() + File.separator +fileName;
	             File serverFile = new File(filePath);
	             file.transferTo(serverFile);
			}catch (Exception e){
				e.printStackTrace();
				model.addAttribute("errorMsg","系统错误");
 				return "error/error.jsp";
			}
             if(taskBook!=null){//记录不为空,之前上传过任务书,重新上传,update覆盖原有记录
     			map.put("teaStatus","0");
     			map.put("leaderStatus","0");
     			map.put("taskBook",filePath);
     			map.put("createTime", nowDate);
     			map.put("mainTask", null);
     			map.put("zhiBiao", null);
     			map.put("yaoQiu", null);
     			map.put("wenXian", null);
     			try{
	     			int j = studentFlowManageSer.updateTaskBookById(student.getUserId(), map);
	     			if(j==0){
	     				model.addAttribute("errorMsg","系统错误");
	     				return "error/error.jsp";
	     			}
     			}catch (Exception e){
					e.printStackTrace();
					model.addAttribute("errorMsg","系统错误");
     				return "error/error.jsp";
				}
     			//添加代办事项记录
     			List<BackLog> backLogs  = null;
     			try{
     				backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
     			}catch (Exception e) {
					e.printStackTrace();
				}
     			Boolean bool = false;
     			if(!backLogs.isEmpty()){
     				for(int q=0;q<backLogs.size();q++){
     					if(backLogs.get(q).getBackLog().equals("taskBook")){
     						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
     						bool=true;						
     						break;
     					}
     				}		
     			}
     			if(!bool){		
     				BackLog backLog = new BackLog();
     				backLog.setId(UuidHelper.getRandomUUID());
     				backLog.setBackLog("taskBook");
     				backLog.setTeaId(student.getZdTeacher());
     				backLog.setTeaStatus("0");
     				backLog.setCreateTime(nowDate);
     				backLog.setCreateUser(student.getStuName());
     				int b = publiSer.insertBackLog(backLog);
     				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
     			}    			
     			return "redirect:taskBook.shtm";
             }          
             //数据表中不存在记录,重新创建对象并insert表中
             taskBook = new TaskBook();
             taskBook.setId(UuidHelper.getRandomUUID());
             taskBook.setStuId(student.getUserId());
             taskBook.setTeaId(student.getZdTeacher());
             taskBook.setMainTask(null);
             taskBook.setZhiBiao(null);
             taskBook.setYaoQiu(null);
             taskBook.setWenXian(null);
             taskBook.setTeaStatus("0");
             taskBook.setLeaderStatus("0");
             taskBook.setTaskBookPath(filePath);
             taskBook.setCreateUser(student.getStuName());
             taskBook.setCreateTime(nowDate);
             try{
            	 int i =studentFlowManageSer.addTaskBook(taskBook);
            	 if(i==0){
                	 model.addAttribute("errorMsg","系统错误");
         			 return "error/error.jsp";
                 }
             }catch (Exception e){
				e.printStackTrace();
				model.addAttribute("errorMsg","系统错误");
     			return "error/error.jsp";
			}
            //添加代办事项记录
 			List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
 			Boolean bool = false;
 			if(!backLogs.isEmpty()){
 				for(int q=0;q<backLogs.size();q++){
 					if(backLogs.get(q).getBackLog().equals("taskBook")){
 						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
 						bool=true;						
 						break;
 					}
 				}		
 			}
 			if(!bool){		
 				BackLog backLog = new BackLog();
 				backLog.setId(UuidHelper.getRandomUUID());
 				backLog.setBackLog("taskBook");
 				backLog.setTeaId(student.getZdTeacher());
 				backLog.setTeaStatus("0");
 				backLog.setCreateTime(nowDate);
 				backLog.setCreateUser(student.getStuName());
 				int b = publiSer.insertBackLog(backLog);
 				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
 			}            
            return "redirect:taskBook.shtm";
		}
		model.addAttribute("errorMsg","系统错误");
		return "error/error.jsp";
	}
	
	@RequestMapping("/taskBook_cancel.shtm")
	public String cancelTaskBook(Model model, String id,Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response){
		
		Student student = (Student) request.getSession().getAttribute("student");
		TaskBook taskBook = null;
		StagePlan stagePlan = null;
		try{
			//获取任务书信息
			taskBook = studentFlowManageSer.findTaskByStuId(student.getUserId());
			//获取时间安排
			stagePlan = studentFlowManageSer.findStagePlan("task_book");
		}catch (Exception e){
			e.printStackTrace();
		}
		if(stagePlan!=null){
			Date limitDate =taskBook.getLimitTime();
			Date nowDate = new Date();//当前时间
			Date endDate = stagePlan.getEndTime();//阶段安排结束时间
			//判断时间限制
			if(nowDate.getTime()>endDate.getTime()){//比较结束时间
				if(limitDate!=null){//判断是否存在特殊延期时间
					if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
						model.addAttribute("errorMsg","任务书提交延期已结束,不能撤回");
						return "forward:taskBook.shtm";
					}
				}else{//无,直接返回超时提示
					model.addAttribute("errorMsg","任务书提交已结束,不能撤回");
					return "forward:taskBook.shtm";
				}
			}
		}else{
			model.addAttribute("errorMsg","未查询到阶段安排时间,系统错误");
			return "forward:taskBook.shtm";
		}
		if(taskBook!=null){
			//判断是否在审核状态中	
			if(!taskBook.getTeaStatus().equals("0")||!taskBook.getLeaderStatus().equals("0")){
				model.addAttribute("errorMsg","任务书未在审核状态中,不能撤回");
				return "forward:taskBook.shtm";
			}
			try{
				//删除服务器文件
				File serverFile = new File(taskBook.getTaskBookPath());
				if(serverFile.exists()){
					Boolean bool = serverFile.delete();
					if(!bool){
						model.addAttribute("errorMsg","执行删除操作失败,请稍后重试");
						return "forward:taskBook.shtm";
					}
				}else{
					model.addAttribute("errorMsg","服务器中找不到相关文件,任务书记录已删除");
					studentFlowManageSer.deleteTaskBookById(id);
					return "forward:taskBook.shtm";
				}
				int i = studentFlowManageSer.deleteTaskBookById(id);		
				if(i==0){
					model.addAttribute("errorMsg","系统错误");
					return "error/error.jsp";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		//删除待办事项记录
		List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("taskBook")){
					if(backLogs.get(q).getNumbers()==1){
						int n = publiSer.removeBackLog(backLogs.get(q).getId());
					}else{
						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
					}
				}
			}		
		}
		return "redirect:taskBook.shtm";
	}
	
	/**
	 * @Title: taskBookOnline
	 * @Description:在线填写任务书--页面初始化
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/taskBookOnline.shtm")
	public String taskBookOnline(HttpServletRequest request, HttpServletResponse response){
		
		return "Student/taskBookOnline.jsp";
	}
	
     @RequestMapping("/downloadFile.shtm")    
     public ResponseEntity<byte[]> download(Model model,String filePath,
 			HttpServletRequest request, HttpServletResponse response){     
         String path = null;
		try {
			path = new String(filePath.getBytes("iso8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			}
    	 String name = path.substring(path.lastIndexOf(File.separator)+1,path.length());
    	 File file=new File(path);  
         HttpHeaders headers = new HttpHeaders();    
         String fileName = null;
         try {
			fileName = new String(name.getBytes("UTF-8"),"iso-8859-1");
         } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
         }//为了解决中文名称乱码问题  
         headers.setContentDispositionFormData("attachment", fileName);   
         headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
         try{
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
			                                   headers, HttpStatus.CREATED);
		 }catch (IOException e) {
			e.printStackTrace();
		}
		return null;    
     }    
	 
     //********************开题报告********************
     /**
 	 * @Title: openingReport
 	 * @Description: 提交开题报告页面初始化
 	 * @param model
 	 * @param map
 	 * @param request
 	 * @param response
 	 * @return String 
 	 * @throws
 	 */
 	@RequestMapping("/openingReport.shtm")
 	public String openingReport(Model model,Map<String, Object> map, 
     		HttpServletRequest request, HttpServletResponse response){
 		Student student = (Student) request.getSession().getAttribute("student");
 		//获取阶段安排时间
 		StagePlan stagePlan =null;
 		try{
 			stagePlan = studentFlowManageSer.findStagePlan("opening_report");
 		}catch (Exception e) {
			e.printStackTrace();
		}
 		if(stagePlan!=null){
	 		Date start = stagePlan.getStartTime();//阶段安排开始时间
	 		Date end = stagePlan.getEndTime();//阶段安排结束时间
	 		//时间转换中文
	 		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	 		String startDate = formatter.format(start);
	 		String endDate = formatter.format(end);
	 		model.addAttribute("startDate", startDate);
	 		model.addAttribute("endDate", endDate);
 		}else{
 			model.addAttribute("errorMsg","该阶段尚未开始");
			model.addAttribute("startDate", "");
			model.addAttribute("endDate", "");	
 		}
 		try{
 			//获取开题报告信息
 			OpeningReport openingReport = studentFlowManageSer.findReportByStuId(student.getUserId());
 	 		model.addAttribute("openingReport",openingReport);
 		}catch (Exception e) {
			e.printStackTrace();
		}	
 		try{
	 		//查询是否有关于更新的待办事项提示,如果有,删除此条提示
			List<BackLog> backLogs = publiSer.findBackLog(student.getUserId(), student.getMajor(),"student");
			if(!backLogs.isEmpty()){
				for(int i=0;i<backLogs.size();i++){
					BackLog backLog = backLogs.get(i);
					if(backLog.getBackLog().equals("reOpeningReport")){
						int j = publiSer.removeBackLog(backLog.getId());
						if(j==0){
							model.addAttribute("errorMsg","系统错误");
							return "error/error.jsp";
						}
					}
				}
			}
 		}catch (Exception e) {
			e.printStackTrace();
		}
 		return "Student/openingReportSubmit.jsp";
 	}
 	
 	/**
 	 * @Title: submitOpeningReport
 	 * @Description: 接收开题报告文件
 	 * @param model
 	 * @param map
 	 * @param request
 	 * @param response
 	 * @return
 	 * @throws Exception String 
 	 * @throws
 	 */
 	@RequestMapping("/openingReport_submitReport.shtm")
 	public String submitOpeningReport(Model model,Map<String, Object> map, 
     		HttpServletRequest request, HttpServletResponse response){		
 		
 		Student student = (Student) request.getSession().getAttribute("student");
 		if(StringUtils.isBlank(student.getZdTeacher())){
			model.addAttribute("errorMsg","尚未选择导师,若确认已选导师,请尝试重新登录");
			return "forward:openingReport.shtm";
		}
 		//获取阶段安排时间
 		StagePlan stagePlan = null;
 		OpeningReport openingReport = null;
 		try{
 			stagePlan =	studentFlowManageSer.findStagePlan("opening_report");	
 			openingReport = studentFlowManageSer.findReportByStuId(student.getUserId());
 		}catch (Exception e){
			e.printStackTrace();
		}
 		if(stagePlan==null){
 			model.addAttribute("errorMsg","阶段尚未开始");
			return "forward:openingReport.shtm";
 		}
 		Date limitDate =null;
 		Date nowDate = new Date();//当前时间
 		Date startDate = stagePlan.getStartTime();//阶段安排开始时间
 		Date endDate = stagePlan.getEndTime();//阶段安排结束时间
 		//判断是否重复提交
 		if(openingReport!=null){
 			if((!openingReport.getTeaStatus().equals("2"))&&(!openingReport.getLeaderStatus().equals("2"))){
 				model.addAttribute("errorMsg","已提交过开题报告,不能重复提交");
 				return "forward:openingReport.shtm";
 			}
 			limitDate=openingReport.getLimitTime();
 		}
 		//判断时间限制
 		if(nowDate.getTime()<startDate.getTime()){//比较开始时间
 			model.addAttribute("errorMsg","开题报告提交尚未开始");
 			return "forward:openingReport.shtm";
 		}
 		if(nowDate.getTime()>endDate.getTime()){//比较结束时间
 			if(limitDate!=null){//判断是否存在特殊延期时间
 				if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
 					model.addAttribute("errorMsg","开题报告提交延期已结束");
 					return "forward:openingReport.shtm";
 				}
 			}else{//无,直接返回超时提示
 				model.addAttribute("errorMsg","开题报告提交已结束");
 				return "forward:openingReport.shtm";
 			}
 		}
 		//获取文件流
 		MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest) request;
 		MultipartFile file = multipartRequest.getFile("taskFile");
 		if(!file.isEmpty()){
 			//重命名文件(开题报告-姓名-学号)
 			String fileOldName = file.getOriginalFilename();
 			String fileName = "openingReport"+"-"+student.getUserNum();
 			if (fileOldName.indexOf(".") != -1) {  
 	            fileName += fileOldName.substring(fileOldName.lastIndexOf("."));  
 			}  	
 			String filePath = null;
 			try{
 			//文件服务器存储地址
 			 String rootFile = SysConfig.getValue("term");//当前届文件夹
 			 String rootPath = request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录
              File dir = new File(rootPath + File.separator+student.getUserNum());
              if (!dir.exists())//若地址文件夹不存在,创建文件夹
                  dir.mkdirs();
              //写文件到服务器
              filePath = dir.getAbsolutePath() + File.separator +fileName;
              File serverFile = new File(filePath);
              file.transferTo(serverFile);
 			}catch (Exception e){
				e.printStackTrace();
			}
              if(openingReport!=null){//记录不为空,之前上传过开题报告,重新上传,update覆盖原有记录
      			map.put("teaStatus","0");
      			map.put("leaderStatus","0");
      			map.put("openingReport",filePath);
      			map.put("createTime", nowDate);
      			try{
	      			int j = studentFlowManageSer.updateOpeningReportById(student.getUserId(), map);
	      			if(j==0){
	      				model.addAttribute("errorMsg","系统错误");
	      				return "error/error.jsp";
	      			}
      			}catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("errorMsg","系统错误");
      				return "error/error.jsp";
				}
      			//添加代办事项记录
       			List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
       			Boolean bool = false;
       			if(!backLogs.isEmpty()){
       				for(int q=0;q<backLogs.size();q++){
       					if(backLogs.get(q).getBackLog().equals("openingReport")){
       						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
       						bool=true;						
       						break;
       					}
       				}		
       			}
       			if(!bool){		
       				BackLog backLog = new BackLog();
       				backLog.setId(UuidHelper.getRandomUUID());
       				backLog.setBackLog("openingReport");
       				backLog.setTeaId(student.getZdTeacher());
       				backLog.setTeaStatus("0");
       				backLog.setCreateTime(nowDate);
       				backLog.setCreateUser(student.getStuName());
       				int b = publiSer.insertBackLog(backLog);
       				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
       			}     			
      			return "redirect:openingReport.shtm";
              }          
              //数据表中不存在记录,重新创建对象并insert表中
              openingReport = new OpeningReport();
              openingReport.setId(UuidHelper.getRandomUUID());
              openingReport.setStuId(student.getUserId());
              openingReport.setTeaId(student.getZdTeacher());
              openingReport.setTeaStatus("0");
              openingReport.setLeaderStatus("0");
              openingReport.setOpeningReportPath(filePath);
              openingReport.setCreateUser(student.getStuName());
              openingReport.setCreateTime(nowDate);
              try{
	              int i =studentFlowManageSer.addOpeningReport(openingReport);
	              if(i==0){
	              	 model.addAttribute("errorMsg","系统错误");
	       			 return "error/error.jsp";
	               }
              }catch (Exception e){
				e.printStackTrace();
			}
              //添加代办事项记录
   			List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
   			Boolean bool = false;
   			if(!backLogs.isEmpty()){
   				for(int q=0;q<backLogs.size();q++){
   					if(backLogs.get(q).getBackLog().equals("openingReport")){
   						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
   						bool=true;						
   						break;
   					}
   				}		
   			}
   			if(!bool){		
   				BackLog backLog = new BackLog();
   				backLog.setId(UuidHelper.getRandomUUID());
   				backLog.setBackLog("openingReport");
   				backLog.setTeaId(student.getZdTeacher());
   				backLog.setTeaStatus("0");
   				backLog.setCreateTime(nowDate);
   				backLog.setCreateUser(student.getStuName());
   				int b = publiSer.insertBackLog(backLog);
   				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
   			}
              
              return "redirect:openingReport.shtm";
 		}
 		return null;
 	}
 	
 	@RequestMapping("/openingReport_cancel.shtm")
 	public String cancelOpeningReport(Model model, String id,Map<String, Object> map,
 			HttpServletRequest request, HttpServletResponse response){
 		
 		Student student = (Student) request.getSession().getAttribute("student");
 		OpeningReport openingReport = null;
 		StagePlan stagePlan = null;		
 		try{
	 		//获取开题报告信息
	 		openingReport = studentFlowManageSer.findReportByStuId(student.getUserId());
	 		//获取时间安排
	 		stagePlan = studentFlowManageSer.findStagePlan("opening_report");
 		}catch (Exception e) {
			e.printStackTrace();
		}
 		if(stagePlan == null){
 			model.addAttribute("errorMsg","阶段尚未开始");
			return "forward:openingReport.shtm";
 		}
 		Date limitDate =openingReport.getLimitTime();
 		Date nowDate = new Date();//当前时间
 		Date endDate = stagePlan.getEndTime();//阶段安排结束时间
 		//判断时间限制
 		if(nowDate.getTime()>endDate.getTime()){//比较结束时间
 			if(limitDate!=null){//判断是否存在特殊延期时间
 				if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
 					model.addAttribute("errorMsg","开题报告提交延期已结束,不能撤回");
 					return "forward:openingReport.shtm";
 				}
 			}else{//无,直接返回超时提示
 				model.addAttribute("errorMsg","开题报告提交已结束,不能撤回");
 				return "forward:openingReport.shtm";
 			}
 		}
 		if(openingReport!=null){
	 		//判断是否在审核状态中	
	 		if(!openingReport.getTeaStatus().equals("0")||!openingReport.getLeaderStatus().equals("0")){
	 			model.addAttribute("errorMsg","开题报告未在审核状态中,不能撤回");
	 			return "forward:openingReport.shtm";
	 		}	
	 		try{
		 		//删除服务器文件
		 		File serverFile = new File(openingReport.getOpeningReportPath());
		 		if(serverFile.exists()){
		 			Boolean bool = serverFile.delete();
		 			if(!bool){
		 				model.addAttribute("errorMsg","执行删除操作失败,请稍后重试");
		 				return "forward:openingReport.shtm";
		 			}
		 		}else{
		 			model.addAttribute("errorMsg","服务器中找不到相关文件,开题报告记录已删除");
		 		    studentFlowManageSer.deleteOpeningReportById(id);
		 			return "forward:openingReport.shtm";
		 		}
		 		int i = studentFlowManageSer.deleteOpeningReportById(id);
		 		if(i==0){
		 			model.addAttribute("errorMsg","系统错误");
		 			return "error/error.jsp";
		 		}
	 		}catch (Exception e) {
				e.printStackTrace();
			}
 		}
 		//删除待办事项记录
		List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("openingReport")){
					if(backLogs.get(q).getNumbers()==1){
						int n = publiSer.removeBackLog(backLogs.get(q).getId());
					}else{
						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
					}
				}
			}		
		}
 		return "redirect:openingReport.shtm";
 	}
 	
 	 //********************中期检查********************
    /**
	 * @Title: midCheck
	 * @Description: 提交中期检查页面初始化
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/midCheck.shtm")
	public String midCheck(Model model,Map<String, Object> map, 
    		HttpServletRequest request, HttpServletResponse response){
		Student student = (Student) request.getSession().getAttribute("student");
		StagePlan stagePlan = null;
		try{
			//获取阶段安排时间
			stagePlan = studentFlowManageSer.findStagePlan("mid_check");
		}catch (Exception e){
			e.printStackTrace();
		}
		if(stagePlan!=null){
	 		Date start = stagePlan.getStartTime();//阶段安排开始时间
	 		Date end = stagePlan.getEndTime();//阶段安排结束时间
	 		//时间转换中文
	 		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	 		String startDate = formatter.format(start);
	 		String endDate = formatter.format(end);
	 		model.addAttribute("startDate", startDate);
	 		model.addAttribute("endDate", endDate);
 		}else{
 			model.addAttribute("errorMsg","该阶段尚未开始");
			model.addAttribute("startDate", "");
			model.addAttribute("endDate", "");	
 		}
		try{
			//获取中期检查信息
			MidCheck midCheck = studentFlowManageSer.findMidCheckByStuId(student.getUserId());	
			model.addAttribute("midCheck",midCheck);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//查询是否有关于更新的待办事项提示,如果有,删除此条提示
		List<BackLog> backLogs = publiSer.findBackLog(student.getUserId(), student.getMajor(),"student");
		if(!backLogs.isEmpty()){
			for(int i=0;i<backLogs.size();i++){
				BackLog backLog = backLogs.get(i);
				if(backLog.getBackLog().equals("reMidCheck")){
					int j = publiSer.removeBackLog(backLog.getId());
					if(j==0){
						model.addAttribute("errorMsg","系统错误");
						return "error/error.jsp";
					}
				}
			}
		}
		return "Student/midCheckSubmit.jsp";
	}
	
	/**
	 * @Title: submitMidCheck
	 * @Description: 接收中期检查文件
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception String 
	 * @throws
	 */
	@RequestMapping("/midCheck_submitMidCheck.shtm")
	public String submitMidCheck(Model model,Map<String, Object> map, 
    		HttpServletRequest request, HttpServletResponse response){		
		
		Student student = (Student) request.getSession().getAttribute("student");
		if(StringUtils.isBlank(student.getZdTeacher())){
			model.addAttribute("errorMsg","尚未选择导师,若确认已选导师,请尝试重新登录");
			return "forward:midCheck.shtm";
		}
		//获取阶段安排时间
		StagePlan stagePlan = null;
		MidCheck midCheck = null;
		try{
			stagePlan =	studentFlowManageSer.findStagePlan("mid_check");	
			midCheck = studentFlowManageSer.findMidCheckByStuId(student.getUserId());
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(stagePlan==null){
			model.addAttribute("errorMsg","阶段尚未开始");
			return "forward:midCheck.shtm";
		}
		Date limitDate =null;
		Date nowDate = new Date();//当前时间
		Date startDate = stagePlan.getStartTime();//阶段安排开始时间
		Date endDate = stagePlan.getEndTime();//阶段安排结束时间
		//判断是否重复提交
		if(midCheck!=null){
			if((!midCheck.getTeaStatus().equals("2"))){
				model.addAttribute("errorMsg","已提交过中期检查,不能重复提交");
				return "forward:midCheck.shtm";
			}
			limitDate=midCheck.getLimitTime();
		}
		//判断时间限制
		if(nowDate.getTime()<startDate.getTime()){//比较开始时间
			model.addAttribute("errorMsg","中期检查提交尚未开始");
			return "forward:midCheck.shtm";
		}
		if(nowDate.getTime()>endDate.getTime()){//比较结束时间
			if(limitDate!=null){//判断是否存在特殊延期时间
				if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
					model.addAttribute("errorMsg","中期检查提交延期已结束");
					return "forward:midCheck.shtm";
				}
			}else{//无,直接返回超时提示
				model.addAttribute("errorMsg","中期检查提交已结束");
				return "forward:midCheck.shtm";
			}
		}
		MultipartHttpServletRequest  multipartRequest =null;
		MultipartFile file = null;
		try{
			//获取文件流
			multipartRequest = (MultipartHttpServletRequest) request;
			file = multipartRequest.getFile("taskFile");
		}catch (Exception e){
			e.printStackTrace();
		}
		if(!file.isEmpty()){
			//重命名文件(中期检查-姓名-学号)
			String fileOldName = file.getOriginalFilename();
			String fileName = "midCheck"+"-"+student.getUserNum();
			if (fileOldName.indexOf(".") != -1) {  
	            fileName += fileOldName.substring(fileOldName.lastIndexOf("."));  
			}  	  
			//文件服务器存储地址
			 String rootFile = SysConfig.getValue("term");//当前届文件夹
			 String rootPath = request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录(年级/专业/班级/学号)
             File dir = new File(rootPath + File.separator+student.getUserNum());
             if (!dir.exists())//若地址文件夹不存在,创建文件夹
                 dir.mkdirs();
             //写文件到服务器
             String filePath = dir.getAbsolutePath() + File.separator +fileName;
             File serverFile = new File(filePath);
             try {
				file.transferTo(serverFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
             if(midCheck!=null){//记录不为空,之前上传过中期检查,重新上传,update覆盖原有记录
     			map.put("teaStatus","0");
     			map.put("leaderStatus","0");
     			map.put("midCheck",filePath);
     			map.put("createTime", nowDate);
     			try{
	     			int j = studentFlowManageSer.updateMidCheckById(student.getUserId(), map);
	     			if(j==0){
	     				model.addAttribute("errorMsg","系统错误");
	     				return "error/error.jsp";
	     			}
     			}catch (Exception e){
					e.printStackTrace();
				}
     			//添加代办事项记录
    			List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
    			Boolean bool = false;
    			if(!backLogs.isEmpty()){
    				for(int q=0;q<backLogs.size();q++){
    					if(backLogs.get(q).getBackLog().equals("midCheck")){
    						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
    						bool=true;						
    						break;
    					}
    				}		
    			}
    			if(!bool){		
    				BackLog backLog = new BackLog();
    				backLog.setId(UuidHelper.getRandomUUID());
    				backLog.setBackLog("midCheck");
    				backLog.setTeaId(student.getZdTeacher());
    				backLog.setTeaStatus("0");		
    				backLog.setCreateTime(nowDate);
    				backLog.setCreateUser(student.getStuName());
    				int b = publiSer.insertBackLog(backLog);
    				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
    			}  			
     			return "redirect:midCheck.shtm";
             }
             //数据表中不存在记录,重新创建对象并insert表中
             midCheck = new MidCheck();
             midCheck.setId(UuidHelper.getRandomUUID());
             midCheck.setStuId(student.getUserId());
             midCheck.setTeaId(student.getZdTeacher());
             midCheck.setTeaStatus("0");
             midCheck.setLeaderStatus("0");
             midCheck.setMidCheckPath(filePath);
             midCheck.setCreateUser(student.getStuName());
             midCheck.setCreateTime(nowDate);
             try{
	             int i =studentFlowManageSer.addMidCheck(midCheck);
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
    			List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
    			Boolean bool = false;
    			if(!backLogs.isEmpty()){
    				for(int q=0;q<backLogs.size();q++){
    					if(backLogs.get(q).getBackLog().equals("midCheck")){
    						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
    						bool=true;						
    						break;
    					}
    				}		
    			}
    			if(!bool){		
    				BackLog backLog = new BackLog();
    				backLog.setId(UuidHelper.getRandomUUID());
    				backLog.setBackLog("midCheck");
    				backLog.setTeaId(student.getZdTeacher());
    				backLog.setTeaStatus("0");		
    				backLog.setCreateTime(nowDate);
    				backLog.setCreateUser(student.getStuName());
    				int b = publiSer.insertBackLog(backLog);
    				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
    			}
             
             return "redirect:midCheck.shtm";
		}
		return null;
	}
	
	@RequestMapping("/midCheck_cancel.shtm")
	public String cancelMidCheck(Model model, String id,Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response){
		
		Student student = (Student) request.getSession().getAttribute("student");
		MidCheck midCheck  =null;
		StagePlan stagePlan =null;
		try{
			//获取中期检查信息
			midCheck = studentFlowManageSer.findMidCheckByStuId(student.getUserId());
			//获取时间安排
		    stagePlan = studentFlowManageSer.findStagePlan("mid_check");
		}catch (Exception e) {
			e.printStackTrace();
		}
		Date limitDate =midCheck.getLimitTime();
		Date nowDate = new Date();//当前时间
		Date endDate = stagePlan.getEndTime();//阶段安排结束时间
		//判断时间限制
		if(nowDate.getTime()>endDate.getTime()){//比较结束时间
			if(limitDate!=null){//判断是否存在特殊延期时间
				if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
					model.addAttribute("errorMsg","中期检查提交延期已结束,不能撤回");
					return "forward:midCheck.shtm";
				}
			}else{//无,直接返回超时提示
				model.addAttribute("errorMsg","中期检查提交已结束,不能撤回");
				return "forward:midCheck.shtm";
			}
		}
		//判断是否在审核状态中	
		if(!midCheck.getTeaStatus().equals("0")){
			model.addAttribute("errorMsg","中期检查未在审核状态中,不能撤回");
			return "forward:midCheck.shtm";
		}
		try{
			//删除服务器文件
			File serverFile = new File(midCheck.getMidCheckPath());
			if(serverFile.exists()){
				Boolean bool = serverFile.delete();
				if(!bool){
					model.addAttribute("errorMsg","执行删除操作失败,请稍后重试");
					return "forward:midCheck.shtm";
				}
			}else{
				model.addAttribute("errorMsg","服务器中找不到相关文件,中期检查记录已删除");
				studentFlowManageSer.deleteMidCheckById(id);
				return "forward:midCheck.shtm";
			}
			int i = studentFlowManageSer.deleteMidCheckById(id);
			if(i==0){
				model.addAttribute("errorMsg","系统错误");
				return "error/error.jsp";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
		//删除待办事项记录
		List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("midCheck")){
					if(backLogs.get(q).getNumbers()==1){
						int n = publiSer.removeBackLog(backLogs.get(q).getId());
					}else{
						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
					}
				}
			}		
		}
		return "redirect:midCheck.shtm";
	}
	
	 //********************论文初稿********************
    /**
	 * @Title: firstPaper
	 * @Description: 提交论文初稿页面初始化
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/firstPaper.shtm")
	public String firstPaper(Model model,Map<String, Object> map, 
    		HttpServletRequest request, HttpServletResponse response){
		Student student = (Student) request.getSession().getAttribute("student");
		StagePlan stagePlan = null;
		//获取阶段安排时间
		try{
			stagePlan = studentFlowManageSer.findStagePlan("first_paper");
		}catch (Exception e){
			e.printStackTrace();
		}
		if(stagePlan!=null){
	 		Date start = stagePlan.getStartTime();//阶段安排开始时间
	 		Date end = stagePlan.getEndTime();//阶段安排结束时间
	 		//时间转换中文
	 		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	 		String startDate = formatter.format(start);
	 		String endDate = formatter.format(end);
	 		model.addAttribute("startDate", startDate);
	 		model.addAttribute("endDate", endDate);
 		}else{
 			model.addAttribute("errorMsg","该阶段尚未开始");
			model.addAttribute("startDate", "");
			model.addAttribute("endDate", "");	
 		}
		try{
			//获取论文初稿信息
			FirstPaper firstPaper = studentFlowManageSer.findFirstPaperByStuId(student.getUserId());
			model.addAttribute("firstPaper",firstPaper);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//查询是否有关于更新的待办事项提示,如果有,删除此条提示
		List<BackLog> backLogs = publiSer.findBackLog(student.getUserId(), student.getMajor(),"student");
		if(!backLogs.isEmpty()){
			for(int i=0;i<backLogs.size();i++){
				BackLog backLog = backLogs.get(i);
				if(backLog.getBackLog().equals("reFirstPaper")){
					int j = publiSer.removeBackLog(backLog.getId());
					if(j==0){
						model.addAttribute("errorMsg","系统错误");
						return "error/error.jsp";
					}
				}
			}
		}	
		return "Student/firstPaperSubmit.jsp";
	}
	
	/**
	 * @Title: submitFirstPaper
	 * @Description: 接收论文初稿文件
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception String 
	 * @throws
	 */
	@RequestMapping("/firstPaper_submitFirstPaper.shtm")
	public String submitFirstPaper(Model model,Map<String, Object> map, 
    		HttpServletRequest request, HttpServletResponse response){		
		
		Student student = (Student) request.getSession().getAttribute("student");
		if(StringUtils.isBlank(student.getZdTeacher())){
			model.addAttribute("errorMsg","尚未选择导师,若确认已选导师,请尝试重新登录");
			return "forward:firstPaper.shtm";
		}
		//获取阶段安排时间
		StagePlan stagePlan = null;
		FirstPaper firstPaper = null;
		try{
			stagePlan =	studentFlowManageSer.findStagePlan("first_paper");	
			firstPaper = studentFlowManageSer.findFirstPaperByStuId(student.getUserId());
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(stagePlan==null){
			model.addAttribute("errorMsg","该阶段尚未开始");
			return "forward:firstPaper.shtm";
		}
		Date limitDate =null;
		Date nowDate = new Date();//当前时间
		Date startDate = stagePlan.getStartTime();//阶段安排开始时间
		Date endDate = stagePlan.getEndTime();//阶段安排结束时间
		//判断是否重复提交
		if(firstPaper!=null){
			if((!firstPaper.getTeaStatus().equals("2"))){
				model.addAttribute("errorMsg","已提交过论文初稿,不能重复提交");
				return "forward:firstPaper.shtm";
			}
			limitDate=firstPaper.getLimitTime();
		}
		//判断时间限制
		if(nowDate.getTime()<startDate.getTime()){//比较开始时间
			model.addAttribute("errorMsg","论文初稿提交尚未开始");
			return "forward:firstPaper.shtm";
		}
		if(nowDate.getTime()>endDate.getTime()){//比较结束时间
			if(limitDate!=null){//判断是否存在特殊延期时间
				if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
					model.addAttribute("errorMsg","论文初稿提交延期已结束");
					return "forward:firstPaper.shtm";
				}
			}else{//无,直接返回超时提示
				model.addAttribute("errorMsg","论文初稿提交已结束");
				return "forward:firstPaper.shtm";
			}
		}
		MultipartHttpServletRequest  multipartRequest = null;
		MultipartFile file = null;
		try{
			//获取文件流
			multipartRequest = (MultipartHttpServletRequest) request;
			file = multipartRequest.getFile("taskFile");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(!file.isEmpty()){
			//重命名文件(论文初稿-姓名-学号)
			String fileOldName = file.getOriginalFilename();
			String fileName = "firstPaper"+"-"+student.getUserNum();
			if (fileOldName.indexOf(".") != -1) {  
	            fileName += fileOldName.substring(fileOldName.lastIndexOf("."));  
			}  	  
			//文件服务器存储地址
			 String rootFile = SysConfig.getValue("term");//当前届文件夹
			 String rootPath = request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录(年级/专业/班级/学号)
             File dir = new File(rootPath + File.separator+student.getUserNum());
             if (!dir.exists())//若地址文件夹不存在,创建文件夹
                 dir.mkdirs();
             //写文件到服务器
             String filePath = dir.getAbsolutePath() + File.separator +fileName;
             File serverFile = new File(filePath);
             try {
				file.transferTo(serverFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
             if(firstPaper!=null){//记录不为空,之前上传过论文初稿,重新上传,update覆盖原有记录
     			map.put("teaStatus","0");
     			map.put("leaderStatus","0");
     			map.put("firstPaper",filePath);
     			map.put("createTime", nowDate);
     			int j = studentFlowManageSer.updateFirstPaperById(student.getUserId(), map);
     			if(j==0){
     				model.addAttribute("errorMsg","系统错误");
     				return "error/error.jsp";
     			}
     			//添加代办事项记录
     			List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
     			Boolean bool = false;
     			if(!backLogs.isEmpty()){
     				for(int q=0;q<backLogs.size();q++){
     					if(backLogs.get(q).getBackLog().equals("firstPaper")){
     						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
     						bool=true;						
     						break;
     					}
     				}		
     			}
     			if(!bool){		
     				BackLog backLog = new BackLog();
     				backLog.setId(UuidHelper.getRandomUUID());
     				backLog.setBackLog("firstPaper");
     				backLog.setTeaId(student.getZdTeacher());
     				backLog.setTeaStatus("0");		
     				backLog.setCreateTime(nowDate);
     				backLog.setCreateUser(student.getStuName());
     				int b = publiSer.insertBackLog(backLog);
     				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
     			}
     			
     			return "redirect:firstPaper.shtm";
             }      
             //数据表中不存在记录,重新创建对象并insert表中
             firstPaper = new FirstPaper();
             firstPaper.setId(UuidHelper.getRandomUUID());
             firstPaper.setStuId(student.getUserId());
             firstPaper.setTeaId(student.getZdTeacher());
             firstPaper.setTeaStatus("0");
             firstPaper.setLeaderStatus("0");
             firstPaper.setFirstPaperPath(filePath);
             firstPaper.setCreateUser(student.getStuName());
             firstPaper.setCreateTime(nowDate);
             try{
	             int i =studentFlowManageSer.addFirstPaper(firstPaper);
	             if(i==0){
	            	 model.addAttribute("errorMsg","系统错误");
	     			 return "error/error.jsp";
	             }
             }catch (Exception e) {
				e.printStackTrace();
			}
             //添加代办事项记录
 			List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
 			Boolean bool = false;
 			if(!backLogs.isEmpty()){
 				for(int q=0;q<backLogs.size();q++){
 					if(backLogs.get(q).getBackLog().equals("firstPaper")){
 						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
 						bool=true;						
 						break;
 					}
 				}		
 			}
 			if(!bool){		
 				BackLog backLog = new BackLog();
 				backLog.setId(UuidHelper.getRandomUUID());
 				backLog.setBackLog("firstPaper");
 				backLog.setTeaId(student.getZdTeacher());
 				backLog.setTeaStatus("0");		
 				backLog.setCreateTime(nowDate);
 				backLog.setCreateUser(student.getStuName());
 				int b = publiSer.insertBackLog(backLog);
 				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
 			}
             
             return "redirect:firstPaper.shtm";
		}
		return null;
	}
	
	@RequestMapping("/firstPaper_cancel.shtm")
	public String cancelfirstPaper(Model model, String id,Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response){
		
		Student student = (Student) request.getSession().getAttribute("student");
		FirstPaper firstPaper = null;
		StagePlan stagePlan = null;
		try{
			//获取论文初稿信息
			firstPaper = studentFlowManageSer.findFirstPaperByStuId(student.getUserId());
			//获取时间安排
			stagePlan = studentFlowManageSer.findStagePlan("first_paper");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(stagePlan==null){
			 model.addAttribute("errorMsg","该阶段尚未开始");
			 return "forward:firstPaper.shtm";
		}
		Date limitDate =firstPaper.getLimitTime();
		Date nowDate = new Date();//当前时间
		Date endDate = stagePlan.getEndTime();//阶段安排结束时间
		//判断时间限制
		if(nowDate.getTime()>endDate.getTime()){//比较结束时间
			if(limitDate!=null){//判断是否存在特殊延期时间
				if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
					model.addAttribute("errorMsg","论文初稿提交延期已结束,不能撤回");
					return "forward:firstPaper.shtm";
				}
			}else{//无,直接返回超时提示
				model.addAttribute("errorMsg","论文初稿提交已结束,不能撤回");
				return "forward:firstPaper.shtm";
			}
		}
		//判断是否在审核状态中	
		if(!firstPaper.getTeaStatus().equals("0")){
			model.addAttribute("errorMsg","论文初稿未在审核状态中,不能撤回");
			return "forward:firstPaper.shtm";
		}
		//删除服务器文件
		File serverFile = new File(firstPaper.getFirstPaperPath());
		if(serverFile.exists()){
			Boolean bool = serverFile.delete();
			if(!bool){
				model.addAttribute("errorMsg","执行删除操作失败,请稍后重试");
				return "forward:firstPaper.shtm";
			}
		}else{
			model.addAttribute("errorMsg","服务器中找不到相关文件,论文初稿记录已删除");
			studentFlowManageSer.deleteFirstPaperById(id);
			return "forward:firstPaper.shtm";
		}
		try{
			int i = studentFlowManageSer.deleteFirstPaperById(id);
			if(i==0){
				model.addAttribute("errorMsg","系统错误");
				return "error/error.jsp";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		//删除待办事项记录
		List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("firstPaper")){
					if(backLogs.get(q).getNumbers()==1){
						int n = publiSer.removeBackLog(backLogs.get(q).getId());
					}else{
						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
					}
				}
			}		
		}
		return "redirect:firstPaper.shtm";
	}
	
	 //********************论文定稿********************
    /**
	 * @Title: finalPaper
	 * @Description: 提交论文定稿页面初始化
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/finalPaper.shtm")
	public String finalPaper(Model model,Map<String, Object> map, 
    		HttpServletRequest request, HttpServletResponse response){
		Student student = (Student) request.getSession().getAttribute("student");
		StagePlan stagePlan = null;
		try{
			//获取阶段安排时间
			stagePlan = studentFlowManageSer.findStagePlan("final_paper");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(stagePlan!=null){
	 		Date start = stagePlan.getStartTime();//阶段安排开始时间
	 		Date end = stagePlan.getEndTime();//阶段安排结束时间
	 		//时间转换中文
	 		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	 		String startDate = formatter.format(start);
	 		String endDate = formatter.format(end);
	 		model.addAttribute("startDate", startDate);
	 		model.addAttribute("endDate", endDate);
 		}else{
 			model.addAttribute("errorMsg","该阶段尚未开始");
			model.addAttribute("startDate", "");
			model.addAttribute("endDate", "");	
 		}
		try{
			//获取论文定稿信息
			FinalPaper finalPaper = studentFlowManageSer.findFinalPaperByStuId(student.getUserId());
			model.addAttribute("finalPaper",finalPaper);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//查询是否有关于更新的待办事项提示,如果有,删除此条提示
		List<BackLog> backLogs = publiSer.findBackLog(student.getUserId(), student.getMajor(),"student");
		if(!backLogs.isEmpty()){
			for(int i=0;i<backLogs.size();i++){
				BackLog backLog = backLogs.get(i);
				if(backLog.getBackLog().equals("reFinalPaper")){
					int j = publiSer.removeBackLog(backLog.getId());
					if(j==0){
						model.addAttribute("errorMsg","系统错误");
						return "error/error.jsp";
					}
				}
			}
		}
		return "Student/finalPaperSubmit.jsp";
	}
	
	/**
	 * @Title: submitFinalPaper
	 * @Description: 接收论文定稿文件
	 * @param model
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception String 
	 * @throws
	 */
	@RequestMapping("/finalPaper_submitFinalPaper.shtm")
	public String submitFinalPaper(Model model,Map<String, Object> map, 
    		HttpServletRequest request, HttpServletResponse response){		
		
		Student student = (Student) request.getSession().getAttribute("student");
		if(StringUtils.isBlank(student.getZdTeacher())){
			model.addAttribute("errorMsg","尚未选择导师,若确认已选导师,请尝试重新登录");
			return "forward:finalPaper.shtm";
		}
		StagePlan stagePlan = null;
		FinalPaper finalPaper = null;
		try{
			//获取阶段安排时间
			stagePlan =	studentFlowManageSer.findStagePlan("final_paper");	
			finalPaper = studentFlowManageSer.findFinalPaperByStuId(student.getUserId());
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(stagePlan==null){
			model.addAttribute("errorMsg","该阶段尚未开始");
			return "forward:finalPaper.shtm";
		}
		Date limitDate =null;
		Date nowDate = new Date();//当前时间
		Date startDate = stagePlan.getStartTime();//阶段安排开始时间
		Date endDate = stagePlan.getEndTime();//阶段安排结束时间
		//判断是否重复提交
		if(finalPaper!=null){
			if(!finalPaper.getTeaStatus().equals("2")){
				model.addAttribute("errorMsg","已提交过论文定稿,不能重复提交");
				return "forward:finalPaper.shtm";
			}
			limitDate=finalPaper.getLimitTime();
		}
		//判断时间限制
		if(nowDate.getTime()<startDate.getTime()){//比较开始时间
			model.addAttribute("errorMsg","论文定稿提交尚未开始");
			return "forward:finalPaper.shtm";
		}
		if(nowDate.getTime()>endDate.getTime()){//比较结束时间
			if(limitDate!=null){//判断是否存在特殊延期时间
				if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
					model.addAttribute("errorMsg","论文定稿提交延期已结束");
					return "forward:finalPaper.shtm";
				}
			}else{//无,直接返回超时提示
				model.addAttribute("errorMsg","论文定稿提交已结束");
				return "forward:finalPaper.shtm";
			}
		}
		MultipartHttpServletRequest  multipartRequest = null;
		MultipartFile file = null;
		try{
		//获取文件流
		multipartRequest = (MultipartHttpServletRequest) request;
		file = multipartRequest.getFile("taskFile");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(!file.isEmpty()){
			//重命名文件(论文定稿-姓名-学号)
			String fileOldName = file.getOriginalFilename();
			String fileName = "finalPaper"+"-"+student.getUserNum();
			if (fileOldName.indexOf(".") != -1) {  
	            fileName += fileOldName.substring(fileOldName.lastIndexOf("."));  
			}  	  
			 //文件服务器存储地址
			 String rootFile = SysConfig.getValue("term");//当前届文件夹
			 String rootPath = request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录(年级/专业/班级/学号)
             File dir = new File(rootPath + File.separator+student.getUserNum());
             if (!dir.exists())//若地址文件夹不存在,创建文件夹
                 dir.mkdirs();
             //写文件到服务器
             String filePath = dir.getAbsolutePath() + File.separator +fileName;
             File serverFile = new File(filePath);
             try {
				file.transferTo(serverFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
             if(finalPaper!=null){//记录不为空,之前上传过论文定稿,重新上传,update覆盖原有记录
     			map.put("teaStatus","0");
     			map.put("leaderStatus","0");
     			map.put("finalPaper",filePath);
     			map.put("createTime", nowDate);
     			try{
	     			int j = studentFlowManageSer.updateFinalPaperById(student.getUserId(), map);
	     			if(j==0){
	     				model.addAttribute("errorMsg","系统错误");
	     				return "error/error.jsp";
	     			}
     			}catch (Exception e) {
					e.printStackTrace();
				}
     			//添加代办事项记录
      			List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
      			Boolean bool = false;
      			if(!backLogs.isEmpty()){
      				for(int q=0;q<backLogs.size();q++){
      					if(backLogs.get(q).getBackLog().equals("finalPaper")){
      						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
      						bool=true;						
      						break;
      					}
      				}		
      			}
      			if(!bool){		
      				BackLog backLog = new BackLog();
      				backLog.setId(UuidHelper.getRandomUUID());
      				backLog.setBackLog("finalPaper");
      				backLog.setTeaId(student.getZdTeacher());
      				backLog.setTeaStatus("0");		
      				backLog.setCreateTime(nowDate);
      				backLog.setCreateUser(student.getStuName());
      				int b = publiSer.insertBackLog(backLog);
      				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
      			}
     			
     			return "redirect:finalPaper.shtm";
             }
             //数据表中不存在记录,重新创建对象并insert表中
             finalPaper = new FinalPaper();
             finalPaper.setId(UuidHelper.getRandomUUID());
             finalPaper.setStuId(student.getUserId());
             finalPaper.setTeaId(student.getZdTeacher());
             finalPaper.setTeaStatus("0");
             finalPaper.setLeaderStatus("0");
             finalPaper.setFinalPaperPath(filePath);
             finalPaper.setCreateUser(student.getStuName());
             finalPaper.setCreateTime(nowDate);
             try{
	             int i =studentFlowManageSer.addFinalPaper(finalPaper);
	             if(i==0){
	            	 model.addAttribute("errorMsg","系统错误");
	     			 return "error/error.jsp";
	             }
             }catch (Exception e) {
				e.printStackTrace();
			}
           //添加代办事项记录
  			List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
  			Boolean bool = false;
  			if(!backLogs.isEmpty()){
  				for(int q=0;q<backLogs.size();q++){
  					if(backLogs.get(q).getBackLog().equals("finalPaper")){
  						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
  						bool=true;						
  						break;
  					}
  				}		
  			}
  			if(!bool){		
  				BackLog backLog = new BackLog();
  				backLog.setId(UuidHelper.getRandomUUID());
  				backLog.setBackLog("finalPaper");
  				backLog.setTeaId(student.getZdTeacher());
  				backLog.setTeaStatus("0");		
  				backLog.setCreateTime(nowDate);
  				backLog.setCreateUser(student.getStuName());
  				int b = publiSer.insertBackLog(backLog);
  				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
  			}
             
             return "redirect:finalPaper.shtm";
		}
		return null;
	}
	
	@RequestMapping("/finalPaper_cancel.shtm")
	public String cancelFinalPaper(Model model, String id,Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response){
		
		Student student = (Student) request.getSession().getAttribute("student");
		FinalPaper finalPaper = null;
		StagePlan stagePlan = null;
		try{
			//获取论文定稿信息
			finalPaper = studentFlowManageSer.findFinalPaperByStuId(student.getUserId());
			//获取时间安排
			stagePlan = studentFlowManageSer.findStagePlan("final_paper");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(stagePlan == null)
		{
			model.addAttribute("errorMsg","该阶段尚未开始");
			return "forward:finalPaper.shtm";
		}
		Date limitDate =finalPaper.getLimitTime();
		Date nowDate = new Date();//当前时间
		Date endDate = stagePlan.getEndTime();//阶段安排结束时间
		//判断时间限制
		if(nowDate.getTime()>endDate.getTime()){//比较结束时间
			if(limitDate!=null){//判断是否存在特殊延期时间
				if(nowDate.getTime()>limitDate.getTime()){//若有,比较延期时间
					model.addAttribute("errorMsg","论文定稿提交延期已结束,不能撤回");
					return "forward:finalPaper.shtm";
				}
			}else{//无,直接返回超时提示
				model.addAttribute("errorMsg","论文定稿提交已结束,不能撤回");
				return "forward:finalPaper.shtm";
			}
		}
		//判断是否在审核状态中	
		if(!finalPaper.getTeaStatus().equals("0")){
			model.addAttribute("errorMsg","论文定稿未在审核状态中,不能撤回");
			return "forward:finalPaper.shtm";
		}
		//删除服务器文件
		File serverFile = new File(finalPaper.getFinalPaperPath());
		if(serverFile.exists()){
			Boolean bool = serverFile.delete();
			if(!bool){
				model.addAttribute("errorMsg","执行删除操作失败,请稍后重试");
				return "forward:finalPaper.shtm";
			}
		}else{
			model.addAttribute("errorMsg","服务器中找不到相关文件,论文定稿记录已删除");
			int i = studentFlowManageSer.deleteFinalPaperById(id);
			return "forward:finalPaper.shtm";
		}
		int i = studentFlowManageSer.deleteFinalPaperById(id);
		if(i==0){
			model.addAttribute("errorMsg","系统错误");
			return "error/error.jsp";
		}
		//删除待办事项记录
		List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
		if(!backLogs.isEmpty()){
			for(int q=0;q<backLogs.size();q++){
				if(backLogs.get(q).getBackLog().equals("finalPaper")){
					if(backLogs.get(q).getNumbers()==1){
						int n = publiSer.removeBackLog(backLogs.get(q).getId());
					}else{
						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
					}
				}
			}		
		}
		return "redirect:finalPaper.shtm";
	}
	
	//**********************课题全部文件提交*************
	/**
	 * @Title: allFile
	 * @Description: 提交项目源码页面初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/allFile.shtm")
	public String allFile(Model model,HttpServletRequest request, HttpServletResponse response)
	{
		Student student = (Student) request.getSession().getAttribute("student");
		Paper paper = null;
		try{
			paper = studentFlowManageSer.selectPaperByStuNum(student.getUserNum());
		}catch (Exception e) {
			e.printStackTrace();
		}
		try{
			if(paper!=null){
				if(StringUtils.isNotBlank(paper.getProjectFile())){
					model.addAttribute("Paper", paper);
				}
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "Student/allFileSubmit.jsp";
	}
	
	/**
	 * @Title: allFileSubmit
	 * @Description: 提交项目源码
	 * @param model
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("/allFile_Submit.shtm")
	public String allFileSubmit(Model model,HttpServletRequest request, HttpServletResponse response)
	{
		
		Student student = (Student) request.getSession().getAttribute("student");
		if(StringUtils.isBlank(student.getZdTeacher())){
			model.addAttribute("errorMsg","尚未选择导师,若确认已选导师,请尝试重新登录");
			return "forward:allFile.shtm";
		}
		MultipartHttpServletRequest  multipartRequest = null;
		MultipartFile file = null;
		try{
			//获取文件流
			multipartRequest = (MultipartHttpServletRequest) request;
			file = multipartRequest.getFile("upfile");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(!file.isEmpty()){
			//重命名文件(课题名-姓名-学号)
			String fileOldName = file.getOriginalFilename();
			String fileName =student.getTitle()+"-"+student.getStuName()+"-"+student.getUserNum();
			if (fileOldName.indexOf(".") != -1) {  
	            fileName += fileOldName.substring(fileOldName.lastIndexOf("."));  
			}  	  
			//文件服务器存储地址
			 String rootFile = SysConfig.getValue("term");//当前届文件夹
			 String rootPath = request.getSession().getServletContext().getRealPath(File.separator)+"attached"+File.separator+rootFile+File.separator+"students";//盘符及根目录(年级/专业/班级/学号)
             File dir = new File(rootPath + File.separator+File.separator+student.getUserNum());
             if (!dir.exists())//若地址文件夹不存在,创建文件夹
                 dir.mkdirs();
             //写文件到服务器
             String filePath = dir.getAbsolutePath() + File.separator +fileName;
             File serverFile = new File(filePath);
             try {
				file.transferTo(serverFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
             Paper rePaper = null;
             try{
            	 //查询之前是否提交过课题文件，如果提交过，待办事项记录减去或删除
            	 rePaper = studentFlowManageSer.selectPaperByStuNum(student.getUserNum());
             }catch (Exception e) {
				e.printStackTrace();
			}
             if(rePaper.getProjectFile()!=null&&(!rePaper.getProjectFile().equals(""))){
            	//删除待办事项记录
         		List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
         		if(!backLogs.isEmpty()){
         			for(int q=0;q<backLogs.size();q++){
         				if(backLogs.get(q).getBackLog().equals("allFile")){
         					if(backLogs.get(q).getNumbers()==1){
         						int n = publiSer.removeBackLog(backLogs.get(q).getId());
         					}else{
         						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"remove");										
         					}
         				}
         			}		
         		}
             }
             //Paper表中已经存在学生记录，直接update即可
             Paper paper = new Paper();
             paper.setStuNum(student.getUserNum());//学号
             paper.setProjectFile(filePath);//文件路径
             try{
	             int i =studentFlowManageSer.submitAllFilePaper(paper);         
	             if(i==0){
	            	 model.addAttribute("errorMsg","系统错误");
	     			 return "error/error.jsp";
	             } 
             }catch (Exception e) {
				e.printStackTrace();
			 }
             //添加代办事项记录
    			List<BackLog> backLogs = publiSer.findBackLog(student.getZdTeacher(),null,"teacher");
    			Boolean bool = false;
    			if(!backLogs.isEmpty()){
    				for(int q=0;q<backLogs.size();q++){
    					if(backLogs.get(q).getBackLog().equals("allFile")){
    						int m = publiSer.updateBackLogNumById(backLogs.get(q).getId(),"add");
    						bool=true;						
    						break;
    					}
    				}		
    			}
    			if(!bool){		
    				BackLog backLog = new BackLog();
    				backLog.setId(UuidHelper.getRandomUUID());
    				backLog.setBackLog("allFile");
    				backLog.setTeaId(student.getZdTeacher());
    				backLog.setTeaStatus("0");		
    				backLog.setCreateTime(new Date());
    				backLog.setCreateUser(student.getStuName());
    				int b = publiSer.insertBackLog(backLog);
    				int m = publiSer.updateBackLogNumById(backLog.getId(),"add");			
    			}
		}
		return "redirect:allFile.shtm";
	}
	
	
}
	

