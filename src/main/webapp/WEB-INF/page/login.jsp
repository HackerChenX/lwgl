<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<!--<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">-->
		<link rel="stylesheet" href="css/bootstrap.min.css" />
		<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">		
		<link rel="stylesheet" href="<%=basePath%>css/loginStyle.css" />
		
		<script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/bootstrap.min.js"></script><!--引入bootstrap js插件-->
		<script src="js/jquery.particleground.min.js"></script><!--引入背景canvas粒子	插件-->
		<title>毕业设计管理系统</title>
		<script>
	    $(function(){
		    $("#myTab a").click(function(e){
		        e.preventDefault();
		        $(this).tab("show");
		    });
		})
        </script>
	</head>

	<body style="background-color: #f7fafc;">
<!--logo&字头-->
<div id="index-main">
		<div class="container-fluid" id="divAdapt">
			<img src="img/logo.png" />
			<img style="margin-left: 15px" id="imgAdapt" src="<%=basePath %>img/byxs.png">
		</div>

<!--左侧公告栏-->
<!--canvas背景-->
		<div id="particles"></div><!--canvas效果-->
			<div class="container" id="logincontainer">
				<div class="row">
					
     <!--选项卡组件-->
					<div class="col-xs-12 col-sm-5 col-sm-offset-1" ><!--占据六格栅栏-->
						<div>
							<ul id="myTab" class="nav nav-pills" role="tablist">
								<li class="active">
									<a href="#bulletin" role="tab" data-toggle="pill">通知公告</a>
								</li>
								<li>
									<a href="#tabulation" role="tab" data-toggle="pill">表格下载</a>
								</li>								
							</ul>
						</div>
     <!-- 选项卡面板 -->
						<div id="myTabContent" class="tab-content" style="text-align: left; padding: 15px;">
							<div class="tab-pane fade in active" id="bulletin">
							<c:if test="${noticeList!=null||fn:length(noticeList)!=0}">
								<c:forEach var="publicNotice" items="${noticeList}">					
								<a href="<%=basePath %>notice.chtm?id=${publicNotice.id }" target="_Blank">
								<c:choose>
								<c:when test="${publicNotice.top==1}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${publicNotice.title }&nbsp;&nbsp;<span class="fa fa-bookmark fa-lg"></span></p>
								</c:when>
								<c:otherwise>
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${publicNotice.title }</p>
								</c:otherwise>
							   </c:choose>
								</a>
								</c:forEach>
								</c:if>
								<c:if test="${noticeList==null||fn:length(noticeList)==0}">
								<a>
									<p align="left">暂无公告！</p>
								</a	>
								</c:if>
							</div>
							<div class="tab-pane fade" id="tabulation">
							<c:if test="${tableList!=null||fn:length(tableList)!=0}">
								<c:forEach var="publicNotice" items="${tableList}">						
							    <a href="<%=basePath %>downloadFile.chtm?filePath=${publicNotice.filePath }">
								<c:choose>
								<c:when test="${publicNotice.top==1}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${publicNotice.title}&nbsp;&nbsp;<span class="fa fa-bookmark fa-lg"></span></p>
								</c:when>
								<c:otherwise>
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${publicNotice.title}</p>
								</c:otherwise>
							    </c:choose>
								</a>
								</c:forEach>
							</c:if>
								<c:if test="${tableList==null||fn:length(tableList)==0}">
								<a>
									<p align="left">暂无表格！</p>
								</a>
								</c:if>
							</div>				
						</div>
					</div>
 <!--右侧登录栏-->
					<div class="col-xs-12 col-sm-4 col-sm-offset-1"><!--占据4格栅栏，向右偏移一格-->
						<div class="panel panel-primary">
							<div class="panel-heading" id="panelHeadFont"><span class="fa fa-user"></span>&nbsp;&nbsp;Login</div>
							<div class="panel-body" id="Loginpanel">
								<!--用户登录-->
								<form id="login" class="" action="login.shtm" method="post">
									<div class="form-group">
										<input type="text" name="username" class="form-control" id="InputAccount" placeholder="请输入您的账户">
									</div>
									<div class="form-group">
										<input type="password" name="password" class="form-control" id="InputPassword" placeholder="请输入您的密码">
									</div>
									<div class="form-group" style="padding-left:15px;padding-right:15px;">  
										<div class="row"> 
											<div class="col-sm-7" style="padding:0px;!important">								
												<input type="text" class="form-control" name="validateCode" style="vertical-align:middle;" placeholder="验证码"/>
											</div>
											<div class="col-sm-5" style="padding:0px;!important">
												<span><img id="randomImg" title="点击更换" style="vertical-align:middle;height:34px;width:100%" onclick="javascript:refresh(this);" src="randomValidateCode.chtm"></span>									
											</div>
										</div>  
									</div>
									<div class="row" style="padding-left:15px;padding-right:15px;margin-top:30px">
										<button type="submit" class="btn btn-primary col-md-12">登录系统</button>
									</div>
									<div class="row">																		
										<p>${errorMsg}</p>
									</div>	
								</form>
								<!-- 点击刷新验证码 -->
								<script type="text/javascript">
									    function refresh(obj) {
									        obj.src = "randomValidateCode.chtm?"+Math.random();
									    }
								</script>
							</div>
						</div>
					</div>
				</div>         
			</div> 
			</div>
		<div class="clearfix visible-xs-block"></div>
				<div id="loginFooter">
					<div class="container">
						<div class="row">
							<div class="col-sm-12">		
				    <p>Copyright © 2016 许昌学院智慧大数据工作室. All rights reserved. </p>
				</div>	
				</div>
		</div>										
	</body>

</html>