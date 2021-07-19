$(document).ready(function() {
	var rowMenuPanel = $("div#row_menu_panel");
	var colMenuPanel = $("div#col_menu_panel");
	
	$.ajax({
		url 	 :"common/menu.jsp",
		dateType  :"xml",
		complete :function(res,status){
					initMenu(res.responseXML);
				 }
	});
	
	function initMenu(xml){
		//$("rowMenus/rowMenu",xml).each(function(){
			//var menuId = $(this).attr("id");
			//var menuText = this.text;
			//var menuItem = $("<div id=\""+menuId+"\" class=\"row_menu_item\"/>");
			//var menuTextDiv = $("<div class=\"row_menu_item_text\"/>");
			//var menuLink = $("<a href=\"javascript:void(0);\" class=\"row_menu_item_link\">"+menuText+"</a>");
			
			//menuTextDiv.append(menuLink);
			//menuItem.append(menuTextDiv);
			//rowMenuPanel.append(menuItem);
		//});
		
		$("a.row_menu_item_link").click(function(){
			var rowMenuId = $(this.parentNode.parentNode).attr("id");
			var colMenuId = $("div.col_menu_item_selected",colMenuPanel).attr("id");
			
			_frame_goPageHref(xml,rowMenuId,colMenuId,this);
		});
		
		$("a.col_menu_item_link").click(function(){
			var colMenuId = $(this.parentNode.parentNode).attr("id");
			var rowMenuId = $("div.row_menu_item_selected",rowMenuPanel).attr("id");
			_frame_goPageHref(xml,rowMenuId,colMenuId,this);
		});
		
		initMenuLink();
	}
	
	function initMenuLink(){
		var rowMenuId = $("div#fbk_menu").attr("rowMenuId");
		var colMenuId = $("div#fbk_menu").attr("colMenuId");
		
		var navigatorText = "";
		$("div.row_menu_item").each(function(){
			if($(this).attr("id")==rowMenuId){
				$(this).addClass("row_menu_item_selected").removeClass("row_menu_item");
				$(this).attr("selected","true");
				navigatorText+=$(this).text();
				navigatorText+=">>";
			}else{
				$(this).addClass("row_menu_item").removeClass("row_menu_item_selected");
				$(this).removeAttr("selected");
			}
		});
		
		$("div.col_menu_item").each(function(){
			if($(this).attr("id")==colMenuId){
				$(this).addClass("col_menu_item_selected").removeClass("col_menu_item");
				$(this).attr("selected","true");
				navigatorText+=$(this).text();
			}else{
				$(this).addClass("col_menu_item").removeClass("col_menu_item_selected");
				$(this).removeAttr("selected");
			}
		});
		
		$("span#navigator_text").append(navigatorText);
	}
	
	function _frame_goPageHref(xml,rowMenuId,colMenuId,aLink){
		var goHref;
		
		if(rowMenuId&&!colMenuId){
			colMenuId = "menu_report_query";
		}

		$("menus/menuPaths/menuPath",xml).each(function(){
			var thisRowMenuId;
			var thisColMenuId;
			var thisHref;
			
			thisRowMenuId = this.getAttribute("rowMenuId");
			thisColMenuId = this.getAttribute("colMenuId");
			thisHref = $(this).text();
			if(rowMenuId==thisRowMenuId&&!thisColMenuId){
				goHref = thisHref;
			}
			if(rowMenuId==thisRowMenuId&&colMenuId==thisColMenuId){
				goHref = thisHref;
			}
		});
		
		if(goHref){
			aLink.href = goHref;
		};
	}
});