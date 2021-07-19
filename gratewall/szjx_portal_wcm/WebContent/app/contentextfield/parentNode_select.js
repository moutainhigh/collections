var SelectedIds = [];
function SetSelected(_nPhotoId,_bChecked){
	if(!_bChecked){
		SelectedIds = SelectedIds.remove(_nPhotoId);
	}
	else if(_bChecked){
		SelectedIds.push(_nPhotoId);
	}
}
function Ok(){
	if(SelectedIds.length == 0){
		Ext.Msg.alert(wcm.LANG.CONTENTEXTFIELD_CONFIRM_45 || "请选择要继承的扩展字段!");
		return false;
	}
	var hostId = getParameter("siteid") || getParameter("channelid");
	var hostType = getParameter("siteid").length > 0 ? 103:101;
	var oPostData = {HostType:hostType,HostId:hostId,SelectedIds:SelectedIds.join(",")};
	BasicDataHelper.call("wcm61_contentextfield","inhertSpecificFromParent",oPostData,true,function(_transport,_json){
		notifyFPCallback();
		FloatPanel.close();
	},function(_transport,_json){
		window.DefaultAjax500CallBack(_transport,_json,this);			
		FloatPanel.disableCommand("Ok",false);
	});
	return false;
}
$MsgCenter.on({
	objType : "extfieldNode",
	afterclick : function(event){
		//负责导航树对应的页面切换
		var context = event.getContext();
		var objId = context.objId;
		var objType = context.objType;
		mySrc = 'extfield_select_list.html?';
		var sParams = '';
		switch(objType){
			case WCMConstants.OBJ_TYPE_WEBSITEROOT:
				mySrc += "SiteType=" + objId;
				break;
			case WCMConstants.OBJ_TYPE_WEBSITE:
				mySrc += "siteid=" + objId;
				break;
			case WCMConstants.OBJ_TYPE_CHANNEL:
				mySrc += "channelid=" + objId ;
				break;
		}
		$('photo_list').src = mySrc;
		return false;
	}
});