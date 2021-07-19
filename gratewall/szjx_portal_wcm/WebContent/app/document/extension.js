/*保证代码都在load事件中处理*/
Event.observe(window, 'load', function(){
	PageWin.addListener('beforeclose', function(params){
		//TODO....
	});	
});