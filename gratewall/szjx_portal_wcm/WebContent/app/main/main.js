window._sessionId = window.setTimeout(function(){
	new Image().src = 'main/refresh.jsp?r='+new Date().getTime();
	window._sessionId = window.setTimeout(arguments.callee, nLoginTimeoutLimitValue);
}, nLoginTimeoutLimitValue);
Event.observe(window, 'unload', function(){
	window.clearTimeout(window._sessionId);
});
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
		case WCMConstants.OBJ_TYPE_TRSSERVERLIST:
			return WCMConstants.TAB_HOST_TYPE_TRSSERVERLIST;
		case WCMConstants.OBJ_TYPE_METAVIEW:
			return WCMConstants.TAB_HOST_TYPE_METAVIEW;
		case WCMConstants.OBJ_TYPE_CLASSINFO:
			return WCMConstants.TAB_HOST_TYPE_CLASSINFO;
	}
}
//TabManager
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_TREENODE,
	beforeclick : function(event){
		var context = event.getContext();
		if(!context || !context.navTree) return;
		try{
			if(context.siteType && !g_IsRegister[context.siteType]){
				alert(wcm.LANG['PLUGIN_NOREG'] || "此选件未正确安装或您没有购买此选件！");
				return false;
			}
			if(context.channelType == 13 && !g_IsRegister['infoview']){
				alert(wcm.LANG['PLUGIN_NOREG'] || "此选件未正确安装或您没有购买此选件！");
				return false;
			}
		}catch(error){
			//just skip it.
		}
	}
});
$MsgCenter.on({
	objType : [WCMConstants.OBJ_TYPE_TREENODE,WCMConstants.OBJ_TYPE_CLSTREENODE],
	afterclick : function(event){
		//负责导航树对应的页面切换
		var context = event.getContext();
		if(!context || !context.navTree) return;
		var objId = context.objId;
		var objType = context.objType;
		var sParams = '';
		var sHostType = mappingHostWithObjType(objType);
		switch(objType){
			case WCMConstants.OBJ_TYPE_WEBSITEROOT:
				sParams = 'SiteType=' + objId;
				break;
			case WCMConstants.OBJ_TYPE_WEBSITE:
				sParams = 'SiteId=' + objId;
				sParams += '&SiteType=' + context.siteType;
				break;
			case WCMConstants.OBJ_TYPE_CHANNEL:
				sParams = 'ChannelId=' + objId;
				sParams += '&SiteType=' + context.siteType;
				sParams += '&IsVirtual=' + context.isVirtual;
				sParams += '&ChannelType=' + context.channelType;
				break;
			case WCMConstants.OBJ_TYPE_MYFLOWDOCLIST:
				sParams = [
					'HostType=', WCMConstants.OBJ_TYPE_MYFLOWDOCLIST,
					'&SiteType=' + objId,
					'&TabHostType=' + sHostType
				].join('');
				break;
			case WCMConstants.OBJ_TYPE_MYMSGLIST:
				sParams = [
					'HostType=', WCMConstants.OBJ_TYPE_MYMSGLIST,
					'&SiteType=' + objId,
					'&TabHostType=' + sHostType
				].join('');
				break;
			case WCMConstants.OBJ_TYPE_TRSSERVERLIST:
				sParams = [
					'HostType=', WCMConstants.OBJ_TYPE_TRSSERVERLIST,
					'&SiteType=' + objId,
					'&TabHostType=' + sHostType
				].join('');
				break;
			case WCMConstants.TAB_HOST_TYPE_METAVIEW:
				//TODO...
				alert(wcm.LANG.main_1000 || '尚未处理...');
				return;
				break;
			case WCMConstants.TAB_HOST_TYPE_CLASSINFO:
				sParams = [
					'HostType=', WCMConstants.OBJ_TYPE_CLASSINFO,
					'&HostId=' + objId,
					'&ViewId=' + context.viewId,
					'&TabHostType=' + sHostType
				].join('');
				break;
		}
		var treeNode = event.getObj();
		sParams += '&RightValue=' + context.right + "&" + (treeNode.params||"");
		var tabItem = null;
		if(treeNode.tabType){
			tabItem = wcm.TabManager.getTab(sHostType, treeNode.tabType, sParams);
		}
		if(tabItem == null){
			tabItem = wcm.TabManager.getDefaultTab(sHostType, sParams);
		}
		if(tabItem==null){
			Ext.Msg.warn('<div class="red-warning">'+(wcm.LANG['NORIGHT_TABCHANGE'] || '对不起，您没有权限处理此节点下的任何操作！')+'</div>');
			return false;
		}
		wcm.TabManager.exec(tabItem, sParams);
		//$('main').src = sUrl;
		return false;
	}
});

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afteredit : function(event){
		var context = event.getContext();
		if(context && context.href){
			$('main').src = context.href;
			return;
		}
		try{
			$('main').contentWindow.location.reload();
		}
		catch(err){
			//$('main').src = $('main').src;
		}
	},
	afterdestroy : function(event){
//		$('main').allowTransparency = false;
//		alert('afterdestroy');
	},
	afterinit : function(event){
	}
});
var m_docQuickOps = {
	docinfos : [],
	lasttype : null,
	showAlert : function(_params, _fDoAfterDisp){
		var DIALOG_DOCUMENT_INFO = 'document_info_dialog';
		wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
			title : wcm.LANG.SYSTEMINFO || '系统提示信息',
			src : WCMConstants.WCM6_PATH + 'docrecycle/document_info.html',
			width:'500px',
			height:'205px',
			maskable:true,
			params :  _params,
			callback : _fDoAfterDisp
		});
	},
	remem : function(infos, excludeIds, keyCode){
		var currInfos = m_docQuickOps.docinfos || [], rst = [];
		var type = String.fromCharCode(keyCode).toUpperCase()=='C'?'copy':'cut';
		if(m_docQuickOps.lasttype!=null && m_docQuickOps.lasttype!=type) currInfos = [];
		m_docQuickOps.lasttype = type;
		for(var j=0,jn=currInfos.length;j<jn;j++){
			if(currInfos[j] == null)continue;
			for(var i=0,n=excludeIds.length;i<n;i++){
				if(currInfos[j].id!=excludeIds[i])continue;
				currInfos[j] = null;
				break;
			}
		}
		currInfos = currInfos.concat(infos);
		for(var j=0,jn=currInfos.length;j<jn;j++){
			if(currInfos[j] == null)continue;
			rst.push(currInfos[j]);
		}
		m_docQuickOps.docinfos = rst;
	},
	uses : function(info){
		var arr = m_docQuickOps.docinfos = m_docQuickOps.docinfos || [];
		if(arr.length==0)return;
		m_docQuickOps.docinfos = [];
		var isCutting = m_docQuickOps.lasttype=='cut', method = info.method;
		if(isCutting && method=='quote')return;
		method = (isCutting && method=='copy') ? 'move' : method;
		var ids = [];
		for(var j=0,jn=arr.length;j<jn;j++){
			if(arr[j] == null)continue;
			ids.push(arr[j].id);
		}
		m_docQuickOps.showAlert({
				objectids: ids.join(),
				operation: '_' + method
			}, function(){
				if(info.callback)info.callback(ids, method);
			});
	}
};

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CHNLDOC,
	aftercopy : function(event){
		var objs = event.getObjs(), context = event.getContext();
		if(context==null || objs.length==0)return false;
		var infos = [], excludeIds = [], allInfos = context.allRowInfos;
		for(var i=0;i<allInfos.length;i++){
			excludeIds.push(allInfos[i].objId);
		}
		for(var i=0;i<objs.size();i++){
			var obj = objs.getAt(i);
			infos.push({
				id : obj.getId(),
				doctitle : obj.getProperty('doctitle')
			});
		}
		m_docQuickOps.remem(infos, excludeIds, context.keyCode);
	},
	afterpaste : function(event){
		var context = event.getContext();
		if(context==null)return false;
		var type = String.fromCharCode(context.keyCode).toUpperCase()=='Q'?'quote':'copy';
		var info = {
			method : type,
			callback : function(ids, method){
				var ids = ids.join();
				var methodName = method=='move' ? '移动' : (method== 'quote' ? '引用' : '复制');
				var oPostData = {
					ObjectIds : ids,
					ToChannelIds : event.getHost().getId(),
					ToChannelId : event.getHost().getId()
				};
				BasicDataHelper.Call('wcm6_viewdocument', method, oPostData, true,
					function(_transport,_json){
						Ext.Msg.report(_json, String.format('文档{0}结果',methodName), function(){
							CMSObj.afteradd(event)();
						});
					}
				);
			}
		};
		m_docQuickOps.uses(info);
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_SEARCH,
	search : function(event){
		var detailArg = "";
		var cmsobjs = event.getObjs().data;
		var detail = cmsobjs.queryInfoDetail.REGION;
		if(detail.length > 0){
			detailArg += "&REGION=";
			var arr = [];
			for(var i=0 ; i < detail.length; i++){
				arr.push(detail[i].NAME);
			}
			detailArg += arr.join(',');
		}
		var sUrl = WCMConstants.WCM6_PATH + 'document/document_list.html';
		try{
			$('main').src = sUrl + '?' + $toQueryStr2(cmsobjs) + detailArg;
		}
		catch(err){
			//$('main').src = $('main').src;
		}
	}
});

function showTreeMoreActions(position){
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call("wcm61_individuation", 'queryCustomSite', {
		ParamName	: "customSite",
		userId		:  userId
	}, true, function(transport, json){
		eval(transport.responseText);
		if(!window.simpleMenu){
			simpleMenu = new com.trs.menu.SimpleMenu({sBaseCls : 'customsitesbox'});
			simpleMenu.on('show', function(){
				if(json == null){
					return;
				}
				else {
					if(!window.checkedIds || checkedIds.length < 1){
						window.checkedIds = [0];
					}
					if(checkedIds.length == 1){
						Element.addClassName('item' + checkedIds[0], "activeItem");
					}
				}
			});
		}
		simpleMenu.show(customSites || [], {x:position[0],y:position[1]+31});
	});
}

Ext.ns('wcm.navTabs');
(function(){
	var template = {
		active : [
			'<span class="tree_navitem tree_navitem{2}" id="navtab_{0}">{1}</span>'
		].join(''),
		normal : [
			'<span class="tree_navitem tree_navitem{2}">',
				'<a href="#" id="navtab_{0}" class="navtab_{2}">{1}</a>',
			'</span>'
		].join('')
	};
	Ext.apply(wcm.navTabs, {
		tabs : [],
		add : function(item){
			wcm.navTabs.tabs.push(item);
		},
		draw : function(info){
			var tabs = wcm.navTabs.tabs;
			if(!tabs)return;
			var nDefIndex = -1;
			if(info!=null){
				nDefIndex = parseInt(info.activeIndex, 10) || 0;
			}
			if(nDefIndex >= tabs.length || nDefIndex < 0) nDefIndex = 2;
			var items = [];
			var nLength = 0;
			for(var i=0,n=tabs.length;i<n;i++){
				var tab = tabs[i];
				if(tab==null)continue;
				nLength++;
				if(nDefIndex==i){
					items.push(String.format(template.active, tab.key, tab.desc, i));
					continue;
				}
				items.push(String.format(template.normal, tab.key, tab.desc, i));
			}
			Element.update('navtab_box', items.join(''));
			$('navtab_box').className = 'navtab_box navtab_box_' + nDefIndex + ' navtab_box_l'+nLength;
			var oActiveTab = tabs[nDefIndex];
			if(info && info.fn && oActiveTab && oActiveTab.fn){
				oActiveTab.fn.call(oActiveTab, oActiveTab);
			}
		},
		init : function(info){
			wcm.navTabs.draw(info);
			Ext.get('navtab_box').on('click', function(event, target){
				if(target.tagName!='A')return;
				var nActiveIndex = target.className.substring('navtab_'.length);
				wcm.navTabs.draw({
					activeIndex : nActiveIndex,
					fn : true
				});
				Event.stop(event.browserEvent);
				return false;
			});
		}
	});
})();
function dispTabCntWith(tabItem){
	for(var i=0,n=wcm.navTabs.tabs.length;i<n;i++){
		var tab = wcm.navTabs.tabs[i];
		if(tab.key==tabItem.key){
			Element.show(tab.key);
		}else{
			Element.hide(tab.key);
		}
	}
}
wcm.navTabs.add({
	key : 'viewclassinfo',
	desc : wcm.LANG['METAVIEWDATA'] || '资源',
	fn : function(tabItem){
		var objType = getParameter('objType');
		var objId = getParameter('objId');
		var sUrl = "viewclassinfo/viewclassinfo.jsp?objType="+objType+"&objId="+objId;
		var dom = $('viewclassinfo');
		if(!dom){
			new Insertion.Bottom('frms', [
				"<iframe id='viewclassinfo'",
					" src='", sUrl, "'",
					" scrolling='no' frameborder='0' allowtransparency='true'",
				"></iframe>"			
			].join(""));
		}else{
			dom.src = sUrl;
		}
		dispTabCntWith(tabItem);
	}
});
wcm.navTabs.add({
	key : 'adv_search',
	desc : wcm.LANG['NAV_SEARCH'] || '检索',
	fn : function(tabItem){
		var sUrl = 'advsearch/advsearch.html';
		try{
			var info = $('nav_tree').contentWindow.getFocusedNodeInfo();
			sUrl += "?nodeid=" + info.id + "&nodetype=" + info.type + "&nodename=" + encodeURIComponent(info.name.replace(/\"/g,"&quot;")) + "&chnlType=" + info.chnltype + "&rootType=" + info.libType;
		}catch(error){
			//just skip it.
		}
		var dom = $('adv_search');
		if(!dom){
			new Insertion.Bottom('frms', [
				"<iframe id='adv_search'",
					' src="', sUrl, '"',
					" scrolling='no' frameborder='0' allowtransparency='true'",
				"></iframe>"			
			].join(""));
		}else{
			dom.src = sUrl;
		}
		dispTabCntWith(tabItem);
	}
});
wcm.navTabs.add({
	key : 'nav_tree',
	desc : wcm.LANG['NAV_TREE'] || '导航',
	fn : function(tabItem){
		var dom = $('nav_tree');
		if(!dom){
			var objType = getParameter('objType');
			var objId = getParameter('objId');
			var sUrl = 'nav_tree/nav_tree.html?objType='+objType+"&objId="+objId;
			new Insertion.Bottom('frms', [
				"<iframe id='nav_tree'",
					' src="', sUrl, '"',
					" scrolling='no' frameborder='0' allowtransparency='true'",
				"></iframe>"			
			].join(""));
		}
		dispTabCntWith(tabItem);
	}
});
/*
Event.observe(window, 'load', function(){
	wcm.navTabs.init({
		activeIndex : 2,
		fn : true
	});
});
*/

//disable the contextmenu.
Event.observe(document, 'contextmenu', function(event){
	if(WCMConstants.DEBUG) return;
	Event.stop(event || window.event);
});

Event.observe(window, 'load', function(){
	Event.observe('nav_toggle', 'click', toggleNavTree, false);
});

function toggleNavTree(){
	var hideCls = Element.hasClassName('main_center_container', 'hide_west');
	var sMethod = hideCls ? 'expand' : 'collapse';
	wcm.Layout[sMethod]('west', 'main_center_container');
}
Event.observe(window, 'load', function(){
	if(!window.bAccess){
		Ext.Msg.alert((wcm.LANG['ALERT_MESSAGE_1'] ||"您是初次访问系统，可能要设置一些系统的配置，") + "<a href=\"./system/config_manager.jsp?first=1\" target=\"_blank\">" + (wcm.LANG['ALERT_MESSAGE_2'] ||"设置入口")+"</a>" + (wcm.LANG['ALERT_MESSAGE_3'] ||" 您也可以以后再设置，设置入口是在菜单栏的配置管理下面的系统配置项。"));
	}
	
});
Event.observe(window, 'load', function(){
	if(window.bRightTimeToReminder){
		Ext.Msg.alert(wcm.LANG['ALERT_MESSAGE_4']||"您在本系统中使用的密码为弱密码，为了确保您个人账号的安全，请点击<a href='#' onclick='onEditPassword();return false'>重设密码</a>进行重新设定！");
	}
});

function onEditPassword() {
	wcm.MessageBox.hide();
	var oTRSAction = new CTRSAction("../console/person/password_reset.jsp");
	var bResult = oTRSAction.doDialogAction(500, 300);
	if(bResult){
		if(CTRSAction_confirm(wcm.LANG.main_4000 || "成功修改用户密码！\r\n您要重新登录吗？")) {
			window.top.location = "../console/logout.jsp";
		} else {
			//CTRSAction_refreshMe();
		}
	}
}

//检验配置的页面是否会返回404
Ext.ns('wcm.CheckerOf404Page');
wcm.CheckerOf404Page = function(){
	var caches = {};
	return {
		handle : function(_hrefSrc){
			_hrefSrc = _hrefSrc.replace(/\?.*$/,'');
			//元数据是动态生成的引用，所以有所区别
			if(_hrefSrc.indexOf("/application") >= 0){
				if(caches[_hrefSrc] === false){
					return false;
				}
			}else if(caches[_hrefSrc] != null){
				return caches[_hrefSrc];
			}
			var r = new Ajax.Request(_hrefSrc, {
				asynchronous:false,
				method:'GET'
			});
			var bResult = r.transport.status==404;
			caches[_hrefSrc] = bResult;
			return bResult;
		}
	};
}();