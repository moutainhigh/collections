Event.observe(window,"load",function(){
   var dimension = top.PageController.getMainWin().PageController.execute('getCurrentWidgetDimension');
		
	try{
		 var dwidth = parseInt(dimension.width);

		 var length =Math.floor((dwidth - 118)/14)*2;
		// alert(dwidth+"    "+length);
		//填充默认值
		if($("标题字数")){
			$("标题字数").value = length;
		}
		
		//追加建议大小
		var elements = document.getElementsByClassName("row");
		if(elements != null && elements.length > 0){
			var newElement = document.createElement("div"); 
			var temp = "<DIV class=row onclick='addAdviceValue()'><DIV class=label>建议大小：</DIV>";
			temp += "<DIV class=value><span style='color:red' id='Titlelength'></span></DIV></DIV>";
			newElement.innerHTML = temp;
			elements[elements.length -1].parentNode.appendChild(newElement);
		}

		setAdviceDimension(length);
	}catch(error){
       alert(error.message);
	}
});

function addAdviceValue(){
	var length = $('Titlelength').getAttribute('_length');
	if($("标题字数")) $("标题字数").value = length;
}

function setAdviceDimension(length){
		$('Titlelength').setAttribute('_length', length);		
		$('Titlelength').innerHTML = " [ "  +  length + " ] 点击可赋值" ;
}
