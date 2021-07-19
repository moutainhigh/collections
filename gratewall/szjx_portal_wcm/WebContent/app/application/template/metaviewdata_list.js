//列表页面检索下拉框字段的添加
var wcmListQueryItems = [];
<TRS_ViewFields SearchField="true">
wcmListQueryItems.push({name : '<TRS_ViewField Field="TABLENAME" filterForJs="true" />.<TRS_ViewField Field="DBFieldName" filterForJs="true" />', desc :'<TRS_ViewField Field="AnotherName" filterForJs="true" />', type : DBTypeMapping['<TRS_ViewField Field="DBType" />'] || 'string'});
</TRS_ViewFields>
