Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
	isLocal : false,
	
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_MetaDataCenter',
	ObjectMethodName : 'queryViewDatas',

	AbstractParams : {
	},//服务所需的参数

	//是否有操作面板
//	enableAttrPanel : false,

	//为了使页面具有行为,定义Mgr对象
	ObjectMgr : ViewTemplateMgr,

	//为了右侧显示操作栏和属性栏,定义右侧面板的类型
	ObjectType	: 'ViewTemplate'
});

Object.extend(PageContext.PageNav,{
	UnitName : '条',
	TypeName : '数据'
});

Object.extend(Grid,{
	draggable : true,
	keyD : function(event){//Trash
		this._delete(event);
	},
	keyDelete : function(event){//Trash
		this._delete(event);
	},
	keyN : function(event){//Edit
		//1.根据页面传入的权限判断是否有权限操作
		if(!isAccessable4WcmObject(PageContext.params.RightValue||'0',31)){
			$timeAlert('您没有权限在当前栏目新建记录.',5);
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		PageContext.ObjectMgr.add(0, PageContext.params);		
	},
	keyE : function(event){//Edit
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("edit", "修改")){
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length == 1 ){
			PageContext.ObjectMgr.edit(pSelectedIds[0], PageContext.params);
		}
	},
	
	//==================内部方法========================//
	_hasRight : function(_sOperation, _sOperationName){
		var ObjectIds = this.checkRight(_sOperation, _sOperationName || _sOperation);
		if(ObjectIds&&ObjectIds.length>0){
			return true;
		}
		return false;
	},

	_delete : function(event){//Trash
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("delete", "删除")){
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length>0 ){
			PageContext.ObjectMgr["delete"](pSelectedIds.join(','), PageContext.params);
		}
	}
});


//拖动功能
Object.extend(com.trs.wcm.ListDragger.prototype,{
	_isEnableAccross : function(){
		return false;
	},
	_getHint : function(_eRoot){
		//
		if(!top.DragAcross){
			top.DragAcross = {};
		}
		//TODO channelid处理
		var sDocId = _eRoot.getAttribute("grid_rowid",2);
		var aCheckboxs = document.getElementsByName("RecId");
		var bCurrSelected = false;
		var aSelectDocIds = [];
		for(var i=0;i<aCheckboxs.length;i++){
			if(!aCheckboxs[i].checked)continue;
			if(aCheckboxs[i].value==sDocId){
				bCurrSelected = true;
			}
			aSelectDocIds.push(aCheckboxs[i].value);
		}
		if(!bCurrSelected){
			aSelectDocIds.push(sDocId);
		}
		Object.extend(top.DragAcross,{
			ObjectType : 605 ,
			FolderId :  _eRoot.getAttribute("channelid", 2),
			ObjectId : sDocId,
			ObjectIds : aSelectDocIds
		});
/*
		if(!PageContext.CanSort){
			return "当前文档列表不支持排序";
		}
//*/
		if(PageContext.params["OrderBy"]){
			return "自动排序列表不支持手动排序";//'<div style="width:20px;height:20px;" class="forbid_sort"></div>';
		}

		if(!this.sortable){
			return "当前文档没有权限排序";
		}
		if(PageContext.params["ContainsChildren"] == 1){
			return "当前文档列表包含子栏目数据,不支持排序";
		}

		return "[文档-"+sDocId+"]";//'<div style="width:20px;height:20px;" class="doctype_'+_eRoot.getAttribute("doctype")+'"></div>';
	},
	_getSelectedHint : function(_eRoot){
		//
		if(top.DragAcross.ObjectIds.length>1){
			return "[引用多篇文档:"+top.DragAcross.ObjectIds+"]";
		}
		else{
			return "[文档-"+top.DragAcross.ObjectId+"]"
		}
	},
	_onDrop : function(){
		if(top.DragAcross&&top.DragAcross.TargetFolderId){
			$documentMgr.quoteTo(top.DragAcross.ObjectIds,top.DragAcross.TargetFolderId);
		}
		top.DragAcross = null;
	},
	_isSortable : function(_eRoot){
		//
		return true;
		if(!PageContext.params["OrderBy"]&&PageContext.CanSort){
			var sRight = _eRoot.getAttribute("right",2);
			var nRightIndex = _eRoot.getAttribute("sortableRightIndex",2);
			if(sRight!=null&&!isAccessable4WcmObject(sRight,nRightIndex)){
				return false;
			}
			return true;
		}
		return false;
	},
	_move : function(_eCurr,_iPosition,_eTarget,_eTargetMore){
		//_iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
		//if(!PageContext.CanSort || PageContext.params["OrderBy"]){
		if(PageContext.params["OrderBy"]){
			return false;
		}
		if(PageContext.params["ContainsChildren"] == 1){
			return false;
		}

		//当前行是否为置顶行?
		var bCurrTopped = _eCurr.getAttribute("isTopped",2)=='true';
		var bTargetTopped = _eTarget.getAttribute("isTopped",2)=='true';
		var docid = _eCurr.getAttribute('docid', 2);
		var targetDocId = _eTarget.getAttribute('docid', 2);
		if(bCurrTopped!=bTargetTopped){//当前行与目标行，一行是置顶，一行是非置顶
			var bFixedUp = true;
			if(_iPosition==0){//有前一行，插入到目标行之后的情况
				if(!bCurrTopped&&_eTargetMore!=null){//非置顶行判断前一行和后一行
					//若下一行为非置顶行，则不交叉
					//反之，则交叉
					var bTargetMoreTopped = _eTargetMore.getAttribute("isTopped",2)=='true';
					if(!bTargetMoreTopped){
						//用后一行的数据，表示插入到它之前
						targetDocId = _eTargetMore.getAttribute('docid', 2);
						_iPosition = (_iPosition==0)?1:0;
						bFixedUp = false;
					}
				}
				else if(!bCurrTopped&&_eTargetMore==null){//非置顶行上一行为置顶行，下一行无
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
				$timeAlert('置顶文档与非置顶文档间不能交叉排序.',5);
				return false;
			}
		}
		if(!confirm('您确定要调整文档的顺序？')){
			return false;
		}
		if(bCurrTopped){
			var oPostData = {
				"TopFlag" : 3,/*表示不改变置顶设置*/
				"ChannelId" : PageContext.params["channelid"],
				"DocumentId" : docid,
				"Position" : _iPosition,
				"TargetDocumentId" : targetDocId
			}
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.Call(PageContext.ObjectServiceId,'setTopDocument',oPostData,true);
		}
		else{
			var LastNextSibling = this.lastNextSibling;
			$chnlDocMgr['saveorder'](docid,_iPosition,targetDocId,PageContext.params["channelid"],{
				onFailure : function(trans,json){
					var getNodeVal = com.trs.util.JSON.value;
					FaultDialog.show({
						code		: getNodeVal(json,'fault.code'),
						message		: getNodeVal(json,'fault.message'),
						detail		: getNodeVal(json,'fault.detail'),
						suggestion  : getNodeVal(json,'fault.suggestion')
					}, '与服务器交互时出现错误' , function(){
						_eCurr.parentNode.insertBefore(_eCurr,LastNextSibling);
						Grid.adjustColors();
					});
				}
			});
		}
		Grid.adjustColors();
		return true;
	}
});
