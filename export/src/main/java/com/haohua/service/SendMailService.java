package com.haohua.service;


/**
 * @author L-heng
 */
public interface SendMailService {

    boolean isEmail(String email);

//    void sendMail(String email, String filePath, String title) throws Exception;

    void sendMail(String email, String[] filePath, String title) throws Exception;




}
