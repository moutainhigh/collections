package com.esp.server.housezuul.result;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/27 22:28
 * @Description:
 */
public class Message {
    private int code; // 状态码
    private String message; // 消息
    private Object data; // 返回的数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
