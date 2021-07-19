package com.esp.server.commentserver.model;

import lombok.Data;

/**
 * @ProjectName: microservice-house
 * @Auther: GERRY
 * @Date: 2018/11/8 20:23
 * @Description: 定制Api响应类(Result)
 */
@Data
public class ApiResponse<T> {
    private int code; // 状态码
    private String message; // 消息
    private T data; // 返回的数据

    public ApiResponse() {

    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 定义常用静态方法
     */
    public static <T> ApiResponse ofSuccess(T data) {
        return new ApiResponse(Status.SUCCESS.getCode(), Status.SUCCESS.getStandardMessage(), data);
    }

    public static ApiResponse ofSuccess() {
        return new ApiResponse(Status.SUCCESS.getCode(), Status.SUCCESS.getStandardMessage(), null);
    }

    public static ApiResponse ofError() {
        return new ApiResponse(Status.ERROR.getCode(), Status.ERROR.getStandardMessage(), null);
    }

    public static <T> ApiResponse ofError(T data) {
        return new ApiResponse(Status.ERROR.getCode(), Status.ERROR.getStandardMessage(), data);
    }

    public static ApiResponse ofMessage(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    public static ApiResponse ofStatus(Status status) {
        return new ApiResponse(status.getCode(), status.getStandardMessage(), null);
    }

    /**
     * 定义状态枚举
     */
    public enum Status {
        SUCCESS(100, "OK"),
        ERROR(200, "fail");

        private int code;
        private String standardMessage;

        Status(int code, String standardMessage) {
            this.code = code;
            this.standardMessage = standardMessage;
        }

        public int getCode() {
            return code;
        }

        public String getStandardMessage() {
            return standardMessage;
        }

    }
}
