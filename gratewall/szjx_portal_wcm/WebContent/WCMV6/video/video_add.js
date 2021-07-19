/**
 * 提交视频表单的信息.
 */
function saveMetaInfo() {
	if ($('DocTitle').value == null || $('DocTitle').value == '') {
		alert("请您输入标题");
		$('DocTitle').focus();
		return;
	}

//	var postData = Form.serialize("frmMeta");
//	var oPost = postData.toQueryParams();
//	var oPost = com.trs.ajaxframe.PostData.form("frmMeta");
	var metadata = eval('(' + $("jsonMeta").value + ')');
	var oPost = new Object();
	Object.extend(oPost, {
			DocContent : "正文",
			//DOCHTMLCON : sDocHtml,
			//DocLink : sDocLink,
			//DocFileName : sDocFileName,
			ObjectId : CurrDocId,
			ChannelId : DocChannelId,
			DocTitle : $('DocTitle').value,
			SubDocTitle : $('SubDocTitle').value,
			DOCKEYWORDS : $('DOCKEYWORDS').value,
			DocAbstract : $('DocAbstract').value,
			DocSource : $('DocSource').value,
			//TitleColor : PageContext.TitleColor||'',
			ATTRIBUTE : "TOKEN=" + metadata.token + "&THUMB=" + metadata.token + ".jpg" + "&DURATION=" + metadata.duration + "&WIDTH=" + metadata.width + "&HEIGHT=" + metadata.height + "&FPS=" + metadata.fps + "&BITRATE=" + metadata.bps + "&TYPE=U"
			//ATTRIBUTE : "TOKEN=" + $('token').value + "&TYPE=U"
	});
	// 视频内容保存
//	alert("location:" + location.href + "\n[video_add.js]\noPost=[" + oPost + "]\nparseSource=[" + Object.parseSource(oPost) + "]");
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call('wcm6_document', 'save',oPost, true,
		function(_transport, _json){
//			alert("finish!");
			Exit();
		}
	);
	// TODO 保存发布信息
	// TODO 保存相关视频或文档信息
	// TODO 发布视频
}

function saveNewRecord() {
	if ($('DocTitle').value == null || $('DocTitle').value == '') {
		alert("请您输入标题");
		$('DocTitle').focus();
		return;
	}

	var metadata = eval('(' + $("jsonMeta").value + ')');
	var oPost = new Object();
	Object.extend(oPost, {
			DocContent : "正文",
			ObjectId : CurrDocId,
			ChannelId : DocChannelId,
			DocTitle : $('DocTitle').value,
			SubDocTitle : $('SubDocTitle').value,
			DOCKEYWORDS : $('DOCKEYWORDS').value,
			DocAbstract : $('DocAbstract').value,
			DocSource : $('DocSource').value,
			ATTRIBUTE : "TOKEN=" + metadata.token + "&THUMB=" + metadata.token + ".jpg" + "&DURATION=" + metadata.duration + "&WIDTH=" + metadata.width + "&HEIGHT=" + metadata.height + "&FPS=" + metadata.fps + "&BITRATE=" + metadata.bps + "&TYPE=R"
	});

	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call('wcm6_document', 'save',oPost, true,
		function(_transport, _json){
			Exit();
		}
	);
}

function Exit(){
//TODO
	if(window.opener){
		window.opener.$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
	}
	window.opener = null;
	window.close();
}
