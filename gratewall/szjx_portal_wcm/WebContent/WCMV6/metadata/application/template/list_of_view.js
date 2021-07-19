Object.extend(PageContext,{
	AbstractParams : {
//		channelId : getParameter('channelid') || 0,
//		AllFieldsToHTML : true,
		viewId : getParameter('viewId') || 0
	},//服务所需的参数
	_doBeforeRefresh : function(){
		var sNewParams = 'siteid=' + PageContext.params['siteid']
			+ '&channelid=' + PageContext.params['channelid'];
		PageContext.drawLiterator('literator_path', sNewParams);
//		PageContext["params"]["viewId"] = getPageParams()["viewId"];
	},
	_doAfterBound : function(){
		HTMLElementParser.parse();
	},
	getRightIndex : function(sFunName){
		var rightIndex = -1;
		sFunName = sFunName.trim().toLowerCase();
		switch(sFunName){
			case 'edit':
				rightIndex = 32;
				break;
			case 'delete':
				rightIndex = 33;
				break;
			case 'view':
				rightIndex = 34;
				break;
			default:
				-1;
		}
		return rightIndex;
	}
});

Event.observe(window, 'load', function(){
	PageContext.LoadPage();
	SimpleQuery.baseURL = '../../../';
	var oParams = getPageParams();
	if(oParams && oParams["searchParams"].length > 0){
		SimpleQuery.register('query_box', oParams["searchParams"], function(_params){
			Object.extend(PageContext.params, _params);
			delete PageContext.params["_isAdvanceSearch_"];
			delete PageContext.params["_queryType_"];
			delete PageContext.params["_sqlWhere_"];
			PageContext.RefreshList();
		}, true);

		var rightEdge = $('rightEdge');
		if(rightEdge){
			var oTd = document.createElement("td");
			with(oTd){
				width = '40';
				valign = 'bottom';
				align = 'right';
			}
			oTd.innerHTML = '<div class="advance_search" id="advanceSearch" title="高级检索"></div>';
			rightEdge.parentNode.insertBefore(oTd, rightEdge);
		}
		Event.observe('advanceSearch', 'click', function(event){
			var url = "./metadata/application/" + getPageParams()["viewId"] + "/advance_search.jsp";
			var params = "?channelId=" + PageContext.params['channelid'];
			FloatPanel.open(url + params, '高级检索对象', 450, 200);
		});
	}

	Event.observe(Grid.gridId, 'click', function(event){
		event = window.event || event;
		var srcElement = Event.element(event);
		var tempNode = srcElement;
		if(Element.hasClassName(tempNode, "grid_column")){
			return;
		}

		while(tempNode && tempNode.id != Grid.gridId){
			if(Element.hasClassName(tempNode, "grid_column")){
				if(Element.hasClassName(tempNode, "normalColumn1")){
					//extend chnldocid
					var eRow = tempNode.parentNode;
					var rightValue = eRow.getAttribute("right");
					var rightIndex = PageContext.getRightIndex("view");
					if(!isAccessable4WcmObject(rightValue, rightIndex)){
						return;
					}
					PageContext.ObjectMgr["view"](
						 eRow.getAttribute("grid_rowid"), 
						 Object.extend({ChnlDocId: eRow.id.split("_")[1]}, PageContext.params)
					);
				}
				return;
			}
			tempNode = tempNode.parentNode;
		}
	});
});

function trace(_sChannelId, _bIsSite, _sRights, _channelType){
	var oParams = {
		RightValue : _sRights || '0',
		ChannelType : _channelType || '0'
	};
	if(_bIsSite) {
		oParams.siteid = _sChannelId;
	}else{
		oParams.channelid = _sChannelId;
	}
	$changeSheet('?' + $toQueryStr(oParams));
}

HTMLElementParser.isTitleField = function(element){
	return Element.hasClassName(element, "normalColumn1");
};

function isDisabled(rightValue, oprkey){
	var rightIndex = PageContext.getRightIndex(oprkey);
	if(!isAccessable4WcmObject(rightValue, rightIndex)){
		return "_disabled";
	}
	return "";
}