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
		pointor.value = new Date().format("HH:MM");
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
		var txt = $(this.input);
		this.fmt = '%y-%m-%d %H:%M';
		this.m_cdt = txt.value ? Date.parseDate(txt.value, this.fmt) : new Date();
		var caller = this;
		txt.onblur = function(event){
			if(!this.value)return;
			var reFmt = /^\d{4}\-\d{2}\-\d{2} (\d{2}):(\d{2})$/g;
			var dft = "yyyy-mm-dd HH:MM";
			if(!this.value.match(reFmt)){
				alert(Calendar.LANG["DD_FORMATE"] || '日期格式不符合[yyyy-mm-dd hh:mm]!');
				defaultNow(this,dft);
				return;
			}
			var dataArray = this.value.split(" ");
			var nYear = dataArray[0].split("-")[0];
			var nMonth = dataArray[0].split("-")[1];
			var nDay = dataArray[0].split("-")[2];
			var nHour = dataArray[1].split(":")[0];
			var nMinute = dataArray[1].split(":")[1];
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
			var dt = Date.parseDate(this.value, caller.fmt);
			this.value = dt.format(dft);
		}
		if(!$(fo.handler)) return;
		$(fo.handler).onclick = function(event){
			event = event || window.event;
			var txt = $(caller.input);
			caller.m_cdt = txt.value ? Date.parseDate(txt.value, caller.fmt) : new Date();
			caller.initDDT(0, false);
			var oStyle = $('cal_dd').style;
			oStyle.display = '';
			oStyle.right = '20px';
			oStyle.top = fo.top ? Event.pointerY(event) + fo.top + 'px': Event.pointerY(event) + 10 + 'px';
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
			var dt = dt || new Date(), hms = dt.format('HH:MM');
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
			wt = String.format(m_calTemp.d5, this.id, hms, Calendar.LANG["DD_TIME"]||"时间");
			var html = String.format(m_calTemp.dd,
				sCurrMonth,	rs3.join(''), rs2.join(''),
				this.id, wt, Calendar.LANG["LAST_MONTH"]||"上月", Calendar.LANG["NEXT_MONTH"]||"下月", Calendar.LANG["DD_CLOSE"]||"关闭", '上年', '下年');
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
			$('hms_'+this.id).onblur = function(event){
				var reFmt = /\d{2}:\d{2}$/g;
				if(!reFmt.test(this.value)){
					alert(Calendar.LANG["DD_TIME_FORMATE"] || '时间格式不符合[hh:mm]!');
					defautltTime(this,caller);
					return;
				}
				var nHour = this.value.split(":")[0];
				var nMinute = this.value.split(":")[1];
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
			oStyle.right = cal.style.right;
			oStyle.top = cal.style.top;
			oStyle.width = cal.offsetWidth;
			oStyle.height = cal.offsetHeight;
			oStyle.display = '';
		},
		hideShield : function(){
			$('cal_dd_shld').style.display = 'none';
		},
		render : function(){
			var dt = this.m_cdt;
			var el = $(this.input);
			el.value = dt.format("yyyy-mm-dd HH:MM");
			el.focus();
			el.blur();
		}
	};	
}
defTRSCalendar();