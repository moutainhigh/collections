$package('com.trs.dialog');

$import('com.trs.dialog.Dialog');
$import('com.trs.util.CommonHelper');
com.trs.dialog.imgDir = null;// = com.trs.util.Common.BASE+'com.trs.dialog/img/';

var FaultDialog = {};
//FaultDialog.dialog = null;
FaultDialog.show = function(_fault, _sTitle, _fClose){
	_fClose = _fClose;
	FaultDialog.dialog = $dialog();
	com.trs.dialog.imgDir = com.trs.dialog.imgPath;	
	FaultDialog.dialog.setTitle(_sTitle);
	FaultDialog.dialog.custom(FaultDialog.getDialogHtml(),_fClose);
	if (_fault){
		$dialogElement('spMsg').innerHTML = _fault.message;
		if(_fault.suggestion && _fault.suggestion.trim() != '') {
			Element.show($dialogElement('trSuggestion'));
			$dialogElement('spSuggestion').innerHTML = _fault.suggestion;
		}
		$dialogElement('txtDetail').value = _fault.detail;
	}
	window.setTimeout(function(){
		FaultDialog.dialog.move();	
		FaultDialog.dialog.shadow();
	}, 0);

	//重置变量
	if($bDisplayDetail === true)
		$bDisplayDetail = false;
}
FaultDialog.hide = function(){
	$dialog().hide();
}
FaultDialog.copyToClipboard = function(){
	if (!$bDisplayDetail)
		FaultDialog.switchDetail();

	window.setTimeout(function(){
		try{
			com.trs.util.CommonHelper.copy($dialogElement("txtDetail").value);
			alert("已经复制到剪切板中！");
		}catch(ex){
			//alert(ex);
			alert('您的浏览器不支持自动复制操作，请手工拷贝！');
			$dialogElement('txtDetail').focus();
			$dialogElement('txtDetail').select();		
		}
	}, 10);
}
FaultDialog.clickContent = function(oTextArea){
	var detail = $dialogElement('txtDetail');
	detail.style.overflow = 'auto';
}
FaultDialog.destroy = function(){
	try{
		FaultDialog.dialog.drag.onDragStart0 = null;
		FaultDialog.dialog.drag.onDragEnd0 = null;
	}catch(error){
		//just skip it
	}
	FaultDialog.dialog = null;
}
var $bDisplayDetail = false;
FaultDialog.switchDetail = function(){
	$bDisplayDetail = !$bDisplayDetail;
	var detail = $dialogElement('txtDetail');
	if ($bDisplayDetail){
		$dialogElement('lnkSwitchDetail').innerHTML = '隐藏详细信息';
		Element.show(detail);
	}else{
		Element.hide(detail);
		$dialogElement('lnkSwitchDetail').innerHTML = '显示详细信息';
	}

	// maybe resize
	FaultDialog.dialog = $dialog(true);
	window.setTimeout(function(){
		FaultDialog.dialog.move();	
		FaultDialog.dialog.shadow();
	}, 0);
}
Event.observe(window,'unload',function(){
	FaultDialog.destroy();
});
FaultDialog.closeWindow = function(){
	var currWin = window.top ? window.top : window;
	try{
		currWin.close();
	}catch (ex){
		//alert(ex.description);
		alert('无法通过此链接关闭当前页面！请点击该页面的关闭按钮。');
	}
}

FaultDialog.getDialogHtml = function(){
	return '\
	<table style="height:250;width:380" border="1" cellpadding="0" cellspacing="0" bgcolor="#cccccc">\
		<tr>\
			<td width="80px" style="padding:5px" bgcolor="#ffffff">\
				<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="1">\
					<tr>\
						<td valign="top" height="20%" align="center">\
							<img src="' + com.trs.dialog.imgDir + '3.gif" align="absmiddle">\
						</td>\
					</tr>\
					<tr>\
						<td width="100%" valign="top" align="center" style="white-space: nowrap">\
							<br><br><br><br>\
							<a id="lnkSwitchDetail" href="#" onclick="FaultDialog.switchDetail(); return false;">显示详细信息</a>\
							<br><br>\
							<a href="#" onclick="FaultDialog.copyToClipboard(); return false;">复制此信息</a>\
							<br><br>\
							<a href="#" onclick="FaultDialog.hide(); return false;">关闭提示窗</a>\
							<!--<br><br>\
							<a href="#" onclick="FaultDialog.closeWindow(); return false;">关闭当前页</a>-->\
						</td>\
					</tr>\
				</table>\
			</td>\
			<td width="*" valign="center" bgcolor="#ffffff">\
				<table width="100%" height="100%" border="0" cellpadding="2" cellspacing="2">\
					<tr>\
						<td align="center" height="90%">\
							<span id="spMsg" style="white-space:normal;font-family:Arial;font-weight:bold;font-size:14px">&lt;Undefined Fault Message&gt;</span>\
						</td>\
					</tr>\
					<tr id="trSuggestion" style="display:none">\
						<td align="left" height="90%" style="border-top:1px solid #efefef; padding-top:3px">\
							<span style="overflow:autho;font-family:Arial;font-weight:bold;font-size:12px">建议：</span>\
							<span id="spSuggestion" style="overflow:autho;font-family:Arial;font-weight:normal;font-size:12px;color:green;">无建议信息</span>\
						</td>\
					</tr>\
					<tr>\
						<td align="center" valign="top" width="100%" style="border-top:1px solid #efefef; padding-top:3px">\
							<textarea id="txtDetail" style="display:none;width:100%; height: 175px; font-family:Courier New;background:#f6f6f6;border:0px;" onclick="FaultDialog.clickContent();">&lt;Undefined Fault Detail&gt;</textarea>\
						</td>\
					</tr>\
				</table>\
			</td>\
		</tr>\
	</table>'; 
}

if(window.useWCMDialog){
	Object.extend(FaultDialog, {
		show : function(_fault, _sTitle, _fClose){
			_fClose = _fClose ? _fClose : '';
			FaultDialog.dialog = $dialog();
			com.trs.dialog.imgDir = com.trs.dialog.imgPath;
			FaultDialog.dialog.setTitle(_sTitle);
			FaultDialog.dialog.custom(FaultDialog.getDialogHtml(),_fClose);
			FaultDialog.dialog.setIcon(com.trs.dialog.imgDir + '8.gif');
			FaultDialog.dealWithDrag();
			if (_fault){
				var msg = $transHtml(_fault.message);
				if(msg.length > 250){
					msg = msg.substr(0, 250) + "...";
				}
				$dialogElement('spMsg').innerHTML = msg;
				if(_fault.suggestion && _fault.suggestion.trim() != '') {
					Element.show($dialogElement('trSuggestion'));
					$dialogElement('spSuggestion').innerHTML = _fault.suggestion;
				}
				$dialogElement('txtDetail').value = _fault.detail;
			}
			window.setTimeout(function(){
				FaultDialog.dialog.move();	
				FaultDialog.dialog.shadow();
			}, 10);
			//重置变量
			if($bDisplayDetail === true)
				$bDisplayDetail = false;
		},	
		dealWithDrag : function(){
			if(FaultDialog.dialog.drag){
				if(!FaultDialog.dialog.drag.onDragStart0){
					FaultDialog.dialog.drag.onDragStart0 = FaultDialog.dialog.drag.onDragStart || Prototype.emptyFunction;
				}
				FaultDialog.dialog.drag.onDragStart = function(){
					FaultDialog.dialog.drag.onDragStart0.apply(FaultDialog.dialog.drag,arguments);
					$dialogElement('txtDetail').style.overflow = 'hidden';
				};
				if(!FaultDialog.dialog.drag.onDragEnd0){
					FaultDialog.dialog.drag.onDragEnd0 = FaultDialog.dialog.drag.onDragEnd || Prototype.emptyFunction;
				}
				FaultDialog.dialog.drag.onDragEnd = function(){
					FaultDialog.dialog.drag.onDragEnd0.apply(FaultDialog.dialog.drag,arguments);
					$dialogElement('txtDetail').style.overflow = 'auto';
				};
			}			
		},
		hide : function(){
			try{
				FaultDialog.dialog.closeElement.click();
			}catch(error){
				$dialog().hide();
			}
		},
		copyToClipboard : function(){
			if (!Element.visible('txtDetail'))
				FaultDialog.switchDetail();

			window.setTimeout(function(){
				try{
					com.trs.util.CommonHelper.copy($dialogElement("txtDetail").value);
					alert("已经复制到剪切板中！");
				}catch(ex){
					alert('您的浏览器不支持自动复制操作，请手工拷贝！');
					$dialogElement('txtDetail').focus();
					$dialogElement('txtDetail').select();		
				}
			}, 10);
		},
		switchDetail : function(){
			var detail = $dialogElement('txtDetail');
			if (!Element.visible(detail)){
				$dialogElement('lnkSwitchDetail').innerHTML = '隐藏详细信息';		
				Element.show(detail);
				var maxWidth = 700;
				var topWidth = parseInt(top.document.body.offsetWidth)-125;
				var detailWidth = Element.getDimensions('txtDetail').width;				
				detail.style.width = [maxWidth, topWidth, detailWidth].min();
			}else{
				Element.hide(detail);
				FaultDialog.dialog.resize();
				$dialogElement('lnkSwitchDetail').innerHTML = '显示详细信息';
			}
			FaultDialog.dialog = $dialog(true);
			window.setTimeout(function(){
				FaultDialog.dialog.move();	
				FaultDialog.dialog.shadow();
			}, 10);
		},
		getDialogHtml : function(){
			return	'\
				<table border=0 cellspacing=0 cellpadding=0 style="height:253px;width:450px;" id="outerTable">\
					<tr>\
						<td class="bodytd">\
							<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">\
								<tr>\
									<td class="body_left2"><div style="width:8px;overflow:hidden;"></div></td>\
									<td style="width:120px;">\
										<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">\
											<tr>\
												<td style="height:150px;" align="center" valign="middle">\
													<div id="dialogIcon" style="background:no-repeat center center;height:100%;width:120px;"></div>\
												</td>\
											</tr>\
											<tr>\
												<td align="center" valign="middle">\
													<div class="toggleDetail" id="lnkSwitchDetail" onclick="FaultDialog.switchDetail();">显示详细信息</div>\
													<div class="copyDetail" onclick="FaultDialog.copyToClipboard();">复制此信息</div>\
													<div class="closeWin" onclick="FaultDialog.hide();">关闭提示窗</div>\
												</td>\
											</tr>\
										</table>\
									</td>\
									<td align="center" valign="middle" style="border-left:1px solid gray;">\
										<table style="width:100%;height:100%;" border="0" cellpadding="2" cellspacing="2">\
											<tr>\
												<td align="center" height="90%">\
													 <span id="spMsg" style="word-wrap:break-word;word-break:break-all;font-family:Arial;font-weight:bold;font-size:14px">&lt;Undefined Fault Message&gt;</span>\
												</td>\
											</tr>\
											<tr id="trSuggestion" style="display:none">\
												<td align="left" height="90%" style="border-top:1px solid #efefef; padding-top:3px">\
													 <span style="overflow:auto;font-family:Arial;font-weight:bold;font-size:12px">建议：</span>\
													 <span id="spSuggestion" style="overflow:auto;font-family:Arial;font-weight:normal;font-size:12px;color:green;">无建议信息</span>\
												</td>\
											</tr>\
											<tr>\
												<td align="center" valign="top" width="100%" style="padding-top:3px">\
													 <textarea id="txtDetail" style="overflow:hidden;display:none;width:100%; height: 175px; font-family:Courier New;background:#f6f6f6;border:0px;" onclick="FaultDialog.clickContent();">&lt;Undefined Fault Detail&gt;</textarea>\
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
	});
}