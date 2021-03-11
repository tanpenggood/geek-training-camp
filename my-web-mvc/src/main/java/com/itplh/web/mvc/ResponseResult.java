package com.itplh.web.mvc;

/**
 * @author: tanpenggood
 * @date: 2021-03-09 23:43
 */
public class ResponseResult<T> {

    private int code;
    private boolean success;
    private String message;
    private String path;
    private long timestamp;

    public static <T> ResponseResult<T> ok(String message, String path) {
        return new ResponseResult<>(1, true, message, path, System.currentTimeMillis());
    }

    public static <T> ResponseResult<T> error(String message, String path) {
        return new ResponseResult<>(0, false, message, path, System.currentTimeMillis());
    }

    private ResponseResult(int code, boolean success, String message, String path, long timestamp) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}
