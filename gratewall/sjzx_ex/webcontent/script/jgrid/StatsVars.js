var Err_NoURL ='¥ÌŒÛµƒ URLµÿ÷∑';
var skinFaceColor='#84BE3C'//'rgb(202,229,232)';
var skinBorderColor='#84BE3C';
var tabpane;
var infoidx ;
var statsrptobj=null;
var barobj;
var menuobj;
var currptobj=null;
var currptname =null;
var clWhite="#FFFFFF";
var bgcolor='#E3F1D1'//'white'//'rgb(202,229,232)';
var first=0;
var errInfo= new Array ();
errInfo[0]='<font color=red>æØ∏Ê–‘…Û∫À¥ÌŒÛ</font>';
errInfo[1]='<font color=red>…Û∫ÀŒ¥Õ®π˝</font>';
errInfo[2]='<font color=red>≥¬ ˆ–‘…Û∫À¥ÌŒÛ</font>';
function SetSkinColor(idx)
{
	if (idx==1)
	{
		skinFaceColor='#84BE3C'//'rgb(170,135,184)';//"#B7BED3"//'rgb(234,242,255)';
		bgcolor='#D3E9BA'//'rgb(232,211,227)';//'yellow';
	}
	else if (idx==2)
	{
		skinFaceColor='#B26B7B';
	    bgcolor='#F4D9DD';
    }
	else if (idx==3)
	{
		skinFaceColor='#7A437A';//'rgb(200,200,200)';
		bgcolor='#E8DAFA';//'red';
	}
	else if (idx==4)
	{
	//	skinFaceColor='#B26B7B';
	//	bgcolor='#F4D9DD)'
		skinFaceColor='#0078F0';//'rgb(200,200,200)';
		bgcolor='#D6EBFF';//'red';
}
	changeStyle(idx);
	if(first==1)
	for(i=0;i<statsrptobj.reportCount;i++)
	{
		curptr=statsrptobj.reportPtr[statsrptobj.reportCode[i]];
		curptr.SetHeadColor(bgcolor);//skinFaceColor);
	}
	first=1;
}
// Example:
// alert( readCookie("myCookie") );
function readCookie(name)
{
  var cookieValue = "";
  var search = name + "=";
  if(document.cookie.length > 0)
  {
    offset = document.cookie.indexOf(search);
    if (offset != -1)
    {
      offset += search.length;
      end = document.cookie.indexOf(";", offset);
      if (end == -1) end = document.cookie.length;
      cookieValue = unescape(document.cookie.substring(offset, end))
    }
  }
  return cookieValue;
}
// Example:
// writeCookie("myCookie", "my name", 24);
// Stores the string "my name" in the cookie "myCookie" which expires after 24 hours.

function writeCookie(name, value)
{
  var expire = "";
    expire = new Date((new Date()).getTime() + 24 * 365 * 3600000);
    expire = "; expires=" + expire.toGMTString();
  document.cookie = name + "=" + escape(value) + expire;
}

function changeStyle(n){
	for(var i=1;i<document.styleSheets.length;++i){
			if(document.styleSheets[i].href)
				document.styleSheets[i].disabled=true;
		}
	/*
	var oCss = document.createElement("LINK");
	oCss.rel="stylesheet";
	oCss.type="text/css";
	oCss.href=(n?"css/style/Sty0"+n+".css":"css/style/main.css");
	document.body.appendChild(oCss)
	*/
	try{
		document.createStyleSheet(n?"styles/jgrid/style"+n+".css":"styles/jgrid/style4.css");
	}catch(e){
		//not ie?
	}
	writeCookie("cc_count_6_style",n);
}

var sCookiesStyle = readCookie("cc_count_6_style");
if(!parseInt(sCookiesStyle)) sCookiesStyle=1;
SetSkinColor(sCookiesStyle);
