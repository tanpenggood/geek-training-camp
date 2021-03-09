package com.itplh.projects.user.domain;

/**
 * @author: tanpenggood
 * @date: 2021-03-09 23:43
 */
public class Result<T> {

    private int code;
    private boolean success;
    private String message;
    private T data;
    private long timestamp;

    public static <T> Result<T> ok() {
        return Result.ok("操作成功");
    }

    public static <T> Result<T> ok(String message) {
        return Result.ok(message, null);
    }

    public static <T> Result<T> ok(String message, T data) {
        return new Result<>(1, true, message, data, System.currentTimeMillis());
    }

    public static <T> Result<T> error() {
        return Result.error("操作失败");
    }

    public static <T> Result<T> error(String message) {
        return Result.error(message, null);
    }

    public static <T> Result<T> error(String message, T data) {
        return new Result<>(0, false, message, data, System.currentTimeMillis());
    }

    private Result(int code, boolean success, String message, T data, long timestamp) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }

}
