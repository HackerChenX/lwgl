<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@page import="com.hlzt.commons.helper.SysConfig"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
		<title></title>
		<style>
			a{
				color: #000000;
				text-decoration: none
			}
			a:hover{
				 text-decoration: none
			}
		</style>
	</head>

	<body>
		<div class="container" style="margin-top: 50px;">
			<div class="row">
				<div class="col-md-5">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-bullhorn fa-lg"></span>&nbsp;&nbsp;通知公告</div>
						<div class="panel-body">
						<c:if test="${noticeList!=null&&fn:length(noticeList)!=0}">
							<c:forEach var="publicNotice" items="${noticeList}">
							<a href="<%=basePath%>notice.chtm?id=${publicNotice.id}" target="_Blank">
							<c:choose>
								<c:when test="${publicNotice.top==1}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${publicNotice.title }&nbsp;&nbsp;<span class="fa fa-bookmark fa-lg"></span></p>
								</c:when>
								<c:otherwise>
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${publicNotice.title }</p>
								</c:otherwise>
							</c:choose>
							</a>
							</c:forEach>
						</c:if>
							<c:if test="${noticeList==null||fn:length(noticeList)==0}">
							<a>
								<p align="center">暂无公告！</p>
							</a>
							</c:if>
						</div>
					</div>
				</div>
				<div class="col-md-5 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-bar-chart-o fa-lg"></span>&nbsp;&nbsp;表格下载</div>
						<div class="panel-body">
						<c:if test="${tableList!=null&&fn:length(tableList)!=0}">
							<c:forEach var="publicNotice" items="${tableList}">
							<a href="<%=basePath%>downloadFile.chtm?filePath=${publicNotice.filePath }">
								<c:choose>
								<c:when test="${publicNotice.top==1}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${publicNotice.title}&nbsp;&nbsp;<span class="fa fa-bookmark fa-lg"></span></p>
								</c:when>
								<c:otherwise>
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${publicNotice.title}</p>
								</c:otherwise>
							</c:choose>
							</a>
							</c:forEach>
						</c:if>
							<c:if test="${tableList==null||fn:length(tableList)==0}">
							<a>
								<p align="center">暂无表格！</p>
							</a>
							</c:if>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 50px;">
				<div class="col-md-5">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-leaf fa-lg"></span>&nbsp;&nbsp;优秀论文展示</div>
						<div class="panel-body">
						<c:if test="${paperList!=null&&fn:length(paperList)!=0}">
							<c:forEach var="publicNotice" items="${paperList}">
							<a href="<%=basePath%>downloadFile.chtm?filePath=${publicNotice.filePath }">
								<c:choose>
									<c:when test="${publicNotice.top==1}">
										<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${publicNotice.title}&nbsp;&nbsp;<span class="fa fa-bookmark fa-lg"></span></p>
									</c:when>
									<c:otherwise>
										<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${publicNotice.title}</p>
									</c:otherwise>
							    </c:choose>
							</a>
							</c:forEach>
						</c:if>
							<c:if test="${paperList==null||fn:length(paperList)==0}">
							<a>
								<p align="center">暂无优秀论文！</p>
							</a>
							</c:if>
						</div>
					</div>
				</div>
				<div class="col-md-5 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-comments-o fa-lg"></span>&nbsp;&nbsp;待办事项</div>
						<div class="panel-body">
							<c:if test="${backLogList!=null&&fn:length(backLogList)!=0}">
							<c:forEach var="BackLog" items="${backLogList}">
								<c:if test="${BackLog.backLog=='stuApplyTitle'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${BackLog.numbers}条学生课题申请待审核&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>				
								<c:if test="${BackLog.backLog=='stuApplySelfTitle'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${BackLog.numbers}条学生课题申请待审核&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='taskBook'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${BackLog.numbers}份任务书待审核&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='openingReport'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${BackLog.numbers}份开题报告待审核&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='midCheck'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${BackLog.numbers}份中期检查待审核&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='firstPaper'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${BackLog.numbers}份论文初稿待审核&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='finalPaper'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${BackLog.numbers}份论文定稿待审核&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='teaApplyTitle'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${BackLog.numbers}条教师课题申请待审核&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='delayApply'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;${BackLog.numbers}条延期申请待审核&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='allFile'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;新提交${BackLog.numbers}条课题文件&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='reStuApplyTitle'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;课题申请状态更新&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='reTeaApplyTitle'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;课题申请状态更新&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='reTaskBook'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;任务书状态更新&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='reOpeningReport'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;开题报告状态更新&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='reMidCheck'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;中期检查状态更新&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='reFirstPaper'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;论文初稿状态更新&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='reFinalPaper'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;论文定稿状态更新&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
								<c:if test="${BackLog.backLog=='reDelayApply'}">
									<p><span class="fa fa-paw"></span>&nbsp;&nbsp;延期申请状态更新&nbsp;&nbsp;<a href="<%=basePath%>removeBackLog.shtm?id=${BackLog.id}&baseUrl=${baseUrl}"><span class="fa fa-trash-o" ></span></a></p>
								</c:if>
							</c:forEach>
						</c:if>
							<c:if test="${backLogList==null||fn:length(backLogList)==0}">
							<a>
								<p align="center">暂无待办事项！</p>
							</a>
							</c:if>			
						</div>
					</div>
				</div>
			</div>
		</div>		
	</body>

</html>