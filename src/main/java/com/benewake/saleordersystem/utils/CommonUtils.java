package com.benewake.saleordersystem.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.benewake.saleordersystem.entity.Md;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Lcs
 * 描述：一些通用工具
 */
public class CommonUtils {
    /**
     * 订单状态映射表
     */
    private static final Map<String,String> ORDER_STATUS_MAP;
    static {
        ORDER_STATUS_MAP = new HashMap<>();
        ORDER_STATUS_MAP.put("A","创建");
        ORDER_STATUS_MAP.put("B","审核中");
        ORDER_STATUS_MAP.put("Z","暂存");
        ORDER_STATUS_MAP.put("C","已审核");
        ORDER_STATUS_MAP.put("D","重新审核");
    }
    public static String getOrderStatus(String key){
        return ORDER_STATUS_MAP.get(key);
    }

    // 生成随机字符串UUID
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    // MD5 加密
    public static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    public static String getJSONString(int code, String msg, Map<String,Object> map){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        if(map!=null){
            for(String key : map.keySet()){
                json.put(key,map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code,String msg){
        return getJSONString(code,msg,null);
    }

    public static String getJSONString(int code){
        return getJSONString(code,null,null);
    }

    /**
     * 将数据写入Excel
     * @param file
     * @param data
     * @param type
     * @return
     */
    public static boolean writeExcel(File file, List data,Class type){
        //1、创建一个文件对象
        //2、判断文件是否存在，不存在则创建一个Excel文件
        if (!file.exists()) {
            try {
                file.createNewFile();//创建一个新的文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //3、指定需要那个class去写。然后写到第一个sheet，名字为模版，然后文件流会自动关闭
        EasyExcel.write(file, type).sheet("订单模版").doWrite(data);
        return true;
    }

}
