var PageContext = {};
Object.extend(PageContext,{
	loadPage : function(){
		var params = PageContext.params;
		//输出文档信息
		$('spTitle').innerHTML		= $trans2Html(params['title']);
		$('spTitle').title			= $transHtml(params['title']);
		$('spCrUser').innerHTML		= $trans2Html(params['cruser']);
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
				var curNode = $('list_node_' + PageContext.params['flowid']);
				//如果没有找到，高亮显示第一行
				if(curNode == null) {
					var rows = $('grid_data').children;
					if(rows != null && rows.length > 0) {
						curNode = rows(0);
					}
				}
				if(curNode == null) return;
				Element.addClassName(curNode, 'current_list_node');
				curNode.style.backgroundImage = 'url(../images/workflow/bg_tracing_s'
					+ this.__getOptionFlag(curNode) + '.gif)';
				curNode.scrollIntoView();
			}.bind(this), 10);
			PageContext.isListLoaded = true;
			Element.hide('divGunter');
			Element.show('divList');
	},
	drawGunter : function(){
			setTimeout(function(){
				//根据当前传入的flowid，找到要高亮显示的那一个
				var curNode = $('gunter_node_' + PageContext.params['flowid']);
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
				curNode.scrollIntoView();
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

	PageContext.isListLoaded = false;
}

function doOK(){
	window.parent.PageContext.params['gunter_view'] = $('rdGunter').checked;		 
}

function openConstruction(){
	var params = {
		'ContentType' : PageContext.params['ctype'],
		'ContentId' : PageContext.params['cid'],
		'Title' : PageContext.params['title'],
		'CrUser' : PageContext.params['cruser'],
		'CrTime' : PageContext.params['crtime']
	}
	$openCentralWin('../workflow/workflow_process_tracing_construction.jsp?' + encodeParams(params));
}
 
 function showDetail(event, lnk){
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
		showHelpTip(event, sContent, false);	
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