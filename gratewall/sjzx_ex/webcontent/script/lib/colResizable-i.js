 var lineMove = false;
 var currTh = null;
 var curTdWidth = null;
 var nextTdWidth = null;
 $(document).ready(function() {
     $("body").append("<div id=\"line\" style=\"width:1px;height:200px;border-left:1px solid gray; position:absolute;display:none\" ></div> ");
     $("body").bind("mousemove", function(event) {
         if (lineMove === true) {
             $("#line").css({ "left": event.clientX }).show();
         }
     });

     $("table#record tr:first td").bind("mousemove", function(event) {
         var th = $(this);
         var left = th.offset().left;
         if (event.clientX - left < 4 || (th.width() - (event.clientX - left)) < 4) {
             th.css({ 'cursor': 'e-resize' });
         }
         else {
             th.css({ 'cursor': 'default' });
         }
     });
     
     $("table#record tr:first td").bind("mousedown", function(event) {
         var th = $(this);
         
         var pos = th.offset();
         if (event.clientX - pos.left < 4 || (th.width() - (event.clientX - pos.left)) < 4) {
             var height = th.parent().parent().height();
             var top = pos.top;
             $("#line").css({ "height": height, "top": top,"left":event .clientX,"display":"" });
             lineMove = true;
             if (event.clientX - pos.left < th.width() / 2) {
                 currTh = th.prev();
             }
             else {
                 currTh = th;
             }
			 curTdWidth = currTh.width();
			 nextTdWidth = currTh.next().width();
         }
     });

     $("body").bind("mouseup", function(event) {
         if (lineMove == true) {
             $("#line").hide();
             lineMove = false;
             var pos = currTh.offset();
             var index = currTh.prevAll().length;
			 var length = $(this).children("td").length;
             currTh.width(event.clientX - pos.left);
             currTh.parent().parent().find("tr").each(function() {
				var newwidth = event.clientX - pos.left;
				$(this).children().eq(index).width(newwidth);
				$(this).children().eq((index+1)).width(curTdWidth+nextTdWidth-newwidth);
				curTdWidth = null;
				nextTdWidth = null;
            }); 
         }
     });
 
     $("table#record tr:first td").bind("mouseup", function(event) {
         if (lineMove == true) {
             $("#line").hide();
             lineMove = false;
             var pos = currTh.offset();
             var index = currTh.prevAll().length;
			 var length = $(this).children("td").length;
             currTh.width(event.clientX - pos.left);
             currTh.parent().parent().find("tr").each(function() {
				var newwidth = event.clientX - pos.left;
				$(this).children().eq(index).width(newwidth);
				$(this).children().eq((index+1)).width(curTdWidth+nextTdWidth-newwidth);
				curTdWidth = null;
				nextTdWidth = null;
            }); 
         }
     });
 }); 