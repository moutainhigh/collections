<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="X-UA-Compatible"content="IE=8;IE=9;IE=EDGE"> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<title>数据对比</title>
<!-- 引入 -->
<script src="<%=request.getContextPath()%>/static/script/jazz.js"	type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/script/uploadify/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/script/uploadify/swfupload/swfupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/script/uploadify/js/swfupload.queue.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/script/uploadify/js/fileprogress.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/script/uploadify/js/handlers.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/script/uploadify/js/jquery.cookie.js"></script>
<style>
.topWrap {
	margin-top: 15px;
	position: relative;
	height: 65px;
}

.tips {
	height: 35px;
	line-height: 35px;
	float: left;
	/* margin-left: 42px; */
	margin-right: 12px;
}

.m-t-15{
	margin-top: 15px;
}
.results{display: none;}
i{
    color: red;
    font-weight: 100;
    font-style: normal;
    }
.btnsChose{float: right}
.btnsChose label{display: inline-block;width: 80px}
.console{height: 60px}
.fl{float:left;}
.clear{clear: both;}

/*修正新版的jazz的组件导致文字不居中的问题*/
.jazz-grid-headerCell-outer {
    padding: 10px 10px;
}
</style>
</head>
<body>
<div class="title_nav">当前位置：数据对比> 数据对比> <span>数据对比</span></div>
	
	<div id="console">
			<div class="results">
			<span style="margin-left: 5px;border-right: 2px solid #4698E6;padding-right: 8px;">查询结果总记录数：<i id="countSum"></i></span>
			<span style="margin-left: 6px;border-right: 2px solid #4698E6;padding-right: 8px;margin-right:6px">匹配记录数：<i id="countMatch"></i></span>
			<span >不匹配记录数：<i id="countNotMatch"></i></span>
			</div>
	</div>
	
	<div class="topWrap">
    <form id="form1" action="" method="post" enctype="multipart/form-data">
		<div class="fieldset flash" id="fsUploadProgress" style="display: none;">
			<span class="legend"></span>
	  </div>
	  	<div id="divStatus" style="display:none;"></div>
			<div>
				<span id="spanButtonPlaceHolder"></span>
				<input id="btnCancel" type="button"  value="取消上传" onclick="swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px;    visibility: hidden; font-size: 8pt; height: 29px;" />
			</div>
		<div class="tips" style="position: relative;">
			<div class="fl"> 请上传xlsx,xls后缀的Excel文件,且excel文档的前四列分别为  “ 注册号、商事主体名称（企业名称）、商事主体类型（企业类型）”，点击选择文件并上传，系统校验通过之后开始上传文件</div>
			<div class="btnsChose fl" style="position: absolute;right:-162px;">
				<label><input type="radio"  class="btns" name='choose' value='1'><span class="text">匹配成功</span></label>
				<label><input type="radio" class="btns" name='choose' value='2'><span class="text">匹配失败 </span></label>
			</div>
			<div class="clear"></div>
		</div>
			

	</form>
			
</div>
	
	<div id="gridpanel" class="m-t-15"  vtype="gridpanel" name="gridpanel"	width="100%" layout="fit"
	 showborder="false" rowselectable="false" titledisplay="false" isshowselecthelper="true" selecttype="0" lineno="false">
		<div vtype="gridcolumn" name="grid_column" id="grid_column" >
			<div id="dateShowRows">
				<div name='regno' text="注册号" textalign="center" width="28%"></div>
				<div name='entname' text="商事主体名称" textalign="center" width="28%"></div>
				<div name='enttype' text="商事主体类型【企业类型】" textalign="center" width="28%"></div>
				<div name='result' text="对比结果" textalign="center" dataurl=""></div>
			</div>
		</div>
		<!-- 表格 -->
		<!-- <div vtype="gridtable" name="grid_table" id="grid_table"></div> -->
		<!-- 分页 -->
		<!-- <div vtype="paginator" name="grid_paginator" pagerows="50"
			id="grid_paginator"></div> -->
			
			<div vtype="gridtable" name="grid_table"></div>
			<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
			<div class="nodata" style="display: none;"></div>
	</div>
	
<script type="text/javascript">
	var swfu;
	$(function(){
		    var settings = {
			flash_url : "/query/static/script/uploadify/swfupload/swfupload.swf",
			upload_url: "/query/datas/upload.do",	
			post_params: {"sessionId" : "ye"},
			file_size_limit : "100 MB",
			file_types : "*.xlsx;.xls",
			file_types_description : "All Files",
			file_upload_limit : 0,  //配置上传个数
			file_queue_limit : 0,
			custom_settings : {
				progressTarget : "fsUploadProgress",
				cancelButtonId : "btnCancel"
			},
			debug: false,

			// Button settings
			button_image_url: "/query/static/script/uploadify/images/TestImageNoText_65x29.png",
			button_width: "65",
			button_height: "29",
			button_placeholder_id: "spanButtonPlaceHolder",
			button_text: '<span class="theFont">上传</span>',
			button_text_style: ".theFont { font-size: 16; }",
			button_text_left_padding: 12,
			button_text_top_padding: 3,
			
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
		
		
		
		$("input[type='radio']").on("click",function(){
			var selectValue = $("input[type='radio']:checked").val();
			var tid  = $.cookie("tid");
			$("#gridpanel").gridpanel("option", "dataurl","../../datas/getList.do?tid=" + tid +"&select="+selectValue);
			$("#gridpanel").gridpanel("reload");
		})
});
</script>
</body>
</html>
