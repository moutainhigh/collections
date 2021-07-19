Event.observe(window, 'load', function(){
	$('ChannelId-StepTwo').value = getParameter('channelid');
	$('ChannelId-StepOne').value = getParameter('channelid');
	LoadRegion();
	Event.observe('cells-area', 'keydown', KeyDownHandle);
	Event.observe('cells-area', 'dblclick', DblClickHandle);
});

function LoadRegion(){
	BasicDataHelper.Call('wcm6_regioninfo', 'query', {channelId : $('ChannelId').value}, true, RegionLoaded,
		function(){
			alert(wcm.LANG.index_1000 || 'ajax 失败!');
		}
	);
}

function RegionLoaded(transport, json){
	var RegionInfos = $a(json, 'RegionInfos.RegionInfo');
	if(RegionInfos.length <= 0){
		Element.show('step-one');
		$('RName-StepOne').focus();
		return;
	}
	Element.show('step-two');
	var RegionInfo = RegionInfos[0];
	BuildDefaultValue($v(RegionInfo, 'RowNum')||defaultRowNum, $v(RegionInfo, 'CellNum')||defaultCellNum);
	$('RName-StepTwo').value = $v(RegionInfo, 'RNAME')||"";
	$('Width-StepTwo').value = $v(RegionInfo, 'Width')||defaultWidth;
	$('cells-area').style.width = $('Width-StepTwo').value + "px";
	$('ObjectId').value = $v(RegionInfo, 'REGIONINFOID') || 0;
	LoadCells($('ObjectId').value);
}

function LoadCells(regionInfoId){
	if(regionInfoId == 0){
		Element.update('cells-area', defaultRowsTemplate);
		FocusMe(Element.first(Element.first($('cells-area'))));
		return;
	}
	BasicDataHelper.Call('wcm6_cellinfo', 'query', {RegionInfoId : regionInfoId,PageSize : -1}, true, CellsLoaded,
		function(){
			alert(wcm.LANG.index_1000 || 'ajax 失败!');
		}
	);
}

function CellsLoaded(transport, json){
	RenderCells(json);
	FocusMe(Element.first(Element.first($('cells-area'))));
}

var RenderCells = function(json){
	var aHtml = [];
	var aCellInfos = $a(json, 'CellInfos.CellInfo');
	if(aCellInfos.length <= 0){
		Element.update('cells-area', defaultRowsTemplate);
		return;
	}
	var lastRowIndex = -1;
	for (var i = 0; i < aCellInfos.length; i++){
		var rowIndex = $v(aCellInfos[i], 'rowIndex');
		if(rowIndex != lastRowIndex){
			if(i != 0)aHtml.push('</div>');
			aHtml.push('<div class="row">');
			lastRowIndex = rowIndex;
		}
		aHtml.push('<div contenteditable="true" class="cell"', 
			' docId="', $v(aCellInfos[i], 'docid') || 0, '"',
			' recId="', $v(aCellInfos[i], 'recid') || 0, '"',
			' objId="', $v(aCellInfos[i], 'cellinfoid') || 0, '"',
		'>',
			$v(aCellInfos[i], 'doctitle') || blankTextTemplate,
		'</div>');
	}
	if(aCellInfos.length > 0) aHtml.push('</div>')
	Element.update('cells-area', aHtml.join(""));
}

function SaveRegion(){
	AjaxCaller.save('region-area');
}

AjaxCaller.valid = function(){
	var dom = $('RName-StepTwo');
	if(dom.value.trim() == ''){
		alert(wcm.LANG.index_2000 || '导读名称不能为空!');
		dom.focus();
		return false;
	}
	var els = [['Width-StepTwo', '宽度']];
	for (var i = 0; i < els.length; i++){
		var dom = $(els[i][0]);
		if(dom.value.trim() == ''){
			alert(String.format('导读{0}不能为空!',els[i][1]));
			dom.focus();
			return false;
		}
		if(!/\d+/g.test(dom.value)){
			alert(String.format('导读{0}必须为数字!',els[i][1]));
			dom.focus();
			return false;
		}
	}
}

AjaxCaller.onSuccess = function(transport, json){
	$('cells-area').style.width = $('Width-StepTwo').value + "px";
	$('ObjectId').value = $v(json, 'result');
	SaveCells();
}

AjaxCaller.onFailure = function(){
	alert(wcm.LANG.index_7000 || '导读保存失败!');
}

function SaveCells(){
	BasicDataHelper.Call('wcm6_cellinfo', 'saveCells', {
		RegionInfoId : $('ObjectId').value,
		CellsXML : BuildXMLData()
	}, true, function(){
		alert(wcm.LANG.index_8000 || '保存导读行信息成功!');
	}, function(){
		alert(wcm.LANG.index_9000 || '保存导读行信息失败!');			
	});
}	

var BuildXMLData = function(){
	var dom = $('cells-area');
	var aXML = ['<OBJECTS>'];
	var i = 0;
	var row = Element.first(dom);
	while(row){
		var j = 0;
		var cell = Element.first(row);
		while(cell){
			var sTitle = cell.innerHTML; 
			if(sTitle == blankTextTemplate) sTitle = "";
			aXML.push('<OBJECT');
			aXML.push(' id="', cell.getAttribute("objId") || 0, '"');
			aXML.push(' docId="', cell.getAttribute("docId") || 0, '"');
			aXML.push(' recId="', cell.getAttribute("recId") || 0, '"');
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

function KeyDownHandle(event){
	event = window.event || event;
	if(event.keyCode == Event.KEY_RETURN){
		if(event.shiftKey){
			Commands.InsertRowAfter();
		}else{
			Commands.InsertCellAfter();
		}
		return false;
	}
}

function DblClickHandle(event){
	event = window.event || event;
	var dom = Event.element(event);
	if(!Element.hasClassName(dom, 'cell')) return;
	SelectDocument(dom);
}

function valid(sPrefix){
	var dom = $('RName' + sPrefix);
	if(dom.value.trim() == ''){
		alert(wcm.LANG.index_2000 || '导读名称不能为空!');
		dom.focus();
		return false;
	}
	var els = [['Width'+sPrefix, '宽度'], ['RowNum', '行数'], ['CellNum', '列数']];
	for (var i = 0; i < els.length; i++){
		var dom = $(els[i][0]);
		if(dom.value.trim() == ''){
			alert(String.format('导读{0}不能为空!',els[i][1]));
			dom.focus();
			return false;
		}
		if(!/\d+/g.test(dom.value)){
			alert(String.format('导读{0}必须为数字!',els[i][1]));
			dom.focus();
			return false;
		}
	}
}

function DoNext(){
	if(valid('-StepOne')) return false;
	BasicDataHelper.Call('wcm6_regioninfo', 'save', 'step-one', true, function(){
		alert(wcm.LANG.index_12000 || '保存导读配置成功!');
		Element.hide('step-one');
		Element.show('step-two');
		LoadRegion();
	}, function(){
		alert(wcm.LANG.index_13000 || '保存导读配置失败!');			
	});
}

function ExecuteCommand(event){
	event = window.event || event;
	var srcElement = event.srcElement || event.target;
	var cmd = srcElement.getAttribute('cmd');
	if(!cmd || !Commands[cmd]) return;
	Commands[cmd]();
}

function ShowDialog4wcm52Style(_url, _nWidth, _nHeight){
	var nWidth = _nWidth , nHeight = _nHeight; 
	var nLeft	= (window.screen.availWidth - nWidth)/2;
	var nTop	= (window.screen.availHeight - nHeight)/2;

	var sFeatures	= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
						+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
						+ "center: Yes; scroll:No;help: No; resizable: No; status: No;";
	return showModalDialog(_url, window, sFeatures);
}

function GetSelectedElement(){
	var dom = document.activeElement;
	if(Element.hasClassName(dom.parentNode, 'row')) return dom;
	return null;
}

var blankTextTemplate = wcm.LANG.index_14000 || '双击选择文档...';
var cellTemplate = '<div contenteditable="true" class="noContent">{0}</div>';
var blankCellTemplate = String.format(cellTemplate, blankTextTemplate);

var rowTemplate = '<div class="row">{0}</div>';
var blankRowTemplate = String.format(rowTemplate, blankTextTemplate);

var GetCellsTemplate = function(colNum){
	colNum = parseInt(colNum, 10);
	var aHtml = [];
	for (var i = 0; i < colNum; i++){
		aHtml.push(blankCellTemplate);
	};
	return aHtml.join("");
}

var GetRowsTemplate = function(rowNum, colNum){
	rowNum = parseInt(rowNum, 10);
	var aHtml = [];
	var cellsTemplate = GetCellsTemplate(colNum);
	for (var i = 0; i < rowNum; i++){
		aHtml.push(String.format(rowTemplate, cellsTemplate));
	};
	return aHtml.join("");
}

var defaultWidth = 400;
var defaultRowNum = 2;
var defaultCellNum = 2;

var BuildDefaultValue = function(rowNum, colNum){
	defaultRowNum = rowNum;
	defaultCellNum = colNum;
	blankRowTemplate = String.format(rowTemplate, GetCellsTemplate(colNum));
	defaultRowsTemplate = GetRowsTemplate(rowNum, colNum);
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
	InsertRowAfter : function(){
		var dom = GetSelectedElement();
		if(dom == null) return;
		var row = dom.parentNode;
		new Insertion.After(row, blankRowTemplate);
		var related = Element.next(row);
		if(related) FocusMe(Element.first(related));
	},
	InsertRowBefore : function(){
		var dom = GetSelectedElement();
		if(dom == null) return;
		var row = dom.parentNode;
		new Insertion.Before(row, blankRowTemplate);
		var related = Element.previous(row);
		if(related) FocusMe(Element.first(related));
	},
	DeleteRows : function(){
		var dom = GetSelectedElement();
		if(dom == null) return;
		var row = dom.parentNode;
		var related = Element.next(row) || Element.previous(row);
		if(related == null){//only last one row.
			Element.update(row, blankCellTemplate);
			FocusMe(Element.first(row));
			return;
		}
		Element.remove(row);	
		if(related) FocusMe(Element.first(related));
	},
	InsertCellAfter : function(){
		var dom = GetSelectedElement();
		if(dom == null) return;
		new Insertion.After(dom, blankCellTemplate);
		var related = Element.next(dom);
		if(related) related.focus();
	},
	InsertCellBefore : function(){
		var dom = GetSelectedElement();
		if(dom == null) return;
		new Insertion.Before(dom, blankCellTemplate);
		var related = Element.previous(dom);
		if(related) FocusMe(related);
	},
	DeleteCells : function(){
		var dom = GetSelectedElement();
		if(dom == null) return;		
		var related = Element.next(dom) || Element.previous(dom);		
		if(related == null){
			this.DeleteRows();
			return;
		}
		Element.remove(dom);
		if(related) FocusMe(related);
	},
	SetDocument : function(){	
		var dom = GetSelectedElement();
		if(dom == null) return;
		SelectDocument(dom);
	}
};

function SelectDocument(dom){
	Element.addClassName(dom, "setdocument");
	var recId = dom.getAttribute('redId') || 0;
	var sUrl = '../document/document_select_index.html?recIds='+recId+"&DocChannelId="+getParameter("channelId");
	var result = ShowDialog4wcm52Style(sUrl, 880, 440);
	Element.removeClassName(dom, "setdocument");
	if(result == null) return;
	var index = Math.max(result['ids'].length-1, 0);
	var recId = result['ids'][index];
	if(!recId) return;
	var infos = result['infos'][recId];
	dom.setAttribute('docId', infos['docId']);
	dom.setAttribute('redId', infos['recId']);
	dom.setAttribute('docTitle', infos['docTitle']);
	dom.innerHTML = $transHtml(infos['docTitle']);
	Element.removeClassName(dom, "noContent");
}

var lastActiveRow = null;
Event.observe(window, 'load', function(){
	Event.observe('toolbar', 'mousemove', function(event){
		event = window.event || event;
		var dom = Event.element(event);
		if(!dom.getAttribute('cmd')) return;
		if(lastActiveRow){
			Element.removeClassName(lastActiveRow, 'activeRow');
		}
		Element.addClassName(lastActiveRow, 'activeRow');
		lastActiveRow = dom;
	});
});