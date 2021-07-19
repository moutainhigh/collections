/* a basic contents menu plugin by Joel Birch. Demo page to come.
 *
 * updated 4th March 2007 to replace switch with 'object[method]()'
 * so now even less code
 *
 * updated 5th June 2007 to add hooks for enhancements (plugins to this plugin)
 * now stores menu widgets in array, $.cm and each menu widget stores its index as elem.serial expando
 */

(function($){
	$.fn.contentMenu = function(o){
		o = $.extend({
			"head"		: "<h3>Some handy links to help you navigate this page:</h3>",
			"beforeLink": "Skip to ",
			"divClass"	: "contentMenu",
			"aClass"	: "inPage",
			"insertMethod"	: "before",
			"insertTarget"	: this.eq(0) }, o || {});
		$.cm = ($.cm) ? $.cm : [];
		var $list = $("<ul>"),
			lastInd = this.length-1,
			lis = '',
			s = $.cm.length,
			menu = $.cm[s] = $('<div class="'+o.divClass+'"></div>').append(o.head,$list);
			menu[0].serial = s;
		o.insertTarget[o.insertMethod](menu);
		return this.each(function(i){
			this.id = this.id || "menu"+s+"-el"+i;
			lis += '<li><a href="#'+this.id+'" class="'+o.aClass+'">'+o.beforeLink+'<em>'+$(this).text()+'</em></a></li>';
			if (i==lastInd){ $list.append(lis); }
		});
	};
})(jQuery);