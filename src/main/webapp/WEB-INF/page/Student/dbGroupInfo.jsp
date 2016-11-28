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
		<!--<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">-->
		<title></title>
	</head>

	<body>
		<div class="container" style="margin-top: 50px;">
			<div class="row">
				<div class="col-xs-12 col-md-10 col-md-offset-1">
					<form role="form" action="##" method="post">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;答辩组安排
							</div>
							<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered table-striped table-hover" style="text-align: center;">
									<thead>
										<tr>
											<th>组名</th>																		
											<th>答辩秘书</th>
											<th>答辩日期</th>
											<th>答辩地点</th>
										</tr>
									</thead>
									<tbody>
									<c:if test="${dbGroup!=null}">
										<tr>								
											<td>${dbGroup.groupName}</td>
											<td>${dbGroup.groupSecretaryName}</td>										
											<td>${dbTime}</td>
											<td>${dbGroup.groupSite}</td>
										</tr>
									</c:if>
									</tbody>
								</table>
							</div>	
							</div>				
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>

</html>