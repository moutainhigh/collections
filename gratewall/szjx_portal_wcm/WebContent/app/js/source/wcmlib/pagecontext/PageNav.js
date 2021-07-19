Ext.ns('PageContext');
//分页导航条
Ext.applyIf(PageContext, {
	PageNav : {
		UnitName : wcm.LANG.PAGENAV1 || '条',
		TypeName : wcm.LANG.PAGENAV2 || '记录'
	}
});
Ext.apply(PageContext.PageNav, {
	go : function(_iPage, _maxPage){
		delete PageContext.params["SELECTIDS"];
		PageContext.loadList({
			CurrPage : (_iPage<1)? 1 : ((_iPage > _maxPage) ? _maxPage : _iPage)
		}, null, true);
	},
	applyKeyProvider : function(info){
		if(!PageContext.keyProvider)return;
		Ext.apply(PageContext.keyProvider, {
			ctrlPgUp : function(event){
				PageContext.PageNav.go(info.CurrPageIndex-1, info.PageCount);
			},
			ctrlPgDn : function(event){
				PageContext.PageNav.go(info.CurrPageIndex+1, info.PageCount);
			},
			ctrlEnd : function(event){
				PageContext.PageNav.go(info.PageCount, info.PageCount);
			},
			ctrlHome : function(event){
				PageContext.PageNav.go(1, info.PageCount);
			}
		});
	},
	EffectMe : function(event, _oElement){
		event = event || window.event;
		switch(event.type){
			case 'blur':
				var newNo = parseInt(_oElement.value, 10);
				_oElement.lastNo = _oElement.lastNo || "";
				if(isNaN(newNo)){
					_oElement.value = _oElement.lastNo;
				}
				else{
					_oElement.value = newNo;
				}
				if(_oElement.lastNo!=_oElement.value){
					PageContext.PageNav.go(parseInt(_oElement.value, 10), PageContext.PageNav["PageCount"]);
				}
				break;
			case 'keydown':
				if(event.keyCode==13){
					_oElement.blur();
					return;
				}
//				Event.stop(event);
				break;
		}
	}
});

PageContext.getPageNavHtml = function(iCurrPage, iPages, info){
	var sHtml = '';
	//output first
	if(iCurrPage!=1){
		sHtml += '<span class="nav_page" title="' + (wcm.LANG.PAGENAV4 || "首页") + '" onclick="PageContext.PageNav.go(1, ' + iPages + ');">1</span>';
	}else{
		sHtml += '<span class="nav_page nav_currpage">1</span>';
	}
	var i,j;
	if(iPages-iCurrPage<=1){
		i = iPages-3;
	}
	else if(iCurrPage<=2){
		i = 2;
	}
	else{
		i = iCurrPage-1;
	}
	var sCenterHtml = '';
	var nFirstIndex = 0;
	var nLastIndex = 0;
	//output 3 maybe
	for(j=0;j<3&&i<iPages;i++){
		if(i<=1)continue;
		j++;
		if(j==1)nFirstIndex = i;
		if(j==3)nLastIndex = i;
		if(iCurrPage!=i){
			sCenterHtml += '<span class="nav_page" onclick="PageContext.PageNav.go('+i+','+iPages+');">'+i+'</span>';
		}else{
			sCenterHtml += '<span class="nav_page nav_currpage">'+i+'</span>';
		}
	}
	//not just after the first page
	if(nFirstIndex!=0&&nFirstIndex!=2){
		sHtml += '<span class="nav_morepage" title="' + (wcm.LANG.PAGENAV5 || "更多") + '">...</span>';
	}
	sHtml += sCenterHtml;
	//not just before the last page
	if(nLastIndex!=0&&nLastIndex!=iPages-1){
		sHtml += '<span class="nav_morepage" title="' + (wcm.LANG.PAGENAV5 || "更多") + '">...</span>';
	}
	//output last
	if(iCurrPage!=iPages){
		sHtml += '<span class="nav_page" title="' + (wcm.LANG.PAGENAV6 || "尾页") + '" onclick="PageContext.PageNav.go(' + iPages + ',' + iPages + ');">'+iPages+'</span>';
	}else{
		sHtml += '<span class="nav_page nav_currpage" title="' + (wcm.LANG.PAGENAV6 || "尾页") + '">'+iPages+'</span>';
	}
	return sHtml;
}

PageContext.getNavigatorHtml = function(info){
	var iRecordNum = info.Num;
	if(iRecordNum==0)return '';
	var iCurrPage = info.CurrPageIndex;
	var iPageSize = info.PageSize;
	var iPages = info.PageCount;
	var aHtml = [
		'<span class="nav_page_detail">',
			String.format("共<span class=\"nav_pagenum\">{0}</span>页",iPages),
			',',
			'<span class="nav_recordnum">', iRecordNum, '</span>',
			PageContext.PageNav["UnitName"], 
			(WCMLANG.LOCALE == 'en' ? PageContext.PageNav["TypeName"].toLowerCase() : PageContext.PageNav["TypeName"]),
			(WCMLANG.LOCALE == 'en' ? '(s)' : ''),
			',',
			String.format("<span class=\"nav_pagesize\">{0}</span>{1}/页", iPageSize, PageContext.PageNav["UnitName"])
	];
	if(iPages > 1){
		aHtml.push(
			',',
			String.format("跳转到第{0}页",'<input type="text" name="nav-go" id="nav-go" value="" onkeydown="PageContext.PageNav.EffectMe(arguments[0], this);" onblur="PageContext.PageNav.EffectMe(arguments[0], this);"/>')
		);
	}
	aHtml.push('.</span>');
	var sHtml = aHtml.join("");
	if(iPages>1){
		sHtml += PageContext.getPageNavHtml(iCurrPage, iPages, info);
	}
	return sHtml;
}

/*avoid memory leak*/
PageContext.destroyPageNavHtml = function(){
	//remove input events.
	var dom = $('nav-go');
	if(dom){
		dom.onkeydown = null;
		dom.onblur = null;
	}
	//remove span events.
	var dom = $('list_navigator');
	if(dom){
		var nodes = dom.getElementsByTagName('span');
		for (var i = 0; i < nodes.length; i++){
			if(nodes[i].onclick) nodes[i].onclick = null;
		}
	}
}

/**
PageContext.drawNavigator({
	Num : 100,
	PageSize : 20,
	PageCount : 5,
	CurrPageIndex : 1
});
*/
PageContext.drawNavigator = function(info){
	Ext.apply(PageContext.PageNav, info);
	var eNavigator = $('list_navigator');
	if(!eNavigator)return;
	PageContext.destroyPageNavHtml();
	var sHtml = PageContext.getNavigatorHtml(info);
	Element.update(eNavigator, sHtml);
	//key provider
	PageContext.PageNav.applyKeyProvider(info);
}

