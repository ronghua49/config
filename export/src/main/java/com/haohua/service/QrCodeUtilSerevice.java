package com.haohua.service;

import java.io.InputStream;

public interface QrCodeUtilSerevice {
    /**
     * 生成二维码
     *
     * @param filePath 二维码输出流路径
     * @param content  二维码携带的信息
     * @return
     */
    String createQrCode(String filePath, String content) throws Exception;

    /**
     * 读取二维码
     *
     * @param inputStream
     */
    void readQrCode(InputStream inputStream) throws Exception;
}
