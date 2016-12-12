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
			<iframe class="col-md-7" frameborder="0" id="iframe" scrolling="auto" src="https://view.officeapps.live.com/op/view.aspx?src=${filePathSrc}">
				
			</iframe>
			<div class="col-md-4" style="margin-top: 50px;">
				<form action="wordJacod.shtm" method="post">
					<input hidden="hidden" value="${filePath}" name="filePath"/>
					<input hidden="hidden" value="midCheck" name="fileType"/>
					<input hidden="hidden" value="${filePathSrc}" name="filePathSrc"/>
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-pencil-square-o">&nbsp;&nbsp;中期检查表</span></div>
						<div class="panel-body">
							<div class="row">
								<div class="form-group col-md-6">
									<label class="control-label">1.与开题报告相比较，毕业论文(设计)的题目和内容有无调整</label>
									<textarea rows="2" class="form-control" placeholder="实际工作状态" name="adjust" id="adjust"></textarea>
								</div>
								<div class="form-group col-md-6">
									<label class="control-label">2.学生论文(设计)所取得的阶段性成果</label>
									<textarea rows="2" class="form-control" placeholder="实际工作状态" name="achievement" id="achievement"></textarea>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-6">
									<label class="control-label">3.学生的工作态度、出勤情况</label>
									<textarea rows="2" class="form-control" placeholder="实际工作状态" name="attitude" id="attitude"></textarea>
								</div>
								<div class="form-group col-md-6">
									<label class="control-label">4.指导教师对学生的指导情况（指导次数、方式）</label>
									<textarea rows="2" class="form-control" placeholder="实际工作状态" name="time" id="time"></textarea>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-6">
									<label class="control-label">5.对能否按期完成毕业论文(设计)的评估</label>
									<textarea rows="2" class="form-control" placeholder="实际工作状态" name="assess" id="assess"></textarea>
								</div>
								<div class="form-group col-md-6">
									<label class="control-label">6.学生与指导教师有关毕业论文(设计)的原始材料是否保存齐全</label>
									<textarea rows="2" class="form-control" placeholder="实际工作状态" name="save" id="save"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">7.指导教师目前对论文进展情况的意见</label>
								<textarea rows="3" class="form-control" placeholder="实际工作状态" name="teaIdea" id="teaIdea"></textarea>
							</div>
						</div>
						<div class="panel-footer">
							<a class="btn btn-primary" type="button" id="midCheckSubmit">提交</a>
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
			window.onload = function(){
			    var iframe = document.getElementById("iframe");
			    iframe.style.height = window.innerHeight + 'px';
			};
			$(function(){
				$("#midCheckSubmit").click(function(){
					var adjust = $("#adjust").val();
					var achievement = $("#achievement").val();
					var attitude = $("#attitude").val();
					var time = $("#time").val();
					var assess = $("#assess").val();
					var save = $("#save").val();
					var teaIdea = $("#teaIdea").val();
					if(adjust==''|| adjust==undefined || adjust==null)
					{
						 $("#error-title").html("错误提示");
						 $("#error-body").html("尚有评价未填写!");
						 $("#mymodal-error").modal("toggle");
						 return false;
					}
					if(achievement==''|| achievement==undefined || achievement==null)
					{
						 $("#error-title").html("错误提示");
						 $("#error-body").html("尚有评价未填写!");
						 $("#mymodal-error").modal("toggle");
						 return false;
					}
					if(attitude==''|| attitude==undefined || attitude==null)
					{
						 $("#error-title").html("错误提示");
						 $("#error-body").html("尚有评价未填写!");
						 $("#mymodal-error").modal("toggle");
						 return false;
					}
					if(time==''|| time==undefined || time==null)
					{
						 $("#error-title").html("错误提示");
						 $("#error-body").html("尚有评价未填写!");
						 $("#mymodal-error").modal("toggle");
						 return false;
					}
					if(assess==''|| assess==undefined || assess==null)
					{
						 $("#error-title").html("错误提示");
						 $("#error-body").html("尚有评价未填写!");
						 $("#mymodal-error").modal("toggle");
						 return false;
					}
					if(save==''|| save==undefined || save==null)
					{
						 $("#error-title").html("错误提示");
						 $("#error-body").html("尚有评价未填写!");
						 $("#mymodal-error").modal("toggle");
						 return false;
					}
					if(teaIdea==''|| teaIdea==undefined || teaIdea==null)
					{
						 $("#error-title").html("错误提示");
						 $("#error-body").html("尚有评价未填写!");
						 $("#mymodal-error").modal("toggle");
						 return false;
					}
					$("form").submit();
				})
			});
		</script>
	</body>