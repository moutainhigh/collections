


/*出现返回顶部按钮*/
showScroll();
var min_height = document.documentElement.clientHeight / 2;
function showScroll() {
	$(window).scroll(function() {
		var s = $(window).scrollTop();
		s > min_height ? $('#top,#down').fadeIn().removeClass("rollOutRight").addClass("ball rollInRight") : $('#top,#down').fadeOut().removeClass("rollInRight").addClass("rollOutRight");
	});
}


/*返回顶部按钮点击*/
$("#top").click(function() {
	$('html,body').stop().animate({
		scrollTop : 0
	}, 700);
});




$(function(){
	//导航高亮开始
	$(".nav-ul li").bind("click", function () {
        var index = $(this).index();//获取序号
        $(".nav-ul li").eq(index).addClass("active").siblings().removeClass("active");
        $(".nav-xs-ul li").eq(index).addClass("active").siblings().removeClass("active");
    });
	//导航高亮结束
	
	
	 var _top;
     var top1 = $("#section2").offset().top-30;
     var top2 = $("#section3").offset().top-30;
     var top3 = $("#section4").offset().top-30;
     var top4 = $("#section5").offset().top-30;
     var top5 = $("#section6").offset().top-30;
	
	
     
     $(".nav-ul li").on("click", function () {
         var index = $(this).index();//获取序号
         _top = $(".section").eq(index+1).offset().top; //获取对应div距顶高度
         moveTo();
     });
     
	/*页面滚动*/
	$(window).scroll(function () {
		  var s = $(window).scrollTop();
		  console.log(s);
	});
	
	/*定义页面滚动位置*/
	 function moveTo(){
	        $('html,body').animate({
	            scrollTop: _top
	        }, 500);
	    }
});



