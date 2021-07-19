/*
 * History			Who			What
 * 2006-4-19		caohui		增加Waiting描述内容的指定
 */
var m_nCurrWidth	=	0;
var m_nBgWidth		=	-1;
var m_bStart		= false;
var m_nCurrTime		= 0;
var OFFSET_HEIGHT	= 40;
var OFFSET_WIDTH	= 40;
var m_nHeight		= 340;
var m_nWidth		= 400;
var MAX_HEIGHT		= 150;
var MAX_WIDTH		= 400;

function RunningProcessBar_CheckClick(_bFirst){
	//RunningProcessBar_init();

	if(!m_bStart){
		document.getElementById("waiting").style.visibility = 'hidden';
		m_nCurrWidth	= 0;
		m_nBgWidth		=-1;
		m_nCurrTime		= 0;
		return true;
	}

	if(m_nBgWidth == -1){
		m_nBgWidth = document.getElementById("waiting").clientWidth;		
	}
	
	
	if (!_bFirst){
//		if ( m_nCurrWidth < m_nBgWidth*0.98 ){
//			m_nCurrWidth += (m_nBgWidth - m_nCurrWidth) * 0.005;
//			document.sbar.width = m_nCurrWidth;
//			setTimeout('RunningProcessBar_CheckClick(false);',250);
//		}		
	}
	else{
		document.getElementById("waiting").style.pixelTop = (document.body.offsetHeight - document.getElementById("waiting").clientHeight) / 2 + document.body.scrollTop;
		document.getElementById("waiting").style.pixelLeft = (document.body.offsetWidth - document.getElementById("waiting").clientWidth) / 2 + document.body.scrollLeft;
		document.getElementById("waiting").style.visibility = 'visible';
		document.getElementById("waiting").style.display = '';
		RunningProcessBar_CheckClick(false);
		m_nCurrWidth = 1;		
	}
	
	m_nCurrTime++;
	document.getElementById("waitingTime").innerHTML = m_nCurrTime+ "秒";
	setTimeout('RunningProcessBar_CheckClick(false);', 1000);
}



function RunningProcessBar_hideBodyChildren(_bDisplay){
	var arChildren = document.body.childNodes;
	var nChildrenCount = arChildren.length;
	for(var i=0; i<nChildrenCount; i++){
		if(arChildren[i].NotInContral)continue;
		
		if(arChildren[i].style){
			if(_bDisplay){
				if(arChildren[i].RunningDowith){
					arChildren[i].style.display = "";
				}
			}else{
				if(arChildren[i].style.display != "none"){
					arChildren[i].style.display = "none";
					arChildren[i].RunningDowith	= 1;
				}
			}
			
		}
	}
}

var m_oLogImg = null;
function RunningProcessBar_draw(_sDesc){
	var sDesc = _sDesc ||  "系统正在运行中,请耐心等待.....";
	var sWaitingID = "div_waiting";
	var oWaitingDiv = document.getElementById(sWaitingID);
	if(oWaitingDiv == null){
		var oNewDiv		= document.createElement("DIV");
		oNewDiv.id		= sWaitingID;
		oNewDiv.NotInContral = 1;
		oWaitingDiv		= document.body.appendChild(oNewDiv);		
	}

	if(oWaitingDiv.innerHTML.length<=10){	
		m_nHeight		= document.body.offsetHeight	- OFFSET_HEIGHT;
		if(m_nHeight>MAX_HEIGHT){
			m_nHeight = MAX_HEIGHT;
		}
		m_nWidth		= document.body.offsetWidth		- OFFSET_WIDTH;
		if(m_nWidth>MAX_WIDTH){
			m_nWidth = MAX_WIDTH;
		}		
/*		var sHTML = "<div id=waiting style=position:absolute;top:0px;left:0px;z-index:1;visibility:hidden>"
						+ "<table border=2 cellspacing=1 cellpadding=0 bordercolorlight=#FFFFFF"
						+" bordercolordark=#C0C0C0 bgcolor=#E0E0E0>"
						+ "<tr><td bgcolor=#E0E0E0>" 
						+ this.createBgHTML() 
						+ "</td></tr><tr><td bgcolor=#E0E0E0>"
						+ "<img src= width=1 height=10 name=sbar style=background-color:#6699cc></td></tr></table></div>";*/
		var sHTML = "<div id=waiting style='position:absolute; width:"+m_nWidth+"; height:"+m_nHeight+"; z-index:1; left: 30%; top: 35%;visibility: hidden;'>"
					+"<table width=100% height=100%  border=1 bordercolor=#000000 bordercolordark=#ffffff cellspacing=0 cellpadding=0 style='font-size:9pt'>"
					+"<tr><td height=22 align=center bgcolor='#bbbbbb' style='font-size:14px;'>" + "欢迎您使用TRS WCM系统"
					+"</td></tr><tr><td align=center bgcolor=#CCCCCC><br><span id=\"spanTitle\">"+sDesc+"</span><br>" + "等待时间：" + "<font color=blue><span id=waitingTime></span></font></td></tr></table></div>";

		
		oWaitingDiv.innerHTML		= sHTML;
	}
	oWaitingDiv.style.display	= "";
}


function RunningProcessBar_start(_sTipHTML){
	m_bStart = true;

	//1.Hide Body's Children
	this.hidBodyChildren();
	
	//2.Draw Waiting HTML
	this.draw(_sTipHTML);

	//3.Start
	RunningProcessBar_CheckClick(true);
}

function RunningProcessBar_close(){
	if(!m_bStart)
		return;

	m_bStart = false;

	//1.Display Body's Children
	this.hidBodyChildren(true);
	
	//2.Hide waiting
	var sWaitingID = "div_waiting";
	var oWaitingDiv = document.getElementById("waiting");
	if(oWaitingDiv != null){
		oWaitingDiv.style.display = "none";
	}
	m_nCurrWidth	=	0;
	m_nBgWidth		=	-1;

}

function RunningProcessBar_onLoad(){	
}


function RunningProcessBar_createBgHTML(){
	if(this.BackgroudHTML != null){
		return this.BackgroudHTML;
	}

	if(m_oLogImg == null){
		m_oLogImg = new Image();
		m_oLogImg.onload = RunningProcessBar_onLoad ;
		m_oLogImg.src = "../images/login_splash.png";		
		//CTRSAction_alert("image load");
	}

	//return '<IFRAME MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 WIDTH=498 HEIGHT=174 SCROLLING=NO '
	//		+' SRC="' + this.BackgroundURL + '"></IFRAME>';
	return '<IMG src="../images/login_splash.png" width="498" height="174">';
}

function CRunningProcessBar(){
	//Properties
	this.BackgroundURL	= "../include/running_background.html";
	this.BackgroudHTML	= null;

	//Interface
	this.start				= RunningProcessBar_start;	
	this.close				= RunningProcessBar_close;

	//Function
	this.hidBodyChildren = RunningProcessBar_hideBodyChildren;
	this.draw				= RunningProcessBar_draw;
	this.createBgHTML		= RunningProcessBar_createBgHTML;
}

var RunningProcessBar = new CRunningProcessBar();