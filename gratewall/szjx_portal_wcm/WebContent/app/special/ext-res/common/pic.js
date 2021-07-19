/*图片轮转JS调用接口*/ 
/*焦点滚动图，参数分别表示：图片宽度(200)，图片高度(100)，图片说明文字高度(25)，图片地址（"地址1|地址2|地址3"），链接地址（（"地址1|地址2|地址3"），说明文字（"文字1|文字2|文字3"）*/

function GetFlashHTML(focus_width,focus_height,text_height,pics,links,texts,sRootURL)
{
   var path = "pixviewer.swf";
   if(window.BasePath){
	  path = sRootURL + "images/resources/pixviewer.swf";
   }
   var swf_height=focus_height+text_height;
   var aHtml = '<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="'+ focus_width +'" height="'+ swf_height +'"><param name="allowScriptAccess" value="sameDomain"><param name="movie" value="' + path + '"><param name="quality" value="high"><param name="bgcolor" value="#DADADA"><param name="menu" value="false"><param name=wmode value="opaque"><param name="FlashVars" value="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'"><embed src="' + path + '" wmode="opaque" FlashVars="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'" menu="false" bgcolor="#DADADA" quality="high" width="'+ focus_width +'" height="'+ swf_height +'" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" /></object>';
	return aHtml;
}