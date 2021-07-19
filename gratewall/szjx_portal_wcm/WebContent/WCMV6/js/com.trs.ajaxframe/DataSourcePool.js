$package('com.trs.ajaxframe');

com.trs.ajaxframe.DataSourcePool=Class.create('ajaxframe.DataSourcePool');
com.trs.ajaxframe.DataSourcePool.prototype={
	initialize : function() {
		this.tagNames=[];
		this.dataSources={};
	},
	register: function(_sTagName,_oDataSource){
		_sTagName=_sTagName.toUpperCase();
		this.tagNames.push(_sTagName);
		this.dataSources[_sTagName]=_oDataSource;
	},
	getDataSource:function(_sTagName){
		_sTagName=_sTagName.toUpperCase();
		var ds=this.dataSources[_sTagName];
		if(!ds){
			throw new Error('未找到任何匹配' + '[TAGNAME="' + _sTagName + '"]的数据源!\n'
				+ '请确认是否引入了该置标所对应的数据源！');
		}
		return ds;
	}
};