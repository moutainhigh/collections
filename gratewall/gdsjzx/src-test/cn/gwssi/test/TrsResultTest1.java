package cn.gwssi.test;

import com.trs.client.ClassInfo;
import com.trs.client.TRSConnection;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

public class TrsResultTest1 {
	/*
	 * 
	 * public int classResult(java.lang.String strColumn,
                       java.lang.String strValues,
                       int iPruneStore,
                       java.lang.String strSortMethod,
                       boolean bMixEnable,
                       boolean bAscentSort)
                throws TRSException���ֶ�ֵ�Լ��������з���ͳ�ơ� 

				����
				strColumn - ָ����ֵ������ֶΣ�������DATE��NUMBER��CHAR�����ֶΡ�
				strValues - ָ������ʱֻ���ע���ֶ�ֵ��Ҳ����˵ֻ����Щ�ֶ�ֵ���Ӧ�Ľ���¼���з���ͳ�ƺʹ洢��ÿ��ֵΪһ�У��û��з�\n��Ϊ�ָ������Ϊ�գ���ȱʡΪָ���ֶε������ֶ�ֵ������������ʶ����ֶ�ֵ����ȡ����ϵͳ�������趨�������
				iPruneStore - ÿ�����б�������¼�ĸ���0��ʾ�Խ���¼ֻ�������ͳ�ƣ���ı����¼�Ĵ洢��65535��ʾ�Խ���¼�������ͳ�ƣ��������洢�ͱ������еĽ���¼������ֵ��ʾ�Խ���¼�������ͳ�ƣ��������洢��ÿ�������Ľ���¼���������ָ����ֵ��
				strSortMethod - ����¼������ʽ��Ϊ�ձ�ʾ���ı�ԭ��������ʽ����������ֶμ��԰�Ƿֺš����Ż�ո�ָ����ֶ���ǰ��ǰ׺��+����ʾ���������У���ǰ׺��-����ʾ���������У�ָ��"RELEVANCE"��ʾ����������򣬻���LIFO��ʾ����¼�������¼�ŵĽ������У���"+����; -����; RELEVANCE"��
				bMixEnable - ���Ϊtrue����ʾ�ڼ���������漰��������ݿ��Ͻ��л�����򣬷�����ÿ����ͼ����������н��ж�������
				bAscentSort - ���Ϊtrue�����ʾ���ؽ��ʱ������������У���������������С� 
	 * */
	public static void main(String[] args) {
		
	
    	 TRSConnection conn = null;
         TRSResultSet  rs   = null;
     try
     {
             conn = new TRSConnection();
             conn.connect("localhost", "8888", "system", "manager", "T10");

             rs = conn.executeSelect("demo2", "�й�", false);
             int iClassNum = rs.classResult("����", "", 10, "", false, false);
             
             System.out.println("ClassCount= " + rs.getClassCount());
             for (int i= 0; i< iClassNum; i++)
             {
                     ClassInfo classInfo= rs.getClassInfo(i);
                     
                     /*
                      * iRecordNum 
							          ���ڸ������ܼ�¼�� 
							 int iValidNum 
							          ���ڸ����Ŀɶ�ȡ����Ч��¼�� 
							 java.lang.String strValue -----------�������
							          ָ�������ֶε��ֶ�ֵ 
                      * */
                     System.out.println(classInfo.strValue + ": " + classInfo.iValidNum + "/" + classInfo.iRecordNum);
             }
     }
     catch (TRSException e)
     {
             System.out.println("ErrorCode: " + e.getErrorCode());
             System.out.println("ErrorString: " + e.getErrorString());
     }
     finally
     {
             if (rs != null) rs.close();
             rs = null;
             if (conn != null) conn.close();
             conn = null;
     	}
     }
}
