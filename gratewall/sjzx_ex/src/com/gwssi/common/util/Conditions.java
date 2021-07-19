package com.gwssi.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ������ѯ���������ࡣ
 * @author lifx
 *
 */
public class Conditions
{
	/**
	 * Bettween where  ��ʾ����
	 */
	public static final String BETWEEN = " BETWEEN ";
	/**
	 * ���� where  ��ʾ����
	 */
	public static final String GREATNESS = " > ";
	/**
	 * С�ڡ�Where ��ʾ����
	 */
	public static final String SMALLNESS = " < ";
	/**
	 * ���ڵ��ڡ�where  ��ʾ����
	 */
	public static final String GREATNESS_AND_EQUALS = " >= ";
	/**
	 * С�ڵ��ڡ�where  ��ʾ����
	 */
	public static final String SMALLNESS_AND_EQUALS = " <= ";
	/**
	 * Like where  ��ʾ���� ,���� ���Ҷ���һ���ٷֺ� '%xx%'
	 */
	public static final String LIKE = " LIKE ";
	/**
	 * Like where  ��ʾ���� ���һ���ٷֺ�'%
	 */
	public static final String LLIKE = " LLIKE ";
	/**
	 * Like where  ��ʾ���� �Ҵ�һ���ٷֺ�'%
	 */
	public static final String RLIKE = " RLIKE ";
	/**
	 * ���� where  ��ʾ���� 
	 */
	public static final String EQUALS = " = ";
    
    /**
     * �ǵ��� where  ��ʾ���� 
     */
    public static final String NOT_EQUALS = " <> ";
	/**
	 * �ǿ� where  ��ʾ���� 
	 */
	public static final String IS_NOT_NULL = " NOT NULL ";
	/**
	 * �� where  ��ʾ���� 
	 */
	public static final String IS_NULL = " IS NULL ";

//	public static final String OR = " OR ";
	/**
	 * IN where  ��ʾ���� 
	 */
	public static final String IN = " IN ";
    
    /**
     * NOT IN where ��ʾ����
     */
    public static final String NOT_IN = " NOT IN ";
	
	/**
	 * �ֶε����Ͷ���ΪVARCHAR  
	 */
	public static final String VARCHAR = "VARCHAR";
	/**
	 * �ֶε����Ͷ���ΪVARCHAR  
	 */
	public static final String NUMERIC = "NUMERIC";
	/**
	 * �ֶε����Ͷ���ΪVARCHAR  
	 */
	public static final String INTEGER = "INTEGER";
	
	/**
	 * ϵͳͳһ��ı��� A
	 */
	public static final String TABLE_A = "a";
	/**
	 * ϵͳͳһ��ı��� B
	 */
	public static final String TABLE_B = "b";
	/**
	 * ϵͳͳһ��ı��� C
	 */
	public static final String TABLE_C = "c";
	/**
	 * ϵͳͳһ��ı��� D
	 */
	public static final String TABLE_D = "d";
	/**
	 * ϵͳͳһ��ı��� E
	 */
	public static final String TABLE_E = "e";
	/**
	 * ϵͳͳһ��ı��� F
	 */
	public static final String TABLE_F = "f";
	/**
	 * ϵͳͳһ��ı��� G
	 */
	public static final String TABLE_G = "g";
	/**
	 * ϵͳͳһ��ı��� H
	 */
	public static final String TABLE_H = "h";
	/**
	 * ������������SQL������AND
	 * @param tablename �����
	 * @param name �ֶ�����
	 * @param state ���ʽ
	 * @param values ֵ����
	 * @param type ֵ��������VARCHAR,NUMERIC,INTEGER
	 * @return
	 */
	public static String createFilterNoAnd(String tablename,String name,String state,String[] values,String type){
		if (name == null || name.equals("") || state == null){
			return "";
		}
		if (type != null && values != null && type.equals(VARCHAR) && !state.equals(LIKE) && !state.equals(LLIKE) && !state.equals(RLIKE)){
			for (int i = 0 ; i < values.length ; i ++){
				values[i] = "'"+values[i]+"'";
			}
		}
		StringBuffer sbSQLStr = new StringBuffer();
		sbSQLStr.append(" (");
		if (tablename != null && !tablename.equals("")){
			sbSQLStr.append(tablename);
			sbSQLStr.append(".");
		}
		if (BETWEEN == state){
			if (values != null && values.length < 2){
				return "";
			}
			sbSQLStr.append(name);
			sbSQLStr.append(" between "+values[0]+" and "+values[1]+") ");
		} else 
		if (LIKE == state){
			if (values != null && values.length < 1){
				return "";
			}
			sbSQLStr.append(name);
			sbSQLStr.append(" like '%"+values[0]+"%' )");
		} else 
		if (LLIKE == state){
			if (values != null && values.length < 1){
				return "";
			}
			sbSQLStr.append(name);
			sbSQLStr.append(" like '%"+values[0]+"' )");
		} else 
		if (RLIKE == state){
			if (values != null && values.length < 1){
				return "";
			}
			sbSQLStr.append(name);
			sbSQLStr.append(" like '"+values[0]+"%' )");
		} else 
		if (EQUALS == state){
			if (values != null && values.length < 1){
				return "";
			}
			sbSQLStr.append(name);
			sbSQLStr.append(" = "+values[0]+" ) ");
		}else 
        if (NOT_EQUALS == state){
            if (values != null && values.length < 1){
                return "";
            }
            sbSQLStr.append(name);
            sbSQLStr.append(" <> "+values[0]+" ) ");
        }else
		if (GREATNESS == state){
			if (values != null && values.length < 1){
				return "";
			}
			sbSQLStr.append(name);
			sbSQLStr.append(" > "+values[0]+" ) ");
		}else 
		if (SMALLNESS == state){
			if (values != null && values.length < 1){
				return "";
			}
			sbSQLStr.append(name);
			sbSQLStr.append(" < "+values[0]+" ) ");
		}else if (GREATNESS_AND_EQUALS == state){
			if (values != null && values.length < 1){
				return "";
			}
			sbSQLStr.append(name);
			sbSQLStr.append(" >= "+values[0]+" ) ");
		}else 
		if (SMALLNESS_AND_EQUALS == state){
			if (values != null && values.length < 1){
				return "";
			}
			sbSQLStr.append(name);
			sbSQLStr.append(" <= "+values[0]+" ) ");
		} else 
		if (IS_NOT_NULL == state){
			sbSQLStr.append(name);
			sbSQLStr.append(" is not null) ");
		} else 
		if (IS_NULL == state){
			sbSQLStr.append(name);
			sbSQLStr.append(" is null) ");
		} else 
		if (IN == state){
			if (values != null && values.length < 1){
				return "";
			}
			sbSQLStr.append(name);
			sbSQLStr.append(" in (");
            for (int i=0; i<values.length; i++) {
            	if (i != 0) {
            		sbSQLStr.append(",");
            	}
    			sbSQLStr.append(values[i]);
            }
			sbSQLStr.append(")) ");
		} else
        if (NOT_IN == state){
            if (values != null && values.length < 1){
                return "";
            }
            sbSQLStr.append(name);
            sbSQLStr.append(" not in (");
            for (int i=0; i<values.length; i++) {
                if (i != 0) {
                    sbSQLStr.append(",");
                }
                sbSQLStr.append(values[i]);
            }
            sbSQLStr.append(")) "); 
        }
		return sbSQLStr.toString();
	}
	
	/**
	 * ������������SQL����AND
	 * @param tablename �����
	 * @param name �ֶ�����
	 * @param state ���ʽ
	 * @param values ֵ����
	 * @param type ֵ��������VARCHAR,NUMERIC,INTEGER
	 * @return
	 */
	public static String createFilter(String tablename,String name,String state,String[] values,String type){
		String strFilter = createFilterNoAnd(tablename,name,state,values,type);
		if (strFilter == null || strFilter.equals("")){
			return "";
		}
		return " AND "+strFilter;
	}
    
    /**
     * ���� OR ��������SQL����AND
     * @param tablename �����
     * @param name �ֶ�����
     * @param state ���ʽ
     * @param values ֵ����
     * @param type ֵ��������VARCHAR,NUMERIC,INTEGER
     * @return
     */
    public static String createOrFilter(String tablename,String state,Map values,String type){
        String strFilter = createOrFilterNoAnd(tablename,state,values,type);
        if (strFilter == null || strFilter.equals("")){
            return "";
        }
        return " AND "+strFilter;
    }
	
    /**
     * ���� OR ��������SQL ����AND
     * @param tablename
     * @param name
     * @param state
     * @param values
     * @param type
     * @return
     */
    public static String createOrFilterNoAnd(String tablename,String state,Map values,String type){
        if (values != null && values.size() < 1){
            return "";
        }
        StringBuffer sbSQLStr = new StringBuffer();
        sbSQLStr.append(" (");
        int i=0;
        for (Iterator iterator=values.keySet().iterator();iterator.hasNext();) {
            if (i != 0) {
                sbSQLStr.append(" OR ");
            }
            String name = (String)iterator.next();
            if (!name.equals("")){
                String filter = createFilterNoAnd(tablename,name,state,new String[]{(String)values.get(name)},type);
                if (filter != null && !filter.equals("")){
                    sbSQLStr.append(filter);
                }else{
                    sbSQLStr.delete(sbSQLStr.length()-4,sbSQLStr.length());
                }
            }else{
                sbSQLStr.delete(sbSQLStr.length()-4,sbSQLStr.length());
            }
            i++;
        }
        sbSQLStr.append(") ");
        return sbSQLStr.toString();
    }
	
	/**
	 * ������������SQL������AND �������ϵ��������
	 * @param tablename1 �����
	 * @param name1 �ֶ�����
	 * @param type1 ת������ֵ��������VARCHAR,NUMERIC,INTEGER,����Ҫת����������
	 * @param state ���ʽ
	 * @param tablename2 �����
	 * @param name2 �ֶ�����
	 * @param type2 ת������ֵ��������VARCHAR,NUMERIC,INTEGER
	 * @return
	 */
	public static String createFilterNoAnd(String tablename1,String name1,String type1,String state,String tablename2,String name2,String type2){
		if (name1 == null  || state == null || name2 == null ){
			return "";
		}
		String strColName1 = tablename1 == null || tablename1.equals("") ? name1 : tablename1+"."+name1;
		String strColName2 = tablename2 == null || tablename2.equals("") ? name2 : tablename2+"."+name2;
		if (type1 != null && !type1.equals("")){
			if (type1.equals(VARCHAR)){
				strColName1 = "char("+strColName1+")";
			}else if (type1.equals(NUMERIC)){
				strColName1 = "numeric("+strColName1+")";
			}else if (type1.equals(INTEGER)){
				strColName1 = "integer("+strColName1+")";
			}
		}
		if (type2 != null && !type2.equals("")){
			if (type2.equals(VARCHAR)){
				strColName2 = "char("+strColName2+")";
			}else if (type2.equals(NUMERIC)){
				strColName2 = "numeric("+strColName2+")";
			}else if (type2.equals(INTEGER)){
				strColName2 = "integer("+strColName2+")";
			}
		}
		StringBuffer sbSQLStr = new StringBuffer();
		if (EQUALS == state){
			sbSQLStr.append(" (");
			sbSQLStr.append(strColName1);
			sbSQLStr.append(" = ");
			sbSQLStr.append(strColName2+" ) ");
		}else if (NOT_EQUALS == state){
            sbSQLStr.append(" (");
            sbSQLStr.append(strColName1);
            sbSQLStr.append(" <> ");
            sbSQLStr.append(strColName2+" ) ");
        }else if (GREATNESS == state){
			sbSQLStr.append(" (");
			sbSQLStr.append(strColName1);
			sbSQLStr.append(" > ");
			sbSQLStr.append(strColName2+" ) ");
		}else if (SMALLNESS == state){
			sbSQLStr.append(" (");
			sbSQLStr.append(strColName1);
			sbSQLStr.append(" < ");
			sbSQLStr.append(strColName2+" ) ");
		}else if (GREATNESS_AND_EQUALS == state){
			sbSQLStr.append(" (");
			sbSQLStr.append(strColName1);
			sbSQLStr.append(" >= ");
			sbSQLStr.append(strColName2+" ) ");
		}else if (SMALLNESS_AND_EQUALS == state){
			sbSQLStr.append(" (");
			sbSQLStr.append(strColName1);
			sbSQLStr.append(" <= ");
			sbSQLStr.append(strColName2+" ) ");
		}
		return sbSQLStr.toString();
	}
	
    
    
    
	/**
	 * ������������SQL����AND
	 * @param tablename �����
	 * @param name �ֶ�����
	 * @param state ���ʽ
	 * @param values ֵ����
	 * @param type ֵ��������VARCHAR,NUMERIC,INTEGER
	 * @return
	 */
	public static String createFilter(String tablename1,String name1,String type1,String state,String tablename2,String name2,String type2){
		String strFilter = createFilterNoAnd(tablename1,name1,type1,state,tablename2,name2,type2);
		if (strFilter == null || strFilter.equals("")){
			return "";
		}
		return " AND "+strFilter;
	}
	
	public static void main(String[] args){
		System.out.println(createFilter(null,"col1",IS_NOT_NULL,null,VARCHAR));
		System.out.println(createFilter("a","col1",EQUALS,new String[]{"dd"},VARCHAR));
		System.out.println(createFilter("a","col1",IN,new String[]{"dd","ee","ff"},VARCHAR));
        Map map = new HashMap();
        map.put("col1", "abc");
        map.put("col2", "efg");
        map.put("col3", "fff");
		System.out.println(createOrFilter("b",EQUALS,map,VARCHAR));
		System.out.println(createFilter("a","col1",VARCHAR,EQUALS,"b","col2",INTEGER));
	}
}