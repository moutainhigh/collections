/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_widget',
	methodName : 'jSelectQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"pageSize" : "20",
		"WidgetCategory" : getParameter('WidgetCategory')
	}
});


wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			WName : sValue
		});
	}
});

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '资源'
});

function getHelper(){
	return new com.trs.web2frame.BasicDataHelper();
}



//register for thumb list cmds.
wcm.ThumbList.register({
	selectWidget : function(properties){
		var oPostData = {
			templateId : getParameter("templateId"),
			widgetId : properties['id']
		};
		getHelper().Call('wcm61_visualtemplate', 'saveWidgetInstance', oPostData, true, function(_trans,json){
			var oparams = {
				bAdd : 1,
				oldWidgetInstanceId : getParameter("oldWidgetInstanceId"),
				pageStyleId : getParameter("pageStyleId"),
				widgetInstanceId : $v(json,"RESULT")
			};
				//需要给专题页面返回资源实例id
			wcm.CrashBoarder.get('set_widgetparameter_value').show({
				title : '设置资源属性',
				src : 'widget/widgetparameter_set.jsp',
				width:'850px',
				height:'400px',
				params : oparams,
				maskable:'true',
				callback : function(params){
					if(params){
						var cbr = wcm.CrashBoarder.get('add-widget-to-page');
						setRecentWidgetInCookie(properties['id']);
						cbr.notify({id:oparams['widgetInstanceId'], wName:properties['WNAME']});
						cbr.close();
					}else{
						this.close();
					}
				}
			});	
		});
		
	}
});

//将最新访问的几个资源块记录在cookie中
function setRecentWidgetInCookie(_selId){
	if(_selId <= 0)return;
	var currCookieForWidget = getCookieValue('WidgetIds');
	var aCurrCookieForWidget = currCookieForWidget.split('/');
	//经重复的id从记录的cookie中移除掉
	aCurrCookieForWidget = removeRepeatCookieId(_selId, aCurrCookieForWidget);
	//当前已经存储的个数为6，则移除掉最后一位。
	if(aCurrCookieForWidget.length == 6){
		aCurrCookieForWidget = aCurrCookieForWidget.slice(0, (aCurrCookieForWidget.length-1));
	}
	//循环往后移位，将当前放置第一位
	aCurrCookieForWidget = moveToAfterAndSetFirst(_selId, aCurrCookieForWidget);
	//将数组组织为一个用/分隔的串
	currCookieForWidget = aCurrCookieForWidget.join('/');
	//设置当前cookie
	document.cookie='WidgetIds=' + currCookieForWidget;
}
function removeRepeatCookieId(_selId, aCurrCookieForWidget){
	if(aCurrCookieForWidget.length == 0 || _selId <= 0)
		return aCurrCookieForWidget;
	for(var i=0; i<aCurrCookieForWidget.length; i++){
		if(aCurrCookieForWidget[i] != _selId) 
			continue;
		else{
			if(i < (aCurrCookieForWidget.length - 1)){
				for(var p = i; p < aCurrCookieForWidget.length; p++){
					if(p == aCurrCookieForWidget.length - 1)continue;
					aCurrCookieForWidget[p] = aCurrCookieForWidget[p+1];
				}
			}
			aCurrCookieForWidget = aCurrCookieForWidget.slice(0, (aCurrCookieForWidget.length-1));
			break;
		}
	}
	return aCurrCookieForWidget;
}

function moveToAfterAndSetFirst(_selId, aCurrCookieForWidget){
	if(aCurrCookieForWidget.length > 0){
		for(var k=aCurrCookieForWidget.length-1; k>=0; k--){
			aCurrCookieForWidget[k+1] = aCurrCookieForWidget[k];
		}
	}
	aCurrCookieForWidget[0] = _selId;
	return aCurrCookieForWidget;
}

function getCookieValue(_cookieKey){
	var cookieValue = '';
	var arrStr = document.cookie.split(";");
	for(var k = 0; k < arrStr.length; k++){
		var temp = arrStr[k].split("=");
		if(temp[0].trim() == _cookieKey){
			cookieValue = temp[1];
			break;
		}
	}
	return cookieValue;
}

function resizeIfNeed(_loadImg){
	if(!_loadImg) return;
	var maxWidth = 260;
	var maxHeight = 200;

	getFittableImageSize(_loadImg, maxWidth, maxHeight, function(width, height){
		_loadImg.width = width;
		_loadImg.height = height;
	});
}


function getFittableImageSize(imgDom, maxWidth, maxHeight, fCallback){
	if(!imgDom) return;
	var img = new Image();
	img.onload = function(){
		var height = img.height, width = img.width;

		if(height > maxHeight || width > maxWidth){	
			if(height > width){
				width = maxHeight * width/height;
				height = maxHeight
			}else{
				height = maxWidth * height/width;
				width = maxWidth;
			}
		}
		img.onload = null;
		if(fCallback) fCallback(width, height);
	}
	img.src = imgDom.src;
}


(function(){
	
	function getTopWin(){
		try{
			return window.top;
		}catch(error){
			return window;
		}
	}

	window.imageMouseOver = function(event, imgDom, index){
		initBigImageBox();
		var srcElement = Event.element(event||window.event);
		var position = Position.getPageInTop(srcElement);
		getFittableImageSize(imgDom, maxWidth, maxHeight, function(width, height){
			var imgEl = getTopWin().$(bigImageId), tmpBigImageBox = getTopWin().$(bigImageBoxId);
			imgEl.src = imgDom.src;
			imgEl.style.width = width + "px";
			imgEl.style.height = height + "px";

			var offset = (index % 2 == 0) ? -width - 5 : imgDom.offsetWidth + 5;
			tmpBigImageBox.style.left = Math.max(0, position[0] + offset) + 'px';
			tmpBigImageBox.style.top = position[1] + 'px';
			tmpBigImageBox.style.width = width + "px";
			tmpBigImageBox.style.height = height + "px";
			tmpBigImageBox.style.display = 'block';
		});
	}

	var maxWidth = 900;
	var maxHeight = 600;
	var bigImageBoxId = 'trs-big-image-box';
	var bigImageId = 'trs-big-image';

	function initBigImageBox(){
		if(getTopWin().$(bigImageBoxId)) return;

		var tmpBigImageBox = getTopWin().document.createElement('div');
		tmpBigImageBox.id = bigImageBoxId;
		tmpBigImageBox.style.visibility = 'hidden';
		Element.addClassName(tmpBigImageBox, 'big-image-box');
		getTopWin().document.body.appendChild(tmpBigImageBox);

		var arrow = getTopWin().document.createElement('div');
		Element.addClassName(arrow, 'arrow');
		tmpBigImageBox.appendChild(arrow);

		var imgEl = getTopWin().document.createElement('img');
		imgEl.id = bigImageId;
		tmpBigImageBox.appendChild(imgEl);
	}

	window.imageMouseOver = function(event, thumb, index){
		initBigImageBox();
		var imgDom = thumb.getElementsByTagName('img')[0];

		var position = Position.getPageInTop(thumb);
		getFittableImageSize(imgDom, maxWidth, maxHeight, function(width, height){
			var imgEl = getTopWin().$(bigImageId), tmpBigImageBox = getTopWin().$(bigImageBoxId);
			imgEl.src = imgDom.src;
			imgEl.style.width = width + "px";
			imgEl.style.height = height + "px";

			if(height < 114){
				tmpBigImageBox.style.height = "140px";
			}else{
				tmpBigImageBox.style.height = "auto";
			}
			var left = (index % 2 == 0) ? (position[0] - imgEl.offsetWidth - 32) : (position[0] + thumb.offsetWidth + 10);
			var top = position[1] - (tmpBigImageBox.offsetHeight - thumb.offsetHeight) / 2;
			if(top < 0) top = 0;
			var topBodyHeight = getTopWin().document.body.offsetHeight;
			if((top + tmpBigImageBox.offsetHeight) > topBodyHeight) top = topBodyHeight - tmpBigImageBox.offsetHeight;

			tmpBigImageBox.style.left = left + 'px';
			tmpBigImageBox.style.top = top + 'px';
			tmpBigImageBox.style.visibility = 'visible';
			Element[index %2 == 0 ? 'addClassName' : 'removeClassName'](tmpBigImageBox, 'arrow-right');
		});
	}

	window.imageMouseOut = function(event, thumb){
		event = event || window.event;
		var toElement = event.toElement;
		if(thumb.contains(toElement)) return;
		var tmpBigImageBox = getTopWin().$(bigImageBoxId);
		if(tmpBigImageBox) tmpBigImageBox.style.visibility = 'hidden';
	}

	window.imageMouseDown = function(event, thumb){
		var tmpBigImageBox = getTopWin().$(bigImageBoxId);
		if(tmpBigImageBox) tmpBigImageBox.style.visibility = 'hidden';
	}

})();