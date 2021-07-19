Ext.apply(PageContext, {
	contextMenuEnable : true,
	gridDraggable : true,
	initDraggable : function(){
		var docGridDragDrop = new wcm.dd.GridDragDrop({
			id : 'wcm_table_grid', 
			rootId : 'grid_body',
			captureEnable:false
		});
		docGridDragDrop.addListener('dispose', function(){
			top.DragAcross = null;
			delete this.hintInSelf;
			delete this.hintInTree;
		});
		Ext.apply(docGridDragDrop, {
			_getHint : function(row){
				if(this.hintInSelf) return this.hintInSelf;
				if(!top.DragAcross){
					top.DragAcross = {};
				}
				var sCurrId = row.getObjId();
				var aSelectedIds = wcm.Grid.getRowIds(true);
				if(!aSelectedIds.include(sCurrId)) aSelectedIds.push(sCurrId);			
				Object.extend(top.DragAcross,{
					ObjectType : 605 ,
					FolderId :  row.getAttribute("channelid"),
					ObjectId : sCurrId,
					ObjectIds : aSelectedIds
				});
				if(!PageContext.CanSort){
					return wcm.LANG.infoviewdoc_list_base_1000 || "当前表单列表不支持排序";
				}
				if(PageContext.params["OrderBy"]){
					return wcm.LANG.infoviewdoc_list_base_2000 || "自动排序列表不支持手动排序";
				}
				if(!this.sortable){
					return wcm.LANG.infoviewdoc_list_base_3000 || "当前表单没有权限排序";
				}
				return "[表单-"+sCurrId+"]";
			},
			_isSortable : function(row){
				if(!PageContext.CanSort || PageContext.params["OrderBy"]) return false;
				var sRight = row.dom.getAttribute("right", 2);
				if(sRight!=null&&!wcm.AuthServer.checkRight(sRight, 62)){
					return false;
				}
				return true;
			},
			_move : function(srcRow, iPosition, dstRow, eTargetMore){
				//iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
				if(!PageContext.CanSort&&PageContext.params["OrderBy"]) return false;
				var bCurrTopped = srcRow.getAttribute("isTopped")=='true';
				var bTargetTopped = dstRow.getAttribute("isTopped")=='true';
				var docid = srcRow.getAttribute('docid');
				var rowId = srcRow.getAttribute('rowId');
				var targetDocId = dstRow.getAttribute('docid');
				if(bCurrTopped!=bTargetTopped){//当前行与目标行，一行是置顶，一行是非置顶
					var bFixedUp = true;
					if(iPosition==0){//有前一行，插入到目标行之后的情况
						if(!bCurrTopped&&eTargetMore!=null){//非置顶行判断前一行和后一行
							//若下一行为非置顶行，则不交叉
							//反之，则交叉
							var bTargetMoreTopped = eTargetMore.getAttribute("isTopped")=='true';
							if(!bTargetMoreTopped){
								//用后一行的数据，表示插入到它之前
								targetDocId = eTargetMore.getAttribute('docid');
								iPosition = (iPosition==0)?1:0;
								bFixedUp = false;
							}
						}
						else if(!bCurrTopped&&eTargetMore==null){//非置顶行上一行为置顶行，下一行无
							//相当于至少有n-1个置顶行,而被拖动的那行是非置顶行
							//所以非置顶行本身未移动,此种情况其实不需要考虑
							//考虑的话不计为交叉
							bFixedUp = false;
						}
						else if(bCurrTopped){//置顶行的上一行为非置顶行,必然交叉
							bFixedUp = true;
						}
					}
					else{//无前一行，但有后一行，插入到目标行之前的情况
						if(!bCurrTopped){//当前行非置顶，插在置顶行之前必然交叉
							bFixedUp = true;
						}
						else{//当前行置顶，插在非置顶行之前必然不交叉
							//但此种情况可以不考虑
							//当前置顶行拖动后前无置顶行(在第一行)，后无置顶行
							//可以知道当前只有一个置顶行，且没有交换位置
							//不应该到这里来
							bFixedUp = false;
							//若来到这里就不能按置顶交换顺序的方式处理了
							bCurrTopped = false;
						}
					}
					if(bFixedUp){
						Ext.Msg.$timeAlert(wcm.LANG.infoviewdoc_list_base_5000 || '置顶表单与非置顶表单间不能交叉排序.',5);
						return false;
					}
				}
				if(!confirm(wcm.LANG.infoviewdoc_list_base_6000 || '您确定要调整表单的顺序？')) return false;
				if(bCurrTopped){
					var oPostData = {
						"TopFlag" : 3,/*表示不改变置顶设置*/
						"ChannelId" : PageContext.getParameter("channelid"),
						"DocumentId" : docid,
						"Position" : iPosition,
						"TargetDocumentId" : targetDocId
					}
					BasicDataHelper.call('wcm6_viewdocument', 'setTopDocument', oPostData, true, function(){
						PageContext.updateCurrRows(rowId);
					});
				}
				else{
					var oPostData = {
						FromDocId:docid,
						ToDocId:targetDocId,
						position:iPosition,
						channelid: PageContext.getParameter("channelid")
					};
					BasicDataHelper.call('wcm6_viewdocument', 'changeOrder', oPostData, false, function(){
						PageContext.updateCurrRows(rowId);
					}, function(trans,json){
						wcm.FaultDialog.show({
							code		: $v(json,'fault.code'),
							message		: $v(json,'fault.message'),
							detail		: $v(json,'fault.detail'),
							suggestion  : $v(json,'fault.suggestion')
						}, wcm.LANG.infoviewdoc_list_base_7000 || '与服务器交互时出现错误' , function(){
							PageContext.updateCurrRows(rowId);
						});
					});
				}
				return true;
			}
		});

		var accrossDragger = new wcm.dd.AccrossFrameDragDrop(docGridDragDrop);
		Ext.apply(accrossDragger, {
			getWinInfos : function(){
				if(!top.$('nav_tree'))return [];
				return [
					{win : top}, 
					{
						win : top.$('nav_tree').contentWindow,
						enterMe : function(event, target, opt){
							if(!this.dd.hintInTree){
								if(top.DragAcross.ObjectIds.length>1){
									this.dd.hintInTree = String.format("[引用多篇表单:{0}]",top.DragAcross.ObjectIds);
								}
								else{
									this.dd.hintInTree = String.format("[表单-{0}]",top.DragAcross.ObjectId);
								}
							}
							this.dd.dragEl.innerHTML = this.dd.hintInTree;
						},
						leaveMe : function(event, target, opt){
							this.dd.dragEl.innerHTML = this.dd._getHint(this.dd.row);
						},
						endDrag : function(event, target, opt){
							if(!top.DragAcross || !top.DragAcross.TargetFolderId) return;
							var objId = top.DragAcross.ObjectId;
							BasicDataHelper.call('wcm6_viewdocument', 'quote', {
								"ObjectIds" : top.DragAcross.ObjectIds,
								"ToChannelIds" : top.DragAcross.TargetFolderId
							}, true, function(_transport,_json){
									Ext.Msg.report(_json, wcm.LANG.infoviewdoc_list_base_9000 || '表单引用结果', function(){
										var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CHNLDOC};
										CMSObj.createFrom(info, PageContext.getContext()).afteredit();
									});
								},
								function(_transport,_json){
									$render500Err(_transport,_json);
								}
							);
						}
				}];	
			}
		});
	}
});