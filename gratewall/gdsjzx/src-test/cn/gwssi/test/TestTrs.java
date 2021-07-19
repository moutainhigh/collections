package cn.gwssi.test;

import cn.gwssi.resource.TrsConnectionUtil;
import com.trs.client.*;

public class TestTrs {
	
	public static void main(String[] args) throws Exception {
		//test1();
		//test2();
		//test3();
		test15();
		  //test7();
		//test6();
		//test9();
		//test11();
	}
	public static void test1() throws  Exception{
		  TRSConnection conn = null;
	        TRSResultSet  rs   = null;
	        try
	        {
	                conn = TrsConnectionUtil.GetTrsConnection();
	                conn.connect("10.1.2.117", "8888", "system", "dc2trs20151125117", "T10");

	                rs = conn.executeSelect("demo2", "中国", false);
	                rs.moveTo(1,2);   
	                System.out.println(rs.getString("正文"));
	                System.out.println("rs.getColumnMultiValue"+rs.getColumnMultiValue(1));
	                
	                String[] words = conn.getSegmentWords("航空器类别级别型别仪表等级为依据的上大声等等生娃儿道", true,true,0);
	                for(String word:words){
	                	System.out.println(word);
	                }
	                
	                TRSColProperty col = conn.getStatCol("demo3", "标题");
	                System.out.println(col.strColumn + ":" + col.IndexMinus+"字段索引存放的实际路径:"+col.strIndexPath);

	                
	        }
	        catch (TRSException e)
	        {
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {
	                if (rs != null) rs.close();
	                rs = null;
	                if (conn != null) conn.close();
	                conn = null;
	        }

	}

	public static void test2(){
		
		 TRSConnection con = null;
		 try
		 {
		     con = new TRSConnection();
		     con.connect("10.1.2.117", "8888", "system", "dc2trs20151125117", "T10");
		             
		     TRSSystemInfo aSysInfo[] = con.getSystemInfos(0);
		     System.out.println(aSysInfo.length);
		     for (int i = 0; i < aSysInfo.length; i++)
		     {
		         System.out.println("OSLabel   = "+aSysInfo[i].OSLabel);
		         
		         TRSDeviceInfo aDiskInfo[] = aSysInfo[i].getGlobalDisks();
		         if (aDiskInfo != null) for (int j = 0; j < aDiskInfo.length; j++)
		         {
		             System.out.println(aDiskInfo[j].Name + ":" + aDiskInfo[j].Features);
		             System.out.println(aDiskInfo[j].AvailSize + "M/" + aDiskInfo[j].TotalSize + "M");
		         }
		     
		         TRSDeviceInfo aPathInfo[] = aSysInfo[i].getSystemPaths();
		         if (aPathInfo != null) for (int j = 0; j < aPathInfo.length; j++)
		         {
		             System.out.println(aPathInfo[j].Name + ":" + aPathInfo[j].Features);
		             System.out.println(aPathInfo[j].AvailSize + "M/" + aPathInfo[j].TotalSize + "M");
		         }
		     }
		 }
		 catch (TRSException e)
		 {
		     System.out.println("ErrorCode: " + e.getErrorCode());
		     System.out.println("ErrorString: " + e.getErrorString());
		 }
		 finally
		 {
		     if (con != null) con.close();
		     con = null;
		 }

	}
	
	public static void test3(){
		 TRSConnection conn = null;
	        try
	        {
	                conn = new TRSConnection();
	                conn.connect("10.1.2.117", "8888", "system", "dc2trs20151125117", "T10");            
	      
	                TRSView[] view = conn.getViews("*", TRSConstant.TCM_ALLPRIV);
	                for (int i = 0; i < view.length; i++)
	                        System.out.println(view[i].getFullName());
	        }
	        catch(TRSException e)
	        {
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {            
	                if (conn != null) conn.close();         
	                        conn = null;
	        }
	}
	public static void test4(){
		  TRSConnection conn = null;
	        TRSResultSet  rs   = null;
	        try
	        {
	                conn = new TRSConnection();
	                conn.connect("10.1.2.117", "8888", "system", "dc2trs20151125117", "T10"); 

	                rs = conn.executeSelectWord("demo3", "system", "正文=中%");
	                for (int i = 0; i < 10 && i < rs.getRecordCount(); i++)
	                {
	                        rs.moveTo(0, i);
	                        System.out.println("总命中词数：" + rs.getRecordCount());
	                        System.out.println("当前索引词为：" + rs.getIndexWord());
	                        System.out.println("当前索引词得词频数为：" + rs.getIndexWordFrequence());
	                        System.out.println("当前索引词得命中数为：" + rs.getIndexWordHitNum());
	                        System.out.println("当前索引词相对于主题词的深度为：" + rs.getIndexWordIndentNum());
	                }
	        }
	        catch (TRSException e)
	        {
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {
	                if (rs != null) rs.close();
	                rs = null;
	                if (conn != null) conn.close();
	                conn = null;
	        }

	}
	public static void test5(){
		 TRSConnection conn = null;
	        TRSResultSet  rs   = null;
	        try
	        {
	                conn = new TRSConnection();
	                conn.connect("10.1.2.117", "8888", "system", "dc2trs20151125117", "T10"); 

	                rs = conn.executeSelect("demo2", "中国", false);
	                rs.moveTo(0, 2);
	                System.out.println("二进制对象数目：" + rs.getBinaryObjectCount("图像"));
	        }
	        catch (TRSException e)
	        {
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {
	                if (rs != null) rs.close();
	                rs = null;
	                if (conn != null) conn.close();
	                conn = null;
	        }

	}
	public static void test6(){
		  TRSConnection conn = null;
	        TRSResultSet rs = null;
	        try
	        {
	                conn = new TRSConnection();
	                conn.connect("10.1.2.117", "8888", "system", "dc2trs20151125117", "T10");          
	                
	                rs = conn.executeSelect("demo3", "我是", "", "", "", 0, TRSConstant.TCE_ROWCOL, false);
	                rs.moveFirst();
	                TRSHitPoint[] hitPoint = rs.getHitPoints("正文");
	                for (int i = 0; i < hitPoint.length; i++)
	                {
	                        System.out.println(hitPoint[i].iBeginRow+"  1");
	                        System.out.println(hitPoint[i].iBeginCol+"  2");
	                        System.out.println(hitPoint[i].iEndRow+"  3");
	                        System.out.println(hitPoint[i].iEndCol+"  4");
	                }
	        }
	        catch (TRSException e)
	        {
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {
	                if (rs != null) rs.close();
	                rs = null;
	                if (conn != null) conn.close();
	                conn = null;
	        }
	 
	}
	
	
	public static void test7(){
		
		TRSConnection conn = null;
        try
        {
                conn = new TRSConnection();
                conn.connect("localhost", "8888", "system", "manager", "T10");          
                
                TRSDataBase[] db = conn.getDataBases("demo3");
                
                TRSIndex[] indexes = db[0].getIndexes();
                
                String comment = db[0].getComment();
                String objectTypeName = db[0].getObjectTypeName();
                System.out.println(comment+"====="+objectTypeName);
                for (int i = 0; i < indexes.length; i++)
                {
                        System.out.println(indexes[i].strColumn);
                        System.out.println(indexes[i].iDivRecordID);
                        System.out.println(indexes[i].iMaxRecordID);
                        System.out.println(indexes[i].iColType);
                }
        }
        catch (TRSException e)
        {
                System.out.println("ErrorCode: " + e.getErrorCode());
                System.out.println("ErrorString: " + e.getErrorString());
        }
        finally
        {
                if (conn != null) conn.close();
                conn = null;
        }
 
	}
	
	public static void test10(){}
	public static void test8(){
		   TRSConnection conn = null;
	        try
	        {
	                conn = new TRSConnection();
	                conn.connect("localhost", "8888", "system", "manager", "T10");
	                
	                TRSView[] view = conn.getViews("demo4");
	                TRSViewColumn[] cols = view[0].getColumns();
	                TRSViewColumn[] columns = view[0].getColumns("版名");
	                for(int j=0;j<columns.length;j++){
	                	System.out.println("sssss"+columns[j].getComment());
	                	
	                }
	                for (int i = 0; i < cols.length; i++)
	                {
	                        System.out.println(cols[i].getBaseCols());
	                        System.out.println("..."+cols[i].getTypeName());
	                }
	        }
	        catch (TRSException e)
	        {
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {
	                if (conn != null) conn.close();
	                conn = null;
	        }

		
	}
	public static void test9(){
		TRSConnection conn = null;
        try
        {
                conn = new TRSConnection();
                conn.connect("localhost", "8888", "system", "manager", "T10");          
                
                TRSDataBase[] db = conn.getDataBases("demo3");
                
                //打印database的属性。key代表key，list 表示key和value
                //value=db[0].getProperty(key);
                String pro= db[0].getProperty("FIRSTTEXTCOL");
                System.out.println(pro);
                
                String[] list = db[0].list();
                for (int i = 0; i < list.length; i++)
                        System.out.println("list-----"+list[i]);    
                System.out.println("==="+list[1]);
                String[] keys = db[0].keys();
                for (int i = 0; i < keys.length; i++)
                        System.out.println("key-----"+keys[i]);                    

        }
        catch (TRSException e)
        {
                System.out.println("ErrorCode: " + e.getErrorCode());
                System.out.println("ErrorString: " + e.getErrorString());
        }
        finally
        {
                if (conn != null) conn.close();
                conn = null;
        }

		
	}
	public static void test11(){
		
		 /*TRSConnection conn = null;
	        try
	        {
	                conn = new TRSConnection();
	                conn.connect("localhost", "8888", "system", "manager", "T10");          
	                
	                TRSDataBaseColumn[] trsDatabaseColumn = new TRSDataBaseColumn[3];
	                trsDatabaseColumn[0] = new TRSDataBaseColumn("demo7", "名称");
	                
	                //trsDatabaseColumn[0].setProperty("名称", "我是名称");
	                //trsDatabaseColumn[0].setIntProperty(arg0, arg1)
	                
	                trsDatabaseColumn[0].setIntProperty("TYPE", TRSConstant.TCE_CHAR);
	                trsDatabaseColumn[1] = new TRSDataBaseColumn("demo7", "口令");
	                trsDatabaseColumn[1].setIntProperty("TYPE", TRSConstant.TCE_CHAR);
	                trsDatabaseColumn[2] = new TRSDataBaseColumn("demo7", "邮箱");
	                trsDatabaseColumn[2].setIntProperty("TYPE", TRSConstant.TCE_CHAR);
	       
	                TRSDataBase trsDatabase = new TRSDataBase(conn, "system.demo7");
	                trsDatabase.addColumn(trsDatabaseColumn);
	                trsDatabase.create();
	                String strValues="名称=刘忠厚\n口令=CN_2151608Y\n邮箱=93202243";
	                
	                conn.setMaintOptions('\n', "", "", 0, 0); //设置维护记录的选项.   
	                conn.executeInsert("demo7", "system", strValues);
	        }
	        catch (TRSException e)
	        {
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {
	                if (conn != null) conn.close();
	                conn = null;
	        }
*/
		
		 TRSConnection conn = null;
	        try
	        {
	                conn = new TRSConnection();
	                conn.connect("localhost", "8888", "system", "manager", "T10");          
	                
	                TRSDataBaseColumn[] trsDatabaseColumn = new TRSDataBaseColumn[2];
	                trsDatabaseColumn[0] = new TRSDataBaseColumn("demo6", "名称");
	                trsDatabaseColumn[0].setProperty("COMMENT", "author");
	               // trsDatabaseColumn[0].setIntProperty("TYPE", TRSConstant.TCE_NUMBER);
	                trsDatabaseColumn[1] = new TRSDataBaseColumn("demo6", "邮箱");
	                trsDatabaseColumn[1].setProperty("COMMENT", "title");
	       
	                TRSDataBase[] trsDataBase = conn.getDataBases ("demo6");
	                trsDataBase[0].alterColumn(trsDatabaseColumn);//修改数据库字段。
	              System.out.println(  trsDataBase[0].updateAlter());//修改生效。
	        }
	        catch (TRSException e)
	        {
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {
	                if (conn != null) conn.close();
	                conn = null;
	        }

		
	}
	public static void test12(){
		 TRSConnection conn = null;
	        try
	        {
	                conn = new TRSConnection();
	                conn.connect("localhost", "8888", "system", "manager", "T10");          
	                
	                conn.setMaintOptions('\b', "", "", 0, 0); //设置维护记录的选项.                  
	                String strValue = "名称=刘忠厚\b口令=CN_2151608Y\b邮箱=93202243";
	                int iInsertNum = conn.executeInsert("Demo6", "system", strValue);
	            System.out.println("插入记录的物理记录号为:" + iInsertNum);
	        }
	        catch (TRSException e)
	        {
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {
	                if (conn != null) conn.close();
	                conn = null;
	        }

		
	}
	
	public static void test13(){
		 TRSConnection conn = null;
	        TRSResultSet  rs   = null;
	        try
	        {
	                conn = new TRSConnection();
	                conn.connect("localhost", "8888", "system", "manager", "T10");

	                rs = conn.executeSelect("demo6", "", "RELEVANCE", "", "", 0, TRSConstant.TCE_OFFSET, false);
	                rs.moveFirst();
	                rs.setString("邮箱", "标题名称");
	                rs.setString("名称","赵云");
	                System.out.println("插入记录的物理记录号:" + rs.insert());
	        }
	        catch (TRSException e)
	        {
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {
	                if (rs != null) rs.close();
	                rs = null;
	                if (conn != null) conn.close();
	                conn = null;
	        }

	}
	
	
	public static void test14(){
		 TRSConnection conn = null;
	        TRSResultSet  rs   = null;
	        try
	        {
	                conn = new TRSConnection();
	                conn.connect("10.1.2.117", "8888", "system", "dc2trs20151125117", "T10");

	                rs = conn.executeSelect("demo3", "正文=中国", "", "", "正文", 0, TRSConstant.TCE_OFFSET, false);
	               // rs = conn.executeSelect("demo3", "中国", "", "", "", 0, TRSConstant.TCE_ROWCOL, false);

	                /*TRSHitPoint[] hitPoint = rs.getHitPoints("正文");
	                System.out.println(hitPoint.length);
	                System.out.println(hitPoint[0].iBeginRow);
                    System.out.println(hitPoint[0].iBeginCol);
                    System.out.println(hitPoint[0].iEndRow);
                    System.out.println(hitPoint[0].iEndCol);*/
	                for (int i = 0; i < 1 && i < rs.getRecordCount(); i++)
	            	{
	                	rs.moveTo(0, i);
	                	TRSHitPoint[] hitPoint = rs.getHitPoints("正文");
	                	System.out.println(hitPoint[0].iCStart);
	            		System.out.println("第" + i + "条记录");
	            		String s =rs.getString("正文", "red");
	            		s=s.substring(0, 202);
	            		System.out.println(s.length());
	            		/*System.out.println(rs.getString("日期"));
	            		System.out.println(rs.getString("版次"));
	            		System.out.println(rs.getString("作者"));
	            		System.out.println(rs.getString("标题", "red"));*/
	            		System.out.println("数据"+rs.getString("正文", "red"));
	            		System.out.println("数据==》"+rs.getStringWithCutsize("正文", 1000, "red"));//getStringWithHtmlVal(iColNo, iCutsize, strHitColor, bWrapLine);
	            		//System.out.println("数据==1》"+rs.getStringWithHtmlVal("正文", 1000, "red",true));
	            		//System.out.println("数据==1》"+rs.getStringWithHtmlVal("正文", 100, "red",false));
	            	}

	        }
	        catch (TRSException e)
	        {
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {
	                if (rs != null) rs.close();
	                rs = null;
	                if (conn != null) conn.close();
	                conn = null;
	        }

	}
	
	
	public static void test15(){
		String str = "众望通";
		 TRSConnection conn = null;
	        TRSResultSet  rs   = null;
	        try
	        {
	                conn = new TRSConnection();
	                conn.connect("10.1.2.117", "8888", "system", "dc2trs20151125117", "T10");

	                rs = conn.executeSelect("sczt", "bi=include("+str+",1)", "", "", "entname;regno;uniscid;entstate;enttype;lerep;estdate;industryphy;industryco,regorg;inv;opstate;opscoandform;opscope;dom;bgsx", 0, TRSConstant.TCE_OFFSET, false);
	               // rs = conn.executeSelect("demo3", "中国", "", "", "", 0, TRSConstant.TCE_ROWCOL, false);

	                /*TRSHitPoint[] hitPoint = rs.getHitPoints("正文");
	                System.out.println(hitPoint.length);
	                System.out.println(hitPoint[0].iBeginRow);
                   System.out.println(hitPoint[0].iBeginCol);
                   System.out.println(hitPoint[0].iEndRow);
                   System.out.println(hitPoint[0].iEndCol);*/
	                for (int i = 0; i < 1 && i < rs.getRecordCount(); i++)
	            	{
	                	rs.moveTo(0, i);
	                	/*TRSHitPoint[] hitPoint = rs.getHitPoints("正文");
	                	System.out.println(hitPoint[0].iCStart);
	            		System.out.println("第" + i + "条记录");
	            		String s =rs.getString("正文", "red");
	            		s=s.substring(0, 202);
	            		System.out.println(s.length());*/
	            		/*System.out.println(rs.getString("日期"));
	            		System.out.println(rs.getString("版次"));
	            		System.out.println(rs.getString("作者"));
	            		System.out.println(rs.getString("标题", "red"));*/
	            		System.out.println("数据"+rs.getString("bgsx", "red"));
	            		System.out.println("数据==》"+rs.getStringWithCutsize("bgsx", 1000, "red"));//getStringWithHtmlVal(iColNo, iCutsize, strHitColor, bWrapLine);
	            		//System.out.println("数据==1》"+rs.getStringWithHtmlVal("正文", 1000, "red",true));
	            		//System.out.println("数据==1》"+rs.getStringWithHtmlVal("正文", 100, "red",false));
	            	}
	        }catch (TRSException e){
	                System.out.println("ErrorCode: " + e.getErrorCode());
	                System.out.println("ErrorString: " + e.getErrorString());
	        }
	        finally
	        {
	                if (rs != null) rs.close();
	                rs = null;
	                if (conn != null) conn.close();
	                conn = null;
	        }

	}
}
