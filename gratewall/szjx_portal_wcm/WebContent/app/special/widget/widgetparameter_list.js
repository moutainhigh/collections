/*-------------中间内容请求的服务相关信息--------------*/
var nWidgetId = getParameter('widgetId');
Ext.apply(PageContext, {
	serviceId : 'wcm61_widgetparameter',
	methodName : 'jQuery',
	initParams : {
		"WidgetId" : nWidgetId,
		"FieldsToHTML" : "",
		"SelectFields" : "",
		OrderBy : "WIDGETPARAMORDER desc",
		"pageSize" : "-1"
	}
});

function getHelper(){
	return new com.trs.web2frame.BasicDataHelper();
}

//apply the default value.
Ext.apply(wcm.ThumbList, {
	thumb_item_cls : 'grid_row',
	thumb_item_active_cls : 'grid_row_active',
	thumb_item_hover_cls : 'grid_row_hover',
	identify_attr : 'rowId',
	thumb_id_prefix : 'tr_'
});	

//register for thumb list cmds.
wcm.ThumbList.register({
	edit : function(properties){
		addOrEdit(properties['id'],false);
	},
	'delete' : function(properties){
		//需要发个请求，判断改资源变量所在的资源是否有被使用
		var oPostData = {
			objectIds : properties['id']
		};
		Ext.Msg.confirm('确实要删除该资源变量吗? ', {
			yes : function(){
				getHelper().call('wcm61_widgetparameter','delete', oPostData, true, 
					function(){
						//页面刷新
						PageContext.loadList(PageContext.params);
				});
			}
		});
	}
});
function addParam(){
	addOrEdit(0,true);
}

function makeWidgetId(){
	var nWidgetId = parent.$('objectId').value||getParameter('widgetId');
	return nWidgetId;
}

function addOrEdit(_objId,_bAdd){
	var oparams = {
		objectId : _objId,
		widgetId : makeWidgetId()
	};
	var sCbName = "";
	var sCbTitle = "";
	if(_bAdd){
		sCbName = 'add_widgetparameter';
		sCbTitle = '新建资源变量';
	}else{
		sCbName = 'edit_widgetparameter';
		sCbTitle = '修改资源变量';
	}
	wcm.CrashBoarder.get(sCbName).show({
		title : sCbTitle,
		src : 'widget/widgetparameter_addedit.jsp',
		width:'550px',
		height:'280px',
		params : oparams,
		callback : function(params){
			this.close();
			//PageContext.refreshList(Ext.apply(PageContext.params,{"WidgetId" : makeWidgetId()}));
			PageContext.refreshList({"WidgetId" : makeWidgetId()});
		}
	});
}

function deleteParam(){
	var objIds = getSelectedListItemIds();
	var nCount = getSelectedListItemIds().length;
	if(nCount<=0){
		Ext.Msg.alert("没有选择要删除的资源变量.");
		return;
	}
	//需要发个请求，判断这些资源变量所在的资源是否有被使用
	var oPostData = {
		objectIds : objIds
	};
	Ext.Msg.confirm('确实要删除该资源变量吗? ', {
		yes : function(){
			getHelper().call('wcm61_widgetparameter','delete', oPostData, true, 
				function(){
					//页面刷新
					PageContext.loadList(PageContext.params);
			});
		}
	});
}

function getSelectedListItemIds(){
	var result = [];
	var doms = document.getElementsByClassName('grid_checkbox');
	for (var i = 0; i < doms.length; i++){
		if(doms[i].checked){
			result.push(doms[i].value);
		}
	}
	return result;
}

PageContext.addListener('afterrender', function(transport, json){
	var bCanAdd = transport.getResponseHeader("bCanAdd");
	if(bCanAdd == 'false'){
		Element.addClassName($('addParam'), "disableCls");
		Element.addClassName($('deleteParam'), "disableCls");
	}
	//处理列表全选的功能
	var bSelAll = false;
	Event.observe($('selAll'),'click',function(){
		if(!bSelAll){
			bSelAll = true;
		}else{
			bSelAll = false;
		}
		var doms = document.getElementsByName("RowId");
		if(bSelAll){
			for (var i = 0,nLen = doms.length; i < nLen; i++){
				doms[i].checked = true;
			}
		}else{
			for (var i = 0,nLen = doms.length; i < nLen; i++){
				doms[i].checked = false;
			}
		}
	});
});



Event.observe(document, 'keydown', function(event){
	event = window.event || event;
	var target = Event.element(event);
	if(target.name != 'order') return;
	OrderHandler.init(target);
});


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
		if(!/^-?\d+$/.test(dom.value)){
			alert(wcm.LANG.DOCUMENT_PROCESS_215 || '请输入合法的数字');
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
		var orders = document.getElementsByName("order");
		var newOrder = parseInt(dom.value, 10);
		if(newOrder <= 0) newOrder = 1;
		if(newOrder > orders.length) newOrder = orders.length;
		var oldOrder = parseInt(dom.getAttribute("_value"), 10);
		if(oldOrder == newOrder){
			dom.value = newOrder;
			return;
		}
		var srcObjectId = dom.getAttribute("rowid");
		var dstObject = newOrder < oldOrder ? orders[newOrder-2] : orders[newOrder-1];
		var postData = {
			srcObjectId : srcObjectId,
			dstObjectId : dstObject ? dstObject.getAttribute("rowid") : 0
		};
		getHelper().call('wcm61_widgetparameter','changeOrder', postData, true, function(){
			//页面刷新
			PageContext.refreshList(null, [srcObjectId]);
		});
	}
};
//change order end
