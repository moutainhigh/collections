//列表页面检索下拉框字段的添加
var wcmListQueryItems = [];

wcmListQueryItems.push({name : 'GovInfo.idxID', desc :'索引', type : DBTypeMapping['12'] || 'string'});

wcmListQueryItems.push({name : 'GovInfo.Title', desc :'名称', type : DBTypeMapping['12'] || 'string'});

wcmListQueryItems.push({name : 'GovInfo.fileNum', desc :'文号', type : DBTypeMapping['12'] || 'string'});

wcmListQueryItems.push({name : 'GovInfo.Description', desc :'内容概述', type : DBTypeMapping['12'] || 'string'});


Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false
});
window.m_cbCfg = {
	btns : [
		{
			id : 'btnTrue',
			text : wcm.LANG.metaviewdata4select_101 || '确定',
			cmd : function(){
				var oRadioEles = document.getElementsByName("RowId");
				var count = 0;
				var nSelDocPos = 0;
				for (var i = 0; i < oRadioEles.length; i++){
					if(!oRadioEles[i].checked){
						count++;
					}else{
						nSelDocPos = oRadioEles[i].getAttribute("_value");
					}
				}
				if(count==oRadioEles.length){
					Ext.Msg.alert(wcm.LANG.metaviewdata4select_102 || "请选择文档");
					return false;
				}
				this.hide();
                this.notify(nSelDocPos);
				return false;
			}
		},
		{
			id : 'btnClose',
			text : wcm.LANG.metaviewdata4select_103 || '取消',
			cmd : function(){
			}
		}
	]
}