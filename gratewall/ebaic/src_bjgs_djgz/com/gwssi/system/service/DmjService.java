
package com.gwssi.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.rodimus.dao.DaoUtil;

/**
 * FIXME
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service(value = "dmjService")
@Deprecated
public class DmjService extends BaseService {

	//private final static Logger logger = Logger.getLogger(DmjService.class);

	/**
	 * 
	 * @desc 前台把需要显示的“区代码”传过来，就可以按配置的代码显示相应的下拉框
	 * domDistrict格式:   110108,110107,110106     为空表示全部加载
	 * @return List<Map>
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getDomDistrict(String domDistrict) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer("SELECT t.wb as text,dm as value,fdm as pvalue ");
		sql.append(" FROM t_pt_dmsjb t");
		sql.append(" WHERE t.dmb_id='CA01' AND fdm='110000' AND cj='3' AND t.qy_bj='1' ");
		if(StringUtils.isBlank(domDistrict)){
			sql.append(" ORDER by xh");
			return dao.queryForList(sql.toString(), null);
		}
		ArrayList<String> params= new ArrayList<String>();
		String [] dom = domDistrict.split(",");
		sql.append(" and ( ");
		for(int i = 0; i<dom.length ;i++){
			if(i == dom.length -1 ){
				sql.append(" t.dm =? ");
				continue;
			}
			sql.append(" t.dm =? or ");
			params.add(dom[i]);
		}
		params.add(dom[dom.length-1]);
		sql.append(" ) ");
		sql.append(" ORDER by xh");
		return dao.queryForList(sql.toString(), params);
	}
	
	/**
	 * 查询省市自治区
	 * 
	 * @author liuhailong
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryDomProvince() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select t.wb as text,t.dm as value from T_PT_DMSJB t where t.dmb_id='CA01' and t.cj=2 order by t.xh asc";
		List<Map> list = dao.queryForList(sql, null);
		return list;
	}
	
	/**
	 * 查询地级市
	 * 
	 * @author liuhailong
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryDomCities(String provinceZipCode)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select t.wb as text,t.dm as value from T_PT_DMSJB t where t.dmb_id='CA01' and t.fdm=? order by t.xh asc";
		List<String> params = new ArrayList<String>();
		params.add(provinceZipCode);
		List<Map> list = dao.queryForList(sql, params);
		return list;
	}
	
	/**
	 * 查询住所产权类型
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getDomOwnType() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "SELECT t.wb as text,dm as value FROM t_pt_dmsjb t WHERE t.dmb_id='WDDJ03' AND cj='1' AND t.qy_bj='1'  ORDER by xh";
		return dao.queryForList(sql, null);
	}

	
	/**
	 * 查询内资类型的国别
	 * 
	 * @param invType
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryContry(String invType) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select dm as value,wb as text from t_pt_dmsjb  t where  t.dmb_id='CA02' and qy_bj='1' ");
		if ("1".equals(invType)) {
			sql.append(" and dm  in('156') ");
		}
		sql.append(" order by xh");
		List<Map> list = dao.queryForList(sql.toString(), null);
		return list;
	}

	/**
	 * 查询证件类型
	 * 
	 * @param invType
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryCerType(String invType, String invCount)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select dm as value,wb as text from t_pt_dmsjb  t where  t.dmb_id='CB16' and qy_bj='1' ");
		if ("1".equals(invType)) {
			sql.append(" and dm  in('1','2','3','5','9') ");
		} else {
			if ("1".equals(invCount)) {
				sql.append(" and dm not in('1','2','3','5','9') ");
			}
		}

		sql.append(" order by xh");
		List<Map> list = dao.queryForList(sql.toString(), null);
		return list;
	}

	/**
	 * 查询内资股东类型。 用于主要人员添加页面
	 * 
	 * @param entId
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryInvestorInnerType() throws OptimusException {
		String sql = "select vl.wb as text,vl.dm as value from t_pt_dmsjb vl	where vl.dmb_id = 'CA24' and vl.dm  in ('11','12','13','14','15','41','42','50','92') and vl.qy_bj='1' order by vl.xh asc";
		List<Map> list = super.getPersistenceDAO().queryForList(sql, null);
		return list;
	}
	
	/**
	 * 查询外资股东类型。 用于主要人员添加页面
	 * 
	 * @param entId
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryInvestorForeignType() throws OptimusException {
		String sql = "select vl.wb as text,vl.dm as value from t_pt_dmsjb vl where vl.dmb_id = 'CA24' and vl.dm in ('15','31','32','33','34','42','92')  and vl.qy_bj='1' order by vl.xh asc";
		List<Map> list = super.getPersistenceDAO().queryForList(sql, null);
		return list;
	}
	
	/**
	 * 查询企业自然投资人。 用于企业联系人,财务负责人添加
	 * yzh
	 * @param gid
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryInvestorsForEntId(String gid,String except)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		List<String> params = new ArrayList<String>();
		
		StringBuffer sql = new StringBuffer()
		.append(" SELECT TEXT || '(' || CASE WHEN TYPE = '0' THEN '股东' ELSE '主要人员' END || ')' AS TEXT ,VALUE FROM (SELECT TEXT,VALUE,TYPE FROM (")
		.append(" select text,value,type from (SELECT IVT.INV AS TEXT,IVT.INVESTOR_ID AS VALUE, row_number() over( partition by cer_no,cer_type order by inv) as row_ ,'0' AS TYPE ")
		.append(" FROM BE_WK_INVESTOR IVT WHERE IVT.INV_TYPE IN ('20','35','36','91') AND IVT.GID = ?  ");
		params.add(gid);
		if(StringUtils.isNotBlank(except)){
			//已经担任监事的不能当作企业联系人
			sql.append(" AND (IVT.CER_TYPE || '##' || IVT.CER_NO) NOT IN")
			.append(" (SELECT M.CER_TYPE || '##' || M.CER_NO")
			.append(" FROM BE_WK_ENTMEMBER M, BE_WK_JOB J")
			.append(" WHERE J.ENTMEMBER_ID = M.ENTMEMBER_ID AND J.POSITION_TYPE = ? AND M.GID = ? ) ");
			params.add(except);
			params.add(gid);
		}
		
		sql.append(" )  where row_ = 1 ) UNION ALL")
		.append("  select text,value,type from (SELECT ENT.NAME AS TEXT,ENT.ENTMEMBER_ID AS VALUE, row_number() over( partition by cer_no,cer_type order by name) as row_ ,'1' AS TYPE")
		.append(" FROM BE_WK_ENTMEMBER ENT, BE_WK_JOB J WHERE J.ENTMEMBER_ID = ENT.ENTMEMBER_ID AND ENT.GID = ? ");
		params.add(gid);
		if(StringUtils.isNotBlank(except)){
			sql.append(" AND J.POSITION_TYPE <> ? ");
			params.add(except);
		}		
		
		sql.append(") where row_ = 1  ) ORDER BY TEXT");
		
		List<Map> list = dao.queryForList(sql.toString(), params);
		return list;
	}
	/**
	 * 主要人员页面查询股东姓名
	 * 将已有股东、董事的姓名带出来，选择姓名后，已经填写过的信息带出来；
	 * 1 董事： 可以从已有股东中选择，也可以自己添加
	 * 2 经理： 可以从已有股东和董事中选择，也可以自己添加
	 * 3 监事： 董事、高级管理人员（经理、财务）不得兼任监事。
	 * @param entmemberId
	 * @param gid
	 * @throws OptimusException 
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryInvesterNames(String gid, String positionType) throws OptimusException {
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("gid不能为空。");
		}
		//1、准备变量
		IPersistenceDAO dao = getPersistenceDAO();
		List<String> params = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		List<Map> list = null;
		
		//2、如果是新增董事， 可以从已有股东中选择，也可以自己添加
		if("1".equals(positionType)){
			sql.append(" select inv.investor_id as value,inv.inv as text ");
			sql.append(" from be_wk_investor inv where inv.inv_type in ('20','35','36','91') and inv.gid =? ");
			params.add(gid);
			list = dao.queryForList(sql.toString(), params);
		}
		//2、如果是新增经理，可以从已有股东和董事中选择，去除董事中有股东的，也可以自己添加
		if("2".equals(positionType)){
			params.add(gid);
			params.add(gid);
			params.add(gid);
			sql.append(" select inv.investor_id as value,inv.inv as text ");
			sql.append(" from be_wk_investor inv where inv.inv_type in ('20','35','36','91') and inv.gid = ? ");
			sql.append(" union all ");
			sql.append(" select ent.entmember_id as value,ent.name as text ");
			sql.append(" from be_wk_entmember ent left join be_wk_job job on ent.entmember_id = job.entmember_id ");
			sql.append(" where job.position_type = '1' and ent.gid = ? ");
			sql.append(" and ent.cer_no not in (select inv.cer_no from be_wk_investor inv where inv.inv_type in ('20','35','36','91') and inv.gid = ?) ");
			list = dao.queryForList(sql.toString(), params);
		}
		//2、如果是新增监事，可以从已有股东，但不能是董事和经理，也可以自己添加
		if("3".equals(positionType)){
			params.add(gid);
			params.add(gid);
			sql.append(" select inv.investor_id as value,inv.inv as text ");
			sql.append(" from be_wk_investor inv where inv.inv_type in ('20','35','36','91') and inv.gid = ? ");
			sql.append(" and inv.cer_no not in (select ent.cer_no from be_wk_entmember ent  where ent.gid = ?) ");
			list = dao.queryForList(sql.toString(), params);
		}
		
		if(list==null){
			throw new RuntimeException("根据传入的gid找不到业务数据。");
		}
		return list;
	}
	/**
	 * 查询经营范围标准用语分类表  用于经营范围标准用语维护中分类查询
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> querySortStandardMaintain() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "SELECT D.WB AS TEXT , D.DM AS VALUE FROM T_PT_DMSJB D WHERE D.DMB_ID = 'BE_SCOPE' AND D.CJ = '1'";
		return dao.queryForList(sql, null);
	}
	
	/**
	 * 
	 * @param gid
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryPostReceiver(String gid) throws OptimusException {
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("gid不能为空。");
		}
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select y.user_name as text,y.user_name as value")
			.append(" from t_pt_yh y, be_wk_requisition r, be_wk_le_rep l")
			.append("  where r.gid = l.gid and ")
			.append(" ((l.le_rep_cer_type = y.cer_type and l.le_rep_cer_no = y.cer_no)")
			.append(" or y.user_id = r.app_user_id) and y.yx_bj = '1' and r.gid = ?");
		List<String> params = new ArrayList<String>();
		params.add(gid);
		List<Map> list = dao.queryForList(sql.toString(), params);
		return list;
	}

	/**
	 * 用于查询该企业可以担任法定代表人的人选
	 * @param gid
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryLegalDelegated(String gid,String isBoardValue) throws OptimusException {
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("gid为空");
		}
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT E.NAME||'('||D.WB||')' AS TEXT, E.ENTMEMBER_ID||'_'||J.POSITION AS VALUE")
			.append(" FROM BE_WK_ENTMEMBER E LEFT JOIN BE_WK_JOB J ON J.ENTMEMBER_ID = E.ENTMEMBER_ID")
			.append(" LEFT JOIN T_PT_DMSJB D ON D.DM = J.POSITION AND D.DMB_ID = 'CB18'");
		
		if("1".equals(isBoardValue)){//设置董事会
			//董事长和经理
			sql.append(" WHERE J.POSITION IN ('431A', '436A') AND E.GID = ? ORDER BY J.POSITION");
		}else{
			//执行董事和经理
			sql.append("  WHERE J.POSITION IN ('432K','436A') AND E.GID = ? ORDER BY J.POSITION");
		}
		List<String> params = new ArrayList<String>();
		params.add(gid);
		return dao.queryForList(sql.toString(), params);
	}

	public void deleteLicense(String delId) {
		String sql = "DELETE FROM SYS_LICENSE_CONFIG S WHERE S.LIC_CONFIG_ID = ?";		
		DaoUtil.getInstance().execute(sql, delId);
	}
}
