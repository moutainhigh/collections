//列表页面检索下拉框字段的添加
var wcmListQueryItems = [];
<TRS_ViewFields SearchField="true">
wcmListQueryItems.push({name : '<TRS_ViewField Field="TABLENAME" filterForJs="true" />.<TRS_ViewField Field="DBFieldName" filterForJs="true" />', desc :'<TRS_ViewField Field="AnotherName" filterForJs="true" />', type : DBTypeMapping['<TRS_ViewField Field="DBType" />'] || 'string'});
</TRS_ViewFields>

Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false
});
window.m_cbCfg = {
	btns : [
		{
			id : 'btnTrue',
			text : '确定',
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
					Ext.Msg.alert("请选择文档");
					return false;
				}
				this.hide();
                this.notify(nSelDocPos);
				return false;
			}
		},
		{
			id : 'btnClose',
			text : '取消',
			cmd : function(){
			}
		}
	]
}
