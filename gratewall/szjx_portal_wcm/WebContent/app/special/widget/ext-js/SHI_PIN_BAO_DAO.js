Event.observe(window,"load",function(){
	try{
		var dimension = top.PageController.getMainWin().PageController.execute('getCurrentWidgetDimension');
		//填充默认值
		if($("视频宽度")){
			$("视频宽度").value = parseInt(dimension.width);
		}
		if($("视频高度")){
			$("视频高度").value = parseInt(dimension.width * 0.8);
		}
		//追加建议大小
		var elements = document.getElementsByClassName("row");
		if(elements != null && elements.length > 0){
			var newElement = document.createElement("div"); 
			var temp = "<DIV class=row><DIV class=label>建议大小：</DIV>";
			temp += "<DIV class=value><span style='color:red'> [ ";
			temp += (dimension.width + " x " + parseInt(dimension.width * 0.8));
			temp += " ] </span></DIV></DIV>";
			newElement.innerHTML = temp;
			elements[elements.length -1].parentNode.appendChild(newElement);
		}
	}catch(error){
		alert(error.message);
	}
	
});
