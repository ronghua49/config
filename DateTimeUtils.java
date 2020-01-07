package com.xyjsoft.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期时间相关工具
 * @author gsh456
 * @date Sep 23, 2018
 */
public class DateTimeUtils {
	private static Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_FORMAT = "yyyy年MM月";
	/**
	 * 获取当前标准格式化日期时间
	 * @param date
	 * @return
	 */
	public static String getDateTime() {
		return getDateTime(new Date());
	}
	
	/**
	 * 标准格式化日期时间
	 * @param date
	 * @return
	 */
	public static String getDateTime(Date date) {
		return (new SimpleDateFormat(DATE_FORMAT)).format(date);
	}
	public static Date getDateTimeMoth(String date) {
		try {
			return (new SimpleDateFormat(DATETIME_FORMAT)).parse(date);
		} catch (ParseException e) {
			logger.error("时间格式错误！", e);
			throw new RuntimeException("时间格式错误！");
		}
	}
	public static void main(String[] args) {
		try {
			Date date = (new SimpleDateFormat(DATETIME_FORMAT)).parse("2018年5月");
			System.out.println(date);
		} catch (ParseException e) {
		}
	}
}
