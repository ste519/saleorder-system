package com.benewake.saleordersystem.utils;

import lombok.Data;

/**
 * @author lcs
 * 描述：封装统一返回结果类型
*/
@Data
public class Result<T> implements BenewakeConstants{


    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    private Result() {
    }

    public static <T> Result<T> success() {
        Result<T> Result = new Result<>();
        Result.setCode(SUCCESS_CODE);
        Result.setMsg(SUCCESS_MESSAGE);
        return Result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> Result = success();
        Result.setData(data);
        return Result;
    }

    public static <T> Result<T> success(String message, T data) {
        Result<T> Result = success();
        Result.setMsg(message);
        Result.setData(data);
        return Result;
    }

    public static <T> Result<T> success(Integer code, String message, T data) {
        Result<T> Result = new Result<>();
        Result.setCode(code);
        Result.setMsg(message);
        Result.setData(data);
        return Result;
    }

    public static <T> Result<T> fail() {
        Result<T> Result = new Result<>();
        Result.setCode(FAIL_CODE);
        Result.setMsg(FAIL_MESSAGE);
        return Result;
    }

    public static <T> Result<T> fail(T data) {
        Result<T> Result = fail();
        Result.setData(data);
        return Result;
    }

    public static <T> Result<T> fail(String message, T data) {
        Result<T> Result = fail();
        Result.setMsg(message);
        Result.setData(data);
        return Result;
    }

    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> Result = fail();
        Result.setCode(code);
        Result.setMsg(message);
        return Result;
    }

    public static <T> Result<T> fail(Integer code, String message, T data) {
        Result<T> Result = new Result<>();
        Result.setCode(code);
        Result.setMsg(message);
        Result.setData(data);
        return Result;
    }

}
