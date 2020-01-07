package com.xyjsoft.core.util;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xyjsoft.core.util.StringUtils;

/**
 * 类名:BeanUtils
 * 类描述:TODO
 *
 * @author gsh456
 * @version 1.0
 * @date 2019-06-06 17:51
 * @since JDK1.8
 */

public class BeanUtils {
    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class); 
    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkAllProIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) != null && !StringUtils.isBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
        	logger.error("[BeanUtils:checkAllProIsNull]",e);
        }
        return true;
    }
    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkAllProIsNullWithOutId(Object object) {
        if (null == object) {
            return true;
        }
        Field[] declaredFields = object.getClass().getDeclaredFields();
        //不校验第一个和最后一个
        for (int i = 1; i < declaredFields.length-1; i++) {
            Field f =declaredFields[i];
            try {
                f.setAccessible(true);
                if (f.get(object) != null && !StringUtils.isBlank(f.get(object).toString())) {
                    return false;
                }
            } catch (Exception e) {
                logger.error("[BeanUtils:checkAllProIsNullWithOutId]",e);
            }
        }

        return true;
    }
    
	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}
	
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		if ((o instanceof String)) {
			if (((String) o).trim().length() == 0) {
				return true;
			}
		} else if ((o instanceof Collection)) {
			if (((Collection) o).size() == 0) {
				return true;
			}
		} else if (o.getClass().isArray()) {
			if (((Object[]) o).length == 0) {
				return true;
			}
		} else if ((o instanceof Map)) {
			if (((Map) o).size() == 0) {
				return true;
			}
		} else {
			if ((o instanceof Serializable)) {
				return ((Serializable) o).toString().trim().length() == 0;
			}
		}
		return false;
	}
	//验证实体类是否存在
	public static boolean validClass(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
		}
		return false;
	}

}
