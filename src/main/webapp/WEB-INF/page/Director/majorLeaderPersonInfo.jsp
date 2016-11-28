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
		<title></title>
	</head>

	<body>
		<script type="text/javascript" src="<%=basePath %>js/public.js"></script>
		<script type="text/javascript">
			window.onload=function(){
				if(document.getElementById("selHid").value==""){
	   				document.getElementById("sel").value=0;
	   			}else{
	   				document.getElementById("sel").value=document.getElementById("selHid").value;
	   			}
	   			
	   			if(document.getElementById("majorHid").value==""){
	   				document.getElementById("major").value=0;
	   			}else{
	   				document.getElementById("major").value=document.getElementById("majorHid").value;
	   			}
	   			
	   			if(document.getElementById("zhichengHid").value==""){
	   				document.getElementById("zhicheng").value=0;
	   			}else{
	   				document.getElementById("zhicheng").value=document.getElementById("zhichengHid").value;
	   			}		
	   			/*弹窗提示异常消息*/
		   		var error = $("#error").val();
		   		if(!(error==''|| error==undefined || error==null)){
				$(".modal-title").html("错误提示");
				$(".modal-body").html(error);
				$("#mymodal-error").modal("toggle");
				}
		   		
		   		/*弹窗提示成功消息*/
		   		var success = $("#success").val();
		   		if(!(success==''|| success==undefined || success==null)){
				$(".modal-title").html("提示");
				$(".modal-body").html(success);
				$("#mymodal-error").modal("toggle"); 
				}  			
   			}
		</script>
		<div class="container" style="margin-top: 50px;">
			<div class="row">
				<div class="col-sm-12 col-md-10 col-md-offset-1">
					<div class="panel panel-primary">
						<div class="panel-heading"><span class="fa fa-edit"></span>&nbsp;&nbsp;教师信息</div>
						<div class="panel-body" style="padding-bottom: 100px;padding-top: 50px;">
							<form class="form-horizontal" role="form" method="post" action="<%=basePath %>majorLeader/findInfo_updateInfo.shtm" onsubmit="return $.check()">
								<div class="form-group ">
									<label class="col-sm-3 control-label">账号:</label>
									<div class="col-sm-8">
										<input class="form-control" value="${tea.teaNum}" type="text" id="teacherID" readonly="readonly"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">姓名:</label>
									<div class="col-sm-8">
										<input class="form-control" value="${tea.teaName}" type="text" name="teaName" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">性别:</label>
									<div class="col-sm-8">
										<input type="hidden" value="${tea.sex }" id="selHid">
										<select class="form-control input-sm" type="text" name="sex" id="sel">
											<option value="男">男</option>
											<option value="女">女</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">新密码:</label>
									<div class="col-sm-8">
										<input class="form-control" type="text" name="password" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">手机号:</label>
									<div class="col-sm-8">
										<input class="form-control" value="${tea.tel}" type="text" name="tel" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">电子邮箱:</label>
									<div class="col-sm-8">
										<input class="form-control" value="${tea.mail}" type="text" name="mail" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">职称:</label>
									<div class="col-sm-8">
										<input type="hidden" value="${tea.zhicheng}" id="zhichengHid">
										<select class="form-control input-sm" type="text" name="zhicheng" id="zhicheng">
											<option value="0">请选择</option>
											<c:forEach var="ZhiCheng" items="${zhiChengList}">
											<option value="${ZhiCheng.zhiCheng}">${ZhiCheng.zhiCheng}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">专业:</label>
									<div class="col-sm-8">
										<input type="hidden" value="${tea.major }" id="majorHid">
										<select class="form-control input-sm" type="text" name="major" id="major" onchange="changeMajor()">
											<option value="0">请选择</option>
											<c:forEach var="major" items="${majorList}">
											<option value="${major.majorName}">${major.majorName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-6 col-sm-offset-3" style="margin-top: 20px">
									<div>
										<input type="hidden" value="${tea.userId}" name="userId">
										<button class="btn btn-primary" type="submit">提交</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<button class="btn btn-default" type="reset">重置</button>
										<input type="hidden" value="${errorMsg}" id="error">
										<input type="hidden" value="${successMsg}" id="success">
									</div>
								</div>
							</form>
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
	<script type="text/javascript">
		jQuery.extend({ 
				check:function(){
					var name = $("input[name='teaName']").val().trim();
					var tel = $("input[name='tel']").val().trim();
					var mail = $("input[name='mail']").val().trim();
					var zhicheng = $("select[name='zhicheng']").val();
					var major = $("select[name='major']").val();
					var password = $("input[name='password']").val().trim();
					if(name==''|| name==undefined ||name==null)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("姓名不能为空！");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}
		        	if(zhicheng==0)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("职称不能为空！");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}		    
		        	if(major==0)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("专业不能为空！");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}		        	
		        	if(tel==''|| tel==undefined ||tel==null)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("手机号码不能为空！");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}else{
		        		var pattern = /^1[34578]\d{9}$/; 
		        		if(pattern.test(tel)==false){
		        			$(".modal-title").html("错误提示");
					   		$(".modal-body").html("手机号码格式错误！");
					   		$("#mymodal-error").modal("toggle");
					   		return false;
		        		}
		        	}
		        	if(mail==''|| mail==undefined ||mail==null)
					{
				       $(".modal-title").html("错误提示");
					   $(".modal-body").html("邮箱不能为空！");
					   $("#mymodal-error").modal("toggle");
					   return false;
		        	}else{
		        		var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;						
		        		if(reg.test(mail)==false){
		        			$(".modal-title").html("错误提示");
					   		$(".modal-body").html("邮箱格式错误！");
					   		$("#mymodal-error").modal("toggle");
					   		return false;
		        		}
		        	}	        	
		        }								
			});
	</script>
</body>

</html>