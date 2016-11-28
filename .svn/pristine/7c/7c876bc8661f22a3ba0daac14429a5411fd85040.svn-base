<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width,initial-scale=1">
		
		<!--my_css  ↓↓↓↓-->
		<base href="<%=basePath %>">
		<link rel="stylesheet" href="<%=basePath %>css/homepageStyle.css" />
	
		<!--my_css  ↑↑↑↑-->
		
		<!--CDN_CSS ↓↓↓↓-->
		<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
		<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">		
			<link rel="stylesheet" href="<%=basePath %>css/dataurl.css" />
		<link rel="stylesheet" href="<%=basePath %>css/metisMenu.min.css" />
		<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/homepage.js"></script>
		<!--jQuery库CDN-->
		<script type="text/javascript" src="<%=basePath%>js/jquery.form.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/page.js"></script>
		<!--CDN_CSS ↑↑↑↑-->

		<title>毕业设计管理系统</title>
		<script type="text/javascript">
			$(function(){
				$('#menu').metisMenu();
			});
		</script>
		<style type="text/css">
		th{
			text-align:center;
		}
		</style>
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
								<a href="<%=basePath %>zdTeacher.shtm"><span class="glyphicon glyphicon-home"></span>&nbsp;返回主页</a>
							</li>
							<!--姓名-->
							<li>
								<a><span class="glyphicon glyphicon-user"></span>&nbsp;${user.realName}</a>
							</li>
							<!--身份-->
							<li>
								<a><span class="glyphicon glyphicon-map-marker"></span>&nbsp;指导老师</a>
							</li>
							<!--交换角色下拉菜单-->
							<c:if test="${teacher.roleList.size()>=3}">
							<li class="dropdown">
								<a href="##" data-toggle="dropdown" class="dropdown-toggle"><span class="fa fa-group"></span>&nbsp;交换角色&nbsp;<span class="caret"></span></a>
								<ul class="dropdown-menu">
									<shiro:hasRole name="major_leader">
									<li>
										<a href="<%=basePath %>majorLeader.shtm"><span class="fa fa-paper-plane-o"></span>&nbsp;专业负责人</a>
									</li>
									</shiro:hasRole>
									<shiro:hasRole name="manager">
									<li>
										<a href="<%=basePath %>secretary.shtm"><span class="fa fa-paper-plane-o"></span>&nbsp;教学秘书</a>
									</li>
									</shiro:hasRole>
								</ul>
							</li>
							</c:if>
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
								<li>
									<a href="<%=basePath%>guideTeacher/findTeaTitle.shtm">
										<span class="fa fa-tag"></span>&nbsp;申报教师课题
									</a>
								</li>
								<li>
									<a href="<%=basePath%>guideTeacher/findStuTitle.shtm?status=0">
										<span class="fa fa-tag"></span>&nbsp;审核学生申报
									</a>
								</li>
								<li>
									<a href="<%=basePath %>guideTeacher/findStudentInfo.shtm">
										<span class="fa fa-tag"></span>&nbsp;查看学生信息
									</a>
								</li>
								<li>
									<a href="<%=basePath%>guideTeacher/taskbook.shtm">
										<span class="fa fa-tag"></span>&nbsp;审阅任务书
									</a>
								</li>
								<li>
									<a href="<%=basePath%>guideTeacher/openingReport.shtm">
										<span class="fa fa-tag"></span>&nbsp;审阅开题报告
									</a>
								</li>
								<li>
									<a href="<%=basePath%>guideTeacher/midCheck.shtm">
										<span class="fa fa-tag"></span>&nbsp;审阅中期检查
									</a>
								</li>
								<li>
										<a href="<%=basePath%>guideTeacher/paperDraft.shtm">
										<span class="fa fa-tag"></span>&nbsp;审阅论文初稿
									</a>
								</li>
								<li>
									<a href="<%=basePath%>guideTeacher/paperFinal.shtm">
										<span class="fa fa-tag"></span>&nbsp;审阅论文定稿
									</a>
								</li>
								<li>
									<a href="<%=basePath%>guideTeacher/findPyStu.shtm">
										<span class="fa fa-tag"></span>&nbsp;查看评阅学生
									</a>
								</li>
								<li>
									<a href="<%=basePath%>guideTeacher/dbGroupInfo.shtm">
										<span class="fa fa-tag"></span>&nbsp;查看答辩小组
									</a>
								</li>
								<li>
									<a href="<%=basePath%>guideTeacher/findStuPaper.shtm">
										<span class="fa fa-tag"></span>&nbsp;查看学生成绩
									</a>
								</li>
								<li>
									<a href="<%=basePath%>guideTeacher/stuAllFile.shtm">
										<span class="fa fa-tag"></span>&nbsp;下载学生课题文件
									</a>
								</li>
								<li>
									<a href="<%=basePath %>guideTeacher/formerTerm.shtm">
										<span class="fa fa-tag"></span>&nbsp;往届信息查询
									</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="Javascript: void(0)" aria-expanded="false">
								<span class="fa fa-wrench fa-lg"></span>
								</span>&nbsp;<span class="sidebar-nav-item">特殊情况处理</span><span class="fa arrow"></span></a>
							<ul aria-expanded="false" class="collapse">
							<li>
									<a href="<%=basePath%>guideTeacher/checkStuDelay.shtm">
										<span class="fa fa-tag"></span>&nbsp;学生延期申请
									</a>
								</li>					
							</ul>
						</li>
									<li>
							<a href="Javascript: void(0)" aria-expanded="false"><span class="fa fa-edit"></span></span>&nbsp;<span class="sidebar-nav-item">各阶段成绩录入</span> <span class="fa arrow"></span></a>
							<ul aria-expanded="false" class="collapse">
								<li>
									<a href="<%=basePath %>guideTeacher/syGrade.shtm" >
										<span class="fa fa-tag"></span>&nbsp;审阅成绩
									</a>
								</li>
								<li>
									<a href="<%=basePath %>guideTeacher/pyGrade.shtm" >
										<span class="fa fa-tag"></span>&nbsp;评阅成绩
									</a>
								</li>
								<li>
										<a href="<%=basePath %>guideTeacher/dbGrade.shtm" >
										<span class="fa fa-tag"></span>&nbsp;答辩成绩
									</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="Javascript: void(0)" aria-expanded="false"><span class="fa fa-key fa-lg"></span></span>&nbsp;<span class="sidebar-nav-item">账号管理</span> <span class="fa arrow"></span></a>
							<ul aria-expanded="false" class="collapse">
								<li >
									<a href="<%=basePath %>guideTeacher/findInfo.shtm">
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
					<%--<div class="row" style="margin-right: 5%">
							<div class="col-xs-12 col-md-11 col-md-offset-1">
								<div  style="float: right; margin-top: -6.6%">
									<c:if test="${

.isAjax==0 }">
										<table>
											<tr>
												<td><c:if test="${page.pageNo-1>0 }">
														<a href="javascript:upPage()" class="btn btn-default btn-sm">&laquo;上一页</a>
													</c:if>
												</td>
												<td><a class="btn btn-default btn-sm">第${page.pageNo }页</a>
												</td>
												<td><c:if test="${page.pageNo+1<=page.totalPage }">
														<a href="javascript:nextPage()" class="btn btn-default btn-sm">下一页&raquo;</a>
													</c:if></td>
												<td>&nbsp;&nbsp;共${page.totalRecord
													}条&nbsp;共${page.totalPage}页</td>
												<td>&nbsp;&nbsp;跳转到&nbsp;<input type="text"
													value="" id="zc_tz_text" style="width:28px;height:28px" />&nbsp;页<input type="button" value="跳转" id="zc_tz_button" class="btn btn-default btn-sm"
													 onclick="zcTzClick(${page.totalPage})" />
												</td>
											</tr>
										</table>
									</c:if>
								</div>
							</div>
						</div>
				--%></div>
			</div>
		</div>
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