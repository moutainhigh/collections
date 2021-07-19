package cn.gwssi.report.service;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cn.gwssi.report.model.TCognosReportBO;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.persistence.datasource.DataSourceManager;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class CognosService extends BaseService{

	
	
    //将字符串转换成二进制字符串，以空格相隔
    private synchronized String StrToBinstr(String str) {
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        return result;
    }
    

    //将二进制字符串转换成Unicode字符串
    private synchronized String BinstrToStr(String binStr) {
        String[] tempStr=StrToStrArray(binStr);
        char[] tempChar=new char[tempStr.length];
        for(int i=0;i<tempStr.length;i++) {
            tempChar[i]=BinstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }
    //将二进制字符串格式化成全16位带空格的Binstr
    private synchronized String BinstrToBinstr16(String input){
        StringBuffer output=new StringBuffer();
        String[] tempStr=StrToStrArray(input);
        for(int i=0;i<tempStr.length;i++){
            for(int j=16-tempStr[i].length();j>0;j--)
                output.append('0');
            output.append(tempStr[i]+" ");
        }
        return output.toString();
    }
    //将全16位带空格的Binstr转化成去0前缀的带空格Binstr
    private synchronized String Binstr16ToBinstr(String input){
        StringBuffer output=new StringBuffer();
        String[] tempStr=StrToStrArray(input);
        for(int i=0;i<tempStr.length;i++){
            for(int j=0;j<16;j++){
                if(tempStr[i].charAt(j)=='1'){
                    output.append(tempStr[i].substring(j)+" ");
                    break;
                }
                if(j==15&&tempStr[i].charAt(j)=='0')
                    output.append("0"+" ");
            }
        }
        return output.toString();
    }   
    //二进制字串转化为boolean型数组  输入16位有空格的Binstr
    private synchronized boolean[] Binstr16ToBool(String input){
        String[] tempStr=StrToStrArray(input);
        boolean[] output=new boolean[tempStr.length*16];
        for(int i=0,j=0;i<input.length();i++,j++)
            if(input.charAt(i)=='1')
                output[j]=true;
            else if(input.charAt(i)=='0')
                output[j]=false;
            else
                j--;
        return output;
    }
    //boolean型数组转化为二进制字串  返回带0前缀16位有空格的Binstr
    private  synchronized String BoolToBinstr16(boolean[] input){ 
        StringBuffer output=new StringBuffer();
        for(int i=0;i<input.length;i++){
            if(input[i])
                output.append('1');
            else
                output.append('0');
            if((i+1)%16==0)
                output.append(' ');           
        }
        output.append(' ');
        return output.toString();
    }
    //将二进制字符串转换为char
    private  synchronized char BinstrToChar(String binStr){
        int[] temp=BinstrToIntArray(binStr);
        int sum=0;   
        for(int i=0; i<temp.length;i++){
            sum +=temp[temp.length-1-i]<<i;
        }   
        return (char)sum;
    }
    //将初始二进制字符串转换成字符串数组，以空格相隔
    private synchronized String[] StrToStrArray(String str) {
        return str.split(" ");
    }
    //将二进制字符串转换成int数组
    private  synchronized int[] BinstrToIntArray(String binStr) {       
        char[] temp=binStr.toCharArray();
        int[] result=new int[temp.length];   
        for(int i=0;i<temp.length;i++) {
            result[i]=temp[i]-48;
        }
        return result;
    }
   

	/**将生产的报表存入数据库
	 * @throws OptimusException 
	 * sdk_m_visit
	 * @throws UnsupportedEncodingException 
	 */
	public void saveReport(TCognosReportBO bo,String result) throws OptimusException, UnsupportedEncodingException{
		IPersistenceDAO dao =this.getPersistenceDAO();
		result=this.StrToBinstr(result);
		String sql=" INSERT INTO dbo.T_COGNOS_Report(id, regcode, reportContext, reportType, reportTime, reportParamters, ReportName, mouth, year)  "
				+ "values ('"+UUID.randomUUID()+"', '"+bo.getRegcode()+"', '"+result+"', '"+bo.getReporttype()+"', '"+bo.getReporttime()+"', '"+bo.getReportparamters()+"', '"+bo.getReportname()+"', '"+bo.getMouth()+"', '"+bo.getYear()+"')";
		
		System.out.println(sql);
		dao.execute(sql, null); 
	}
	
	public List<Map> getAllMouthReports(String year,String reportParamters) throws OptimusException{
		IPersistenceDAO dao =this.getPersistenceDAO();
		String sql="  select a.code reporttype ,a.value reportname ,b.code regcode,c.code reportparamters from T_COGNOS_reporttype a, T_DM_BBQH b,T_DM_BBLB c"
				+ "   where c.code='03' and (a.value='内资1表' or a.value='内资2表' or a.value='内资3表' or a.value='个体2表'  or a.value='个体1表' or a.value='个体3表'  or a.value='外资1表'  or a.value='外资2表 '   or   a.value='外资3表'  or a.value='农合1表'   )    and	a.code||b.code||'"+reportParamters+"'   not in (select reportType||regcode||reportParamters from T_COGNOS_report where year='"+year+"'        and reportParamters='"+reportParamters+"') order by b.code  ";
		//    and 
		System.out.println("执行sql==="+sql);
		List<Map> list=dao.queryForList(sql, null);
		
		return list;
	};
	
	
	public void deleteALlByDate(String year ,String reportParamters) throws OptimusException{
		IPersistenceDAO dao =this.getPersistenceDAO();
		String sql="delete from T_COGNOS_REPORT where year='"+year+"' and reportParamters='"+reportParamters+"'";
		System.out.println("执行sql==="+sql);
		dao.execute(sql, null);
	}
	
	
	public Map queryCognosReport(String id) throws SQLException{
		Map map=null;
		Connection conn= DataSourceManager.getConnection("defaultDataSource");
		String sql="select  b.value as regcode,id,year,mouth,reportname,reportContext from T_COGNOS_report a left join T_DM_BBQH b on a.regcode=b.code where a.id='"+id+"'";
		ResultSet rs= conn.createStatement().executeQuery(sql);
		while(rs.next()){
			 map=new HashMap();
			 String res=rs.getString("reportContext");
		     res=this.BinstrToStr(res);
			 map.put("res",res );
			 map.put("regcode", rs.getString("regcode"));
			 map.put("year", rs.getString("year"));
			 map.put("mouth", rs.getString("mouth"));
			 map.put("reportname", rs.getString("reportname"));
		}
		

		if(rs!=null){
			rs.close();
		}
		if(conn!=null){
			conn.close();
		}
		return map;
	}


	public Map queryCognosReport1() throws SQLException{
		Map map=null;
		Connection conn= DataSourceManager.getConnection("defaultDataSource");
		String sql="select  top 1 b.value as regcode,id,year,mouth,reportname,reportContext from T_COGNOS_report a left join T_DM_BBQH b on a.regcode=b.code ";
		ResultSet rs= conn.createStatement().executeQuery(sql);
		while(rs.next()){
			 map=new HashMap();
			 String res=rs.getString("reportContext");
		     res=this.BinstrToStr(res);
			 map.put("res",res );
			 map.put("regcode", rs.getString("regcode"));
			 map.put("year", rs.getString("year"));
			 map.put("mouth", rs.getString("mouth"));
			 map.put("reportname", rs.getString("reportname"));
		}
		

		if(rs!=null){
			rs.close();
		}
		if(conn!=null){
			conn.close();
		}
		return map;
	}

	
	public List queryReport(String reportname, String regcode, String year,
			String bgq) throws OptimusException {
		IPersistenceDAO dao=this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer(" select a.id,a.year||'-'||a.mouth||c.value as bqg,b.value as regcode,a.reportname,a.year from T_COGNOS_report a left join T_DM_BBQH b on a.regcode=b.code left join T_DM_BBLB c on c.code=a.reportParamters where 1=1 ");
		StringBuffer sql=new StringBuffer(" select a.id,a.year||'-'||a.mouth||c.value as bqg,b.value as regcode,a.reportname,a.year from T_COGNOS_report a left join T_DM_BBQH b on a.regcode=b.code left join T_DM_BBLB c on c.code=a.reportParamters where  ");
		
		/*if(reportname!=null&&!reportname.isEmpty()){
			sql.append(" and  charindex('"+reportname+"',a.reportname)>0 ");
		}
		*/
		
		if(reportname!=null&&!reportname.isEmpty()){
			sql.append("   a.reportname like '%"+reportname+"%'");
		}
		
		if(regcode!=null&&!regcode.isEmpty()){
			sql.append(" and  charindex('"+regcode+"',a.regcode)>0 ");
		}
		if(year!=null&&!year.isEmpty()){
			sql.append(" and  charindex('"+year+"',a.year)>0 ");
		}
		//需要对数据进行处理
		if(bgq!=null&&!bgq.isEmpty()){
			sql.append(" and  charindex('"+bgq+"',a.reportParamters)>0 ");
		}
		sql.append( " order by a.reportname");
		System.out.println(sql.toString());
		List<Map> list=dao.pageQueryForList(sql.toString(), null);
		return list;
	}
	
	
	
	public void updateReport(String id,String report) throws OptimusException{
		report=this.StrToBinstr(report);
		IPersistenceDAO dao=this.getPersistenceDAO();
		String sql="update T_COGNOS_REPORT set reportContext='"+report+"' where id='"+id+"'";
		System.out.println(sql);
		dao.execute(sql, null);
	}
}
