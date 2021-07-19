<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelSyn" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelSyns" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.DreamFactory" %>
<%@include file="../include/public_server.jsp"%>
<%@ include file="/app/system/status_locale.jsp"%>

<%
	//接受页面参数
	int nObjectId = currRequestHelper.getInt("ObjectId",0);
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	int nChannelType = Channel.TYPE_NORM;
	if(nChannelId > 0){
		nChannelType = Channel.findById(nChannelId).getType();
	}
	ChannelSyn currChannelSyn = null;
	String status = "",sWhereSql = "";
	int nTransmitType = 2;
	if(nObjectId == 0){
		currChannelSyn = ChannelSyn.createNewInstance();
		currChannelSyn.setStartTime(CMyDateTime.now());
		currChannelSyn.setEndTime(CMyDateTime.now().dateAdd(CMyDateTime.YEAR, 10));
		currChannelSyn.setProperty("SynTypes", "NEW");
	}else{
		currChannelSyn = ChannelSyn.findById(nObjectId);
		//参数校验
		if(currChannelSyn == null){
		    throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nObjectId),WCMTypes.getLowerSynName(false)}));
		}
		status = currChannelSyn.getStatusIds();
		nTransmitType = Integer.parseInt(currChannelSyn.getAttributeValue("TransmitType"));
		sWhereSql = CMyString.showNull(currChannelSyn.getWhereSql());
	}
	Date date = new Date();
	SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd"); 
    String times = from.format(date);  
	//遍历状态
	WCMFilter filter = new WCMFilter("", "", Status.DB_ID_NAME, Status.DB_ID_NAME);
	Statuses allStatuses = Statuses.openWCMObjs(loginUser, filter);

	boolean bHiddenDocTitle = false;
	//by CC 20120618 获取栏目是否配置了视图
	IMetaViewEmployerMgr m_oMetaViewEmployerMgr = (IMetaViewEmployerMgr) DreamFactory.createObjectById("IMetaViewEmployerMgr");
	MetaView view = null;
	view = m_oMetaViewEmployerMgr.getViewOfEmployer(Channel.findById(nChannelId));
	if(view != null){
		bHiddenDocTitle = true;
	}
%>
<HTML xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="" xmlns:TRS_UI="">
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/easyversion/resource/calendar.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
<style type="text/css">
    *{
        font-size:12px;
    }
	/*input{
		height:20px;
	}*/
    .leftTip{
        width:115px;
        text-align:left;
        padding-right:5px;
        display:inline-block;
    }
    .title{
        padding-left:20px;
        font-size:12px;
        font-weight:bold;
    }
    .row{
        padding-top:0px;
        padding-bottom:5px;
    }
    .attention{
        color:red;
        padding-top:30px;
        padding-left:20px;
        font-size:12px; 
    }
	body{
		overflow: hidden;
	}
	.calendarShow{
		width:28px;
		line-height:18px;
		display:inline;
	}
	.ext-ie .calendarShow{
		height:20px;
	}
	.ext-gecko .calendarShow{
		width:30px;
	}
	.ext-safari .calendarShow{
		width:30px;
	}
	.ext-safari .img{
		margin-left:-5px;
	}
	.input_text{
		height:20px;
		width :90px;
		border:1px solid lightgray;
		margin:0px;			
	}
	.text_param{
		background-image:url(../images/icon/Window-more.png);
		background-repeat:no-repeat;
		background-position:center center;
		cursor: pointer;
		width: 16px;
	}
</style> 
</head>

<body style="font-size:12px;">
    <form id="docSynForm">
        <input type="hidden" name="channelid" id="channelid">
        <input type="hidden" name="ObjectId" id="ObjectId">
        <input type="hidden" name="ChannelAsTarget" id="ChannelAsTarget">
        <div id="docSyn"></div>

    <div name="docSyn_template" id="docSyn_template">
        <div class="row">
            <span class="leftTip" WCMAnt:param="docsyn_dis_add_edit.jsp.synfrom">同步从</span>
			<input type="text" elname="分发开始时间" WCMAnt:paramattr="elname:docsyn_dis_add_edit.jsp.beginTime" name="SDATE" id="SDATE" value='<%=currChannelSyn.getStartTime().toString("yyyy-MM-dd")%>' class="input_text"><button type="button" id="embed1" class="calendarShow"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0 class="img"></button>
			
			<span WCMAnt:param="docsyn_dis_add_edit.jsp.synto">到</span>

			<input type="text" name="EDATE" id="EDATE" elname="分发结束时间" WCMAnt:paramattr="elname:docsyn_dis_add_edit.jsp.endTime" value='<%=currChannelSyn.getEndTime().toString("yyyy-MM-dd")%>' class="input_text"><button id="embed2" type="button" class="calendarShow"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0 class="img"></button>

			<span WCMAnt:param="docsyn_dis_add_edit.jsp.synend">结束</span>
			
        </div>
        
		
        <div class="row">
			
            <span class="leftTip" WCMAnt:param="docsyn_dis_add_edit.jsp.docTime">同步</span>
			<input type="text" name="DOCSDATE" id="DOCSDATE" elname="文档创建时间" WCMAnt:paramattr="elname:docsyn_dis_add_edit.jsp.docCreateTime" value='<%=(nObjectId == 0? times : currChannelSyn.getDocStartTime().toString("yyyy-MM-dd"))%>' class="input_text"><button id="embed3" class="calendarShow" type="button"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0 class="img"></button>
			之后创建的文档
			<span style="display:none">
				<span WCMAnt:param="docsyn_dis_add_edit.jsp.and">和</span>
				<input type="text" name="DOCEDATE" id="DOCEDATE" elname="文档创建时间" WCMAnt:paramattr="elname:docsyn_dis_add_edit.jsp.docCreateTime" value='<%=(nObjectId == 0? times : currChannelSyn.getDocEndTime().toString("yyyy-MM-dd"))%>' class="input_text"><button id="embed4" class="calendarShow" type="button"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0 class="img"></button>
            </span>
			
        </div>
		<div class="row">
            <span class="leftTip" WCMAnt:param="docsyn_dis_add_edit.jsp.trigger">同步时机:</span>
            <span>
				<input type="checkbox" value="NEW" name="syntypes" id="trigger_new"><label for="trigger_new" style="cursor:hand" WCMAnt:param="docsyn_dis_add_edit.jsp.triggeradd">新建时</label>
				<input type="checkbox" value="MODIFY" name="syntypes" id="trigger_modify"><label for="trigger_2" style="cursor:hand" WCMAnt:param="docsyn_dis_add_edit.jsp.triggermodify">修改时</label>
				<input type="checkbox" value="PUBLISH" name="syntypes" id="trigger_publish" WCMAnt:param="docsyn_dis_add_edit.jsp.triggerpub"><label for="trigger_3" style="cursor:hand">发布后同步并且</label>
				<select name="OPERAFTER" id="OPERAFTER">
					<option value="0"  WCMAnt:param="docsyn_dis_add_edit.jsp.notpubafter">不发布</option>
					<option value="1"  WCMAnt:param="docsyn_dis_add_edit.jsp.pubafter">发布</option>
				</select>
			</span>
        </div>
		<div class="row">
            <span class="leftTip" WCMAnt:param="docsyn_dis_add_edit.jsp.queryCondition">筛选条件(SQL语句):</span>
            <span><input type="text" name="WHERESQL" id="WHERESQL" value="<%=(nObjectId==0?"":sWhereSql)%>" size="40" validation="type:'string',max_len:'500',no_desc:'',showid:'validationShowId'">
			<span onclick="openSearchCondition();return false;" title="点击设置检索条件" class="text_param" WCMAnt:paramattr="title:docsyn_dis_add_edit.jsp.searchcondition" id="WhereSp">&nbsp;&nbsp;&nbsp;</span>		
                (<span><a href="#" onclick="checkSoloSQLValid('WHERESQL'); return false" WCMAnt:param="docsyn_dis_add_edit.jsp.checkTip">检验语句</a></span>)
            </span>
        </div>
        <div class="row" style="height:18px;border:1px;overflow:hidden;">
            <span class="leftTip"></span>
            <span id="validationShowId"></span>
        </div>
        <div class="row" id="syncedstatusrow" style="display:none">
            <table border=0 cellspacing=0 cellpadding=0>
            	<tr>
            		<td valign="top"><span class="leftTip" WCMAnt:param="docsyn_dis_add_edit.jsp.status">被同步文档的状态:</span></td>
                    <td valign="top">
                        <span style="width:300px;">
							<input type="hidden" name="Statuses" id="Statuses" value="">
							<%
								for(int i = 0; i < allStatuses.size(); i++){
									
									Status currStatus = (Status) allStatuses.getAt(i);
									if(currStatus == null){
										continue;
									}
									// 排除草稿和撤销审批中两个状态
									int nStatusId = currStatus.getId();
									if(nStatusId == Status.STATUS_ID_DRAFT || nStatusId == Status.STATUS_ID_IN_DESTROY || nStatusId == Status.STATUS_ID_NEW ){
										continue;
									}
								
							%>
						<input id="StatusesAss_<%=i%>" type="checkbox" name="StatusesAss" value="<%=currStatus.getId()%>"><label for="StatusesAss_<%=i%>"><%=getStatusLocale(currStatus.getDisp())%></label>
							<%
								}
							%>
                        </span>                    
                    </td>
            	</tr>
            </table>
        </div>
        <div class="row">
            <span class="leftTip" WCMAnt:param="docsyn_dis_add_edit.jsp.colstyle">同步模式:</span>
            <span style="margin-left:-6px;">
                <input type="hidden" name="Attribute" id="Attribute" value="">
                <input id="TransmitTypeAss_0" type="radio" name="TransmitTypeAss" value="1" onclick="return validSelectedInfoViewChannelType();"><label for = "TransmitTypeAss_0" WCMAnt:param="docsyn_dis_add_edit.jsp.copy">复制</label>
                <input id="TransmitTypeAss_1" type="radio" name="TransmitTypeAss" value="2"><label for = "TransmitTypeAss_1" WCMAnt:param="docsyn_dis_add_edit.jsp.quote">引用</label>
                <input id="TransmitTypeAss_2" type="radio" name="TransmitTypeAss" value="3"><label for = "TransmitTypeAss_2" WCMAnt:param="docsyn_dis_add_edit.jsp.mirror">镜像</label>
            </span>
        </div>    
        <div class="row">
            <table border=0 cellspacing=0 cellpadding=0>
            	<tr>
            		<td valign="top" style="width:80px"><span class="leftTip" WCMAnt:param="docsyn_dis_add_edit.jsp.select">目标栏目:</span></td>
                    <td valign="top">
                        <div><button type="button" onclick="selectChannel();return false;" WCMAnt:param="docsyn_dis_add_edit.jsp.choose">选择</button></div>                           
                    </td>
            	</tr>
                <tr>
                	<td style="width:80px">&nbsp;</td>
					<td style="padding-top:3px;"><input type="hidden" name="DstChannelIds" id="DstChannelIds" value="<%=(nObjectId==0?"":currChannelSyn.getToChannelId()+"")%>">
                        <div style="width:300px;height:50px;overflow-y:auto;" id="selectedChannel">
                            <div><%=(nObjectId==0?"":currChannelSyn.getToChannel().getDesc())%></div>
                        </div>                     
                    </td>
                </tr>
            </table>         
        </div>
    </div>
	</form>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/channelsyn.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<!-- Component Start -->
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<!-- Component End -->
<!--validator-->
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<!-- Calendar-->
<script language="javascript" src="../../app/js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../../app/js/easyversion/calendar_lang/$locale$.js" type="text/javascript"></script>
<script language="javascript" src="../../app/js/easyversion/calendar.js" type="text/javascript"></script>
<!--locker-->
<SCRIPT src="../../app/js/source/wcmlib/core/LockUtil.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'saveDocSyn',
			name : wcm.LANG.CHANNELSYN_SURE || '确定'
		}],
		size : [540, 420]
	};
</SCRIPT>
<script language="javascript">
	var calSpIds = ['SDATE','EDATE','DOCSDATE'];
	var calBuTds = ['embed1','embed2','embed3'];
	for (var i = 0; i < calSpIds.length; i++){
		wcm.TRSCalendar.get({
			input : calSpIds[i],
			handler : calBuTds[i]
		});
	}
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	wcm.TRSCalendar.get({
		input : 'DOCEDATE',
		handler : 'embed4',
		top : '110px',
		left : '190px'
	});
//-->
</SCRIPT>
<script language="javascript">
    var objType = 903;
    var channelId = getParameter("channelid") || 0;
    var objectId = getParameter("objectid") || 0;

    if(objectId != 0){
        LockerUtil.register2(objectId, objType, true, 'saveDocSyn');
    }

    Event.observe(window, 'load', function(){
		$('channelid').value = channelId;
        $('ObjectId').value = objectId;
        $('ChannelAsTarget').value = false;
		loadValue('<%=status%>',<%=nTransmitType%>);
		initValidation();

		// 初始化同步时机
		var sSynTypes = "<%=currChannelSyn.getPropertyAsString("SynTypes","NEW").toLowerCase()%>";
		if(sSynTypes.length>0){
			var pSynTyps = sSynTypes.split(",");
			for(var i=0; i<pSynTyps.length; i++){
				var elTrigger = $('trigger_' + pSynTyps[i]);
				if(elTrigger != null) {
					elTrigger.checked = true;
					if(pSynTyps[i] == "modify"){
						Element.show($("syncedstatusrow"));
				    }
				}
			}
		}
		$("OPERAFTER").value = "<%=currChannelSyn.getPropertyAsString("OPERAFTER", "0")%>";
    });
	Event.observe($("trigger_modify"), "click", function(){
        if($("trigger_modify").checked){
                Element.show($("syncedstatusrow"));
        }else{
                Element.hide($("syncedstatusrow"));
        }
	});
	function validSelectedInfoViewChannelType(){
		if('<%=nChannelType%>' != 13 || $('DstChannelIds').value == '')
			return true;
		else{
			var selectedChannelIds = $('DstChannelIds').value;
			var sUrl = '../../app/infoview/infoview_employer_filter2.jsp';
			sUrl += '?FromChannelId=' + <%=nChannelId%>;
			sUrl += '&ToChannelIds=' + selectedChannelIds;
			new Ajax.Request(sUrl, {'onSuccess' : 
				function(_trans, _json){
					var result = _trans.responseText.trim();
					if(parseInt(result,10) > 1){
						Ext.Msg.alert(wcm.LANG.CHANNELSYN_VALID_20 || "所选的栏目中有与源栏目使用的表单不一致!");
						$('TransmitTypeAss_0').checked = false;
						$('TransmitTypeAss_1').checked = true;
						return false;
					}
				}
			});
			return true;
		}
	}
	function loadValue(status,nTransmitType){
		var objArray = document.getElementsByName('TransmitTypeAss');
        for (var i = 0; i < objArray.length; i++){//获得分发模式
            if(nTransmitType == objArray[i].value){
                objArray[i].checked = true;
                break;
            }
        }
		if(status == ""){
			status ="1,2,3,10,15,16,18";
		}
		var objArray1 = document.getElementsByName('StatusesAss');
		var statusGroup = status.split(",");
		for(var i = 0;i < statusGroup.length; i++){
			 for (var j = 0; j < objArray1.length; j++){//获得允许分发的状态
				if(statusGroup[i] == objArray1[j].value){
					objArray1[j].checked = true;
				}
			}
		}
	}
    function initValidation(){//页面进行校验时的初始化
        ValidationHelper.addValidListener(function(){
            FloatPanel.disableCommand('saveDocSyn', false);
        }, "docSynForm");
        ValidationHelper.addInvalidListener(function(){
            FloatPanel.disableCommand('saveDocSyn', true);
        }, "docSynForm");
        ValidationHelper.initValidation(); 
    }
	function $compareDate(dateStr1, dateStr2, dateFormat){
		dateFormat = dateFormat || "%y-%m-%d";
		date1 = Date.parseDate(dateStr1, dateFormat);
		date2 = Date.parseDate(dateStr2, dateFormat);
		return date1.getTime() - date2.getTime();
	}
    function isValid(){
        var sDate = $F("SDATE");
        var eDate = $F("EDATE");

        if($compareDate(sDate, eDate) > 0){
            Ext.Msg.alert(wcm.LANG.CHANNELSYN_VALID_35 ||"分发开始时间大于分发结束时间");
            return false;
        }
        var docSDate = $F("DOCSDATE");
        var docEDate = $F("DOCEDATE");
		
		//服务器端分发的时候只是根据开始时间字段来分发，结束时间字段是历史遗留字段，目前在界面中已经隐藏，
		//不需要进行范围的校验
		/*
        if($compareDate(docSDate, docEDate) > 0){
            Ext.Msg.alert(wcm.LANG.CHANNELSYN_VALID_2 ||"文档创建时间范围错误");
            return false;
        }*/
        if($('DstChannelIds').value.trim() == "" || $('DstChannelIds').value == "0"){
            Ext.Msg.alert(wcm.LANG.CHANNELSYN_VALID_22 ||"请选择要分发到哪个/些栏目");
            return false;
        }
		if(!($('trigger_new').checked || $('trigger_modify').checked || $('trigger_publish').checked)){
			Ext.Msg.alert('请至少选择一个同步时机');
			return false;
		}
        return true;
    }

    function saveDocSyn(){//保存文档同步信息
       if(!isValid()){
            return false;
        }
        //配置了过滤条件
        var eText = $('WHERESQL');
        if(eText.value.trim() != ''){
            var oPostData = {
                channelid   : channelId,
                queryby     : eText.value
            }
			doCheck(oPostData,true, eText);
			return false;
        }
        return saveAction();
    }

    //执行实际的保存操作
    function saveAction(){
        var objArray = document.getElementsByName('TransmitTypeAss');
        for (var i = 0; i < objArray.length; i++){//获得分发模式
            if(objArray[i].checked){
                $('Attribute').value = 'TransmitType=' + objArray[i].value;
                break;
            }
        }
        objArray = document.getElementsByName('StatusesAss');
        var statusArray = [];
        for (var i = 0; i < objArray.length; i++){//获得允许分发的状态
            if(objArray[i].checked){
                statusArray.push(objArray[i].value);
            }
        }
        $('Statuses').value = statusArray.join(",");

		var oHelper = new com.trs.web2frame.BasicDataHelper();
        oHelper.Call('wcm6_documentSyn', 'save', 'docSynForm', true, function(oTransport, oJson){
			notifyFPCallback(oTransport);
            FloatPanel.close();
        }); 
		
        return false;
    }

    function selectChannel(){
        var dstChannelIds = $("DstChannelIds").value;
        //var positions = Position.getAbsolutePositionInTop(element);
		var link = WCMConstants.WCM6_PATH + 'channelsyn/channel_select.html';
		var cb = wcm.CrashBoarder.get('select_channel').show({
			title :wcm.LANG.CHANNELSYN_VALID_4 ||'选择栏目',
			src : link,
			width: '300px',
			height: '280px',
			maskable : true,
			params : [channelId, objectId, dstChannelIds, $$F("TransmitTypeAss"), true, <%=nChannelType%>],
			callback : function(params){
				if(!params) return false;
				$("DstChannelIds").value = (params.ids).join(",");
				var innerContent = '';
				for (var i = 0; i < (params.names).length; i+=2){
					innerContent += "<div><span style='width:45%;text-align:left;'>" + (params.names)[i] + "</span>";
					innerContent += "<span style='width:45%;text-align:left;'>" + ((params.names)[i+1] || "") + "</span></div>";
				}
				$('selectedChannel').innerHTML = innerContent;
				var cbr = wcm.CrashBoarder.get("select_channel");
				cbr.close();
			}
		});
    }

    function checkSoloSQLValid(_sTextId){
        var eText = $(_sTextId);
        if(eText == null || eText.value.trim() == '' || eText.value.length > 500) {
            return;
        }
        var oPostData = {
            channelid   : channelId,
            queryby     : eText.value
        }
        doCheck(oPostData,false, _sTextId);        
    }

    function doCheck(_oPostData, _bHasProcessBar, _sTextId){
        var oHelper = new com.trs.web2frame.BasicDataHelper();
        oHelper.call('wcm6_channel','checkSQLValid', _oPostData, true, function(_trans, _json){
            var sSql = $v(_json, 'sql');
            var sError = $v(_json, 'error');
            if(sError == null) {
				if(!_bHasProcessBar){
					Ext.Msg.alert(wcm.LANG.CHANNELSYN_VALID_5 ||"验证成功!");
				}else{
					saveAction();
				}
            }else{
				Ext.Msg.warn("ERROR : " + sError + "  SQL : " + sSql);
            }
        });
    }
	function openSearchCondition(){
		var lastValue = $('WHERESQL').value;
		$('WHERESQL').value = "";
		var cb = wcm.CrashBoarder.get('selectSearchConditonWay').show({
			title : wcm.LANG.CHANNELSYN_VALID_41 || '选择筛选条件',
			src : WCMConstants.WCM6_PATH + 'channel/channel_get_search_condition.jsp',
			width:'530px',
			height : '250px',
			maskable : true,
			params : {initValue : lastValue,
				bHiddenDocTitle : <%=bHiddenDocTitle%>,
				wcmdoconly : 'true'//目前的检索条件仅仅支持WCMDOCUMENT表的检索
			},
			callback : function(queryValue){	
				if(queryValue){
					$('WHERESQL').value = queryValue;
				}else{
					$('WHERESQL').value = lastValue;
				}
				$('WHERESQL').focus();

				var cbr = wcm.CrashBoarder.get("selectSearchConditonWay");
				cbr.close();
			}
		});
	}
</script>
</body>
</html>