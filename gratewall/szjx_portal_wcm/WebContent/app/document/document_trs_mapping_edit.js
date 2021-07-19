//TO Extend
Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : false,
	/**/
	objectType : '',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		//return WCMConstants.OBJ_TYPE_DOCRECYCLE;
	},
	rowInfo : {
		name : true,
		key : true
	}
});
Ext.apply(PageContext, {
	_doBeforeLoad : function(){
		return false;
	},
	getContext : function(){
		var context = this.getContext0();
		//var bIsChannel = !!getParameter("ChannelId");
		//var bIsSite = !!getParameter("SiteId");
		//Ext.apply(context, {
		//	relateType : bIsChannel ? 'docrecycleInChannel' :
		//		(bIsSite ? 'docrecycleInSite' : 'docrecycleInRoot')
		//});
		return context;
	},
	//**
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		//Ext.applyIf(this.params, {
		//	HostType : PageContext.intHostType,
		//	hostId : PageContext.hostId
		//});
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	//*/
	/**/
	pageFilters : null,
	pageTabs : null,
	gridFunctions : function(){
		wcm.Grid.addFunction('edit', function(event){
			var strTempValue = event.getObj().getPropertyAsString('name', '');
			var strTempKey = event.getObj().getPropertyAsString('key', '');
			PageContext.onEdit(strTempValue,strTempKey);
		});
		wcm.Grid.addFunction('delete', function(event){
			var strTempValue = event.getObj().getPropertyAsString('name', '');
			var strTempKey = event.getObj().getPropertyAsString('key', '');
			PageContext.doDelete(strTempValue,strTempKey);
		});
	},
	doDelete : function(_strXMLField, _strSQLField) {
	
		var oPostData = {
			"FileName" : $("input").value,
			"XmlField" : _strXMLField
		}
		BasicDataHelper.JspRequest('../document/document_trs_mapping_doedit.jsp',
			oPostData,true,function(){
				deleteRow(_strXMLField);
				$('XMLField').value = "";
				$('TRSField').value = "";
				removeValue(_strSQLField);
				//解锁
				historySQLField = "";
				$('SQLField').disabled = false; //解锁下拉框
				window.location.reload();
		});
	},
	onEdit : function(_strTRSField, _strSQLField) {
		$('XMLField').value = _strTRSField;
		$('TRSField').value = _strTRSField;
		$('SQLField').value = _strSQLField;
		if(_strSQLField == "DOCTITLE" || _strSQLField == "DOCCONTENT") {
			$('SQLField').disabled = true; //锁定下拉框
		} else {
			$('SQLField').disabled = false; //解锁下拉框
		}
		//记下SQL字段名
		historySQLField = _strSQLField;
		$('btn_edit').disabled = false;
	},
	doAdd : function(){
		var strXMLField = $('XMLField').value;
		var strTRSField = $('TRSField').value.trim();
		var strSQLField = $('SQLField').value;
		if($('SQLField').selectedIndex==0){
			Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_59 || '请选择数据库字段.');
			return;
		}
		var strSQLName  = $('SQLField').options[$('SQLField').selectedIndex].innerHTML;
		if(strTRSField == "") {
			Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_60 || "请指定TRS库字段名.");
			$('TRSField').focus();
			return;
		}
		if(historySQLField != strSQLField) {
			if(exist(strSQLField)) {
				Ext.Msg.$alert(String.format(wcm.LANG.DOCUMENT_PROCESS_61 || ("数据库字段 [{0}] 已经存在!"),strSQLName));
				return;
			}
		}

		$('SQLField').disabled = false; //解锁下拉框

		var oPostData = {
			"FileName" : $("input").value,
			"XmlField" : strXMLField,
			"TRSField" : strTRSField,
			"SQLField" : strSQLField
		}
		BasicDataHelper.JspRequest('../document/document_trs_mapping_doedit.jsp',
			oPostData,true,function(){
				$('XMLField').value = "";
				$('TRSField').value = "";
				var arElements = [];
				arElements.push("<center>"+'<span title="'+strTRSField+'" style="width:80px;height:24px;line-height:24px;overflow:hidden;">'+strTRSField+'</span>'+"</center>");
				//arElements.push("<center>"+strTRSField+"</center>");
				arElements.push("<center>"+strSQLName+"</center>");
				arElements.push("<center><a href=\"#\" WCMAnt:param=\"document_trs_mapping_edit.jsp.modify\" onClick=\"PageContext.onEdit('"+strTRSField+"','"+strSQLField+"');return false; \">修改</a></center>");
				if(strSQLField != "DOCTITLE" && strSQLField != "DOCCONTENT") {
					arElements.push("<center><a href=\"#\" WCMAnt:param=\"document_trs_mapping_edit.jsp.delete\" onClick=\"PageContext.doDelete('"+strTRSField+"', '"+strSQLField+"');return false;\">删除</a></center>");
				} else {
					arElements.push("<center>&nbsp;</center>");
				}
				if(strXMLField == "") {
					insertRow($('showFieldMaps'),arElements, strTRSField, -1);
				} else {
					var rowIndex = deleteRow(strXMLField);
					insertRow($('showFieldMaps'),arElements, strTRSField, rowIndex);
				}
				//加入Array
				add(strSQLField);
				$('SQLField').value = 0;
				$('btn_edit').disabled = true;
				//解锁
				historySQLField = "";
				window.location.reload();
		});
	}
	

});


function add(_value) {
	if(!arSQLFields.include(_value))
	arSQLFields.push(_value);
}
function exist(_value) {
	//alert(_value + ":" + TRSArray.toString(arSQLFields));
	return arSQLFields.include(_value);
}
//table中插入行
function insertRow(myTable, arElements, rowId, rowIndex) {
	var thisRow = myTable.insertRow(rowIndex);
	thisRow.className = "grid_row";
	thisRow.id = rowId;
	var thisCol;
	for(var i=0; i<arElements.length; i++) {
		thisCol = thisRow.insertCell(-1);
		thisCol.innerHTML = arElements[i];
	}
}
//table中删除行
function deleteRow(rowId) {
	var thisRow = $(rowId);
	$('showFieldMaps').deleteRow(thisRow.sectionRowIndex);
	return thisRow.rowIndex;
}

function removeValue(_value) {
	arSQLFields = arSQLFields.remove(_value);
}
function CheckNewAdd(){
	var _strSQLField = $('SQLField').value;
	if(_strSQLField == "DOCTITLE" || _strSQLField == "DOCCONTENT"||arSQLFields.include(_strSQLField)) {
		$('btn_add').disabled = true;
	} else {
		$('btn_add').disabled = false;
		$('btn_edit').disabled = true;
	}
}

Event.observe('btn_add', 'click', function(event){
	PageContext.doAdd();
})

Event.observe('btn_edit', 'click', function(event){
	PageContext.doAdd();
})

$MsgCenter.un($MsgCenter.getListener('sys_gridrow'));
//$MsgCenter.un($MsgCenter.getListener('sys_gridcell'));
$MsgCenter.un($MsgCenter.getListener('sys_allcmsobjs'));
