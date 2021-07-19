function validate(_bSkimCommentValidate){
	//并联审批开始节点，并且不能选择分支，需要根据选择的用户来自动设置分支
	if(PageContext.params['nodemodal'] == 2 && PageContext.params['applyExtension'] != 1 &&(PageContext.params['reworked'] == 1 || PageContext.params['refuse'] == 1 || bCanSelectSepNode == 'false')){
		return validSepratorStartPostData(_bSkimCommentValidate);
	}
	//在校验前进行一次当前节点的信息收集
	PageContext.m_hNodePreparedBranchInfos[PageContext.m_nCurrNodeId] = PageContext.getBranchInfo();
	
	//转交给一个指定的节点
	if(PageContext.m_sCurrSpecialOption != null && PageContext.m_sCurrSpecialOption == 'referredto'){
		var nReferredToNodeId = $('selReferredToNodes').value;
		if(!nReferredToNodeId){
			alert("打回必须选择一个目标节点！");
			return false;
		}	
	}

 	//ge gfc add @ 2007-8-14 9:21 对非并联审批节点，强制要求填写处理人员
	if(Element.visible('divOptionalParams') 
		&& PageContext.params['workmodal'] != 2) {
		var nodeParams = PageContext.m_hNodePreparedBranchInfos[PageContext.m_nCurrNodeId];
		if(nodeParams && nodeParams['SelectedUsers'] && !(nodeParams['SelectedUsers'].length > 0)){
			if(($('cb_asRule') && !$('cb_asRule').checked)|| $('cb_asRule')==undefined){
				alert((wcm.LANG['IFLOWCONTENT_28'] || '请指定下一节点的处理人员!'));
				return false;
			}
		}
	}
	if(_bSkimCommentValidate != true){
		if(!validComment()){
			return false;
		}
	}
	return true;
}
function validSepratorStartPostData(_bSkimCommentValidate){
	if($('dvWorkModalSepratorEndNodes').style.display == ''){
		return true;
	}
	//必须选择用户
	var arrSelectedUserIds = PageContext.getCurrSelectedUserIds();
	if(arrSelectedUserIds.length == 0){
		alert((wcm.LANG['IFLOWCONTENT_28'] || '请指定下一节点的处理人员!'));
		return false;
	}
	if(bCanSelectSepNode == 'false'){
		//选择用户的个数不能多于分支节点的个数
		var nextSepratorBranchMaxSize = PageContext.getNextNodesSize();
		if(arrSelectedUserIds.length > nextSepratorBranchMaxSize){
			alert('您最多只能指定【' +nextSepratorBranchMaxSize+"】个处理用户！");
			return false;
		}
	}
	//意见的校验
	if(_bSkimCommentValidate != true){
		if(!validComment()){
			return false;
		}
	}
	return true;
}
function validComment(){
	if(!$('txtComment').disabled){
			var sLen = 0;
			if($('txtComment').value.trim() == ''){
				if (!confirm(wcm.LANG['IFLOWCONTENT_13'] || '没有填写处理意见！仍要继续提交吗？')) {
					//兼容了该页面嵌入到别的页面的时候，控件不可见引起的问题
					try{
						$('txtComment').select();
						$('txtComment').focus();
					}catch(ex){}
					return false;
				}
			}else if((sLen = $('txtComment').value.byteLength()) > 500){
				var sErrorInfo = String.format("处理意见限制为500个字符长度，当前为{1},每个汉字长度为2。",sLen);
				//Ext.Msg.alert(sErrorInfo);
				alert(sErrorInfo);
				//兼容了该页面嵌入到别的页面的时候，控件不可见引起的问题
				try{
					$('txtComment').select();
					$('txtComment').focus();
				}catch(ex){}
				return false;
			}
		}
		return true;
}
function buildPostXMLData(){
	var oPostData = {
		PostDesc: $('txtComment').value,
		ObjectIds: PageContext.params['flowid'],
		ContentType: PageContext.params['ctype'],
		ContentId: PageContext.params['cid']
	}

	var aCombine = [];
	if(PageContext.m_sCurrSpecialOption == 'hold'){
		return null;
	}
	if(PageContext.m_sCurrSpecialOption != null){
		if(PageContext.m_sCurrSpecialOption == 'referredto'){
			oPostData['ReffererTo'] = true;
			aCombine = PageContext.buildPostData(oPostData);
		}else{
			var method = PageContext.m_sCurrSpecialOption;
			if(PageContext.m_sCurrSpecialOption == 'backTo'){
				oPostData['NotifyTypes'] = 'email,message';
			}
			if(PageContext.m_sCurrSpecialOption == 'applyExtension'){
				//申请延时还是走返工的逻辑，内部设置一个标识来处理
				oPostData['NotifyTypes'] = 'email,message';
				oPostData['ApplyExtension'] = '1';
				method = "backTo";
			}
			aCombine.push(BasicDataHelper.Combine('wcm6_process', method, oPostData));	
		}
	}else{
		aCombine = PageContext.buildPostData(oPostData);
	}
	return makeMultiPostData(aCombine);
}
 
function makeMultiPostData(_arrCombined){
	var doc = loadXml('<post-data></post-data>');
	var root = doc.documentElement;
	for(var i=0;i<_arrCombined.length;i++){
		var fo = _arrCombined[i];
		var mel = doc.createElement("method");
		root.appendChild(mel);
		mel.setAttribute("type", fo.m);
		mel.appendChild(doc.createTextNode(fo.s));
		var pel = doc.createElement("parameters");
		root.appendChild(pel);
		jsonIntoEle(doc, pel, fo.data, true);
	}
	return doc.xml;
}

function isSpecialOption(){
	return (PageContext.m_sCurrSpecialOption != null && PageContext.m_sCurrSpecialOption != '' && PageContext.m_sCurrSpecialOption != 'hold' 
		&& PageContext.m_sCurrSpecialOption != 'referredto');
}
 