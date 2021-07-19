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
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods			= getAllMethod(
															TxnXbReportBase.class,
															XbReportBaseContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME			= "xb_report_base";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION		= "query_xb_report_base";

	// ��ѯ��¼
	private static final String	SELECT_RESULT		= "select_xb_report_result";

	// ��ѯ��¼
	private static final String	SELECT_RESULT_CASE	= "select_xb_report_result_case";

	// �޸ļ�¼
	private static final String	SELECT_FAKE			= "select_xb_rpt_trea_fake_goods";

	// ���Ӽ�¼
	private static final String	SELECT_GOOD			= "select_xb_rpt_confi_goods";

	// ɾ����¼
	private static final String	SELECT_GOOD_CASE	= "select_case_bus_mater";

	private static CodeMap		code				= PublicResource
															.getCodeFactory();

	/**
	 * ���캯��
	 */
	public TxnXbReportBase()
	{

	}

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * ��ѯ�ٱ���Ϣ�б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60172001(XbReportBaseContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);

	}

	/**
	 * ��ѯ�ٱ���Ϣ�б����ڵǼ�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60172011(XbReportBaseContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoXbReportBaseSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoXbReportBase result[] = context.getXbReportBases(
		// outputNode );

	}

	/**
	 * ��ѯ�ٱ�������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60172002(XbReportBaseContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoXbReportBase xb_report_base = context.getXbReportBase(
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
				String codeValue = code.getCodeDesc(context, "12315������ʽDFAT89",
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
				: code.getCodeDesc(context, "12315���ͻ��ش�С��DFAT102",
						send_gaverment_fir);
		String str2 = send_gaverment_sec == null ? send_gaverment_sec
				: code.getCodeDesc(context, "12315���ͻ��ش�С��DFAT102",
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
		 * ����������
		 */
		DataBus bus = new DataBus();
		table.executeFunction("select_xb_report_result_case_for_dcqk", context,
				inputNode, "bus");
		String bri_st = context.getRecord("bus").getValue("bri_st");
		context.getRecord(outputNode).setValue("bri_st", bri_st);
		// log.debug(">>>>>>>>bri_st="+bri_st);

		// Recordset rs= context.getRecordset(outputNode);
		// //��������û�ҵ��͵�12315����
		// if(rs==null||rs.size()==0)
		// {
		// table.executeFunction( SELECT_RESULT, context, inputNode, outputNode
		// );
		// context.getRecord(outputNode).setValue("case_flag","N");
		//		
		//
		//		
		// String
		// pen_type=codetoname(context,context.getRecord(outputNode).getValue("pen_type"),"12315_������ʽ�����DFXB11");;
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
		// state1 = code.getCodeDesc(context, "12315_�������ϱ����DFXB07",
		// context.getRecord(outputNode).getValue("rpt_addi_material"));
		// state2 = code.getCodeDesc(context, "12315_�������ϱ����DFXB07",
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
		// log.debug("��������rpt_case_type:"+temp);
		//			
		// context.getRecord(outputNode).setValue("rpt_case_type", temp+"0");
		// }

		/**
		 * @ Υ����Ϊ_���3�� start break_dl 14 \1304 \010104 12315Υ����Ϊ����DFAT100 by
		 *   dingL 2010-12-29 11:44:57
		 */

		StringBuffer taget_goods_class = new StringBuffer();
		String sorce_goods_class = context.getRecord(outputNode).getValue(
				"break_dl");
		if (sorce_goods_class != null && !"".equals(sorce_goods_class)) {
			String sorce_goods_class_fir = sorce_goods_class.substring(0, 2);
			String sorce_goods_class_sec = sorce_goods_class.substring(0, 4);

			String goods_class_thir = sorce_goods_class == null ? sorce_goods_class
					: code.getCodeDesc(context, "12315Υ����Ϊ����DFAT100",
							sorce_goods_class);
			String goods_class_sec = sorce_goods_class_sec == null ? sorce_goods_class_sec
					: code.getCodeDesc(context, "12315Υ����Ϊ����DFAT100",
							sorce_goods_class_sec);
			String goods_class_fir = sorce_goods_class_fir == null ? sorce_goods_class_fir
					: code.getCodeDesc(context, "12315Υ����Ϊ����DFAT100",
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
		 * @ Υ����Ϊ_���3�� end by dingL 2010-12-29 11:44:57
		 */

	}

	/**
	 * ���Ӿٱ���Ϣ��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60172003(XbReportBaseContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoXbReportBase xb_report_base = context.getXbReportBase(
		// inputNode );
		table.executeFunction(SELECT_GOOD_CASE, context, inputNode, outputNode);
		Recordset rs = context.getRecordset(outputNode);
		if (rs == null || rs.size() == 0) {
			table.executeFunction(SELECT_GOOD, context, inputNode, outputNode);
		}
	}

	/**
	 * ��ѯ�ٱ���Ϣ�����޸�
	 * 
	 * @param context
	 *            ����������
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
								+ " Сʱ");
			} else {
				context.getRecord(outputNode).setValue("limit", "");
			}
		} else {
			if (!context.getRecord(outputNode).getValue("feed_requ").equals("")) {
				context.getRecord(outputNode).setValue(
						"limit",
						context.getRecord(outputNode).getValue("feed_requ")
								+ " ��");
			} else {
				context.getRecord(outputNode).setValue("limit", "");
			}
		}

		String demand = context.getRecord(outputNode).getValue("demand");
		if (demand != null) {
		String[] demands = demand.split(",", -1);
		StringBuffer req = new StringBuffer();
		for (int i = 0; i < demands.length; i++) {
			String codeValue = code.getCodeDesc(context, "12315�ٱ���Ҫ��DFAT85",
					demands[i]);
			req.append(codeValue == null ? demands[i] : codeValue);
			if (i != demands.length - 1) {
				req.append(", ");
			}
		}
		
		 context.getRecord(outputNode).setValue("demand", req.toString());
		}

		// ������� ƴ�Ӵ��� ques_fir ques_sec by dingL 2010��12��22��12:19:09
		String send_gaverment_fir = context.getRecord(outputNode).getValue(
				"ques_fir");
		String send_gaverment_sec = context.getRecord(outputNode).getValue(
				"ques_sec");

		StringBuffer req2 = new StringBuffer();

		String str1 = send_gaverment_fir == null ? send_gaverment_fir : code
				.getCodeDesc(context, "12315�������DFAT68", send_gaverment_fir);
		String str2 = send_gaverment_sec == null ? send_gaverment_sec : code
				.getCodeDesc(context, "12315�������DFAT68", send_gaverment_sec);
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
		 * @ ҵ����� start ƴ�Ӵ���b.work_type1,b.work_type,work_type2 by dingL
		 *   2010-12-23 14:07:26
		 */

		String work_type = context.getRecord(outputNode).getValue("work_type");
		String work_type1 = context.getRecord(outputNode)
				.getValue("work_type1");
		String work_type2 = context.getRecord(outputNode)
				.getValue("work_type2");

		StringBuffer req3 = new StringBuffer();

		String str_work_type_1 = work_type == null ? "" : code.getCodeDesc(
				context, "12315ϵͳ����DFAT94", work_type);
		String str_work_type_2 = work_type1 == null ? "" : code.getCodeDesc(
				context, "12315ҵ������DFAT71", work_type1);
		String str_work_type_3 = work_type2 == null ? "" : code.getCodeDesc(
				context, "12315ҵ�����2DFAT117", work_type2);

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
		 * @ ҵ����� end ƴ�Ӵ���b.work_type1,b.work_type,work_type2 by dingL
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
		// state1 = code.getCodeDesc(context, "12315_��Ʒ�����������DFXB30",
		// context.getRecord(outputNode).getValue("inv_ob_type_mid"));
		// state2 = code.getCodeDesc(context, "12315_��Ʒ�����������DFXB30",
		// context.getRecord(outputNode).getValue("inv_ob_type_small"));
		// state3 = code.getCodeDesc(context, "12315_��Ʒ�����������DFXB30",
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
		// state4 = code.getCodeDesc(context, "12315_�ٱ����ݼ���DFXB14",
		// context.getRecord(outputNode).getValue("acci_lev"));
		// state5 = code.getCodeDesc(context, "12315_����ϸ�ֱ����DFXB15",
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
		// per_accq=codetoname(context,context.getRecord(outputNode).getValue("rpt_per_accq"),"12315_�ٱ���Ҫ������DFXB16");;
		// context.getRecord(outputNode).setValue("rpt_per_accq",per_accq);
		//	
		// flag_test=context.getRecord(inputNode).getValue("flag");
		// if("M".equalsIgnoreCase(flag_test))
		// throw new TxnErrorException("99999","�ӵǼǱ��н����");
		/**
		 * ��Ʒ����_���3�� --\���������\��������\�������� --0202\020201\02020103 bydingL
		 * 2010-12-28 14:54:23
		 */
		StringBuffer taget_goods_class = new StringBuffer();
		String sorce_goods_class = context.getRecord(outputNode).getValue(
				"goods_class");
		if (sorce_goods_class != null && !"".equals(sorce_goods_class)) {
			String sorce_goods_class_fir = sorce_goods_class.substring(0, 4);
			String sorce_goods_class_sec = sorce_goods_class.substring(0, 6);

			String goods_class_thir = sorce_goods_class == null ? sorce_goods_class
					: code.getCodeDesc(context, "12315��Ʒ�������DFAT66",
							sorce_goods_class);
			String goods_class_sec = sorce_goods_class_sec == null ? sorce_goods_class_sec
					: code.getCodeDesc(context, "12315��Ʒ�������DFAT66",
							sorce_goods_class_sec);
			String goods_class_fir = sorce_goods_class_fir == null ? sorce_goods_class_fir
					: code.getCodeDesc(context, "12315��Ʒ�������DFAT66",
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
	 * ɾ���ٱ���Ϣ��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60172005(XbReportBaseContext context) throws TxnException
	{
		// BaseTable table = TableFactory.getInstance().getTableObject( this,
		// TABLE_NAME );
		// ɾ����¼�������б� VoXbReportBasePrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		// table.executeFunction( DELETE_FUNCTION, context, inputNode,
		// outputNode );
	}

	/**
	 * ��ѯ��ðα��������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60172006(XbReportBaseContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoXbReportBasePrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FAKE, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoXbReportBase result = context.getXbReportBase( outputNode
		// );
	}

	/**
	 * ���ظ���ķ����������滻���׽ӿڵ�������� ���ú���
	 * 
	 * @param funcName
	 *            ��������
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����");
		}

		// ִ��
		XbReportBaseContext appContext = new XbReportBaseContext(context);
		invoke(method, appContext);
	}
}
