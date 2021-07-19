<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.cms.content.ExtendedField" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.database.TableInfo" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.DreamFactory" %> 
<%@ page import="com.trs.components.wcm.content.persistent.DocumentShowFieldConfig" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="java.util.HashMap" %>

<%@include file="../include/public_server.jsp"%>
<%@include file="document_field_init.jsp"%>
<%
	//sql：ALTER table WCMCHANNEL add SHOWFIELDS nvarchar(100);ALTER table wcmchannel add FIELDSWIDTH NVARCHAR(100);
	//1、需要得到栏目id，将信息保存到栏目上
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	//2、获取到已设置的字段的值
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("flowemployee.object.not.found.channel", "没有找到ID为{0}的栏目"), new int[]{nChannelId}));
	}
	String sFields = currChannel.getPropertyAsString("SHOWFIELDS");
	String sFieldsWidth = currChannel.getPropertyAsString("FIELDSWIDTH");
	List list = XMLConfigServer.getInstance().getConfigObjects(DocumentShowFieldConfig.class);
	StringBuffer sbFieldName = new StringBuffer();
	StringBuffer sbFieldDesc = new StringBuffer();
	DocumentShowFieldConfig currDocumentShowFieldConfig = null;
	for(java.util.Iterator it=list.iterator(); it.hasNext(); ) {
		currDocumentShowFieldConfig = (DocumentShowFieldConfig)it.next();
		if(!currDocumentShowFieldConfig.isRead())continue;
		if(sbFieldName.length() == 0){
			sbFieldName.append(currDocumentShowFieldConfig.getFieldname());
			sbFieldDesc.append(currDocumentShowFieldConfig.getDesc());
		}else{
			sbFieldName.append(",").append(currDocumentShowFieldConfig.getFieldname());
			sbFieldDesc.append(",").append(currDocumentShowFieldConfig.getDesc());
		}
	}
	String[] allFields = sbFieldName.toString().split(",");
	String[] aFieldsDesc = sbFieldDesc.toString().split(",");
	HashMap allHasField = new HashMap();
	for(int t=0;t<allFields.length;t++){
		allHasField.put(allFields[t],aFieldsDesc[t]);
	}
	int count=1;//已有标题
	out.clear();
	String sSelectedIds = "";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="document_field_showset.jsp.title">自定义文档列表显示字段</title>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/data/locale/channel.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-list-common.css">
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<!--定义了CrashBoard对象的实现-->
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<!--定义CrashBoard窗口相关样式-->
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />

</head>
<style type="text/css">
	body{
		font-size:12px;
	}
	td{
		border:1px;
	}
	.row_order{width:35px;margin:0px 2px;border:0px;border-bottom:1px dotted silver;}
	.grid_head{
	height:25px;
	background:#d3e6fa;
	color:#133151;
}
.grid_row{
	height:25px;background:#FFFFFF;
}
</style>
<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'save',
		name : wcm.LANG.CHANNEL_TRUE||'确定'
	},{
		cmd : 'resetFields',
		name : wcm.LANG.CHANNEL_RESET_FIELDS||'恢复默认'
	}],
	size : [550, 500]
};
</script>
<script language="javascript">
<!--
	var m_FieldsMapping = {
		"WCMDocument.DOCTITLE" : 400,
		"WCMDocument.SUBDOCTITLE" : 100,
		"WCMDocument.DOCPEOPLE" : 100,
		"WCMCHNLDOC.DOCSTATUS" : 60,
		"WCMCHNLDOC.DOCRELTIME" : 80,
		"WCMCHNLDOC.CRUSER" : 70,
		"WCMCHNLDOC.CRTIME" : 80,
		"WCMCHNLDOC.DOCPUBTIME" : 80,
		"WCMCHNLDOC.OPERUSER" : 70,
		"WCMCHNLDOC.OPERTIME" : 80,
		"WCMCHNLDOC.DOCPUBURL" : 100,
		"WCMCHNLDOC.DOCCHANNEL" : 70,
		"WCMDocument.DOCKEYWORDS" : 80,
		"WCMDocument.DOCSOURCENAME" : 100,
		"WCMDocument.DOCAUTHOR" : 70,
		"WCMDocument.DOCWORDSCOUNT" : 40
	};
	function initCheck(){
		//ValidationHelper.registerValidations(m_arrValidations);
        ValidationHelper.addValidListener(function(){
            FloatPanel.disableCommand('save', false);
        }, "action_form");
        ValidationHelper.addInvalidListener(function(){
            FloatPanel.disableCommand('save', true);
        }, "action_form");
        ValidationHelper.initValidation();
    }
	Event.observe(window,'load',function(){
		initData();
		initCheck();
		Ext.get('field_div').on('keydown', function(event, target){
			if(target.name != 'order') return;
			OrderHandler.init(target);
		});
	});
	//还需要监听checkbox的click事件，当取消选择时，宽度要置为空
	Event.observe(document,'click',function(event){
		event = window.event || event;
		var srcElement = Event.element(event);
		if(srcElement.tagName.toLowerCase()!="input")return;
		if(srcElement.type.toLowerCase()!="checkbox")return;
		if(srcElement.id.toLowerCase() == "check") return;
		var FieldsName = srcElement.name;
		if(!srcElement.checked){
			$(FieldsName+"_width").value="";
		}else{
			var nWidth = m_FieldsMapping[FieldsName];
			if(!nWidth) nWidth = 60;
			$(FieldsName+"_width").value=nWidth;
		}
	});
	function initData(){
		var sFields = "<%=sFields%>";
		var sFieldsWidth = "<%=sFieldsWidth%>";
		if(sFields==""||sFields=="null")return;
		var aFields = sFields.split(',');
		var aFieldsWidth = sFieldsWidth.split(',');
		for (var i = 0; i < aFields.length; i++){
			$(aFields[i]).checked = true;
			$(aFields[i]+'_width').value = aFieldsWidth[i];
		}
	}
	function save(bResetFields){
		//需要把设置的值拼起来，组成一个字段再提交
		var elm = $('field_div').getElementsByTagName("input");
		var sFields = "";
		var sFieldsWidth = "";
		var sExtFields = "";
		var sExtFieldsWidth = "";
		var sExtFieldsIds = "";
		if(!bResetFields){
			for(var i=0;i<elm.length;i++){
				if(elm[i].type.toLowerCase()!="checkbox")continue;
				if(elm[i].id.toLowerCase()=="check") continue;
				//if(!elm[i].disabled && elm[i].checked){
				if(elm[i].checked){
					if($(elm[i].name+'_width').value.trim()==""){
						Ext.Msg.alert(wcm.LANG.CHANNEL_76 ||"选定的字段都必须设定宽度.");
						return false;
					}
					if(!elm[i].getAttribute("bExtField")){
						if(sFields==""){
							sFields += elm[i].name;
							sFieldsWidth += $(elm[i].name+'_width').value;
						}else{
							sFields += ("," + elm[i].name);
							sFieldsWidth += ("," + $(elm[i].name+'_width').value);
						}
					}else{
						if(sExtFields==""){
							sExtFields += elm[i].name;
							sExtFieldsIds += elm[i].value;
							sExtFieldsWidth += $(elm[i].name+'_width').value;
						}else{
							sExtFields += ("," + elm[i].name);
							sExtFieldsIds += ("," +elm[i].value);
							sExtFieldsWidth += ("," + $(elm[i].name+'_width').value);
						}
					}
				}
			}
		}
		if((sFields.length+sExtFields.length)>=300){
			Ext.Msg.alert(wcm.LANG.CHANNEL_80 ||"您选的字段太多，数据超长。");
			return false;
		}
		var sSelectedChnlIds = "";
		var oCheckSltChnl = document.getElementById("check");
		if(oCheckSltChnl.checked){
			sSelectedChnlIds = document.getElementById("SelectedIds").value;
		}
		var oPostData = {
			ChannelId : $('ChannelId').value,
			SHOWFIELDS : sFields,
			FIELDSWIDTH : sFieldsWidth,
			SHOWEXTFIELDS :sExtFields,
			EXTFIELDSWIDTH :sExtFieldsWidth,
			EXTFIELDSIDS : sExtFieldsIds,
			SelectedChnlIds : sSelectedChnlIds
		};
		getHelper().JspRequest(WCMConstants.WCM6_PATH + "channel/document_field_showset_dowith.jsp", oPostData,  true, function(transport, json){
			Ext.Msg.report(json,wcm.LANG.document_field_showset_1001 || "设置文档列表显示字段结果",function(){
				notifyFPCallback();
				FloatPanel.close();
			});
		});
		return false;
	}
	function resetFields(){
		return save(true);
	}
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}


	var OrderHandler = {
		init : function(dom){
			if(dom.getAttribute('inited')) return;
			dom.setAttribute('inited', true);
			dom.onblur = OrderHandler.blur;
			dom.onkeydown = OrderHandler.keydown;
		},
		destroy : function(dom){
			dom.removeAttribute('inited');
			dom.onblur = null;
			dom.onkeydown = null;
		},
		valid : function(dom){
			if(!/^\d+$/.test(dom.value)){
				Ext.Msg.alert(wcm.LANG.DOCUMENT_PROCESS_215 || '请输入合法的数字');
				dom.select();
				return false;
			}		
			return true;
		},
		blur : function(event){
			var dom = this;
			if(!OrderHandler.valid(dom)) return;
			OrderHandler.destroy(dom);
			OrderHandler.change(dom);
		},
		keydown : function(event){
			event = window.event || event;
			if(event.keyCode != 13) return;
			this.blur();
		},
		change : function(dom){
			var newOrder = dom.value;
			newOrder = parseInt(newOrder);
			if(newOrder <= 0) newOrder = 1;
			if(newOrder > count) newOrder = count;
			var oldOrder = dom.getAttribute("_value");
			oldOrder = parseInt(oldOrder);
			if(oldOrder == newOrder) return;
			//插入
			var thEle = null;
			var thEles = $('tr_'+(oldOrder+"")).getElementsByTagName("input");
			for (var i = 0; i < thEles.length; i++){
				if(thEles[i].type.toLowerCase()!="checkbox")continue;
				thEle = thEles[i];
			}
			if(thEle==null){
				Ext.Msg.alert(wcm.LANG.CHANNEL_77 ||"排序出错.");
				return;
			}
			var bChecked = thEle.checked;
			if(newOrder<oldOrder){
				$('tb').insertBefore($('tr_'+(oldOrder+"")),$('tr_'+newOrder));
				thEle.checked = bChecked;
				//先记一下被插入的元素的id,以免id冲突
				$('tr_'+oldOrder).id='tr_';
				dom.id = 'ipt_';
				//改变值
				dom.value = newOrder;
				dom.setAttribute("_value",newOrder);
				//从插入的那个元素开始，依次改变id，值
				for (var i = oldOrder-1; i >=newOrder; i--){
					//if(i==oldOrder)continue;
					$('tr_'+i).id='tr_'+(i+1);
					$('ipt_'+i).value = i+1;
					$('ipt_'+i).setAttribute("_value",(i+1));
					$('ipt_'+i).id = 'ipt_'+(i+1);
				}
			}else if(newOrder>oldOrder){
				var newEl = null;
				if($('tr_'+(newOrder+1))){
					newEl = $('tr_'+(newOrder+1));
				}else{
					newEl = $('tr_last');
				}
				$('tb').insertBefore($('tr_'+(oldOrder+"")),newEl);
				thEle.checked = bChecked;
				$('tr_'+oldOrder).id='tr_';
				dom.id = 'ipt_';
				//改变值
				dom.value = newOrder;
				dom.setAttribute("_value",newOrder);
				for (var i = oldOrder+1; i <=newOrder; i++){
					//if(i==oldOrder)continue;
					$('tr_'+i).id='tr_'+(i-1);
					$('ipt_'+i).value = i-1;
					$('ipt_'+i).setAttribute("_value",(i-1));
					$('ipt_'+i).id = 'ipt_'+(i-1);
				}
			}
			//再次改变被插入元素的id
			$('tr_').id = 'tr_' +newOrder;
			dom.id = 'ipt_'+newOrder;
		}
	};

	//选择需要同步到的栏目
	function selectChnl(event){
		Event.stop(window.event || event);
		var selectedChannelIds = document.getElementById("SelectedIds").value;
		var chnlId = <%=nChannelId%>;
		wcm.CrashBoarder.get("chnlSltCB").show({
			title : wcm.LANG.document_field_showset_1002 || "选择要同步的栏目",
			src : WCMConstants.WCM6_PATH+'include/channel_select_forCB.html',
			width: '400px',
			height: '450px',
			maskable : true,
			params:{isRadio:0,currChannelId:chnlId,ExcludeSelf:1,SELECTEDCHANNELIDS:selectedChannelIds,ExcludeTop:1,ExcludeVirtual:1,MultiSiteType:1,SiteTypes:'0,1,2,4'},
			callback : function(params){
				document.getElementById("SelectedIds").value = params[0];
            }

		});
	}
	//是否显示"选择栏目"的链接
	function showLink(){
		var oChkbox = document.getElementById("check");
		var oLink = document.getElementById("ChnlSlt");
		if(oChkbox.checked){
			if(oLink.style.display == "none"){
				oLink.style.display = "inline";
			}
		}else{
			oLink.style.display = "none";
		}
	}

//-->
</script>
<body>
<input type="hidden" name="ChannelId" id="ChannelId" value="<%=nChannelId%>" />
<input type="hidden" name="SelectedIds" id="SelectedIds" value="<%=sSelectedIds%>"/>
<form method="post" action="" id="action_form" name="action_form">
<div id="field_div" style="width:100%;height:420px;overflow:auto;">
<table cellspacing="0" cellpadding="0" class="grid_table" width="100%" height="100%">
<tbody id="tb">
<tr class="grid_head">
	<td align="center" width="60" WCMAnt:param="document_field_showset.jsp.sel">选择</td><td style="text-align:left;" width="180" WCMAnt:param="document_field_showset.jsp.fieldname">字段名</td><td style="text-align:left;" WCMAnt:param="document_field_showset.jsp.width">宽度</td><td align="center" width="50" WCMAnt:param="document_field_showset.jsp.order">排序</td>
</tr>
<%
	int n = 0;
	if(!CMyString.isEmpty(sFields)){
		if(sFields.charAt(0) == ','){
			sFields = sFields.substring(1);
		}
		if(sFieldsWidth.charAt(0) == ','){
			sFieldsWidth = sFieldsWidth.substring(1);
		}
		String[] aDBFields = sFields.split(",");
		for (n = 1; n <=aDBFields.length; n++){
			count++;
			String sTbField = aDBFields[n-1];
			String sTbFieldToShow = "";
			boolean bExtField = false;
			int nExtFieldId = 0;
			if(allHasField.get(sTbField)==null){//那就是扩展字段
				String sWhere = "OBJID=? and EXTFIELDID in (select EXTFIELDID from WCMEXTFIELD where FIELDNAME=?)";
				WCMFilter filter = new WCMFilter("WCMCONTENTEXTFIELD",sWhere,"","");
				filter.addSearchValues(nChannelId);
				filter.addSearchValues(sTbField.split("\\.")[1]);
				ContentExtFields m_oContentExtFields = ContentExtFields.openWCMObjs(null,filter);
				if(m_oContentExtFields.size()>=1){
					ContentExtField m_oContentExtField = (ContentExtField)m_oContentExtFields.getAt(0);
					sTbFieldToShow = m_oContentExtField.getDesc();
					bExtField = true;
					nExtFieldId = m_oContentExtField.getId();
				}
			}else{
				sTbFieldToShow = allHasField.get(sTbField).toString();
			}
			boolean bNeed = false;
			for (int j = 0; j < mustNeed.length; j++){
				if(sTbField.equalsIgnoreCase(mustNeed[j])){
					bNeed = true;
				}
			}
%>
		<tr class="grid_row" id="tr_<%=n%>">
		<td><input type="checkbox" name="<%=sTbField%>" id="<%=sTbField%>" value=<%=bExtField?Integer.toString(nExtFieldId):""%> <%=bNeed?"disabled checked":""%> <%=bExtField?"bExtField=true":""%>/></td><td style="text-align:left;white-space:nowrap; text-overflow:ellipsis; overflow:hidden;"><label for="<%=sTbField%>"><%=sTbFieldToShow%></label></td><td style="text-align:left;"><input type="text" class="row_order" name="<%=sTbField%>_width" id="<%=sTbField%>_width" validation="no_desc:'',type:'int',min:'1',max:'500'" value="" /></td>
		<td>
			<input type="text" id="ipt_<%=n%>" name="order" class="row_order" _value="<%=n%>" value="<%=n%>" />
		</td>
		</tr>
<%
		}
	}
%>
<%
	
	//先输出系统已有的字段
	
	int i = 0;
	int nCountDB = 0;
	for (i = 1; i <allFields.length; i++){
		String sAllDbField = allFields[i-1];
		//相关文档不该出现在文档列表显示字段中
		if (sAllDbField.toUpperCase().indexOf("DOCQUOTE")>=0)continue;
		boolean bNeed = false;
		boolean bDel = false;
		if(!CMyString.isEmpty(sFields)){
			String[] aDBFieldsToDel = sFields.split(",");
			for (int indexDel = 0; indexDel < aDBFieldsToDel.length; indexDel++){
				if(sAllDbField.equalsIgnoreCase(aDBFieldsToDel[indexDel])){
					bDel = true;
				}
			}
		}
		if(bDel){
			nCountDB++;
			continue;
		}
		//if(sAllDbField.equals("WCMDocument.DOCTITLE"))continue;
		count++;
		for (int j = 0; j < mustNeed.length; j++){
			if(sAllDbField.equalsIgnoreCase(mustNeed[j])){
				bNeed = true;
			}
		}
		int nIndex1 = (n>0)?(n+i-nCountDB-1):(n+i-nCountDB);
%>
		<tr class="grid_row" id="tr_<%=nIndex1%>">
		<td><input type="checkbox" name="<%=sAllDbField%>" id="<%=sAllDbField%>" value="" <%=bNeed?"disabled checked":""%>/></td><td style="text-align:left;white-space:nowrap; text-overflow:ellipsis; overflow:hidden;"><label for="<%=sAllDbField%>"><%=allHasField.get(sAllDbField)%></label></td><td style="text-align:left;"><input type="text" class="row_order" name="<%=sAllDbField%>_width" id="<%=sAllDbField%>_width" validation="no_desc:'',type:'int',min:'1',max:'500'" value=""/></td>
		<td>
			<input type="text" id="ipt_<%=nIndex1%>" name="order" class="row_order" _value="<%=nIndex1%>" value="<%=nIndex1%>"/>
		</td>
		</tr>
<%
	}
	//获得扩展字段集合
	int nCountExt = 0;
	ContentExtFields currExtendedFields = null;
	IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	if(currChannel != null){
		currExtendedFields  = currChannelService.getExtFields(currChannel, null);
		for (int z = 0; z < currExtendedFields.size(); z++){
			ContentExtField currExtendedField = (ContentExtField)currExtendedFields.getAt(z);
			String sDBFieldName = CMyString.transDisplay(currExtendedField.getName());
			sDBFieldName = "WCMDocument." + sDBFieldName;
			String sDBFieldDesc = CMyString.transDisplay(currExtendedField.getPropertyAsString("LOGICFIELDDESC"));
			boolean bDelExt = false;
			if(!CMyString.isEmpty(sFields)){
				String[] aDBFieldsExtToDel = sFields.split(",");
				for (int indexDele = 0; indexDele < aDBFieldsExtToDel.length; indexDele++){
					if(sDBFieldName.equalsIgnoreCase(aDBFieldsExtToDel[indexDele])){
						bDelExt = true;
					}
				}
			}
			if(bDelExt){
				nCountExt++;
				continue;
			}
			count++;
			int nIndex2 = (n>0)?(n+i+z-nCountDB-1):(n+i+z-nCountDB);
%>
		<tr class="grid_row" id="tr_<%=nIndex2%>">
		<td><input type="checkbox" name="<%=sDBFieldName%>" id="<%=sDBFieldName%>" value="<%=currExtendedField.getId()%>" bExtField="true"/></td><td style="text-align:left;white-space:nowrap; text-overflow:ellipsis; overflow:hidden;"><%=sDBFieldDesc%></td><td style="text-align:left;"><input type="text" name="<%=sDBFieldName%>_width" class="row_order" id="<%=sDBFieldName%>_width" validation="no_desc:'',type:'int',min:'0',max:'150'" value="" /></td>
		<td>
			<input id="ipt_<%=nIndex2-nCountExt%>" type="text" name="order" class="row_order" _value="<%=nIndex2-nCountExt%>" value="<%=nIndex2-nCountExt%>">
		</td>
		</tr>
<%
		}
	}
%>
	<tr style="display:none;" id="tr_last">
	</tr>
</tbody>
</table>
</div>
</form>
<div style="border-top:1px solid gray;padding-top:5px;">
	<input type="checkbox" name="check" id="check" onclick="showLink()"><label for="check" WCMAnt:param="document_field_show.jsp.synctochnl">同步到栏目</label><span style="padding-left:20px;"></span><a id="ChnlSlt" href="#" onclick="selectChnl(event)" style="display:none;">选择栏目</a>
	<br/>
	<span class="" style="color:green;font-size:10pt;" WCMAnt:param="document_field_show.jsp.note">说明:如果要同步到某个栏目A，而栏目A中没有当前栏目所包含的扩展字段，那么这个字段会被忽略</span>
</div>
<br/>

<script language="javascript">
<!--
	var count = <%=count%> - 1;
//-->
</script>
</body>
</html>