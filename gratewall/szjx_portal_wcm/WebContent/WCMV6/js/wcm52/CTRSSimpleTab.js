//中文

var TRSSIMPLETAB_PARENT_ELEMENT			= "TD";
var TRSSIMPLETAB_LINK_TARGET			= "_self";
var TRSSIMPLETAB_TAB_CONTENT_ID			= "id_TRSSimpleTab";

function getParentElement(_el, _sTagName){
	var elTemp = _el;	
	if(elTemp == null)return null;	

	for(var i=0; i<15; i++){	
		elTemp = elTemp.parentElement;

		if(elTemp == null)return null;
		if(elTemp.tagName == _sTagName)return elTemp;		
	}
}
function CTRSSimpleTab_openItem(_elItemA, _nIndex){
	var nPreIndex	= this.nCurrIndex;

	//Hidden PreTab
	var elPreTabContent = document.all(TRSSIMPLETAB_TAB_CONTENT_ID + nPreIndex);
	if(elPreTabContent)
		elPreTabContent.style.display = "none";

	//Display CurrTab
	var elTabContent = document.all(TRSSIMPLETAB_TAB_CONTENT_ID + _nIndex);
	if(elTabContent)
		elTabContent.style.display = "inline";

	this.nCurrIndex = _nIndex;
	//CTRSAction_alert("this.toHTML():"+this.toHTML());
	//CTRSAction_alert("elTabParent:"+elTabParent.tagName);
	document.all(TRSSIMPLETAB_TAB_CONTENT_ID).innerHTML = this.toHTML();



	return true;
}

function CTRSSimpleTab_size(){
	return this.arTabItems.length;
}

function CTRSSimpleTab_addItem(_sTitle, _sHref, _bDisabled){
	var nIndex = this.size();
	this.arTabItems[nIndex] = new CTRSItem(_sTitle, _sHref, this, nIndex, _bDisabled);
}

function CTRSSimpleTab_draw(){
	document.write("<div id=\""+TRSSIMPLETAB_TAB_CONTENT_ID+"\">"+this.toHTML()+"</div>");	
}

function CTRSSimpleTab_onClickItem(_nIndex){
	var nIndex = _nIndex || 0;
	var oItem = this.arTabItems[nIndex];
	if(oItem == null) return;

	if(oItem.bDisabled)
		return;

	try{
		eval(oItem.onClickFunc);
	} catch(e) { return; }
}

function CTRSSimpleTab_toHTML(){
	var sHTML = ""
			+"<TABLE border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=100% " + (this.bDisabled?"disable=true":"");

	var nCount = this.size();
	for(var i=0; i<nCount; i++){
		//CTRSAction_alert(this.arTabItems[i].toHTML());
		sHTML += this.arTabItems[i].toHTML();
	}

	sHTML += "</TABLE>";

	if (this.bDisabled)
	{
		CTRSAction_alert ("disabled tag: " + sHTML);
	}
	return sHTML;
}

function CTRSItem_isFirst(){
	return (this.nIndex == 0);
}

function CTRSItem_isLast(){
	return (this.nIndex == (this.oTRSTab.size()-1));
}

function CTRSItem_toHTML() {
	var bCurrIndex			= (this.oTRSTab.nCurrIndex == this.nIndex);
	//var sSeperateTdHeight	= (this.isLast()?(this.nIndex<3?"height=90%":"height=80%"):"height=0");
	var sImgAfter			= (bCurrIndex?"":"a");
	var sClickFunc	= "onclick=\""+this.onClickFunc+"return false;\"";
	
	var nHeight = this.sTitle.length*17;

	var sHTML = "<TR>"
			+"<TD width='100%' height=\""+(nHeight+10)+"\" " + (this.bDisabled?"disabled=true style=\"cursor:not-allowed\" ":"")+ ">"
			+'<table width="31" height="90" border="0" cellpadding="0" cellspacing="0" class="navtitle12">\
			   <tr> \
				  <td width="31" height="16"><img src="../images/include/card1'+sImgAfter+'.gif" width="31" height="21"></td>\
				</tr>\
				<tr>\
				  <td height="48" align="center" valign="top" background="../images/include/card2'+sImgAfter+'.gif">';
	if (this.bDisabled)
	{
		sHTML = sHTML + this.sTitle;		
	} else{
		sHTML = sHTML 
		+"<A  class='navtitle12' href=\""+this.sHref+"\" " + sClickFunc
		+" target=\""+TRSSIMPLETAB_LINK_TARGET+"\" "
		+">"+this.sTitle.split('').join('<br>')+"</A>"	;		
	}
	sHTML = sHTML +
		   '</td>\
        </tr>\
        <tr>\
          <td height="16"><img src="../images/include/card3'+sImgAfter+'.gif" width="31" height="21"></td>\
        </tr>\
      </table>'
			+"</TD></TR>";
	if(this.isLast()){
		var height = this.nIndex<3?"height=90%":"height=80%"
		/*
		sHTML += "<TR><TD " + height + " style='BACKGROUND:url(\"../images/include/card2a.gif\");' width='100%'></TD>"
			+"</TR>";
		*/
		sHTML += "<TR><TD " + height + " style='BACKGROUND:url(\"../images/include/card4.gif\") repeat-y; width:2px;'></TD></TR>";
	}
	
	return sHTML;
}

function CTRSItem_draw(){
	document.write(this.toHTML());
}

function CTRSItem(_sTitle, _sHref, _oTRSTab, _nIndex, _bDisabled){
//Define Properties
	this.sTitle			= _sTitle;
	this.sHref			= _sHref || "##";
	this.oTRSTab		= _oTRSTab;
	this.nIndex			= _nIndex;
	this.bDisabled		= _bDisabled;

	var sHref = this.sHref;
	var nPose = sHref.toUpperCase().indexOf("JAVASCRIPT:");
	this.onClickFunc = "TRSSimpleTab.openItem(this, "+this.nIndex+");";
	if(nPose>=0){
		this.onClickFunc += this.sHref.substring(nPose + "JAVASCRIPT:".length);
	}
	this.onClickFunc += ";";

//Define Methods
	this.isFirst		= CTRSItem_isFirst;
	this.isLast			= CTRSItem_isLast;
	this.draw			= CTRSItem_draw;
	this.toHTML			= CTRSItem_toHTML;
	
}

function CTRSSimpleTab(){
//Define Properties
	this.arTabItems	= new Array();	
	this.nCurrIndex	= -1;

//Define Methods
	this.size			= CTRSSimpleTab_size;
	this.addItem		= CTRSSimpleTab_addItem;
	this.draw			= CTRSSimpleTab_draw;
	this.toHTML			= CTRSSimpleTab_toHTML;
	this.openItem		= CTRSSimpleTab_openItem;
	this.onClickItem	= CTRSSimpleTab_onClickItem;
}
var TRSSimpleTab = new CTRSSimpleTab();
