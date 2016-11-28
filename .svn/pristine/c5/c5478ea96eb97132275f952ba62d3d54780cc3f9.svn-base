function zcTzClick(totalPage)
			{
				$(function(){
					
					var reqPage=$("#zc_tz_text").val();
					if(isNaN(reqPage)||reqPage.length<=0)
					  {
					  	alert("请输入数字");
					  	return ;
					  }
					if(reqPage>totalPage||reqPage<=0)
						{
						  alert("没有该页");
						}
					else
						{
							$("#pageNow").val(reqPage)
							document.forms["select"].submit();
						}	
					    
				})
			}
			
/**
 * 每页显示记录设置
 * @param pageUrl
 */
function pageSizeClick()
	{  $(function(){
			var pageSize=$("#page_size_text").val();
			if(isNaN(pageSize))
				{
				  alert("请输入数字！");
				}else if(pageSize<1){
					alert("请输入大于0的数字！");
				}else{
					$("#pageSize").val(pageSize)
					document.forms["select"].submit();
			}
		})
	}
/**
 * 下一页
 */
function nextPage(){
	var pageNow = $("#pageNow").val();
	pageNow = parseInt(pageNow) + 1;
	$("#pageNow").val(pageNow)
	document.forms["select"].submit();
}

/**
 * 上一页
 */
function upPage(){
	var pageNow = $("#pageNow").val();
	pageNow = parseInt(pageNow) - 1;
	$("#pageNow").val(pageNow)
	document.forms["select"].submit();
}


