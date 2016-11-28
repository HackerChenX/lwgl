<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":"   + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width,initial-scale=1">						
		<title></title>
	</head>
	<body>
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
		<div class="container">
			<div class="row  col-md-8 col-md-offset-1" style="margin-top: 50px;">
				<div class="panel panel-info">
					<div class="panel-body">
						<div class="alert alert-info">
							<p>1、请将需要提交的毕业设计项目文件打包压缩后上传</p>
							<p>2、提交文件后由系统命名为：课题名+姓名+学号 格式</p>
							<p>3、若提交错误，可重新提交覆盖原纪录</p>
						</div>
					</div>
				</div>
			</div>
			<div class="row  col-md-8 col-md-offset-1">
				<form method="post" id="uploadForm" enctype="multipart/form-data" action="<%=basePath%>student/allFile_Submit.shtm">					
				<div class="panel panel-primary">
					<div class="panel-heading"><span class="fa fa-file-zip-o"></span>&nbsp;&nbsp;项目提交</div>
					<div class="panel-body">						
								<div class="fileUpload btn btn-primary">
								    <span>选择文件</span>
								    <input id="upfile" type="file" name="upfile"  class="upload" />
								</div>
								<input id="uploadFile"  placeholder="选择文件" disabled="disabled" />
								<input type="hidden" value="student" name="roleName"/> 		
								<script type="text/javascript">
									document.getElementById("upfile").onchange = function () {
												    document.getElementById("uploadFile").value = this.value;
												};
								</script>				
						
					</div>
					<div class="panel-footer">
						<button class="btn btn-primary" type="submit">提交</button>
					</div>
				</div>
				</form>
			</div>
			<div class="row  col-md-8 col-md-offset-1">
				<div class="panel panel-success">
					<div class="panel-body">
						<table style="text-align:center" class="table table-bordered table-striped table-hover">
							<thead>
								<tr>
									<th>姓名</th>
									<th>学号</th>
									<th>项目文件</th>
								</tr>
							</thead>
							<tbody>
							<c:if test="${Paper!=null}">
								<tr>
									<td>${Paper.stuName}</td>
									<td>${Paper.stuNum}</td>
									<td><a href="<%=basePath%>downloadFile.chtm?filePath=${Paper.projectFile}">项目文件</a></td>
								</tr>
							</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</body>