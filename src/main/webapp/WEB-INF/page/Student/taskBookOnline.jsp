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
					<form method="post" action="<%=basePath%>createDoc.chtm">
						<div class="panel panel-primary">
							<div class="panel-heading"><span class="fa fa-edit"></span>&nbsp;&nbsp;填写许昌学院本科毕业论文（设计）任务书</div>
							<div class="panel-body">
								<div class="alert alert-info">
									<p>注：基本信息由系统自动填充</p>
								</div>
								<div>
									<label>毕业论文（设计）要求完成的主要任务及其时间安排:</label>
									<div>
										<textarea class="form-control" rows="6" name="taskBook_PartOne" id="taskBook_PartOne"></textarea>
									</div>
								</div>
								<div style="margin-top: 20px;">
									<label>毕业论文（设计）的主要技术指标:</label>
									<div>
										<textarea class="form-control" rows="6" name="taskBook_PartTwo" id="taskBook_PartTwo"></textarea>
									</div>
								</div>
								<div style="margin-top: 20px;">
									<label>毕业论文（设计）的基本要求及应完成的成果形式:</label>
									<div>
										<textarea class="form-control" rows="6" name="taskBook_PartThree" id="taskBook_PartThree"></textarea>
									</div>
								</div>
								<div style="margin-top: 20px;">
									<label>毕业论文（设计）应收集的资料及主要参考文献:</label>
									<div>
										<textarea class="form-control" rows="6" name="taskBook_PartFour" id="taskBook_PartFour"></textarea>
									</div>
								</div>
							</div>
							<div class="panel-footer text-center">
								<button class="btn btn-primary" type="submit" id="taskBookOnlineSubmit">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="btn btn-default" type="reset">重置</button>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-default" onclick="<%=basePath %>student/taskBook.shtm">返回</a>
							</div>
						</div>
					</form>
				</div>
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
			$("#taskBookOnlineSubmit").click(function(){
				var taskBook_PartOne=$("#taskBook_PartOne").val().trim();
				var taskBook_PartTwo=$("#taskBook_PartTwo").val();
				var taskBook_PartThree=$("#taskBook_PartThree").val();
				var taskBook_PartFour=$("#taskBook_PartFour").val().trim();
				if(taskBook_PartOne==""||taskBook_PartTwo==""||taskBook_PartThree==""||taskBook_PartFour=="")
				{
			       $("#error-title").html("错误提示");
				   $("#error-body").html("当前表单不能有空项!");
				   $("#mymodal-error").modal("toggle");
				   return false;
	        	}
				$("#dataTitle").html("任务书提交");
				$("#dataBody").html("确认提交？");
				$("#mymodal-data").modal("toggle");
			});
		});    
		</script>
	</body>
</html>