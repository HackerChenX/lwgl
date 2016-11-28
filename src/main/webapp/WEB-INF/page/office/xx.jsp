<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="<%=basePath%>css/homepageStyle.css" />
		<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="http://apps.bdimg.com/libs/fontawesome/4.4.0/css/font-awesome.min.css"/>
		<link rel="stylesheet" href="<%=basePath%>css/dataurl.css" />
		<link rel="stylesheet" href="<%=basePath%>css/metisMenu.min.css" />
<script type="text/javascript" src="<%=basePath%>office/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>office/main.js"></script>
<script language="javascript" type="text/javascript">
	//WebOffice1_NotifyCtrlReady();
</script>
<title>毕业设计管理系统</title>
</head>
<body>
	<div id="saveButton"  >
		<button style="margin-left:50px;" class="btn btn-primary" type="button" id="selectSave" >保存文件</button>
	如果ActiveX已启用，仍不能进行预览时请下载<a href="<%=basePath%>office/weboffice.exe">此插件</a>
		<input	hidden="hidden" id="filePath" name="filePath" value="${filePath}" />
	</div>
	
	<form id="form1" runat="server">
		<div id="webofficeDiv" style="position:relative top：60px">
			<!--
       <object id="WebOffice1" height="768" width='100%' style='left: 0px; top: 0px' classid='clsid:E77E049B-23FC-4DB8-B756-60529A35FAD5'
            codebase='weboffice/WebOffice.ocx'>
            <param name='_ExtentX' value='6350'>
            <param name='_ExtentY' value='6350'>            
        </object>
		-->
		</div>
	</form>
	<script language=javascript event=NotifyCtrlReady for=WebOffice1>
		/****************************************************
		 *
		 *	在装载完Weboffice(执行<object>...</object>)
		 *	控件后执行 "WebOffice1_NotifyCtrlReady"方法
		 *
		 ****************************************************/
		//WebOffice_Event_Flash("NotifyCtrlReady");
		WebOffice1_NotifyCtrlReady()
	</script>
	<script language=javascript event=NotifyWordEvent(eventname)
		for=WebOffice1>
	<!--
		//WebOffice_Event_Flash("NotifyWordEvent");
		WebOffice1_NotifyWordEvent(eventname);
	//-->
	</script>
	<SCRIPT language=javascript event=NotifyToolBarClick(iIndex)
		for=WebOffice1>
		//WebOffice_Event_Flash("NotifyToolBarClick");
		WebOffice1_NotifyToolBarClick(iIndex);
	</SCRIPT>
	<script language=javascript>
		/****************************************************
		 *
		 *		控件初始化WebOffice方法
		 *
		 ****************************************************/
		function WebOffice1_NotifyCtrlReady() {
			//document.all.WebOffice1.SetWindowText("授权XX(可通过接口自定义)", 0);
			//document.all.WebOffice1.OptionFlag |= 128;
			//spnWebOfficeInfo.innerText="----   您电脑上安装的WebOffice版本为:V" + document.all.WebOffice1.GetOcxVersion() +"\t\t\t本实例是根据版本V6044编写";
		}
		function WebOffice1_NotifyWordEvent(eventname) {
		}
	</script>


	</div>
</div>	
	<script type="text/javascript"
		src="<%=basePath%>office/loadweboffice.js"></script>
</body>
</html>
