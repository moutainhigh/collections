package com.ye.stock.tushare.openfeign;

import lombok.Data;

@Data
public class Params {
    private String exchange;
    private String  start_date;
    private String end_date;
    private String ts_code;
    private String trade_date;


}
