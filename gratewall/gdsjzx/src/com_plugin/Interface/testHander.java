package com_plugin.Interface;

import cn.gwssi.test.Test;

import javax.xml.ws.Service;

/**放入数据
 * Created by Administrator on 2016/5/20.
 */
public class testHander {

 public static void main(String [] args)throws  Exception{
    Object obj=Class.forName("com_plugin.Interface.ServicePush").newInstance();
     if(obj instanceof  ServiceHandler){
         ServiceHandler sc=(ServiceHandler) obj;
         sc.pushData();
     }
 }


  //添加数据方法
    public void parse(){


    }
}
