window._isIE = (navigator.appName == "Microsoft Internet Explorer");
if(window._isIE) {
	if(navigator.userAgent.indexOf("Opera")>-1) window._isIE = null;
	if(navigator.userAgent.indexOf("Windows NT 6.0")>-1) window._isVista = true;
	else window._isVista = false;
}
else {
	if(navigator.userAgent.indexOf("Gecko")==-1) window._isIE = null;
}
function $(_sId){
	return document.getElementById(_sId);
}

var EditorEvent=new Array();

/// Word////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
var editorUrl = "/wcm/app/neweditor"
EDiaryEditor = {
	initialize: function (sEditorID, sParentID, bWordMode, sContent) {
		this.initView(sParentID);
		this.initControl(bWordMode, sContent);
	},
	config: {node:{}, core:{}, frame:{}},
	getElement: function() {
		var _this = this;
		var element = null;
		var sel;
		var range;
		
		if(window._isIE) {
			sel = _this.iframe.contentWindow.document.selection;
			switch(sel.type.toLowerCase()) {
				case "none":
				case "text": {
					range = sel.createRange();
					element = range.parentElement();
					break;
				}
				case "control": {
					var ranges = sel.createRange();
					element = ranges.item(0);
					break;
				}
			}
		}
		else
		{
			sel = _this.iframe.contentWindow.getSelection();
			if(sel.rangeCount > 0)
			{
				range = sel.getRangeAt(0);
				if(range.startContainer == range.endContainer)
				{
					if(range.startContainer.nodeType != 3)
					{
						element = range.startContainer.childNodes[range.startOffset];
					}
					else element = range.startContainer;
				}
				else element = range.commonAncestorContainer;
			}
			if(element && element.nodeType == 3) element = element.parentNode;
		}
		
		return element;
	},
	initView: function (sParentID) {
		var _this = this;
		var _toolbarStr = "\
			<div id='EDiaryEditor'style='width: 495px; padding-left:2px;'>\
				<div id='EDiaryEditorToolBar' style='position: relative; width: 495px; height: 69px; background: url(images/editor/toolbar_bg.gif) -72px center;'>\
					<div id='EDiaryEditorFontFamilyMenu' title='字体' style='position: absolute; left: 8px; top: 12px; width: 92px; height: 18px; line-height: 20px; padding-left: 4px; cursor: pointer;' action='Family' unselectable='on' WCMAnt:param='editor.html.font' WCMAnt:paramattr='title:editor.html.font'>字体</div>\
					<div id='EDiaryEditorFontSizeMenu' title='字号' style='position: absolute; left: 105px; top: 12px; width: 66px; height: 18px; line-height: 20px; padding-left: 4px; cursor: pointer;' action='Size' unselectable='on' WCMAnt:param='editor.html.fontSize' WCMAnt:paramattr='title:editor.html.fontSize'>字号</div>\
					<div id='EDiaryEditorFontStyleMenu' title='段落' style='position: absolute; left: 173px; top: 12px; width: 66px; height: 18px; line-height: 20px; padding-left: 4px; cursor: pointer;' action='Style' unselectable='on' WCMAnt:param='editor.html.duanLuo' WCMAnt:paramattr='title:editor.html.duanLuo'>段落</div>\
				</div>\
				<div style='height: 200px; width: 582px!important ; width: 495px;' id='EDiaryEditorContent'>\
					<iframe id='editor_body_area' name='editor_body_area' style='width: 493px!important ; width: 493px; height: 200px; border:1px solid #abc0db; overflow-x: atuo; display: nones;' frameborder='0'></iframe>\
					<textarea id='editor_body_textarea' name='editor_body' style='width: 493px; height: 202px; border-width: 0px; padding-top:8px; display: none;'></textarea>\
					<input type='hidden' name='char_count' value='-1' id='EDiaryEditorStrLen'>\
				</div>\
				<div style='width: 495px; height: 1px;display:none'>\
					<div style='float: left; padding-top: 18px; margin-left: 18px; margin-top: 16px;'><input type='checkbox' id='ModeCheck'><label for='ModeCheck' WCMAnt:param='editor.html.displayCode'>显示源代码</label></div>\
				</div>\
			</div>\
		";
		this.addHTML($(sParentID), _toolbarStr);

		var _toolBarInfo = [
			//{l: 11, t: 12, w: 35, h: 51, a: "粘贴", n: "paste"},
			//{l: 46, t: 12, w: 21, h: 24, a: "剪切", n: "cut"},
			//{l: 46, t: 36, w: 21, h: 27, a: "复制", n: "copy"},
			{l: 77, t: 41, w: 23, h: 23, a: FCKLang.TEXT2, n: "undo"},
			{l: 102, t: 41, w: 23, h: 23, a: FCKLang.TEXT3, n: "redo"},
			{l: 133, t: 41, w: 23, h: 23, a: FCKLang.TEXT4, n: "bold"},
			{l: 158, t: 41, w: 24, h: 23, a: FCKLang.TEXT5, n: "italic"},
			{l: 184, t: 41, w: 24, h: 23, a: FCKLang.TEXT6, n: "underline"},
			{l: 210, t: 41, w: 24, h: 23, a: FCKLang.TEXT7, n: "forecolor", c: "FColor"},
			{l: 236, t: 41, w: 24, h: 23, a: FCKLang.TEXT8, n: "hilitecolor", c: "HColor"},
			{l: 262, t: 41, w: 24, h: 23, a: FCKLang.TEXT9, n: "inserthorizontalrule"},
			{l: 288, t: 41, w: 24, h: 23, a: FCKLang.TEXT10, n: "face", c: "FaceBG"},
			{l: 320, t: 41, w: 23, h: 23, a: FCKLang.TEXT11, n: "justifyleft"},
			{l: 345, t: 41, w: 24, h: 23, a: FCKLang.TEXT12, n: "justifycenter"},
			{l: 371, t: 41, w: 24, h: 23, a: FCKLang.TEXT13, n: "justifyright"},
			{l: 397, t: 41, w: 24, h: 23, a: FCKLang.TEXT14, n: "justifyfull"},
			{l: 429, t: 41, w: 59, h: 23, a: FCKLang.TEXT15, n: "justifyformat", c: "Justify"},
			{l: 320, t: 10, w: 23, h: 23, a: FCKLang.TEXT16, n: "insertorderedlist"},
			{l: 345, t: 10, w: 24, h: 23, a: FCKLang.TEXT17, n: "insertunorderedlist"},
			{l: 371, t: 10, w: 24, h: 23, a: FCKLang.TEXT18, n: "outdent"},
			{l: 397, t: 10, w: 24, h: 23, a: FCKLang.TEXT19, n: "indent"},
			{l: 429, t: 10, w: 23, h: 23, a: FCKLang.TEXT20, n: "img"},
			{l: 454, t: 10, w: 24, h: 23, a: FCKLang.TEXT21, n: "link"},
			{l: 480, t: 10, w: 24, h: 23, a: FCKLang.TEXT22, n: "table"},
			//{l: 531, t: 10, w: 24, h: 23, a: FCKLang.TEXT23, n: "localeimg"},
			{l: 506, t: 10, w: 23, h: 23, a: FCKLang.TEXT24, n: "textarea"},
			{l: 496, t: 41, w: 59, h: 23, a: FCKLang.TEXT25, n: "view"},
			//{l: 563, t: 10, w: 48, h: 54, a: "段落化", n: "ParaFormatting"},
		];
		if(window.bFromInternet){
			 var _toolBarInfo1 = _toolBarInfo.slice(0, 18);
			_toolBarInfo = _toolBarInfo1.concat(_toolBarInfo.slice(19));
		}
		_this.config.toolbarinfo = _toolBarInfo;
		var _toolbarStr2 = new String;
		for(var key in _toolBarInfo) {
			_toolbarStr2 += "<img src='images/editor/blank.gif' title='" + _toolBarInfo[key]["a"] + "' alt='" + _toolBarInfo[key]["a"] + "' id='EDiaryEditor" + _toolBarInfo[key]["n"] + "' style='position: absolute; left: " + (_toolBarInfo[key]["l"]-72) + "px; top: " + _toolBarInfo[key]["t"] + "px; width: " + _toolBarInfo[key]["w"] + "px; height: " + _toolBarInfo[key]["h"] + "px;' func='" + _toolBarInfo[key]["n"] + "' action='" + _toolBarInfo[key]["c"] + "'>";
		}
		this.addHTML($("EDiaryEditorToolBar"), _toolbarStr2);
		
		var _toolMenuInfo = {
			Family: [
				{t: FCKLang.TEXT26},
				{t: FCKLang.TEXT27},
				{t: FCKLang.TEXT28},
				{t: FCKLang.TEXT29},
				{t: FCKLang.TEXT30},
				{t: "Times New Roman"},
				{t: "Arial"},
				//{t: "Impact"},
				//{t: "Georgia"},
				//{t: "Verdana"},
				{t: "Courier New"}				
			],
			Size: [
		    	//{t: "10px",n:"(六号)"},
				//{t: "12px",n:"(小五)"},
				{t: "14px",n:FCKLang.TEXT31},
				{t: "16px",n:FCKLang.TEXT32},
				{t: "18px",n:""},
				{t: "20px",n:FCKLang.TEXT33},
				{t: "22px",n:""},
				{t: "24px",n:FCKLang.TEXT34},
				{t: "32px",n:FCKLang.TEXT35}
				//{t: "56px",n:"(初号)"}
			],
			Style: [
                {t: "div",n:FCKLang.TEXT36, s: "14"},     
                {t: "H1",n:FCKLang.TEXT37, s: "32"},
				{t: "H2",n:FCKLang.TEXT38, s: "24"},
				{t: "H3",n:FCKLang.TEXT39, s: "18"},
				{t: "H4",n:FCKLang.TEXT40, s: "16"}
				//{t: "H5",n:"段落5", s: "12"},
				//{t: "H6",n:"段落6", s: "10"}
			]
		};

		var _toolbarStr3 = new String;
		var node;
		for(key in _toolMenuInfo) {
			for(var key2 in _toolMenuInfo[key]) {
				node = _toolMenuInfo[key][key2];
				if(key == "Family") {
					key2 = parseInt(key2);
					if(key2 == 0) {// head
						_toolbarStr3 += "\
							<!-- Font Family Menu -->\
							<div style='cursor: default; position: absolute; width: 163px; top: 32px; left: 6px; display: none;' id='EDiaryEditorFontFamilyItem'>\
								<div style='height: 205px !important; background-image: url(images/editor/menu_bg.gif);' id='EDiaryEditorFontFamilyBox'>\
						";
					}
					if(key2 < _toolMenuInfo[key].length) {// body
						_toolbarStr3 += "\
										<div style='text-align: center; background: #f4f4f4; border: 1px solid #ccc; margin: 2px; padding: 4px; width: 149px!important ; width: 158px; font-family: " + node["t"] + "' unselectable='on' action='family' value='" + node["t"] + "'>" + node["t"] + "</div>\
						";
					}
					if(key2 + 1 == _toolMenuInfo[key].length) {// foot
						_toolbarStr3 += "\
									</div>\
								<div style='height: 10px; background-image: url(images/editor/menu_foot.gif); font-size: 1px;'></div>\
							</div>\
						";
					}
				}
				if(key == "Size") {
					key2 = parseInt(key2);
					if(key2 == 0) {// head
						_toolbarStr3 += "\
							<!-- Font Size Menu -->\
							<div style='cursor: default; position: absolute; width: 163px; top: 32px; left: 103px; display: none;' id='EDiaryEditorFontSizeItem'>\
								<div style='height: 254px!important ; background-image: url(images/editor/menu_bg.gif);' id='EDiaryEditorFontSizeBox'>\
						";
					}
					if(key2 < _toolMenuInfo[key].length) {// body
						_toolbarStr3 += "<div style='text-align: center; background: #f4f4f4; border: 1px solid #ccc; margin: 2px; padding: 4px; width: 149px!important ; width: 158px; font-size: " + node["t"] + ";' unselectable='on' action='size' value='" + node["t"] + "'>" + node["t"] + "<span unselectable='on' style='font-size:12px'>"+node["n"]+"</span></div>";
					}
					if(key2 + 1 == _toolMenuInfo[key].length) {// foot
						_toolbarStr3 += "\
									</div>\
								<div style='height: 10px; background-image: url(images/editor/menu_foot.gif); font-size: 1px;'></div>\
							</div>\
						";
					}
				}
				if(key == "Style") {
					key2 = parseInt(key2);
					if(key2 == 0) {// head
						_toolbarStr3 += "\
							<!-- Font Style Menu -->\
							<div style='cursor: default; position: absolute; width: 163px; top: 32px; left: 171px;background:#fff; display: none;' id='EDiaryEditorFontStyleItem'>\
								<div style='text-align: center; height: 20px; background-image: url(images/editor/menu_title.gif); padding-top: 6px; padding-left: 6px;'><b WCMAnt:param='editor.html.duanLuo'>段落</b></div>\
									<div style='height: 179px!important ; background-image: url(images/editor/menu_bg.gif);margin-top:-3px\0\9' id='EDiaryEditorFontStyleBox'>\
						";
					}
					if(key2 < _toolMenuInfo[key].length) {// body
						_toolbarStr3 += "<div style='text-align: center; background: #f4f4f4; border: 1px solid #ccc; margin: 2px; padding: 4px; width: 149px!important ; width: 158px; font-size: " + node["s"] + "px;' unselectable='on' action='style' value='" + node["t"] + "'>" + node["n"] + "</div>";
					}
					if(key2 + 1 == _toolMenuInfo[key].length) {// foot
						_toolbarStr3 += "\
									</div>\
								<div style='height: 10px; background-image: url(images/editor/menu_foot.gif); font-size: 1px;'></div>\
							</div>\
						";
					}
				}
			}
		}
		this.addHTML($("EDiaryEditorToolBar"), _toolbarStr3);


		var _toolbarStr5 = "\
			<div style='position: absolute; background-image: url(images/editor/blank.gif); left: 0px; top: 0px; width: " + $("EDiaryEditor").offsetWidth + "px; height: " + $("EDiaryEditor").offsetHeight + "px; display: none;' id='EDiaryEditorDialog'>\
				<div style='position: absolute; background-image: url(images/editor/dilog_bg.gif); left: 160px; top: 140px; width: 310px; height: 157px; display: none;' id='EDiaryEditorIMGDialog'>\
				<iframe src='editor/img.html'></iframe>\
				</div>\
			</div>\
		";
		//this.addHTML($("EDiaryEditorToolBar"), _toolbarStr5);
		
		var _toolbarStr6 = "<div style='position: absolute; background: #fff; left: 162px; top: 66px; display: none;' id='EDiaryEditorforecolorItem'>";
		var k = 1;
		var c = ["","#FF9999","#FFFF80","#80FF80","#80FFFF","#0080FF","#FF80C0","#FF0000","#CCCC66","#00FF40","#0099CC","#9999CC","#FF00FF","#CC3333","#FF9933","#009999","#006699","#9999FF","#990033","#990000","#FF9900","#009900","#0000FF","#0000CC","#990099","#660000","#006666","#999900","#660099","#339999","#66CCCC","#000000","#494949","#767676","#A6A6A6","#C7C7C7","#FFFFFF"];
		for(var i = 0; i < 6; i ++) {
			for(var j = 0; j < 6; j++) {
				_toolbarStr6 += ("<img src='images/editor/blank.gif' onclick='EDiaryEditor.runCMD(\"forecolor\", \"" + c[k] + "\");' style='background: " + c[k] + ";border: 1px solid #999; margin: 0px; padding: 0px; margin-top: 1px; margin-left: 1px; width: 14px; height: 14px; cursor: hand; cursor: pointer;' />");
				k ++;
			}
			_toolbarStr6 += ('<br />');
		}
		_toolbarStr6 += "</div>";
		this.addHTML($("EDiaryEditorToolBar"), _toolbarStr6);

		var _toolbarStr7 = "<div style='position: absolute; background: #fff; left: 188px; top: 66px; display: none;' id='EDiaryEditorhilitecolorItem'>";
		var k = 1;
		var c = ["","#FF9999","#FFFF80","#80FF80","#80FFFF","#0080FF","#FF80C0","#FF0000","#CCCC66","#00FF40","#0099CC","#9999CC","#FF00FF","#CC3333","#FF9933","#009999","#006699","#9999FF","#990033","#990000","#FF9900","#009900","#0000FF","#0000CC","#990099","#660000","#006666","#999900","#660099","#339999","#66CCCC","#000000","#494949","#767676","#A6A6A6","#C7C7C7","#FFFFFF"];
		for(var i = 0; i < 6; i ++) {
			for(var j = 0; j < 6; j++) {
				_toolbarStr7 += ("<img src='images/editor/blank.gif' onclick='EDiaryEditor.runCMD(\"hilitecolor\", \"" + c[k] + "\");' style='background: " + c[k] + ";border: 1px solid #999; margin: 0px; padding: 0px; margin-top: 1px; margin-left: 1px; width: 14px; height: 14px; cursor: hand; cursor: pointer;' />");
				k ++;
			}
			_toolbarStr7 += ('<br />');
		}
		_toolbarStr7 += "</div>";
		this.addHTML($("EDiaryEditorToolBar"), _toolbarStr7);

		var _toolbarStr8 = "<div style='position: absolute; left: 218px; top: 66px; display: none;' id='EDiaryEditorfaceItem'>\
			<img border='0' src='images/editor/face_bg.gif' usemap='#Map' align='center'  style='cursor: hand; cursor: pointer;'/>\
			<map name='Map'>\
			  <area shape='rect' coords='246,83,272,107' onClick='EDiaryEditor.insertFace(\"040\");'>\
			  <area shape='rect' coords='219,83,245,107' onClick='EDiaryEditor.insertFace(\"039\");'>\
			  <area shape='rect' coords='192,83,218,107' onClick='EDiaryEditor.insertFace(\"038\");'>\
			  <area shape='rect' coords='165,83,191,107' onClick='EDiaryEditor.insertFace(\"037\");'>\
			  <area shape='rect' coords='138,83,164,107' onClick='EDiaryEditor.insertFace(\"036\");'>\
			  <area shape='rect' coords='111,83,137,107' onClick='EDiaryEditor.insertFace(\"035\");'>\
			  <area shape='rect' coords='84,83,110,107' onClick='EDiaryEditor.insertFace(\"034\");'>\
			  <area shape='rect' coords='57,83,83,107' onClick='EDiaryEditor.insertFace(\"033\");'>\
			  <area shape='rect' coords='30,83,56,107' onClick='EDiaryEditor.insertFace(\"032\");'>\
			  <area shape='rect' coords='3,83,29,107' onClick='EDiaryEditor.insertFace(\"031\");'>\
			  <area shape='rect' coords='246,56,272,80' onClick='EDiaryEditor.insertFace(\"030\");'>\
			  <area shape='rect' coords='219,56,245,80' onClick='EDiaryEditor.insertFace(\"029\");'>\
			  <area shape='rect' coords='192,56,218,80' onClick='EDiaryEditor.insertFace(\"028\");'>\
			  <area shape='rect' coords='165,56,191,80' onClick='EDiaryEditor.insertFace(\"027\");'>\
			  <area shape='rect' coords='138,56,164,80' onClick='EDiaryEditor.insertFace(\"026\");'>\
			  <area shape='rect' coords='111,56,137,80' onClick='EDiaryEditor.insertFace(\"025\");'>\
			  <area shape='rect' coords='84,56,110,80' onClick='EDiaryEditor.insertFace(\"024\");'>\
			  <area shape='rect' coords='57,56,83,80' onClick='EDiaryEditor.insertFace(\"023\");'>\
			  <area shape='rect' coords='30,56,56,80' onClick='EDiaryEditor.insertFace(\"022\");'>\
			  <area shape='rect' coords='3,56,29,80' onClick='EDiaryEditor.insertFace(\"021\");'>\
			  <area shape='rect' coords='246,30,272,54' onClick='EDiaryEditor.insertFace(\"020\");'>\
			  <area shape='rect' coords='219,30,245,54' onClick='EDiaryEditor.insertFace(\"019\");'>\
			  <area shape='rect' coords='192,30,218,54' onClick='EDiaryEditor.insertFace(\"018\");'>\
			  <area shape='rect' coords='165,30,191,54' onClick='EDiaryEditor.insertFace(\"017\");'>\
			  <area shape='rect' coords='138,30,164,54' onClick='EDiaryEditor.insertFace(\"016\");'>\
			  <area shape='rect' coords='111,30,137,54' onClick='EDiaryEditor.insertFace(\"015\");'>\
			  <area shape='rect' coords='84,30,110,54' onClick='EDiaryEditor.insertFace(\"014\");'>\
			  <area shape='rect' coords='57,30,83,54' onClick='EDiaryEditor.insertFace(\"013\");'>\
			  <area shape='rect' coords='30,30,56,54' onClick='EDiaryEditor.insertFace(\"012\");'>\
			  <area shape='rect' coords='3,30,29,54' onClick='EDiaryEditor.insertFace(\"011\");'>\
			  <area shape='rect' coords='246,4,272,28' onClick='EDiaryEditor.insertFace(\"010\");'>\
			  <area shape='rect' coords='219,4,245,28' onClick='EDiaryEditor.insertFace(\"009\");'>\
			  <area shape='rect' coords='192,4,218,28' onClick='EDiaryEditor.insertFace(\"008\");'>\
			  <area shape='rect' coords='165,4,191,28' onClick='EDiaryEditor.insertFace(\"007\");'>\
			  <area shape='rect' coords='138,4,164,28' onClick='EDiaryEditor.insertFace(\"006\");'>\
			  <area shape='rect' coords='111,4,137,28' onClick='EDiaryEditor.insertFace(\"005\");'>\
			  <area shape='rect' coords='84,4,110,28' onClick='EDiaryEditor.insertFace(\"004\");'>\
			  <area shape='rect' coords='57,4,83,28' onClick='EDiaryEditor.insertFace(\"003\");'>\
			  <area shape='rect' coords='30,4,56,28' onClick='EDiaryEditor.insertFace(\"002\");'>\
			  <area shape='rect' coords='3,4,29,28' onClick='EDiaryEditor.insertFace(\"001\");'>\
			</map>\
		</div>\
		";
		this.addHTML($("EDiaryEditorToolBar"), _toolbarStr8);

		var _toolbarStr9 = "<div style='position: absolute; left: 0px; top: 0px; width: 495px; background: url(images/editor/blank.gif); height: 69px; display: none;' id='EDiaryEditorToolBarMask'></div>";

		this.addHTML($("EDiaryEditorToolBar"), _toolbarStr9);	

		var _toolbarStr9 = "<div style='position: absolute; left: 483px; top: 41px; display: none;' id='EDiaryEditorRsave' title='恢复上一次未成功发表内容'><img src='images/editor/rsave.gif' csrc='/images/rsave.gif' bsrc='/images/rsave_over.gif' onmouseover='this.src=this.bsrc' onmouseout='this.src=this.csrc'></div>";
		this.addHTML($("EDiaryEditorToolBar"), _toolbarStr9);
		
		
		var _toolbarStr4 = "\
			<div style='position: absolute; width: 198px; height: 95px; top: 66px; left: 250px; display: none; background-image: url(images/editor/justifyformat_bg.gif);' id='EDiaryEditorJustifyFormatItem'>\
				<div style='float: left; margin-left: 12px; margin-top: 29px; width: 42px; height: 50px; cursor: pointer;' id='EDiaryEditorIMGLeft' unselectable='on'></div>\
				<div style='float: left; margin-left: 11px; margin-top: 29px; width: 42px; height: 50px; cursor: pointer;' id='EDiaryEditorIMGCenter' unselectable='on'></div>\
				<div style='float: left; margin-left: 24px; margin-top: 29px; width: 43px; height: 50px; cursor: pointer;' id='EDiaryEditorIMGRight' unselectable='on'></div>\
			</div>\
		";
		this.addHTML($("EDiaryEditorToolBar"), _toolbarStr4);

		var _toolBarHash = this.config.node;
		for(var key in _toolBarInfo) {
			_toolBarHash[_toolBarInfo[key]["n"]] = $("EDiaryEditor" + _toolBarInfo[key]["n"]);
		}
		this.iframe = $("editor_body_area");		
		if(window._isIE) {
		}else{
			this.delEditorRsave = this.EDiaryEditorRsave = function(){}
			}
	},
	initControl: function (bWordMode, sContent) {
		var _this = this;
		
		var FontMenuFuncHash = {
			menulist: {
				Family: {obj: $("EDiaryEditorFontFamilyItem")},
				Size: {obj: $("EDiaryEditorFontSizeItem")},
				Style: {obj: $("EDiaryEditorFontStyleItem")},
				Justify: {obj: $("EDiaryEditorJustifyFormatItem")},
				FColor: {obj: $("EDiaryEditorforecolorItem")},
				HColor: {obj: $("EDiaryEditorhilitecolorItem")},
				FaceBG: {obj: $("EDiaryEditorfaceItem")}
			},
			click: function (el, _tthis) {
				var node;
				var event = window.event || el;
				var esrc = event.srcElement || event.target;
				for(var key in _tthis.menulist) {
					node = _tthis.menulist[key];
					if(esrc.getAttribute("action") == key) {
						if(key == "Justify") {
							if(!window._isIE) {
								var oSelection = _this.iframe.contentWindow.getSelection();
							}
							else {
								var oSelection = _this.iframe.contentWindow.document.selection.createRange();
							}
							var sRangeType = _this.getElement();
							if (sRangeType.tagName.toLowerCase() != "img"){
								alert(FCKLang.TEXT41)
							return;
							}
						}
						if(node["obj"].style.display == "") {
							node["obj"].style.display = "none";
						}
						else {
							node["obj"].style.display = "";
						}
					}
					else {
						node["obj"].style.display = "none";
					}
				}
			},
			hide: function (event, _this) {
				var node;
				for(var key in _this.menulist) {
					node = _this.menulist[key];
					node["obj"].style.display = "none";
				};
			}
		};
		/////////////////////////////////////////////////////////////////////////

		function hiddenDialog(event, Act) {
			if(Act == "img") {
				//$("EDiaryEditorIMGDialog").style.display = "none";
				//$("EDiaryEditorDialog").style.display = "none";
			}
		}
		function getWindowTopWidth(){
			var nWidth	= document.body.clientWidth;
			var nHeight = document.body.clientHeight;
			var nLeft	= (window.screen.availWidth - nWidth) / 2;
			var nTop	= (window.screen.availHeight - nHeight)/2;
			return [nLeft, nTop];
		}
		function viewDialog(el, Act) {
			var event = window.event || el;
			var win;
			if(window._isIE) {
				/*
				if(Act == "img") {
					window.showModalDialog("editor/img.htm?version=200712142", window, "dialogWidth: 400px; dialogHeight: 200px; help: no; scroll: no; status: no");
				}*/
				if(Act == "link") {
					window.showModalDialog("editor/link.htm", window, "dialogWidth: 400px; dialogHeight: 200px; help: no; scroll: no; status: no");
				}
				if(Act == "table") {
					window.showModalDialog("editor/table.htm", window, "dialogWidth: 400px; dialogHeight: 200px; help: no; scroll: no; status: no");
				}
				if(Act == "img") {
					window.showModalDialog("editor/localeimg.htm", window, "dialogWidth: 500px; dialogHeight: 300px; help: no; scroll: no; status: no");
				}
			}
			else {
				if(Act == "img") {
					var oPosition = getWindowTopWidth();
					win = window.open("editor/localeimg.htm?version=200907131", null,"resizable=yes,top="+oPosition[1] + ",left=" + oPosition[0] + ",width=500,Height=400");
					win.dialogArguments = window;
					if(win)win.focus();

				}
				if(Act == "link") {
					var oPosition = getWindowTopWidth();
					win = window.open("editor/link.htm", null, "resizable=yes,top="+oPosition[1] + ",left=" + oPosition[0] + ",width=400,Height=200");
					win.dialogArguments = window;
				}
				if(Act == "table") {
					var oPosition = getWindowTopWidth();
					win = window.open("editor/table.htm", null, "resizable=yes,top="+oPosition[1] + ",left=" + oPosition[0] + ",width=400,Height=200");
					win.dialogArguments = window;
				}
			}

		}
		function addIMG(event, Act) {
				var html = "<img src='" + $("EDiaryEditorIMGInput").value + "'>";
				if (_this.iframe.contentWindow.document.selection.type.toLowerCase() != "none"){
					_this.iframe.contentWindow.document.selection.clear() ;
				}
				_this.iframe.contentWindow.focus();
				var oRng = _this.iframe.contentWindow.document.selection.createRange();
				oRng.pasteHTML(html);
				oRng.collapse(false);
				oRng.select();
				hiddenDialog(event, Act);
		}

		////////////////////////////////////////////////////////////
		// 需要优化
        function eventObserver(){
            var es =_this.iframe.contentWindow.document.body.keyupEvents;
				/* 字体预设
                if(es.fontsize){
                    reaplceFontName("fontsize",es.fontsize)
                }
				*/
                if(es.fontname){
                    reaplceFontName("fontname",es.fontname)
                }
        }
        function formatFont(what, v){
            var idocument= _this.iframe.contentWindow.document;

            if(window._isIE) {
				if(v == (FCKLang.TEXT42) && !window._isVista) {
					v = FCKLang.TEXT43;
				}
                idocument.execCommand("fontname", "", "EDiaryEditor_Temp_FontName");
                 if(!idocument.body.keyupEvents)
                    idocument.body.keyupEvents=new Array();
                 if( idocument.selection.type!="Text"){
                     idocument.body.keyupEvents[what]=v;
                     idocument.body.onkeyup=eventObserver;
                 }else{
                     reaplceFontName(what,v);
                 }
            }
			else {
				switch(what){
					case "fontname":
						_this.iframe.contentWindow.document.execCommand("fontname", "", v);
						break;
					case "fontsize":
						v = parseInt(v) / 6;
						_this.iframe.contentWindow.document.execCommand("fontsize", "", v);
						break;
					default:
						break;
				}
			}
		}
        function reaplceFontName(what,v){
            var idocument=_this.iframe.contentWindow.document;
            var es=idocument.body.keyupEvents;
            var a_Font = idocument.body.getElementsByTagName("font");
                  for (var i = 0; i < a_Font.length; i++){
                        var o_Font = a_Font[i];
                        if (o_Font.getAttribute("face") == "EDiaryEditor_Temp_FontName"){
                             delInFont(o_Font, what);
                             setStyleValue(o_Font, what, v);
                            es[what]=null;
                            if(!es.fontsize && !es.fontname)
                                 o_Font.removeAttribute("face");
                       }
                 }
         }
        function delInFont(obj, what){
			var o_Children = obj.children;
			for (var j = 0; j < o_Children.length; j++){
				setStyleValue(o_Children[j], what, "");
				delInFont(o_Children[j], what);

				if (o_Children[j].style.cssText == ""){
					if ((o_Children[j].tagName == "FONT") || (o_Children[j].tagName == "SPAN")){
						o_Children[j].outerHTML = o_Children[j].innerHTML;
					}
				}
			}
		}
		function setStyleValue(obj, what, v){
			switch(what){
				case "fontname":
					obj.style.fontFamily = v;
					break;
				case "fontsize":
					obj.style.fontSize = v;
					break;
				default:
					break;
			}
		}
		////////////////////////////////////////////////////////
		function setMenuFunc(node) {
			node.onclick = function () {
				if(this.getAttribute("action") == "family") {
					formatFont("fontname", this.getAttribute("value"));
					$('EDiaryEditorFontFamilyMenu').innerHTML = this.getAttribute("value");
				}
				if(this.getAttribute("action") == "size") {
					formatFont("fontsize", this.getAttribute("value"));
					$('EDiaryEditorFontSizeMenu').innerHTML = this.getAttribute("value");
				}
				if(this.getAttribute("action") == "style") {
					_this.runCMD("formatblock", "<" + this.getAttribute("value") + ">");
					$('EDiaryEditorFontStyleMenu').innerHTML = this.getAttribute("value");
						
				}
			}
			node.onmouseover = function () {
				this.style.border = "solid #f90 1px";
				this.style.background = "#fff";
			}
			node.onmouseout = function () {
				this.style.border = "solid #ccc 1px";
				this.style.background = "#f4f4f4";
			}
		}
		var nDiv = $("EDiaryEditorFontFamilyBox").getElementsByTagName("div");
		for(var i = 0; i < nDiv.length; i ++) {
			setMenuFunc(nDiv[i]);
		}
		
		var nDiv = $("EDiaryEditorFontSizeBox").getElementsByTagName("div");
		for(var i = 0; i < nDiv.length; i ++) {
			setMenuFunc(nDiv[i]);
		}
		
		var nDiv = $("EDiaryEditorFontStyleBox").getElementsByTagName("div");
		for(var i = 0; i < nDiv.length; i ++) {
			setMenuFunc(nDiv[i]);
		}
		function formatIMG(el, Act) {
			var event = window.event || el;
			if(window.Event) {
				var oSelection = _this.iframe.contentWindow.getSelection();
			}
			else {
				var oSelection = _this.iframe.contentWindow.document.selection.createRange();
			}
			
			var sRangeType = _this.getElement();
			if (sRangeType.tagName.toLowerCase() == "img"){
				if(Act == "left") {
					sRangeType.style.margin = "4px";
					if(window._isIE){
						sRangeType.style.styleFloat = "left";
					}else{
						sRangeType.style.cssFloat = "left";
					}
					sRangeType.style.display = "";
					sRangeType.style.textAlign = "";
				}
				if(Act == "center") {
					sRangeType.style.margin = "4px auto";
					sRangeType.style.display = "block";
					if(window._isIE){
						sRangeType.style.styleFloat = "";
					}else{
						sRangeType.style.cssFloat = "";
					}
					sRangeType.style.textAlign = "center";
					sRangeType.setAttribute("align", "");
				
				}
				if(Act == "right") {
					sRangeType.style.margin = "4px";
					if(window._isIE){
						sRangeType.style.styleFloat = "right";
					}else{
						sRangeType.style.cssFloat = "right";
					}
					sRangeType.style.display = "";
					sRangeType.style.textAlign = "";
				}
				_this.iframe.contentWindow.document.body.innerHTML = _this.iframe.contentWindow.document.body.innerHTML;
			}
			else {
				alert(FCKLang.TEXT41);
			}
		}
		function swapMode(event, Act) {
			if(Act == "code") {
				$("editor_body_textarea").style.display = "";
				$("editor_body_area").style.display = "none";
				$("editor_body_textarea").value = _this.iframe.contentWindow.document.body.innerHTML;
				$("EDiaryEditorToolBarMask").style.display = "";
				var info = _this.config.toolbarinfo;
				for(var key in info) {
					$("EDiaryEditor" + info[key]["n"]).className = "EDiaryEditorDisable";
				}
				$("EDiaryEditorFontFamilyMenu").className = "EDiaryEditorDisable";
				$("EDiaryEditorFontSizeMenu").className = "EDiaryEditorDisable";
				$("EDiaryEditorFontStyleMenu").className = "EDiaryEditorDisable";
				$("ModeCheck").checked = true;
			}
			else {
				$("editor_body_textarea").style.display = "none";
				$("editor_body_area").style.display = "";
				_this.iframe.contentWindow.document.body.innerHTML = $("editor_body_textarea").value;
				$("EDiaryEditorToolBarMask").style.display = "none";
				var info = _this.config.toolbarinfo;
				for(var key in info) {
					$("EDiaryEditor" + info[key]["n"]).className = "";
				}
				$("EDiaryEditorFontFamilyMenu").className = "";
				$("EDiaryEditorFontSizeMenu").className = "";
				$("EDiaryEditorFontStyleMenu").className = "";
				$("ModeCheck").checked = false;
			}
		}
		_this.swapMode = swapMode;
		$("ModeCheck").onclick = function (event) {
			event = event || window.event;
			if(this.checked == true)swapMode(event, "code");
			else swapMode(event, "design");

		}
		var _imgArr = {};
		var node;
		for(var key in this.config.node) {
			node = this.config.node[key];
			if(key == 'undefined')continue;
			_imgArr[key] = new Image;
			_imgArr[key].src = "images/editor/" + key + ".gif";
			_imgArr[key + "_over"] = new Image;
			_imgArr[key + "_over"].src = "images/editor/" + key + "_over.gif";
			node.src = _imgArr[key].src;
			node.bname = key;
			node.onmouseover = function () {
				this.src = _imgArr[this.bname + "_over"].src;
			}
			node.onmouseout = function () {
				this.src = _imgArr[this.bname].src;
			}
			node.onclick = function () {
				_this.runCMD(this.bname);
			}
		}
		
		$("EDiaryEditorface").onclick = $("EDiaryEditorhilitecolor").onclick = $("EDiaryEditorforecolor").onclick = $("EDiaryEditorjustifyformat").onclick = $("EDiaryEditorFontStyleMenu").onclick = $("EDiaryEditorFontSizeMenu").onclick = $("EDiaryEditorFontFamilyMenu").onclick = function (event) {
			FontMenuFuncHash.click(event, FontMenuFuncHash);
			event = event || window.event;
			if(window._isIE){
				event.cancelBubble = true;
			}
			else {
				event.stopPropagation();
			}
		}
		$("EDiaryEditorIMGLeft").onmouseup = function (event) {
            formatIMG(event, "left");
		}
		$("EDiaryEditorIMGCenter").onmouseup = function (event) {
		        formatIMG(event, "center");
		}
		$("EDiaryEditorIMGRight").onmouseup = function (event) {
		        formatIMG(event, "right");
		}/*	
		$("EDiaryEditorimg").onclick = function (event) {
			viewDialog(event, "img");
		}*/
		if(!window.bFromInternet){
			$("EDiaryEditorimg").onclick = function(event){
				viewDialog(event, "img");
			}
		}
		$("EDiaryEditorlink").onclick = function (event) {
			viewDialog(event, "link");
		}
		$("EDiaryEditortable").onclick = function (event) {
			viewDialog(event, "table");
		}
		var _iframeHTML = "\
		<html>\
		<head>\
		<style>\
		body {\
			background: #ffffff;\
			margin: 0px;\
			padding: 8px 5px 2px 12px;\
			font-size: 14px;\
			overflow: auto;\
			scrollbar-face-color: #fff;\
			scrollbar-highlight-color: #c1c1bb;\
			scrollbar-shadow-color: #c1c1bb;\
			scrollbar-3dlight-color: #ebebe4;\
			scrollbar-arrow-color: #cacab7;\
			scrollbar-track-color: #f4f4f0;\
			scrollbar-darkshadow-color: #ebebe4;\
			word-wrap: break-word;\
			font-family: '宋体', 'Courier New';\
		}\
		p {\
			margin: 0px;\
		}\
		li, div, span , p {\
 			 line-height:1.5;\
		}\
		</style>\
		<script language=\"JavaScript\">\
			function resizeImg(_oObj, _iWidth){\
				var tMark = true;var iWidth = 0;var sOuterHtml;var aNode = _oObj.attributes;\
				for(var i = 0; i < aNode.length; i++){if(aNode[i].specified){if(aNode[i].name == \"width\" || aNode[i].name == \"height\"){tMark = false;}}}\
				if(tMark){if(_iWidth){setTimeout(\"resize()\",500);}}\
				this.resize = function(){if(_oObj.width > _iWidth){_oObj.width = _iWidth;}}\
			}\
		<\/script>\
		</head>\
		<body>" + (sContent ? sContent : "") + "</body>\
		</html>";
		_this.iframe.contentWindow.document.open();
		_this.iframe.contentWindow.document.write(_iframeHTML);
		_this.iframe.contentWindow.document.close();
		if(window._isIE){
			_this.iframe.contentWindow.document.body.contentEditable = true;
		}else{
			_this.iframe.contentWindow.document.designMode = "on";
		}
		this.cacheIframe = document.createElement("iframe");
		this.cacheIframe.style.visibility = "hidden";
		this.cacheIframe.style.position = "absolute";
		document.body.appendChild(this.cacheIframe);
		_this.iframeWindow = _this.iframe.contentWindow;
		_this.iframeDocument = _this.iframeWindow.document;

		// 初始化IFRAME中的事件 
		this.iframeEventCore.init(this);
		_this.iframe.contentWindow.document.onclick = document.onclick = function (event) {
			event = event || window.event;
			FontMenuFuncHash.hide(event, FontMenuFuncHash);
		}
		// zly, moz 下点击编辑区清除菜单无效, try 解决单纯判断浏览器引起的误差。
		try{
			_this.iframe.contentWindow.document.addEventListener('click', function (event) {
				event = event || window.event;
				FontMenuFuncHash.hide(event, FontMenuFuncHash);
			},true);
		}catch(e){};

		setTimeout(function () {
		_this.iframe.contentWindow.document.body.onpaste = function (event) {
			var clearFromWord = function(html) {
				// for Word2000+
				html = html.replace(/<\/?SPAN[^>]*>/gi, "" );
				html = html.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi, "<$1$3");
				html = html.replace(/<(\w[^>]*) style="([^"]*)"([^>]*)/gi, "<$1$3");
				html = html.replace(/<(\w[^>]*) lang=([^ |>]*)([^>]*)/gi, "<$1$3");
				html = html.replace(/<\\?\?xml[^>]*>/gi, "");
				html = html.replace(/<\/?\w+:[^>]*>/gi, "");
				// for Word2000
				html = html.replace(/<img+.[^>]*>/gi, "");
				return html;
			};
			if(window._isIE) {
				var pasteData = function () {
					var tif = _this.cacheIframe
					document.body.appendChild(tif);
					tlf = tif.contentWindow.document;
					tlf.body.innerHTML = "";
					tlf.body.createTextRange().execCommand("Paste");
					var html = tlf.body.innerHTML;
					var len = html.indexOf("&nbsp;");
					if(len == 0) html = html.replace(/\&nbsp;/i, "");
					var htmlData = html;
					tlf.body.innerHTML = "";
					return htmlData;
				}
				pasteData = pasteData();
				if(pasteData && pasteData.length > 0) {
					var wordPattern = /<\w[^>]* class="?MsoNormal"?/gi;
					if(wordPattern.test(pasteData)) {
						if(confirm(FCKLang.TEXT44)) {
							pasteData = clearFromWord(pasteData);
						} 
						else {	
							pasteData = pasteData.replace(/<img+.[^>]*>/gi, "");
							pasteData = pasteData.replace(/<\/?\w+:imagedata[^>]*>/gi, "");
							pasteData = pasteData.replace(/<\/?\w+:shape[^>]*>/gi, "");
						}
					}
					var oRTE = _this.iframe.contentWindow;
					var oRng = oRTE.document.selection.createRange();
					oRng.pasteHTML(pasteData);
				}
				return false;
			}
			return false;
		}
		}, 500);
		$("EDiaryEditorview").onclick = function () {
			$("editor_body_textarea").value = _this.iframe.contentWindow.document.body.innerHTML;
			article_preview();
		}
		//if(window._isIE)setTimeout(this.EDiaryEditorRsave, 500);
	},
	iframeEventCore: {
		init: function (_this) {
			var tthis = this;
			_this.iframe.contentWindow.document.onmousedown = function () {
				tthis.mousedown(_this.iframe.contentWindow.event, _this);
			}
			_this.iframe.contentWindow.document.onkeyup = function () {
				tthis.keyup(_this.iframe.contentWindow.event, _this);
			}
			setInterval(function () {
				tthis.imgsize(_this);
			}, 2000);
		},
		keyup: function (event, _this) {
		},
		imgsize: function (_this) {
			var o = _this.iframe.contentWindow.document.getElementsByTagName("img");
			var w, h;
			for(var i = 0; i < o.length; i ++) {
				w = o[i].width;
				h = o[i].height;
				if(o[i].getAttribute("width",2))continue;
				if(w > 500) {
					o[i].style.width = 500;
					o[i].style.height = Math.floor(o[i].width * (h / w));
				}
			}
		},
		mousedown: function (el, _this) {
			var tthis = this;
			var event = window.event || el;
			var esrc = event.srcElement || event.target;
			if(esrc.tagName.toLowerCase() == "img") {
				this.img = esrc;
				this.w = this.t_w = this.img.offsetWidth;
				this.h = this.t_h = this.img.offsetHeight;
				this.resizeEnd = false;
				this.img.onresize = function () {
					tthis.img = this;
					tthis.resize(event, _this);
					
				}
			}
		},
		resize: function (event, _this) {
			var tthis = this;
			if((this.t_w != this.img.offsetWidth || this.t_h != this.img.offsetHeight) && this.resizeEnd == false) {
				setTimeout(function () {
					tthis.timeout(tthis);
				}, 10);
				this.resizeEnd = true;
				this.img.onresize = null;
			}
		},
		timeout: function (_this) {
			if(Math.abs(_this.t_w - _this.img.offsetWidth) > 0) {
				_this.img.style.height = Math.floor(_this.img.offsetWidth * (_this.h / _this.w));
			}
			if(Math.abs(_this.t_h - _this.img.offsetHeight) > 0) {
				_this.img.style.width = Math.floor(_this.img.offsetHeight * (_this.w / _this.h));
			}
			_this.t_w = _this.img.offsetWidth;
			_this.t_h = _this.img.offsetHeight;
		}
	},
	runCMD: function (CMD, sValue) {
		var _this = this;
		if(CMD == "" || CMD == null)return;
		else if(CMD == "ParaFormatting") {
			var oBody	= _this.iframe.contentWindow.document.body;
			var oChild	= oBody.childNodes;
			for(var i = 0; i < oChild.length; i++){
				if(oChild[i].tagName){
					
					// 去掉首尾空格
					oChild[i].innerHTML	= oChild[i].innerHTML.split('&nbsp;').join('');
					oChild[i].innerHTML	= oChild[i].innerHTML.replace(/(^[ |　|]*)|([ |　|]*$)/g, "");
					oChild[i].innerHTML	= oChild[i].innerHTML.split('').join('&nbsp;');
					
					// 是否已经排过版,使用 2em 会使段落排版混乱［h1,h2混］,可设计默认 28 像素。通过计算子节点 fontSize 方式同样有此问题
					if(!oChild[i].style.textIndent){
						oChild[i].style.textIndent	= '2em';
					// 默认排版前
					}else{
						oChild[i].style.textIndent	= '';
					}
				// 纯文本
				}else{
					oBody.innerHTML = '<div style="text-indent:2em;">' + oBody.innerHTML.replace(/(^[ |　]*)|([ |　]*$)/g, ""); + '</div>';
				}
			}
		}
		else if(CMD == "justifyformat" || CMD == "img" || CMD == "link" || CMD == "table" || CMD == "face") {
			
		}
		else if(CMD == "forecolor") {
			_this.iframe.contentWindow.document.execCommand("forecolor", false, sValue);
		}
		else if(CMD == "hilitecolor") {
			if(!window._isIE){
				this.iframe.contentWindow.document.execCommand('styleWithCSS',false, true);
				this.iframe.contentWindow.document.execCommand("hilitecolor",false, sValue);
				this.iframe.contentWindow.document.execCommand('styleWithCSS',false, false)
			}
			_this.iframe.contentWindow.document.execCommand("backcolor", false, sValue);
		}
		else if(CMD == "img") {
			/*
			var oRTE = _this.iframe.contentWindow;
			var rng;
			if(window._isIE) {
				var selection = oRTE.document.selection; 
				if (selection != null) {
					rng = selection.createRange();
				}
			}
			else {
				var selection = oRTE.getSelection();
				rng = selection.getRangeAt(selection.rangeCount - 1).cloneRange();
				rng.text = rng.toString();
			}
			var html = rng.text;
			if(window._isIE) {
				var oSelection = oRTE.document.selection.createRange();
				var sRangeType = oRTE.document.selection.type;
				if (sRangeType == "Text") {
					try{
						oRTE.focus();
						var oRng = oRTE.document.selection.createRange();
						oRng.pasteHTML(html);
						oRng.collapse(false);
						oRng.select();
					}
					catch(e){}
				}
				else {
					alert(FCKLang.TEXT45);
				}
			}
			else {
				if(rng.text == "") {
					alert(FCKLang.TEXT45);
				}
				else {
					_this.iframe.contentWindow.document.execCommand("insertHTML", false, html);
				}
			}
			*/
			 viewDialog(event, 'img');
		}
		else if(CMD == "cleartype") {
			if(window._isIE) {
				for(var i = 0; i < _this.iframe.contentWindow.document.all.length; i ++) {
					var node = _this.iframe.contentWindow.document.all[i];
					if(node.tagName == "IMG") {
						_this.addHTML2(node, "&lt;Sina_Temp_IMG_Editor src='" + node.src + "' width='" + node.width + "' height='" + node.height + "' border='0'&gt;");
					}
				}
				var str = _this.iframe.contentWindow.document.body.innerText;
				str = str.replace(/<Sina_Temp_IMG_Editor/g, "<img");
				_this.iframe.contentWindow.document.body.innerHTML = str;
			}
			else {
				alert(FCKLang.TEXT46);
			}
		}
		else if(CMD == "textarea") {
			var _sVal;
			var frameWin = _this.iframe.contentWindow;
			var rng = {};
			if (window._isIE) {
				var selection = frameWin.document.selection; 
				if (selection != null) {
					rng = selection.createRange();
				}
			} else {
				var selection = frameWin.getSelection();
				
				rng = selection.getRangeAt(selection.rangeCount - 1).cloneRange();
				rng.text = rng.toString();
			}
			_sVal = rng.text == "" ? FCKLang.TEXT47 : rng.text;
			var html = "<table style='border:1px solid #999;width:80%;font-size:12px;' align='center'><tr><td>"+ _sVal +"</td></tr></table>";
		
			frameWin.focus();
			if(window._isIE) {
				var oSelection = _this.iframe.contentWindow.document.selection.createRange();
				var sRangeType = _this.iframe.contentWindow.document.selection.type;
				
					oSelection.pasteHTML(html);
					oSelection.collapse(false);
					oSelection.select();
			}
			else {
				 _this.iframe.contentWindow.document.execCommand('insertHTML', false, html);
			}
		}
		else if (CMD == "hilitecolor") {
			if(window._isIE) this.iframe.contentWindow.document.execCommand("BackColor",sValue);
			else{
				this.iframe.contentWindow.document.execCommand('styleWithCSS',false, true);
				this.iframe.contentWindow.document.execCommand("hilitecolor",false, sValue);
				this.iframe.contentWindow.document.execCommand('styleWithCSS',false, false)
			} 
		}
		else if (CMD == "undo" || CMD == "redo" || CMD == "cut" || CMD == "copy" || CMD == "paste") {
				this.iframe.contentWindow.focus();
				this.iframe.contentWindow.document.execCommand(CMD, false, sValue);
				this.iframe.contentWindow.focus();
				//ff下可以支持这些操作。参见：http://www.mozilla.org/editor/midas-spec.html
		}
		else {
			this.iframe.contentWindow.focus();
			this.iframe.contentWindow.document.execCommand(CMD, false, sValue);
			this.iframe.contentWindow.focus();
		}
	},
	addHTML: function (oParentNode, sHTML) {
		if(window.addEventListener) {// for MOZ
			var oRange = oParentNode.ownerDocument.createRange();
			oRange.setStartBefore(oParentNode);
			var oFrag = oRange.createContextualFragment(sHTML);
			oParentNode.appendChild(oFrag);
		}
		else {// for IE5+
			oParentNode.insertAdjacentHTML("BeforeEnd", sHTML);
		}
	},
	addHTML2: function (oParentNode, sHTML) {
		oParentNode.insertAdjacentHTML("afterEnd", sHTML);
	},
	insertFace: function (nFaceNum) {
		var FacePath = editorUrl + "/face/";
		this.iframe.contentWindow.focus();
		this.iframe.contentWindow.document.execCommand("InsertImage", false, FacePath + nFaceNum + ".gif");
		this.iframe.contentWindow.focus();
	},
	save: function () {
		var _this = this;
		if($("ModeCheck").checked == true) {
			_this.iframeEventCore.imgsize(_this);
			_this.iframe.contentWindow.document.body.innerHTML = $("editor_body_textarea").value;
			$("editor_body_textarea").value = _this.iframe.contentWindow.document.body.innerHTML;
		}
		else {
			$("editor_body_textarea").value = _this.iframe.contentWindow.document.body.innerHTML;
		}
	}
};