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
		<title></title>
	</head>
	<body>
		<div style="margin-top: 50px;">		
			<div class="row">
				<div class="col-xs-12 col-md-10 col-md-offset-1">
					<form method="post" action="$$">
						<div class="panel panel-primary">
							<div class="panel-heading"><span class="fa fa-check-square-o"></span>&nbsp;&nbsp;已申报课题</div>
							<div class="table-responsive">
								<table class="table table-bordered table-striped table-hover text-center">
								   <thead>
										<tr>
											<th>学号</th>
											<th>姓名</th>
											<th>课题名称</th>
											<th>指导老师</th>
											<th>选题方式</th>
											<th class="text-center" colspan="2">操作</th>
											<th>审核状态</th>
										</tr>
								    </thead>
									<tbody>
									<c:if test="${applyTitle!=null}">
										<tr>
											<td>${student.userNum}</td>
											<td>${student.stuName}</td>
											<td><a href="<%=basePath%>student/initApplyTitle_title.shtm?id=${applyTitle.stuId}&Source=1&readOnly=1">${applyTitle.title}</a></td>
											<td>${teacher.teaName}</td>
											<td><c:if test="${applyTitle.titleSource==0}">教师课题</c:if>
												<c:if test="${applyTitle.titleSource==1}">学生课题</c:if>
											</td>
											<td>
												<a href="<%=basePath%>student/initApplyTitle_title.shtm?id=${applyTitle.stuId}&Source=1&readOnly=0" class="btn btn-default">修改</a>
											</td>
											<td>
												<a class="btn btn-default" name="unApplySubmit" href="javascript:void(0);">退选</a>
											</td>
											<td>
												<c:choose>
												<c:when test="${applyTitle.titleSource==0}">
													<c:if test="${applyTitle.teaStatus==0}">审核中</c:if>
													<c:if test="${applyTitle.teaStatus==1}">审核通过</c:if>
												    <c:if test="${applyTitle.teaStatus==2}">驳回</c:if>		
												</c:when>
												<c:otherwise>
													<c:if test="${applyTitle.teaStatus==0}">指导老师审核中</c:if>
													<c:if test="${applyTitle.teaStatus==1&&applyTitle.leaderStatus==0}">专业负责人审核中</c:if>
													<c:if test="${applyTitle.teaStatus==1&&applyTitle.leaderStatus==1&&applyTitle.managerStatus==0}">教学秘书审核中</c:if>
													<c:if test="${applyTitle.teaStatus==2}">指导老师驳回</c:if>
													<c:if test="${applyTitle.leaderStatus==2}">专业负责人驳回</c:if>
													<c:if test="${applyTitle.managerStatus==2}">教学秘书驳回</c:if>
													<c:if test="${applyTitle.managerStatus==1}">审核通过</c:if>
												</c:otherwise>
											</c:choose>
											</td>
										</tr>
									</c:if>			
									</tbody>
								</table>
							</div>
							<input hidden="hidden" id = "applyTitle" value="${applyTitle}">
							<input hidden="hidden" id = "teaStatus" value="${applyTitle.teaStatus}">
							<input hidden="hidden" id = "leaderStatus" value="${applyTitle.leaderStatus}">
							<input hidden="hidden" id = "managerStatus" value="${applyTitle.managerStatus}">
							<input hidden="hidden" id = "titleSource" value="${applyTitle.titleSource}">
							<div class="panel-footer">
								<a id="applyProject" class="btn btn-primary" href="<%=basePath%>student/ApplyTitleSelf_applyForProject.shtm">申报课题</a>
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
	<div class="modal fade" id="mymodal-unApply" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
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
					<a type="submit" id="unApplySubmit" class="btn btn-primary" href="<%=basePath%>student/initApplyTitle_unApplyTitle.shtm?id=${applyTitle.id}">确认</a>
					<button type="button" class="btn btn-default"
						data-dismiss="modal">取消</button>
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
			$("[name='unApplySubmit']").click(function(){
				$(".modal-title").html("退选课题");
				$(".modal-body").html("仅限退选审核状态中的课题，确认退选？");
				$("#mymodal-unApply").modal("toggle");		
			});
		});	
	</script>
	</body>

</html>