<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<title></title>
	</head>

	<body>
		<base href="<%=basePath %>"/>
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/major_class.js"></script>
		<div class="container" style="margin-top: 50px;">
			<div class="row">
				<div class="col-md-9 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-edit"></span>&nbsp;&nbsp;添加学生</div>
						<div class="panel-body" style="padding-bottom: 100px;padding-top: 50px;">
							<form class="form-horizontal" role="form" method="post" action="<%=basePath %>user/findStudent_addStudent.shtm" onsubmit="return $.check()">
								<div class="form-group ">
									<label class="col-sm-3 control-label">账号:</label>
									<div class="col-sm-8">
										<input class="form-control" type="text" value="" name="stuNum"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">姓名:</label>
									<div class="col-sm-8">
										<input class="form-control" type="text" name="stuName" value=""/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">性别:</label>
									<div class="col-sm-8">
										<select class="form-control input-sm" type="text" name="sex" id="sel">
											<option value="男">男</option>
											<option value="女">女</option>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label">密码:</label>
									<div class="col-sm-8">
										<input class="form-control" type="text" name="password" placeholder="【选填--默认与账号相同】"/>
									</div>
								</div>								
								<div class="form-group">
									<label class="col-sm-3 control-label">专业:</label>
									<div class="col-sm-8">
										<select class="form-control input-sm" type="text" name="major" id="major" onchange="changeMajor()">
											<option value="0">请选择</option>
											<c:forEach var="major" items="${majorList}">
											<option value="${major.majorName}">${major.majorName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">班级:</label>
									<div class="col-sm-8">
										<select class="form-control input-sm" type="text" name="stuClass" id="class">
											<option value="0">请选择</option>
											<c:forEach var="className" items="${classList}">
											<option value="${className.cName}">${className.cName}</option>
											</c:forEach>
										</select>
									</div>
								</div>						
								<div class="col-sm-6 col-sm-offset-3" style="margin-top: 20px">
									<div>
										<input type="hidden" value="student" name="roleName">
										<button class="btn btn-primary" type="submit">提交</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<button class="btn btn-default" type="reset">重置</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a class="btn btn-default" href="<%=basePath %>user/findStudent.shtm">返回</a>
									</div>
								</div>
								<input type="hidden" value="${errorMsg}" id="error"/>
								<input type="hidden" value="${successMsg}" id="success"/>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="mymodal-error" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
		 <div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
				<p></p>
				</div>
				<div class="modal-footer">			
					<button type="button" class="btn btn-primary"
						data-dismiss="modal">确认</button>
				</div>
			</div>
		</div>
	</div>
		<script type="text/javascript">
		jQuery.extend({ 
				check:function(){
					var stuNum = $("input[name='stuNum']").val().trim();
					var name = $("input[name='stuName']").val().trim();
					var major = $("select[name='major']").val();
					var stuClass = $("select[name='stuClass']").val();
					if(stuNum==''|| stuNum==undefined ||stuNum==null)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("请填写账号!");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}
					if(name==''|| name==undefined ||name==null)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("请填写姓名!");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}		    
		        	if(major==0)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("请选择专业！");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}
		        	if(stuClass==0)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("请选择班级！");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}		        			        			        		        	
		        }								
			});
	</script>	
</body>

</html>