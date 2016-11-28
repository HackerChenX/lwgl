function changeMajor(){
			    var major=$("#major").val();		    
			    $.ajax({     
			        type: "POST",
			        dataType: "json",
			        //contentType:"application/x-www-form-urlencoded; charset=utf-8",
			        url: "findClassByMajor.shtm?majorName="+major,
			        success: function (data) {
			            $("#class").empty();
			            $("#class").empty();
			            
			            var json = JSON.parse(data);
			            if(json.length==0){
			            	 $("#class").empty();
			            	 $("#class").empty();
			            }
			            $("#class").append("<option value='0'>全部班级</option>");
			            for(var i=0;i<json.length;i++){
			            	$("#class").append("<option value='"+json[i].cName+"'>"+json[i].cName+"</option>")
			            }
			        }
		   	 });
		   	 }