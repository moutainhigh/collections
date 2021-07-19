package cn.gwssi.test;

import cn.gwssi.resource.Conts;
import cn.gwssi.resource.TrsConnectionUtil;
import com.trs.client.TRSConnection;
import com.trs.client.TRSConstant;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Test {

	/**
	 * @param args
	 * @throws TRSException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * <!-- 
◎ direction表示滚动的方向，值可以是left，right，up，down，默认为left 
◎ behavior表示滚动的方式，值可以是scroll（连续滚动）slide（滑动一次）alternate（往返滚动） 
◎ loop表示循环的次数，值是正整数，默认为无限循环 
◎ scrollamount表示运动速度，值是正整数，默认为6 
◎ scrolldelay表示停顿时间，值是正整数，默认为0，单位似乎是毫秒 
◎ align表示元素的垂直对齐方式，值可以是top，middle，bottom，默认为middle 
◎ bgcolor表示运动区域的背景色，值是16进制的RGB颜色，默认为白色 
◎ height、width表示运动区域的高度和宽度，值是正整数（单位是像素）或百分数，默认width=100% height为标签内元素的高度 
◎ hspace、vspace表示元素到区域边界的水平距离和垂直距离，值是正整数，单位是像素。
◎ onmouseover=this.stop() onmouseout=this.start()表示当鼠标以上区域的时候滚动停止，当鼠标移开的时候又继续滚动。
 -->
	 */
	/**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static Map<String,String> readFileByLines(String fileName) {
    	Map<String,String> map = new HashMap<String,String>();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            String[] temp = null;
            int line = 1;
            
            //String str="hello            song";
            Pattern p = Pattern.compile("\\s+");
            //Matcher m = p.matcher(str);
            /*w= m.replaceAll(" ");
            System.out.println(w);*/
            String str="";
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
               // System.out.println("line " + line + ": " + tempString);
                Matcher m = p.matcher(tempString);
                str= m.replaceAll(" ");
                temp =  str.split(" ");
               // System.out.println("line " + temp[1] + ": " + temp[0]);
                map.put(temp[1].toLowerCase(), temp[0]);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return map;
    }
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
			/*Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//com.sybase.jdbc2.jdbc.SybDriver
				String url ="jdbc:sybase:Tds:10.1.2.110:7220/datacenter?charset=cp936"; 
				//jdbc:sybase:Tds:10.1.2.110:7220/datacenter?charset=cp936
				//jdbc:sybase:Tds:10.1.2.110:7220
				int i=0;
				String sql="select * from dbo.t_sczt_scztjbxx";
				long startTime = new Date().getTime();
				conn = DriverManager.getConnection(url, "sa","123456"); 
				System.out.println(new Date().getTime()-startTime);//1 483
				stmt = conn.createStatement(); 
				System.out.println(new Date().getTime()-startTime);//2 483
				rs = stmt.executeQuery(sql); 
				System.out.println(new Date().getTime()-startTime);//3 547
				while(rs.next()) {
					System.out.println(new Date().getTime()-startTime);//4 547
					System.out.println(rs.getString(1));
					System.out.println(new Date().getTime()-startTime);//5 547
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					rs.close();
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("=============");
				//sybase();
			}*/
		/*List<String> l = connecttable();
		for(String s : l){
			connect(s,true);
		}*/
		//t_sczt_bgxx --100/T_SCZT_QRXX--201/T_SCZT_QCXX--202 /T_SCZT_QSXX--203/T_SCZT_ZDXXX--204
		//T_SCZT_GQDJXX--205
		//connectColumn("laolai","259");
		GetconnectColumn("T_JYYCML_QYGTNZ","");
		/*T_QTBM_XZXK
		
		T_SB_DLRXX
		T_SB_GYRXX
		T_SB_SBSQRXX
		T_SB_SPXX
		T_SB_SBJBXX*/
	}
	
	/**
	 * 获取表字段UI
	 * @param tableName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void GetconnectColumn(String tableName,String type) throws ClassNotFoundException, SQLException {
		Class.forName("com.sybase.jdbc3.jdbc.SybDriver");
		String url ="jdbc:sybase:Tds:10.1.2.110:7220/datacenter?charset=cp936"; 
		Connection conn= DriverManager.getConnection(url, "sa","123456"); 
		Statement stmt=conn.createStatement(); 
		String sql="select name from syscolumns where id in(select id from sysobjects where type='U' and name='"+tableName+"')";
		ResultSet rs=stmt.executeQuery(sql); 
		int i =0;
		Map<String,String> map = readFileByLines("E:\\workspace\\gdsjzx\\src-test\\cn\\gwssi\\test\\testFile.txt");
		while(rs.next()) {
			i++;
			String key = rs.getString("name").toLowerCase();
			String value = map.get(key);
			if(!StringUtils.isNotBlank(value)){
				value = "变";
			}
			StringBuffer sbf = new StringBuffer("<div name='"+key+"' text=\""+value+"\" visible=\"false\" textalign=\"center\"  width=\"10%\"></div>");
			System.out.println(sbf.toString());
		}
	}
		//获取表名
		private static List connecttable() throws ClassNotFoundException, SQLException {
			Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//com.sybase.jdbc2.jdbc.SybDriver
			String url ="jdbc:sybase:Tds:10.1.2.110:7220/datacenter?charset=cp936"; 
			Connection conn= DriverManager.getConnection(url, "sa","123456"); 
			Statement stmt=conn.createStatement(); 
			String sql="select name from sysobjects where type='U' order by name";
			ResultSet rs=stmt.executeQuery(sql);
			List l = new ArrayList();
			while(rs.next()) {
				l.add(rs.getString("name"));
			}
			return l;
		}
		/**
		 * 获取表字段
		 * @param tableName
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		private static void connect(String tableName,boolean flag) throws ClassNotFoundException, SQLException {
			Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//com.sybase.jdbc2.jdbc.SybDriver
			String url ="jdbc:sybase:Tds:10.1.2.110:7220/datacenter?charset=cp936"; 
			//jdbc:sybase:Tds:10.1.2.110:7220/datacenter?charset=cp936
			//jdbc:sybase:Tds:10.1.2.110:7220
			Connection conn= DriverManager.getConnection(url, "sa","123456"); 
			Statement stmt=conn.createStatement(); 
			//String sql="select * from dbo.T_SCZT_QRXX where PriPID = 'e2d36701-0130-1000-e000-24930a0c0115'"; 
			String sql="select name from syscolumns where id in(select id from sysobjects where type='U' and name='"+tableName+"')";
			ResultSet rs=stmt.executeQuery(sql); 
			//System.out.println("表名："+tableName);
			StringBuffer sbf = new StringBuffer();
			while(rs.next()) {
				//sbf.append("a."+rs.getString("name").toLowerCase());
				if(flag){
					sbf.append("a."+rs.getString("name").toLowerCase());
				}else{
					sbf.append(""+rs.getString("name").toLowerCase());
				}
				sbf.append(",");
			}
			if(flag){
				System.out.println(tableName + "=select "+sbf.toString().substring(0, sbf.toString().length()-1)+ " from "+ tableName.toLowerCase() +" a ");
			}else{
				System.out.println(tableName + "=select "+sbf.toString().substring(0, sbf.toString().length()-1)+ " from "+ tableName.toLowerCase());
			}
		}
		
		
		/**
		 * 获取表字段
		 * @param tableName
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		private static void connectColumn(String tableName,String type) throws ClassNotFoundException, SQLException {
			Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//com.sybase.jdbc2.jdbc.SybDriver
			String url ="jdbc:sybase:Tds:10.1.2.110:7220/datacenter?charset=cp936"; 
			Connection conn= DriverManager.getConnection(url, "sa","123456"); 
			Statement stmt=conn.createStatement(); 
			String sql="select name from syscolumns where id in(select id from sysobjects where type='U' and name='"+tableName+"')";
			ResultSet rs=stmt.executeQuery(sql); 
			//StringBuffer sbf = new StringBuffer("");
			//insert into T_SCZT_JBXXINHTML(fieldcn,fieldeng,type,sort,state)values('','100','','1')
			int i =0;
			while(rs.next()) {
				i++;
				StringBuffer sbf = new StringBuffer("insert into T_SCZT_JBXXINHTML(fieldcn,fieldeng,type,sort,state)values('','"+rs.getString("name").toLowerCase()+"','"+type+"',"+i+",'1')");
				System.out.println(sbf.toString());
				//sbf.append("a."+rs.getString("name").toLowerCase());
				//sbf.append(""+rs.getString("name").toLowerCase());
				//sbf.append(",");
			}
			//System.out.println(tableName + "=select "+sbf.toString().substring(0, sbf.toString().length()-1)+ " from "+ tableName.toLowerCase());
		}
	
	private static void sybase() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//com.sybase.jdbc2.jdbc.SybDriver
			String url ="jdbc:sybase:Tds:10.1.2.110:7220/datacenter?charset=cp936"; 
			String sql="select count(*) from dbo.t_sczt_scztjbxx";
			long startTime = new Date().getTime();
			conn = DriverManager.getConnection(url, "sa","123456"); 
			System.out.println(new Date().getTime()-startTime);//1 483
			pstmt = conn.prepareStatement(sql);
			System.out.println(new Date().getTime()-startTime);//2 483
			rs = pstmt.executeQuery(); 
			System.out.println(new Date().getTime()-startTime);//3 547
			while(rs.next()) {
				System.out.println(new Date().getTime()-startTime);//4 547
				System.out.println(rs.getString(1));
				System.out.println(new Date().getTime()-startTime);//5 547
				break;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static void mytest(String keyword) {
		TRSConnection trscon = null;
		TRSResultSet trsrs = null;
		try {
			trscon = TrsConnectionUtil.GetTrsConnection();
			// 从demo3中检索标题或正文含有"中国"的记录
			/*trsrs = trscon.executeSelect("demo3", "标题=中国 or 正文=中国", "", "",
					"正文", 0, TRSConstant.TCE_OFFSET, false);*/
			//String str = "entname/10,regno/5,uniscid,dom,addr,opscope,industryco,frxx,gjrxx,inv,bgsx,lerep+="+keyword;
			String str = "bi=include("+keyword+",1)";
			long t1=new java.util.Date().getTime();
			trsrs = trscon.executeSelect(Conts.SELTABREG, str, "-enttype;RELEVANCE", "", "", 0,
					TRSConstant.TCE_OFFSET, false);
			long t2=new java.util.Date().getTime();
			System.out.println("trs全文检索耗时："+(t2-t1));
			// 输出记录数
			System.out.println("记录数1:" + trsrs.getRecordCount());

			// 设置概览/细览字段, 提高读取记录的效率
			/*trsrs.setReadOptions("日期;版次;作者;标题", "正文", ";",
					TRSConstant.TCE_OFFSET, 0);*/
			str = "bi=include("+keyword+",1)";
			long t3=new java.util.Date().getTime();
			trsrs = trscon.executeSelect(Conts.SELTABREG, str, "-enttype", "", "", 0,
					TRSConstant.TCE_OFFSET, false);
			long t4=new java.util.Date().getTime();
			System.out.println("trs全文检索耗时："+(t4-t3));
			// 输出记录数
			System.out.println("记录数2:" + trsrs.getRecordCount());
			
			
			str = "bi=include("+keyword+",1)";
			long t5=new java.util.Date().getTime();
			trsrs = trscon.executeSelect(Conts.SELTABREG, str, "RELEVANCE", "", "", 0,
					TRSConstant.TCE_OFFSET, false);
			long t6=new java.util.Date().getTime();
			System.out.println("trs全文检索耗时："+(t6-t5));
			// 输出记录数
			System.out.println("记录数3:" + trsrs.getRecordCount());
			
		} catch (TRSException e) {
			System.out.println("ErrorCode: " + e.getErrorCode());
			System.out.println("ErrorString: " + e.getErrorString());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			TrsConnectionUtil.release(trsrs, trscon);
		}
	}

	private static void testList() {
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("title", "一级1");
		m.put("url", "url1");
		m.put("l", list(4));
		l.add(m);
		m= new HashMap<String,Object>();
		System.out.println(m.get("title"));
		m.put("title", "一级2");
		m.put("url", "url2");
		m.put("l", list(3));
		l.add(m);
		m= new HashMap<String,Object>();
		m.put("title", "一级3");
		m.put("url", "url3");
		m.put("l", list(2));
		l.add(m);
		
		m= new HashMap<String,Object>();
		m.put("title", "一级4");
		m.put("url", "url4");
		m.put("l", list(1));
		l.add(m);
		int c=0;
		System.out.println(l.size());
		l(l,c);
	}

	private static void l(List<Map<String, Object>> l, int c) {
		int t = c;
		++c;
		if(l!=null && l.size()>0){
			for(Map<String,Object> mp : l){
				if(t==0){
					System.out.println("1>>"+mp.get("title"));
					System.out.println("1>>"+mp.get("url"));
					l((List<Map<String, Object>>) mp.get("l"),c);
				}else if(t==1){
					System.out.println("	2>>"+mp.get("title"));
					System.out.println("	2>>"+mp.get("url"));
					l((List<Map<String, Object>>) mp.get("l"),c);
				}else{
					System.out.println("		3>>"+mp.get("title"));
					System.out.println("		3>>"+mp.get("url"));
					l((List<Map<String, Object>>) mp.get("l"),c);
				}
			}
		}
	}

	private static Object list(int i) {
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		Map<String,Object> m = new HashMap<String,Object>();
		if(i==1){
			m.put("title", "二级1");
			m.put("url", "url1");
			m.put("l", list1(1));
			l.add(m);
		}else if(i==2){
			m.put("title", "二级1");
			m.put("url", "url1");
			m.put("l", list1(1));
			l.add(m);
			m= new HashMap<String,Object>();
			
			m.put("title", "二级2");
			m.put("url", "url2");
			m.put("l", list1(2));
			l.add(m);
			
		}else if(i==3){
			m.put("title", "二级1");
			m.put("url", "url1");
			m.put("l", list1(1));
			l.add(m);
			m= new HashMap<String,Object>();
			
			m.put("title", "二级2");
			m.put("url", "url2");
			m.put("l", list1(2));
			l.add(m);
			
			m= new HashMap<String,Object>();
			m.put("title", "二级3");
			m.put("url", "url3");
			m.put("l", list1(3));
			l.add(m);
			
		}else{
			m.put("title", "二级1");
			m.put("url", "url1");
			m.put("l", list1(1));
			l.add(m);
			m= new HashMap<String,Object>();
			
			m.put("title", "二级2");
			m.put("url", "url2");
			m.put("l", list1(2));
			l.add(m);
			
			m= new HashMap<String,Object>();
			m.put("title", "二级3");
			m.put("url", "url3");
			m.put("l", list1(3));
			l.add(m);
			
			m= new HashMap<String,Object>();
			m.put("title", "二级4");
			m.put("url", "url4");
			m.put("l", list1(4));
			l.add(m);
		}
		return l;
	}
	
	private static Object list1(int i) {
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		Map<String,Object> m = new HashMap<String,Object>();
		List lt = new ArrayList();
		if(i==1){
			m.put("title", "三级1");
			m.put("url", "url1");
			m.put("l", lt);
			l.add(m);
		}else if(i==2){
			m.put("title", "三级1");
			m.put("url", "url1");
			m.put("l", lt);
			l.add(m);
			m= new HashMap<String,Object>();
			
			m.put("title", "三级2");
			m.put("url", "url2");
			m.put("l", lt);
			l.add(m);
			
		}else if(i==3){
			m.put("title", "三级1");
			m.put("url", "url1");
			m.put("l", lt);
			l.add(m);
			m= new HashMap<String,Object>();
			
			m.put("title", "三级2");
			m.put("url", "url2");
			m.put("l", lt);
			l.add(m);
			
			m= new HashMap<String,Object>();
			m.put("title", "三级3");
			m.put("url", "url3");
			m.put("l", lt);
			l.add(m);
			
		}else{
			m.put("title", "三级1");
			m.put("url", "url1");
			m.put("l", lt);
			l.add(m);
			m= new HashMap<String,Object>();
			
			m.put("title", "三级2");
			m.put("url", "url2");
			m.put("l", lt);
			l.add(m);
			
			m= new HashMap<String,Object>();
			m.put("title", "三级3");
			m.put("url", "url3");
			m.put("l", lt);
			l.add(m);
			
			m= new HashMap<String,Object>();
			m.put("title", "三级4");
			m.put("url", "url4");
			m.put("l", lt);
			l.add(m);
		}
		return l;
	}

	private static void testConnect() {
		TRSConnection trscon = null;
		TRSResultSet trsrs = null;
		try {
			trscon = TrsConnectionUtil.GetTrsConnection();
			// 从demo3中检索标题或正文含有"中国"的记录
			trsrs = trscon.executeSelect("demo3", "标题=中国 or 正文=中国", "", "",
					"正文", 0, TRSConstant.TCE_OFFSET, false);
			// 输出记录数
			System.out.println("记录数:" + trsrs.getRecordCount());

			// 设置概览/细览字段, 提高读取记录的效率
			trsrs.setReadOptions("日期;版次;作者;标题", "正文", ";",
					TRSConstant.TCE_OFFSET, 0);

			// 输出前20条记录
			for (int i = 0; i < 20 && i < trsrs.getRecordCount(); i++) {
				trsrs.moveTo(0, i);
				System.out.println("第" + i + "条记录");

				System.out.println(trsrs.getString("日期"));
				System.out.println(trsrs.getString("版次"));
				System.out.println(trsrs.getString("作者"));
				System.out.println(trsrs.getString("标题", "red"));
			}
		} catch (TRSException e) {
			System.out.println("ErrorCode: " + e.getErrorCode());
			System.out.println("ErrorString: " + e.getErrorString());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			TrsConnectionUtil.release(trsrs, trscon);
		}
	}

	private static void test1() throws IOException, Exception {
		TRSConnection trscon = TrsConnectionUtil.GetTrsConnection();
		TRSResultSet trsrs = trscon.executeSelect("demo3", "中国 ", "", "", "正文",
				0, TRSConstant.TCE_OFFSET, false);
		// 输出记录数
		System.out.println("记录数:" + trsrs.getRecordCount());

		// 设置概览/细览字段, 提高读取记录的效率
		trsrs.setReadOptions("日期;版次;作者;标题", "正文", ";", TRSConstant.TCE_OFFSET,
				0);
		
		// 输出前20条记录
		/*for (int i = 0; i < 20 && i < trsrs.getRecordCount(); i++)// 分词、分页、参数
		{
			trsrs.moveTo(0, i);
			System.out.println("第" + i + "条记录");

			System.out.println(trsrs.getString("日期"));
			System.out.println(trsrs.getString("版次"));
			System.out.println(trsrs.getString("作者"));
			System.out.println(trsrs.getString("标题", "red"));
		}*/
		/*int iClassNum = trsrs.classResult("版次", "", 0, "", true, false);
        System.out.println("分类的结果数为:" + iClassNum);
        
        for (int i = 0; i < iClassNum; i++)
        {
                ClassInfo classInfo = trsrs.getClassInfo(i);
                System.out.println("类别" + i + "的分类字段值为:" + classInfo.strValue);
                System.out.println("类别" + i + "的总记录数为:" + classInfo.iRecordNum);
                System.out.println("类别" + i + "的有效记录数为:" + classInfo.iValidNum);
        }*/



		TrsConnectionUtil.release(trsrs, trscon);
	}

	private static void test() {
		// TODO Auto-generated method stub
		String sHost = "127.0.0.1";
		String sPort = "8888";
		String sUserName = "system";
		String sPassWord = "manager";
		TRSConnection trscon = null;
		TRSResultSet trsrs = null;

		try {
			// 建立连接
			trscon = new TRSConnection();
			trscon.connect(sHost, sPort, sUserName, sPassWord, "T10");

			// 从demo3中检索标题或正文含有"中国"的记录
			trsrs = trscon.executeSelect("demo3", "中国 ", "", "", "正文", 0,
					TRSConstant.TCE_OFFSET, false);

			// 输出记录数
			System.out.println("记录数:" + trsrs.getRecordCount());

			// 设置概览/细览字段, 提高读取记录的效率
			trsrs.setReadOptions("日期;版次;作者;标题", "正文", ";",
					TRSConstant.TCE_OFFSET, 0);

			// 输出前20条记录
			for (int i = 0; i < 20 && i < trsrs.getRecordCount(); i++)// 分词、分页、参数
			{
				trsrs.moveTo(0, i);
				System.out.println("第" + i + "条记录");

				System.out.println(trsrs.getString("日期"));
				System.out.println(trsrs.getString("版次"));
				System.out.println(trsrs.getString("作者"));
				System.out.println(trsrs.getString("标题", "red"));
			}
		} catch (TRSException ex) {
			// 输出错误信息
			System.out.println(ex.getErrorCode() + ":" + ex.getErrorString());
			ex.printStackTrace();
		} finally {
			// 关闭结果集
			if (trsrs != null)
				trsrs.close();
			trsrs = null;

			// 关闭连接
			if (trscon != null)
				trscon.close();
			trscon = null;
		}
	}

	private static void te() {
		try {
			int i =0,j=10,z=0;
			z = j/i;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return;
		}
	}
}
