Ext.apply(FieldInfos, {
	servicesName : 'wcm6_MetaDataDef',
	findMethodName : 'findDBFieldInfoById',
	saveMethodName : 'saveDBFieldInfo',
	setControlsDefault : function(){
		//clear some controls.
		var resetControls = ['fieldName','anotherName','enmValue','classId','dbScale','defaultValue','notNull','notEdit','hiddenField','RADORCHK','titleField','searchField','validator'];
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
		$('radioOrCheckContainer').style.display = $('classIdContainer').style.display;

		//附件和相关文档类型     与“检索字段”的关联。
     	var oSearchField = $("searchField");
		var noSearchAttrArray = ['appendix', 'relation']; 
		if(noSearchAttrArray.include(sFieldType)){
			 oSearchField.checked = false;
			 oSearchField.disabled = true;
		}else{
			 oSearchField.disabled = false;
		}

		//隐藏字段和不能为空字段的关联。

	}
});

Event.observe(window, 'load', function(){
	ValidationHelper.addValidListener(function(){
		FloatPanel.disableCommand('saveData', false);
	}, "ObjectForm");

	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand('saveData', true);
	}, "ObjectForm");
	
	initSpecialValue();
	FieldInfos.initEvent();
});

function initSpecialValue(){
	//init the checkbox status
	var checkboxArray = ['notNull', 'notEdit', 'hiddenField', 'inOutline', "inDetail", "searchField", "titleField", "RADORCHK"];
	for (var i = 0; i < checkboxArray.length; i++){
		var oCheckbox = $(checkboxArray[i]);
		if(!oCheckbox) continue;
		oCheckbox.checked = oCheckbox.getAttribute("initValue") == "1" ? true : false;
	}

	//字段为标题字段时设置其可以在概览中显示
	if($('titleField')){
		setTitleEvent($('titleField'));
	}

	//新建时，初始化一些字段选中
	if(getParameter("OBJECTID") == 0){
		var checkedArray = ["inDetail"];
		$("classId").value = "";
		for (var i = 0; i < checkedArray.length; i++){
			var oCheckbox = $(checkedArray[i]);
			if(!oCheckbox) continue;
			oCheckbox.checked = true;
		}
	}		
}

WCMConstants.OBJ_TYPE_METADBFIELD = 'MetaDBField';
var addCount = 1;
function saveData(needClose){
	var sFieldName = $F('fieldName').toLowerCase();
	//如果校验不通过，return false，阻断事件的传播。使编辑页面不关闭。
	if(!beforeSaveCheck()){
		return false;
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
			$('objectId').value = objectId;
			$('tableInfoId').value = getParameter("tableInfoId") || '0';

			ProcessBar.start(wcm.LANG.METAVIEWFIELD_ALERT_13 || '执行保存操作');
			BasicDataHelper.call(FieldInfos.servicesName,FieldInfos.saveMethodName,"ObjectForm", true,function(transport, json){
				ProcessBar.exit();
				FieldInfos.isChanged = true;
				if(objectId != "0"){
					FloatPanel.close(true);
					var info = {objId : objectId, objType : WCMConstants.OBJ_TYPE_METADBFIELD};					
					CMSObj.createFrom(info, {}).afteredit();
				}else{
					FieldInfos.setControlsDefault();
					FieldInfos.fieldTypeChange();
					addCount++;		
					var info = {objId : $v(json,"METADBFIELD.DBFIELDINFOID"), objType : WCMConstants.OBJ_TYPE_METADBFIELD};					
					CMSObj.createFrom(info, {}).afteradd();
					var sTilte = String.format("新建元数据字段")+"<span style=\'font-size:12px;\'>"+"--" + String.format("新建第<font color=\'blue\'>[{0}]</font>个",addCount) + "</span>";
					FloatPanel.setTitle(sTilte);
					$('fieldName').focus();
					FloatPanel.disableCommand('saveData', true);
				}
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
		name : wcm.LANG.METADBFIELD_1 || '确定'
	}],
	size : [620, 350]
};
LockerUtil.register2(getParameter("objectId"), 1269981572, true, "saveData");