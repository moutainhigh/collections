var href = window.location.href;
var port = window.location.port;
var baseUrl = '';
var baseUrl =""
var pathName = window.location.pathname.substring(1);
var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
if (webName == "") {
	baseUrl = window.location.protocol + '//' + window.location.host;
}else {
	baseUrl =  window.location.protocol + '//' + window.location.host + '/' + webName;
}

var require={    
    baseUrl:baseUrl+'/static/js', 
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
        "form":"../lib/jquery.form",
        "cookie":"../lib/jquery.cookie"
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
        },
        'cookie': {
        	deps: ['jquery'],
        	exports: 'cookie'
        }
    } 
};