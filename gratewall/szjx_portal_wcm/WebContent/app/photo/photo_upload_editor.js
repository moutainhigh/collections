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
var CurrChannelId = getParameter("ChannelId")||0;
var PageContext = {};
Object.extend(PageContext,{
	init : function(){
		this.mainKindId = getParameter("ChannelId")||0;
		this.LibId = getParameter("SiteId") || 0;
		$("MainKindId").value = this.mainKindId;
		PageContext.mainKindId = this.mainKindId;
		BasicDataHelper.call("wcm6_photo","getSupportedFormat",null,false,function(_transport,_json){			
			var sValue = _transport.responseText;
			if(sValue.trim().length > 0){
				eval("var j = "+sValue);
				Object.extend(m_hValidateFiles,j);						
			}
		});
		BasicDataHelper.call("wcm6_photo","getDefaultBmpConverType",null,false,function(_transport,_json){			
			$("BmpConverTypeSelect").value = _transport.responseText;
			$("BmpConverType").value =  _transport.responseText;
		});
	},
	loadOtherKinds : function(_ids){
		var ids = _ids || "";	
		if(ids == "") return ;
		BasicDataHelper.call("wcm6_channel","findByIds",{ObjectIds:ids},true,function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('template_otherkinds', _json);			
			Element.update($("otherkinds_holder"),sValue);
			$("OtherKindIds").value = ids;
		});
	},
	removeOtherKind : function(_evt){
		var evt = (_evt) ? _evt : ((window.event) ? window.event : "")
		var keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
		if(keyCode == 46){//删除
			var r = [];
			var ops = $("otherkinds");	
			var op = null;
			for(var i=ops.length-1;i>=0;i--){
				op = ops[i];
				if(op.selected){
					ops.remove(i);
				}else{
					r.push(op.value);
				}
			}
			$("OtherKindIds").value = r.join(",");
		}		
	},
	save : function(){	
		ProcessBar.start(wcm.LANG.PHOTO_CONFIRM_32 || '保存图片');
		var wmpos = [];
		var wmposcheckbox = $("LT","CM","RB");
		for(var i=0;i<3;i++){
			if(wmposcheckbox[i].checked){
				wmpos.push(wmposcheckbox[i].value);
			}
		}
		$("WatermarkPos").value = wmpos.join(",");
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var eQuoteKinds = $('QuoteKinds');
		var sChannelIds = eQuoteKinds.getAttribute("ChannelIds",2);
		$('form_imageInfo').OtherKindIds.value = sChannelIds;
		oHelper.call("wcm6_photo","saveImageInfo","form_imageInfo",true,function(_transport,_json){
//			CMSObj.createFrom({
//				objId : $v(_json, 'result'),
//				objType : 'photo'//WCMConstants.OBJ_TYPE_PHOTO
//			}).afterdelete();
			window.returnValue = "refresh";
			window.close();
		});
	}	
});
function SelectAllQuoteKinds(){
	var checkBoxs = document.getElementsByName('cb_quotekind');
	var tmpCb = null;
	var hasNotChecked = false;
	for(var i=0;i<checkBoxs.length;i++){
		tmpCb = checkBoxs[i];
		if(!tmpCb.disabled&&!tmpCb.checked){
			hasNotChecked = true;
		}
	}
	for(var i=0;i<checkBoxs.length;i++){
		tmpCb = checkBoxs[i];
		if(!tmpCb.disabled){
			tmpCb.checked = hasNotChecked;
		}
	}
}
function AddQuoteKind(){
	var eQuoteKinds = $('QuoteKinds');
	var sChannelIds = eQuoteKinds.getAttribute("ChannelIds",2);
	var oParams = {
		"SelectedChannelIds" : sChannelIds,
		"FromChannelId" : CurrChannelId
	}
	window.showModalDialog('../photo/photo_select_quotekind.html?' + $toQueryStr2(oParams), window ,"dialogWidth:300px;dialogHeight:440px;status:0;resizable:0;help:0;center:1;");
}
function SetQuoteKinds(_aChannelIds,_aChannelNames){
	var eQuoteKinds = $('QuoteKinds');
	RemoveAllQuoteChannel();
	var sHtml = '';
	var sRowHtml = '<TR id="quotekind_row_#ID#" class="attr_quotekind_row" valign="middle">\
		<TD style="height:20px;">\
			<input type="checkbox" id="cb_quotekind_#ID#" name="cb_quotekind" value="#ID#" #DD#>\
		</TD>\
		<TD class="attr_quotekind_column" onclick="toggleCb(\'cb_quotekind_#ID#\')">\
			#NAME#\
		</TD>\
	</TR>';
	_aChannelIds.each(function(_sId,_index){
		sHtml += sRowHtml.replace(/#ID#/ig,_sId).replace(/#NAME#/ig,_aChannelNames[_index]||'todo').replace(/#DD#/ig,'');
	});
	if(_aChannelIds.length==0){
		sHtml += '<TR id="quotekind_row_#ID#" class="attr_quotekind_row" valign="middle">\
			<TD style="height:20px;">\
				<input type="checkbox" id="cb_quotekind_" name="cb_quotekind" disabled>\
			</TD>\
			<TD class="attr_quotekind_column">\
			</TD>\
		</TR>';
	}
	if(_aChannelIds.length < 3){
		for(var i=0; i< getSize(_aChannelIds.length);i++){
		sHtml += '<TR bgColor="white">\
						<TD colspan="2" style="height:20px">\
						</TD>\
					</TR>';
		}
	}
	var tBody = $('otherkinds_holder');
	new Insertion.After(tBody,sHtml);
	eQuoteKinds.setAttribute("ChannelIds",_aChannelIds.join(','));
}
function getSize(_nSize){
	switch (_nSize){
		case 0:
			return 2;
		case 1:
			return 2;
		case 2:
			return 1;

	}
}
function toggleCb(_cbId){
	var eCheckBox = $(_cbId);
	if(eCheckBox){
		eCheckBox.checked = !eCheckBox.checked;
	}
}
function RemoveAllQuoteChannel(){
	var tBody = $('otherkinds_holder');
	$removeChilds(tBody);
	$removeChilds(tBody.nextSibling);
	var eQuoteKinds = $('QuoteKinds');
	eQuoteKinds.setAttribute("ChannelIds","");
}
function $removeChilds(_node){
	if(_node){
		var childs = _node.childNodes;
		for(var i=childs.length-1;i>=0;i--){
			$removeNode(childs[i]);
		}
		childs = [];
		delete _node;
	}
}
function $removeNode(_node){
	if(_node){
		var childs = _node.childNodes;
		for(var i=childs.length-1;i>=0;i--){
			$removeNode(childs[i]);
		}
		childs = [];
		if(_node.parentNode){
			_node.parentNode.removeChild(_node);
		}
//		Event.stopAllObserving(_node);
		delete _node;
	}
}
function RemoveSelectedQuoteKind(){
	var checkBoxs = document.getElementsByName('cb_quotekind');
	var tmpCb = null;
	var selectedChannelIds = [];
	for(var i=0;i<checkBoxs.length;i++){
		tmpCb = checkBoxs[i];
		if(!tmpCb.disabled&&tmpCb.checked){
			selectedChannelIds.push(tmpCb.value);
		}
	}
	if(selectedChannelIds.length <= 0) return;
	selectedChannelIds.each(function(_sId){
		var eRow = $('quotekind_row_'+_sId);
		eRow.parentNode.deleteRow(eRow.rowIndex-1);
	});
	var eQuoteKinds = $('QuoteKinds');
	var sChannelIds = eQuoteKinds.getAttribute("ChannelIds",2);
	if(sChannelIds.trim()!=''){
		sChannelIds = sChannelIds.split(',');
		selectedChannelIds.each(function(_sId){
			sChannelIds = sChannelIds.remove(_sId);
		});
		eQuoteKinds.setAttribute("ChannelIds",sChannelIds.join(','));
		var sHtml = "";
		if(sChannelIds.length < 3){
			sHtml = '<TR bgColor="white">\
						<TD colspan="2" class="chromeTD">\
						</TD>\
					</TR>';
		var tBody = $('otherkinds_holder');
		new Insertion.Bottom(tBody.nextSibling,sHtml);	
		}
	}
}
function onSelectedFile(_el){
	var fn = _el.value;
	if(fn.trim().length == 0){
		return false;
	}
	var fext = fn.substr(fn.lastIndexOf(".")+1).toLowerCase();	
	if($("batchupload").checked){
		if("zip" != fext){
			Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_51 || "请选择一个有效的zip文件!!");
			return false;
		}

		return true;
	}else{
		return validatePicFile(fext);
	}
}

function validatePicFile(_fext){
	if(m_hValidateFiles[_fext] == void(0)){
		$alert(String.format(wcm.LANG.PHOTO_CONFIRM_47 || ("只支持[{0}]格式的图片！"),m_hValidateFiles.toStr()));
		return false;
	}

	return true;
}

function switchUploadMode(){
	var checkboxEl = $("batchupload");
	var picnum = $("picnum");
	var picnumbtn = $("picnumbtn");
	if(checkboxEl.checked){
		picnum.disabled = true;
		picnumbtn.disabled = true;
		picnum.value = 1;
		changeUploadBlocks();
		$("BatchMode").value = "1";
	}else{
		picnum.disabled = false;
		picnumbtn.disabled = false;
		$("BatchMode").value = "0";
	}
	
	checkboxEl = null;
	picnum = null;
	picnumbtn = null;
}

var m_nTotalPicNum = 1;
function changeUploadBlocks(){
	var num = $F("picnum");
	if(num>10 || num<1 || isNaN(parseInt(num)) || num.length != 1){
		Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_44 || "请输入[1-9]的数字！");
		return;
	}
	var diff = num - m_nTotalPicNum;	
	var picForm = $("form_pic");
	var formContainer = picForm.parentNode;
	var tempChild = null;
	if(diff > 0){
		for(var i=0;i<diff;i++){
			tempChild = picForm.cloneNode(true);
			tempChild.id = "form_pic" + m_nTotalPicNum;
			formContainer.appendChild(tempChild);
			m_nTotalPicNum++;
		}
	}else if(diff < 0){
		diff = -diff;
		var elid = null;			
		for(var i=0;i<diff;i++){
			elid = "form_pic" + (--m_nTotalPicNum);	
			tempChild = $(elid);
			formContainer.removeChild(tempChild);				
		}
	}		
		
	picForm = null;
	formContainer = null;
}

var UploadFileName = null;
var m_aUploadedFiles = [];
var m_aSrcFiles = [];
var m_nUploaded = 0;
var isSSL = location.href.indexOf("https://")!=-1;
function uploadFile(_formId){
	var formId = _formId || "form_pic";		
	var fn = $(formId).PicUpload.value;	
	var callBack1 = {
		"upload":function(_transport){
			var sResponseText = _transport.responseText;			
			if(sResponseText.match(/<!--ERROR-->/img)){
				var texts = sResponseText.split('<!--##########-->');
				Ext.Msg.fault({
					message : texts[1],
					detail : texts[2]
				},wcm.LANG.PHOTO_CONFIRM_34 || '上传文件失败，与服务器交互时出错啦！');
				ProcessBar.close();
			}
			else{						
				saveImageInfo(fn,sResponseText);
			}
		}
	}
	try{
		YUIConnect.setForm(formId,true,isSSL);
		YUIConnect.asyncRequest('POST','../system/file_upload_dowith.jsp?FileParamName=PicUpload',callBack1);
	}catch(err){
		ProcessBar.close();
		Ext.Msg.alert(err.message);
	}
}
function saveImageInfo(_fn,_uploaded){
	if($("batchupload").checked){		
		$("UploadedFiles").value = _uploaded;		
		PageContext.save();	
		return false;
	}	
	
	m_aUploadedFiles.push(_uploaded);
	m_aSrcFiles.push(_fn);
	m_nUploaded++;

	if(m_nUploaded < m_nTotalPicNum){
		uploadFile("form_pic"+m_nUploaded);
	}else{
		$("SourceFiles").value = m_aSrcFiles.join(",");
		$("UploadedFiles").value = m_aUploadedFiles.join(",");	
		ProcessBar.close();
		PageContext.save();	
	}
}
function Ok(){
	//主分类
	if($F("MainKindId") == 0){
		Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_48 || "请选择一个主分类！");
		return false;
	}
	var arrUpload = [];
	//文件格式
	if($F("BatchMode") == 0){
		var uploadPics = document.getElementsByClassName("input_file");
		var uploadPic = null;	
		var fn = null;
		var fext = null;		
		var zNotValid = false;
		for(var i=0,len=uploadPics.length;i<len;i++){			
			uploadPic = uploadPics[i];
			fn = uploadPic.value;
			
			if(fn){
				fext = fn.substr(fn.lastIndexOf(".")+1);
			}else{
				fext = "";				
			}

			if(m_hValidateFiles[fext.toLowerCase()] == void(0)){				
				uploadPic.className = "input_file invalid_file";				
				if(fn){				
					Ext.Msg.alert(String.format(wcm.LANG.PHOTO_CONFIRM_49 || ("[{0}]不是支持的图片格式!\n仅支持[{1}]类型"),fn,m_hValidateFiles.toStr()));
				}else{				
					Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_50 || "没有选择图片!");
				}				
				zNotValid = zNotValid || true;
			}			
		}
		if(zNotValid){			
			return false;
		}
	}else{
		var fzip = document.getElementById("PicUpload").value;		
		if(!fzip || "zip" != fzip.substr(fzip.lastIndexOf(".")+1)){
			Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_51 || "请选择一个有效的zip文件!");
			return false;
		}
	}	
	ProcessBar.start(wcm.LANG.PHOTO_CONFIRM_30 || '上传图片');
	uploadFile();
	return false;
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
		if(height > 110 || width > 97){	
			if(height > width){				
				h = 110;	
				w = 110 * width/height;
			}else{				
				w = 97;
				h = 97 * height/width;
			}			
		}

		$("watermarkpic").width = w;
		$("watermarkpic").height = h;
}
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
		}
		imgLoaded.src = op.getAttribute("_picsrc") + "?r="+Math.random();
		$("watermarkpic").src = op.getAttribute("_picsrc");
		Element.show($("watermarkpic"));					
		Element.show($("div_watermarkpos"));					
		$("WatermarkFile").value = op.getAttribute("_picfile");
	}
}
Event.observe(window,'load',function(){
	PageContext.init();
	Event.observe($("batchupload"),"click",switchUploadMode);
	Event.observe($("picnumbtn"),"click",changeUploadBlocks);
});

