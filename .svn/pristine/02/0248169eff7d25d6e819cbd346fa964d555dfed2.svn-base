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
		<div class="row" style="margin-top:50px ;">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<form role="form" action="<%=basePath %>majorLeader/findDbGroup_setStuForDbGroup.shtm" method="post" onsubmit="return $.removeCheck()">
					<div class="panel panel-info">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;已选学生列表
						</div>
						<div class="table-responsive">
							<table class="table table-bordered table-hover" style="text-align:center">
								<thead>
									<tr class="tr">
										<th style="text-align: center;">选择</th>
										<th>学号</th>
										<th>姓名</th>
										<th>性别</th>
										<th>班级</th>
										<th>课题名</th>
										<th>指导老师</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="student" items="${groupList}">
									<tr>
										<td style="text-align: center;"><input type="checkbox" name="removeStuIds" value="${student.userId}"></td>
										<td>${student.userNum}</td>
										<td>${student.stuName}</td>
										<td>${student.sex}</td>
										<td>${student.stuClass}</td>
										<td>${student.title}</td>
										<td>${student.zdTeaName}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="panel-footer">
							<input type="button" class="btn btn-default" id="check_all_cancel" value="全选"></input>&nbsp;&nbsp;
							<input type="hidden" value="${dbGroupId}" name="dbGroupId">
							<input type="hidden" value="remove" name="flag">
							<button class="btn btn-primary" type="submit">移除</button>&nbsp;&nbsp;
						</div>
				</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<div class="panel panel-primary">
					<div class="panel-body">
						<form class="form-inline">
							<label class="control-label">已分配答辩学生数：</label>${groupList.size()}
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<form role="form" action="<%=basePath %>majorLeader/findDbGroup_setStuForDbGroup.shtm" method="post" onsubmit="return $.check()">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;未选学生列表
						</div>
						<div class="table-responsive">
							<table class="table table-bordered table-hover" style="text-align:center">
								<thead>
									<tr class="tr">
										<th style="text-align: center;">选择</th>
										<th>学号</th>
										<th>姓名</th>
										<th>性别</th>
										<th>班级</th>
										<th>课题名</th>
										<th>指导老师</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="student" items="${page.results}">
									<tr>
										<td style="text-align: center;"><input type="checkbox" name="stuIds" value="${student.userId}"></td>
										<td>${student.userNum}</td>
										<td>${student.stuName}</td>
										<td>${student.sex}</td>
										<td>${student.stuClass}</td>
										<td>${student.title}</td>
										<td>${student.zdTeaName}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="panel-footer">
							<input type="button" class="btn btn-default" id="check_all" value="全选"></input>&nbsp;&nbsp;
							<input type="hidden" value="add" name="flag">
							<input type="hidden" value="${dbGroupId}" name="dbGroupId">
							<button class="btn btn-primary" type="submit">确认</button>&nbsp;&nbsp;
							<button class="btn btn-default" type="reset">重置</button>&nbsp;&nbsp;
							<a class="btn btn-default" href="<%=basePath%>majorLeader/findDbGroup.shtm">返回</a>
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
	<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
		<script>
			var a = 1;
			$('#check_all').click(function() {
				if(a == 1) {
					$('[name=stuIds]:checkbox').prop('checked', true);
					a--;
				} else {
					$('[name=stuIds]:checkbox').attr('checked', false);
					a++;
				}
			})
			var b = 1;
			$('#check_all_cancel').click(function() {
				if(b == 1) {
					$('[name=removeStuIds]:checkbox').prop('checked', true);
					b--;
				} else {
					$('[name=removeStuIds]:checkbox').attr('checked', false);
					b++;
				}
			})
			jQuery.extend({ 
				removeCheck:function(){	
					var removeVal=$('[name="removeStuIds"]:checked').val();
				
					if(removeVal==undefined){
					$(".modal-title").html("错误提示");
					$(".modal-body").html("未选择学生");
					$("#mymodal-error").modal("toggle");
					return false;
				}
				return true;
				}
			});
			jQuery.extend({ 
				check:function(){          
					var val=$('[name="stuIds"]:checked').val();
					if(val==undefined){
					$(".modal-title").html("错误提示");
					$(".modal-body").html("未选择学生");
					$("#mymodal-error").modal("toggle");
					return false;
				}
				return true;
				}
			});
		</script>
	</body>

</html>