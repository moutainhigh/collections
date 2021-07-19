//列表内部打开新列表
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_GRIDROW,
	beforeclick : function(event){
		event.cancelBubble = true;
	},
	afterclick : function(event){
		event.cancelBubble = true;
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CELL,
	beforeclick : function(event){
		event.cancelBubble = true;
	},
	afterclick : function(event){
		event.cancelBubble = true;
	}
});
$MsgCenter.on({
	sid : 'sys_allcmsobjs_cancel',
	objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afterselect : function(event){
		event.cancelBubble = true;
	}
});
PageContext.m_CurrPage = $MsgCenter.$currPage();
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
//中文
if(!window.Calendar)Calendar={};
if(!Calendar.LANG)Calendar.LANG={};
Calendar._SDN = new Array
("日",
 "一",
 "二",
 "三",
 "四",
 "五",
 "六",
 "日");
Calendar.LANG["LAST_MONTH"] = "上月";
Calendar.LANG["NEXT_MONTH"] = "下月";
Calendar.LANG["DD_CLOSE"] = "关闭";
Calendar.LANG["DD_TIME"] = "时间";
Calendar.LANG["DD_FORMATE"] = '日期格式不符合[yyyy-mm-dd hh:mm]!';
Calendar.LANG["DD_TIME_FORMATE"] = '时间格式不符合[hh:mm]!';
Calendar.LANG["DD_FORMATE_error"] = ']日期格式不符合[';
Calendar.LANG["DD_TIME_FORMATE1"]  = '时间格式不符合[hh:mm:ss]!';
Calendar.LANG["DD_ERROR_FORMATE1"] = '不支持的日期格式[';
Calendar.LANG["DD_ERROR_FORMATE2"] = ']没有匹配日期格式[';
Calendar.LANG["DD_ERROR_FORMATE3"] = '月份应该为1到12的整数';
Calendar.LANG["DD_ERROR_FORMATE4"] = '每个月的天数应该为1到31的整数';
Calendar.LANG["DD_ERROR_FORMATE5"] = '该月不存在31号';
Calendar.LANG["DD_ERROR_FORMATE6"] = '2月最多有29天';
Calendar.LANG["DD_ERROR_FORMATE7"] = '闰年2月才有29天';
Calendar.LANG["DD_ERROR_FORMATE8"] = '小时应该是0到23的整数';
Calendar.LANG["DD_ERROR_FORMATE9"] = '分应该是0到59的整数';
Calendar.LANG["DD_ERROR_FORMATE10"] = '秒应该是0到59的整数';
Calendar.LANG["DD_ERROR_FORMATE11"] = ']日期格式不正确[';
if(!window.Calendar)Calendar={};
if(!Calendar.LANG)Calendar.LANG={};
Date.parseDate = function(str, fmt) {
	var a = str.split(/\W+/), b = fmt.match(/%./g);
	var i = 0, h = {};
	for (i = 0; i < a.length; ++i) {
		if(!a[i])continue;
		h[b[i]] = parseInt(a[i], 10);
	}
	if (h['%y'] && h['%m'] && h['%d']){
		if(h['%y']<100)h['%y'] = 2000 + h['%y'];
		return new Date(h['%y'], h['%m'] - 1, h['%d'],
			h['%H']||0, h['%M']||0, h['%s']||0);
	}
	return new Date();
};
function fmt2Digit(n){
	return n>=10 ? n : '0' + n;
}
Date.prototype.format = function(fm){
	if(!fm)return "";
	var dt=this;
	fm = fm.replace(/yyyy/ig,dt.getFullYear());
	var y = "" + dt.getFullYear();
	y = y.substring(y.length-2);
	return fm.replace(/yy/ig, y)
		.replace(/mm/g,fmt2Digit(dt.getMonth()+1))
		.replace(/dd/ig,fmt2Digit(dt.getDate()))
		.replace(/hh/ig,fmt2Digit(dt.getHours()))
		.replace(/MM/g,fmt2Digit(dt.getMinutes()))
		.replace(/ss/ig,fmt2Digit(dt.getSeconds()));
}
Date.prototype.clone = function(){
	return new Date(this.getFullYear(),this.getMonth(),this.getDate(),
		this.getHours(),this.getMinutes(),this.getSeconds());
}
Date.prototype.compare = function(dt){
	if(!dt)return 1;
	var arr = ['getFullYear', 'getMonth', 'getDate'];
	for(var i=0;i<arr.length;i++){
		if(this[arr[i]]()!=dt[arr[i]]())return 1;
	}
	return 0;
}
var DateHelper =  {
	defaultRegExp : 'yyyy-mm-dd',
	RegExpLibs : {
		'yyyy-mm-dd' : /^(\d{4})-(\d{2})-(\d{2})$/,
		'yyyy/mm/dd' : /^(\d{4})\/(\d{2})\/(\d{2})$/,
		'yyyy-mm-dd HH:MM' : /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})$/,
		'yyyy-mm-dd HH:MM:ss' : /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}:(\d{2}))$/
	},
	check : function(_sDate, _sFormat){
		_sFormat = _sFormat || this.defaultRegExp;
		var regExp = this.RegExpLibs[_sFormat];
		if(!regExp) {
			return String.format("不支持的日期格式[{0}]",_sFormat.toLowerCase());
		}
		var result = _sDate.match(regExp);
		if(!result){
			return String.format("[{0}]没有匹配日期格式[{1}]",_sDate, _sFormat.toLowerCase());
		}
		return this.checkRange(result);
	},
	checkRange:function(aDatePart){
		var year = parseInt(aDatePart[1], 10);
		var month = parseInt(aDatePart[2], 10);
		var day = parseInt(aDatePart[3], 10);

		if(aDatePart[4]) var hour = parseInt(aDatePart[4], 10);
		if(aDatePart[5]) var minute = parseInt(aDatePart[5], 10);
		if(aDatePart[6]) var second = parseInt(aDatePart[6], 10);
		
		if(month < 1 || month > 12){
			return Calendar.LANG['DD_ERROR_FORMATE3'] || "月份应该为1到12的整数";   
		}
		if (day < 1 || day > 31){
			return (Calendar.LANG["DD_ERROR_FORMATE4"] || "每个月的天数应该为1到31的整数"); 
		}
		if ((month==4 || month==6 || month==9 || month==11) && day==31){   
			return Calendar.LANG["DD_ERROR_FORMATE5"] || "该月不存在31号";   
		}   
		if (month==2){   
			var isleap=(year % 4==0 && (year % 100 !=0 || year % 400==0));   
			if (day>29){   
				return Calendar.LANG["DD_ERROR_FORMATE6"] || "2月最多有29天";   
			}   
			if ((day==29) && (!isleap)){   
				return Calendar.LANG["DD_ERROR_FORMATE7"] || "闰年2月才有29天";   
			}   
		}
		if(hour && hour<0 || hour>23){   
			return Calendar.LANG["DD_ERROR_FORMATE8"] || "小时应该是0到23的整数";   
		}   
		if(minute && minute<0 || minute>59){   
			return Calendar.LANG["DD_ERROR_FORMATE9"] || "分应该是0到59的整数";   
		}   
		if(second && second<0 || second>59){   
			return Calendar.LANG["DD_ERROR_FORMATE10"] || "秒应该是0到59的整数";  
		}
	}
};
function setFitPosition(el, point){
	var left = point[0], top = point[1];
	var right = left + 250;
	if(right >= document.body.offsetWidth){
		left = Math.max(left - 250, 0);
	}
	var bottom = top + 120;
	if(bottom >= document.body.offsetHeight){
		top = Math.max(top - 120, 0);
	}
	el.style.left = left + "px";
	el.style.top = top + "px";
}
Ext.ns('wcm.TRSCalendar');
function defTRSCalendar(){
	var m_calTemp = {
		dd : [
			'<TABLE id=cal_dp CELLSPACING=0 border=0>',
				'<TR class=DPTitle>',
					'<TD align="center"><span class=DPBtn id="py_{3}" title="{8}"><<</span>&nbsp;<span class=DPBtn id="pm_{3}" title="{5}"><</span></TD>',
					'<TD align=center colspan=4>{0}</TD>',
					'<TD align="right" colspan=2><span class=DPBtn id="nm_{3}" title="{6}">></span>&nbsp;<span class=DPBtn id="ny_{3}" title="{9}">>></span><span class=closeBtn id="cls_{3}" title="{7}">&nbsp;&nbsp;X&nbsp;</span></TD>',
				'</TR>',
				'<TR>{1}</TR>',
				'{2}',
				'{4}',
			'</TABLE>'
		].join(''),
		d3 : '<TD class=DPWeekName width="40">{0}</TD>',
		d4 : '<TR>{0}</TR>',
		d1 : '<TD class=DPCellOther>&nbsp</TD>',
		d2 : '<TD class="{1}" _date="{0}">{0}</TD>',
		d5 : '<TR><TD colspan="7" align="center" class=DPTime>{2}:&nbsp;&nbsp;<input class=DPInput id="hms_{0}" value="{1}"/></TD></TR>'
	}
	var caches = {};
	function inputBlur(event, caller){
		var inputEl = caller.input;
		if(typeof inputEl === 'string')
			inputEl = $(inputEl);
		if(!inputEl.value)return;
		var reFmt = !caller.withtime ? /^\d{4}\-\d{2}\-\d{2}$/g :
				/^\d{4}\-\d{2}\-\d{2} ([0-1]\d|2[0-3]):([0-5]\d):([0-5]\d)$/g;
	 
		//var reFmt = caller.dtReg || /^\d{4}\-\d{2}\-\d{2}$/g;
		var dft = caller.dtFmt || "yyyy-mm-dd";
		if(DateHelper.check(inputEl.value, dft)){
			alert(String.format('[{0}]日期格式不正确[{1}!]',(inputEl.getAttribute("elname")||inputEl.getAttribute("name")||inputEl.id),DateHelper.check(inputEl.value, dft)));
			inputEl.value = new Date().format(dft);
			inputEl.select();
			inputEl.focus();
			return;
		}
		//var dt = Date.parseDate(inputEl.value, caller.fmt);
		//inputEl.value = dt.format(dft);
	}
	function handlerClick(event, caller){
		event = event || window.event;
		var value = caller.getValue ? caller.getValue() : $(caller.input).value;
		caller.m_cdt = value ? Date.parseDate(value, caller.fmt) : new Date();
		caller.initDDT(0, false);
		var oStyle = $('cal_dd').style;
		setFitPosition($('cal_dd'), [Event.pointerX(event), Event.pointerY(event)]);
		oStyle.display = '';
		caller.showShield();
	}
	function TRSCalendar(fo){
		Ext.apply(this, fo);
		this.fmt = !this.withtime ? '%y-%m-%d' :'%y-%m-%d %H:%M:%s';;
		var txt = $(this.input), caller = this;
		if(txt && txt.tagName=='INPUT')txt.onblur = function(event){
			inputBlur(event, caller);
		}
		if(!$(fo.handler)) return;
		$(fo.handler).onclick = function(event){
			handlerClick(event, caller);
		}
	}
	var genId = 1;
	wcm.TRSCalendar.get = function(fo){
		var id = fo.id = 'trscal-'+genId++;
		if(!caches[id])
			caches[id] = new TRSCalendar(fo);
		return caches[id];
	}
	TRSCalendar.prototype = {
		getDDTHtml : function (dt){
			var dt = dt || new Date(), hms = dt.format('HH:MM:ss');
			if(this.dtFmt == 'yyyy-mm-dd HH:MM')
				hms = dt.format('HH:MM');
			dt.setDate(1);
			var fst = dt.getDay(), m = dt.getMonth();
			var sCurrMonth = dt.format('yyyy-mm');
			var rs1 = [], dcnt = 0, rs2 = [], rs3 = [], wt = '';
			for(var i=0;i<fst;i++){
				rs1.push(m_calTemp.d1);
			}
			while(dt.getMonth()==m){
				var date = dt.getDate();
				var className = new Date().compare(dt)==0?'DPCellToday':
					(this.m_cdt.compare(dt)==0?'DPCellCurr':'DPCell');
				rs1.push(String.format(m_calTemp.d2, date, className));
				dcnt++;
				if((dcnt+fst)%7==0){
					dcnt = fst = 0;
					rs2.push(String.format(m_calTemp.d4, rs1.join('')));
					rs1 = [];
				}
				dt.setDate(date+1);
			}
			if(dcnt!=0){
				for(var i=dcnt;i<7;i++){
					rs1.push(m_calTemp.d1);
				}
				rs2.push(String.format(m_calTemp.d4, rs1.join('')));
				rs1 = [];
			}
			if(this.withtime){
				wt = String.format(m_calTemp.d5, this.id, hms, Calendar.LANG["DD_TIME"]||"时间");
			}
			var a = Calendar._SDN || ["日", "一", "二", "三", "四", "五", "六", "日"];
			for(var i=0;i<7;i++){
				rs3.push(String.format(m_calTemp.d3, a[i]));
			}
			var html = String.format(m_calTemp.dd,
				sCurrMonth,	rs3.join(''), rs2.join(''),
				this.id, wt, Calendar.LANG["LAST_MONTH"]||"上月", Calendar.LANG["NEXT_MONTH"]||"下月", Calendar.LANG["DD_CLOSE"]||"关闭",'上年','下年');
			return html;
		},
		initDDT : function(_dx, bYear){
			if(!_dx)this.m_tdt = this.m_cdt.clone();
			var y = this.m_tdt.getFullYear();
			var m = this.m_tdt.getMonth();
			if(bYear){
				y = y + (_dx||0);
			}else{
				m = m + (_dx||0);
			}
			this.m_tdt.setDate(1);
			this.m_tdt.setMonth(m);
			this.m_tdt.setFullYear(y);
			var calDDT = $('cal_dd');
			if(!calDDT){
				calDDT = document.createElement('DIV');
				calDDT.style.position = 'absolute';
				calDDT.style.display = 'none';
				calDDT.style.zIndex = 999;
				calDDT.id = 'cal_dd';
				document.body.appendChild(calDDT);
			}
			calDDT.innerHTML = this.getDDTHtml(this.m_tdt.clone());
			this.showShield();
			var lst_s = null;
			$('cal_dp').onclick = function(event){
				var src = Event.element(event || window.event);
				var cn = src.className;
				if(src.tagName!='TD' ||
					(cn!='DPCell' && cn!='DPCellCurr'
						&& cn!='DPCellToday'))return;
				if(lst_s)lst_s.className = "DPCell";
				src.className = "DPCellSelect";
				lst_s = src;
			}
			$('cal_dp').ondblclick = function(event){
				var m_dt = caller.m_tdt || caller.m_cdt;
				var src = Event.element(event || window.event);
				var m_dt = caller.m_tdt || caller.m_cdt;
				var cn = src.className;
				if(src.tagName!='TD' || 
					(cn!='DPCell' && cn!='DPCellCurr'
						 && cn!='DPCellToday'
						 && cn!='DPCellSelect'))
					return;
				var sdate = m_dt.getFullYear() + '-' + (m_dt.getMonth()+1)
					+ '-' + src.getAttribute("_date");
				if(caller.withtime)
					sdate += ' ' + $('hms_'+caller.id).value;
				
				caller.m_cdt = Date.parseDate(sdate, caller.fmt);
				caller.render();
				$('cal_dd').style.display = 'none';
				caller.hideShield();
			}
			var caller = this;
			$('pm_' + this.id).onclick = function(){
				caller.initDDT(-1, false);
			};
			$('nm_' + this.id).onclick = function(){
				caller.initDDT(1, false);
			};
			$('py_' + this.id).onclick = function(){
				caller.initDDT(-1, true);
			};
			$('ny_' + this.id).onclick = function(){
				caller.initDDT(1, true);
			};
			$('cls_' + this.id).onclick = function(){
				$('cal_dd').style.display = 'none';
				caller.hideShield();
			};
			if(caller.withtime){
				$('hms_'+this.id).onblur = function(event){
					var reFmt = /([0-1]\d|2[0-3]):([0-5]\d):([0-5]\d)$/g;
					if(caller.dtFmt == 'yyyy-mm-dd HH:MM')
						reFmt = /([0-1]\d|2[0-3]):([0-5]\d)$/g;
					if(!reFmt.test(this.value)){
						var tipMsg = Calendar.LANG["DD_TIME_FORMATE1"] || '时间格式不符合[hh:mm:ss]!';
						if(caller.dtFmt == 'yyyy-mm-dd HH:MM') tipMsg = Calendar.LANG["DD_TIME_FORMATE"] || '时间格式不符合[hh:mm]!';
						alert(tipMsg);
						caller.m_cdt = new Date();
						if(caller.dtFmt == 'yyyy-mm-dd HH:MM') this.value = new Date().format("HH:MM");
						else this.value = new Date().format("HH:MM:ss");
						caller.render();
						this.select();
						return;
					}
				}
				$('hms_'+this.id).onkeydown = function(event){
					event = window.event || event;
					if(event.keyCode != 13) return;				
					var m_dt = caller.m_cdt;
					var date = m_dt.getDate();
					if(lst_s != null) date = lst_s.getAttribute("_date");
					var sdate = m_dt.getFullYear() + '-' + (m_dt.getMonth()+1) + '-' + date;
					sdate += ' ' + $('hms_'+caller.id).value;
					caller.m_cdt = Date.parseDate(sdate, caller.fmt);
					caller.render();
					$('cal_dd').style.display = 'none';
					caller.hideShield();
				}
			}
		},
		initShield : function(){
			if($('cal_dd_shld')) return;
			var dom = document.createElement('iframe');
			dom.src = Ext.blankUrl;
			dom.style.display = 'none';
			dom.style.position = 'absolute';
			dom.style.zIndex = 998;
			dom.style.border = '0';
			dom.id = 'cal_dd_shld';
			document.body.appendChild(dom);
		},
		showShield : function(){
			this.initShield();
			var cal = $('cal_dd');
			var oStyle = $('cal_dd_shld').style;
			oStyle.left = cal.style.left;
			oStyle.top = cal.style.top;
			oStyle.width = cal.offsetWidth;
			oStyle.height = cal.offsetHeight;
			oStyle.display = '';
		},
		hideShield : function(){
			$('cal_dd_shld').style.display = 'none';
		},
		render : function(){
			var dt = this.m_cdt, el = $(this.input);
			var rst = dt.format(this.dtFmt || "yyyy-mm-dd");
			if(this.setValue)return this.setValue(rst);
			if(el){
				el.value = rst;
				el.focus();
				el.blur();
			}
		}
	};	
}
defTRSCalendar();
/*
*  子页面中需要引入的JS文件
*	这个文件中定义了搜索组件 和tab标签组件的使用，防止需要在每一个子页面中进行配置，导致对这些组件配置项进行修改时的繁琐
*/
var SQLINDEX = {
	DOC_STAT : {
		DOC_NUM:1,
		DOC_STATUS:2
	}
}

var SEARCH_CONFIG = {
	USER_DEFAULT : {
		select_time_defaultValue:0,
		select_item:[{
				text:"发稿人",
				value:"username"
			},{
				text:"部门名称",
				value:"gname"
		}]
	},
	GROUP_DEFAULT : {
		select_time_defaultValue:0,
		select_item:[{
				text:"部门名称",
				value:"gname"
		}]
	},
	CHANNEL_DEFAULT : {
		select_time_defaultValue:0,
		select_item:[{
				text:"栏目名称",
				value:"chnlname"
		},{
				text:"站点名称",
				value:"sitename"
		}]
	},
	SITE_DEFAULT : {
		select_time_defaultValue:0,
		select_item:[{
				text:"站点名称",
				value:"sitename"
		}]
	},
	CHANNEL_HITSCOUNT : {
		select_item:[{
			text:"栏目名称",
			value:"hostName"
		},{
			text:"部门名称",
			value:"GroupName"
		},{
			text:"站点名称",
			value:"siteName"
		}]	
	},
	SITE_HITSCOUNT : {
		select_item:[{
			text:"站点名称",
			value:"SiteName"
		}]
	},
	DOCUMENT_HITSCOUNT : {
		select_item:[{
			text:"稿件名称",
			value:"DocTitle"
		},{
			text:"发稿人",
			value:"DocCrUser"
		},{
			text:"部门名称",
			value:"GroupName"
		}]
	},
	DOCUMENT_HITSCOUNT_BYUSER : {
		select_item:[{
			text:"栏目名称",
			value:"hostName"
		},{
			text:"站点名称",
			value:"siteName"
		}]	
	},
	GROUP_HITSCOUNT : {
		select_item:[{
			text:"部门名称",
			value:"GroupName"
		}]
	},
	SPECIAL_HITSCOUNT : {
		select_item:[{
			text:"专题名称",
			value:"HostName"
		},{
			text:"部门名称",
			value:"GroupName"
		}]
	}
}
var TABS_CONFIG = {
	USER_DEFAULT : {
		items:[{
			key:'table',
			url:'user_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'user_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'user_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'user_trendchart.jsp',
			desc:'走势图'
		}]
	},
	GROUP_DEFAULT : {
		items:[{
			key:'table',
			url:'group_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'group_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'group_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'group_trendchart.jsp',
			desc:'走势图'
		}]
	},
	CHANNEL_DEFAULT : {
		items:[{
			key:'table',
			url:'chnl_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'chnl_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'chnl_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'chnl_trendchart.jsp',
			desc:'走势图'
		}]
	},
	SITE_DEFAULT : {
		items:[{
			key:'table',
			url:'site_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'site_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'site_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'site_trendchart.jsp',
			desc:'走势图'
		}]
	},
	CHANNEL_HITSCOUNT : {
		items:[{
			key:'table',
			url:'channel_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'channel_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'channel_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'channel_trendchart.jsp',
			desc:'走势图'
		}]	
	},
	SITE_HITSCOUNT : {
		items:[{
			key:'table',
			url:'site_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'site_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'site_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'site_trendchart.jsp',
			desc:'走势图'
		}]	
	},
	SPECIAL_HITSCOUNT : {
		items:[{
			key:'table',
			url:'special_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'special_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'special_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'special_trendchart.jsp',
			desc:'走势图'
		}]	
	},
	GROUP_HITSCOUNT : {
		items:[{
			key:'table',
			url:'group_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'group_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'group_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'group_trendchart.jsp',
			desc:'走势图'
		}]	
	}
	
}

/*
*  将JSON格式转换成检索字符串
*/
var parseJsonToParams = function(jsonObject){
	if(jsonObject==null)
		return '';
	if(Ext.isSimpType(jsonObject))
		return jsonObject + '';
	var vReturn = [];
	for(var paramName in jsonObject){
		if(!paramName || !jsonObject[paramName])continue;
		vReturn.push(paramName+"="+encodeURIComponent(jsonObject[paramName]));
	}
	return vReturn.join('&');
}

/*
*  时间相关方法
*/
function fmt2Digit(n){
	return n>=10 ? n : '0' + n;
}
Date.prototype.format = function(fm){
	if(!fm)return "";
	var dt=this;
	fm = fm.replace(/yyyy/ig,dt.getFullYear());
	var y = "" + dt.getFullYear();
	y = y.substring(y.length-2);
	return fm.replace(/yy/ig, y)
		.replace(/mm/g,fmt2Digit(dt.getMonth()+1))
		.replace(/dd/ig,fmt2Digit(dt.getDate()))
		.replace(/hh/ig,fmt2Digit(dt.getHours()))
		.replace(/MM/g,fmt2Digit(dt.getMinutes()))
		.replace(/ss/ig,fmt2Digit(dt.getSeconds()));
}

/*
*  给返回按钮添加事件
*/
Event.observe(window,"load",function(){
	var el =$("return_btn");
	if(!el) return;
	Event.observe(el,"click",function(){
		// 只需要TimeItem 参数
		var params = {
			TimeItem: getParameter("TimeItem")
		}
		if(el.getAttribute("url")){
			var sUrl = el.getAttribute("url");
			if(sUrl.indexOf("?")>0)
				location.href=sUrl+parseJsonToParams(params);
			else
				location.href=sUrl+"?"+parseJsonToParams(params);
		}else
			history.go(-1);
	});
});

/*
*  获取某一类radio和checkbox的值
*/
function $F$F(name){
	var eles = document.getElementsByName(name);
	var rst = [], v;
	for(var i=0; i<eles.length; i++){
		if(eles[i].tagName!="INPUT")continue;
		if(eles[i].checked)
			rst.push($F(eles[i]));
	}
	return rst.join(',');
}


/*
*  获取页面中最合适的页面分页大小
*/
function getBestPageSize(){
	var bestSize = 15;
	var screenHeight = window.screen.height;
	if(screenHeight>=1000)bestSize = 20;
	else if(screenHeight>=900)bestSize = 15;
	else bestSize = 10;
	return bestSize;
}
/*
* 绩效考核系统中各子页面的搜索框定义JS
* 1.select_time 时间范围选择共有[{text:"不限",value:"0"},{text:"今日",value:"1"},{text:"本月",value:"2"},{text:"本季",value:"3"},{text:"本年",value:"4"},{text:"指定",value:"-1"}] 如果在页面中不需要今日选项，在配置对象时可以把{text:"今日",value:"1"}去掉即可,如果需要添加一些新的时间范围下拉菜单，则需要在页面的选项中定义一个对象并添加开始时间和结束时间信息，
  例如：我需要一个新的下拉菜单，统计上个星期的情况，添加如下对象
	{
		text:"上星期",
		value:"last_week",
		start_time:"2010-10-11 00:00",
		end_time:"2010-10-15 00:00"
	}
  上面这个对象中start_time和end_time都需要自己计算，如果没有start_time，则表示不限制开始时间，如果没有end_time，则表示统计到当前时间为止。select_time_defaultValue可以指定默认的时间选择项值。
*
* 2.start_time_defaultValue 和end_time_defaultValue 分别表示当需要指定时间时，输入框中的时间默认值
*
* 3.select_item 为需要检索的下拉项，同select_time，是一个对象数组，定义了text和value。其中select_item_defaultValue可以定义默认值。
*
* 4.search_cmd 和export_cmd 分别为组件的检索和导出按键命令，一般在检索命令中需要对时间格式进行校验。
*
* 5.getSearchInfo() 这是个函数返回检索所需要的信息，包括开始时间、结束时间、检索项、检索项值。可以在search_cmd中通过对象Stat.SearchBar.UI.getSearchInfo()获取检索信息。
*/
Ext.ns("Stat.SearchBar");
(function(){
	var DATE_VALUE = {
		NO_LIMIT:"0",
		TODAY:"1",
		MONTH:"2",
		SEASON:"3",
		YEAR:"4",
		SPECIFY:"-1"
	}
	/*
	*  搜索模块模板
	*/
	var HTML_TEMPLATE='<span class="select_time_desc">{3}</span><select id="select_time_" class="select_time">{0}</select><span class="time_set display-none" id="time_set_">{1}</span><select class="select_item" id="select_item_">{2}</select><input type="text" id="search_value_" name="" value=""><span class="btns"><input type="button" class="search_btn" id="search_btn_" name="" value="检索"/><input type="button" id="export_btn_" name="" class="export_btn" value="导出"/></span>';

	var HTML_TIME_SELECT = '从<input id="_search_start_time_" type="text" name="" value="" elname="开始时间"><button class="time_select" id="_start_time_select_btn_" onclick=""></button>至<input id="_search_end_time_" type="text" name="" value="" elname="结束时间"><button class="time_select" id="_end_time_select_btn_" onclick=""></button>';
	Stat.SearchBar = function(){};
	Ext.apply(Stat.SearchBar.prototype,{
		/*
		*  默认配置值
		*/
		options : {
			/*
			*  需要绘制的目标元素ID
			*/
			renderTo:"search_bar",
			/*
			*  时间选择下拉菜单值
			*/
			select_time:[{
				text:"不限",
				value:DATE_VALUE.NO_LIMIT
			},{
				text:"今日",
				value:DATE_VALUE.TODAY
			},{
				text:"本月",
				value:DATE_VALUE.MONTH
			},{
				text:"本季",
				value:DATE_VALUE.SEASON
			},{
				text:"本年",
				value:DATE_VALUE.YEAR
			},{
				text:"指定",
				value:DATE_VALUE.SPECIFY
			}],
			/*
			*  下拉时间选择默认值
			*/
			select_time_defaultValue:"",
			select_time_desc:"统计时间：",
			/*
			*  时间选择组件默认的值，如果没有，则开始时间选择今天的时间，结束时间为当前时间
			*/
			start_time_defaultValue:"",
			end_time_defaultValue:"",
			/*
			*  检索项，如发稿人、部门名称等
			*/
			select_item:[],
			/*
			*  设置检索项的默认值
			*/
			select_item_defaultValue:"",
			search_defaultValue:"",
			/*
			*  检索和导出按钮触发函数
			*/
			search_cmd:function(){
				var sUrl = Stat.SearchBar.UI .options.search_url;
				if(!sUrl)sUrl = window.location.href.match(/^[^?]*/)[0];
				var sUrlString = Stat.SearchBar.UI.getUrlString();
				if(!sUrlString){
					return;
				}
				window.location.href=sUrl+"?"+sUrlString;
			},
			ext_url_param:"",
			export_cmd:function(){},
			search_url:""
		},
		/*
		*  初始化搜索栏
		*/
		init : function(opts){
			//获取用户配置项
			Ext.apply(this.options,opts);
			Ext.apply(this.options,this.getOptFromHref());
			
			if(!$(this.options.renderTo)) return;
			//初始化时间选项
			var sSearch_HTML=String.format(HTML_TEMPLATE,this._init_select_time(),HTML_TIME_SELECT,this._init_select_item(),this.options.select_time_desc);
			//renderTo
			$(this.options.renderTo).innerHTML = sSearch_HTML;
			//初始化默认值
			this.initDefaultValue();
			//事件初始化
			this.initEvent();
		},
		/*
		* 从页面的链接中获取搜索组件的参数
		*/
		getOptFromHref : function(){
			var opt = {};
			queryJson = window.location.search.parseQuery();
			var startTime_value = null,endTime_value=null;
			if(queryJson["TimeItem"]){
				opt.select_time_defaultValue=queryJson["TimeItem"];
				if(queryJson["TimeItem"]==DATE_VALUE.SPECIFY){
					opt.start_time_defaultValue = queryJson["StartTime"] || "";
					opt.end_time_defaultValue = queryJson["EndTime"] || "";
				}
				
			}
			opt.select_item_defaultValue = queryJson["SelectItem"] || "";
			opt.search_defaultValue = queryJson["SearchValue"] || "";
			return opt;
		},
		/*
		*  初始化默认值
		*/
		initDefaultValue : function(){
			// 初始化下拉选择菜单的默认值
			if(this.options.select_time_defaultValue){
				$("select_time_").value = this.options.select_time_defaultValue;
				if($("select_time_").value == DATE_VALUE.SPECIFY)
					Element.removeClassName($("time_set_"),"display-none");
			}
			if(this.options.select_item_defaultValue){
				//如果当前检索栏中不存在该值，则默认取第一个检索项目
				for (var i = 0; i < this.options.select_item.length; i++){
					if(this.options.select_item[i].value != this.options.select_item_defaultValue)continue;
					$("select_item_").value = this.options.select_item_defaultValue;
				}
			}
			// 初始化时间选择框的默认值
			if(this.options.start_time_defaultValue)
				$("_search_start_time_").value = this.options.start_time_defaultValue;
			else
				$("_search_start_time_").value = new Date().format("yyyy-mm-dd 00:00");
			if(this.options.end_time_defaultValue)
				$("_search_end_time_").value = this.options.end_time_defaultValue;
			else
				$("_search_end_time_").value = new Date().format("yyyy-mm-dd HH:MM");
			//需要先判断该input是否是隐藏元素 先定位到输入框，再赋值可以把光标移到末尾
			var searchInput = $("search_value_");
			if(Element.getStyle(searchInput,"display")=="none")return;
			searchInput.focus();
			// 设置检索的默认值
			if(this.options.search_defaultValue){
				searchInput.value = this.options.search_defaultValue;
			}
		},
		/*
		*  事件初始化
		*/
		initEvent : function(){
			// 时间范围下拉菜单，当其选项为“指定”时，需要显示时间组件
			Event.observe($("select_time_"),"change",function(){
				if($("select_time_").value == DATE_VALUE.SPECIFY)
					Element.removeClassName($("time_set_"),"display-none");
				else{
					Element.addClassName($("time_set_"),"display-none");
				}
				
				// 如果已经显示出来了日历组件，则从"指定"修改成其它选项时需要隐藏该组件
				var cal_dd = $("cal_dd");
				if(!cal_dd) return;
				//if(cal_dd.)
				if(cal_dd.style.display=="none")return;
				cal_dd.style.display="none";
				$("cal_dd_shld").style.display="none";
			});
			// 当点击按钮时事件
			//var s_cmd = typeof(this.options.search_cmd)=="string"?e
			var caller = this;
			Event.observe($("search_btn_"),"click",this.options.search_cmd.bind(this));
			Event.observe($("export_btn_"),"click",this.options.export_cmd.bind(this));
			// 回车检索功能
			Event.observe(document,"keydown",function(ev){
				var e = ev || window.event;
				switch(e.keyCode){
					case 13:
						caller.options.search_cmd();
					break;
				}
			});
			// 时间组件点击显示日历
			if(window.wcm && wcm.TRSCalendar){
				wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
				wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
			}
		},
		/*
		*  初始化搜索时间下拉选择
		*/
		_init_select_time : function(){
			var select_time = this.options.select_time;
			var sHtml = "";
			for(var i=0;i<select_time.length;i++){
				var option = document.createElement("option");
				option.value = select_time[i].value;
				option.innerHTML = select_time[i].text;
				if(select_time[i].start_time)
					option.setAttribute("start_time",select_time[i].start_time);
				if(select_time[i].end_time)
					option.setAttribute("end_time",select_time[i].end_time);
				sHtml+=option.outerHTML;
			}
			return sHtml;
		},
		/*
		*  初始化搜索项下拉选择
		*/
		_init_select_item : function(){
			var select_item = this.options.select_item;
			var sHtml = "";
			for(var i=0;i<select_item.length;i++){
				var option = document.createElement("option");
				option.value = select_item[i].value;
				option.innerHTML = select_item[i].text;
				sHtml+=option.outerHTML;
			}
			return sHtml;
		},
		/*
		*  获取搜索信息，主要包括时间、搜索项和搜索值
		*/
		getSearchInfo : function(){
			// 1.获取时间
			var select_time = this._get_select_time();
			if(!select_time){
				return null;
			}
			return {
				start_time:select_time.start_time,
				end_time:select_time.end_time,
				select_item:$("select_item_").value,
				search_value:$("search_value_").value
			}
		},
		getUrlString : function(){
			var info=this.getSearchInfo();
			if(!info){
				return null;
			}
			var tempQueryJson = {
				StartTime:info.start_time,
				EndTime:info.end_time,
				SelectItem:info.select_item,
				SearchValue:info.search_value,
				TimeItem:$("select_time_").value
			};
			// 支持传字符串 和 json
			if(typeof(this.options.ext_url_param)=="string")
				Ext.apply(tempQueryJson,this.options.ext_url_param.parseQuery());
			else
				Ext.apply(tempQueryJson,this.options.ext_url_param);
			// 需要覆盖当前链接上的参数
			var queryJson = window.location.search.parseQuery();
			Ext.apply(queryJson,tempQueryJson);
			return parseJsonToParams(queryJson);
		},
		/*
		*  获取搜索的时间值，包括开始时间和结束时间，如果选择"不限"，则表现开始时间和结束时间都为null或者""
		*/
		_get_select_time : function(){
			var select_time_info ={};
			/* // 只有当是指定时才需要传入时间
			switch($("select_time_").value){
				case DATE_VALUE.NO_LIMIT:
					break;
				case DATE_VALUE.TODAY:
					select_time_info.start_time = new Date().format("yyyy-mm-dd 00:00");
					select_time_info.end_time = new Date().format("yyyy-mm-dd HH:MM");
					break;
				case DATE_VALUE.MONTH:
					select_time_info.start_time = new Date().format("yyyy-mm-01 00:00");
					select_time_info.end_time = new Date().format("yyyy-mm-dd HH:MM");
					break;
				case DATE_VALUE.SEASON:
					var month = new Date().getMonth()+1;
					if(month>=10)month=10;
					else if(month>=7)month=7;
					else if(month>=4)month=4
					else month=1;
					//var season_month = month-month%3;
					select_time_info.start_time = new Date().format("yyyy")+"-"+month+"-01 00:00";
					select_time_info.end_time = new Date().format("yyyy-mm-dd HH:MM");
					break;
				case DATE_VALUE.YEAR:
					select_time_info.start_time = new Date().format("yyyy-01-01 00:00");
					select_time_info.end_time = new Date().format("yyyy-mm-dd HH:MM");
					break;
				case DATE_VALUE.SPECIFY:
					select_time_info.start_time = $("_search_start_time_").value;
					select_time_info.end_time = $("_search_end_time_").value;
					break;
				default:
					var select = $("select_time_");
					var option = select.options[select.selectedIndex];
					select_time_info.start_time = option.getAttribute("start_time");
					select_time_info.end_time = option.getAttribute("end_time");
			}*/
			switch($("select_time_").value){
				case DATE_VALUE.SPECIFY:
					// 这里是针对点击回车键之后，没有进行日期格式的判断新增的，否则，会出现异常
					if(!this.checkDateTime($("_search_start_time_")) || !this.checkDateTime($("_search_end_time_"))){
						return null;
					}
					select_time_info.start_time = $("_search_start_time_").value;
					select_time_info.end_time = $("_search_end_time_").value;
					break;
			}
			
			return select_time_info;
		},
		checkDateTime : function(inputEl, sDateFormat){
			var dft = sDateFormat || "yyyy-mm-dd HH:MM";
			if(DateHelper.check(inputEl.value, dft)){
				inputEl.blur();
				return false;
			}
			return true;
		}
	});
	Stat.SearchBar.UI = new Stat.SearchBar();
})();
/*
*  tab组件，支持
*
*/
(function(){
	var options = {
		renderTo:"tab-nav",
		items:[{
			key:"ss",
			url:"http://g.cn",
			desc:"图表"
		}],
		default_item:""
	};
	var HTML_TEMPLATE =[
		'<div class="tab tab-{0}" id="{0}" url="{1}">',
			'<div class="l">',
				'<div class="r">',
					'<div class="c">',
						'<div class="desc">{2}</div>',
					'</div>',
				'</div>',
			'</div>',
		'</div>'
	].join("");
	Stat.Tab = function(){};
	Ext.apply(Stat.Tab.prototype,{
		/*
		*  页面初始化，包括配置项
		*/
		init : function(opt){
			// 配置项
			Ext.apply(options,opt);
			// 绘制HTML元素
			this.initHTML();
			// 事件初始化
			this.initEvent();
			this.initDefault();
		},
		/*
		*  输出HTML结构
		*/
		initHTML : function(){
			var aHtml = [];
			for(var i= 0;i<options.items.length;i++){
				var item = options.items[i];
				aHtml.push(String.format(HTML_TEMPLATE, item.key, item.url, item.desc));
			}
			var renderTo = $(options.renderTo);
			renderTo.innerHTML = aHtml.join("");
			Element.addClassName(Element.first(Element.first(renderTo)),"first-tab");
			Element.addClassName(Element.first(Element.last(renderTo)),"last-tab");
		},
		/*
		*  初始化默认值
		*/
		initDefault : function(){
			var def_tab_el = null;
			if(options.default_item)
				def_tab_el = $(options.default_item);
			if(!def_tab_el)
				def_tab_el = Element.first($(options.renderTo));
			if(!def_tab_el)return;
			this.toggleActive(def_tab_el, 'addClassName');
		},
		toggleActive : function(dom, sMethod){
			Element[sMethod](dom,"active");
			Element[sMethod](Element.previous(dom),"active-left");
			Element[sMethod](Element.next(dom),"active-right");
		},
		/*
		*  初始化点击事件
		*/
		initEvent : function(){
			var caller = this;
			Event.observe($(options.renderTo), "click", function(e){
				var dom = Event.element(e);
				var target = Element.find(dom, null, 'tab');
				if(!target) return;
				if(Element.hasClassName(target, "active"))return;

				var brothers = target.parentNode.childNodes;
				for(var i=0;i<brothers.length;i++){
					caller.toggleActive(brothers[i], 'removeClassName');
				}
				caller.toggleActive(target, 'addClassName');
				//页面跳转 和 执行事件
				if(target.getAttribute("cmd")){
					eval(target.getAttribute("cmd"));
				}
				if(target.getAttribute("url")){
					window.location.href = target.getAttribute("url")+"?"+caller._getUrlString();
				}
			});
		},
		_getUrlString : function(){
			var queryJson = window.location.search.parseQuery();
			if(Stat.SearchBar.UI){
				Ext.apply(queryJson,Stat.SearchBar.UI.getUrlString().parseQuery());
			}
			return parseJsonToParams(queryJson);
		},
		/*
		*  页面卸载
		*/
		destroy:function(){
		}
	});
	Stat.Tab.UI = new Stat.Tab();
})()
