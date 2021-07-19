//选择机构
function selectJG(types,idname,textname,jsclrid){
   var page = new pageDefine( "/txn808001.do?type="+types+"&idname="+idname+"&textname="+textname+"&jsclrid="+jsclrid, "机构选择列表","win",320,460);
   page.goPage();
}
//选择用户
function selectJGYH(types,idname,textname,jsclrid,jgid){
   var page = new pageDefine( "/txn808002.do?type="+types+"&idname="+idname+"&textname="+textname+"&jsclrid="+jsclrid, "用户选择列表","win",320,460);
   if(jgid != 'undefined'&& jgid !=''){  
   	page.addValue( jgid,"input-data:jgid_pk" );
   	page.addValue( jgid,"input-data:jgid_fk" );
  }
   page.goPage();
}
//带回返回值
function addvalue(idlist,textlist,idname,textname){
   //document.all(idname).value=idlist;
   //document.all(textname).value=textlist;
   document.getElementById(idname).value=idlist;
   document.getElementById(textname).value=textlist;
}
//清空返回值
function removevalue(idname,textname){
   //document.all(idname).value=idlist;
   //document.all(textname).value=textlist;
   document.getElementById(idname).value="";
   document.getElementById(textname).value="";
}