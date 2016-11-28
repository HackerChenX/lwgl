<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>	
	</head>

	<body>
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<div class="container col-md-6 col-md-offset-3">
			<div class="row" style="margin-top: 50px;margin-left: 0px;margin-right: 0px;">
				<form class="form-horizontal" method="post" action="<%=basePath %>secretary/findTitleForm_addTitleForm.shtm">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-check-square-o"></span>&nbsp;&nbsp;添加完成形式</div>
						<div class="form-group" style="padding: 10px;">		
							<div class="col-sm-3" style="text-align: right;">
								<label class="control-label">完成形式:</label>
							</div>
							<div class="col-sm-5">
							<input class="form-control" type="text" name="formName"/>
							</div>			
						</div>					
					<div class="panel-footer ">
						<button class="btn btn-primary " type="submit">保存</button>&nbsp;&nbsp;
						<a class="btn btn-default " href="<%=basePath %>secretary/findTitleForm.shtm">返回</a>
					</div>
					<input type="hidden" value="${errorMsg}" id="error">
					<input type="hidden" value="${successMsg}" id="success">
				</div>
				</form>
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