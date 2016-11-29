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
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width,initial-scale=1">
<title></title>
</head>

<body style="font-family: '微软雅黑'; font-size: 17px;">
<link rel="stylesheet" href="<%=basePath%>css/pace-theme-bounce.css"/>	
<script type="text/javascript" src="<%=basePath%>js/AnsjCheck.js" ></script>

<style>
	#mymodal-ansj .modal-body{
		height:400px;
  		overflow:auto;
	}
</style>	
	<div style="margin-top: 50px;">
	<form hidden="hidden" name="findTeacherInfo" method="post" action="<%=basePath%>student/ApplyTitleSelf_applyForProject.shtm" id="select">
		<input name="pageNo"  type="hidden"  value="${page.pageNo}"   id="pageNow" />
		<input name="pageSize" type="hidden"  value="${page.pageSize}" id="pageSize">
	</form>
		<form  name="applyTitleSelf" class="form-horizontal" role="form" method="post" action="<%=basePath%>student/ApplyTitleSelf_applyTitle.shtm" >			
			<div class="row">
				<div class="col-md-9 col-md-offset-1">
					<div class="panel panel-info">
						<div class="panel-heading">
							<span class="fa fa-edit"></span>&nbsp;&nbsp;可选指导老师
						</div>
						<div class="panel-body">
							<table style="text-align:center"
								class="table table-bordered table-striped table-hover text-center">
								<thead>
									<tr>
										<th>选择</th>
										<th>教师名</th>
										<th>性别</th>
										<th>职称</th>
										<th>专业</th>
										<th>学生名额</th>
										<th>已申请名额</th>
										<th>联系方式</th>
										<th>邮箱</th>
									</tr>
								</thead>
								<tbody>
								  <c:if test="${page.results!=null}">
									<c:forEach var="teacher" items="${page.results}">
										<tr>
											<td><input type="radio" name="id"
												value="${teacher.id}"></td>
											<td>${teacher.teaName}</td>
											<td>${teacher.sex}</td>
											<td>${teacher.zhicheng}</td>
											<td>${teacher.major}</td>
											<td>${teacher.allStunum}</td>
											<td>${teacher.nowStunum}</td>
											<td>${teacher.tel}</td>
											<td>${teacher.mail}</td>										
										</tr>					
									</c:forEach>
								  </c:if>
								</tbody>
							</table>
						</div>
						<div class="panel-footer">
						<div class="row" style="padding-left: 15px;">						
						<div class="col-xs-8 col-md-8 " style="float: right; text-align:right;">
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
						</div><!-- /page-row -->
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-9 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-edit"></span>&nbsp;&nbsp;课题申报
						</div>
						<div class="panel-body" style="padding-bottom: 100px;padding-top: 50px;">
							<div class="form-group">
							<div class="alert alert-info text-center col-sm-7 col-sm-offset-3"><p>课题三年内不能重复,请查询确认后申报</p></div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">课题名称:</label>
								<div class="col-sm-7">
									<input class="form-control" type="text" id="titleName" name="titleName" />
								</div>								
								<div class="col-sm-1">
									<button class="btn btn-success" type="button" onclick="checkTitle()">查重</button>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">题目性质:</label>
								<div class="col-sm-8">
									<select class="form-control" type="text" id="titleNature" name="titleNature">
										<option value=null>请选择</option>
										<c:if test="${titleNatures!=null}">
											<c:forEach var="titleNature" items="${titleNatures}">
												<option>${titleNature.natureName}</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">完成形式:</label>
								<div class="col-sm-8">
									<select class="form-control" type="text" id="titleForm" name="titleForm">
										<option value="">请选择</option>
										<c:if test="${titleForms!=null}">
											<c:forEach var="titleForm" items="${titleForms}">
												<option>${titleForm.formName}</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">立题理由:</label>
								<div class="col-sm-8">
									<textarea name="titleReason" id="titleReason" class="form-control" rows="6"></textarea>
								</div>
							</div>
							<div class="col-sm-6 col-sm-offset-3" style="margin-top: 20px">
								<div>
									<button id="ApplyCheck" class="btn btn-primary" type="button">提交</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button class="btn btn-default" type="reset">重置</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a class="btn btn-default" href="<%=basePath %>student/ApplyTitleSelf.shtm">返回</a>
								</div>
							</div>
							<div class="modal fade" id="mymodal-data" tabindex="-1" role="dialog"
							aria-labelledby="mySmallModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="dataTitle"></h4>
									</div>
									<div class="modal-body" id="dataBody">
										<p></p>
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">确认</button>
										<button type="button" class="btn btn-default "
											data-dismiss="modal">取消</button>
									</div>
								</div>
							</div>
						</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="modal fade" id="mymodal-error" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
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
				<div class="modal-footer">			
					<button type="button" class="btn btn-primary"
						data-dismiss="modal">确认</button>
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
	</div>
	<script type="text/javascript">
	$(function(){
		$("#ApplyCheck").click(function(){
			var val=$('input:radio[name="id"]:checked').val();			
			if(val==null){
				$("#error-title").html("错误提示");
				$("#error-body").html("请选择指导老师!");
				$("#mymodal-error").modal("toggle");
				return false;
			}
			var titleName=$("#titleName").val().trim();
			var titleNature=$("#titleNature").val();
			var titleForm=$("#titleForm").val();
			var titleReason=$("#titleReason").val().trim();
			if(titleName==""||titleNature==""||titleForm==""||titleReason=="")
			{
		       $("#error-title").html("错误提示");
			   $("#error-body").html("当前表单不能有空项!");
			   $("#mymodal-error").modal("toggle");
			   return false;
        	}
			$("#dataTitle").html("提交申请");
			$("#dataBody").html("审核后无法修改，确认提交？");
			$("#mymodal-data").modal("toggle");
		});
	});    
    </script>
</body>
</html>