var ua = navigator.userAgent.toLowerCase();
var isSafari = (/webkit|khtml/).test(ua),
	isGecko = !isSafari && ua.indexOf("gecko") > -1;
var m_readFileUrl = '../file/read_file.jsp?FileName=';
var m_sRootPath = './infoview/';
PgC = {
	attach_file : function(ev, tg){
		var xp = tg.getAttribute('_tgid', 2);
		var el = $(xp);
		PgC.appendFileForEl(el);
	},
	append_file : function(ev, tg){
		PgC.attach_file(ev, tg);
	},
	open_file : function(ev, tg){
		var xp = tg.getAttribute('_tgid', 2);
		var el = $(xp);
		if(!el) return;
		var file = EleHelper.getFile(el, true);
		if(file!=null && file!=''){
			window.open(m_readFileUrl + file.FileName + '&DownName=' + encodeURIComponent(file.FileDesc));
			return;
		}
	},
	remove_file : function(ev, tg){
		var xp = tg.getAttribute('_tgid', 2);
		var el = $(xp);
		if(!el)return;
		var bIsAppend = el.getAttribute('trs_temp_id', 2)!=xp && el.id != xp;
		if(bIsAppend){
			el.parentNode.removeChild(el);
			return;
		}
		EleHelper.setValue(el, '', {FileDesc:''});
	},
	appendFileForEl : function(el){
		if(!parent.InfoDocHelper)return;
		if(!el) return;
		parent.InfoDocHelper.attachFile({
			params : {
				AllowExt : el.getAttribute('allow_ext', 2),
				InlineImg : el.getAttribute('trs_file_upload_type', 2) == 'InlineImage'
			},
			callback : function(_args){
				var file = _args[0], fileDesc = _args[1] || file;
				var currFile = EleHelper.getFile(el);
				if(currFile){
					var newEl = el.cloneNode(true);
					newEl.id = el.id + '_' + new Date().getTime();
					el.parentNode.insertBefore(newEl, el.nextSibling);
					el = newEl;
				}
				EleHelper.setValue(el, file, {FileDesc:fileDesc});
			}
		});
	},
	repeat_insert1 : function(ev, tg, end){
		var xp = tg.getAttribute('_tgid', 2);
		var el = $(xp);
		if(!el)return;
		var newEl = el.cloneNode(true);
		newEl.id = el.name + '_' + new Date().getTime();
		var els = newEl.getElementsByTagName("*");
		for(i=0; i<els.length;i++){
			var currEl = els[i];
			this.renameRepeatId(currEl);
			var currElXctName = currEl.getAttribute('xd:xctname',2);
			if(!currElXctName)continue;
			if(currElXctName == 'DTPicker_DTText'){
				if(!currEl.disabled){
					makeDateCalendarRule(currEl);
				}
			}
			if(currElXctName == 'RichText'){
				if(!currEl.disabled){
					makeTextEditorRule(currEl);
				}
			}
		}
		el.parentNode.insertBefore(newEl, end ? el.nextSibling : el);
		EleHelper.setValue(newEl, '');
		exCenter._afterModify(newEl);
		rePraintRxTool();
	},
	repeat_insert2 : function(ev, tg){
		this.repeat_insert1(ev, tg, true);
	},
	repeat_remove : function(ev, tg){
		var xp = tg.getAttribute('_tgid', 2);
		var el = $(xp);
		if(!el)return;
		if(!this.isTheOnlyRepeatSection(el)){
			el.parentNode.removeChild(el);
		}else{
			EleHelper.setValue(el, '');
		}
		rePraintRxTool();
	},
	isTheOnlyRepeatSection : function(el) {
		var children = el.parentNode.children;
		var count = 0;
		for(var i=0,len=children.length;i<len && (count <= 1);i++){
			if(Element.hasClassName(children[i],'xdRepeatingSection')) {
				count++;
			}
		}
		//重复表的情况
		if(count == 0){
			for(var i=0,len=children.length;i<len && (count <= 1);i++){
				if(children[i].getAttribute("xd:xctname",2) == "RepeatingTable") {
					count++;
				}
			}
		}
		return (count <= 1);
	},
	renameRepeatId : function(el){
		var id = el.id;
		if(!id || $(id) == el) return el;
		el.id = id + "_" + (new Date().getTime());
	},
	flys : function(elType, el){
		var rst;
		if(elType==5){
			var isImg = el.getAttribute('is-image', 2)=='1';
			var file = EleHelper.getFile(el);
			rst = [{
					action : file ? 'append_file' : 'attach_file',
					name : wcm.LANG['INFOVIEW_DOC_74'] || '附加...',
					desc : isImg? (wcm.LANG['INFOVIEW_DOC_75'] ||'上传图片'):(wcm.LANG['INFOVIEW_DOC_76'] || '上传附件')
				}];
			if(file){
				rst.push({
						action : 'open_file',
						name : isImg? (wcm.LANG['INFOVIEW_DOC_77'] ||'查看图片'):(wcm.LANG['INFOVIEW_DOC_78'] ||'打开附件')
					},{
						action : 'remove_file',
						name : wcm.LANG['INFOVIEW_DOC_79'] || '删除',
						desc : isImg?(wcm.LANG['INFOVIEW_DOC_80'] || '移除图片'): (wcm.LANG['INFOVIEW_DOC_81'] ||'移除附件')
					}
				);
			}
		}
		else if(elType==4 || elType==3){
			if(el.getAttribute('trs_readonly_field', 2)=='1' || el.getAttribute('trs_backreadonly_field', 2)=='1')
				return null;
			var sInsert = el.getAttribute("trs_xCollection_insert", 2)||'';
			sInsert = sInsert.replace(/^Insert\s*(.*?)\s*$/ig, wcm.LANG['INFOVIEW_DOC_82'] || '在$1当前行插入');
			var sInsertBefore = el.getAttribute("trs_xCollection_insertBefore", 2)||'';
			sInsertBefore = sInsertBefore.replace(/^Insert\s*(.*?)\s*Above$/ig, wcm.LANG['INFOVIEW_DOC_83'] ||'在$1当前行前插入');
			var sInsertAfter = el.getAttribute("trs_xCollection_insertAfter", 2)||'';
			sInsertAfter = sInsertAfter.replace(/^Insert\s*(.*?)\s*Below$/ig, wcm.LANG['INFOVIEW_DOC_84'] ||'在$1当前行后插入');
			var sRemove = el.getAttribute("trs_xCollection_remove", 2)||'';
			sRemove = sRemove.replace(/^Remove\s*(.*?)\s*$/ig, wcm.LANG['INFOVIEW_DOC_85'] || '删除$1当前行');
			rst = [{
					action : 'repeat_insert1',
					name : wcm.LANG['INFOVIEW_DOC_86'] || '在前面添加',
					desc : sInsertBefore
				},
				{
					action : 'repeat_insert2',
					name : wcm.LANG['INFOVIEW_DOC_87'] || '在后面添加',
					desc : sInsertAfter
				},{
					action : 'repeat_remove',
					name : wcm.LANG['INFOVIEW_DOC_79'] || '删除',
					desc : sRemove
				}
			];
		}
		return rst;
	},
	flyItem : '<DIV class="menuitem" unselectable="on" _action="{1}" _tgid="{0}" title="{3}">{2}</DIV>',
	buildMenu : function(items, tg){
		var rst = [];
		for(var i=0, n=items.length; i<n; i++){
			var t = items[i];
			rst.push(String.format(PgC.flyItem, tg.id, t.action, t.name, t.desc));
		}
		return rst.join('');
	},
	borderFx : function(el, undo){
		if(el.getAttribute('trs_readonly_field', 2)=='1' || el.getAttribute('trs_backreadonly_field', 2)=='1')return;
		var nElType = el.getAttribute('element-type', 2);
		if(nElType==13)return;
		if(nElType==11)el = el.parentNode;
		Element[undo?'removeClassName':'addClassName'](el, 'xct-hover');
	},
	flyBtnFx : function(ev, el, undo){
		el = findItem(el, false, 'fly-button');
		if(!el)return;
		var fb = $('fly-button');
		if(undo){
			fb.style.display = 'none';
			fb.cfg = '';
			return;
		}
		var eid = el.getAttribute('trs_temp_id', 2);
		if(fb.cfg && fb.cfg.id == eid 
			&& fb.style.display!='none')return;
		var p = ev.pointer;
		Position.clone(el, fb, {
			setWidth:   false,
			setHeight:  false,
			offsetTop:  2,
			offsetLeft: 2
		});
		var nElType = el.getAttribute('element-type', 2);
		fb.style.display = '';
		fb.cfg = {
			id : eid,
			elType : nElType,
			el : el
		};
	},
	openeditor : function(ev, el){
		if(!parent.InfoDocHelper)return;
		var xp = el.getAttribute('trs_temp_id', 2);
		var myEl = $(xp);
		if(!myEl)return;
		if(myEl.tagName=='IFRAME'){
			html = myEl.contentWindow.document.body.innerHTML;
		}
		else html = myEl.innerHTML;
		parent.InfoDocHelper.openeditor({
			html : html,
			callback : function(a, b){
				var myEl = $(xp);
				if(myEl.tagName=='IFRAME'){
					myEl.contentWindow.document.body.innerHTML = a;
				}
				else myEl.innerHTML = a;
			}
		});
	},
	openFileOrAppend : function(exEv, el){
		if(!el) return;
		var readonly = el.parentNode.getAttribute('trs_readonly_field', 2)=='1' || el.parentNode.getAttribute('trs_backreadonly_field', 2)=='1'; 
		readonly = readonly || el.getAttribute('trs_readonly_field', 2)=='1' || el.getAttribute('trs_backreadonly_field', 2)=='1';
		var xct = el.getAttribute("xd:xctname", 2);
		if(!xct)el = el.parentNode;
		var file = EleHelper.getFile(el, true);
		if(file!=null && file!=''){
			window.open(m_readFileUrl + file.FileName + '&DownName=' + encodeURIComponent(file.FileDesc));
			return;
		}
		else{
			if(!readonly){
				PgC.appendFileForEl(el);
			}
		}
	}
};
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
		var v = nd.getAttribute('default_value', 2);
		if(parent.m_FieldHelper.getDefaultValue)
			v = parent.m_FieldHelper.getDefaultValue(v, nd);
		if(!v)return;
		EleHelper.setValue(nd, v);
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
			this.pushToMustDoCaches([nd, sp, os]);
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
	},
	appendExTool : function(nd, sp, os){
		os = Object.extend({
			exTool : 1
		}, os);
		this.pushToMustDoCaches([nd, sp, os]);
		sp.className = 'ex-tool';
		sp.setAttribute('_fxType', 'ex-tool');
		document.body.appendChild(sp);
		Position.clone(nd, sp, {
			setWidth : false,
			setHeight : false,
			offsetLeft : -15,
			offsetTop : 5
		});
		for(var n in os){
			sp.style[n] = os[n];
		}
	}
};
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
	tg.className = src.className;
	tg.style.height = src.offsetHeight + 'px';
	tg.style.width = src.offsetWidth + 'px';
	if(Ext.isIE) return;
	//if(tg.tagName == 'TEXTAREA' || tg.TagName == 'INPUT')
	//tg.style.width = src.offsetWidth + 25 + 'px';
}
function disableInput(rp){
	rp.readOnly = true;
	rp.disabled = true;
	rp.style.backgroundColor = '#ECECEC';
}
function getRepeatTableParentNode(currNode){
	var currNodeId = currNode.id;
	var parentNodeId = "";
	var currNodeIds = currNodeId.split('/');
	for(var k=0; k<currNodeIds.length-1; k++){
		parentNodeId +=currNodeIds[k] + '/';
	}
	parentNodeId = parentNodeId.substr(0, parentNodeId.length-1);
	if($(parentNodeId))
		return $(parentNodeId);
	return null;
}
function isParentNodeReadOnly(nd){
	if(nd.id.indexOf('/')>0){
		if(parentNode==null) return false;
		var parentNode = getRepeatTableParentNode(nd); 
		var readonly = parentNode.getAttribute('trs_readonly_field', 2)=='1' || parentNode.getAttribute('trs_backreadonly_field', 2)=='1';
		return readonly;
	}
	return false;
}
function setFieldShowAble(el){
	var sBackshowValue = el.getAttribute("trs_backshow_field");
	if(sBackshowValue){ //为了兼容TRSWCMV65 build1096之前的历史数据，添加的条件判断
		if(sBackshowValue != "1"){ //假如trs_backshow_field属性的值不为1的话，就进行隐藏
			Element.hide(el);
			return false;
		}
	}
	return true;
}
_TransRule_.save("span", "PlainText", function(nd, only) {
	var nHeight = parseInt(nd.style.height, 10);
	if(isNaN(nHeight))nHeight = 0;
	var isTextArea = nHeight > 30;
	nd.setAttribute('infodoc_data', 1);
	nd.setAttribute("element-type", isTextArea?2:1);
	var rp = nd;
	if(isTextArea) {
		rp = document.createElement("textarea");
		rp.style.overflow = "auto";
	} else {
		rp = document.createElement("input");
	}
	copyAttrs(nd, rp);
	if(nd.innerHTML!=""){
		rp.value = nd.innerHTML;
	}
	if(isTextArea) {
		rp.style.whiteSpace="pre-wrap";
	}
	nd.parentNode.insertBefore(rp, nd.nextSibling);
	nd.parentNode.removeChild(nd);
	if(!setFieldShowAble(rp)){
		return rp;
	}
	if(only){
		disableInput(rp);
		return rp;
	}
	var ParentNodeReadOnly = isParentNodeReadOnly(nd);
	if(ParentNodeReadOnly){
		disableInput(rp);
		return rp;
	}
	_TransRule_.NotifyMustFill(rp, {marginLeft:'-15px'});
	return rp;
});
_TransRule_.save("span", "RichText", function(nd, only) {
	nd.style.overflow = "auto";
	nd.setAttribute('infodoc_data', 1);
	nd.setAttribute("element-type", "7");
	if(!isGecko){
		nd.contentEditable = !only;
	}else{
		var rp = document.createElement("iframe");
		rp.src = '';
		copyAttrs(nd, rp);
		nd.parentNode.insertBefore(rp, nd.nextSibling);
		nd.parentNode.removeChild(nd);
		var doc = rp.contentWindow.document;
		doc.write('<html><head></head><body style="font-size:14px;font-family:宋体,Arial;padding:0;margin:0;"></body></html>');
		doc.close();
		doc.designMode = only ? 'off' : 'on';
		nd = rp;
	}
	if(nd.innerHTML!=""){
		nd.value = nd.innerHTML;
	}
	if(!setFieldShowAble(nd)){
		return nd;
	}
	if(only)return nd;
	var ParentNodeReadOnly = isParentNodeReadOnly(nd);
	if(ParentNodeReadOnly){
		 return nd;
	}
	makeTextEditorRule(nd);
	_TransRule_.NotifyMustFill(nd, {marginLeft:'-15px'});
	return nd;
});
function makeTextEditorRule(currNode){
	var img = document.createElement("img");
	img.src = m_sRootPath + "editor.gif";
	img.title = wcm.LANG['INFOVIEW_DOC_92'] || '点击编辑格式文本';
	img.style.width = "16px";
	img.style.height = "16px";
	img.setAttribute('_action', 'openeditor');
	//img.setAttribute('trs_temp_id', nd.getAttribute("trs_temp_id", "2"));
	img.setAttribute('trs_temp_id', currNode.id);
	_TransRule_.appendExTool(currNode, img, {marginLeft:'-5px'});
}
_TransRule_.save("select", "DropDown", function(nd, only) {
	nd.setAttribute('infodoc_data', 1);
	nd.setAttribute("element-type", "8");
	var ParentNodeReadOnly = isParentNodeReadOnly(nd);
	if(!setFieldShowAble(nd)){
		return nd;
	}
	nd.disabled = only || ParentNodeReadOnly;
	_TransRule_.NotifyMustFill(nd);
});
_TransRule_.save("select", "ListBox", function(nd, only) {
	nd.setAttribute('infodoc_data', 1);
	nd.setAttribute("element-type", "8");
	if(!setFieldShowAble(nd)){
		return nd;
	}
	var ParentNodeReadOnly = isParentNodeReadOnly(nd); 
	nd.disabled = only || ParentNodeReadOnly;
	_TransRule_.NotifyMustFill(nd);
});
_TransRule_.save("input", "CheckBox", function(nd, only) {
	nd.setAttribute('infodoc_data', 1);
	nd.setAttribute("element-type", "9");
	if(!setFieldShowAble(nd)){
		return nd;
	}
	var ParentNodeReadOnly = isParentNodeReadOnly(nd);
	nd.disabled = only || ParentNodeReadOnly;
});
_TransRule_.save("input", "OptionButton", function(nd, only) {
	nd.setAttribute('infodoc_data', 1);
	nd.setAttribute("element-type", "10");
	if(!setFieldShowAble(nd)){
		return nd;
	}
	var ParentNodeReadOnly = isParentNodeReadOnly(nd);
	nd.disabled = only || ParentNodeReadOnly;
});
_TransRule_.save("div", "Section", function(nd, only) {
	nd.setAttribute("element-type", "13");
});
_TransRule_.save("div", "RepeatingSection", function(nd, only) {
	nd.setAttribute("element-type", "3");
	nd.setAttribute("fly-button", 1);
	nd.setAttribute("context-menu", 1);
	var ParentNodeReadOnly = isParentNodeReadOnly(nd);
	if(only || ParentNodeReadOnly)return;
	nd.style.border = '1px dotted gray';
});
_TransRule_.save("tbody", "RepeatingTable", function(nd, only) {
	nd.setAttribute("element-type", "4");
	nd.setAttribute("fly-button", 1);
	nd.setAttribute("context-menu", 1);
	//var ParentNodeReadOnly = isParentNodeReadOnly(nd);
	//兼容多浏览器，给重复表的父元素添加样式
	if(nd.parentNode.tagName.toUpperCase() == 'TABLE'){
		var repeatingTableContainer = nd.parentNode.parentNode;
		if(repeatingTableContainer.tagName.toUpperCase() == 'DIV')
			repeatingTableContainer.className = 'xdRepeatingTableContainer';
	}
	
	if(only)return;
	nd.style.border = '1px dotted gray';
});
_TransRule_.save("span", "ListItem_Plain", function(nd, only) {
	nd.setAttribute("element-type", "1");
	nd.setAttribute('infodoc_data', 1);
	var rp = document.createElement("input");
	copyAttrs(nd, rp);
	nd.parentNode.insertBefore(rp, nd.nextSibling);
	nd.parentNode.removeChild(nd);
	if(!setFieldShowAble(rp)){
		return rp;
	}
	var ParentNodeReadOnly = isParentNodeReadOnly(nd);
	if(only || ParentNodeReadOnly){
		disableInput(rp);
		return rp;
	}
	_TransRule_.NotifyMustFill(rp);
	return rp;
});
_TransRule_.save("span", "DTPicker_DTText", function(nd, only) {
	nd.setAttribute('infodoc_data', 1);
	nd.setAttribute("element-type", "11");
	var rp = document.createElement("input");
	copyAttrs(nd, rp);
	rp.style.border = 0;
	//日期控件多浏览器的兼容，减掉的宽度为日期按钮占的区域
	rp.style.width = (nd.parentNode.offsetWidth - 37) + 'px';	
	rp.style.height = (nd.parentNode.offsetHeight - 4) + 'px';
	//日期控件的父容器指定100%会溢出
	if(nd.parentNode.style.width == "100%")
		nd.parentNode.style.width = (nd.parentNode.offsetWidth - 5)+ "px";
	nd.parentNode.insertBefore(rp, nd.nextSibling);
	nd.parentNode.removeChild(nd);
	if(!setFieldShowAble(rp)){
		return rp;
	}
	var img = rp.parentNode.getElementsByTagName("BUTTON")[0];
	var ParentNodeReadOnly = isParentNodeReadOnly(nd);
	if(only || ParentNodeReadOnly){
		disableInput(rp);
		img.disabled = true;
		img.style.filter = 'gray';
		return rp;
	}
	makeDateCalendarRule(rp);
	if(nd.innerHTML != ""){
		if(nd.innerHTML.indexOf('T') > 0)
			rp.value = nd.innerHTML.replace('T', ' ');
		else rp.value = nd.innerHTML;
	}
	_TransRule_.NotifyMustFill(rp, {marginLeft:'-13px'});
	return rp;

});
function makeDateCalendarRule(el){
	var img = el.parentNode.getElementsByTagName("BUTTON")[0];
	img.setAttribute("type", 'button');
	var df = el.getAttribute("xd:datafmt", 2);
	var time = df != null && df.indexOf("datetime") != -1;
	var dtfm = null;
	if(time){
		img.unselectable = 'on';
		var validation = el.getAttribute("validation", 2);
		if(!validation)dtfm = 'yyyy-mm-dd HH:MM:ss';
		else{
			eval("var obj = {" + validation + "}");
			dtfm = obj['date_format'];
			if(dtfm==undefined)
			dtfm = 'yyyy-mm-dd HH:MM:ss';
		}
	}else{
		var validation = el.getAttribute("validation", 2);
		if(!validation)dtfm = 'yyyy-mm-dd';
		else{
			eval("var obj = {" + validation + "}");
			dtfm = obj['date_format'];
			if(dtfm==undefined)
			dtfm = 'yyyy-mm-dd';
		}
	}
	wcm.TRSCalendar.get({
		input : el,
		handler : img,
		dtFmt : dtfm,
		withtime : time
	});
}
_TransRule_.save("span", "FileAttachment", function(nd, only) {
	nd.setAttribute("element-type", "5");
	var ParentNodeReadOnly = isParentNodeReadOnly(nd);
	if(!only && !ParentNodeReadOnly){
		nd.setAttribute("fly-button", 1);
		nd.setAttribute("context-menu", 1);
		nd.style.cursor = "pointer";
	}
	nd.setAttribute('_action', "openFileOrAppend");
	nd.setAttribute('infodoc_data', 1);
	var img = document.createElement("img");
	img.src = m_sRootPath + "FileAttachment.gif";
	img.style.width = "14px";
	img.style.height = "13px";
	img.align = 'absmiddle';
	nd.appendChild(img);
	var txt = document.createElement("span");
	txt.setAttribute("text_body", "1");
	txt.className = 'appendix_txt';
	txt.innerHTML = wcm.LANG['INFOVIEW_DOC_54'] || "点击此处以添加附件";
	nd.appendChild(txt);
	var txtFile = document.createElement("span");
	txtFile.setAttribute("text_file", "1");
	txtFile.style.display = 'none';
	txtFile.innerHTML = "";
	var validation = nd.getAttribute("validation", 2);
	if(validation){
		eval("var obj = {" + validation + "}");
		var validType = obj['extvalid'];
		if(validType)
			nd.setAttribute("allow_ext", validType);
	}
	nd.appendChild(txtFile);
	_TransRule_.NotifyMustFill(nd, {marginLeft:'-15px', marginTop:'7px'});
});
var GetImageAttachmentRule = function(xct){
	return function(nd, only) {
		var sp = document.createElement('SPAN');
		sp.setAttribute("element-type", "5");
		var ParentNodeReadOnly = isParentNodeReadOnly(nd);
		if(!only && !ParentNodeReadOnly){
			nd.setAttribute("fly-button", 1);
			nd.setAttribute("context-menu", 1);
			nd.style.cursor = "pointer";
		}
		nd.setAttribute('_action', "openFileOrAppend");
		sp.setAttribute('infodoc_data', 1);
		sp.setAttribute("is-image", 1);
		nd.setAttribute('trs_file_upload_type', "InlineImage");
		if(nd.getAttribute('allow_ext', 2) == null)
		var validation = nd.getAttribute("validation", 2);
		if(validation){
			eval("var obj = {" + validation + "}");
			var validType = obj['extvalid'];
			if(validType){
				nd.setAttribute("allow_ext", validType);
			}else {
				nd.setAttribute('allow_ext', "jpg,png,gif,bmp,jpeg");
			}
		}
		copyAttrs(nd, sp);
		//sp.style.cursor = "pointer";
		nd.parentNode.insertBefore(sp, nd.nextSibling);
		var w = nd.offsetWidth, h = nd.offsetHeight;
		nd.parentNode.removeChild(nd);
		var img = document.createElement('IMG');
		img.setAttribute("image_body", "1");
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
		return sp;
	};
}
var _genId = 0;
function genId(){
	return ++_genId;
}
_TransRule_.save("img", "InlineImage", GetImageAttachmentRule('trs_is_inline_image'));
_TransRule_.save("img", "LinkedImage", GetImageAttachmentRule('trs_is_linked_image'));
_TransRule_.save("span", "ExpressionBox", function(nd, only) {
	nd.setAttribute("element-type", "101");
	nd.setAttribute('infodoc_data', 1);
	var rp = document.createElement("input");
	rp.id = rp.id || 'trs-exp-' + genId();
	rp.setAttribute('trs_temp_id', rp.id);
	copyAttrs(nd, rp);
	nd.parentNode.insertBefore(rp, nd.nextSibling);
	nd.parentNode.removeChild(nd);
	only = only || nd.getAttribute("xd:disableEditing") == "yes";
	if(only){
		disableInput(rp);
		return rp;
	}
	return rp;
});
Event.observe(window, 'resize', function(){
	rePraintRxTool();
});
function rePraintRxTool(){
	var caches = _TransRule_.getMustDoCaches();
	for(var i=0, n=caches.length; i<n; i++){
		var sp = caches[i][1], os = caches[i][2] || {}, nd = caches[i][0];
		Position.clone(nd, sp, {
			setWidth : false,
			setHeight : false,
			offsetLeft : os.exTool ? -15 : nd.offsetWidth,
			offsetTop : 5
		});
		for(var nm in os){
			if(nm=='exTool')continue;
			sp.style[nm] = os[nm];
		}
	}
}
Event.observe(window, 'unload', function(){
	_TransRule_.removeMustDoCaches();
});
function bodyFxEvents(){
	//bubble-panel
	var divBp = document.createElement('DIV');
	divBp.id = 'bubble-panel';
	divBp.setAttribute('_fxType', 'bubble-panel');
	divBp.style.display = 'none';
	divBp.style.position = "absolute";
	divBp.style.zIndex = 999;
	document.body.appendChild(divBp);
	regMenuItemEvent('bubble-panel');
	//fly-button
	var flyBtn = document.createElement('DIV');
	flyBtn.style.display = 'none';
	flyBtn.id = 'fly-button';
	flyBtn.setAttribute('_fxType', 'fly-button');
	flyBtn.style.zIndex = 99;
	flyBtn.style.cursor = 'pointer';
	flyBtn.style.position = "absolute";
	flyBtn.style.border = 0;
	flyBtn.setAttribute("xd:xctname", "FlyButton");
	flyBtn.innerHTML = "<img src='" + m_sRootPath + "DownButton.gif' border='0'>";
	document.body.appendChild(flyBtn);
	var bubblePanel = new wcm.BubblePanel('bubble-panel');
	function showContextMenu(ev, tg){
		var fb = $('fly-button');
		if(!fb.cfg)return true;
		var items = PgC.flys(fb.cfg.elType, fb.cfg.el);
		if(!items)return true;
		$('bubble-panel').innerHTML = PgC.buildMenu(items, fb.cfg.el);
		bubblePanel.bubble(ev.pointer, function(p){
			return [p[0], p[1]+5];
		});
		ev.stop();
		return false;
	}
	Ext.get(flyBtn).on('click', showContextMenu);
	var lastMsm = null;
	function mouseoverFx(ev, tg){
		if(!tg.tagName)return;
		var temp = findItem(tg, false, '_fxType');
		if(temp!=null)return;
		tg = findItem(tg, false, 'trs_temp_id');
		if(lastMsm!=null && lastMsm.el!=tg){
			PgC.borderFx(lastMsm.el, true);
			PgC.flyBtnFx(null, lastMsm.el, true);
			lastMsm = null;
		}
		if(tg==null)return;
		var nElType = tg.getAttribute('element-type', 2);
		PgC.borderFx(tg);
		PgC.flyBtnFx(ev, tg);
		lastMsm = {ev:ev.browserEvent, el:tg};
		return true;
	}
	var extBody = Ext.get(document.body);
	extBody.on('contextmenu', function(ev, tg){
		var el = findItem(tg, false, 'context-menu');
		if(!el)return true;
		mouseoverFx(ev, tg);
		var eid = el.getAttribute('trs_temp_id', 2);
		var nElType = el.getAttribute('element-type', 2);
		var items = PgC.flys(nElType, el);
		if(!items)return true;
		$('bubble-panel').innerHTML = PgC.buildMenu(items, el);
		bubblePanel.bubble(ev.pointer, function(p){
			return [p[0], p[1]+5];
		});
		ev.stop();
		return false;
	});
	extBody.on('mousemove', function(ev, tg){
		if(Element.visible('bubble-panel'))return;
		mouseoverFx(ev, tg);
	});
}
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
function regMenuItemEvent(id){
	var lstMenuItem = null;
	Ext.get(id).on('mousemove', function(exEv, tg){
		var menuitem = findItem(tg, 'menuitem');
		if(!menuitem)return;
		if(lstMenuItem){
			Element.removeClassName(lstMenuItem, 'menuitem_active');
		}
		Element.addClassName(menuitem, 'menuitem_active');
		lstMenuItem = menuitem;
	});
	Ext.get(id).on('mouseout', function(exEv, tg){
		if(lstMenuItem){
			Element.removeClassName(lstMenuItem, 'menuitem_active');
		}
		lstMenuItem = null;
	});
}
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
window.bShowTitle = true;
Event.observe(window, 'load', function(){
	var eles = document.body.getElementsByTagName("*");
	for(var i=0; i<eles.length; i++) {
		var el = eles[i];
		var readonly = el.getAttribute('trs_readonly_field', 2)=='1' || el.getAttribute('trs_backreadonly_field', 2)=='1';
		_TransRule_.doTrans(el, readonly);
	}
	exCenter._afterTrans();
	bodyFxEvents();
	regClickEvent(document.body);
	if(parent.$ && parent.$('frmAction')){
		XmlDataHelper.initData(parent.$('frmAction').DataXML.value);
	}
	window.__loaded = true;
	exCenter._afterInitData();
	rePraintRxTool();
});
/*
window.onValidError = function(json){
	if(window.customValidError){
		window.customValidError(json);
		return;
	}
	var infos = json.infos;
	if(infos.length>0){
		alert(infos[0].warning);
		$(infos[0].id).focus();
	}
}
*/
/*
window.onValid = function(){
	var el = this;
	alert(el.name);
}
*/

