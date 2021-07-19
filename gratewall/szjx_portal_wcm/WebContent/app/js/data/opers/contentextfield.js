//扩展字段操作信息和Mgr定义
Ext.ns('wcm.domain.ContentExtFieldMgr');
(function(){
	var m_oMgr = wcm.domain.ContentExtFieldMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	Ext.apply(wcm.domain.ContentExtFieldMgr, {
		"new" : function(event){
			var oPageParams = event.getContext();
			Object.extend(oPageParams,{"ObjectId":0});			
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'contentextfield/contentextfield_addedit.jsp?' + $toQueryStr(oPageParams),
					wcm.LANG.CONTENTEXTFIELD_CONFIRM_34 || '新建扩展字段',
					CMSObj.afteradd(event)
			);
		},
		edit : function(event){
			var oPageParams = event.getContext();
			var sId = event.getIds().join();
			Object.extend(oPageParams,{"ObjectId":sId});			

			FloatPanel.open(WCMConstants.WCM6_PATH +
					'contentextfield/contentextfield_addedit.jsp?' + $toQueryStr(oPageParams),
					wcm.LANG.CONTENTEXTFIELD_CONFIRM_35 || '修改这个扩展字段',
					CMSObj.afteredit(event));
		},
		//'delete' : function(_sId,_eRow,_eSrcElement){
		'delete' : function(event){	
			var oPageParams = event.getContext();
			//alert(Ext.toSource(oPageParams));
			var sId = event.getIds();
			Ext.Msg.confirm((wcm.LANG.CONTENTEXTFIELD_CONFIRM_5 || "您确定要删除扩展字段吗?")+"<br><table><tr style='float:left'><td style='padding-bottom:" + (Ext.isIE ? '5px':'0px') + ";'><input type='checkbox' name='cbx' id='cbx' value=''></td><td style='text-align:left'><font color='red'>" + (wcm.LANG.CONTENTEXTFIELD_CONFIRM_12 ||"同步删除子对象的同一字段") + "</font></td></tr></table>", {
                yes : function(){
						Object.extend(PageContext.params,{"ObjectIds":sId,"ContainsChildren":this.$("cbx").checked,'HideChildren':true});
						BasicDataHelper.call('wcm6_extendfield',"delete",PageContext.params,true,function(){
							delete PageContext.params['ContainsChildren'];//for refresh:do not show the children.
							//$MsgCenter.$main().afteredit();
							event.getObjs().afterdelete();
						});
                }
            });
		},
		sync : function(event){
			var hostType = event.getHost().getType();
			var hostId = event.getHost().getId();
			var sId = event.getIds().reverse();
			var params = {
				objType: hostType == "channel" ? 101 : (hostType == "website" ? 103:1),
				objId:hostId,
				close : 0,
				ExcludeTop : 1,
				ExcludeInfoview : 1,
				ExcludeLink : 1
			};
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'website/object_select.html',
				dialogArguments : params,
				title : wcm.LANG.CONTENTEXTFIELD_CONFIRM_41||'选择子对象',
				callback : function(args){
					if(args.allChildren){
						var sHtml = wcm.LANG.CONTENTEXTFIELD_CONFIRM_1 || "确定要同步创建到所有子对象吗?";
						Ext.Msg.confirm(sHtml, {
							ok : function(){	
								Object.extend(PageContext.params,{"ObjectIds":sId,"ImpartAll":true});
								BasicDataHelper.call('wcm6_extendfield','impartExtendFields',PageContext.params,true,
									function(){Ext.Msg.alert(wcm.LANG.CONTENTEXTFIELD_CONFIRM_2 || "同步扩展字段到子对象成功!");FloatPanel.close();});	
							}
						});
					}else{
						//进行参数组装，拆分发站点和栏目ID序列
						var sSiteIds = [],sChannelIds = [];
						for(var i =0;i< args.typenames.length;i++){
							if(args.typenames[i].indexOf("s") > 0) sSiteIds.push(args.typenames[i].split("_")[1]);
							else {sChannelIds.push(args.typenames[i].split("_")[1])};
						}
						var sHtml = wcm.LANG.CONTENTEXTFIELD_CONFIRM_42 || "确定要同步创建到以上选中子对象吗?";
						Ext.Msg.confirm(sHtml, {
							ok : function(){	
								Object.extend(PageContext.params,{"ObjectIds":sId,"ImpartAll":false,"SiteIds":sSiteIds.join(","),"ChannelIds":sChannelIds.join(",")});
								BasicDataHelper.call('wcm6_extendfield','impartExtendFields',PageContext.params,true,
									function(){Ext.Msg.alert(wcm.LANG.CONTENTEXTFIELD_CONFIRM_2 || "同步扩展字段到子对象成功!");FloatPanel.close();});	
							}
						});
					}
				}
			});
		},
		inhert : function(event){
			var oPageParams = {};
			var host = event.getHost();
			var hostid = host.getId();
			Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'contentextfield/parentNode_select.jsp?' + $toQueryStr(oPageParams),
					wcm.LANG.CONTENTEXTFIELD_CONFIRM_44 || '选择父对象', function(){event.getObjs().afteradd()});
		},
		docpositionset : function(event){
			var oPageParams = event.getContext();
			var sId = event.getIds().join();
			var host = event.getHost();
			var hostid = host.getId();
			Object.extend(oPageParams,{"channelid":hostid,"objectid":sId});
			var sTitle = wcm.LANG.CONTENTEXTFIELD_CONFIRM_49 || "扩展字段-调整顺序";
			FloatPanel.open(WCMConstants.WCM6_PATH + 'contentextfield/contentextfield_position_set.jsp?' + $toQueryStr(oPageParams), sTitle, CMSObj.afteredit(event));
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.ContentExtFieldMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'edit',
		type : 'contentextfield',
		desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_7 || '修改这个扩展字段',
		title:'修改这个扩展字段...',
		rightIndex : 19,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'delete',
		type : 'contentextfield',
		desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_8 || '删除这个扩展字段',
		title:'删除这个扩展字段...',
		rightIndex : 19,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'sync',
		type : 'contentextfield',
		desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_6 || '同步到子对象',
		title : '同步到子对象...',
		rightIndex : 19,
		order : 3,
		fn : pageObjMgr['sync']
	});
	reg({
		key : 'new',
		type : 'contentextfieldInChannel',
		desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_4 || '新建扩展字段',
		title:'新建扩展字段...',
		rightIndex : 19,
		order : 4,
		fn : pageObjMgr['new'],
		quickKey : 'N'
	});
	reg({
		key : 'inhert',
		type : 'contentextfieldInChannel',
		desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_43 || '继承父对象扩展字段',
		title:'继承父对象扩展字段...',
		rightIndex : 19,
		order : 4,
		fn : pageObjMgr['inhert']
	});
	reg({
		key : 'new',
		type : 'contentextfieldInSite',
		desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_4 || '新建扩展字段',
		title:'新建扩展字段...',
		rightIndex : 19,
		order : 5,
		fn : pageObjMgr['new'],
		quickKey : 'N'
	});
	reg({
		key : 'inhert',
		type : 'contentextfieldInSite',
		desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_43 || '继承父对象扩展字段',
		title:'继承父对象扩展字段...',
		rightIndex : 19,
		order : 6,
		fn : pageObjMgr['inhert']
	});
	reg({
		key : 'new',
		type : 'contentextfieldInRoot',
		desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_4 || '新建扩展字段',
		title:'新建扩展字段...',
		rightIndex : 19,
		order : 7,
		fn : pageObjMgr['new'],
		quickKey : 'N'
	});
	reg({
		key : 'delete',
		type : 'contentextfields',
		desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_9 || '删除这些扩展字段',
		title:'删除这些扩展字段',
		rightIndex : 19,
		order : 8,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'sync',
		type : 'contentextfields',
		desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_6 || '同步到子对象',
		title :'同步到子对象...',
		rightIndex : 19,
		order : 9,
		fn : pageObjMgr['sync']
	});
	reg({
		key : 'docpositionset',
		type : 'contentextfield',
		desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_48 ||'调整顺序',
		title:'调整顺序',
		rightIndex : 19,
		order : 10,
		fn : pageObjMgr['docpositionset'],
		isVisible : function(event){
			if(event.getHost().getType() == WCMConstants.OBJ_TYPE_CHANNEL)
				return true;
			return false;
		}
	});
})();