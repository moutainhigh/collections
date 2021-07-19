Event.observe(window,"load",function(){
	try{
	
		var tpgs = 5;
		var dimension = top.PageController.getMainWin().PageController.execute('getCurrentWidgetDimension');
		//填充默认值
		if($("图片宽度")){
			$("图片宽度").value = parseInt(dimension.width)-10;
		}
		if($("图片高度")){
			$("图片高度").value = parseInt(dimension.width * 0.8)-10;
		}
		if($("缩略图宽度")){
			var doms = document.getElementsByName('图片个数');
			
		for(var index = 0; index < doms.length - 1; index++){
		var label = Element.next(doms[index]);
		tpgs = doms[index].value;		
		}

			$("缩略图宽度").value = parseInt((dimension.width-tpgs*13) /tpgs);
		}
		//追加建议大小
		var elements = document.getElementsByClassName("row");
		if(elements != null && elements.length > 0){
			var newElement = document.createElement("div"); 
			var temp = "<DIV class=row><DIV class=label>建议大小：</DIV>";
			temp += "<DIV class=value><span style='color:red'>图片（高、宽）: [ ";
			temp += (parseInt(dimension.width * 0.8)-10)+ " x " + ((dimension.width -10));
			temp += " ] 缩略图建议大小："+ parseInt((dimension.width-tpgs*13) /tpgs)+" </span></DIV></DIV>";
			newElement.innerHTML = temp;
			elements[elements.length -1].parentNode.appendChild(newElement);
		}
	}catch(error){
		alert(error.message);
	}
	
});
