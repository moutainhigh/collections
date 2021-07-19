package cn.gwssi.broker.server.hander;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @ThreadNotSafe
 * @author wuyincheng
 * @date 2016/06/15
 * 
 *       <SRContent> 
 *           <MethodName>QueryDriverInfo</MethodName>
 *           <Parameters>
 *       		<Parameter>  //可携带and\or逻辑符号，Parameter可嵌套，
 *       		    <p filter='egt(eq | neq | gt |egt | lt | elt | like  )') name='age'>23</p> //可携带逻辑操作符号，默认eq
 *       			<p op='or' type='string' filter='like' name='name'>admin</p>
 *       			<Parameter op='and'>
 *                      <Parameter>
 *                      	<p filter='egt' name='high'>160</p>
 *                      	<p op='and' filter='elt'  name='high'>170</p>
 *                      </Parameter>
 *       				<Parameter op='or'>
 *                      	<p filter='egt' name='high'>178</p>
 *                      </Parameter>
 *          		</Parameter>
 *       		</Parameter>
 *              <Parameter op='or'>
 *       		    <p filter='gt' name='level'>5</p>
 *       			<p type='string' op='and' filter='egt' name='address'>2016-05-05</p>
 *       		</Parameter>
 *              <ResultRows> //返回列名集合，需要为Parameters的子节点
 *       		    <row>level</row>
 *       			<row>name</row>
 *      			<row>age</row>
 *       		</ResultRows>
 *           </Parameters> 
 *       </SRContent>
 */
public class SRContent {
	
	private String xmlStr;
	
	private String methodName;
	
	private List<String> sqlConditionStruct = new ArrayList<String>(); //结构语句
	
	private int point = 0; //记录结构位置
	
	private List<String> sqlConditionValues = new ArrayList<String>(); //值集合
	
	private Set<String> resultFields = new LinkedHashSet<String>();
		
	private String sqlCondition;

	public SRContent(String xmlStr) {
		this.xmlStr = xmlStr;
		init();
	}

	//dom4j解析
	private void init()  {
		try{
			Document document = DocumentHelper.parseText(xmlStr);
			Element users = document.getRootElement(); 
			for (@SuppressWarnings("unchecked") Iterator<Element> i = users.elementIterator(); i.hasNext();) {//SRContent循环节点  
	            Element e =  i.next(); 
	            if("MethodName".equals(e.getName())){
	            	this.methodName = e.getStringValue();
	            }
	            else { //sql查询参数,Parameters节点
	            	StringBuilder s = new StringBuilder();
	            	for (@SuppressWarnings("unchecked") Iterator<Element> j = e.elementIterator(); j.hasNext();) {//Parameters节点
		                Element param =  j.next();  
		                compile(param, s);
		            }
//	            	System.out.println(s.toString());getSqlConditionValues
	            	this.sqlCondition = s.toString();
	            }
	        }  
			if(this.point != sqlCondition.length())
				sqlConditionStruct.add(sqlCondition.substring(point, sqlCondition.length()));
		}catch(Exception e) {//构造异常
			e.printStackTrace();
		}
	}

	//<Parameter op='and'><p filter='gt' name='level'>5</p></Parameter>
	private void compile(Element param, StringBuilder s) { //
		@SuppressWarnings("unchecked") List<Element> temp = param.elements();
		if(temp == null)
			return ;
		operationAppend(param, s);
		if(param.elements().size() > 1 && !"ResultRows".equals(param.getName()))
			s.append(' ').append('(');
		for(Element e : temp) {
			if("Parameter".equals(e.getName())){
				compile(e, s);//如果为嵌套Parameter则递归
			}else if("p".equals(e.getName())){//分查询字段以及返回字段
				operationAppend(e, s);
				s.append(e.attribute("name").getValue());//字段名
				runAppend(e, s);//操作符与值
			}else if("row".equals(e.getName())){//表示返回值
				this.resultFields.add(e.getText());
			}
		}
		if(param.elements().size() > 1 && !"ResultRows".equals(param.getName()))
			s.append(' ').append(')');
	}
	
	//逻辑操作符拼接s.substring(start, end)
	public void operationAppend(Element e, StringBuilder s) {
		if(e == null || e.attribute("op") == null)
			return ;
		String op = null;
		if((op = e.attribute("op").getValue()) != null) { //逻辑操作符
			if("and".equalsIgnoreCase(op))
				s.append(" AND ");
			if("or".equalsIgnoreCase(op))
				s.append(" OR ");
		}
	}
	
	//运算符拼接
	public void runAppend(Element e, StringBuilder s) {
		String r = (e.attribute("filter") == null ? null : e.attribute("filter").getValue());
		r =(r == null ? "eq" : r);
		String type = (e.attribute("type") == null ? null : e.attribute("type").getValue());
		switch(r.toLowerCase()) {
			case "eq" : s.append(" = ") ;break;
			case "neq" : s.append( " != ")	; break;
			case "like" : s.append( " LIKE "); break;
			case "gt" : s.append( " > ")	; break;
			case "egt" : s.append( " >= ")	; break;
			case "lt" : s.append( " < ")	; break;
			case "elt" : s.append( " <= ")	; break;
//			case "null" : s.append( " IS NULL"	
//			case "notnull" : s.append( " get
		}
		sqlConditionStruct.add(s.substring(point, s.length()));
		if(type != null && "string".equals(type)) {
			s.append('\'').append(e.getText()).append('\'');
		}else{
			s.append(e.getText());//拼接值
		}
		point = s.length();
		sqlConditionValues.add(e.getText());
		
		
	}

	public String getSqlCondition() {
		return sqlCondition;
	}
	
	public List<String> getSqlConditionValues() {
		return this.sqlConditionValues;
	}
	
	//返回sql语句结构
	public List<String> getSqlConditionStruct() {
		return this.sqlConditionStruct;
	}
	
	///返回所需要查询字段名
	public List<String> getResultFields() {
		return new ArrayList<String>(this.resultFields);
	}
	
	//返回安全sql语句
	public String getSqlConditionStructStr() {
		StringBuilder s = new StringBuilder();
		int i = 0;
		for( ; i < this.sqlConditionStruct.size() - 1; i ++) {
			s.append(this.sqlConditionStruct.get(i)).append(' ').append('?');
		}
		if(i < this.sqlConditionStruct.size())
			s.append(' ').append(this.sqlConditionStruct.get(i));
		return s.toString();
	}
	
	public String getXmlString() {
		return this.xmlStr;
	}
	
	public String getMethodName() {
		return this.methodName;
	}
	
	public static void main(String[] args) {
		
		String xml = "<SRContent>" + 
		           "<MethodName>QueryDriverInfo</MethodName>" + 
		            "<Parameters>" + 	
		        		"<Parameter> " + 
		        		    "<p filter='egt' name='age'>23</p>" + 
		       			"<p op='or' type='string' filter='like' name='name'>admin</p>" + 
		        			"<Parameter op='and'>" + 
		                       "<Parameter>" + 
		                       	"<p filter='egt' name='high'>160</p>" + 
		                       	"<p op='and' filter='elt'  name='high'>170</p>" + 
		                       "</Parameter>" + 
		        				"<Parameter op='or'>" + 
		                       	"<p filter='egt' name='high'>178</p>" + 
		                       "</Parameter>" + 
		           		"</Parameter>" + 
		        		"</Parameter>" + 
		               "<Parameter op='or'>" + 
		        		    "<p filter='gt' name='level'>5</p>" + 
		       			"<p op='and' type='string' filter='egt' name='address'>2016-05-05</p>" + 
		        		"</Parameter>" + 
		        		"<ResultRows>" + 
		        			"<row>level</row>" + 
		        			"<row>name</row>" + 
		        			"<row>age</row>" + 
		        		"</ResultRows>" + 
		            "</Parameters> " + 
		        "</SRContent>";
		SRContent s = new SRContent(xml);
		System.out.println(s.getSqlConditionValues());//参数值
		System.out.println(s.getSqlConditionStruct());
		System.out.println(s.getSqlConditionStructStr());//带问号的条件
		System.out.println(s.getResultFields());//返回项
		System.out.println(s.getSqlCondition());//带参数的条件
	}
}
