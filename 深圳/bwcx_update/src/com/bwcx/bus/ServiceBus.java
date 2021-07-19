package com.bwcx.bus;

public class ServiceBus {

	
	public static String insertSQL () {
		
		
		String sql   = 
				"insert into dc_document\n" +
						"(docid,\n" + 
						"title,\n" + 
						"contentmeta,\n" + 
						"--editor\n" + 
						"--contents\n" + 
						"--savepath\n" + 
						"filename,\n" + 
						"--thumburl\n" + 
						"--attachfilesavepath\n" + 
						"channelid,\n" + 
						"pubdate,\n" + 
						"ishavethumb,\n" + 
						"--tranformecontents\n" + 
						"--contenttoed\n" + 
						"ispuborsave,\n" + 
						"edittime,\n" + 
						"contentsplaintext\n" + 
						")\n" + 
						"select sys_guid(),\n" + 
						"       t.doctitle,\n" + 
						"       t.doctitle,\n" + 
						"       t2.appfile,\n" + 
						"       '1',\n" + 
						"       t.crtime,\n" + 
						"       case\n" + 
						"         when t2.appfile is not null then\n" + 
						"          '1'\n" + 
						"         else\n" + 
						"          '0'\n" + 
						"       end,\n" + 
						"       '1',\n" + 
						"       t.docpubtime,\n" + 
						"       t.doccontent\n" + 
						"  from SZW_WCM.wcmdocument t, SZW_WCM.wcmappendix t2\n" + 
						" where t.doctitle like '%不忘初心%'\n" + 
						"   and (t2.appflag = '20' or t2.appflag is null)\n" + 
						"   and t.docid = t2.appdocid(+)\n" + 
						"   and t.docstatus = 10\n" + 
						"   and t.doceditor is not null\n" + 
						"   and t.docchannel in ('74','83')\n" + 
						"   and not exists (SELECT 1 FROM dc_document s where s.channelid = '1' and trim(s.title) = trim(t.doctitle))";

		
		return sql;
		
	}
	
	
	
	public static String updateSQL() {
		
		String sql = " update dc_document s set s.thumburl = 'pic/'||s.docid||'.do' where s.filename is not null and s.ishavethumb = '1' and s.thumburl is null ";
		
		return sql;
	}
	
	
	public static String getThumbPic() {
		String sql = "select filename  from  dc_document s  where s.filename is not null and s.ishavethumb = '1' and s.thumburl is null";
		return sql;
	}
	
	
	
	
}
