var s = "";
if(navigator.userAgent.indexOf("NET")>0){//IE
	s = "<OBJECT id='WebOffice1' align='middle' style='LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT:768px'"
		+ "classid=clsid:E77E049B-23FC-4DB8-B756-60529A35FAD5>"
		+ "</OBJECT>";
}

if(navigator.userAgent.indexOf("Chrome")>0){//谷歌
	s = "<object id='WebOffice1' type='application/x-itst-activex' align='baseline' border='0'"
		+ "style='LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT: 768px'"
		+ "clsid='{E77E049B-23FC-4DB8-B756-60529A35FAD5}'"
		+ "event_NotifyCtrlReady='WebOffice1_NotifyCtrlReady'>"
		+ "</object>";	
}

if(navigator.userAgent.indexOf("Firefox")>0){//火狐
	s = "<object id='WebOffice1' type='application/x-itst-activex' align='baseline' border='0'"
		+ "style='LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT: 768px'" 
		+ "clsid='{E77E049B-23FC-4DB8-B756-60529A35FAD5}'"
		+ "event_NotifyCtrlReady='WebOffice1_NotifyCtrlReady'>"
		+ "</object>";	
}//加载控件


//document.getElementById("webofficeDiv").
$(function(){
	if(s!="")
		$("#webofficeDiv").html(s);
	WebOpen();
})

function openDoc() {
	var wordUrl=document.getElementById("filePath").value;
//	var wordUrl=encodeURIComponent(wordUrl);
	//	var uri=document.getElementById("filePath").value;
		//	var wordUrl=encodeURIComponent(uri);  
//	var localhostPath=curWwwPath.substring(0,pos); 
            //加载控件
	//var wordUrl="C:/Users/Harker/Desktop/11.doc";
			try{
				document.all.WebOffice1.LoadOriginalFile(wordUrl, "doc");
			}catch(e)
			{
				alert(e.message); 
				var r=confirm("检测到您的电脑没有安装ActiveX插件,或者没有启用，请点击确定，安装后使用");
				if (r==true)
				  {
					
				    window.location.href="../office/ffactivex-setup-r39/ffactivex-setup-r39.exe";
				  }
				else
				  {
				  $("#webofficeDiv").html("<a href='../office/ffactivex-setup-r39/ffactivex-setup-r39.exe'>点击安装Activex插件</a>");
				  alert("请安装后使用");
				  }
		
				return;
			}
            //隐藏不需要的按钮
//document.all.WebOffice1.HideMenuItem(0x01 + 0x02 + 0x04 + 0x10 + 0x20);
			document.all.WebOffice1.HideMenuItem(0x04);
			//SetCustomToolBtn(0,"选择保存");
           // SetCustomToolBtn(1,"保存");
        }
        function WebOpen(obj)
        {
            obj = document.all.item("WebOffice1");
            if (obj !='')
            {
 		        //等待控件初始化完毕，时间长短可以根据网络速度设定。
 		        setTimeout('openDoc()',100);
			   hideAll('hideall','','','')
 		    }
 		}
		
		$(function(){
			$("#selectSave").click(function(){			
				SaveAsTo();
			})
			$("#savleResource").click(function(){
				var r=confirm("您确定要保存修改后的文件，此操作会覆盖源文件，请慎重操作，修改后不可恢复！");
				if (r==true)
				  {
				    newSave();
				  }
				else
				  {				 
				  }
			})		
		})
		