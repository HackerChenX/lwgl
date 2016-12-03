<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>

<body>
	&nbsp;
	<base href="<%=basePath%>" />
	<script type="text/javascript" src="<%=basePath%>js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/page.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/uploadExcel.js"></script>
	<style>
	  .fileUpload {
		position: relative;
		overflow: hidden;
		margin: 10px;
		}	
	.fileUpload input.upload {	
		position: absolute;
		top: 0;
		right: 0;
		margin: 0;
		padding: 0;
		font-size: 20px;
		cursor: pointer;
		opacity: 0;
		filter: alpha(opacity=0);
		}
	</style>
	
	<input type="hidden" value="${errorMsg}" id="error">
	<input type="hidden" value="${successMsg}" id="success">
	<div class="row" style="margin-top: 50px;">
		<div class="col-xs-12 col-md-11 col-md-offset-1">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form role="form" class="form-inline"
						action="<%=basePath%>user/findStudent.shtm" method="post"
						id="select">				
						<div class="form-group">
							<label>学号:</label> <input class="form-control input-sm"
								type="text" name="userNum" id="userNum" value="${userNum}" />
						</div>
						<div class="form-group">
							<label>姓名：</label> <input class="form-control input-sm"
								type="text" name="userName" id="userName" value="${userName }" />
						</div>
						<input name="roleName" type="hidden" value="student" /> 
						<input name="pageSize"type="hidden"  value="${page.pageSize}" id="pageSize">					
						<input name="pageNo"  type="hidden"  value="${page.pageNo}"   id="pageNow" />					
						<input class="btn btn-primary" type="submit" id="keyWordSelect" value="查询">
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 col-md-11 col-md-offset-1">
			<form role="form"
				action="<%=basePath%>user/findStudent_removeStudent.shtm"
				method="post">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;学生账号
					</div>
					<div class="table-responsive">
						<table class="table table-bordered table-striped table-hover"
							style="text-align: center;">
							<thead>
								<tr>
									<th>选择</th>
									<th>账号</th>
									<th>姓名</th>
									<th>性别</th>
									<th>专业</th>
									<th>班级</th>
									<th colspan="2">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${page.results!=null}">
									<c:forEach var="student" items="${page.results}">
										<tr>
											<td style="text-align: center;"><input type="checkbox" name="ids" value="${student.userId}"></td>
											<td>${student.userNum }</td>
											<td>${student.stuName }</td>
											<td>${student.sex }</td>
											<td>${student.major}</td>
											<td>${student.stuClass}</td>
											<td><a
												href="<%=basePath %>user/findStudent_findStudentInfo.shtm?id=${student.userId}">修改</a>
											</td>
											<td><a id="del"
												href="<%=basePath %>user/findStudent_removeStudent.shtm?ids=${student.userId}">删除</a>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
					<div class="panel-footer">						
					<div class="row" style="padding-left:15px">
					    <input type="button" class="btn btn-default" id="check_all" value="全选"></input>&nbsp;&nbsp;	
						<a class="btn btn-primary" href="<%=basePath%>user/findStudent_jumpAddStudent.shtm">添加</a>&nbsp;&nbsp;
						<input type="submit" value="批量删除" class="btn btn-primary">&nbsp;&nbsp;
						<button id="LoadExcel"  class="btn btn-primary" type="button" data-toggle="modal" data-target=".bs-model">Excel导入</button>
				
					
						<div class="col-xs-8 col-md-8 " style="float: right; text-align:right;">
								<script type="text/javascript" src="<%=basePath%>js/page.js"></script>
								<c:if test="${page.isAjax==0}">
									<c:if test="${page.pageNo-1>0 }">
										<a href="javascript:upPage()" class="btn btn-default btn-sm">&laquo;上一页</a>
									</c:if>

									<a class="btn btn-default btn-sm">第${page.pageNo }页</a>

									<c:if test="${page.pageNo+1<=page.totalPage }">
										<a href="javascript:nextPage()" class="btn btn-default btn-sm">下一页&raquo;</a>
									</c:if>
										&nbsp;&nbsp;共${page.totalRecord }条&nbsp;共${page.totalPage}页 &nbsp;&nbsp;跳转到&nbsp;
										<input type="text" value="" id="zc_tz_text"
										style="width:28px;height:28px" />&nbsp;页<input type="button"
										value="跳转" id="zc_tz_button" class="btn btn-default btn-sm"
										onclick="zcTzClick(${page.totalPage})" /> &nbsp;&nbsp;每页&nbsp;<input type="text" value="" id="page_size_text"
										style="width:28px;height:28px" />&nbsp;条<input type="button"
										value="设置" id="zc_size_button" class="btn btn-default btn-sm"
										onclick="pageSizeClick()" />
								</c:if>
							</div>
							</div><!-- /row -->
				</div>
				</div>
			</form>
			<div class="modal fade bs-model" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title"><span class="fa fa-file-excel-o"></span>&nbsp;&nbsp;Excel导入</h4>
						</div>
						<div class="modal-body">
							<form method="post" id="uploadForm" enctype="multipart/form-data" action="user/uploadExcel.shtm">					
								<div class="fileUpload btn btn-primary">
								    <span>选择文件</span>
								    <input id="upfile" type="file" name="upfile"  class="upload" />
								</div>
								<input id="uploadFile"  placeholder="Choose File" disabled="disabled" />&nbsp;&nbsp;				
								<a href="<%=basePath%>downloadFile.chtm?filePath=D:\apache-tomcat-7.0.70\webapps\lwgl\attached\tableModel\学生账号模板.xlsx">模板下载</a>
								<input type="hidden" value="student" name="roleName"/> 		
								<script type="text/javascript">
									document.getElementById("upfile").onchange = function () {
												    document.getElementById("uploadFile").value = this.value;
												};
								</script>				
							</form>
						</div>
						<div class="modal-footer">		
							<input type="button" value="Excel导入" id="btn" class="btn btn-primary" onclick="checkData()"/>
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
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
				<h4 class="modal-title" id="error-title"></h4>
				</div>
				<div class="modal-body" id="error-body">
				<p></p>
				</div>
				<div class="modal-footer" id="error-footer">			
					<button type="button" class="btn btn-primary"
						data-dismiss="modal">确认</button>
				</div>
			</div>
		</div>
	</div>

          
		</div>
	</div>	
	</div>
	</div>
	<script type="text/javascript"
		src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	<script>
		var a = 1;
		$('#check_all').click(function() {
			if (a == 1) {
				$('[name=ids]:checkbox').prop('checked', true);
				a--;
			} else {
				$('[name=ids]:checkbox').attr('checked', false);
				a++;
			}
		})
		   	$(document).ready(function(){	
	   			/*弹窗提示异常消息*/
		   		var error = $("#error").val();
		   		if(!(error==''|| error==undefined || error==null)){
				$("#error-title").html("错误提示");
				$("#error-body").html(error);
				$("#mymodal-error").modal("toggle");
				}
		   		
		   		/*弹窗提示成功消息*/
		   		var success = $("#success").val();
		   		if(!(success==''|| success==undefined || success==null)){
				$("#error-title").html("提示");
				$("#error-body").html(success);
				$("#mymodal-error").modal("toggle"); 
				}
				});
	</script>
</body>
</html>