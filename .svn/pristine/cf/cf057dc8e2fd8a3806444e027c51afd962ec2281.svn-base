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
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<input type="hidden" value="${errorMsg }" id="error"/>
		<input type="hidden" value="${successMsg }" id="success"/>
		<div class="container col-md-6 col-md-offset-3">
		<div class="row" style="margin-top: 50px;margin-left: 0px;margin-right: 0px;">
			<div class="panel panel-primary">
				<div class="panel-heading"><span class="fa fa-check-square-o"></span>&nbsp;&nbsp;本届数据归档</div>
				<form class="form-inline" method="post" action="<%=basePath %>secretary/dataSave_setDataSave.shtm">
					<div class="panel-body">
						<h3 style="color: red;text-align: center;">操作前请阅读以下注意内容</h3>
						<div class="alert alert-danger" role="alert">
						<p>注意 ：本届数据归档操作,是将本届学生的所有论文和教师课题数据备份成往年记录,</p>
   						<p>同时初始化系统供今年使用，请不要在一学年的使用中操作本功能。</p></div>
   						<div style="text-align: center;">
							<label>请输入年份：</label>
							<input class="form-control" type="text" name="year" id="fileDate"/>
							<button class="btn btn-danger" type="submit">本届数据归档</button>
						</div>
					</div>
				</form>
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

	
	</body>
</html>
