package com.gwssi.entSelect.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gwssi.entSelect.util.StringUtil;
import com.gwssi.entSelect.util.util;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;

public class EntSelectService2 extends BaseService {
	
	/*变更信息*/
	public List<Map> queryBianGen() throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		String sql = "SELECT  t.* FROM DC_RA_MER_ALTITEM t  where t.LASTDATE = sysdate-2  ";
		List<String> list = new ArrayList<String>();
		return dao.queryForList(sql, list);
	}
	
	/*变更详细信息(第二种需要查询额外表)*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> queryBiangengDetail2(String altitem, String regino,String main_tb_id) throws OptimusException{		
		String altitemcode = altitem;
		String id = main_tb_id;		
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = new StringBuffer();
		
		
		List ids  = new ArrayList();
		List params = new ArrayList();
		List weipaiDaiBiaoBianGenQianParam = new ArrayList();
		List weipaiDaiBiaoHouParam = new ArrayList();
		
		if("03".equals(altitemcode)|| "B7".equals(altitemcode)){ //负责人变更--首席代表变更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where (t.licflag = 'fddbr' or t.licflag = 'jyz') and t.regino = ?");
			params.add(regino);
		}else if("30".equals(altitemcode)||"D2".equals(altitemcode) || "D9".equals(altitemcode)){ //投资人变更
			//sql.append("select t.inv||' '||to_char(t.subconam,'fm999999999990.099999')||'（万元）'||to_char(t.conprop,'fm99990.009999')||'%' as content, t.bgtype,case EXCAFFSIGN  when '1' then '是' when '2' then '否' end as zshhr, case RESPONWAY when '1' then '普通合伙人' when '2' then '有限合伙人' end as  RESPONWAY  from dc_ra_mer_invest_ext t where 1=1 and t.regino = ? order by t.id");
			
			sql.append("select t.inv || ' ' || to_char(t.subconam, 'fm999999999990.099999') ||'（万元）' || to_char(t.conprop, 'fm99990.009999') || '%' as content,t.bgtype,case EXCAFFSIGN\n" +
					"         when '1' then\n" + 
					"          '是'\n" + 
					"         when '2' then\n" + 
					"          '否'\n" + 
					"       end as zshhr,\n" + 
					"       case RESPONWAY\n" + 
					"         when '1' then\n" + 
					"          '普通合伙人'\n" + 
					"         when '2' then\n" + 
					"          '有限合伙人'\n" + 
					"       end as RESPONWAY,\n" + 
					"       case when p.persname is not null then '委派代表:'||P.Persname else null end as persname\n" + 
					"  from dc_ra_mer_invest_ext t left join dc_ra_mer_persons_ext p  on t.id = p.id  and t.bgtype = p.bgtype \n" + 
					" where 1 = 1 \n " + 
					"   and t.regino = ? \n" + 
					" order by t.id ");

			
			params.add(regino);
		}
		/*else if("30".equals(altitemcode)||"D2".equals(altitemcode) || "D9".equals(altitemcode) || "11".equals(altitemcode)){ //投资人变更
			sql.append("select t.inv||' '||to_char(t.subconam,'fm999999999990.099999')||'（万元）'||to_char(t.conprop,'fm99990.009999')||'%' as content, t.bgtype from dc_ra_mer_invest_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}*/else if("46".equals(altitemcode) || "A3".equals(altitemcode) || "48".equals(altitemcode) || "B8".equals(altitemcode) || "47".equals(altitemcode) || "49".equals(altitemcode) || "70".equals(altitemcode) || "C7".equals(altitemcode)){ //主要人员变更
			sql.append("select wm_concat(t.persname ||'('||(func_getcode('C00005', a.POST)) ||')') as content, t.bgtype from dc_ra_mer_member_ext a left join dc_ra_mer_persons_ext t on t.id = a.id where t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("A8".equals(altitemcode) || "D6".equals(altitemcode) || "D7".equals(altitemcode) || "42".equals(altitemcode) || "15".equals(altitemcode) || "19".equals(altitemcode)){ //隶属企业名称变更
			sql.append("select t.entname as content, t.bgtype from dc_ra_mer_subent_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("A9".equals(altitemcode) || "B2".equals(altitemcode)){ //隶属企业地址变更
			sql.append("select t.dom as content, t.bgtype from dc_ra_mer_subent_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("B9".equals(altitemcode)){ //经营者表更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where t.licflag = 'jyz' and t.regino = ?");
			params.add(regino);
		}else if("B8".equals(altitemcode)){ //代表表更
			sql.append("select t.persname||'('||t.POST||')' as content, t.bgtype from V_OUTER_QY_ZHUYAORENYUANBIAO t where t.licflag in ('DSZ','QTDS','ZJL','JSCY','QT') and t.regino = ?");
			params.add(regino);
		}else if("C3".equals(altitemcode)){ //投资人姓名变更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where t.licflag = 'fddbr' and t.regino = ?");
			params.add(regino);
		}else if("C9".equals(altitemcode)){ //出资计划变更
			sql.append("select t.curactconam as content, t.bgtype from dc_ra_mer_invplan_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("D1".equals(altitemcode)){ //审批项目变更
			sql.append("select t.licname as content, t.bgtype from dc_ra_mer_license_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("E3".equals(altitemcode) || "E7".equals(altitemcode) || "73".equals(altitemcode)){ //清算组成员变更
			sql.append("select wm_concat(t.persname) as content, t.bgtype from dc_ra_mer_clmember_ext t where t.licflag in ('qsz','qszcy') and t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("E9".equals(altitemcode)){ //合伙人变更
			sql.append("select wm_concat(t.inv) as content, t.bgtype from dc_ra_mer_invest_ext t where 1=1 and t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("F4".equals(altitemcode)){ //执行事务合伙人/委派代表变更
			sql.append("select t.persname as content, t.bgtype from V_OUTER_QY_ZHUYAORENYUANBIAO t where t.licflag = 'hhrxx' and t.regino = ?");
			params.add(regino);
		}else if("43".equals(altitemcode)){ //指定联系人变更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where t.licflag = 'gslly' and t.regino = ?");
			params.add(regino);
		}else if("44".equals(altitemcode)){ //前置许可信息变更
			sql.append("select wm_concat(t.licname||'('||t.licno||')') as content, t.bgtype from dc_ra_mer_license_ext t where t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("72".equals(altitemcode)){ //分公司备案变更
			sql.append("select t.brname as content, t.bgtype from dc_ra_mer_branches_ext t where t.regino = ?");
			params.add(regino);
		}else{
			return null;
		}
		List<Map> resultList =  dao.queryForList(sql.toString(), params);
		
		
		if("E9".equals(altitemcode)) {
			 String sql2  ="select inv,subconam,conprop,zshhr from v_qy_chuzi_xinxi2 t where t.pripid= ? and t.regino = ? ";
			 ids.add(id);
			 ids.add(regino);
			List<Map> list =  dao.queryForList(sql2, ids);
			Map addMap = null;
			/*for (int i = 0; i < resultList.size(); i++) {
				addMap = new HashMap(); 
				addMap  = resultList.get(i);
				String contents = (String) addMap.get("content");
				System.out.println(contents);
			}*/
			
			addMap = new HashMap(); 
			addMap  = resultList.get(1);
			String contents = (String) addMap.get("content");
			String [] tempCon = contents.split(",");
			//System.out.println("=========> " +list.size());
			Map ret = null;
			StringBuffer retSb = new StringBuffer();
			for (int i = 0; i < tempCon.length; i++) {
				for (int j = 0; j < list.size(); j++) {
					ret = (Map) list.get(j);
					//System.out.println(ret);
					String inv = (String) ret.get("inv");
					BigDecimal conprop = (BigDecimal) ret.get("conprop");
					BigDecimal subconam = (BigDecimal) ret.get("subconam");
					String  zshhr = (String) ret.get("zshhr");
					
					if(inv.equals(tempCon[i])) {
						//System.out.println(zshhr);
						retSb.append(tempCon[i] +"  "+subconam +"（万元）" + conprop+"% <br/>");
					}
					
				}
			}
			
			Map mapReturn = resultList.get(1);
			mapReturn.put("content", retSb);
			resultList.add(mapReturn);
		}
		
		return resultList;
		
	}

	
	public void insert() throws OptimusException {
		
		 List<Map>  biangGenAsy  = this.queryBianGen();
		
		for (int i = 0; i < biangGenAsy.size(); i++) {
			
			Map mapParent = biangGenAsy.get(i);
			
			String altitemParent = (String) mapParent.get("altitem");
			String regino = (String) mapParent.get("regino");
			String main_tb_id = (String) mapParent.get("main_tb_id");
			String idParent = (String) mapParent.get("id");
			String pripidParent = (String) mapParent.get("pripid");
			String dateParent =  (String) mapParent.get("altdate");
			String altitem_cnParent = (String) mapParent.get("altitem_cn");
			
			List<Map> list = this.queryBiangengDetail2(altitemParent,regino,main_tb_id);
			List params = null;
			for (Map map : list) {
				
				params = new ArrayList ();
				//String altid = (String)map.get("altid");
			//	String pripid = (String)map.get("pripid");
				String altitem = (String)map.get("altitem");
				//String altitem_cn = (String)map.get("altitem_cn");
				String bgtype = (String)map.get("bgtype");
				String conents = (String)map.get("conents");
				String altbe = "";
				String altaf = "";
				
				if(bgtype.equals("1")) {
					 altbe  = 	conents; 
				}
				if(bgtype.equals("2")) {
					altaf  = 	conents; 
				}
				
				String altdate = (String)map.get("altdate");
				
				params.add(idParent);
				params.add(pripidParent);
				params.add(altitemParent);
				params.add(altitem_cnParent);
				params.add(altbe);
				params.add(altaf);
				params.add(dateParent);
				
				String sql = " insert into e_alter_recoder_sj\n" +
								"      (altid,\n" + 
								"       pripid,\n" + 
								"       altitem,\n" + 
								"       altitem_cn,\n" + 
								"       altbe,\n" + 
								"       altaf,\n" + 
								"       altdate,\n" + 
								"       s_ext_fromnode,\n" + 
								"       s_ext_datatime)\n" + 
								"    values\n" + 
								"      (?,\n" + 
								"       ?,\n" + 
								"       ?,\n" + 
								"       ?,\n" + 
								"       ?,\n" + 
								"       ?,\n" + 
								"       ?,\n" + 
								"       '440300',\n" + 
								"       sysdate)";
					
				
			}
		}
		
		
		
		
	}
}
