package com.gwssi.common.util;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;

public class PlainSequence {

    /**
     * ��ʼ������
     * 
     * @param context
     *            ����������
     * @param node
     *            ��Ҫ������ŵ������������ƣ�����������ӦΪ������¼����ϸ��������
     * @param index
     *            ������ŵ��ֶ���
     * @throws cn.gwssi.common.component.exception.TxnException
     */
    public static void addIndex(TxnContext context, String node)
            throws TxnException {
        int startRow = 0;
        String sStartRow = context.getAttribute(null, "record_start-row");
        if (sStartRow != null) {
            try {
                startRow = Integer.parseInt(sStartRow);
            } catch (Exception e) {

            }
        }

        // Ҫ�����쳣����
        Recordset rs = null;
        try {
            rs = context.getRecordset(node);
        }
        catch(TxnException e) {
            return;
        }
        
        int size = rs.size();
        if (size == 0) {
            return;
        }

        DataBus db = null;
        if (startRow == 0) {
            startRow = 1;
        }
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                db = rs.get(i);
                db.setValue("index", String.valueOf(startRow + i));
            }
        }

    }
}
