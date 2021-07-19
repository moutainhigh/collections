//中文
function CTRSButton(id){
	var itm = [
		"<div class='ctrsbtn' _action='{1}'>",
			"<div class='ctrsbtn_left'>",
				"<div class='ctrsbtn_right'>{0}</div>",
			"</div>",
		"</div>"
	].join('');
	this.id = id;
	this.init = function(){
		var rst = [];
		var btns = this.btns;
		//此处颇为诡异，button有action和id属性，却将id属性作为要生成的div的_action属性。
		for(var i=0,n=btns.length;i<n;i++){
			rst.push(String.format(itm, btns[i].html, btns[i].id));
		}
		var html = rst.join('\n');
		$(this.id).innerHTML = html;
		var caller = this;
		Ext.get(this.id).on('click', function(event, target){
			var btn = findItem(target, 'ctrsbtn');
			if(btn==null)return;
			var action = btn.getAttribute('_action', 2);
			var item = caller.json_btns[action], fn;
			if(!item || !(fn=item.action))return;
			if(typeof fn=='string')eval('fn=' + fn);
			fn.apply(null, [event, target]);
		});
	};
	this.setButtons = function(buttons){
		this.btns = buttons;
		var json = this.json_btns = {};
		for(var i=0,n=buttons.length;i<n;i++){
			json[buttons[i].id] = buttons[i];
		}
	}
};