var exCenter = null;
function defExCenter(){
	var l1 = [], l2 = [], l3 = [], l4 = [], inited;
	exCenter = {
		onafterTrans : function(fn){
			l1.push(fn);
			if(window.__loaded)fn();
		},
		onafterInitData : function(fn){
			l2.push(fn);
			if(window.__loaded)fn();
		},
		onafterModify : function(fn){
			l3.push(fn);
		},
		onbeforeSubmit : function(fn){
			l4.push(fn);
		},
		_afterTrans : function(){
			for(var i=0,n=l1.length;i<n;i++){
				(l1[i])();
			}
		},
		_afterInitData : function(){
			for(var i=0,n=l2.length;i<n;i++){
				(l2[i])();
			}
		},
		_afterModify : function(el){
			for(var i=0,n=l3.length;i<n;i++){
				(l3[i])(el);
			}
		},
		_beforeSubmit : function(rst){
			for(var i=0,n=l4.length;i<n;i++){
				if((l4[i])(rst)===false)return false;
			}
			return true;
		}
	}
};
defExCenter();
Event.observe(window, 'load', function(){
	var eles = document.getElementsByTagName("*");
	for(var i=0; i<eles.length; i++) {
		var el = eles[i];
		_TransRule_.doTrans(el);
	}
	exCenter._afterTrans();
	XmlDataHelper.initData(parent.$('frmAction').DataXML.value);
	exCenter._afterInitData();
	regClickEvent(document.body);
});
var m_sRootPath = './infoview/';
var m_readFileUrl = '../file/read_file.jsp?FileName=';
var _TransRule_ = {
	xct : 'xd:xctname',
	Rules : {},
	save : function(tag, xct, m) {
		tag = tag.toLowerCase();
		var l = this.Rules[tag];
		this.Rules[tag] = l = l || {};
		l[xct.toLowerCase()] = m;
	},
	doTrans : function(el, args) {
		var tag = el.tagName;
		if(!tag)return false;
		var xct = el.getAttribute(this.xct, 2);
		if(!xct)return false;
		xct = xct.toLowerCase();
		var l = this.Rules[tag.toLowerCase()];
		if(!l || !l[xct])return;
		el = l[xct](el, args) || el;
		if(!el.id)el.id = el.getAttribute('trs_temp_id', 2);
		this.initDefaultValue(el);
	},
	initDefaultValue : function(nd){
		var nElType = nd.getAttribute('element-type', 2);
		var v = nd.getAttribute('default_value', 2) || '';
		EleHelper.setValue(nd, v);
	}
};
_TransRule_.save("span", "PlainText", function(nd) {
	var nHeight = parseInt(nd.style.height, 10);
	if(isNaN(nHeight))nHeight = 0;
	var isTextArea = nHeight > 30;
	nd.contentEditable = false;
	nd.setAttribute("element-type", isTextArea?2:1);
	if(!isTextArea && nHeight > 0){
		nd.style.lineHeight = nd.style.height;
	}
	if(!isTextArea) return;
	var rp = nd;
	rp = document.createElement("textarea");
	copyAttrs(nd, rp); 
	rp.style.overflow = "auto"; 
	nd.parentNode.insertBefore(rp, nd.nextSibling);
	nd.parentNode.removeChild(nd); 

});
_TransRule_.save("span", "RichText", function(nd) {
	nd.style.overflow = "auto";
	nd.setAttribute("element-type", "7");
});
_TransRule_.save("select", "DropDown", function(nd) {
	nd.setAttribute("element-type", "8");
});
_TransRule_.save("select", "ListBox", function(nd) {
	nd.setAttribute("element-type", "8");
});
_TransRule_.save("input", "CheckBox", function(nd) {
	nd.setAttribute("element-type", "9");
});
_TransRule_.save("input", "OptionButton", function(nd) {
	nd.setAttribute("element-type", "10");
});
_TransRule_.save("div", "Section", function(nd) {
	nd.setAttribute("element-type", "13");
});
_TransRule_.save("div", "RepeatingSection", function(nd) {
	nd.setAttribute("element-type", "3");
	nd.style.lineHeight = nd.style.height;
});
_TransRule_.save("tbody", "RepeatingTable", function(nd) {
	nd.setAttribute("element-type", "4");
	//兼容多浏览器，给重复表的父元素添加样式
	if(nd.parentNode.tagName.toUpperCase() == 'TABLE'){
		var repeatingTableContainer = nd.parentNode.parentNode;
		if(repeatingTableContainer.tagName.toUpperCase() == 'DIV')
			repeatingTableContainer.className = 'xdRepeatingTableContainer';
	}
});
_TransRule_.save("span", "ListItem_Plain", function(nd) {
	nd.setAttribute("element-type", "1");
});
_TransRule_.save("span", "DTPicker_DTText", function(nd) {
	nd.contentEditable = false;
	nd.setAttribute("element-type", "11");
	if(nd.parentNode.offsetHeight > 25)
		nd.style.height = '21px';
	else 
		nd.style.height = (nd.parentNode.offsetHeight - 4 ) + 'px';
	nd.style.lineHeight = nd.style.height;
	if(nd.nextSibling.tagName.toUpperCase() == 'BUTTON')
		nd.parentNode.removeChild(nd.nextSibling); 
});
_TransRule_.save("span", "FileAttachment", function(nd) {
	nd.setAttribute("element-type", "5");
	nd.style.padding = '4px';
	nd.style.cursor = 'pointer';
	var img = document.createElement("img");
	img.src = m_sRootPath + "FileAttachment.gif";
	img.style.width = "14px";
	img.style.height = "13px";
	img.align = 'absmiddle';
	nd.appendChild(img);
	var txt = document.createElement("span");
	txt.setAttribute("text_body", "1");
	txt.style.position = "relative";
	txt.style.paddingLeft = '1px';
	txt.innerHTML = "&nbsp;" + wcm.LANG['INFOVIEW_DOC_54'] || "点击此处以添加附件";
	nd.appendChild(txt);
	txt = document.createElement("span");
	txt.setAttribute("text_file", "1");
	txt.style.display = 'none';
	txt.style.position = "relative";
	txt.style.paddingLeft = '1px';
	txt.innerHTML = "";
	nd.appendChild(txt);
	//nd.onclick = fileBrowser;
	nd.setAttribute('_action', "fileBrowser");
});
function copyAttrs(src, tg){
	var attrs = src.attributes;
	for(var i=0; i<attrs.length; i++) {
		if(!attrs[i].specified)continue;
		if(attrs[i].name == "style"){
			tg.style.cssText = src.style.cssText;
			continue;
		}
		tg.setAttribute(attrs[i].name, attrs[i].value);
	}
}
var PgC = {
	fileBrowser : function(event, tg){
		var nd = tg.parentNode || tg;
		var xct = nd.getAttribute("xd:xctname", 2);
		xct = xct ? xct.toLowerCase() : '';
		var file = null;
		var sDownName = null;
		if(xct == "fileattachment"){
			var arr = nd.childNodes;
			for(var i=0; i<arr.length; i++) {
				var c1 = arr[i];
				if(c1.getAttribute("text_file") == "1"){
					file = c1.getAttribute("_fileName", 2);
					sDownName = c1.getAttribute("_fileDesc", 2);
					break;
				}
			}
		}
		else if(xct == "inlineimage" || xct == "linkedimage"){
			file = nd.getElementsByTagName('IMG')[0].getAttribute("_fileName", 2);
			sDownName = nd.getElementsByTagName('IMG')[0].getAttribute("_fileDesc", 2);
			
		}
		if(file!=null && file!=''){
			window.open(m_readFileUrl + file + '&DownName=' + encodeURIComponent(sDownName));
			return;
		}
	}
}
 
var GetImageAttachmentRule = function(_sXctNameFlag){
	return function(nd) {
		var sp = document.createElement('SPAN');
		sp.setAttribute("element-type", "5");
		copyAttrs(nd, sp);
		sp.style.cursor = "pointer";
		nd.parentNode.insertBefore(sp, nd.nextSibling);
		var w = nd.offsetWidth, h = nd.offsetHeight;
		nd.parentNode.removeChild(nd);
		var img = document.createElement('IMG');
		img.setAttribute('image_body', 1);
		img.src = m_sRootPath + 'spacer.gif';
		img.style.width = w + 'px';
		img.style.height = h + 'px';
		img.style.background = "url(" + m_sRootPath + "ImageAttachment.gif) no-repeat center center";
		sp.appendChild(img);
		var txt = document.createElement("span");
		txt.setAttribute("text_body", "1");
		txt.innerHTML = '&nbsp;' + wcm.LANG['INFOVIEW_DOC_93'] || '点击此处以添加图片';
		txt.style.marginLeft = (-w)  + 'px';
		txt.style.marginTop = '-22px';
		txt.style.height = '16px';
		txt.style.lineHeight = '16px';
		sp.appendChild(txt);
		sp.setAttribute('_action', "fileBrowser");
		return sp;
	};
}
_TransRule_.save("img", "InlineImage", GetImageAttachmentRule('trs_is_inline_image'));
_TransRule_.save("img", "LinkedImage", GetImageAttachmentRule('trs_is_linked_image'));
_TransRule_.save("span", "ExpressionBox", function(nd) {
	nd.setAttribute("element-type", "101");
});
function regClickEvent(id){
	Ext.get(id).on('click', function(exEv, tg){
		var actionItem = findItem(tg, false, '_action');
		if(!actionItem)return;
		var action = actionItem.getAttribute('_action', 2);
		var fn = PgC[action];
		if(!fn)return;
		fn.apply(PgC, [exEv, tg]);
	});
}
function $transHtml(_sContent) {
	if (_sContent == null)
		return '';

	var nLen = _sContent.length;
	if (nLen == 0)
		return '';

	var result = '';
	for (var i = 0; i < nLen; i++) {
		var cTemp = _sContent.charAt(i);
		switch (cTemp) {
		case '<': // 转化：< --> &lt;
			result += '&lt;';
			break;
		case '>': // 转化：> --> &gt;
			result += '&gt;';
			break;
		case '"': // 转化：" --> &quot;
			result += '&quot;';
			break;$transHtml
		default:
			result += cTemp;
		}// case
	}// end for
	return result;
}
var EleHelper = {
	setValue : function(nd, myv, info){
		var v = (typeof myv=='string') ? myv : XmlHelper._xvalue(myv);
		if(v == null)return false;
		var xp = nd.getAttribute("trs_temp_id", 2);
		var nElType = nd.getAttribute('element-type', 2);
		if(!xp || !nElType)return false;
		if(nElType==1 || nElType==11){
			nd.innerHTML = $transHtml(v);
		}
		else if(nElType==2)nd.value = v;
		else if(nElType==7)nd.innerHTML = v;
		else if(nElType==8){
			nd.value = v;
			if(v!='' && nd.value==''){
				var a = document.createElement('OPTION');
				nd.appendChild(a);
				a.value = a.innerHTML = v;
				a.selected = true;
			}
			nd.value = v;
		}
		else if(nElType==10){
			if(nd.getAttribute("xd:onValue")==v)nd.checked = true;
		}
		else if(nElType==9){
			var sv = ',' + nd.getAttribute("xd:onValue") + ',';
			if((','+v+',').indexOf(sv)!=-1)nd.checked = true;
			else nd.checked = false;
		}
		else if(nElType==5){
			var xct = nd.getAttribute("xd:xctname", 2);
			xct = xct ? xct.toLowerCase() : '';
			var arr = nd.childNodes;
			for(var i=0; i<arr.length; i++) {
				var c1 = arr[i];
				if(!c1.tagName)continue;
				if(xct == "fileattachment"){
					if(c1.getAttribute("text_body") == "1") {
						c1.style.display = v ? "none" : "";
						continue;
					}
					if(c1.getAttribute("text_file") == "1") {
						c1.style.display = v ? "" : "none";
						var v2 = '';
						try{
							v2 = (info==null ? myv.getAttribute('FileName') : info.FileDesc) || v;	
						}catch(err){
							v2 = v;
						}
						c1.innerHTML = v2;
						if(v=="")continue;
						c1.title = v;
						c1.setAttribute("_fileDesc", v2);
						c1.setAttribute("_fileName", v);
						continue;
					}
				}
				else{
					if(c1.getAttribute("image_body") == "1") {
						var v2 = '';
						try{
							v2 = info==null ? myv.getAttribute('FileName') : info.FileName;	
						}catch(err){}
						if(v=="")continue;
						c1.src = m_readFileUrl + v;
						c1.title = v2;
						c1.style.display = v ? "" : "none";
						c1.style.background = '';
						c1.setAttribute("_fileDesc", v2);
						c1.setAttribute("_fileName", v);
						continue;
					}
					c1.style.display = v ? "none" : "";
				}
			}
		}
		return true;
	},
	isRepeatEle : function(nd){
		var type = nd.getAttribute("element-type", 2);
		return type==3 || type==4;
	},
	isContainerEle : function(nd){
		var type = nd.getAttribute("element-type", 2);
		return type==3 || type==13 || type==4;
	}
}