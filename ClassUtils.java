package com.xyjsoft.core.util;

import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类名:ClassUtils
 * 类描述:class相关的工具类
 *
 * @author gsh456
 * @version 1.0
 * @date 2019-11-27 8:51
 * @since JDK1.8
 */

public class ClassUtils {
    public static Field[] getDeclaredFields(T t){
        return getDeclaredFields(t.getClass());
    }
    public static Field[] getDeclaredFields(Class<?> aClass){
        if(isObject(aClass)){
            return null;
        }
        return aClass.getDeclaredFields();
    }
    public static <T> Field[] getAllDeclaredFields(T t){
        return getAllDeclaredFields(t.getClass());
    }
    /**
     * 获取一个类的所有字段（包含所有父类）
     * @param aClass class
     * @return java.lang.reflect.Field[] 所有字段
     * @since JDK1.8
     * @author gsh456
     * @date 2019/11/27 9:59
     */
    public static <T> Field[] getAllDeclaredFields(Class<?> aClass){
        Field[] fields = aClass.getDeclaredFields();
        List<Field> listField = new ArrayList<>(Arrays.asList(fields));
        while (!isObject(getSuperClass(aClass))){
            aClass = getSuperClass(aClass);
            Field[] superFields = aClass.getDeclaredFields();
            listField.addAll(Arrays.asList(superFields));
        }
        //过滤重复字段并进行转化
        return list2Field(filterSameName(listField));
    }
    /**
     * 数据转化
     * @param listField list
     * @return java.lang.reflect.Field[] 数组
     * @since JDK1.8
     * @author gsh456
     * @date 2019/11/27 9:54
     */
    private static Field[] list2Field(List<Field> listField){
        Field[] newField =new Field[listField.size()];
        return listField.toArray(newField);
    }
    /**
     * 过滤又有重复名字的字段
     * @param listField 原数据
     * @return java.util.List<java.lang.reflect.Field> 过滤后
     * @since JDK1.8
     * @author gsh456
     * @date 2019/11/27 9:50
     */
    private static List<Field> filterSameName(List<Field> listField){
        List<String> str= new ArrayList<>();
        List<Field> newList= new ArrayList<>();
        for (Field field : listField) {
            String name = field.getName();
            if(str.contains(name)){
                continue;
            }
            newList.add(field);
            str.add(field.getName());
        }
        return newList;
    }
    /**
     * 判断是否为 Object类
     * @param aClass 要判断的类
     * @return boolean 是否Object
     * @since JDK1.8
     * @author gsh456
     * @date 2019/11/27 9:50
     */
    private static boolean isObject(Class<?> aClass){
        if(aClass!=null){
            if(Object.class==aClass){
                return true;
            }
            if(aClass.getName().toLowerCase().equals("java.lang.object")){
                return true;
            }
        }else {
            return true;
        }
        return false;
    }
    /**
     * 获取父类
     * @param aClass 本类
     * @return java.lang.Class<?>  父类
     * @since JDK1.8
     * @author gsh456
     * @date 2019/11/27 9:51
     */
    private static Class<?> getSuperClass(Class<?> aClass){
        return aClass.getSuperclass();
    }
}
