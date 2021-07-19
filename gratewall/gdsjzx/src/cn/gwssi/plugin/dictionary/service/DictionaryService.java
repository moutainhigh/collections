package cn.gwssi.plugin.dictionary.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.cache.dictionary.model.TPtDmsjbBO;
import com.gwssi.optimus.core.cache.dictionary.model.TPtDmsybBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;


@Service(value = "dictionaryService")
public class DictionaryService extends BaseService  {
    
    /**
     * 根据代码表ID查询代码索引表
     * @param dmbId 代码表ID
     * @return 查询结果 结果为List<TPtDmsybBO>类型
     * @throws OptimusException
     */
    public List<TPtDmsybBO> getDefById(String dmbId) throws OptimusException{
    	IPersistenceDAO dao = getPersistenceDAO();
    	StringBuffer sql = new StringBuffer(); 
    	if(dmbId != null)
    		dmbId = dmbId.trim();
    	sql.append("select * from t_pt_dmsyb ");
    	if(StringUtils.isEmpty(dmbId)){
    		
    	}else{
    		sql.append("where DMB_ID='" + dmbId + "'");
    	}
    	return dao.pageQueryForList(TPtDmsybBO.class, sql.toString(), null);
    } 
    
    /**
     * 根据代码表名称查询代码索引表
     * @param dmbMc 代码表名称
     * @return 查询结果 结果为List<TPtDmsybBO>类型
     * @throws OptimusException
     */
    public List<TPtDmsybBO> getDefByName(String dmbMc) throws OptimusException{
    	IPersistenceDAO dao = getPersistenceDAO();
    	StringBuffer sql = new StringBuffer(); 
    	if(dmbMc != null)
    		dmbMc = dmbMc.trim();
    	sql.append("select * from t_pt_dmsyb ");
    	if(StringUtils.isEmpty(dmbMc)){
    		
    	}else{
    		sql.append("where DMB_MC like '%" + dmbMc + "%'");
    	}
    	return dao.pageQueryForList(TPtDmsybBO.class, sql.toString(), null);
    }
    
    /**
     * 根据代码表ID和名称查询代码索引表
     * @param dmbId 代码表ID
     * @param dmbMc	代码表名称
     * @return 查询结果 结果为List<TPtDmsybBO>类型
     * @throws OptimusException
     */
    public List<TPtDmsybBO> getDefByIdAndName(String dmbId,String dmbMc) throws OptimusException{
    	IPersistenceDAO dao = getPersistenceDAO();
    	StringBuffer sql = new StringBuffer(); 
    	if(dmbId != null)
    		dmbId = dmbId.trim();
    	if(dmbMc != null)
    		dmbMc = dmbMc.trim();
    	sql.append("select * from t_pt_dmsyb where 1=1 ");
    	if(StringUtils.isEmpty(dmbId) && StringUtils.isEmpty(dmbMc)){
    		
    	}else if (StringUtils.isEmpty(dmbMc)){
    		sql.append("and DMB_ID='" + dmbId + "'");
    	}else if (StringUtils.isEmpty(dmbId)){
    		sql.append("and DMB_MC like '%" + dmbMc + "%'");
    	}else{
    		sql.append("and DMB_ID='" + dmbId + "' and DMB_MC like '%" + dmbMc + "%'");
    	}
    	return dao.pageQueryForList(TPtDmsybBO.class, sql.toString(), null);
    }
    
    /**
     * 新增代码索引表数据
     * @param tPtDmsybBO 代码索引表BO
     * @throws OptimusException
     */
    public void saveDef(TPtDmsybBO tPtDmsybBO) throws OptimusException{
    	IPersistenceDAO dao = getPersistenceDAO();
    	dao.insert(tPtDmsybBO);
    }
    
    /**
     * 更新代码索引表数据
     * @param tPtDmsybBO 代码索引表BO
     * @throws OptimusException
     */
    public void updateDef(TPtDmsybBO tPtDmsybBO) throws OptimusException{
    	IPersistenceDAO dao = getPersistenceDAO();
    	dao.update(tPtDmsybBO);
    }
    
    /**
     * 删除代码索引表数据
     * @param tPtDmsybBO 代码索引表BO
     * @throws OptimusException
     */
    public void deleteDef(TPtDmsybBO tPtDmsybBO) throws OptimusException{
    	IPersistenceDAO dao = getPersistenceDAO();
    	dao.delete(tPtDmsybBO);
    }
    
    /**
     * 根据代码表ID删除代码数据表数据
     * @param dmbId 代码表ID
     * @throws OptimusException
     */
    public void deleteData(TPtDmsjbBO tPtDmsjbBO) throws OptimusException{
    	IPersistenceDAO dao = getPersistenceDAO();
    	dao.delete(tPtDmsjbBO);
    }
    
    /**
     * 根据代码表ID查询代码数据表
     * @param dmbId 代码表ID
     * @return 查询结果 结果为List<TPtDmsjbBO>类型
     * @throws OptimusException
     */
    public List<TPtDmsjbBO> getDataById(String dmbId,String sjId) throws OptimusException{
    	IPersistenceDAO dao = getPersistenceDAO();
    	StringBuffer sql = new StringBuffer(); 
    	sql.append("select * from t_pt_dmsjb where 1=1 ");
    	if(StringUtils.isNotEmpty(dmbId)&&StringUtils.isNotEmpty(sjId)){
    		sql.append("and DMB_ID='" + dmbId + "' and SJ_ID!='" + sjId + "'");
    	}else if(StringUtils.isNotEmpty(sjId)){
    		sql.append("and SJ_ID!='" + sjId + "'");
    	}else if(StringUtils.isNotEmpty(dmbId)){
    		sql.append("and DMB_ID='" + dmbId + "'");
    	}
    	return dao.pageQueryForList(TPtDmsjbBO.class, sql.toString(), null);
    } 
    
    /**
     * 根据数据ID查询代码数据表
     * @param sjId 数据ID
     * @return 查询结果 结果为List<TPtDmsjbBO>类型
     * @throws OptimusException
     */
    public List<TPtDmsjbBO> getDataBySjId(String sjId) throws OptimusException{
    	IPersistenceDAO dao = getPersistenceDAO();
    	StringBuffer sql = new StringBuffer(); 
    	sql.append("select * from t_pt_dmsjb ");
    	if(sjId.isEmpty()){
    		
    	}else{
    		sql.append("where SJ_ID='" + sjId + "'");
    	}
    	return dao.pageQueryForList(TPtDmsjbBO.class, sql.toString(), null);
    } 
    
    /**
     * 新增代码索引表数据
     * @param tPtDmsjbBO 代码数据表BO
     * @throws OptimusException
     */
    public void saveData(TPtDmsjbBO tPtDmsjbBO) throws OptimusException{
    	IPersistenceDAO dao = getPersistenceDAO();
    	dao.insert(tPtDmsjbBO);
    }


	public List<TPtDmsjbBO> getDataByWb(String wb,String dmbId) throws OptimusException{
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
    	StringBuffer sql = new StringBuffer(); 
    	if(wb!=null)
    		wb = wb.trim();
    	sql.append("select * from t_pt_dmsjb where DMB_ID='" + dmbId + "'");
    	if(StringUtils.isEmpty(wb)){
    		
    	}else{
    		sql.append(" and Wb like '%" + wb + "%'");
    	}
    	return dao.pageQueryForList(TPtDmsjbBO.class, sql.toString(), null);
	}


	public List<TPtDmsjbBO> getDataByDm(String dm , String dmbId) throws OptimusException{
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
    	StringBuffer sql = new StringBuffer(); 
    	if(dm!=null)
    		dm = dm.trim();
    	sql.append("select * from t_pt_dmsjb where DMB_ID='" + dmbId + "'");
    	if(StringUtils.isEmpty(dm)){
    		
    	}else{
    		sql.append(" and DM='" + dm + "'");
    	}
    	return dao.pageQueryForList(TPtDmsjbBO.class, sql.toString(), null);
	}


	public List<TPtDmsjbBO> getDataByWbAndDm(String wb, String dm, String dmbId) throws OptimusException{
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
    	StringBuffer sql = new StringBuffer(); 
    	if(wb!=null)
    		wb = wb.trim();
    	if(dm!=null)
    		dm = dm.trim();
    	sql.append("select * from t_pt_dmsjb where 1=1 and DMB_ID='" + dmbId + "'");
    	if(StringUtils.isEmpty(wb) && StringUtils.isEmpty(dm)){
    		
    	}else if (StringUtils.isEmpty(wb)){
    		sql.append(" and DM='" + dm + "'");
    	}else if (StringUtils.isEmpty(dm)){
    		sql.append(" and WB like '%" + wb + "%'");
    	}else{
    		sql.append(" and WB like '%" + wb + "%' and DM='" + dm + "'");
    	}
    	return dao.pageQueryForList(TPtDmsjbBO.class, sql.toString(), null);
	}


	public void deleteDataByDmbId(String dmbId) throws OptimusException{
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "delete from t_pt_dmsjb where DMB_ID ='" + dmbId + "'";
		dao.execute(sql, null);
	}
	
	/**
     * 更新代码数据表数据
     * @param tPtDmsjbBO 代码数据表BO
     * @throws OptimusException
     */
    public void updateData(TPtDmsjbBO tPtDmsjbBO) throws OptimusException{
    	IPersistenceDAO dao = getPersistenceDAO();
    	dao.update(tPtDmsjbBO);
    }


	public boolean testSql(String sql) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(StringUtils.isNotBlank(sql)){
			IPersistenceDAO dao = getPersistenceDAO();
			try {
				dao.execute(sql, null);
				flag = true;
			} catch (OptimusException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				return flag;
			}
		}
		return flag;
	}
}
