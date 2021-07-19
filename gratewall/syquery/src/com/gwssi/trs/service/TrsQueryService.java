package com.gwssi.trs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.trs.JsonUtil;
import com.gwssi.trs.PageResult;
import com.gwssi.util.CacheUtile;
import com.trs.client.Date;

@Service("trsQueryService")
public class TrsQueryService extends BaseService {

	/**
	 * 全文检索详细 信息库
	 * 
	 * @return
	 */
	private static String getDetail_datasourcekey() {
		Properties properties = ConfigManager.getProperties("optimus");

		String key = properties.getProperty("regDetail.datasourcekey");

		return key;
	}
   
	/*
	 * 内资外资隶属信息
	 */
	public Object querynzwzlishuxx(String brpripid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_QY_NZWZ_LISHU_XX t where trim(t.brpripid) = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(brpripid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	
    /*
     * 内资外资出资信息
     */
	public Object querynzwzchuzixx(String invid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_chuzi_xinxi t where t.invid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(invid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		if(list.size()>0){
			Map<String,Object> dataMap = list.get(0);
			
			return dataMap;
		}
		return null;
		/*String cerno = (String) dataMap.get("cerno");
		String mobel = (String) dataMap.get("mobel");
		String tel = (String) dataMap.get("tel");
		dataMap.put("cerno", CacheUtile.encrypt(cerno));
		dataMap.put("mobel", CacheUtile.encrypt(mobel));
		dataMap.put("tel", CacheUtile.encrypt(tel));*/
	} 
	
	   /*
     * 内资外资出资计划
     */
	public Object querynzwzchuzijihuaxx(String invid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_QY_NZWZ_CHUZIJIHUA t where t.recid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(invid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);
	} 
	
	
	
	 /*
     * 内资外资人员信息
     */
	public Object querynzwzrenyuanxx(String personid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_renyuan_xinxi t where t.personid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(personid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		
		if(list.size()>0 && list !=null){
			Map<String,Object> dataMap = list.get(0);
			return dataMap;
		}
		return null;
		/*if(list.size()>0){
		Map<String,Object> dataMap = list.get(0);
		String cerno = (String) dataMap.get("cerno");
		String mobel = (String) dataMap.get("mobel");
		String tel = (String) dataMap.get("tel");
		dataMap.put("cerno", CacheUtile.encrypt(cerno));
		dataMap.put("mobel", CacheUtile.encrypt(mobel));
		dataMap.put("tel", CacheUtile.encrypt(tel));
		return dataMap;
	}
	return null;*/
	} 
	
	
	 /*
     * 内资外资股权冻结信息
     */
	public Object querynzwzgqdongjiexx(String recid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_guquan_dongjie t where t.recid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(recid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	
	
	 /*
     * 内资外资股权出质信息
     */
	public Object querynzwzgqchuzhixx(String recid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_guquan_chuzhi t where t.recid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(recid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	
	
	
	 /*
     * 内资外资注销信息
     */
	public Object querynzwzzhuxiaoxx(String recid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_zhuxiaoxx t where t.recid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(recid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	
	
	 /*
     * 内资外资吊销信息
     */
	public Object querynzwzdiaoxiaoxx(String recid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_diaoxiaoxx t where t.recid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(recid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	
	
	/*
     * 内资外资变更信息
     */
	public Object querynzwzbiangengxx(String recid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_QY_NZWZ_BIANGENG_XX t where t.recid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(recid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);
		// dao.queryByKey(arg0, arg1)

	}
	/*
     * 变更信息
     * yzh
     */
	public List<Map> querynzwzduibixx(String pripid, String beforeAfter,String altitemcode, String regino) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		if(JsonUtil.Json.isEmpty()){
			JsonUtil.getJson("Bg");
		}
		
		HashMap data = (HashMap) JsonUtil.Json.get(altitemcode);
		String oldSql = (String)data.get("sql");
		StringBuffer newSql = new StringBuffer();
		
		if("45".equals(altitemcode)){
			if("1".equals(beforeAfter)){
				oldSql = oldSql.replace("*", " pripid,altdate,altitem,altbe,regino ");
				newSql.append(oldSql);
			}else if("2".equals(beforeAfter)){
				oldSql = oldSql.replace("*", " pripid,altdate,altitem,altaf,regino ");
				newSql.append(oldSql);
			}
		}else{
			newSql.append(oldSql);
			if("1".equals(beforeAfter)){
				newSql.append(" and bgtype='1' ").append(" and regino = ? ");
			}else if("2".equals(beforeAfter)){
				newSql.append(" and bgtype='2' ").append(" and regino = ? ");
			}
			
		}
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(pripid);
		paramlist.add(regino);
		
		System.out.println(newSql.toString());
		
		List<Map> list = dao.queryForList(newSql.toString(), paramlist);
		return list;
		/*StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_RA_MER_PERSONS_EXT t where t.main_tb_id = ?  and t.licflag = 'fddbr' ");
		if("before".equals(type)){
			sql.append(" and bgtype='1' ");
		}
		else if("after".equals(type)){
			sql.append(" and bgtype='2' ");
		}
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(pripid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list;*/
		// dao.queryByKey(arg0, arg1)
	}
	
	
	/*
	 * 内资外资许可信息
	 */
	public Object querynzwzxukexx(String licid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_xukexx t where t.recid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(licid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		if (list == null) {
			System.out.println("list  null------");
			return null;
		} else {
			System.out.println(list);
			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		}
	}
	
	
	/*
     * 内资外资迁移信息
     */
	public Object querynzwzqianyixx(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_qianru_qinachu_xx t where id = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 内资外资清算信息
     */
	public Object querynzwzqingsuanxx(String recid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_qingsuanxx t where t.recid = ?");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(recid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	/*
     * 内资外资成员清算信息
     */
	public Object querynzwzqingsuanchengyuanxx(String recid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_qingsuanchengyuanxx t where t.recid = ?");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(recid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 内资外资财务负责信息
     */
	public Object querynzwzcaiwufuzexx(String recid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_caiwu_fuzexx t where t.recid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(recid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 内资外资联络信息
     */
	public Object querynzwzlianluoxx(String recid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_lianluo_yuanxx t where t.recid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(recid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	/*
     * 集团成员信息
     */
	public Object queryjituanchengyuanxx(String grpmemid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_jituan_chengyuan_xx t where t.grpmemid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(grpmemid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 集团变更信息
     */
	public Object queryjituanbiangengxx(String recid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_jituan_biangeng_xx t where t.recid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(recid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 集团注销信息
     */
	public Object queryjituanzhuxiaoxx(String grpcanid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_jituan_zhuxiao_xx t where t.grpcanid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(grpcanid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 个体经营信息
     */
	public Object querygetijinyinxx(String personid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_gt_jinyin t where t.personid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(personid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 个体许可信息
     */
	public Object querygetixukexx(String licid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_geti_xuke_xx t where t.licid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(licid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 个体变更信息
     */
	public Object querygetibiangengxx(String changeid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_gt_biangeng_xx t where t.changeid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(changeid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 个体注销信息
     */
	public Object querygetizhuxiaoxx(String canid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_geti_zhuxiao_xx t where t.canid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(canid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 个体吊销信息
     */
	public Object querygetidiaoxiaoxx(String revid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_geti_diaoxiao_xx t where t.revid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(revid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}

	public List<Map> queryRenYuanNames(String pripid,String regino,String altitemcodes) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql =  new StringBuffer().append("select persname,bgtype from dc_ra_mer_persons_ext where main_tb_id = ? and regino = ? ");
		if("03".equals(altitemcodes)){
			sql.append(" and licflag = 'fddbr' ");
		}
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(pripid);
		paramlist.add(regino);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list;
	}

	public String queryFddbrPersonid(String priPid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		String sql = "select personid from v_qy_nzwz_renyuan_xinxi t where name = (select persname from dc_ra_mer_persons where  main_tb_id = ? and licflag = 'fddbr')and pripid = ? and licflag = 'fddbr' and rownum <= 1";
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(priPid);
		paramlist.add(priPid);
		List<Map> list = dao.queryForList(sql, paramlist);
		if(list.size()>0){
			String personid = (String)list.get(0).get("personid");
			return personid;
		}
		return "";
	}

	public String queryFaRenInfo(String priPid,String licFlag) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		String sql = "select personid from v_qy_faren_info t where persname = (select persname from dc_ra_mer_persons where  main_tb_id = ? and licflag = ?)and pripid = ? and rownum <= 1";
		ArrayList<String> paramlist = new ArrayList<String>();//v_qy_faren_info
		paramlist.add(priPid);
		if("1".equals(licFlag)){
			paramlist.add("fddbr");
		}else{
			paramlist.add("jyz");
		}
		paramlist.add(priPid);
		List<Map> list = dao.queryForList(sql, paramlist);
		if(list.size()>0){
			String personid = (String)list.get(0).get("personid");
			return personid;
		}
		return "";
	}

	public List<?> querynzwzchuzifsdata(String invid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		String sql = "select func_getcode('C00024',b.conform) as conform,b.conam from DC_RA_MER_INVEST a,DC_RA_MER_INVEST_TYPE b where a.id=b.gdrecordid and a.id = ?";
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(invid);
		List<Map> list = dao.queryForList(sql, paramlist);
		return list;
	}

	public List<Map> queryRenYuanPersonId(String pripid, String cerno) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		String sql = "select personid from v_qy_nzwz_renyuan_xinxi t where t.pripid = ? and t.cerno = ? and rownum <2";
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(pripid);
		paramlist.add(cerno);
		List<Map> list = dao.queryForList(sql, paramlist);
		return list;
	}

	public List<Map> queryFuZeRenPersonId(String pripid, String regino,String bgFlag) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		String sql = "select personid from dc_ra_mer_persons_ext t where t.main_tb_id = ? and t.regino = ? and t.licflag = 'fddbr' and t.bgType = ? ";
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(pripid);
		paramlist.add(regino);
		paramlist.add(bgFlag);
		List<Map> list = dao.queryForList(sql, paramlist);
		return list;
	}

	public Object querynzwzrenyuanxxdataBg(String personid, String regino) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_renyuan_xinxi_ext t where t.personid = ? and t.regino = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(personid);
		paramlist.add(regino);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Object querynzwzduozhengheyi(String pripid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_duozheng_xx t where t.pripid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(pripid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		if(list.size()>0){
			Map<String,Object> map = list.get(0);
			String issmalente = (String)map.get("issmalente");
			if("1".equals(issmalente)){
				issmalente = "是";
			}else if("0".equals(issmalente)){
				issmalente = "否";
			}else{
				issmalente = "";
			}
			map.put("issmalente", issmalente);
			
			
			//刻章申请
			String sealtypeOld = (String)map.get("sealtype");
			String[] sealtypeAl = null;
			if(sealtypeOld !=null && !"".equals(sealtypeOld)){
				sealtypeAl = map.get("sealtype").toString().split(",");	
				StringBuffer sealtype = new StringBuffer();
				for(int i=0;i<sealtypeAl.length;i++){
					if("1".equals(sealtypeAl[i])){
						sealtype.append("行政章,");
					}else if("2".equals(sealtypeAl[i])){
						sealtype.append("财务专用章,");
					}else if("3".equals(sealtypeAl[i])){
						sealtype.append("发票专用章,");
					}else if("4".equals(sealtypeAl[i])){
						sealtype.append("合同专用章,");
					}else if("5".equals(sealtypeAl[i])){
						sealtype.append("业务专用章,");
					}else if("5".equals(sealtypeAl[i])){
						sealtype.append("报关专用章,");
					}else{
						sealtype.append("");
					}
				}
				if(sealtype.length()>0){
					sealtype.deleteCharAt(sealtype.length()-1);
				}
				map.put("sealtype", sealtype.toString());
			}
			
			//是否申请电子刻章
			String sfsqdzyzOld = (String)map.get("sfsqdzyz");
			String[] sfsqdzyzAl = null;
			if(sfsqdzyzOld !=null && !"".equals(sfsqdzyzOld)){
				sfsqdzyzAl = sfsqdzyzOld.split(",");
				StringBuffer sfsqdzyz = new StringBuffer();
				for(int i=0;i<sfsqdzyzAl.length;i++){
					if("1".equals(sfsqdzyzAl[i])){
						sfsqdzyz.append("电子发票专用章,");
					}else if("2".equals(sfsqdzyzAl[i])){
						sfsqdzyz.append("电子行政章,");
					}else{
						sfsqdzyz.append("");
					}
				}
				if(sfsqdzyz.length()>0){
				sfsqdzyz.deleteCharAt(sfsqdzyz.length()-1);
				}
				map.put("sfsqdzyz", sfsqdzyz.toString());
			}
			return map;
		}
		return null;
	}
	
	/*
	 * 异常名录信息
	 */
	public Object queryyichangminglu(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_ent_yichang t where id = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
}
