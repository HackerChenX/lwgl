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
		</head>
		
	<body>
		<script type="text/javascript" src="<%=basePath %>js/page.js"></script>
			<div class="row" style="margin-top: 50px;">
				<div class="col-sm-12 col-md-11 col-md-offset-1">
					<form action="<%=basePath %>majorLeader/selectTB_findStuList.shtm" id="select" method="post">
						<input type="hidden" value="${stageName}" name="stageName">
						<input type="hidden" value="${zdTeaStatus}" name="zdTeaStatus">
						<input type="hidden" value="${className}" name="className">
						<input name="pageNo" type="hidden" value="${page.pageNo}" id="pageNow" />
						<input type="hidden" value="${page.pageSize}" name="pageSize" id="pageSize">
					</form>
					<form role="form" action="<%=basePath %>majorLeader/exportStuList.shtm" method="post" >
						<div class="panel panel-primary">
							<div class="panel-heading">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;学生信息
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-striped table-hover" style="text-align:center">
									<thead>
										<tr>
											<th>学号</th>
											<th>姓名</th>
											<th>性别</th>
											<th>专业名称</th>
											<th>班级</th>
											<th>课题名</th>
											<th>联系方式</th>
											<th>电子邮箱</th>
											<th>指导老师</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="student" items="${page.results}">
										<tr>
											<td>${student.userNum }</td>
											<td>${student.stuName }</td>
											<td>${student.sex }</td>
											<td>${student.major }</td>
											<td>${student.stuClass }</td>
											<td>${student.title }</td>
											<td>${student.tel }</td>
											<td>${student.mail }</td>
											<td>${student.zdTeaName }</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="panel-footer">
									<div class="row" style="padding-left:15px">
								<input type="hidden" value="${stageName}" name="stageName">
								<input type="hidden" value="${zdTeaStatus}" name="zdTeaStatus">
								<input type="hidden" value="${className}" name="className">
								<button class="btn btn-primary" type="submit">导出</button>
								<button class="btn btn-primary" type="button" onclick="history.back()">返回</button>
							
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
							</div>
							

						</div>
						</div>
					</form>
					
				</div>
			</div>
	</body>

</html>