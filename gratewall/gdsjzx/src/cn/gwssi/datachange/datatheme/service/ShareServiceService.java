package cn.gwssi.datachange.datatheme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
/**
 * 对增删改操作之后调用返回xml
 * */
@Service
public class ShareServiceService extends BaseService{
	/**
	 * 查找对象名1
	 */
	public List<Map> findServiceName1(String serviceid) throws OptimusException {
		List<Map> list=new ArrayList<Map>();
		IPersistenceDAO dao = this.getPersistenceDAO();
		String sql="select top 1 " +
				"a.serviceObjectName from T_PT_FWJBXX b " +
				"left join T_PT_FWDXQXGLB c on b.serviceId = c.serviceId " +
				"left join T_PT_FWDXJBXX a on a.fwdxjbId= c.serviceObjectId " +
				"where b.serviceId='"+serviceid+"'";
		list=dao.queryForList(sql, null);
		return list;
	}

	public List<Map> findServiceName(Map params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO();
		String sql="";
		//update,del服务
		if("service".equals(params.get("handle"))){
			sql="select a.serviceObjectName from T_PT_FWJBXX b left join T_PT_FWDXQXGLB c on b.serviceId = c.serviceId left join T_PT_FWDXJBXX a on a.fwdxjbId= c.serviceObjectId where b.serviceId='"+params.get("serviceid")+"'";
		}else {
			//update服务内容时推送  ---是没有的
			if("serviceDXControl".equals(params.get("handle"))){
				sql="select a.serviceObjectName from  T_PT_FWDXJBXX a  where a.fwdxjbid='"+params.get("serviceDXidControl")+"'";
			}else{
				//update,del主题
				if("serviceTheme".equals(params.get("handle"))){
						sql="select a.serviceObjectName from T_PT_THEMEXX d left join  T_PT_FWDXJBXX a on d.fwdxjbId = a.fwdxjbId where d.ztid ='"+params.get("themeid")+"'";
				}else{
					//修改，增加，删除服务对象
					if("serviceDX".equals(params.get("handle"))){
						sql="select a.serviceObjectName from T_PT_FWJBXX b left join T_PT_FWDXQXGLB c on b.serviceId = c.serviceId left join T_PT_FWDXJBXX a on a.fwdxjbId= c.serviceObjectId where a.fwdxjbId='"+params.get("serviceDXid")+"'";
					}
				}
			}
		};
		return  dao.queryForList(sql.toString(), null);
	}
	
	
	/**--修改    服务基本信息  找到   服务对象
	 *  and a.state='0' and b.servicestate = '0' and e.isEnabled='0'
	 * 返回结果
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<String> selectClientList(String serviceObjectName) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO();
		/*String objectsql="select d.controlobjectstate controlobjectstate,d.serviceobjectname serviceobjectname,d.serviceobjectip serviceobjectip, "+
				" d.serviceobjectport serviceobjectport,d.servicename servicename,d.serviceurl serviceurl,d.invokeclass invokeclass,"+
				" d.executetype executetype,d.defaulttime defaulttime,d.themename themename,e.tablecode tablecode,e.columncode columncode,"+
				" e.servicecontentcondition servicecontentcondition,e.servicecontent servicecontent,e.servicecontentname servicecontentname "+
				" from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceObjectIP serviceobjectip "+
				" ,a.serviceObjectPort serviceobjectport,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,"+
				" b.executetype executetype,b.defaulttime defaulttime,'' themename,b.serviceconentid from T_PT_FWDXJBXX a left join T_PT_FWDXQXGLB c on a.fwdxjbId=c.serviceObjectId left join T_PT_FWJBXX b on b.serviceId=c.serviceID where a.state='0' and b.servicestate='0' and a.controlobjectstate='0' and a.serviceObjectName='"+serviceObjectName+"') d left join T_PT_FWNRXX e on d.serviceconentId=e.fwnrId and e.isenabled='0'"+
				" union all "+
				" select controlobjectstate,serviceobjectname,serviceobjectip,serviceobjectport,servicename,serviceurl,invokeclass,executetype,defaulttime,themename,tablecode,columncode,servicecontentcondition,servicecontent,servicecontentname from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceObjectIP serviceobjectip,a.serviceObjectPort serviceobjectport,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,d.themename themename,'' tablecode,'' columncode,'' servicecontentcondition,'' servicecontent,'' servicecontentname,a.state from T_PT_FWDXJBXX a left join T_PT_THEMEXX d on d.fwdxjbId=a.fwdxjbid and d.isstart='0') s where s.serviceObjectName='"+serviceObjectName+"' and s.state='0' and s.controlobjectstate='0'";*/
		/*StringBuffer sbf = new StringBuffer("select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,b.executetype executetype,b.defaulttime defaulttime,'' themename from T_PT_FWDXJBXX a left join T_PT_FWDXQXGLB c on a.fwdxjbId=c.serviceObjectId left join T_PT_FWJBXX b on b.serviceId=c.serviceID where a.state='0' and b.servicestate='0' and a.controlobjectstate='0' and a.serviceObjectName=?"); 
		sbf.append(" union all ");
		sbf.append("select e.controlobjectstate,e.serviceobjectname,e.serviceobjectip,e.serviceobjectport,e.servicename,e.serviceurl,e.invokeclass,e.executetype,e.defaulttime,e.themename from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,d.themename themename from T_PT_FWDXJBXX a left join T_PT_THEMEXX d on d.fwdxjbId=a.fwdxjbid and d.isstart='0' and a.state='0' and a.controlobjectstate='0' and a.serviceObjectName=?) e where e.themename is not null");*/
		/*StringBuffer sbf = new StringBuffer("select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,b.executetype executetype,b.defaulttime defaulttime,'' themename from T_PT_FWDXJBXX a left join T_PT_FWDXQXGLB c on a.fwdxjbId=c.serviceObjectId left join T_PT_FWJBXX b on b.serviceId=c.serviceID where a.state='0' and b.servicestate='0' and a.serviceObjectName=?"); 
		sbf.append(" union all ");
		sbf.append("select e.controlobjectstate,e.serviceobjectname,e.serviceobjectip,e.serviceobjectport,e.servicename,e.serviceurl,e.invokeclass,e.executetype,e.defaulttime,e.themename from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,d.themename themename from T_PT_FWDXJBXX a left join T_PT_THEMEXX d on d.fwdxjbId=a.fwdxjbid and d.isstart='0' and a.state='0' and a.serviceObjectName=?) e where e.themename is not null");*/
		StringBuffer sbf = new StringBuffer("select '0' type,a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,(select serviceobjectname from T_PT_FWDXJBXX where fwdxjbid in(select serviceobjectid from T_PT_FWJBXX where serviceid=b.serviceid)) serviceobjectnamef,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,b.executetype executetype,b.defaulttime defaulttime,b.alias,'' themename from T_PT_FWDXJBXX a left join T_PT_FWDXQXGLB c on a.fwdxjbId=c.serviceObjectId left join T_PT_FWJBXX b on b.serviceId=c.serviceID where a.state='0' and b.servicestate='0' and a.serviceObjectName=?"); 
		sbf.append(" union all ");
		sbf.append("select '0' type,e.controlobjectstate,e.serviceobjectname,e.serviceobjectip,e.serviceobjectport,''  serviceobjectnamef,e.servicename,e.serviceurl,e.invokeclass,e.executetype,e.defaulttime,alias,e.themename from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,'' alias,d.themename themename from T_PT_FWDXJBXX a left join T_PT_THEMEXX d on d.fwdxjbId=a.fwdxjbid and d.isstart='0' and a.state='0' and a.serviceObjectName=?) e where e.themename is not null");
		sbf.append(" union all ");
		sbf.append("select '1' type,d.controlobjectstate controlobjectstate,d.serviceobjectname serviceobjectname,d.serviceobjectip serviceobjectip,d.serviceobjectport serviceobjectport,'' serviceobjectnamef,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,'' alias,'' themename from T_PT_FWDXJBXX d where d.state='0' and d.serviceObjectName=?");
		List<String> params = new ArrayList<String>();
		params.add(serviceObjectName);
		params.add(serviceObjectName);
		params.add(serviceObjectName);
		List<Map> col=dao.queryForList(sbf.toString(), params);
		return list2xml(col);
	}
	
	    /**--修改    服务基本信息  找到   服务对象
		 *  and a.state='0' and b.servicestate = '0' and e.isEnabled='0'
		 * 返回结果
		 * @param params
		 * @return
		 * @throws OptimusException
		 */
		public List<String> selectServerNameList(String serviceObjectName) throws OptimusException {
			IPersistenceDAO dao = this.getPersistenceDAO();
			StringBuffer sbf = new StringBuffer("select serviceobjectname from T_PT_FWDXJBXX a,(select distinct a.serviceObjectid from T_PT_FWJBXX a left join T_PT_FWDXQXGLB b on a.serviceId=b.serviceID left join T_PT_FWDXJBXX c on c.fwdxjbId=b.serviceObjectId and c.serviceObjectName=?) b where a.fwdxjbId =b.serviceObjectid");
			List<String> params = new ArrayList<String>();
			params.add(serviceObjectName);
			List<Map> col=dao.queryForList(sbf.toString(), params);
			List<String> list=new ArrayList<String>();
			if(col!=null && col.size()>0){
				for(Map m: col){
					list.add((String)m.get("serviceobjectname"));
				}
			}
			return list;
		}
	 
	/**--修改    服务基本信息  找到   服务对象
	 *  and a.state='0' and b.servicestate = '0' and e.isEnabled='0'
	 * 返回结果
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<String> selectServerList(String serviceObjectName) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO();
		/*String objectsql="select d.controlobjectstate controlobjectstate,d.serviceobjectname serviceobjectname,d.serviceobjectip serviceobjectip, "+
				" d.serviceobjectport serviceobjectport,d.servicename servicename,d.serviceurl serviceurl,d.invokeclass invokeclass,"+
				" d.executetype executetype,d.defaulttime defaulttime,d.themename themename,e.tablecode tablecode,e.columncode columncode,"+
				" e.servicecontentcondition servicecontentcondition,e.servicecontent servicecontent,e.servicecontentname servicecontentname "+
				" from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceObjectIP serviceobjectip "+
				" ,a.serviceObjectPort serviceobjectport,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,"+
				" b.executetype executetype,b.defaulttime defaulttime,'' themename,b.serviceconentid from T_PT_FWDXJBXX a left join T_PT_FWDXQXGLB c on a.fwdxjbId=c.serviceObjectId left join T_PT_FWJBXX b on b.serviceId=c.serviceID where a.state='0' and b.servicestate='0' and a.controlobjectstate='0' and a.serviceObjectName='"+serviceObjectName+"') d left join T_PT_FWNRXX e on d.serviceconentId=e.fwnrId and e.isenabled='0'"+
				" union all "+
				" select controlobjectstate,serviceobjectname,serviceobjectip,serviceobjectport,servicename,serviceurl,invokeclass,executetype,defaulttime,themename,tablecode,columncode,servicecontentcondition,servicecontent,servicecontentname from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceObjectIP serviceobjectip,a.serviceObjectPort serviceobjectport,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,d.themename themename,'' tablecode,'' columncode,'' servicecontentcondition,'' servicecontent,'' servicecontentname,a.state from T_PT_FWDXJBXX a left join T_PT_THEMEXX d on d.fwdxjbId=a.fwdxjbid and d.isstart='0') s where s.serviceObjectName='"+serviceObjectName+"' and s.state='0' and s.controlobjectstate='0'";*/
		//StringBuffer sbf = new StringBuffer("select d.controlobjectstate controlobjectstate,d.serviceobjectname serviceobjectname,d.serviceobjectip serviceobjectip,d.serviceobjectport serviceobjectport,d.servicename servicename,d.serviceurl serviceurl,d.invokeclass invokeclass,d.executetype executetype,d.defaulttime defaulttime,e.tablecode tablecode,e.columncode columncode,e.servicecontentcondition servicecontentcondition,e.servicecontent servicecontent,e.servicecontentname servicecontentname from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceObjectIP serviceobjectip,a.serviceObjectPort serviceobjectport,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,b.executetype executetype,b.defaulttime defaulttime,b.serviceconentid from T_PT_FWDXJBXX a left join T_PT_FWDXQXGLB c on a.fwdxjbId=c.serviceObjectId left join T_PT_FWJBXX b on b.serviceId=c.serviceID where c.serviceID in(select distinct b.serviceId from T_PT_FWJBXX b left join T_PT_FWDXQXGLB c on b.serviceId=c.serviceID left join T_PT_FWDXJBXX a on a.fwdxjbId=c.serviceObjectId where a.state='0' and b.servicestate='0' and a.controlobjectstate='0' and a.serviceObjectName=?) and a.state='0' and a.controlobjectstate='0') d left join T_PT_FWNRXX e on d.serviceconentId=e.fwnrId and e.isenabled='0'");
		//StringBuffer sbf = new StringBuffer("select d.controlobjectstate controlobjectstate,d.serviceobjectname serviceobjectname,d.serviceobjectip serviceobjectip,d.serviceobjectport serviceobjectport,d.servicename servicename,d.serviceurl serviceurl,d.invokeclass invokeclass,d.executetype executetype,d.defaulttime defaulttime,e.tablecode tablecode,e.columncode columncode,e.servicecontentcondition servicecontentcondition,e.servicecontent servicecontent,e.servicecontentname servicecontentname from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,b.executetype executetype,b.defaulttime defaulttime,b.serviceconentid from T_PT_FWDXJBXX a left join T_PT_FWDXQXGLB c on a.fwdxjbId=c.serviceObjectId left join T_PT_FWJBXX b on b.serviceId=c.serviceID where c.serviceID in(select distinct b.serviceId from T_PT_FWJBXX b where b.servicestate='0' and serviceobjectid in (select fwdxjbid from T_PT_FWDXJBXX a where a.state='0' and a.serviceObjectName=?)) and a.state='0') d left join T_PT_FWNRXX e on d.serviceconentId=e.fwnrId and e.isenabled='0'");
		StringBuffer sbf = new StringBuffer("select '0' type,d.controlobjectstate controlobjectstate,d.serviceobjectname serviceobjectname,d.serviceobjectip serviceobjectip,d.serviceobjectport serviceobjectport,d.serviceobjectnamef,d.servicename servicename,d.serviceurl serviceurl,d.invokeclass invokeclass,d.executetype executetype,d.defaulttime defaulttime,d.alias,e.tablecode tablecode,e.columncode columncode,e.servicecontentcondition servicecontentcondition,e.servicecontent servicecontent,e.servicecontentname servicecontentname from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,(select serviceobjectname from T_PT_FWDXJBXX where fwdxjbid in(select serviceobjectid from T_PT_FWJBXX where serviceid=b.serviceid)) serviceobjectnamef,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,b.executetype executetype,b.defaulttime defaulttime,b.alias,b.serviceconentid from T_PT_FWDXJBXX a left join T_PT_FWDXQXGLB c on a.fwdxjbId=c.serviceObjectId left join T_PT_FWJBXX b on b.serviceId=c.serviceID where c.serviceID in(select distinct b.serviceId from T_PT_FWJBXX b where b.servicestate='0' and serviceobjectid in (select fwdxjbid from T_PT_FWDXJBXX a where a.state='0' and a.serviceObjectName=?)) and a.state='0') d left join T_PT_FWNRXX e on d.serviceconentId=e.fwnrId and e.isenabled='0'");
		sbf.append(" union all ");
		sbf.append("select '1' type,d.controlobjectstate controlobjectstate,d.serviceobjectname serviceobjectname,d.serviceobjectip serviceobjectip,d.serviceobjectport serviceobjectport,'' serviceobjectnamef,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,'' alias,'' tablecode,'' columncode,'' servicecontentcondition,'' servicecontent,'' servicecontentname from T_PT_FWDXJBXX d where d.state='0' and d.serviceObjectName=?");
		List<String> params = new ArrayList<String>();
		params.add(serviceObjectName);
		params.add(serviceObjectName);
		List<Map> col=dao.queryForList(sbf.toString(), params);
		return list2xml(col);
	}
																																											
	/**--修改    服务基本信息  找到   服务对象
	 * 返回结果
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<String> selectServiceNRList(Map<String, String> params) throws OptimusException {
		List<String> list=new ArrayList<String>();
		IPersistenceDAO dao = this.getPersistenceDAO();
		List<Map> data = new ArrayList<Map>();
		if("service".equals(params.get("handle"))){
			String sql="select a.serviceObjectName from T_PT_FWJBXX b left join T_PT_FWDXQXGLB c on b.serviceId = c.serviceId left join T_PT_FWDXJBXX a on a.fwdxjbId= c.serviceObjectId where b.serviceId='"+params.get("serviceid")+"'";
			data = dao.queryForList(sql.toString(), null);
			//serviceobjectname  serviceDXid
			for(int i=0;i<data.size();i++){
			  if(data.get(i).get("serviceobjectname")!=null){
				String objectsql="select a.serviceobjectname,b.servicename,d.themename from T_PT_FWDXJBXX a,T_PT_FWJBXX b,T_PT_FWDXQXGLB c,T_PT_THEMEXX d where a.fwdxjbId=c.serviceObjectId and b.serviceId=c.serviceID and d.fwdxjbId=a.fwdxjbid and a.serviceObjectName='"+data.get(i).get("serviceobjectname")+"'";
				List<Map> col=dao.queryForList(objectsql, null);	
				list2xml(col);
			  }
			}
		};
		return list;
	}
	
	/**
	 * 
	 * 一个对象名，多个数据情况,多个xml
	 * <对象名-col>
	 * 	<数据1></数据1>
	 * </对象名-col>
	 * <对象名-col>
	 * 	<数据2></数据2>
	 * </对象名-col>
	 * @param col
	 * @return
	 */
	public List<String> list2xml(List<Map> col){
		List<String> list=new ArrayList<String>();
		for(int i=0;i<col.size();i++){
			StringBuffer xmlstr=new StringBuffer("<col>");
			Map colMap=col.get(i);
			 Set<Entry<String, String>> set = colMap.entrySet();  
		        for (Entry<String, String> entry : set) {  
		            String key = entry.getKey();  
		            String value = entry.getValue();  
		            xmlstr = xmlstr.append("<"+key+">"+value+"</"+key+">");
		        }
		   xmlstr.append("</col>");
		   list.add(xmlstr.toString());
		}
		return list;
	}
	
	/**
	 * 一个对象名，多个数据情况，一个xml   
	 * <对象名-col>
	 * 	<数据1></数据1>
	 *	<数据2></数据2>
	 * </对象名-col>
	 * @param col
	 * @return
	 */
	public String listtoxml(List<Map> col){
		List<String> list=new ArrayList<String>();
		StringBuffer xmlstr=new StringBuffer("<col>");
		for(int i=0;i<col.size();i++){
			Map colMap=col.get(i);
			 Set<Entry<String, String>> set = colMap.entrySet();  
		        for (Entry<String, String> entry : set) {  
		            String key = entry.getKey();  
		            String value = entry.getValue();  
		            xmlstr = xmlstr.append("<"+key+">"+value+"</"+key+">");
		        }
		}
		xmlstr.append("</col>");
		return xmlstr.toString();
	}
}
