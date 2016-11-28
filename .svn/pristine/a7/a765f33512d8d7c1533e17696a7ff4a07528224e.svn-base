<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
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
		<title></title>
	</head>
	<body>
		
		<div class="container col-md-7 col-md-offset-3">
		<div class="row" style="margin-top: 50px;margin-left: 0px;margin-right: 0px;">
			<div class="panel panel-primary">
				<div class="panel-heading"><span class="fa fa-check-square-o"></span>&nbsp;&nbsp;当前届设置</div>
				<form class="form-inline" method="post" action="<%=basePath %>secretary/findNowTerm_setNowTerm.shtm">
					<div class="panel-body" style="text-align: center;">
						<p style="color: blue">注：设置当前届，用于导出各种表格或统计表的调出显示</p>
						<label>当前届：</label>
						<input class="form-control" type="text" name="term" id="termNum"/>
						<span>（例如：2016）</span>
						<button class="btn btn-primary" type="submit" id="termChoice">设置</button>&nbsp;&nbsp;&nbsp;&nbsp;
						<div style="margin-top: 30px;">
							<b>已设置当前届为：</b>
								<c:if test="${term!=''}">
									${term}
								</c:if>
								<c:if test="${term==''}">
									<label id="termSelected">未设置</laber>
								</c:if>
								
						</div>
						<input type="hidden" value="${errorMsg}" id="error">
						<input type="hidden" value="${successMsg}" id="success">
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
//	$('#termChoice').click(function(){
//		
//		document.getElementById('termSelected').innerHTML = $('#termNum').text() ;
//	})
//		
	</script>
	<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
	<div class="modal fade" id="mymodal-error" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
		 <div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
				<p></p>
				</div>
				<div class="modal-footer">			
					<button type="button" class="btn btn-primary"
						data-dismiss="modal">确认</button>
				</div>
			</div>
		</div>
	</div>	
	</body>
</html>
