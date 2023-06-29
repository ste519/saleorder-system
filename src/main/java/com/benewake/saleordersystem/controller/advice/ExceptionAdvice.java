package com.benewake.saleordersystem.controller.advice;

import com.benewake.saleordersystem.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Lcs
 * 描述：全局捕获异常并返回相应提示
 */
@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class ExceptionAdvice{

    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error("服务器发生异常："+e.getMessage());
        for(StackTraceElement element : e.getStackTrace()){
            log.error(element.toString());
        }
        response.setContentType("application/plain;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(CommonUtils.getJSONString(1,"服务器异常！"));
    }
}
