package com.xyjsoft.core.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO相关工具类
 * @author gsh456
 * @date 2019-03-15 11:10
 */
public class IOUtils {

	/**
	 * 关闭对象，连接
	 * @param closeable
	 */
    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }
}
