Event.observe(window,"load",function(){
	try{
		var dimension = top.PageController.getMainWin().PageController.execute('getCurrentWidgetDimension');
		if(getParameter("bAdd") ==0){
			dimension.width += 28;
		}
		var elements = document.getElementsByClassName("row");
		if(elements != null && elements.length > 0){
			var newElement = document.createElement("div"); 
			newElement.setAttribute("class","row");
			var columnNum = 4;
			if($("每行显示个数")){
				for(var j=0;j<5;j++){
					if($("每行显示个数_" + j).checked){
						columnNum = parseInt(j) + 1;
					}
				}
			}
			var tempWidth = parseInt(dimension.width / parseInt(columnNum)) - 40;
			//新增模式时填充默认值
			if(getParameter("bAdd") !=0){
				setPhotoSize(tempWidth);
			}
			var temp = "<DIV class=label>建议大小：</DIV>";
			temp += "<DIV class=value><span style='color:red;cursor:pointer' id='tip' title='点击完成自动填充'> [ ";
			temp += (tempWidth + " x " + parseInt(tempWidth * 0.8));
			temp += " ] </span></DIV>";
			newElement.innerHTML = temp;
			
			//默认建议大小放在图片高度后面，如果没有就放在最后
			if($("图片高度")){
				var parentElement = $("图片高度").parentNode;
				while(!Element.hasClassName(parentElement, "row")){
					parentElement = parentElement.parentNode;
				}
				parentElement.parentNode.insertBefore(newElement, parentElement.nextSibling);
			}else{
				elements[elements.length -1].parentNode.appendChild(newElement);
			}
			
			//绑定每行显示个数的按钮事件
			if($("每行显示个数-box")){
				for(var i=0; i<5; i++){
					Event.observe($("每行显示个数_" + i),"click",function(){
						updateMessage(dimension);
					});
				}
			}
			//绑定自动完成事件
			if($("tip")){
				Event.observe($("tip"),"click",function(){
						autoUpdate(dimension);
					});
			}
		}
	}catch(error){
		alert(error.message);
	}
	
});

function updateMessage(dimension){
	for(var j=0;j<5;j++){
		if($("每行显示个数_" + j).checked){
			var tempWidth = parseInt(dimension.width / parseInt(j + 1)) - 40;
			if(getParameter("bAdd") !=0){
				setPhotoSize(tempWidth);
			}
			if($("tip")){
				$("tip").innerHTML = "[ " + (tempWidth + " x " + parseInt(tempWidth * 0.8)) + " ]";
			}
		}
	}
}

function setPhotoSize(tempWidth){
	if($("图片宽度")){
		$("图片宽度").value = parseInt(tempWidth);
	}
	if($("图片高度")){
		$("图片高度").value = parseInt(tempWidth * 0.8);
	}
}

function autoUpdate(dimension){
	for(var j=0;j<5;j++){
		if($("每行显示个数_" + j).checked){
			var tempWidth = parseInt(dimension.width / parseInt(j + 1)) - 40;
			setPhotoSize(tempWidth);
		}
	}
}