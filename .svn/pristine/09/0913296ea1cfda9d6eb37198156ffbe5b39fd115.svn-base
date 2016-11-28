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
		<div class="container col-md-6 col-md-offset-3">
			<div class="row" style="margin-top: 50px;margin-left: 0px;margin-right: 0px;">
				<form class="form-inline" method="post" action="<%=basePath %>secretary/findTitleForm_deleteTitleForm.shtm">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-check-square-o"></span>&nbsp;&nbsp;完成形式</div>
						<div class="table-responsive">
							<table class="table table-bordered table-hover" style="text-align: center;">
								<thead>
									<tr>
										<th>选择</th>
										<th>
											完成形式
										</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="titleForm" items="${list}">
									<tr>
										<td><input type="checkbox" name="ids" value="${titleForm.id}"></td>
										<td>
											${titleForm.formName}
										</td>
										<td>
											<a href="<%=basePath %>secretary/findTitleForm_deleteTitleForm.shtm?ids=${titleForm.id}">删除</a>
										</td>
									</tr>
									</c:forEach>	
								</tbody>
							</table>
						</div>
						<div class="panel-footer">
							<input type="button" class="btn btn-default" id="check_all" value="全选"></input>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="btn btn-primary" href="<%=basePath %>secretary/findTitleForm_setTitleForm.shtm">添加</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn btn-primary" type="submit">批量删除</button>&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
						<input type="hidden" value="${errorMsg}" id="error">
						<input type="hidden" value="${successMsg}" id="success">
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