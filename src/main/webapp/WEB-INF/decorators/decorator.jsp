<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@page import="com.hlzt.commons.helper.SysConfig"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title><sitemesh:write property='title' /></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />

<link rel="stylesheet" type="text/css" href="resource/commons/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="resource/commons/css/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="resource/commons/css/index.css"/>
<script src="resource/commons/js/jquery-1.10.2.min.js" type="text/javascript" charset="utf-8"></script>
<script src="resource/commons/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
<sitemesh:write property='head' />

<style type="text/css">
table{
margin-left:auto;
margin-right:auto;
width:800px
} 
tr{
  width:800px
}

  th,td{
     border: 1px solid #ddd;
     height:30px;
     padding:5px;
     text-align: center;
  } 
  .text-center td{
  border:none;
  }
</style>
</head>

<body>
	<nav class="navbar  " role="navigation">
				<div class="container-fluid">
					<div class="navbar-header ">
						<img src="resource/commons/img/logo.png" />
						<span class="logo">政务直通车</span>
						<button class="navbar-toggle" data-toggle="collapse" 
							data-target="#navbar-collapse" style="color:#000;">
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
	    		</div>
					<div class="collapse navbar-collapse clearfix" id="navbar-collapse" >
					
						<ul class="nav navbar-nav nav-ul" style="margin-left: 100px" >
							<li>
								<a href="javascript:; ">系统管理</a>
							</li>
						
						</ul>	
						<div class=" navbar navbar-right">
							<ul class="nav navbar-nav">							
								<li class="dropdown">
									<a href="javacript:;" class="dropdown-toggle" data-toggle="dropdown">
										<span class="glyphicon glyphicon-user"></span> ${user.realName }
										<b class="caret"></b>
									</a>
									<ul class="dropdown-menu">
									    <li><a href="logout.shtm">退出</a></li>
									    <li><a href="power/user/beforeModifyPassWd.shtm?userId=${user.id }">密码重置</a></li>
									    
									 </ul>
								</li>						
							</ul>
						</div><!-- navbar-right -->
					</div><!-- navbar-collapse -->
				</div><!-- container-fluid -->
			</nav>
			<div class="center">
				<div class="container-fluid clearfix">
					<div class="center-l pull-left col-lg-2" style="padding-right:0;">
						<span style="color:#3c8dbc;">功能导航</span>
						<ul class="center-l-ul">
				
							<li class="active"><a href="power/user/showPageUsers.shtm"><span class="glyphicon glyphicon-th-list"></span>&nbsp;&nbsp;&nbsp;&nbsp;用户管理</a></li>
							<li><a href="power/role/showPageRoles.shtm"><span class="glyphicon glyphicon-th-list"></span>&nbsp;&nbsp;&nbsp;&nbsp;角色管理</a></li>
						

					</div>
					<!-- ************************* 右侧-->
					<div class="center-r pull-left col-lg-10">
					<!-- ***************其他页面内容 -->
						<section class="content-header">
				    			<ol class="breadcrumb"">
						                    <li><a><i class="fa fa-home"></i>系统管理</a></li>
						            <!--         <li><a>权限管理</a></li>
						                    <li class="active">用户管理</li> -->
						        </ol>
						</section>
						<div class="content" >
							
							<sitemesh:write property='body' />

						</div>
					<!-- ******************内容结束 -->
						
						<div class="text-center">
									<c:if test="${page.isAjax==0 }">
										<table style="margin:10px auto;width:auto;float:left; font-size:14px;">
											<tr style="padding:0px;">
												<td style="border:1px #ddd solid;background-color:#fff; padding:0px 4px 0px 4px;">
													<c:if test="${page.pageNo-1>0 }">
														<a href="${page.pageUrl }?pageNo=${page.pageNo-1}&${page.urlParam }">上一页</a>
													</c:if>
												</td>
												<td  style="border:1px #ddd solid;background-color:#fff;padding:0px 4px 0px 4px;"><a>第${page.pageNo }页</a></td>
												<td style="border:1px #ddd solid;background-color:#fff; padding:0px 4px 0px 4px;" >
													<c:if test="${page.pageNo+1<=page.totalPage }">
														<a href="${page.pageUrl }?pageNo=${page.pageNo+1}&${page.urlParam }">下一页</a>
													</c:if>
												</td>
												<td>共${page.totalRecord }条&nbsp;共${page.totalPage}页</td>
												<td>
												  	跳转到&nbsp;<input type="text" value="" id="zc_tz_text" style="width:30px;height:30px" />&nbsp;页&nbsp;
												    <input type="button" value="跳转" id="zc_tz_button" style="padding:5px;" onclick="zcTzClick(${page.totalPage},'${page.pageUrl }')" />
												</td>
												<td>
												 	显示&nbsp;<input type="text" value="<%=GlobalVar.getPageSize() %>" id="page_size_text" style="width:30px;height:30px" />&nbsp;条/页&nbsp;
													<input type="button" value="设置" id="page_size_button" style="padding:5px;" onclick="pageSizeClick('${page.pageUrl }')" />
												</td>
											</tr>
										</table>
								</c:if>
							</div>
					</div>
					
					<!-- ************************ -->
				</div>	
			</div>
			<script type="text/javascript">
					$(function(){
						$('.center-l-ul').find('li').click(function(){
							$('.center-l-ul').find('li').removeClass();
							$(this).addClass('active');
						});
						$('.group-add').find('button').click(function(){
							$('.group-add').find('button').removeClass('active1');
							$(this).addClass('active1');
						});
					})
					
			
		//登陆			
		function zcTzClick(totalPage,pageUrl)
			{
				$(function(){
					
					var reqPage=$("#zc_tz_text").val();
					if(isNaN(reqPage)||reqPage.length<=0)
					  {
					  	alert("请输入数字");
					  	return ;
					  }
					if(reqPage>totalPage||reqPage<=0)
						{
						  alert("没有该页");
						}
					else
						{
							window.location.href=pageUrl+"?pageNo="+reqPage;
						
						}
						
					    
				})
			}
			
			/**
			 * 每页显示记录设置
			 * @param pageUrl
			 */
			function pageSizeClick(pageUrl)
			{  $(function(){
					var pageSize=$("#page_size_text").val();
					
					if(isNaN(pageSize))
						{
						  alert("请输入数字！");
						}
					else
					  window.location.href="wx/apiparams/resetPageSize.shtm?pageUrl="+pageUrl+"&pageSize="+pageSize;
				})
			}
	
				</script>
	</body>
</html>
