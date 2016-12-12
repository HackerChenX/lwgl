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
					<form method="post" action="<%=basePath%>student/openingReportOnline_submit.shtm">
						<div class="panel panel-primary">
							<div class="panel-heading"><span class="fa fa-edit"></span>&nbsp;&nbsp;填写许昌学院本科毕业论文（设计）开题报告</div>
							<div class="panel-body">
								<div class="alert alert-info">
									<p>注：基本信息由系统自动填充</p>
								</div>
								<div class="form-group">
									<label>1.开题报告内容包括选题的依据和意义、国内外有关本选题研究的动态、拟解决的主要问题、研究方法、选题的特色及创新点、论文的写作提纲或毕业设计（创作）的基本思路、主要参考文献等;</label>
									<label>2.如果选题为研究论文形式者，应在开题报告中以提纲形式体现初稿写作思路。</label>
									<div>
										<textarea class="form-control" rows="12" name="openingReportContent" id="openingReportContent" placeholder="开题报告内容"></textarea>
									</div>
								</div>
								<div class="form-group" style="margin-top:30px">
									<div>
										<table class="table table-bordered table-hover table-striped text-center">
											<thead>
												<tr>
													<th colspan="2">写作进度及具体时间安排</th>
												</tr>
												<tr>
													<th width="30%">起始日期</th>
													<th width="70%">主要研究内容</th>
												</tr>
											</thead>
											<tbody>
												<tr class="active">
													<td><input class="form-control" name="dateOne" id="dateOne"></input></td>
													<td><input class="form-control" name="dateOneContent" id="dateOneContent"></input></td>
												</tr>
												<tr class="active">
													<td><input class="form-control" name="dateTwo"></input></td>
													<td><input class="form-control" name="dateTwoContent"></input></td>
												</tr>
												<tr class="active">
													<td><input class="form-control" name="dateThree"></input></td>
													<td><input class="form-control" name="dateThreeContent"></input></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>																	
							</div>
							<div class="panel-footer text-center">
								<input class="btn btn-primary" type="button" id="openingReportSubmit" value="提交"></input>&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="btn btn-default" type="reset">重置</button>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-default" href="<%=basePath %>student/openingReport.shtm">返回</a>
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
			$("#openingReportSubmit").click(function(){
				var openingReportContent=$("#openingReportContent").val().trim();
				var dateOne = $("#dateOne").val().trim();
				var dateOneContent = $("#dateOneContent").val().trim();
				if(openingReportContent == '' || openingReportContent == undefined || openingReportContent == null)
				{
			       $("#error-title").html("错误提示");
				   $("#error-body").html("“开题报告内容”不能为空");
				   $("#mymodal-error").modal("toggle");
				   $("#openingReportContent").focus();
				   return false;
	        	}
	        	if(dateOne == '' ||dateOneContent =='' )
	        	{
	        	   $("#error-title").html("错误提示");
				   $("#error-body").html("“写作进度及具体时间安排”不能为空");
				   $("#mymodal-error").modal("toggle");
				   return false;
	        	}
				$("#dataTitle").html("开题报告提交");
				$("#dataBody").html("确认提交？");
				$("#mymodal-data").modal("toggle");
			});
		});    
		</script>
	</body>
</html>