package com.gwssi.comselect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.comselect.utils.StringUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value = "ycSelectService")
public class YCSelectService extends BaseService{
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getEntList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select t.regno,t.pripid as id, t.entid, t.entname, t.enttype_cn as enttype, t.estdate, t.regstate_cn as regstate, t.regstate as entstatus, t.opetype, t.enttype as type from dc_ra_mer_base_query t where entid in (select entid from DC_MS_ABNORMAL_DIRETORY a where a.type in ('1', '2'))");
		List params = new ArrayList();
		if (map!=null) {
			Object entname = map.get("entname");
			if (entname!=null) {
				if (entname.toString().length()>0) {
						sql.append(" and entname = ?");
						params.add(entname.toString().trim());
					}
				}
			
			
			Object regno = map.get("regno");
			if (regno!=null) {
				if (regno.toString().length()>0) {
					sql.append(" and (regno = ? or regno_old = ?)");
					params.add(regno.toString().trim());
					params.add(regno.toString().trim());
				}	
			}
			
			Object regorg = map.get("regorg");
			if (regorg!=null) {
				if (regorg.toString().length()>0) {
					sql.append(" and regorg = ?");
					params.add(regorg.toString().trim());
				}	
			}
			
			Object adminbrancode = map.get("adminbrancode");
			if (adminbrancode!=null) {
				if (adminbrancode.toString().length()>0) {
					sql.append(" and adminbrancode = ?");
					params.add(adminbrancode.toString().trim());
				}	
			}
			
			Object entstatus = map.get("entstatus");
			if (entstatus!=null) {
				if (entstatus.toString().length()>0) {
					sql.append(" and regstate = ?");
					params.add(entstatus.toString().trim());
				}	
			}
			
			String area = (String)map.get("area");
			if (area!=null) {
				if ("-1".equals(area)) { //全部
					sql.append(" and t.pripid in (select b.id from dc_ns_enterprise_list b)");
				}else if ("1".equals(area)) { //前海
					sql.append(" and t.pripid in (select b.id from dc_ns_enterprise_list b where b.addr_flag = ?)");
					params.add(area);
				}else if ("0".equals(area)) { //蛇口
					sql.append(" and  t.pripid in (select b.id from dc_ns_enterprise_list b where b.addr_flag = ?)");
					params.add(area);
				}	
			}
		}
		
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),params);
		LogUtil.insertLog("异常名录企业查询", sql.toString(), params.toString(), httpServletRequest, dao);
		return pageQueryForList;
		
	}
	
    /**
     * 异常名录查询列表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	public List<Map> getSXList(String entid, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		if(StringUtil.isNotEmpty(entid)){
			sql.append("select " 
					+ "b.id as id,"
					+ "b.entid,"
			        + "b.RESOLEUNIT,"
			        + " case b.ABNORMALTYPE"
			        + " when '1' then '通过登记的住所或经营场所无法联系'"
			        + " when '2' then '未在责令的期限内公示即时信息'"
			        + " when '3' then '公示企业信息隐瞒真实情况，弄虚作假'"
			        + " when '4' then '个体工商户年度报告隐瞒真实情况，弄虚作假'"
			        + " when '5' then '未按规定期限公示年度报告' end as ABNORMALTYPE,"
			        + "b.CREATETIME,"
			        + "b.REMOVETYPECN,"
			        + "b.REMOVETIME,"
			        + "b.REMARK,"
			        + "case b.TYPE "
			        + " when '1' then '拟载入'"
			        + " when '2' then '载入'"
			        + " when '3' then '移出' end as btype,"
			        + "b.REMOVEDEPT,"
			        + "b.PUBLICTIME"
			        + " from dc_ms_abnormal_diretory b"
			        + " where b.entid = ?");	
			List params = new ArrayList();
			params.add(entid);			
			List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),params);
			return pageQueryForList;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据sExtSequence获取异常目录的查询信息详情
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getSXQueryById(String sExtSequence) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select "
		        +"b.RESOLEUNIT,"
		        +" case b.ABNORMALTYPE"
	            +" when '1' then '通过登记的住所或经营场所无法联系'"
	            +" when '2' then '未在责令的期限内公示即时信息'"
	            +" when '3' then '公示企业信息隐瞒真实情况，弄虚作假'"
	            +" when '4' then '个体工商户年度报告隐瞒真实情况，弄虚作假'"
	            +" when '5' then '未按规定期限公示年度报告' end as ABNORMALTYPE,"
		        +"b.CREATETIME,"
		        +"b.REMOVETYPECN,"
		        +"b.REMOVETIME,"
		        +"b.REMARK,"
		        +"case b.TYPE "
		        +" when '1' then '拟载入'"
	            +" when '2' then '载入'"
	            +" when '3' then '移出' end as btype,"
		        +"b.REMOVEDEPT,"
		        +"b.PUBLICTIME"
		        +" from dc_ms_abnormal_diretory b"
		        +" where b.id = ?");
		List list=new ArrayList();
		list.add(sExtSequence);
		Map res = dao.pageQueryForList(sql.toString(), list).get(0);
		return res;
	}

}
