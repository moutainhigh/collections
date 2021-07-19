package cn.gwssi.etlschedule;




import java.sql.Connection;
import java.sql.DriverManager;
/**
 * Created by Administrator on 2016/6/17.
 */
public class ETLDataSourceConnect {

    private ETLDataSourceConnect(){

    }


    //获取数据源，根据传入数据参数进行判断，获取那一个数据的数据源
   public static Connection getConnection(String key,String code,String user,String pwd,String jobtype)throws Exception{
     
	   String  url="jdbc:sybase:Tds:10.1.1.115:2110/"+code+"aicbiz?charset=iso_1";
       
       if("nz".equals(jobtype)){
           Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//加载驱动程序类
    	   url="jdbc:sybase:Tds:10.1.1.121:7220/aicmssw?charset=iso_1";
       }if("nb".equals(jobtype)){
           Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//加载驱动程序类
           url="jdbc:sybase:Tds:10.1.1.159:7220/aiceps?charset=iso_1";
       }      
       if("qtgs".equals(jobtype)){
           Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//加载驱动程序类
           url="jdbc:sybase:Tds:10.1.1.159:7220/aiceps?charset=iso_1";
       }if("12315".equals(jobtype)){
           Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//加载驱动程序类
    	   url="jdbc:sybase:Tds:10.1.1.131:5000/gs12315v5?charset=iso_1";
       }
       if("sbzj".equals(jobtype)){
           Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//加载驱动程序类
    	   url="jdbc:sybase:Tds:10.1.2.110:7220/datacenter?charset=iso_1";
       }
       if("sdnb".equals(jobtype)){
           Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//加载驱动程序类
    	   url="jdbc:sybase:Tds:10.1.1.115:2110/sdaiccips?charset=iso_1";
       }
       if("zhnb".equals(jobtype)){
           Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//加载驱动程序类
    	   url="jdbc:sybase:Tds:10.1.1.115:2110/aiceps_zh?charset=iso_1";
       }if("gzsb".equals(jobtype)){
           Class.forName("com.ibm.db2.jcc.DB2Driver");//加载驱动程序类
    	   url="jdbc:db2://172.26.0.11:60000/ZBK";
       }if("szsb".equals(jobtype)){
           Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//加载驱动程序类
           url="jdbc:sqlserver://172.26.0.12:1433;databaseName=RegDB";
       }
       Connection  conn=(Connection) DriverManager.getConnection(url,user,pwd);
       return conn;
    }

    //关闭数据源链接
    public static void closeConn(Connection connection)throws Exception{
        connection.close();
    }


    public static void main(String[] args) throws Exception {
		Connection conn=ETLDataSourceConnect.getConnection("", "", "gwssi", "gwssi0802data", "szsb");
		conn.createStatement().execute("SELECT max(S_EXT_DATATIME) FROM  AGR_GOODS ");
		ETLDataSourceConnect.closeConn(conn);	
	}

}
