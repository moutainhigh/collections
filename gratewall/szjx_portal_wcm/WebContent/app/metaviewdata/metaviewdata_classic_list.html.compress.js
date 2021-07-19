Ext.ns("wcm.ListQuery", "wcm.ListQuery.Checker");

(function(){
	var sContent = [
		'<div class="querybox">',
			'<div class="qbr">',
				'<table border=0 cellspacing=0 cellpadding=0 class="qbc">',
					'<tr>',
						'<td class="elebox">',
							'<input type="text" name="queryValue" id="queryValue" onfocus="wcm.ListQuery.focusQueryValue();" onkeydown="wcm.ListQuery.keydownQueryValue(event);">',
							'<select name="queryType" id="queryType" onchange="wcm.ListQuery.changeQueryType();">{0}</select>',
						'</td>',
						'<td class="search" onclick="wcm.ListQuery.doQuery();"><div>&nbsp;</div></td>',
					'</tr>',
				'</table>',
			'</div>',
		'</div>'
	].join("");

	var allFlag = "-1";

	Ext.apply(wcm.ListQuery, {
		/**
		 * @cfg {String} container
		 * the container of query box to render to.
		 */
		/**
		 * @cfg {Boolean} appendQueryAll
		 * whether append the query all item or not, default to false.
		 */
		/**
		 * @cfg {Boolean} autoLoad
		 * whether the query box auto loads itself or not, default to true.
		 */
		/**
		*@cfg {String} maxStrLen
		*the max length of string value. default to 100
		*/
		/**
		 * @cfg {Object} items
		 * the query items of query box.
		 *eg. {name : 'id', desc : '站点', type : 'string'}
		 */
		/**
		 * @cfg {Function} callback
		 * the callback when user clicks the search button.
		 */
		config : null,
		register : function(_config){
			var config = {maxStrLen : 100, appendQueryAll : false, autoLoad : true};
			Ext.apply(config, _config);
			if(config["appendQueryAll"]){
				config["items"].unshift({name: allFlag, desc: WCMLANG["LIST_QUERY_ALL_DESC"] || "全部", type: 'string'});
			}
			this.config = config;
			if(config["autoLoad"]){
				if(document.body){
					this.render();
				}else{
					Event.observe(window, 'load', this.render.bind(this), false);
				}
			}
			return this;
		},
		render : function(){
			var sOptHTML = "";
			var items = this.config.items;
			for (var i = 0; i < items.length; i++){
				sOptHTML += "<option value='" + items[i].name + "' title='"+ items[i].desc + "'>" +  items[i].desc + "</option>";
			}
			Element.update(this.config.container, String.format(sContent, sOptHTML));
			$('queryValue').value = this.getDefaultValue();
		},
		changeQueryType : function(){
			var eQVal = $('queryValue');
			if(eQVal.value.indexOf(WCMLANG["LIST_QUERY_INPUT_DESC"]||"..输入") >= 0) {
				eQVal.value = this.getDefaultValue();
				eQVal.style.color = 'gray';
			}
			eQVal.select();
			eQVal.focus();
		},
		keydownQueryValue : function(event){
			event = window.event || event;
			if(event.keyCode == 13){
				Event.stop(event);
				this.doQuery();
			}
		},
		focusQueryValue : function(){
			var eQVal = $('queryValue');
			eQVal.style.color = '#414141';
			eQVal.select();
		},
		getDefaultValue : function(){
			var nIndex = $('queryType').selectedIndex;
			if(nIndex < 0) return "";
			var oItem =  this.getItem(nIndex);
			return (WCMLANG["LIST_QUERY_INPUT_DESC"]||"..输入") + (oItem["name"] == allFlag ? (WCMLANG["LIST_QUERY_JSC_DESC"]||"检索词") : oItem["desc"]);
		},
		getItem : function(_index){
			return this.config["items"][_index];
		},
		getParams : function(){
			var params = {};
			var sQType = $F("queryType");
			var sQValue= $F("queryValue");
			if(this.getDefaultValue() == sQValue){
				sQValue = "";
			}
			if(sQType == allFlag){
				params["isor"] = true;
				var items = this.config["items"];
				for (var i = 0; i < items.length; i++){
					var item = items[i];
					if(item["name"] == allFlag) continue;
					if(this.valid(item).isFault) continue;
					params[item["name"]] = sQValue;
				}
			}else{
				params["isor"] = false;
				params[sQType] = sQValue;
			}
			return params;
		},
		valid : function(item){
			var sQValue = $F("queryValue").trim();
			var sType = item["type"] || '';
			sType = sType.toLowerCase();
			var checker = wcm.ListQuery.Checker;
			var result = (checker[sType]||checker['default'])(sQValue, item);
			return {isFault : !!result, msg : result}
		},
		clearLastParams : function(){
			if(!window.PageContext || !PageContext.params) return;
			var params = PageContext.params;
			var items = this.config["items"];
			for (var i = 0; i < items.length; i++){
				var item = items[i];
				delete params[item["name"]];
				delete params[item["name"].toUpperCase()];
			}
			delete params["SelectIds"];
		},
		doQuery : function(){
			//check the valid.
			var validInfo = this.valid(this.getItem($('queryType').selectedIndex));
			if(validInfo.isFault) {
				Ext.Msg.$alert(validInfo["msg"]);
				return;
			}
			//exec the callback.
			if(this.config.callback){
				this.clearLastParams();
				this.config.callback(this.getParams());
			}
		}
	});

	//wcm.ListQuery.Checker
	Ext.apply(wcm.ListQuery.Checker, {
		'default' : function(){
			return false;
		},
		"int" : function(sValue){
			if(sValue.trim().length == 0) return false;
			if(wcm.ListQuery.getDefaultValue() == sValue) return false;
			var nIntVal = parseInt(sValue, 10);
			if(!(/^-?[0-9]+\d*$/).test(sValue)) {
				return WCMLANG["LIST_QUERY_INT_MIN"] || "要求为整数！";
			}else if(nIntVal > 2147483647){
				return WCMLANG["LIST_QUERY_INT_MAX"] || '要求在-2147483648~2147483647(-2^31~2^31-1)之间的数字！';
			}
			return false;
		},
		"float" : function(sValue){
			if(sValue.trim().length == 0) return false;
			if(sValue.match(/^-?[0-9]+(.[0-9]*)?$/) == null){
				return WCMLANG["LIST_QUERY_FLOAT"] || "要求为小数！";
			}
			return false;
		},
		"double" : function(sValue){
			if(sValue.trim().length == 0) return false;
			if(sValue.match(/^-?[0-9]+(.[0-9]*)?$/) == null){
				return WCMLANG["LIST_QUERY_FLOAT"] || "要求为小数！";
			}
			return false;
		},
		"string" : function(sValue, item){
			var nDefMaxLen = wcm.ListQuery.config["maxStrLen"];
			var nItemMaxLen = parseInt(item["maxLength"], 10) || nDefMaxLen;
			var nMaxLen = Math.min(nDefMaxLen, nItemMaxLen);
			if(sValue.length > nMaxLen){
				return '<span style="width:180px;overflow-y:auto;">'+String.format("当前检索字段限制为[<b>{0}</b>]个字符长度，当前为[<b>{1}</b>]！<br><br><b>提示：</b>每个汉字长度为2。", nMaxLen, sValue.length)+'</span>';
			}
			return false;
		},
		"date" : function(sValue, item){
			var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
			if(sValue && !reg.test(sValue)){
				return '<span style="width:180px;overflow-y:auto;">当前检索字段限制为日期类型！<br><br><b>提示：</b>如yyyy-MM-dd。</span>';
			}
			return false;
		}
	});
})();
//经典列表
Ext.ns('ClassicList.cfg');
var m_sToolbarTemplate = {
	item : [
		'<table class="toolbar_item {3}" {4} id="{0}" {2}>',
		'<tr>',
			'<td style="width:16px;"><div class="toolbar_icon {0}">&nbsp;</div></td>',
			'<td class="toolbar_cont" nowrap="true">{1}</td>',
		'</tr>',
		'</table>'
	].join(''),
	sep : [
		'<table class="toolbar_sep">',
		'<tr>',
			'<td>&nbsp;</td>',
		'</tr>',
		'</table>'
	].join(''),
	main : [
		'<table cellspacing="0" cellpadding="0" border="0" valign="top" class="list_table">',
			'<tr>',
				'<td height="26" class="head_td">',
				'<span>{0}：</span>',
				'<span id="literator_path"></span></td>',
				'<td class="head_td">{2}</td>',
			'</tr>',
		'</table>',
		'<table cellspacing="0" cellpadding="0" border="0" class="toolbar">',
			'<tr>',
				'<td height="32" valign="center" id="toolbar_container" style="visibility:hidden;">{1}</td>',
				'<td id="query_box"></td>',
				'<td width="20">&nbsp;</td>',
			'</tr>',
		'</table>'
	].join(''),
	morebtn : [
		'<span class="toolbar_more_btn" style="display:{1};" id="toolbar_more_btn">&nbsp;</span>',	
		'<div id="more_toolbar" class="more_toolbar" style="display:none;">{0}</div>'
	].join('')
};

function _mergeRight(objs){
	var arrRight = [];
	for (var i=0,n=objs.length(); i<n; i++){
		arrRight.push(objs.getAt(i).right);
	}
	return wcm.AuthServer.mergeRights(arrRight);
}

function getRight(item, event){
	if(!event) return wcm.AuthServer.getRightValue();
	var host = event.getContext().getHost();
	if(item.isHost) return host.right;
	var objs = event.getObjs();
	if(objs.length()==0) return host.right;
	if(objs.length()>1) return _mergeRight(objs);
	return objs.getAt(0).right;
}

function toToolbarHtml(cfg){
	var result = [];
	var moreResult = [];
	var json = {};
	var displayNum = window.screen.width <= 1024 ? 4 : 8;
	var nSep = 0;
	for(var i=0, n=cfg.length; i<n; i++){
		var item = cfg[i];
		if(item=='/'){
			result.push(m_sToolbarTemplate.sep);
			nSep ++;
			continue;
		}
		var bisvisible = true;
		if(item.isVisible){
			bisvisible = item.isVisible(PageContext.event);
		}
		if(!bisvisible){
			nSep ++;
			continue;
		}
		var event = PageContext.event;
		var right = getRight(item, event);
		json[item.id.toLowerCase()] = item;
		var bDisabled = (item.isDisabled && item.isDisabled(PageContext.event)) ||
			(item.rightIndex != undefined && !wcm.AuthServer.checkRight(right, item.rightIndex));
		item.disabled = bDisabled;
		if(i-nSep<displayNum){
			result.push(String.format(m_sToolbarTemplate.item,
					item.id.toLowerCase(), item.name, (item.desc?("title='"+item.desc+"'"):""),
					bDisabled ? 'toolbar_item_disabled' : '',
					bDisabled ? 'disabled' : ''
				)
			);
		}else{
			moreResult.push(String.format(m_sToolbarTemplate.item,
					item.id.toLowerCase(), item.name, (item.desc?("title='"+item.desc+"'"):""),
					bDisabled ? 'toolbar_item_disabled' : '',
					bDisabled ? 'disabled' : ''
				)
			);
		}
	}
	result.push(String.format(m_sToolbarTemplate.morebtn,
			moreResult.join(''), moreResult.length > 0 ? '' : 'none'));
	return {
		html : result.join(''),
		json : json
	};
}
function refreshToolbars(cfg){
	var result = toToolbarHtml(cfg);
	$('toolbar_container').style.visibility = 'visible';
	$('toolbar_container').innerHTML = result.html;
}
function doClassicList(){
	var loaded = false;
	ClassicList.makeLoad = ClassicList.autoLoad = function(){
		if(loaded)return;
		loaded = true;
		var arrToolbarCfg = ClassicList.cfg.toolbar || [];
		var result = toToolbarHtml(arrToolbarCfg);
		$('classic_cnt').innerHTML = String.format(m_sToolbarTemplate.main,
			ClassicList.cfg.listTitle || "",
			result.html,
			ClassicList.cfg.path || ""
		);
		function findTarget(target){
			while(target!=null && target.tagName!='BODY'){
				if(Element.hasClassName(target, 'toolbar_item'))return target;
				target = target.parentNode;
			}
			return null;
		}
		function clickToolbarMoreBtn(event, target){
			var p = event.getPoint();
			var x = p.x + 4;
			var y = p.y + 4;
			var bubblePanel = new wcm.BubblePanel($('more_toolbar'));
			bubblePanel.bubble([x,y], function(_Point){
				return [_Point[0], _Point[1]];
			});
		}
		Ext.get('classic_cnt').on('click', function(event, target){
			if(target.id == 'toolbar_more_btn'){
				clickToolbarMoreBtn.apply(this, arguments);
				return;
			}
			var target = findTarget(target);
			if(target==null || target.id==null)return;
			var toolbarItem = result.json[target.id];
			if(toolbarItem==null || !toolbarItem.fn)return;
			if(toolbarItem.disabled)return;
			toolbarItem.fn.call(null, PageContext.event, target);
		});
	}
}
doClassicList();
Event.observe(window, 'load', ClassicList.autoLoad);
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afterselect : function(event){
		PageContext.event = event;
		try{
			refreshToolbars(ClassicList.cfg.toolbar);
		}catch(err){
		}
	}
});
var aFieldType = ['check' , 'appendix' ,'radio' , 'editor'];

Event.observe(window, 'load', function(){
	Event.observe(document.body, 'click', function(event){
		EventHandler.dispatch(window.event || event);
	}, false)
});


var EventHandler = {
	dispatch : function(event){
		//先找到事件触发元素
		var dom = Event.element(event);
		//在事件元素外（的target元素上）包含事件产生所需要的属性。
		dom = this.findTarget(dom);
		if(!dom) return;
		//根据属性调用方法
		var type = dom.getAttribute('_type');
		//是单个数据类型的问题交给单个来处理。
		(this[type] || Ext.emptyFn)(dom, event);
	},
	findTarget : function(dom){
		while(dom){
			if(dom.tagName == "BODY") return null;
			if(dom.getAttribute("_type")) return dom;
			dom = dom.parentNode;
		}
		return null;
	},
	appendix : function(dom, event){
		var bIsTitleField = Element.hasClassName(dom.parentNode,"titleField");
		if(!bIsTitleField){
			var sFileName = dom.getAttribute("value");
			if(sFileName != ""){
				FileDownloader.download("/wcm/file/read_file.jsp?FileName=" + sFileName);
			}
		}
	}
};
var FileUploader = Class.create();
Object.extend(FileUploader, {
	_cache_ : [],
	onUploadAll : null,
	getCache : function(){
		return this._cache_;
	},
	setUploadAll : function(fUploadAll){
		this.onUploadAll = fUploadAll;
	},
	isEmptyValue : function(){
		return this.isUploadAll();
	},
	isUploadAll : function(){
		for (var i = 0, length = this._cache_.length; i < length; i++){
			var instance = this._cache_[i];
			if(instance.isBindData) return false;
		}
		return true;
	},
	destory : function(){
		for (var i = 0, length = this._cache_.length; i < length; i++){
			var instance = this._cache_[i];
			delete instance["oRelateElement"];
			delete instance["oIframeElement"];
			delete instance["oFileControl"];
			delete instance["fChangeEvent"];
			delete instance["fUploadedCallBack"];
			delete instance["fUploadedCallBack2"];
			this._cache_[i] = null;
		}
		delete FileUploader["onUploadAll"];
		this._cache_ = [];
	}
});
Object.extend(FileUploader.prototype, {
	uploadSrc					: 'file_upload.html',
	uploadDoWithSrc				: 'file_upload_dowith.jsp',
	appendixIframeIdSuffix		: '_iframe',
	appendixFileControlId		: 'fileNameControl',
	oRelateElement				: null,
	fChangeEvent				: null,
	fUploadedCallBack			: null,
	fUploadedCallBack2			: null,
	oIframeElement				: null,
	oFileControl				: null,
	isBindData					: false,

	initialize : function(sRelateElement, fChangeEvent, fUploadedCallBack){
		//1.cache the instance, for destroy.
		FileUploader._cache_.push(this);

		//2.init apperance.
		this.oRelateElement = $(sRelateElement);
		this.fChangeEvent = fChangeEvent;
		this.fUploadedCallBack = fUploadedCallBack;
		this.oIframeElement = document.createElement('iframe');
		this.oIframeElement.id = sRelateElement + this.appendixIframeIdSuffix;
		this.oIframeElement.style.display = 'none';
		this.oIframeElement.src = this.uploadSrc;
		document.body.appendChild(this.oIframeElement);

		//3.init actions--events.
		this.initEvent();
	},
	initEvent : function(){
		//1.bind this.oRelateElement events.
		Event.observe(this.oRelateElement, 'click', this.onBrowse.bind(this));

		//2.bind this.oIframeElement events.
		Event.observe(this.oIframeElement, 'readystatechange', this.onIframeStateChanged.bind(this));
	},
	onBrowse : function(){
		try{			
			if(!this.oFileControl){
				this.oFileControl = this.oIframeElement.contentWindow.document.getElementById(this.appendixFileControlId);
				this.oFileControl.onchange = function(event){
					this.isBindData = true;
					if(this.fChangeEvent){
						this.fChangeEvent(this.oFileControl.value);
					}
				}.bind(this);
			}	
			this.oFileControl.click();
		}catch(error){
			alert(error.message);
		}
	},
	onIframeStateChanged : function(){
		if(!this.isBindData) return;
		if(this.oIframeElement.readyState.toLowerCase() != 'complete') return;
		this.onUpload();
	},
	onUpload : function(){
		var oInfoDiv = this.oIframeElement.contentWindow.document.getElementById("infoId");
		if(oInfoDiv){
			if(oInfoDiv.getAttribute("isError")){
				alert(wcm.LANG.METAVIEWDATA_109 || "上传文件失败！\n" + oInfoDiv.innerText);
			}else{
				this.isBindData = false;
				var fUploadCallBack = this.fUploadedCallBack2 || this.fUploadedCallBack;
				if(fUploadCallBack){
					fUploadCallBack(decodeURI(oInfoDiv.innerHTML));
				}
			}
		}
		if(FileUploader.isUploadAll() && FileUploader.onUploadAll){
			FileUploader.onUploadAll();
		}
		this.oIframeElement.setAttribute("src", this.uploadSrc);
	},
	reset : function(){
		//reset the file control value by reload the page.
		if(!this.oFileControl) return;
		this.isBindData = false;
		this.oFileControl.form.reset();
	},
	doUpload : function(fUploadedCallBack){
		if(!this.oFileControl || !this.isBindData){
			//not trigger the browser action, so return.
			if(FileUploader.isUploadAll() && FileUploader.onUploadAll){
				FileUploader.onUploadAll();
			}
			return;
		}
		this.fUploadedCallBack2 = fUploadedCallBack;
		var sValue = this.oFileControl.value;
		var fileNameValue = sValue.substring(sValue.lastIndexOf("\\")+1);
		var sParams = "fileNameParam=" + this.appendixFileControlId + "&fileNameValue="+encodeURI(fileNameValue);
		var fileForm = this.oFileControl.form;
		fileForm.action = this.uploadDoWithSrc + "?" + sParams;
		fileForm.submit();
	}
});

// destroy the FileUploader cache.
Event.observe(window, 'unload', function(){
	FileUploader.destory();
});
/**-------------------------------------------------------------------------**/

var FileDownloader = Class.create();
Object.extend(FileDownloader, {
	download : function(sUrl){
		var frm = (top.actualTop||top).$('iframe4download');
		if(!frm) {
			frm = (top.actualTop||top).document.createElement('IFRAME');
			frm.id = "iframe4download";
			frm.style.display = 'none';
			(top.actualTop||top).document.body.appendChild(frm);
		}
		frm.src = sUrl;		
	}
});
