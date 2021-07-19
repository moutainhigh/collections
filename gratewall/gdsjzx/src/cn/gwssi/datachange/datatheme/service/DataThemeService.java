package cn.gwssi.datachange.datatheme.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.gwssi.datachange.datatheme.model.TPtFwdxtopicBO;
import cn.gwssi.datachange.datatheme.model.TPtThemexxBO;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.TreeUtil;

@Service
public class DataThemeService extends BaseService{
	/**
	 *详细主题信息
	 */ 
	public List<Map> selectThemeDetail(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer("select * from T_PT_THEMEXX ");
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String themeid = (String) params.get("themeid");
			if(StringUtils.isNotBlank(themeid)){
				sql.append(" where ztid = ?");
				list.add(themeid);
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
			
	}
	
	/**
	 * 主题列表
	 * */
	public List<Map> selectThemeList(Map<String, String> params)  throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO();
		StringBuffer sql = new StringBuffer("select * from T_PT_THEMEXX where 1=1 and fwdxjbId ='0' ");
		if (params != null && params.size() > 0) {
			String isstart=(String) params.get("isstart");
			String createperson=(String) params.get("createperson");
			String themename=(String) params.get("themename");
			if(StringUtils.isNotBlank(themename)){
				sql.append(" and charindex('");
				sql.append(themename);
				sql.append("',themename)>0");
			}
			if(StringUtils.isNotBlank(createperson)){
				sql.append(" and charindex('");
				sql.append(createperson);
				sql.append("',createperson)>0");
			}
			if(StringUtils.isNotBlank(isstart)){
				sql.append(" and charindex('");
				sql.append(isstart);
				sql.append("',isstart)>0");
			}
			
		}
		sql.append(" order by createtime desc");
		List l = dao.pageQueryForList(sql.toString(), null);
		return l;
	}

	public void updateRunService(String themeid, String state) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO();
		String sql="";
		if( "0".equals(state)){
			sql="update T_PT_THEMEXX set isStart = '0' where ztid='"+themeid+"'";
		}else{
			sql="update T_PT_THEMEXX set isStart = '1' where ztid='"+themeid+"'";
		}
		dao.execute(sql, null);
	}
	
	/**
	 * 根据服务id获取服务对象
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectServiceObjectByServiceId(String themeid) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql;
		if(StringUtils.isNotBlank(themeid)){
		sql=new StringBuffer("select * from T_PT_FWDXJBXX a where a.state='0' and a.fwdxjbId in (select serviceobjectid from T_PT_FWDXTOPIC where themeId ='"+themeid+"')");
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), null);
	}

	
	/**
	 * 服务对象树--新增
	 * @return
	 * @throws OptimusException
	 */
	public List selectThemeTreeAdd() throws OptimusException{
		 IPersistenceDAO dao = getPersistenceDAO();
	        String sql = "select t.ztid as id, '0' as pid, t.themename as name  from T_PT_THEMEXX t where t.fwdxjbId='0' and isStart='0'";
	        List funcList = dao.queryForList(sql, null);
	        return funcList;
	}
	
	/**
	 * 服务对象树--修改
	 * @return
	 * @throws OptimusException
	 */
	public List selectThemeTreeUpdate(String fwdxid) throws OptimusException{
		 IPersistenceDAO dao = getPersistenceDAO();
	        String sql = "select t.ztid as id, '0' as pid, t.themename as name  from T_PT_THEMEXX t where  isStart='0' and (t.fwdxjbId='0' or t.fwdxjbId='"+fwdxid+"')";
	        List funcList = dao.queryForList(sql, null);
	        return funcList;
	}
	
	
	
	/**
	 * 获取服务对象树选择节点
	 * @param serviceid
	 * @return
	 * @throws OptimusException
	 */

	public List selectServicemakeCheckedTree(String fwdxjbid) throws OptimusException{
		 IPersistenceDAO dao = getPersistenceDAO();
	      //  String sql = "select t.serviceobjectid as fwdxjbId from T_PT_FWDXTOPIC t where t.themeid='"+themeid+"'";
	        String sql = "select t.ztid from T_PT_THEMEXX t where t.isStart='0' and t.fwdxjbid='"+fwdxjbid+"'";
	        List authFuncList = dao.queryForList(sql, null);
	        return authFuncList;
	}

	public void updateService(TPtThemexxBO bo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(bo);
	}

	public void addTheme(TPtThemexxBO bo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String funcIdsStr=bo.getFwdxjbid();
		/*if(!StringUtils.isEmpty(funcIdsStr)){
			List<String> list=Arrays.asList(funcIdsStr.split(","));
			for(int i=0;i<list.size();i++){
				bo.setFwdxjbid(list.get(i));
				bo.setFwdxjbid(UUID.randomUUID().toString().replace("-",""));
			}
		}*/
		dao.insert(bo);
	}
	/**
	 * 插入中间表
	 * @param bo
	 * @throws OptimusException
	 */
/*	public void insertTopicDX(TPtThemexxBO bo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String funcIdsStr=bo.getFwdxjbid();
		if(bo.getThemeid()!=null){
			String sql="delete from T_PT_FWDXTOPIC where themeid='"+bo.getThemeid()+"'";
			dao.execute(sql, null);
		}
		
		if(!StringUtils.isEmpty(funcIdsStr)){
			List<String> list=Arrays.asList(funcIdsStr.split(","));
			for(int i=0;i<list.size();i++){
        		TPtFwdxtopicBO to=new TPtFwdxtopicBO();
        		to.setServiceobjectid(list.get(i));
        		to.setThemeid(bo.getThemeid());
        		dao.insert(to);
        	}
		}
		
	}*/

	public List findTheme(String themeid) throws OptimusException {
		 IPersistenceDAO dao = getPersistenceDAO();
	     String sql = "select * from T_PT_THEMEXX t where t.ztid='"+themeid+"'";
		 return dao.queryForList(sql, null);
	}
	
	
	public List findZtid(String themeid) throws OptimusException {
		 IPersistenceDAO dao = getPersistenceDAO();
	     String sql = "select * from T_PT_THEMEXX t where t.ztid='"+themeid+"' and t.fwdxjbId='0'";
	     return dao.queryForList(sql, null);
	}
		
	/*public Object findTheme(String themeid) throws OptimusException {
		 IPersistenceDAO dao = getPersistenceDAO();
	     String sql = "select * from T_PT_THEMEXX t where t.themeid='"+themeid+"'";
		 return dao.queryForList(sql, null);
	}*/
	
	/**
	 * 删除对象修改主题表中的数据
	 */
	public void delTheme(String fwdxid) throws OptimusException {
		 IPersistenceDAO dao = getPersistenceDAO();
		// String sql = "update t_pt_themexx set isstart = '1' where  fwdxjbid='"+fwdxid+"'";
	     String sql = "delete from T_PT_THEMEXX  where fwdxjbid='"+fwdxid+"'";
	     dao.execute(sql, null);
	}
	
	/**
	 * 对象修改还原主题表中的数据
	 */
	public void originalTheme(String fwdxid) throws OptimusException {
		 IPersistenceDAO dao = getPersistenceDAO();
		 String sql = "update t_pt_themexx set fwdxjbid = '0' where  fwdxjbid='"+fwdxid+"'";
	     dao.execute(sql, null);
	}	
}
