package com_plugin.Interface;

import java.sql.ResultSet;

/**
 * Created by Administrator on 2016/5/20.
 */
public    class ServicePush  implements  ServiceHandler{
    public void pushData ()throws  Exception{
        System.out.print("放入数据");
        for(int i=0;i<=200;i++){
            if(i<200){
                ServiceUtil.writeData(i,null);
            }else{
                ServiceUtil.writeData(i,"END");
            }


        }
    };





}
