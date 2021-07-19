var href = window.location.href;
var port = window.location.port;
var baseUrl = '';
var inhref = href.indexOf('/page');
var inport = href.indexOf(port);
if(inport > 0 && inhref > 0){
	baseUrl = href.substring(inport + port.length,inhref);
}
var require={    
    baseUrl:'static/js', 
    paths: {    
        "jquery": "../lib/jquery-1.8.3",    
        "jquery.ui": "../lib/jquery-ui-1.9.2.custom",
        "ajaxfileupload": "../lib/ajaxfileupload",
        "etpl": "../lib/etpl.source",    
        "jazz": "../lib/JAZZ-UI/lib/jazz-all",
        "domReady": "../lib/domReady",
        "jquery.Jcrop": "../lib/jquery.Jcrop",
        "xtxsutit":"../lib/XTXSuite",
        "print":"../lib/jquery.PrintArea",
        "form":"../lib/jquery.form"
    },
    waitSeconds: 150,
    shim: {
        'jquery.ui': {
            deps: ['jquery'],
            exports: 'jQuery.fn.ui'
        },
        'jquery.Jcrop': {
        	deps: ['jquery'],
        	exports: 'jQuery.fn.jcrop'
        },
        'ajaxfileupload': {
        	deps: ['jquery'],
        	exports: 'ajaxfileupload'
        },
        'uploadToo': {
        	deps: ['jquery','jquery.Jcrop','ajaxfileupload'],
        	exports: 'uploadToo'
        },
        'print': {
        	deps: ['jquery'],
        	exports: 'PrintArea'
        },
        'form': {
        	deps: ['jquery'],
        	exports: 'form'
        }
    } 
};