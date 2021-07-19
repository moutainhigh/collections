function addAdviceValue(){
	var width = $('AdviceValueTip').getAttribute('_width');
	var height = $('AdviceValueTip').getAttribute('_height');

	if($("图片宽度")) $("图片宽度").value = width;
	if($("图片高度")) $("图片高度").value = height;
}

function setPicAdviceDimension(picfield, fcallback){
	if(!picfield) return;
	var sImgFileName = picfield.getValue();

	if(!sImgFileName) return;

	var img = new Image();
	img.onload = function(){

		var dimension = top.PageController.getMainWin().PageController.execute('getCurrentWidgetDimension');

		var nWidth = parseInt(dimension.width);
		var nHeight = Math.ceil(this.height * parseInt(dimension.width) / this.width);

		$('AdviceValueTip').setAttribute('_width', nWidth);
		$('AdviceValueTip').setAttribute('_height', nHeight);
		$('AdviceValueTip').innerHTML = " [ " + nWidth + " x " +  nHeight + " ] 点击可赋值" ;

		if(fcallback) fcallback();
	}
	img.src = '../../file/read_image.jsp?FileName=' + sImgFileName;
}

Event.observe(window,"load",function(){

	var picfield = com.trs.ui.ComponentMgr.get('PIC_FILE');
	if(picfield){
		picfield.addListener('upload', function(){
			setPicAdviceDimension(picfield, function(){
				addAdviceValue();
			});
		});
	}

	try{
		var dimension = top.PageController.getMainWin().PageController.execute('getCurrentWidgetDimension');

		//追加建议大小
		var elements = document.getElementsByClassName("row");
		if(elements != null && elements.length > 0){
			var newElement = document.createElement("div"); 
			var temp = "<DIV class=row onclick='addAdviceValue()'><DIV class=label>建议大小：</DIV>";
			temp += "<DIV class=value><span style='color:red' id='AdviceValueTip'></span></DIV></DIV>";
			newElement.innerHTML = temp;
			elements[elements.length -1].parentNode.appendChild(newElement);
		}

		setPicAdviceDimension(picfield);
	}catch(error){
		alert(error.message);
	}
	
});
