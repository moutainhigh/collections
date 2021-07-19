PageContext.setFilters0 = PageContext.setFilters;
PageContext.setFilters = function(filters, info){
	var info = PageContext.getCustomizeFilterInfo({displayNum : 6, filterType : 0})
	this.setFilters0(filters, info);
};
Ext.apply(PageContext, {
	contextMenuEnable : true,
	gridDraggable : true && !getParameter("doSearch"),
	getContext : function(){
		var context0 = this.getContext0();
		var bIsSearch = !!PageContext.getParameter("IsSearch");
		var bIsChannel = !!PageContext.getParameter("ChannelId");
		if(bIsSearch){
			var context = Ext.applyIf({
				isChannel : false,
				host : {
					objType : 'docSearchContext',
					objId : bIsChannel ? PageContext.getParameter("ChannelId")
										: PageContext.getParameter("SiteId"),
					right : PageContext.getParameter("RightValue"),
					isVirtual : PageContext.getParameter("IsVirtual"),
					detail : location.search.substring(1)
				}
			}, context0);
			return context;
		}
		var context = Ext.applyIf({
			isChannel : bIsChannel,
			host : {
				right : PageContext.getParameter("RightValue"),
				isVirtual : PageContext.getParameter("IsVirtual"),
				objType : bIsChannel ? WCMConstants.OBJ_TYPE_CHANNEL
										: WCMConstants.OBJ_TYPE_WEBSITE,
				objId : bIsChannel ? PageContext.getParameter("ChannelId")
										: PageContext.getParameter("SiteId")
			}
		}, context0);
		context['CanSort'] = this.CanSort;
		return context;
	},
	getCustomizeFilterInfo : function(def){
		if(!parent.m_CustomizeInfo) return def;
		var displayNum = parent.m_CustomizeInfo.listFilterSize.paramValue;
		var filterType = parent.m_CustomizeInfo.documentDefaultShow.paramValue;
		if(filterType == "normal"){
			filterType = 0;
		}
		return {displayNum : displayNum, filterType : filterType};
	},
	getParameter : function(paramName){
		paramName = paramName.toLowerCase();
		if(['channelid', 'siteid'].include(paramName)){
			return getParameter(paramName) || getParameter(paramName + 's') || '';
		}
		return getParameter.apply(null, arguments);
	},
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
					return wcm.LANG.DOCUMENT_PROCESS_197 || "当前文档列表不支持排序";
				}
				if(PageContext.params["ORDERBY"]){
					return wcm.LANG.DOCUMENT_PROCESS_198 || "自动排序列表不支持手动排序";
				}
				if(!this.sortable){
					return wcm.LANG.DOCUMENT_PROCESS_199 || "当前文档没有权限排序";
				}
				return String.format("[文档RecID-{0}]",sCurrId);
			},
			_isSortable : function(row){
				if(!PageContext.CanSort || PageContext.params["ORDERBY"]) return false;
				var sRight = row.dom.getAttribute("right", 2);
				//排序的权限调整为用户在当前栏目下是否有编辑文档权限
				if(sRight!=null&&!wcm.AuthServer.checkRight(sRight, 62)){
					return false;
				}
				return true;
			},
			_move : function(srcRow, iPosition, dstRow, eTargetMore){
				//iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
				if(!PageContext.CanSort&&PageContext.params["ORDERBY"]) return false;
				if(!srcRow) return;
				var bCurrTopped = srcRow.getAttribute("isTopped")=='true';
				var bTargetTopped = dstRow.getAttribute("isTopped")=='true';
				var docid = srcRow.getAttribute('docid');
				var rowId = srcRow.getAttribute('rowId');
				var targetDocId = dstRow.getAttribute('docid');
				if(bCurrTopped!=bTargetTopped){//当前行与目标行,一行是置顶,一行是非置顶
					var bFixedUp = true;
					if(iPosition==0){//有前一行,插入到目标行之后的情况
						if(!bCurrTopped&&eTargetMore!=null){//非置顶行判断前一行和后一行
							//若下一行为非置顶行,则不交叉
							//反之,则交叉
							var bTargetMoreTopped = eTargetMore.getAttribute("isTopped")=='true';
							if(!bTargetMoreTopped){
								//用后一行的数据,表示插入到它之前
								targetDocId = eTargetMore.getAttribute('docid');
								iPosition = (iPosition==0)?1:0;
								bFixedUp = false;
							}
						}
						else if(!bCurrTopped&&eTargetMore==null){//非置顶行上一行为置顶行,下一行无
							//相当于至少有n-1个置顶行,而被拖动的那行是非置顶行
							//所以非置顶行本身未移动,此种情况其实不需要考虑
							//考虑的话不计为交叉
							bFixedUp = false;
						}
						else if(bCurrTopped){//置顶行的上一行为非置顶行,必然交叉
							bFixedUp = true;
						}
					}
					else{//无前一行,但有后一行,插入到目标行之前的情况
						if(!bCurrTopped){//当前行非置顶,插在置顶行之前必然交叉
							bFixedUp = true;
						}
						else{//当前行置顶,插在非置顶行之前必然不交叉
							//但此种情况可以不考虑
							//当前置顶行拖动后前无置顶行(在第一行),后无置顶行
							//可以知道当前只有一个置顶行,且没有交换位置
							//不应该到这里来
							bFixedUp = false;
							//若来到这里就不能按置顶交换顺序的方式处理了
							bCurrTopped = false;
						}
					}
					if(bFixedUp){
						Ext.Msg.$timeAlert(wcm.LANG.DOCUMENT_PROCESS_201 || '置顶文档与非置顶文档间不能交叉排序.',5);
						return false;
					}
				}
				if(!confirm(wcm.LANG.DOCUMENT_PROCESS_202 || '您确定要调整文档的顺序?')) return false;
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
					wcm.domain.ChnlDocMgr['saveorder'](oPostData, {
						onSuccess : function(){
							PageContext.updateCurrRows(rowId);
						},
						onFailure : function(trans,json){
							wcm.FaultDialog.show({
								code		: $v(json,'fault.code'),
								message		: $v(json,'fault.message'),
								detail		: $v(json,'fault.detail'),
								suggestion  : $v(json,'fault.suggestion')
							}, wcm.LANG.DOCUMENT_PROCESS_51 || '与服务器交互时出现错误' , function(){
								PageContext.updateCurrRows();
							});
						}
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
									this.dd.hintInTree = String.format("[引用多篇文档:{0}]",top.DragAcross.ObjectIds);
								}
								else{
									this.dd.hintInTree = String.format("[文档RecID-{0}]",top.DragAcross.ObjectId);
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
									Ext.Msg.report(_json, wcm.LANG.DOCUMENT_PROCESS_47 || '文档引用结果', function(){
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


/*   操作面板需要执行相关函数 */
Ext.apply(PageContext, {
	//下面这个函数没用了应该
	filterDocSources : function(event, valueDom, wcmEvent){
		var oHelper = BasicDataHelper;
		var oPostData = {
			k : this.value,
			r : new Date().getTime()
		};
		var elSuggestion = $('suggestion_cnt');
		if(!elSuggestion){
			elSuggestion = document.createElement('DIV');
			elSuggestion.style.position = 'absolute';
			elSuggestion.id = 'suggestion_cnt';
			elSuggestion.style.zIndex = 1000;
			document.body.appendChild(elSuggestion);
			Element.hide(elSuggestion);
		}
		oHelper.JspRequest(WCMConstants.WCM6_PATH + 'system/filter_docsource.jsp', oPostData,
			true, function(_trans){
				var extEvent = new Ext.EventObjectImpl(event);
				var point = extEvent.getPoint();
				var x = point.x + 4;
				var y = point.y + 4;
				var elSuggestion = $('suggestion_cnt');
				var oBubbler = new wcm.BubblePanel(elSuggestion);
				Element.update('suggestion_cnt', _trans.responseText);	
				oBubbler.bubble([x,y], function(_Point){
					return [_Point[0]-this.offsetWidth,_Point[1]];
				});
				Element.show(elSuggestion);
			}
		);
	},
	//在属性面板中,构造属性保存时,需要提交的参数
	_buildParams : function(wcmEvent, actionType , valueDom){
		if(wcmEvent.length() <= 0) return; 
		if(wcmEvent.getObjs().getType() != WCMConstants.OBJ_TYPE_CHNLDOC) return;
		var obj = wcmEvent.getObjs().getAt(0);
		if(actionType=='save'){
			var host = wcmEvent.getHost();
			return {
				Force : {
					ObjectId : obj ? obj.getDocId() : 0
				},
				ChannelId : !!PageContext.getParameter("IsSearch") ? valueDom.getAttribute("channelId", 2): PageContext.getParameter("ChannelId") || 0,
				SiteId : !!PageContext.getParameter("IsSearch") ? 0:PageContext.getParameter("SiteId") || 0
			}
		}else if(actionType=='changestatus'){
			return {
				objectIds : obj.getId(),
				StatusId : valueDom.getAttribute("_fieldValue", 2)
			};
		}
	}
});

//详细信息面板中文档来源的suggestion,利用消息机制绑定元素
var sg1 = null;
function showSuggestion(){
	setTimeout(function(){
		if(!$('DocSourceName'))return;
		sg1 = new wcm.Suggestion();
		sg1.init({
			el : 'DocSourceName',
			request : function(sValue){
				var all = [];
				BasicDataHelper.JspRequest(
				WCMConstants.WCM6_PATH + "nav_tree/source_create.jsp?SourceName=",
				{SiteId : PageContext.event.getObj().getPropertyAsInt('siteId', 0) || 0},  true,
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
	},1000);
}
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CHNLDOC,
	afterselect : function(event){
		if(sg1) {
			sg1.destroy();
			sg1 = null;
		}
		if(event.length() != 1) return;
		showSuggestion();
	}
});
