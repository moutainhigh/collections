/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, { 
	serviceId : 'wcm61_message',
	methodName : 'jQuery',
	/**/
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"MsgTypes" : "1,2,3"
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		return WCMConstants.OBJ_TYPE_MESSAGE;
	},
	rowInfo : { 
		cruser : true,
		title : true,
		mbody : true,
		receiver :true,
		crtime : true,
		rowid : true,
		editClassName : true
	}
});
/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	{
		desc : wcm.LANG['MESSAGE_LIST'] || '短消息列表',
		type : 0
	}
]);
//检索框信息
Event.observe(window, 'load', function(){
	ClassicList.makeLoad();
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'query_box', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			{name: 'title', desc: wcm.LANG['MESSAGE_TITLE'] || '消息标题', type: 'string'},
			{name: 'mbody', desc:  wcm.LANG['MESSAGE_CONTENT'] || '消息内容', type: 'string'}

		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});
});
//路径信息
Ext.apply(PageContext.literator, {
	enable : true,
	width : 350
});
/*-------------指定各种情况下右侧的操作面板--------------*/
/*
*设置第一块操作面板的类型；
*第二块和第三块面板的类型通过如下方式获取：
*如果列表页面没有选择记录，则为导航树节点对应的类型(如：website,channel);
*如果列表当前选中一条记录，则类型为当前列表的rowType(如：chnldoc);
*如果列表当前选中多条记录，则类型为当前列表的rowType+s(如：chnldocs);
*操作面板中操作的具体定义在文件wcm/app/js/data/opers/xxx.js中；
*/
PageContext.operEnable = false; 
/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG['MESSAGE_LIST_UNIT'] || '个',
	TypeName : wcm.LANG['MESSAGE'] || '短消息'
});
/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'reply' : function(event){
		var obj = event.getObjs().getAt(0)
		var className = obj.getPropertyAsString('editClassName');
		var mBody = obj.getPropertyAsString('mbody');
		if(className=="func_reply") wcm.domain.MessageMgr['reply'](event);
		else if(className=="func_post"){
			doWithWorkFlowDoc(mBody,event);
		}
	},
	'showDetail' : function(event){
		var extEvent = event.getContext().opt.event;
		showDetail(extEvent,event);
	}
});
/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'message' + getParameter('ReadFlag')
});
 /*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_MESSAGE,
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			ReadFlag : getParameter('ReadFlag')
		});
		return context;
	}
});

Object.extend(Event, {
  pointerX: function(event) {
    return event.pageX || (event.clientX +
      (document.documentElement.scrollLeft || document.body.scrollLeft));
  },

  pointerY: function(event) {
    return event.pageY || (event.clientY +
      (document.documentElement.scrollTop || document.body.scrollTop));
  }
});
function showDetail(extEvent,event){
	var x = Event.pointerX(extEvent.browserEvent);
	var y = Event.pointerY(extEvent.browserEvent);
	if(event.length==0) return;
	var obj =  event.getObjs().getAt(0);
	var rowId = obj.getPropertyAsInt('rowid',0);
	var context = event.getContext();
	var sTitle = $transHtml(obj.getPropertyAsString("title"));
	var sDetail = obj.getPropertyAsString("mbody");
	var receiver = obj.getPropertyAsString("receiver");
	var sTime = obj.getPropertyAsString("crtime");
	var crUser = obj.getPropertyAsString("cruser");
	var sUser = (context.ReadFlag == 1) ? ((wcm.LANG['MESSAGE_25'] || '接收者: ') + receiver) : (( wcm.LANG['MESSAGE_26'] || '发送者: ') + crUser);
	var srcElement = extEvent.target;
	if(srcElement && srcElement.className=='title_unreaded'){
		srcElement.className ='title_normal';
		signAsReaded1(event);
	}
	var sContent = '<div class="dv_title">' + sTitle
		+ ' (<span class="sp_time">' + (wcm.LANG['MESSAGE_54'] || '创建时间: ') + sTime + ', '  +  sUser + '</span>)</div>';
	sContent += '<div class="dv_detail">' + sDetail + '</div>';
	Element.update('content',sContent);
	var oBubbler = new wcm.BubblePanel('showDetail');
	oBubbler.bubble([x,y + 15], null, function(p){
		setFitPosition(this, p);
	});
	var box = $('content');
	var doms = box.getElementsByTagName("div");
	doms[1].style.width = doms[0].offsetWidth;	


}
function setFitPosition(el, point){
	var bMustShow = Element.getStyle(el, 'display')=='none';
	if(bMustShow){
		el.style.visibility = 'hidden';
		el.style.display = '';
	}
	var left = point[0], top = point[1];
	var right = left + el.offsetWidth;
	if(right >= document.body.offsetWidth){
		left = Math.max(left - el.offsetWidth, 0);
	}
	var bottom = top + el.offsetHeight;
	if(bottom >= document.body.offsetHeight){
		top = Math.max(top - el.offsetHeight, 0);
	}
	el.style.left = left + "px";
	el.style.top = top + "px";
	if(bMustShow){
		el.style.display = 'none';
		el.style.visibility = 'visible';
	}
}
function signAsReaded1(event){
	var _arIds = event.getIds();
	if(!wcm.domain.MessageMgr.checkSubmit(_arIds, wcm.LANG['MESSAGE_18'] || '设置为已读')) {
		return;
	}
	BasicDataHelper.call(serviceId, 'setReadFlag', {Readed: true, ObjectIds: _arIds}, false, function(){
		//CMSObj.afterdelete(event)();
	});		
}

function doWithWorkFlowDoc(mbody,event){
	if(mbody!=null && mbody!=""){
		var aMatches = mbody.match(/href=['"]([^'"\s]*)['"\s]/);
		var strParams = aMatches[1].substr(aMatches[1].indexOf("?"),aMatches[1].length);
		if(aMatches){
			var sUrl = WCMConstants.WCM6_PATH + 'flowdoc/workflow_process_render.jsp' + strParams;
			if(aMatches[1].indexOf("app/scm/index")>0){
				sUrl = aMatches[1];
				$openMaxWin(sUrl);
				return;
			}
			wcm.CrashBoarder.get('Process_Render').show({
				title :  wcm.LANG['MESSAGE_37'] || "处理文档",
				src : sUrl,
				width: '480px',
				height: '450px',
				maskable : true,
				callback : function(){
					CMSObj.afteredit(event)();
				}
			});
		}
	}
}
ClassicList.cfg = {
	toolbar : [
		{
			id : 'message_new',
			fn : function(event, elToolbar){
				wcm.domain.MessageMgr.newMsg(event);
			},
			name : wcm.LANG.MESSAGE_27 || '写新短消息',
			desc : wcm.LANG.MESSAGE_27 || '写新短消息',
			rightIndex : -1
		}, {
			id : 'message_clearinbox',
			fn : function(event, elToolbar){
				wcm.domain.MessageMgr.clearInbox(event);
			},
			name : wcm.LANG['MESSAGE_32'] || '清空收件箱',
			desc : wcm.LANG['MESSAGE_32'] || '清空收件箱',
			isVisible : function(event){
				if(event && event.getContext().ReadFlag!=2)return false;
				return true;
			},
			rightIndex : -1
		}, {
			id : 'message_clearoutbox',
			fn : function(event, elToolbar){
				wcm.domain.MessageMgr.clearOutbox(event);
			},
			name : wcm.LANG['MESSAGE_33'] || '清空已发短消息',
			desc : wcm.LANG['MESSAGE_33'] || '清空已发短消息',
			isVisible : function(event){ 
				if(event && event.getContext().ReadFlag!=1)return false;
				return true;
			},
			rightIndex : -1
		},{
			id : 'message_delete',
			fn : function(event, elToolbar){
				wcm.domain.MessageMgr['delete'](event);
			},
			name : wcm.LANG.MESSAGE_12 || '删除',
			desc : wcm.LANG.MESSAGE_31 || '删除短消息',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : -1
		},{
			id : 'message_transfer',
			fn : function(event, elToolbar){
					wcm.domain.MessageMgr.forward(event);
				},
			name : wcm.LANG.MESSAGE_9 || '转发',
			desc : wcm.LANG.MESSAGE_30 || '转发短消息',
			isDisabled : function(event){
				if(!event || event.length() <= 0 || event.length() != 1) return true;
			},
			rightIndex : -1
		},
		{
			id : 'message_reply',
			fn : function(event, elToolbar){
					wcm.domain.MessageMgr.reply(event);
				},
			name : wcm.LANG.MESSAGE_56 || '回复',
			desc : wcm.LANG.MESSAGE_29 || '回复短消息',
			isVisible : function(event){
				if(event && event.getContext().ReadFlag==1)return false;
				return true;
			},
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
			},
			rightIndex : -1
		},{
			id : 'message_signasreaded',
			fn : function(event, elToolbar){
					wcm.domain.MessageMgr.signAsReaded(event);
				},
			name :  wcm.LANG['MESSAGE_28'] || '标记为已读',
			desc :  wcm.LANG['MESSAGE_28'] || '标记为已读',
			isVisible : function(event){
				if(event && event.getContext().ReadFlag!=0)return false;
				return true;
			},
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : -1
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.MESSAGE_38 || '刷新',
			desc : wcm.LANG.MESSAGE_REFRESH || '刷新列表'
		}
	],
	listTitle : wcm.LANG.MESSAGE_6 || '短消息列表详细信息',
	path : ''
}
function $transHtml(_sContent) {
	if (_sContent == null)
		return '';

	var nLen = _sContent.length;
	if (nLen == 0)
		return '';

	var result = '';
	for (var i = 0; i < nLen; i++) {
		var cTemp = _sContent.charAt(i);
		switch (cTemp) {
		case '<': // 转化:< --> &lt;
			result += '&lt;';
			break;
		case '>': // 转化:> --> &gt;
			result += '&gt;';
			break;
		case '"': // 转化:" --> &quot;
			result += '&quot;';
			break;
		default:
			result += cTemp;
		}// case
	}// end for
	return result;
}

