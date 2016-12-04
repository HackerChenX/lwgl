function checkTitle()
{
	    var title = $("#titleName").val();				
		if(title==''|| title==undefined || title==null)
		{
			$("#error-title").html("错误提示");
			$("#error-body").html("课题名称不能为空!");
			$("#mymodal-error").modal("toggle");
			return false;
		}else{
			 $("#ansjModalTitle").find("span").empty();
			 $("#ansjModalTitle").find("span").append("<h>"+"&nbsp;&nbsp;课题查重结果"+"</h>");
			 $("#ansj-tbody").empty();
			 $("#keyWords").empty();
			 $("#ansj-tbody").append("<tr><td colspan='2' class='text-center'>"+"Load...."+"</td></tr>");
			 $("#mymodal-ansj").modal("toggle");
			 $.ajax({     
			        type: "POST",
			        dataType: "json",
			        data:{title:title},
			        url: "/lwgl/AnsjSplitWord.shtm",
			        success: function (data){
			        	 var json = JSON.parse(data);
			        	 
			        	 if(json[json.length-1][0]!=null)
		        		 {
			        		 var m = 0;
			        		 for(var j=0;j<json[json.length-1].length;j++){
			        			 //关键词标签样式有四种，做一个循环
			        			 switch(m)
			        			 {
			        			 	case 0:$("#keyWords").append("<span class='label label-primary'>"+json[json.length-1][j]+"</span>&nbsp&nbsp");
			        			 	break;
			        			 	case 1:$("#keyWords").append("<span class='label label-success'>"+json[json.length-1][j]+"</span>&nbsp&nbsp");
			        			 	break;
			        			 	case 2:$("#keyWords").append("<span class='label label-info'>"+json[json.length-1][j]+"</span>&nbsp&nbsp");
			        			 	break;
			        			 	case 3:$("#keyWords").append("<span class='label label-warning'>"+json[json.length-1][j]+"</span>&nbsp&nbsp");
			        			 	break;
			        			 }
			        			 if(++m==4){
			        				 m=0;
			        			 }
			        		 }
			        		 		        		 
		        		 }
			        	 if(json.length == 0 ||json[0] ==null){			        		 			    		
			        		 $("#ansj-tbody").empty();
			        		 $("#ansj-tbody").append("<tr><td colspan='2' class='text-center'>"+"往届无相似课题"+"</td></tr>");
			        		
			        	 }else{
			        		 $("#ansj-tbody").empty();
			        		 //json最后一位存放的是关键词
			        		 for(var i=0;i<json.length-1;i++){
			        			 $("#ansj-tbody").append("<tr><td>"+json[i].term+"</td><td>"+json[i].title+"</td></tr>");
			        		 } 		
			        	 }
			        	 
			        },
			        error: function (data){
			        	 $("#ansj-tbody").empty();
		        		 $("#ansj-tbody").append("<tr><td colspan='2' class='text-center'>"+"往届无相似课题"+"</td></tr>");
			        }
			        
			 });
		}
}