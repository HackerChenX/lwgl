<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
		<title>教师所带学生数分配</title>
	</head>

	<body>
		<script type="text/javascript">
			window.onload=function(){
				/*刷新页面下拉框值不变*/	
		   		if(document.getElementById("selHid").value==""){
		   			document.getElementById("sel").value=0;
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
						<form class="form-inline" action="<%=basePath %>majorLeader/findTeacher_setAllTeaStuNum.shtm">
							<label class="control-label">教师所带学生数：</label>
							<input type="text" class="form-control" id="teacher_id" placeholder="人数" name="number" />
							<input type="submit" class="btn btn-primary" id="#" value="全部设置" />
						</form>
					</div>

				</div>

			</div>
		</div>

		<div class="row">
			<div class="col-xs-12 col-md-11 col-md-offset-1">

				<div class="panel panel-primary">

					<div class="panel-body">
						<form class="form-inline" action="<%=basePath %>majorLeader/findTeacher.shtm" method="post">
							<label class="control-label">姓名：</label><input type="text" class="form-control" name="teaName" value="${teaName}" placeholder="教师姓名" />&nbsp;&nbsp;&nbsp;&nbsp;
							<label class="control-label">职称:</label>
							<input type="hidden" value="${zhicheng}" id="selHid">
							<select class="form-control" name="zhicheng" id="sel">
								<option value="0">请选择</option>
								<c:forEach var="ZhiCheng" items="${zhiChengList}">
								<option value="${ZhiCheng.zhiCheng}">${ZhiCheng.zhiCheng}</option>
								</c:forEach>
							</select>
							<button class="btn btn-primary" type="submit" id="search"><span class="fa fa-search">搜索</span></button>
						</form>
					</div>

				</div>

			</div>
		</div>
		<!--row_1 ↑↑↑↑-->
		<div class="row" id="row2">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<form class="form-inline" action="<%=basePath %>majorLeader/findTeacher_setTeaStuNum.shtm" method="post">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;教师所带学生数分配
						</div>
						<div class=" table-responsive">
							<table style="text-align:center" class="table table-bordered table-hover">
								<thead>
									<tr class="tr">
										<th>选择</th>
										<th>姓名</th>
										<th>职称</th>
										<th>专业</th>
										<th>所带学生数</th>
										<th>电话</th>
										<th>邮箱</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="teacher" items="${list}">
									<tr >
										<td><input type="checkbox" name="ids" value="${teacher.userId}"></td>
										<td>${teacher.teaName}</td>
										<td>${teacher.zhicheng}</td>
										<td>${teacher.major}</td>
										<td>${teacher.allStunum}</td>
										<td>${teacher.tel}</td>
										<td>${teacher.mail}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="panel-footer">
							<input type="button" class="btn btn-default" id="check_all" value="全选"></input>&nbsp;&nbsp;
							<label class="control-label">教师所带学生数：</label><input type="text" name="number" class="form-control" id="teacher_id" placeholder="人数" />
							<input type="hidden" name="teaNum" value="${teaNum}">
							<input type="hidden" name="teaName" value="${teaName}">
							<input type="hidden" name="zhicheng" value="${zhicheng}">
							<button type="submit" class="btn btn-primary alert-success">设置</button>&nbsp;&nbsp;
							<input type="hidden" value="${errorMsg}" id="error">
							<input type="hidden" value="${successMsg}" id="success">
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
		
		<script type="text/javascript">
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