window.onload=function(){
	/*刷新页面下拉框值不变*/	
   		if(document.getElementById("selHid").value==""){
   			document.getElementById("sel").value=3;
   		}else{
   			document.getElementById("sel").value=document.getElementById("selHid").value;
   		}
   		/*刷新页面下拉框值不变2*/	
   		if(document.getElementById("selHid2").value==""){
   			document.getElementById("sel2").value=0;
   		}else{
   			document.getElementById("sel2").value=document.getElementById("selHid2").value;
   		}
	
}