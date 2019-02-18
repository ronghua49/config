package com.haohua.controller;    /*
 * @author  Administrator
 * @date 2019/2/14
 */

import com.haohua.service.SendMailService;
import com.haohua.service.ZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.Arrays;

@Controller
public class sendMailController {

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private ZipService zipService;


    /**
     *邮箱发送附件文件地址
     * @return
     */
    @GetMapping("/send")
    @ResponseBody
    public String  sendMail(){
        try {
            sendMailService.sendMail("1540307734@qq.com", new String[]{"E:\\img\\0a8a1e2343f641deb62aa0d27abc4f67.jpg", "E:\\img\\6df12c6073534e609d1acf96331433ef.jpg"},"测试邮件");
            return  "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";

        }
    }


    /**
     * 压缩文件zip发送
     * @return
     */
    @GetMapping("/send2")
    @ResponseBody
    public String  sendMail2(){
        String[] picPaths = {"E:\\img\\0a8a1e2343f641deb62aa0d27abc4f67.jpg", "E:\\img\\6df12c6073534e609d1acf96331433ef.jpg"};
        try {
            zipService.zipFiles(Arrays.asList(picPaths),new File("E:\\img\\两张图片.zip"));
            sendMailService.sendMail("1540307734@qq.com",new String[]{"E:\\img\\两张图片.zip"},"测试邮件");
            return  "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";

        }
    }



}
