var m_hValidateFiles = {
	jpg:1,gif:2,bmp:3,jpeg:4,
	toStr : function(){
		var str = [];
		for(var p in this){
			if(p != "toStr"){
				str.push(p);
			}
		}
		return str.join(",");
	}
};

function myclose(){
	setTimeout(function(){
		FloatPanel.close();
	},100);
	return false;
}

var PageContext = {};
Object.extend(PageContext,{
	mainKindId : 0,
	init : function(){
		this.mainKindId = getParameter("ChannelId")||0;
		this.LibId = getParameter("SiteId") || 0;
		BasicDataHelper.call("wcm6_photo","getSupportedFormat",null,false,function(_transport,_json){			
			var sValue = _transport.responseText;
			if(sValue.trim().length > 0){
				eval("var j = "+sValue);
				Object.extend(m_hValidateFiles,j);						
			}
		});		
	},
	loadDefaultBmpConverType : function(){
		BasicDataHelper.call("wcm6_photo","getDefaultBmpConverType",null,false,function(_transport,_json){			
			$("BmpConverTypeSelect").value = _transport.responseText;
			$("BmpConverType").value =  _transport.responseText;
		});
	},
	loadMainKind : function(_id){
		var id = _id || this.mainKindId;
		BasicDataHelper.call("wcm6_channel","findById",{ObjectId:id},false,function(_transport,_json){		
			$("mainkind").innerHTML = _json.CHANNEL.CHNLDESC.NODEVALUE;
			$("mainkind").className = "mainkindfound";
		});
		$("MainKindId").value = id;
		PageContext.mainKindId = id;
	}
});


function getPageSize(){
	var de = document.documentElement;
	var w = window.innerWidth || self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
	var h = window.innerHeight || self.innerHeight || (de&&de.clientHeight) || document.body.clientHeight;
	arrayPageSize = [w,h];
	return arrayPageSize;
}

var CurrType = 1;
var UploadFileName = null;
var sDefaultNoneImg = '';
var isSSL = location.href.indexOf("https://")!=-1;

var m_aUploadedFiles = [];
var m_aSrcFiles = [];
var m_nUploaded = 0;

function addWaterMark(_select){
	var el = $("selwatermark");
	var op = el.options[el.selectedIndex];
	if(op.value == -1){
		Element.hide($("watermarkpic"));
		Element.hide($("div_watermarkpos"));
		$("WatermarkFile").value = "";
	}else{
		var imgLoaded = new Image();
		imgLoaded.onload = function(){
			resizeIfNeed(imgLoaded.height,imgLoaded.width);
			imgLoaded.onload = null;
			$("watermarkpic").src = op.getAttribute("_picsrc");
			Element.show($("watermarkpic"));
		}
		imgLoaded.src = op.getAttribute("_picsrc") + "?r="+Math.random();
							
		Element.show($("div_watermarkpos"));					
		$("WatermarkFile").value = op.getAttribute("_picfile");
	}
}

function convertBmp(_select){
	$("BmpConverType").value = _select.value;	
}

//全选或反全选水印位置
function selectAllPos(){
	var poses = $("LT","CM","RB");
	var unchecked = false;
	for(var i=0;i<poses.length;i++){
		if(!poses[i].checked){
			unchecked = true;
			poses[i].checked = true;
		}
	}

	if(!unchecked){
		for(var i=0;i<poses.length;i++){				
			poses[i].checked = false;				
		}
	}
	
	poses = null;
}

function resizeIfNeed(height,width){
		var h = height,w = width;
		if(height > 50 || width > 50){	
			if(height > width){				
				h = 50;	
				w = 50 * width/height;
			}else if(height==width){				
				w = 50;
				h = 50;
			}else{				
				w = 50;
				h = 50 * height/width;
			}			
		}

		$("watermarkpic").width = w;
		$("watermarkpic").height = h;			
}
function truncateIfNeed(_srcstr){
	if(_srcstr.length > 12){
		_srcstr = _srcstr.substr(0,12) + "...";
	}

	return _srcstr;
}


