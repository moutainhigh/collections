//Download by http://www.codefans.net
var h0 = -1;
var h1 = -1;
var h2 = -1;
var h3 = -1;
    //arr=new Array(0,0,0,0)
function flip(upperId, lowerId, changeNumber, pathUpper, pathLower) {
	var upperBackId = upperId + "Back";
	$(upperId).src = $(upperBackId).src;
	$(upperId).setStyle("height", "18px");
	$(upperId).setStyle("visibility", "visible");
	var changeNumber = parseInt(changeNumber);
	if(changeNumber == "NaN"){
		changeNumber = 0;
	}
	$(upperBackId).src = pathUpper + changeNumber + ".png";
	$(lowerId).src = pathLower + changeNumber + ".png";
	$(lowerId).setStyle("height", "0px");
	$(lowerId).setStyle("visibility", "visible");
	var flipUpper = new Fx.Tween(upperId, {duration:200, transition:Fx.Transitions.Sine.easeInOut});
	flipUpper.addEvents({"complete":function () {
		var flipLower = new Fx.Tween(lowerId, {duration:200, transition:Fx.Transitions.Sine.easeInOut});
		flipLower.addEvents({"complete":function () {
			lowerBackId = lowerId + "Back";
			$(lowerBackId).src = $(lowerId).src;
			$(lowerId).setStyle("visibility", "hidden");
			$(upperId).setStyle("visibility", "hidden");
		}});
		flipLower.start("height", 18);
	}});
	flipUpper.start("height", 0);
}//flip
function retroClock(s) {
	//alert(s);
	//arr=new Array(0,0,0,0)
	//	arr=new Array(8,6,8,8)
	arr = new Array;
	j = 0;
       //  Str=document.getElementById("mgr").value ;
	
	   // Str='8688';
       //alert(Str.length);
	i = 4 - s.length;
	while (j < s.length) {
		arr[i] = s.charAt(j);
			//alert("i="+i+","+"arr="+arr[i]);
		i++;
		j++;
	}
	//alert(arr);
	if (arr[0] != h0) {
		flip("h3Up", "h3Down", arr[0], "css/Single/Up/", "css/Single/Down/");
		h0 = arr[0];
	}
	if (arr[1] != h1) {
		flip("h2Up", "h2Down", arr[1], "css/Single/Up/", "css/Single/Down/");
		h1 = arr[1];
	}
	if (arr[2] != h2) {
		flip("h1Up", "h1Down", arr[2], "css/Single/Up/", "css/Single/Down/");
		h2 = arr[2];
	}
	if (arr[3] != h3) {
		flip("h0Up", "h0Down", arr[3], "css/Single/Up/", "css/Single/Down/");
		h3 = arr[3];
	}
}
function addNum(s) {
	//alert(s);
	//arr=new Array(0,0,0,0)
	arr = new Array();
	j = 0;
	var str = String(s);
	i = 4 - str.length;
	for(var ii=0;ii<i;ii++){
		str = 0+str;
	}
	i = 4 - str.length;
	while (j < str.length) {
		
		arr[i] = str.charAt(j);
		//alert("i="+i+","+"arr="+arr[i]);
		i++;
		j++;
	}
		// alert(arr);
	if (arr[0] != h0) {
		flip("h3Up", "h3Down", arr[0], "css/Single/Up/", "css/Single/Down/");
		h0 = arr[0];
	}
	if (arr[1] != h1) {
		flip("h2Up", "h2Down", arr[1], "css/Single/Up/", "css/Single/Down/");
		h1 = arr[1];
	}
	if (arr[2] != h2) {
		flip("h1Up", "h1Down", arr[2], "css/Single/Up/", "css/Single/Down/");
		h2 = arr[2];
	}
	if (arr[3] != h3) {
		flip("h0Up", "h0Down", arr[3], "css/Single/Up/", "css/Single/Down/");
		h3 = arr[3];
	}
}

