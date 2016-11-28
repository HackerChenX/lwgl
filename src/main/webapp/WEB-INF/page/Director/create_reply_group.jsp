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
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width,initial-scale=1">

		<title>创建答辩小组</title>
	</head>

	<body>
		<style type="text/css">
		th{
			text-align:center;
		}
		</style>
	 <link href="<%=basePath %>css/daterangepicker.css" rel="stylesheet" />
		
	 <form role="form" class="form-inline" action="<%=basePath%>majorLeader/findDbGroup_setDbGroup.shtm" method="post" onsubmit="return $.check()">
			<div class="row" style="margin-top: 50px;">
				<div class="col-sm-12 col-md-8 col-md-offset-2">
						<div class="panel panel-primary">
							<div class="panel-body">
								<div class="form-group">
									<label>小组编号：</label>
									<input type="text" id="groupNum" name="groupNum" value="${dbGroup.groupNum}" class="form-control" />
								</div>
								<div class="form-group">
									<label>答辩组名：</label>
									<input type="text" id="groupName" name="groupName" value="${dbGroup.groupName}" class="form-control" />
								</div>
								<div class="form-group">
									<label>答辩地点：</label>
									<input type="text" id="groupSite" name="groupSite" value="${dbGroup.groupSite}" class="form-control" />
								</div>
								<div class="form-group">
									<label>答辩时间：</label>
									<input type="text" id="datetimepicker" class="form-control" name="dbTime" />
								</div>
								<c:if test="${dbGroup.dateTimeStr!=null&&dbGroup.dateTimeStr!=''}">
								<div class="form-group">
									<label>已设置的答辩时间：</label>
									${dbGroup.dateTimeStr}
								</div>
								</c:if>
							</div>
						</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 col-md-8 col-md-offset-2">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<span class="fa fa-check-circle-o"></span>&nbsp;&nbsp;答辩组组长
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-striped table-hover" style="text-align: center;">
									<thead>
										<tr>
											<th style="text-align: center;">选择</th>
											<th>姓名</th>
											<th>职称</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="teacher" items="${leaderTea}">
										<tr>
											<td>
												<input type="radio" name="groupLeader" <c:if test="${teacher.flag==1}">checked="checked"</c:if> value="${teacher.userId}"/>
											</td>
												
											<td>${teacher.teaName}</td>
											<td>${teacher.zhicheng}</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="panel-footer">
									<input type="hidden" value="${dbGroup.id}" name="id">
									<input type="submit" class="btn btn-primary" value="保存" />&nbsp;&nbsp;
									<button type="reset" class="btn btn-default">重置</button>&nbsp;&nbsp;
									<a class="btn btn-default" href="<%=basePath%>majorLeader/findDbGroup.shtm">返回</a>
									<input type="hidden" value="${errorMsg}" id="error">
									<input type="hidden" value="${successMsg}" id="success">
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
		<script type="text/javascript" src="<%=basePath%>js/moment-with-locales.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/daterangepicker.js"></script>
		<script type="text/javascript">
			$(function(){
				$('#datetimepicker').daterangepicker({
					timePicker: true,
					timePickerIncrement: 1,
					singleDatePicker:true,
					locale: {
						format: 'YYYY/MM/DD HH:MM'
					},
				}, function(start, end, label) {
					$("#teacherStartTime").html(start.format('YYYY/MM/DD HH:MM'));
					$("#teacherEndTime").html(end.format('YYYY/MM/DD HH:MM'));
				});
				//对中文的支持
				$("#datetimepicker").data('daterangepicker').updateMomentLocale('zh-cn');
			});
			jQuery.extend({ 
				check:function(){
				var groupNum = $("#groupNum").val();
				var groupName = $("#groupName").val();
				var groupSite = $("#groupSite").val();
				var val=$('input:radio[name="groupLeader"]:checked').val();			
			    if(val==null){
					$(".modal-title").html("错误提示");
					$(".modal-body").html("请选择答辩组长!");
					$("#mymodal-error").modal("toggle");
					return false;
				}
				if(groupNum==""||groupName==""||groupSite=="")
				{
			       $(".modal-title").html("错误提示");
				   $(".modal-body").html("当前设置不能有空项!");
				   $("#mymodal-error").modal("toggle");
				   return false;
	        	}
	        	return true;
	        	}
			});
		</script>
	</body>

</html>