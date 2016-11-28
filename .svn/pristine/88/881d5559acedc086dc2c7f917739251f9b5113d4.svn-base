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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1">
<!--<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">-->
<title></title>
</head>
<body style="font-family: '微软雅黑'; font-size: 17px;">
<c:if test="students!=null">students</c:if>
		<div class="row" style="margin-top: 100px;">
			<div class="col-xs-12 col-md-10 col-md-offset-1">
				<form role="form" action="##" method="post">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;答辩组安排
						</div>
						<div class="table-responsive">
							<table class="table table-bordered table-striped table-hover"
								style="text-align: center;">
								<thead>
									<tr>
										<th>小组编号</th>
										<th>小组名称</th>
										<th>答辩地点</th>
										<th>答辩时间</th>
										<th>答辩组长</th>
										<th>答辩秘书</th>
										<th>答辩学生</th>
										<th>答辩组员</th>
									</tr>
									
								</thead>
								<tbody>			
									<c:if test="${dbGroups!=null}">
										<c:forEach var="dbGroups" items="${dbGroups}">
											<tr>
												<td>${dbGroups.groupNum}</td>
												<td>${dbGroups.groupName}</td>
												<td>${dbGroups.groupSite}</td>
												<td>${dbGroups.dateTimeStr}</td>
												<td>${dbGroups.groupLeaderName}</td>
												<td>${dbGroups.groupSecretaryName}</td>
												<td>
													<!-- Student Modal -->
													<div class="modal fade" id="dbStudent" tabindex="-1"
														role="dialog" aria-labelledby="myModalLabel"
														aria-hidden="true">
														<div class="modal-dialog" style="width:800px">
															<div class="modal-content">
																<div class="modal-header">
																	<button type="button" class="close"
																		data-dismiss="modal">
																		<span aria-hidden="true">&times;</span><span
																			class="sr-only">Close</span>
																	</button>
																	<h4 class="modal-title" id="myModalLabel">答辩学生信息</h4>
																</div>
																<div class="modal-body">
																	<div class="table-responsive">
																		<table
																			class="table table-bordered table-hover table-striped">
																			<thead>
																				<tr class="tr">
																					<th>姓名</th>
																					<th>性别</th>
																					<th>学号</th>
																					<th>课题名</th>
																					<th>班级</th>
																					<th>电话</th>
																					<th>邮箱</th>
																				</tr>
																			</thead>
																			<c:if test="${students!=null}">
																				<c:forEach var="students" items="${students}">

																					<tbody>
																						<tr>
																							<td>${students.stuName }</td>
																							<td>${students.sex}</td>
																							<td>${students.userNum }</td>
																							<td>${students.title }</a></td>
																							<td>${students.stuClass }</td>
																							<td>${students.tel}</td>
																							<td>${students.mail }</td>
																					</tbody>
																				</c:forEach>
																			</c:if>

																		</table>
																	</div>
																</div>
															</div>
														</div>
													</div> <!--/modal-->



													<button class="btn btn-default" name="dbStudent"
														type="button" data-toggle="modal" data-target="#dbStudent">查看</button>
												</td>
												<td>
												<!-- modal教师 -->
													<div class="modal fade" id="dbTeacher" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"aria-hidden="true">
														<div class="modal-dialog" style="width:800px">
															<div class="modal-content">
																<div class="modal-header">
																	<button type="button" class="close"
																		data-dismiss="modal">
																		<span aria-hidden="true">&times;</span><span
																			class="sr-only">Close</span>
																	</button>
																	<h4 class="modal-title" id="myModalLabel">小组成员信息</h4>
																</div>
																<div class="modal-body">
																	<div class="table-responsive">
																		<table
																			class="table table-bordered table-hover table-striped" style="text-align:center">
																			<thead>
																				<tr class="tr">
																					<th>姓名</th>
																					<th>性别</th>
																					<th>工号</th>																		
																					<th>电话</th>
																					<th>邮箱</th>
																				</tr>
																			</thead>
																			<c:if test="${teachers!=null}">
																				<c:forEach var="teachers" items="${teachers}">
																					<tbody>
																						<tr>
																							<td>${teachers.teaName }</td>
																							<td>${teachers.sex}</td>
																							<td>${teachers.teaNum }</td>																							
																							<td>${teachers.tel}</td>
																							<td>${teachers.mail}</td>
																					</tbody>
																				</c:forEach>
																			</c:if>
																		</table>
																	</div>
																</div>
															</div>
														</div>
													</div> <!--/modal-->
												
												 <!-- /modal教师 -->
													<button name="dbTeacher" class="btn btn-default" type="button"
														data-toggle="modal" data-target="#dbTeacher">查看</button>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>						
					</div>
				</form>
			</div>
		</div>
</body>

</html>