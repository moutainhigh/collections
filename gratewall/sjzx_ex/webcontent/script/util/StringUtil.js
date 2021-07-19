var StringUtil = new Object();

StringUtil.create = function(){
}

StringUtil.create.prototype={
	numberToChar:function(number){
		if(number==26){
			return 'AA';
		}
		if(!isNaN(number)){
			var basis=26,offset=65,order=[],name="";
			while(number>basis){
				order[order.length]=number%basis;
			number=number/basis;
			}order[order.length]=number%basis;
			if(order.length>1)
			  if(order[order.length-1]>0)
			      order[order.length-1]--;
			for(var z=order.length-1;z>-1;z--){
				name+=String.fromCharCode(order[z]*1+offset);
			};
			return name;
		}
	},
	
	charToNumber:function(str){
		str = str.toUpperCase();
		var chCode;
		var number=0;
		for(var i=0;i<str.length;i++){
			 chCode = str.charCodeAt(i);
			 number+=(chCode-65)+(26*i);
		}		
		return number;
	},
	//A1 --> x:1,y:1
	parseCellIndex:function(cellIndex){
		  
	  	var row,col,outStr;
	 	var c;
	  	var splitIndex;
	 	for(i=0;i<cellIndex.length;i++){
	 	 	 c = cellIndex.charAt(i);
	 	 	 if(!isNaN(c)){//????????????????????????
	  	  	     splitIndex = i;
	  	  	     break;
	  	     }
	    }
	  
	    row = cellIndex.substring(splitIndex);
	  
	    col = this.charToNumber(cellIndex.substring(0,splitIndex))+1;
	  	return {x:row,y:col};
  	},
  	
  	getSpaceNumber:function(str){
  		var c;
  		var spaceNumber=0;
  		for(i=0;i<str.length;i++){
	 	 	 c = str.charAt(i);
	 	 	 spaceNumber = i;
	 	 	 if(c!=" "){
	 	 	 	break;
	 	 	 }
  		}
  		return spaceNumber;
  	}
}