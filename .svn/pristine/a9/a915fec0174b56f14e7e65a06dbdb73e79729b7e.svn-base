<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
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
		<div class="container col-md-6 col-md-offset-3">
		<div class="row" style="margin-top: 50px;margin-left: 0px;margin-right: 0px;">
			<div class="panel panel-primary">
				<div class="panel-heading"><span class="fa fa-check-square-o"></span>&nbsp;&nbsp;系统维护开关</div>
				<form class="form-inline" method="post" action="<%=basePath %>secretary/setUpdateSystem.shtm">
					<div class="panel-body">
						<h3 style="color: red;text-align: center;">操作前请阅读以下注意内容</h3>
						<div class="alert alert-danger" role="alert">
						<p>注意 ：   在系统需要功能升级时，为了防止升级时人员登陆系统进行操作，设置后除教学秘书之外其他人将无法登陆系统。</p></div>
						<div style="text-align: center;">
							<c:if test="${flag=='0'}">
							<input type="hidden" value="1" name="flag">
							<button class="btn btn-danger" type="submit" style=": center;">系统维护开启</button>
							</c:if>
							<c:if test="${flag=='1'}">
							<input type="hidden" value="0" name="flag">
							<button class="btn btn-danger" type="submit" style=": center;">系统维护关闭</button>
							</c:if>
						</div>
						<input type="hidden" value="${successMsg}" id="success">
						<input type="hidden" value="${errorMsg}" id="error">						
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
