package com.xyjsoft.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射相关辅助方法
 * @author gsh456
 * @date Aug 19, 2018
 */
public class ReflectionUtils {
	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
	
	/**
	 * 根据方法名调用指定对象的方法
	 * @param object 要调用方法的对象
	 * @param method 要调用的方法名
	 * @param args 参数对象数组
	 * @return
	 */
	public static Object invoke(Object object, String method, Object... args) {
		Object result = null;
		Class<? extends Object> clazz = object.getClass();
		Method queryMethod = getMethod(clazz, method, args);
		if(queryMethod != null) {
			try {
				result = queryMethod.invoke(object, args);
			} catch (IllegalAccessException e) {
				logger.error("[ReflectionUtils:invoke:IllegalAccessException]", e);
			} catch (IllegalArgumentException e) {
				logger.error("[ReflectionUtils:invoke:IllegalArgumentException]", e);
			} catch (InvocationTargetException e) {
				logger.error("[ReflectionUtils:invoke:InvocationTargetException]", e);
			}
		} else {
			try {
				throw new NoSuchMethodException(clazz.getName() + " 类中没有找到 " + method + " 方法。");
			} catch (NoSuchMethodException e) {
				logger.error("[ReflectionUtils:invoke:NoSuchMethodException]", e);
			}
		}
		return result;
	}
	
	/**
	 * 根据方法名和参数对象查找方法
	 * @param clazz
	 * @param name
	 * @param args 参数实例数据
	 * @return
	 */
	public static Method getMethod(Class<? extends Object> clazz, String name, Object[] args) {
		Method queryMethod = null;
		Method[] methods = clazz.getMethods();
		for(Method method:methods) {
			if(method.getName().equals(name)) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if(parameterTypes.length == args.length) {
					boolean isSameMethod = true;
					for(int i=0; i<parameterTypes.length; i++) {
						Object arg = args[i];
						if(arg == null) {
							arg = "";
						}
						if(!parameterTypes[i].equals(args[i].getClass())) {
							isSameMethod = false;
						}
					}
					if(isSameMethod) {
						queryMethod = method;
						break ;
					}
				}
			}
		}
		return queryMethod;
	}
}
