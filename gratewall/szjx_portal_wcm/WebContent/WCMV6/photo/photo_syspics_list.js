Object.extend(PageContext,{	
	isLocal : false,	
	
	ObjectServiceId : 'wcm6_photo',
	ObjectMethodName : 'getSysPics',	
	AbstractParams : {		
	},
	_doBeforeRefresh : function(_params){
		Object.extend(PageContext.params,{
			//ChannelId : getParameter("channelid")||0,
			//SiteId :getParameter("siteid")||0
		});
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '幅',
	TypeName : '图片'
});
Object.extend(Grid,{
	draggable : false,
	_exec : function(_sFunctionType,_sRowId,_eRow,_eSrcElement){
		var sFunctionType = _sFunctionType.toLowerCase();
		switch(sFunctionType){
			case 'photo_select':
				//var sPhotoSrcs = _eRow.getAttribute("photo_srcs",2);
				//parent.window.SetPhotoSelected(_sRowId,sPhotoSrcs,_eSrcElement.checked);
				//_dealWithSelectedRows instead.
				break;
			case 'photo_view':				
				var imgEl = _eRow.getElementsByTagName("img")[0];
				//$openMaxWin(imgEl.src);
				var postParams ={PicName:imgEl.src,PhotoId:imgEl.AppDocId};				
				var targetUrl = "./photo_syspic_showdetail.html?" + $toQueryStr(postParams);				
				if(_IE){
					window.showModalDialog(targetUrl,"dialogWidth:330px;dialogHeight:426px;status:0;resizable:0;help:0;center:1;");
				}
				else{
					window.open(targetUrl);
				}
				break;
		}
		return false;
	},
	_dealWithSelectedRows : function(){
		var arrDocIds = document.getElementsByName("AppendixId");
		for(var i=0;i<arrDocIds.length;i++){
			var eTmp = arrDocIds[i];
			var sPhotoSrc = eTmp.getAttribute("photo_srcs",2);
			if(!parent.SelectedPhotoIds) break;
			var bIncluded = parent.SelectedPhotoIds.include(eTmp.value);
			if(eTmp.checked&&!bIncluded){
				parent.SetPhotoSelected(eTmp.value,sPhotoSrc,true);
			}
			else if(!eTmp.checked&&bIncluded){
				parent.SetPhotoSelected(eTmp.value,sPhotoSrc,false);
			}
		}
		
		PageContext.SelectedRowIds = parent.SelectedPhotoIds;
	}
});
function RefreshSelected(){
	var arrDocIds = document.getElementsByName("AppendixId");
	for(var i=0;i<arrDocIds.length;i++){
		arrDocIds[i].checked = parent.SelectedPhotoIds.include(arrDocIds[i].value);
	}
	PageContext.SelectedRowIds = parent.SelectedPhotoIds;
}


function mapFileName(_fn){
	if(_fn == null || _fn.trim().length == 0){
		return "../images/photo/pic_notfound.gif";
	}
	var r = _fn || "";
	return "/webpic/" + r.substr(0,8) + "/" + r.substr(0,10) + "/" + r;
}

function resizeIfNeed(_imgloaded){	
	if(_imgloaded){
		var height = _imgloaded.height;
		var width = _imgloaded.width;				
		if(height > 124 || width > 97){			
			if(height > width){
				_imgloaded.height = 110;				
				width = 110*width/height;
				height = 110;
			}else{
				_imgloaded.width = 97;					
				height = 97 * height/width;
				width = 97;
			}
		}
		if(width != 97){			
			_imgloaded.style.right = 20+Math.floor((97-width)/2)+"px";
		}
		if(height != 110){
			_imgloaded.style.bottom = Math.floor((110-height)/2)+15+"px";
		}
	}
}

Event.observe(window,'load',function(){		
		var nSiteId = PageContext.params.siteid || 0;
		var nChnlId = PageContext.params.channelid || 0;
		drawLiterator(nSiteId,nChnlId);
});

function drawLiterator(nSiteId,nChnlId){
		$("literator_path").params = "siteid="+nSiteId+"&channelid="+nChnlId;
		PageContext.drawLiterator('literator_path');
}

function changeQueryType(_oSelect){
	var queryValue = $F("txtQueryVal");
	if(queryValue.length == 0 || queryValue.indexOf("..请输入") == 0 ){
		var queryVal = $("txtQueryVal");		
		var desc = "..请输入" ;
		var ix = _oSelect.selectedIndex;
		if(ix > 0){
			desc += _oSelect.options[ix].innerText;
		}else{
			desc += "检索值";
		}
		queryVal.value = desc;
		queryVal.color = "gray";
	}
}

function changeQueryVal(_queryVal){
	var queryValue = _queryVal.value;
	if(queryValue.indexOf("..请输入") == 0 ){
		_queryVal.value = "";
	}
	_queryVal.select();
	_queryVal.style.color = "black";
}

function renderQuery(){	
	var searchKey = $("selQueryType").value;
	var searchValue = $F("txtQueryVal");
	if(searchValue.indexOf("..请输入") == 0 ){
		searchValue = "";
	}
	var dtStart = $F("StartTime");
	var dtEnd = $F("EndTime");
	if(dtStart.length > 0) dtStart += ":00";
	if(dtEnd.length > 0) dtEnd += ":59";
	if(!validateTimeRange(dtStart,dtEnd)) return false;
	var oPostData = {SearchKey:searchKey,SearchValue:searchValue,StartTime:dtStart,EndTime:dtEnd};
	Object.extend(oPostData,PageContext.params);
	BasicDataHelper.call("wcm6_photo","getSysPics",oPostData,true,function(_trans,_json){
		PageContext.PageLoaded(_trans,_json);			
	});

	return false;
}

function validateTimeRange(dtStart,dtEnd){	
	if(dtStart.length == 0 && dtEnd.length == 0){
		return true;
	}else if(dtEnd.length == 0){
		alert("没有指定结束时间！");
		return false;
	}else if(dtStart.length == 0){
		alert("没有指定开始时间！");
		return false;
	}	
	
	dtStart = dtStart.toDate();
	dtEnd = dtEnd.toDate();
	
	if ((dtEnd.getTime() - dtStart.getTime()) < 0){
		alert("无效的时间段：结束时间早于开始时间！");
		return false;
	}

	return true;
}

//override the trace method for the channel link path.
function trace(_id,_isSite, rightValue, channelType){
	var url = "photo_syspics_list.html?"
	if(_isSite){
		url += "siteid=" + _id;
	}else{
		url += "channelid=" + _id;
		if(channelType != undefined){
			url += "&ChannelType=" + channelType;
		}
	}
	if(rightValue != undefined){
		url += "&RightValue=" + rightValue;
	}
	window.location = url;

	// ge gfc add @ 2007-4-3 17:15 加入页面切换的过度页面
	/*
	if((top.actualTop || top).RotatingBar) {
		(top.actualTop || top).RotatingBar.start();
	}*/
}

function getAppDesc(_value,_defaultValue){
	return _value || _defaultValue || "无描述";
}