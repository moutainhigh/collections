$(function(){
	$("#formpanel").formpanel("option","dataurlparams",{"pkDcColumn":pkDcColumn});
	$("#formpanel").formpanel("option","dataurl",rootPath+"/tableManager/findColumnById.do?pkDcColumn="+pkDcColumn);
	$("#formpanel").formpanel("reload");
	
	if(type==2){
		$("#btn3").button("hide");
		$("#formpanel").formpanel("option","readonly",true);
	}
	   
    $("#standardCode").comboxfield("option","dataurl", rootPath+"/tableManager/getALLCodeOrStand.do"); 

    $("#standardCode").comboxfield("reload"); 	
    
    $("#pkDcStandarCodeindex").comboxfield("option","disabled", true); 
    
	//是否代码集
    $("#isStandard").on("radiofielditemselect",function(event, ui){  

    	var iscodedate =$("#isStandard").radiofield("getValue");
    
    	if(iscodedate==1){//合标
    		  $("#pkDcStandardDataelement").comboxfield("option","disabled", false); 
	   	     $("#pkDcStandarCodeindex").comboxfield("reset"); 
	   	     $("#pkDcStandarCodeindex").comboxfield("option","disabled", true); 
    	}else if(iscodedate==2){//参照
	
	   	     $("#pkDcStandardDataelement").comboxfield("option","disabled", false); 

	   	     $("#pkDcStandarCodeindex").comboxfield("option","disabled", false);   	 
    	}else if(iscodedate==3){//非合标
	   	     $("#pkDcStandardDataelement").comboxfield("option","disabled", false); 

	   	     $("#pkDcStandarCodeindex").comboxfield("option","disabled", false);   	  	 
    	}else if(iscodedate==4){//无标可依
	   	     $("#pkDcStandardDataelement").comboxfield("reset"); 
	   	     $("#pkDcStandardDataelement").comboxfield("option","disabled", true); 
	   	     $("#pkDcStandarCodeindex").comboxfield("reset"); 
	   	     $("#pkDcStandarCodeindex").comboxfield("option","disabled", true);   	     
    	}

    }); 
    
    //是否合标
    $("#isStandard").on("radiofielditemselect",function(event, ui){  

    	dual();

    }); 
});
function dual(){

	var iscodedate =$("#isCodedata").radiofield("getValue");
	var isStandard =$("#isStandard").radiofield("getValue");
	if(iscodedate=="Y"&&isStandard=="Y"){//是代码集并且合标
	
	     $("#standardCode").comboxfield("reset"); 
	    $("#standardCode").comboxfield("option","disabled", false); 
	   
	    $("#standardCode").comboxfield("option","dataurl", rootPath+"/tableManager/getCode.do"); 

	    $("#standardCode").comboxfield("reload"); 	
	}else if(iscodedate=="N"&&isStandard=="Y"){//是基础数据元 并且合标
		
	     $("#standardCode").comboxfield("reset"); 
	    $("#standardCode").comboxfield("option","disabled", false); 
	    
	    $("#standardCode").comboxfield("option","dataurl", rootPath+"/tableManager/getStand.do"); 

	    $("#standardCode").comboxfield("reload"); 	
	}else{//不合标
        $("#standardCode").comboxfield("option","disabled",true);  
        $("#standardCode").comboxfield("reset"); 
	}


	
}
function save(){
	var params = {
		url:rootPath+"/tableManager/updateColumn.do", 
		components: ['formpanel'],
		callback : function(data,obj,res) {
			query();
			leave();
		}
	};
	$.DataAdapter.submit(params);
}

function leave(){
	window.top.$("#frame_maincontent").get(0).contentWindow.leave();
}

function query(){
	window.top.$("#frame_maincontent").get(0).contentWindow.query();
}


