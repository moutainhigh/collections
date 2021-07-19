<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.presentation.plugin.PluginConfig" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%String sChannelId = request.getParameter("ChannelId");%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="document_addedit.jsp.trswcmeditor">TRS WCM 编辑器</title>
<link rel="stylesheet" type="text/css" href="../js/source/wcmlib/components/processbar.css">
<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
<link rel="stylesheet" type="text/css"  href="../js/resource/widget.css">
<link rel="stylesheet" type="text/css"  href="../js/easyversion/resource/crashboard.css">
<script src="../js/easyversion/lightbase.js"></script>
<script src="../js/easyversion/extrender.js"></script>
<script src="../js/easyversion/elementmore.js"></script>
<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/data/locale/document.js"></script>
<script src="../js/data/locale/system.js"></script>
<script src="../js/data/locale/ajax.js"></script>
<script src="../js/data/locale/template.js"></script>
<script src="../individuation/customize_config.jsp"></script>
<style type="text/css">
.ctrsbtn_left{background:url(../../images/button_left.gif) no-repeat 0 0;height:24px;}
.ctrsbtn_right{background:url(../../images/button_right.gif) no-repeat right 0;height:24px;line-height:24px;}
.ctrsbtn{cursor:pointer;background:url(../../images/button_bg_line.gif) repeat-x 4px 0;font-size:12px;height:24px;text-align:center;margin:0 5px;width:90px;display:inline-block!important;display:inline;}
.ext-strict .box{position:absolute;left:0px;top:0px;bottom:30px;right:0px;}
.ext-strict .ctrsbtn{float:none;}
.ext-ie7 .ctrsbtn{zoom:1;display:inline!important;}
.ext-ie8 .ctrsbtn{display:inline-block!important;}
.ext-strict .box iframe{position:absolute;left:0px;top:0px;bottom:0px;right:0px;}
.ext-ie6 .box{width:100%;height:100%;}
.ext-ie6 .box iframe{width:100%;height:100%;}
</style>
</head>
<body>
<script>
	initExtCss();
	Ext.ns('PgC', 'editorCfg');
	Ext.apply(editorCfg, {
		basePath : WCMConstants.WCM6_PATH + 'document/',
		enablePhotolib : <%=PluginConfig.isStartPhoto()%>,
		enableFlashlib : <%=PluginConfig.isStartVideo()%>,
		enableAdintrs : false,
		enableCkmExtract : false,
		enableCkmSimSearch : false,
		enableCkmspellcheck : false,
		enableAutoSave : parseInt(m_CustomizeInfo.documentAutoSave.paramValue)==1,
		enableAutoPaste : parseInt(m_CustomizeInfo.documentAutoPaste.paramValue)==1,
		enterAs : m_CustomizeInfo.documentEnterAs.paramValue,
		tabSpaces : parseInt(m_CustomizeInfo.documentTabAs.paramValue),
		autoTitleLength : parseInt(m_CustomizeInfo.documentAsTitleLength.paramValue),
		editMode : true,
		excludeBtns4MetaData : true,
		onAutoSave : function(){
			$('lbl_autosave').innerHTML = String.format("自动保存于:{0}", new Date().format('yyyy-mm-dd HH:MM:ss')); 
			Element.show('lbl_autosave');
			setTimeout("Element.hide('lbl_autosave')", 5000);
		}
	});
	Ext.apply(PgC, {
		OK : function(){
			try{
				var testRight = top.opener.setHTML;
			}catch(e){
				if(confirm('原记录编辑页面可能已经关闭，所以本次保存操作无效，是否关闭本页面？')){
					window.close();
				}
				return;
			}
			if(top.opener){
				if(top.opener.closed){
					if(confirm('原记录编辑页面可能已经关闭，所以本次保存操作无效，是否关闭本页面？')){
						window.close();
					}
					return;
				}
				var win = top.opener;
				if(win.setHTML){
					var oTrsEditor = $('_trs_editor_');
					var FCK = oTrsEditor.contentWindow.GetEditor();
					var oWindow = oTrsEditor.contentWindow.GetTrueEditor();
					if(oWindow.OfficeActiveX){
						oWindow.OfficeActiveX.UploadLocals();
					}
					var sHtml = FCK.QuickGetHtml(true, true);
					win.setHTML(sHtml);
				}
			}
			window.close();
		},
		Close : function(){
			window.close();
		}
	});

	/*---------------- 全屏打开编辑器 ------------------*/
	function FullOpenEditor(bFull){
		Element[bFull ? 'addClassName' : 'removeClassName']('frmAction', 'full-edit');
	}

	//add by huxuanlai 2009/12/21传递编辑器内部的参数，相当于document_edit.jsp页面向编辑器内部传参。
	var CurrDocId = getParameter('DocumentId');
	var DocChannelId = getParameter('ChannelId');
	var ChnlDocId = getParameter('ChnlDocId');

</script>
<div id="colorpicker" style="position:absolute;z-index:9999;display:none;"></div>
<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%;">
	<tr>
		<td>
			<div class="box">
				<iframe id="_trs_editor_" frameborder=0 src="editor.html?ChannelId=<%=CMyString.URLEncode(sChannelId)%>&excludeToolbar=FitWindow"  allowtransparency="true"></iframe>
			</div>
		</td>
	</tr>
	<tr>
		<td id="btns_td" style="height:30px;text-align:center;vertical-align:bottom;padding-bottom:4px;">
		</td>
	</tr>
</table>
<textarea id="_editorValue_" name="_editorValue_"></textarea>
<script language="javascript">
<!--
	if(top.opener){
		var win = top.opener;
		try{
			if(win.getHTML){
				$('_editorValue_').value = win.getHTML();
			}
		}catch(error){
			//just skip it.
		}
	}
//-->
</script>
<div id="lbl_autosave" style="display:none"></div>
<script src="../js/source/wcmlib/core/CMSObj.js"></script>
<script src="../js/source/wcmlib/core/AuthServer.js"></script>
<script src="../js/easyversion/ctrsbutton.js"></script>
<script src="../js/easyversion/ajax.js"></script>
<script src="../js/easyversion/basicdatahelper.js"></script>
<script src="../js/easyversion/web2frameadapter.js"></script>
<script src="../js/easyversion/bubblepanel.js"></script>
<script src="../js/easyversion/lockutil.js"></script>
<script src="../js/easyversion/processbar.js"></script>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../js/easyversion/colorpicker.js"></script>
<script src="../js/easyversion/yuiconnect.js"></script>
<script language="javascript">
	var m_myArrBtns = [];
	Event.observe(window, 'load', function(){
		m_myArrBtns.push({
			html : "确定",
			action : 'PgC.OK',
			id : 'okBtn'
		});
		m_myArrBtns.push({
			html : "关闭",
			action : 'PgC.Close',
			id : 'closeBtn'
		});
		//TRSButtons
		var oTRSButton = new CTRSButton('btns_td');
		oTRSButton.setButtons(m_myArrBtns);
		oTRSButton.init();
	});
</script>
<script src="../js/data/cmsobj/document.js"></script>
<script src="../js/source/wcmlib/Observable.js"></script>
<script src="../js/source/wcmlib/Component.js"></script>
<script src="../js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
</body>
</html>