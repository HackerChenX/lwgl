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
<title>教师申报课题</title>
</head>

<body>
<script type="text/javascript" src="<%=basePath%>js/AnsjCheck.js" ></script>
<style>
	#mymodal-ansj .modal-body{
		height:400px;
  		overflow:auto;
	}
</style>
		<div class="row" style="margin-top:50px;">
			<div class="col-md-9 col-md-offset-1">
				<form id="form" class="form-horizontal"
					action="<%=basePath %>guideTeacher/findTeaTitle_applyTeaTitle.shtm"
					method="post">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-edit"></span>&nbsp;&nbsp;申报课题
						</div>
						<div class="panel-body"
							style="padding-bottom: 100px;padding-top: 50px;">
							<div class="form-group">
								<div class="alert alert-info text-center col-sm-7 col-sm-offset-3"><p>课题三年内不能重复,请查询确认后申报</p></div>
							</div>
							<div class="form-group ">
								<label class="col-sm-3 control-label">课题名称:</label>
								<div class="col-sm-7">
									<input class="form-control" type="text" id="titleName"
										name="titleName" value="${teacherTitle.title}" />
								</div>
								<div class="col-sm-1">
									<button class="btn btn-success" type="button" onclick="checkTitle()">查重</button>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">题目性质:</label>
								<div class="col-sm-8">
									<select class="form-control" type="text" id="titleNature"
										name="titleNature">
										<option value="">请选择</option>
										<c:if test="${titleNatures!=null}">
											<c:forEach var="titleNature" items="${titleNatures}">
												<c:if test="${teacherTitle.nature==titleNature.natureName}">
													<option selected="selected">${titleNature.natureName}</option>
												</c:if>
												<option>${titleNature.natureName}</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">完成形式:</label>
								<div class="col-sm-8">
									<select class="form-control" type="text" id="titleForm"
										name="titleForm">
										<option value="">请选择</option>
										<c:if test="${titleForms!=null}">
											<c:forEach var="titleForm" items="${titleForms}">
												<c:if test="${teacherTitle.titleForm==titleForm.formName}">
													<option selected="selected">${titleForm.formName}</option>
												</c:if>

												<option>${titleForm.formName}</option>

											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">立题理由:</label>
								<div class="col-sm-8">
									<textarea name="titleReason" id="titleReason"
										class="form-control" rows="6">${teacherTitle.titleReason}</textarea>
								</div>
							</div>
							<div class="col-sm-6 col-sm-offset-3" style="margin-top: 20px">
								<div>
									<input hidden="hidden" name="oldTitle"
										value="${teacherTitle.title}" /> <input hidden="hidden"
										name="titleId" value="${teacherTitle.id}" />
									<button class="btn btn-primary" id="ApplyCheck" type="button">提交</button>
									&nbsp;&nbsp;
									<c:if test="${teacherTitle==null}">
										<button class="btn btn-default" type="reset">重置</button>
									</c:if>
									&nbsp;&nbsp; <a class="btn btn-default"
										href="<%=basePath%>guideTeacher/findTeaTitle.shtm">返回</a>
								</div>
							</div>
							<div class="modal fade" id="mymodal-data" tabindex="-1"
								role="dialog" aria-labelledby="mySmallModalLabel"
								aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">
												<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
											</button>
											<h4 class="modal-title" id="data-title"></h4>
										</div>
										<div class="modal-body" id="data-body">
											<p></p>
										</div>
										<div class="modal-footer">
											<button type="submit" id="ApplyCheck" class="btn btn-primary">确认</button>
											<button type="button" class="btn btn-default"
												data-dismiss="modal">取消</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
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
					<div class="modal-footer" id="error-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
					</div>
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
<script src="//cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
	<script type="text/javascript">
//表单验证
			$(function() {
				$("#ApplyCheck").click(function() {
					var titleName = $("#titleName").val().trim();
					var titleNature = $("#titleNature").val();
					var titleForm = $("#titleForm").val();
					var titleReason = $("#titleReason").val().trim();
					if(titleName == "" || titleNature == "" || titleForm == "" || titleReason == "") {
						$("#error-title").html("错误提示");
						$("#error-body").html("当前表单不能有空项!");
						$("#mymodal-error").modal("toggle");
						return false;
					}
					$("#data-title").html("提交申请");
					$("#data-body").html("确认提交？");
					$("#mymodal-data").modal("toggle");
				});
			});
//jquery回车键表单提交禁用
	$("#form").keypress(function(e) {
  if (e.which == 13) {
    return false;
  }
});
		</script>
</body>

</html>