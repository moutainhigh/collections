/**分类树显示和隐藏事件**/
$(document).ready(function(){
	$('#sepBar').bind("click",function(){
		var dom = $('#container');
		dom.toggleClass('hideNavTree');
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

	$("#list-data").bind("dblclick",function(event){
		var element = event.target;

		var dom = $(element).closest(".thumb").get(0);
		if(dom==null)return;
		var nClassInfoId = $(dom).attr("itemid");
		parent.$.Tabpage().openTabPage({
			URL : 'classinfo/classinfo_resource_list.jsp?CLASSINFOID='+nClassInfoId,
			params : {id:'classinfo_resource_list',text:'分类资源列表',withClose:true},
			callback : function(){
				
			}
		});
	});
});
