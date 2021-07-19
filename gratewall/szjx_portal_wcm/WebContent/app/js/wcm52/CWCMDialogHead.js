function CWCMDialogHead_draw(_sTitle, _bNoHelp,_sParameters){
	var sHTML = ""
		+"<TABLE width=\"100%\" height=\"30\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" 
		+"<!--~--- ROW1 ---~-->" 
		+"<TR>" 
		+"  <TD height=\"25\">" 
		+"  <!--~== TABLE2 ==~-->" 
		+"  <TABLE width=\"100%\" height=\"39px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" background=\"../images/wcm52/window_general_them_title_bg.jpg\">" 
		+"  <!--~--- ROW2 ---~-->" 
		+"  <TR>" 
		+"      <TD width=\"17\" align=center></TD>" 
		+"    " 
		+"  <TD class=\"windowname\">"+_sTitle+"</TD>" 
		+"" 
		+"		<TD width=\"52\" valign=\"bottom\" align=center background=\"../images/wcm52/window_general_them_hinder_.jpg\">" 
		+"			 <!--~== TABLE3 ==~-->" 
		+"			 <TABLE width=\"52\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" 
		+"			 <!--~--- ROW3 ---~-->" 
		+"			 <TR>" 
		+"				<TD width=\"50\">" + (_bNoHelp?"&nbsp;":"<A href=\"#\" class=\"top_white_link\" onClick=\"goforHelp();\">" + (wcm.LANG.WCM52_ALERT_14 || "帮助") + "</A>") + "</TD>" 
		+"			 </TR>" 
		+"			 <!--~- END ROW3 -~-->" 
		+"			 </TABLE>" 
		+"			 <!--~ END TABLE3 ~-->" 
		+"		</TD>" 
		+"  </TR>" 
		+"  <!--~- END ROW2 -~-->" 
		+"  </TABLE>" 
		+"  <!--~ END TABLE2 ~-->" 
		+"  </TD>" 
		+"</TR>" 
		+"<!--~- END ROW1 -~-->" 
		+"<!--~--- ROW4 ---~-->" 
		+"<!--~- END ROW4 -~-->" 
		+"</TABLE>" ;
	document.write(sHTML);
}
function CWCMDialogHead(){

//Define Methods
	this.draw	= CWCMDialogHead_draw;
	
}

function importScriptSrc(_src){
	document.write("<script src=\""+_src+"\"><\/script>");
}

function goforHelp(){
	
	var sUrl;
	sUrl = window.location.href;
	var oTRSAction = new CTRSAction("../help/wcmhelp_list.jsp");
	oTRSAction.setParameter("JspUrl", sUrl);
	oTRSAction.doOpenWinAction(800, 700);
}

var WCMDialogHead = new CWCMDialogHead();
try{
	CTRSAction;
	}catch(ex){
		importScriptSrc("../js/wcm52/CTRSHashtable.js");
		importScriptSrc("../js/wcm52/CTRSRequestParam.js");
		importScriptSrc("../js/wcm52/CTRSAction.js");	
	}