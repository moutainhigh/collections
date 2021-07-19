function multiSaveMetaInfo() {
	var arr = document.getElementsByClassName('uploadedInfo');
	$("s").disabled = true;
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	var aCombine = [];
	for(index=0; index<arr.length;index++){
		var element = arr[index];
		var DocTitle = element.getAttribute('doctitle');
		var lastDot = DocTitle.lastIndexOf('.');
		var nameWithoutExt = (lastDot == -1) ? DocTitle : DocTitle.substring(0, lastDot);
		var metadata = eval('(' + element.getAttribute('metadata') + ')');
		var oPost = new Object();
		Object.extend(oPost, {
				DocContent : "正文",
				ObjectId : 0,
				ChannelId : DocChannelId,
				DocTitle : nameWithoutExt,
				SRCFILENAME : metadata.srcFileName,
				TOKEN : metadata.token,
				THUMB : metadata.token + ".jpg",
				DURATION : metadata.duration,
				WIDTH : metadata.width,
				HEIGHT : metadata.height,
				FPS : metadata.fps,
				BITRATE : metadata.bps,
				CREATETYPE : 10
		});
		aCombine.push(oHelper.Combine('wcm61_video', 'save', oPost));
	}
	

	oHelper.MultiCall(aCombine, function(_transport, _json){
		//fjh@2008-8-22 提示批量上传失败
		var jsonStr = Object.parseSource(_json);
		if (jsonStr.indexOf("-1") != -1){
			alert("批量上传视频失败，WCM请求TRSVIDEO处理视频失败。" );
		}
		Exit();
	});
}

function Exit() {
	if (window.opener) {
		window.opener.PageContext.loadList();
	}
//	var arr = document.getElementsByClassName('uploadedInfo');
/*	curElement.className = 'savedDoc';
	var originInnerHTML = curElement.innerHTML;
	curElement.innerHTML = "<font color='green'>已保存: </font>" + originInnerHTML;*/
	window.opener = null;
	window.close();
}