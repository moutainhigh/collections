package com_plugin.Interface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class ServiceUtil {
    public static List list=new ArrayList();

    //放入数据方法
    public static void writeData(Object obj,String lastFlag){
        lincenter(lastFlag);
        list.add(obj);
        //问题：如何知道数据已经最后一批放完，并进行发送
    }


    public static void lincenter(String lastFLag){
        if("END".equals(lastFLag)){
            //直接将list中缓存数据发出
            System.out.println("放入数据="+list.toString());
        }else{
            int num=list.size();
            if(num==100){
                //模拟将数据放入kafka，将结果进行编码设置固定值
                System.out.println("放入数据="+list.toString());
                //重新构建容器
                list=new ArrayList();
            }
        }

    }
}



