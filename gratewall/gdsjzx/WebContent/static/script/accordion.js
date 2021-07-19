(function($){
    $.fn.extend({

    //pass the options variable to the function
    accordion: function(options) {
        
		var defaults = {
			accordion: 'true',
			speed: 300,
			closedSign: '[+]',
			openedSign: '[-]'
		};

		// Extend our default options with those provided.
		var opts = $.extend(defaults, options);
		//Assign current element to variable, in this case is UL element
 		var $this = $(this);
 		
 		//add a mark [+] to a multilevel menu
 		$this.find("li").each(function() {
 			if($(this).find("ul").length != 0){
 				//add the multilevel sign next to the link
 				$(this).find("a:first").append("<span>"+ opts.closedSign +"</span>");
 				
 				//avoid jumping to the top of the page when the href is an #
 				if($(this).find("a:first").attr('href') == "javascript:void(0);"){
 		  			$(this).find("a:first").click(function(){return false;});
 		  		}
 			}
 		});

 		//open active level
 		$this.find("li.active").each(function() {
 			//$(this).parents("ul").slideDown(opts.speed);
 			//$(this).parents("ul").parent("li").find("span:first").html(opts.openedSign);
 			$(this).children("ul").slideDown(opts.speed);
 			$(this).children("ul").parent("li").find("span:first").html(opts.openedSign);
 		});

  		$this.find("li a").click(function() {
  			if($(this).parent().find("ul").length != 0){
  				if(opts.accordion){
  					//Do nothing when the list is open
  					if(!$(this).parent().find("ul").is(':visible')){
  						parents = $(this).parent().parents("ul");
  						visible = $this.find("ul:visible");
  						visible.each(function(visibleIndex){
  							var close = true;
  							parents.each(function(parentIndex){
  								if(parents[parentIndex] == visible[visibleIndex]){
  									close = false;
  									return false;
  								}
  							});
  							if(close){
  								if($(this).parent().find("ul") != visible[visibleIndex]){
  									$(visible[visibleIndex]).slideUp(opts.speed, function(){
  										$(this).parent("li").find("span:first").html(opts.closedSign);
  									});
  									
  								}
  							}
  						});
  					}
  				}
  				if($(this).parent().find("ul:first").is(":visible")){
  					$(this).parent().find("ul:first").slideUp(opts.speed, function(){
  						$(this).parent("li").find("span:first").delay(opts.speed).html(opts.closedSign);
  					});
  					
  					
  				}else{
  					$(this).parent().find("ul:first").slideDown(opts.speed, function(){
  						$(this).parent("li").find("span:first").delay(opts.speed).html(opts.openedSign);
  					});
  				}
  			}else{
  				if($(this).attr('uri')!='#'){
  					window.open('/gdsjzx/'+$(this).attr('uri'),'contload','');// testLoad为iframe的name属性
  					//ul.navs li a:hover {background-color:#675C7C;    color:white;}
  					$("li a").css("background-color","");
  					$("li a").css("color","");
  					//$(this).css("background-color","#D3CEB8");
  					//$(this).css("color","#675C7C");
  					$(this).css("background-color","#ffffff");
  					$(this).css("color","#1c97ca");
  				}
  			}
  		});
    }
});
})(jQuery);