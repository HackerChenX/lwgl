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
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://apps.bdimg.com/libs/fontawesome/4.4.0/css/font-awesome.min.css">
		<title>公告</title>
	</head>
	<body>
		<div class="container">
			<div class="row" style="margin-top: 50px;">		
				<div class="panel panel-primary">
					<div class="panel-heading" style="font-size: 15px;"><span class="fa fa-newspaper-o fa-lg"></span>&nbsp;&nbsp;通知</div>
					<div class="panel-body">
						${publicNotice.content}
						<br/>
						<c:if test="${publicNotice.filePath!=null}">
							<p>【附件下载】：<a href="<%=basePath %>downloadFile.chtm?filePath=${publicNotice.filePath}">${publicNotice.realName}</a></p>
						</c:if>						
					</div>
				</div>
			</div>
		</div>
	</body>
</html>