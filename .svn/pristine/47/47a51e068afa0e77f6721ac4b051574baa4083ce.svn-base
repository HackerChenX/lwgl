<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html><head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<title></title>
	</head>

	<body>
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<div class="row" style="margin-top: 50px;">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form role="form" class="form-inline" method="post" action="<%=basePath %>secretary/findGradeWeight_setGradeWeight.shtm">
							<div class="alert alert-info" role="alert">
							<h4>批量设置权重：（评分权重未设置时，默认指导教师评分占0.25，评阅评分占0.25，答辩评分占0.5）</h4>
							</div>
							<div class="form-group">
								<label class="control-label">指导评分：</label>
								<input class="form-control input-sm" type="text" name="zdPingfen" id="guideScore"/>
							</div>
							<div class="form-group">
								<label class="control-label">评阅评分：</label>
								<input class="form-control input-sm " type="text" name="pyPingfen" id="reviewScore"/>
							</div>
							<div class="form-group">
								<label class="control-label">答辩评分：</label>
								<input class="form-control input-sm" type="text" name="dbPingfen" id="defenseScore"/>
							</div>
							<input type="hidden" value="0" name="flag">
							<button class="btn btn-primary " type="submit" name="weightSet" id="weightAllSet">全部设置</button>&nbsp;&nbsp;				
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="fa fa-check-square-o"></span>&nbsp;&nbsp;评分权重设置
						</div>
						<div class="table-responsive">
							<table class="table table-bordered table-striped table-hover" style="text-align: center;">
								<thead>
									<tr>
										<th>专业</th>
										<th>指导评分</th>
										<th>评阅评分</th>
										<th>答辩评分</th>
										<th>设置</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="gradeWeight" items="${list}">
									<form role="form" action="<%=basePath %>secretary/findGradeWeight_setGradeWeight.shtm" method="post">
									<tr>
										<td>
											${gradeWeight.major }
										</td>
										<td>
											<input class="form-control input-sm" name="zdPingfen" value="${gradeWeight.zdPingfen }" type="text" placeholder="0.25" ></input>
										</td>
										<td>
											<input class="form-control input-sm" name="pyPingfen" value="${gradeWeight.pyPingfen }" type="text" placeholder="0.25" ></input>
										</td>
										<td>
											<input class="form-control input-sm" name="daPingfen" value="${gradeWeight.dbPingfen }" type="text" placeholder="0.5" ></input>
										</td>
										<td>
											<input type="hidden" value="1" name="flag">
											<button class="btn btn-primary " type="submit">设置</button>&nbsp;&nbsp;	
										</td>
									</tr>
									</form>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="panel-footer">
							<input type="hidden" value="${errorMsg }" id="error">
							<input type="hidden" value="${successMsg }" id="success">							
						</div>
					</div>
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

	
</body>
</html>