<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width,initial-scale=1">

<!--CDN_CSS ↓↓↓↓-->
<!--<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://apps.bdimg.com/libs/fontawesome/4.4.0/css/font-awesome.min.css">-->
<!--CDN_CSS ↑↑↑↑-->
<title>审核学生课题</title>

</head>

<body style="margin-top:50px;">
	<div class="row" style="margin-top: 50px;" id="row1">
		<div class="col-xs-12 col-md-12">
			<form class="form-inline" method="post"
				action="<%=basePath%>guideTeacher/findStuTitle.shtm" id="select">
				<div class="panel panel-primary">
					<div class="panel-body">
						<label class="control-label">学号：</label> <input type="text"
							class="form-control" name="stuNum" value="${stuNum}" id="stu_id"
							placeholder="学生学号" /> <label class="control-label">姓名：</label> <input
							type="text" class="form-control" name="stuName"
							value="${stuName}" id="stu_name" placeholder="学生姓名" /> <label
							class="control-label">状态:</label> <select class="form-control"
							name="status">
							<option <c:if test="${status==3}">selected="selected"</c:if>
								value="3">全部</option>
							<option <c:if test="${status==0}">selected="selected"</c:if>
								value="0">未审核</option>
							<option <c:if test="${status==1}">selected="selected"</c:if>
								value="1">已通过</option>
							<option <c:if test="${status==2}">selected="selected"</c:if>
								value="2">未通过</option>
						</select>
						<input name="pageNo"  type="hidden"  value="${page.pageNo}"   id="pageNow" />
						<input name="pageSize" type="hidden"  value="${page.pageSize}" id="pageSize">
						<button class="btn btn-primary" type="submit" id="keyWordSelect">
							<span class="fa fa-search">搜索</span>
						</button>
					</div>

				</div>
			</form>
		</div>
	</div>
	<!--row_1 ↑↑↑↑-->
	<c:if test="${tea.allStunum!=0}">
		<div class="row">
			<div class="col-xs-12 col-md-12">
			<div class="alert alert-info">
				<p>可带学生数：${tea.allStunum}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前申请学生数：${tea.nowStunum}</p>
			</div>
			</div>
		</div>
	</c:if>
	<!--row_2 ↓↓↓↓-->
	<div class="row" id="row2">
				<div class="col-xs-12 col-md-12">
			<form class="form-horizontal"  method="post"
				action="<%=basePath%>guideTeacher/findStuTitle_checkStuTitile.shtm">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;审核学生课题
					</div>

					<div class=" table-responsive">
						<table style="text-align:center"
							class="table table-bordered table-hover">
							<thead>
								<tr class="tr">
									<th>选择</th>
									<th>姓名</th>
									<th>性别</th>
									<th>学号</th>
									<th>课题名</th>
									<th>选题方式</th>
									<th>专业</th>
									<th>电话</th>
									<th>邮箱</th>
									<th>审核状态</th>
									<th>审核课题</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${page!=null}">
									<c:forEach var="StuTitle" items="${page.results}">
										<tr>
											<td><input type="checkbox" name="ids"
												value="${StuTitle.applyTitleId}">
											</td>
											<td>${StuTitle.stuName }</td>
											<td>${StuTitle.sex}</td>
											<td>${StuTitle.userNum }</td>
											<td>${StuTitle.titleName}</td>
											<td>
											<c:if test="${StuTitle.titleSourse==1}">自拟课题</c:if>
											<c:if test="${StuTitle.titleSourse==0}">教师课题</c:if></td>
											<td>${StuTitle.major}</td>
											<td>${StuTitle.tel}</td>
											<td>${StuTitle.mail}</td>
											<td>
												<c:if test="${StuTitle.teaStatus==0}">
													未审核
												</c:if>
												<c:if test="${StuTitle.teaStatus==1}">
													已通过
												</c:if>
												<c:if test="${StuTitle.teaStatus==2}">
													已驳回
												</c:if>
											</td>
											<td><a
												href="<%=basePath %>guideTeacher/findStuTitle_shStuTitle/${StuTitle.applyTitleId}.shtm">审核</a>
											</td>
										</tr>
									</c:forEach>
								</c:if>

							</tbody>
						</table>
					</div>
					<div class="panel-footer">
					<div class="row" style="padding-left:15px"><!-- row -->		
						<input type="button" class="btn btn-default" id="check_all"
							value="全选"></input>&nbsp;&nbsp;
					
						<button type="submit" name="status" value="1"
							class="btn btn-primary alert-success">通过</button>
						&nbsp;&nbsp;
						<button name="status" value="2"
							class="btn btn-primary alert-danger">退回</button>
						&nbsp;&nbsp;															
							<div class="col-xs-8 col-md-8 "	style="float: right; text-align:right;">
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
	<script type="text/javascript"
		src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	<script>
		var a = 1;
		$('#check_all').click(function() {
			if (a == 1) {
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