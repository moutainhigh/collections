$(document).ready(function(){
	/**页面对象**/
	var PageContext = window.PageContext || {};
	$.extend(PageContext,{
		"delete" : function($dom){
			if(!confirm("确定要删除这条记录吗？")){
				return;
			}
			this._delete($dom.attr("ObjectId"));
		},
		_delete : function(ObjectIds){
			$.wcmAjax("wcm6_viewdocument","delete",{ObjectIds:ObjectIds},
				function(){
				PageContext.refresh();
			});
		},
		"refresh" : function(){
			window.location.reload();
		},
		"edit" : function($dom){
			$.CrashBoard({
				id : 'metafield_add_edit',
				title : '修改字段',
				maskable : false,
				draggable : true,
				url : 'metafield_add_edit.jsp',
				width : '600px',
				height : '400px',
				params : {ObjectId:$dom.attr("ObjectId"),viewId:m_viewId},
				callback : function(params){
					window.location.reload();
				}
			}).show();
		}
	});
	/**给document绑定click事件**/
	$(document).click(function(e){
		var $dom = $(e.target);
		$dom = $dom.closest("[cmd]");
		if(!$dom.attr("cmd"))return;
		PageContext[$dom.attr("cmd")]($dom.closest("tr"));
	});
});

/**分类树显示和隐藏事件以及检索的事件绑定**/
$(document).ready(function(){
	$('#sepBar').bind("click",function(){
		var dom = $('#container');
		dom.toggleClass('shrinkNavTree');
	});
	$('#search_form').bind("submit", function(){
		var itemValue = $('#search_select').val();
		var bIsAll = itemValue.indexOf(",")>-1;
		if(bIsAll){
			var aItemName = itemValue.split(",");
			var searchValue = $('#search_input').val();
			for (var i = 0,nLen = aItemName.length; i < nLen; i++){
				var sInput = '<input type="hidden" name="'+aItemName[i]+'" id="" value="'+searchValue+'" />';
				$('#search_form').append(sInput);
			}
			$('#search_input').attr("name","isOr");
			$('#search_input').attr("value",true);
		}else{
			$('#search_input').attr("name",$('#search_select').val());
		}
	});
	$('#search_btn').bind("click",function(){
		$('#search_form').submit();
	});
});

$(function() {
	$(".toolbar a").imgbutton();
});
function publishRes(){
	
}

function previewRes(){
	
}

function deleteRes(){
	var ObjectIds = getChnlDocIds();
	PageContext._delete(ObjectIds);
}

function getChnlDocIds(){
	var objectIds = [];
	var $trs = $(".select:checked").closest("tr");
	$trs.each(function(){
		objectIds.push($(this).attr("rowid"));
	});
	return objectIds.join(",");
}
