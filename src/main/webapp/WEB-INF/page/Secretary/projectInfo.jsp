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
		<div class="container" style="margin-top: 50px;">
			<div class="row">
				<div class="col-md-9 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-edit"></span>&nbsp;&nbsp;审核课题</div>
						<form class="form-horizontal" role="form" method="post" action="<%=basePath %>secretary/findTeaTitle_checkTeaTitile.shtm">
							<div style="padding: 50px;">
							<table class="table table-bordered table-responsive table-hover">
								<tbody>
									<tr>
										<td><strong>课题申报人：</strong>${teaTitle.teacher.teaName}</td>
									</tr>
									<tr>
										<td><strong>课题所属专业：</strong>${teaTitle.teacher.major}</td>
									</tr>
									<tr>
										<td><strong>课题名称：</strong>${teaTitle.title}</td>
									</tr>
									<tr>
										<td><strong>题目性质：</strong>${teaTitle.nature}</td>
									</tr>
									<tr>
										<td><strong>完成形式：</strong>${teaTitle.titleForm}</td>
									</tr>
									<tr>
										<td><strong>立题理由：</strong><br>${teaTitle.titleReason}</td>
									</tr>
								</tbody>
							</table>
							<div>
								<label class="control-label">审核意见:</label>
								<div>
									<textarea class="form-control" rows="4" name="managerIdea"></textarea>
								</div>
							</div>
							<div>
								<input type="hidden" value="${teaTitle.id}" name="ids">
								<label class=" control-label">是否通过审核:</label>
								<div>
									<select class="form-control" type="text" name="status">
										<option value="1">通过</option>
										<option value="2">退回修改</option>
									</select>
								</div>
							</div>				
							<div style="margin-top: 20px">
								<button class="btn btn-primary" type="submit">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-default" onclick="history.back()">返回</a>						
							</div>
							<div class="alert alert-info" style="margin-top: 20px">
								<p>通过：表示教学秘书认可该课题,所属专业学生可选择作为论文题目。</p>
                                <p>退回修改：表示教学秘书不认可该课题，要求申报人根据审核意见修改课题内容，重新申报。</p>
							</div>			
							</div>
						</form>				
					</div>
				</div>
			</div>
		</div>
	</body>

</html>