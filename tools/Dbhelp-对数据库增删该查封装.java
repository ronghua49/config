package com.haohua.util;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haohua.Exception.DBAccessException;

public class Dbhelp {
	private static Logger logger = LoggerFactory.getLogger(Dbhelp.class);
	private static QueryRunner runner = new QueryRunner(ConncetionManager.getDataSource());

	public static <T> T Query(String sql, ResultSetHandler<T> rsh, Object... params) {
		T t = null;
		try {
			t = runner.query(sql, rsh, params);
		} catch (SQLException e) {
			logger.info(sql);
			e.printStackTrace();
			throw new DBAccessException("数据库访问异常");
		}
		return t;
	}

	public static void update(String sql, Object... params) {
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			logger.info(sql);
			e.printStackTrace();
			throw new DBAccessException("数据库访问异常");
		}	
	}
	public static<T>  T  insert(String sql,ResultSetHandler<T> rsh,Object...params) {
		
		T t = null;
		try {
			t = runner.insert(sql, rsh, params);
		} catch (SQLException e) {
			logger.info(sql);
			e.printStackTrace();
			throw new DBAccessException("数据库访问异常");
		}
		return t;
	}
}
