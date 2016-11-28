<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'sucess.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    登录成功。。。<br>	
    <shiro:authenticated>用户已经登录显示此内容</shiro:authenticated>
    <shiro:hasRole name="student">
    	学生
    </shiro:hasRole><br>
    <shiro:hasRole name="major_leader">
    	老师
    </shiro:hasRole><br>
    <shiro:hasRole name="zd_teacher">
    	指导老师
    </shiro:hasRole><br>
    <shiro:hasPermission name="delete">
    	删除
    </shiro:hasPermission><br>
    <shiro:hasPermission name="add">
    	增加
    </shiro:hasPermission>
  </body>
</html>
