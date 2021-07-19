package com.gwssi.report.balance.api;

import java.util.List;

import com.gwssi.report.balance.BalanceInfo;
import com.gwssi.report.balance.test.TestReportSource;
import com.gwssi.report.model.TCognosReportBO;


/**
 * 平衡校验入口，实例化执行
 * @author wuyincheng ,Nov 2, 2016
 * @NotThreadSafe
 */
public class BalanceClient {
	
	private ReportModel model;    //需要校验的报表
	private Expression runEngine ;//校验引擎
	
	public BalanceClient(ReportModel model, ReportSource source) {
		this.model = model;
		//校验引擎
		this.runEngine = new OperCharCompile(
				          new FillData(new SimpleJSEngineExpression(),model, source));
	}
	
	public static void main(String[] args) {
		//测试
		TestReportSource source = new TestReportSource();
		TCognosReportBO query = new TCognosReportBO();
		query.setReportname("市场1表");
		query.setReporttype("1");
		ReportModel model = source.getReport(query);
		BalanceClient client = new BalanceClient(model, source);
		BalanceInfo info = new BalanceInfo();

		info.setRowBalance("2>=5;5==6+7+8+9+10");
		info.setColumnBalance("1==3+9;1>=2;2==4+10;3==5+6+7+8;3>=4;9==11+12+13+14;9>=10;15==16+17+18+19;15==20+21+22+23+24+25+26+27");
//		info.setGridBalance("(1,6)==[(1,4)-(1,5)]/(1,5)*'100';(2,6)==[(2,4)-(2,5)]/(2,5)*'100';(3,6)==[(3,4)-(3,5)]/(3,5)*'100';(4,6)==[(4,4)-(4,5)]/(4,5)*'100';(5,6)==[(5,4)-(5,5)]/(5,5)*'100'");
		info.setTableBalance("市场1表(20,2)==市场2表(1,1);市场1表(20,3)==市场2表(1,2);市场1表(20,4)==市场2表(1,3)");
		StringBuilder result = new StringBuilder(2048);
		
		client.rowCheck(info, result);
		client.columnCheck(info, result);
		client.gridCheck(info, result);
		client.tableCheck(info, result);
		
		System.out.println(result.toString());
		
		//需要校验的报表ID
	}
	
	//行校验
	public void rowCheck(BalanceInfo balance, StringBuilder result){
		if(balance == null || balance.getRowBalanceInfo() == null)
			return ;
		List<String> rowBalance = balance.getRowBalanceInfo();
		final ColumnAndRowToGrid rowCheck = new ColumnAndRowToGrid(runEngine);
		int flag = 0;
		//人事1表 与 人事6表 只有个别行需要校验。(参考某白皮书)
		flag = "人事1表".equals(model.getReportInfo().getReportname()) ? 1 : 
			   ("人事6表".equals(model.getReportInfo().getReportname()) ? 2 : 0);
		Object res = null;
		String resData = null;
		for(String row : rowBalance){
			rowCheck.reset();
			rowCheck.cleanRowChecked();
			rowCheck.cleanRowUnChecked();
			if(flag != 0){
				if("1==2+3+4+5+6+7+8+9+10+11+12+13+14+15+16+17+18+19+20+21+22".equals(row)){//1、3行需要校验
					if(flag == 1)//人事1表
						rowCheck.addRowChecked(1,3);
					else         //人事6表
						rowCheck.addRowUnChecked(1);
				}else{
					if(flag == 1)
						rowCheck.addRowUnChecked(1,3);
					else
						rowCheck.addRowChecked(1);
				}
			}
			for(int i = 0; i < model.getRows(); i ++){
				res = rowCheck.execute(row);
				resData = "行:[" + (i + 1) + "] 校验 [" + row + "]" + 
		                   (res == Expression.TRUE ? "成功": res == Expression.FALSE ? "失败" : res) + "\n";
				System.out.println(resData);
//				if(res == Expression.TRUE || res == Expression.FALSE){
//					result.append(resData);
//				}
				if(res == Expression.FALSE)
					result.append(resData);
			}
		}
	}
	
	//列校验
	public void columnCheck(BalanceInfo balance, StringBuilder result){
		if(balance == null || balance.getColumnBalanceInfo() == null)
			return ;
		List<String> rowBalance = balance.getColumnBalanceInfo();
		final ColumnAndRowToGrid columnCheck = new ColumnAndRowToGrid(runEngine);
		columnCheck.setType(ColumnAndRowToGrid.COLUMN);
		Object res = null;
		String resData = null;
		for(String row : rowBalance){
			columnCheck.reset();
			for(int i = 0; i < model.getColumns(); i ++){
				res = columnCheck.execute(row);
				resData = "列:[" + (i + 1) + "] 校验 [" + row + "]" + 
		                   (res == Expression.TRUE ? "成功": res == Expression.FALSE ? "失败" : res) + "\n";
				System.out.println(resData);
//				if(res == Expression.TRUE || res == Expression.FALSE){
//					result.append(resData);
//				}
				if(res == Expression.FALSE)
					result.append(resData);
			}
		}
	}
	
	//单元格
	public void gridCheck(BalanceInfo balance, StringBuilder result){
		if(balance == null || balance.getGridBalanceInfo() == null)
			return ;
		gridCheck(balance.getGridBalanceInfo(), result, "单元格");
	}
	
	private void gridCheck(List<String> gridBalance, StringBuilder result, String title){
		Object res = null;
		String resData = null;
		for(String grid : gridBalance){
			res = runEngine.execute(grid);
			resData = title + ":[" + grid + "] 校验 " + 
	                   (res == Expression.TRUE ? "成功": res == Expression.FALSE ? "失败" : res) + "\n";
			System.out.println(resData);
//			if(res == Expression.TRUE || res == Expression.FALSE){
//				result.append(resData);
//			}
			if(res == Expression.FALSE)
				result.append(resData);
		}
	}
	
	//表间
	public void tableCheck(BalanceInfo balance, StringBuilder result){
		if(balance == null || balance.getTableBalanceInfo() == null)
			return ;
		gridCheck(balance.getTableBalanceInfo(), result, "表间");
	}
}
