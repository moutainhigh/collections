window.m_editorCfg = {};
function GetContentWindow(){
	return $(window.m_editorCfg.editorName||'simple_editor').contentWindow;
}
function GetEditor(){
	if(!GetContentWindow())return null;
	return GetContentWindow().GetEditor();
}
function GetTrueEditor(){
	if(!GetContentWindow())return null;
	return GetContentWindow().GetTrueEditor();
}
function SetHtml(_sHtml){
	if(!GetContentWindow())return;
	return GetContentWindow().SetHTML();
}
function GetHtml(){
	if(!GetContentWindow())return '';
	return GetContentWindow().GetHtml();
}
function GetText(){
	if(!GetContentWindow())return '';
	return GetContentWindow().GetText();
}
function InitEditor(){
	if(!GetContentWindow())return;
	return GetContentWindow().init();
}