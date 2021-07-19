Object.extend(PageContext,{
	init : function(_params){
		var docId = getParameter("DocId")||getParameter("DocumentId")||0;
		var channelId = getParameter("ChannelId")||0;
		var siteId = getParameter("SiteId")||0;
		var flowDocId = getParameter("FlowDocId") || 0;		
		this.params = {ObjectId:docId,ChannelId:channelId,SiteId:siteId,FlowDocId:flowDocId};
	},
	loadProps : function(){
		BasicDataHelper.call("wcm6_document","findById",this.params,true,function(_transport,_json){
			
			var sValue = TempEvaler.evaluateTemplater('template_photoprops', _json);			
			Element.update($("photoprops"),sValue);
			$("FlowDocId").value = PageContext.params.FlowDocId;
			PageContext.loadExtendedProps();

			var calendarImg = $("img_calendar");
			Event.observe(calendarImg,"click",showCalendar);
			Event.observe(calendarImg,"mouseover",function(){
				$("img_calendar").style.background = "red"
			});
			Event.observe(calendarImg,"mouseout",function(){
				$("img_calendar").style.background = ""
			});

			

			var picUploader = new PicReuploader();			
			Event.observe($("photouploader"),'click',picUploader.upload);
		});
	},
	loadExtendedProps : function(){
		BasicDataHelper.call("wcm6_photo","getExtendedProps",this.params,true,function(_transport,_json){			
			var sValue = TempEvaler.evaluateTemplater('template_imageextprops', _json);			
			Element.update($("attr_extend"),sValue);
			ValidationHelper.initValidation();
		});
	},
	save : function(){
		BasicDataHelper.call("wcm6_document","save",'form_photoprops',true,function(_transport,_json){
			$MessageCenter.sendMessage('main', 'PageContext.RefreshList2', 'PageContext', [PageContext.params.ObjectId]);
			FloatPanel.close();	
		});
	}
});

PageContext.init();

function Ok(){
	if(ValidationHelper.doValid("form_photoprops")){
		PageContext.save();	
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

var DIALOG_REUPLOAD = "Photo_Reupload_Dialog";
PicReuploader = Class.create("PicReuploader");
PicReuploader.prototype = {
	m_eActor : null,
	initialize : function(){
		m_eActor = TRSDialogContainer.register(DIALOG_REUPLOAD, '重新上传图片', './photo_reupload.html', '450px', '220px', false,true,true);
		m_eActor.onFinished = function(){
			//refresh the imgae with same src...			
			var imgEl = $("photoitem");
			imgEl.src += "?" + Math.random();
		}
	},
	upload : function(){
		if(!LockerUtil.lock(PageContext.params.ObjectId,605)) return false;
		Object.extend(PageContext.params,{LibId:PageContext.params.SiteId});
		TRSDialogContainer.display(DIALOG_REUPLOAD, PageContext.params);
		return false;
	}
}

function editPhoto(_id){
	if(!LockerUtil.lock(_id,605)) return;
	$openMaxWin("./photo_origin_edit.html?PhotoId="+_id);
}

function refreshImg(){	
	var imgsrc = $("photoitem").src;
	if(imgsrc.indexOf("?") == -1){
		imgsrc += "?r"+Math.random();
	}else{
		imgsrc += "&r="+Math.random();
	}

	$("photoitem").src = imgsrc;
}

function showCalendar(_el){
	var elId = _el.id || "DocRelTime";
	var el = document.getElementById(elId);	
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
	_dynarch_popupCalendar.showAtElement(el,"BC"); // show the calendar

	return false;
}

function drawInputEl(_name,_value,_type,_maxlen,_minlen,_desc){
	var r = "";
	if(_type == "string"){
		r += "<textarea style='border:1px solid black' name='" + _name +"' cols='18' rows='3'";
		r += " validation=\"type:'" + _type;
		r += "',max_len:'" + _maxlen;
		//r += "',min_len:'" + _minlen;
		r += "',desc:'" + _desc;
		r += "',showid:'validatetip'\">";		
		r += _value + "</textarea>";
	}else{
		r += "<input class='input_text' type='text' name='" + _name + "'";
		r += "value='"+ _value;
		r += "' validation=\"type:'" + _type;
		r += "',max_len:'" + _maxlen;
		//r += "',min_len:'" + _minlen;
		r += "',max:'" + _maxlen;
		//r += "',min:'" + _minlen;
		r += "',desc:'" + _desc;
		if(_type == "date"){
			r += "',date_format:'yyyy-mm-dd hh:MM:ss";
		}
		r += "',showid:'validatetip'\">";
		if(_type == "date"){
			r += '<img src="img_calendar.gif"';
			r += 'id="' + _name + '" style="cursor:hand;width:20px;height:14px"';
			r += ' onmouseover="this.style.background=\'red\'" onmouseout="this.style.background=\'\'"'
			r += ' onclick="showCalendar(this)">';
		}
	}

	return r;
}

LockerUtil.register2(PageContext.params.ObjectId,605,true,"btnOnOK");

Event.observe(window,'load',function(){	
	PageContext.loadProps();
	//LockerUtil.lock(PageContext.params.ObjectId,605);
});

/*
Event.observe(window,'unload',function(){	
	LockerUtil.unlock(PageContext.params.ObjectId,605);
});*/
