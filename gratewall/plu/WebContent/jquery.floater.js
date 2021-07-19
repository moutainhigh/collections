(function ($) {
$.fn.floateradv  = function(options){
     $.fn.floateradv.defaults = {
    		domId:domId,
		    imgSrc:'',  //图片URL
            targetUrl:'',
			openStyle:1,
			endTime:'2017-09-08',
			startTime:'2017-09-08',
			isDisplay:false,
	 }

    var setting =  $.extend({},$.fn.floateradv.defaults,options);

     
     $.fn.floateradv.check = function(){
    	 
    	 
    	 
     },

	
	
	$.fn.floateradv.template=function(id){
    	/*var leftFloater = $("#leftFloater");
    	var rightFloater = $("#rightFloater");
    	if(rightFloater.html()==undefinded||leftFloater.html()==undefinded){
    		var $dom1 = "<div id='leftFloater' style='position: absolute; left: 984px; top: 1094px; z-index: 1000000;'>  <a href='http://aicwebw04/2.html' target='_blank'><img src='images/cc.png' border='0' class='float_ad_img'></a> <a href='javascript:;' id='close_float_ad' style='' title='点击关闭'>x</a></div>";
    		var $dom2 = "<div id='rightFloater' style='position: absolute; left: 984px; top: 1094px; z-index: 1000000;'>  <a href='http://aicwebw04/2.html' target='_blank'><img src='images/cc.png' border='0' class='float_ad_img'></a> <a href='javascript:;' id='close_float_ad' style='' title='点击关闭'>x</a></div>"; 
    		$(body).append($dom1);
    		$(body).append($dom2);
    	}
    	 return {
    		 dom1:dom1,
    		 dom2:dom2
    	 };*/
    	 
    	 var _dom1="";
    	 
	},
	
	$.fn.floateradv.init = function(){
		var imgDom = $(".floateradv img");
		imgDom.each(function(){
			var _this = $(this);
			_this.load(function(){
				var imgWidth = _this.width();
				var height = _this.height();

				return _this;
			});
			
		
		});

	},
	
	
	$.fn.floateradv.resize=function(dom){
		
		
		return $dom;
	},
	$.fn.floateradv.scorll=function(dom){
		return $dom;	
	}
	

}


})(jQuery)