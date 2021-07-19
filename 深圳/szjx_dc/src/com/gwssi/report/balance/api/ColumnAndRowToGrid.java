package com.gwssi.report.balance.api;

import java.util.HashSet;
import java.util.Set;


/**
 * 行列平衡关系表达式转单元格表达式
 * 1>=2    ->    (1,1)>=(1,2)
 * @author wuyincheng ,Nov 8, 2016
 * @ThreadNotSafe
 */
public class ColumnAndRowToGrid extends ExpressionDecorator{
	
	public static final int ROW = 1;
	public static final int COLUMN = 2;
	
	//标识符，是行校验还是列校验
	private int type = ROW;
	//行、列数
	private int num = 1;
	//需要校验的row_num
	private Set<Integer> rowChecked = null;//！！高优先级
	//不需要校验的row_num
	private Set<Integer> rowUnChecked = null;
	//需要校验的column_num 没有报表个别列需要校验，没用上
	//private Set<Integer> columnChecked = null;
	//不需要校验的column_num
	//private Set<Integer> columnUnChecked = null;
	
	protected ColumnAndRowToGrid(Expression expression) {
		super(expression);
	}
	
	
	@Override
	public Object before(Object expression) {
		if(rowChecked != null && rowChecked.size() > 0){//指定的校验列
			if(!rowChecked.contains(num)){
				num ++;
				return null;
			}
		}
		if(rowUnChecked != null && rowUnChecked.size() > 0){//需要过滤的列
			if(rowUnChecked.contains(num)){
				num ++;
				return null;
			}
		}
		final StringBuilder s = new StringBuilder();
		final String exp = expression.toString();
		int start = 0;
		for(int i = 0; i < exp.length(); i ++){
			if(exp.charAt(i) == ' ')
				continue ;
			if(exp.charAt(i) < '0' || exp.charAt(i) > '9') {
				s.append('(').append(type == ROW ? num : (i - start == 1 ? exp.charAt(start)
						: exp.substring(start, i)));
				s.append(',').append(type == ROW ? (i - start == 1 ? exp.charAt(start)
                        : exp.substring(start, i)) : num).append(')');
				s.append(exp.charAt(i));
				while(++i < exp.length() && (exp.charAt(i) < '0' || exp.charAt(i) > '9'))
					s.append(exp.charAt(i));
				start = i;
				i --;
			}
		} 
		s.append('(').append(type == ROW ? num : (exp.length() - start == 1 ? exp.charAt(start)
                : exp.substring(start, exp.length())));
		s.append(',').append(type == ROW ? (exp.length() - start == 1 ? exp.charAt(start)
				: exp.substring(start, exp.length())) : num).append(')');
		num ++;
		System.out.println(s.toString());
		return s.toString();
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public int getNum(){
		return num;
	}
	//新增过滤行
	public void addRowChecked(int ... nums){
		if(nums == null || nums.length == 0)
			return ;
		if(rowChecked == null)
			rowChecked = new HashSet<Integer>(16);
		for(int i : nums) {
			rowChecked.add(i);
		}
	}
	public void addRowUnChecked(int ... nums){
		if(nums == null || nums.length == 0)
			return ;
		if(rowUnChecked == null)
			rowUnChecked = new HashSet<Integer>(16);
		for(int i : nums) {
			rowUnChecked.add(i);
		}
	}
	
	//清空
	public void cleanRowChecked() {
		if(this.rowChecked != null)
			this.rowChecked.clear();
	}
	public void cleanRowUnChecked() {
		if(this.rowUnChecked != null)
			this.rowUnChecked.clear();
	}
	
	
	//重置行/列数
	public void reset(){
		num = 1;
	}
	
	public static void main(String[] args) {
//		Object i =3;
//		System.out.println(i);
//		String s = "1==2+3+4+5";
//		Pattern p = Pattern.compile("\\d*");
//		Matcher m = p.matcher(s);
//		List<Integer> values = new ArrayList<Integer>();
//		while (m.find()) {//获取所有坐标点
////			if (!"".equals(m.group())){
////				values.add(Integer.parseInt(m.group()));
////			}
//			System.out.println(m.group());
//		}
//		System.out.println(values);
	}
}
