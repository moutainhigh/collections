define(['require', 'jquery', 'approvecommon'], function(require, $, approvecommon){
	var index = {
		_init : function(){
			require(['domReady'], function (domReady) {
				  domReady(function () {
			          $('li').on('click',index.addTab);
				  });
			});
		},
		addTab : function(e){
			var element;
			if(e.target.tagName == 'LI'){
				element = $(e.target).child().eq(0);
			}else{
				element = $(e.target);
			}
			var name = element.html();
			var appSign = true;
			$('.tab_menu_name').each(function(index,item){
				if($(item).html() == name){
					appSign = false;					
				}
			});
			if(appSign){
				$('.top').append('<div class="tab_menu"><span class="tab_menu_name">'+name+'</span><span class="close_tab_menu">x</span></div>')
				var tabContent = '<div class="content">'+
					                '<div class="content_top">'+name+'</div>'+
					                '<iframe src="'+element.attr('src')+'" border="0" width="100%"></iframe>'+
					            '</div>';
					
				$('#tab_content').append(tabContent);
			}
		}
	}
	index._init();
    return index;
});