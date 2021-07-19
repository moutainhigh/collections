package cn.gwssi.datachange.datashare.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.optimus.core.persistence.datasource.DataSourceManager;

import cn.gwssi.broker.server.Cursor;
import cn.gwssi.broker.server.DefaultCursor;
import cn.gwssi.broker.server.ServiceProvider;
import cn.gwssi.broker.server.auth.ServiceAuth;
import cn.gwssi.broker.server.hander.SRContent;
import cn.gwssi.common.exception.BrokerException;

public class ServiceImpl extends ServiceProvider{
    
	@Override
	public Cursor execute(String params) throws BrokerException {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs= null;
		try {
			String tablecode = ServiceAuth.getAuth().get("tablecode");
			String servicecontentcondition = ServiceAuth.getAuth().get("servicecontentcondition");
			String servicecontent = ServiceAuth.getAuth().get("servicecontent");
			String columncode = ServiceAuth.getAuth().get("columncode");
		      
			String sqlCondition="";
			List<String> sqlConditionValues= new ArrayList<String>();
			
			StringBuffer sbf = new StringBuffer("select ");
			if(StringUtils.isNotBlank(params)){
				SRContent sRContent = new SRContent(params);
				List<String> resultFields = sRContent.getResultFields();//返回项
				if(resultFields!=null && resultFields.size()>0){
					String resultField = resultFields.toString();
					resultField = resultField.replace("[", "");
					resultField = resultField.replace("]", "");
					sbf.append(resultField);
				}else{
					if(StringUtils.isNotBlank(columncode)){
						sbf.append(columncode);
					}else{
						sbf.append(" *");
					}
				}
				sbf.append(" from ").append(tablecode);
				sqlCondition = sRContent.getSqlConditionStructStr();//带问号的条件
				if(StringUtils.isNotBlank(sqlCondition)){
					sqlConditionValues=sRContent.getSqlConditionValues();//参数值
					sbf.append(" where ").append(sqlCondition);
				}else{
					if(StringUtils.isNotBlank(servicecontentcondition)){
						sbf.append(" where ").append(servicecontentcondition);
					}
				}
			}else{
				if(StringUtils.isNotBlank(columncode)){
					sbf.append(columncode);
				}else{
					sbf.append(" *");
				}
				sbf.append(" from ").append(tablecode);
				if(StringUtils.isNotBlank(servicecontentcondition)){
					sbf.append(" where ").append(servicecontentcondition);
				}
			}
			con = DataSourceManager.getConnection("defaultDataSource");//同步获取参数，只要是获取的数据项和返回的结果项
			pstmt = con.prepareStatement(sbf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			if(StringUtils.isNotBlank(sqlCondition)){
				if(sqlConditionValues!=null && sqlConditionValues.size()>0){
					for(int i=0;i<sqlConditionValues.size();i++){
						pstmt.setString(i+1, sqlConditionValues.get(i));
					}
				}
			}
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BrokerException(e.getMessage());
		}
		return new DefaultCursor(con,pstmt,rs);
	}

	/**
	 * 转化格式
	 * @param params
	 * @return
	 */
    private static List<Map<String,String>> convertData(List<Map> params){
    	List<Map<String,String>> resultData = new ArrayList<Map<String,String>>();
    	if(params!=null&&params.size()>0){
    		for(Map resultMap : params){
    			Map<String,String> resultMapData = new HashMap<String,String>();
    			for (Object key : resultMap.keySet()) {
    				resultMapData.put((String)key, (String)resultMap.get(key));
    			}
    			resultData.add(resultMapData);
    		}
    	}
    	return resultData;
    }
    
    /**
     * 组装sql
     * @param params
     * @param list
     * @param flag
     * @return
     */
    private static String getSql(Map params,List<Map<String, String>> list,String flag){//flag为0时是普通sql，为1时时总数sql
    	String tableCode = (String) params.get("tablecode");//表名
		String columnenname = (String) params.get("columnenname");//列名
		StringBuffer sbf =new StringBuffer("select ");
		String queryCondition="";//查询条件
    	if("1".equals(flag)){
    		sbf.append("count(1)");
    	}else{
    		String showResult="";//查询显示项
    		if(StringUtils.isNotBlank(showResult)){
    			sbf.append(showResult);
    		}else{
    			sbf.append(columnenname);
    		}
    	}
    	sbf.append(" from ").append(tableCode);
    	if(list!=null && list.size()>0){
			Map<String, String> map = list.get(0);
			queryCondition = map.get("queryCondition");
		}
		if(StringUtils.isNotBlank(queryCondition)){
			sbf.append(" where ").append(queryCondition);
		}
    	return sbf.toString();
    }
}
