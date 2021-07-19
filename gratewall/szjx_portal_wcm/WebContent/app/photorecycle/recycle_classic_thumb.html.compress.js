Ext.ns("wcm.ListQuery", "wcm.ListQuery.Checker", "wcm.ListOrder");

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
				sOptHTML += "<option value='" + items[i].name + "' title='" + items[i].desc + "'>" + items[i].desc + "</option>";
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
			if(event.keyCode == 13) {
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
				return '<span style="width:180px;overflow-y:auto;">'+String.format("当前检索字段限制为[<b>{0}</b>]个字符长度，当前为[<b>{1}</b>]！\<br><br><b>提示：</b>每个汉字长度为2。", nMaxLen, sValue.length)+'</span>';
			}
			return false;
		}
	});
})();


(function(){
	var headerTemplate = [
		'<div class="orderbox-header">',
			'<div class="left" id="{0}-header">',
				'<div class="right">',
					'<div class="center">',
						'<div class="text" id="{0}-text">' + (WCMLANG['LIST_QUERY_DEFAULTORDER'] || '默认排序') + '</div>',
					'</div>',
				'</div>',
			'</div>',
		'</div>'
	].join("");
	var bodyTemplate = '<div class="orderbox-body" id="{0}-body" style="display:none;"></div>';
	var itemTemplate = '<div class="item"><a href="#" id="{0}-item" field="{0}">{1}</a></div>';
	var tipTemplate = '<div class="tip">{0}</div>';

	Ext.apply(wcm.ListOrder, {
		activeKey : null,
		config : null,
		register : function(_config){
			var config = {appendTip : true, autoLoad : true, id : 'list-order-' + (new Date().getTime())};
			Ext.apply(config, _config);
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
			var activeKey = null;
			var config = this.config;
			Element.update(config.container, String.format(headerTemplate, config.id));
			new Insertion.Bottom(document.body, String.format(bodyTemplate, config.id));
			var aHtml = [];
			var items = config.items;
			for (var i = 0, length = items.length; i < length; i++){
				var item = items[i];
				items[item['name']] = item;
				if(item["isActive"]){
					activeKey = item["name"];
				}
				aHtml.push(String.format(itemTemplate, item['name'], item['desc']));
			}
			if(config.appendTip){
				var appendTip = Ext.isBoolean(config.appendTip) ? (WCMLANG['LIST_QUERY_NOTDEFAULT'] || '非默认排序时不保存拖动排序结果') : config.appendTip;
				aHtml.push(String.format(tipTemplate, appendTip));
			}
			Element.update(config.id + "-body", aHtml.join(""));
			if(!activeKey){
				activeKey = items[0]["name"];
			}
			this.setActive(activeKey);
			this.bindEvents();
		},
		bindEvents : function(){
			var id = this.config.id;
			Event.observe(id + "-header", 'click', this.clickHeader.bind(this));
			Event.observe(id + "-body", 'click', this.clickBody.bind(this));
		},
		clickHeader : function(event){
			event = window.event || event;
			Event.stop(event);
			var srcElement = Event.element(event);
			if(Element.hasClassName(srcElement, 'text')){
				this.setActive(this.activeKey, true);
				return;
			}
			var id = this.config.id;
			var header = $(id + "-header");
			var body = $(id + "-body");
			if(!this.bubblePanel){
				this.bubblePanel = new wcm.BubblePanel(body);
			}
			Position.clone(header, body, {setWidth:false, setHeight:false, offsetTop:header.offsetHeight});
			this.bubblePanel.bubble();
		},
		clickBody : function(event){
			event = window.event || event;
			Event.stop(event);
			var srcElement = Event.element(event);
			var fieldName = srcElement.getAttribute("field");
			if(!fieldName) return;
			this.setActive(fieldName, true);
		},
		setActive : function(sActiveKey, bExeCallBack){
			if(!sActiveKey) return;
			var activeItem = this.config.items[sActiveKey];
			if(!activeItem) return;
			if(!activeItem["order"]){
				activeItem["order"] = 'desc';
			}else if(!activeItem.isDefault){
				activeItem["order"] = activeItem["order"] == 'desc' ? 'asc' : "desc";
			}
			//handle the header style
			var dom = $(this.config.id + "-text");
			dom.className = 'text ' + (activeItem.isDefault ? "" : activeItem["order"]);
			if(this.activeKey != sActiveKey){
				Element.update(dom, activeItem["desc"]);

				//handle the body style
				if(this.activeKey){
					dom = $(this.activeKey + "-item");
					Element.removeClassName(dom.parentNode, 'active');
				}
				this.activeKey = sActiveKey;
				dom = $(this.activeKey + "-item");
				Element.addClassName(dom.parentNode, 'active');
			}
			if(bExeCallBack) this.config['callback'].call(activeItem, sActiveKey + " " + activeItem['order']);
		}
	});
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
	if (h['%y'] && h['%m'] && h['%d'])
		return new Date(h['%y'], h['%m'] - 1, h['%d'],
			h['%H']||0, h['%M']||0, h['%s']||0);
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
	function defaultNow(pointor,dft){
		pointor.value = new Date().format(dft);
		pointor.select();
		pointor.focus();
	}
	function defautltTime(pointor,caller){
		caller.m_cdt = new Date();
		pointor.value = new Date().format("HH:MM:ss");
		caller.render();
		pointor.select();
	}
	function isLeapYear(year){
		if((year%4==0 && year%100!=0)||(year%400==0)){
			return true;
		}
		return false;
	}
	function TRSCalendar(fo){
		this.input = fo.input;
		this.id = fo.id;
		this.wot = !fo.withtime;
		this.top = fo.top;
		this.left = fo.left;
		var blurClose = fo.bBlurClose || false;
		var txt = $(this.input);
		var fmt = this.wot ? '%y-%m-%d' :'%y-%m-%d %H:%M:%s';
		this.fmt = fmt = fo.format || fmt;
		this.m_cdt = txt.value ? Date.parseDate(txt.value, fmt) : new Date();
		var caller = this;
		txt.onblur = function(event){
			if(!this.value)return;
			var reFmt = caller.wot ? /^\d{4}\-\d{2}\-\d{2}$/g :
				/^\d{4}\-\d{2}\-\d{2} ([0-1]\d|2[0-3]):([0-5]\d):([0-5]\d)$/g;
			var dft = caller.wot ? "yyyy-mm-dd" : "yyyy-mm-dd HH:MM:ss";
			if(!this.value.match(reFmt)){
				alert(String.format("[{0}]日期格式不符合[{1}]!",this.getAttribute("elname")||this.getAttribute("title")||this.value||this.id,dft.toLowerCase()));
				defaultNow(this,dft);
				return;
			}
			var dataArray = this.value.split(" ");
			var nYear = dataArray[0].split("-")[0];
			var nMonth = dataArray[0].split("-")[1];
			var nDay = dataArray[0].split("-")[2];
			
			if(nMonth < 1 || nMonth > 12){
				alert(Calendar.LANG["DD_ERROR_FORMATE3"] || '月份应该为1到12的整数');
				defaultNow(this,dft);
				return;
			}
			if(nDay < 1 || nDay > 31){
				alert(Calendar.LANG["DD_ERROR_FORMATE4"] || '每个月的天数应该为1到31的整数');
				defaultNow(this,dft);
				return;
			}
			if(nMonth==2){ 
				if(isLeapYear(nYear)&&nDay>29){
					alert(Calendar.LANG["DD_ERROR_FORMATE6"] || '2月最多有29天');
					defaultNow(this,dft);
					return;
				}
				if(!isLeapYear(nYear)&&nDay>28){
					alert(Calendar.LANG["DD_ERROR_FORMATE7"] ||'闰年2月才有29天');
					defaultNow(this,dft);
					return;
				}	
			}
			if(!caller.wot){
				var nHour = dataArray[1].split(":")[0];
				var nMinute = dataArray[1].split(":")[1];
				var nSecond = dataArray[1].split(":")[2];
				if(nHour < 0 || nHour > 23){
					alert(Calendar.LANG["DD_ERROR_FORMATE8"] || '小时应该是0到23的整数');
					defaultNow(this,dft);
					return;
				}
				if(nMinute < 0 || nMinute > 59){
					alert(Calendar.LANG["DD_ERROR_FORMATE9"] || '分应该是0到59的整数');
					defaultNow(this,dft);
					return;
				}
				if(nSecond < 0 || nSecond > 59){
					alert(Calendar.LANG["DD_ERROR_FORMATE10"] || '秒应该是0到59的整数');
					defaultNow(this,dft);
					return;
				}
			}
			var dt = Date.parseDate(this.value, caller.fmt);
			this.value = dt.format(dft);
		}
		if(!$(fo.handler)) return;
		$(fo.handler).onclick = function(event){
			event = event || window.event;
			var txt = $(caller.input);
			caller.m_cdt = txt.value ? Date.parseDate(txt.value, caller.fmt) : new Date();
			caller.initDDT(0, false,blurClose);
			var oStyle = $('cal_dd').style;
			oStyle.display = '';
			oStyle.left = caller.left || (Event.pointerX(event) - 130 + 'px');
			oStyle.top = caller.top || (Event.pointerY(event) + 15 + 'px');
			caller.showShield();
		};
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
			var a = Calendar._SDN || ["日", "一", "二", "三", "四", "五", "六", "日"];
			for(var i=0;i<7;i++){
				rs3.push(String.format(m_calTemp.d3, a[i]));
			}
			if(!this.wot){
				wt = String.format(m_calTemp.d5, this.id, hms, Calendar.LANG["DD_TIME"]||"时间");
			}
			var html = String.format(m_calTemp.dd,
				sCurrMonth,	rs3.join(''), rs2.join(''),
				this.id, wt, Calendar.LANG["LAST_MONTH"]||"上月", Calendar.LANG["NEXT_MONTH"]||"下月", Calendar.LANG["DD_CLOSE"]||"关闭", "上年", "下年");
			return html;
		},
		initDDT : function(_dx, bYear,blurClose){
			var bBlurClose = blurClose;
			if(!_dx)this.m_tdt = this.m_cdt.clone();
			var y = this.m_tdt.getFullYear();
			var m = this.m_tdt.getMonth();
			var d = this.m_tdt.getDate();
			if(bYear){
				y = y + (_dx||0);
			}else{
				m = m + (_dx||0);
			}
			this.m_tdt.setDate(d);
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
			var isInContainer = false;
			setTimeout(function(){
				var calEl = $('cal_dp');
				calEl.tabIndex =100;
				calEl.focus();
				calEl.style.border = "1px dotted gray";
				calEl.onmouseover = function(){
					isInContainer =true;
					calEl.blur()
				}
				calEl.onmouseout = function(){
					try{
						calEl.focus();
					}catch(e){}
					isInContainer =false;
				}
			},0);
			$('cal_dp').onblur = function(){
				if(blurClose &&!isInContainer){
					$('cal_dd').style.display = 'none';
					caller.hideShield();
				}
			}
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
				if(!caller.wot)
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
			if(!caller.wot){
				$('hms_'+this.id).onblur = function(event){
					var reFmt = /([0-1]\d|2[0-3]):([0-5]\d):([0-5]\d)$/g;
					if(!reFmt.test(this.value)){
						alert(Calendar.LANG["DD_TIME_FORMATE1"] || '时间格式不符合[hh:mm:ss]!');
						defautltTime(this,caller);
						return;
					}
					var nHour = this.value.split(":")[0];
					var nMinute = this.value.split(":")[1];
					var nSecond = this.value.split(":")[2];
					if(nHour < 0 || nHour > 23){
						alert(Calendar.LANG["DD_ERROR_FORMATE8"] || '小时应该是0到23的整数');
						defautltTime(this,caller);
						return;
					}
					if(nMinute < 0 || nMinute > 59){
						alert(Calendar.LANG["DD_ERROR_FORMATE9"] || '分应该是0到59的整数');
						defautltTime(this,caller);
						return;
					}
					if(nSecond < 0 || nSecond > 59){
						alert(Calendar.LANG["DD_ERROR_FORMATE10"] || '秒应该是0到59的整数');
						defaultNow(this,dft);
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
			if(!Ext.isIE6) return;
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
			if(!Ext.isIE6) return;
			$('cal_dd_shld').style.display = 'none';
		},
		render : function(){
			var dt = this.m_cdt;
			var dft = this.wot ? "yyyy-mm-dd" : "yyyy-mm-dd HH:MM:ss";
			var el = $(this.input);
			el.value = dt.format(dft);
			el.focus();
			el.blur();
		}
	};	
}
defTRSCalendar();
//多语种
Ext.apply(wcm.LANG, {
	PHOTO_CONFIRM_1 : '确定',
	PHOTO_CONFIRM_2 : '取消',
	PHOTO_CONFIRM_3 : "不符合规则！",
	PHOTO_CONFIRM_4 : "Id[{0}]规则不一致",
	PHOTO_CONFIRM_5 : "指定的对象[{0}.{1}]可能已经被删除!栏目树的同步刷新操作不能进行!",
	PHOTO_CONFIRM_6 : "Root节点元素[Id=r_{0}]竟然都不存在？？？\n(reloadChildrenAndFocus)相关信息：NodeType:{1}, _nFocusNodeId:{2}\n相关HTML代码：",
	PHOTO_CONFIRM_7 : '个',
	PHOTO_CONFIRM_8 : '图片',
	PHOTO_CONFIRM_9 : '您正准备删除如下图片：',
	PHOTO_CONFIRM_10 : '您正准备还原如下图片：',
	PHOTO_CONFIRM_11 : '您正准备将如下图片放入废稿箱：',
	PHOTO_CONFIRM_12 : '由于以下原因，您无法进行图片还原操作！',
	PHOTO_CONFIRM_13 : '图片所属分类尚在分类回收站内',
	PHOTO_CONFIRM_14 : '引用图片的实体尚在废稿箱内',
	PHOTO_CONFIRM_15 : '未知操作',
	PHOTO_CONFIRM_16 : '全部图片',
	PHOTO_CONFIRM_17 : '新稿', 
	PHOTO_CONFIRM_18 : '可发',
	PHOTO_CONFIRM_19 : '已发', 
	PHOTO_CONFIRM_20 : '已否',
	PHOTO_CONFIRM_21 : '我创建的',
	PHOTO_CONFIRM_22 : '最近三天', 
	PHOTO_CONFIRM_23 : '最近一周', 
	PHOTO_CONFIRM_24 : '最近一月',
	PHOTO_CONFIRM_25 : '宽度缩放比例',
	PHOTO_CONFIRM_26 : '高度缩放比例',
	PHOTO_CONFIRM_27 : '正在处理，请稍候...',
	PHOTO_CONFIRM_28 : "仅支持[{0}]类型的图片!",
	PHOTO_CONFIRM_29 : '执行进度，请稍候...',
	PHOTO_CONFIRM_30 : '上传图片',
	PHOTO_CONFIRM_31 : '成功上传文件.',
	PHOTO_CONFIRM_32 : '保存图片',
	PHOTO_CONFIRM_33 : '保存图片成功.',
	PHOTO_CONFIRM_34 : '上传文件失败,与服务器交互时出错啦！',
	PHOTO_CONFIRM_35 : "没有指定结束时间！",
	PHOTO_CONFIRM_36 : "没有指定开始时间！",
	PHOTO_CONFIRM_37 : "无效的时间段：结束时间早于开始时间！",
	PHOTO_CONFIRM_38 : "后面已经没有图片了！",
	PHOTO_CONFIRM_39 : "前面已经没有图片了！",
	PHOTO_CONFIRM_40 : '图片快速检索',
	PHOTO_CONFIRM_41 : '创建时间',
	PHOTO_CONFIRM_42 : '图片标题',
	PHOTO_CONFIRM_43 : '创建者',
	PHOTO_CONFIRM_44 : "请输入[1-9]的数字！",
	PHOTO_CONFIRM_45 : "标注图片属性",
	PHOTO_CONFIRM_46 : "您所选的不是一个zip格式的文件!",
	PHOTO_CONFIRM_47 : "只支持[{0}]格式的图片！",
	PHOTO_CONFIRM_48 : "请选择一个主分类！",
	PHOTO_CONFIRM_49 : "[{0}]不是支持的图片格式!\n仅支持[{1}]类型",
	PHOTO_CONFIRM_50 : "没有选择图片!",
	PHOTO_CONFIRM_51 : "请选择一个有效的zip文件!",
	PHOTO_CONFIRM_52 : "选择主分类",
	PHOTO_CONFIRM_53 : "选择其它分类",
	PHOTO_CONFIRM_54 : "请选择目标分类",
	PHOTO_CONFIRM_55 : '重新上传图片',
	PHOTO_CONFIRM_56 : "设置成功!",
	PHOTO_CONFIRM_57 : "已经是第一幅图了!",
	PHOTO_CONFIRM_58 : "已经是最后一幅图了!",
	PHOTO_CONFIRM_59 : '下幅',
	PHOTO_CONFIRM_60 : '上幅',
	PHOTO_CONFIRM_61 : '上传图片',
	PHOTO_CONFIRM_62 : '选择对象',
	PHOTO_CONFIRM_63 : "系统提示信息",
	PHOTO_CONFIRM_64 : "重新分类图片[",
	PHOTO_CONFIRM_65 : "]到分类[",
	PHOTO_CONFIRM_66 : "]失败！",
	PHOTO_CONFIRM_67 : "编辑图片信息",
	PHOTO_CONFIRM_68 : '文档',
	PHOTO_CONFIRM_69 : '-调整顺序',
	PHOTO_CONFIRM_70 : '图片-改变状态',
	PHOTO_CONFIRM_71 : '编辑这幅图片',
	PHOTO_CONFIRM_72 : '删除这幅图片',
	PHOTO_CONFIRM_73 : '预览这幅图片',
	PHOTO_CONFIRM_74 : '发布这幅图片',
	PHOTO_CONFIRM_75 : '重新分类',
	PHOTO_CONFIRM_76 : '增加分类',
	PHOTO_CONFIRM_77 : '改变这幅图片的状态',
	PHOTO_CONFIRM_78 : '上传新图片',
	PHOTO_CONFIRM_79 : '导入系统图片',
	PHOTO_CONFIRM_80 : '删除这些图片',
	PHOTO_CONFIRM_81 : '预览这些图片',
	PHOTO_CONFIRM_82 : '发布这些图片',
	PHOTO_CONFIRM_83 : '改变这些图片的状态',
	PHOTO_CONFIRM_84 : '撤销发布这幅图片',
	PHOTO_CONFIRM_85 : '撤销发布这幅图片,撤回已发布目录或页面',
	PHOTO_CONFIRM_86 : '撤销发布这些图片',
	PHOTO_CONFIRM_87 : '撤销发布这些图片,撤回已发布目录或页面',
	PHOTO_CONFIRM_88 : '设置这幅图片的权限',
	PHOTO_CONFIRM_89 : '设置这些图片的权限',
	PHOTO_CONFIRM_90 : '调整顺序',
	PHOTO_CONFIRM_91 : '待导入',
	PHOTO_CONFIRM_92 : '选择',
	PHOTO_CONFIRM_93 : '非法输入',
	PHOTO_CONFIRM_94 : '上传',
	PHOTO_CONFIRM_95 : '请选择要删除的图片',
	PHOTO_CONFIRM_96 : '删除',
	PHOTO_CONFIRM_97 : '删除这幅/些图片',
	PHOTO_CONFIRM_98 : '刷新',
	PHOTO_CONFIRM_99 : '刷新列表',
	PHOTO_CONFIRM_100 : '图片列表管理',
	PHOTO_CONFIRM_101 : '显示名称',
	PHOTO_CONFIRM_102 : '预览',
	PHOTO_CONFIRM_103 : '预览这个/些图片',
	PHOTO_CONFIRM_104 : '请选择要预览的图片',
	PHOTO_CONFIRM_105 : '发布',
	PHOTO_CONFIRM_106 : '发布这个/些图片',
	PHOTO_CONFIRM_107 : '请选择要发布的图片',
	PHOTO_CONFIRM_108 : '标题',
	PHOTO_CONFIRM_109 : '关键词',
	PHOTO_CONFIRM_110 : '正文',
	PHOTO_CONFIRM_111 : '批量上传图片',
	PHOTO_CONFIRM_112 : '选择要批量上传图片的栏目',
	PHOTO_CONFIRM_113 : '批量上传图片',
	PHOTO_CONFIRM_114 : '批量上传图片到栏目[',
	PHOTO_CONFIRM_115 : '关闭',
	PHOTO_CONFIRM_116 : '结束',
	PHOTO_CONFIRM_117 : '拍摄时间',
	PHOTO_CONFIRM_118 : '大标题',
	PHOTO_CONFIRM_119 : '标题',
	PHOTO_CONFIRM_120 : '作者',
	PHOTO_CONFIRM_121 : '说明内容',
	PHOTO_CONFIRM_122 : '地点',
	PHOTO_CONFIRM_123 : '人物',
	PHOTO_CONFIRM_124 : '保存图片相关信息完毕！',
	PHOTO_CONFIRM_125 : '与服务器交互时出现错误',
	PHOTO_CONFIRM_126 : "请输入[1-9]个分类!",
	PHOTO_CONFIRM_127 : "确实要改变图片排列顺序吗？",
	PHOTO_CONFIRM_128 : "当前图片列表不支持拖动排序",
	PHOTO_CONFIRM_129 : "移动图片",
	PHOTO_CONFIRM_130 : "引用图片",
	PHOTO_CONFIRM_131 : "图片移动结果",
	PHOTO_CONFIRM_132 : "图片引用结果",
	PHOTO_CONFIRM_133 : "管理评论",
	PHOTO_CONFIRM_134 : "管理图片的评论",
	PHOTO_CONFIRM_135 : "仅发布这幅图片细览",
	PHOTO_CONFIRM_136 : "仅发布这些图片细览",
	PHOTO_CONFIRM_137 : '还原这幅图片',
	PHOTO_CONFIRM_138 : '还原这些图片',
	PHOTO_CONFIRM_139 : '还原图片...',
	PHOTO_CONFIRM_140 : '清空废稿箱',
	PHOTO_CONFIRM_141 : '还原所有图片',
	PHOTO_CONFIRM_142 : '您确定要还原所有图片',
	PHOTO_CONFIRM_143 : '您当前所执行的操作将彻底删除废稿箱中所有图片,您确定仍要继续清空当前废稿箱?',
	PHOTO_CONFIRM_144 : '删除图片...',
	PHOTO_CONFIRM_145 : '您正准备强制删除如下图片：',
	PHOTO_CONFIRM_146 : '确实要删除这些图片吗',
	PHOTO_CONFIRM_147 : '您正准备还原如下图片（含有不能还原的图片）：',
	PHOTO_CONFIRM_148 : '无法还原：图片所属分类尚在分类回收站内',
	PHOTO_CONFIRM_149 : '还原',
	PHOTO_CONFIRM_150 : '还原这幅/些图片',
	PHOTO_CONFIRM_151 : "确定要<font color='red' style='font-size:14px;'>撤销发布</font>所选图片么？将<font color='red' style='font-size:14px;'>不可逆转</font>！",
	PHOTO_CONFIRM_152 :	"改变状态",
	PHOTO_CONFIRM_153 :'改变这幅/些图片的状态',
	PHOTO_CONFIRM_154 :'仅发布细览',
	PHOTO_CONFIRM_155 :'仅发布这幅/些图片的细览',
	PHOTO_CONFIRM_156 :'撤销发布',
	PHOTO_CONFIRM_157 :'撤销发布这幅/些图片,撤回已发布目录或页面',
	PHOTO_CONFIRM_158 :'设置权限',
	PHOTO_CONFIRM_159 :'设置这幅/些图片的权限',
	PHOTO_CONFIRM_160 :'直接发布这幅图片',
	PHOTO_CONFIRM_161 :'发布这幅图片,同时发布此图片的所有引用图片',
	PHOTO_CONFIRM_162 :'直接发布这些图片',
	PHOTO_CONFIRM_163 :'发布这些图片,同时发布这些图片的所有引用图片',
	PHOTO_CONFIRM_164 :'直接撤销发布这幅图片',
	PHOTO_CONFIRM_165 :'撤回当前图片的发布页面，并同步撤销图片的所有引用以及原图片发布页面',
	PHOTO_CONFIRM_166 :'直接撤销发布这些图片',
	PHOTO_CONFIRM_167 :'撤回这些图片的发布页面，并同步撤销图片的所有引用以及原图片发布页面',
	PHOTO_CONFIRM_168 :"确定要<font color='red' style='font-size:14px;'>撤销发布</font>所选图片及其所有的引用图片么？将<font color='red' style='font-size:14px;'>不可逆转</font>！",
	PHOTO_CONFIRM_169 :"下载原图"
});
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
