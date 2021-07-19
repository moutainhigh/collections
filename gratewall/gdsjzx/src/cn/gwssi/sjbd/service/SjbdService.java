package cn.gwssi.sjbd.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class SjbdService  extends BaseService{
	
	public List<Map> queryZzjgDm(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer("select * from Organization_code_table  where 1=1 ");
		
		if(map!=null&&map.size()>0){
			String zzjgdm=(String) map.get("zzjgdm");
			String zch=(String) map.get("zch");
			String qymc=(String) map.get("qymc");
			
			if(zzjgdm!=null&&!"".equals(zzjgdm)){
				sql.append(" and  charindex('"+zzjgdm+"',orgno)>0 ");
				
				
			}
			if(zch!=null&&!"".equals(zch)){
				sql.append(" and  charindex('"+zch+"',registerNo)>0 ");
			}
			if(qymc!=null&&!"".equals(qymc)){
				sql.append(" and  charindex('"+qymc+"',corpName)>0 ");
				
			}
		}
		sql.append(" and pripid='6b6558c1-a4ad-4ac9-8c6a-40e18d117c84'");
		List<Map> list=dao.pageQueryForList(sql.toString(), null);
		
		return list;
	}
	
	public String getZzjgdmByEntityNoToTrue(String entityNo) throws OptimusException{
		String returnValue=null;
		IPersistenceDAO dao= this.getPersistenceDAO();
		String sql="select orgno from isexistTable where entityNo='"+entityNo+"'";
		List<Map> list=dao.queryForList(sql, null);
		if(list!=null&&list.size()>0){
			returnValue=(String) list.get(0).get("orgno");
		}
		return returnValue;
	}
	/**查询历史比中表
	 * @param entityNo
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> getZzjgdmByEntityNoToHistory(String entityNo) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		String sql="SELECT a.approveDate,a.corpName,a.orgno,b.CertificatesNo,b.principal,b.subEntityType,b.registerNo,b.regData,b.orgname FROM dbo.isexistTable_history a ,Organization_code_table b where a.registerNo=b.registerNo and a.entityNo='"+entityNo+"'";
		List<Map> list=dao.queryForList(sql.toString(), null);
		return list;
	}
	
	/*//历史查询中管理质检提供数据
	public Map getZjsj(String registerNo) throws OptimusException{
		Map map =new HashMap();
		IPersistenceDAO dao= this.getPersistenceDAO();
		String sql="select b.subEntityType,b.CertificatesNo,b.regData,b.orgno,b.principal,b.registerNo,b.orgname  from dbo.Organization_code_table b where   b.registerNo='"+registerNo+"'";
		List  list= dao.queryForList(sql, null);
		if(list!=null&&list.size()>0){
			map=(Map) list.get(0);
		}
		return map;
	}*/
}
