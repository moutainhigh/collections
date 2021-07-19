<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="../include/public_server.jsp"%>
<%
	String sViewName = currRequestHelper.getString("ViewName");
	String viewId = currRequestHelper.getString("ViewId");

	String scurrDocId = currRequestHelper.getString("scurrDocId");
	String scurrMetaViewId = currRequestHelper.getString("scurrMetaViewId");

	sViewName = CMyString.showNull(sViewName);
	if(CMyString.isEmpty(sViewName)){
		sViewName = LocaleServer.getString("metaview_document_relation.jsp.relationdatamgr","相关数据");
	}
	String sRelationDataTitle = CMyString.format(LocaleServer.getString("metaview_document_relation.jsp.relationdataTitle","{0}标题"),new String[]{sViewName});
	String sSettingDataTitle = CMyString.format(LocaleServer.getString("metaview_document_relation.jsp.settingdatatitle","已关联的{0}"),new String[]{sViewName});
	String sPageTitle = CMyString.format(LocaleServer.getString("metaview_document_relation.jsp.pagetitle","TRS WCM {0}管理"),new String[]{sViewName});
%>
<HTML xmlns:TRS_UI>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=8">
  <TITLE><%=sPageTitle%></TITLE>
<script language="javascript">
var PageContext = {};
var PRV_OBJ_TYPE_DOCUMENT = "Document_Relation";
var PRV_OBJ_TYPE_CHNLDOC = "ChnlDoc_Relation";
var PRV_OBJ_TYPE_CURRPAGE = "CurrPage_Relation";
</script>
</HEAD>

<BODY style="margin:0;padding:0;">
	<TABLE width="620" height="100%" border="0" cellpadding="0" cellspacing="0">
		<TR>
			<TD valign="top">
				<DIV id="docs_explorer" style="background:#FFFFFF;padding:0;height:350px;overflow:auto;">
					<TABLE width="680" height="300" border="0" cellpadding="0" cellspacing="1" style="font-size:12px;background:gray;">
						<TR bgcolor="#FFFFFF">
							<TD width="191">
								<iframe src="../include/blank.html" id="treeNav" allowtransparency="true" scrolling="auto" frameborder="0" style="width:191px;height:340px;"></iframe>
							</TD>
							<TD width="480">
								<DIV id="search" style="width:420px;height:22px;overflow:hidden;font-size:12px;display:none">
									<SPAN style="float:right;height:22px;margin-right:20px;">
									<select name="FieldName" id="FieldName" style="float:left;width:60px;height:18px;overflow:hidden;marign:-2px 4px 0 0;">
										<option value="DocTitle" WCMAnt:param="metaview_document_relations.html.title">标题</option>
										<option value="CrUser" WCMAnt:param="metaview_document_relations.html.pulishman">发稿人</option>
										<option value="DocKeywords" WCMAnt:param="metaview_document_relations.html.keywords">关键词</option>
									</select>
									<input id="SearchWord" type='text' class="input_text" style="float:left;border-color:gray;height:18px;width:100px;margin:2px 3px 0;">
									<SPAN style="float:left;border:1px solid gray;height:16px;margin-top:2px;padding:1 4px;cursor:pointer" tabIndex="1" onclick="doSearch();" WCMAnt:param="metaview_document_relations.html.search">检索</SPAN></SPAN>
								</DIV>
								<iframe id='channel_doc_list' src="../include/blank.html" allowtransparency="true" scrolling="no" frameborder="0" style="width:565px;height:340px;"></iframe>
							</TD>
						</TR>
					</TABLE>
				</DIV>
				<fieldset style="background:#FFFFFF;padding:0;height:146px;width:770px;">
					<legend style="line-height:24px;font-size:12px;"><%=sSettingDataTitle%></legend>
					<div id="curr_relations" style="width:765px;height:120px;overflow:auto;"></div>
				</fieldset>
			</TD>
		</TR>
	</TABLE>
	<textarea id="template_relations" style="display:none">
		<TABLE width="745" border="0" cellpadding="0" cellspacing="1" class="relations_grid">
			<THEAD align="center" valign="middle" class="grid_head">
				<TD width="40" WCMAnt:param="metaview_document_relations.html.sortnumber">序号</TD>
				<TD width="560"><%=sRelationDataTitle%></TD>
				<TD width="40" WCMAnt:param="metaview_document_relations.html.delete">删除</TD>
				<TD width="40" WCMAnt:param="metaview_document_relations.html.sort">排序</TD> 
			</THEAD>
			<TBODY id="ralations_tbody">
		    <for select="Relations.Relation" blankRef="noObjectFound">
			<TR align="center" valign="middle" class="grid_row" grid_rowid="{#RELDOC.Id}" relation_order="{$$INDEX}">
				<TD>
					{$$INDEX}
				</TD>
				<TD align="left">
					<div class="doctitle">{@$transHtml(#RELDOC.Title)}</div>
				</TD>
				<TD>
					<SPAN class="row_delete grid_function" grid_function="delete">&nbsp;&nbsp;</SPAN>
				</TD>
				<TD>
					<input type="text" name="order" class="row_order" _value="{$$INDEX}" value="{$$INDEX}">
				</TD>
			</TR>
			</for>
			</TBODY>
		</TABLE>
	</textarea>
	<TABLE style="display:none">
	<TBODY id="noObjectFound">
		<TR class="grid_row">
			<TD></TD><TD></TD><TD></TD><TD></TD>
		</TR>
	</TBODY>
	</TABLE>
<script src="../js/runtime/myext-debug.js"></script>
<script src="../js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/data/locale/document.js"></script>
<script src="../js/source/wcmlib/core/CMSObj.js"></script>
<script src="../js/source/wcmlib/core/AuthServer.js"></script>
<link rel="stylesheet" type="text/css" href="./metaviewdata_forallrelations_back_detail_select.css"/>
<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
<script src="../js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script>

//by CC 20120416 首先需要为这些内容赋值
var m_Relations = {};
var m_CurrDocId = <%=scurrDocId%>;
var m_CurrChannelId = '';
var m_nSiteType = "0,4";
var SELECT_TYPE = 'checkbox';
/*被关联的视图id*/
var m_nRelatedViewId = <%=scurrMetaViewId%>;
/*是否从被关联的文档id反选使用到此的源文档的序列*/
var m_bFromBackSelect = true;
/*被相关的文档id*/
var m_nRelatedDocId = <%=scurrDocId%>;
/*关联的视图id*/
var m_nRelatingViewId = <%=viewId%>;

function myClose(){
	PageContext.collectDatas();
	saveRelations();
}
function saveRelations(){
	var docIds = getRelationDocIds();
	var params = {
		RelatedDocId : m_nRelatedDocId,
		RelatingDocIds : docIds,
		RelatingViewId : m_nRelatingViewId,
		RelatingFieldName : m_Relations.RELATIONS.RELATIONFIELD
	}
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.call('wcm61_metaviewdata','saveRelatingViewDatas',params,true,function(_transport,_json){
		notifyFPCallback();
	});
}
function getRelationDocIds(){
	var relationDocIds = [];
	var relationDocs = m_Relations["RELATIONS"]["RELATION"];
	for(var i=0;i<relationDocs.length;i++){
		var relationDoc = relationDocs[i];
		var docId = relationDoc.RELDOC.ID;
		if(docId)
			relationDocIds.push(docId);
	}
	return relationDocIds.join(",");
}

function fastCreate(namespace, scope){
	scope = scope || window;
	if(namespace==null || namespace=='')return;
	var arr = namespace.split('.');
	for(var i=0,n=arr.length;i<n;i++){
		if(!scope[arr[i]]){
			scope[arr[i]] = {};
		}
		scope = scope[arr[i]];
	}
}
function getRelationsArray(relations){
	var arr = $v(relations, "RELATIONS.RELATION");
	fastCreate("RELATIONS.RELATION", relations);
	if(arr==null || arr==false){
		arr = relations["RELATIONS"]["RELATION"] = [];
	}
	else if(!Array.isArray(arr)){
		var tmpArr = relations["RELATIONS"]["RELATION"] = [];
		tmpArr.push(arr);
		arr = tmpArr;
	}
	return arr;
}
function refreshGrid(){
	Element.update($('curr_relations'), '');
	var sValue = TempEvaler.evaluateTemplater('template_relations', m_Relations, {});
	Element.update($('curr_relations'), sValue);
}
Ext.apply(PageContext, {
	collectDatas : function(){
	},
	"add" : function(_Relation){
		this.collectDatas();
		var arr = getRelationsArray(m_Relations);
		var nRelDocId = _Relation["RelDocId"];
		for(var i=0;i<arr.length;i++){
			if(nRelDocId==$v(arr[i], "RELDOC.ID"))
				return;
		}
		var oRelation = {
			RELDOC : {
				ID : nRelDocId,
				CHANNELID : _Relation["RelDocChannelId"],
				TITLE : _Relation["RelDocTitle"]
			},
			RELATIONID : 0
		};
		arr.push(oRelation);
		m_Relations["RELATIONS"]["RELATION"] = arr;
		refreshGrid();
	},
	"mydelete" : function(row){
		this.collectDatas();
		var relDocId = row.getAttribute("grid_rowid", 2);
		var arr = getRelationsArray(m_Relations);
		var result = [];
		for(var i=0;arr&&i<arr.length;i++){
			if(relDocId != $v(arr[i],"RELDOC.ID")){
				result.push(arr[i]);
			}
		}
		m_Relations["RELATIONS"]["RELATION"] = result;
		refreshGrid();
	},
	"delete" : function(row){
		CMSObj.createFrom({
			objId : row.getAttribute("grid_rowid", 2),
			objType : PRV_OBJ_TYPE_DOCUMENT
		}).afterunselect();
	},
	"deleteById" : function(_RelDocId){
		var tBody = $('ralations_tbody');
		var rows = tBody.rows;
		for(var i=0;i<rows.length;i++){
			if(_RelDocId==rows[i].getAttribute("grid_rowid", 2)){
				PageContext['mydelete'](rows[i]);
				break;
			}
		}
	}
});
function getRow(target){
	while(target!=null && target.tagName!='BODY'){
		if(target.getAttribute('grid_rowid', 2))return target;
		target = target.parentNode;
	}
	return null;
}
function doGridFunction(event, target){
	var row = getRow(target);
	if(row==null)return;
	var gridFunc = target.getAttribute('grid_function', 2);
	if(!PageContext[gridFunc])return;
	PageContext[gridFunc](row);
}

//change order start
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
			alert('请输入合法的数字');
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
		var relations = m_Relations["RELATIONS"]["RELATION"];
		var newOrder = dom.value;
		if(newOrder <= 0) newOrder = 1;
		if(newOrder > relations.length) newOrder = relations.length;
		var oldOrder = dom.getAttribute("_value");
		if(oldOrder == newOrder) return;
		OrderHandler.insert(relations, oldOrder - 1, newOrder - 1);
		refreshGrid();
	},
	insert : function(arr, from, to){
		arr.splice(to, 0, arr.splice(from, 1)[0]);
	}
};
//change order end

Event.observe(window, 'load', function(){
	var oPostData = {
		RelatedDocId : m_nRelatedDocId,
		RelatedViewId : m_nRelatedViewId,
		RelatingViewId : m_nRelatingViewId
	}
	var url = WCMConstants.WCM6_PATH + "metaviewdata/relatingdocs_creator.jsp";
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.JspRequest(url,oPostData,true,function(transport, json){
		m_Relations = eval("(" + transport.responseText + ")");
		$('treeNav').src = '../include/metaview_channel_list.jsp?MetaViewId=' + m_nRelatingViewId + '&FromBackSelect=1';
		refreshGrid();
		Ext.get('curr_relations').on('click', function(event, target){
			if(target.getAttribute('grid_function', 2)){
				return doGridFunction(event, target);
			}
		});
		Ext.get('curr_relations').on('keydown', function(event, target){
			if(target.name != 'order') return;
			OrderHandler.init(target);
		});
	});
});
$MsgCenter.on({
	objType : PRV_OBJ_TYPE_CURRPAGE,
	afterinit : function(event){
		var objs = CMSObj.createEnumsFrom({
			objType : PRV_OBJ_TYPE_DOCUMENT
		});
		var arr = getRelationsArray(m_Relations);
		for(var i=0, n=arr.length; i<n; i++){
			objs.addElement(CMSObj.createFrom({
				objId : $v(arr[i], "RELDOC.ID"),
				objType : PRV_OBJ_TYPE_DOCUMENT
			}));
		}
		objs.afterselect();
	}
});

function doSearch(){
	var oPostData = Ext.Json.toUpperCase($('channel_doc_list').src.parseQuery());
	var selFieldName = $('FieldName');
	var sFieldName = selFieldName.value;
	for (var i = 0; i < selFieldName.options.length; i++){
		delete oPostData[selFieldName.options[i].value.toUpperCase()];
	}
	oPostData[sFieldName] = $('SearchWord').value;
	$('channel_doc_list').src = '../document/document_relation_select.html?' + $toQueryStr(oPostData);
}
function mappingHostWithObjType(objType){
	switch(objType){
		case WCMConstants.OBJ_TYPE_WEBSITEROOT:
			return WCMConstants.TAB_HOST_TYPE_WEBSITEROOT;
		case WCMConstants.OBJ_TYPE_WEBSITE:
			return WCMConstants.TAB_HOST_TYPE_WEBSITE;
		case WCMConstants.OBJ_TYPE_CHANNEL:
			return WCMConstants.TAB_HOST_TYPE_CHANNEL;
		case WCMConstants.OBJ_TYPE_MYFLOWDOCLIST:
			return WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST;
		case WCMConstants.OBJ_TYPE_MYMSGLIST:
			return WCMConstants.TAB_HOST_TYPE_MYMSGLIST;
	}
}
$MsgCenter.on({
	objType : PRV_OBJ_TYPE_CHNLDOC,
	afterselect : function(event){
		var obj = event.getObjs().getAt(0);
		if(obj==null)return;

		//获取当前选择的记录所属的视图ID
		var viewId = obj.getPropertyAsInt('viewid', 0);
		//这一步骤很重要，过滤掉选择一条记录后，会同步添加到其他视图的情况
		if(m_nRelatingViewId != viewId) return;

		var relation = {
			RelDocId : obj.getPropertyAsInt('docid', 0),
			RecId : obj.getId(),
			RelDocChannelId : obj.getPropertyAsInt('channelid', 0),
			RelDocTitle : obj.getPropertyAsString('doctitle', '')
		};
		PageContext.add(relation);
	},
	afterunselect : function(event){
		var obj = event.getObjs().getAt(0);
		if(obj==null)return;
		PageContext.deleteById(obj.getPropertyAsInt('docid', 0));
	}
});
$MsgCenter.on({
	objType : PRV_OBJ_TYPE_DOCUMENT,
	afterunselect : function(event){
		var obj = event.getObjs().getAt(0);
		if(obj==null)return;
		PageContext.deleteById(obj.getId());
	}
});

</SCRIPT>
</BODY>
</HTML>