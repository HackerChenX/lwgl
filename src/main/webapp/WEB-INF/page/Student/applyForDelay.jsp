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
	<link href="<%=basePath %>css/daterangepicker.css" rel="stylesheet" />
		<div class="container">
			<div class="row  col-md-8 col-md-offset-1" style="margin-top: 50px;">
				<div class="panel panel-info">
					<div class="panel-body">
						<div class="alert alert-info" role="alert">
							<p>1、各阶段逾期未完成或更改,可申请延期,提请指导老师和教学秘书审核批准</p>
							<p>2、如需更换课题,请同时提交课题申请阶段到现阶段间所有阶段延期申请</p>
							<p>3、请在合理范围内选择延期阶段和截止时间</p>
						</div>
					</div>
				</div>
			</div>
			<div class="row col-md-8 col-md-offset-1">
				<div class="panel panel-primary">
					<div class="panel-heading"><span class="fa fa-edit"></span>&nbsp;&nbsp;延期申请</div>
					<form method="post" action="<%=basePath %>student/initApplyForDelay_applyForDelay.shtm">
						<div class="panel-body">
							<table class="table table-bordered table-responsive">
								<thead>
									<tr>
										<th>延期阶段</th>
										<th>延期截止时间</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<select class="form-control" id="stageChioce" name="delayStage">
												<option value="">请选择</option>											
													<option  value="apply_title">课题申请</option>
													<option  value="task_book">任务书</option>
													<option  value="opening_report">开题报告</option>
													<option  value="mid_check">中期检查</option>
													<option  value="first_paper">论文初稿</option>
													<option  value="final_paper">论文定稿</option>																
											</select>
										</td>
										<td>
											<input type="text" id="delay" class="form-control" name="delayTime" />
										</td>
									</tr>
								</tbody>
							</table>
							<textarea name="delayReason" id="delayReason" class="form-control" placeholder="延期理由"></textarea>
						</div>
						<div class="panel-footer">
							<button class="btn btn-primary" id="delaySubmit" type="button">提交</button>	
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
										<h4 class="modal-title"></h4>
									</div>
									<div class="modal-body">
										<p></p>
									</div>
									<div class="modal-footer">
										<button class="btn btn-primary" type="submit">确认</button>
										&nbsp;&nbsp;
										<button type="button" class="btn btn-default"
											data-dismiss="modal">取消</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="row col-md-8 col-md-offset-1" >
				<div class="panel panel-success">
					<div class="panel-heading"><span class="fa fa-calendar"></span>&nbsp;&nbsp;已申请延期</div>
					<div class="panel-body">
						<table style="text-align:center" class="table table-bordered table-responsive tabel-hover">
							<thead>
								<tr>
									<th>延期阶段</th>
									<th>延期时间</th>
									<th>指导老师审核</th>
									<th>教学秘书审核</th>
									<th>退回</th>
								</tr>
							</thead>
							<tbody>
							<c:if test="${applyDelays!=null}"></c:if>
							<c:forEach var="applyDelay" items="${applyDelays}">
								<tr>
									<td><button type="button" class="btn btn-link" style="padding:0px" name="delayName" reason="${applyDelay.delayReason}" teaIdea="${applyDelay.teaIdea}" managerIdea="${applyDelay.managerIdea}">${applyDelay.delayStage}</button></td>
									<td>${applyDelay.dateTimeStr}</td>
									<td><c:if test="${applyDelay.teaStatus==0}">审核中</c:if>
										<c:if test="${applyDelay.teaStatus==1}">通过</c:if>
										<c:if test="${applyDelay.teaStatus==2}">驳回</c:if>
									</td>
									<td><c:if test="${applyDelay.managerStatus==0}">审核中</c:if>
										<c:if test="${applyDelay.managerStatus==1}">通过</c:if>
										<c:if test="${applyDelay.managerStatus==2}">驳回</c:if>
									</td>
									<input hidden="hidden" id="applyDelayId" name="applyDelayId" value="${applyDelay.id}">
									<td><button name="cancelDelay" class="btn btn-link" style="padding:0px" value="${applyDelay.id}">撤回</button></td>																	
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal fade" id="mymodal-unApply" tabindex="-1"
									role="dialog" aria-labelledby="mySmallModalLabel"
									aria-hidden="true">
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
												<a id="cancelSubmit" class="btn btn-primary" href="<%=basePath %>student/initApplyForDelay_cancel.shtm?id=">确认</a>
												<button type="button" class="btn btn-default"
													data-dismiss="modal">取消</button>
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
				<div class="modal-body errorBody">
				<p></p>
				</div>
				<div class="modal-footer">			
					<button type="button" class="btn btn-primary"
						data-dismiss="modal">确认</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="mymodal-reason" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
		 <div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
				<strong>延期理由:</strong>
				<textarea id="reasonForDelay" class="form-control" readonly>无</textarea>
				</br>
				<strong>指导老师意见:</strong>
				<textarea id="zdTeaIdea" class="form-control" readonly>暂无意见</textarea>
				</br>
				<strong>教学秘书意见:</strong>
				<textarea id="managerTeaIdea" class="form-control" readonly>暂无意见</textarea>		
				</div>
				<div class="modal-footer">			
					<button type="button" class="btn btn-primary"
						data-dismiss="modal">确认</button>
				</div>
			</div>
		</div>
	</div>
	<input hidden="hidden" id="ErrorMsg" value="${errorMsg}"/>
		</div>	
		<script type="text/javascript" src="<%=basePath%>js/moment-with-locales.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/daterangepicker.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				var ErrorMsg=$("#ErrorMsg").val();
				if(!(ErrorMsg==''|| ErrorMsg==undefined || ErrorMsg==null)){
					$(".modal-title").html("错误提示");
					$(".errorBody").html(ErrorMsg);
					$("#mymodal-error").modal("toggle");
				}
			});
			$(function(){
				$('#delay').daterangepicker({
					timePicker: true,
					timePickerIncrement: 1,
					singleDatePicker:true,
					locale: {
						format: 'YYYY/MM/DD HH:MM'
					},
				}, function(start, end, label) {
					$("#teacherStartTime").html(start.format('YYYY/MM/DD HH:MM'));
					$("#teacherEndTime").html(end.format('YYYY/MM/DD HH:MM'));
				});
				//对中文的支持
				$("#delay").data('daterangepicker').updateMomentLocale('zh-cn');
			});
			$(function(){
			$("#delaySubmit").click(function(){
			    var option = $("#stageChioce").val();
			    if(option==''|| option==undefined || option==null){
				    $(".modal-title").html("提示");
					$(".errorBody").html("请选择一个阶段");
					$("#mymodal-error").modal("toggle");
					return false;
			    }
			    var time=$("#delay").val();
			    if(time==''|| time==undefined || time==null){
				    $(".modal-title").html("提示");
					$(".errorBody").html("请选择时间");
					$("#mymodal-error").modal("toggle");
					return false;
			    }
			    var delayReason=$("#delayReason").val();
			    if(delayReason==''|| delayReason==undefined || delayReason==null){
				    $(".modal-title").html("提示");
					$(".errorBody").html("请填写延期理由");
					$("#mymodal-error").modal("toggle");
					return false;
			    }
		        $(".modal-title").html("提交申请");
				$(".modal-body").html("审核后无法撤回,确认提交?");
				$("#mymodal-data").modal("toggle");	    
				return true;
			});
		});	
		$(function(){
			$("button[name='cancelDelay']").on("click",function(){
				var url =$("#cancelSubmit").attr("href")+$(this).val();
				$(".modal-title").html("撤回提示");
				$(".modal-body").html("仅限撤回审核中的延期申请，确认撤回？");						
				$("#cancelSubmit").attr("href",url)	
				$("#mymodal-unApply").modal("toggle");
			});
		});
		$(function(){
			$("button[name='delayName']").on("click",function(){
				$("#reasonForDelay").html("无");
				$("#managerTeaIdea").html("暂无审核意见");
				$("#zdTeaIdea").html("暂无审核意见");	
				var teaIdea =$(this).attr("teaIdea");
				if(!(teaIdea==''|| teaIdea==undefined || teaIdea==null)){
					 $("#zdTeaIdea").html(teaIdea);
				}
				var managerIdea=$(this).attr("managerIdea");
				if(!(managerIdea==''|| managerIdea==undefined || managerIdea==null)){
					 $("#managerTeaIdea").html(managerIdea);

				}
				var reason=$(this).attr("reason");	
				if(!(reason==''|| reason==undefined || reason==null)){
					 $("#reasonForDelay").html(reason);				
				}				
				$(".modal-title").html("审核意见");						
				$("#mymodal-reason").modal("toggle");							
			});
		});
		</script>
	</body>
</html>