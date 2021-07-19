$package('com.trs.wcm.domain');

$import('com.trs.dialog.Dialog');
com.trs.wcm.domain.ExtendFieldMgr=Class.create('wcm.domain.channel.ExtendFieldMgr');
com.trs.wcm.domain.ExtendFieldMgr.prototype = {
	initialize: function(){
		this.actionParams = {};
	},
	
	serviceId : 'wcm6_extendfield',
	helpers : {},
	
	getInstance : function(){
		if($ExtendFieldMgr == null) {
			$ExtendFieldMgr = new com.trs.wcm.domain.ExtendFieldMgr();
		}
		return $ExtendFieldMgr;
	},
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	},
	/********************************************************
	 * 定义业务逻辑
	 *********************************************************
	 */
	'new' : function(_args, _oPageParams){
		this.edit(0, _oPageParams);
	},
	edit : function(_sId, _oPageParams){		
		if(_sId != void(0)){
			Object.extend(this.actionParams,{"ObjectId":_sId});			
		}				
		if(_oPageParams){
			Object.extend(this.actionParams, _oPageParams);
		}///wenyh@2007-02-26 no need?	菜单需要此参数	
		
		FloatPanel.open('./extendfield/extendfield_addedit.html?' + $toQueryStr(this.actionParams), '新建/修改扩展字段', 600, 300);
	},	
	'delete' : function(_sId,_eRow,_eSrcElement){		
		if(!_sId){
			_sId = this.actionParams.ObjectIds || this.actionParams.ObjectId;
		}
		
		var sHtml = "<div style='padding-left:10px;text-indent:20px'>";
		if(_eRow){
			sHtml +="您确定要删除扩展字段["+_eRow.getAttribute("grid_rowdesc")+"]吗?";
		}else{
			sHtml +="您确定要删除扩展字段吗?";
		}
		sHtml += '<div style="color:red;padding-top:4px;cursor:hand">';
		sHtml += '<input type="checkbox" id="synced"><label for="synced">同步删除子对象的同一字段</label>';		
		sHtml += "</div></div>";
		var okFunc = function(){
			$dialog().hide();
			
			Object.extend(PageContext.params,{"ObjectIds":_sId,"ContainsChildren":$dialogElement("synced").checked,'HideChildren':true});

			this.getHelper().call(this.serviceId,"delete",PageContext.params,true,function(){
				delete PageContext.params['ContainsChildren'];//for refresh:do not show the children.
				$MessageCenter.sendMessage('main', 'PageContext.loadExtendfields', 'PageContext', []);
			});			
		}.bind(this);

		$confirm(sHtml,okFunc,function (){$dialog().hide()},"删除确认");
	},
	sync : function(_sId){
		if(!_sId){
			_sId = this.actionParams.ObjectIds || this.actionParams.ObjectId;
		}		
		var okFunc = function(){
			$dialog().hide()
			Object.extend(PageContext.params,{"ObjectIds":_sId});
			this.getHelper().call(this.serviceId,'impartExtendFields',PageContext.params,true,function(){$success("同步扩展字段到子对象成功！")});		
		}.bind(this);

		$confirm("确定要同步扩展字段到子对象?",okFunc,function(){$dialog().hide()},"同步确认");		
	},
	setParams : function(_params){
		Object.extend(this.actionParams,_params);
	},
	unlock : function(_args){
		if(_args.ObjectId && _args.ObjectType){
			LockerUtil.unlock(_args.ObjectId,_args.ObjectType);
		}
	}
}

var $ExtendFieldMgr = new com.trs.wcm.domain.ExtendFieldMgr();
