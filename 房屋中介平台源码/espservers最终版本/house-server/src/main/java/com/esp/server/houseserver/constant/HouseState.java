package com.esp.server.houseserver.constant;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/29 17:53
 * @Description:
 */
public enum  HouseState {
    HOUSE_STATE_UP(1),HOUSE_STATE_DOWN(2);

    public  final Integer value;

    HouseState(Integer value){
        this.value = value;
    }
}
