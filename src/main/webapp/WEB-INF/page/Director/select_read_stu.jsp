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
		 <form action="<%=basePath %>majorLeader/findPyTea_jumpSelectPyStu.shtm" method="post" id="select">
		 		<input name="pageNo"  type="hidden"  value="${page.pageNo}"   id="pageNow" />
				<input name="pageSize"type="hidden"  value="${page.pageSize}" id="pageSize">
				<input type="hidden" name="teaId" value="${teaId}">
		</form>
		 
		<div class="row" style="margin-top:50px">
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
									<c:forEach var="student" items="${page.results}">
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
						<div class="row" style="padding-left:15px">
							<input type="button" class="btn btn-default" id="check_all" value="全选"></input>&nbsp;&nbsp;
							<input type="hidden" name="teaId" value="${teaId}">
							<button class="btn btn-primary" type="submit">确认</button>&nbsp;&nbsp;
							<button class="btn btn-default" type="reset">重置</button>&nbsp;&nbsp;
							<a class="btn btn-default" href="<%=basePath %>majorLeader/findPyTea.shtm">返回</a>									 		
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