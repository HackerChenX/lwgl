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
<title></title>
</head>

<body>
	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="col-md-9 col-md-offset-1">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<span class="fa fa-edit"></span>&nbsp;&nbsp;中期检查：
						<c:choose>
						<c:when test="${midCheck==null}">未提交</c:when>
						<c:when test="${midCheck.teaStatus==2}">退回</c:when>
						<c:when test="${midCheck.teaStatus==1}">通过</c:when>
						<c:otherwise>审核中</c:otherwise>
						</c:choose>	
					</div>
					<form method="post" enctype="multipart/form-data" action="<%=basePath%>student/midCheck_submitMidCheck.shtm">
						<div class="panel-body">
							<table cellspacing="20">
								<tbody style="line-height: 50px;">
									<tr>
										<th>学生：</th>
										<td>${student.stuName}</td>
									</tr>
									<tr>
										<th>开始时间：</th>
										<td>${startDate}</td>
									</tr>
									<tr>
										<th>截止时间：</th>
										<td>${endDate}</td>
									</tr>
								</tbody>
							</table>
							<div class="alert alert-info" role="alert"
								style="margin-top: 15px;">
								<strong>注意事项：</strong>
								<p>（1）选择附件点击提交后上传</p>
								<p>（2）仅限撤回审核中的文件</p>
								<p>（3）附件上传后由系统统一命名为:中期检查-姓名-学号格式</p>
							</div>
							<div>
								<label>上传附件:</label> <input type="file" id="taskFile" name="taskFile" />
							</div>
						</div>
						<div class="panel-footer">					
							<button id="submitMidCheck" class="btn btn-primary" type="button">提交</button>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="reset" class="btn btn-default">撤销</button>
						</div>
						<div class="modal fade" id="mymodal-data" tabindex="-1"
							role="dialog" aria-labelledby="mySmallModalLabel"
							aria-hidden="true">
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
										<button class="btn btn-primary" type="submit">确认</a></button>
										&nbsp;&nbsp;
										<button type="button" class="btn btn-default"
											data-dismiss="modal">取消</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-9 col-md-offset-1">
				<div class="panel panel-success">
					<div class="panel panel-heading">
						<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;已提交信息
					</div>
					<div class="panel-body">
						<table
							class="table table-bordered table-striped table-hover text-center"
							style="text-overflow: ellipsis;">
							<thead>
								<tr>
									<th>姓名</th>
									<th>中期检查</th>
									<th>指导老师审核意见</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<c:if test="${midCheck!=null}">
								<tr>
									<td>${midCheck.createUser}</td>
									<td><a href="<%=basePath%>student/downloadFile.shtm?filePath=${midCheck.midCheckPath}">许昌学院本科毕业论文（设计）中期检查</a></td>
									<td><c:if test="${midCheck.teaStatus==0}">审核中</c:if>
										<c:if test="${midCheck.teaStatus==1}">通过</c:if>
										<c:if test="${midCheck.teaStatus==2}">退回</c:if>
									</td>									
									<td><a id="cancelMidCheck" href="javascript:void(0);">撤回</a></td>
								</tr>
							</c:if>
							</tbody>
						</table>
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
	<div class="modal fade" id="mymodal-unApply" tabindex="-1"
	role="dialog" aria-labelledby="mySmallModalLabel"
	aria-hidden="true">
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
				<a type="submit" id="unApplySubmit" class="btn btn-primary"
					href="<%=basePath%>student/midCheck_cancel.shtm?id=${midCheck.id}">确认</a>
				<button type="button" class="btn btn-default"
					data-dismiss="modal">取消</button>
			</div>
		</div>
	</div>
</div>
	</div>
	<input id="ErrorMsg" value="${errorMsg}" hidden="hidden"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var ErrorMsg=$("#ErrorMsg").val();
			if(!(ErrorMsg==''|| ErrorMsg==undefined || ErrorMsg==null)){
				$(".modal-title").html("错误提示");
				$(".modal-body").html(ErrorMsg);
				$("#mymodal-error").modal("toggle");
			}
		});
		$(function(){
			$("#submitMidCheck").click(function(){
			    var file = $("#taskFile").val();
			    if(file==''|| file==undefined || file==null){
				    $(".modal-title").html("提示");
					$(".modal-body").html("请选择一个文件");
					$("#mymodal-error").modal("toggle");
					return false;
			    }
		        $(".modal-title").html("提交中期检查");
				$(".modal-body").html("审核后无法撤回,确认提交?");
				$("#mymodal-data").modal("toggle");	    
				return true;
			});
		});	
		$(function(){
			$("#cancelMidCheck").click(function(){
				$(".modal-title").html("撤回提示");
				$(".modal-body").html("仅限撤回审核中的中期检查，确认撤回？");
				$("#mymodal-unApply").modal("toggle");
			});
		});
		
	</script>
</body>
</html>