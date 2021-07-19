$package('com.trs.dialog');
$importCSS('com.trs.dialog.ReportsDialog');
$import('com.trs.dialog.Dialog');
$import('com.trs.util.CommonHelper');
$import('com.trs.util.JSON');
com.trs.dialog.imgDir = null;
_TRANS_TITLE_FUN_NAME = 'ReportsDialog.transTitle';

var ReportsDialog = new Object();
ReportsDialog.show = function(_reports, _sTitle, _fClose, _titleTransOptions){
	ReportsDialog.close = _fClose;
	ReportsDialog.reports = _reports;
	ReportsDialog.dialog = $dialog();

	com.trs.dialog.imgDir = com.trs.dialog.imgPath;	
	ReportsDialog.dialog.setTitle(_sTitle);
	ReportsDialog.dealWithDrag();

	ReportsDialog.titleTransOptions = _titleTransOptions;
	// set the content of the reports dialog
	ReportsDialog.dialog.custom(ReportsDialog.getDialogHtml(_reports),_fClose);
	ReportsDialog.dialog.move();
}
ReportsDialog.hide = function(){
	var fClose = ReportsDialog.close;
	if(fClose != null) {
		if(typeof(fClose) == 'string') {
			eval(fClose);
		}else if(typeof(fClose) == 'function') {
			fClose();
		}
	}

	$dialog().hide();
}
ReportsDialog.copyToClipboard = function(){
	try{
		if(ReportsDialog.Summary == null || ReportsDialog.Summary.trim() == '') {
			alert("没有要复制的任何信息！");
			return;
		}
		com.trs.util.CommonHelper.copy(ReportsDialog.Summary);
		alert("已经复制到剪切板中！");
	}catch(ex){
		//alert(ex);
		alert('您的浏览器不支持自动复制操作，请手工拷贝！');
		$dialogElement('txtDetail').focus();
		$dialogElement('txtDetail').select();		
	}
}
ReportsDialog.destroy = function(){
	try{
		ReportsDialog.dialog.drag.onDragStart0 = null;
		ReportsDialog.dialog.drag.onDragEnd0 = null;
	}catch(error){
		//just skip it
	}
	ReportsDialog.dialog = null;
}
Event.observe(window,'unload',function(){
	ReportsDialog.destroy();
});

ReportsDialog.dealWithDrag = function(){
	if(ReportsDialog.dialog.drag){
		if(!ReportsDialog.dialog.drag.onDragStart0){
			ReportsDialog.dialog.drag.onDragStart0 = ReportsDialog.dialog.drag.onDragStart || Prototype.emptyFunction;
		}
		ReportsDialog.dialog.drag.onDragStart = function(){
			ReportsDialog.dialog.drag.onDragStart0.apply(ReportsDialog.dialog.drag,arguments);
			$dialogElement('divReportsList').style.overflowY = 'hidden';
		};
		if(!ReportsDialog.dialog.drag.onDragEnd0){
			ReportsDialog.dialog.drag.onDragEnd0 = ReportsDialog.dialog.drag.onDragEnd || Prototype.emptyFunction;
		}
		ReportsDialog.dialog.drag.onDragEnd = function(){
			ReportsDialog.dialog.drag.onDragEnd0.apply(ReportsDialog.dialog.drag,arguments);
			$dialogElement('divReportsList').style.overflowY = 'auto';
		};
	}			
};
ReportsDialog.clickContent = function(oTextArea){
	$dialogElement('divReportsList').style.overflowY = 'auto';
}

ReportsDialog.switchDetail = function(_sId, _oLnk){
	var detail = $dialogElement('divDetails_' + _sId);
	if(_oLnk._bDispay === true ) {
		_oLnk.innerHTML = '详细信息';
		_oLnk._bDispay = false;
		Element.hide(detail);
	}else{
		_oLnk.innerHTML = '隐藏显示';
		_oLnk._bDispay = true;
		Element.show(detail);
	}
	// maybe resize
	ReportsDialog.dialog=$dialog(true);
	ReportsDialog.dialog.move();	
	ReportsDialog.dialog.shadow();
}


ReportsDialog.transTitle = function(_sTitle, _sOption){
	var str = _sTitle;
	var reg = new RegExp('\~' + _sOption + '\-([0-9]+)\~', 'i');
	var matches = str.match(reg);
	var nId = 0;
	if(matches != null && (nId = parseInt(matches[1])) > 0) {
		str = '<span class="option_' + _sOption + '" onclick="ReportsDialog.renderOption('
			+ nId + ')"></span>' + str.replace(reg, '');
	}

	return str;
}
ReportsDialog.renderOption = function(_sObjId){
	var options = ReportsDialog.titleTransOptions;
	if(options.renderOption) {
		options.renderOption(_sObjId);
	}
}
ReportsDialog.getDialogHtml = function(_reports){
	//var testJson = com.trs.util.JSON.parseXml(com.trs.util.XML.loadXML(testReports));
	//_reports = testJson;
	var getNodeVal = com.trs.util.JSON.value;
	try{
		var reportsInfo = {
			isSuccess: getNodeVal(_reports, 'reports.is_success') === 'true',
			title: getNodeVal(_reports, 'reports.title'),
			reports: $a(_reports, 'reports.report') || []
		};
		
		var sIcon =  com.trs.dialog.imgDir + (reportsInfo.isSuccess ? 'succeed' : 'error') + '.gif';
		var sTopTitle = reportsInfo.title;
		var sSummary = reportsInfo.title + '\n';
		var sContent  = '<div id="divReportsList" style="width:90%;overflow-y:hidden;" onclick="ReportsDialog.clickContent();">';

		var options = ReportsDialog.titleTransOptions;
		var bNeedTransTitle = (options != null && options.option != null);
		for (var i = 0; i < reportsInfo.reports.length;){
			var report = reportsInfo.reports[i++];
			var sTitle = getNodeVal(report, 'title');
			var sDetails = getNodeVal(report, 'ERROR_INFO');
			var sType = getNodeVal(report, 'TYPE');
			if(sType == '') {
				sType = '3';
			}
			var bHasDetail = (sDetails != null && sDetails.trim() != '');
			sTitle = bNeedTransTitle ? ReportsDialog.transTitle(sTitle, options.option) : $transHtml(sTitle);
			sContent += '<div class="' + (bHasDetail ? 'innerTitle_withTip' : 'innerTitle') + '"><b>' + i + '.</b> \
							<img src="' + com.trs.dialog.imgDir + 'type_' + sType + '.gif" align="absmiddle" border=0 >\ ' + sTitle + '</div>';
					
			if(bHasDetail) {
				sContent += '<div class="tip">（<a href="#" onclick="ReportsDialog.switchDetail(' + i + ', this);return false;">详细信息</a>）</div>\
							<div id="divDetails_' + i + '" style="margin-top:-13px; display:none">\
							<div class="commentHeader"></div>\
								<textarea class="commentbox" readonly>' + sDetails + '</textarea>\
							</div>';
			}
			sContent += '<div class="sep">&nbsp;</div>';
			sSummary += i + '. [' + getTypeName(sType) + ']' + sTitle + '\n';
			sSummary += '[详细信息] ' + (sDetails || '') + '\n\n';
		}
		ReportsDialog.Summary = sSummary;
		sContent += '</div>';

		return ReportsDialog.buildDialogHtml(sIcon, sTopTitle, sContent);
	}catch(err){
		return("服务器返回的数据不是合适的报告类型！" + err.message);
	}
}
var m_arrTypeNames = ['UNKNOWN', 'SUCCESS', 'WARN', 'ERROR'];
function getTypeName(_nType){
	_nType = parseInt(_nType);
	if(isNaN(_nType) || _nType < 3 || _nType > 5) {
		_nType = 2;
	}
	return m_arrTypeNames[_nType - 2];
}

if(window.useWCMDialog != true){
	var ReportsDialog = myActualTop.ReportsDialog;
}


ReportsDialog.buildDialogHtml = function(_sIcon, _sTitle, _sContent){
	return	'\
		<table border=0 cellspacing=0 cellpadding=0 style="height:250px;width:550px;" id="outerTable">\
			<tr>\
				<td class="bodytd">\
					<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">\
						<tr>\
							<td class="body_left2"><div style="width:8px;overflow:hidden;"></div></td>\
							<td style="width:120px;">\
								<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">\
									<tr>\
										<td style="height:150px;" align="center" valign="middle">\
											<div style="background:no-repeat center center;height:100%;width:120px; background-image: url(' + _sIcon + ')"></div>\
										</td>\
									</tr>\
									<tr>\
										<td align="center" valign="middle">\
											<div class="copyDetail" onclick="ReportsDialog.copyToClipboard()">复制此信息</div>\
											<div class="closeWin" onclick="ReportsDialog.hide();">关闭提示窗</div>\
										</td>\
									</tr>\
								</table>\
							</td>\
							<td align="center" valign="middle" style="border-left:1px solid gray;">\
								<table style="width:100%;height:100%;" border="0" cellpadding="2" cellspacing="2">\
									<tr>\
										<td align="center" style="padding: 8px;">\
											 <div class="reports_title" style="display:inline;">' + _sTitle + '</div>\
										</td>\
									</tr>\
									<tr>\
										<td align="center" valign="top" width="100%;" height="100%" style="padding-top:3px">' + _sContent + '\
										</td>\
									</tr>\
								</table>\
							</td>\
							<td class="body_right2"><div style="width:8px;overflow:hidden;"></div></td>\
						</tr>\
					</table>\
				</td>\
			</tr>\
			<tr height="9">\
				<td height="9">\
					<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">\
					<tbody>\
						<tr>\
							<td class="bottom_left3"><div style="width:14px;height:9px;overflow:hidden;"></div></td>\
							<td class="bottom_middle3" colspan="2"><div style="height:9px;overflow:hidden;"></div></td>\
							<td class="bottom_right3"><div style="width:14px;height:9px;overflow:hidden;"></div></td>\
						</tr>\
					</tbody>\
					</table>\
				</td>\
			</tr>\
		</table>';    
}
