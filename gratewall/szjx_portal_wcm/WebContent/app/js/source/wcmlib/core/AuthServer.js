Ext.ns('wcm.AuthServer');
(function(){
	function isAdmin(){
		return $MsgCenter.getActualTop().global_IsAdmin;
	}	var arr = [];
	for(var i=0;i<64;i++){
		arr.push([0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]);
	}
	arr[14] = [11,13,12,15,16,17,19,18,53,55,0,0,0,0,0,0,0,0,0,0];
	arr[24] = [21,23,22,25,24,21,24,28,0,0,0,0,0,0,0,0,0,0,0,0];
	arr[25] = [21,23,21,28,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
	arr[30] = [18,31,32,33,33,34,38,39,34,34,34,35,36,37,56,54,8,0,0,0];
	arr[33] = [18,18,33,33,56,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
	arr[34] = [18,32,33,33,38,39,35,36,37,56,54,0,0,0,0,0,0,0,0,0];
	arr[35] = [36,37,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
	m_pSimilarIndexs = arr;
	var m_pSpecialIndexs = [38,39];//合并权限时作或操作
	function _hasSimlarRight(srg, nrix) {
		var oas = wcm.AuthServer;
		if(m_pSimilarIndexs.length <= nrix)return false;
		var exitRecursion = false;
		if(!oas.sriCache){
			oas.sriCache = {};
			exitRecursion = true;
		}
		if(oas.sriCache[nrix])return false;
		oas.sriCache[nrix] = true;
		var arr = m_pSimilarIndexs[nrix] || [];
		var bResult = false;
		for (var i = 0; i < arr.length; i++) {
			var nsi = arr[i];
			if (nsi == 0)break;
			if (oas.hasRight(srg, nsi)) {
				 bResult = true;
				 break;
			}
		}	
		if(exitRecursion)oas.sriCache = null;
		return bResult;
	}
	function _hasRight(srg, rix){
		var len = srg.length;
		if(rix>=len)return false;
		var nri = len-1-rix;
		return srg.charAt(nri)=='1';
	}

	Ext.apply(wcm.AuthServer, {
		isAdmin : isAdmin,
		getRightValue : function(){
			return getParameter("RightValue") || '0';
		},
		hasRight : function(srg, rix){
			rix = parseInt(rix, 10);
			if(rix == -1)return true;
			if(rix == -2){
				if(isAdmin())return true;
				return false;
			}
			if(!srg)return false;
			if (rix == 64 && srg.indexOf("1") >= 0)
				return true;
			if (_hasRight(srg, rix))return true;
			if (_hasSimlarRight(srg, rix))return true;
			return false;
		},
		checkRight : function(rightValue, rightIndex){
			//if(isAdmin()) return true;
			if(rightIndex == -1)return true;
			if(!rightValue) return false;
			if(Ext.isNumber(rightIndex)){
				return this.hasRight(rightValue, rightIndex);
			}else if(Ext.isString(rightIndex)){
				var rightIndexs = rightIndex.split(",");
				if(rightIndexs.length>1){
					for (var i = 0; i < rightIndexs.length; i++){
						if(this.checkRight(rightValue, rightIndexs[i]))
							return true;
					}
					return false;
				}
				var rightIndexs = rightIndex.split("-");//仅为负数的时候，不应该作为区间判断，如-2
				if(rightIndexs[0] == '' || rightIndexs.length==1){
					return this.hasRight(rightValue, parseInt(rightIndex, 10));
				}
				for (var i = parseInt(rightIndexs[0]); i <= parseInt(rightIndexs[1]); i++){
					if(this.hasRight(rightValue, i))
						return true;
				}
				return false;
			}else if(Ext.isArray(rightIndex)){//eg. rightIndex:[3,"32-45"]
				for (var i = 0; i < rightIndex.length; i++){
					if(this.checkRight(rightValue, rightIndex[i]))
						return true;
				}
				return false;
			}
			return false;
		},
		mergeRights : function(arr){
			var maxLen = 0, rst = '', tmpidx = -1;
			for(var i=0;i<arr.length;i++){
				arr[i] = arr[i] || '';
				if(arr[i].length>maxLen){
					maxLen = arr[i].length;
				}
			}
			for(var i=0;i<maxLen;i++){
				var b = maxLen>i&&m_pSpecialIndexs.include(maxLen-1-i);
				var c0 = b?'0':'1', c1 = b?'1':'0';
				for(var j=0;j<arr.length;j++){
					tmpidx = i+arr[j].length-maxLen;
					if(tmpidx>=0&&arr[j].charAt(tmpidx)==c1){
						c0 = c1;
						break;
					}
				}
				rst += c0;
			}
			return rst;
		}
	});
})();
