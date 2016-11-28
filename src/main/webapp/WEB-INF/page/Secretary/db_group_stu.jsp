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
			<div class="row" style="margin-top: 50px;">
				<div class="col-xs-12 col-md-11 col-md-offset-1">
					<form action="<%=basePath %>secretary/findDbGroup_findStuByDbgroup.shtm" method="post" id="select">
						<input type="hidden" value="${dbGroupId}" name="dbGroupId">
						<input type="hidden" value="${page.pageNo}" name="pageNo" id="pageNow">					
					</form>
					<form role="form" action="<%=basePath %>secretary/exportStuByDbgroup.shtm" method="post">
						<input type="hidden" value="${dbGroupId}" name="dbGroupId">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;学生信息
							</div>
							<div class="table-responsive">
								<table style="text-align:center" class="table table-bordered table-striped table-hover">
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
								<button class="btn btn-primary" type="submit">导出</button>
								<button class="btn btn-primary" type="button" href="<%=basePath %>secretary/findDbGroup.shtm">返回</button>
							</div>

						</div>
					
					</form>
				</div>
			</div>
	</body>

</html>