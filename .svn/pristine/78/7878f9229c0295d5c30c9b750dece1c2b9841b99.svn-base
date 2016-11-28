<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width,initial-scale=1">

		<!--CDN_CSS ↓↓↓↓-->
		<!--<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://apps.bdimg.com/libs/fontawesome/4.4.0/css/font-awesome.min.css">-->
		<!--CDN_CSS ↑↑↑↑-->
		<title>学生论文设计</title>

	</head>

	<body>
		<!--row_1 ↓↓↓↓-->
		<div class="row" style="margin-top: 50px;" id="row1">
			<div class="col-xs-12 col-md-10 col-md-offset-1">
				<form class="form-inline" method="post" action="<%=basePath %>guideTeacher/stuAllFile.shtm">
					<div class="panel panel-primary">
						<div class="panel-body">
							<label class="control-label">学号：</label><input name="stuNum" value="${stuNum}" type="text" class="form-control" id="stu_id" placeholder="学生学号" />
							<label class="control-label">姓名：</label><input name="stuName" value="${stuName}" type="text" class="form-control" id="stu_name" placeholder="学生姓名" />
							<button class="btn btn-primary" type="submit" id="search"><span class="fa fa-search">搜索</span></button>
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<div class="row">
			<div class="col-xs-12 col-md-10 col-md-offset-1">
				<form action="<%=basePath%>guideTeacher/exportStuInfo.shtm" method="post">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-user"></span>&nbsp;&nbsp;学生论文设计
						</div>

						<div class="table-responsive">
							<table style="text-align:center" class="table table-bordered table-hover table-striped">
								<thead>
									<tr class="tr">
										<th>姓名</th>
										<th>学号</th>
										<th>课题名</th>
										<th>班级</th>
										<th>论文附件</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="papers" items="${papers}">
										<tr>
											<td>${papers.stuName }</td>
											<td>${papers.stuNum }</td>
											<td>${papers.title }</td>
											<td>${papers.stuClass }</td>
											<td>
												<a class="btn btn-primary" href="<%=basePath %>downloadFile.chtm?filePath=${papers.projectFile}" target="_blank">下载</a>
											</td>

									</c:forEach>
									<c:if test="${papers==null||fn:length(papers)==0}">
										<tr>
											<td align="center" colspan="7">暂无学生信息！</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</form>
			</div>
		</div>
	</body>

</html>