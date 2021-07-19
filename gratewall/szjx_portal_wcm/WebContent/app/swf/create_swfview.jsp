<%
// 提取出需要转换的文件
String sFileName = currDocument.getPropertyAsString("DOCFILENAME");
String sAbsoluteFileName = null, sFileNamePre = null;
if(!CMyString.isEmpty(sFileName)){
	sFileNamePre = sFileName.substring(0,sFileName.lastIndexOf("."));
	sAbsoluteFileName = FilesMan.getFilesMan().mapFilePath(
			sFileName, FilesMan.PATH_LOCAL)
			+ sFileNamePre + ".swf";
}

if(currDocument.getType() == Document.DOC_TYPE_FILE 
	&& sAbsoluteFileName != null 
	&& CMyFile.fileExists(sAbsoluteFileName)){
	

	String sSwfFileName = sFileNamePre + ".swf";

%>

	<script type="text/javascript" src="../swf/js/swfobject/swfobject.js"></script>
	<script type="text/javascript" src="../swf/js/flexpaper_flash.js"></script>
	<script type="text/javascript"> 
		<!-- For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. --> 
		var swfVersionStr = "10.0.0";
		<!-- To use express install, set to playerProductInstall.swf, otherwise the empty string. -->
		var xiSwfUrlStr = "playerProductInstall.swf";
		
		var flashvars = { 
			  SwfFile : escape("../file/read_file.jsp?FileName=<%=sSwfFileName%>"),
			  Scale : 0.6, 
			  ZoomTransition : "easeOut",
			  ZoomTime : 0.5,
			  ZoomInterval : 0.1,
			  FitPageOnLoad : true,
			  FitWidthOnLoad : true,
			  PrintEnabled : false,
			  FullScreenAsMaxWindow : false,
			  ProgressiveLoading : true,
			  PrintToolsVisible : false,
			  ViewModeToolsVisible : false,
			  ZoomToolsVisible : true,
			  FullScreenVisible : true,
			  NavToolsVisible : true,
			  CursorToolsVisible : false,
			  SearchToolsVisible : true,
			  localeChain: "en_US"
		};
		var params = {}
		params.quality = "high";
		params.bgcolor = "#ffffff";
		params.allowscriptaccess = "sameDomain";
		params.allowfullscreen = "true";
		var attributes = {};
		attributes.id = "FlexPaperViewer";
		attributes.name = "FlexPaperViewer";
		swfobject.embedSWF(
			"../swf/js/PdfReader.swf", "flashContent", 
			"100%", "100%",
			swfVersionStr, xiSwfUrlStr, 
			flashvars, params, attributes);
		swfobject.createCSS("#flashContent", "display:block;text-align:left;");
	</script> 
        
    
	<div style="width:98%;height:580px;">
		<div id="flashContent"> 
			<p WCMAnt:param="create_swfview.jsp.please_install_the_lastest_flash"> 
				请安装最新版本Adobe Flash Player.
			</p> 
			<script type="text/javascript"> 
				var pageHost = ((document.location.protocol == "https:") ? "https://" :	"http://"); 
				document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
								+ pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
			</script> 
		</div>
		<div id="errNoDocument" style="padding-top:10px;">
		</div> 
	</div>
   
<%
}					
%>