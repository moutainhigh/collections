package com.ye.stock.tushare.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ye.stock.tushare.openfeign.Params;
import com.ye.stock.tushare.openfeign.ReqJson;
import com.ye.stock.tushare.openfeign.OpenFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {

    @Autowired
    private OpenFeignService openFeignService;




    @RequestMapping("/")
    public String doGo(){
        ReqJson reqJson = new ReqJson();
        reqJson.setApi_name("stock_basic");
        reqJson.setToken("6c4fdbb8254085bf8559a620ccebcbd1dd4bfe361f10b498b7a3ee0a");
        reqJson.setFields("");

        Params params  = new Params();
        //params.setEnd_date("20210717");
       // params.setStart_date("20210716");

        reqJson.setParams(params);
        String str = openFeignService.getInfos(reqJson);
       // System.out.println(str);


        JSONObject obj = JSON.parseObject(str);

        String str2 = obj.getString("data");

        JSONObject obj2 = JSON.parseObject(str2);

        JSONArray arry  = obj2.getJSONArray("items");

        for (int i = 0; i <arry.size() ; i++) {
            System.out.println(arry.get(i));
        }


        return  obj.get("data").toString();
    }



    @RequestMapping("/d")
    public String dayInfo(){
        ReqJson reqJson = new ReqJson();
        reqJson.setApi_name("daily");
        reqJson.setToken("6c4fdbb8254085bf8559a620ccebcbd1dd4bfe361f10b498b7a3ee0a");
        reqJson.setFields("");

        Params params  = new Params();

        params.setTs_code("000983.sz");

        params.setStart_date("20210616");
        params.setEnd_date("20210717");
        reqJson.setParams(params);
        String str = openFeignService.getInfos(reqJson);
        JSONObject obj = JSON.parseObject(str);

        String str2 = obj.getString("data");

        JSONObject obj2 = JSON.parseObject(str2);

        JSONArray arry  = obj2.getJSONArray("items");

        for (int i = 0; i <arry.size() ; i++) {
            System.out.println(arry.get(i));
        }


        return  obj.get("data").toString();
    }


}
