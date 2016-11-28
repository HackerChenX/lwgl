<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" />
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width,initial-scale=1">
<title></title>
</head>

<body>
<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">		
<div class="container col-md-6 col-md-offset-3">
		<div class="row" style="margin-top: 50px;margin-left: 0px;margin-right: 0px;">
			<div class="panel panel-info">
				<div class="panel-heading"><span class="fa fa-check-square-o"></span>&nbsp;&nbsp;提示</div>
				<div class="panel-body">
					<div class="alert alert-danger" role="alert">
					<div style="text-align: center;">
					<p>${errorMsg}</p>
					</div>
					</div>
				</div>
			</div>
		</div>
   </div>
</body>
</html>
