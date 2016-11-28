<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

<head>
<meta charset="UTF-8">
<title>已申报的课题</title>
</head>

<body>
<style type="text/css">
	th{
		text-align:center;
	}
</style>
	<div class="row" style="margin-top: 50px;">
<div class="col-xs-12 col-md-11 col-md-offset-1">
			<form method="post" action="##">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<span class="fa fa-edit"></span>&nbsp;&nbsp;已申报的课题
					</div>
					
					<div class="table-responsive">
						<table class="table table-bordered table-striped table-hover">
							<thead style="text-align:center;">
								<tr>
									<th>课题名称</th>
									<th>课题性质</th>
									<th>完成形式</th>					
									<th>审核状态</th>
									<th>选报状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody style="text-align:center;">
								<c:if test="${teacherTitles!=null}">
									<c:forEach var="TeacherTitle" items="${teacherTitles}">
										<tr>
											<td>${TeacherTitle.title}</td>
											<td>${TeacherTitle.nature}</td>
											<td>${TeacherTitle.titleForm}</td>											
											<td>${TeacherTitle.sumStatus}</td>
											<td><c:if test="${TeacherTitle.choose==1}">已被选报</c:if>
											<c:if test="${TeacherTitle.choose==0}">待选</c:if></td>
											<td>
										   <a class="btn btn-default" name="delTeaTitle"  href="<%=basePath %>guideTeacher/findTeaTitle_updataTeaTitle/${TeacherTitle.id}.shtm">修改</a>
											<a class="btn btn-default" name="delTeaTitle"  href="<%=basePath %>guideTeacher/findTeaTitle_deleteTeaTitle/${TeacherTitle.id}.shtm">删除</a>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
					<div class="panel-footer">
						<a class="btn btn-primary"
							href="<%=basePath%>guideTeacher/findTeaTitle_applyProject.shtm">申报课题</a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- modal_start -->
	<div class="modal fade" id="mymodal-unApply" tabindex="-1"
		role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
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
					<a id="cancelSubmit" class="btn btn-primary"
						href="<%=basePath %>guideTeacher/findTeaTitle/deleteTeaTitle/">确认</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal_end-->
	<script type="text/javascript">
        $(function(){
			$("button[name='cancelDelay']").on("click",function(){
				var url =$("#cancelSubmit").attr("href")+$(this).val();
				$(".modal-title").html("撤回提示");
				$(".modal-body").html("仅限撤回审核中的延期申请，确认撤回？");						
				$("#cancelSubmit").attr("href",url)	
				$("#mymodal-unApply").modal("toggle");
			});
		});
		
		
		</script>


</body>
</html>