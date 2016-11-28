<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hlzt.commons.model.GlobalVar"%>
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
<meta name="viewport" content="width=device-width,initial-scale=1">
<title></title>

</head>

<body>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
		 var ue = UE.getEditor('editor');
		 $("#content").val(UE.getContent());		  		 
   </script>
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
		<div class="col-xs-12 col-md-10 col-md-offset-1">
			<form name="noticeEditor" method="post" enctype="multipart/form-data" class="form-horizontal" action="<%=basePath%>secretary/uploadNotice.shtm">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<span class="fa fa-pencil-square-o"></span>&nbsp;&nbsp;编辑公告
					</div>
					<div class="penel-body" style="padding-top: 50px;">
						<div class="form-group">
							<label class="col-sm-3 control-label">公告标题:</label>
							<div class="col-sm-6">
								<input class="form-control" name="noticeTitle" id="noticeTitle" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-10 col-sm-offset-1">
								<textarea id="editor" name="content" type="text/plain" style="width:100%;height:350px;"></textarea>
							</div>
						</div>
						<div class="form-group">
						     <div class="col-sm-offset-1 col-sm-4">	
						       <div class="fileUpload btn btn-primary">
						          <span>上传附件</span>
						          <input id="upfile" type="file" name="upfile"  class="upload" />
						       </div>
							   <input id="uploadFile"  placeholder="Choose File" disabled="disabled" />
							   <input type="hidden" value="student" name="roleName"/> 		
							    <script type="text/javascript">
							         document.getElementById("upfile").onchange = function () {
										    document.getElementById("uploadFile").value = this.value;
										};
						        </script>				
				             </div>	
						     <div class="col-sm-6">
						     	<div class="alert alert-info">
						     	<p>上传多个附件请打包压缩后上传。</p>
						     	</div>				 
						     </div>	
						 </div>															
					   </div>					
					<div class="panel-footer">
						<button id="noticeSubmit" class="btn btn-primary col-sm-offset-1" type="submit">提交</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="btn btn-default" href="<%=basePath %>secretary/noticeList.shtm">返回</a>
					</div>			
				</div>
			</form>
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
	<input id="ErrorMsg" value="${errorMsg}" hidden="hidden"/>
	<script type="text/javascript">
	  $(document).ready(function() {
			var ErrorMsg=$("#ErrorMsg").val();
			if(!(ErrorMsg==''|| ErrorMsg==undefined || ErrorMsg==null)){
				$(".modal-title").html("错误提示");
				$(".modal-body").html(ErrorMsg);
				$("#mymodal-error").modal("toggle");
			}
		});
		$("#noticeSubmit").click(function(){
			var title=$("#noticeTitle").val();
			if(title==''|| title==undefined || title==null){
				$(".modal-title").html("错误提示");
				$(".modal-body").html("请设置公告标题");
				$("#mymodal-error").modal("toggle");
				return false;
			}			
			document.forms['noticeEditor'].submit();
		});
			
	</script>
</body>

</html>