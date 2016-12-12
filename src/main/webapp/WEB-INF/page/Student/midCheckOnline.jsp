<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" /
		<title></title>
	</head>
	<body>
		<div class="container" style="margin-top: 50px;">
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<form method="post" action="<%=basePath%>student/midCheckOnline_submit.shtm">
						<div class="panel panel-primary">
							<div class="panel-heading"><span class="fa fa-edit"></span>&nbsp;&nbsp;填写许昌学院本科毕业论文（设计）中期检查表</div>
							<div class="panel-body">
								<div class="alert alert-info">
									<p>注：基本信息由系统自动填充</p>
								</div>
								<div class="form-group">
									<label>论文（设计）进展情况</label>
									<div>
										<textarea class="form-control" rows="6" name="midCheckProgress" id="midCheckProgress"></textarea>
									</div>
								</div>
								<div class="form-group" style="margin-top:30px">
									<label>论文撰写中存在的突出困难及解决办法</label>
									<textarea class="form-control" rows="6" name="problemAndSolve" id="problemAndSolve"></textarea>
								</div>																	
							</div>
							<div class="panel-footer text-center">
								<input class="btn btn-primary" type="button" id="midCheckSubmit" value="提交"></input>&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="btn btn-default" type="reset">重置</button>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-default" href="<%=basePath %>student/midCheck.shtm">返回</a>
							</div>
						</div>
						<div class="modal fade" id="mymodal-data" tabindex="-1" role="dialog"
							aria-labelledby="mySmallModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="dataTitle"></h4>
									</div>
									<div class="modal-body" id="dataBody">
										<p></p>
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">确认</button>
										<button type="button" class="btn btn-default "
											data-dismiss="modal">取消</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>		
		<div class="modal fade" id="mymodal-error" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
		 <div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="error-title"></h4>
				</div>
				<div class="modal-body" id="error-body">
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
		$(function(){
			$("#midCheckSubmit").click(function(){
				var midCheckProgress=$("#midCheckProgress").val().trim();
				var problemAndSolve = $("#problemAndSolve").val().trim();
				if(midCheckProgress == '' || midCheckProgress == undefined || midCheckProgress == null)
				{
			       $("#error-title").html("错误提示");
				   $("#error-body").html("“论文（设计）进展情况”不能为空");
				   $("#mymodal-error").modal("toggle");
				   return false;
	        	}
	        	if(problemAndSolve == '' || problemAndSolve == undefined || problemAndSolve == null)
	        	{
	        	   $("#error-title").html("错误提示");
				   $("#error-body").html("“论文撰写中存在的突出困难及解决办法”不能为空");
				   $("#mymodal-error").modal("toggle");
				   return false;
	        	}
				$("#dataTitle").html("中期检查表提交");
				$("#dataBody").html("确认提交？");
				$("#mymodal-data").modal("toggle");
			});
		});    
		</script>
	</body>
</html>