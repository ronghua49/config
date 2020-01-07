package com.xyjsoft.core.util;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 类名:FileUtils
 * 类描述:文件工具
 *
 * @author gsh456
 * @version 1.0
 * @date 2019-09-04 10:10
 * @since JDK1.8
 */

public class FileUtils {
    /**
     * excel导出
     * @param response 响应
     * @param workbook 数据
     * @param fileName 文件名
     * @since JDK1.8
     * @author gsh456
     * @date 2019/9/5 10:48
     */
    public static void exportExcel(HttpServletResponse response, Workbook workbook, String fileName) throws Exception {
        response.reset();
        response.setContentType("application/x-msdownload;charset=utf-8");
        if(StringUtils.isBlank(fileName)){
            fileName="数据导出";
        }
        fileName+= ".xlsx";
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
        response.setHeader("fileName", URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "fileName");
        ServletOutputStream outStream = null;
        try {
            outStream = response.getOutputStream();
            workbook.write(outStream);
        } finally {
            outStream.close();
        }
    }
    /**
     * 根据文件名判断是否图片
     * @param fileName 文件名
     * @since JDK1.8
     * @author gsh456
     * @date 2019/9/5 10:48
     */
    public static boolean judgePicByFileName(String fileName){
        if(StringUtils.isBlank(fileName)){
            return false;
        }
        String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.PNG|.png|.BMP|.bmp)$";
        return fileName.matches(reg);
    }
    /**
     * 根据文件路径下载文件
     * @param path 文件路径
     * @param response 响应
     * @since JDK1.8
     * @author gsh456
     * @date 2019/9/5 10:48
     */
    public static String downloadFile(String path, HttpServletResponse response){
        File file = new File(path);
        if(!file.exists()){
            throw new RuntimeException("文件不存在，文件可能被删除。");
        }
        DataInputStream in = null;
        OutputStream out = null;
        try {
            String name = file.getName();
            //输入流：本地文件路径
            response.reset();
            FileInputStream inputStream = new FileInputStream(file);
            XyjUtils.setResponse(response, name);
            /*response.setContentType("application/octet-stream;chaset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
            response.setHeader("fileName", URLEncoder.encode(name, "UTF-8"));
            response.setHeader("Content-Length",inputStream.available()+"");
            response.setHeader("Access-Control-Expose-Headers", "fileName");*/
            
            in = new DataInputStream(inputStream);
            //输出流
            out = response.getOutputStream();
            //输出文件
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) >0) {
                out.write(bufferOut, 0, bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
