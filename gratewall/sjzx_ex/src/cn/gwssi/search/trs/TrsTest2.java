package cn.gwssi.search.trs;

import com.trs.client.TRSConnection;
import com.trs.client.TRSDataBase;
import com.trs.client.TRSDataBaseColumn;
import com.trs.client.TRSException;
import com.trs.client.TRSIndex;

public class TrsTest2
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		TRSConnection conn = null;
        try
        {
                conn = new TRSConnection();
                conn.connect("160.100.0.218", "8889", "system", "manager", "T10");            
      
                TRSDataBase[] db = conn.getDataBases("*");
                for (int i = 0; i < db.length; i++){
                	TRSDataBase dbt = db[i];
                	System.out.println(db[i].getName());
                	TRSDataBaseColumn[] columns = dbt.getColumns();
                	for(int n=0; n<columns.length; n++){
                		TRSDataBaseColumn col = columns[n];
                		System.out.println("{"+(n+1)+"} [column name] "+col.getName()+"  [column type name]  "+col.getColTypeName()+"");
                	}
                	TRSIndex[] idex = dbt.getIndexes();
                	for(int j=0;j<idex.length; j++){
                		TRSIndex indext = idex[j];
                	    
                		System.out.println("<"+(j+1)+">  [strColumn]  "+indext.strColumn+"  [iColType]  "+indext.iColType+"");
                		
                	}
                	
                }
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

}
