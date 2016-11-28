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
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<title></title>
	</head>
	<body>
		<script type="text/javascript" src="<%=basePath%>js/AnsjCheck.js" ></script>

<style>
	#mymodal-ansj .modal-body{
		height:400px;
  		overflow:auto;
	}
</style>
		<div class="container" style="margin-top: 50px;">
			<div class="row">
				<div class="col-md-9 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-edit"></span>&nbsp;&nbsp;审核课题</div>
						<form class="form-horizontal" role="form" method="post" action="<%=basePath %>majorLeader/findApplyTitle_checkApplyTitle.shtm">
							<div style="padding: 50px;">
							<table class="table table-bordered table-responsive table-hover">
								<tbody>
									<tr>
										<td><strong>课题申报人：</strong>${applyTitle.stuName}</td>
									</tr>
									<tr>
										<td><strong>课题所属专业：</strong>${applyTitle.stuMajor}</td>
									</tr>
									<tr>
										<td><div class="col-sm-10" style="padding:0px;"><strong>课题名称：</strong>${applyTitle.title}</div>
										<input hidden="hidden" id="titleName" value="${applyTitle.title}">
										<div class="col-sm-1"><button class="btn btn-success" type="button" onclick="checkTitle()">查重</button>
										</div>
										</td>		
									</tr>
									<tr>
										<td><strong>题目性质：</strong>${applyTitle.nature}</td>
									</tr>
									<tr>
										<td><strong>完成形式：</strong>${applyTitle.titleForm}</td>
									</tr>
									<tr>
										<td><strong>立题理由：</strong><br>${titleForm.titleReason}</td>
									</tr>
								</tbody>
							</table>
							<div>
								<label class="control-label">审核意见:</label>
								<div>
									<textarea class="form-control" rows="4" name="managerIdea"></textarea>
								</div>
							</div>
							<div>
								<input type="hidden" value="${applyTitle.id}" name="ids">
								<label class=" control-label">是否通过审核:</label>
								<div>
									<select class="form-control" type="text" name="status">
										<option value="1">通过</option>
										<option value="2">退回修改</option>
									</select>
								</div>
							</div>				
							<div style="margin-top: 20px">
								<button class="btn btn-primary" type="submit">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-default" onclick="history.back()">返回</a>						
							</div>
							<div class="alert alert-info" style="margin-top: 20px">
								<p>通过：表示专业负责人认可该课题，学生可用此题目作为论文题目。</p>
                                <p>退回修改：表示专业负责人不认可该课题，要求学生根据审核意见修改课题内容，重新申报。</p>
							</div>			
							</div>
						</form>				
					</div>
				</div>
			</div>
		</div>
			<!-- 课题查重结果显示模态窗 -->
	<div class="modal fade" id="mymodal-ansj" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	   <div class="modal-dialog">
		 <div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="ansjModalTitle"><span class="fa fa-database"></span></h4>
				</div>
				<div class="modal-body" id="ansjModalBody">
				<div class="alert alert-info text-center"><p>课题查重尚有不足，结果仅供参考</p></div>
				<div class="" id="keyWords"></div>
				<div>
					<table class="table table-hover" id="ansj-table">
						<thead>
							<tr>
							    <th>学届</th>
								<th><span style="margin-left:15%">往届课题名</span>
									<span class="text-success" style="float:right;"><small>相似度<span class="fa fa-sort-amount-desc fa-xs"></span></small></span>
								</th>	
								<div></div>
							</tr>
						</thead>
						<tbody id="ansj-tbody" class="text-center">

						</tbody>
					</table>
				</div>
				</div>
				<div class="modal-footer" id="ansjModalFooter">			
					<button type="button" class="btn btn-primary"
						data-dismiss="modal">确认</button>
				</div>
			</div>
		</div>
	</div>
	</body>

</html>