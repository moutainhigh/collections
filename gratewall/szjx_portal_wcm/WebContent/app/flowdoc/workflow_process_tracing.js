if(!window.PageContext){
	var PageContext = {};
}
Object.extend(PageContext,{
	loadPage : function(){
		var params = PageContext.params;
		//输出文档信息
		$('spTitle').innerHTML		= $transHtml(params['title']||"");
		$('spTitle').title			= params['title'];
		$('spCrUser').innerHTML		= params['cruser'];
		$('spCrTime').innerHTML		= params['crtime'];
		//绑定轨迹信息
		this.loadTracing();
	},
	loadTracing : function(){
		if(PageContext.params['gunter_view'] == true) {
			$('rdGunter').checked = true;
			this.drawGunter();
		}else{
			$('rdList').checked = true;
			this.drawList();
		}
	},
	drawList : function(){
			setTimeout(function(){
				//根据当前传入的flowid，找到要高亮显示的那一行
				var curNode = $('list_node_' + PageContext.params['flowdocid']);
				//如果没有找到，高亮显示第一行
				if(curNode == null) {
					var rows = $('grid_data').children;
					if(rows != null && rows.length > 0) {
						curNode = rows[0];
					}
				}
				if(curNode == null) return;
				Element.addClassName(curNode, 'current_list_node');
				curNode.style.backgroundImage = 'url(../images/workflow/bg_tracing_s'
					+ this.__getOptionFlag(curNode) + '.gif)';
				//curNode.scrollIntoView();
			}.bind(this), 10);
			PageContext.isListLoaded = true;
			Element.hide('divGunter');
			Element.show('divList');
	},
	drawGunter : function(){
			setTimeout(function(){
				//根据当前传入的flowid，找到要高亮显示的那一个
				var curNode = $('gunter_node_' + PageContext.params['flowdocid']);
				//alert(curNode + '\n' + PageContext.params['flowid']);
				//如果没有找到，高亮显示第一个
				if(curNode == null) {
					var rows = $('divGunter').children;
					if(rows != null && rows.length > 0) {
						curNode = rows(rows.length - 1);
					}			
				}
				if(curNode == null) return;
				Element.addClassName(curNode, 'current_gunter_node');
				curNode.style.backgroundImage = 'url(../images/workflow/bg_tracing_'
					+ this.__getOptionFlag(curNode) + '.gif)';
				//alert(curNode.style.backgroundImage)
				//curNode.scrollIntoView();
			}.bind(this), 10);	
			PageContext.isGunterLoaded = true;
			Element.hide('divList');
			Element.show('divGunter');
	},
	__getOptionFlag : function(_curNode){
		var flag = _curNode.getAttribute('_flag', 2);
		var worked = _curNode.getAttribute('_worked', 2);
		//如果flag值为1(“正在流转”)，但是“worked”返回状态为1，flag仍然取5(“已处理”)
		if(flag == 1 && worked == 1) {
			flag = 5;
		}
		//ge gfc add @ 2008-1-2 10:54 如果flag值4（“重新指派”），flag仍然取5(“已处理”)
		if(flag == 4) {
			flag = 5;
		}

		delete _curNode;
		return flag;
	}
})
function init(_params){
	//绑定轨迹信息 
	PageContext.params = _params;
	PageContext.loadPage();
	initWorkFlowMode();
	PageContext.isListLoaded = false;
}

function doOK(){
	window.parent.PageContext.params['gunter_view'] = $('rdGunter').checked;		 
}

function openConstruction(event){
	Event.stop(event || window.event);
	var params = {
		'FlowId' : nFlowId || 0,
		'ContentType' : PageContext.params['ctype'],
		'ContentId' : PageContext.params['cid'],
		'Title' : PageContext.params['title'],
		'CrUser' : PageContext.params['cruser'],
		'CrTime' : PageContext.params['crtime']
	}
	$openCentralWin('workflow_process_tracing_construction.jsp?' + $toQueryStr(params));
}

function initWorkFlowMode(){
	if(window.nFlowId == 0 && window.nFlowDocId > 0){
		//var dom = $('workflowmode');
		//dom.innerHTML = dom.innerHTML + "<b style='color:red;font-size:12px;'>(" + (wcm.LANG['IFLOWCONTENT_97'] || "该节点的工作流已经删除") + ")</b>";
		Event.observe('workflowmode', 'click', function(){
			Ext.Msg.alert(wcm.LANG['IFLOWCONTENT_97'] || "该节点的工作流已经删除");
		}, false);
		return;
	}
	Event.observe('workflowmode', 'click', openConstruction, false);
}

function showDetail(event, lnk){
	//var x = Event.pointerX(event);
	//var y = Event.pointerY(event);
	var position = Position.page(lnk);
	x = position[0];
	y = position[1];
	var arComments = getComments(lnk);
	var arItems = [];
	for (var i = 0; i < arComments.length; i++){
		var comment = arComments[i];
		var sHtml = '<div class="dv_item">';
		sHtml += '<span class="sp_title">' + comment[0] + '：</span>';
		sHtml += '<span class="sp_detail">' + comment[1] + '</span>';
		sHtml += '</div>';
		arItems.push(sHtml);
	}
	var sContent = arItems.join('<div class="dv_item_sep"></div>');
	Element.update('content',sContent);
	var oBubbler = new wcm.BubblePanel('showDetail');
	oBubbler.bubble([x-10,y + 15], null, function(p){
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

function getComments(srcElement){
		var arComments = [];
		var nodes = srcElement.childNodes;
		for(var i = 0; i < nodes.length; i++){
			var node = nodes[i];
			if(node.nodeType != 1) continue;
			var aSpan = node.getElementsByTagName("span");
			arComments.push([aSpan[0].innerHTML, aSpan[1].innerHTML]);
		}
		return arComments;
	} 
function switchMode(_event, _nType){
	if(_nType == 1) {
		 PageContext.drawList();
	}else{
		 PageContext.drawGunter();
	}
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