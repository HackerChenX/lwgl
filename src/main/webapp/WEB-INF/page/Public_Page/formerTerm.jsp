<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
		<title></title>
	</head>

	<body>
		<div class="row" style="margin-top: 50px;">
		<div class="col-xs-12 col-md-11 col-md-offset-1">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form role="form" class="form-inline" method="post" action="<%=basePath%>${url}/formerTerm.shtm">
						<div class="form-group">
							<label>往届年份：</label>
							<input type="text" name="formerTerm" class="form-control input-sm" value="${formerTerm}"/>
						</div>
						<div class="form-group">
							<label>指导教师姓名：</label>
							<input type="text" name="teacherName" class="form-control input-sm" value="${teacherName}" />
						</div>
						<div class="form-group">
							<label>学生姓名：</label>
							<input type="text" name="studentName" class="form-control input-sm" value="${studentName}"/>
						</div>
						<div class="form-group">
							<label>学生学号：</label>
							<input type="text" name="studentNum" class="form-control input-sm" value="${studentNum}" />
						</div>
						
						<input name="pageNo"  type="hidden"  value="${page.pageNo}"   id="pageNow" />
						<input name="pageSize"type="hidden"  value="${page.pageSize}" id="pageSize">
						<button class="btn btn-primary" type="submit" id="keyWordSelect">查询</button>
					</form>
				</div>
			</div>	
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<form method="post" action="<%=basePath %>${url}/exportFormerTermInfo.shtm" >
					<div class="panel panel-primary">					
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;往届信息查询
						</div>
						<div class="table-responsive">
							<table class="table table-bordered table-striped table-hover" style="text-align: center;">
								<thead>
									<tr>
										<th>学号</th>
										<th>姓名</th>							
										<th>专业名称</th>
										<th>班级</th>
										<th>毕业年级</th>
										<th>课题名称</th>			
										<th>最终成绩</th>
										<th>指导老师</th>
										<th>评阅老师</th>
									</tr>
								</thead>
								<tbody>
								<c:if test="${page.results!=null}">
									<c:forEach var="paper" items="${page.results}">
										<tr>
											<td>${paper.stuNum}</td>
											<td>${paper.stuName}</td>
											<td>${paper.stuMajor}</td>
											<td>${paper.stuClass}</td>
											<td>${paper.term}</td>
											<td><a href="<%=basePath%>${url}/formerTermInfo.shtm?id=${paper.id}">${paper.title}</a></td>
											<td>${paper.finalGrade}</td>
											<td>${paper.zdTeacher}</td>
											<td>${paper.pyTeacher}</td>											
										</tr>	
								    </c:forEach>	
							    </c:if>														
								</tbody>
							</table>
						</div>
						<div class="panel-footer">
						 	<input type="hidden" value="${formerTerm}" name="formerTerm">
							<input type="hidden" value="${teacherName}" name="teacherName">
							<input type="hidden" value="${studentName}" name="studentName">
							<input type="hidden" value="${studentNum}" name="studentNum">
							<button class="btn btn-primary" type="submit">导出</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	
	</body>

</html>