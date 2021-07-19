ValidationHelper.validByValidation = function(sValidation, sValue){
	if(!sValidation) return true;
	var validation = eval('({' + sValidation + '})');

	//valid required.
	if(validation["required"] == 1 && sValue.length <= 0){
		Ext.Msg.alert((validation["desc"] || "") + "不能为空");
		return false;
	}
	//valid allowExt
	if(validation["allowExt"] && validation["allowExt"].length>0 && sValue.length>0){
		try{
			FileUploadHelper.validFileExt(sValue, validation["allowExt"]);
		}catch(err){
			Ext.Msg.$alert(err.message);
			return false;
		}
	}
	//valid max length.
	var maxLen = validation['max_len'] || 0;
	if(maxLen > 0 && sValue.length > maxLen){
		Ext.Msg.alert(String.fromat("{0}大于最大长度[{1}]",validation["desc"] || "",maxLen));
		return false;
	}	
	return true;
};

function validData(){	
	var components = com.trs.ui.ComponentMgr.getAllComponents();
	for (var i = 0; i < components.length; i++){
		var validation = components[i].getProperty('validation');
		if(!validation) continue;
		if(!ValidationHelper.validByValidation(validation, components[i].getValue())) return false;
	}
	return true;
};
function selectRegion(){
	var currRegionName = $('regionNameText').innerHTML;
	var srcUrl = WCMConstants.WCM6_PATH + 'region/region_select_list.html?regionName=' + currRegionName;
	wcm.CrashBoarder.get('select_region').show({
		title : '选择导读',
		src : srcUrl,
		width:'750px',
		height:'400px',
		callback : function(params){
			var sRegionName = params['RegionName'];
			$('regionNameText').innerHTML = sRegionName;
			var inputEls = document.getElementsByTagName('INPUT');
			for (var i = 0; i < inputEls.length; i++){
				var currEl = inputEls[i];
				if(currEl.getAttribute("bName") == 'true')
					currEl.value = sRegionName;
			}
			this.close();
			 
		}
	});	
}
function makeData(func){
	if(!ValidationHelper.doValid("data")){
		return false;
	}
	func('data');
}
/*
*初始化校验处理
*/
function initValidation(){
	ValidationHelper.initValidation();
};
 
 
 
 