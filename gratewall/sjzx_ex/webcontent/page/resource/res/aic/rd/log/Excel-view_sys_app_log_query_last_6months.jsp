<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.web.util.RequestUtil,cn.gwssi.common.web.context.UserContext"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="com.gwssi.dw.runmgr.etl.txn.ETLConstants" %>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="com.gwssi.common.util.ExcelWrite"%>
<%@ page import="jxl.Workbook"%>
<%@ page import="jxl.format.Colour"%>
<%@ page import="jxl.format.UnderlineStyle"%>
<%@ page import="jxl.write.DateFormat"%>
<%@ page import="jxl.write.DateTime"%>
<%@ page import="jxl.write.Label"%>
<%@ page import="jxl.write.NumberFormat"%>
<%@ page import="jxl.write.WritableCellFormat"%>
<%@ page import="jxl.write.WritableFont"%>
<%@ page import="jxl.write.WritableSheet"%>
<%@ page import="jxl.write.WritableWorkbook"%>
<%
System.out.println("come here half year!");
DataBus context = (DataBus) request.getAttribute("freeze-databus");
Recordset rs = context.getRecordset("record");  
String[] Titles = {"用户账号","所属机构","日志类型","操作时间","IP"} ;
String[][] Contents;
String title="最近半年用户使用日志查询";
response.reset();
response.setContentType("aplication/vnd.ms-excel");
response.addHeader("Content-Disposition","inline; filename=" + new String(title.getBytes("GBK"),"ISO8859_1") + ".xls"); 
OutputStream os=response.getOutputStream();;

Contents=new String[rs.size()][5];
System.out.println("--------2.1-----------");
System.out.println(rs.size());
for(int i=0;i<rs.size();i++)
{
	DataBus db = (DataBus)rs.get(i);
	Contents[i][0]=db.getValue("username");
	Contents[i][1]=db.getValue("orgid");
	Contents[i][2]=db.getValue("type");
	Contents[i][3]=db.getValue("query_time");
	Contents[i][4]=db.getValue("ipaddress");
}

WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
// 设置excel标题
WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,
		WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
		Colour.BLACK);
WritableCellFormat wcfFC = new WritableCellFormat(wfont);
wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 14,
		WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
		Colour.BLACK);
wcfFC = new WritableCellFormat(wfont);

	// 判断数据量低于65000条则使用传入sheet名称，超过则按数字命名
	WritableSheet wsheet = wbook.createSheet("sheet1", 0); // sheet名称

	// 开始生成主体内容
	for (int i = 0; i < Titles.length; i++) {
		wsheet.addCell(new Label(i, 0, Titles[i], wcfFC));
	}

	for (int i = 1; i < Contents.length + 1; i++) {
		for (int j = 0; j < Contents[i - 1].length; j++) {
			wsheet.addCell(new Label(j, i, Contents[i - 1][j]));
		}
	}
wbook.write(); // 写入文件
wbook.close();
os.close();

%>