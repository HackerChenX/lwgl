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
		<title></title>
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
		   			document.getElementById("sel").value=0;
		   		}else{
		   			document.getElementById("sel").value=document.getElementById("selHid").value;
		   		}
				$(document).ready(function(){	
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
				});
	
			}	
		</script>
		<div class="row" style="margin-top: 50px;">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form role="form" class="form-inline" method="post" action="<%=basePath%>majorLeader/findStuFinalGrade.shtm" id="select">			
						<div class="form-group">
							<label>班级：</label>
							<input type="hidden" value="${className}" id="selHid">
							<select class="form-control input-sm" type="text" name="className" id="sel">
								<option value="0">全部班级</option>
								<c:forEach var="className" items="${cnList}">
								<option value="${className.cName }">${className.cName }</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label>指导教师姓名：</label>
							<input type="text" name="zdTeaName" value="${zdTeaName }" class="form-control input-sm" />
						</div>
						<div class="form-group">
							<label>学生姓名：</label>
							<input type="text" name="stuName" value="${stuName }" class="form-control input-sm" />
						</div>

						<div class="form-group">
							<label>学生学号：</label>
							<input type="text" name="stuNum" value="${stuNum }" class="form-control input-sm" />
						</div>
						<input name="pageNo"  type="hidden"  value="${page.pageNo}"   id="pageNow" />
						<input name="pageSize"type="hidden"  value="${page.pageSize}" id="pageSize">
						<button class="btn btn-primary" type="submit" id="keyWordSelect">查询</button>
					</form>
				</div>
			</div>
			</div>	
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<form role="form" action="<%=basePath %>majorLeader/exportStuFinalGrade.shtm" method="post">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;成绩汇总查询
						</div>
						<div class="table-responsive">
							<table style="text-align:center" class="table table-bordered table-striped table-hover">
								<thead>
									<tr>
										<th>学号</th>
										<th>姓名</th>
										<th>性别</th>
										<th>专业名称</th>
										<th>班级</th>
										<th>课题名</th>
										<th>审阅成绩</th>
										<th>评阅成绩</th>
										<th>答辩成绩</th>
										<th>最终成绩</th>
										<th>评价</th>
										<th>指导老师</th>
										<th>评阅老师</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="grade" items="${page.results}">
									<tr>
										<td>${grade.stuNum }</td>
										<td>${grade.stuName }</td>
										<td>${grade.stuSex }</td>
										<td>${grade.stuMajor }</td>
										<td>${grade.stuClass }</td>
										<td>${grade.title }</td>
										<td>${grade.syGrade }</td>
										<td>${grade.pyGrade }</td>
										<td>${grade.dbGrade }</td>
										<td>${grade.finalGrade }</td>
										<td>${grade.evaluate }</td>
										<td>${grade.zdTeacherName }</td>
										<td>${grade.pyTeacherName }</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="panel-footer">				
							<input type="hidden" value="${className }" name="className">
							<input type="hidden" value="${zdTeaName }" name="zdTeaName">
							<input type="hidden" value="${stuName }" name="stuName">
							<input type="hidden" value="${stuNum }" name="stuNum">
						<div class="row" style="padding-left:15px">
							<div class="col-xs-5 col-md-3 ">
								<button type="submit" class="btn btn-primary">导出</button>
							</div>

							<div class="col-xs-8 col-md-8 "
									style="float: right; text-align:right;">
									<script type="text/javascript" src="<%=basePath%>js/page.js"></script>
									<c:if test="${page.isAjax==0}">
										<c:if test="${page.pageNo-1>0 }">
											<a href="javascript:upPage()" class="btn btn-default btn-sm">&laquo;上一页</a>
										</c:if>

										<a class="btn btn-default btn-sm">第${page.pageNo }页</a>

										<c:if test="${page.pageNo+1<=page.totalPage }">
											<a href="javascript:nextPage()"
												class="btn btn-default btn-sm">下一页&raquo;</a>
										</c:if>
										&nbsp;&nbsp;共${page.totalRecord }条&nbsp;共${page.totalPage}页 &nbsp;&nbsp;跳转到&nbsp;
										<input type="text" value="" id="zc_tz_text"
											style="width:28px;height:28px" />&nbsp;页<input type="button"
											value="跳转" id="zc_tz_button" class="btn btn-default btn-sm"
											onclick="zcTzClick(${page.totalPage})" /> &nbsp;&nbsp;每页&nbsp;<input
											type="text" value="" id="page_size_text"
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
	</body>
</html>