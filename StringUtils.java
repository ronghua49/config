package com.xyjsoft.core.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 字符串工具类
 *
 * @author gsh456
 * @date Sep 1, 2018
 */
@Api(tags="StringUtils 字符串工具类")
public class StringUtils {
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    public static final String EMPTY = "";
    /**
     * 判空操作
     *
     * @param value
     * @return
     */
    @ApiOperation(value="判断字符串是否为空",notes = "判断字符串是否为空,返回true为空，返回false则不为空")
    public static boolean isBlank(@ApiParam(name="value",value="要检查的字符串") String value) {
        return value == null || "".equals(value) || "null".equals(value) || "undefined".equals(value);
    }

    public static boolean isNotBlank(String value) {
        value = Optional.ofNullable(value).orElse("");
        return value.length() != 0;
    }

    /**
     * <b>类名:</b> AbstractManagerImpl <br>
     * <b>方法名</b>: listToString <br>
     * <b>详细描述</b>: id数组转换为 (1,2,3)类型的字符串  <br>
     *
     * @param list id数组
     * @return java.lang.String (1,2,3)类型的字符串
     * @author gsh456
     * @date 2019/4/2 10:57
     */
    public static String listToString(List<Long> list) {
        StringBuffer sb = new StringBuffer().append("(");
        for (Long item : list) {
            sb.append(item + ",");
        }
        return sb.substring(0, sb.length() - 1) + ")";
    }
    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String under2Camels(String str) {
        StringBuilder result = new StringBuilder();
        if (str != null && str.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }
    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String camel2Under(String str) {
        StringBuilder result = new StringBuilder();
        if (str != null && str.length() > 0) {
            result.append(str.substring(0, 1).toLowerCase());
            for (int i = 1; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }
    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 首字母转小写
     * @param str
     * @return
     */
    public static String uncapitalize(String str) {
        if (Character.isLowerCase(str.charAt(0)))
            return str;
        else
            return (new StringBuilder()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    /**
     * 首字母转大写
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        if (Character.isUpperCase(str.charAt(0)))
            return str;
        else
            return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).toString();
    }
    
    /**
     * UFT-8字符串截取  宋建国
     * @param originalStr 要截取的字符串
     * @param bitNum 截取到第几位
     * @return
     */
    public static String InterceptString(String originalStr,int bitNum){
    	int byteLength;
		try {
			byteLength = originalStr.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8字符串截取错误:"+e.getMessage());
		}
    	if(byteLength > bitNum){
    		originalStr = originalStr.substring(0,originalStr.length()-1);
    		originalStr = InterceptString(originalStr, bitNum);
    	}
    	return originalStr;
    }

    public static void main(String[] args) {
        String str1 ="java_test";
        String str2 ="javaTest";
        String str3 ="java_Test";
        long l1 = System.currentTimeMillis();
        String s1 = under2Camels(str1);
        long d1 = System.currentTimeMillis()-l1;
        long l2 = System.currentTimeMillis();
        String s2 = camel2Under(str2);
        long d2 = System.currentTimeMillis()-l2;
        long l3 = System.currentTimeMillis();
        String s3 = camel2Under(str3);
        long d3 = System.currentTimeMillis()-l3;
        long l4 = System.currentTimeMillis();
        String s4 = humpToLine(str3);
        long d4 = System.currentTimeMillis()-l4;
        System.out.println(d1+" s1 = " + s1);
        System.out.println(d2+" s2 = " + s2);
        System.out.println(d3+" s3 = " + s3);
        System.out.println(d4+" s4 = " + s4);
        System.out.println(l4-l1);
    }
}
