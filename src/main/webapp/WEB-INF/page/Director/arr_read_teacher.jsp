<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
	</head>

	<body>
	<style type="text/css">
	th{
		text-align:center;
	}
	</style>
		<script type="text/javascript">
			window.onload=function(){
				/*刷新页面下拉框值不变*/	
		   		if(document.getElementById("selHid").value==""){
		   			document.getElementById("sel").value=0;
		   		}else{
		   			document.getElementById("sel").value=document.getElementById("selHid").value;
		   		}		   			
	   			/*弹窗提示异常消息*/
		   		var error = $("#error").val();
		   		if(!(error==''|| error==undefined || error==null)){
				$(".modal-title").html("错误提示");
				$(".modal-body").html(error);
				$("#mymodal-error").modal("toggle");
				}
		   		
		   		/*弹窗提示成功消息*/
		   		var success = $("#success").val();
		   		if(!(success==''|| success==undefined || success==null)){
				$(".modal-title").html("提示");
				$(".modal-body").html(success);
				$("#mymodal-error").modal("toggle"); 
				}
		   	}
		</script>
		<div class="row" style="margin-top: 50px;">
			<div class="col-xs-12 col-md-8 col-md-offset-2">

				<div class="panel panel-primary">

					<div class="panel-body">
						<form class="form-inline" action="<%=basePath %>majorLeader/findPyTea.shtm" method="post">
							<label class="control-label">工号：</label><input type="text" name="teaNum" value="${teaNum }" class="form-control" id="teacher_id" placeholder="教师工号" />
							<label class="control-label">姓名：</label><input type="text" name="teaName" value="${teaName }" class="form-control" id="stu_name" placeholder="教师姓名" />
							<label class="control-label">职称:</label>
							<input type="hidden" value="${zhicheng}" id="selHid">
							<select class="form-control" name="zhicheng" id="sel">
								<option value="0">请选择</option>
								<c:forEach var="ZhiCheng" items="${zhiChengList}">
								<option value="${ZhiCheng.zhiCheng}">${ZhiCheng.zhiCheng}</option>
								</c:forEach>							
							</select>
							<button class="btn btn-primary" type="submit" id="search"><span class="fa fa-search">搜索</span></button>
						</form>
					</div>
					
				</div>

			</div>
		</div>
		<div class="row" >
			<div class="col-xs-12 col-md-8 col-md-offset-2">
				<form role="form" action="##" method="post">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-check-square-o"></span>&nbsp;&nbsp;分配评阅教师</div>
						<div class="table-responsive">
							<table style="text-align:center" class="table table-bordered table-striped table-hover">
								<thead>
									<tr>
										<th>教师姓名</th>
										<th>职称</th>
										<th>已分配的评阅课题数</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="teacher" items="${list}">
									<tr>
										<td>${teacher.teaName }</td>
										<td>${teacher.zhicheng }</td>
										<td>
											<a href="<%=basePath %>majorLeader/findPyTea_jumpPyStu.shtm?teaId=${teacher.userId}">${teacher.pyNowStunum }</a>
										</td>
										<td>
											<a href="<%=basePath %>majorLeader/findPyTea_jumpSelectPyStu.shtm?teaId=${teacher.userId}">进入选择</a>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>

					</div>
				</form>
			</div>
		</div>
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