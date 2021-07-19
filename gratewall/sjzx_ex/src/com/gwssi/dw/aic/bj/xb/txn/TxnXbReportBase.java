package com.gwssi.dw.aic.bj.xb.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.dw.aic.bj.xb.vo.XbReportBaseContext;

public class TxnXbReportBase extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods			= getAllMethod(
															TxnXbReportBase.class,
															XbReportBaseContext.class);

	// 数据表名称
	private static final String	TABLE_NAME			= "xb_report_base";

	// 查询列表
	private static final String	ROWSET_FUNCTION		= "query_xb_report_base";

	// 查询记录
	private static final String	SELECT_RESULT		= "select_xb_report_result";

	// 查询记录
	private static final String	SELECT_RESULT_CASE	= "select_xb_report_result_case";

	// 修改记录
	private static final String	SELECT_FAKE			= "select_xb_rpt_trea_fake_goods";

	// 增加记录
	private static final String	SELECT_GOOD			= "select_xb_rpt_confi_goods";

	// 删除记录
	private static final String	SELECT_GOOD_CASE	= "select_case_bus_mater";

	private static CodeMap		code				= PublicResource
															.getCodeFactory();

	/**
	 * 构造函数
	 */
	public TxnXbReportBase()
	{

	}

	/**
	 * 初始化函数
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * 查询举报信息列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60172001(XbReportBaseContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);

	}

	/**
	 * 查询举报信息列表用于登记
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60172011(XbReportBaseContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoXbReportBaseSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录集 VoXbReportBase result[] = context.getXbReportBases(
		// outputNode );

	}

	/**
	 * 查询举报处理信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60172002(XbReportBaseContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 修改记录的内容 VoXbReportBase xb_report_base = context.getXbReportBase(
		// inputNode );

		table.executeFunction(SELECT_RESULT_CASE, context, inputNode,
				outputNode);

		String ajxz = context.getRecord(outputNode).getValue("case_chr");

		String demand = context.getRecord(outputNode).getValue("puni_type");

		String[] demands = null;
		if (demand != null) {
			demands = demand.split(";", -1);
			 
			StringBuffer req = new StringBuffer();
			for (int i = 0; i < demands.length; i++) {
				String codeValue = code.getCodeDesc(context, "12315处罚方式DFAT89",
						demands[i]);
				req.append(codeValue == null ? demands[i] : codeValue);
				if (i != demands.length - 1) {
					req.append("\\ ");
				}
			}
			context.getRecord(outputNode).setValue("puni_type", req.toString());
		}

		String send_gaverment_fir = context.getRecord(outputNode).getValue(
				"accp_tranf_auth_fir");
		String send_gaverment_sec = context.getRecord(outputNode).getValue(
				"accp_tranf_auth_sec");

		StringBuffer req2 = new StringBuffer();

		String str1 = send_gaverment_fir == null ? send_gaverment_fir
				: code.getCodeDesc(context, "12315移送机关大小类DFAT102",
						send_gaverment_fir);
		String str2 = send_gaverment_sec == null ? send_gaverment_sec
				: code.getCodeDesc(context, "12315移送机关大小类DFAT102",
						send_gaverment_sec);
		if (str1 == null) {
			str1 = send_gaverment_fir;
		}
		if (send_gaverment_fir != null) {
			req2.append(str1);
		}
		if (str2 == null) {
			str2 = send_gaverment_sec;
		}
		if (str2 != null && !"".equals(str2)) {
			req2.append("\\");
		}

		if (send_gaverment_sec != null) {
			req2.append(str2);
		}
		context.getRecord(outputNode).setValue("send_gaverment",
				req2.toString());

		/**
		 * 加入调解情况
		 */
		DataBus bus = new DataBus();
		table.executeFunction("select_xb_report_result_case_for_dcqk", context,
				inputNode, "bus");
		String bri_st = context.getRecord("bus").getValue("bri_st");
		context.getRecord(outputNode).setValue("bri_st", bri_st);
		// log.debug(">>>>>>>>bri_st="+bri_st);

		// Recordset rs= context.getRecordset(outputNode);
		// //若案件中没找到就到12315中找
		// if(rs==null||rs.size()==0)
		// {
		// table.executeFunction( SELECT_RESULT, context, inputNode, outputNode
		// );
		// context.getRecord(outputNode).setValue("case_flag","N");
		//		
		//
		//		
		// String
		// pen_type=codetoname(context,context.getRecord(outputNode).getValue("pen_type"),"12315_处罚方式编码表DFXB11");;
		// context.getRecord(outputNode).setValue("pen_type",pen_type);
		// }
		// else{
		// context.getRecord(outputNode).setValue("case_flag","Y");
		// }
		//		
		//		
		//		
		// String state1 = "";
		// String state2 = "";
		// String state = "";
		//		
		// try {
		// state1 = code.getCodeDesc(context, "12315_补充资料编码表DFXB07",
		// context.getRecord(outputNode).getValue("rpt_addi_material"));
		// state2 = code.getCodeDesc(context, "12315_补充资料编码表DFXB07",
		// context.getRecord(outputNode).getValue("rpt_addi_material_detail"));
		// } catch (TxnException e) {
		// e.printStackTrace();
		// }
		// if(state1==null||state1=="")
		// {
		// if(state2==null||state2=="")
		// state="";
		// else
		// state=state2;
		// }
		// else
		// {
		// if(state2==null||state2=="")
		// state=state1;
		// else
		// state=state1+"--"+state2;
		// }
		// context.getRecord(outputNode).setValue("rpt_a", state);
		//		
		// String rpt_case_type =
		// context.getRecord(outputNode).getString("rpt_case_type");
		// //System.out.println("rpt_case_type>>1>>"+rpt_case_type);
		// if(null!=rpt_case_type && rpt_case_type.length()>= 2){
		// rpt_case_type = rpt_case_type.substring(0, 2);
		// //System.out.println("rpt_case_type>>2>>"+rpt_case_type);
		// StringBuffer sbf = new StringBuffer();
		// sbf.append(rpt_case_type);
		// sbf.reverse();
		// rpt_case_type = sbf.toString();
		// String temp = String.valueOf(Integer.parseInt(rpt_case_type, 2));//);
		// log.debug("案件类型rpt_case_type:"+temp);
		//			
		// context.getRecord(outputNode).setValue("rpt_case_type", temp+"0");
		// }

		/**
		 * @ 违法行为_拆分3级 start break_dl 14 \1304 \010104 12315违法行为大类DFAT100 by
		 *   dingL 2010-12-29 11:44:57
		 */

		StringBuffer taget_goods_class = new StringBuffer();
		String sorce_goods_class = context.getRecord(outputNode).getValue(
				"break_dl");
		if (sorce_goods_class != null && !"".equals(sorce_goods_class)) {
			String sorce_goods_class_fir = sorce_goods_class.substring(0, 2);
			String sorce_goods_class_sec = sorce_goods_class.substring(0, 4);

			String goods_class_thir = sorce_goods_class == null ? sorce_goods_class
					: code.getCodeDesc(context, "12315违法行为大类DFAT100",
							sorce_goods_class);
			String goods_class_sec = sorce_goods_class_sec == null ? sorce_goods_class_sec
					: code.getCodeDesc(context, "12315违法行为大类DFAT100",
							sorce_goods_class_sec);
			String goods_class_fir = sorce_goods_class_fir == null ? sorce_goods_class_fir
					: code.getCodeDesc(context, "12315违法行为大类DFAT100",
							sorce_goods_class_fir);
			taget_goods_class.append(goods_class_fir);
			if (goods_class_sec != null) {
				taget_goods_class.append("\\");
			}
			taget_goods_class.append(goods_class_sec);
			if (goods_class_thir != null) {
				taget_goods_class.append("\\");
			}
			taget_goods_class.append(goods_class_thir);
			context.getRecord(outputNode).setValue("break_dl",
					taget_goods_class.toString());
		}

		/**
		 * @ 违法行为_拆分3级 end by dingL 2010-12-29 11:44:57
		 */

	}

	/**
	 * 增加举报信息信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60172003(XbReportBaseContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 增加记录的内容 VoXbReportBase xb_report_base = context.getXbReportBase(
		// inputNode );
		table.executeFunction(SELECT_GOOD_CASE, context, inputNode, outputNode);
		Recordset rs = context.getRecordset(outputNode);
		if (rs == null || rs.size() == 0) {
			table.executeFunction(SELECT_GOOD, context, inputNode, outputNode);
		}
	}

	/**
	 * 查询举报信息用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60172004(XbReportBaseContext context) throws TxnException
	{

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("select_xb_report_base", context, inputNode,
				outputNode);
		if (context.getRecord(outputNode).getValue("feed_requ_unit")
				.equals("1")) {
			if (!context.getRecord(outputNode).getValue("feed_requ").equals("")) {
				context.getRecord(outputNode).setValue(
						"limit",
						context.getRecord(outputNode).getValue("feed_requ")
								+ " 小时");
			} else {
				context.getRecord(outputNode).setValue("limit", "");
			}
		} else {
			if (!context.getRecord(outputNode).getValue("feed_requ").equals("")) {
				context.getRecord(outputNode).setValue(
						"limit",
						context.getRecord(outputNode).getValue("feed_requ")
								+ " 日");
			} else {
				context.getRecord(outputNode).setValue("limit", "");
			}
		}

		String demand = context.getRecord(outputNode).getValue("demand");
		if (demand != null) {
		String[] demands = demand.split(",", -1);
		StringBuffer req = new StringBuffer();
		for (int i = 0; i < demands.length; i++) {
			String codeValue = code.getCodeDesc(context, "12315举报人要求DFAT85",
					demands[i]);
			req.append(codeValue == null ? demands[i] : codeValue);
			if (i != demands.length - 1) {
				req.append(", ");
			}
		}
		
		 context.getRecord(outputNode).setValue("demand", req.toString());
		}

		// 问题大类 拼接处理 ques_fir ques_sec by dingL 2010年12月22日12:19:09
		String send_gaverment_fir = context.getRecord(outputNode).getValue(
				"ques_fir");
		String send_gaverment_sec = context.getRecord(outputNode).getValue(
				"ques_sec");

		StringBuffer req2 = new StringBuffer();

		String str1 = send_gaverment_fir == null ? send_gaverment_fir : code
				.getCodeDesc(context, "12315问题大类DFAT68", send_gaverment_fir);
		String str2 = send_gaverment_sec == null ? send_gaverment_sec : code
				.getCodeDesc(context, "12315问题大类DFAT68", send_gaverment_sec);
		if (str1 == null) {
			str1 = send_gaverment_fir;
		}
		if (send_gaverment_fir != null) {
			req2.append(str1);
		}
		if (str2 == null) {
			str2 = send_gaverment_sec;
		}
		if (str2 != null && !"".equals(str2)) {
			req2.append("\\");
		}

		if (send_gaverment_sec != null) {
			req2.append(str2);
		}
		context.getRecord(outputNode).setValue("ques_fir_ques_sec",
				req2.toString());

		/**
		 * @ 业务分类 start 拼接处理b.work_type1,b.work_type,work_type2 by dingL
		 *   2010-12-23 14:07:26
		 */

		String work_type = context.getRecord(outputNode).getValue("work_type");
		String work_type1 = context.getRecord(outputNode)
				.getValue("work_type1");
		String work_type2 = context.getRecord(outputNode)
				.getValue("work_type2");

		StringBuffer req3 = new StringBuffer();

		String str_work_type_1 = work_type == null ? "" : code.getCodeDesc(
				context, "12315系统类型DFAT94", work_type);
		String str_work_type_2 = work_type1 == null ? "" : code.getCodeDesc(
				context, "12315业务类型DFAT71", work_type1);
		String str_work_type_3 = work_type2 == null ? "" : code.getCodeDesc(
				context, "12315业务分类2DFAT117", work_type2);

		if (str_work_type_1 == null) {
			req3.append("");
		} else {
			req3.append(str_work_type_1);
		}

		if (str_work_type_2 != null) {
			req3.append("\\");
		}

		if (str_work_type_2 == null) {
			req3.append("");
		} else {
			req3.append(str_work_type_2);
		}
		/*
		 * if(str_work_type_3!=null){ req3.append("\\"); }
		 * req3.append(str_work_type_3);
		 */

		context.getRecord(outputNode)
				.setValue("work_type_all", req3.toString());
		/**
		 * @ 业务分类 end 拼接处理b.work_type1,b.work_type,work_type2 by dingL
		 *   2010-12-23 14:07:26
		 */

		// String flag_test;
		// String state1 = "";
		// String state2 = "";
		// String state3 = "";
		// String state4 = "";
		// String state5 = "";
		// String state = "";
		//		
		// try {
		// state1 = code.getCodeDesc(context, "12315_商品服务类别编码表DFXB30",
		// context.getRecord(outputNode).getValue("inv_ob_type_mid"));
		// state2 = code.getCodeDesc(context, "12315_商品服务类别编码表DFXB30",
		// context.getRecord(outputNode).getValue("inv_ob_type_small"));
		// state3 = code.getCodeDesc(context, "12315_商品服务类别编码表DFXB30",
		// context.getRecord(outputNode).getValue("inv_ob_type_most_small"));
		// System.out.println("inv_ob_type_mid:"+context.getRecord(outputNode).getValue("inv_ob_type_mid")+"/state1:"+state1);
		// System.out.println("inv_ob_type_small:"+context.getRecord(outputNode).getValue("inv_ob_type_small")+"/state1:"+state2);
		// System.out.println("inv_ob_type_most_small:"+context.getRecord(outputNode).getValue("inv_ob_type_most_small")+"/state1:"+state3);
		//			
		// } catch (TxnException e) {
		// e.printStackTrace();
		// }
		// context.getRecord(outputNode).setValue("result_r",
		// state1+"--"+state2+"--"+state3);
		//		
		// try {
		// state4 = code.getCodeDesc(context, "12315_举报内容级别DFXB14",
		// context.getRecord(outputNode).getValue("acci_lev"));
		// state5 = code.getCodeDesc(context, "12315_级别细分编码表DFXB15",
		// context.getRecord(outputNode).getValue("acci_lev_detail"));
		// } catch (TxnException e) {
		// e.printStackTrace();
		// }
		//		
		// if(state4==null||state4=="")
		// {
		// if(state5==null||state5=="")
		// state="";
		// else
		// state=state5;
		// }
		// else
		// {
		// if(state5==null||state5=="")
		// state=state4;
		// else
		// state=state4+"--"+state5;
		// }
		// context.getRecord(outputNode).setValue("acci_l", state);
		//		
		//		
		// String
		// per_accq=codetoname(context,context.getRecord(outputNode).getValue("rpt_per_accq"),"12315_举报人要求编码表DFXB16");;
		// context.getRecord(outputNode).setValue("rpt_per_accq",per_accq);
		//	
		// flag_test=context.getRecord(inputNode).getValue("flag");
		// if("M".equalsIgnoreCase(flag_test))
		// throw new TxnErrorException("99999","从登记表中进入的");
		/**
		 * 商品分类_拆分3级 --\修理服务类\电器修理\冰箱修理 --0202\020201\02020103 bydingL
		 * 2010-12-28 14:54:23
		 */
		StringBuffer taget_goods_class = new StringBuffer();
		String sorce_goods_class = context.getRecord(outputNode).getValue(
				"goods_class");
		if (sorce_goods_class != null && !"".equals(sorce_goods_class)) {
			String sorce_goods_class_fir = sorce_goods_class.substring(0, 4);
			String sorce_goods_class_sec = sorce_goods_class.substring(0, 6);

			String goods_class_thir = sorce_goods_class == null ? sorce_goods_class
					: code.getCodeDesc(context, "12315商品服务分类DFAT66",
							sorce_goods_class);
			String goods_class_sec = sorce_goods_class_sec == null ? sorce_goods_class_sec
					: code.getCodeDesc(context, "12315商品服务分类DFAT66",
							sorce_goods_class_sec);
			String goods_class_fir = sorce_goods_class_fir == null ? sorce_goods_class_fir
					: code.getCodeDesc(context, "12315商品服务分类DFAT66",
							sorce_goods_class_fir);
			taget_goods_class.append(goods_class_fir);
			if (goods_class_sec != null) {
				taget_goods_class.append("\\");
			}
			taget_goods_class.append(goods_class_sec);
			if (goods_class_thir != null) {
				taget_goods_class.append("\\");
			}
			taget_goods_class.append(goods_class_thir);
			context.getRecord(outputNode).setValue("taget_goods_class",
					taget_goods_class.toString());

		}

	}

	public String codetoname(XbReportBaseContext context, String in_come,
			String code_list)
	{
		StringBuffer test = new StringBuffer();
		char[] a = in_come.toCharArray();
		int k = 0;
		if (in_come == null || in_come == "")
			return "";
		else {
			for (int i = 0; i < a.length; i++) {

				int j = i + 1;
				if (a[i] == '1') {
					if (k != 0)
						test.append(",  ");
					try {
						if (j < 10)
							test.append(code.getCodeDesc(context, code_list,
									"0" + j));
						else
							test.append(code.getCodeDesc(context, code_list,
									String.valueOf(j)));
					} catch (TxnException e) {
						e.printStackTrace();
					}
					k = 1;

				}
			}
			return test.toString();
		}
	}

	/**
	 * 删除举报信息信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60172005(XbReportBaseContext context) throws TxnException
	{
		// BaseTable table = TableFactory.getInstance().getTableObject( this,
		// TABLE_NAME );
		// 删除记录的主键列表 VoXbReportBasePrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		// table.executeFunction( DELETE_FUNCTION, context, inputNode,
		// outputNode );
	}

	/**
	 * 查询假冒伪劣物资信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60172006(XbReportBaseContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoXbReportBasePrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FAKE, context, inputNode, outputNode);
		// 查询到的记录内容 VoXbReportBase result = context.getXbReportBase( outputNode
		// );
	}

	/**
	 * 重载父类的方法，用于替换交易接口的输入变量 调用函数
	 * 
	 * @param funcName
	 *            方法名称
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数");
		}

		// 执行
		XbReportBaseContext appContext = new XbReportBaseContext(context);
		invoke(method, appContext);
	}
}
