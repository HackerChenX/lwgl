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
		<title></title>
	</head>

	<body>
		<style type="text/css">
		th{
			text-align:center;
		}
		</style>
		<div class="row" style="margin-top:50px;">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<form role="form" action="<%=basePath %>majorLeader/findPyTea_setPyStu.shtm" method="post">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;学生课题列表
						</div>
						<div class="table-responsive">
							<table class="table table-bordered table-hover" style="text-align:center">
								<thead>
									<tr class="tr">
										<th style="text-align: center;">选择</th>
										<th>学号</th>
										<th>姓名</th>
										<th>性别</th>
										<th>班级</th>
										<th>课题名</th>
										<th>指导老师</th>
										<th>职称</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="student" items="${list}">
									<tr>
										<td style="text-align: center;">
											<input type="checkbox" name="stuIds" value="${student.userId}">
										</td>
										<td>${student.userNum}</td>
										<td>${student.stuName}</td>
										<td>${student.sex}</td>
										<td>${student.stuClass}</td>
										<td>${student.title}</td>
										<td>${student.zdTeaName}</td>
										<td>${student.zdTeaZhiCheng}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="panel-footer">
							<input type="button" class="btn btn-default" id="check_all" value="全选"></input>&nbsp;&nbsp;
							<input type="hidden" name="teaId" value="">
							<button class="btn btn-primary" type="submit">移除</button>&nbsp;&nbsp;
							<button class="btn btn-default" type="reset">重置</button>&nbsp;&nbsp;
							<a class="btn btn-default" href="<%=basePath%>majorLeader/findPyTea.shtm">返回</a>
						</div>
				</form>
				</div>
			</div>
		</div>
		<script>
			var a = 1;
			$('#check_all').click(function() {
				if(a == 1) {
					$('[name=stuIds]:checkbox').prop('checked', true);
					a--;
				} else {
					$('[name=stuIds]:checkbox').attr('checked', false);
					a++;
				}
			})
		</script>
	</body>

</html>