<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<base href="<%=basePath %>">
		<link rel="stylesheet" href="<%=basePath %>css/homepageStyle.css" />
		<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
		<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/homepage.js"></script>
		<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">		
		<link href="<%=basePath %>css/dataurl.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=basePath %>css/metisMenu.min.css" />	
		<script type="text/javascript" src="<%=basePath%>js/page.js"></script>
		<title>毕业设计管理系统</title>
		<script>
			$(function() {
				$('#menu').metisMenu();
			});
		</script>
		<style type="text/css">
		th{
			text-align:center;
		}
		</style>
		<sitemesh:write property='head' />
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
								<a href="<%=basePath %>secretary.shtm"><span class="glyphicon glyphicon-home"></span>&nbsp;返回主页</a>
							</li>
							<!--姓名-->
							<li>
								<a><span class="glyphicon glyphicon-user"></span>&nbsp;${user.realName}</a>
							</li>
							<!--身份-->
							<li>
								<a><span class="glyphicon glyphicon-map-marker"></span>&nbsp;教学秘书</a>
							</li>
							<!--交换角色下拉菜单-->
							<c:if test="${teacher.roleList.size()>=3}">
							<li class="dropdown">
								<a href="##" data-toggle="dropdown" class="dropdown-toggle"><span class="fa fa-group"></span>&nbsp;交换角色&nbsp;<span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li>
										<shiro:hasRole name="zd_teacher">
										<a href="<%=basePath %>zdTeacher.shtm"><span class="fa fa-paper-plane-o"></span>&nbsp;指导老师</a>
										</shiro:hasRole>
									</li>
									<li>
										<shiro:hasRole name="major_leader">
										<a href="<%=basePath %>majorLeader.shtm"><span class="fa fa-paper-plane-o"></span>&nbsp;专业负责人</a>
										</shiro:hasRole>
									</li>
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
									<a href="<%=basePath %>secretary/noticeList.shtm">
										<span class="fa fa-tag"></span>&nbsp;院内公告
									</a>
								</li>
								<li>
									<a href="<%=basePath %>secretary/findTeaTitle.shtm?status=0">
										<span class="fa fa-tag"></span>&nbsp;审核教师申报课题
									</a>
								</li>
								<li>
									<a href="<%=basePath %>secretary/findApplyTitle.shtm?status=0">
										<span class="fa fa-tag"></span>&nbsp;审核学生自拟课题
									</a>
								</li>
								<li>
									<a href="<%=basePath %>secretary/findAllGrade.shtm">
										<span class="fa fa-tag"></span>&nbsp;成绩汇总查询
									</a>
								</li>
								<li>
									<a href="<%=basePath %>secretary/findGradeAllEvaluate.shtm">
										<span class="fa fa-tag"></span>&nbsp;成绩总评
									</a>
								</li>								
								<li>
									<a href="<%=basePath %>secretary/findYxPaper.shtm">
										<span class="fa fa-tag"></span>&nbsp;学院推荐优秀论文
									</a>
								</li>
								<li>
									<a href="<%=basePath %>secretary/findStuPaper.shtm">
										<span class="fa fa-tag"></span>&nbsp;抽检学生信息
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
									<a href="<%=basePath%>secretary/findSpecialInit.shtm">
										<span class="fa fa-tag"></span>&nbsp;截止时间延期
									</a>
								</li>						
							</ul>
						</li>
						<li>
							<a href="Javascript: void(0)" aria-expanded="false"><span class="fa fa-pie-chart fa-lg"></span></span>&nbsp;<span class="sidebar-nav-item">过程信息统计</span><span class="fa arrow"></span></a>
							<ul aria-expanded="false" class="collapse">
								<li>
									<a href="<%=basePath %>secretary/findStuInfo.shtm">
										<span class="fa fa-tag"></span>&nbsp;毕设学生信息
									</a>
								</li>
								<li>
									<a href="<%=basePath %>secretary/findTaskBookSubmit.shtm?stageName=task_book">
										<span class="fa fa-tag"></span>&nbsp;任务书提交情况
									</a>
								</li>
								<li>
									<a href="<%=basePath %>secretary/findOpeningReportSubmit.shtm?stageName=opening_report">
										<span class="fa fa-tag"></span>&nbsp;开题报告提交情况
									</a>
								</li>
								<li>
									<a href="<%=basePath %>secretary/findMidCheckSubmit.shtm?stageName=mid_check">
										<span class="fa fa-tag"></span>&nbsp;中期检查提交情况
									</a>
								</li>
								<li>
									<a href="<%=basePath %>secretary/findFinalPaperSubmit.shtm?stageName=final_paper">
										<span class="fa fa-tag"></span>&nbsp;论文定稿提交情况
									</a>
								</li>
								<li>
									<a href="<%=basePath %>secretary/findDbGroup.shtm">
										<span class="fa fa-tag"></span>&nbsp;答辩组安排情况
									</a>
								</li>
								<li>
									<a href="<%=basePath %>secretary/formerTerm.shtm">
										<span class="fa fa-tag"></span>&nbsp;往届信息查询
									</a>
								</li>
								<li style="display: none;">
									<a href="<%=basePath %>secretary/findStuSubmit.shtm" hidden="hidden">
									</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="Javascript: void(0)" aria-expanded="false"><span class="fa fa-cogs fa-lg"></span></span>&nbsp;<span class="sidebar-nav-item">初始管理</span> <span class="fa arrow"></span></a>
							<ul aria-expanded="false" class="collapse">
								<li >
									<a href="<%=basePath %>secretary/findNowTerm.shtm">
										<span class="fa fa-tag"></span>&nbsp;当前届设置
									</a>
								</li>
								<li >
									<a href="<%=basePath %>secretary/findMajor.shtm">
										<span class="fa fa-tag"></span>&nbsp;专业管理
									</a>
								</li>
								<li >
									<a href="<%=basePath %>secretary/findTitleNature.shtm">
										<span class="fa fa-tag"></span>&nbsp;课题性质设置
									</a>
								</li>
								<li >
									<a href="<%=basePath %>secretary/findTitleForm.shtm">
										<span class="fa fa-tag"></span>&nbsp;完成形式设置
									</a>
								</li>
								<li >
									<a href="<%=basePath %>secretary/findZhiCheng.shtm">
										<span class="fa fa-tag"></span>&nbsp;教师职称管理
									</a>
								</li>
								<li >
									<a href="<%=basePath %>secretary/findStagePlan.shtm">
										<span class="fa fa-tag"></span>&nbsp;截止日期设置
									</a>
								</li>
								<li >
									<a href="<%=basePath %>secretary/findGradeWeight.shtm">
										<span class="fa fa-tag"></span>&nbsp;评分权重设置
									</a>
								</li>
								<li >
									<a href="<%=basePath %>secretary/updateSystem.shtm">
										<span class="fa fa-tag"></span>&nbsp;系统维护开关
									</a>
								</li>
								<li >
									<a href="<%=basePath %>secretary/dataSave.shtm">
										<span class="fa fa-tag"></span>&nbsp;往年数据归档
									</a>
								</li>
							</ul>
						</li>  
						
						<li>
							<a href="Javascript: void(0)" aria-expanded="false"><span class="fa fa-key fa-lg"></span></span>&nbsp;<span class="sidebar-nav-item">账号管理</span> <span class="fa arrow"></span></a>
							<ul aria-expanded="false" class="collapse">
								<li >
									<a href="<%=basePath %>secretary/findInfo.shtm">
										<span class="fa fa-tag"></span>&nbsp;修改个人信息
									</a>
								</li>
								<li >
									<a href="<%=basePath %>user/findStudent.shtm">
										<span class="fa fa-tag"></span>&nbsp;学生账号
									</a>
								</li>
								<li >
									<a href="<%=basePath %>user/findTeacher.shtm">
										<span class="fa fa-tag"></span>&nbsp;教师账号
									</a>
								</li>
								<li >
									<a href="<%=basePath %>user/findTeaRole.shtm?role =5">
										<span class="fa fa-tag"></span>&nbsp;权限管理
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
					<!--<div class="row" style="margin-right: 5%">
							<div class="col-xs-12 col-md-11 col-md-offset-1">
								<div  style="float: right; margin-top:-5.3%">
									<c:if test="${page.isAjax==0 }">
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
						</div>  -->
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