<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">-->
		<title></title>
		
	</head>

	<body>
		<script type="text/javascript" src="<%=basePath %>js/major_class.js"></script>
		<script type="text/javascript">
			window.onload=function(){
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
					<form role="form" class="form-inline" method="post" action="<%=basePath %>guideTeacher/findStuPaper.shtm" id="select">
						<div class="form-group">
							<label>学生学号</label>
							<input type="text" name="stuNum" value="${stuNum}" class="form-control"/>
						</div>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<div class="form-group">
							<label>学生姓名</label>
							<input type="text" name="stuName" value="${stuName}" class="form-control"/>
						</div>
						<input name="pageNo"  type="hidden"  value="${page.pageNo}"   id="pageNow" />
						<input name="pageSize"type="hidden"  value="${page.pageSize}" id="pageSize">
						&nbsp;&nbsp;
						<button class="btn btn-primary" type="submit">查询</button>
					</form>
				</div>
			</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-10 col-md-offset-1">
				<form role="form" action="<%=basePath %>guideTeacher/findStuPaper_exportStuPaper.shtm" method="post">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;学生信息
						</div>
						<div class="table-responsive">
							<table class="table table-bordered table-striped table-hover" style="text-align: center;">
								<thead>
									<tr>									
										<th>学号</th>
										<th>姓名</th>
										<th>班级</th>
										<th>课题名</th>
										<th>审阅成绩</th>
										<th>评阅成绩</th>
										<th>答辩成绩</th>
										<th>最终成绩</th>				
									</tr>
								</thead>
								<tbody>
									<c:forEach var="paper" items="${page.results}">
									<tr>				
										<td>${paper.stuNum}</td>
										<td>${paper.stuName}</td>
										<td>${paper.stuClass}</td>
										<td><a href="<%=basePath%>guideTeacher/findStuPaperInfo.shtm?id=${paper.id}">${paper.title}</a></td>
										<td>${paper.syGrade}</td>
										<td>${paper.pyGrade}</td>
										<td>${paper.dbGrade}</td>				
										<td>${paper.finalGrade}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="panel-footer">
					<div class="row" style="padding-left:15px">
						<button type="submit" class="btn btn-primary">导出</button>
						
					<div class="col-xs-8 col-md-8 "	style="float: right; text-align:right;">
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