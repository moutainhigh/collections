package com.gwssi.sysmgr.priv.func.txn;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;

public class Functxn2Htmls
{
	protected static Logger log = TxnLogger.getLogger(Functxn2Htmls.class.getName());

	public String createDocument( TxnContext context ) throws TxnException 
	{
		StringBuffer result = new StringBuffer();
		
		// 生成内容
		prepareFileHead(result);
		
		// 生成权限配置列表
		prepareDocument(result, context);
		
		// 保存文件
		prepareFileTail(result);
		
		return result.toString();
	}

	/**
	 * 生成全部数据字典
	 * @param result
	 * @throws TxnException
	 */
	protected void prepareDocument(StringBuffer result,
			TxnContext context)throws TxnException 
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this, "functxn");
		table.executeFunction("select functxn list", context, "select-key", "record");
		
		BaseTable funcinfoTable = TableFactory.getInstance().getTableObject(this, "funcinfo");
		funcinfoTable.executeFunction("select one funcinfo", context, "select-key", "funcinfo");
		log.debug("===========context is " + context);

		DataBus funcinfoDB = context.getRecord("funcinfo");
		String funccode = funcinfoDB.getValue("funccode");
		String funcname = funcinfoDB.getValue("funcname");

		Recordset functxnSet = context.getRecordset("record");
		
		result.append("<h2>功能名称〔");
		result.append(funccode);
		result.append("）；功能描述〔");
		result.append(funcname);
		result.append("）");
		result.append("</h2>\n");
		prepareTable(result, functxnSet);

	}

	/**
	 * 文件头
	 * @param result
	 * @param table
	 */
	protected void prepareFileHead(StringBuffer result)
	{
		result.append("<html>\n");
		result.append("<head>\n");
		result.append("<meta http-equiv='Content-Type' content='text/html; charset=GBK'>\n");
		result.append("<title>权限配置</title>\n");
		result.append("</head>\n");
		result.append("<body>\n");
	}

	/**
	 * 文件结束
	 * @param result
	 * @param table
	 */
	protected void prepareFileTail(StringBuffer result)
	{
		result.append("</body>\n");
		result.append("</html>\n");
	}

	/**
	 * 表体
	 * @param result
	 */
	private void prepareTable(StringBuffer result, Recordset functxnSet)
	{
		result.append("<table border='1' width='100%' cellpadding='0' cellspacing='0' style='border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt; mso-yfti-tbllook:480;mso-padding-alt:0cm 5.4pt 0cm 5.4pt;mso-border-insideh: .5pt solid windowtext;mso-border-insidev:.5pt solid windowtext; font-family: 宋体; font-size:10.5pt'>\n");
		result.append("<tr bgcolor='#FFFF99' style='font-weight: bold'>\n");
		result.append("<td width='25%' align='center'>交易代码</td>\n"); //txncode
		result.append("<td width='45%' align='center'>交易名称</td>\n"); //txnname
		result.append("</tr>\n");
		
		for (int ii = 0; ii < functxnSet.size(); ii++) {
			DataBus functxnDB = functxnSet.get(ii);
			result.append("<tr>\n");
			result.append("<td>");
			result.append(functxnDB.getValue("txncode"));
			result.append("</td>\n");
			result.append("<td>");
			result.append(functxnDB.getValue("txnname"));
			result.append("</td>\n");
			result.append("</tr>\n");
		}
		
		result.append("</table>\n");
	}
}
