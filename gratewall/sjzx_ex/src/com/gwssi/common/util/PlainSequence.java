package com.gwssi.common.util;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;

public class PlainSequence {

    /**
     * 初始化函数
     * 
     * @param context
     *            交易上下文
     * @param node
     *            需要增加序号的数据总线名称，此数据总线应为多条记录的明细数据总线
     * @param index
     *            存入序号的字段名
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

        // 要进行异常处理
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
