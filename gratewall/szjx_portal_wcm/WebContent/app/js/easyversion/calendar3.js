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