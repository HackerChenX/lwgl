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

		<!--modal-->

		<!--modal结束-->

		<div class="container" style="margin-top: 50px;">
			<div class="row" style="margin-top: 50px;" id="row1">
				<div class="col-xs-12 col-md-11 col-md-offset-1">
					<form class="form-inline">
						<div class="panel panel-primary">
							<div class="panel-body">
								<label class="control-label">导师：</label><input type="text" class="form-control" name="teaNUm" id="stu_id" placeholder="导师姓名" />
								<label class="control-label">学号：</label><input type="text" class="form-control" name="teaNUm" id="stu_id" placeholder="学生学号" />
								<label class="control-label">姓名：</label><input type="text" class="form-control" name="teaName" id="stu_name" placeholder="学生姓名" />
								<label class="control-label">状态:</label>
								<select class="form-control">
									<option>未审核</option>
									<option>已通过</option>
									<option>未通过</option>
								</select>
								<button class="btn btn-primary" type="submit" id="search"><span class="fa fa-search">搜索</span></button>
							</div>

						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-md-11 col-md-offset-1">
					<form method="post" action="##">
						<div class="panel panel-primary">
							<div class="panel-heading"><span class="fa fa-check-square-o"></span>&nbsp;&nbsp;学生延期申请</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover text-center">
									<thead>
										<tr>
											<td>选择</td>
											<td>学号</td>
											<td>姓名</td>
											<td>导师</td>
											<td>延期类型</td>
											<td>延期时间至</td>
											<td class="text-center">操作</td>
											<td>状态</td>
										</tr>
									</thead>
									<tbody>
										<c:if test="${item!=null}">
											<c:forEach var="item" items="${item}">
												<tr>
													<td><input type="checkbox" name="checkForOpt"></td>
													<td>${学号}</td>
													<td>${姓名}</td>
													<td>${导师}</td>
													<td>${延期类型}</td>
													<td>${延期时间至}</td>
													<td>
														<button class="btn btn-default" name="delayReason" type="button" data-toggle="modal" yanqi="${延期原因}">查看</button>
														<a class="btn btn-default" href="##">通过</a>
														<a class="btn btn-primary" data-toggle="modal" href="#reqback">驳回</a>
													</td>
													<td>未审核</td>
												</tr>
											</c:forEach>
										</c:if>

									</tbody>
								</table>
							</div>
							<div class="panel-footer">
								<button class="btn btn-primary" type="button" id="check_all">全选</button>
								<a class="btn btn-primary">通过</a>
								<a class="btn btn-primary" data-toggle="modal" href="#reqback">驳回</a>

							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	
		<!-- 全选js-->
		<script type="text/javascript">
			var a = 1;
			$('#check_all').click(function() {
				if(a == 1) {
					$('[name="checkForOpt"]:checkbox').prop('checked', true);
					a--;
				} else {
					$('[name="checkForOpt"]:checkbox').attr('checked', false);
					a++;
				}
			})
		</script>
		<!--全选over-->

	</body>

</html>