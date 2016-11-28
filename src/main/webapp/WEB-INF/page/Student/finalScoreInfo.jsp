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
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<title></title>
	</head>

	<body>
		<div class="container" style="margin-top: 50px;">
			<div class="row">
				<div class="col-xs-12 col-md-10 col-md-offset-1">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;成绩汇总
							</div>
							<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered table-striped table-hover" style="text-align: center;">
									<thead>
										<tr>
											<th>课题名称</th>
											<th>指导老师</th>
											<th>审阅成绩</th>																		
											<th>评阅成绩</th>
											<th>答辩成绩</th>
											<th>最终成绩</th>
											<th>评价</th>
											<th>优秀论文</th>
										</tr>
									</thead>
									<tbody>
									<c:if test="${grade.dbGrade!=null&&grade.pyGrade!=null&&grade.syGrade!=null}">
										<tr>
											<td>${grade.title}</td>
											<td>${grade.zdTeacherName}</td>
											<td>${grade.syGrade}</td>
											<td>${grade.pyGrade}</td>
											<td>${grade.dbGrade}</td>										
											<td>${grade.finalGrade}</td>
											<td><c:choose>
													<c:when test="${grade.evaluate!=null}">${grade.evaluate}</c:when>
													<c:otherwise>暂无</c:otherwise>
												</c:choose>											
											</td>
											<td><c:choose>
													<c:when test="${grade.recommend!=null}">
														<c:if test="${grade.recommend==0}">未推荐</c:if>
														<c:if test="${grade.recommend==1}">已推荐</c:if>
													</c:when>
													<c:otherwise>暂无</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:if>
									</tbody>
								</table>
							</div>
							</div>
						</div>
				</div>
			</div>
		</div>
	</body>

</html>