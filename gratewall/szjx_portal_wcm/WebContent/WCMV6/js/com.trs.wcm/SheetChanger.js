$package('com.trs.wcm');

com.trs.wcm.SheetChanger = Class.create("SheetChanger");
Object.extend(com.trs.wcm.SheetChanger.prototype, {
	initialize : function(){
	},

	setLocationSearch : function(){
		(top.actualTop||top).location_search = this.query;//记录上次访问的地址参数
	},
	changeSheet : function(query, _sType, needSaveStatus){
		var index = query.indexOf("?");
		if(index == -1) return;
		this.query = query;
		if(index != 0){
			//TODO 特殊处理
			this.setOperateType(_sType);
			$MessageCenter.changeSrc("main", query);
			return;
		}
		if(!/channelid=0/.test(query) 
			&& (/channelid/.test(query) || /objtype.*101/i.test(query))){//导航树中栏目处于选中状态
			if(_sType && needSaveStatus)
				(top.actualTop||top).PageContext.channelHost = _sType;
			var flag = query.indexOf("channelid");
//			if(flag < 0 && (top.actualTop||top).PageContext.channelHost != "right"){
			_sType = _sType || (top.actualTop||top).PageContext.channelHost;
			if(flag < 0 && _sType != "right"){
				query = query.replace(/ObjId/i, "channelid").replace(/ObjType[^&]+(&|$)/i, "");
//			}else if(flag > -1 && (top.actualTop||top).PageContext.channelHost == "right"){
			}else if(flag > -1 && _sType == "right"){
				query = query.replace(/channelid/i, "ObjType=101&ObjId");
			}
			this.setPage(_sType || (top.actualTop||top).PageContext.channelHost, query);
		}else if(/siteid/.test(query) || /objtype.*103/i.test(query)){//导航树中站点处于选中状态
			if(_sType && needSaveStatus)
				(top.actualTop||top).PageContext.siteHost = _sType;
			var flag = query.indexOf("siteid");
//			if(flag < 0 && (top.actualTop||top).PageContext.siteHost != "right"){
			_sType = _sType || (top.actualTop||top).PageContext.siteHost;
			if(flag < 0 && _sType != "right"){
				query = query.replace(/ObjId/i, "siteid").replace(/ObjType[^&]+(&|$)/i, "");
//			}else if(flag > -1 && (top.actualTop||top).PageContext.siteHost == "right"){
			}else if(flag > -1 && _sType == "right"){
				query = query.replace(/siteid/i, "ObjType=103&ObjId");
			}
			this.setPage(_sType || (top.actualTop||top).PageContext.siteHost, query);
		}else{//导航树中站点类型处于选中状态
			if(_sType && needSaveStatus)
				(top.actualTop||top).PageContext.rootHost = _sType;
			this.setPage(_sType || (top.actualTop||top).PageContext.rootHost, query);
		}
	},

	setPage : function(type, query){
		this.setOperateType(type);
		query = (query)?query.replace(/(&)?tab_type=[^&]*/,''):'';
		query = (type)?((query)?query+'&tab_type='+type:'tab_type='+type):query;
		switch(type){
			case "channel" :
				$MessageCenter.changeSrc("main", "channel/channel_thumbs_index.html" + query);
				break;
			case "document" :
				if(/sitetype=[^0]/i.test(query) || (top.actualTop||top).PageContext.mode == "normal"){
					this.setOperateType(type, 'normal');
					(top.actualTop||top).$('document_redirect').src ="document/document_list_redirect.jsp" + query;
//					$MessageCenter.changeSrc("main", "document/document_list_redirect.jsp" + query);
				}
				else
					$MessageCenter.changeSrc("main", "document/document_readmode_index.html" + query);
				break;
			case "right" :
					$MessageCenter.changeSrc("main", "auth/right_set.jsp" + query);
				break;
			case "extendfield" :
				$MessageCenter.changeSrc("main", "extendfield/extendfield_index.html" + query);
				break;	
			case "template" :
				$MessageCenter.changeSrc("main", "template/template_index.html" + query);
				break;
			case "website" :
				$MessageCenter.changeSrc("main", "website/website_thumbs_index.html" + query);
				break;	
			case "distribution" :
				$MessageCenter.changeSrc("main", "distribution/distribution_list.html" + query);
				break;	
			case "replace" :
				$MessageCenter.changeSrc("main", "replace/replace_list.html" + query);
				break;	
			case "documentsyn" :
				if(/channeltype=(?!(0|13))/i.test(query) || (top.actualTop||top).PageContext.documentSynMode == 'col'){
					$MessageCenter.changeSrc("main", "documentsyn/docsyn_col_list.html" + query);					
				}else{
					$MessageCenter.changeSrc("main", "documentsyn/docsyn_dis_list.html" + query);				
				}
				break;
			case "trashbox" :
				// fjh@2008-8-15 视频废稿箱独立
				try	{
					var ch = query.charAt(query.indexOf('SiteType=') + 9 );
					if (ch == '2') {
						$MessageCenter.changeSrc("main", "video/video_recycle_list.jsp" + query);
					} else {
						$MessageCenter.changeSrc("main", "docrecycle/doc_recycle_list.html" + query);
					}
				} catch (e)	{
					$MessageCenter.changeSrc("main", "docrecycle/doc_recycle_list.html" + query);
				}
				break;
			case "contentlink" :
				$MessageCenter.changeSrc("main", "contentlink/contentlink_index.html" + query);
				break;
			case "watermark" :
				$MessageCenter.changeSrc("main", "photo/watermark_list.html" + query);
				break;
			case "workflow" : 
				$MessageCenter.changeSrc("main", "workflow/workflow_list.html" + query);
				break;
			case "flowemployee" : 
				$MessageCenter.changeSrc("main", "workflow/flowemployee_view.html" + query);
				break;
            /*------------------------------------------metadata------------------------------------------------*/
			case "tableinfo" :
				if(/tableInfoId=[^0]/i.test(query)){
					$MessageCenter.changeSrc("main", "metadata/fieldinfo_list.html" + query);
				}else{
					$MessageCenter.changeSrc("main", "metadata/tableinfo_thumbs_index.html" + query);
				}
				break;
			case "viewinfo" : 
				if(/channelid=[^0]/i.test(query) || /viewid=[^0]/i.test(query)){
					$MessageCenter.changeSrc("main", "metadata/viewfieldinfo_list.html" + query);
				}else{
					$MessageCenter.changeSrc("main", "metadata/viewinfo_thumbs_index.html" + query);
				}
				break;
			case "classinfo" : 
				$MessageCenter.changeSrc("main", "metadata/classinfo_list.html" + query);
				break;
			default :
				alert('尚未实现：' + type);
		}
	},

	setTab : function(sType){
		if(!sType)return;
		sType = sType.toLowerCase();
		if(sType == "r" && $tab().tabType != TabType.SYSTEM){
			$tab().loadTab(TabType.SYSTEM);
		}else if(sType == "s" && $tab().tabType != TabType.SITE){
			$tab().loadTab(TabType.SITE);
		}else if(sType == "c" && $tab().tabType != TabType.CHANNEL){
			$tab().loadTab(TabType.CHANNEL);
		}
	},

	setOperateType : function(type, currMode){
		if(type == 'right' || (type=="document" && (currMode || (top.actualTop||top).PageContext.mode)=="read")){
			(top.actualTop||top).showHideAttrPanel(false, false);
		}else{
			(top.actualTop||top).showHideAttrPanel(true, (top.actualTop||top).PageContext.attribute_bar_status);	
		}		
	}	
});

if(top.$SheetChanger){
	$SheetChanger = top.$SheetChanger;
	$changeSheet = top.$changeSheet;
}else{
	top.$SheetChanger = $SheetChanger = new com.trs.wcm.SheetChanger();
	top.$changeSheet = $changeSheet = $SheetChanger.changeSheet.bind($SheetChanger);
}
