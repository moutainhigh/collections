//初始化资源块的高度
DOM.ready(function(){
	var widgets = document.getElementsByClassName("trs-widget");
	for(var i = 0; i < widgets.length; i++){
		var doms = document.getElementsByClassName('p_w_content', widgets[i]);
		
		if(doms.length <= 0) continue;
	
		var wHeight = widgets[i].getAttribute('trs-WHEIGHT');

		if(!wHeight) continue;

		try{
			doms[0].style.height = wHeight;
		}catch(error){
			//just skip it
		}
	}
});