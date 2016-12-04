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
<html>
<head>
<meta http-equiv="Content-Type" />
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width,initial-scale=1">
<title></title>
<style>
a {
	color: #000000;
	text-decoration: none;
}

a:hover {
	color: #0088CC;
	text-decoration: none;
}
</style>
</head>

<body style="font-family: '微软雅黑'; font-size: 17px;">
	<div style="margin-top: 50px;">
		<div class="row">
			<div class="col-sm-12 col-md-11 col-md-offset-1">
				<div class="panel panel-info">
					<div class="panel-body">
						<div class="alert alert-info" role="alert">
							<p>1、 每个课题有效被选次数为：1,不能重复选择课题</p>			
							<p>2、 超出学生名额仍可申请该导师课题,导师审核筛选后,驳回超出学生名额的申请</p>	
							<p>3、 被驳回申请后,可选择其他导师课题或自拟课题并重新申报</p>						
							<p>4、 仅限退选审核状态中的课题</p>
						</div>
						<div class="col-sm-5">
							<b>所属专业：</b>
							<h>${student.major}</h>
							<input hidden="hidden" id="ErrorMsg" value="${errorMsg}">
						</div>
						<form class="form-inline" method="post"	action="<%=basePath%>student/initApplyTitle.shtm" id="select">
							<div class="col-sm-7">
								<label class="">教师姓名:</label> <input
									class="form-control input-sm" type="text" name="teaName"
									id="teaName" value="${teaName}" />
									<input name="pageNo"  type="hidden"  value="${page.pageNo}"   id="pageNow" />
						<input name="pageSize" type="hidden"  value="${page.pageSize}" id="pageSize">
								<button class="btn btn-primary btn-sm" id="searchBtn"
									type="submit">查询</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row" style="margin-top: 30px;">
			<div class="col-sm-12 col-md-11 col-md-offset-1">
				
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-edit"></span>&nbsp;&nbsp;可选指导老师
						</div>
						<div class="panel-body">
						<div class="table-responsive">
							<table
								class="table table-bordered table-striped table-hover text-center"
								id="teaTitle">
								<thead>
									<tr>
										<th>教师名</th>
										<th>性别</th>
										<th>职称</th>
										<th>专业</th>
										<th>学生名额</th>
										<th>已申请名额</th>
										<th>联系方式</th>
										<th>邮箱</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${page.results!=null}">
										<c:forEach var="teacher" items="${page.results}">
											<tr>
												<td>${teacher.teaName}</td>
												<td>${teacher.sex}</td>
												<td>${teacher.zhicheng}</td>
												<td>${teacher.major}</td>
										 		<td>${teacher.allStunum}</td>
												<td>${teacher.nowStunum}</td>
												<td>${teacher.tel}</td>
												<td>${teacher.mail}</td>
												<td><a  href="<%=basePath%>student/initApplyTitle_ApplyTeaProject.shtm?teaId=${teacher.userId}">查看课题</a></td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${page.results==null}">
										<tr><td colspan="9">暂无教师课题</td></tr>
									</c:if>
								</tbody>
							</table>
						</div>
						<div class="row" style="padding-left: 15px;">
											
						<div class="col-xs-8 col-md-8 " style="float: right; text-align:right;">
								<script type="text/javascript" src="<%=basePath%>js/page.js"></script>
								<c:if test="${page.isAjax==0}">
									<c:if test="${page.pageNo-1>0 }">
										<a href="javascript:upPage()" class="btn btn-default btn-sm">&laquo;上一页</a>
									</c:if>

									<a class="btn btn-default btn-sm">第${page.pageNo }页</a>

									<c:if test="${page.pageNo+1<=page.totalPage }">
										<a href="javascript:nextPage()" class="btn btn-default btn-sm">下一页&raquo;</a>
									</c:if>
										&nbsp;&nbsp;共${page.totalRecord }条&nbsp;共${page.totalPage}页 &nbsp;&nbsp;跳转到&nbsp;
										<input type="text" value="" id="zc_tz_text"
										style="width:28px;height:28px" />&nbsp;页<input type="button"
										value="跳转" id="zc_tz_button" class="btn btn-default btn-sm"
										onclick="zcTzClick(${page.totalPage})" /> &nbsp;&nbsp;每页&nbsp;<input type="text" value="" id="page_size_text"
										style="width:28px;height:28px" />&nbsp;条<input type="button"
										value="设置" id="zc_size_button" class="btn btn-default btn-sm"
										onclick="pageSizeClick()" />
								</c:if>
							</div>
						</div><!-- /page-row -->
					</div>
				</div>
				</form>
			</div>
		</div>		
		<div class="row" style="margin-top: 30px;">
			<div class="col-sm-12 col-md-11 col-md-offset-1">
				<div class="panel panel-success">
					<div class="panel-heading">
						<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;已选课题
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table
								class="table table-bordered table-striped table-hover text-center">
								<thead>
									<tr>
										<th>课题名称</th>
										<th>教师名</th>
										<th>性别</th>
										<th>职称</th>
										<th>专业</th>
										<th>联系方式</th>
										<th>邮箱</th>
										<th>状态</th>
										<th>退选</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${teacher!=null}">
										<tr>
											<td><a name="titleName" href="<%=basePath%>student/initApplyTitle_title.shtm?id=${applyTitle.stuId}&Source=1&readOnly=1">${applyTitle.title}</a>
											</td>
											<td>${teacher.teaName}</td>
											<td>${teacher.sex}</td>
											<td>${teacher.zhicheng}</td>
											<td>${teacher.major}</td>
											<td>${teacher.tel}</td>
											<td>${teacher.mail}</td>
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
											<td><a name="unApplySubmit" id="unApplySubmit" href="javascript:void(0);"
												value="${applyTitle.id}">退选</a>
											</td>
										</tr>
									</c:if>
								</tbody>
							</table>
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
											href="<%=basePath%>student/initApplyTitle_unApplyTitle.shtm?id=${applyTitle.id}">确认</a>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">取消</button>
									</div>
								</div>
							</div>
						</div>
					</div>
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
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
					<p></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(
			function() {
				var ErrorMsg = $("#ErrorMsg").val();
				if (!(ErrorMsg == '' || ErrorMsg == undefined || ErrorMsg == null)) {
					$(".modal-title").html("错误提示");
					$(".modal-body").html(ErrorMsg);
					$("#mymodal-error").modal("toggle");
				}
			});
		$(function() {
			$("#unApplySubmit").click(function() {
				$(".modal-title").html("退选课题");
				$(".modal-body").html("仅限退选审核状态中的课题，确认退选？");
				$("#mymodal-unApply").modal("toggle");
			});
		});
	</script>
</body>

</html>
