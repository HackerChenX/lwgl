<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<title>审核开题报告</title>
	</head>

	<body>
		<style type="text/css">
		th{
			text-align:center;
		}
		</style>
		<script type="text/javascript">
			window.onload=function(){
				/*刷新页面下拉框值不变*/	
		   		if(document.getElementById("selHid").value==""){
		   			document.getElementById("sel").value=3;
		   		}else{
		   			document.getElementById("sel").value=document.getElementById("selHid").value;
		   		}
				/*弹窗提示异常消息*/
		   		var error = $("#error").val();
		   		if(!(error==''|| error==undefined || error==null)){
				$(".modal-title").html("错误提示");
				$(".modal-body").html(error);
				$("#mymodal-error").modal("toggle");
				}
		   		
		   		/*弹窗提示成功消息*/
		   		var success = $("#success").val();
		   		if(!(success==''|| success==undefined || success==null)){
				$(".modal-title").html("提示");
				$(".modal-body").html(success);
				$("#mymodal-error").modal("toggle"); 
				}
	
			}	
		</script>
		<!--下面是代码任务部分-->
		
			<div class="row" style="margin-top: 50px;" id="row1">
				<div class="col-xs-12 col-md-11 col-md-offset-1">
					
						<div class="panel panel-primary">
							
							<div class="panel-body">
								<form class="form-inline" action="<%=basePath %>majorLeader/findOpeningReport.shtm" method="post" id="select">
								<label class="control-label">学号：</label><input type="text" name="stuNum" value="${stuNum}" class="form-control" id="stu_id" placeholder="学生学号" />
								<label class="control-label">姓名：</label><input type="text" name="stuName" value="${stuName}" class="form-control" id="stu_name" placeholder="学生姓名" />
								<label class="control-label">状态:</label>
								<input type="hidden" value="${status}" id="selHid">
								<select class="form-control" name="status" id="sel">
									<option value="0">未审核</option>
									<option value="1">已通过</option>
									<option value="2">未通过</option>
									<option value="3">查询全部</option>
								</select>
								<input name="pageNo" type="hidden" value="${page.pageNo}" id="pageNow" /><!-- /记录当前页 -->
								<input type="hidden" value="${page.pageSize}" name="pageSize" id="pageSize"><!-- /记录每页的大小 -->
								<button class="btn btn-primary" type="submit" id="search"><span class="fa fa-search">搜索</span></button>
								</form>
							
							</div>

						</div>
					
				</div>
			</div>
			<!--row_1 ↑↑↑↑-->
			<div class="row" id="row2">
				<div class="col-xs-12 col-md-11 col-md-offset-1">
					<form action="<%=basePath %>majorLeader/findOpeningReport_checkOpeningReport.shtm" method="post">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;审阅开题报告
							</div>
							<div class=" table-responsive">
								<table style="text-align:center" class="table table-bordered table-hover">
									<thead>
										<tr class="tr">
											<th>选择</th>
											<th>姓名</th>
											<th>性别</th>
											<th>学号</th>
											<th>课题名</th>
											<th>指导老师</th>				
											<th>审核状态</th>
											<th>开题报告</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach var="stuStageFile" items="${page.results}">
										<tr>
											<td><input type="checkbox" name="ids" value="${stuStageFile.id}"></td>
											<td>${stuStageFile.stuName}</td>
											<td>${stuStageFile.sex}</td>
											<td>${stuStageFile.stuNum}</td>
											<td>${stuStageFile.title}</td>
											<td>${stuStageFile.teaName}</td>
											<td>
												<c:if test="${stuStageFile.status==0}">
												待审核
												</c:if>
												<c:if test="${stuStageFile.status==1}">
												已通过
												</c:if>
												<c:if test="${stuStageFile.status==2}">
												未通过
												</c:if>
											</td>
											<td>
												<a class="btn btn-primary" href="<%=basePath %>downloadFile.chtm?filePath=${stuStageFile.openingReport}"  target="_blank">下载</a>&nbsp;&nbsp;&nbsp;
												<a class="btn btn-primary" href="<%=basePath %>stagePaper/yulan.shtm?path=${stuStageFile.openingReport}" target="_blank">预览</a>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="panel-footer">
							
						<div class="row" style="padding-left:15px">					
								<input type="button" class="btn btn-default" id="check_all" value="全选"></input>&nbsp;&nbsp;
								<input type="hidden" name="stuNum" value="${stuNum}">
								<input type="hidden" name="stuName" value="${stuName}">
								<input type="hidden" name="titleName" value="${titleName}">
								<input type="hidden" name="findStatus" value="${status}">
								<input type="hidden" name="status" value="" id="cs">
								<script type="text/javascript">
									function pass(){
										document.getElementById("cs").value = 1;
									}
									function notpass(){
										document.getElementById("cs").value = 2;
									}
								</script>
								<button type="submit" onclick="pass()" class="btn btn-primary alert-success">通过</button>&nbsp;&nbsp;
								<button type="submit" onclick="notpass()" class="btn btn-primary alert-danger">退回</button>&nbsp;&nbsp;
								<input type="hidden" value="${errorMsg}" id="error">
								<input type="hidden" value="${successMsg}" id="success">
								
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
		<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
		<script>
			var a = 1;
			$('#check_all').click(function() {
				if(a == 1) {
					$('[name=ids]:checkbox').prop('checked', true);
					a--;
				} else {
					$('[name=ids]:checkbox').attr('checked', false);
					a++;
				}
			})
		</script>
	</body>

</html>