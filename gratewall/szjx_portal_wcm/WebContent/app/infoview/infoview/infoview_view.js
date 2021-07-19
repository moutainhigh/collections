var PgC = {
	names : {
		1 : 'input', 2 : 'textarea', 3 : 'repeatsection', 4 : 'repeattable',
		5 : 'fileattachment', 6 : 'trsobject', 7 : 'richtext', 8 : 'select',
		9 : 'checkbox', 10 : 'optionbutton', 11 : 'dtpicker_dttext', 12 : 'viewnavtab',
		13 : 'section', 101 : 'expression'
	}
};
Object.extend(PgC, {
	isBlockEle : function(el){
		var nElType = el.getAttribute('element-type', 2);
		return nElType==3 || (nElType>=12 && nElType<100);
	}
});
Event.observe(window, 'load', function(){
	var eles = document.getElementsByTagName("*");
	for(var i=0; i<eles.length; i++) {
		var el = eles[i];
		//_TransRule_.doTrans(el);
		_TransRule_.doTrans(el, el.getAttribute('trs_readonly_field', 2)=='1');
	}
});
var m_sRootPath = './infoview/';
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
		this.initPower(el);
		this.initDefaultValue(el);
	},
	initPower : function(nd){
		var block = PgC.isBlockEle(nd), backable, frontable, pubable;
		if(block){
			pubable = parent.m_FieldHelper.isPublishable(nd);
			return Element[pubable?'removeClassName':'addClassName'](nd, 'unpublish');
		}
		frontable = parent.m_FieldHelper.isFrontWriteable(nd);
		backable = parent.m_FieldHelper.isBackWriteable(nd);
		if(nd.getAttribute('xd:xctname', 2) == 'FileAttachment' || nd.getAttribute('xd:xctname', 2) == 'InlineImage'){
			if(!frontable && !backable)nd.style.backgroundColor = '#DFDFDF';
			else if(!frontable)nd.style.backgroundColor = '#ADD69B';
			else if(!backable)nd.style.backgroundColor = '#D2A499';
			else nd.style.backgroundColor = '';
			return;
		}
		if(!backable && !frontable){
			Element['addClassName'](nd, 'unwrite'); 
			Element['removeClassName'](nd, 'unfrontwrite'); 
			Element['removeClassName'](nd, 'unbackwrite');
			return;
		}else{
			Element[frontable?'removeClassName':'addClassName'](nd, 'unfrontwrite');
			Element[backable?'removeClassName':'addClassName'](nd, 'unbackwrite');
			Element['removeClassName'](nd, 'unwrite');
		}
	},
	initDefaultValue : function(nd){
		var nElType = nd.getAttribute('element-type', 2);
		var v = nd.getAttribute('default_value', 2) || '';
		if(nElType==1 || nElType==2 || nElType==7){
			nd.innerHTML = v;
		}
		else if(nElType==8){
			nd.value = v;
		}
		else if(nElType==9 || nElType==10){
			if(nd.value==v)nd.checked = true;
		}
	},
	mustDoCaches : [],
	pushToMustDoCaches : function(item){
		this.mustDoCaches.push(item);
	},
	removeMustDoCaches : function(){
		this.mustDoCaches = [];
	},
	getMustDoCaches : function(){
		return this.mustDoCaches;
	},
	_MustDoSpan : function(){
		var sp = document.createElement("SPAN");
		sp.className = 'must_fill';
		sp.innerHTML = '*';
		return sp;
	},
	NotifyMustFill : function(nd, os){
		var spid = nd.getAttribute('trs_obj_id', 2) + "_mustfill";
		if(nd.getAttribute("not_null", 2) == "1"){
			if($(spid)){
				Element.show(spid);
				return;
			}
			var sp = this._MustDoSpan();
			this.pushToMustDoCaches([nd, sp]);
			sp.id = spid;
			document.body.appendChild(sp);
			Position.clone(nd, sp, {
				setWidth : false,
				setHeight : false,
				offsetLeft : nd.offsetWidth,
				offsetTop : 5
			});
			for(var n in os){
				sp.style[n] = os[n];
			}
		}
		else if($(spid)){
			Element.hide(spid);
		}
	}
};
_TransRule_.save("span", "PlainText", function(nd) {
	var nHeight = parseInt(nd.style.height, 10);
	if(isNaN(nHeight))nHeight = 0;
	var isTextArea = nHeight > 30;
	nd.setAttribute("element-type", isTextArea?2:1);
	if(isTextArea)
		 nd.className = nd.className + ' ' + 'xdAreaBox'; 
	_TransRule_.NotifyMustFill(nd, {marginLeft:'-15px'});
});
_TransRule_.save("span", "RichText", function(nd) {
	nd.style.overflow = "auto";
	nd.setAttribute("element-type", "7");
	_TransRule_.NotifyMustFill(nd, {marginLeft:'-15px'});
});
_TransRule_.save("select", "DropDown", function(nd) {
	nd.setAttribute("element-type", "8");
	_TransRule_.NotifyMustFill(nd);
});
_TransRule_.save("select", "ListBox", function(nd) {
	nd.setAttribute("element-type", "8");
	_TransRule_.NotifyMustFill(nd);
});
_TransRule_.save("input", "CheckBox", function(nd) {
	nd.setAttribute("element-type", "9");
});
_TransRule_.save("input", "OptionButton", function(nd) {
	nd.setAttribute("element-type", "10");
});
_TransRule_.save("div", "Section", function(nd) {
	nd.setAttribute("element-type", "13");
	nd.style.border = '1px dotted gray';
});
_TransRule_.save("div", "RepeatingSection", function(nd) {
	nd.setAttribute("element-type", "3");
	nd.style.border = '1px dotted gray';
});
_TransRule_.save("tbody", "RepeatingTable", function(nd) {
	nd.setAttribute("element-type", "4");
});
_TransRule_.save("span", "ListItem_Plain", function(nd) {
	nd.setAttribute("element-type", "1");
});
_TransRule_.save("span", "DTPicker_DTText", function(nd) {
	nd.contentEditable = false;
	nd.setAttribute("element-type", "11");
	nd.style.width = (nd.parentNode.offsetWidth - 37) + "px";
	_TransRule_.NotifyMustFill(nd, {marginLeft:'-15px'});
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
	txt.innerHTML = wcm.LANG['INFOVIEW_DOC_54'] || "点击此处以添加附件";
	nd.appendChild(txt);
	_TransRule_.NotifyMustFill(nd, {marginLeft:'-15px', marginTop:'7px'});
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
		img.src = m_sRootPath + 'spacer.gif';
		img.style.width = w + 'px';
		img.style.height = h + 'px';
		img.style.background = "url(" + m_sRootPath + "ImageAttachment.gif) no-repeat center center";
		sp.appendChild(img);
		var txt = document.createElement("span");
		txt.setAttribute("text_body", "1");
		txt.innerHTML = '&nbsp;' + (wcm.LANG['INFOVIEW_DOC_93'] || '点击此处以添加图片');
		txt.style.marginLeft = (-w)  + 'px';
		txt.style.marginTop = '-22px';
		txt.style.height = '16px';
		txt.style.lineHeight = '16px';
		sp.appendChild(txt);
		return sp;
	};
}
_TransRule_.save("img", "InlineImage", GetImageAttachmentRule('trs_is_inline_image'));
_TransRule_.save("img", "LinkedImage", GetImageAttachmentRule('trs_is_linked_image'));
_TransRule_.save("span", "ExpressionBox", function(nd) {
	nd.setAttribute("element-type", "101");
});
Event.observe(window, 'resize', function(){
	var caches = _TransRule_.getMustDoCaches();
	for(var i=0, n=caches.length; i<n; i=i+1){
		Position.clone(caches[i][0], caches[i][1], {
			setWidth : false,
			setHeight : false,
			offsetLeft : caches[i][0].offsetWidth,
			offsetTop : 5
		});
	}
});
Event.observe(window, 'unload', function(){
	_TransRule_.removeMustDoCaches();
});