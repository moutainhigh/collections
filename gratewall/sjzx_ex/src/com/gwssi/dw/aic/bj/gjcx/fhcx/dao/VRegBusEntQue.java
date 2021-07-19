package com.gwssi.dw.aic.bj.gjcx.fhcx.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class VRegBusEntQue extends BaseTable
{
   public VRegBusEntQue()
   {
      
   }

   
   
   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   this.registerSQLFunction("queryEntList", DaoFunction.SQL_ROWSET, "复合查询");
	   this.registerSQLFunction("queryJcdmfx", DaoFunction.SQL_ROWSET, "选择代码集");
   }
   
   public SqlStatement queryJcdmfx(TxnContext request, DataBus inputData) throws TxnException{
	   
	   SqlStatement stmt = new SqlStatement();	   
	   String jc_dm_id =request.getString("select-key:jc_dm_id");
	   String sql = "select jcsjfx_dm,jcsjfx_mc from gz_dm_jcdm_fx  where jc_dm_id = '"+jc_dm_id+"'";
	   //System.out.println(sql);
	   stmt.addSqlStmt(sql);
	   return stmt;	   
   }
   
   public SqlStatement queryEntList(TxnContext request, DataBus inputData) throws TxnException{
	   
	   SqlStatement stmt = new SqlStatement();	 
	   
	   boolean hisWg = false;
	   boolean hisCase = false;
	   boolean hisMonTm = false;
	   boolean hisFf = false;	 
	   boolean hisMds = false;
	  
	   
	   
	   
//	   String sql = "";
//	   String sqlCount = "";
//	   String viewName = "";
	   int lineValue = 10;
	   // 根据其他业务企业名称
	   StringBuffer query_entname = new StringBuffer();
	   //查询根据条件查询网格的企业名称
	   StringBuffer query_wg_ent_name = new StringBuffer();
	   //查询根据条件查询商标的企业名称
	   StringBuffer query_tm_ent_name = new StringBuffer();
	   //查询根据条件查询案件的企业名称
	   StringBuffer query_case_ent_name = new StringBuffer();
	   //查询根据条件查询食品的企业名称
	   StringBuffer query_food_ent_name = new StringBuffer();
	   //查询根据条件查询商品的企业名称
	   StringBuffer query_mds_ent_name = new StringBuffer();
	   
	   
	   String ent_name =request.getString("select-key:ent_name");
	   String corp_rpt =request.getString("select-key:corp_rpt");
	   String reg_no =request.getString("select-key:reg_no");
	   String dom =request.getString("select-key:dom");
	   String pt_bus_scope =request.getString("select-key:pt_bus_scope");
	   String reg_cap_begain =request.getString("select-key:reg_cap_begain");
	   String reg_cap_end =request.getString("select-key:reg_cap_end");
	   String local_adm =request.getString("select-key:local_adm");
	   String cer_no =request.getString("select-key:cer_no");
	   String est_date_start =request.getString("select-key:est_date_start");
	   String est_date_end =request.getString("select-key:est_date_end");
	   String ent_type =request.getString("select-key:ent_type");	   
	   String ent_state =request.getString("select-key:ent_state");
	   String ent_sort =request.getString("select-key:ent_sort");
	   String industry_co =request.getString("select-key:industry_co");   	   
	   String organ_code =request.getString("select-key:organ_code"); 
	   String country =request.getString("select-key:country");
	   
	   //网格
	   String adm_org_id =request.getString("select-key:adm_org_id");	   
	   String adm_dep_id =request.getString("select-key:adm_dep_id");
	   String grid_id =request.getString("select-key:grid_id");
	   String grid_type =request.getString("select-key:grid_type");
	   if((adm_org_id!=null&&!adm_org_id.equals(""))||(adm_dep_id!=null&&!adm_dep_id.equals(""))
			   ||(grid_id!=null&&!grid_id.equals(""))||(grid_type!=null&&!grid_type.equals(""))){
		   hisWg = true;
		   query_wg_ent_name.append("select a.ent_title as ent_name from MON_MAIN_BASIC a,MON_BUSS_GRID b where a.grid_id = b.Grid_Id");
		   
		   if(adm_org_id!=null&&!adm_org_id.equals("")){		   
			   query_wg_ent_name.append(" and b.adm_org_id = '" + adm_org_id + "'");
		   }
		   if(adm_dep_id!=null&&!adm_dep_id.equals("")){		   
			   query_wg_ent_name.append(" and b.adm_dep_id = '" + adm_dep_id + "'");
		   }
		   if(grid_id!=null&&!grid_id.equals("")){		   
			   query_wg_ent_name.append(" and b.grid_id = '" + grid_id + "'");
		   }
		   if(grid_type!=null&&!grid_type.equals("")){		   
			   query_wg_ent_name.append(" and b.grid_type = '" + grid_type + "'");
		   }
	   }
	   
	   //商标
	   String tm_name =request.getString("select-key:tm_name"); 	 
	   String tm_reg_id =request.getString("select-key:tm_reg_id");
	   String tm_type =request.getString("select-key:tm_type");
	   String top_bra_sig =request.getString("select-key:top_bra_sig");			   
	   
	   if((tm_name!=null&&!tm_name.equals(""))||(tm_reg_id!=null&&!tm_reg_id.equals(""))
			   ||(tm_type!=null&&!tm_type.equals(""))||(top_bra_sig!=null&&!top_bra_sig.equals(""))){
		   hisMonTm = true;
		   query_tm_ent_name.append("select mtb.ent_title from MON_TM_Bas_Info mtb");
		   if(top_bra_sig!=null&&!top_bra_sig.equals("")){
			   query_tm_ent_name.append(" ,MON_TM_Ent_Rlt mer where mtb.TM_Reg_ID = mer.TM_Reg_ID");
			   query_tm_ent_name.append(" and mer.top_bra_sig='").append(top_bra_sig).append("' ");
		   }else {
			   query_tm_ent_name.append(" where 1=1 ");
		   }
		   
		   if(tm_reg_id!=null&&!tm_reg_id.equals("")){		   
			   query_tm_ent_name.append(" and mtb.tm_reg_id = '" + tm_reg_id + "'");
		   }
		   if(tm_type!=null&&!tm_type.equals("")){		   
			   query_tm_ent_name.append(" and mtb.tm_type = '" + tm_type + "'");
		   }
		   
		   if(tm_name!=null&&!tm_name.equals("")){		   
			   query_tm_ent_name.append(" and mtb.tm_name like '" + tm_name + "%'");
		   }
	   }	   
	   
	   //案件
	   String case_no =request.getString("select-key:case_no");
	   String case_type =request.getString("select-key:case_type");	 
	   if((case_no!=null&&!case_no.equals(""))||(case_type!=null&&!case_type.equals(""))){
		   hisCase = true;
		   query_case_ent_name.append("select ca.name as ent_name from Case_Bus_Case ca where 1=1");
		   if(case_no!=null&&!case_no.equals("")){		   
			   query_case_ent_name.append(" and ca.case_no = '" + case_no + "'");
		   }
		   if(case_type!=null&&!case_type.equals("")){		   
			   query_case_ent_name.append(" and ca.case_type = '" + case_type + "'");
		   }		   
	   }	   
	   
	   //食品
	   String food_name =request.getString("select-key:food_name");
	   String food_class_code =request.getString("select-key:food_class_code");
	   String ind_type_code =request.getString("select-key:ind_type_code");
	   if((food_name!=null&&!food_name.equals(""))
			   ||(food_class_code!=null&&!food_class_code.equals(""))
			   ||(ind_type_code!=null&&!ind_type_code.equals(""))
			   ){
		   hisFf = true;
		   query_food_ent_name.append("select fe.ent_name from FOOD_REC_ENT fe ");
		   if((food_name!=null&&!food_name.equals(""))
				   ||(food_class_code!=null&&!food_class_code.equals(""))){			   
			   query_food_ent_name.append(", FOOD_REC_FOOD ff ");
			   query_food_ent_name.append(" where (ff.Prod_Ent_ID=fe.Old_FOOD_ENT_ID or ff.Rec_Ent_ID=fe.Old_FOOD_ENT_ID) ")
			                      .append(" and (fe.food_ent_sign='0' OR fe.main_ent_sign='1' OR fe.main_ent_sign='0')"); 

		   }else{
               query_food_ent_name.append(" where (fe.food_ent_sign='0' OR fe.main_ent_sign='1' OR fe.main_ent_sign='0')"); 
		   }
		   
		   if(food_class_code!=null&&!food_class_code.equals("")){		   
			   query_food_ent_name.append(" and ff.food_class_code = '" + food_class_code + "'");
		   }
		   
		   if(ind_type_code!=null&&!ind_type_code.equals("")){		   
			   query_food_ent_name.append(" and fe.ind_type_code = '" + ind_type_code + "'");
		   }		
		   
		   if(food_name!=null&&!food_name.equals("")){		   
			   query_food_ent_name.append(" and ff.food_name like '" + food_name + "%'");
		   }   
	   }	   
	   
	   //商品
	   String speci_type_big_id =request.getString("select-key:speci_type_big_id");
	   String speci_ent_type_id =request.getString("select-key:speci_ent_type_id"); 
	   
	   String com_name =request.getString("select-key:com_name");
	   String com_co_id =request.getString("select-key:com_co_id");
	   String com_level =request.getString("select-key:com_level"); 
	   if((speci_type_big_id!=null&&!speci_type_big_id.equals(""))||(speci_ent_type_id!=null&&!speci_ent_type_id.equals(""))
			   ||(com_name!=null&&!com_name.equals(""))||(com_co_id!=null&&!com_co_id.equals(""))
			   ||(com_level!=null&&!com_level.equals(""))){
		   hisMds = true;
		   query_mds_ent_name.append("select me.ent_name from MDS_ENT_Ent_Inf me ");
		   
		   if((speci_type_big_id!=null&&!speci_type_big_id.equals(""))||(speci_ent_type_id!=null&&!speci_ent_type_id.equals(""))){
			   query_mds_ent_name.append(" ,MDS_ENT_Spe_Ent_Cat_Face ms ");
		   }
		   if((com_name!=null&&!com_name.equals(""))||(com_co_id!=null&&!com_co_id.equals(""))
				   ||(com_level!=null&&!com_level.equals(""))){
			   query_mds_ent_name.append(" ,MDS_COM_Bas_Inf mb ");
		   }
		   
		   query_mds_ent_name.append(" where 1=1");
		   if((speci_type_big_id!=null&&!speci_type_big_id.equals(""))||(speci_ent_type_id!=null&&!speci_ent_type_id.equals(""))){
			   query_mds_ent_name.append(" and me.mds_ent_ent_inf_id = ms.mds_ent_ent_inf_id ");
		   }
		   if((com_name!=null&&!com_name.equals(""))||(com_co_id!=null&&!com_co_id.equals(""))
				   ||(com_level!=null&&!com_level.equals(""))){
			   query_mds_ent_name.append(" and me.mds_ent_ent_inf_id = mb.mds_ent_ent_inf_id ");
		   }		   
		   		   
		   if(speci_type_big_id!=null&&!speci_type_big_id.equals("")){
			   query_mds_ent_name.append(" and ms.speci_type_big_id='").append(speci_type_big_id).append("'");
		   }
		   if(speci_ent_type_id!=null&&!speci_ent_type_id.equals("")){
			   query_mds_ent_name.append(" and ms.speci_ent_type_id='").append(speci_ent_type_id).append("'");
		   }
		   
		   if(com_name!=null&&!com_name.equals("")){		   
			   query_mds_ent_name.append(" and mb.com_name like '" + com_name + "%'");
		   }
		   if(com_co_id!=null&&!com_co_id.equals("")){		   
			   query_mds_ent_name.append(" and mb.com_co_id = '" + com_co_id + "'");
		   }
		   if(com_level!=null&&!com_level.equals("")){		   
			   query_mds_ent_name.append(" and mb.com_level = '" + com_level + "'");
		   }
	   }
	   
	   // 连接查询企业名称sql
	   if(hisWg||hisCase||hisMonTm||hisFf||hisMds){
		   if(hisWg){
			   if(query_entname.length()>0){
				   query_entname.append(" intersect ");
			   }
			   query_entname.append(query_wg_ent_name);
		   }
		   if(hisCase){
			   if(query_entname.length()>0){
				   query_entname.append(" intersect ");
			   }
			   query_entname.append(query_case_ent_name);
		   }
		   if(hisMonTm){
			   if(query_entname.length()>0){
				   query_entname.append(" intersect ");
			   }
			   query_entname.append(query_tm_ent_name);
		   }
		   if(hisFf){
			   if(query_entname.length()>0){
				   query_entname.append(" intersect ");
			   }
			   query_entname.append(query_food_ent_name);
		   }
		   if(hisMds){
			   if(query_entname.length()>0){
				   query_entname.append(" intersect ");
			   }
			   query_entname.append(query_mds_ent_name);
		   }
	   }
	   
	   //s 表示根据条件具体查询哪些表，第一位1标识查询内资，第二位1标识查询外资，第三位1标识查询私营，第四位1标识查询代表机构
	   //第五位1标识查询个体，第六位1标识查询非企，第七位1标识查询外埠，第八位1标识查询黑牌，第九位1标识查询无照经营。
	   String s = "111111111";
	   if((hisWg||hisCase||hisMonTm||hisFf||hisMds)
		||(industry_co!=null&&!industry_co.equals(""))   
	   ){
		   s = Integer.toBinaryString(Integer.parseInt(s,2)&Integer.parseInt("111110000",2));
	   }
	   log.debug("s9 = "+s);
	   if((ent_name!=null&&!ent_name.equals(""))
		||(reg_no!=null&&!reg_no.equals(""))
        ||(corp_rpt!=null&&!corp_rpt.equals(""))
        ||(ent_state!=null&&!ent_state.equals(""))
	   ){		   
		   s = Integer.toBinaryString(Integer.parseInt(s,2)&Integer.parseInt("111111110",2));
	   }	
	   log.debug("s8 = "+s);
	   if((dom!=null&&!dom.equals(""))
        ||(pt_bus_scope!=null&&!pt_bus_scope.equals(""))
		||(reg_cap_begain!=null&&!reg_cap_begain.equals(""))
		||(reg_cap_end!=null&&!reg_cap_end.equals(""))
		||(ent_type!=null&&!ent_type.equals(""))
	   ){	
		   s = Integer.toBinaryString(Integer.parseInt(s,2)&Integer.parseInt("111111100",2));
	   }
	   log.debug("s7 = "+s);
	   if (local_adm != null && !local_adm.equals("")) {
		   s = Integer.toBinaryString(Integer.parseInt(s,2)&Integer.parseInt("111111000",2));
	   }
	   log.debug("s6 = "+s);
	   if(cer_no!=null&&!cer_no.equals("")){		
		   s = Integer.toBinaryString(Integer.parseInt(s,2)&Integer.parseInt("111110111",2));
	   }
	   log.debug("s5 = "+s);
	   if((est_date_start!=null&&!est_date_start.equals(""))
		||(est_date_end!=null&&!est_date_end.equals(""))
	   ){	
		   s = Integer.toBinaryString(Integer.parseInt(s,2)&Integer.parseInt("111111101",2));
	   }
	   log.debug("s4 = "+s);
	   if(ent_sort!=null&&!ent_sort.equals("")){
		   StringBuffer sort=new StringBuffer();
		   if(ent_sort.indexOf("'NZ'")>-1){
			   sort.append("1");
		   }else{
			   sort.append("0");
		   }
		   if(ent_sort.indexOf("'WZ'")>-1){
			   sort.append("1");
		   }else{
			   sort.append("0");
		   }	
		   if(ent_sort.indexOf("'SQ'")>-1){
			   sort.append("1");
		   }else{
			   sort.append("0");
		   }	
		   if(ent_sort.indexOf("'JG'")>-1){
			   sort.append("1");
		   }else{
			   sort.append("0");
		   }		
		   if(ent_sort.indexOf("'GT'")>-1){
			   sort.append("1");
		   }else{
			   sort.append("0");
		   }		
		   if(ent_sort.indexOf("'FQ'")>-1){
			   sort.append("1");
		   }else{
			   sort.append("0");
		   }		
		   if(ent_sort.indexOf("'WB'")>-1){
			   sort.append("1");
		   }else{
			   sort.append("0");
		   }		
		   if(ent_sort.indexOf("'HP'")>-1){
			   sort.append("1");
		   }else{
			   sort.append("0");
		   }	
		   if(ent_sort.indexOf("'WZJY'")>-1){
			   sort.append("1");
		   }else{
			   sort.append("0");
		   }
		   s = Integer.toBinaryString(Integer.parseInt(s,2)&Integer.parseInt(sort.toString(),2));
	   }
	   log.debug("s3 = "+s);
	   if(organ_code!=null&&!organ_code.equals("")){		
		   s = Integer.toBinaryString(Integer.parseInt(s,2)&Integer.parseInt("111101000",2));
	   }
	   log.debug("s2 = "+s);
	   if(country!=null&&!country.equals("")){
		   s = Integer.toBinaryString(Integer.parseInt(s,2)&Integer.parseInt("010100000",2));
	   }

	   // 前面的位运算会将s开始部门的0全部去掉，以下步骤实现将位补齐
	   int l = s.length();
	   if(l<9){
		   for(int i=0;i<9-l;i++){
			   s = "0"+s;
		   }
	   }
	   log.debug("s1 = "+s);
   
	   StringBuffer tables = new StringBuffer();
	   
	   String qy = s.substring(0, 4);
	   
	   if(!"0000".equals(qy)){
		   if(tables.length()>0)
			   tables.append(" union all ");
		   tables.append("SELECT reg_bus_ent.reg_bus_ent_id,ent_name, lic_reg_no,corp_rpt,ent_state,ent_sort,organ_code FROM reg_bus_ent");		   
		   if(country!=null&&!country.equals("")){
			   tables.append(" ,reg_bus_for_cap");
		   }if(cer_no!=null&&!cer_no.equals("")){
			   tables.append(" ,reg_bus_ent_mem");   
		   }		   
		   tables.append(" where 1=1");
		   if(cer_no!=null&&!cer_no.equals("")){
			   tables.append(" and reg_bus_ent.reg_bus_ent_id=reg_bus_ent_mem.reg_bus_ent_id"); 
			   tables.append(" and cer_no='").append(cer_no).append("'");
		   }		
		   
		   if(ent_name!=null&&!ent_name.equals("")){		   
			   tables.append(" and (ent_name like '" + ent_name + "%' or ent_name like '北京" + ent_name + "%' or ent_name like '北京市" + ent_name + "%')");
		   }	
		   
		   if(reg_no!=null&&!reg_no.equals("")){		   
			   tables.append(" and (reg_no = '" + reg_no + "' or old_reg_no = '" + reg_no + "')");
		   }	 	   
		   
		   if(corp_rpt!=null&&!corp_rpt.equals("")){		   
			   tables.append(" and corp_rpt = '" + corp_rpt + "'");
		   }
		   if(organ_code!=null&&!organ_code.equals("")){		   
			   tables.append(" and organ_code = '" + organ_code + "'");
		   }	   
		   if(ent_state!=null&&!ent_state.equals("")){		   
			   tables.append(" and ent_state = '" + ent_state + "'");
		   }
		   
		   if(reg_cap_begain!=null&&!reg_cap_begain.equals("")){		   
			   tables.append(" and reg_cap >= " + reg_cap_begain );
		   }
		   if(reg_cap_end!=null&&!reg_cap_end.equals("")){		   
			   tables.append(" and reg_cap <= " + reg_cap_end );
		   }

		   if(est_date_start!=null&&!est_date_start.equals("")){		   
			   tables.append(" and est_date >= '" + est_date_start + "'");
		   }
		   if(est_date_end!=null&&!est_date_end.equals("")){		   
			   tables.append(" and est_date <= '" + est_date_end + "'");
		   }
		   
		   if(country!=null&&!country.equals("")){
			   tables.append(" and reg_bus_ent.reg_bus_ent_id=reg_bus_for_cap.reg_bus_ent_id");
			   String[] capF = country.split(",");
			   int capLength = capF.length;
			   if(capLength>lineValue){
				   tables.append(" and reg_bus_for_cap.country in (" + country + ")");
			   }else{
				   tables.append(" and ( reg_bus_for_cap.country=" +capF[0]);
				   for(int capI=1;capI<capF.length;capI++){
					   tables.append(" or reg_bus_for_cap.country=" +capF[capI]); 
				   }
				   tables.append(") ");
			   }	
		   }
		   
		   if(local_adm!=null&&!local_adm.equals("")){	
			   String[] admF = local_adm.split(",");
			   int admLength = admF.length;
			   if(admLength>lineValue){
				   tables.append(" and substr(local_adm,4,3) in (" + local_adm + ")");
			   }else{
				   tables.append(" and ( substr(local_adm,4,3)=" +admF[0]);
				   for(int admI=1;admI<admF.length;admI++){
					   tables.append(" or substr(local_adm,4,3)=" +admF[admI]); 
				   }
				   tables.append(") ");
			   }
		   }		   
		   if(ent_type!=null&&!ent_type.equals("")){			   
			   String[] typeF = ent_type.split(",");
			   int typeLength = typeF.length;
			   if(typeLength>lineValue){
				   tables.append(" and ent_type in (" + ent_type + ")");
			   }else{
				   tables.append(" and ( ent_type=" +typeF[0]);
				   for(int typeI=1;typeI<typeF.length;typeI++){
					   tables.append(" or ent_type=" +typeF[typeI]); 
				   }
				   tables.append(") ");
			   }		   	   
		   }	   
		   if(industry_co!=null&&!industry_co.equals("")){	
			   String[] coF = industry_co.split(",");
			   int coLength = coF.length;
			   if(coLength>lineValue){
				   tables.append(" and industry_co in (" + industry_co + ")");
			   }else{
				   tables.append(" and ( industry_co=" +coF[0]);
				   for(int coI=1;coI<coF.length;coI++){
					   tables.append(" or industry_co=" +coF[coI]); 
				   }
				   tables.append(") ");
			   }	
		   }	
		   
		   if(dom!=null&&!dom.equals("")){
			   tables.append(" and dom like '%" + dom + "%'");
		   }
		   
		   if(pt_bus_scope!=null&&!pt_bus_scope.equals("")){
			   tables.append(" and pt_bus_scope like '%" + pt_bus_scope + "%'");
		   }		   
		   if(!"1111".equals(qy)){
			   tables.append(" and (");
			   StringBuffer sort=new StringBuffer();
			   if('1'==s.charAt(0)){
				   if(sort.length()>0)
					   sort.append(" or ");
				   sort.append("ent_sort = 'NZ'");
			   }
			   if('1'==s.charAt(1)){
				   if(sort.length()>0)
					   sort.append(" or ");
				   sort.append("ent_sort = 'WZ'");
			   }
			   if('1'==s.charAt(2)){
				   if(sort.length()>0)
					   sort.append(" or ");
				   sort.append("ent_sort = 'SQ'");
			   }
			   if('1'==s.charAt(3)){
				   if(sort.length()>0)
					   sort.append(" or ");
				   sort.append("ent_sort = 'JG'");
			   }
			   tables.append(sort).append(")");
		   }
	   }
	   
	   if('1' == s.charAt(4)){
		   if(tables.length()>0)
			   tables.append(" union all ");
		   tables.append("SELECT reg_indiv_base.reg_indiv_base_id AS reg_bus_ent_id,indi_name AS ent_name,reg_no AS lic_reg_no,oper AS corp_rpt,indivi_state AS ent_state,'GT' AS ent_sort,'' AS organ_code FROM reg_indiv_base");
		   
		   if(cer_no!=null&&!cer_no.equals("")){		   
			   tables.append(" ,reg_indiv_operator where reg_indiv_base.reg_indiv_base_id=reg_indiv_operator.reg_indiv_base_id");
			   tables.append(" and cerno='").append(cer_no).append("'");
		   }else{
			   tables.append(" where 1=1");
		   }
		   
		   if(ent_name!=null&&!ent_name.equals("")){		   
			   tables.append(" and (indi_name like '" + ent_name + "%' or indi_name like '北京" + ent_name + "%' or indi_name like '北京市" + ent_name + "%')");
		   }	
		   
		   if(reg_no!=null&&!reg_no.equals("")){		   
			   tables.append(" and reg_no = '" + reg_no + "'");
		   }	 	   
		   
		   if(corp_rpt!=null&&!corp_rpt.equals("")){		   
			   tables.append(" and oper = '" + corp_rpt + "'");
		   }
		   
		   if(ent_state!=null&&!ent_state.equals("")){		   
			   tables.append(" and indivi_state = '" + ent_state + "'");
		   }
		   
		   if(reg_cap_begain!=null&&!reg_cap_begain.equals("")){		   
			   tables.append(" and reg_cap >= " + reg_cap_begain );
		   }
		   if(reg_cap_end!=null&&!reg_cap_end.equals("")){		   
			   tables.append(" and reg_cap <= " + reg_cap_end );
		   }

		   if(est_date_start!=null&&!est_date_start.equals("")){		   
			   tables.append(" and est_date >= '" + est_date_start + "'");
		   }
		   if(est_date_end!=null&&!est_date_end.equals("")){		   
			   tables.append(" and est_date <= '" + est_date_end + "'");
		   }		
		   
		   if(local_adm!=null&&!local_adm.equals("")){	
			   String[] admF = local_adm.split(",");
			   int admLength = admF.length;
			   if(admLength>lineValue){
				   tables.append(" and substr(local_adm,4,3) in (" + local_adm + ")");
			   }else{
				   tables.append(" and ( substr(local_adm,4,3)=" +admF[0]);
				   for(int admI=1;admI<admF.length;admI++){
					   tables.append(" or substr(local_adm,4,3)=" +admF[admI]); 
				   }
				   tables.append(") ");
			   }
		   }		   
		   if(ent_type!=null&&!ent_type.equals("")){			   
			   String[] typeF = ent_type.split(",");
			   int typeLength = typeF.length;
			   if(typeLength>lineValue){
				   tables.append(" and indi_type in (" + ent_type + ")");
			   }else{
				   tables.append(" and ( indi_type=" +typeF[0]);
				   for(int typeI=1;typeI<typeF.length;typeI++){
					   tables.append(" or indi_type=" +typeF[typeI]); 
				   }
				   tables.append(") ");
			   }		   	   
		   }	   
		   if(industry_co!=null&&!industry_co.equals("")){	
			   String[] coF = industry_co.split(",");
			   int coLength = coF.length;
			   if(coLength>lineValue){
				   tables.append(" and industry_co in (" + industry_co + ")");
			   }else{
				   tables.append(" and ( industry_co=" +coF[0]);
				   for(int coI=1;coI<coF.length;coI++){
					   tables.append(" or industry_co=" +coF[coI]); 
				   }
				   tables.append(") ");
			   }	
		   }  
		   
		   if(dom!=null&&!dom.equals("")){
			   tables.append(" and op_loc like '%" + dom + "%'");
		   }
		   
		   if(pt_bus_scope!=null&&!pt_bus_scope.equals("")){
			   tables.append(" and pt_bus_scope like '%" + pt_bus_scope + "%'");
		   }	   
	   }
	   
	   else if('1' == s.charAt(5)){
		   if(tables.length()>0)
			   tables.append(" union all ");
		   
		   StringBuffer fqTable = new StringBuffer();
		   StringBuffer fqTable1 = new StringBuffer();
		   StringBuffer fqCondition = new StringBuffer();
		   
		   fqTable.append("SELECT exc_que_reg_id AS reg_bus_ent_id,ent_name AS ent_name,reg_no AS lic_reg_no,corp_rpt AS corp_rpt,ent_state,'FQ' AS ent_sort,organ_code from exc_que_reg");		   
		   
		   if((reg_cap_begain!=null&&!reg_cap_begain.equals(""))
			||(reg_cap_end!=null&&!reg_cap_end.equals(""))
		   ){		   
			   fqTable1.append(fqTable);
			   fqTable.append(",Exc_Que_Civ where exc_que_reg.Civ_ID = Exc_Que_Civ.Civ_ID and Data_Sou='3'");
			   fqTable1.append(",Exc_Que_Auth where exc_que_reg.Civ_ID = Exc_Que_Auth.Civ_ID and Data_Sou='4'");
		   }else{
			   fqCondition.append(" where 1=1 ");
		   }
		   
		   if(reg_cap_begain!=null&&!reg_cap_begain.equals("")){		   
			   fqCondition.append(" and reg_cap >= " + reg_cap_begain );
		   }
		   
		   if(reg_cap_end!=null&&!reg_cap_end.equals("")){		   
			   fqCondition.append(" and reg_cap <= " + reg_cap_end );
		   }
		   
		   
		   if(ent_name!=null&&!ent_name.equals("")){		   
			   fqCondition.append(" and (ent_name like '" + ent_name + "%' or ent_name like '北京" + ent_name + "%' or ent_name like '北京市" + ent_name + "%')");
		   }	
		   
		   if(reg_no!=null&&!reg_no.equals("")){		   
			   fqCondition.append(" and reg_no = '" + reg_no + "'");
		   }	 	   
		   
		   if(corp_rpt!=null&&!corp_rpt.equals("")){		   
			   fqCondition.append(" and corp_rpt = '" + corp_rpt + "'");
		   }
		   if(organ_code!=null&&!organ_code.equals("")){		   
			   fqCondition.append(" and organ_code = '" + organ_code + "'");
		   }	   
		   if(ent_state!=null&&!ent_state.equals("")){		   
			   fqCondition.append(" and ent_state = '" + ent_state + "'");
		   }

		   if(est_date_start!=null&&!est_date_start.equals("")){		   
			   fqCondition.append(" and est_date >= '" + est_date_start + "'");
		   }
		   if(est_date_end!=null&&!est_date_end.equals("")){		   
			   fqCondition.append(" and est_date <= '" + est_date_end + "'");
		   }	
		   
		   if(local_adm!=null&&!local_adm.equals("")){	
			   String[] admF = local_adm.split(",");
			   int admLength = admF.length;
			   if(admLength>lineValue){
				   tables.append(" and substr(distr_code,4,3) in (" + local_adm + ")");
			   }else{
				   tables.append(" and ( substr(distr_code,4,3)=" +admF[0]);
				   for(int admI=1;admI<admF.length;admI++){
					   tables.append(" or substr(distr_code,4,3)=" +admF[admI]); 
				   }
				   tables.append(") ");
			   }
		   }		   
		   if(ent_type!=null&&!ent_type.equals("")){			   
			   String[] typeF = ent_type.split(",");
			   int typeLength = typeF.length;
			   if(typeLength>lineValue){
				   fqCondition.append(" and ent_type in (" + ent_type + ")");
			   }else{
				   fqCondition.append(" and ( ent_type=" +typeF[0]);
				   for(int typeI=1;typeI<typeF.length;typeI++){
					   fqCondition.append(" or ent_type=" +typeF[typeI]); 
				   }
				   fqCondition.append(") ");
			   }		   	   
		   }	     
		   
		   if(dom!=null&&!dom.equals("")){
			   fqCondition.append(" and dom like '%" + dom + "%'");
		   }
		   
		   if(pt_bus_scope!=null&&!pt_bus_scope.equals("")){
			   fqCondition.append(" and op_scope like '%" + pt_bus_scope + "%'");
		   }
		   
		   tables.append(fqTable).append(fqCondition);
		   if(fqTable1.length()!=0){
			   tables.append(" union all ").append(fqTable1).append(fqCondition);
		   }
	   }
	   
	   if('1' == s.charAt(6)){
		   if(tables.length()>0)
			   tables.append(" union all ");
		   tables.append("SELECT reg_bus_ent_oc_id AS reg_bus_ent_id,ent_name,reg_no AS lic_reg_no,corp_rpt,ent_state,'WB' AS ent_sort,'' AS organ_code FROM reg_bus_ent_oc");
	 	   tables.append(" where 1=1");   		   		   
		   if(cer_no!=null&&!cer_no.equals("")){		   
			   tables.append(" and cer_no='").append(cer_no).append("'");
		   }
		   
		   if(ent_name!=null&&!ent_name.equals("")){		   
			   tables.append(" and (ent_name like '" + ent_name + "%' or ent_name like '北京" + ent_name + "%' or ent_name like '北京市" + ent_name + "%')");
		   }	
		   
		   if(reg_no!=null&&!reg_no.equals("")){		   
			   tables.append(" and reg_no = '" + reg_no + "'");
		   }	 	   
		   
		   if(corp_rpt!=null&&!corp_rpt.equals("")){		   
			   tables.append(" and corp_rpt = '" + corp_rpt + "'");
		   }	   
		   if(ent_state!=null&&!ent_state.equals("")){		   
			   tables.append(" and ent_state = '" + ent_state + "'");
		   }
		   
		   if(reg_cap_begain!=null&&!reg_cap_begain.equals("")){		   
			   tables.append(" and reg_cap >= " + reg_cap_begain );
		   }
		   if(reg_cap_end!=null&&!reg_cap_end.equals("")){		   
			   tables.append(" and reg_cap <= " + reg_cap_end );
		   }

		   if(est_date_start!=null&&!est_date_start.equals("")){		   
			   tables.append(" and est_date >= '" + est_date_start + "'");
		   }
		   if(est_date_end!=null&&!est_date_end.equals("")){		   
			   tables.append(" and est_date <= '" + est_date_end + "'");
		   }		
		   		   
		   if(ent_type!=null&&!ent_type.equals("")){			   
			   String[] typeF = ent_type.split(",");
			   int typeLength = typeF.length;
			   if(typeLength>lineValue){
				   tables.append(" and ent_type in (" + ent_type + ")");
			   }else{
				   tables.append(" and ( ent_type=" +typeF[0]);
				   for(int typeI=1;typeI<typeF.length;typeI++){
					   tables.append(" or ent_type=" +typeF[typeI]); 
				   }
				   tables.append(") ");
			   }		   	   
		   }	   		    
		   
		   if(dom!=null&&!dom.equals("")){
			   tables.append(" and dom like '%" + dom + "%'");
		   }
		   
		   if(pt_bus_scope!=null&&!pt_bus_scope.equals("")){
			   tables.append(" and pt_bus_scope like '%" + pt_bus_scope + "%'");
		   }
	   }
	   
	   if('1' == s.charAt(7)){
		   if(tables.length()>0)
			   tables.append(" union all ");
		   tables.append("SELECT entid AS reg_bus_ent_id,entname AS ent_name,entregno AS lic_reg_no,lerep AS corp_rpt,iscan AS ent_state,'HP' AS ent_sort,'' AS organ_code FROM t_xy_dx");
		   tables.append(" where 1=1");  		   		   
		   if(cer_no!=null&&!cer_no.equals("")){	
			   tables.append(" and idno='").append(cer_no).append("'");
		   }
		   
		   if(ent_name!=null&&!ent_name.equals("")){		   
			   tables.append(" and (entname like '" + ent_name + "%' or entname like '北京" + ent_name + "%' or entname like '北京市" + ent_name + "%')");
		   }	
		   
		   if(reg_no!=null&&!reg_no.equals("")){		   
			   tables.append(" and entregno = '" + reg_no + "'");
		   }	 	   
		   
		   if(corp_rpt!=null&&!corp_rpt.equals("")){		   
			   tables.append(" and lerep = '" + corp_rpt + "'");
		   }	   
		   if(ent_state!=null&&!ent_state.equals("")){		   
			   tables.append(" and iscan = '" + ent_state + "'");
		   }
	   }	   
	   
	   
	   if('1' == s.charAt(8)){
		   if(tables.length()>0)
			   tables.append(" union all ");
		   tables.append("SELECT mon_buss_no_cert_id AS reg_bus_ent_id,'' AS ent_name,'' AS lic_reg_no,op_name AS corp_rpt,'' AS ent_state,'WZJY' AS ent_sort,'' AS organ_code FROM mon_buss_no_cert");
		   tables.append("where 1=1");  		   		   
		   if(cer_no!=null&&!cer_no.equals("")){	
			   tables.append(" and cert_no='").append(cer_no).append("'");
		   } 	   
		   
		   if(corp_rpt!=null&&!corp_rpt.equals("")){		   
			   tables.append(" and op_name = '" + corp_rpt + "'");
		   }	   
		   if(ent_state!=null&&!ent_state.equals("")){		   
			   tables.append(" and iscan = '" + ent_state + "'");
		   }
		   if(est_date_start!=null&&!est_date_start.equals("")){		   
			   tables.append(" and nocert_from >= '" + est_date_start + "'");
		   }
		   if(est_date_end!=null&&!est_date_end.equals("")){		   
			   tables.append(" and nocert_from <= '" + est_date_end + "'");
		   }	
		   if(dom!=null&&!dom.equals("")){
			   tables.append(" and dom like '%" + dom + "%'");
		   }
	   }
	   
	   if("000000000".equals(s)){
		   return null;
	   }else{
		   if("00000".equals(s.substring(0, 5))){
			   query_entname = new StringBuffer();
		   }
		   StringBuffer stringBfSql = new StringBuffer();
		   StringBuffer stringBfCount = new StringBuffer();	
		   if(query_entname.length()>0){
			   stringBfSql.append("select reg_bus_ent_id,lic_reg_no,ent_sort,ent_name,corp_rpt,organ_code,ent_state from ");
			   stringBfSql.append("(").append(tables).append(")");
			   stringBfSql.append(" where ent_name in(").append(query_entname).append(")");
			   stringBfCount.append("select count(reg_bus_ent_id) from ");
			   stringBfCount.append("(").append(tables).append(")");
			   stringBfCount.append(" where ent_name in(").append(query_entname).append(")");	   
		   }else{
			   stringBfSql.append(tables);
			   stringBfCount.append("select count(1) from (").append(tables).append(")");
		   }
		   stmt.addSqlStmt(stringBfSql.toString());
		   stmt.setCountStmt(stringBfCount.toString());		   
	   }
	   return stmt;	 
   }   
   /**
    * 执行SQL语句前的处理
    */
   public void prepareExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

   /**
    * 执行完SQL语句后的处理
    */
   public void afterExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

}
