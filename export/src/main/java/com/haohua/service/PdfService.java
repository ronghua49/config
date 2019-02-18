package com.haohua.service;

import java.util.Map;

/**
 * @author L-heng
 */
public interface PdfService {
    /**
     * 生成pdf
     *
     * @param _template 一次模板内容
     * @return 生成pdf的路径
     * @throws Exception
     */
    String pdf(String _template, String hash, String title, String qrFilePath, String GrFilePath, String pdfFilePath, Integer hashSize, Integer titleSize, Integer contentSize) throws Exception;

    String pdf(Map<String, String> map, String hash, String qrFilePath, String GrFilePath, String pdfFilePath) throws Exception;

}
