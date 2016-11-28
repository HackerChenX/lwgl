<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
		<link rel="stylesheet" href="<%=basePath %>css/homepageStyle.css" />
		<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
		<script src="//apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/homepage.js"></script>
		<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">		
		<link href="<%=basePath %>css/dataurl.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=basePath%>css/metisMenu.min.css" />	
		<script type="text/javascript" src="<%=basePath%>js/page.js"></script>
		<style type="text/css">
		th{
			text-align:center;
		}
		</style>
		<title>毕业设计管理系统</title>
	</head>

	<body>
		<header>
			<!--顶栏logo&全响应设置-->
			<nav class="navbar navbar-fixed-top navbar-default">
				<div class="container-fluid">
					<div class="navbar-header">
						<!--顶栏右侧内容全响应下拉设置-->
						<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
	                    	<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
	               		</button>
						<!--左侧导航栏全响应隐藏/显示设置-->
						<button type="button" class="navbar-toggle show pull-left" data-toggle="collapse" data-target="sidebar">
	                    	<span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
	                   </button>
						<!--logo-->
						<div style="float: left"><img src="<%=basePath %>img/logo.png" height="50px" /></div>
					</div>
					<!--顶栏右侧内容-->
					<div id="navbar" class="collapse navbar-collapse">
						<ul class="nav navbar-nav navbar-right">
							<li>
								<a href="<%=basePath %>student.shtm"><span class="glyphicon glyphicon-home"></span>&nbsp;返回主页</a>
							</li>
							<!--姓名-->
							<li>
								<a><span class="glyphicon glyphicon-user"></span>&nbsp;${user.realName}</a>
							</li>
							<!--身份-->
							<li>
								<a><span class="glyphicon glyphicon-map-marker"></span>&nbsp;学生</a>
							</li>
							<!--退出-->
							<li>
								<a href="<%=basePath %>logout.shtm"><span class="glyphicon glyphicon-log-out"></span>&nbsp;退出</a>
							</li>
						</ul>
					</div>
				</div>
			</nav>
		</header>
		<div class="container-fluid all">
			<!--左侧导航栏-->
			<aside class="sidebar">
				<nav class="sidebar-nav">
					<ul class="metismenu" id="menu">
						<li>
							<a href="Javascript: void(0)" aria-expanded="false">
								<span class=" fa fa-graduation-cap fa-lg"></span>
								<span class="sidebar-nav-item">流程管理</span>
								<span class="fa arrow"></span>
							</a>
							<ul aria-expanded="false" class="collapse">	
								<li >
									<a href="<%=basePath %>student/initApplyTitle.shtm">
										<span class="fa fa-tag"></span>&nbsp;教师课题申报
									</a>
								</li>
								<li >
									<a href="<%=basePath %>student/ApplyTitleSelf.shtm">
										<span class="fa fa-tag"></span>&nbsp;自拟课题申报
									</a>
								</li>
								<li >
									<a href="<%=basePath %>student/formerTerm.shtm">
										<span class="fa fa-tag"></span>&nbsp;往届课题查询
									</a>
								</li>
								<li >
									<a href="<%=basePath %>student/taskBook.shtm">
										<span class="fa fa-tag"></span>&nbsp;提交任务书
									</a>
								</li>
								<li >
									<a href="<%=basePath %>student/openingReport.shtm">
										<span class="fa fa-tag"></span>&nbsp;提交开题报告
									</a>
								</li>
								<li >
									<a href="<%=basePath %>student/midCheck.shtm">
										<span class="fa fa-tag"></span>&nbsp;提交中期检查
									</a>
								</li>
								<li >
									<a href="<%=basePath %>student/firstPaper.shtm">
										<span class="fa fa-tag"></span>&nbsp;提交论文初稿
									</a>
								</li>
								<li >
									<a href="<%=basePath %>student/finalPaper.shtm">
										<span class="fa fa-tag"></span>&nbsp;提交论文定稿
									</a>
								</li>
								<li >
									<a href="<%=basePath %>student/dbGroupInfo.shtm">
										<span class="fa fa-tag"></span>&nbsp;查看答辩安排
									</a>
								</li>
								<li >
									<a href="<%=basePath %>student/finalScoreInfo.shtm">
										<span class="fa fa-tag"></span>&nbsp;查询最终成绩
									</a>
								</li>
								<li >
									<a href="<%=basePath %>student/allFile.shtm">
										<span class="fa fa-tag"></span>&nbsp;课题文件提交
									</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="Javascript: void(0)" aria-expanded="false"><span class="fa fa-wrench fa-lg"></span></span>&nbsp;<span class="sidebar-nav-item">特殊情况处理</span><span class="fa arrow"></span></a>
							<ul aria-expanded="false" class="collapse">
								<li >
									<a href="<%=basePath %>student/initApplyForDelay.shtm">
										<span class="fa fa-tag"></span>&nbsp;延期申请
									</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="Javascript: void(0)" aria-expanded="false"><span class="fa fa-key fa-lg"></span></span>&nbsp;<span class="sidebar-nav-item">账号管理</span> <span class="fa arrow"></span></a>
							<ul aria-expanded="false" class="collapse">
								<li >
									<a href="<%=basePath %>student/findInfo.shtm">
										<span class="fa fa-tag"></span>&nbsp;修改个人信息
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</nav>
			</aside>
			<div class="maincontent row">
				<div class="col-sm-12">			
					<sitemesh:write property='body' />					
				</div>
			</div>
		</div>
		<a href="#top" id="goTop"><i class="fa fa-angle-up fa-3x"></i></a>
		<!--回到顶部-->
		
		<script>
			$(function() {
				$('#menu').metisMenu();
			});
		</script>
        <a href="#top" id="goTop"><i class="fa fa-angle-up fa-3x"></i></a>
		<!--回到顶部-->	
        <script src="<%=basePath %>js/pace.min.js"></script>
        <!--进度条效果-->
		<script type="text/javascript" src="<%=basePath %>js/scrolltopcontrol.js"></script>
		<!--平滑滚动回顶部效果-->		
		<!--[if lt IE 9]>
        <script src="js/html5shiv.min.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->
		<script type="text/javascript" src="<%=basePath %>js/metisMenu.min.js"></script>
		<!--左侧菜单js-->
		<script type="text/javascript" src="<%=basePath %>js/bootstrap.min.js"></script>
		<!--bootstrap js插件-->		
	</body>

</html>