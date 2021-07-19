package com.ye.stock.tushare.openfeign;

import lombok.Data;

@Data
public class ReqJson {
    private String api_name;
    private String token;
    private Params params;
    private String fields;




}
