// define for cb
function init(_json) {
	var bSuccess = $v(_json, 'Report.IS_SUCCESS').trim().toLowerCase() == 'true';
	var sTitle = String.format('当前编辑的模板 - {0}',$v(_json, 'Report.TITLE')) ;
	if(bSuccess) {
		Element.update($('spTitle'), sTitle);
		$('spTitle').style.color = 'black';
		Element.hide('divErrInfo');
		$('imClue').src = WCMConstants.WCM6_PATH+'js/source/wcmlib/dialog/resource/2.gif';
	}else{
		var sErrInfo = $v(_json, 'Report.ERROR_INFO');
		Element.update($('spTitle'), sTitle);
		$('spTitle').style.color = 'black';
		$('divErrInfo').innerHTML = sErrInfo;
		Element.show('divErrInfo');
		$('imClue').src = WCMConstants.WCM6_PATH+'js/source/wcmlib/dialog/resource/4.gif';
	}

	//if(_json.isJustCheck) {
	//	Element.hide('trOnError');
	//	Element.show('trOnSuccess');
	//}else{
	//	Element.hide('trOnSuccess');
	//	Element.show('trOnError');
	//}


}

function closeframe(_bResume){
	$('imClue').src = '../js/com.trs.dialog/img/7.gif';
	var cbr = wcm.CrashBoarder.get(window);
	if(_bResume){
		cbr.notify();
	}
	cbr.hide();
}

window.m_cbCfg = {
	btns : [
		{
			id : 'btnSkip',
			text : wcm.LANG.TRUE||'确定',
			cmd : function(){
				closeframe(true);
				//return false;
			}
		},
		{
			id : 'btnReturn',
			text : wcm.LANG.CANCEL||'取消',
			cmd : function(){
				closeframe(false);
			}
		}
	]
}
function renderAccessibility(id){
	var cnt = getAccessibilityExample();
	cnt.style.display = 'none';
	var html = [];
	html.push('<a class="accessibility_close" href="#" onclick="closeAccessibilityExample();return false;">'+(wcm.LANG.CLOSE||'关闭')+'</a><div class="accessibility_title">'+(wcm.LANG.TEMPLATE_56||'无障碍处理建议')+'</div>');
	html.push('<p>');
	html.push($(id).innerHTML);
	html.push('</p>');
	cnt.innerHTML = html.join('');
	cnt.style.display = '';
	cnt.style.zIndex = 992;
}
function closeAccessibilityExample(){
	var cnt = getAccessibilityExample();
	cnt.style.display = 'none';
}
function getAccessibilityExample(){
	var cnt = $('accessibility_example');
	if(!cnt){
		cnt = document.createElement('DIV');
		cnt.id = 'accessibility_example';
		cnt.className = 'accessibility_example';
		cnt.style.display = 'none';
		document.body.appendChild(cnt);
	}
	return cnt;
}