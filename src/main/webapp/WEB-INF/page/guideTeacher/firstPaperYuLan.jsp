<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<link rel="stylesheet" href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
		<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
		<title></title>
	</head>

	<body>
		<div class="row">
			<iframe class="col-md-7" frameborder="0" id="iframe" scrolling="auto" src="https://view.officeapps.live.com/op/view.aspx?src=${filePathSrc}"></iframe>
			<div class="col-md-4" style="margin-top: 100px;">
				<form action="<%=basePath%>FreeMarker.shtm" method="post" id="openingReportYuLan">
				<input hidden="hidden" value="${filePath}" name="filePath"/>
				<input hidden="hidden" value="${firstPaper.stuId}" name="stuId"/>
				<input hidden="hidden" value="firstPaper" name="stage"/>
				<input hidden="hidden" value="${filePathSrc}" name="filePathSrc" id="filePathSrc"/>
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-pencil-square-o ">&nbsp;&nbsp;指导记录表(初稿)</span></div>
						<div class="panel-body">
							<div class="form-group">
								<label>指导教师评语及意见（对优点、缺点进行全面评价，并提出具体、具有针对性的修改意见，字数不少于300字）</label>
								<textarea rows="15" class="form-control" name="teaIdea" id="teaIdea" onkeydown='countChar("teaIdea","counter");'
								onkeyup='countChar("teaIdea","counter");'></textarea>
								 已输入<span id="counter" style="color:red;"">0</span>/字<br/>
							</div>
						</div>
						<div class="panel-footer">
							<a class="btn btn-primary" type="button" id="firstPaperSubmit">提交</a>
							<c:if test="${firstPaper.zdTablePath!=null}">
								<button class="btn btn-primary" type="button" id="tableYuLan" key="0">预览指导记录表</button>
							</c:if>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="modal fade" id="mymodal-error" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
		 <div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="error-title"></h4>
				</div>
				<div class="modal-body" id="error-body">
				<p></p>
				</div>
				<div class="modal-footer">			
					<button type="button" class="btn btn-primary"
						data-dismiss="modal">确认</button>
				</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"></h4>
				</div>
				<div class="modal-body" id="myModalReason">
					<p></p>
				</div>
				<div class="modal-footer">			
					<button type="button" class="btn btn-primary"
						data-dismiss="modal">确认</button>
				</div>				     
				</div>
			</div>
		</div>
		<input id="tablePath" value="${firstPaper.zdTableSrc}" hidden="hidden"/>
		<input id="successMsg" value="${successMsg}" hidden="hidden"/>
		<script type="text/javascript">
			window.onload = function(){
				var successMsg = $("#successMsg").val();
			    if(!(successMsg==''|| successMsg==undefined || successMsg==null))
			    {
			    	$("#myModalLabel").html("提示");
					$("#myModalReason").html(successMsg);
					$("#myModal").modal("toggle");
			    }
			    var iframe = document.getElementById("iframe");
			    iframe.style.height = window.innerHeight + 'px';			   			    
			};
			$(function(){
				$("#firstPaperSubmit").click(function(){
					var teaIdea = $("#teaIdea").val();
					if(teaIdea==''|| teaIdea==undefined || teaIdea==null)
					{
						 $("#error-title").html("错误提示");
						 $("#error-body").html("尚未填写教师意见!");
						 $("#mymodal-error").modal("toggle");
						 return false;
					}
					$("form").submit();
				});
				$("#tableYuLan").click(function(){
				    var tableYuLanVal = $("#tableYuLan").attr("key");
				    if(tableYuLanVal=="0")
				    {
				    	var tablePath = $("#tablePath").val();
						$("#iframe").attr("src","https://view.officeapps.live.com/op/view.aspx?src="+tablePath);
						$("#tableYuLan").html("预览论文初稿");
						$("#tableYuLan").attr("key","1");
						return true;
				    }else if(tableYuLanVal=="1"){
				    	var filePathSrc = $("#filePathSrc").val();
						$("#iframe").attr("src","https://view.officeapps.live.com/op/view.aspx?src="+filePathSrc);
						$("#tableYuLan").html("预览指导记录表");
						$("#tableYuLan").attr("key","0");
						return true;
				    }		   
				});
			});	
			function countChar(textareaName,spanName)
			{
				document.getElementById(spanName).innerHTML = document.getElementById(textareaName).value.length;
			} 		
		</script>
	</body>