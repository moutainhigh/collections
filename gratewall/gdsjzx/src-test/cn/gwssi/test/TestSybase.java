package cn.gwssi.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestSybase {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println(getTeacherList("上海隽仁投资中心（有限合伙）(310230000470992);深圳市和景资产管理有限责任公司(440301105732270);于光(440102197010266518);李红(210204196703275805);李军(4"));
		System.out.println("12345678".substring(1, 5));
		//connecttable();
		System.out.println("310230000470992".replaceAll("300", "(("));
		
	}
	
	public static List<String> getTeacherList(String managers){
        List<String> ls=new ArrayList<String>();
        Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
        Matcher matcher = pattern.matcher(managers);
        while(matcher.find()){
        	String oldStri=matcher.group();
        	if(oldStri.length()==15||oldStri.length()==18){
        		managers.replaceAll(oldStri, oldStri.substring(0,7)+"****"+oldStri.substring(15,oldStri.length()));
        	}//441424198706012233
            ls.add(managers);
        }
        System.out.println(managers);
        return ls;
    }

	private static List connecttable() throws ClassNotFoundException, SQLException {
		Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//com.sybase.jdbc2.jdbc.SybDriver
		String url ="jdbc:sybase:Tds:10.1.1.134:5000/sjfx?charset=cp936"; 
		Connection conn= DriverManager.getConnection(url, "sa","gwssi@gdaic"); 
		Statement stmt=conn.createStatement(); 
		String sql="";//select * from GZ_DM_BGQ
		//sql="insert into GZ_DM_BGQ values('20160010','1','4','2016年01月','','2016年1月','','1','2016-01-01','2016-01-31')";
		//sql="insert into GZ_DM_BGQ values('20160000','1','1','2016年','2016','','','1','2016-01-01','2016-12-31')";
		//  sql="insert into GZ_DM_BGQ values('20160020','1','4','2016年02月','','2016年2月','','1','2016-02-01','2016-01-29')";
		 // sql="insert into GZ_DM_BGQ values('20160030','1','4','2016年03月','','2016年3月','','1','2016-03-01','2016-03-31')";
		  // sql="insert into GZ_DM_BGQ values('20160040','1','4','2016年04月','','2016年4月','','1','2016-04-01','2016-04-30')";
		  //  sql="insert into GZ_DM_BGQ values('20160050','1','4','2016年05月','','2016年5月','','1','2016-05-01','2016-05-31')";
		  //   sql="insert into GZ_DM_BGQ values('20160060','1','4','2016年06月','','2016年6月','','1','2016-06-01','2016-06-30')";
		  //sql="insert into GZ_DM_BGQ values('20160070','1','4','2016年07月','','2016年7月','','1','2016-07-01','2016-07-31')";
		  //sql="insert into GZ_DM_BGQ values('20160080','1','4','2016年08月','','2016年8月','','1','2016-08-01','2016-08-31')";
		   //sql="insert into GZ_DM_BGQ values('20160090','1','4','2016年09月','','2016年9月','','1','2016-09-01','2016-09-30')";
		  //  sql="insert into GZ_DM_BGQ values('20160100','1','4','2016年10月','','2016年10月','','1','2016-10-01','2016-10-31')";
		 //sql="insert into GZ_DM_BGQ values('20160110','1','4','2016年11月','','2016年11月','','1','2016-11-01','2016-11-30')";
		    sql="insert into GZ_DM_BGQ values('20163000','1','3','2016年3季度','','','','1','2016-07-01','2016-09-30')";

		int i = stmt.executeUpdate(sql);
		System.out.println(">>>>"+i);
		conn.commit();
		stmt.close();
		conn.close();
		//ResultSet rs=stmt.executeQuery(sql);
		List l = new ArrayList();
		/*while(rs.next()) {
			l.add(rs.getString("bgq_dm"));
			System.out.println(">>>>"+rs.getString("bgq_dm"));
		}
		System.out.println(">>>>"+l.size());*/
		return l;
	}
	
	private static List connecttable12() throws ClassNotFoundException, SQLException {
		Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//com.sybase.jdbc2.jdbc.SybDriver
		String url ="jdbc:sybase:Tds:10.1.2.110:7220/datacenter?charset=cp936"; 
		Connection conn= DriverManager.getConnection(url, "sa","123456"); 
		Statement stmt=conn.createStatement(); 
		String sql="select b.GQCZXXID,b.Pledgor,b.BLicType,b.BLicNO,b.CerType,b.CerNO,b.PledAmUnit,b.ImpAm,b.ImpOrg,b.ImpOrgTel,b.EntName,b.RegNO,convert(datetime,b.applyDate) applyDate,convert(datetime,b.approveDate) approveDate,b.regStatus,b.PriPID,(select value from T_DM_GSGLJDM where code=b.RegOrg) RegOrg,b.stockRegisterNo,b.TIMESTAMP,b.historyInfoID,b.rescindOpinion,b.stockPledgeHostExclusiveID,b.ZQR_BLicType ZQRBLicType,b.ZQR_BLicNO ZQRBLicNO,b.ZQR_CerType zqrcertype,b.ZQR_CerNO zqrcerno from T_SCZT_GQCZXX b";
		ResultSet rs=stmt.executeQuery(sql);
		List l = new ArrayList();
		while(rs.next()) {
			l.add(rs.getString("name"));
		}
		return l;
	}
}
