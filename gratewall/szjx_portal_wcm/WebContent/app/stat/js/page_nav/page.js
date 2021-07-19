(function(){
	var sTemplateHtml = "<span class=\"nav_pagesize\"><input type=\"text\" name=\"nav-pagesize\" id=\"nav-pagesize\" value=\"{0}\" onkeydown=\"PageNav.setPageSize(arguments[0], this);\" onblur=\"PageNav.setPageSize(arguments[0], this);\"/></span>{1}/页";
	window.PageNav = {
		go : function(pageIndex, pageCount, pageSize){
			if(pageIndex < 1) pageIndex = 1;
			if(pageIndex > pageCount) pageIndex = pageCount;
			var queryJson = window.location.search.parseQuery();
			// 获取检索组件中其它额外的参数
			if(window.Stat && window.Stat.SearchBar){
				var ext_param = Stat.SearchBar.UI.options.ext_url_param;
				if(typeof(ext_param)=="string")
					Ext.apply(queryJson,ext_param.parseQuery());
				else
					Ext.apply(queryJson,ext_param);
			}
			queryJson["CurrPage"]=pageIndex;
			queryJson["PageSize"]=pageSize;
			location.href = location.href.match(/^[^?]*/)[0]+"?"+parseJsonToParams(queryJson);
		},
		setPageSize : function(event, dom){
			event = event || window.event;
			switch(event.type){
				case 'blur':
					var newNo = parseInt(dom.value, 10);
					dom.lastNo = PageNav["PageSize"] || "";
					if(isNaN(newNo)){
						dom.value = dom.lastNo;
					}
					else{
						dom.value = newNo;
					}

					if(dom.lastNo!=newNo){
						this.go(PageNav["PageIndex"], PageNav["PageCount"], (dom.value < -1) ? -1 : dom.value);
					}
					break;
				case 'keydown':
					if(event.keyCode==13){
						dom.blur();
						Event.stop(event);
					}
					break;
			}
		},
		EffectMe : function(event, dom){
			event = event || window.event;
			switch(event.type){
				case 'blur':
					var newNo = parseInt(dom.value, 10);
					dom.lastNo = PageNav["PageSize"] || "";
					if(isNaN(newNo)){
						dom.value = dom.lastNo;
					}
					else{
						dom.value = newNo;
					}
					if(dom.lastNo!=dom.value){
						PageNav.go(parseInt(dom.value, 10), PageNav["PageCount"], PageNav.PageSize);
					}
					break;
				case 'keydown':
					if(event.keyCode==13){
						dom.blur();
						return;
					}
					break;
			}
		}
	};

	function getPageNavHtml(iCurrPage, iPages, info){
		var sHtml = '';
		//output first
		if(iCurrPage!=1){
			sHtml += '<span class="nav_page" title="首页" onclick="PageNav.go(1, ' + iPages + ',' + PageNav.PageSize + ');">1</span>';
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
				sCenterHtml += '<span class="nav_page" onclick="PageNav.go('+i+','+iPages+',' + PageNav.PageSize+');">'+i+'</span>';
			}else{
				sCenterHtml += '<span class="nav_page nav_currpage">'+i+'</span>';
			}
		}
		//not just after the first page
		if(nFirstIndex!=0&&nFirstIndex!=2){
			sHtml += '<span class="nav_morepage" title="更多">...</span>';
		}
		sHtml += sCenterHtml;
		//not just before the last page
		if(nLastIndex!=0&&nLastIndex!=iPages-1){
			sHtml += '<span class="nav_morepage" title="更多">...</span>';
		}
		//output last
		if(iCurrPage!=iPages){
			sHtml += '<span class="nav_page" title="尾页" onclick="PageNav.go(' + iPages + ',' + iPages + ',' + PageNav.PageSize+');">'+iPages+'</span>';
		}else{
			sHtml += '<span class="nav_page nav_currpage" title="尾页">'+iPages+'</span>';
		}
		return sHtml;
	}

	function getNavigatorHtml(info){
		var iRecordNum = info.Num;
		if(iRecordNum==0)return '';
		var iCurrPage = info.PageIndex;
		var iPageSize = info.PageSize;
		var iPages = info.PageCount;
		var aHtml = [
			'<span class="nav_page_detail">',
				String.format("共<span class=\"nav_pagenum\">{0}</span>页",iPages),
				',',
				'<span class="nav_recordnum">', iRecordNum, '</span>',
				"条记录",
				String.format(sTemplateHtml, iPageSize, "记录")
		];
		if(iPages > 1){
			aHtml.push(
				',',
				String.format("跳转到第{0}页",'<input type="text" name="nav-go" id="nav-go" value="" onkeydown="PageNav.EffectMe(arguments[0], this);" onblur="PageNav.EffectMe(arguments[0], this);"/>')
			);
		}
		aHtml.push('.</span>');
		var sHtml = aHtml.join("");
		if(iPages>1){
			sHtml += getPageNavHtml(iCurrPage, iPages, info);
		}
		return sHtml;
	}
	window.drawNavigator = function(info){
		Ext.apply(PageNav, info);
		var eNavigator = $('list-navigator');
		if(!eNavigator)return;
		var sHtml = getNavigatorHtml(info);
		eNavigator.innerHTML = sHtml;
	}
})();
