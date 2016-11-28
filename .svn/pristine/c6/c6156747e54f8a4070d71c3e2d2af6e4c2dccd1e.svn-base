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
			<div class="col-md-9 col-md-offset-1">
				<div class="panel panel-primary">
					<div class="panel-heading"><span class="fa fa-edit"></span>&nbsp;&nbsp;课题信息</div>
					<form class="form-horizontal" role="form" method="post" action="##">
						<div style="padding: 50px;">
						<table class="table table-bordered table-responsive table-hover">
							<c:if test="${paper!=null}">
							<tbody>								
								<tr>
									<td><strong>学生姓名：</strong>${paper.stuName}</td>
								</tr>
								<tr>
									<td><strong>指导老师：</strong>${paper.zdTeacher}</td>
								</tr>
								<tr>
									<td><strong>课题名称：</strong>${paper.title}</td>
								</tr>
								<tr>
									<td><strong>题目性质：</strong>${paper.titleNature}</td>
								</tr>
								<tr>
									<td><strong>完成形式：</strong>${paper.titleForm}</td>
								</tr>
								<tr>
									<td><strong>立题理由：</strong><br>${paper.titleReason}</td>
								</tr>																
							</tbody>					
						</table>
						<table class="table table-bordered table-responsive table-hover">
							<tbody>
							    <tr>
							    	<td>审阅成绩:${paper.syGrade}</td>
							    	<td>评阅成绩:${paper.pyGrade}</td>
							    	<td>答辩成绩:${paper.dbGrade}</td>
							    </tr>
								<tr>
									
									<td>
										<c:choose>
											<c:when test="${paper.taskBook!=null}"><a href="<%=basePath%>downloadFile.chtm?filePath=${paper.taskBook}">任务书</a></c:when>
											<c:otherwise>无任务书记录</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>									
											<c:when test="${paper.openingReport!=null}"><a href="<%=basePath%>downloadFile.chtm?filePath=${paper.openingReport}">开题报告</a></c:when>
											<c:otherwise>无开题报告记录</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${paper.midCheck!=null}"><a href="<%=basePath%>downloadFile.chtm?filePath=${paper.midCheck}">中期检查</a></c:when>
											<c:otherwise>无中期检查记录</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td>
										<c:choose>
											<c:when test="${paper.firstPaper!=null}"><a href="<%=basePath%>downloadFile.chtm?filePath=${paper.firstPaper}">论文初稿</a></c:when>
											<c:otherwise>无论文初稿记录</c:otherwise>
										</c:choose>				
									</td>
									<td>
										<c:choose>
											<c:when test="${paper.finalPaper!=null}"><a href="<%=basePath%>downloadFile.chtm?filePath=${paper.finalPaper}">论文定稿</a></c:when>
											<c:otherwise>无论文定稿记录</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${paper.projectFile!=null}"><a href="<%=basePath%>downloadFile.chtm?filePath=${paper.projectFile}">项目源码</a></c:when>
											<c:otherwise>无项目源码记录</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</tbody>
							</c:if>	
						</table>																
						<div style="margin-top: 20px">
							<a class="btn btn-default" onclick="history.back()">返回</a>						
						</div>
								
						</div>
					</form>				
				</div>
			</div>
		</div>
	</body>

</html>