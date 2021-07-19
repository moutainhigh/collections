package com.gwssi.otherselect.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.otherselect.model.BlackEntBO;

@Service(value = "blackEntService")
public class BlackEntService extends BaseService{
	
	@Autowired
	private JdbcTemplate jdbcTemplate2;
	
	
	public List<Map> queryList(Map<String, String> str) throws OptimusException {
		/*IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from V_BLACKENT t where t.pripid = ? ");
		params.add(str.get("priPid"));
		return typechage(dao.pageQueryForList(sql.toString(), params));*/
		
		StringBuffer sb = new StringBuffer();
		sb.append("	select top 100 ");
		sb.append("	s_ext_sequence as id, ");
		sb.append("	pripid, ");
		sb.append("	entname, ");
		sb.append("	regno, ");
		sb.append("	name, ");
		sb.append("	a.code_name as certype, ");
		sb.append("	 cerno, ");
		sb.append("	c.code_name as entstatus, ");
		sb.append("	dom, ");
		sb.append("	D.code_name as domdistrict, ");
		sb.append("	regcap, ");
		sb.append("	reccap, ");
		sb.append("	  e.code_name as regcapcur, ");
		sb.append("	  abuitem, ");
		sb.append("	  cbuitem, ");
		sb.append("	I.code_name as regorg, ");
		sb.append("	  esdate, ");
		sb.append("	  apprdate, ");
		sb.append("	  f.code_name as industryco, ");
		sb.append("	 opfrom, ");
		sb.append("	 opto, ");
		sb.append("	 opscope, ");
		sb.append("	  opscoandform, ");
		sb.append("	  b.code_name as enttype, ");
		sb.append("	g.code_name as entcat, ");
		sb.append("	tel, ");
		sb.append("	h.code_name as credlevel, ");
		sb.append("	  revdate, ");
		sb.append("	  illegact, ");
		sb.append("	  remark, ");
		sb.append("	  case s_ext_validflag when '1' then '有效' when '2' then '无效' end as s_ext_validflag, ");
		sb.append("	  j.code_name as s_ext_nodenum ");
		sb.append("	 from  ENTER_BLACK s ");
		sb.append("	left join CERTYPE_TMP a  on s.CERTYPE = a.CODE_VALUE ");
		sb.append("	left join ENTTYPE_TMP b  on s.ENTTYPE = b.CODE_VALUE ");
		sb.append("	left join ENTSATUS_TMP c  on s.ENTSTATUS = C.CODE_VALUE ");
		sb.append("	left join DOMDISTRICT_TMP D  on s.DOMDISTRICT = D.CODE_VALUE ");
	    sb.append("	left join REGCAPCUR_TMP E  on s.REGCAPCUR = E.CODE_VALUE ");
		sb.append("	left join INDUSTRYCO_TMP F  on s.INDUSTRYCO = F.CODE_VALUE ");
		sb.append("	left join ENTCAT_TMP G on s.ENTTYPE = G.code_value ");
		sb.append("	left join CREDLEVEL_TMP H on  s.CREDLEVEL =  H.code_value ");
		sb.append("	left join REGORG_TMP I on  s.REGORG = I.code_value ");
		sb.append("	left join DOMDISTRICT_TMP j on s.S_EXT_NODENUM = j.code_value where s.pripid = ? ");
		
		
		List params =new ArrayList();
		params.add(str.get("priPid"));
		Object [] info = (Object[]) params.toArray();
		List list = jdbcTemplate2.queryForList(sb.toString(), info);
		
		return typechage(list);
		

	}
	
	public List<Map> queryYR(Map<String, String> str) throws OptimusException {
		/*IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from V_YRENT t where t.pripid = ? ");
		params.add(str.get("priPid"));
		return typechage(dao.pageQueryForList(sql.toString(), params));*/
		
		
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("	select top 100  ");
	
		sb.append("	s_ext_sequence as id, ");
		sb.append("	pripid, ");
		sb.append("	entname, ");
		sb.append("	regno, ");
		sb.append("	name, ");
		sb.append("	a.code_name as certype, ");
		sb.append("	 cerno, ");
		sb.append("	inv, ");
		sb.append("	a.code_name as certype_inv, ");
		sb.append("	cerno_inv, ");
		sb.append("	c.code_name as entstatus, ");
		sb.append("	dom, ");
		sb.append("	D.code_name as domdistrict, ");
		sb.append("	regcap, ");
		sb.append("	reccap, ");
		sb.append("	  e.code_name as regcapcur, ");
		sb.append("	  abuitem, ");
		sb.append("	  cbuitem, ");
		sb.append("	  esdate, ");
		sb.append("	  apprdate, ");
		sb.append("	  f.code_name as industryco, ");
		sb.append("	 opfrom, ");
		sb.append("	 opto, ");
		sb.append("	 opscope, ");
		sb.append("	  opscoandform, ");
		sb.append("	  b.code_name as enttype, ");
		sb.append("	g.code_name as entcat, ");
		sb.append("	h.code_name as credlevel, ");
		sb.append("	I.code_name as regorg, ");
		sb.append("	tel, ");
		sb.append("	  remark, ");
		sb.append("	 remark_inv, ");
		sb.append("	 case s_ext_validflag when '1' then '有效' when '2' then '无效' end as s_ext_validflag, ");
		sb.append("	  j.code_name as s_ext_nodenum ");
		sb.append("	 from   ");
		sb.append("	ENTER_ONEPERSON s ");
		sb.append("	left join CERTYPE_TMP a  on s.CERTYPE = a.CODE_VALUE ");
		sb.append("	left join ENTTYPE_TMP b  on s.ENTTYPE = b.CODE_VALUE ");
		sb.append("	left join ENTSATUS_TMP c  on s.ENTSTATUS = C.CODE_VALUE ");
		sb.append("	left join DOMDISTRICT_TMP D  on s.DOMDISTRICT = D.CODE_VALUE ");
		sb.append("	left join REGCAPCUR_TMP E  on s.REGCAPCUR = E.CODE_VALUE ");
		sb.append("	left join INDUSTRYCO_TMP F  on s.INDUSTRYCO = F.CODE_VALUE ");
		sb.append("	left join ENTCAT_TMP G on s.ENTTYPE = G.code_value ");
		sb.append("	left join CREDLEVEL_TMP H on  s.CREDLEVEL =  H.code_value ");
		sb.append("	left join REGORG_TMP I on  s.REGORG = I.code_value ");
		sb.append("	left join DOMDISTRICT_TMP j on s.S_EXT_NODENUM = j.code_value where s.pripid = ?  AND S.cerno = ? ");
		
		List params =new ArrayList();
		params.add(str.get("priPid"));
		params.add(str.get("cerno"));
		//System.out.println(params);
		Object [] info = (Object[]) params.toArray();
		List list = jdbcTemplate2.queryForList(sb.toString(), info);
		//System.out.println(list);
		return typechage(list);
		
	}
	
	/*public List<Map> queryRenyuanxx(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from DC_BL_ENTER_BLACK_PERSON t where t.pripid = ? ");
		params.add(str.get("priPid"));
		List<Map> res = typechage(dao.pageQueryForList(sql.toString(), params));
		return res;
	}
	*/
	
	public List<Map> queryRenyuanxx(Map<String, String> str) throws OptimusException {
		
		
		List<String> params = new ArrayList<String>();
		StringBuffer sb= new StringBuffer();
		sb.append("select cerno,certype,dom,lerepsign,litdeg,name,nation,person_id as personId,polstand,pripid,s_ext_Nodenum sExtNodenum,s_ext_Sequence sExtSequence,s_Ext_Timestamp sExtTimestamp,s_Ext_Validflag sExtValidflag,sex, tel from ENTER_BLACK_PERSON t where t.pripid = ? ");
		params.add(str.get("priPid"));
		
		
		Object [] info = (Object[]) params.toArray();
		List list = jdbcTemplate2.queryForList(sb.toString(), info);
		return typechage(list);
		
	}
	
	//一人企业
	public List<Map> queryYRRenyuanxx(Map<String, String> str) throws OptimusException {
		/*IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from DC_BL_ENTER_ONEPERSON_PERSON t where t.pripid = ? ");
		params.add(str.get("priPid"));
		List<Map> res = typechage(dao.pageQueryForList(sql.toString(), params));
		
		return res;

		*/
		
		
		
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select  top 100  s_ext_nodenum,    ");
		sb.append(" pripid, person_id, name, ");
		sb.append(" certype , cerno, sex, natdate, dom, postalcode , tel, ");
		sb.append(" litdeg, nation, polstand, occst , offsign, accdside, lerepsign, character, country , arrchdate, cerlssdate, ");
		sb.append(" cervalper, chiofthedelsign, s_ext_timestamp , s_ext_batch, notorg, notdocno, s_ext_sequence sExtSequence, s_ext_validflag ");
		sb.append("  FROM ENTER_ONEPERSON_PERSON where  pripid = ? ");
		
		List params =new ArrayList();
		params.add(str.get("priPid"));
		System.out.println("参数   ===>  "  +str.get("priPid"));
		Object [] info = (Object[]) params.toArray();
		List list = jdbcTemplate2.queryForList(sb.toString(), info);
		
		return list;
		
	}
	
	/*public List<Map> queryTouzirenxx(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from V_BLACKENT_TOUZIREN t where t.pripid = ? ");
		params.add(str.get("priPid"));
		List<Map> res = typechage(dao.pageQueryForList(sql.toString(), params));
		return res;
	}*/
	//===YYYY
	public List<Map> queryTouzirenxx(Map<String, String> str) throws OptimusException {
		
		List<String> params = new ArrayList<String>();
		StringBuffer sb= new StringBuffer();
		params.add(str.get("priPid"));
		
		
		sb.append(" select  s_ext_sequence as id,  ");
		sb.append("  pripid,  ");
		sb.append("  inv,  ");
		sb.append("  a.code_name as invtype,  ");
		sb.append(" invtype as invtypecode,  ");
		sb.append("  b.code_name as certype,  ");
		sb.append("  cerno,  ");
		sb.append(" d.code_name as blictype,  ");
		sb.append("   blicno,  ");
		sb.append("  e.code_name as country,  ");
		sb.append("  f.code_name as currency,  ");
		sb.append(" subconam,  ");
		sb.append(" acconam,  ");
		sb.append("  subconamusd,  ");
		sb.append(" acconamusd,  ");
		sb.append(" conprop,  ");
		sb.append("  g.code_name as conform,  ");
		sb.append(" condate,  ");
		sb.append("  baldelper,  ");
		sb.append(" conam,  ");
		sb.append("  case exeaffsign when '1' then '执行' when '2' then '不执行' end as exeaffsign,  ");
		sb.append("  case s_ext_validflag when '1' then '有效' when '2' then '无效' end as s_ext_validflag,  ");
		sb.append("  h.code_name as sEextNodenum  ");
		sb.append(" FROM   ENTER_BLACK_INVESTMENT t   ");
		sb.append(" left join INVTYPE_TMP  a on t.INVTYPE = a.code_value  ");
		sb.append(" left join CERTYPE_TMP   b on t.CERTYPE = a.code_value  ");
		sb.append(" left join BLICTYPE_TMP  d on t.BLICTYPE = d.code_value  ");
		sb.append(" left join COUNTRY_TMP   e on t.COUNTRY = e.code_value  ");
		sb.append(" left join REGCAPCUR_TMP  f on t.CURRENCY = f.code_value  ");
		sb.append(" left join CONFORM_TMP  g on t.CONFORM = g.code_value  ");
		sb.append(" left join DOMDISTRICT_TMP  h on t.s_ext_nodenum  = h.code_value   ");
		sb.append(" where  pripid = ?   ");

		Object [] info = (Object[]) params.toArray();
		List list = jdbcTemplate2.queryForList(sb.toString(), info);
			
	    if(list!=null&&list.size()>0) {
	    	return typechage(list);
	    }else {
	    	return null;
	    }
	}
	
	
	
	public List<Map> queryYRTouzirenxx(Map<String, String> str) throws OptimusException {
		/*IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from V_YR_TOUZIREN t where t.pripid = ? ");
		params.add(str.get("priPid"));
		List<Map> res = typechage(dao.pageQueryForList(sql.toString(), params));
		return res;*/
		
		
		
		List<String> params = new ArrayList<String>();
		StringBuffer sb= new StringBuffer();
		params.add(str.get("priPid"));
		
		
		sb.append(" select  s_ext_sequence as id,  ");
		sb.append("  pripid,  ");
		sb.append("  inv,  ");
		sb.append("  a.code_name as invtype,  ");
		sb.append(" invtype as invtypecode,  ");
		sb.append("  b.code_name as certype,  ");
		sb.append("  cerno,  ");
		sb.append(" d.code_name as blictype,  ");
		sb.append("   blicno,  ");
		sb.append("  e.code_name as country,  ");
		sb.append("  f.code_name as currency,  ");
		sb.append(" subconam,  ");
		sb.append(" acconam,  ");
		sb.append("  subconamusd,  ");
		sb.append(" acconamusd,  ");
		sb.append(" conprop,  ");
		sb.append("  g.code_name as conform,  ");
		sb.append(" condate,  ");
		sb.append("  baldelper,  ");
		sb.append(" conam,  ");
		sb.append("  case exeaffsign when '1' then '执行' when '2' then '不执行' end as exeaffsign,  ");
		sb.append("  case s_ext_validflag when '1' then '有效' when '2' then '无效' end as s_ext_validflag,  ");
		sb.append("  h.code_name as sEextNodenum  ");
		sb.append(" FROM   ENTER_ONEPERSON_INVESTMENT t   ");
		sb.append(" left join INVTYPE_TMP  a on t.INVTYPE = a.code_value  ");
		sb.append(" left join CERTYPE_TMP   b on t.CERTYPE = a.code_value  ");
		sb.append(" left join BLICTYPE_TMP  d on t.BLICTYPE = d.code_value  ");
		sb.append(" left join COUNTRY_TMP   e on t.COUNTRY = e.code_value  ");
		sb.append(" left join REGCAPCUR_TMP  f on t.CURRENCY = f.code_value  ");
		sb.append(" left join CONFORM_TMP  g on t.CONFORM = g.code_value  ");
		sb.append(" left join DOMDISTRICT_TMP  h on t.s_ext_nodenum  = h.code_value   ");
		sb.append(" where t.s_ext_validflag ='1' and pripid = ?   ");

		Object [] info = (Object[]) params.toArray();
		List list = jdbcTemplate2.queryForList(sb.toString(), info);
		
		return list;
		
		
	}
	
/*	public List<Map> queryDiaoxiao(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from V_BLACKENT_DIAOXIAO t where t.pripid = ? ");
		params.add(str.get("priPid"));
		return typechage(dao.pageQueryForList(sql.toString(), params));
	}*/
	
	
	
	public List<Map> queryDiaoxiao(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
	//	sql.append("select * from V_BLACKENT_DIAOXIAO t where t.pripid = ? ");
		
		sql.append("SELECT  S_EXT_SEQUENCE as id, ");
		sql.append(" pripid, ");
		sql.append("   revdate, ");
		sql.append(" revbasis, ");
		sql.append(" case iscan when '1' then '注销' when '0' then '不注销' end as iscan, ");
		sql.append(" b.code_name as illegact  , ");
		sql.append(" case S_EXT_VALIDFLAG when '1' then '有效' when '0' then '无效' end as s_ext_validflag, ");
		sql.append(" c.code_name as  s_ext_nodenum");
		sql.append("  FROM ENTER_BLACK_REVOKE t   ");
		sql.append(" left join ILLEGACT_tmp  b on t.illegact = b.code_value  ");
		sql.append(" left join ILLEGACT_EXT_NODE_NUM_tmp  c on t.S_EXT_NODENUM = c.code_value  ");
		sql.append("  where t.pripid = ?");
				
				
				
		params.add(str.get("priPid"));
		
		Object [] info = (Object[]) params.toArray();
		List list = jdbcTemplate2.queryForList(sql.toString(), info);
		
		if(list!=null&&list.size()>0) {
			return typechage(list);
		}else {
			return null;
		}
		
	}
	
	/*
	 * 黑牌企业主要人员信息
	 */
	/*public Object queryrenyuanxx(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_BLACKENT_RENYUAN t where t.id = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	*/
	
	
	
	
	public Object queryrenyuanxx(String id) throws OptimusException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  s_ext_sequence as id,");
		sql.append(" pripid,");
		sql.append("  name,");
		sql.append("  a.code_name as certype,  ");
		sql.append(" cerno,");
		sql.append(" b.code_name as sex,  ");
		sql.append("  natdate,");
		sql.append(" dom,");
		sql.append("  postalcode,");
		sql.append(" tel,");
		sql.append(" c.code_name as litdeg,  ");
		sql.append(" d.code_name as nation,  ");
		sql.append(" e.code_name as polstand,  ");
		sql.append(" occst,");
		sql.append(" case offsign when '1' then '是' when '2' then '否' end as offsign,");
		sql.append("  accdside,");
		sql.append("  case lerepsign when '1' then '是' when '2' then '否' end as lerepsign,");
		sql.append("f.code_name as character,  ");
		sql.append("g.code_name as country,  ");
		sql.append("  arrchdate,");
		sql.append("  cerlssdate,");
		sql.append("  cervalper,");
		sql.append("  case chiofthedelsign when '1' then '是' when '2' then '否' end as chiofthedelsign,");
		sql.append("  notorg,");
		sql.append("  notdocno,");
		sql.append(" case s_ext_validflag when '1' then '有效' when '2' then '无效' end as s_ext_validflag,");
		sql.append(" h.code_name as  sExtNodenum  ");

		sql.append(" from enter_black_person t ");
		sql.append("left join CERTYPE_TMP a on t.certype = a.code_value  ");
		sql.append("left join SEX_TMP  b on t.sex = b.code_value  ");
		sql.append("left join LITDEG_TMP   c on t.LITDEG = c.code_value  ");
		sql.append("left join NATION_TMP   d on t.NATION = d.code_value  ");
		sql.append("left join POLSTAND_TMP   e on t.POLSTAND = e.code_value  ");
		sql.append("left join CHARACTER_TMP  f on t.CHARACTER = f.code_value  ");
		sql.append("left join COUNTRY_TMP g  on t.COUNTRY = g.code_value  ");
		sql.append("left join DOMDISTRICT_TMP  h on t.s_ext_nodenum  = h.code_value  ");
		sql.append("where t.s_ext_sequence = ? ");
		
		
		
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		Object [] info = (Object[]) paramlist.toArray();
		List list = jdbcTemplate2.queryForList(sql.toString(), info);
		
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}

	}
	
	/*
	 * 黑牌企业投资人信息
	 */
	/*public Object querytouzirenxx(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_BLACKENT_TOUZIREN t where t.id = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} */
	public Object querytouzirenxx(String id) throws OptimusException {
		List<String> params = new ArrayList<String>();
		StringBuffer sb= new StringBuffer();
		
		
		
		sb.append(" select  s_ext_sequence as id,  ");
		sb.append("  pripid,  ");
		sb.append("  inv,  ");
		sb.append("  a.code_name as invtype,  ");
		sb.append(" invtype as invtypecode,  ");
		sb.append("  b.code_name as certype,  ");
		sb.append("  cerno,  ");
		sb.append(" d.code_name as blictype,  ");
		sb.append("   blicno,  ");
		sb.append("  e.code_name as country,  ");
		sb.append("  f.code_name as currency,  ");
		sb.append(" subconam,  ");
		sb.append(" acconam,  ");
		sb.append("  subconamusd,  ");
		sb.append(" acconamusd,  ");
		sb.append(" conprop,  ");
		sb.append("  g.code_name as conform,  ");
		sb.append(" condate,  ");
		sb.append("  baldelper,  ");
		sb.append(" conam,  ");
		sb.append("  case exeaffsign when '1' then '执行' when '2' then '不执行' end as exeaffsign,  ");
		sb.append("  case s_ext_validflag when '1' then '有效' when '2' then '无效' end as s_ext_validflag,  ");
		sb.append("  h.code_name as sEextNodenum  ");
		sb.append(" FROM   ENTER_BLACK_INVESTMENT t   ");
		sb.append(" left join INVTYPE_TMP  a on t.INVTYPE = a.code_value  ");
		sb.append(" left join CERTYPE_TMP   b on t.CERTYPE = b.code_value  ");
		sb.append(" left join BLICTYPE_TMP  d on t.BLICTYPE = d.code_value  ");
		sb.append(" left join COUNTRY_TMP   e on t.COUNTRY = e.code_value  ");
		sb.append(" left join REGCAPCUR_TMP  f on t.CURRENCY = f.code_value  ");
		sb.append(" left join CONFORM_TMP  g on t.CONFORM = g.code_value  ");
		sb.append(" left join DOMDISTRICT_TMP  h on t.s_ext_nodenum  = h.code_value   ");
		sb.append(" where  t.s_ext_sequence = ? ");
		
		params.add(id);
		Object [] info = (Object[]) params.toArray();
		List list = jdbcTemplate2.queryForList(sb.toString(), info);
		
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
		
	} 
	
	
	
	
	
	/*
	 * 一人企业主要人员信息
	 */
	public Object queryyrrenyuanxx(String id) throws OptimusException {
		/*IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_YR_RENYUAN t where t.id = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);*/
		
		
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select top 100 s_ext_sequence as id,  ");
		sb.append(" pripid,  ");
		sb.append(" name,  ");
		sb.append(" a.code_name as certype,  ");
		sb.append(" cerno,  ");
		sb.append(" b.code_name as sex,  ");
		sb.append(" natdate,  ");
		sb.append(" dom,  ");
		sb.append(" postalcode,  ");
		sb.append(" tel,  ");
		sb.append(" c.code_name as litdeg,  ");
		sb.append(" d.code_name as nation,  ");
		sb.append(" e.code_name as polstand,  ");
		sb.append(" occst,  ");
		sb.append(" case offsign when '1' then '是' when '2' then '否' end as offsign,  ");
		sb.append(" accdside,  ");
		sb.append("case lerepsign when '1' then '是' when '2' then '否' end as lerepsign,  ");
		sb.append("f.code_name as character,  ");
		sb.append("g.code_name as country,  ");
		sb.append("arrchdate,  ");
		sb.append("cerlssdate,  ");
		sb.append("cervalper,  ");
		sb.append("case chiofthedelsign when '1' then '是' when '2' then '否' end as chiofthedelsign,  ");
		sb.append("notorg,  ");
		sb.append("notdocno,  ");
		sb.append("case s_ext_validflag when '1' then '有效' when '2' then '无效' end as s_ext_validflag,  ");
		sb.append(" h.code_name as  sExtNodenum  ");
		sb.append("from enter_oneperson_person t  ");
		sb.append("left join CERTYPE_TMP a on t.certype = a.code_value  ");
		sb.append("left join SEX_TMP  b on t.sex = b.code_value  ");
		sb.append("left join LITDEG_TMP   c on t.LITDEG = c.code_value  ");
		sb.append("left join NATION_TMP   d on t.NATION = d.code_value  ");
		sb.append("left join POLSTAND_TMP   e on t.POLSTAND = e.code_value  ");
		sb.append("left join CHARACTER_TMP  f on t.CHARACTER = f.code_value  ");
		sb.append("left join COUNTRY_TMP g  on t.COUNTRY = g.code_value  ");
		sb.append("left join DOMDISTRICT_TMP  h on t.s_ext_nodenum  = h.code_value  ");
		sb.append("where t.s_ext_sequence = ? ");
  
		
		
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		Object [] info = (Object[]) paramlist.toArray();
		
		List list  = jdbcTemplate2.queryForList(sb.toString(), info);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}

	}
	
	/*
	 * 一人企业投资人信息
	 */
	public Object queryyrtouzirenxx(String id) throws OptimusException {
		/*IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_YR_TOUZIREN t where t.id = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);*/
		
		
		
		
		
		
		StringBuffer sb = new StringBuffer();
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		
		sb.append(" select  s_ext_sequence as id,  ");
		sb.append("  pripid,  ");
		sb.append("  inv,  ");
		sb.append("  a.code_name as invtype,  ");
		sb.append(" invtype as invtypecode,  ");
		sb.append("  b.code_name as certype,  ");
		sb.append("  cerno,  ");
		sb.append(" d.code_name as blictype,  ");
		sb.append("   blicno,  ");
		sb.append("  e.code_name as country,  ");
		sb.append("  f.code_name as currency,  ");
		sb.append(" subconam,  ");
		sb.append(" acconam,  ");
		sb.append("  subconamusd,  ");
		sb.append(" acconamusd,  ");
		sb.append(" conprop,  ");
		sb.append("  g.code_name as conform,  ");
		sb.append(" condate,  ");
		sb.append("  baldelper,  ");
		sb.append(" conam,  ");
		sb.append("  case exeaffsign when '1' then '执行' when '2' then '不执行' end as exeaffsign,  ");
		sb.append("  case s_ext_validflag when '1' then '有效' when '2' then '无效' end as s_ext_validflag,  ");
		sb.append("  h.code_name as sEextNodenum  ");
		sb.append(" FROM   ENTER_ONEPERSON_INVESTMENT t   ");
		sb.append(" left join INVTYPE_TMP  a on t.INVTYPE = a.code_value  ");
		sb.append(" left join CERTYPE_TMP   b on t.CERTYPE = a.code_value  ");
		sb.append(" left join BLICTYPE_TMP  d on t.BLICTYPE = d.code_value  ");
		sb.append(" left join COUNTRY_TMP   e on t.COUNTRY = e.code_value  ");
		sb.append(" left join REGCAPCUR_TMP  f on t.CURRENCY = f.code_value  ");
		sb.append(" left join CONFORM_TMP  g on t.CONFORM = g.code_value  ");
		sb.append(" left join DOMDISTRICT_TMP  h on t.s_ext_nodenum  = h.code_value   ");
		sb.append(" where t.S_EXT_SEQUENCE = ?   ");
		
		Object [] info = (Object[]) paramlist.toArray();

		List list = jdbcTemplate2.queryForList(sb.toString(),info);
		
		
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
		
	} 
	
	/**
	 * 类型转换(把GregorianCalendar 转换为String)
	 * @return
	 */
	public List<Map> typechage(List<Map> list){
		List<Map> changtype =new ArrayList<Map>();
		for(Map<String,Object> map1:list){
			
			Map<String,Object> newMap= new HashMap<String,Object>();
			for(String s :map1.keySet()){
				Object obj=map1.get(s);
				
				if (obj!=null&&(obj.getClass()==GregorianCalendar.class)){
					GregorianCalendar gcal =(GregorianCalendar)obj;
					String format = "yyyy-MM-dd HH:mm:ss";
					SimpleDateFormat formatter = new SimpleDateFormat(format);
					newMap.put(s,  formatter.format(gcal.getTime()).toString());
				}else{
					newMap.put(s, map1.get(s));
				}
			}
			changtype.add(newMap);
		}
		
		return  changtype;
	}
}
