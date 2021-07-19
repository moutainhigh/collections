//中文
var TYPE_DEFAULT_BUTTON					= 1;
var TYPE_ROMANTIC_BUTTON				= 2;
var TYPE_LINK_BUTTON					= 3;
var TYPE_SMALLROMANTIC_BUTTON			= 4;


function CTRSButtons_draw(){
	var bNewLineButtonStart = true, sHTML;
	
	for(var i=0; i<this.arTRSButton.length; i++){
		//IF Seperator
		if(this.arTRSButton[i] != null && this.arTRSButton[i].constructor == TRSSeperator){
			bNewLineButtonStart = true;
			if(!bNewLineButtonStart){
				sHTML = "</TR></TABLE>\n" 					
				document.write(sHTML);
			}
			continue;
		}

		if(bNewLineButtonStart){
			var sHTML = "<TABLE border=\"0\" cellpadding=\"0\" cellspacing=\""+this.cellSpacing+"\">\n" 
				+"<TR>\n";
			document.write(sHTML);
			bNewLineButtonStart = false;
		}

		if(this.cellSpacing==0){
			document.write("<TD width=10>&nbsp;&nbsp;</TD>");
		}

		sHTML = "<TD title=\""+(this.arTRSButton[i].sTitle)+"\">\n" 					
		document.write(sHTML);
		
		if(this.arTRSButton[i] != null && this.arTRSButton[i].constructor == CTRSButton){
			this.arTRSButton[i].draw();
		}else{
			document.write("["+(i+1)+"]Button invalid!");
		}

		sHTML = "</TD>\n" 					

		if(this.cellSpacing==0){
			document.write("<TD width=10>&nbsp;&nbsp;</TD>");
		}

		document.write(sHTML);			
	}

	if(!bNewLineButtonStart){
		sHTML = "</TR></TABLE>\n" 					
		document.write(sHTML);
	}		
}

function CTRSButton_draw(){		
	document.write(this.toHTML());
}

function CTRSButton_toHTML(){		
	var sHTML ;
	switch(this.nType){
		case TYPE_LINK_BUTTON:
			if (this.sStyleName.indexOf("_disable")>0)
			{ // disabled button
				sHTML = ""
				+"<TABLE disable=true class=\""+this.sStyleName+"\">" 
				+"	<TR>" 
				+"		<TD><IMG src=\""+this.sButtonImgSrc+"\"></TD>" 
				+"		<TD nowrap>"+this.sText+"</TD>" 
				+"	</TR>" 
				+"</TABLE>";
			} else {
				sHTML = ""
				+"<TABLE class=\""+this.sStyleName+"\">" 
				+"	<TR onclick=\"TRSButton_gotoLink(this);\">" 
				+"		<TD><IMG src=\""+this.sButtonImgSrc+"\"></TD>" 
				+"		<TD nowrap><A class=button_link "
				+(this.sClickFunc == null?"":" onclick=\""+this.sClickFunc+"\" ")
				+" href=\""+this.sLink+"\" target=\""+this.sTarget+"\">"+this.sText+"</A></TD>" 
				+"	</TR>" 
				+"</TABLE>";
			} // end of if...else...
			break;
		case TYPE_ROMANTIC_BUTTON:
			if (this.sStyleName.indexOf("_disable")>0)
			{ // disabled button
				sHTML = ''
				+'<TABLE disable=true width="100" border="0" cellspacing="0" cellpadding="0"  style="cursor:not-allowed; filter:GRAY">' 
				+'	<TR>' 
			} else {
				sHTML = ''
				+'<TABLE width="100" border="0" cellspacing="0" cellpadding="0"  style="cursor:hand;">' 
				+'	<TR onclick="'+this.sClickFunc+'">' 
			} // end of if...else...

			sHTML = sHTML +'		<TD width="4"><img src="../images/button_left.gif" width="4" height="24"></TD>' 
			+'		<TD nowrap align="center" background="../images/button_bg_line.gif">'+this.sText+'</TD>'
			+'		<TD width="4"><IMG src="../images/button_right.gif" width="4" height="24"></TD>'
			+"	</TR>" 
			+"</TABLE>";
			break;
		case TYPE_SMALLROMANTIC_BUTTON:
			if (this.sStyleName.indexOf("_disable")>0)
			{ // disabled button
				sHTML = ''
				+'<TABLE disable=true width="50" border="0" cellspacing="0" cellpadding="0"  style="cursor:not-allowed; filter:GRAY">' 
				+'	<TR>' 
			} else {
				sHTML = ''
				+'<TABLE width="50" border="0" cellspacing="0" cellpadding="0"  style="cursor:hand;">' 
				+'	<TR onclick="'+this.sClickFunc+'">' 
			} // end of if...else...

			sHTML = sHTML +'		<TD width="4"><img src="../images/button_left.gif" width="4" height="24"></TD>' 
			+'		<TD nowrap align="center" background="../images/button_bg_line.gif">'+this.sText+'</TD>'
			+'		<TD width="4"><IMG src="../images/button_right.gif" width="4" height="24"></TD>'
			+"	</TR>" 
			+"</TABLE>";
			break;
		case TYPE_DEFAULT_BUTTON:
		default:
			if (this.sStyleName.indexOf("_disable")>0)
			{ // disabled button
				sHTML = ""
				+"<TABLE disabled=true class=\""+this.sStyleName+"\">" 
				+"	<TR>" ;
			} else { // enabled button
				sHTML = ""
				+"<TABLE class=\""+this.sStyleName+"\">" 
				+"	<TR onclick=\""+this.sClickFunc+"\">" 
			} // end of if...else...

			sHTML = sHTML + "		<TD><IMG src=\""+this.sButtonImgSrc+"\"></TD>" 
			+"		<TD nowrap>"+this.sText+"</TD>" 
			+"	</TR>" 
			+"</TABLE>";

			break;		
	} // end of switch...case...

	return sHTML;
}


function TRSSeperator(){
}


function CTRSButton(_sText, _sClickFunc, _sButtonImgSrc, _sToolTip,  _sStyleName){
	this.sText			= _sText		|| "Default";
	this.sClickFunc		= _sClickFunc	|| "";
	this.sButtonImgSrc	= _sButtonImgSrc|| "../images/button_default.gif";
	this.sTitle			= _sToolTip		|| this.sText;
	this.sStyleName		= _sStyleName	|| "bt_table";
	this.sLinkHref		;
	this.sTarget		;	
	this.nType			= TYPE_DEFAULT_BUTTON;

	this.draw			= CTRSButton_draw;
	
	this.toHTML			= CTRSButton_toHTML;

}


function TRSButton_gotoLink(_elTR){
	var arA = _elTR.getElementsByTagName("A");
	if(arA == null || arA.length<=0){
		CTRSAction_alert("Not Deifine!");
		return;
	}
	arA[0].click();
}



function CTRSButtons(_sStyleName, _nType){
	this.nType		 =	_nType		||	TYPE_DEFAULT_BUTTON;
	this.cellSpacing = "3";	
	this.styleName	 = _sStyleName	||	"bt_table";	
	this.arTRSButton = [];
	
	
	//Define Methods
	this.addTRSButton		= function(_sText, _sClickFunc, _sButtonImgSrc, _sToolTip, _sStyleName){
		var oTRSButton = new CTRSButton(_sText, _sClickFunc, _sButtonImgSrc, _sToolTip, _sStyleName || this.styleName);
		oTRSButton.nType = this.nType;
		this.arTRSButton[this.arTRSButton.length] = oTRSButton;
	}

	this.addTRSDisableButton= function(_sText, _sClickFunc, _sButtonImgSrc, _sToolTip){
		this.addTRSButton(_sText, _sClickFunc, _sButtonImgSrc, _sToolTip, "bt_table_disable");		
	}

	this.addTRSLinkButton	= function(_sText, _sButtonImgSrc, _sLink, _sTarget, _sClickFunc,  _sToolTip, _sStyleName){
		var oTRSButton = new CTRSButton(_sText, _sClickFunc, _sButtonImgSrc, _sToolTip, _sStyleName || this.styleName);

		oTRSButton.nType	= TYPE_LINK_BUTTON;
		oTRSButton.sLink	= _sLink	|| "";
		oTRSButton.sTarget	= _sTarget	|| "";

		this.arTRSButton[this.arTRSButton.length] = oTRSButton;
	}

	this.addTRSSeperator	= function(){
		this.arTRSButton[this.arTRSButton.length] = new TRSSeperator();
	}

	this.draw				= CTRSButtons_draw;	
}
