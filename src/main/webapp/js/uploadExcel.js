
   
 //JS校验form表单信息  
 function checkData(){
    var fileDir = $("#upfile").val();  
    var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
    if("" == fileDir){
        alert("选择需要导入的Excel文件！");  
        return false;  
    }  
    if(".xls" != suffix && ".xlsx" != suffix ){  
        alert("选择Excel格式的文件导入！");
        $("#upfile").val("");  
        return false;  
    }  
    document.forms["uploadForm"].submit();
 }  