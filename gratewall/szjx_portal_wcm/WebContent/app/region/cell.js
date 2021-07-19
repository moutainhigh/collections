Event.observe(window, 'load', function(){
	$('ChannelId').value = getParameter('channelid') || 0;
	$('WebSiteId').value = getParameter('websiteId') || 0;
	$('ObjectId').value = getParameter('objectId') || 0;
	LoadRegion();
	Event.observe('trs-region', 'keydown', KeyDownHandle);
	Event.observe('trs-region', 'dblclick', DblClickHandle);
	Event.observe('trs-region', 'click', ClickHandle);
	bindOperToEl();
});
function bindOperToEl(){
	var titleEls = document.getElementsByClassName('attr_column_title');
	for(var i=0; i < titleEls.length; i++){
		Event.observe(titleEls[i], 'mouseover', function(){
			this.style.backgroundImage='url(image/attr_column_head_bg_over.gif)';
		}.bind(titleEls[i]));
		Event.observe(titleEls[i], 'mouseout', function(){
			this.style.backgroundImage='url(image/attr_column_head_bg.gif)';
		}.bind(titleEls[i]));
	}
	var buttonSpEls = document.getElementsByClassName('buttonsp');
	for(var i=0; i < buttonSpEls.length; i++){
		Event.observe(buttonSpEls[i], 'mouseover', function(){
			 Element.addClassName(this, 'buttonspover');
		}.bind(buttonSpEls[i]));
		Event.observe(buttonSpEls[i], 'mouseout', function(){
			Element.removeClassName(this, 'buttonspover');
		}.bind(buttonSpEls[i]));
	}
}
function LoadRegion(){
	BasicDataHelper.Call('wcm6_regioninfo', 'findById', {objectId : $('ObjectId').value}, true, RegionLoaded,
		function(){
			alert(wcm.LANG.CHANNEL_88 || 'ajax 失败!');
		}
	);
}
function RegionLoaded(transport, json){
	var RegionInfo = $a(json, 'RegionInfo');
	if(RegionInfo.length <= 0){
		alert(String.format('没有找到id为[{0}]的导读信息!',$('ObjectId').value));
		return;
	}
	var RegionInfo = RegionInfo[0];
	BuildDefaultValue($v(RegionInfo, 'RowNumber')||defaultRowCount, $v(RegionInfo, 'CellNum')||defaultCellNum);
	$('RName').value = $v(RegionInfo, 'RNAME')||"";
	$('Width').value = $v(RegionInfo, 'Width')||defaultWidth;
	$('CssText').value = $v(RegionInfo, 'CssText')||"";
	$('CellPadding').value = $v(RegionInfo, 'CellPadding')||"3";
	$('RegionFontSize').value = $v(RegionInfo, 'RegionFontSize')||"12";
	$('ObjectId').value = $v(RegionInfo, 'REGIONINFOID') || 0;
	$('ChannelId').value = $v(RegionInfo, 'CHANNELID') || 0;
	$('WebSiteId').value = $v(RegionInfo, 'WEBSITEID') || 0;
	LoadCells($('ObjectId').value);
	RenderRegionCells();
}

function LoadCells(regionInfoId){
	BasicDataHelper.Call('wcm6_cellinfo', 'query', {RegionInfoId : regionInfoId,PageSize : -1}, true, CellsLoaded,
		function(){
			alert(wcm.LANG.CHANNEL_88 || 'ajax 失败!');
		}
	);
}

function CellsLoaded(transport, json){
	RenderCells(json);
	FocusMe(Element.first(Element.first($('trs-region'))));
}
var RenderCells = function(json){
	var aHtml = [];
	var aCellInfos = $a(json, 'CellInfos.CellInfo');
	if(aCellInfos.length <= 0){
		Element.update('trs-region', defaultRowsTemplate);
		addOperationRecode();
		return;
	}
	var lastRowIndex = -1;
	for (var i = 0; i < aCellInfos.length; i++){
		var rowIndex = $v(aCellInfos[i], 'rowIndex');
		if(rowIndex != lastRowIndex){
			if(i != 0)aHtml.push('</div>');
			aHtml.push('<div class="trs-row">');
			lastRowIndex = rowIndex;
		}
		var currTrueTitle = "";
		var currShowTitle = $v(aCellInfos[i], 'doctitle') || "";
		if(currShowTitle.match(/<a(.*)<\/a>/i) != null){
			currTrueTitle = $v(aCellInfos[i], 'doctitle');
			currShowTitle = currShowTitle.replace(/<a[^<]*>/i,'');
			currShowTitle = currShowTitle.replace(/<\/a>/i, '');
		}
		var bindDocCls = ($v(aCellInfos[i], 'docid') == 0 &&  $v(aCellInfos[i], 'chnlid') == 0)? " nobinddoc" : "";
		aHtml.push('<a href="#" contenteditable="true" class="trs-cell', bindDocCls, '"', 
			' docId="', $v(aCellInfos[i], 'docid') || 0, '"',
			' recId="', $v(aCellInfos[i], 'recid') || 0, '"',
			' chnlId="', $v(aCellInfos[i], 'chnlId') || 0, '"',
			' objId="', $v(aCellInfos[i], 'cellinfoid') || 0, '"',
			' appendixId="', $v(aCellInfos[i], 'appendixid') || 0, '"',
			" _truehtml='", currTrueTitle, "'",

		'>',
			currShowTitle || blankTextTemplate,
		'</a>');
	}
	if(aCellInfos.length > 0) aHtml.push('</div>')
	Element.update('trs-region', aHtml.join(""));
	RecodeOperation[LastOperationIndex] = aHtml.join("");
}

Valid = function(){
	var dom = $('RName');
	if(dom.value.trim() == ''){
		alert(wcm.LANG.CHANNEL_90 || '导读名称不能为空!');
		dom.focus();
		return false;
	}
	if(dom.value.trim().length > 60){
		alert(wcm.LANG.CHANNEL_116 || '导读名称最大长度为60!');
		dom.focus();
		return false;
	}

	var els = [['Width', wcm.LANG.CHANNEL_91 || '宽度', 1, 1000], ['RegionFontSize', wcm.LANG.CHANNEL_117 || '字体大小', 1, 50], ['CellPadding', wcm.LANG.CHANNEL_118 || '单元间距', 0, 100]];
	for (var i = 0; i < els.length; i++){
		var dom = $(els[i][0]);
		if(dom.value.trim() == ''){
			alert(String.format('导读{0}不能为空!',els[i][1]));
			dom.focus();
			return false;
		}
		var exp = new RegExp(/^-?\d+$/g);
		exp.lastIndex=0;
		if(!exp.test(dom.value)){
			alert(String.format('导读{0}必须为数字!',els[i][1]));
			dom.focus();
			return false;
		}
		var nValue = parseInt(dom.value, 10);
		if(nValue < els[i][2] || nValue > els[i][3]){
			alert(String.format('导读{0}不在范围[{1}~{2}]!',els[i][1],els[i][2],els[i][3]));
			dom.focus();
			return false;
		}
	}
}

function SaveRegion(bClose){
	if(Valid() === false) return false;
	BasicDataHelper.Call('wcm6_regioninfo', 'existsSimilarName', {ObjectId : $('ObjectId').value, channelId : $('ChannelId').value,WebSiteId : $('WebSiteId').value, Rname : $('RName').value}, true, 
		function(_transport,_json){
			var bExsit = $a(_json, 'result');
			if(bExsit == 'true'){
				 alert(String.format('导读名称【{0}】已经存在，请重新输入！',$('RName').value));
				 return;
			}else{
				BasicDataHelper.Call('wcm6_regioninfo', 'save', 'data', true, RegionSaved.bind(window, bClose),
					function(){
						alert(wcm.LANG.CHANNEL_95 || '保存失败!');
					}
				); 
				return false;
			}
		}
	);
	return false;
}

function RegionSaved(bClose){
	RenderRegionCells();
	SaveCells(bClose);
}

function SaveCells(bClose){
	var postData = {
		RegionInfoId : $('ObjectId').value,
		CellsXML : BuildXMLData(),
		BPublish : $('BPublish').value
	};
	BasicDataHelper.Call('wcm6_cellinfo', 'saveCells', postData, true, CellsSaved.bind(window, bClose),
		function(){
			alert(wcm.LANG.CHANNEL_95 || '保存失败!');
		}
	);
	return false;
}	

var BuildXMLData = function(){
	var dom = $('trs-region');
	var aXML = ['<OBJECTS>'];
	var i = 0;
	var row = Element.first(dom);
	while(row){
		var j = 0;
		var cell = Element.first(row);
		while(cell){
			var sTitle = null;
			sTitle = cell.getAttribute("_truehtml") || cell.innerHTML;
			var bNotHasAel = sTitle.match(/<a(.*)<\/a>/i) == null;
			if(!bNotHasAel){
				var sMatch = sTitle.match(/<span[^<]*>/i);
				var sLeftContent = (sMatch == null ? '' : sMatch[0]);
				sLeftContent = sLeftContent + sTitle.match(/<a[^<]*>/i)[0];
				sMatch = sTitle.match(/<\/span>/i);
				var sRightContent = (sMatch == null ? '' : sMatch[0]);
				sRightContent = sTitle.match(/<\/a>/i)[0] + sRightContent;
				var sMiddleContent = cell.innerHTML.replace(/<span[^<]*>/i, '').replace(/<\/span>/i, '');
				sMiddleContent = sMiddleContent.replace(/<a[^<]*>/i, '').replace(/<\/a>/i, '');
				sTitle = sLeftContent + sMiddleContent + sRightContent;
			}
			if(sTitle == blankTextTemplate) sTitle = "";
			aXML.push('<OBJECT');
			aXML.push(' ID="', cell.getAttribute("objId") || 0, '"');
			aXML.push(' docId="', cell.getAttribute("docId") || 0, '"');
			aXML.push(' recId="', cell.getAttribute("recId") || 0, '"');
			aXML.push(' chnlId="', cell.getAttribute("chnlId") || 0, '"');
			aXML.push(' appendixId="', cell.getAttribute("appendixId") || 0, '"');
			aXML.push(' rowIndex="', i, '"');
			aXML.push(' cellIndex="', j, '"');
			aXML.push(' docTitle="', sTitle.escape4Xml(), '"');
			aXML.push('/>');
			cell = Element.next(cell);
			j++;
		}
		i++;
		row = Element.next(row);
	}
	aXML.push("</OBJECTS>");
	return aXML.join("");
};

function CellsSaved(bClose){
	if(bClose){
		var cbr = wcm.CrashBoarder.get('Trs_Region_Set_Deatail');
		cbr.notify($('ObjectId').value);
	}else{
		alert(wcm.LANG.CHANNEL_96 || '保存成功!');
	}
};

function KeyDownHandle(event){
	event = window.event || event;
	if(event.keyCode == Event.KEY_RETURN){
		Event.stop(event);
		if(event.shiftKey){
			Commands.InsertRowAfter();
		}else{
			Commands.InsertCellAfter();
		}
	}
}
function ClickHandle(event){
	event = window.event || event;
	var dom = Event.element(event);
	//WCM-703解决单元格无法选中的问题，如果当前设置的内容中有样式就有问题了
	while(dom && dom.tagName && dom.tagName.toUpperCase() != 'body'){
		if(Element.hasClassName(dom, 'trs-cell')){
			activeCellDom = dom;
			break;
		}
		dom = dom.parentNode;
	}
	return;
}

function DblClickHandle(event){
	event = window.event || event;
	var cell;
	var dom = Event.element(event);
	while(dom && dom.tagName && dom.tagName.toUpperCase() != 'body'){
		if(Element.hasClassName(dom, 'trs-cell')){
			cell = dom;
			break;
		}
		dom = dom.parentNode;
	}
	if(cell){
		var appendixId = dom.getAttribute('appendixId', 0);
		var channelId = dom.getAttribute('chnlId', 0);
		if(appendixId > 0){
			SetDocAppendix(cell);
		}else if(channelId > 0){
			SelectChannel(cell);
		}else{
			SelectDocument(cell);
		}
	} 
}

function ExecuteCommand(event){
	event = window.event || event;
	var srcElement = event.srcElement || event.target;
	var cmd = srcElement.getAttribute('cmd');
	if(!cmd || !Commands[cmd]) return;
	Commands[cmd]();
}

function ShowDialog4wcm52Style(_url, _nWidth, _nHeight, dialogArguments){
	var nWidth = _nWidth , nHeight = _nHeight; 
	var nLeft	= (window.screen.availWidth - nWidth)/2;
	var nTop	= (window.screen.availHeight - nHeight)/2;

	var sFeatures	= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
						+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
						+ "center: Yes; scroll:No;help: No; resizable: No; status: No;";
	try{
		var bResult = showModalDialog(_url, dialogArguments==null?window:dialogArguments, sFeatures);
		return bResult;
	}catch(e){
		alert((wcm.LANG.CHANNEL_104 || "您的IE插件已经将对话框拦截!\n")
				+ (wcm.LANG.CHANNEL_105 || "请将拦截去掉-->点击退出-->关闭IE,然后重新打开IE登录即可!\n")
				+ (wcm.LANG.CHANNEL_106 || "给您造成不便,TRS致以深深的歉意!"));		
	}
	return true;
}

function GetSelectedElement(){
	var dom = activeCellDom;
	if(!dom){
		return null;
	}
	if(Element.hasClassName(dom.parentNode, 'trs-row')) return dom;
	return null;
}
var activeCellDom = null;
var blankTextTemplate = wcm.LANG.CHANNEL_97 || '双击选择文档...';
//var cellTemplate = '<div contenteditable="true" class="trs-cell">{0}</div>';
var cellTemplate = '<a href="#" contenteditable="true" class="trs-cell nobinddoc">{0}</a>';
var blankCellTemplate = String.format(cellTemplate, blankTextTemplate);
var cellDocTemplate = '<a href="#" contenteditable="true" class="trs-cell" recId={1} docId={2}>{0}</a>';

var rowTemplate = '<div class="trs-row">{0}</div>';
var blankRowTemplate = String.format(rowTemplate, blankTextTemplate);

var GetCellsTemplate = function(colNum){
	colNum = parseInt(colNum, 10);
	var aHtml = [];
	for (var i = 0; i < colNum; i++){
		aHtml.push(blankCellTemplate);
	};
	return aHtml.join("");
}

var GetRowsTemplate = function(rowCount, colNum){
	rowCount = parseInt(rowCount, 10);
	var aHtml = [];
	var cellsTemplate = GetCellsTemplate(colNum);
	for (var i = 0; i < rowCount; i++){
		aHtml.push(String.format(rowTemplate, cellsTemplate));
	};
	return aHtml.join("");
}

var defaultWidth = 400;
var defaultRowCount = 2;
var defaultCellNum = 2;

var BuildDefaultValue = function(rowCount, colNum){
	defaultRowCount = rowCount;
	defaultCellNum = colNum;
	blankRowTemplate = String.format(rowTemplate, GetCellsTemplate(colNum));
	defaultRowsTemplate = GetRowsTemplate(rowCount, colNum);
}
var GetCellsTemplateForDocs = function(json){
	var CellsTemplateForDocs = [];
	var recIds = json['ids'];
	for (var j = 0; j < recIds.length; j++){
		var info = json['infos'][recIds[j]];
		CellsTemplateForDocs.push(String.format(cellDocTemplate, info['docTitle'], info['recId'], info['docId']));
	};
	return CellsTemplateForDocs;
}
function renderCell4DocumentAllSelect(json){
	if(json['ids'].length == 0) return;
	var docSelectRowsTemplate = [];
	var docSelectCellsTemplate = GetCellsTemplateForDocs(json);
	var number = 0;
    for (var i = 0; i < defaultRowCount; i++){
		var currCellsTemplate = [];
		if(number >= docSelectCellsTemplate.length) break;
		for(var k = 0; k < defaultCellNum; k++){
			currCellsTemplate.push(docSelectCellsTemplate[number]);
			number++;
		}
		docSelectRowsTemplate.push(String.format(rowTemplate, currCellsTemplate.join("")));
	}
	if(docSelectRowsTemplate.length > 0){
		Element.update('trs-region', docSelectRowsTemplate.join(""));
	}else{
		Element.update('trs-region', defaultRowsTemplate);
	}
	FocusMe(Element.first(Element.first($('trs-region'))));
}
var FocusMe = function(dom){
	setTimeout(function(){
		if(!dom) return;
		try{
			dom.focus();
		}catch(err){}
	}, 0);
};
var Commands = {
	SaveRegion : function(){
		SaveRegion();
	},
	SetCssText : function(){
		var result = ShowDialog4wcm52Style('region_csstext.html', 700, 400, $('CssText').value);
		if(result == undefined) return;
		$('CssText').value = result;
	},
	SetHTMLFormat : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert("必须先在左侧选择需要操作的单元格！");
			return;
		}
		OpenSimpleEditor(dom);
	},
	InsertRowAfter : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		var row = dom.parentNode;
		new Insertion.After(row, blankRowTemplate);
		var related = Element.next(row);
		if(related) FocusMe(Element.first(related));
		addOperationRecode();
	},
	InsertRowBefore : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		var row = dom.parentNode;
		new Insertion.Before(row, blankRowTemplate);
		var related = Element.previous(row);
		if(related) FocusMe(Element.first(related));
		addOperationRecode();
	},
	DeleteRows : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		var row = dom.parentNode;
		var related = Element.next(row) || Element.previous(row);
		if(related == null){//only last one row.
			Element.update(row, blankCellTemplate);
			FocusMe(Element.first(row));
			return;
		}
		Element.remove(row);	
		if(related) FocusMe(Element.first(related));
		addOperationRecode();
	},
	InsertCellAfter : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		new Insertion.After(dom, blankCellTemplate);
		var related = Element.next(dom);
		if(related) FocusMe(related);
		addOperationRecode();
	},
	InsertCellBefore : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		new Insertion.Before(dom, blankCellTemplate);
		var related = Element.previous(dom);
		if(related) FocusMe(related);
		addOperationRecode();
	},
	DeleteCells : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		var related = Element.next(dom) || Element.previous(dom);		
		if(related == null){
			this.DeleteRows();
			return;
		}
		Element.remove(dom);
		if(related) FocusMe(related);
		addOperationRecode();
	},
	MoveRowUp : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}	
		var row = dom.parentNode;
		var previous = Element.previous(row);
		if(previous == row) return;
		row.parentNode.insertBefore(row, previous);
		FocusMe(dom);
		addOperationRecode();
	},
	MoveRowDown : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}	
		var row = dom.parentNode;
		var next = Element.next(row);
		if(next == null){
			next = Element.first(row.parentNode);
			if(next != row){
				row.parentNode.insertBefore(row, next);
			}
		}else if(next != row){
			row.parentNode.insertBefore(next, row);
		}
		FocusMe(dom);
		addOperationRecode();
	},
	MoveCellUp : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}	
		var row = dom.parentNode;
		row = Element.previous(row);
		if(row) row.appendChild(dom);		
		FocusMe(dom);
		addOperationRecode();
	},
	MoveCellDown : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}	
		var row = dom.parentNode;
		row = Element.next(row);
		if(row) row.insertBefore(dom, Element.first(row));		
		FocusMe(dom);
		addOperationRecode();
	},
	MoveCellLeft : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}	
		var previous = Element.previous(dom);
		if(previous){
			dom.parentNode.insertBefore(dom, previous);
		}else{
			this.MoveCellUp();
		}
		FocusMe(dom);
		addOperationRecode();
	},
	MoveCellRight : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		var next = Element.next(dom);
		if(next){
			next.parentNode.insertBefore(next, dom);
		}else{
			this.MoveCellDown();
		}
		FocusMe(dom);
		addOperationRecode();
	},
	SetAllDocument : function(){
		// var sHtml = "确实要批量设置文档吗？<br><br><span style='color:red;font:14px;'>注意：<br>该操作会覆盖更改当前所有的设置！</span>";
		var sHtml = "确实要批量设置文档吗？注意：该操作会覆盖更改当前所有的设置！";
		if (confirm(sHtml)){
			var sUrl = "../document/document_select_index.jsp?recIds=&DocChannelId="+$('ChannelId').value+"&SiteId=" + $('WebSiteId').value;
			var result = ShowDialog4wcm52Style(sUrl, 800, 440);
			if(!result) return;
			renderCell4DocumentAllSelect(result);
			addOperationRecode();
		}
	},
	SetDocument : function(){	
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		SelectDocument(dom);
	},
	SetChannel : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		SelectChannel(dom);
	},
	SetDocAppendix : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		SetDocAppendix(dom);
	},
	RemoveDocAppendix : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		var appendixId = dom.getAttribute("appendixId") || '0';
		if(parseInt(appendixId) == 0)return;
		else{
			dom.setAttribute("appendixId", 0);
			dom.setAttribute('_truehtml', dom.innerHTML);
		}
		addOperationRecode();
	},
	InsertIcon : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		var currTitle = dom.innerHTML;
		var aMatches = currTitle.match(/^<span class=(.*)<\/span>/i);
		if(aMatches == null)
			selectTitleIcon(dom);
		else 
			alert(wcm.LANG.cell_6000 || "需要先移除已经设置的图标，才能进行新的设置！");
	},
	RemoveIcon : function(){
		var dom = GetSelectedElement();
		if(dom == null){
			alert(wcm.LANG.cell_5000 || "必须先在左侧选择需要操作的单元格！");
			return;
		}
		var currTitle = dom.innerHTML;
		var currTrueTitle = dom.getAttribute("_truehtml") || '';
		var aMatches = currTitle.match(/^<span(.*)<\/span>/i);
		if(aMatches == null)return;
		else{
			currTrueTitle = currTrueTitle.replace(/^<span[^<]*>/i, '');
			currTrueTitle = currTrueTitle.replace(/<\/span>$/i, '');
			currTitle = currTitle.replace(/^<span[^<]*>/i, '');
			currTitle = currTitle.replace(/<\/span>$/i, '');
			dom.innerHTML = currTitle;
			dom.setAttribute("_truehtml", currTrueTitle);
			FocusMe(dom);
		}
		addOperationRecode();
	},
	Revocate : function(){
		Revocate();
	},
	Redo : function(){
		Redo();
	}
};
function SelectChannel(dom){
	Element.addClassName(dom, "setrelation");
	var channelId = dom.getAttribute('chnlId') || 0;
	var hostChannelId = $('ChannelId').value;
	var hostSiteId = $('WebSiteId').value;
	var sUrl = './region_select_channel.html?CurrChannelId='+ channelId +'&SelectedChannelIds=' 
		+ channelId + '&hostSiteId=' + hostSiteId + '&hostChannelId=' + hostChannelId;
	var result = ShowDialog4wcm52Style(sUrl, 300, 400);
	Element.removeClassName(dom, "setrelation");
	if(!result) {
		FocusMe(dom);
		return;
	}
	var index = Math.max(result['channelIds'].length-1, 0);
	var channelId = result['channelIds'][index];
	var chnlname = result['channelNames'][index];
	if(!channelId) return;
	if(!chnlname) return;
	dom.setAttribute('chnlId', channelId);
	dom.setAttribute('docId', 0);
	dom.setAttribute('recId', 0);
	dom.setAttribute('docTitle', chnlname);
	dom.setAttribute('_truehtml', $transHtml(chnlname));
	dom.setAttribute('appendixId', 0);
	dom.innerHTML = $transHtml(chnlname);
	Element.removeClassName(dom, "nobinddoc");
	addOperationRecode();
}
function SelectDocument(dom){
	Element.addClassName(dom, "setrelation");
	var recId = dom.getAttribute('recId') || 0;
	if(dom.getAttribute('appendixId') > 0)
		recId = 0;
	var sUrl = '../document/document_select_index.jsp?selectType=radio&recIds='+recId+"&DocChannelId="+$('ChannelId').value+"&SiteId=" + $('WebSiteId').value;
	var result = ShowDialog4wcm52Style(sUrl, 800, 440);
	Element.removeClassName(dom, "setrelation");
	if(!result) return;
	//result['ids'] = result['ids'].split(",");
	var index = Math.max(result['ids'].length-1, 0);
	var recId = result['ids'][index];
	if(!recId) return;
	var infos = result['infos'][recId];
	if(dom.getAttribute('docId') != infos['docId'])
		dom.setAttribute('appendixId', 0);
	dom.setAttribute('docId', infos['docId']);
	dom.setAttribute('recId', infos['recId']);
	dom.setAttribute('docTitle', infos['docTitle']);
	dom.setAttribute('chnlId', 0);
	//WCM-952 导读功能改进之更换文档须保留样式
	//获取存放title的节点，替换节点的title
	var oEndChildDom = dom;
	var oEndChildText = "";
	while(oEndChildDom.childNodes.length == 1 && oEndChildDom.firstChild && oEndChildDom.firstChild.firstChild){
		oEndChildDom = oEndChildDom.firstChild;
	}
	oEndChildText = oEndChildDom.innerHTML;
	var sEndCellHtml = dom.innerHTML.replace(oEndChildText,$transHtml(infos['docTitle'])); 
	//替换为修改后的html内容
	dom.innerHTML = sEndCellHtml;
	Element.removeClassName(dom, "nobinddoc");
	addOperationRecode();
}

function SetDocAppendix(dom){
	var docId = dom.getAttribute('docId') || 0;
	var recId = dom.getAttribute('recId') || 0;
	var param = {
		selectType : "radio", 
		recIds : recId,
		DocChannelId : $('ChannelId').value,
		SiteId : $('WebSiteId').value,
		DocId : docId,
		AppendixId : dom.getAttribute('appendixId') || 0
	}
	var sUrl = WCMConstants.WCM6_PATH + 'document/document_attachments_index.jsp';
	wcm.CrashBoarder.get('Trs_Region_Select_Appendix').show({
		width:'950px',
		height:'550px',
		title :wcm.LANG.cell_7000 ||  '设置文档附件',
		src : sUrl,
		maskable:true,
		params : param,
		callback : function(_param){
			if(!_param['appendixId']){
				FocusMe(dom);
				this.close();
				return;
			}
			var currTitle = dom.getAttribute('_truehtml') || dom.innerHTML;
			var sTitle = _param['showname'] ||  _param['appendixPath'];
			if(currTitle.match(/<a(.*)<\/a>/i) == null){
				currTitle = '<a href=' + _param['appendixPath'] + '>' + sTitle + '</a>';
			}else{
				currTitle = currTitle.replace(/href=(.*)>[^>]/i, 'href=' + _param['appendixPath'] + '>');
			}
			dom.setAttribute('_truehtml', currTitle);
			dom.setAttribute('appendixId', _param['appendixId']);
			var selectedDocInfo = _param.SelectedDocInfo;
			for(var nchnldoc in selectedDocInfo){
				dom.setAttribute('docId', selectedDocInfo[nchnldoc].docId);
				dom.setAttribute('recId', selectedDocInfo[nchnldoc].recId);
			}
			dom.setAttribute('chnlId', 0);
			dom.innerHTML = sTitle;
			Element.removeClassName(dom, "nobinddoc");
			FocusMe(dom);
			addOperationRecode();
			this.close();
	   }
  });
}
function RenderRegionCells(){	
	var nWidth = parseInt($('Width').value, 10) + parseInt($('CellPadding').value, 10) + 25;
	var dynamicFieldsCssText = [
		'.trs-region', '{width:', nWidth, 'px;font-size:', $('RegionFontSize').value, 'px;}','\n',
		'.trs-cell', '{margin-right:', $('CellPadding').value, 'px;}'
	].join("");
	
	if($('dynamicFields').styleSheet){//IE
		$('dynamicFields').styleSheet.cssText = dynamicFieldsCssText;
	}else{//w3c
		var dynamicCssNode = document.createTextNode(dynamicFieldsCssText);
		$('dynamicFields').appendChild(dynamicCssNode);
	}

	var cssText = $('CssText').value.trim();
	if(cssText == "" && $('dynamicCssText').styleSheet){//fix ie6 bug.
		$('dynamicCssText').styleSheet.disabled = true;
		return;
	}
	//只对容器内的东西添加样式，否则会对整个页面都会有影响
	cssText = ".trs-region " + cssText;
	var exp = /[}]/g;//这里应该只要一个正则就可以搞定，需再想想
	var newCssText = cssText.replace(exp, "} .trs-region ");
	newCssText = newCssText.substring(0,newCssText.lastIndexOf("}")+1);

	if($('dynamicCssText').styleSheet){//IE
		$('dynamicCssText').styleSheet.disabled = false;
		$('dynamicCssText').styleSheet.cssText = newCssText;
	}else{//w3c
		var newCssTextNode = document.createTextNode(newCssText);
		$('dynamicCssText').appendChild(newCssTextNode);
	}
}

var oSimpleEditorDialog = null;
function OpenSimpleEditor(dom){
	CurrentTitleDom = dom;
	var sToolBar = 'Title';
	var sElementCmd = GetTitleFormat();
	var nWidth = 800;
	var nHeight = 300;
	var sUrl = WCMConstants.WCM6_PATH + 'editor/simp_editor_region.html';
	wcm.CrashBoarder.get('Trs_Region_Simple_Editor').show({
		maskable:true,
		reloadable : true,
		title : wcm.LANG.CHANNEL_98 || '设置标题格式',
		src : sUrl,
		width:'800px',
		height:'300px',
		params : {
			 toolbar : sToolBar,
			 html : sElementCmd
		},
		callback:function(sHtml){
			SetTitleFormat(sHtml);
			this.close();
		}
    });
}
var CurrentTitleDom = null;
function GetTitleFormat(){
	return CurrentTitleDom.getAttribute('_truehtml') || CurrentTitleDom.innerHTML;
}
function SetTitleFormat(sHtml){
	//WCM-952 导读功能改进之去除链接中的_fcksavedurl
	sHtml = sHtml[0].replace(/\s_fcksavedurl=\".*?\"/i,"");
	sHtml = sHtml.replace(/^<p>(.*)<\/p>$/i, "$1");
	if(sHtml.match(/<a(.*)<\/a>/i) != null){
		CurrentTitleDom.setAttribute("_truehtml", sHtml);
		sHtml = sHtml.replace(/<a[^<]*>/i, '');
		sHtml = sHtml.replace(/<\/a>/i, '');
	}else{
		CurrentTitleDom.setAttribute("_truehtml", "");
	}
	CurrentTitleDom.innerHTML = sHtml;
	FocusMe(CurrentTitleDom);
	addOperationRecode();
}
function selectTitleIcon(dom){
	CurrentTitleDom = dom;
	var nWidth = 400;
	var nHeight = 200;
	var sUrl = WCMConstants.WCM6_PATH + 'region/region_select_title_icon.html';
	wcm.CrashBoarder.get('Trs_Region_Set_Icon').show({
		maskable:true,
		reloadable : true,
		title :wcm.LANG.cell_8000 ||  '设置图标',
		src : sUrl,
		width:'400px',
		height:'150px',
		callback : function(params){
			setTitleIcon(params);
			this.close();
		}
    });
}
function setTitleIcon(param){
	var dom = CurrentTitleDom;
	var currTitle = dom.innerHTML;
	var trueTitle = dom.getAttribute("_truehtml");
	if(trueTitle != '') trueTitle = '<span class="'+ param.position + '_' + param.subject + '">' + trueTitle + '</span>'; 
	currTitle = '<span class="'+ param.position + '_' + param.subject + '">' + currTitle + '</span>'; 
	dom.innerHTML = currTitle;
	FocusMe(dom);
	addOperationRecode();
}
function ShowAttrColumn(_sId){
	var contents = document.getElementsByClassName('attr_column_content');
	for(var i=0;i<contents.length;i++){
		if(contents[i].id==_sId+'_attr_content'){
			Element.show(contents[i]);
		}
		else{
			Element.hide(contents[i]);
		}
	}
	return false;
}
function toggleShowOrHide(currDiv){
	Element.hasClassName('boxdiv', 'hide_east') ? Element.removeClassName('boxdiv', 'hide_east') : Element.addClassName('boxdiv', 'hide_east');
}
var RecodeOperation = [];
var LastOperationIndex = 0;
function addOperationRecode(){
	var currHtml = $('trs-region').innerHTML;
	RecodeOperation[RecodeOperation.length] = currHtml;
	LastOperationIndex = RecodeOperation.length - 1;
}
function Revocate(){
	if(LastOperationIndex > 0)
		LastOperationIndex = LastOperationIndex - 1;
	if(!RecodeOperation[LastOperationIndex]) return;
	$('trs-region').innerHTML = RecodeOperation[LastOperationIndex];
}
function Redo(){
	if((LastOperationIndex + 1) == RecodeOperation.length) return;
	LastOperationIndex = LastOperationIndex + 1;
	if(!RecodeOperation[LastOperationIndex])return;
	$('trs-region').innerHTML = RecodeOperation[LastOperationIndex];
}
window.m_cbCfg = {
		btns : [
			{
				text :wcm.LANG.cell_9000 ||  '保存',
				id : 'btn-save',
				cmd : function(){
					SaveRegion(true);
					return false;
				}
			},
			{
				text :wcm.LANG.cell_10000 ||  '保存并发布',
				id : 'btn-save-and-publish',
				cmd : function(){
					$('BPublish').value = true;
					SaveRegion(true);
					return false;

				}
			},
			{
				text :wcm.LANG.cell_11000 ||  '关闭',
				cmd : function(){
						var cbr = wcm.CrashBoarder.get('Trs_Region_Set_Deatail');
						if(cbr)
							cbr.notify($('ObjectId').value);
				}
			}
		]
	};


function init(){
	if(getParameter('objectId') != 0){
		LockerUtil.render(getParameter('objectId'), 1911708670, true);

		if(LockerUtil.failedToLock == true) {//加锁失败时，禁用提交按钮
			wcm.ComponentMgr.get('btn-save').disable();
			wcm.ComponentMgr.get('btn-save-and-publish').disable();
		}
	}
};