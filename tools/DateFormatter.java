/*
 * MIT License
 *
 * Copyright (c) 2008-2017 q-wang, &lt;apeidou@gmail.com&gt;
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.lc4ever.framework.format;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

/**
 * DateFormat常用格式封装.
 * 
 * @dependency compile 'joda-time:joda-time:2.1+'
 * @revision $Revision$
 * @author <a href="mailto:apeidou@gmail.com">Q-Wang</a>
 */
public final class DateFormatter {

	/** Date Format String: "yyyyMMdd" */
	public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
	
	/** Date Format String "yyyy年MM月dd日"*/
	public static final String yyyyMMdd ="yyyy年MM月dd日";

	/** Date Format String: "yyyy-MM-dd" */
	public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";

	/** Date Format String: "HHmmss" */
	public static final String PATTERN_HHmmss = "HHmmss";

	/** Date Format String: "HH:mm:ss" */
	public static final String PATTERN_HH_mm_ss = "HH:mm:ss";

	/** Date Format String: "yyyyMMddHHmmss" */
	public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";

	/** Date Format String: "yyyy-MM-dd HH:mm:ss" */
	public static final String PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

	/** Date Format String: "yyyy-MM-dd HH:mm" */
	public static final String PATTERN_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

	/** Date Format String: "HH:mm" */
	public static final String PATTERN_HH_mm = "HH:mm";

	/** Date Format String: "HHmmssSSS" */
	public static final String PATTERN_HHmmssSSS = "HHmmssSSS";

	/** Date Format String: "yyyy-MM/dd", used in: file store etc. */
	public static final String PATTERN_DATEPATH = "yyyy-MM/dd";

	private DateFormatter() {
	}

	/**
	 * Format date using pattern.
	 * 
	 * @param pattern
	 *            {@link org.joda.time.format.DateTimeFormat} patterns
	 * @param date
	 *            date to format
	 * @return formatted string for date
	 */
	public static String format(final String pattern, final Date date) {
		if (date==null) {
			return null;
		}
		return new DateTime(date).toString(pattern);
	}

	/**
	 * Format calendar using pattern.
	 * 
	 * @param pattern
	 *            {@link org.joda.time.format.DateTimeFormat} patterns
	 * @param calendar
	 *            calendar to format
	 * @return formatted string for calendar
	 */
	public static String format(final String pattern, final Calendar calendar) {
		if (calendar==null) {
			return null;
		}
		return new DateTime(calendar).toString(pattern);
	}

	/**
	 * Parse string to date using pattern.
	 * 
	 * @param pattern
	 *            {@link org.joda.time.format.DateTimeFormat} patterns
	 * @param date
	 *            date string to parse
	 * @return parsed date
	 */
	public static Date parse(final String pattern, final String date) {
		if (date==null) {
			return null;
		}
		return DateTimeFormat.forPattern(pattern).parseDateTime(date).toDate();
	}

	// ========================================================================
	/**
	 * Format current date using pattern.
	 * 
	 * @param pattern
	 *            {@link org.joda.time.format.DateTimeFormat} patterns
	 * @return formatted string for current date
	 */
	public static String format(final String pattern) {
		return format(pattern, now());
	}

	// ========================================================================
	// ========================================================================
	/**
	 * Format date using pattern {@link #PATTERN_yyyyMMdd "yyyyMMdd"}.
	 * 
	 * @param date
	 *            date to format
	 * @return formatted string for date
	 */
	public static String yyyyMMdd(final Date date) {
		return format(PATTERN_yyyyMMdd, date);
	}
	
	public static String yyyyMMdd2(final Date date) {
		return format(yyyyMMdd, date);
	}

	/**
	 * Format calendar using pattern {@link #PATTERN_yyyyMMdd "yyyyMMdd"}.
	 * 
	 * @param calendar
	 *            calendar to format
	 * @return formatted string for calendar
	 */
	public static String yyyyMMdd(final Calendar calendar) {
		return format(PATTERN_yyyyMMdd, calendar);
	}

	/**
	 * Format current date using {@link #PATTERN_yyyyMMdd "yyyyMMdd"}.
	 * 
	 * @return formatted string for current date
	 */
	public static String yyyyMMdd() {
		return format(PATTERN_yyyyMMdd, now());
	}

	/**
	 * Parse date string using {@link #PATTERN_yyyyMMdd "yyyyMMdd"}.
	 * 
	 * @param date
	 *            date string to parse
	 * @return parsed date
	 */
	public static Date yyyyMMdd(final String date) {
		return parse(PATTERN_yyyyMMdd, date);
	}

	// ========================================================================
	/**
	 * Format date string using pattern {@link #PATTERN_HHmmss "HHmmss"}.
	 * 
	 * @param date
	 *            date to format
	 * @return formatted string for date
	 */
	public static String HHmmss(final Date date) {
		return format(PATTERN_HHmmss, date);
	}

	/**
	 * Format calendar using pattern {@link #PATTERN_HHmmss "HHmmss"}.
	 * 
	 * @param calendar
	 *            calendar to format
	 * @return formatted string for calendar
	 */
	public static String HHmmss(final Calendar calendar) {
		return format(PATTERN_HHmmss, calendar);
	}

	/**
	 * Format current date using {@link #PATTERN_HHmmss "HHmmss"}.
	 * 
	 * @return formatted string for current date
	 */
	public static String HHmmss() {
		return format(PATTERN_HHmmss, now());
	}

	/**
	 * Parse date string using {@link #PATTERN_HHmmss "HHmmss"}.
	 * 
	 * @param date
	 *            date string to parse
	 * @return parsed date
	 */
	public static Date HHmmss(final String date) {
		return parse(PATTERN_HHmmss, date);
	}

	// ========================================================================

	/**
	 * Format date using {@link #PATTERN_yyyy_MM_dd "yyyy-MM-dd"}.
	 * 
	 * @param date
	 *            date to format
	 * @return formatted string for date
	 */
	public static String yyyy_MM_dd(final Date date) {
		return format(PATTERN_yyyy_MM_dd, date);
	}

	/**
	 * Format calendar using {@link #PATTERN_yyyy_MM_dd "yyyy-MM-dd"}.
	 * 
	 * @param calendar
	 *            calendar to format
	 * @return formatted string for calendar
	 */
	public static String yyyy_MM_dd(final Calendar calendar) {
		return format(PATTERN_yyyy_MM_dd, calendar);
	}

	/**
	 * Format current date using {@link #PATTERN_yyyy_MM_dd "yyyy-MM-dd"}.
	 * 
	 * @return formatted string for current date
	 */
	public static String yyyy_MM_dd() {
		return format(PATTERN_yyyy_MM_dd, now());
	}

	/**
	 * Parse date string using {@link #PATTERN_yyyy_MM_dd "yyyy-MM-dd"}.
	 * 
	 * @param date
	 *            date string to parse
	 * @return parsed date
	 */
	public static Date yyyy_MM_dd(final String date) {
		return parse(PATTERN_yyyy_MM_dd, date);
	}

	// ========================================================================

	public static String HH_mm_ss(final Date date) {
		return format(PATTERN_HH_mm_ss, date);
	}

	public static String HH_mm_ss(final Calendar calendar) {
		return format(PATTERN_HH_mm_ss, calendar);
	}

	public static String HH_mm_ss() {
		return format(PATTERN_HH_mm_ss, now());
	}

	public static Date HH_mm_ss(final String date) {
		return parse(PATTERN_HH_mm_ss, date);
	}

	// ========================================================================

	public static String yyyyMMddHHmmss(final Date date) {
		return format(PATTERN_yyyyMMddHHmmss, date);
	}

	public static String yyyyMMddHHmmss(final Calendar calendar) {
		return format(PATTERN_yyyyMMddHHmmss, calendar);
	}

	public static String yyyyMMddHHmmss() {
		return format(PATTERN_yyyyMMddHHmmss, now());
	}

	public static Date yyyyMMddHHmmss(final String date) {
		return parse(PATTERN_yyyyMMddHHmmss, date);
	}

	// ========================================================================

	public static String yyyy_MM_dd_HH_mm_ss(final Date date) {
		return format(PATTERN_yyyy_MM_dd_HH_mm_ss, date);
	}

	public static String yyyy_MM_dd_HH_mm_ss(final Calendar calendar) {
		return format(PATTERN_yyyy_MM_dd_HH_mm_ss, calendar);
	}

	public static String yyyy_MM_dd_HH_mm_ss() {
		return format(PATTERN_yyyy_MM_dd_HH_mm_ss, now());
	}

	public static Date yyyy_MM_dd_HH_mm_ss(final String date) {
		return parse(PATTERN_yyyy_MM_dd_HH_mm_ss, date);
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
	
	// ========================================================================

	public static String yyyy_MM_dd_HH_mm(final Date date) {
		return format(PATTERN_yyyy_MM_dd_HH_mm, date);
	}

	public static String yyyy_MM_dd_HH_mm(final Calendar calendar) {
		return format(PATTERN_yyyy_MM_dd_HH_mm, calendar);
	}

	public static String yyyy_MM_dd_HH_mm() {
		return format(PATTERN_yyyy_MM_dd_HH_mm, now());
	}

	public static Date yyyy_MM_dd_HH_mm(final String date) {
		return parse(PATTERN_yyyy_MM_dd_HH_mm, date);
	}

	// ========================================================================

	public static String HH_mm(final Date date) {
		return format(PATTERN_HH_mm, date);
	}

	public static String HH_mm(final Calendar calendar) {
		return format(PATTERN_HH_mm, calendar);
	}

	public static String HH_mm() {
		return format(PATTERN_HH_mm, now());
	}

	public static Date HH_mm(String date) {
		return parse(PATTERN_HH_mm, date);
	}

	// ========================================================================
	/**
	 * Format date string using pattern {@link #PATTERN_HHmmssSSS "HHmmssSSS"}.
	 * 
	 * @param date
	 * @return
	 */
	public static String HHmmssSSS(final Date date) {
		return format(PATTERN_HHmmssSSS, date);
	}

	public static String HHmmssSSS(final Calendar date) {
		return format(PATTERN_HHmmssSSS, date);
	}

	public static String HHmmssSSS() {
		return format(PATTERN_HHmmssSSS, now());
	}

	public static Date HHmmssSSS(String date) {
		return parse(PATTERN_HHmmssSSS, date);
	}

	// ========================================================================

	/**
	 * @see DateTime#now()
	 * @return current DateTime (eg. new Date())
	 */
	public static Date now() {
		return DateTime.now().toDate();
	}

	
	/**
	 * @see Calendar#getInstance()
	 * @return currentCalendar
	 */
	public static Calendar calendar() {
		return DateTime.now().toCalendar(Locale.getDefault());
	}

	/**
	 * @see System#currentTimeMillis()
	 * @return the difference, measured in milliseconds, between the current
	 *         time and midnight, January 1, 1970 UTC.
	 */
	public static long millis() {
		return System.currentTimeMillis();
	}

	public static final int MILLIS_PER_SECOND = 1000;

	public static final int SECONDS_PER_MINUTE = 60;

	public static final int MINUTES_PER_HOUR = 60;

	public static final int HOURS_PER_DAY = 24;

	public static final int DAYS_PER_WEEK = 7;

	/**
	 * Get current date with time 00:00:00.
	 * 
	 * @return current date with time: 00:00:00
	 */
	public static Date startOfDay() {
		return startOfDay(now());
	}

	public static Date endOfDay() {
		return endOfDay(now());
	}

	/**
	 * Get gived date with time: 00:00:00.
	 * 
	 * @param date
	 *            given date
	 * @return gived date with time: 00:00:00
	 */
	public static Date startOfDay(final Date date) {
		return new DateTime(date.getTime()).withTimeAtStartOfDay().toDate();
	}

	public static Date endOfDay(final Date date) {
		return new DateTime(date.getTime()).millisOfDay().withMaximumValue().toDate();
	}

	/**
	 * Get current month first day with time: 00:00:00.
	 * 
	 * @return current month first day with time: 00:00:00
	 */
	public static Date startOfMonth() {
		return startOfMonth(now());
	}

	/**
	 * Get month first day with time: 00:00:00 of given date.
	 * 
	 * @param date
	 *            given date
	 * @return first day of month with time: 00:00:00 for given date.
	 */
	public static Date startOfMonth(final Date date) {
		return new DateTime(date.getTime()).dayOfMonth().withMinimumValue().withTimeAtStartOfDay().toDate();
	}

	public static Date endOfMonth() {
		return DateTime.now().dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toDate();
	}

	public static Date endOfMonth(final Date date) {
		return new DateTime(date.getTime()).dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toDate();
	}

	public static Date startOfWeek() {
		return DateTime.now().dayOfWeek().withMinimumValue().withTimeAtStartOfDay().toDate();
	}

	public static Date startOfWeek(final Date date) {
		return new DateTime(date.getTime()).dayOfWeek().withMinimumValue().withTimeAtStartOfDay().toDate();
	}

	public static Date endOfWeek() {
		return DateTime.now().dayOfWeek().withMaximumValue().millisOfDay().withMaximumValue().toDate();
	}

	public static Date endOfWeek(final Date date) {
		return new DateTime(date).dayOfWeek().withMaximumValue().millisOfDay().withMaximumValue().toDate();
	}

	public static boolean expiredSeconds(final Date from, final int seconds) {
		return expiredSeconds(from, now(), seconds);
	}

	public static boolean expiredSeconds(final Date from, final Date to, final int seconds) {
		return new DateTime(from).plusSeconds(seconds).isBefore(to.getTime());
	}

	public static boolean expiredMinutes(final Date from, final int minutes) {
		return expiredMinutes(from, now(), minutes);
	}

	public static boolean expiredMinutes(final Date from, final Date to, final int minutes) {
		return new DateTime(from).plusMinutes(minutes).isBefore(to.getTime());
	}

	public static boolean expiredHours(final Date from, final int hours) {
		return expiredHours(from, now(), hours);
	}

	public static boolean expiredHours(final Date from, final Date to, final int hours) {
		return new DateTime(from).plusHours(hours).isBefore(to.getTime());
	}

	public static boolean expiredDays(final Date from, final int days) {
		return expiredDays(from, now(), days);
	}

	public static boolean expiredDays(final Date from, final Date to, final int days) {
		return new DateTime(from).plusDays(days).isBefore(to.getTime());
	}

	public static boolean expiredMonth(final Date from, final int month) {
		return expiredMonth(from, now(), month);
	}

	public static boolean expiredMonth(final Date from, final Date to, final int months) {
		return new DateTime(from).plusMonths(months).isBefore(to.getTime());
	}

	public static boolean sameDay(final Date from) {
		return sameDay(from, now());
	}

	public static boolean sameDay(final Date from, final Date to) {
		return startOfDay(from).equals(startOfDay(to));
	}

	public static boolean sameWeek(final Date from) {
		return sameWeek(from, now());
	}

	public static boolean sameWeek(final Date from, final Date to) {
		return startOfWeek(from).equals(startOfWeek(to));
	}

	public static int days(Date from, Date to) {
		from = startOfDay(from);
		to = startOfDay(to);
		return (int) ((to.getTime() - from.getTime()) / (1000 * 3600 * 24));
	}

	public static String datePath() {
		return format(PATTERN_DATEPATH);
	}

	public static String datePath(Date date) {
		return format(PATTERN_DATEPATH, date);
	}

	public static java.sql.Date sqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	public static java.sql.Date sqlDate() {
		return sqlDate(now());
	}

	public static java.sql.Time sqlTime(Date date) {
		return new java.sql.Time(date.getTime());
	}

	public static java.sql.Time sqlTime() {
		return sqlTime(now());
	}

	public static java.sql.Timestamp sqlTimestamp(Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	public static java.sql.Timestamp sqlTimestamp() {
		return sqlTimestamp(now());
	}

	public static Date nextYear(Date from) {
		return nextYear(from, 1);
	}

	public static Date previousYear(Date from) {
		return nextYear(from, -1);
	}

	public static Date nextDay(Date from) {
		return nextDay(from, 1);
	}

	public static Date previousDay(Date from) {
		return nextDay(from, -1);
	}

	public static Date nextWeek(Date from) {
		return nextWeek(from, 1);
	}

	public static Date previousWeek(Date from) {
		return nextDay(from, -1);
	}

	public static Date nextMonth(Date from) {
		return nextMonth(from, 1);
	}

	public static Date previousMonth(Date from) {
		return nextMonth(from, -1);
	}

	public static Date nextHour(Date from) {
		return nextHour(from, 1);
	}

	public static Date previousHour(Date from) {
		return nextHour(from, -1);
	}

	public static Date nextYear(Date from, int years) {
		return new DateTime(from).plusYears(years).toDate();
	}

	public static Date nextDay(Date from, int days) {
		return new DateTime(from).plusDays(days).toDate();
	}

	public static Date nextWeek(Date from, int weeks) {
		return new DateTime(from).plusWeeks(weeks).toDate();
	}

	public static Date nextMonth(Date from, int months) {
		return new DateTime(from).plusMonths(months).toDate();
	}

	public static Date nextHour(Date from, int hours) {
		return new DateTime(from).plusHours(hours).toDate();
	}

	public static Date next(Date from, Period period) {
		return new DateTime(from).plus(period).toDate();
	}

	public static Date next(Period period) {
		return DateTime.now().plus(period).toDate();
	}
	
	@SuppressWarnings("deprecation")
	public static int months(Date from, Date to) {
		return (to.getMonth() - from.getMonth()) + (to.getYear() - from.getYear()) *12;
	}

}
