<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title  WCMAnt:param="master_import.jsp.title"> 导入母板 </title>
<script src="../../js/easyversion/lightbase.js"></script>
<script src="../../js/easyversion/extrender.js"></script>
<script src="../../js/easyversion/elementmore.js"></script>
<script src="../js/adapter4Top.js"></script>
<style type="text/css">
	#divBox{
		background: lightblue;
		padding:1px;
	}
	#divContainer{
		background: #ffffff;
		padding:5px;
	}
	#divClue{
		color:blue;
		font-size:14px;
	}
	body{
		background:white;
	}
	.operations_title{
		font-size:12px;
		height: 28px;
		line-height: 28px;
	}
	.operations{
		font-size:12px;
		height: 25px;
		line-height: 25px;
		padding-left: 30px;
	}
</style>

<script language="javascript">
<!--
	window.m_cbCfg = {
		btns : [
			{
				text : '确定',
				cmd : function(){
					uploadFile();
					return false;
				}
			},
			{
				extraCls : 'wcm-btn-close',
				text : '取消'
			}
		]
	};

	
	function uploadFile(){
		var winUpload = $("frmUploadFile").contentWindow;
		var frm = winUpload.document.getElementById("frmPost");
		var sFileName = winUpload.document.getElementById("fileUpload").value;
		if(!winUpload.valid(sFileName)){
			return false;
		}

		frm.submit();
		return false;
	}

	
	function getRadioValue(_sRadioName){
		var doms = document.getElementsByName(_sRadioName);
		for (var i = 0; i < doms.length; i++){
			if(doms[i].checked) {
				return doms[i].value;
			}
		}
		return null;
	}

	function addFile(_sFilePath){
		if(_sFilePath==null){
			Ext.Msg.$alert("文件路径为空，上传失败！");
			return false;
		}
		var oPostData = {
			ImportMode: getRadioValue('masterImportMode'),
			FileName: _sFilePath
		};
		var c_bWin = wcm.CrashBoarder.get(window);
		BasicDataHelper.Call('wcm61_master','importMasters', oPostData,true, function(_trans, _json){
			Ext.Msg.report(_json, '母板导入结果', function(){
				c_bWin.hide();
				c_bWin.notify('true');
			});
		},function(_trans, _json){
			var dataObj= eval(_json);//转换为json对象 
			var reg = /.*?\[(.*?)\].*?(\[.*?\]).*/g;
			if(dataObj && dataObj.FAULT && dataObj.FAULT.MESSAGE){
				var str = dataObj.FAULT.MESSAGE;
				var arr = str.replace(str.replace(reg,"$2"),""); 
				Ext.Msg.alert(arr);
			}else{
				Ext.Msg.alert("读取XML文件失败！");
			}
		});
	}
	function notifyOnUploadFileError(_sErrorMsg) {
		Ext.Msg.$alert(_sErrorMsg);
	}
//-->
</script>
</head>

<body>
<div id="divBox">
<div id="divContainer">
	<div id="divFileUpload">
		<IFRAME name="frmUploadFile" id="frmUploadFile" style="height:40px; width:330px" frameborder="0" vspace="0" src="../../file/file_upload.jsp?SelfControl=0&AllowExt=ZIP" scrolling="NO" noresize="true"></IFRAME>
		<INPUT TYPE="hidden" NAME="RealFileName" id="RealFileName">
	</div>
	<div id="divClue"  WCMAnt:param="master_import.jsp.support_zip_file">
		支持zip格式文件
	</div>
	<div class="operations_title"  WCMAnt:param="master_import.jsp.if_master_overname">
		如果母板重名
	</div>
	<div class="operations base_line">
		<span><input type="radio" name="masterImportMode" value="1" checked></span>
		<span  WCMAnt:param="master_import.jsp.auto_override">自动覆盖</span>
		<span><input type="radio" name="masterImportMode" value="3"></span>
		<span  WCMAnt:param="master_import.jsp.auto_alername">自动更名</span>
		<span><input type="radio" name="masterImportMode" value="2"></span>
		<span  WCMAnt:param="master_import.jsp.skip">跳过</span>
	</div>
</div>
</div>
</body>
</html>