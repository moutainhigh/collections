var m_Templates = {
	topset_table : [
		'<table border=0 cellspacing=1 cellpadding=0 style="width:88%;table-layout:fixed;background:gray;">',
		'<thead>',
			'<tr bgcolor="#CCCCCC" align=center valign=middle>',
				'<td width="32">', wcm.LANG.METAVIEWDATA_118 || "序号",'</td>',
				'<td>', wcm.LANG.METAVIEWDATA_119 || "文档标题",'</td>',
				'<td width="40">', wcm.LANG.METAVIEWDATA_120 || "排序",'</td>',
			'</tr>',
		'</thead>',
		'<tbody id="topset_order_tbody">{0}</tbody>',
		'</table>'
	].join(''),
	topset_item_tr : [
		'<tr bgcolor="#FFFFFF" align=center valign=middle _docid="{0}" _doctitle="{2}">',
			'<td>{1}</td>',
			'<td align=left title="{0}-{2}"><div style="overflow:hidden">{2}</div></td>',
			'<td>&nbsp;</td>',
		'</tr>'
	].join(''),
	topset_curr_tr : [
		'<tr bgcolor="#FFFFCF" align=center valign=middle _docid="" _currdoc="1">',
			'<td>{0}</td>',
			'<td align=left style="color:red;">',wcm.LANG.METAVIEWDATA_121 || "--当前文档--",'</td>',
			'<td>',
				'<span class="topset_up" title="', wcm.LANG.METAVIEWDATA_122 || "上移",  '" _action="topsetUp">&nbsp;</span>',
				'<span class="topset_down" title="', wcm.LANG.METAVIEWDATA_123 || "下移", '" _action="topsetDown">&nbsp;</span>',
			'</td>',
		'</tr>'
	].join('')
}

Ext.ns('PgC');

Ext.apply(PgC, {
/*---------------- 获取置顶信息 ------------------*/
	getTopsetInfo : function(){
		if(!PgC.TopFlag || PgC.TopFlag=='0'){
			return {
				TopFlag : PgC.TopFlag,
				Position : 0,
				TargetDocumentId : 0
			};
		}
		var rows = $('topset_order_tbody').rows;
		var nCurrIndex = -1;
		for(var i=0,n=rows.length; i<n; i++){
			if(rows[i].getAttribute("_currdoc", 2)){
				nCurrIndex = i;
				break;
			}
		}
		var nPosition = 0;
		var nTargetDocId = 0;
		if(nCurrIndex==rows.length-1 && nCurrIndex!=0){
			var beforeRow = rows[nCurrIndex-1];
			nPosition = 0;
			nTargetDocId = beforeRow.getAttribute("_docid", 2);
		}else if(nCurrIndex!=rows.length-1){
			var afterRow = rows[nCurrIndex+1];
			nPosition = 1;
			nTargetDocId = afterRow.getAttribute("_docid", 2);
		}
		if(PgC.TopFlag == 2){
			PgC.TopFlag =3;
		}
		return {
			TopFlag : PgC.TopFlag,
			Position : nPosition,
			TargetDocumentId : nTargetDocId
		};
	},
	makeCurrDocInTopList : function(){
		if(PgC.DocInTopList)return;
		PgC.DocInTopList = true;
		PgC._renderTopList();
	},
	_renderTopList : function(index){
		index = index || 0;
		var rows = $('topset_order_tbody').rows;
		if(index<0 || index>=rows.length)return;
		var items = [];
		var infos = [];
		for(var i=0,n=rows.length; i<n; i++){
			var recid = rows[i].getAttribute("_docid", 2);
			if(!recid)continue;
			var doctitle = rows[i].getAttribute("_doctitle", 2);
			infos.push({recid:recid, doctitle:doctitle});
		}
		for(var i=0,n=infos.length; i<n; i++){
			var myIdx = i+1;
			if(i>=index)myIdx=i+2;
			items.push(String.format(m_Templates.topset_item_tr, infos[i].recid,
				myIdx, infos[i].doctitle));
		}
		items.splice(index, 0, String.format(m_Templates.topset_curr_tr, index+1));
		var html = String.format(m_Templates.topset_table, items.join(''));
		Element.update('topset_order_table', html);
	},
	topsetUp : function(event, target, actionItem){
		var row = actionItem.parentNode.parentNode;
		PgC._renderTopList(row.rowIndex-2);
	},
	topsetDown : function(event, target, actionItem){
		var row = actionItem.parentNode.parentNode;
		PgC._renderTopList(row.rowIndex);
	},
	topset : function(){
		var choises = document.getElementsByName('TopFlag');
		var value = 0;
		for(var i=0, n=choises.length; i<n; i++){
			if(choises[i].checked){
				value = choises[i].value;
				break;
			}
		}
		PgC.TopFlag = value;
		if(value=='0'){
			Element.hide('topset_order');
			Element.hide('pri_set_deadline');
		}else{
			Element.show('topset_order');
			Element.hide('pri_set_deadline');
		}
		if(value=='1'){
			Element.show('pri_set_deadline');
		}
		PgC.makeCurrDocInTopList();
		var tab = $("topset_order");
		if(Element.visible(tab)){
			//滚动到底部
			$("topset_order").scrollIntoView(false);
		}
	},
	getActionItem : function(target){
		while(target!=null&&target.tagName!='BODY'){
			if(target.getAttribute('_action', 2)!=null)return target;
			target = target.parentNode;
		}
		return null;
	},
	init : function(){
		Event.observe(document.body, 'click', function(event){
			var target = Event.element(event||window.event);
			var actionItem = PgC.getActionItem(target);
			if(actionItem==null)return;
			var action = actionItem.getAttribute('_action', 2);
			if(!action || !PgC[action])return;
			PgC[action].apply(PgC, [event, target, actionItem]);
		});
	}
});