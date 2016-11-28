<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
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
		<link href="<%=basePath %>css/daterangepicker.css" rel="stylesheet" />
		<title></title>
	</head>

	<body>
		<div class="row" style="margin-top: 50px;">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<div class="panel panel-primary">
					<div class="panel-heading"><span class="fa fa-edit"></span>&nbsp;&nbsp;截止日期设置</div>
					<div class="panel-body">
			
						<form class="form-horizontal" method="post" action="<%=basePath %>secretary/findStagePlan_setStageDate.shtm">
							<div class="page-header">
								<h4><span class="fa fa-calendar-check-o"></span>&nbsp;&nbsp;课题申报日期：</h4>
							</div>
							<div class="row" id="plug-DateRangePicker">
								<div class="col-md-5">
									<div class="form-group">
										<label for="projectTime" class="col-sm-2 control-label">日期:</label>
										<div class="col-sm-10">
											<input type="text" id="projectTime" class="form-control" name="date" readonly="readonly"/>
										</div>
									</div>
								</div>
								<div class="col-sm-5">
									<div class="form-group">
										<label class="col-sm-2 control-label">结果:</label>
										<div class="col-sm-10 ">
											<p class="form-control-static ">
												<c:if test="${applyTitle==null}">
												<label class="label control-label label-success" id="teacherStartTime">未设置</label>
												<label class="label control-label label-success" id="teacherEndTime">未设置</label>
												</c:if>
												<c:if test="${applyTitle!=null}">
												<label class="label control-label label-success" id="teacherStartTime">${applyTitle.startTimeStr}</label>
												<label class="label control-label label-success" id="teacherEndTime">${applyTitle.endTimeStr}</label>
												</c:if>
											</p>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<input type="hidden" name="stageName" value="apply_title">
									<button class="btn btn-primary" type="submit">提交</button>
								</div>
							</div>
						</form>
						<form class="form-horizontal" method="post" action="<%=basePath %>secretary/findStagePlan_setStageDate.shtm">
							<div class="page-header">
								<h4><span class="fa fa-calendar-check-o"><span>&nbsp;&nbsp;提交任务书日期：</h4>
							</div>
							<div class="row" id="plug-DateRangePicker">
								<div class="col-sm-5">
									<div class="form-group">
										<label for="taskTime" class="col-sm-2 control-label">日期:</label>
										<div class="col-sm-10">
											<input type="text" id="taskTime" class="form-control" name="date" readonly="readonly"/>
										</div>
									</div>
								</div>
								<div class="col-sm-5">
									<div class="form-group">
										<label class="col-sm-2 control-label">结果:</label>
										<div class="col-sm-10 ">
											<p class="form-control-static ">
												<c:if test="${taskBook==null}">
												<label class="label control-label label-success" id="teacherStartTime">未设置</label>
												<label class="label control-label label-success" id="teacherEndTime">未设置</label>
												</c:if>
												<c:if test="${taskBook!=null}">
												<label class="label control-label label-success" id="teacherStartTime">${taskBook.startTimeStr}</label>
												<label class="label control-label label-success" id="teacherEndTime">${taskBook.endTimeStr}</label>
												</c:if>
											</p>
										</div>

									</div>
								</div>
								<div class="col-sm-2">
									<input type="hidden" name="stageName" value="task_book">
									<button class="btn btn-primary" type="submit">提交</button>
								</div>
							</div>
						</form>
						<form class="form-horizontal" method="post" action="<%=basePath %>secretary/findStagePlan_setStageDate.shtm">
							<div class="page-header">
								<h4><span class="fa fa-calendar-check-o"><span>&nbsp;&nbsp;提交开题报告日期：</h4>
							</div>
							<div class="row" id="plug-DateRangePicker">
								<div class="col-sm-5">
									<div class="form-group">
										<label for="openBookTime" class="col-sm-2 control-label">日期:</label>
										<div class="col-sm-10">
											<input type="text" id="openBookTime" class="form-control" name="date" readonly="readonly"/>
										</div>
									</div>
								</div>
								<div class="col-sm-5">
									<div class="form-group">
										<label class="col-md-2 control-label">结果:</label>
										<div class="col-sm-10">
											<p class="form-control-static">
												<c:if test="${openingReport==null}">
												<label class="label control-label label-success" id="teacherStartTime">未设置</label>
												<label class="label control-label label-success" id="teacherEndTime">未设置</label>
												</c:if>
												<c:if test="${openingReport!=null}">
												<label class="label control-label label-success" id="teacherStartTime">${openingReport.startTimeStr}</label>
												<label class="label control-label label-success" id="teacherEndTime">${openingReport.endTimeStr}</label>
												</c:if>
											</p>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<input type="hidden" name="stageName" value="opening_report">
									<button class="btn btn-primary" type="submit">提交</button>
								</div>
							</div>
						</form>
						<form class="form-horizontal" method="post" action="<%=basePath %>secretary/findStagePlan_setStageDate.shtm">
							<div class="page-header">
								<h4><span class="fa fa-calendar-check-o"><span>&nbsp;&nbsp;提交中期检查日期：</h4>
							</div>
							<div class="row" id="plug-DateRangePicker">
								<div class="col-sm-5">
									<div class="form-group">
										<label for="midTime" class="col-sm-2 control-label">日期:</label>
										<div class="col-sm-10">
											<input type="text" id="midTime" class="form-control" name="date" readonly="readonly"/>
										</div>
									</div>
								</div>
								<div class="col-sm-5">
									<div class="form-group">
										<label class="col-md-2 control-label">结果:</label>
										<div class="col-sm-10 ">
											<p class="form-control-static ">
												<c:if test="${midCheck==null}">
												<label class="label control-label label-success" id="teacherStartTime">未设置</label>
												<label class="label control-label label-success" id="teacherEndTime">未设置</label>
												</c:if>
												<c:if test="${midCheck!=null}">
												<label class="label control-label label-success" id="teacherStartTime">${midCheck.startTimeStr}</label>
												<label class="label control-label label-success" id="teacherEndTime">${midCheck.endTimeStr}</label>
												</c:if>
											</p>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<input type="hidden" name="stageName" value="mid_check">
									<button class="btn btn-primary" type="submit">提交</button>
								</div>
							</div>
						</form>
						<form class="form-horizontal" method="post" action="<%=basePath %>secretary/findStagePlan_setStageDate.shtm">
							<div class="page-header">
								<h4><span class="fa fa-calendar-check-o"><span>&nbsp;&nbsp;提交论文初稿日期：</h4>
							</div>
							<div class="row" id="plug-DateRangePicker">
								<div class="col-sm-5">
									<div class="form-group">
										<label for="firstTime" class="col-sm-2 control-label">日期:</label>
										<div class="col-sm-10">
											<input type="text" id="firstTime" class="form-control" name="date" readonly="readonly"/>
										</div>
									</div>
								</div>
								<div class="col-sm-5">
									<div class="form-group">
										<label class="col-sm-2 control-label">结果:</label>
										<div class="col-sm-10 ">
											<p class="form-control-static ">
												<c:if test="${firstPaper==null}">
												<label class="label control-label label-success" id="teacherStartTime">未设置</label>
												<label class="label control-label label-success" id="teacherEndTime">未设置</label>
												</c:if>
												<c:if test="${firstPaper!=null}">
												<label class="label control-label label-success" id="teacherStartTime">${firstPaper.startTimeStr}</label>
												<label class="label control-label label-success" id="teacherEndTime">${firstPaper.endTimeStr}</label>
												</c:if>
											</p>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<input type="hidden" name="stageName" value="first_paper">
									<button class="btn btn-primary" type="submit">提交</button>
								</div>
							</div>
						</form>
						<form class="form-horizontal" method="post" action="<%=basePath %>secretary/findStagePlan_setStageDate.shtm">
							<div class="page-header">
								<h4><span class="fa fa-calendar-check-o"><span>&nbsp;&nbsp;提交论文定稿日期：</h4>
							</div>
							<div class="row" id="plug-DateRangePicker">
								<div class="col-sm-5">
									<div class="form-group">
										<label for="finalTime" class="col-sm-2 control-label">日期:</label>
										<div class="col-sm-10">
											<input type="text" id="finalTime" class="form-control" name="date" readonly="readonly"/>
										</div>
									</div>
								</div>
								<div class="col-sm-5">
									<div class="form-group">
										<label class="col-sm-2 control-label">结果:</label>
										<div class="col-sm-10 ">
											<p class="form-control-static ">
												<c:if test="${finalPaper==null}">
												<label class="label control-label label-success" id="teacherStartTime">未设置</label>
												<label class="label control-label label-success" id="teacherEndTime">未设置</label>
												</c:if>
												<c:if test="${finalPaper!=null}">
												<label class="label control-label label-success" id="teacherStartTime">${finalPaper.startTimeStr}</label>
												<label class="label control-label label-success" id="teacherEndTime">${finalPaper.endTimeStr}</label>
												</c:if>
											</p>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<input type="hidden" name="stageName" value="final_paper">
									<button class="btn btn-primary" type="submit">提交</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" value="${errorMsg}" id="error">
		<input type="hidden" value="${successMsg}" id="success">
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/moment-with-locales.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/daterangepicker.js"></script>
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
		<script type="text/javascript">
			
			$(function() {
				$('#projectTime').daterangepicker({
					timePicker: true,
					timePickerIncrement: 1,
					locale: {
						format: 'YYYY/MM/DD HH:MM'
					},
				}, function(start, end, label) {
					$("#projectStartTime").html(start.format('YYYY/MM/DD HH:MM'));
					$("#projectEndTime").html(end.format('YYYY/MM/DD HH:MM'));
				});
				//对中文的支持
				$('#projectTime').data('daterangepicker').updateMomentLocale('zh-cn');
			});
			$(function() {
				$('#taskTime').daterangepicker({
					timePicker: true,
					timePickerIncrement: 1,
					locale: {
						format: 'YYYY/MM/DD HH:MM'
					},
				}, function(start, end, label) {
					$("#taskStartTime").html(start.format('YYYY/MM/DD HH:MM'));
					$("#taskEndTime").html(end.format('YYYY/MM/DD HH:MM'));
				});
				//对中文的支持
				$('#taskTime').data('daterangepicker').updateMomentLocale('zh-cn');
			});
			$(function() {
				$('#openBookTime').daterangepicker({
					timePicker: true,
					timePickerIncrement: 1,
					locale: {
						format: 'YYYY/MM/DD HH:MM'
					},
				}, function(start, end, label) {
					$("#openBookStartTime").html(start.format('YYYY/MM/DD HH:MM'));
					$("#openBookEndTime").html(end.format('YYYY/MM/DD HH:MM'));
				});
				//对中文的支持
				$('#openBookTime').data('daterangepicker').updateMomentLocale('zh-cn');
			});
			$(function() {
				$('#midTime').daterangepicker({
					timePicker: true,
					timePickerIncrement: 1,
					locale: {
						format: 'YYYY/MM/DD HH:MM'
					},
				}, function(start, end, label) {
					$("#midStartTime").html(start.format('YYYY/MM/DD HH:MM'));
					$("#midEndTime").html(end.format('YYYY/MM/DD HH:MM'));
				});
				//对中文的支持
				$('#midTime').data('daterangepicker').updateMomentLocale('zh-cn');
			});
			$(function() {
				$('#firstTime').daterangepicker({
					timePicker: true,
					timePickerIncrement: 1,
					locale: {
						format: 'YYYY/MM/DD HH:MM'
					},
				}, function(start, end, label) {
					$("#firstStartTime").html(start.format('YYYY/MM/DD HH:MM'));
					$("#firstEndTime").html(end.format('YYYY/MM/DD HH:MM'));
				});
				//对中文的支持
				$('#firstTime').data('daterangepicker').updateMomentLocale('zh-cn');
			});
			$(function() {
				$('#finalTime').daterangepicker({
					timePicker: true,
					timePickerIncrement: 1,
					locale: {
						format: 'YYYY/MM/DD HH:MM'
					},
				}, function(start, end, label) {
					$("#finalStartTime").html(start.format('YYYY/MM/DD HH:MM'));
					$("#finalEndTime").html(end.format('YYYY/MM/DD HH:MM'));
				});
				//对中文的支持
				$('#finalTime').data('daterangepicker').updateMomentLocale('zh-cn');
			});
		</script>
	</body>

</html>