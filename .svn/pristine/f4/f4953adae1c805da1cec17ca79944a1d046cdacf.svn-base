<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width,initial-scale=1">	
		<title></title>
	</head>

	<body>
		<div style="margin-top: 50px;">	
		<div class="row">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<form role="form" action="<%=basePath %>secretary/exportGradeAllEvaluate.shtm" method="post">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;成绩总评
						</div>
						<div class="table-responsive">
							<table class="table table-bordered table-striped table-hover" style="text-align: center;">
								<thead>
									<tr>
										<th>专业名称</th>										
										<th>≥90分</th>
										<th>80-89分</th>
										<th>70-79分</th>
										<th>60-69分</th>
										<th><60分</th>
										<th>专业学生数</th>
										<th>有效学生数</th>
									</tr>
								</thead>
								<tbody>
								<c:if test="${list!=null}">
									<c:forEach var="gradeAll" items="${list}">									
									<tr>
										<th>${gradeAll.majorName }</th>																			
										<td>${gradeAll.excellentNum }</td>
										<td>${gradeAll.wellNum }</td>
										<td>${gradeAll.mediumNum }</td>
										<td>${gradeAll.passNum }</td>
										<td>${gradeAll.notPassNum }</td>										
										<td>${gradeAll.majorStuNum }</td>
										<td>${gradeAll.majorValidStuNum }</td>
									</tr>
									<tr>
										<td>百分比</td>										
										<td>${gradeAll.excellentRates }</td>
										<td>${gradeAll.wellRates }</td>
										<td>${gradeAll.mediumRates }</td>
										<td>${gradeAll.passRates }</td>
										<td>${gradeAll.notPassRates}</td>
										<td></td>
										<td></td>
									</tr>
									</c:forEach>
								</c:if>
								</tbody>
							</table>
						</div>						
						<div class="panel-footer">
							<button class="btn btn-primary" type="submit">导出</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		
	  </div>
	</body>

</html>

