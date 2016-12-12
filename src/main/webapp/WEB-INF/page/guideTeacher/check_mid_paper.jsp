<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width,initial-scale=1">

		<!--CDN_CSS ↓↓↓↓-->
		<!--<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://apps.bdimg.com/libs/fontawesome/4.4.0/css/font-awesome.min.css">-->
		<!--CDN_CSS ↑↑↑↑-->
		<title>审核中期检查</title>

	</head>

	<body style="margin-top:50px;">
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<input type="hidden" value="${errorMsg}" id="error">
		<input type="hidden" value="${successMsg}" id="success">
		<!--下面是代码任务部分-->
			<!--row_1 ↓↓↓↓-->
			<div class="row" style="margin-top: 50px;" id="row1">
			<div class="col-xs-12 col-md-12">
					<form class="form-inline" method="post" action="<%=basePath %>guideTeacher/midCheck.shtm">
						<div class="panel panel-primary">
							<div class="panel-body">
								<label class="control-label">学号：</label><input name="stuNum" value="${stuNum}" type="text" class="form-control" id="stu_id" placeholder="学生学号" />
								<label class="control-label">姓名：</label><input name="stuName" value="${stuName}" type="text" class="form-control" id="stu_name" placeholder="学生姓名" />
								<label class="control-label">状态:</label>
								<select class="form-control" name="status">
								<option value="3" <c:if test="${status==3}">selected="selected"</c:if> >全部</option>
								<option value="0" <c:if test="${status==0}">selected="selected"</c:if> > 未审核</option>
									<option value="1" <c:if test="${status==1}">selected="selected"</c:if> >已通过</option>
									<option value="2" <c:if test="${status==2}">selected="selected"</c:if> >已驳回</option>
								</select>
								<button class="btn btn-primary" type="submit" id="search"><span class="fa fa-search">搜索</span></button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<!--row_1 ↑↑↑↑-->
			<!--row_2 ↓↓↓↓-->
			<div class="row" id="row2">
			<div class="col-xs-12 col-md-12">
					<form action="<%=basePath %>guideTeacher/midCheck_checkMidCheck.shtm" method="post">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;审阅中期检查
							</div>
							<div class=" table-responsive">
								<table style="text-align:center" class="table table-bordered table-hover">
									<thead>
										<tr class="tr">
											<th>选择</th>
											<th>姓名</th>
											<th>性别</th>
											<th>学号</th>
											<th>课题名</th>
											<th>专业</th>
											<th>审核状态</th>
											<th>中期检查</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach var="item" items="${result}">
										<tr>
											<td><input type="checkbox" value="${item.id}" name="ids"></td>
											<td>${item.student.stuName }</td>
											<td>${item.student.sex}</td>
											<td>${item.student.userNum }</td>
									 		<td>${item.student.title }</td>
											<td>${item.student.major }</td>											
											<td>
												<c:if test="${item.teaStatus==0}">
													未审核
												</c:if>
												<c:if test="${item.teaStatus==1}">
													已通过
												</c:if>
												<c:if test="${item.teaStatus==2}">
													已驳回
												</c:if>
											</td>						
											<td>
												<a class="btn btn-primary" href="<%=basePath %>downloadFile.chtm?filePath=${item.midCheckPath}">下载</a>&nbsp;&nbsp;
												<a class="btn btn-primary" href="<%=basePath %>midCheckYuLan.shtm?filePathSrc=${item.midCheckSrc}&filePath=${item.midCheckPath}"  target="_blank" >预览</a>
											</td>
										</tr>
										</c:forEach>							
									</tbody>
								</table>
							</div>
							<div class="panel-footer">
								<input type="button" class="btn btn-default" id="check_all" value="全选"></input>&nbsp;&nbsp;
								<input type="hidden" value="${status}" name="findStatus">
								<input type="hidden" value="${stuNum}" name="stuNum">
								<input type="hidden" value="${stuName}" name="stuName">
								<button type="submit" name="status" value="1" class="btn btn-primary alert-success">通过</button>&nbsp;&nbsp;
								<button type="submit" name="status" value="2" class="btn btn-primary alert-danger">退回</button>&nbsp;&nbsp;
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
			
			<!--row_2 ↑↑↑↑-->
		<script>
			var a = 1;
			$('#check_all').click(function() {
				if(a == 1) {
					$('[name=ids]:checkbox').prop('checked', true);
					a = a - 1;
				} else {
					$('[name=ids]:checkbox').attr('checked', false);
					a = a + 1;
				}

			})
		</script>
	</body>

</html>