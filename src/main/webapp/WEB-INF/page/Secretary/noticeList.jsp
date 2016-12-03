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
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<title></title>
	</head>
	<body>
	 <style>
		  .fileUpload {
			position: relative;
			overflow: hidden;
			margin: 10px;
			}	
		.fileUpload input.upload {	
			position: absolute;
			top: 0;
			right: 0;
			margin: 0;
			padding: 0;
			font-size: 20px;
			cursor: pointer;
			opacity: 0;
			filter: alpha(opacity=0);
			}
	 </style>
	 <div class="row" style="margin-top: 50px;margin-left: 0px;margin-right: 0px;">
	 	<div class="col-xs-12 col-md-11 col-md-offset-1">
	 	<div class="panel panel-info">
	 		<div class="panel-body">
	 			<form class="form-inline" method="post" action="<%=basePath%>secretary/noticeList.shtm" id="select">
				  <div class="form-group">
						<label>公告名称:</label>
						<input class="form-control input-sm" type="text" name="title" id="title" value="${title}"/>
				  </div>&nbsp;&nbsp;&nbsp;&nbsp;
				  <div class="form-group">
				  		<label>公告类型:</label>
				  		<select name="noticeType" class="form-control">
				  			<option value="all"  <c:if test="${noticeType=='all'}">selected="selected"</c:if>>全部类型</option>
				  			<option value="notice" <c:if test="${noticeType=='notice'}">selected="selected"</c:if>>通知公告</option>
				  			<option value="table" <c:if test="${noticeType=='table'}">selected="selected"</c:if>>表格模板</option>
				  			<option value="paper" <c:if test="${noticeType=='paper'}">selected="selected"</c:if>>优秀论文</option>
				  		</select>&nbsp;&nbsp;
				  		<input name="pageNo"  type="hidden"  value="${page.pageNo}"   id="pageNow" />
						<input name="pageSize"type="hidden"  value="${page.pageSize}" id="pageSize">
						<button class="btn btn-primary btn-sm" id="keyWordSelect" type="submit">查询</button>
				  </div>
			    </form>
	 		</div>
	 	</div>
	 </div>
	 </div>
	<div class="row" style="margin-left: 0px;margin-right: 0px;">
			<div class="col-xs-12 col-md-11 col-md-offset-1">
				<div class="panel panel-primary">
					<div class="panel-heading"><span class="fa fa-paperclip"></span>&nbsp;&nbsp;公告管理</div>
					<div class="panel-body">
						<div class="table-responsive text-center">
							<table style="text-align:center" class="table tabel-hover table-striped table-bordered">
								<thead>
							        <tr>
										<th>公告题目</th>
										<th>类型</th>
										<th colspan="2">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${page!=null}">
									<c:forEach var="publicNotice" items="${page.results}">
										<tr>
											<td>${publicNotice.title}</td>
											<td>
												<c:if test="${publicNotice.noticeType=='notice'}">通知公告</c:if>
												<c:if test="${publicNotice.noticeType=='table'}">表格模板</c:if>
												<c:if test="${publicNotice.noticeType=='paper'}">优秀论文</c:if>
											</td>
											<td><c:if test="${publicNotice.top==0}"><a href="<%=basePath%>secretary/updateNotice.shtm?top=true&id=${publicNotice.id}">置顶</a></c:if>
												<c:if test="${publicNotice.top==1}"><a href="<%=basePath%>secretary/updateNotice.shtm?top=false&id=${publicNotice.id}">取消置顶</a></c:if>	
											</td>
											<td><a href="<%=basePath%>secretary/updateNotice.shtm?remove=ture&id=${publicNotice.id}">删除</a></td>
										</tr>
									  </c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
					<div class="panel-footer">					
					
					<div class="row" style="padding-left:15px"> <!-- row -->
							
						<a class="btn btn-primary" id="addNotice" href="<%=basePath%>secretary/noticeList_addNotice.shtm" type="button" >通知公告</a>
						<button class="btn btn-primary" id="addTable"  data-toggle="modal" data-target=".addTable" >表格模板</button>
						<button class="btn btn-primary" id="addPaper"  data-toggle="modal" data-target=".addPaper" >优秀论文</button>					
						

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
						</div>   <!-- /row -->				
					</div><!--/panel-footer -->
				</div>
				<div class="modal fade" id="mymodal-error" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
								<h4 class="modal-title" id="error-title"></h4>
							</div>
							<div class="modal-body" id="error-body">
								<p></p>
							</div>
							<div class="modal-footer" id="error-footer">
								<button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
							</div>
						</div>
					</div>
				</div>				
				<div class="modal fade addTable" id="mymodal-addTable" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title"><span class="fa fa-file-text-o"></span>&nbsp;&nbsp;</h4>
						</div>					
						<div class="modal-body">
						<form method="post" id="uploadFormTable" class="form-horizontal" enctype="multipart/form-data" action="<%=basePath%>secretary/uploadNotice.shtm">															
							 <div class="form-group">
								<label class="col-sm-2 control-label">标题:</label>
								<div class="col-sm-8">
									<input class="form-control" name="noticeTitle" id="noticeTable" />
								</div> 
							</div>
							<div class="form-group">		
								<div class="fileUpload btn btn-primary col-xs-2">
								    <span>选择文件</span>
								    <input id="upTable" type="file" name="upfile"  class="upload" />
								</div>
								<div class="col-sm-4">
									<input id="uploadTable" name="upTable" class="form-control" style="margin-top:10px;"  placeholder="Choose File" readonly="readonly"/>
								</div>
							</div>							
							<script type="text/javascript">
								document.getElementById("upTable").onchange = function () {
											    document.getElementById("uploadTable").value = this.value;
											};
							</script>	
						  </form> 				
						</div>						
						 <div class="modal-footer">
							<button type="button" id="btn" class="btn btn-primary" onclick="checkDataTable()">上传</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>					
				   </div>
			 </div>
          </div>              		
          <div class="modal fade addPaper" id="mymodal-addPaper" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title"><span class="fa fa-file-code-o"></span>&nbsp;&nbsp;</h4>
						</div>
						<div class="modal-body">
						<form method="post" id="uploadFormPaper" class="form-horizontal" enctype="multipart/form-data" action="<%=basePath%>secretary/uploadNotice.shtm">							
							 <div class="form-group">
								<label class="col-sm-2 control-label">标题:</label>
								<div class="col-sm-8">
									<input class="form-control" name="noticeTitle" id="noticePaper" />
								</div> 
							</div>
							<div class="form-group">		
								<div class="fileUpload btn btn-primary col-xs-2">
								    <span>选择文件</span>
								    <input id="upPaper" type="file" name="upfile"  class="upload" />
								</div>
								<div class="col-sm-4">
									<input id="uploadPaper" name="upPaper" class="form-control" style="margin-top:10px;"  placeholder="Choose File" readonly="readonly"/>
								</div>
							</div>					 		
							<script type="text/javascript">
								document.getElementById("upPaper").onchange = function () {
											    document.getElementById("uploadPaper").value = this.value;
											};
							</script>
							</form>									
						</div>
						<div class="modal-footer">
							<input type="button" value="上传" id="btn" class="btn btn-primary" onclick="checkDataPaper()"/>
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>							
				   </div>
			 </div>
          </div>       
		</div>
	    </div>
		<input hidden="hidden" id="ErrorMsg" value="${errorMsg}">
		<script src="//cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
		<script type="text/javascript">
		$(document).ready(
			function() {
				var ErrorMsg = $("#ErrorMsg").val();
				if (!(ErrorMsg == '' || ErrorMsg == undefined || ErrorMsg == null)) {
					$("#error-title").html("错误提示");
					$("#error-body").html(ErrorMsg);
					$("#mymodal-error").modal("toggle");
				}
			});
			$(function(){
				$("#addTable").click(function() {					
					$(".modal-title").html("表格模板上传");
					$("#uploadForm").attr("action", "");
					$("#addThings").attr("href", "");
					$("#mymodal-submit").modal("toggle");							
				});
			});
			$(function() {
				$("#addPaper").click(function(){	
					$(".modal-title").html("优秀论文展示");
					$("#uploadForm").attr("action", "");
					$("#addThings").attr("href", "");
					$("#mymodal-submit").modal("toggle");
				});				
			});
		function checkDataTable(){
    		var title= $("#noticeTable").val();
    		var upfile=$("#uploadTable").val();
    		if(title==''|| title==undefined || title==null)
    		{
    			alert("请设置公告标题")			
				return false;		
    		}
    		if(upfile==''|| upfile==undefined || upfile==null)
    		{
    		  	alert("请选择上传文件");
				return false;
    		}
  			document.forms["uploadFormTable"].submit();
		}
		function checkDataPaper(){
    		var title= $("#noticePaper").val();
    		var upfile=$("#uploadPaper").val();
    		if(title==''|| title==undefined || title==null)
    		{
    			alert("请设置公告标题")			
				return false;		
    		}
    		if(upfile==''|| upfile==undefined || upfile==null)
    		{
    		  	alert("请选择上传文件");
				return false;
    		}
  			document.forms["uploadFormPaper"].submit();
		}
		
		</script>
	</body>

</html>