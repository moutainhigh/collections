Ext.apply(PageContext, { 
	serviceId : 'wcm61_flowdoc',
	methodName : 'getContentsOfUser',
	/**/
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});
/*-------------指定列表上的过滤器--------------*/
PageContext.filterEnable = false;

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG['IFLOWCONTENT_4'] || '个',
	TypeName : wcm.LANG['IFLOWCONTENT_5'] || '流转文档'
});


//检索信息
Event.observe(window, 'load', function(){
		ClassicList.makeLoad();
		if(PageContext.hostType == WCMConstants.OBJ_TYPE_WEBSITE){
			Element.hide('divDispMode');
		}
        wcm.ListQuery.register({
            /*检索控件追加到的容器*/
            container : 'query_box', 
            /*是否追加“全部”这个检索项, default to false*/
            appendQueryAll : true,
            /*是否自动加载, default to true*/
            autoLoad : true,
            /*检索项的内容*/
            items : [
    			{name : 'SearchValue', desc : wcm.LANG['IFLOWCONTENT_98'] || '文档标题', type : 'string'},
    			{name : 'SearchValue', desc : wcm.LANG['IFLOWCONTENT_99'] || '文档ID', type : 'int'}
            ],
            /*执行检索按钮时执行的回调函数*/
            callback : function(params){
               PageContext.loadList(params);

            }
        });
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
	UnitName : wcm.LANG['IFLOWCONTENT_4'] || '个',
	TypeName : wcm.LANG['IFLOWCONTENT_5'] || '流转文档'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'tracing' : function(event){
		tracing(event);
	},
	'dealing' : function(event){
		wcm.domain.IFlowContentMgr['dealing'](event);
	},
	'reasign' : function(event){
		wcm.domain.IFlowContentMgr['reasign'](event);
	},
	'cease' : function(event){
		wcm.domain.IFlowContentMgr['cease'](event);
	},
	'signFlowDoc' : function(event){
		wcm.domain.IFlowContentMgr['sign'](event);
	},
	'show' : function(event){
		wcm.domain.IFlowContentMgr['show'](event);
	},
	'showDetailComments' : function(event){
		var extEvent = event.getContext().opt.event;
		showDetailComments(extEvent,event);
	},
	'showDetailProcessInfo' : function(event){
		var extEvent = event.getContext().opt.event;
		showDetailProcessInfo(extEvent,event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'flowdoc' + getParameter('ViewType')
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_IFLOWCONTENT,
	getContext : function(){
		var context = this.getContext0();
		Ext.apply(context, {
			ViewType : getParameter('ViewType')
		});
		return context;
	},
	getIds : function(event){
		return event.getObjs().getPropertyAsString("flowdocid", 0);
	} 
});

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afterinit : function(event){
		if(getParameter('ViewType') == 1) {
			var rowInfos = wcm.Grid.getAllRowInfos({
				flowdocid : true,
				received : true			
			});
			if(rowInfos.length==0)return;
			var arrIds = [];
			for (var i = 0; i < rowInfos.length; i++){
				var id = rowInfos[i].flowdocid;
				var recd = rowInfos[i].received;
				if(id && recd && recd != '1') {
					arrIds.push(id);
				}
			}
			if(arrIds.length == 0)return;
			receive(arrIds);
		}
	}
});
function tracing(event){
	var obj = event.getObjs().getAt(0);
	var o_params = {
		flowid: obj.getPropertyAsInt('flowDocId', 0),
		title:	obj.getPropertyAsString('contentTitle'),
		ctype:  obj.getPropertyAsInt('contentType', 0),
		cid:	obj.getId(),
		cruser: obj.getPropertyAsString('crUser'),
		crtime: obj.getPropertyAsString('crtime'),
		isend:	obj.getPropertyAsInt('isend'),
		gunter_view: obj.getPropertyAsInt('gunter_view') || false
	};
	var sUrl = WCMConstants.WCM6_PATH + 'flowdoc/workflow_process_tracing.jsp';
	wcm.CrashBoarder.get('Process_Tracing').show({
			title :  wcm.LANG['IFLOWCONTENT_53'] || '查看文档流转情况',
			reloadable : true,
			src : sUrl,
			width: '600px',
			height: '350px',
			params : o_params,
			maskable : true,
			callback : function(){ }
	});	
}
function receive(_ids){
	var oPostData = {
		ObjectIds: _ids
	};
	BasicDataHelper.call('wcm6_process', 'doReceive', oPostData, true, function(_trans, _json){
	});
}
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

function showDetailComments(extEvent,event){
	var x = Event.pointerX(extEvent.browserEvent);
	var y = Event.pointerY(extEvent.browserEvent);
	if(event.length==0) return;
	var obj =  event.getObjs().getAt(0);
	var sComments = obj.getPropertyAsString("comments");
	var sContent = '<div class="dv_detail">' + $transHtml(sComments) + '</div>';
	Element.update('contentinfo',sContent);
	var oBubbler = new wcm.BubblePanel('showDetailInfo');
	oBubbler.bubble([x,y + 15], null, function(p){
		setFitPosition(this, p);
	});
}
function showDetailProcessInfo(extEvent,event){
	var x = Event.pointerX(extEvent.browserEvent);
	var y = Event.pointerY(extEvent.browserEvent);
	if(event.length==0) return;
	var obj =  event.getObjs().getAt(0);
	var sComments = obj.getPropertyAsString("processinfo");
	var sContent = '<div class="dv_detail">' + $transHtml(sComments)  + '</div>';
	Element.update('contentinfo',sContent);
	var oBubbler = new wcm.BubblePanel('showDetailInfo');
	oBubbler.bubble([x,y + 15], null, function(p){
		setFitPosition(this, p);
	});
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
ClassicList.cfg = {
	toolbar : [
		{
			id : 'flowdoc_sign',
			fn : function(event, elToolbar){
				wcm.domain.IFlowContentMgr['sign'](event);
			},
			name : wcm.LANG.IFLOWCONTENT_74 || '签收',
			desc : wcm.LANG.IFLOWCONTENT_75 || '签收工作流文档',
			isVisible : function(){
				var ViewType = getParameter('ViewType');
				if(ViewType==1)return true;
				return false;
			},
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
				return false;
			},
			rightIndex : -1
		}, {
			id : 'flowdoc_refuse',
			fn : function(event, elToolbar){
				wcm.domain.IFlowContentMgr['refuse'](event);
			},
			name : wcm.LANG.IFLOWCONTENT_46 || '拒绝',
			desc : wcm.LANG.IFLOWCONTENT_76 || '拒绝工作流文档',
			isVisible : function(){
				var ViewType = getParameter('ViewType');
				if(ViewType==1)return true;
				return false;
			},
			isDisabled : function(event){
				if(!event || event.length() <= 0 || event.length() > 1) return true;
				var obj = event.getObjs().getAt(0);
				if(obj && (obj.getPropertyAsString("flagid") == "2" || obj.getPropertyAsString("flagid") == "8" || obj.getPropertyAsString("flagid") == "14" )) return true;  //返工后不能拒绝,拒绝后不能再拒绝,其中flagid="2"表示是返工后的文档，flagid="8"表示是拒绝后的文档
				if (obj && (obj.getPropertyAsString("nCanBackRefuse") == "0")) {
					return true;
				} 
				return false;
			},
			rightIndex : -1
		}, {
			id : 'flowdoc_rework',
			fn : function(event, elToolbar){
				wcm.domain.IFlowContentMgr['rework'](event);
			},
			name : wcm.LANG.IFLOWCONTENT_44 || '要求返工',
			desc : wcm.LANG.IFLOWCONTENT_64 || '要求返工工作流文档',
			isVisible : function(){
				var ViewType = getParameter('ViewType');
				if(ViewType==1)return true;
				return false;
			},
			isDisabled : function(event){
				if(!event || event.length() <= 0 || event.length() > 1) return true;
				var obj = event.getObjs().getAt(0);
				if(obj && (obj.getPropertyAsString("flagid") == "8" || obj.getPropertyAsString("flagid") == "14")) return true; //拒绝后不能返工，其中flagid="2"表示是返工后的文档，flagid="8"表示是拒绝后的文档@comment by kwok
				if (obj && (obj.getPropertyAsString("nCanBackRefuse") == "0")) {
					return true;
				} 
				return false;
			},
			rightIndex : -1
		}, {
			id : 'flowdoc_show',
			fn : function(event, elToolbar){
				wcm.domain.IFlowContentMgr['show'](event);
			},
			name : wcm.LANG.IFLOWCONTENT_77 || '查看',
			desc : wcm.LANG.IFLOWCONTENT_68 || '查看这篇文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0 || event.length() > 1) return true;
			},
			rightIndex : -1
		}, {
			id : 'flowdoc_edit',
			fn : function(event, elToolbar){
				wcm.domain.IFlowContentMgr['edit'](event);
			},
			name : wcm.LANG.IFLOWCONTENT_82 || '编辑',
			desc : wcm.LANG.IFLOWCONTENT_69 || '编辑这篇文档',
			isVisible : function(){
				var ViewType = getParameter('ViewType');
				if(ViewType==1)return true;
				return false;
			},
			isDisabled : function(event){
				if(!event || event.length() <= 0 || event.length() > 1) return true;
				return false;
			},
			rightIndex : 0
		}, {
			id : 'flowdoc_detailpublish',
			fn : function(event, elToolbar){
				wcm.domain.IFlowContentMgr['detailPublish'](event);
			},
			name : wcm.LANG.IFLOWCONTENT_84 || '发布',
			desc : wcm.LANG.IFLOWCONTENT_71 || '发布这篇文档',
			isVisible : function(){
				var ViewType = getParameter('ViewType');
				if(ViewType==1)return true;
				return false;
			},
			isDisabled : function(event){
				if(!event || event.length() <= 0 || event.length() > 1) return true;
				var obj = event.getObjs().getAt(0);
				if(obj.getPropertyAsString('bCanPublish') == 'false') return true;
				return false;
			},
			rightIndex : 2
		}, {
			id : 'flowdoc_delete',
			fn : function(event, elToolbar){
				wcm.domain.IFlowContentMgr['deletedoc'](event);
			},
			name : wcm.LANG.IFLOWCONTENT_83 || '删除',
			desc : wcm.LANG.IFLOWCONTENT_78 || '删除这个/些工作流文档',
			isVisible : function(){
				var ViewType = getParameter('ViewType');
				if(ViewType==1)return true;
				return false;
			},
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
				return false
			},
			rightIndex : 1
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.IFLOWCONTENT_79 || '刷新',
			desc : wcm.LANG.IFLOWCONTENT_80 || '刷新列表'
		},{
			id : 'action_takeback',
			fn : function(event, elToolbar){
				var nFlowDocId = event.getObj().getPropertyAsInt('flowdocid');
				var oPostData = {
								"flowdocid" : nFlowDocId
							}
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				oHelper.Call("wcm6_process", "takeBack", oPostData, true,
					function(_transport,_json){
					CMSObj.afteredit(event)();
			});
			},
			name : '收回',
			desc : '收回已处理',
			isVisible : function(){
				var ViewType = getParameter('ViewType');
				if(ViewType==2)return true;
				return false;
			},
			isDisabled : function(event){
				if(!event || event.length() <= 0  || event.length() > 1) return true;
				var nFlowDocId = event.getObj().getPropertyAsInt('flowdocid');

				var transport = ajaxRequest({
					url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm6_process&methodname=canTakeBack',
					method : 'GET',
					parameters : 'flowdocid='+nFlowDocId,
					asyn : false//执行同步请求
				});
				var json = parseXml(loadXml(transport.responseText));
				if(json.RESULT == 'false'){
					return true;
				}
				return false;
			},
			rightIndex : -1
		}
	],
	listTitle : (getParameter('ViewType') == 1 ? (wcm.LANG['IFLOWCONTENT_105'] || '待处理工作列表') : (getParameter('ViewType') == 2 ? (wcm.LANG['IFLOWCONTENT_106'] || '已处理工作列表') : (wcm.LANG['IFLOWCONTENT_107'] || '我发起工作列表'))),
	path : ''
}