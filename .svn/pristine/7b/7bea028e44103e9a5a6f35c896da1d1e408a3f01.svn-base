<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title></title>
</head>

<body>
	<script type="text/javascript">
			window.onload=function(){
			/*刷新页面下拉框值不变*/	
		   		if(document.getElementById("statusHid").value==""){
		   			document.getElementById("status").value="3";
		   		}else{
		   			document.getElementById("status").value=document.getElementById("statusHid").value;
		   		}
			}
		</script>
	<div class="row" style="margin-top: 50px;" id="row1">
		<div class="col-xs-12 col-md-11 col-md-offset-1">
			<form class="form-inline"
				action="<%=basePath %>secretary/findSpecialInit_findSpecial.shtm"
				method="post" id="select">
				<div class="panel panel-primary">
					<div class="panel-body">
						<label class="control-label">学号：</label><input type="text"
							class="form-control" name="stuNum" id="stu_id" placeholder="学生学号" />
						<label class="control-label">姓名：</label><input type="text"
							class="form-control" name="stuName" id="stu_name"
							placeholder="学生姓名" /> <label class="control-label">导师：</label><input
							type="text" class="form-control" name="teaName" id="stu_id"
							placeholder="导师姓名" /> <label class="control-label">状态:</label> <input
							type="hidden" value="${status}" id="statusHid"> <select
							class="form-control" name="status" id="status">
							<option value="3">查询全部</option>
							<option value="0">未审核</option>
							<option value="1">已通过</option>
							<option value="2">未通过</option>
						</select> <input type="hidden" value="${page.pageNo}" name="pageNo"
							id="pageNow">
						<button class="btn btn-primary" type="submit" id="search">
							<span class="fa fa-search">搜索</span>
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 col-md-11 col-md-offset-1">
			<form method="post"
				action="<%=basePath %>secretary/findSpecialInit_setSpecial.shtm">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;学生延期申请
					</div>
					<div class="table-responsive">
						<table  style="text-align:center" class="table table-bordered table-hover text-center">
							<thead>
								<tr>
									<th>选择</th>
									<th>学号</th>
									<th>姓名</th>
									<th>导师</th>
									<th>延期类型</th>
									<th>延期时间至</th>
									<th class="text-center">操作</th>
									<th>状态</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="applyDelay" items="${page.results}">
									<tr>
										<td><input type="checkbox" name="ids"
											value="${applyDelay.id}">
										</td>
										<td>${applyDelay.stuNum}</td>
										<td>${applyDelay.stuName}</td>
										<td>${applyDelay.teaName}</td>
										<td>${applyDelay.delayStage}</td>
										<td>${applyDelay.delayTimeStr}</td>
										<td>
											<button class="btn btn-default" name="delayReason"
												type="button" data-toggle="modal"
												yanqi="${applyDelay.delayReason}">查看</button> <a
											class="btn btn-default"
											href="<%=basePath %>secretary/findSpecialInit_setSpecial.shtm?ids=${applyDelay.id}&status=1">通过</a>
											<button type="button" name="reqback"
												delayId="${applyDelay.id}" class="btn btn-primary"
												data-toggle="modal" data-target="#reqback">驳回</button></td>
										<td><c:if test="${applyDelay.managerStatus=='0'}">
														未审核
													</c:if> <c:if test="${applyDelay.managerStatus=='1'}">
														通过
													</c:if> <c:if test="${applyDelay.managerStatus=='2'}">
														驳回
													</c:if></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>
					<!-- 延期原因Modal -->
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
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
					<div class="panel-footer">
						<input type="hidden" value="${stuNum}" name="stuNum"> <input
							type="hidden" value="${page.pageNo}" name="pageNo" id="pageNow"><!-- 记录当前页 -->
						<input type="hidden" value="${stuName}" name="stuName"> <input
							type="hidden" value="${zdTeaName}" name="zdTeaName"> <input
							type="hidden" value="${status}" name="findStatus">
							<div class="row" style="padding-left:15px">
							
								<button class="btn btn-primary" type="button" id="check_all">全选</button>
								<button class="btn btn-primary" type="submit" name="status"
									value="1">通过</button>
								<button class="btn btn-primary" name="status" value="2"
									type="submit">驳回</button>						
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
		</div>
	</div>


	<!-- Modal -->
	<div class="modal fade" id="reqback" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">驳回理由</h4>
				</div>
				<div class="modal-body">
					<input hidden="hidden" type="text" id="delayId" />

					<p>
						<input id="idea" type="text" class="form-control" name="idea"
							value="" placeholder="（选填）驳回理由" />
					</p>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" name="saveOpt" class="btn btn-primary">确定</button>
				</div>
				     
			</div>
		</div>
	</div>
	<!--/modal-->

	<!--modal调用-->
	<script type="text/javascript">
		$(function() {
			$('button[name="delayReason"]').click(function() {
				var delayInfo = $(this).attr("yanqi");//从自定义属性延期中获取延期信息
				if (delayInfo == '') {
					delayInfo = '该生没有填写延期理由';
				}
				$('#myModalLabel').html('延期原因');//填充modal的标题
				$('#myModalReason').html(delayInfo);//把延期内容填充到modal_body中
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
				var idea = $('#idea').val();//从modal中取出teaIdea
				window.location.href = "secretary/findSpecialInit_setSpecial.shtm?ids=" + delayId + "&status=2" +"&idea="+idea;
			});
	</script>
	<!--/modal调用-->

	<!-- 全选js-->
	<script type="text/javascript">
			var a = 1;
			$('#check_all').click(function() {
				if(a == 1) {
					$('[name="ids"]:checkbox').prop('checked', true);
					a--;
				} else {
					$('[name="ids"]:checkbox').attr('checked', false);
					a++;
				}
			})
		</script>
	<!--全选over-->

</body>

</html>