define(['jquery', 'jazz', 'renderTemplate', 'util', '../torch/demoTorch'], function($, jazz, renderTemplate, util, demoTorch){
    var demo = {
    	_init: function(){
	
	    	$("body",window.parent.document).renderTemplate({templateName:'footer',insertType:'append'});
	    	
	    	var bb = $("#bbb").textfield("getValue");
	    	//var ii = $("#bbb",window.parent.document).textfield("getValue");
	    	//var ii = $("#aaa",window.parent.document).textfield("getValue");
	    	
	    	$("#closewin").on('click',function(){
	    		demoTorch.aa();
	    		util.closeWindow('ccc');
	    	});
	    	
	    	
	    	
    	},
    	aclick: function(){
    		alert(demo.ii);
    	},
    	ii: "abcaaaaaaaaaaaaaa"
    	
    };
    demo._init();
    return demo;
});