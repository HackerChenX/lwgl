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
		<title></title>
	</head>

	<body>
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<div class="container col-md-8 col-md-offset-2">
			<div class="row" style="margin-top: 50px;margin-left: 0px;margin-right: 0px;">
				<form class="form-inline" method="post" action="<%=basePath %>secretary/findMajor_deleteMajor.shtm">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-check-square-o"></span>&nbsp;&nbsp;专业管理</div>

						<div class="table-responsive">
							<table class="table table-bordered table-striped table-hover" style="text-align: center;">
								<thead>
									<tr>
										<th>选择</th>
										<th>专业名称</th>
										<th>操作</th>
										<th>管理专业下的班级</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="major" items="${list}">
									<tr>
										<td><input type="checkbox" name="ids" value="${major.id}"></td>
										<td>${major.majorName}</td>
										<td>
											<a href="<%=basePath %>secretary/findMajor_deleteMajor.shtm?ids=${major.id}">删除</a>
										</td>
										<td>
											<a href="<%=basePath %>secretary/findMajor_findClass.shtm?majorId=${major.id}">管理专业下的班级</a>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

						<div class="panel-footer">
							<input type="button" class="btn btn-default" id="check_all" value="全选"></input>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="btn btn-primary" href="<%=basePath %>secretary/findMajor_jumpAddMajor.shtm">添加</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="submit" class="btn btn-default">批量删除</button>
							<input type="hidden" value="${errorMsg}" id="error">
							<input type="hidden" value="${successMsg}" id="success">
						</div>
					</div>
				</form>
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
						$('[name=ids]:checkbox').prop('checked', true);
						a--;
					} else {
						$('[name=ids]:checkbox').attr('checked', false);
						a++;
					}
				})
			</script>
	</body>

</html>