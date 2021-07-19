<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible"content="IE=8;IE=9;IE=EDGE"> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<title>人员查询</title>
<link rel="stylesheet" href="../../static/css/common.css">
<link href="../../static/css/jquery-ui-1.9.2.custom.css" rel="stylesheet" type="text/css" />
<link href="../../static/lib/JAZZ-UI/lib/themes/default/jazz-all.css" rel="stylesheet" type="text/css" />	
<script src="../../static/script/JAZZ-UI/external/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="../../static/script/uploadify/swfupload/swfupload.js"></script>
<script type="text/javascript" src="../../static/script/uploadify/js/swfupload.queue.js"></script>
<script type="text/javascript" src="../../static/script/uploadify/js/fileprogress.js"></script> 
<script type="text/javascript" src="../../static/script/personHandle.js"></script>

<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/common.css">
<link href="<%=request.getContextPath()%>/static/css/jquery-ui-1.9.2.custom.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/lib/JAZZ-UI/lib/themes/default/jazz-all.css" rel="stylesheet" type="text/css" />	
<script src="<%=request.getContextPath()%>/static/script/jazz.js"	type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/script/uploadify/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/script/uploadify/swfupload/swfupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/script/uploadify/js/swfupload.queue.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/script/uploadify/js/fileprogress.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/script/personHandle.js"></script> --%>


<style type="text/css">
.jazz-field-comp-input{
	line-height: 22px;
	_line-height: 22px;
}


.jazz-att-panel {
	background-color: #F8F9FB !important;
}

#form1{
    position: absolute;
    z-index: 999;
    right:37%;
    top:102px;
}

.jazz-toolbar-content-wrap{
	background: #F8F9FB;
	
}

.jazz-toolbar-content{
	width: 96%;
}

a:focus{outline:none;}

.exportData{
	position: absolute;
    top: 127px;
    right: 13px;
    background: #3398DA;
    padding: 2px 4px;
    text-align: center;
    display: block;
    color: #fff;
    height: 22px;
    line-height: 22px;
    display: none;
    font-family: "宋体";
    font-size: 12px;
}

.hide{display:none;}
</style>
</head>
<body>
<script src="../../static/js/sczt/base64.js" type="text/javascript"></script>

<script>

</script>
<body style="overflow-x:hidden">
<!-- 构建页面==开始 -->

<div style="position: relative;">
<form id="form1" class="hide" action="" method="post" enctype="multipart/form-data">
		<div class="fieldset flash" id="fsUploadProgress" style="display:none">
			<span class="legend"></span>
	  </div>
	  	<div id="divStatus" style="display:none"></div>
			<div>
				<span id="spanButtonPlaceHolder"></span>
				<input id="btnCancel" type="button"  value="取消上传" onclick="swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px;    visibility: hidden; font-size: 8pt; height: 29px;" />
			</div>
</form>



<!-- 查询条件 -->
<div name="PersonQueryListPanel" id="PersonQueryListPanel"
		vtype="formpanel" width="100%" layout="table"  
	layoutconfig="{cols:2, columnwidth: ['40%','*']}"
	labelstyleclass="labelstyle" title="人员查询" 
	titledisplay="true" showborder="false">
	
		
		
	<div style="position: relative;">
		<div name='select' id='select'  vtype="comboxfield" label="姓名" labelwidth="150"  dataurl="" rule="" labelalign="right" width="220" ></div>	
		<div name="persname" id="persname" style="position: absolute;top:0;left:218px" vtype="textfield" label="" labelwidth="150" labelalign="left" width="250" valuetip="请输入姓名……" tooltip="" rule="" ></div>
	</div>
	<div name="cerno" id="cerno" vtype="textfield" label="身份证号码"  labelwidth="300" labelalign="right" width="600" valuetip="请输入身份证号码……" tooltip="" rule="" ></div>
 	
    <div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
				align="center" width="100%">
				<div name="save_button" id="save_button" vtype="button" text="查询"
					icon="../query/queryssuo.png"></div>
				<div name="reset_button" id="reset_button" vtype="button" text="重置"></div>
				
				<!-- <div name="back_button" id="back_button" vtype="button" text="返回"
					icon="../query/queryssuo.png" click="goBack()"></div> -->
    </div>
    
    
</div>

<a class="exportData" id="exportData" href="../../readTxt/personDown.do" target="_blank">导出</a>

<div name="PersonQueryListGrid" id="PersonQueryListGrid" vtype="gridpanel" titleDisplay="false" title="查询列表" labelStyleClass="labelstyle" lineno="true" isshowselecthelper="false" showborder=true width="100%" >
		<!-- <div name="add_function_button" id="add_function_button" vtype='button' text='新增功能信息' ></div> -->
	<div style="display:none;color:red" id="tips">共<span id="totals"></span>条记录。以下为查询结果的前100条，如有更多需求，请与信息中心联系，电话：83070056。</div>
			
			<div name="functionConfigGrid_column" vtype="gridcolumn">
				<div>
					<div name='typeFlag' text="变更"  textalign="left" width="5%"></div>
				    <div name='cerno' text="身份证号码"  textalign="left" width="10%"></div>
				    <div name='unifsocicrediden' text="统一社会信用代码" textalign="left" width="8%"></div>
					<div name='entname' text="企业名称" textalign="left" width="10%"></div>
					<div name='persname' text="姓名" textalign="left" width="6%"></div>
					<div name='perflag' text="职务" textalign="left" width="7%"></div>
					<div name='enttype' text="经济性质" textalign="left" width="8%"></div>
					<div name='regcap' text="注册资本" textalign="left" width="4%"></div>
					<div name='memo' text="出资额及比例（或职务）" textalign="left" ></div>
					<div name='estdate' text="成立日期" textalign="left" width="7%"></div>
					<div name='entstatus' text="企业状态" textalign="left" width="7%"></div>
					<div name='apprdate' text="注吊销时间" textalign="left" width="6%"></div>
					<div name='regorg' text="登记机关" textalign="left" width="4%" ></div>
					<div name='dom' text="住址" textalign="left" width="4%"></div>
				</div>
			</div>
			<div vtype="gridtable" name="grid_table"></div>
			<!-- <div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div> -->
			<div class="nodata" style="display: none;"></div>
		</div>
</div>
 <div id="demo" style="background-color: #999;color:#fff;font-size:20px;padding:4px;margin: 10px auto;display:none;position: absolute;left: 40%;top: 47%;z-index: 99999; ">
查询中，请稍后...</div> 


<!-- 构建页面==结束 -->

<script type="text/javascript" src="../../static/js/config.js"></script>
<script type="text/javascript" data-main="com/PersonQueryList.js" src="../../static/lib/require.js"></script>
<!-- <script>

var win;
function createNewWindow(){
	  win = top.jazz.widget({   
		vtype: 'window', 
        name:'win',
        title:"上传文件", 
        width:550, 
        height:450, 
        modal:true, 
        visible: true ,
		showborder : true, //true显示窗体边框    false不显示窗体边
		closestate: false,
		minimizable : true, //是否显示最小化按钮
		titleicon : "../../static/images/other/notepad-.png",
		frameurl: "../../page/comselect/bathPerson.html"
    }); 
}


function batchQuery(){
	createNewWindow();
	
}


function batchExport(){

}




function leave(){
	win.window('close'); 
	 
}


</script> -->


<script type="text/javascript">

var rootpath = "<%=request.getContextPath()%>";
var winWidth = $(window).width();
if(winWidth>980&&winWidth<1440){
	$("#form1").css({"right":"357px","top":"102px"});
}

/* function batchExport(){
	window.open("../../readTxt/personDown.do");
} */

	var swfu;
	$(function(){
		    var settings = {
			flash_url :rootpath+ "/static/script/uploadify/swfupload/swfupload.swf",
			upload_url:rootpath+ "/readTxt/text.do",	
			post_params: {"sessionId" : "aut"},
			file_size_limit : "100 MB",
			file_types : "*.txt",
			file_types_description : "All Files",
			file_upload_limit : 0,  //配置上传个数
			file_queue_limit : 0,
			custom_settings : {
				progressTarget : "fsUploadProgress",
				cancelButtonId : "btnCancel"
			},
			debug: false,

			// Button settings
			button_image_url: "../../static/script/uploadify/images/TestImageNoText.png",
			button_width: "80",
			button_height: "30",
			button_placeholder_id: "spanButtonPlaceHolder",
			button_text: '<span class="theFont">批量查询</span>',
			button_text_style: ".theFont { font-size: 16;color:#FFFFFF }",
			button_text_left_padding: 8,
			button_text_top_padding: 4,
			button_cursor : SWFUpload.CURSOR.HAND,
            button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
			file_queued_handler : fileQueued,
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,
			queue_complete_handler : queueComplete	
		};

		swfu = new SWFUpload(settings);
		
		
});
	
	
</script>
</body>
</html>