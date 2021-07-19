function CTRSString(){
	this.trim			= function(s){
		return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
	}

	this.getStringLength = function(str){
		var totallength=0;
		for (var i=0; i < str.length;i++){
			var intCode=str.charCodeAt(i);
			if (intCode>=0&&intCode<=128) {
				totallength=totallength+1;	//非中文单个字符长度加 1
			}else {
				totallength=totallength+2;	//中文字符长度则加 2
			}
		} //end for
		return totallength;	
	}

	this.truncateStr = function(str, length, ext){
		/*
		var oTRSAction = new CTRSAction("../tools/string_truncate.jsp");
		oTRSAction.setParameter("TruncateStr", (str || ""));
		oTRSAction.setParameter("Length", (length || 0));
		if(ext)
			oTRSAction.setParameter("Ext", ext);
		return this.trim(oTRSAction.doXMLHttpAction());
		*/
		var strTemp = str || "";
		if(strTemp.length <= length) return strTemp;
		var strExt = ext || "...";
		return strTemp.substring(0, length) + strExt;
	}

	this.transDisplayHtml = function(str){
		var sValue = str || "";
		var regExp1 = /\</g;
		sValue = sValue.replace(regExp1, "&lt;");
		var regExp2 = /\>/g;
		sValue = sValue.replace(regExp2, "&gt;");
		return sValue;
	}

	this.filterForStr = function(str, nType){
		var oTRSAction = new CTRSAction("../tools/string_filter.jsp");
		oTRSAction.setParameter("StringToFilte", (str || ""));
		oTRSAction.setParameter("FilterType", (nType || 0));
		return this.trim(oTRSAction.doXMLHttpAction());
	}

	this.encodeForCDATA = function(str){
		if(!str) return;
		var regExp = /\]\]>/g;
		return str.replace(regExp, "(TRSWCM_CDATA_END_HOLDER_TRSWCM)");
	}

	this.decodeForCDATA = function(str){
		if(!str) return;
		var regExp = /\(TRSWCM_CDATA_END_HOLDER_TRSWCM\)/g;
		return str.replace(regExp, "]]>");
	}

	this.isEmailAddress  = function(emailStr){
		if (emailStr.length == 0) {
		   return true;
		}
		var emailPat=/^(.+)@(.+)$/;
		var specialChars="\\(\\)<>@,;:\\\\\\\"\\.\\[\\]";
		var validChars="\[^\\s" + specialChars + "\]";
		var quotedUser="(\"[^\"]*\")";
		var ipDomainPat=/^(\d{1,3})[.](\d{1,3})[.](\d{1,3})[.](\d{1,3})$/;
		var atom=validChars + '+';
		var word="(" + atom + "|" + quotedUser + ")";
		var userPat=new RegExp("^" + word + "(\\." + word + ")*$");
		var domainPat=new RegExp("^" + atom + "(\\." + atom + ")*$");
		var matchArray=emailStr.match(emailPat);
		if (matchArray == null) {
		   return false;
		}
		var user=matchArray[1];
		var domain=matchArray[2];
		if (user.match(userPat) == null) {
		   return false;
		}
		var IPArray = domain.match(ipDomainPat);
		if (IPArray != null) {
		   for (var i = 1; i <= 4; i++) {
			  if (IPArray[i] > 255) {
				 return false;
			  }
		   }
		   return true;
		}
		var domainArray=domain.match(domainPat);
		if (domainArray == null) {
		   return false;
		}
		var atomPat=new RegExp(atom,"g");
		var domArr=domain.match(atomPat);
		var len=domArr.length;
		if ((domArr[domArr.length-1].length < 2) ||
		   (domArr[domArr.length-1].length > 3)) {
		   return false;
		}
		if (len < 2) {
		   return false;
		}
		return true;
	}
}

var TRSString = new CTRSString();