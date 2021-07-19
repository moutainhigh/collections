//列表内部打开新列表
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_XTHUMBLIST,
	selectedchange : function(event){
		event.cancelBubble = true;
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_XTHUMBCELL,
	afterclick : function(event){
		event.cancelBubble = true;
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afterselect : function(event){
		event.cancelBubble = true;
	}
});
PageContext.m_CurrPage = $MsgCenter.$currPage();
