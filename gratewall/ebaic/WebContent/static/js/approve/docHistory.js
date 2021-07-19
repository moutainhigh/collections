define(['require','jquery','approvecommon'], function(require,$, approvecommon){
	
	var pic = {
		_init : function(){
			var that = this;
			require(['domReady'], function (domReady) {
				domReady(function (){
					$.ajax({
		        		url: '../../../pdf/v2/getHtml.do?time='+new Date().getTime(),
		        		data: {gid: jazz.util.getParameter('gid')||''},
						dataType:'json',
		        		success : function(data) {
		        			if(data && data.length > 0){
		        				var chapter = '';
		        				var content = '';
		        				for(var i = 0, len = data.length; i < len; i++){
		        					chapter += '<a class="chapter_content" href="#'+data[i].href+'">'+data[i].title+'</a>';
		        					content += '<div class="gap" id="' + data[i].href + '"></div>' + data[i].htmlCont;
		        				}
		        				$('#chapter').append(chapter);
		        				$('#contentMain').append(content);
		        				$('.chapter_content').on('click',function(){
		        					$(this).siblings().removeClass('selected');
		        					$(this).addClass('selected');
		        				});
		        				$('.chapter_content').first().addClass('selected');
		        				var width = $('#chapter').width();
		        				$('#fixed_content').css('width',width);
		        				var height = $('#fixed_content').height();
		        				$('#contentMain').css('padding-top',height);
		        			}
			        	},
	        			error : function(){
	        				jazz.info('文书加载失败！');
	        			}
		            });
					
					
				});//end of dom ready
			});//end of require
		}// end of _init
	};
	pic._init();
	return pic;
});


