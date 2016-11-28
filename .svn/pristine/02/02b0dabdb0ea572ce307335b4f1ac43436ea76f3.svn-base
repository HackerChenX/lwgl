<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<title>学生延期申请</title>
</head>
<body style="font-family: '微软雅黑'; font-size: 17px; ">
		<div class="row" style="margin-top: 50px;" id="row1">
		<div class="col-xs-12 col-md-11 col-md-offset-1">		
				<form class="form-inline" method="post" action="<%=basePath%>guideTeacher/checkStuDelay.shtm">
					<div class="panel panel-primary">
						<div class="panel-body">
							<label class="control-label">学号：</label><input type="text"
								class="form-control" name="stuNum" value="${stuNum}" id="stu_id"
								placeholder="学生学号" /> <label class="control-label">姓名：</label><input
								type="text" class="form-control" name="stuName"
								value="${stuName}" id="stu_name" placeholder="学生姓名" /> <label
								class="control-label">状态:</label> <select class="form-control"
								name="status">
								<option value="3"
									<c:if test="${status==3}">selected="selected"</c:if>>全部</option>
								<option value="0"
									<c:if test="${status==0}">selected="selected"</c:if>>未审核</option>
								<option value="1"
									<c:if test="${status==1}">selected="selected"</c:if>>已通过</option>
								<option value="2"
									<c:if test="${status==2}">selected="selected"</c:if>>未通过</option>
							</select>
							<button class="btn btn-primary" type="submit" id="search">
								<span class="fa fa-search">搜索</span>
							</button>
						</div>
					</div>
				</form>				
			</div>
		</div>
		
		<div class="row" id="row2">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<form method="post" action="<%=basePath%>guideTeacher/checkStuDelay_opt.shtm">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;学生延期申请
						</div>
						
						<div class="table-responsive">
							<table style="text-align:center" class="table table-bordered table-hover text-center">
								<thead>
									<tr>
										<th>选择</th>
										<th>学号</th>
										<th>姓名</th>
										<th>延期类型</th>
										<th>延期时间至</th>
										<th class="text-center">操作</th>
										<th>状态</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${applyDelaysinfo!=null}">
										<c:forEach var="applyDelaysinfo" items="${applyDelaysinfo}">
											<tr>
												<td><input type="checkbox" name="ids" value="${applyDelaysinfo.id}">
												</td>
												<td>${applyDelaysinfo.stuNum}</td>
												<td>${applyDelaysinfo.stuName}</td>
												<td>
													<c:if test="${applyDelaysinfo.delayStage=='apply_title'}">
														申请课题
													</c:if>
													<c:if test="${applyDelaysinfo.delayStage=='task_book'}">
														提交任务书
													</c:if>
													<c:if test="${applyDelaysinfo.delayStage=='opening_report'}">
														提交开题报告
													</c:if>
													<c:if test="${applyDelaysinfo.delayStage=='mid_check'}">
														提交中期检查表
													</c:if>
													<c:if test="${applyDelaysinfo.delayStage=='first_paper'}">
														提交论文初稿
													</c:if>
													<c:if test="${applyDelaysinfo.delayStage=='final_paper'}">
														提交论文定稿
													</c:if>
												</td>
												<td>${applyDelaysinfo.dateTimeStr}</td>
												<td>
													<button class="btn btn-default" name="delayReason"
														type="button" data-toggle="modal" data-target="#myModal"
														yanqi="${applyDelaysinfo.delayReason}">查看</button> 
												<a	class="btn btn-default" href="<%=basePath%>guideTeacher/checkStuDelay_opt.shtm?ids=${applyDelaysinfo.id}&status=1">通过</a>
												<button type="button" name="reqback" delayId="${applyDelaysinfo.id}"class="btn btn-primary" data-toggle="modal" data-target="#reqback">驳回</button>
												</td>
												<td><c:if test="${applyDelaysinfo.teaStatus==0}">未审核</c:if>
												<c:if test="${applyDelaysinfo.teaStatus==1}">通过</c:if>
												<c:if test="${applyDelaysinfo.teaStatus==2}">未通过</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${applyDelaysinfo==null||fn:length(applyDelaysinfo)==0}">
									<tr>
										<td align="center" colspan="7">暂无该条件下的延期申请！</td>
									</tr>
								</c:if>
									
								</tbody>
							</table>
						</div>
						
						<div class="panel-footer">
							<button class="btn btn-primary" type="button" id="check_all">全选</button>
							<button type="submit" name="status" value="1"
							class="btn btn-primary alert-success">通过</button>
							<button name="status" value="2"
							class="btn btn-primary alert-danger" >驳回</button>
						</div>
					</div>
				</form>
					<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"></h4>
				</div>
				<div class="modal-body" id="myModalReason">
					<p></p>
				</div>				     
			</div>
		</div>
	</div>
	<!--/modal-->
	
	<!-- Modal -->
	<div class="modal fade" id="reqback" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">驳回理由</h4>
				</div>
				<div class="modal-body">
				<input hidden="hidden" type="text" id="delayId"/>
				
					<p><input id="teaIdea" type="text" class="form-control" name="teaIdea"
								value=""  placeholder="（选填）驳回理由" /></p>
				</div>
				
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" name="saveOpt" class="btn btn-primary">确定</button>
				</div>
							     
			</div>
		</div>
	</div>
	<!--/modal-->
								
			</div>
		</div>
	</div>
	<!--modal调用-->
<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$('button[name="delayReason"]').click(function() {
				var delayInfo = $(this).attr("yanqi");//从自定义属性延期中获取延期信息
				if (delayInfo == '') {
					delayInfo = '该生没有填写延期理由';
				}
				$('#myModalReason').html('延期原因');//填充modal的标题
				$('#myModalLabel').html(delayInfo);//把延期内容填充到modal_body中
				$('#myModal').modal('toggle');//激活modal	
				return false;			
			});
		});
		
		$(function() {
			$('button[name="reqback"]').click(function() {
				var delayId = $(this).attr("delayId");//从自定义属性延期中获取延期id
				$('#delayId').val(delayId);//
				$('#reqback').modal('toggle');//激活modal	
				return false;			
			});
		});
		
			$('button[name="saveOpt"]').click(function() {
				var delayId = $('#delayId').val();//从modal中取出delayId
				var teaIdea = $('#teaIdea').val();//从modal中取出teaIdea
				window.location.href = "checkStuDelay_opt.shtm?ids=" + delayId + "&status=2" +"&teaIdea="+teaIdea;
			});
	</script>
	<!--/modal调用-->

	<!-- 全选js-->
	<script type="text/javascript">
		var a = 1;
		$('#check_all').click(function() {
			if (a == 1) {
				$('[name="ids"]:checkbox').prop('checked', true);
				a--;
			} else {
				$('[name="ids"]:checkbox').attr('checked', false);
				a++;
			}
		})
	</script>
	<!--/全选js-->

</body>

</html>