/**
 * ls@2007-11-29 22:28 created according video_add.js_rev23534
 */
function saveNewRecord() {
	if ($('DocTitle').value == null || $('DocTitle').value == '') {
		alert("请您输入视频标题");
		$('DocTitle').focus();
		return;
	}
	if ($('DocTitle').value.byteLength() > 200)
	{
		alert("视频标题长度不能大于200");
		$('DocTitle').focus();
		return;
	}
	if ($('DOCPEOPLE').value.byteLength() > 200)
	{
		alert("首页标题长度不能大于200");
		$('DOCPEOPLE').focus();
		return;
	}
	if ($('SubDocTitle').value.byteLength() > 200)
	{
		alert("副标题长度不能大于200");
		$('SubDocTitle').focus();
		return;
	}
	if ($('DOCKEYWORDS').value.byteLength() > 200)
	{
		alert("关键字长度不能大于200");
		$('DOCKEYWORDS').focus();
		return;
	}
	var metadata = eval('(' + $("jsonMeta").value + ')');
	var oPost = new Object();
	Object.extend(oPost, {
			DocContent : "正文",
			ObjectId : 0,
			ChannelId : DocChannelId,
			DocTitle : $('DocTitle').value,
			SubDocTitle : $('SubDocTitle').value,
			DOCPEOPLE	: $('DOCPEOPLE').value,
			DOCKEYWORDS : $('DOCKEYWORDS').value,
			DocAbstract : $('DocAbstract').value,
			SRCFILENAME : metadata.token + ".flv",
			FILENAME : metadata.token + ".flv",
			THUMB : metadata.token + ".jpg",
			DURATION : metadata.duration,
			WIDTH : metadata.width,
			HEIGHT : metadata.height,
			FPS : metadata.fps,
			BITRATE : metadata.bps,
			CREATETYPE : 20,
			CONVERT_STATUS : 1
	});

	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call('wcm61_video', 'save',oPost, true,
		function(_transport,_json){
				var docId = com.trs.util.JSON.value(_json,'result');
				if(docId == -1) {
					alert("保存视频失败，WCM请求TRSVIDEO处理视频失败。TOKEN：" + metadata.token);
				}
				Exit();
			}
	);
}

function Exit(){
	if(window.opener){
		window.opener.PageContext.loadList();
	}
	window.opener = null;
	window.close();

}