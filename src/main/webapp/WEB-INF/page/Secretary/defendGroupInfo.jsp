<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<title></title>
	</head>

	<body style="font-family: '微软雅黑'; font-size: 17px;">
		<script type="text/javascript">
			window.onload=function(){
				/*刷新页面下拉框值不变*/	
		   		if(document.getElementById("majorHid").value==""){
		   			document.getElementById("major").value=0;
		   		}else{
		   			document.getElementById("major").value=document.getElementById("majorHid").value;
		   		}
		   	}
		</script>
			<div class="col-xs-12 col-md-11 col-md-offset-1">
			<div class="row" style="margin-top: 50px;">
				<div class="panel panel-primary">
					<div class="panel-body">
						<form role="form" class="form-inline" action="<%=basePath %>secretary/findDbGroup.shtm" method="post" id="select">
							<div class="form-group">
								<label class="control-label">专业：</label>
								<input type="hidden" value="${major}" id="majorHid">
								<select class="form-control input-sm" type="text" name="major" id="major" onchange="changeMajor()">
									<option value="0">全部专业</option>
									<c:forEach var="major" items="${majorList}">
									<option value="${major.majorName}">${major.majorName}</option>
									</c:forEach>
								</select>
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
					<form role="form" action="<%=basePath %>secretary/exportDbGroup.shtm" method="post">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;答辩组安排
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-striped table-hover" style="text-align: center;">
									<thead>
										<tr>
											<th>专业</th>
											<th>组名</th>
											<th>答辩组组长</th>
											<th>答辩组成员</th>
											<th>答辩秘书</th>
											<th>答辩组学生</th>
											<th>答辩日期</th>
											<th>答辩地点</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="dbGroup" items="${page.results}">
										<tr>
											<td>${dbGroup.major }</td>
											<td>${dbGroup.groupName }</td>
											<td>${dbGroup.groupLeaderName }</td>
											<td>${dbGroup.groupMemberName }</td>
											<td>${dbGroup.groupSecretaryName }</td>
											<td>
												<a href="<%=basePath %>secretary/findDbGroup_findStuByDbgroup.shtm?dbGroupId=${dbGroup.id}">${dbGroup.groupStuNum }</a>
											</td>											
											<td>${dbGroup.dateTimeStr}</td>
											<td>${dbGroup.groupSite }</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="panel-footer">							
						<div class="row" style="padding-left:15px">							
								<button type="submit" class="btn btn-primary">导出</button>						
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
	</body>

</html>