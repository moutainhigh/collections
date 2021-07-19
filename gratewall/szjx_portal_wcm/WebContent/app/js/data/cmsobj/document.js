//文档CMSObj对象
Ext.ns('wcm.Documents', 'wcm.Document', 'wcm.ChnlDoc', 'wcm.ChnlDocs');
wcm.Documents = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_DOCUMENT;
	wcm.Documents.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Documents, wcm.CMSObjs, {
	getDocIds : function(){
		return this.getIds();
	}
});
wcm.Document = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_DOCUMENT;
	wcm.Document.superclass.constructor.call(this, _config, _context);
	var arrRights ={
		"view":30,
		"new":31,
		"edit":32,
		"trash":33,
		"detail":34,
		"preview":38,
		"publish":39
	};
	for(rightName in arrRights){
		var cameRightName = rightName.camelize();
		var nRightIndex = parseInt(arrRights[rightName], 10);
		this['can'+cameRightName] = function(nRightIndex){
			return wcm.AuthServer.hasRight(this.right, nRightIndex);
		}.bind(this, nRightIndex);
	}
	this.addEvents(['preview', 'afterpreview', 'publish', 'afterpublish']);
}
CMSObj.register(WCMConstants.OBJ_TYPE_DOCUMENT, 'wcm.Document');
Ext.extend(wcm.Document, wcm.CMSObj, {
	getDocId : function(){
		return this.getId();
	},
	getRecId : function(){
		return 0;
	},
	getDocIds : function(){
		return [this.getDocId()];
	},
	getRelIds : function(){
		return [this.getRecId()];
	},
	getChannelId : function(){
		return this.getPropertyAsInt('DocChannel', 0);
	}
});
wcm.ChnlDocs = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHNLDOC;
	wcm.ChnlDocs.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.ChnlDocs, wcm.CMSObjs, {
	getDocIds : function(){
		var rstIds = [];
		for(var i=0,n=this.m_objs.length;i<n;i++){
			rstIds.push(this.m_objs[i].getDocId());
		}
		return rstIds;
	},
	getRelIds : function(){
		return this.getIds();
	}
});

wcm.ChnlDoc = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHNLDOC;
	wcm.ChnlDoc.superclass.constructor.call(this, _config, _context);
}
CMSObj.register(WCMConstants.OBJ_TYPE_CHNLDOC, 'wcm.ChnlDoc');
Ext.extend(wcm.ChnlDoc, wcm.Document, {
	getDocId : function(){
		return this.getPropertyAsInt("DocId", 0);
	},
	getRecId : function(){
		return this.getId();
	},
	getCurrChannelId : function(){
		return this.getPropertyAsInt('ChnlId', 0);
	}
});
