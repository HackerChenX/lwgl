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
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<input type="hidden" id="error" value="${errorMsg}">
		<input type="hidden" id="success" value="${successMsg}">
		<div class="row" style="margin-top:50px ;">
			<div class="col-xs-12 col-md-8 col-md-offset-2">
				<form role="form" action="<%=basePath %>majorLeader/findDbGroup_setTeaForDbGroup.shtm" method="post">
					<div class="panel panel-info">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;
							<c:if test="${flag=='groupLeader'}">
							已选答辩组长
							</c:if>
							<c:if test="${flag=='memberTea'}">
							已选答辩组员
							</c:if>
							<c:if test="${flag=='secretaryTea'}">
							已选答辩秘书
							</c:if>
						</div>
						<div class="table-responsive">
							<table class="table table-bordered table-hover" style="text-align:center"> 
								<thead>
									<tr>
										<th style="text-align: center;">选择</th>
										<th>姓名</th>
										<th>职称</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${flag=='groupLeader'}">
									<tr>
										<td style="text-align:center;">	
											<input type="radio" name="removeTeaIds" value="${leaderTea.userId}"/>
										</td>	
										<td>${leaderTea.teaName}</td>
										<td>${leaderTea.zhicheng}</td>
									</tr>
									</c:if>
									
									<c:if test="${flag=='memberTea'}">
										<c:forEach var="teacher" items="${nowTeacherList}">
										<tr>
										<td style="text-align:center;">	
											<input type="checkbox" name="removeTeaIds" value="${teacher.userId}"/>
										</td>
											
										<td>${teacher.teaNum}</td>
										<td>${teacher.teaName}</td>
										<td>${teacher.zhicheng}</td>
										</tr>
										</c:forEach>
									</c:if>
									
									<c:if test="${flag=='secretaryTea'}">
									<tr>
										<c:if test="${secretaryTea!=null&&secretaryTea!=''}">
										<td style="text-align:center;">	
											<input type="radio" name="removeTeaIds" value="${secretaryTea.userId}"/>
										</td>
											
										<td>${secretaryTea.teaNum}</td>
										<td>${secretaryTea.teaName}</td>
										<td>${secretaryTea.zhicheng}</td>
										</c:if>
									</tr>
									</c:if>
								</tbody>
							</table>
						</div>						
						<div class="panel-footer">
							<input type="hidden" value="${dbGroupId}" name="dbGroupId">
							<input type="hidden" value="remove" name="action">
							<input type="hidden" value="${flag}" name="flag">
							<c:if test="${flag=='memberTea'}">
							<input type="button" class="btn btn-default" id="check_all_cancel" value="全选"></input>&nbsp;&nbsp;
							</c:if>
							<button class="btn btn-primary" type="submit">移除</button>&nbsp;&nbsp;														
						</div>
				</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-8 col-md-offset-2">
				<form role="form" action="<%=basePath %>majorLeader/findDbGroup_setTeaForDbGroup.shtm" method="post">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;未选教师列表
						</div>
						<div class="table-responsive">
							<table class="table table-bordered table-hover" style="text-align:center">
								<thead>
									<tr>
										<th style="text-align: center;">选择</th>
										<th>姓名</th>
										<th>职称</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="teacher" items="${allTeacherList}">
										<tr>
											<td style="text-align:center;">												
												<input 
														<c:if test="${flag=='groupLeader'}">type="radio"</c:if> 
														<c:if test="${flag=='memberTea'}">type="checkbox"</c:if>
														<c:if test="${flag=='secretaryTea'}">type="radio"</c:if> 
												name="teaIds" value="${teacher.userId}"/>
											</td>
												
											<td>${teacher.teaName}</td>
											<td>${teacher.zhicheng}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="panel-footer">
						<c:if test="${flag=='memberTea'}">
							<input type="button" class="btn btn-default" id="check_all" value="全选"></input>&nbsp;&nbsp;
						</c:if>
							<input type="hidden" value="add" name="action">
							<input type="hidden" value="${dbGroupId}" name="dbGroupId">
							<input type="hidden" value="${flag}" name="flag">
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
		
		<script>
			var a = 1;
			$('#check_all').click(function() {
				if(a == 1) {
					$('[name=teaIds]:checkbox').prop('checked', true);
					a--;
				} else {
					$('[name=teaIds]:checkbox').attr('checked', false);
					a++;
				}
			})
			var b = 1;
			$('#check_all_cancel').click(function() {

				if(b == 1) {
					$('[name=removeTeaIds]:checkbox').prop('checked', true);
					b--;
				} else {
					$('[name=removeTeaIds]:checkbox').attr('checked', false);
					b++;
				}
			})
		</script>
	</body>

</html>