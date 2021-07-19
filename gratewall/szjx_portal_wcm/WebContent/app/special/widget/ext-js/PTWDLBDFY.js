Event.observe(window,"load",function(){
   var dimension = top.PageController.getMainWin().PageController.execute('getCurrentWidgetDimension');
	
	try{
		 var dwidth = parseInt(dimension.width);
         
		 var length =Math.floor((dwidth - 120)/14)*2;		
		
		// alert(dwidth+"    "+length);
		//填充默认值
		if($("标题字数")){
			$("标题字数").value = length;
		}
		
		//追加建议大小
		var elements = document.getElementsByClassName("row");
		if(elements != null && elements.length > 0){
			var newElement = document.createElement("div"); 
			var temp = "<DIV class=row onclick='addAdviceValue()'><DIV class=label>标题字数建议个数：</DIV>";
			temp += "<DIV class=value><span style='color:red' id='Titlelength'>[ "+length+" ] 个</span></DIV></DIV>";
			newElement.innerHTML = temp;
			elements[elements.length -1].parentNode.appendChild(newElement);
		}
	}catch(error){
       alert(error.message);
	}
});

