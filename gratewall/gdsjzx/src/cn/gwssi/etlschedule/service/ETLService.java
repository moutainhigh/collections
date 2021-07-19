package cn.gwssi.etlschedule.service;

import cn.gwssi.etlschedule.module.EtlLogBO;
import cn.gwssi.etlschedule.module.EtlRwxxBO;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

import javax.print.attribute.standard.JobName;

/**
 * Created by Administrator on 2016/6/20.
 */

@Service
public class ETLService extends BaseService {


	public EtlRwxxBO getETLRwxxByOne(String name,String area) throws OptimusException{
		String sql="select * from ETL_RWXX where jobname='"+name+"' and area='"+area+"'";
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql, null);
		if(list.size()>0) {
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public List<EtlRwxxBO> getETLRwxxByModel(String jobname) throws OptimusException{
		
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX where  type='3' ");
		if(jobname!=null&&jobname!=""&&!jobname.equals("")){
			sql.append(" and jobname='"+jobname+"'");
		}
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}


	public int updateETLRwxx(EtlRwxxBO bo) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		int i=dao.update(bo);
		return i;
	}

	public int updateETLIncrementalTime(String sql) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		int i=dao.execute(sql, null);
		return i;
	}


	public void saveEtlLog(EtlLogBO bo) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(bo);
	}


	public List<EtlRwxxBO> getETLRwxxByName(String paramers) throws OptimusException {
		String sql="select * from ETL_RWXX where jobname='"+paramers+"'";
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql, null);
		return list;
	}




	public List<EtlRwxxBO> getETLRwxxByArea(String paramers) throws OptimusException {
		String sql="select * from ETL_RWXX where  type='1'and  area='"+paramers+"'";
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql, null);
		return list;
	}

	public List<EtlRwxxBO> getETLRwxxForNbZl(String jobname) throws OptimusException {
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX where  type='1' ");
		if(jobname!=null) sql.append( " and jobname='"+jobname+"'");
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}


	public List<EtlRwxxBO> getETLRwxxByType(String jobname) throws OptimusException {
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX where  type='1' ");
		if(jobname!=null) sql.append( " and jobname='"+jobname+"'");
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}


	/**记录ETL执行日志
	 * @param obj
	 * @return
	 * @throws OptimusException
	 */
	public int addLog(AbsDaoBussinessObject obj) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(obj);
		return 1;
	}



	public List<EtlRwxxBO> getETLRwxxByJobType(String jobtype,String area) throws OptimusException {
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX where  type='1'and  jobtype='"+jobtype+"'");
		if(area!=null&&!"".equals(jobtype)&&!"all".equals(area)){
			sql.append(" and area='"+area+"'");
		}
		sql.append(" order by sequence");
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}

	public List<EtlRwxxBO> getETLRwxx12315() throws OptimusException {
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX where  type='2'and  jobtype='"+12315+"'");
		sql.append(" order by sequence");
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}

	

	public List<EtlRwxxBO> getETLRwxxByJobType2(String jobtype,String area) throws OptimusException {
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX where  type='2'and  jobtype='"+jobtype+"'");
		if(area!=null&&!"".equals(jobtype)&&!"all".equals(area)){
			sql.append(" and area='"+area+"'");
		}
		sql.append(" order by sequence");
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}
	
	
	public List<EtlRwxxBO> getETLRwxxByType(String jobtype,String type) throws OptimusException {
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX where    jobtype='"+jobtype+"' and type='"+type+"'");
	
		sql.append(" order by sequence");
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}





	public List<EtlRwxxBO> getETLRwxxForNbQL(String jobname) throws OptimusException {
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX where  type='2' ");
		if(jobname!=null) sql.append( " and jobname='"+jobname+"'");
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}



	public List<EtlRwxxBO> getETLRwxxForNb(String type) throws OptimusException {
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX where  jobtype='nb' ");
		if(type!=null&&!"".equals(type)) sql.append( " and type='"+type+"'");
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}

	
	public List<EtlRwxxBO> getETLRwxxForJobNameByAllArea(String jobname,String type) throws OptimusException {
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX where  jobname= '"+jobname+"'");
		sql.append(" and type ='"+type+"'");
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}
					
						
	public List<EtlRwxxBO> getETLRwxxForNz(String type) throws OptimusException {
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX where  jobtype='nz' ");
		if(type!=null&&!"".equals(type)) sql.append( " and type='"+type+"'");
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}
	
	
	public List<EtlRwxxBO> getAllETLRwxx() throws OptimusException{
		StringBuffer sql=new StringBuffer("select * from ETL_RWXX ");
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}
	
	
	public void updateSeq(String id,int seq) throws OptimusException{
		String updateSql="update etl_rwxx set sequence="+seq+" where id='"+id+"'";
		IPersistenceDAO dao = getPersistenceDAO();
		dao.execute(updateSql, null);
	}

	public List getSbzj() throws OptimusException {
//		String querySql=" select * from ETL_RWXX where jobname  in (select jobname from etl_log where jobtype='sbzj' and state='失败')  ";
		String querySql=" select * from etl_rwxx where   jobtype='sbzj' and (flag is null or  flag ='') and type='2'  ";
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, querySql.toString(), null);
		
		return list;
	}
	
	
	public void deleteETLErrorLog(String jobtype) throws OptimusException{
		String delete =" delete from etl_log where jobtype='"+jobtype+"' and state ='失败' ";
		IPersistenceDAO dao = getPersistenceDAO();
		dao.execute(delete, null);
	}

	public List getTimestampError(String area) throws OptimusException {
			// TODO Auto-generated method stub
		String sql="SELECT A.* FROM ETL_RWXX A ,ETL_ERROR_XZ B WHERE A.JOBNAME=B.JOBNAME AND A.JOBNAME||AREA NOT IN (SELECT JOBNAME||AREA FROM ETL_LOG WHERE JOBNAME IN (SELECT JOBNAME FROM ETL_ERROR_XZ) AND STATE<>'失败' AND STARTTIME >'2016-10-08 14:34:33') and a.area='"+area+"' ";
		IPersistenceDAO dao = getPersistenceDAO();
		List<EtlRwxxBO> list= dao.queryForList(EtlRwxxBO.class, sql.toString(), null);
		return list;
	}
}
