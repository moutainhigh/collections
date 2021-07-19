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
Object.extend(PageContext,{
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
		BasicDataHelper.call("wcm6_photo","getDefaultBmpConverType",null,false,function(_transport,_json){			
			$("BmpConverTypeSelect").value = _transport.responseText;
			$("BmpConverType").value =  _transport.responseText;
		});
	},
	loadMainKind : function(_id){
		var id = _id || this.mainKindId;
		BasicDataHelper.call("wcm6_channel","findById",{ObjectId:id},false,function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('template_mainkind', _json);			
			Element.update($("mainkind"),sValue);
			PageContext.loadWatermarks(PageContext.LibId||$("_mainkind")._siteId);
			$("MainKindId").value = id;
			PageContext.mainKindId = id;
		});
	},
	loadOtherKinds : function(_ids){
		var ids = _ids || "";				
		BasicDataHelper.call("wcm6_channel","findByIds",{ObjectIds:ids},true,function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('template_otherkinds', _json);			
			Element.update($("otherkinds_holder"),sValue);
			$("OtherKindIds").value = ids;
		});
	},
	removeOtherKind : function(_evt){
		var evt = window.event || _evt;
		if(evt.keyCode == 46){//删除
			var r = [];
			var ops = $("otherkinds").options;	
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
	loadWatermarks : function(_libId){
		BasicDataHelper.call("wcm6_watermark","query",{LibId:_libId},false,function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('template_watermarks', _json);			
			Element.update($("watermarks"),sValue);
			Event.observe($("selwatermark"),"change",function(){
				var el = $("selwatermark");
				var op = el.options[el.selectedIndex];
				if(op.value == -1){
					Element.hide($("watermarkpic"));
					Element.hide($("div_watermarkpos"));
					$("WatermarkFile").value = "";
				}else{
					$("watermarkpic").src = op._picsrc;
					Element.show($("watermarkpic"));					
					Element.show($("div_watermarkpos"));					
					$("WatermarkFile").value = op._picfile;
				}
			});
		});
	},
	save : function(){	
		ProcessBar.addState('正在保存图片.');
		ProcessBar.start();
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
			ProcessBar.exit();
			var oScope = window.dialogArguments;
			oScope.setTimeout(new oScope.Function("PageContext.RefreshList();"),10);
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
	window.showModalDialog('../photo/photo_select_quotekind.html?' + $toQueryStr(oParams), window ,"dialogWidth:300px;dialogHeight:440px;status:0;resizable:0;help:0;center:1;");
}
function SetQuoteKinds(_aChannelIds,_aChannelNames){
	var eQuoteKinds = $('QuoteKinds');
	RemoveAllQuoteChannel();
	var sHtml = '';
	var sRowHtml = '<TR id="quotekind_row_#ID#" class="attr_quotekind_row" valign="middle">\
		<TD>\
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
		sHtml += sRowHtml.replace(/#ID#/ig,'').replace(/#DD#/ig,'disabled').replace(/#NAME#/ig,'');
	}
	var tBody = $('otherkinds_holder');
	new Insertion.Bottom(tBody,sHtml);
	eQuoteKinds.setAttribute("ChannelIds",_aChannelIds.join(','));
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
	var eQuoteKinds = $('QuoteKinds');
	eQuoteKinds.setAttribute("ChannelIds","");
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
	selectedChannelIds.each(function(_sId){
		var eRow = $('quotekind_row_'+_sId);
		eRow.parentNode.deleteRow(eRow.rowIndex-1);
	});
	var eQuoteKinds = $('QuoteKinds');
	var sChannelIds = eQuoteKinds.getAttribute("ChannelIds",2);
	if(sChannelIds.trim()!=''){
		sChannelIds = sChannelIds.split(',');
		sChannelIds = sChannelIds.without.apply(sChannelIds,selectedChannelIds);
		eQuoteKinds.setAttribute("ChannelIds",sChannelIds.join(','));
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
			$alert("您所选的不是一个zip格式的文件!");
			return false;
		}

		return true;
	}else{
		return validatePicFile(fext);
	}
}

function validatePicFile(_fext){
	if(m_hValidateFiles[_fext] == void(0)){
		$alert("只支持["+m_hValidateFiles.toStr()+"]格式的图片！");
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
		$alert("请输入[1-9]的数字！");
		return;
	}
	var diff = num - m_nTotalPicNum;	
	var picForm = $("form_pic0");
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
var m_toUploadIndexs = [];
var isSSL = location.href.indexOf("https://")!=-1;
function uploadFile(_nIdx){
	var formId = "form_pic" + m_toUploadIndexs[_nIdx];
	var fn = $(formId).PicUpload.value;	
	var callBack1 = {
		"upload":function(_transport){
			var sResponseText = _transport.responseText;			
			if(sResponseText.match(/<!--ERROR-->/img)){
				var texts = sResponseText.split('<!--##########-->');
				FaultDialog.show({
					code		: texts[0],
					message		: texts[1],
					detail		: texts[2],
					suggestion  : ''
				}, '上传文件失败，与服务器交互时出错啦！');
				ProcessBar.exit();
			}
			else{						
				if($("batchupload").checked){		
					$("UploadedFiles").value = sResponseText;
					ProcessBar.next();
					setTimeout(function(){
						ProcessBar.next();
						PageContext.save();	
					},10);
					return;
				}
				m_aUploadedFiles.push(sResponseText);
				m_aSrcFiles.push(fn);
				m_nUploaded++;
				if(m_nUploaded < m_toUploadIndexs.length){
					ProcessBar.next();
					setTimeout(function(){
						ProcessBar.next();
						uploadFile(m_nUploaded);
					},10);
				}else{
					$("SourceFiles").value = m_aSrcFiles.join(",");
					$("UploadedFiles").value = m_aUploadedFiles.join(",");		
					ProcessBar.next();
					setTimeout(function(){
						ProcessBar.next();
						PageContext.save();	
					},10);
				}
			}
		}
	}
	try{
		YUIConnect.setForm(formId,true,isSSL);
		YUIConnect.asyncRequest('POST','../system/file_upload_dowith.jsp?FileParamName=PicUpload',callBack1);
	}catch(err){
		ProcessBar.exit();
	}
}

function Ok(){
	//主分类
	if($F("MainKindId") == 0){
		$alert("您怎么做到不选择一个主分类的?");
		return false;
	}
	var arrUpload = [];
	//文件格式
	ProcessBar.init('执行进度，请稍候...');
	if($F("BatchMode") == 0){
		var uploadPics = document.getElementsByClassName("input_file");
		var uploadPic = null;	
		var fn = null;
		var fext = null;		
		var zNotValid = false;
		var sWarning = "";
		m_toUploadIndexs = [];
		for(var i=0,len=uploadPics.length;i<len;i++){			
			uploadPic = uploadPics[i];
			fn = uploadPic.value;
			if(!fn)continue;
			arrUpload.push(fn);
			m_toUploadIndexs.push(i);
			fext = fn.substr(fn.lastIndexOf(".")+1);

			if(m_hValidateFiles[fext.toLowerCase()] == void(0)){				
				uploadPic.className = "input_file invalid_file";				
				sWarning += "第"+(i+1)+"副图片:["+fn+"]不是支持的图片格式!<br>";
			}
		}
		if(sWarning!=""){
			$alert(sWarning);
			return false;
		}
		if(arrUpload.length==0){
			$alert('没有选择任何本地图片!');
			return;
		}
	}else{
		var fzip = document.getElementById("PicUpload").value;	
		if(!fzip || "zip" != fzip.substr(fzip.lastIndexOf(".")+1)){
			$alert("请选择一个有效的zip文件!");
			return false;
		}
		ProcessBar.addState('正在上传文件:' + fzip);
		ProcessBar.addState('成功上传文件:' + fzip);
		m_toUploadIndexs.push(0);
	}	
	
	for(var i=0;i<arrUpload.length;i++){
		ProcessBar.addState('正在上传图片:' + arrUpload[i]);
		ProcessBar.addState('成功上传图片:' + arrUpload[i]);
	}
	ProcessBar.addState('正在处理上传的图片.');
	ProcessBar.addState('任务执行成功.');
	ProcessBar.start();
	uploadFile(0);
	//FloatPanel.open("./photo/photoprops_edit.html?DocIds=796,795,794&ChannelId=53","标注图片属性",680,350);for test.
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
Event.observe(window,'load',function(){
	PageContext.init();
	PageContext.loadMainKind();
	Event.observe($("batchupload"),"click",switchUploadMode);
	Event.observe($("picnumbtn"),"click",changeUploadBlocks);
});

