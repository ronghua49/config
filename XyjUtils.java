package com.xyjsoft.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;

import com.xyjsoft.core.exception.XyjException;

/**
 * 讯宜捷工具类，如果需求功能在此类中存在，则请大家使用。
 * @author sjg
 * 2019年8月11日 下午10:49:43  
 *
 */
public class XyjUtils {
	/**
	 * 
	 * @describe 向前端传输文件时，设置response的头部信息并且在头部增加文件名信息，方便前端从头部取出文件名信息
	 * @author sjg 
	 * @date 2019年8月11日 下午10:59:56
	 * @version 1.0
	 * @param response 响应前端对象
	 * @param fieldName 下载的文件名
	 * @return 返回 所传入的response并且已经设置传输二进制流数据的头部信息
	 */
	public static HttpServletResponse setResponse(HttpServletResponse response,String fieldName){
		response.setContentType("application/x-msdownload;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers","Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
		try {
			response.setHeader("fileName", URLEncoder.encode(fieldName, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new XyjException(e.getMessage());
		}
		response.setHeader("Access-Control-Expose-Headers","fileName");
    	response.setCharacterEncoding("utf-8");
    	return response;
	}
	
	/**
	 * @describe 设置返回图片流文件Response函数
	 * @author sjg 
	 * @date 2019年9月24日 下午3:27:05
	 * @version 1.0
	 * @param response
	 * @return
	 */
	public static HttpServletResponse setResponseImage(HttpServletResponse response){
		response.reset();
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
        response.setContentType("image/png");
		return response;
	}
	
	/**
	 * @describe 将xyjError错误信息写入到头部，方便前端拿到错误信息
	 * @author sjg 
	 * @date 2019年9月24日 下午3:33:08
	 * @version 1.0
	 * @param response
	 * @param xyjError
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static HttpServletResponse setResponseError(HttpServletResponse response,String xyjError) throws UnsupportedEncodingException{
		response.setHeader("xyjError", URLEncoder.encode(xyjError, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "xyjError");
		return response;
	}
}
