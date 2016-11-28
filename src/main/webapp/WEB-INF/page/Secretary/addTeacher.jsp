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
		<div class="container" style="margin-top: 50px;">
			<div class="row">
				<div class="col-md-9 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-edit"></span>&nbsp;&nbsp;教师信息</div>
						<div class="panel-body" style="padding-bottom: 100px;padding-top: 50px;">
							<form class="form-horizontal" role="form" method="post" action="<%=basePath %>user/findTeacher_addTeacher.shtm" onsubmit="return $.check()">
								<div class="form-group ">
									<label class="col-sm-3 control-label">账号:</label>
									<div class="col-sm-8">
										<input class="form-control" value="" type="text" name="teaNum"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">姓名:</label>
									<div class="col-sm-8">
										<input class="form-control" value="" type="text" name="teaName" />
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
									<label class="col-sm-3 control-label">职称:</label>
									<div class="col-sm-8">
										<select class="form-control input-sm" type="text" name="zhiCheng" id="zhicheng">
											<option value="0">请选择</option>
											<c:forEach var="ZhiCheng" items="${zhiChengList}">
											<option value="${ZhiCheng.zhiCheng}">${ZhiCheng.zhiCheng}</option>
											</c:forEach>
										</select>
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
								<div class="col-sm-6 col-sm-offset-3" style="margin-top: 20px">
									<div>
										<input type="hidden" value="teacher" name="roleName">
										<button class="btn btn-primary" type="submit">提交</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<button class="btn btn-default" type="reset">重置</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a class="btn btn-default"  href="<%=basePath %>user/findTeacher.shtm">返回</a>
									</div>
								</div>
								<input type="hidden" value="${errorMsg}" id="error">
								<input type="hidden" value="${successMsg}" id="success">
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
					var teaNum = $("input[name='teaNum']").val().trim();
					var name = $("input[name='teaName']").val().trim();
					var zhicheng = $("select[name='zhicheng']").val();
					var major = $("select[name='major']").val();
					if(teaNum==''|| teaNum==undefined ||teaNum==null)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("账号不能为空！");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}
					if(name==''|| name==undefined ||name==null)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("姓名不能为空！");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}
		        	if(zhicheng==0)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("职称不能为空！");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}		    
		        	if(major==0)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("专业不能为空！");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}       			        			     	   	        	
		        }								
			});
	</script>
	</body>

</html>