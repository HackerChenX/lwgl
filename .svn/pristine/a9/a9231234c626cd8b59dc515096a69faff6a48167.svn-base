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
		<title>答辩小组</title>
	</head>

	<body>
	<style type="text/css">
	th{
		text-align:center;
	}
	</style>
		<!--下面是代码任务部分-->
		<div class="row" style="margin-top:50px ;">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<form>
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;答辩小组
						</div>
						<div class=" table-responsive">
							<table style="text-align:center" class="table table-bordered table-hover">
								<thead>
									<tr class="tr">
										<th>小组编号</th>
										<th>小组名</th>
										<th>组长</th>
										<th>成员</th>
										<th>答辩秘书</th>
										<th>安排答辩学生</th>
										<th>答辩地点</th>
										<th>答辩时间</th>
										<th>修改答辩小组</th>
										<th>删除答辩小组</th>							
									</tr>
								</thead>
								<tbody>
								
									<c:forEach var="dbGroup" items="${list}">
									<tr>
										<td>${dbGroup.groupNum }</td>
										<td>${dbGroup.groupName }</td>
										<td>${dbGroup.groupLeaderName }</td>
										<td>
											<a href="<%=basePath %>majorLeader/findDbGroup_jumpSetDbGroupForTea.shtm?dbGroupId=${dbGroup.id}&flag=memberTea">
												<c:if test="${dbGroup.groupMemberName==''||dbGroup.groupMemberName==null}">
													设置组员
												</c:if>
												${dbGroup.groupMemberName }
											</a>
										</td>
										<td>
											<a href="<%=basePath %>majorLeader/findDbGroup_jumpSetDbGroupForTea.shtm?dbGroupId=${dbGroup.id}&flag=secretaryTea">
												<c:if test="${dbGroup.groupSecretaryName==''||dbGroup.groupSecretaryName==null}">
													设置答辩秘书
												</c:if>
												${dbGroup.groupSecretaryName }
											</a>
										</td>
										<td>					
											<a href="<%=basePath %>majorLeader/findDbGroup_jumpSetDbGroupForStu.shtm?dbGroupId=${dbGroup.id}">
												<c:choose>
													<c:when test="${dbGroup.groupStuNum==0}">
														设置答辩学生
													</c:when>
													<c:otherwise>
														${dbGroup.groupStuNum}
													</c:otherwise>
												</c:choose>
											</a>
										</td>
										<td>${dbGroup.groupSite }</a></td>
										<td>${dbGroup.dateTimeStr }</a></td>
										<td>
											<a href="<%=basePath %>majorLeader/findDbGroup_jumpSetDbGroup.shtm?id=${dbGroup.id}">修改</a>
										</td>

										<td>
											<a href="<%=basePath %>majorLeader/findDbGroup_deleteDbGroup.shtm?id=${dbGroup.id}">删除</a>
										</td>

									</tr>
									</c:forEach>
									
								</tbody>
							</table>
						</div>
						<div class="panel-footer">
							<a class="btn btn-primary" href="<%=basePath %>majorLeader/findDbGroup_jumpSetDbGroup.shtm" id="check_all">添加新的答辩小组</a>&nbsp;&nbsp;

						</div>

					</div>
				</form>

			</div>
		</div>
	
	</body>

</html>