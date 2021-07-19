<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>主体登记信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<!-- <script src="../common.js" type="text/javascript"></script>    -->
<style type="text/css">
    td{
		text-align: center;
	}
.jazz-pagearea{
	height: 0px;
}

</style>

<script>

function _enter(e, data){  
    //鼠标移入所累发图时的事件  
    jazz.log("enter");  
}   
function _leave(e, data){  
    //鼠标移出缩略图时的事件  
    jazz.log("leave");  
}   
function _close(e, data){  
    //鼠标点击关闭按钮图片时的事件  
    jazz.log("close");  
}  
function _click(e, data){  
    //鼠标上传缩略图时的事件     
    jazz.log("click");  
}     
  
jazz.config.default_flash9_url="<%=request.getContextPath()%>/static/script/JAZZ-UI/lib/swfupload/swfupload_fp9.swf"; 
jazz.config.default_flash_url="<%=request.getContextPath()%>/static/script/JAZZ-UI/lib/swfupload/swfupload.swf";  
 
  
function _setValue(){  
    var jsonData = [{name:'回显附件1111', id: '8689adcb3cc74882ab345e731d61de99', size: '919k'},  
                 {name:'回显附件2222', id: '8689adcb3cc74882ab345e731d61de99', size: '193k'}];
    $('#attach1').attachment('setValue', jsonData);  
}  
  
</script>   
</head>
<body id="view">   
  
<input id="btn_id" type="button" value='附件数据回显' onclick="_setValue()" />  
  
<!--  name vtype 必填 -->  
<!-- 附件上传的其他属性，可以参考form表单中的其他字段 -->  
<div id="attach1" name="attach1" vtype="attachment" label="附件" title="上传附件" width="500"   
     description="说明： 如果用网上下载的标准模板，请上传签字盖章页；如果不是网上下载的文件，请上传全部章程文件。 "  
     ishidelist="0" theme="1" uploadsuccess="_uploadsuccess()"  
     uploadurl="http://localhost:8080/gdsjzx/attach/upload.do"  
     previewurl="http://localhost:8080/gdsjzx/attach/preview.do"  
     enter="_enter()" leave="_leave()" close="_close()" click="_click()"></div>      
     
</body>  
</html>  

﻿ 