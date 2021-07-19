package com.esp.server.web.config.convertor;

import org.dozer.DozerConverter;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/12/2 16:51
 * @Description: 字符串转换为时间
 */
public class StringToDateConverter extends DozerConverter<String, Date> {

    public StringToDateConverter() {
        super(String.class, Date.class);
    }

    @Override
    public String convertFrom(Date source, String destination) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        destination = formatter.format(source);
        return destination;
    }

    @Override
    public Date convertTo(String source, Date destination) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        destination = formatter.parse(source, pos);
        return destination;
    }
}
