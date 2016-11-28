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
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width,initial-scale=1">
<title></title>
</head>

<body style="font-family: '微软雅黑'; font-size: 17px;">
	<div class="container" style="margin-top: 50px;">
		<form  name="applyTitleSelf" class="form-horizontal" role="form" method="post" action="<%=basePath%>student/ApplyTitleSelf_applyTitle.shtm">
			<div class="row">
				<div class="col-md-9 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-edit"></span>&nbsp;&nbsp;课题信息
						</div>
						<div class="panel-body" style="padding-bottom: 100px;padding-top: 50px;">
							<div class="form-group ">
								<label class="col-sm-3 control-label">课题名称:</label>
								<div class="col-sm-8">
									<input class="form-control" type="text" id="titleName" name="titleName" value="${Title.title}" readonly/>							
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">题目性质:</label>
								<div class="col-sm-8">
									<select class="form-control" type="text" id="titleNature" name="titleNature" readonly>					
										<option>${Title.nature}</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">完成形式:</label>
								<div class="col-sm-8">
									<select class="form-control" type="text" id="titleForm" name="titleForm" readonly>
										<option>${Title.titleForm}</option>				
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">立题理由:</label>
								<div class="col-sm-8">
									<textarea name="titleReason" id="titleReason" class="form-control" rows="6" readonly>${Title.titleReason}</textarea>
								</div>
							</div>
							<c:if test="${teaTitle==0}">
							<div class="form-group">		
								<c:if test="${Title.teaIdea!=null&&Title.teaIdea!=''}">
									<label class="col-sm-3 control-label">指导老师意见:</label>
									<div class="col-sm-8">
										<textarea name="teaIdea" id="teaIdea" class="form-control" rows="2" readonly>${Title.teaIdea}</textarea>
									</div>
								</c:if>
							</div>
							<div class="form-group">	
								<c:if test="${Title.leaderIdea!=null&&Title.leaderIdea!=''}">
									<label class="col-sm-3 control-label">专业负责人意见:</label>
									<div class="col-sm-8">
										<textarea name="leaderIdea" id="leaderIdea" class="form-control" rows="2" readonly>${Title.leaderIdea}</textarea>
									</div>
								</c:if>
							</div>
							<div class="form-group">	
								<c:if test="${Title.managerIdea!=null&&Title.managerIdea!=''}">
									<label class="col-sm-3 control-label">教学秘书意见:</label>
									<div class="col-sm-8">
										<textarea name="managerIdea" id="managerIdea" class="form-control" rows="2" readonly>${Title.managerIdea}</textarea>
									</div>
								</c:if>
							</div>
							</c:if>
							<div class="col-sm-6 col-sm-offset-3" style="margin-top: 20px">
								<div>
									<a class="btn btn-default" href="javascript:" onclick="self.location=document.referrer;">返回</a>
								</div>
							</div>
							</div>						
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>