Ext.apply(FieldInfos, {
	servicesName : 'wcm61_MetaViewField',
	findMethodName : 'findViewFieldById',
    saveMethodName : 'saveViewField',
	setControlsDefault : function(){
		//clear some controls.
		var resetControls = ['fieldName','anotherName','enmValue','classId','dbScale','defaultValue','validator'];
		for (var i = 0; i < resetControls.length; i++){
			var oControl = $(resetControls[i]);
			if(!oControl) continue;
			oControl.value = '';
			if(oControl.tagName == 'INPUT'){
				var type = oControl.type.toLowerCase();
				if(type == 'checkbox' || type == 'radio'){
					oControl.checked = false;
				}
			}
		}
		//set select default.
		this.setDefaultFieldType();
	},
	toggleAttrRelation : function(){
		//此处的切换是初始状态时的切换，对于动态的设置的切换放在jsp页面input处以事件形式触发。
		//公共量
		var fieldType = $('fieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var sFieldType = option.getAttribute("_value").toLowerCase();
		//分类法
		$('defaultSelectClassInfo').style.display = $('classIdContainer').style.display;
		$('parent_optional_base').style.display = $('defaultSelectClassInfo').style.display;
		$('radioOrCheckContainer').style.display = $('classIdContainer').style.display;
		
		//字段分组
		$('fieldGroupContainer').style.display = ($('relationViewContainer') && $('relationViewContainer').style.display == "") ? 'none' : "";

		//可下拉的列表和提示列表增加一个属性
		if(option.value==15||option.value==17){
			$('selToDatabase').style.display = "";
		}else{
			$('selToDatabase').style.display = "none";
		}

		//附件和相关文档类型     与“检索字段”的关联。
     	var oSearchField = $("searchField");
		var noSearchAttrArray = ['appendix', 'relation','password']; 
		if(noSearchAttrArray.include(sFieldType)){
			 oSearchField.checked = false;
			 oSearchField.disabled = true;
		}else{
			 oSearchField.disabled = false;
		}

		//字段和是否是标题字段的关联
		var oTitleField = $("titleField");
		var noTitleField = ['appendix'];
		if(noTitleField.include(sFieldType)){
			 oTitleField.checked = false;
			 oTitleField.disabled = true;
		}else{
			 oTitleField.disabled = false;
		}

		//隐藏字段和不能为空字段的关联。

	}
});


function hasRight(){
	try{
		return $('hasRight').value ==1 ? true : false;
	}catch(error){
	}
	return false;
}

 function validViewFieldEnmValue(newViewFieldEnmValue, sDBfieldEnmValue){
	return true;
	if(!sDBfieldEnmValue || sDBfieldEnmValue.length == 0)
		return false;
	if(!newViewFieldEnmValue || newViewFieldEnmValue.length == 0)
		return false;
	var arrNewViewFieldEnmValue = newViewFieldEnmValue.split("~");
	var arrDBfieldEnmValue = sDBfieldEnmValue.split("~");
	for(var i = 0 ; i < arrNewViewFieldEnmValue.length; i++){
		if(!arrDBfieldEnmValue.include(arrNewViewFieldEnmValue[i])){
			return false;
		}
	}
	return true;
}

Event.observe(window, 'load', function(){
	if(hasRight){
		ValidationHelper.addValidListener(function(){
			FloatPanel.disableCommand('saveData', false);
		}, "ObjectForm");
		ValidationHelper.addInvalidListener(function(){
			FloatPanel.disableCommand('saveData', true);
		}, "ObjectForm");
	}

	if(!hasRight()){
		FieldInfos.disable('ObjectForm');
		Element.hide('selectClassInfo');
		Element.hide('defaultSelectClassInfo');
		FloatPanel.disableCommand('saveData', true);
	}
	initSpecialValue();
	FieldInfos.initEvent();
	Event.observe("FieldGroupManage", 'click', selectFieldGroup);
	Event.observe("selectMetaView", 'click', function(){
		selectMetaView('RelationViewId',  '_relationViewId');
	});		
	Event.observe("selectFeatureMetaView", 'click', function(){
		selectMetaView('FeatureViewIds',  '_featureViewIds');
	});
	Event.observe("selectChannel", 'click', function(){
		selectChannel('FeatureChnlIds',  '_featureChnlIds');
	});
	
	initFieldGroup();

	initRelationViewChnl();
});

function initSpecialValue(){
	//init the checkbox status
	var checkboxArray = ['notNull', 'notEdit', 'hiddenField', 'IsURLField', 'inOutline', "inDetail", "searchField", "titleField","RADORCHK", "inMultiTable", "SELTODATA","identityField","PARENTOPTIONAL"];
	for (var i = 0; i < checkboxArray.length; i++){
		var oCheckbox = $(checkboxArray[i]);
		if(!oCheckbox) continue;
		oCheckbox.checked = oCheckbox.getAttribute("initValue") == "1" ? true : false;//值为true or false;
	}
	
	//设置多表字段是否显示在选择列表中
	var oMultiTableContainer = $('MultiTableContainer');
	var bFromMainTable = MultiTableContainer.getAttribute("initValue");
	if(bFromMainTable == 0){
		Element.show(oMultiTableContainer);
	}

	//字段为标题字段时设置其可以在概览中显示
	if($('titleField')){
		setTitleEvent($('titleField'));
	}
	if($('hiddenField')){
		setHiddenEvent($("hiddenField"), bFromMainTable);
	}
}

function setHiddenEvent(oHiddenField, nFromMainTable){
	if(nFromMainTable == 0) return;
	var oNotNull = $("notNull");
	var oNotEdit = $("notEdit");
	if(oHiddenField.checked){
		oNotNull.checked = false;
		oNotNull.disabled = true;
		oNotEdit.checked = false;
		oNotEdit.disabled = true;
	}else{
		oNotNull.disabled = false;
		oNotEdit.disabled = false;
	}
}

function saveData(){
	//如果校验不通过，return false，阻断事件的传播。使编辑页面不关闭。
	if(!beforeSaveCheck()){
		return false;
	}

	var relViewEl = $('RelationViewId');
	if(relViewEl){
		relViewEl.value = relViewEl.getAttribute('_RelationViewId');
	}
	
	if(relViewEl.value == ""){
		relViewEl.value = "-1";
	}

	var relViewChnlIdEl = $('RelationViewChnlId');
	if(relViewChnlIdEl){
		relViewChnlIdEl.value = relViewChnlIdEl.value;
	}
	
	var featureViewIdsEl = $('FeatureViewIds');
	if(featureViewIdsEl){
		featureViewIdsEl.value = featureViewIdsEl.getAttribute('_featureViewIds');
	}
	
	var featureChnlIdsEl = $('FeatureChnlIds');
	if(featureChnlIdsEl){
		featureChnlIdsEl.value = featureChnlIdsEl.getAttribute('_featureChnlIds');
	}

	
	//check the class info valid.
	FieldInfos.checkClassInfoValid(
		function(){
			var objectId = getParameter("objectId");
			var domOfClassId = $('classId');
			domOfClassId.value = domOfClassId.value.split(":")[0];
			if(domOfClassId.value.trim() == ""){
				domOfClassId.value = "0";
			}
			ProcessBar.start(wcm.LANG.METAVIEWFIELD_ALERT_13 || '执行保存操作');
			BasicDataHelper.call(FieldInfos.servicesName,FieldInfos.saveMethodName,"ObjectForm", true,function(transport, json){
				ProcessBar.exit();
				FieldInfos.isChanged = true;
				notifyFPCallback();
			});
			
		},
		function(sMsg){
			sMsg = sMsg == 'notFound' ? (wcm.LANG.METAVIEWFIELD_ALERT_14 || "不存在对应的分类法;") : (wcm.LANG.METAVIEWFIELD_ALERT_15 || "输入的不是正整数;")
			Element.update("validTip", "<font color='red'>" + sMsg + "</font>");
			FloatPanel.disableCommand('saveData', true);
		}
	);
	return false;
}

window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'saveData',
		name : wcm.LANG.METADATA_BUTTON_4 || '保存'
	}],
	size : [620, 400]
};

LockerUtil.register2(getParameter("objectId"), 1886731157, true, "saveData");

function initFieldGroup(){
	var fieldGroupSelect = $('FieldGroupId');
	if(!fieldGroupSelect)return;
	var oPostData = {
		MetaViewId : $('viewId').value
	}
	var currGroupId = fieldGroupSelect.getAttribute("_value") || 0;
	BasicDataHelper.JspRequest('../metaviewfieldgroup/viewfield_group_create.jsp',oPostData,false,function(_trans,_json){
		//移除当前的options
		for(var i=fieldGroupSelect.length-1;i>0;i--){
			if(i>0)fieldGroupSelect.remove(i);	
		}
		var groupInfos = com.trs.util.JSON.eval(_trans.responseText.trim());
		for(var i=0;i<groupInfos.length;i++){
			var groupInfo = groupInfos[i];
			var eOption = document.createElement("OPTION");
			fieldGroupSelect.appendChild(eOption);
			eOption.value = groupInfo.GROUPID;
			eOption.innerText = groupInfo.GROUPNAME;
			if(eOption.value == currGroupId){
				fieldGroupSelect.selectedIndex = i+1;
			}
		}
	});
}

//by CC 20120719 获取到所有的关联视图下的栏目列表
function initRelationViewChnl(){
	var relationViewChnlSelect = $('RelationViewChnlId');
	if(!relationViewChnlSelect)return;
	relationViewChnlSelect.title="";

	var relViewEl = $('RelationViewId');
	if(!relViewEl){return;}
	var relationViewId = relViewEl.getAttribute('_RelationViewId');
	if(relationViewId == -1 || relationViewId == 0){return;}
	var oPostData = {
		MetaViewId : relViewEl.getAttribute('_RelationViewId')
	}

	var currRelationViewChnlId = relationViewChnlSelect.getAttribute("_value") || 0;
	BasicDataHelper.JspRequest('./metaview_channel_list.jsp',oPostData,false,function(_trans,_json){

		for(var i=relationViewChnlSelect.length-1;i>0;i--){
			if(i>0)relationViewChnlSelect.remove(i);	
		}

		var channelInfos = com.trs.util.JSON.eval(_trans.responseText.trim());
		for(var i=0;i<channelInfos.length;i++){
			var channelInfo = channelInfos[i];
			var eOption = document.createElement("OPTION");
			relationViewChnlSelect.appendChild(eOption);
			eOption.value = channelInfo.CHANNELID;
			eOption.innerText = channelInfo.CHANNELPATH;
			if(eOption.value == currRelationViewChnlId){
				relationViewChnlSelect.selectedIndex = i+1;
				relationViewChnlSelect.title=channelInfo.CHANNELPATH;
			}
			
		}
	});
}

//调整title提示内容
function changeTitle(selectedIndex){
	var relationViewChnlSelect = $('RelationViewChnlId');
	relationViewChnlSelect.title = relationViewChnlSelect.options[selectedIndex].text; 
	//alert(relationViewChnlSelect.title);
}

function selectFieldGroup(){
	var sURL = WCMConstants.WCM6_PATH + "metaviewfieldgroup/metaviewfieldgroup_list.jsp";
	var sTitle = '字段分组维护列表';		
	wcm.CrashBoarder.get('fieldgroup_Select').show({
		title : sTitle,
		src : sURL,
		width: '650px',
		height: '400px',
		reloadable : true,
		params :  {MetaViewId : $('viewId').value},
		maskable : true,
		callback : function(_args){
			 initFieldGroup();
		}
	});
}


function selectMetaView(elId, elAttr){
	var nRelationViewId =  $(elId).getAttribute(elAttr) || 0;
	var params = {
		selectIds : nRelationViewId,
		excludedViewId:$('viewId').value,
		ContainsChildrenBox:false
	}
	wcm.MetaViewSelector.selectView(params, function(args){
		var relationViewObj = $(elId);
		var targetViewIds = args['ViewId'];
		if(!targetViewIds || targetViewIds.length <= 0){
			relationViewObj.value = "";
			relationViewObj.setAttribute(elAttr, "");
			
			//by CC 20120719 如果是取消关联视图的话，关联视图栏目也应该没有值。
			var relationViewChnlSelect = $('RelationViewChnlId');
			if(!relationViewChnlSelect)return;
			relationViewChnlSelect.title="";
			for(var i=relationViewChnlSelect.length-1;i>0;i--){
				if(i>0)relationViewChnlSelect.remove(i);	
			}
			return;
		}
		var targetViewId = targetViewIds[0] || 0;	
		var targetViewValue = "";
		if(targetViewId > 0){
			targetViewValue = targetViewId;
		}
		var viewName = args['selectedNames'][0];
		if(viewName.trim() != ''){
			targetViewValue = viewName;
		}
		relationViewObj.value = targetViewValue || "";
		relationViewObj.setAttribute(elAttr, targetViewId || "");
		
		//by CC 选择完成视图后，同步的使用到当前视图的栏目下拉列表也要同步进行调整
		initRelationViewChnl();
	});
}

function selectChannel(elId, elAttr){
	var selectedChannelIds = $(elId).getAttribute(elAttr);
	wcm.CrashBoarder.get("chnlSltCB").show({
		title : "选择特性库栏目",
		src : WCMConstants.WCM6_PATH+'include/channel_select_forCB.html',
		width: '480px',
		height: '440px',
		maskable : true,			
		params:{isRadio:1,ExcludeSelf:1,SELECTEDCHANNELIDS:selectedChannelIds,ExcludeTop:1,ExcludeVirtual:1,MultiSiteType:1},
		callback : function(params){
			$(elId).setAttribute(elAttr, (params[0] || []).join(","));
			$(elId).value = (params[1] || []).join(";");
		}
	});
}
