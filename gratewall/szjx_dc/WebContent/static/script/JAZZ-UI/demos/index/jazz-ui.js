var theme = "";
try{
	theme = window.top.window.getThemes();
}catch(e){
}
if(!theme){
	theme = "default";
}
theme = "gongshang";
document.write('<link href="../../../external/jquery-ui/jquery-ui-1.9.2.custom.css" rel="stylesheet" type="text/css" />');
document.write('<link href="../../../lib/themes/'+theme+'/jazz-all.css" rel="stylesheet" type="text/css" />');

document.write("<script language='javascript' src='../../../external/jquery-1.8.3.js' ></script>");
document.write("<script language='javascript' src='../../../external/jquery-ui-1.9.2.custom.js' ></script>");
document.write("<script language='javascript' src='../../../lib/jazz-dependent.js' ></script>");
document.write("<script language='javascript' src='../../../lib/jazz-all.js' ></script>");


