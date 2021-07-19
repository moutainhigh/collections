package com.gwssi.dw.aic.bj.xb.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

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

import com.gwssi.dw.aic.bj.xb.vo.XbAppealBaseContext;

public class TxnXbAppealBase extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnXbAppealBase.class, XbAppealBaseContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "xb_appeal_base";
	
	private static CodeMap code = PublicResource.getCodeFactory();
	
	/**
	 * ���캯��
	 */
	public TxnXbAppealBase()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ������Ϣ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60171001( XbAppealBaseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "query_xb_appeal_base", context, inputNode, outputNode );

		
	}
	
	/** ��ѯ������Ϣ�б����ڵǼ�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60171011( XbAppealBaseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoXbAppealBaseSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "query_xb_appeal_base", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoXbAppealBase result[] = context.getXbAppealBases( outputNode );
		
	}
	
	/** �޸�������Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60171002( XbAppealBaseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoXbAppealBase xb_appeal_base = context.getXbAppealBase( inputNode );
		table.executeFunction( "query_xb_appeal_result", context, inputNode, outputNode );
		
		String require = context.getRecord(outputNode).getValue("proc_resul_deta");
		if(require!=null){
			if(!require.trim().equals("")){
				String[] requires = require.split(",",-1);
				StringBuffer req = new StringBuffer();
				for(int i = 0; i < requires.length; i++){
					String codeValue = code.getCodeDesc(context, "12315������ϸ��DFAT70", requires[i]);
					req.append( codeValue == null ? requires[i] : codeValue);
					if(i != requires.length - 1){
						req.append(", ");
					}
				}
				context.getRecord(outputNode).setValue("proc_resul_deta", req.toString());
		}
			
			/**
			 * unre_reas_dl"  ������ԭ�� 12315������ԭ�����DFAT73"
			 */
			  
			
		}
		
//		String state1 = "";
//		String state2 = "";
//		String state = "";
//		try {
//			state1 = code.getCodeDesc(context, "12315_���⴦���������DFXB06", context.getRecord(outputNode).getValue("apl_last_resu"));
//			state2 = code.getCodeDesc(context, "12315_���⴦���������DFXB06", context.getRecord(outputNode).getValue("apl_last_resu_deta"));
//		} catch (TxnException e) {
//			e.printStackTrace();
//		}
//		
//		if(state1==null||state1=="")
//		{
//			if(state2==null||state2=="")
//				state="";
//			else
//				state=state2;
//		}
//		else
//		{
//			if(state2==null||state2=="")
//				state=state1;
//			else
//				state=state1+"--"+state2;
//		}
//		context.getRecord(outputNode).setValue("result_r", state);
		
		
		
		//DataBus rs= context.getRecord(outputNode);
		//StringBuffer result_r=new StringBuffer();
		//result_r.append(rs.getValue("apl_last_resu"));
		//result_r.append("--");
		//result_r.append(rs.getValue("apl_last_resu_deta"));
		
		//rs.setValue("result_r", result_r.toString());
	}
	
	/** ����������Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60171003( XbAppealBaseContext context ) throws TxnException
	{
		 
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		 table.executeFunction( "query_xb_appeal_to_case", context, inputNode, outputNode );
		  
		 String send_gaverment_fir = context.getRecord(outputNode).getValue("accp_tranf_auth_fir");
		 String send_gaverment_sec = context.getRecord(outputNode).getValue("accp_tranf_auth_sec");
			
		 StringBuffer req = new StringBuffer();
		 
		 String str1 =  send_gaverment_fir == null ?  send_gaverment_fir : code.getCodeDesc(context, "12315���ͻ��ش�С��DFAT102",  send_gaverment_fir);
		 String str2  =   send_gaverment_sec== null ?  send_gaverment_sec : code.getCodeDesc(context, "12315���ͻ��ش�С��DFAT102",  send_gaverment_sec);
		 if(str1==null){
			 str1 = send_gaverment_fir;
		 } 
		 if(send_gaverment_fir!=null){
			 req.append(str1); 
		 }
		 if(str2==null){
			 str2 = send_gaverment_sec;
 		 } 
		 if(str2!=null&&!"".equals(str2)){
			 req.append("\\");
		 }
		 
		  if(send_gaverment_sec!=null){
			  req.append(str2);
			 }
		 context.getRecord(outputNode).setValue("send_gaverment", req.toString());
		 
		 
		 String demand = context.getRecord(outputNode).getValue("puni_type");
			if(demand!=null){
				String[] demands = demand.split(";",-1);
				StringBuffer req2 = new StringBuffer();
				for(int i = 0; i < demands.length; i++){
					String codeValue = code.getCodeDesc(context, "12315������ʽDFAT89", demands[i]);
					req.append( codeValue == null ? demands[i] : codeValue);
					if(i != demands.length - 1){
						req2.append("\\ ");
					}
				}
			}
			context.getRecord(outputNode).setValue("puni_type", req.toString());
			 /**
				 * ����������
				 */	 
					DataBus bus = new DataBus();
					table.executeFunction( "query_xb_appeal_to_case_for_dcqk", context, inputNode, "bus" );
					  String bri_st = context.getRecord("bus").getValue("bri_st");
					 context.getRecord(outputNode).setValue("bri_st", bri_st);
//		if(resNo>0){
//			Recordset rs= context.getRecordset(outputNode);
//			//��������û�ҵ��͵�12315����
//			if (rs!=null&&rs.size()!=0)
//			{
//			String state = "";
//			String state1 = "";
//			String state2 = "";
//			
//			try {
//				state1 = code.getCodeDesc(context, "12315_�������ϱ����DFXB07", context.getRecord(outputNode).getValue("apl_addi_material"));
//				state2 = code.getCodeDesc(context, "12315_�������ϱ����DFXB07", context.getRecord(outputNode).getValue("apl_addi_material_detail"));
//			} catch (TxnException e) {
//				e.printStackTrace();
//			}
//			
//			if(state1==null||state1=="")
//			{
//				if(state2==null||state2=="")
//					state="";
//				else
//					state=state2;
//			}
//			else
//			{
//				if(state2==null||state2=="")
//					state=state1;
//				else
//					state=state1+"--"+state2;
//			}
//			context.getRecord(outputNode).setValue("addi_a", state);
//					
//			String pen_type=codetoname(context,context.getRecord(outputNode).getValue("pen_type"),"12315_��������������Υ�����ã�û�ս��DFXB34");
//			context.getRecord(outputNode).setValue("pen_type",pen_type);
//			
//			}
//		}
		
					 /**@
					   ��������_���3��  	start	               --2\101\20106             
					 */ 
			 			 
					
					 
					 StringBuffer    taget_goods_class = new   StringBuffer();
					String  sorce_goods_class =  context.getRecord(outputNode).getValue("case_chr");
					  log.debug(">>>>>>>sorce_goods_class22="+sorce_goods_class);
					 if(sorce_goods_class!=null&&!"".equals(sorce_goods_class)){
						 log.debug(">>>>>>>taget_case_chr12="+taget_goods_class.toString());
						 String sorce_goods_class_fir = sorce_goods_class.substring(0, 1);
						 String  sorce_goods_class_sec = sorce_goods_class.substring(0, 3);
						 
						 String	  goods_class_thir =  sorce_goods_class == null ?  sorce_goods_class : code.getCodeDesc(context, "12315��������DFAT88",  sorce_goods_class);
						 String  goods_class_sec =  sorce_goods_class_sec == null ?  sorce_goods_class_sec : code.getCodeDesc(context, "12315��������DFAT88",  sorce_goods_class_sec);
						 String goods_class_fir =  sorce_goods_class_fir == null ?  sorce_goods_class_fir : code.getCodeDesc(context, "12315��������DFAT88",  sorce_goods_class_fir);
						taget_goods_class.append(goods_class_fir);
						if(goods_class_sec!=null){
							taget_goods_class.append("\\");
						}
						taget_goods_class.append(goods_class_sec);
						if(goods_class_thir!=null){
							taget_goods_class.append("\\");
						}
						taget_goods_class.append(goods_class_thir);
						 context.getRecord(outputNode).setValue("taget_case_chr", taget_goods_class.toString());
						 log.debug(">>>>>>>taget_case_chr1="+taget_goods_class.toString());
						 
					 }
				   /**@
					   ��������_���3��  	end	     by dingL  2010-12-29 11:44:57
					 */ 
					 
					 /**@
					   Υ����Ϊ_���3��  	start	 break_dl  14 \1304 \010104  12315Υ����Ϊ����DFAT100   by dingL  2010-12-29 11:44:57
					 */ 
					 
					  taget_goods_class = new   StringBuffer();
					  sorce_goods_class =  context.getRecord(outputNode).getValue("break_dl");
					if(sorce_goods_class!=null && !"".equals(sorce_goods_class)){
						
						String sorce_goods_class_fir = sorce_goods_class.substring(0, 2);
						String sorce_goods_class_sec = sorce_goods_class.substring(0, 4);
						 
						String goods_class_thir =  sorce_goods_class == null ?  sorce_goods_class : code.getCodeDesc(context, "12315Υ����Ϊ����DFAT100",  sorce_goods_class);
						String goods_class_sec =  sorce_goods_class_sec == null ?  sorce_goods_class_sec : code.getCodeDesc(context, "12315Υ����Ϊ����DFAT100",  sorce_goods_class_sec);
						String goods_class_fir =  sorce_goods_class_fir == null ?  sorce_goods_class_fir : code.getCodeDesc(context, "12315Υ����Ϊ����DFAT100",  sorce_goods_class_fir);
						taget_goods_class.append(goods_class_fir);
						if(goods_class_sec!=null){
							taget_goods_class.append("\\");
						}
						taget_goods_class.append(goods_class_sec);
						if(goods_class_thir!=null){
							taget_goods_class.append("\\");
						}
						taget_goods_class.append(goods_class_thir);
						 context.getRecord(outputNode).setValue("break_dl", taget_goods_class.toString());
					}
				   
					 
					 /**@
					   Υ����Ϊ_���3��  	end	     by dingL  2010-12-29 11:44:57
					 */ 
	}
	
	/** ��ѯ������Ϣ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60171004( XbAppealBaseContext context ) throws TxnException
	{
	 
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "query_one_xb_appeal_base", context, inputNode, outputNode );
		
		String require = context.getRecord(outputNode).getValue("require");
		if(!require.trim().equals("")){
			String[] requires = require.split(",",-1);
			StringBuffer req = new StringBuffer();
			for(int i = 0; i < requires.length; i++){
			                                                                               //requires[i]
				req.append(code.getCodeDesc(context, "12315������ϸ��DFAT70", requires[i]) == null ? " " : code.getCodeDesc(context, "12315������ϸ��DFAT70", requires[i]));
				if(i != requires.length - 1){
					req.append(", ");
				}
			}
			context.getRecord(outputNode).setValue("require", req.toString());
			
			 /**@ ҵ����� start  ƴ�Ӵ���b.work_type1,b.work_type,work_type2   by dingL  2010-12-23 14:07:26 */
			 
			 String work_type = context.getRecord(outputNode).getValue("work_type");
			 String work_type1 = context.getRecord(outputNode).getValue("work_type1");
			 String work_type2 = context.getRecord(outputNode).getValue("work_type2");
			log.debug(">>>>>>>>>>>work_type1==="+work_type1);
			log.debug(">>>>>>>>>>>work_type==="+work_type);
			 StringBuffer req3 = new StringBuffer();
			 
			 String str_work_type_1 =  work_type == null ?  "" : code.getCodeDesc(context, "12315ϵͳ����DFAT94",  work_type);
			 String str_work_type_2  =   work_type1== null ?  "" : code.getCodeDesc(context, "12315ҵ������DFAT71",  work_type1);
			 String str_work_type_3  =   work_type2== null ?  "" : code.getCodeDesc(context, "12315ҵ�����2DFAT117",  work_type2);
			
			 log.debug("str_work_type_1"+str_work_type_1);
			 
			 if (StringUtils.isNotBlank(str_work_type_1)) {
				 req3.append(str_work_type_1);
			 }
			 if (StringUtils.isNotBlank(str_work_type_2)) {
				 req3.append("\\");
				 req3.append(str_work_type_2);
			 }
			
			/* if(str_work_type_3!=null){
				 req3.append("\\");
			 }
			 req3.append(str_work_type_3);*/
			 
			 
			 context.getRecord(outputNode).setValue("work_type_all", req3.toString());		
			 /**@ ҵ�����   end ƴ�Ӵ���b.work_type1,b.work_type,work_type2   by dingL  2010-12-23 14:07:26 */
			 
			/**@
			   ��Ʒ����_���3��  	start	   --\���������\��������\��������             --0202\020201\02020103             bydingL 2010-12-28 14:54:23
			 */ 
			 StringBuffer   taget_goods_class = new   StringBuffer();
			String sorce_goods_class =  context.getRecord(outputNode).getValue("goods_class");
			if(sorce_goods_class!=null&&!"".equals(sorce_goods_class )){
			String sorce_goods_class_fir = sorce_goods_class.substring(0, 4);
			String sorce_goods_class_sec = sorce_goods_class.substring(0, 6);
			 
			String goods_class_thir =  sorce_goods_class == null ?  sorce_goods_class : code.getCodeDesc(context, "12315��Ʒ�������DFAT66",  sorce_goods_class);
			String goods_class_sec =  sorce_goods_class_sec == null ?  sorce_goods_class_sec : code.getCodeDesc(context, "12315��Ʒ�������DFAT66",  sorce_goods_class_sec);
			String goods_class_fir =  sorce_goods_class_fir == null ?  sorce_goods_class_fir : code.getCodeDesc(context, "12315��Ʒ�������DFAT66",  sorce_goods_class_fir);
			taget_goods_class.append(goods_class_fir);
			if(goods_class_sec!=null){
				taget_goods_class.append("\\");
			}
			taget_goods_class.append(goods_class_sec);
			if(goods_class_thir!=null){
				taget_goods_class.append("\\");
				taget_goods_class.append(goods_class_thir);
			}
			 context.getRecord(outputNode).setValue("taget_goods_class", taget_goods_class.toString());
			 
			}
		   /**@
			   ��Ʒ����_���3��  	end	   --\���������\��������\��������             --0202\020201\02020103             bydingL 2010-12-28 14:54:23
			 */ 
			
			 /**@
			   �������_���2��  	start	    b.ques_fir, b.ques_sec                      by dingL  2010-12-29 10:06:22 
			 */
			 
			 StringBuffer   taget_ques_all = new   StringBuffer();
				 sorce_goods_class =  context.getRecord(outputNode).getValue("ques_fir");
				String sorce_goods_class_sec = context.getRecord(outputNode).getValue("ques_sec");
				
				 
				String goods_class_thir =  sorce_goods_class == null ?  sorce_goods_class : code.getCodeDesc(context, "12315�������DFAT68",  sorce_goods_class);
				String goods_class_sec =  sorce_goods_class_sec == null ?  sorce_goods_class_sec : code.getCodeDesc(context, "12315�������DFAT68",  sorce_goods_class_sec);
				 
				
				 taget_ques_all.append(goods_class_thir);
				if(goods_class_sec!=null){
					taget_ques_all.append("\\");
				}
				taget_ques_all.append(goods_class_sec);
				 
				 context.getRecord(outputNode).setValue("taget_ques_all", taget_ques_all.toString());
			
			 /**@
			   �������_���2��  	end	    b.ques_fir, b.ques_sec                      by dingL  2010-12-29 10:06:22 
			 */
			
				 
			
		}
//		String state1 = "";
//		String state2 = "";
//		String state3 = "";
//		String state4 = "";
//		String state5 = "";
//		String flag_test;
//		
//		try {
//			state1 = code.getCodeDesc(context, "12315_��Ʒ�����������DFXB30", context.getRecord(outputNode).getValue("inv_ob_type_mid"));
//			state2 = code.getCodeDesc(context, "12315_��Ʒ�����������DFXB30", context.getRecord(outputNode).getValue("inv_ob_type_small"));
//			state3 = code.getCodeDesc(context, "12315_��Ʒ�����������DFXB30", context.getRecord(outputNode).getValue("inv_ob_type_most_small"));
//				
//			context.getRecord(outputNode).setValue("type_c", state1+"--"+state2+"--"+state3 );
//		} catch (TxnException e) {
//			e.printStackTrace();
//			context.getRecord(outputNode).setValue("type_c", " " );
//		}
//		
//		try {
//			state4 = code.getCodeDesc(context, "12315_�������ݷ�������DFXB24", context.getRecord(outputNode).getValue("apl_que_clas"));
//			state5 = code.getCodeDesc(context, "12315_�������ݷ�������DFXB24", context.getRecord(outputNode).getValue("apl_que_deta_clas"));
//		} catch (TxnException e) {
//			e.printStackTrace();
//		}
//		context.getRecord(outputNode).setValue("type_p", state4+"--"+state5);
//		String apl_per_requ=codetoname(context,context.getRecord(outputNode).getValue("apl_per_requ"),"12315_������Ҫ������DFXB26");
//		//System.out.println(apl_per_requ);
//		context.getRecord(outputNode).setValue("apl_per_requ",apl_per_requ);
//		flag_test=context.getRecord(inputNode).getValue("flag");
//		
//		
//		
//		
//		if("M".equalsIgnoreCase(flag_test))
//			throw new TxnErrorException("99999","�ӵǼǱ��н����");
	}
	
	public String codetoname(XbAppealBaseContext context, String in_come, String code_list)
	{
		StringBuffer test=new StringBuffer();
		char []a=in_come.toCharArray();
		int k=0;
		if(in_come==null||in_come=="")
			return "";
		else
		{
		for(int i=0;i<a.length;i++)
		{
			
			int j=i+1;
			if(a[i]=='1')
			{
				if(k!=0)
					test.append(",  ");
				try {
					if(j<10)
						test.append(code.getCodeDesc(context, code_list, "0"+j));
					else
						test.append(code.getCodeDesc(context, code_list, String.valueOf(j)));
				} catch (TxnException e) {
					e.printStackTrace();
				}
				k=1;
				
			}
		}
		return test.toString();
		}
	}
	
	
	/** 
	 * �����ת
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60171005( XbAppealBaseContext context ) throws TxnException
	{
	}
		
	/**
	 * ���ظ���ķ����������滻���׽ӿڵ��������
	 * ���ú���
	 * @param funcName ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����"
			);
		}
		
		// ִ��
		XbAppealBaseContext appContext = new XbAppealBaseContext( context );
		invoke( method, appContext );
	}
	
	
	/** 
	 * ��ѯ��Ϣ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60173001( XbAppealBaseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "query_zxxx", context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		context.remove(outputNode);
		while(rs.hasNext()){
			DataBus db = (DataBus)rs.next();
			String bus_type = db.getValue("bus_type");
			if(bus_type.trim().equals("1")){
				String indu_org_sec = code.getCodeDesc(context, "12315��ѯ����DFAT72", db.getValue("indu_org_sec"));
				if(indu_org_sec != null && !indu_org_sec.trim().equals("")){
					db.setValue("zxname", indu_org_sec);
				}else{
					db.setValue("zxname", db.getValue("indu_org_sec"));
				}
				
			}else{
				String un_indu_org_sec = code.getCodeDesc(context, "12315�ǹ��̵�λ����DFAT115", db.getValue("un_indu_org_sec"));
				if(un_indu_org_sec != null && !un_indu_org_sec.trim().equals("")){
					db.setValue("zxname", un_indu_org_sec);
				}else{
					db.setValue("zxname", db.getValue("un_indu_org_sec"));
				}
			}
			context.addRecord(outputNode, db);
		}
	}
	
	
	/** ��ѯ��ѯ��Ϣ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60173002( XbAppealBaseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "query_zxxx_detail", context, inputNode, outputNode );
		String bus_type = context.getRecord(outputNode).getValue("bus_type");
		if(bus_type.trim().equals("1")){
			String indu_org_fir = code.getCodeDesc(context, "12315��ѯ����DFAT72", context.getRecord(outputNode).getValue("indu_org_fir"));
			if(indu_org_fir==null || indu_org_fir.trim().equals("")){
				indu_org_fir = context.getRecord(outputNode).getValue("indu_org_fir");
			}
			String indu_org_sec = code.getCodeDesc(context, "12315��ѯ����DFAT72", context.getRecord(outputNode).getValue("indu_org_sec"));
			if(indu_org_sec== null || indu_org_sec.trim().equals("")){
				indu_org_sec = context.getRecord(outputNode).getValue("indu_org_sec");
			}
			context.getRecord(outputNode).setValue("zxname", indu_org_fir + "  "+ indu_org_sec);
		}else{
			String un_indu_org_fir = code.getCodeDesc(context, "12315�ǹ��̵�λһ��DFAT114", context.getRecord(outputNode).getValue("un_indu_org_fir"));
			if(un_indu_org_fir==null || un_indu_org_fir.trim().equals("")){
				un_indu_org_fir = context.getRecord(outputNode).getValue("un_indu_org_fir");
			}
			String un_indu_org_sec = code.getCodeDesc(context, "12315�ǹ��̵�λ����DFAT115", context.getRecord(outputNode).getValue("un_indu_org_sec"));
			if(un_indu_org_sec==null || un_indu_org_sec.trim().equals("")){
				un_indu_org_sec = context.getRecord(outputNode).getValue("un_indu_org_sec");
			}
			context.getRecord(outputNode).setValue("zxname", un_indu_org_fir + "  "+ un_indu_org_sec);
		}
	}
	
	
	/** ��ѯ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60174001( XbAppealBaseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "query_jyxx", context, inputNode, outputNode );
	}
	
	
	/** ��ѯ������Ϣ��ϸ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60174002( XbAppealBaseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "query_jyxx_detail", context, inputNode, outputNode );
	}
}
