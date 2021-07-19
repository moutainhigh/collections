
var PageContext = window.PageContext || {};
$.extend(PageContext,{
	//初始化顺序
	_reInitOrder : function(){
		var order = 1;
		$("td:first-child span").each(function(){
			var sHtml = $(this).html();
			if(!/^\d+$/.test(sHtml))return;
			$(this).html(order++);
		});
	},
	_delete : function(ObjectIds){
		$.wcmAjax("wcm6_MetaDataDef","deleteViewField",{ObjectIds:ObjectIds},
			function(){
			PageContext.refresh();
		});
	},
	"select_fields" : function(){
		$.CrashBoard({
			id : 'select_fields',
			maskable : true,
			draggable : true,
			url : 'select_fields.jsp',
			width : '700px',
			height : '400px',
			callback : function(params){
				$.wcmAjax("wcm61_MetaField","createFieldsFromLibrary",{FieldIds:params,ViewId:m_viewId},function(){
					PageContext.refresh();
				});
			}
		}).show();
	},
	"refresh" : function(){
		window.location.reload();
	},
	"new_field" : function(){
	},
	"set_properties" : function(){
		$.CrashBoard({
			id : 'select_fields',
			maskable : true,
			draggable : true,
			url : 'set_resourcestr_field_prototies.jsp',
			width : '700px',
			height : '400px',
			params : {ViewId:m_viewId},
			callback : function(params){
				
			}
		}).show();
	},
	"del" : function(){
		var objectIds = [];
		var $trs = $(".select:checked").closest("tr");
		$trs.each(function(){
			objectIds.push($(this).attr("ObjectId"));
		});
		this._delete(objectIds.join(","));
	},
	"edit" : function($dom){
		$.CrashBoard({
			id:"field-edit",
			maskable:true,
			url:"g.cn",
			width:"400px",
			height:"300px"
		}).show();
		//alert($dom.attr("ObjectId"));
	},
	"delete" : function($dom){
		this._delete($dom.attr("ObjectId"));
	},
	"selectAll" : function(){
	},
	"pre_step" : function(){
		window.location.href="resourcestr_addedit_step01.jsp?ViewId="+m_viewId;
	},
	"next_step" : function(){
		window.location.href="resourcestr_addedit_step03.jsp?ViewId="+m_viewId;
	},
	"save" : function(){
		
	},
	"refresh" : function(){
		window.location.reload();
	}
});
$(document).click(function(e){
	var $dom = $(e.target);
	$dom = $dom.closest("[cmd]");
	if(!$dom.attr("cmd"))return;
	var $tr = $dom.closest("tr");
	PageContext[$dom.attr("cmd")]($tr);
});
$(function(){
	$(".toolbar span").imgbutton();
});