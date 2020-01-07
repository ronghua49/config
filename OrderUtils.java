package com.xyjsoft.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 字符串工具类
 * @author gsh456
 * @date Sep 1, 2018
 */
public class OrderUtils {

	/**
	 * 传入单号类型生成唯一单号
	 * @param type单号类型:档案  DA  采购合同  CGHT   采购订单  CGDD  采购补单  CGBD...
	 * @return
	 */
	public static String getCode(String type){
		if(type == null) {
			throw new RuntimeException("订单类型异常,请传入正确订单类型");
		}
		type = type.toUpperCase();
		Date date=new Date();
		DateFormat format = new SimpleDateFormat("yyMMdd");
		String time = format.format(date);
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {//有可能是负数
			hashCodeV = -hashCodeV;
		}
		return new StringBuffer().append(type).append(time).append(String.format("%010d", hashCodeV)).toString();
	}
}
