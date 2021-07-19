Event.observe(window, 'load', function(){
	$('ChannelId').value = getParameter('channelid') || 0;
	$('WebSiteId').value = getParameter('webSiteId') || 0;
	LoadRegion();
});
var sDefaultValue = "/*示例如下*/\n.trs-region {font-size:14px;}\n.trs-row {height:20px;}\n.trs-cell {width:90px;}";

Event.observe(document, 'click', function(){
	if($('CssText').value == ""){
		$('CssText').value = sDefaultValue;
		$('CssText').style.color = 'gray';
		$('CssText').style.fontStyle = 'italic';
		$('CssText').setAttribute('bChange', false);
	}
});
function LoadRegion(){
	BasicDataHelper.Call('wcm6_regioninfo', 'findById', {ObjectId : getParameter('ObjectId') || 0}, true, RegionLoaded,
		function(){
			alert(wcm.LANG.CHANNEL_88 || 'ajax 失败!');
		}
	);
}
function RegionLoaded(transport, json){
	var RegionInfo = $a(json, 'RegionInfo');
	if(RegionInfo.length <= 0){
		$('RName').focus();
		$('CssText').value = sDefaultValue;
		$('CssText').style.color = 'gray';
		$('CssText').style.fontStyle = 'italic';
		$('CssText').setAttribute('bChange', false);
		AfterRenderCallBack();
		return;
	}
	var RegionInfo = RegionInfo[0];
	$('ObjectId').value = $v(RegionInfo, 'REGIONINFOID') || 0;
	$('RName').value = $v(RegionInfo, 'RNAME')||"";
	$('Width').value = $v(RegionInfo, 'Width')||"385";
	$('RowNumber').value = $v(RegionInfo, 'RowNumber')||"5";
	$('CellNum').value = $v(RegionInfo, 'CellNum')||"3";
	$('CellPadding').value = $v(RegionInfo, 'CellPadding')||"3";
	$('RegionFontSize').value = $v(RegionInfo, 'RegionFontSize')||"12";
	$('CssText').value = $v(RegionInfo, 'CssText')||sDefaultValue;
	$('ChannelId').value = $v(RegionInfo,'ChannelId') || 0;
	$('WebSiteId').value = $v(RegionInfo,'WebSiteId') || 0;
	if($v(RegionInfo, 'CssText')==""||$v(RegionInfo, 'CssText')==null){
		$('CssText').style.color = 'gray';
		$('CssText').style.fontStyle = 'italic';
		$('CssText').setAttribute('bChange', false);
	}else{
		$('CssText').setAttribute('bChange', true);
	}
	LockerUtil.render($('ObjectId').value, 1911708670, null, function(){
		FloatPanel.disableCommand('SaveRegion', true,true);
		FloatPanel.disableCommand('SaveRegionAndNext', true,true);
	});
	if(LockerUtil.failedToLock == true) {//加锁失败时，禁用提交按钮
		FloatPanel.disableCommand('SaveRegion', true,true);	
		FloatPanel.disableCommand('SaveRegionAndNext', true,true);
	}
	AfterRenderCallBack();
}

function focusCssValue(){
	var eCssText = $('CssText');
	eCssText.style.color = '#414141';
	eCssText.select();
}
function keydownCssValue(){
	$('CssText').style.color = 'black';
	$('CssText').style.fontStyle = 'normal';
	$('CssText').setAttribute('bChange', true);
}
function Valid(){
	var dom = $('RName');
	if(dom.value.trim() == ''){
		alert(wcm.LANG.CHANNEL_90 || '导读名称不能为空!');
		dom.focus();
		return false;
	}
	var els = [['Width', wcm.LANG.CHANNEL_91 || '宽度'], ['RowNumber', wcm.LANG.CHANNEL_99 || '行数'], ['CellNum', wcm.LANG.CHANNEL_100 || '列数']];
	for (var i = 0; i < els.length; i++){
		var dom = $(els[i][0]);
		if(dom.value.trim() == ''){
			alert(String.foarmt('导读{0}不能为空!',els[i][1]));
			dom.focus();
			return false;
		}
		//解决在ff下正则校验不对的问题，正则直接量
		var exp = new RegExp(/\d+/g);
		exp.lastIndex=0;
		if(!exp.test(dom.value)){
			alert(String.foarmt('导读{0}必须为数字!',els[i][1]));
			dom.focus();
			return false;
		}
	}
}

function SaveRegion(){
	if(Valid() === false) return false;
	if((!$('CssText').getAttribute('bChange'))||$('CssText').getAttribute('bChange')=="false"){
		$('CssText').value = "";
	}
	BasicDataHelper.Call('wcm6_regioninfo', 'existsSimilarName', {ObjectId : $('ObjectId').value, channelId : $('ChannelId').value,WebSiteId : $('WebSiteId').value, Rname : $('RName').value}, true, 
		function(_transport,_json){
			var bExsit = $a(_json, 'result');
			if(bExsit == 'true'){
				 alert(String.format('导读名称【{0}】已经存在，请重新输入！',$('RName').value));
				 return;
			}else{
				BasicDataHelper.Call('wcm6_regioninfo', 'save', 'data', true, RegionSaved,
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
function RegionSaved(transport, json){
	$('ObjectId').value = $v(json, 'result');
	if(window.doNextStep){
		wcm.CrashBoarder.get('Trs_Region_Set_Deatail').show({
            maskable:true,
            reloadable : true,
            title : '导读详细设置页面',
            src : './region/cell.html',
            width:'850px',
            height:'400px',
            params : {
				channelId : $('ChannelId').value || 0, 
				websiteId : $('WebSiteId').value || 0,
				objectId : $('ObjectId').value || 0
            },
			callback : function(_arg){
				notifyFPCallback(_arg);
			}
        });
		FloatPanel.hide();
	}else{
		notifyFPCallback($('ObjectId').value);
	}
}
function SaveRegionAndNext(){
	if(Valid() === false) return false;
	window.doNextStep = true;
	SaveRegion();
	return false;
}
function ShowDialog4wcm52Style(_url, _nWidth, _nHeight){
	var nWidth = _nWidth , nHeight = _nHeight; 
	var nLeft	= (window.screen.availWidth - nWidth)/2;
	var nTop	= (window.screen.availHeight - nHeight)/2;

	var sFeatures	= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
						+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
						+ "center: Yes; scroll:No;help: No; resizable: No; status: No;";
	try{
		var bResult = showModalDialog(_url, window, sFeatures);
		return bResult;
	}catch(e){
		alert((wcm.LANG.CHANNEL_104 || "您的IE插件已经将对话框拦截!\n")
				+ (wcm.LANG.CHANNEL_105 || "请将拦截去掉-->点击退出-->关闭IE,然后重新打开IE登录即可!\n")
				+ (wcm.LANG.CHANNEL_106 || "给您造成不便,TRS致以深深的歉意!"));		
	}
	return true;
}
/*页面数据加载完成之后执行的回调*/
function AfterRenderCallBack(){
	InitValidation();
}
function checkFieldName(){
	if($('ObjectId').value > 0) return;
	BasicDataHelper.Call('wcm6_regioninfo', 'existsSimilarName', {channelId : $('ChannelId').value,WebSiteId : $('WebSiteId').value, Rname : $('RName').value}, true, 
		function(_transport,_json){
			var bExsit = $a(_json, 'result');
			if(bExsit == 'true'){
				ValidationHelper.failureRPCCallBack(String.format('导读名称【{0}】已经存在，请重新输入！',$('RName').value));
			}else{
				ValidationHelper.successRPCCallBack();
			}
		},
		function(){
			 
		}
	);
}
function InitValidation(){
	//注册校验成功时执行的回调函数
	ValidationHelper.addValidListener(function(){
		FloatPanel.disableCommand('SaveRegion', false);
		FloatPanel.disableCommand('SaveRegionAndNext', false);		
	});

	//注册校验失败时执行的回调函数
	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand('SaveRegion', true);
		FloatPanel.disableCommand('SaveRegionAndNext', true);
	});

	//初始化页面中需要校验的元素
	ValidationHelper.initValidation();
}