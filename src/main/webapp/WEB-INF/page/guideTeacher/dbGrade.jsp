<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>录入答辩成绩</title>
	</head>

	<body style="font-family: '微软雅黑'; ">
		<base href="<%=basePath %>"/>
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/page.js"></script>

			<div class="row" style="margin-top: 50px;" id="row1">
		<div class="col-xs-12 col-md-11 col-md-offset-1">
					<form class="form-inline" method="post" action="<%=basePath %>guideTeacher/dbGrade.shtm" id="select">
						<div class="panel panel-primary">
							<div class="panel-body">
								<label class="control-label">学号：</label>
								<input type="text" class="form-control" id="stu_id" value="${stuNum}" name="stuNum" placeholder="学生学号" />
								<label class="control-label">姓名：</label>
								<input type="text" class="form-control" id="stu_name" name="stuName"  value="${stuName}" placeholder="学生姓名" />
								<input type="hidden" value="${page.pageNo}" name="pageNo">
								<button class="btn btn-primary" type="submit" id="search"><span class="fa fa-search">搜索</span></button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="row">
		<div class="col-xs-12 col-md-11 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-check-square-o"></span>&nbsp;&nbsp;录入答辩成绩</div>
						<div class="table-responsive">
							<table style="text-align:center" class="table table-bordered table-hover text-center">
								<thead>
									<tr>
										<th>学号</th>
										<th>姓名</th>
										<th>课题</th>
										<th>答辩成绩</th>
										<th class="text-center">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="grade" items="${page.results}">
									<tr>
										<td>${grade.stuNum}</td>
										<td>${grade.stuName}</td>
										<td>${grade.title}</td>
										<td><input class="input-group form-control" name="dbGrade" value="${grade.dbGrade}"/></td>
										<td>
											<button name="luru" stuNum="${grade.stuNum}" class="btn btn-primary">录入</button>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							<input type="hidden" value="${errorMsg}" id="error">
							<input type="hidden" value="${successMsg}" id="success">
						</div>
						<div class="panel-footer">
						<script type="text/javascript" src="<%=basePath %>js/page.js"></script>
						<div class="row" style="margin-right: 5%;">
							<div class="col-xs-8 col-md-8 "style="float: right; text-align:right;">
									<script type="text/javascript" src="<%=basePath%>js/page.js"></script>
									<c:if test="${page.isAjax==0}">
										<c:if test="${page.pageNo-1>0 }">
											<a href="javascript:upPage()" class="btn btn-default btn-sm">&laquo;上一页</a>
										</c:if>

										<a class="btn btn-default btn-sm">第${page.pageNo }页</a>

										<c:if test="${page.pageNo+1<=page.totalPage }">
											<a href="javascript:nextPage()"
												class="btn btn-default btn-sm">下一页&raquo;</a>
										</c:if>
										&nbsp;&nbsp;共${page.totalRecord }条&nbsp;共${page.totalPage}页 &nbsp;&nbsp;跳转到&nbsp;
										<input type="text" value="" id="zc_tz_text"
											style="width:28px;height:28px" />&nbsp;页<input type="button"
											value="跳转" id="zc_tz_button" class="btn btn-default btn-sm"
											onclick="zcTzClick(${page.totalPage})" /> &nbsp;&nbsp;每页&nbsp;<input
											type="text" value="" id="page_size_text"
											style="width:28px;height:28px" />&nbsp;条<input type="button"
											value="设置" id="zc_size_button" class="btn btn-default btn-sm"
											onclick="pageSizeClick()" />
									</c:if>
								</div>
						</div>
					</div>
					</div>
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

		
		<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
		<script type="text/javascript">
			$('button[name="luru"]').click(function() {
				var stuNum = $(this).attr("stuNum");
				var dbGrade = $(this).parent("td").prev("td").children("input[name='dbGrade']").val();
				window.location.href="guideTeacher/dbGrade_setDbGrade.shtm?stuNum="+stuNum+"&dbGrade="+dbGrade;
			});
		</script>

	</body>

</html>
