Ext.ns('PageContext.literator');
//栏目导航条
/*
info = {
	litid : '',

}
*/
PageContext.literator = {
	enable : false,
	litid : 'literator_path',
	url : WCMConstants.WCM6_PATH + 'include/entity_path.jsp',
	params : {
		sitetypeid : PageContext.getParameter('SiteType'),
		siteid : PageContext.getParameter('SiteId'),
		channelid : PageContext.getParameter('ChannelId')
	},
	width : 350
};
Ext.apply(PageContext, {
	drawLiterator : function(){
		if(!PageContext.literator)return;
		var config = PageContext.literator;
		(config.doBefore || Ext.emptyFn)();
		if(!config.enable)return;
		var eLit = $(config.litid);
		if(eLit == null)return;
		Ext.get(eLit).on('click', function(event, target){
			if(target.tagName=='A')return;
			var config = this;
			var eLit = $(config.litid);
			if(!eLit.getAttribute('showmore'))return;
			if(!Element.hasClassName(target, 'literator_more_btn'))return;
			var oBubbler = new wcm.BubblePanel('literator_more');
			var point = event.getPoint();
			var x = point.x + 4;
			var y = point.y + 4;
			oBubbler.bubble([x,y], function(_Point){
				return [_Point[0]-this.offsetWidth,_Point[1]];
			});
			//TODO bubble
		}.bind(config));
		var url = config.url;
		if(url == null)return;
		var params = $toQueryStr(config.params);
		params += (params.length>0 ? '&' : '') + 'random=' + Math.random();
		new Ajax.Request(url, {
			method : 'get',
			parameters : params,
			onSuccess : function(_tran){
				var config = this;
				var litid = config.litid;
				var lit = $(litid);
				lit.innerHTML = _tran.responseText;
				PageContext.createWardOfLiterator();
				PageContext.bindEventsOfLiterator(litid);
			}.bind(config)
		});
	},
	createWardOfLiterator : function(_sLitId){
		var config = PageContext.literator;
		if(!config.enable)return;
		var literator = $(config.litid);
		if(!window.literatorInited){
			//init the literator style.
			literator.style.width = getWidthOfLiterator(config.litid) + 'px';
			literator.style.display = 'inline-block';
			literator.style.whiteSpace = "nowrap";
			literator.style.overflow = "hidden";
			literator.style.textOverflow = "ellipsis";
			literator.style.margin = "0px 5px";
			literator.scrollLeft = literator.offsetWidth;
				
			//create ward.
			var createWard = function(sInnerText, oParentNode, oNode){
				var ward = document.createElement("span");
				oParentNode.insertBefore(ward, oNode);
				ward.style.visibility = 'hidden';
				ward.style.color = "blue";
				ward.innerText = sInnerText;
				return ward;
			};
			var foreWard = createWard("<<", literator.parentNode, literator);
			var backWard = createWard(">>", literator.parentNode, getNextHTMLSibling(literator));
		}else{
			var foreWard = getPreviousHTMLSibling(literator);	
		}
		if(literator.scrollLeft > 0){
			foreWard.style.visibility = 'visible';
		}
	},
	
	bindEventsOfLiterator : function(_sLitId){
		if(window.literatorInited){
			return;
		}
		var config = PageContext.literator;
		if(!config.enable)return;
		var literator = $(config.litid);
		var foreWard = getPreviousHTMLSibling(literator);
		var backWard = getNextHTMLSibling(literator);

		Event.observe(foreWard, 'mouseover', function(){
			backWard.style.visibility = 'visible';
			window.foreWardHandler = setInterval(function(){
				var oldScrollLeft = literator.scrollLeft;
				literator.scrollLeft = parseInt(literator.scrollLeft, 10) - 5;
				if(literator.scrollLeft == oldScrollLeft){
					foreWard.style.visibility = 'hidden';
				}
			},40);
		});
		Event.observe(foreWard, 'mouseout', function(){
			clearInterval(window.foreWardHandler);
		});
		Event.observe(backWard, 'mouseover', function(){
			foreWard.style.visibility = 'visible';
			window.backWardHandler = setInterval(function(){
				var oldScrollLeft = literator.scrollLeft;
				literator.scrollLeft = parseInt(literator.scrollLeft, 10) + 5;
				if(literator.scrollLeft == oldScrollLeft){
					backWard.style.visibility = 'hidden';
				}
			},40);
		});
		Event.observe(backWard, 'mouseout', function(){
			clearInterval(window.backWardHandler);
		});
		Event.observe(window, 'resize', function(_sLitId){
			literator.style.width = getWidthOfLiterator(_sLitId);
			literator.scrollLeft = literator.offsetWidth;
			foreWard.style.visibility = 'hidden';
			backWard.style.visibility = 'hidden';
			if(literator.scrollLeft > 0){
				foreWard.style.visibility = 'visible';
			}
		}.bind(window, _sLitId));
		window.literatorInited = true;
	},
	traceLiterator : function(siteOrChannelId, siteOrChannel, right){
		var context = {
			objId : siteOrChannelId,
			objType : arguments.length <= 1 ? 
					WCMConstants.OBJ_TYPE_WEBSITEROOT :
						(siteOrChannel ? WCMConstants.OBJ_TYPE_WEBSITE : WCMConstants.OBJ_TYPE_CHANNEL)
		};
		context.tabType =  PageContext.getActiveTabType(context);
		$MsgCenter.$main(context).redirect();
	}
});
function getWidthOfLiterator(_sLitId){
	var literator = $(_sLitId);
	//get width for the literator.
	var bodyWidth = parseInt(document.body.offsetWidth, 10);
	var sQueryBox = $('list-order-box') ? 'list-order-box' : "query_box";
	var queryBoxWidth = 300; 
	if($(sQueryBox)){
		var width = parseInt($(sQueryBox).offsetWidth, 10);
		if(width > queryBoxWidth){
			queryBoxWidth = width;
		}
	}
	var previousSibling = getPreviousHTMLSibling(literator);
	var previousWidth = 0;
	if(previousSibling && previousSibling.id != sQueryBox){
		previousWidth = previousSibling.offsetWidth;
	}
	var width = bodyWidth - queryBoxWidth - previousWidth - 250;
	if($MsgCenter.getActualTop().m_bClassicList || location.href.indexOf('_classic_') > 0)
		width = bodyWidth - previousWidth - 250;
	return width > 0 ? width : 30;
}
function getPreviousHTMLSibling(domNode){
	if(domNode == null) return null;
	var tempNode = domNode.previousSibling;
	while(tempNode && tempNode.nodeType != 1){
		tempNode = tempNode.previousSibling;
	}
	return tempNode;
}
function getNextHTMLSibling(domNode){
	if(domNode == null) return null;
	var tempNode = domNode.nextSibling;
	while(tempNode && tempNode.nodeType != 1){
		tempNode = tempNode.nextSibling;
	}
	//fix <p>ab<b>sf</p>ddsf</b>sd<div>dsf</div>d
	return ((tempNode == null)||(tempNode.parentNode != domNode.parentNode)) ? null : tempNode;
}
