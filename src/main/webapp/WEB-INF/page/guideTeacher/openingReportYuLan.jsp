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
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
		<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
		<title></title>
	</head>

	<body>
		<div class="row">
			<iframe class="col-md-7" frameborder="0" id="iframe" scrolling="auto" src="https://view.officeapps.live.com/op/view.aspx?src=${filePathSrc}"></iframe>
			<div class="col-md-4" style="margin-top: 100px;">
				<form action="wordJacod.shtm" method="post" id="openingReportYuLan">
				<input hidden="hidden" value="${filePath}" name="filePath"/>
				<input hidden="hidden" value="openingReport" name="fileType"/>
				<input hidden="hidden" value="${filePathSrc}" name="filePathSrc"/>
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-pencil-square-o ">&nbsp;&nbsp;开题报告</span></div>
						<div class="panel-body">
							<div class="form-group">
								<label><h4>指导教师对开题报告的意见</h4></label>
								<textarea rows="12" class="form-control" name="teaIdea" id="teaIdea"></textarea>
							</div>
						</div>
						<div class="panel-footer">
							<a class="btn btn-primary" type="button" id="openingReportSubmit">提交</a>
						</div>
					</div>
				</form>
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
			window.onload = function() {
			    var iframe = document.getElementById("iframe");
			    iframe.style.height = window.innerHeight + 'px';
			};
			$(function(){
				$("#openingReportSubmit").click(function(){
					var teaIdea = $("#teaIdea").val();
					if(teaIdea==''|| teaIdea==undefined || teaIdea==null)
					{
						 $("#error-title").html("错误提示");
						 $("#error-body").html("尚未填写教师意见!");
						 $("#mymodal-error").modal("toggle");
						 return false;
					}
					$("form").submit();
				})
			});
		</script>
	</body>