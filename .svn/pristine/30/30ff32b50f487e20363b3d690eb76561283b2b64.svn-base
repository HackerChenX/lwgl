<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html><head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<title></title>
	</head>

	<body>
		<script type="text/javascript" src="<%=basePath %>js/major_class.js"></script>
		<script type="text/javascript">
			window.onload=function(){
				/*刷新页面下拉框值不变*/	
		   		if(document.getElementById("majorHid").value==""){
		   			document.getElementById("major").value=0;
		   		}else{
		   			document.getElementById("major").value=document.getElementById("majorHid").value;
		   		}
		   		/*刷新页面下拉框值不变2*/	
		   		if(document.getElementById("classHid").value==""){
		   			document.getElementById("class").value=0;
		   		}else{
		   			document.getElementById("class").value=document.getElementById("classHid").value;
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
			<div class="col-xs-12 col-md-10 col-md-offset-1">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form role="form" class="form-inline" method="post" action="<%=basePath %>secretary/${url}">
						<div class="form-group">
							<label class="control-label">专业：</label>
							<input type="hidden" value="${major}" id="majorHid">
							<select class="form-control" type="text" name="major" id="major" onchange="changeMajor()">
								<option value="0">全部专业</option>
								<c:forEach var="major" items="${majorList}">
								<option value="${major.majorName}">${major.majorName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label>班级：</label>
							<input type="hidden" value="${className }" id="classHid">
							<select class="form-control" type="text" name="className" id="class">
								<option value="0">全部班级</option>
								<c:forEach var="className" items="${classList}">
								<option value="${className.cName}">${className.cName}</option>
								</c:forEach>
							</select>
						</div>
						<input type="hidden" value="${stageName}" name="stageName">
						
						<button class="btn btn-primary" type="submit">查询</button>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-10 col-md-offset-1">
				<form role="form" action="##" method="post">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<c:if test="${stageName=='task_book'}">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;任务书提交信息
							</c:if>
							<c:if test="${stageName=='opening_report'}">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;开题报告提交信息
							</c:if>
							<c:if test="${stageName=='mid_check'}">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;中期检查表提交信息
							</c:if>
							<c:if test="${stageName=='first_paper'}">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;初稿提交信息
							</c:if>
							<c:if test="${stageName=='final_paper'}">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;定稿提交信息
							</c:if>
						</div>
						<div class="table-responsive">
									<table class="table table-bordered table-striped table-hover" style="text-align: center;">
								<thead>
									<tr>
										<th>专业名称</th>
										<th>班级名称</th>
										<th>学生总数</th>
										<th>未提交</th>
										<th>专业审核中</th>
										<th>专业退回中</th>
										<th>审核通过</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="submitStatistics" items="${list}">
									<tr>
										<td>${submitStatistics.major }</td>
										<td>${submitStatistics.className }</td>
										<td>
											${submitStatistics.stuAllNum }
										</td>
										<td>
											<a href="<%=basePath %>secretary/findStuSubmit_findStuBySubStatus.shtm?major=${submitStatistics.major}&className=${submitStatistics.className}&stageName=${stageName}&leaderStatus=3">${submitStatistics.notSubmitNum }</a>
										</td>
										<td>
											<a href="<%=basePath %>secretary/findStuSubmit_findStuBySubStatus.shtm?major=${submitStatistics.major}&className=${submitStatistics.className}&stageName=${stageName}&leaderStatus=0" id="checking">${submitStatistics.checkingNum }</a>
										</td>
										<td>
											<a href="<%=basePath %>secretary/findStuSubmit_findStuBySubStatus.shtm?major=${submitStatistics.major}&className=${submitStatistics.className}&stageName=${stageName}&leaderStatus=2" id="checkNot">${submitStatistics.checkNotNum }</a>
										</td>
										<td>
											<a href="<%=basePath %>secretary/findStuSubmit_findStuBySubStatus.shtm?major=${submitStatistics.major}&className=${submitStatistics.className}&stageName=${stageName}&leaderStatus=1" id="checkPass">${submitStatistics.checkPassNum }</a>
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
	
</body></html>