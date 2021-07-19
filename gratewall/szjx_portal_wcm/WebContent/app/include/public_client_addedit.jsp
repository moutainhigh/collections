<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></script>
<%=currRequestHelper.toTRSRequestParam()%>

<!--Form 数据有效性的校验 BEGIN-->
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/TRSBase.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSString.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSValidator.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSValidator_res_default.js"></SCRIPT>
<!--Form 数据有效性的校验 END-->

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObjHelper.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObj.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMAction.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></script>

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></script>

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSBitsValue.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMDialogHead.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
function unlockObj(_nObjId, _nObjType){
	var oTRSAction = new CTRSAction("../include/object_unlock.jsp");
	oTRSAction.setParameter("ObjId", _nObjId);
	oTRSAction.setParameter("ObjType", _nObjType);
	oTRSAction.doXMLHttpAction();
	return;
}

function unlock(_nObjId, _nObjType){
	if(_nObjId>0) {
		unlockObj(_nObjId, _nObjType);
	}
}
</SCRIPT>