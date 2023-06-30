package com.benewake.saleordersystem.utils;

/**
 * @author Lcs
 * 描 述：一些通用常量定义
 */
public interface BenewakeConstants {
    /**
     * 一天的时间
     */
    int ONE_DAY = 1000*60*60*24;
    /**
     * 响应code：成功
     */
    Integer SUCCESS_CODE = 200;
    /**
     * 响应code：失败
     */
    Integer FAIL_CODE = 400;
    /**
     * 响应message：成功
     */
    String SUCCESS_MESSAGE = "success";
    /**
     * 响应message：失败
     */
    String FAIL_MESSAGE = "fail";
    /**
     * 默认状态的登录凭证超时时间 (7天)
     */
    int DEFAULT_EXPIRED_SECONDS = 1000*3600*24*7;
}
