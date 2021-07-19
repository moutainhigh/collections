var theme = "";
try{
	theme = window.top.window.getThemes();
}catch(e){
}
if(!theme){
	theme = "default";
}

document.write('<link href="../../../external/jquery-ui/jquery-ui-1.9.2.custom.css" rel="stylesheet" type="text/css" />');
document.write('<link href="../../../lib/themes/'+theme+'/jazz-all.css" rel="stylesheet" type="text/css" />');
//document.write('<script charset="UTF-8" src="../../../external/require.js" type="text/javascript"></script>');  
document.write("<script type='text/javascript' src='../../../external/jquery-1.8.3.js' charset='utf-8' ></script>");
document.write("<script type='text/javascript' src='../../../external/jquery-ui-1.9.2.custom.js' charset='utf-8'></script>");
document.write("<script type='text/javascript' src='../../../lib/jazz-all.js' charset='utf-8' ></script>");
