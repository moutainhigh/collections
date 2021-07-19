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
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.common.publish.widget.Masters" %>
<%@ page import="com.trs.components.common.publish.widget.Master" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@include file="../include/public_server.jsp"%>

<%!
    private IPublishFolder findPublishFolder(int nObjectType, int nObjectId) throws WCMException {
        IPublishElement publishElement = PublishElementFactory.lookupElement(
                nObjectType, nObjectId);
        if (publishElement == null) {
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,
                    CMyString.format(LocaleServer.getString("template_addedit_label_1","指定的对象不存在!(Type= {0} , Id= {1} )"),new int[]{nObjectType,nObjectId}));
        }
        if (!(publishElement instanceof IPublishFolder)) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,  CMyString.format(LocaleServer.getString("template_addedit_label_2","指定的对象({0})不是Folder!"), new String[]{publishElement.getInfo()}));
        }
        return (IPublishFolder) publishElement;
    }
%>
<%
	int nObjId = currRequestHelper.getInt("objectid",0);
	Template currTemplate = null;
	boolean bEdit = false;
	if(nObjId>0){
		bEdit = true;
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
	//获取含有指定类型的母版
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	HashMap parameters = new HashMap();
	parameters.put("MasterType",String.valueOf(Master.MASTER_TYPE_INDEX));
	Masters outlineMasters = (Masters)processor.excute("wcm61_master","query",parameters);
	processor.reset();
	parameters.put("MasterType",String.valueOf(Master.MASTER_TYPE_DETAIL));
	Masters detailMasters = (Masters)processor.excute("wcm61_master","query",parameters);

%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title id="page_title">
	<%=sTitle%>
</title>
<link rel="stylesheet" type="text/css" href="../../app/js/resource/widget.css">
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<link href="../js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="visualtemplate_add_edit.css">
</HEAD>
<style type="text/css">
	html,body{
		padding:0px;
		margin:0px;
		width:100%;
		height:100%;
	}
	.master_list{
		padding:5px;
	}
	.master_alert{
		text-align:center;
		line-height:60px;
	}
</style>
<BODY>
<form id="addEditForm" method=post onsubmit="PageContext.saveTemplate(this); return false;">
<input type="hidden" name="HostType" id="hdHostType" value="<%=nHosttype%>">
<input type="hidden" name="HostId"	 id="hdHostId" value="<%=nHostid%>">
<input type="hidden" name="RootId"	 id="hdRootId" value="<%=nRootId%>">
<input type="hidden" name="ObjectId" id="hdObjectId" value="<%=nObjId%>">
<input type="hidden" name="MasterId" id="hdMasterId" value="0">

<div id="tbBox" border="0" cellspacing="1" cellpadding="0" style="height:100%;">
	<fieldset>
		<legend WCMAnt:param="visualtemplate_add_edit.jsp.information">基本信息</legend>
		<div class="dvbox">
			<span class="template_attr_name" WCMAnt:param="template_add_edit.jsp.tempname">模板名称:</span>
			<span>
				<input type="text" class="inputtext" name="TempName" id="txt_temp_name" value="<%=sTempName%>" _rawValue="<%=sTempName%>" validation="type:'filename',required:true,max_len:'50', rpc:'checkTempName'">
			</span>
		</div>
		<div class="dvbox">
			<span class="template_attr_name template_attr_name_en" WCMAnt:param="template_add_edit.jsp.tempdesc">模板描述:</span>
			<span>
				<input type="text" class="inputtext" name="TempDesc" id="TempDesc" value="<%=sTempDesc%>" validation="type:'string',max_len:'200'">
			</span>
		</div>
		<div class="dvbox">
			<span class="template_attr_name" WCMAnt:param="template_add_edit.jsp.tempType">模板类型:</span>
			<span>
				<select name="TempType" id="selTempType" onchange="PageContext.determinDisplay()" <%=bEdit ? "disabled" : ""%>>
					<option value="-1" WCMAnt:param="template_add_edit.jsp.select">=请选择=</option>
					<option value="1" WCMAnt:param="template_add_edit.jsp.outline">概览</option>
					<option value="2" WCMAnt:param="template_add_edit.jsp.detail">细览</option>
				</select>&nbsp;<span style="color: red; font-size: 12px;">*</span>
			</span>
		</div>
		<div class="dvbox">
			<span id="divTempFileExtAttr" style="display: none;">
				<span class="template_attr_name" WCMAnt:param="template_add_edit.jsp.extendname">文件扩展名:</span>
				<span>
					<input id="txtTempExt" class="inputtext" type="text" name="TempExt" value="<%=sTempExt%>" validation="type:'common_char',required:'',max_len:'50',showid:'txtTempExtMsg'">
				</span>
			</span>
			<span id="txtTempExtMsg" style="margin-top:5px;display:none;"></span>
		</div>
		<div class="dvbox">
			<span id="divTempOutputFileAttr" style="display: none">
				<span class="template_attr_name" WCMAnt:param="template_add_edit.jsp.outputname">发布文件名:</span>
				<span>
					<input id="txtFileName" class="inputtext" type="text" name="OutputFileName" value="<%=CMyString.filterForHTMLValue(sOutputFileName)%>" validation="type:'common_char',required:'',max_len:'50',showid:'txtFileNameMsg'" />
				</span>
			</span>
			<span id="txtFileNameMsg" style="margin-top:5px;display:none;"></span>
		</div>
	</fieldset>
	<div id="masterbox" style="display:none;height:120px;">
		<fieldset>
		<legend WCMAnt:param="visualtemplate_add_edit.jsp.stampers">选择母版</legend>
				<div id="outline_thumb_list" style="display:none;overflow:auto;height:150px;">
				<%
					Master master = null;
					for(int i= outlineMasters.size()-1;i>=0;i--){
						master = (Master)outlineMasters.getAt(i);
						if(master == null) continue;
						String sViewName = master.getPicFileName();
						String sPicFileName = mapFile(sViewName);
						String sViewURL = "";
						if(CMyString.isEmpty(sViewName) || sViewName.equals("0") || sViewName.equalsIgnoreCase("none.gif")){
							sViewURL = "../special/images/zt_wt.gif";
						}else{
							sViewURL = "../special/file/read_image.jsp?FileName=" + sViewName;	
						}
				%>
						<div class="master_list"><a class="piclook" href="<%=CMyString.filterForHTMLValue(sPicFileName)%>"><img class="imageborder" title="看大图" src="<%=CMyString.filterForHTMLValue(sViewURL)%>" border=0 alt="" width="100px;" height="100px"/></a><br/>
						<span style="padding-top:5px;height:20px;display:inline-block;vertical-align:middle;"><label for="<%=master.getId()%>" title="<%=CMyString.filterForHTMLValue(master.getMName())%>" style="padding-left:5px;"><input type="radio" name="MASTER_VIEW" ignore=1 id="<%=master.getId()%>" value="<%=master.getId()%>" style="VERTICAL-ALIGN: middle;width:20px;border:0"/><%=CMyString.transDisplay(CMyString.truncateStr(master.getMName(),10))%></label>
						</span>
						</div>
				<%
					}
					if(outlineMasters.size()==0){
					%>
						<div class="master_alert">没有可用的概览母板,请确认是否已经进行了专题的初始化操作！</div>
					<%
					}
				%>
				</div>
				<div id="detail_thumb_list" style="display:none;overflow:auto;height:140px;">
				 
				<%
					Master detailmaster = null;
					for(int i= detailMasters.size()-1;i>=0;i--){
						detailmaster = (Master)detailMasters.getAt(i);
						if(detailmaster == null) continue;
						String sThumbName = detailmaster.getPicFileName();
						String sPicFileName2 = mapFile(sThumbName);
						String sViewURL = "";
						if(CMyString.isEmpty(sThumbName) || sThumbName.equals("0") || sThumbName.equalsIgnoreCase("none.gif")){
							sViewURL = "../special/images/zt_wt.gif";
						}else{
							sViewURL = "../special/file/read_image.jsp?FileName=" + sThumbName;	
						}

				%>
						<div class="master_list"><img src="<%=CMyString.filterForHTMLValue(sViewURL)%>" border=0 alt="" width="100px;" height="100px"/><br/>
						<span style="padding-top:5px;height:20px;display:inline-block;vertical-align:middle;">
						<label for="<%=detailmaster.getId()%>" title="<%=CMyString.filterForHTMLValue(detailmaster.getMName())%>" style="padding-left:5px;"><input type="radio" name="MASTER_VIEW" ignore=1 id="<%=master.getId()%>" value="<%=detailmaster.getId()%>" style="VERTICAL-ALIGN: middle;width:20px;border:0;padding-top:3px;"/><%=CMyString.transDisplay(CMyString.truncateStr(detailmaster.getMName(),10))%></label>
						</span>
						</div>
				<%
					}
					if(detailMasters.size()==0){
					%>
						<div class="master_alert">没有可用的细览母板,请确认是否已经进行了专题的初始化操作！</div>
					<%
					}
				%>
				</div>
				<div>
					&nbsp;
				</div>
		</fieldset>
	</div>
</div>
</form>
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
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<!--wcm-dialog end-->
<SCRIPT src="../../app/js/source/wcmlib/FixFF.js"></SCRIPT>
<script label="PageScope" src="visualtemplate_add_edit.js"></script>
<script language="javascript" src="../template/trsad_config.jsp" type="text/javascript"></script>
<!--jquery flyout start-->
<script src="../../app/js/opensource/jquery-1.6.2.min.js"></script>
<script src="../../app/js/opensource/jquery.easing.1.3.js"></script>
<script src="../../app/js/opensource/jquery.flyout-1.2.min.js"></script>
<script language="javascript">
<!--
	jQuery.noConflict();//解决$命名冲突
//-->
</script>
<!--jquery flyout end-->
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
<script type="text/javascript">
imgFly();
</script>
<%!
	private String mapFile(String _sFileName){
		if(CMyString.isEmpty(_sFileName) || _sFileName.equals("0") || _sFileName.equalsIgnoreCase("none.gif")){
			return "../special/images/zt_wt.gif";
		}else{
			return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
		}
	}
%>
</BODY>
</HTML>