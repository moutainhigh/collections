Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : true,
	filterEnable : false,
	gridDraggable : !getParameter("doSearch"),
	serviceId : 'wcm61_viewdocument',
	methodName : 'jQuery',
	/**/
	objectType : 'chnldoc',
	initParams : {
		"FieldsToHTML" : "DocTitle,DocChannel.Name",
		"DocumentSelectFields" : "DOCID,DocTitle,DocType,CrUser,CrTime,DocEditor,AttachPic"
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		return WCMConstants.OBJ_TYPE_CHNLDOC;
	},
	rowInfo : {
		docid : true,
		channelid : true
	}
});

Ext.apply(PageContext, {
	_doBeforeLoad : function(){
		//Stop Ajax Request
		return false;
	},
	getContext : function(){
		var context0 = this.getContext0();
		var bIsSearch = !!PageContext.getParameter("IsSearch");
		if(bIsSearch){
			var context = Ext.applyIf({
				isChannel : false,
				relateType : 'docInSearch',
				host : {
					objType : 'docSearchContext',
					objId : 0,
					right : PageContext.getParameter("RightValue"),
					isVirtual : PageContext.getParameter("IsVirtual"),
					detail : location.search.substring(1)
				}
			}, context0);
			return context;
		}
		var bIsChannel = !!PageContext.getParameter("ChannelId");
		var context = Ext.applyIf({
			isChannel : bIsChannel,
			relateType : bIsChannel ? 'documentInChannel' : 'documentInSite',
			host : {
				right : PageContext.getParameter("RightValue"),
				isVirtual : PageContext.getParameter("IsVirtual"),
				objType : bIsChannel ? WCMConstants.OBJ_TYPE_CHANNEL
										: WCMConstants.OBJ_TYPE_WEBSITE,
				objId : bIsChannel ? PageContext.getParameter("ChannelId")
										: PageContext.getParameter("SiteId")
			}
		}, context0);
		return context;
	},
	/**/
	pageFilters : (function(){
		return null;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'document'
		});
		return tabs;
	}())
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG['DOC_UNIT'] || '篇',
	TypeName : wcm.LANG['DOCUMENT'] || '文档'
});
function getPrettyUrl(_sUrl, _nMaxLen, _sSkimWords){
	var nDemPos = 0;
	if(_sUrl == null || _nMaxLen <= 0 || _sUrl.length <= _nMaxLen 
		|| (nDemPos = _sUrl.lastIndexOf('/')) == -1) {
		return _sUrl;
	}
	//else
	var nFirstPartDemPos = _sUrl.lastIndexOf('://') + 3;
	var sFirstPart	= _sUrl.substr(0, nFirstPartDemPos);
	var sMidPart	= _sUrl.substring(nFirstPartDemPos, nDemPos);
	if(sMidPart.length < 3) {
		return _sUrl;
	}
	var nMidLen = (_nMaxLen + sMidPart.length - _sUrl.length);
	//alert(nMidLen + ', ' + sMidPart)
	if(nMidLen <= 3) {
		nMidLen = 3;
	}
	sMidPart = sMidPart.substr(0, nMidLen);
	sMidPart += (_sSkimWords ? _sSkimWords : '......');
	
	var sLastPart = _sUrl.substr(nDemPos);
	return sFirstPart + sMidPart + sLastPart;
}
function drawContent(){
	var sLinkHtml = [
		'<table cellspacing=0 border="1" height="120" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">',
			'<tbody id="grid_NoObjectFound">',
			'<tr>',
				'<td class="no_object_found" style="line-height:30px;" height="120">',
					'<br>' + (wcm.LANG.DOCUMENT_PROCESS_204 || '链接型栏目不允许文档管理.') + '<br>',
					(wcm.LANG.DOCUMENT_PROCESS_205 || '链接地址:') + ' <a href="{0}" id="linkUrl" title="{0}" target="_blank">{1}</a><br>',
				'</td>',
			'</tr>',
			'</tbody>',
		'</table>'
	].join('')
	var sUrl = decodeURIComponent(getParameter("linkUrl"));
	Element.update('wcm_table_grid', String.format(sLinkHtml, sUrl, getPrettyUrl(sUrl, 50)));
}
Event.observe(window, 'load', drawContent);
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afterinit : function(event){
		var context = Ext.apply({}, PageContext.getContext());
		var oCmsObjs = CMSObj.createEnumsFrom({
			objType : wcm.Grid.rowType()
		}, context);
		oCmsObjs.afterselect();
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CHANNEL,
	afteredit : function(event){
		var hostId = event.getObj().getId();
		if(hostId!=PageContext.hostId)return;
		wcm.TabManager.exec(
			wcm.TabManager.getTab(PageContext.hostType, 
			'document', PageContext.getContext()));
	}
});
