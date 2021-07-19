//Download by http://www.codefans.net
	var h0= -1;
	var h1= -1;
	var h2= -1;
	var h3= -1;
	var h4= -1;
	var h5= -1;
	var h6= -1;
	var h7= -1;
	var h8= -1;
	var h9= -1;
    //arr=new Array(0,0,0,0,0,0,0,0,0,0)
	
	function flip (upperId, lowerId, changeNumber, pathUpper, pathLower){
		var upperBackId = upperId+"Back";
		$(upperId).src = $(upperBackId).src;
		$(upperId).setStyle("height", "18px");
		$(upperId).setStyle("visibility", "visible");
		$(upperBackId).src = pathUpper+parseInt(changeNumber)+".png";
		
		$(lowerId).src = pathLower+parseInt(changeNumber)+".png";
		$(lowerId).setStyle("height", "0px");
		$(lowerId).setStyle("visibility", "visible");
		
		var flipUpper = new Fx.Tween(upperId, {duration: 200, transition: Fx.Transitions.Sine.easeInOut});

		flipUpper.addEvents({
			'complete': function(){
				var flipLower = new Fx.Tween(lowerId, {duration: 200, transition: Fx.Transitions.Sine.easeInOut});
					flipLower.addEvents({
						'complete': function(){	
							lowerBackId = lowerId+"Back";
							$(lowerBackId).src = $(lowerId).src;
							$(lowerId).setStyle("visibility", "hidden");
							$(upperId).setStyle("visibility", "hidden");
						}				});					
					flipLower.start('height', 18);
					
			}
							});
		flipUpper.start('height', 0);
		
		
	}//flip
				
	
	function retroClock(s){
		alert(s)
		//arr=new Array(0,0,0,0,0,0,0,0,0)
	//	arr=new Array(1,0,9,3,5,8,6,8,8)
	     arr = new Array;
		 j=0;

       //  Str=document.getElementById("mgr").value ;
	
	   // Str='109358688';
       //alert(Str.length);
		i=9-s.length;
        
       while(j<s.length){
            
            arr[i]=s.charAt(j);
			//alert("i="+i+","+"arr="+arr[i]);
			i++;
			j++;

		}
		 //  alert(arr);

		if(arr[0]!=h0){
			flip('h8Up', 'h8Down', arr[0], 'css/Single/Up/', 'css/Single/Down/');
			h0=arr[0];
		}
		if(arr[1]!=h1){
			flip('h7Up', 'h7Down', arr[1], 'css/Single/Up/', 'css/Single/Down/');
			h1=arr[1];
		}
		if(arr[2]!=h2){
			flip('h6Up', 'h6Down', arr[2], 'css/Single/Up/', 'css/Single/Down/');
			h2=arr[2]
		}
		if(arr[3]!=h3){
			flip('h5Up', 'h5Down', arr[3], 'css/Single/Up/', 'css/Single/Down/');
			h3=arr[3]
		}
		if(arr[4]!=h4){
			flip('h4Up', 'h4Down', arr[4], 'css/Single/Up/', 'css/Single/Down/');
			h4=arr[4]
		}
		if(arr[5]!=h5){
			flip('h3Up', 'h3Down', arr[5], 'css/Single/Up/', 'css/Single/Down/');
			h5=arr[5]
		}
		if(arr[6]!=h6){
			flip('h2Up', 'h2Down', arr[6], 'css/Single/Up/', 'css/Single/Down/');
			h6=arr[6]
		}
		if(arr[7]!=h7){
			flip('h1Up', 'h1Down', arr[7], 'css/Single/Up/', 'css/Single/Down/');
			h7=arr[7]
		}
		if(arr[8]!=h8){
			flip('h0Up', 'h0Down', arr[8], 'css/Single/Up/', 'css/Single/Down/');
			h8=arr[8]
		}
		
		
	}
	

function addNum(s){
		//alert(s)
		//arr=new Array(0,0,0,0,0,0,0,0,0)
		  
	    arr = new Array();
		 j=0;
var str=String(s);
		i=9-str.length;
               
      while(j<str.length){
        arr[i]=str.charAt(j);
			//alert("i="+i+","+"arr="+arr[i]);
			i++;
			j++;
		}
		// alert(arr);

		if(arr[0]!=h0){
			flip('h8Up', 'h8Down', arr[0], 'css/Single/Up/', 'css/Single/Down/');
			h0=arr[0];
		}
		if(arr[1]!=h1){
			flip('h7Up', 'h7Down', arr[1], 'css/Single/Up/', 'css/Single/Down/');
			h1=arr[1];
		}
		if(arr[2]!=h2){
			flip('h6Up', 'h6Down', arr[2], 'css/Single/Up/', 'css/Single/Down/');
			h2=arr[2]
		}
		if(arr[3]!=h3){
			flip('h5Up', 'h5Down', arr[3], 'css/Single/Up/', 'css/Single/Down/');
			h3=arr[3]
		}
		if(arr[4]!=h4){
			flip('h4Up', 'h4Down', arr[4], 'css/Single/Up/', 'css/Single/Down/');
			h4=arr[4]
		}
		if(arr[5]!=h5){
			flip('h3Up', 'h3Down', arr[5], 'css/Single/Up/', 'css/Single/Down/');
			h5=arr[5]
		}
		if(arr[6]!=h6){
			flip('h2Up', 'h2Down', arr[6], 'css/Single/Up/', 'css/Single/Down/');
			h6=arr[6]
		}
		if(arr[7]!=h7){
			flip('h1Up', 'h1Down', arr[7], 'css/Single/Up/', 'css/Single/Down/');
			h7=arr[7]
		}
		if(arr[8]!=h8){
			flip('h0Up', 'h0Down', arr[8], 'css/Single/Up/', 'css/Single/Down/');
			h8=arr[8]
		}
		
		
	}
	
	
			
	