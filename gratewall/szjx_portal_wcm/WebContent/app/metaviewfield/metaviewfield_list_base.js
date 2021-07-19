Event.observe(window, 'load', function(){
	PageContext.gridDraggable = true ;
	var viewFieldGridDragDrop = new wcm.dd.GridDragDrop({
		id : 'wcm_table_grid', 
		rootId : 'grid_body'
	});
	Ext.apply(viewFieldGridDragDrop, {
		_isSortable : function(_eRoot){
			return true || PageContext.checkRight();
		},
		_getHint : function(row){
			return row.dom.getAttribute("anotherName");
		},
		_move : function(srcRow, iPosition, dstRow, eTargetMore){
			BasicDataHelper.call("wcm6_MetaDataDef", 'changeViewFieldOrder', {
				fromId : srcRow.getObjId(),
				toId : dstRow.getObjId(),
				position : iPosition
			}, true, function(transport, json){
				var info = {objId : srcRow.getObjId(), objType : WCMConstants.OBJ_TYPE_METAVIEWFIELD};
				CMSObj.createFrom(info, PageContext.getContext()).afteredit();
			});
			return true;
		}
	});
});