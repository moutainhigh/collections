$package("com.trs.editor");

com.trs.editor.Component=Class.create('editor.Component');
com.trs.editor.Component.prototype={
	initialize:function(id,x,y){
		this.id=id;
		this.x=x;
		this.y=y;
		this.commands=[];

		Event.observe(window, 'unload', function(){
			$destroy(this);
		}.bind(this),false);
		
	},
	addCommand:function(name,id,hint,cmd,type,other){
		var command=new com.trs.editor.Commander(name,id,hint,cmd,type,other);
		this.commands[this.commands.length]=command;
	},
	toElement:function(_parentElement)	{
		var div=$(this.id);
		if(!div)
		{
			div=document.createElement('DIV');
			div.className='Component';
			div.id=this.id;
			div.style.top=this.y;
			div.style.left=this.x;
			_parentElement.appendChild(div);
			delete _parentElement;
			if(	this.id=="UploadImg")
			{
				var html='<table width="100%" cellspacing=0 cellpadding=0 border=0>';
				var oneline=2;
				for(var i=0;i<this.commands.length;i++)
				{
					var hideDiv=document.createElement('div');
					hideDiv.style.display='none';
					document.body.appendChild(hideDiv);
					var cmd=this.commands[i].toElement(hideDiv);
					if(i%oneline==0)html+='<tr>';
					html+='<td align=center>'+cmd.innerHTML+'</td>';
					if((i+1)%oneline==0)html+='</tr>';
					document.body.removeChild(hideDiv);
				}
				if(this.commands.length%oneline!=0)
				{
					html+='<td colspan='+this.commands.length%oneline+'>&nbsp;</td></tr>'
				}
				div.innerHTML=html+'</table>';
				div.style.marginLeft=10;
			}
			else
			{
				for(var i=0;i<this.commands.length;i++)
				{
					if(i!=0)
					{
						var separator=new com.trs.editor.Separator();
						separator.toElement(div);
					}
					this.commands[i].toElement(div);
				}
			}
		}
	}
}

com.trs.editor.Separator=Class.create();
com.trs.editor.Separator.prototype={
	initialize:function(id,b){
		if(b)
		{
			this.className='Component_Separator';
		}
		else
		{
			this.className='Command_Separator';
		}
		Event.observe(window, 'unload', function(){
			$destroy(this);
		}.bind(this),false);
	},
	toElement:function(_parentElement){
		var div=document.createElement('DIV');
		div.className=this.className;
		_parentElement.appendChild(div);
		delete _parentElement;
		return div;
	}
}