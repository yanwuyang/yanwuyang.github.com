package com.qqyx.file.manage.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * Copyright(c) 2015-2015 by yonyouup. 
 * All Rights Reserved
 * 数据转换帮助类
 * @author wang zhuo
 * @version <类版本> , 2015年5月19日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class ConvertUtil {
    
    /**
     * 数组转字符串 <br/>
     * [1,3,4,5] = > {1,2,4,5}
     * null = > {}
     * @param input
     * @return
     */
    public static <T> String ArrayToString(T[] input) {
        if  (input == null){
            return "{}";
        }
        StringBuilder sBuilder = new StringBuilder("{");
        for(T t : input) {
            sBuilder.append(t+",");
        }
        if (input.length > 0){
            sBuilder.replace(sBuilder.length()-1, sBuilder.length(), "");
        }
        sBuilder.append("}");
        return sBuilder.toString();
    }
    
    /**
     * 字符串转字符串数组
     * {1,2,3,4} => [1,2,3,4]
     * null => []
     * @param input
     * @return
     */
    public static String[] StringToArray(String input) {
        List<String> resList = new ArrayList<String>();
        
        if (input == null || input.length() < 2){
            return resList.toArray(new String[]{});
        }
        if (input.charAt(0) == '{' && input.charAt(input.length()-1) == '}'){
            input = input.substring(1, input.length()-1);
            String[] strs = input.split(",");
            for(String item : strs) {
                resList.add(item);
            }
            return resList.toArray(new String[]{});
        }
        return resList.toArray(new String[]{});
    }
}
