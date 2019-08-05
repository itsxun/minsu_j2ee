package com.minsu.structure.entity;

/**
 * @author LIHAO845
 * @date 2016-11-11
 */
public class ResponseDTO<T> {

    private String code;
    private String msg;
    private T result;

    public ResponseDTO(String code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public static ResponseDTO<?> success() {
        return success(null);
    }

    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>("success", null, data);
    }

    public static <T> ResponseDTO<T> invalid(T data) {
        return new ResponseDTO<>("invalid", null, data);
    }

    public static ResponseDTO<String> invalid(String msg) {
        return new ResponseDTO<>("invalid", msg, null);
    }

    public static ResponseDTO<String> exception(Throwable e) {
        return new ResponseDTO<>(e instanceof InterruptedException ? "interrupted" : "exception", e.toString(), null);
    }

    public static <T> ResponseDTO<T> exception(String message) {
        return new ResponseDTO<>("exception", message, null);
    }

    public static <T> ResponseDTO<T> error(String message) {
        return new ResponseDTO<>("error", message, null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
