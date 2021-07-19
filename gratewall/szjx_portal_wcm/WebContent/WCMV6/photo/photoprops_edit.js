Object.extend(PageContext,{
	init : function(_params){
		var docIds = getParameter("DocIds")||"0";
		var channelId = getParameter("ChannelId")||0;
		var siteId = getParameter("SiteId")||0;
		this.params = {ChannelId:channelId,SiteId:siteId};		
		this.pDocIds = docIds.split(",");		
	},
	loadProps : function(_id){
		Object.extend(this.params,{ObjectId:_id});
		BasicDataHelper.call("wcm6_document","findById",this.params,true,function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('template_photoprops', _json);			
			Element.update($("photoprops"),sValue);
			var calendarImg = $("img_calendar");
			Event.observe(calendarImg,"click",showCalendar);
			Event.observe(calendarImg,"mouseover",function(){
				$("img_calendar").style.background = "red"
			});
			Event.observe(calendarImg,"mouseout",function(){
				$("img_calendar").style.background = ""
			});
			ValidationHelper.initValidation();
		});
	},
	save : function(_finished){
		BasicDataHelper.call("wcm6_document","save",'form_photoprops',true,function(_transport,_json){
			if(_finished == true){
				$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
				FloatPanel.close();	
			}
		});
	}
});

PageContext.init();
var m_nCurrIndex = 0;
var m_nAll = PageContext.pDocIds.length;
function onPrevious(){
	if(m_nCurrIndex == 0){
		$alert("已经是第一幅图了!");
		return false;
	}
	
	if(!ValidationHelper.doValid("form_photoprops")){
		return false;
	}

	PageContext.save();	
	/*
	if(m_nCurrIndex == 1){
		FloatPanel.removeCommand("btnPrevious");
	}
	if(m_nCurrIndex == m_nAll-1){
		FloatPanel.addCommand("btnNext","下一个","onNext");
	}*/

	Element.update($("currpic"),m_nCurrIndex+"");
	m_nCurrIndex--;
	disableCommand();
	
	PageContext.loadProps(PageContext.pDocIds[m_nCurrIndex]);
	return false;
}

function disableCommand(){
	FloatPanel.disableCommand("btnPrevious",(m_nCurrIndex==0));
	FloatPanel.disableCommand("btnNext",(m_nCurrIndex==m_nAll-1));
}

function onNext(){
	if(m_nCurrIndex == m_nAll-1){
		$alert("已经是最后一幅图了!");
		return false;
	}

	if(!ValidationHelper.doValid("form_photoprops")){
		return false;
	}
	PageContext.save();	
	m_nCurrIndex++;
	disableCommand();
	Element.update($("currpic"),m_nCurrIndex+1+"");	
	/*
	if(m_nCurrIndex == 1){
		FloatPanel.addCommand("btnPrevious","上一个","onPrevious");
	}
	if(m_nCurrIndex == m_nAll-1){		
		FloatPanel.removeCommand("btnNext");
	}*/

	PageContext.loadProps(PageContext.pDocIds[m_nCurrIndex]);	
	return false;
}

function Ok(){
	if(ValidationHelper.doValid("form_photoprops")){
		PageContext.save(true);
	}
	
	return false;
}

function mapFileName(_fn,_ix,_default){
	var fn = "../images/photo/pic_notfound.gif";
	if(_fn == null || _fn.trim().length == 0){
		return fn;
	}

	var fs = _fn.split(",");
	if(fs.length == 0){
		return fn;
	}
	
	if(fs.length < _ix){
		fn = fs[0];
	}else{
		fn = fs[_ix];
	}

	if(!fn){
		fn = fs[0];
	}
	
	return "/webpic/"+fn.substr(0,8)+"/"+fn.substr(0,10)+"/"+fn;
}

function showCalendar(){
	var el = document.getElementById("DocRelTime");	
	if (window._dynarch_popupCalendar != null) {
		_dynarch_popupCalendar.hide(); // so we hide it first.
	} else {
		// first-time call, create the calendar.
		var cal = new Calendar(0, null, 
			function(_cal,_date){
				if(_cal.daySelected != true){
					return false;
				}
				el.value = _date;
				_cal.hide();
			}, 
			function(_cal){
				_cal.hide();
				_dynarch_popupCalendar=null;
			},false);

		
		cal.weekNumbers = false;				
		cal.showsOtherMonths = true;
		cal.showsTime = true;
		cal.time24 = true;
		
		_dynarch_popupCalendar = cal;// remember it in the global var
		cal.setRange(1990, 2070);
		cal.create();
	}

	_dynarch_popupCalendar.setDateFormat("%Y-%m-%d %H:%M:%S");// set the specified date format
	
	_dynarch_popupCalendar.sel = el;
	_dynarch_popupCalendar.showAtElement(el); // show the calendar

	return false;
}

Event.observe(window,'load',function(){
	PageContext.loadProps(PageContext.pDocIds[m_nCurrIndex]);
	Element.update($("picnums"),m_nAll+"");
	FloatPanel.disableCommand("btnPrevious",true);
	if(m_nAll < 2){		
		FloatPanel.disableCommand("btnNext",true);
	}
});