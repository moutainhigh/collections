<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.PublishConstants"%>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder"%>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement"%>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory"%>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_server.jsp"%>

<%!

    private IPublishFolder findPublishFolder(int nObjectType, int nObjectId) throws WCMException {
        IPublishElement publishElement = PublishElementFactory.lookupElement(
                nObjectType, nObjectId);
        if (publishElement == null) {
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,
                    CMyString.format(LocaleServer.getString("template_addedit_label_1","指定的对象不存在!(Type= {0} , Id= {1} )"),new String[]{nObjectType 
				+ "",String.valueOf(nObjectId)}));
        }
        if (!(publishElement instanceof IPublishFolder)) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,  CMyString.format(LocaleServer.getString("template_addedit_label_2","指定的对象({0})不是Folder!"),new String[]{publishElement.getInfo()}));
        }
        return (IPublishFolder) publishElement;
    }
%>
<%
	int nObjId = currRequestHelper.getInt("objectid",0);
	Template currTemplate = null;
	if(nObjId>0){
		currTemplate = Template.findById(nObjId);
		if(currTemplate == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nObjId),WCMTypes.getLowerObjName(Template.OBJ_TYPE)}));
		}
	}else{
		currTemplate = Template.createNewInstance();
	}
		
	String sTempName = CMyString.filterForHTMLValue(currTemplate.getName());
	String sTempDesc = CMyString.filterForHTMLValue(currTemplate.getDesc());
	String sTempExt = (nObjId>0)?CMyString.filterForHTMLValue(currTemplate.getOutputFileExt()):PublishConstants.DEF_PAGE_FILEEXT;
	//String sTempExt = CMyString.filterForHTMLValue(currTemplate.getOutputFileExt());
	String sOutputFileName = (nObjId>0)?currTemplate.getOutputFileName():PublishConstants.DEF_PAGE_FILENAME_OUTLINE;
	String sText = (nObjId>0)?currTemplate.getText():"";
	int nTempType = (nObjId>0)?currTemplate.getType():(-1);
	int nHosttype = currRequestHelper.getInt("hosttype",0);
	if(nHosttype==0){
		nHosttype = currTemplate.getFolderType();
	}
	int nHostid = currRequestHelper.getInt("hostid",0);
	if(nHostid==0){
		nHostid = currTemplate.getFolderId();
	}
	int nRootId = (nObjId>0)?currTemplate.getRootId():nHostid;
	if(nObjId==0){
		IPublishFolder publishElement = findPublishFolder(nHosttype, nHostid);
		nRootId = publishElement.getRoot().getId();
	}
	String sTitle = (nObjId>0)?LocaleServer.getString("template_addedit.label.edit", "编辑模板"):LocaleServer.getString("template_addedit.label.new", "新建模板");

%>
<%
	out.clear();
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", -1);
	//prevents caching at the proxy server
	response.setDateHeader("max-age", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title id="page_title">
	<%=sTitle%>
</title>

<link rel="stylesheet" type="text/css" href="../../app/js/resource/widget.css">
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<link href="../js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="template_add_edit.css" WCMAnt:locale="template_add_edit_$locale$.css">




</HEAD>
<BODY>

<form id="addEditForm" method=post onsubmit="PageContext.saveTemplate(this); return false;">
<table id="tbBox" border="0" cellspacing="1" cellpadding="0" style="width:100%;height:100%;table-layout:fixed;">
<tbody>
	<tr>
		<td id="column_summary" valign="top" style="height:100px;">
		<div id="template_attribute"></div>
		<input type="hidden" name="HostType" id="hdHostType" value="<%=nHosttype%>">
		<input type="hidden" name="HostId"	 id="hdHostId" value="<%=nHostid%>">
		<input type="hidden" name="RootId"	 id="hdRootId" value="<%=nRootId%>">
		<input type="hidden" name="ObjectId" id="hdObjectId" value="<%=nObjId%>">
		
			<table border="0" cellspacing="1" cellpadding="3" width="100%" style="background: silver" class="tab_summary">
			<tbody>
				<tr>
					<td width="50%">
						<span class="template_attr_name" WCMAnt:param="template_add_edit.jsp.tempname">模板名称:</span>
						<span>
							<input type="text" name="TempName" id="txt_temp_name" value="<%=sTempName%>" _rawValue="<%=sTempName%>" validation="type:'filename',required:true,max_len:'50', rpc:'checkTempName'">
						</span>
					</td>
					<td>&nbsp;
						<span class="template_attr_name template_attr_name_en" WCMAnt:param="template_add_edit.jsp.tempdesc">模板描述:</span>
						<span>
							<input type="text" name="TempDesc" id="TempDesc" value="<%=sTempDesc%>" validation="type:'string',max_len:'200'">
						</span>
					</td>
				</tr>
				<tr class="sec-row">
					<td class="row_en">
						<span class="template_attr_name" WCMAnt:param="template_add_edit.jsp.tempType">模板类型:</span>
						<span>
							<select name="TempType" id="selTempType" onchange="PageContext.determinDisplay()">
								<option value="-1" WCMAnt:param="template_add_edit.jsp.select">=请选择=</option>
								<option value="1" WCMAnt:param="template_add_edit.jsp.outline">概览</option>
								<option value="2" WCMAnt:param="template_add_edit.jsp.detail">细览</option>
								<option value="0" WCMAnt:param="template_add_edit.jsp.nest">嵌套</option>
								<option value="3" WCMAnt:param="template_add_edit.jsp.infoviewprint">表单打印</option>
							</select>&nbsp;<span style="color: red; font-size: 12px;">*</span>
						</span>
						
						<span id="divTempFileExtAttr" style="display: none;position:relative;left:40px;">
							<span class="template_attr_name" WCMAnt:param="template_add_edit.jsp.extendname">文件扩展名:</span>
							<span>
								<input id="txtTempExt" type="text" name="TempExt" value="<%=sTempExt%>" class=""  validation="type:'common_char',required:'',max_len:'50',showid:'txtTempExtMsg'">
							</span>
						</span><br/>
						<span id="txtTempExtMsg" class="TempExtshow" style="margin-top:5px;display:none;"></span>
					</td>
					<td class="row_en">
						<span id="divTempOutputFileAttr" style="display: none">&nbsp;
							<span class="template_attr_name publish_file_name" WCMAnt:param="template_add_edit.jsp.outputname">发布文件名:</span>
							<span>
								<input id="txtFileName" type="text" name="OutputFileName" value="<%=CMyString.filterForHTMLValue(sOutputFileName)%>" validation="type:'common_char',required:'',max_len:'50',showid:'txtFileNameMsg'" />
							</span>
						</span>
						<span id="txtFileNameMsg" style="margin-top:5px;display:none;"></span>
					</td>
				</tr>
			</tbody>
			</table>
		<!--</textarea>-->
		</td>
	</tr>
	<tr>
		<td id="column_content" valign="top">
			<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%;">
				<tr>
					<td id="template_operators">
							<span><a href="#" onclick="switchWizardDisp(this); return false;" title="展开TRS置标添加向导" WCMAnt:paramattr="title:template_add_edit.jsp.open_guide" id="lnkWizardSwitcher" WCMAnt:param="template_add_edit.jsp.open_guide">展开TRS置标添加向导</a> | </span>
							<span><a href="# "onclick="selectSourceTemp(); return false;" WCMAnt:param="template_add_edit.jsp.template_plaster">模板粘贴</a> | </span>
							<span><a href="#" onclick="PageContext.validateTemplate(); return false;" WCMAnt:param="template_add_edit.jsp.template_check">模板校验</a> | </span>
							<span><a href="#" onclick="PageContext.validateWCAG2Template(); return false;" WCMAnt:param="template_add_edit.jsp.template_WCAG2check">无障碍校验</a></span>
							<span style="display: none" id="spInsertAd"> | <a href="#" id="lnk_insertAd" WCMAnt:param="template_add_edit.jsp.insert_ads">插入广告位</a></span>
							<!--<span><a href="#" onclick="switchDisp(this); return false;" title="置标高亮显示模式">置标高亮显示模式</a></span>-->
					</td>
				</tr>
				<tr id="divWinzard" style="display:none;">
					<td style="height:260px;border-bottom: solid 1px gray;">

					<table border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;height:100%;width:100%;">
						<tr>
							<td id="divWiz_Left" style="width:160px;background: #efefef;">
					<div class="circleBox">
						<ol>
							<li id="step_clue_1" class="listOrderCurrent"><span WCMAnt:param="template_add_edit.jsp.select_type" class="lispan">选择置标类型</span></li>
							<li id="step_clue_2"><span WCMAnt:param="template_add_edit.jsp.edit_attr" class="lispan">编辑置标属性</span></li>
							<li id="step_clue_3"><span WCMAnt:param="template_add_edit.jsp.preview" class="lispan">预览</span></li>
						</ol>
					</div>
							</td>
							<td id="divWiz_Middle">					
					<div style="height:100%;" class="dem_WinzardBorad">
						<span id="spFrmStep" style="border: 0px solid blue; width: 100%; height: 100%;">
							<IFRAME id="frmStep" frameborder="0" vspace="0" src="../template/template_wizard_step1.html" scrolling="NO" noresize></IFRAME>
						</span>
						<span id="spStepingPanel" style="display: none; width: 100%; height: 100%; background-image: url(../images/icon/waiting2.gif); background-repeat: no-repeat; background-position: center center;"></span>
					</div>
							</td>
							<td style="width:150px;" id="divWiz_Right">
					<div style="height: 100%;text-align: center; padding-top: 20px; background: aliceblue;" class="dem_WinzardBorad">
						<div>
							<button href="#" type="button" id="lnk_first" onclick="$winzard.stepFirst();" disabled WCMAnt:param="template_add_edit.jsp.return">返回</button>
						</div>
						<div>
							<button href="#" id="lnk_pre" type="button" onclick="$winzard.stepPre();return false;" disabled WCMAnt:param="template_add_edit.jsp.front">上一步</button>
						</div>
						<div>
							<button href="#" id="lnk_next" type="button" onclick="$winzard.stepNext();return false;" WCMAnt:param="template_add_edit.jsp.next">下一步</button>
						</div>
						<div>
							<button href="#" id="lnk_next" type="button" onclick="switchWizardDisp($('lnkWizardSwitcher'));" WCMAnt:param="template_add_edit.jsp.hidden">隐藏</button>
						</div>
						<div id="divCopyOpts" style="display: none;">
							<div>
								<button id="lnk_insert" type="button" onclick="$winzard.insert();"  WCMAnt:param="template_add_edit.jsp.insert_cursor">插入到光标处</button>
							</div>
							<div>
								<button id="lnk_copy" type="button" onclick="$winzard.copy();"  WCMAnt:param="template_add_edit.jsp.copy_content">复制置标内容</button>
							</div>							
						</div>

					</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td id="template_text_content">
				<textarea name="TempText" class="xml" id="txtContent"  style="width:100%;height:100%;"><%=CMyString.filterForHTMLValue(sText)%></textarea>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr style="height:40px">
		<td id="column_footer" colspan="2">
			<span><input id="btnSubmit" WCMAnt:paramattr="value:template_add_edit.jsp.edit_submit" type="submit" value="保存" ></span>&nbsp;
			<span><input type="button" WCMAnt:paramattr="value:template_add_edit.jsp.edit_cancel" value="取消" onclick="window.close(); return false;"></span>&nbsp;
		</td>
	</tr>
</tbody>
</table>
</form>
<div id="divMask" style="position: absolute; z-index: 999; -moz-opacity:10; filter:alpha(opacity=10); background: #fff; display: none">&nbsp;</div>
<script src="../js/easyversion/lightbase.js"></script>
<script src="../js/easyversion/extrender.js"></script>
<script src="../js/easyversion/elementmore.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/data/locale/template.js"></script>
<script src="../js/data/locale/system.js"></script>
<script src="../js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../js/easyversion/ajax.js"></script>
<script src="../js/easyversion/basicdatahelper.js"></script>
<script src="../js/easyversion/web2frameadapter.js"></script>
<script src="../js/easyversion/crashboard.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<script src="../../app/js/source/wcmlib/core/LockUtil.js"></script>
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<!--wcm-dialog end-->
<SCRIPT src="../../app/js/source/wcmlib/FixFF.js"></SCRIPT>
<script label="PageScope" src="template_add_edit.js"></script>
<script language="javascript" src="../template/trsad_config.jsp" type="text/javascript"></script>
<script language="javascript">
<!--
	function loadAdditionalOptions(){
		loadTRSAdOption();
	}
	
	function loadTRSAdOption(){
		//广告选件已打开
		var strsAdCon = trsad_config['root_path'];
		if(strsAdCon==null)return;
		var nStrLen = strsAdCon.length;
		if(strsAdCon.charAt(nStrLen-1)!='/'){
			strsAdCon = strsAdCon + '/';
		}
		strsAdCon = strsAdCon + 'adintrs/';
		if(trsad_config && trsad_config['enable'] == true) {
			Element.show('spInsertAd');
			Event.observe($('lnk_insertAd'), 'click', function(){
				try{
					var sWcmurl = "http://"+window.location.host+"/wcm/app/template/adintrs_intoTemp.jsp";
					var cb = wcm.CrashBoard.get({
						id : 'adintrs_sel',
						width:'600px',
						height:'600px',
						title : wcm.LANG.TEMPLATE_47 ||'选择广告',
						url : WCMConstants.WCM6_PATH + 'template/dialog_window.html',
						params : {URL:7,WCMURL:sWcmurl},
						btns : false,
						callback : function(params){
							var sResult = params.result;
							sResult = sResult.replace("${admanage_root_path}", strsAdCon);
							sResult = '<script src="' + sResult + '" IGNOREAPD="1"></'+ 'script>';
							insertToTextArea(sResult);
						},
						cancel : wcm.LANG.CANCEL ||'取消'
					});
					cb.show();				
				}catch(err){
					//TODO
					alert((wcm.LANG.TEMPLATE_48 ||'插入广告位出错：') + err.message);
				}
				return false;
			}, false);
		}
	};
	
//-->
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	$('selTempType').value="<%=nTempType%>";
//-->
</SCRIPT>
</BODY>
</HTML>