package cn.gwssi.trs.yearReport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import cn.gwssi.resource.Conts;

@Service
public class ReportService extends BaseService{
	
	/**
	 * 年度基本信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryReportJbxx(Map map,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=null;
		sql=new StringBuffer(Conts.NDBGJBXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String year=(String)map.get("year");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = ? ");
				list.add(pripid);
			}
			if(StringUtils.isNotBlank(year)){
				sql.append(" and a.AnCheYear = ? ");
				list.add(year);
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 年度隶属信息(T_SCZT_SCZTLSBCXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryReportFzjgxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.NDBGLSXX);
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String year=(String) map.get("year");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and b.priPid = '"+pripid+"'");
			}
			if(StringUtils.isNotBlank(year)){
				sql.append(" and b.ancheyear = '"+year+"'");
			}
			
		}else{
			return null;
		}
		//sql.append(" Order by EstDate desc");
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 *	年度党建信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryReportDjxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.NDBGDJXX);
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String year=(String)map.get("year");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and b.pripid = '"+pripid+"'");
			}
			if(StringUtils.isNotBlank(year)){
				sql.append(" and b.ancheyear = '"+year+"'");
			}
		}else{
			return null;
		}
		//sql.append(" order by mdate desc");
		List<Map> list=dao.pageQueryForList(sql.toString(), null);
		return list;
	}
	
	//[{minstatus=1, qrqctype=迁入, memo=null, moutareregorg=广东省东莞市工商行政管理局长安, pripid=05a57209-0106-1000-e009-631d0a0c0115, archremovemode=null, minrea=null, historyinfoid=1c096e45-0115-1000-e000-19120a0c0115, sourceflag=441900, mindate=null, minletnum=0700890250, moutarea=null}, {minstatus=1, qrqctype=迁出, memo=null, moutareregorg=441904, pripid=05a57209-0106-1000-e009-631d0a0c0115, archremovemode=null, minrea=null, historyinfoid=1c096e45-0115-1000-e000-19120a0c0115, sourceflag=441900, mindate=null, minletnum=null, moutarea=null}]
	/**
	 * 对外担保信息(T_NDBG_DWDBXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryReportDwdbxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_GQDJXX ");
		StringBuffer sql=new StringBuffer(Conts.NDBGDWDB);
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String year=(String)map.get("year");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and b.priPid = '"+pripid+"'");
			}
			if(StringUtils.isNotBlank(year)){
				sql.append(" and b.ancheyear = '"+year+"'");
			}
			
		}else{
			return null;
		}
	//	sql.append(" order by a.frofrom desc ");
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 网站信息()
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryReportWzxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.NDBGWZXX);
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String year=(String) map.get("year");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and b.priPid = '"+pripid+"'");
			}
			if(StringUtils.isNotBlank(year)){
				sql.append(" and b.ancheyear = '"+year+"'");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	

	
	/**
	 * 变更信息(T_SCZT_BGXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryReportBgxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_BGXX ");
		StringBuffer sql=new StringBuffer(Conts.NDBGBGXX);
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String year=(String)map.get("year");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.pripid = '"+pripid+"'");
			}
			if(StringUtils.isNotBlank(year)){
				sql.append(" and b.ancheyear = '"+year+"'");
			}
			
		}else{
			return null;
		}
		//order by case when col is null then 0 else 1 end,col desc 
		//sql.append(" order by case when AltDate is null then 0 else 1 end desc,AltDate desc ");
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 出资及经济信息 
	 * 股东出资(T_NDBG_GDJCZXX)
	 * 企业经济(T_NDBG_QYZCZK)
	 * 对外担保(T_NDBG_DWDBXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryReportCzxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_CZYWXX ");
		StringBuffer sql=new StringBuffer(Conts.NDBGCZ);
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and b.priPid = '");
				sql.append(pripid);
				sql.append("'");
			}
			
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(),null);
	}
	
	public List queryYear(String entityNo) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		//select * from T_DM_QYLXDM 农民专业合作经济组织  农民专业合作社分支机构  个体
		//select * from T_DM_JJXZDM 外资economicProperty
		String sql="SELECT distinct a.AnCheYear FROM T_NDBG_QYBSJBXX a  where a.pripid='"+entityNo+"'";
		List map=dao.queryForList(sql, null);
		return map;
	}

	public List<Map> queryReportDwCzxx(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_CZYWXX ");
		StringBuffer sql=new StringBuffer(Conts.NDBGDWCZ);
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = '");
				sql.append(pripid);
				sql.append("'");
			}
			
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(),null);
	}
	
	public List<Map> queryReportXzxkxx(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_CZYWXX ");
		StringBuffer sql=new StringBuffer(Conts.NDBGDWCZ);
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = '");
				sql.append(pripid);
				sql.append("'");
			}
			
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(),null);
	}
}





