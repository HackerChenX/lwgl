<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<title></title>
	</head>

	<body style="font-family: '微软雅黑'; font-size: 17px;">
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
		<input type="hidden" value="${errorMsg }" id="error">
		<input type="hidden" value="${successMsg }" id="success">
			<div class="row " style="margin-top: 50px;">
				<div class="col-sm-12 col-md-11 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-body">
							<form role="form" class="form-inline" method="post" action="<%=basePath %>majorLeader/findOpeningReportInfo.shtm" id="select">
								<div class="form-group">
									<label>班级：</label>
									<input type="hidden" value="${className }" id="selHid">
									<select class="form-control input-sm" type="text" name="className" id="sel">
										<option value="0">全部班级</option>
										<c:forEach var="className" items="${cnList}">
										<option value="${className.cName }">${className.cName }</option>										
										</c:forEach>
									</select>
								</div>
								<input name="pageNo"  type="hidden"  value="${page.pageNo}"   id="pageNow" />
								<input name="pageSize"type="hidden"  value="${page.pageSize}" id="pageSize">
								
								<button class="btn btn-primary" type="submit">查询</button>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 col-md-11 col-md-offset-1">
					<form role="form" action="##" method="post">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;开题报告提交信息
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-striped table-hover" style="text-align: center;">
									<thead>
										<tr>
											<th>班级</th>
											<th>学生总数</th>
											<th>未提交任务书</th>
											<th>指导审核中</th>
											<th>指导退回中</th>
											<th>指导审核通过</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="submitStatistics" items="${list}">
										<tr>
											<td>${submitStatistics.className }</td>
											<td>
												${submitStatistics.stuAllNum }
											</td>
											<td>
												<a href="<%=basePath %>majorLeader/selectOR_findStuList.shtm?stageName=opening_report&zdTeaStatus=3&className=${submitStatistics.className }">${submitStatistics.notSubmitNum }</a>
											</td>
											<td>
												<a href="<%=basePath %>majorLeader/selectOR_findStuList.shtm?stageName=opening_report&zdTeaStatus=0&className=${submitStatistics.className }">${submitStatistics.checkingNum }</a>
											</td>
											<td>
												<a href="<%=basePath %>majorLeader/selectOR_findStuList.shtm?stageName=opening_report&zdTeaStatus=2&className=${submitStatistics.className }">${submitStatistics.checkNotNum }</a>
											</td>
											<td>
												<a href="<%=basePath %>majorLeader/selectOR_findStuList.shtm?stageName=opening_report&zdTeaStatus=1&className=${submitStatistics.className }">${submitStatistics.checkPassNum }</a>
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