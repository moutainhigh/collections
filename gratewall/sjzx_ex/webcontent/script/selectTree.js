//ѡ�����
function selectJG(types,idname,textname,jsclrid){
   var page = new pageDefine( "/txn808001.do?type="+types+"&idname="+idname+"&textname="+textname+"&jsclrid="+jsclrid, "����ѡ���б�","win",320,460);
   page.goPage();
}
//ѡ���û�
function selectJGYH(types,idname,textname,jsclrid,jgid){
   var page = new pageDefine( "/txn808002.do?type="+types+"&idname="+idname+"&textname="+textname+"&jsclrid="+jsclrid, "�û�ѡ���б�","win",320,460);
   if(jgid != 'undefined'&& jgid !=''){  
   	page.addValue( jgid,"input-data:jgid_pk" );
   	page.addValue( jgid,"input-data:jgid_fk" );
  }
   page.goPage();
}
//���ط���ֵ
function addvalue(idlist,textlist,idname,textname){
   //document.all(idname).value=idlist;
   //document.all(textname).value=textlist;
   document.getElementById(idname).value=idlist;
   document.getElementById(textname).value=textlist;
}
//��շ���ֵ
function removevalue(idname,textname){
   //document.all(idname).value=idlist;
   //document.all(textname).value=textlist;
   document.getElementById(idname).value="";
   document.getElementById(textname).value="";
}