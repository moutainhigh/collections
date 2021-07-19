Ext.apply(String.prototype, {
	byteLength : function(){
		var length = 0;
		this.replace(/[^\x00-\xff]/g,function(){length++;});
		return this.length+length;
	}
});

/*-------------------- 上传文件的逻辑 ----------------------*/
function UpdateDocFile(){
	var sDocFileName = $("DocFile").value;
	try{
		FileUploadHelper.validFileExt(sDocFileName, ".*");
	}catch(err){
		Ext.Msg.$alert(err.message, function(){
			$("DocFile").focus();
		});
		return false;
	}
	YUIConnect.setForm('frmUploadDocFile', true, Ext.isSecure);
	YUIConnect.asyncRequest('POST',
		'../system/file_upload_dowith.jsp?FileParamName=DocFile',{
			"upload" : function(_transport){
				var sResponseText = _transport.responseText;
				FileUploadHelper.fileUploadedAlert(sResponseText,{
					succ : function(){
						$('DOCFILENAME').value = sResponseText;
					}
				});
			}
		}
	);
	return false;
}

/*-------------------- 获取文档附件 ----------------------*/
function getAppendixesXML(iType){
	/**
	 *  <OBJECTS>
	 *		<OBJECT ID="" APPFILE="" SRCFILE="" APPLINKALT="" APPFLAG="" APPDESC=""/>
	 *	</OBJECTS>
	 */
	var appendixs = PgC.m_Appendixs['Type_'+iType];
	var arr = Ext.Json.array(appendixs,"APPENDIXES.APPENDIX")||[];
	var sParams = ["APPENDIXID","APPFILE","SRCFILE","APPLINKALT","APPFLAG","APPDESC"];
	var myValue = Ext.Json.value;
	var sRetVal = '<OBJECTS>';
	for(var i=0;i<arr.length;i++){
		var oAppendix = arr[i];
		sRetVal += '<OBJECT';
		for(var j=0;j<sParams.length;j++){
			var sName = sParams[j];
			var sValue = myValue(oAppendix,sName)||'';
			if(sName=='APPENDIXID'){
				if(isNaN(sValue)) sValue = 0;
			}
			if(iType==20&&sName=='APPFILE'){
				sRetVal += ' APPFILE="'+((myValue(oAppendix,'APPFILE.FILENAME')||'')+'').escape4Xml()+'"';
			}
			else if(sName=='APPENDIXID'){
				sRetVal += ' ID="'+sValue+'"';
			}
			else{
				sRetVal += ' '+sName+'="'+(sValue+'').escape4Xml()+'"';
			}
		}
		sRetVal += '/>';
	}
	sRetVal += '</OBJECTS>';
	return sRetVal;
}

/*-------------------- 获取相关文档 ----------------------*/
function getRelationsXML(){
	/**
	 *  <OBJECTS>
	 *		<OBJECT ID="" RELDOCID=""/>
	 *	</OBJECTS>
	 */
	var arr = Ext.Json.array(PgC.m_Relations,"RELATIONS.RELATION")||[];
	var myValue = Ext.Json.value;
	var sRetVal = '<OBJECTS>';
	for(var i=0;i<arr.length;i++){
		var oRelation = arr[i];
		sRetVal += '<OBJECT ID="'+(myValue(oRelation,'RELATIONID')||0)+'" RELDOCID="'+myValue(oRelation,'RELDOC.ID')+'"/>';
	}
	sRetVal += '</OBJECTS>';
	return sRetVal;
}
/*---------------- 获取html的内容 ------------------*/
function GetHTML(_bPreview){
	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		var oWindow = oTrsEditor.contentWindow.GetTrueEditor();
		if(!_bPreview && oWindow.OfficeActiveX)
			oWindow.OfficeActiveX.UploadLocals();
		return FCK.QuickGetHtml(true,true);
	}
	return '';
}
/*---------------- 获取纯文本的内容 ------------------*/
function GetText(){
	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		return FCK.QuickGetText();
	}
	else if(Element.visible('txt_editor')){
		return $('_editorValue_').value;
	}
	return '';
}

/*---------------- 清空剪切板 ------------------*/
function notifyClearClipboard(){
	var oTrsEditor = $('_trs_editor_');
	if(oTrsEditor){
		try{
			oTrsEditor.contentWindow.ClearAutoPasteData();
		}catch(err){
			//Just skip it.
		}
	}
}

/*---------------- 切换文档类型编辑器相应改变 ------------------*/
function SwitchDocEditPanel(_nDocType){
	var nDocType = parseInt(_nDocType, 10);
	switch(nDocType){
		case 20:
			if(Element.visible('txt_editor')){
				var oTrsEditor = $('_trs_editor_');
				var eTmpDiv = document.createElement('DIV');
				eTmpDiv.innerText = $('_editorValue_').value;
				delete eTmpDiv;
				try{
					var FCK = oTrsEditor.contentWindow.GetEditor();
					oTrsEditor.contentWindow.SetHTML(eTmpDiv.innerHTML);
				}catch(err){
					alert(wcm.LANG.DOCUMENT_PROCESS_6 || 'HTML编辑器尚未加载完成，请稍后再试.');
					return;
				}
			}
			Element.hide('nothtml_editor');
			showCertainBlock(20);
			break;
		case 10:
			var sValue = $('_editorValue_').value;
			if(Element.visible('html_editor')){
				var oTrsEditor = $('_trs_editor_');
				var FCK = oTrsEditor.contentWindow.GetEditor();
				var bConfirm = confirm(wcm.LANG.DOCUMENT_PROCESS_7 || '切换到纯文本，当前文档中的字体、格式等信息都将丢失，是否确认切换？');
				if(bConfirm){
					Element.show('nothtml_editor');
					$('_editorValue_').value = FCK.QuickGetText(true,true);
					Element.hide('html_editor');
					showCertainBlock(10);
				}else{
					$("DocType").options[0].selected="selected";
					SwitchDocEditPanel(20);
				}
			}
			break;
		case 30:
			Element.show('nothtml_editor');
			showCertainBlock(30);
			break;
		case 40:
			Element.show('nothtml_editor');
			showCertainBlock(40);
			break;
	}
}
function showCertainBlock(n){
	Element.hide('file_editor');
	Element.hide('link_editor');
	Element.hide('txt_editor');
	Element.hide('file_editor');
	switch(n){
		case 10:
			Element.show('txt_editor');
			break;
		case 20:
			Element.show('html_editor');
			break;
		case 30:
			Element.show('link_editor');
			break;
		case 40:
			Element.show('file_editor');
			break;
	};
}
function UnLockMe(){
	if(CurrDocId!=0){
		LockerUtil.unlock(CurrDocId,605);
	}
}
function transTxt(_sTxt){
	if(!/<.*>/.test(_sTxt)){
		return _sTxt.replace(/\n/g,'<br>');
	}
	return _sTxt;
}
function k(s){
	return s;
}

/*---------------- 对一些基本数据的校验 ------------------*/
function doValidation(){
	if(bIsReadonly)return false;
	$('DocTitle').value = $('DocTitle').value.trim();
	var nDocType = 20;//parseInt($('DocType').value, 10);
	var filter = function(el){
		if(nDocType!=10 && el==$('frmData').DocContent)return true;
		if(nDocType!=20 && el==$('frmData').DocHtmlCon)return true;
		if(nDocType!=30 && el==$('DOCLINK'))return true;
		if(nDocType!=40 && el==$('DOCFILENAME'))return true;
		return false;
	};
	var rst = TRSValidator52.validatorForm('frmAction', true, filter);
	if(rst.valid)return true;
	Ext.Msg.$alert(rst.einfos.join('\n'), function(){
		try{rst.fstEle.focus();}catch(e){}
	});
	return false;
}

/*---------------- 预览或保存前的数据的准备 ------------------*/
function prepareDatas(_bPreview){
	var nDocType = 20;//parseInt($('DocType').value, 10);
	if(nDocType==20){
		$('frmData').DocHtmlCon.value = GetHTML(_bPreview);
		$('frmData').DocContent.value = GetText();
	}
	else if(nDocType==10){
		$('frmData').DocHtmlCon.value = '';
		$('frmData').DocContent.value = $('_editorValue_').value;
	}
	if(CurrDocId == 0 ) {
		if($("jsonMeta").value.length==0){
			alert("请上传视频！");
			return false;
		}
		var metadata = eval('(' + $("jsonMeta").value + ')');
		$('frmData').SrcFileName.value = metadata.srcFileName;
		$('frmData').Duration.value = metadata.duration;
		$('frmData').Width.value = metadata.width;
		$('frmData').Height.value = metadata.height;
		$('frmData').Fps.value = metadata.fps;
		$('frmData').Bitrate.value = metadata.bps;	
		$('frmData').sampleId.value = metadata.sampleId;		
	}
	return true;
}
function getFlowDocInfo(){
	var winFlowdoc = $('frmFlowDoc').contentWindow;
	if(!winFlowdoc.validate(false)) {
		return;
	}
	var sPostData = winFlowdoc.buildData();
	return sPostData;
}
function makePostData(){
	var PostDataUtil = com.trs.web2frame.PostData;
	var postdata = PostDataUtil.form('frmAction', k);
	postdata = Ext.apply(postdata, PostDataUtil.form('frmData', k));
	postdata = Ext.apply(postdata, PostDataUtil.form('doc_props', k));
	postdata = Ext.apply(postdata, {
		FlowDocId	: getParameter('FlowDocId') || 0
	});
	postdata = Ext.apply(postdata, PgC.m_DocExtends);
	if($('frmFlowDoc') && $('frmFlowDoc').contentWindow.bCanInFlow){
		var flowDocInfo = getFlowDocInfo();
		postdata = Ext.apply(postdata, {
			ToUserIds : flowDocInfo.ToUserIds,
			NotifyTypes : flowDocInfo.NotifyTypes,
			reflow : true
		});
	}
	return postdata;
}
Ext.ns('PgC');
/*-------------------- 页面右侧添加引用后的展现（数据update了） ----------------------*/
var m_Templates = {
	quoto_table : [
		'<table border=0 cellspacing=1 cellpadding=0 style="width:100%;table-layout:fixed;background:gray;">',
		'<tbody>',
			'<tr bgcolor="#CCCCCC" align=center valign=middle>',
				'<td width="32">', wcm.LANG.DOCUMENT_PROCESS_178 || '序号','</td>',
				'<td>', wcm.LANG.DOCUMENT_PROCESS_179 || '栏目名称','</td>',
				'<td width="32">',wcm.LANG.DOCUMENT_PROCESS_161 || '删除','</td>',
			'</tr>',
			'{0}',
		'</tbody>',
		'</table>'
	].join(''),
	quoto_item_tr : [
		'<tr bgcolor="#FFFFFF" align=center valign=middle>',
			'<td>{0}</td>',
			'<td align=left title="{1}">{2}</td>',
			'<td _action="removeQuote" _channelid="{1}">',
				'<span class="remove_quotechannel" title="', wcm.LANG.DOCUMENT_PROCESS_180 || "移除引用",'"></span>',
			'</td>',
		'</tr>'
	].join(''),
	quoto_item_tr_blank : [
		'<tr bgcolor="#FFFFFF" align=center valign=middle>',
			'<td>&nbsp;</td>',
			'<td align=left>&nbsp;</td>',
			'<td>&nbsp;</td>',
		'</tr>'
	].join(''),
	topset_table : [
		'<table border=0 cellspacing=1 cellpadding=0 style="width:100%;table-layout:fixed;background:gray;">',
		'<thead>',
			'<tr bgcolor="#CCCCCC" align=center valign=middle>',
				'<td width="32">', wcm.LANG.DOCUMENT_PROCESS_178 || "序号",'</td>',
				'<td>', wcm.LANG.DOCUMENT_PROCESS_177 || "文档标题",'</td>',
				'<td width="40">', wcm.LANG.DOCUMENT_PROCESS_181 || "排序",'</td>',
			'</tr>',
		'</thead>',
		'<tbody id="topset_order_tbody">{0}</tbody>',
		'</table>'
	].join(''),
	topset_item_tr : [
		'<tr bgcolor="#FFFFFF" align=center valign=middle _docid="{0}" _doctitle="{2}">',
			'<td>{1}</td>',
			'<td align=left title="{0}"><div style="overflow:hidden">{2}</div></td>',
			'<td>&nbsp;</td>',
		'</tr>'
	].join(''),
	topset_curr_tr : [
		'<tr bgcolor="#FFFFCF" align=center valign=middle _docid="" _currdoc="1">',
			'<td>{0}</td>',
			'<td align=left style="color:red;">',wcm.LANG.DOCUMENT_PROCESS_182 || "--当前文档--",'</td>',
			'<td>',
				'<span class="topset_up" title="', wcm.LANG.DOCUMENT_PROCESS_183 || "上移",  '" _action="topsetUp">&nbsp;</span>',
				'<span class="topset_down" title="', wcm.LANG.DOCUMENT_PROCESS_184 || "下移", '" _action="topsetDown">&nbsp;</span>',
			'</td>',
		'</tr>'
	].join('')
}
Event.observe(window, 'unload', function(){
	//解锁
	if(CurrDocId!=0){
		LockerUtil.unlock(CurrDocId, 605);
	}
});

/*---------------- 下面一排操作按钮的相关逻辑 ------------------*/
Ext.apply(PgC, {
/*---------------- 直接退出 ------------------*/
	SimpleExit : function(){
		window.open("","_self");//fix ie7
		window.close();
	},
/*---------------- 预览 ------------------*/
	Preview : function(){
		prepareDatas(true);
		if(!doValidation())return;
		if(!ContentNotNull()) return;
		ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_185 || '预览文档');
		var postdata = {};
		var myDocument = CMSObj.createFrom({
			objType : WCMConstants.OBJ_TYPE_DOCUMENT,
			objId : CurrDocId
		}, {
			postdata : postdata
		});
		if(!myDocument.preview())return;
		if(nModal == 3){   //如果是镜像型引用，需调整为当前栏目
			Ext.apply(postdata, {CHANNELID : CurrChannelId});
		}
		m_oAjaxHelper.Call('wcm6_document', 'preview', postdata, true,
			function(_transport, _json){
				myDocument.previewUrl = $v(_json, 'result');
				myDocument.afterpreview();
			},function(_transport, _json){
				ProcessBar.close();
				Ext.Msg.fault({
                message : $v(_json, "FAULT.MESSAGE"),
                detail :  $v(_json, "FAULT.DETAIL")
            },wcm.LANG.DIALOG_SERVER_ERROR || '与服务器交互时出错啦！');
			}
		);
	},
/*---------------- 保存 ------------------*/
	save : function(next){
		var aCombine = [];
		var myDocument = CMSObj.createFrom({
			objType : WCMConstants.OBJ_TYPE_DOCUMENT,
			objId : CurrDocId
		}, {
			combine : aCombine
		});
		if(!prepareDatas()){
			return;
		}
		if(!doValidation())return;
		if(!myDocument.save())return;
		ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_186 ||'保存文档');
		m_oAjaxHelper.JspMultiCall('../../app/video/video_addedit_dowith.jsp', aCombine,
			function(_transport){
				myDocument.objId = Ext.result(_transport);
				myDocument.aftersave();
				if(next)(next)();
			}
		);
	},
/*---------------- 保存并关闭 ------------------*/
	SaveExit : function(){
		$('frmData').DirectlyPublish.value = 0;
		PgC.save(PgC.SimpleExit);
	},

	addnew : function(){
		location.href = location.href.replace(/DocumentId=[^&]*/ig, 'DocumentId=0');
	},
/*---------------- 保存并新建 ------------------*/
	SaveAddNew : function(){
		$('frmData').DirectlyPublish.value = 0;
		PgC.save(PgC.addnew);
	},
/*---------------- 发布并新建 ------------------*/
	PublishAddNew : function(){
		$('frmData').DirectlyPublish.value = 1;
		PgC.save(PgC.addnew);
	},
/*---------------- 保存并发布 ------------------*/
	SavePublish : function(next){
		$('frmData').DirectlyPublish.value = 1;
		PgC.save(PgC.SimpleExit);
	},
	getActionItem : function(target){
		while(target!=null&&target.tagName!='BODY'){
			if(target.getAttribute('_action', 2)!=null)return target;
			target = target.parentNode;
		}
		return null;
	},
/*---------------- 所属栏目的选择 ------------------*/
	selectChannel : function(){
		var caller = this;
		FloatPanel.open({
			src : '../../app/include/channel_select.html',
			title : wcm.LANG.VIDEO_PROCESS_14 || '视频另存到栏目...',
			callback : function(selectIds, selectChnlDescs){
				if(selectIds[0]==window.DocChannelId)return;
				//置换当前栏目
				if(selectIds[0]!=$('frmData').ChannelId.value){
					window.DocMove = true;
				}
				else{
					window.DocMove = false;
				}
				window.DocChannelId = selectIds[0];
				var spChnl = $('sp_DocChannel');
				spChnl.innerHTML = selectChnlDescs[0];
				spChnl.title = selectIds[0] + "-" + selectChnlDescs[0];
				//置换扩展字段
				//置换模板
				//置换按钮
			},
			dialogArguments : {
				IsRadio : 1,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ExcludeInfoView : 1,
				ExcludeOnlySearch : 1,
				//ExcludeSelf : 1,
				ShowOneType : 1,
				MultiSites : 1,
				SelectedChannelIds : DocChannelId,
				CurrChannelId : DocChannelId,
				RightIndex : 31
			}
		});
	},
	getQuotedChannelIds : function(){
		return $('quoto_table').getAttribute("_selectedChannelIds", 2) || '';
	},
/*---------------- 引用到栏目的选择 ------------------*/
	setQuote : function(){
		var caller = this;
		FloatPanel.open({
			src : '../../app/include/channel_select.html',
			title : wcm.LANG.VIDEO_PROCESS_15 || '视频引用到栏目...',
			callback : function(selectIds, selectChnlDescs){
				var items = [];
				for(var i=0,n=selectIds.length; i<n; i++){
					items.push(String.format(m_Templates.quoto_item_tr, (i+1),
						selectIds[i], selectChnlDescs[i]));
				}
				if(selectIds.length==0){
					items.push(m_Templates.quoto_item_tr_blank);
				}
				var html = String.format(m_Templates.quoto_table, items.join(''));
				Element.update('quoto_table', html);
				//设置值
				$('quoto_table').setAttribute("_selectedChannelIds", selectIds.join());
				$('sp_quotenum').innerHTML = selectIds.length;
			},
			dialogArguments : {
				IsRadio : 0,
				ExcludeTop : 0,
				ExcludeLink : 1,
				ExcludeInfoView : 1,
				ExcludeOnlySearch : 1,
				ExcludeSelf : 1,
				SiteTypes : '2',
				MultiSiteType : 0,
				SelectedChannelIds : caller.getQuotedChannelIds(),
				CurrChannelId : DocChannelId,
				RightIndex : 31,
				canEmpty : true
			}
		});
	},
/*---------------- 移除引用 ------------------*/
	removeQuote : function(event, target, actionItem){
		var row = actionItem.parentNode;
		var sChnlId = actionItem.getAttribute('_channelid', 2);
		row.parentNode.deleteRow(row.rowIndex);
		var currChnlIds = this.getQuotedChannelIds().split(',');
		currChnlIds.remove(sChnlId);
		$('quoto_table').setAttribute("_selectedChannelIds", currChnlIds.join());
		$('sp_quotenum').innerHTML = currChnlIds.length;
	},
/*---------------- 获取置顶信息 ------------------*/
	getTopsetInfo : function(){
		if(!PgC.TopFlag || PgC.TopFlag=='0'){
			return {
				TopFlag : PgC.TopFlag,
				Position : 0,
				TargetDocumentId : 0
			};
		}
		var rows = $('topset_order_tbody').rows;
		var nCurrIndex = -1;
		for(var i=0,n=rows.length; i<n; i++){
			if(rows[i].getAttribute("_currdoc", 2)){
				nCurrIndex = i;
				break;
			}
		}
		var nPosition = 0;
		var nTargetDocId = 0;
		if(nCurrIndex==rows.length-1 && nCurrIndex!=0){
			var beforeRow = rows[nCurrIndex-1];
			nPosition = 0;
			nTargetDocId = beforeRow.getAttribute("_docid", 2);
		}else if(nCurrIndex!=rows.length-1){
			var afterRow = rows[nCurrIndex+1];
			nPosition = 1;
			nTargetDocId = afterRow.getAttribute("_docid", 2);
		}
		if(PgC.TopFlag == 2){
			PgC.TopFlag =3;
		}
		return {
			TopFlag : PgC.TopFlag,
			Position : nPosition,
			TargetDocumentId : nTargetDocId
		};
	},
	makeCurrDocInTopList : function(){
		if(PgC.DocInTopList)return;
		PgC.DocInTopList = true;
		PgC._renderTopList();
	},
	_renderTopList : function(index){
		index = index || 0;
		var rows = $('topset_order_tbody').rows;
		if(index<0 || index>=rows.length)return;
		var items = [];
		var infos = [];
		for(var i=0,n=rows.length; i<n; i++){
			var recid = rows[i].getAttribute("_docid", 2);
			if(!recid)continue;
			var doctitle = rows[i].getAttribute("_doctitle", 2);
			infos.push({recid:recid, doctitle:$transHtml(doctitle)});
		}
		for(var i=0,n=infos.length; i<n; i++){
			var myIdx = i+1;
			if(i>=index)myIdx=i+2;
			items.push(String.format(m_Templates.topset_item_tr, infos[i].recid,
				myIdx, infos[i].doctitle));
		}
		items.splice(index, 0, String.format(m_Templates.topset_curr_tr, index+1));
		var html = String.format(m_Templates.topset_table, items.join(''));
		Element.update('topset_order_table', html);
	},
	topsetUp : function(event, target, actionItem){
		var row = actionItem.parentNode.parentNode;
		PgC._renderTopList(row.rowIndex-2);
	},
	topsetDown : function(event, target, actionItem){
		var row = actionItem.parentNode.parentNode;
		PgC._renderTopList(row.rowIndex);
	},
	topset : function(){
		var choises = document.getElementsByName('TopFlag');
		var value = 0;
		for(var i=0, n=choises.length; i<n; i++){
			if(choises[i].checked){
				value = choises[i].value;
				break;
			}
		}
		PgC.TopFlag = value;
		if(value=='0'){
			Element.hide('topset_order');
			Element.hide('pri_set_deadline');
		}else{
			Element.show('topset_order');
			Element.hide('pri_set_deadline');
		}
		if(value=='1'){
			Element.show("display_padding");
			Element.show('pri_set_deadline');
		}
		PgC.makeCurrDocInTopList();
	},
/*---------------- 打开发布设置中的设置模板的窗口 ------------------*/
	selectTemplate : function(event, target){
		var cb = wcm.CrashBoard.get({
			id : 'TEMPLATE_SELECT_SOLO',
            width : '480px',
            height : '340px',
			left : (event.pointer[0] - 410) + 'px',
			top : (event.pointer[1] - 120) + 'px',
            title : wcm.LANG.DOCUMENT_PROCESS_209 || '选择模板',
            url : WCMConstants.WCM6_PATH + 'template/template_select_listsimple.html?' + $toQueryStr({
				channelId :  CurrChannelId,
				templateType : 2,
				templateIds : $('spDetailTemp').getAttribute('_tempid', 2) || 0
			}),
			callback : function(_args){
				$('spDetailTemp').setAttribute('_tempid', _args.selectedIds.join(","));
				$('spDetailTemp').setAttribute('_tempname', _args.selectedNames.join(',&nbsp;&nbsp;') || (wcm.LANG.DOCUMENT_PROCESS_190 || "无"));
				Element.update('spDetailTemp', _args.selectedNames.join(',&nbsp;&nbsp;') || (wcm.LANG.DOCUMENT_PROCESS_190 || "无"));
			},
			cancel : wcm.LANG.DOCUMENT_PROCESS_25 || '关闭'
        });
        cb.show();
	},
/*---------------- 是否定时发布的设置 ------------------*/
	defineSchedule : function(){
		var bDefined = $('ip_DefineSchedule').checked;
		if(bDefined){
			Element.show('sp_PublishOnTime');
			Element.hide('sp_NoPublish');
		}else{
			Element.hide('sp_PublishOnTime');
			Element.show('sp_NoPublish');
		}
	},
/*---------------- 打开附件管理的窗口 ------------------*/
	manageAttachment : function(){
		var caller = this;
		FloatPanel.open({
			src : 'video_attachments.html',
			title : wcm.LANG.DOCUMENT_PROCESS_16 || '附件管理',
			callback : function(info){
				caller.m_Appendixs = Object.deepClone(info);
				var arr = $a(caller.m_Appendixs['Type_20'], 'APPENDIXES.APPENDIX');
				if(arr.length>0){
					//$('AttachPic').checked = true;
				}
			},
			dialogArguments : caller.m_Appendixs
		});
	},
/*---------------- 打开相关文档管理的窗口 ------------------*/
	manageRelation : function(){
		var caller = this;
		FloatPanel.open({
			src : 'video_relations.html',
			title : wcm.LANG.DOCUMENT_PROCESS_17 || '相关文档管理',
			callback : function(info){
				caller.m_Relations = Object.deepClone(info);
			},
			dialogArguments : {
				relations : caller.m_Relations,
				CurrDocId : CurrDocId,
				CurrChannelId : CurrChannelId/*,
				DocKeyWords : $('DOCKEYWORDS').value*/
			}
		});
	},
/*---------------- 打开管理扩展字段的窗口 ------------------*/
	manageExtends : function(){
		Element.hide('advancedprops');
		Element.hide('basicprops');
		Element.hide('otherprops');
		Element.show('extendprops');
//		var caller = this;
//		FloatPanel.open({
//			src : 'document_addedit_extendedfield.jsp?DocumentId=' + CurrDocId + '&ChannelId=' + DocChannelId,
//			title : wcm.LANG.DOCUMENT_PROCESS_187 ||'扩展字段管理',
//			callback : function(info){
//				caller.m_DocExtends = Object.deepClone(info);
//			},
//			dialogArguments : {
//				docextends : caller.m_DocExtends
//			}
//		});
	},
/*---------------- 切换到基本属性窗口 ------------------*/
	manageBasic : function(){
		Element.hide('advancedprops');
		Element.show('basicprops');
		Element.hide('otherprops');
		Element.hide('extendprops');
	},
/*---------------- 切换到其它属性窗口 ------------------*/
	manageOther : function(){
		Element.hide('advancedprops');
		Element.hide('basicprops');
		Element.show('otherprops');
		Element.hide('extendprops');
	},
/*---------------- 切换到高级属性窗口 ------------------*/
	manageAdvanced : function(){
		Element.show('advancedprops');
		Element.hide('basicprops');
		Element.hide('otherprops');
		Element.hide('extendprops');
	},
/*---------------- 打开简易编辑器 ------------------*/
	openSimpleEditor : function(event){
		var cb = wcm.CrashBoard.get({
			id : 'SIMP_EDITOR',
            width : '600px',
            height : '300px',
			left : (event.pointer[0] - 300) + 'px',
			top : '40px',
            title : wcm.LANG.DOCUMENT_PROCESS_18 || '标题维护-简易编辑器',
			appendParamsToUrl : false,
            url : WCMConstants.WCM6_PATH + 'editor/simp_editor2.html',
			params : {
				html : $('DocTitle').value,
				Toolbar : 'Title'//'Abstract','Title','MetaData'
			},
			callback : function(params){
				var sTitle = params[0].replace(/^<(p|div|span)\s*[^>]*>((.|\n|\r)*)<\/\1>$/,function(_s0,_s1,_s2){
					if(_s2.indexOf('<\/'+_s1)!=-1)return _s0;
					return _s2;
				});
				$('DocTitle').value = sTitle;
			},
			cancel : wcm.LANG.DOCUMENT_PROCESS_25 || '关闭'
        });
        cb.show();
	},
	openSimpleEditor2 : function(event){
		var cb = wcm.CrashBoard.get({
			id : 'SIMP_EDITOR',
            width : '800px',
            height : '300px',			
			left : (event.pointer[0] - 620) + 'px',
			top : (event.pointer[1] - 150) + 'px',
            title : wcm.LANG.DOCUMENT_PROCESS_19 || '摘要维护-简易编辑器',
			appendParamsToUrl : false,
            url : WCMConstants.WCM6_PATH + 'editor/simp_editor2.html',
			params : {
				html : $('DocAbstract').value,
				Toolbar : 'Abstract'//'Abstract','Title','MetaData'
			},
			callback : function(params){
				var sTitle = params[0].replace(/^<(p|div|span)\s*[^>]*>((.|\n|\r)*)<\/\1>$/,function(_s0,_s1,_s2){
					if(_s2.indexOf('<\/'+_s1)!=-1)return _s0;
					return _s2;
				});
				$('DocAbstract').value = sTitle;
			},
			cancel : wcm.LANG.DOCUMENT_PROCESS_25 || '关闭'
        });
        cb.show();
	},
	titlecolor : function(event, target){
		var p = event.getPoint();
		var x = p.x + 4;
		var y = p.y + 4;
		var bubblePanel = new wcm.BubblePanel($('colorpicker'));
		bubblePanel.bubble([x,y]);
	},
	getSettingItem : function(target){
		while(target!=null&&target.tagName!='BODY'){
			if(Element.hasClassName(target, 'td_setting'))return target;
			target = target.parentNode;
		}
		return null;
	},
	init : function(){
		//Editor Header
		Ext.getBody().on('click', function(event, target){
			var actionItem = PgC.getActionItem(target);
			if(actionItem==null)return;
			var action = actionItem.getAttribute('_action', 2);
			if(!action || !PgC[action])return;
			PgC[action].apply(PgC, [event, target, actionItem]);
		});
		//
		var lastSettingItem = null;
		Ext.getBody().on('mouseover', function(event, target){
			var settingItem = PgC.getSettingItem(target);
			if(settingItem==lastSettingItem)return;
			if(lastSettingItem){
				Element.removeClassName(lastSettingItem, 'td_active');
			}
			lastSettingItem = settingItem;
			if(settingItem==null)return;
			Element.addClassName(settingItem, 'td_active');
		});
		var lastSettingItem2 = $('td_basic_setting');
		Ext.get('props_setting').on('click', function(event, target){
			var settingItem = PgC.getSettingItem(target);
			if(settingItem==null)return;
			if(settingItem==lastSettingItem2)return;
			if(lastSettingItem2){
				Element.removeClassName(lastSettingItem2, 'td_active2');
			}
			Element.addClassName(settingItem, 'td_active2');
			lastSettingItem2 = settingItem;
		});
		var oAppendixs = null;
		try{
			oAppendixs = {
				Type_10:
					Ext.Json.parseXml(
						loadXml($('appendix_10').value)),
				Type_20:
					Ext.Json.parseXml(
						loadXml($('appendix_20').value)),
				Type_40:
					Ext.Json.parseXml(
						loadXml($('appendix_40').value))
			}
		}catch(err){
			oAppendixs = {
				Type_10:{},
				Type_20:{},
				Type_40:{}
			}
		}
		this.m_Appendixs = oAppendixs;
		//缓存相关文档管理中的数据
		var oRelations = null;
		try{
			oRelations = Ext.Json.parseXml(
						loadXml($('relations').value));
		}catch(err){
			oRelations = {};
		}
		this.m_Relations = oRelations;
		//colorpicker
		var m_colorpicker = new wcm.ColorPicker('colorpicker');
		m_colorpicker.doAfterClick = function(sColor){
			$('frmData').TitleColor.value = sColor || '';
			$('title_color').style.backgroundColor = sColor || '';
		}
	}
});

/*---------------- 文档来源的suggestion处理 ------------------*/
Event.observe(window, 'load', function(){
	var sg1 = new wcm.Suggestion();
	sg1.init({
		el : 'DocSourceName',
		request : function(sValue){
			var all = [];
			BasicDataHelper.JspRequest(
			WCMConstants.WCM6_PATH + "nav_tree/source_create.jsp?SourceName=",
			{},  true,
			function(transport, json){
				var result = eval(transport.responseText.trim());
				for (var i = 0; i < result.length; i++){
					var sGroup = {};
					sGroup.value = result[i].title;
					sGroup.label = result[i].desc;
					all.push(sGroup);
				}
				var items = [];
				for (var i = 0; i < all.length; i++){
					if(all[i].label.toUpperCase().indexOf(sValue.toUpperCase()) >= 0) items.push(all[i]);
				}
				sg1.setItems(items);
			});
		}
	});
});
function ContentNotNull(){
	//添加对正文内容的非空判断
	var nDocType = 20;//parseInt($('DocType').value);
	var sDocHtml = '';
	var sDocContent = '';
	if(nDocType==20){
		sDocHtml = GetHTML(true);
		sDocContent = GetText();
	}
	else if(nDocType==10){
		sDocHtml = '';
		sDocContent = $('_editorValue_').value;
	}
	try{
		if(nDocType==20){
			if(sDocHtml.trim()==''){
				Ext.Msg.alert(wcm.LANG.DOCUMENT_PROCESS_220 || '正文内容不能为空.');
				return false;
			}
		}
		else if(nDocType==10){
			if(sDocContent.trim()==''){
				Ext.Msg.alert(wcm.LANG.DOCUMENT_PROCESS_220 || '正文内容不能为空.');
				return false;
			}
		}
	}catch(err){
	}
	return true;
}
function CountTitle(event){
	var eTitle = $('DocTitle');
	var nLength = eTitle.value.byteLength();
	var eCountTitle = $('TitleCount');
	eCountTitle.innerHTML = nLength;
	if(nLength<=200){
		eCountTitle.style.color = 'green';
	}else{
		eCountTitle.style.color = 'red';
	}
}
function CalTitlePsn(event){
	var evt = event || window.event;
	var keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
	//if(evt.type=='keyup'&&keyCode!=Event.KEY_LEFT&&keyCode!=Event.KEY_RIGHT)return;
	try{
		var psn = GetCursorPsn($('DocTitle'), true);
	}catch(err){
		psn = 0;
	}
	if(psn==-1)return;
	$('TitlePsn').innerHTML = psn;
}
//Cal Current cursor psn
function GetCursorPsn(_elInpt,_bUnicodeDouble){
	if(Ext.isIE){
		var slct = document.selection;
		var rng = slct.createRange();
		if(!rng.text||rng.text.length==0){
			_elInpt.select();
			rng.setEndPoint("StartToStart", slct.createRange());
			var psn = (_bUnicodeDouble)?rng.text.byteLength():rng.text.length;
			rng.collapse(false);
			rng.select();
			return psn;
		}
	}else{
		var obj = $("DocTitle");
		var prefix = obj.value.substring(0, obj.selectionStart);   
		return prefix.byteLength();
	}
	return -1;
}
function observeTitle(){
	if(Ext.isIE){
		Ext.get('DocTitle').on('propertychange', function(event){
			var event = event.browserEvent;
			if(event.propertyName=='value'){
				CountTitle(event);
			}
		});
	}else{
		if($('DocTitle')) CountTitle(window.event);
		Ext.get('DocTitle').on('input', CountTitle);
	}
	$('DocTitle').value = $('DocTitle').value;
	Ext.get('DocTitle').on('keyup', CalTitlePsn);
	Ext.get('DocTitle').on('mouseup', CalTitlePsn);
}
/*---------------- 全屏打开编辑器 ------------------*/
function FullOpenEditor(bFull){
	Element[bFull ? 'addClassName' : 'removeClassName']('frmAction', 'full-edit');
}
//init
Event.observe(window,'load',function(){
	observeTitle();
	PgC.init();
	window.focus();
});
//basic listen
/*---------------- 一些基本操作的监听 ------------------*/
var m_oAjaxHelper = new com.trs.web2frame.BasicDataHelper();
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		var context = event.getContext();
		return context && context.combine;
	},
	save : function(event){
		var aCombine = event.getContext().combine;
		var postdata = makePostData();
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm61_video',
			'save',
			postdata
		));
	},
	aftersave : function(event){
		var obj = event.getObj();
		//清空剪贴板
		try{
			window.clipboardData.clearData('Text');
		}catch(err){
		}
		//关闭进度条
		ProcessBar.close();
		//刷新列表
		if(window.opener){
			try{
				if(getParameter('callback')){
					var fCallBack = eval("opener." + getParameter('callback'));
					if(fCallBack) {
						fCallBack(obj.getId(), obj.chnldocId);
						return true;
					}
				}
				if(getParameter('FlowDocId') > 0){
                   window.opener.CMSObj.createFrom({
						objType : 'IFlowContent',
						objId : obj.getId()
					}).afteredit();
					return true;
				}
				var oMsgSrc = getParameter("openerTopAsMsgSrc") ? opener.top : opener;
				oMsgSrc.CMSObj.createFrom({
					objType : obj.getType(),
					objId : obj.getId()
				}, {chnldocId : getParameter("chnldocid")}).aftersave();
				return true;
			}catch(err){
				alert(err.message);
			}
		}
	},
	beforepreview : function(event){
		var context = event.getContext();
		return context!=null;
	},
	preview : function(event){
		var context = event.getContext();
		var postdata = makePostData();
		Ext.apply(context.postdata, postdata);
	},
	afterpreview : function(event){
		var obj = event.getObj();
		$openMaxWin(obj.previewUrl, 'r_' + new Date().getTime());
		ProcessBar.close();
	}
});
//listening
/*---------------- 监听保存文档的时候设置引用 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		var postdata = {
			"DocumentId" : CurrDocId,
			"FromChannelId" : DocChannelId,
			"ToChannelIds" : PgC.getQuotedChannelIds(),
			FlowDocId : nFlowDocId
		};
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_document',
			'setQuote',
			postdata
		));
	}
});
/*---------------- 监听保存文档的时候设置置顶 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		if(!PgC.IsCanTop)return;
		var aCombine = event.getContext().combine;
		var info = PgC.getTopsetInfo();
		if(info.TopFlag == 2){
			info.TopFlag = 3;
		}
		var postdata = {
			"TopFlag" : info.TopFlag,
			"ChannelId" : CurrChannelId,
			"Position" : info.Position,
			"DocumentId" : CurrDocId,
			"TargetDocumentId" : info.TargetDocumentId,
			"InvalidTime" : (PgC.TopFlag==1)?$('TopInvalidTime').value:'',
			FlowDocId : getParameter('FlowDocId') || 0
		};
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_document',
			'setTopDocument',
			postdata
		));
	}
});
/*---------------- 监听保存文档的时候保存发布设置 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		if(!bIsCanPub)return;
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		var postdata = {
			"ObjectId" : CurrDocId,
			"DetailTemplate" : $('spDetailTemp').getAttribute('_tempId', 2) || 0,//sTempId,
			"ScheduleTime" : $('ip_DefineSchedule').checked ? $('ScheduleTime').value : '',
			FlowDocId : nFlowDocId
		};
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_publish',
			'saveDocumentPublishConfig',
			postdata
		));
	},
	preview : function(event){
		var context = event.getContext();
		Ext.apply(context.postdata, {
			TemplateId	: $('spDetailTemp').getAttribute('_tempId', 2) || 0
		});
	}
});
/*---------------- 监听保存文档的时候保存附件 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		var postdata = {
			"DocId" : CurrDocId,
			"AppendixType" : 10,
			"AppendixesXML" : getAppendixesXML(10),
			FlowDocId : nFlowDocId
		}
		aCombine.push(m_oAjaxHelper.Combine('wcm6_document','saveAppendixes',postdata));
		postdata = {
			"DocId" : CurrDocId,
			"AppendixType" : 20,
			"AppendixesXML" : getAppendixesXML(20),
			FlowDocId : nFlowDocId
		}
		aCombine.push(m_oAjaxHelper.Combine('wcm6_document','saveAppendixes',postdata));
		postdata = {
			"DocId" : CurrDocId,
			"AppendixType" : 40,
			"AppendixesXML" : getAppendixesXML(40),
			FlowDocId : nFlowDocId
		}
		aCombine.push(m_oAjaxHelper.Combine('wcm6_document','saveAppendixes',postdata));
	}
});
/*---------------- 监听保存文档的时候保存相关文档 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		var postdata = {
			"DocId" : CurrDocId,
			"RelationsXML" : getRelationsXML(),
			FlowDocId : nFlowDocId
		};
		aCombine.push(m_oAjaxHelper.Combine('wcm6_document','saveRelation',postdata));
	}
});
/*---------------- 监听保存文档的时候执行移动文档 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		if(!window.DocMove)return;
		var FromChannelId = $('frmData').ChannelId.value;
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		var postdata = {
			"ObjectIds" : CurrDocId,
			"FromChannelId" : FromChannelId,
			"ToChannelId" : DocChannelId,
			FlowDocId : nFlowDocId
		};
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_document',
			'move',
			postdata
		));
	}
});
/*---------------- 监听保存文档的时候直接发布 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		if(!bIsCanPub || $('frmData').DirectlyPublish.value != 1)return;
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		var postdata = {
			"ObjectIds" : CurrDocId,
			"ObjectType" : 605,
			FlowDocId : nFlowDocId
		}
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_publish',
			'directPublish',
			postdata
		));
	}
});

function onPubjobset(){
	if($("unpubjob").checked){
		Element.show($("unpubjobdatetime"));
	}else{
		Element.hide($("unpubjobdatetime"));
	}
}

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		var aCombine = event.getContext().combine;
		var postData = {SenderType : 605,SchId:$F("UnpubSchId")||0};
		if($("unpubjob").checked){
			Object.extend(postData,{ETime:$F("UNPUBTIME")});
			aCombine.push(m_oAjaxHelper.Combine('wcm6_publish', 'setUnpubSchedule', postData));
		}else if($F("UnpubSchId")>0){
			Object.extend(postData,{Unset:1});
			aCombine.push(m_oAjaxHelper.Combine('wcm6_publish', 'setUnpubSchedule', postData));
		}
	}
});

//hdg@2010-1-28 限时置顶添加提醒
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		if($("pri_set_1").checked){
			var exetime = $F("TopInvalidTime") || "";
			if(exetime.trim() == ''){
				Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_263 || "限时置顶时间不能为空", function(){
					try{
						oTabPanel.setActiveTab('tab-advance');
						$('TopInvalidTime').focus();
						$('TopInvalidTime').select();
					}catch(error){
						//just skip it
					}
				});
				return false;
			}
			var s = exetime.split(" "); 
			var s1 = s[0].split("-"); 
			var s2 = s[1].split(":"); 
			var dtEtime = new Date(s1[0],s1[1]-1,s1[2],s2[0],s2[1]);
			var now = new Date();
			if(Date.parse(now)>=Date.parse(dtEtime)||isNaN(Date.parse(dtEtime))){
				Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_261||"限时置顶时间不能早于当前时间", function(){
					try{
						oTabPanel.setActiveTab('tab-advance');
						$('TopInvalidTime').focus();
						$('TopInvalidTime').select();
					}catch(error){
						//just skip it
					}
				});
				return false;
			}
		}
	}
});

//hdg@2010-1-28 定时发布添加提醒
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		if($("ip_DefineSchedule").checked){
			var exetime = $F("ScheduleTime") || "";
			if(exetime.trim() == ''){
				Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_262 || "定时发布时间不能为空", function(){
					try{
						oTabPanel.setActiveTab('tab-advance');
						$('ScheduleTime').focus();
						$('ScheduleTime').select();
					}catch(error){
						//just skip it
					}
				});
				return false;
			}
			var s = exetime.split(" "); 
			var s1 = s[0].split("-"); 
			var s2 = s[1].split(":"); 
			var dtEtime = new Date(s1[0],s1[1]-1,s1[2],s2[0],s2[1]);
			var now = new Date();
			if(Date.parse(now)>=Date.parse(dtEtime)||isNaN(Date.parse(dtEtime))){
				Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_260||"定时发布时间不能早于当前时间", function(){
					try{
						oTabPanel.setActiveTab('tab-advance');
						$('ScheduleTime').focus();
						$('ScheduleTime').select();
					}catch(error){
						//just skip it
					}
				});
				return false;
			}
		}
	}
});

//wenyh@2009-05-04 定时撤稿
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		if($("unpubjob").checked){
			var exetime = $F("UNPUBTIME") || "";
			var dtEtime = new Date(exetime.replace('-','/'));
			var now = new Date();
			if(Date.parse(now)>=Date.parse(dtEtime)||isNaN(Date.parse(dtEtime))){
				Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_207||"计划撤销发布时间不能早于当前时间", function(){
					try{
						oTabPanel.setActiveTab('tab-advance');
						$('UNPUBTIME').focus();
						$('UNPUBTIME').select();
					}catch(error){
						//just skip it
					}
				});
				return false;
			}
		}
	}
});

//广告选件
function loadTRSAdOption(){
	//广告选件已打开
	if(window.bEnableAdInTrs) {
		try{
			var strsAdCon = trsad_config['root_path'];
			if(strsAdCon==null)return;
			var nStrLen = strsAdCon.length;
			if(strsAdCon.charAt(nStrLen-1)!='/'){
				strsAdCon = strsAdCon + '/';
			}
			if(window.bEnableAdInTrs) {
				try{
					var sWcmurl = "http://"+window.location.host+"/wcm/app/template/adintrs_intoTemp.jsp";
					var cb = wcm.CrashBoard.get({
						id : 'adintrs_sel',
						width:'600px',
						height:'600px',
						title : wcm.LANG.TEMPLATE_47 ||'选择广告',
						url : WCMConstants.WCM6_PATH + 'template/dialog_window.html',
						params : {URL:7,WCMURL:sWcmurl},
						btns : false,
						callback : function(params){
							var sResult = params.result;
							sResult = sResult.replace("${admanage_root_path}", strsAdCon);
							var oTrsEditor = $('_trs_editor_');
							var oWindow = oTrsEditor.contentWindow.GetTrueEditor();
							if(oWindow.FCK.doAdInTrs)
								oWindow.FCK.doAdInTrs({src:sResult,IGNOREAPD:1});
						},
						cancel : wcm.LANG.CANCEL ||'取消'
					});
					cb.show();				
				}catch(err){
					//TODO
					alert((wcm.LANG.TEMPLATE_48 ||'插入广告位出错：') + err.message);
				}
				return false;
			}
		}catch(err){
			//TODO
			alert(wcm.LANG.DOCUMENT_PROCESS_210 ||'插入广告位出错:' + err.message);
		}
	}
};


//ckm similar search.
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		if(!editorCfg.enableCkmSimSearch) return true;
		var args = [];
		args[0] = CurrDocId;
		args[1] = GetText();

		//1.verify parameters
		var nWidth	= 700;
		var nHeight = 300;

		var nLeft	= (window.screen.availWidth - nWidth)/2;
		var nTop	= (window.screen.availHeight - nHeight)/2;


		//2.Construct parameters for dialog
		var sFeatures		= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
							+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
							+ "center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		//3.display Dialog
		var sURL = "../../ckm/document_sim_search.html";
		try{
			var bResult = window.showModalDialog(sURL, args, sFeatures);
			return bResult;			
		}catch(e){
			alert((wcm.LANG.DOCUMENT_PROCESS_216 || "您的IE插件已经将对话框拦截！\n")
					+ (wcm.LANG.DOCUMENT_PROCESS_217 || "请将拦截去掉-->点击退出-->关闭IE，然后重新打开IE登录即可！\n")
					+ (wcm.LANG.DOCUMENT_PROCESS_218 || "给您造成不便，TRS致以深深的歉意！"));		
		}	
		return true;
	}
});

//ckm auto extract.
function extractAbstractAndKeywords(){
	var sURL = "../../ckm/auto_extract_editor.jsp";
	var content = GetText();
	BasicDataHelper.JspRequest(sURL, {Content : content}, true, 
		function(transport,_json) {
			try{    
				$('DocAbstract').value= $v(_json,'root.abstractcontent');   
				$('DOCKEYWORDS').value= $v(_json,'root.keywords');   ;
			}catch(error){
				//Just Skip it.
			}
		},
		function(){
			alert(wcm.LANG.DOCUMENT_PROCESS_219 || '抽取失败.');
		}
	);
}

//ckm check spell.
function checkWordSpell(){
	var args = {value:GetText()};
	var nWidth	= 400;
	var nHeight = 500;
	var nLeft	= (window.screen.availWidth - nWidth)/2;
	var nTop	= (window.screen.availHeight - nHeight)/2;

	//2.Construct parameters for dialog
	var sFeatures		= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
	+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
	+ "center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	//3.display Dialog
	var sURL = "../../ckm/document_spell_check.html";
	try{
		var oResult = window.showModalDialog(sURL, args, sFeatures);
	}catch(e){
		alert((wcm.LANG.DOCUMENT_PROCESS_216 || "您的IE插件已经将对话框拦截！\n")
					+ (wcm.LANG.DOCUMENT_PROCESS_217 || "请将拦截去掉-->点击退出-->关闭IE，然后重新打开IE登录即可！\n")
					+ (wcm.LANG.DOCUMENT_PROCESS_218 || "给您造成不便，TRS致以深深的歉意！"));			
	}	
}

//keywords replace
Event.observe($('dockeywords'), 'blur', function(){
	keyWordsReplace('dockeywords');
});
function keyWordsReplace(domId){
	var oldValue = $(domId).value;
	var sValue = oldValue.trim().replace(/[ 　,，；]/g , ";"); 
	$(domId).value = sValue.replace(/;;/g , ";"); 
}

//以下三个函数由flash调用，$("save")为保存并退出按钮
function uploadStarted(fileName) {
	if (!fileName) {
		return;
	}
	if ($("DocTitle").value.replace(/^\s*/, '') != "") {
		return;
	}
	var lastDot = fileName.lastIndexOf('.');
	var nameWithoutExt = (lastDot == -1) ? fileName : fileName.substring(0, lastDot);
	$("DocTitle").value = nameWithoutExt;
}
function setMetaData(jsonStr) {
	//alert("video metadata = [\n" + jsonStr + "\n]");
	$("jsonMeta").value = jsonStr;
}
function enableSubmit() {
	$("save").disabled = false;
}