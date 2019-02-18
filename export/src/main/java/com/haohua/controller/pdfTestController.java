package com.haohua.controller;    /*
 * @author  Administrator
 * @date 2019/2/17
 */

import com.haohua.service.PdfService;
import com.haohua.service.QrCodeUtilSerevice;
import com.haohua.utils.ChartGraphics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class pdfTestController {

    @Autowired
    private QrCodeUtilSerevice qrCodeUtilSerevice;

    @Autowired
    private PdfService pdfService;


    /**
     * 生成盖章图片
     * @return
     */
    @ResponseBody
    @GetMapping("/img")
    public String createPic(){
        ChartGraphics cg = new ChartGraphics();
        cg.graphicsGeneration("我爱你有限公司","E:\\我爱你有限公司.png");
        return  "success";

    }


    /**
     * java generated qrcode
     * @return
     */
    @ResponseBody
    @GetMapping("/qrcode")
    public String createQrcode(){
        try {
            qrCodeUtilSerevice.createQrCode("E:\\qr2.jpg","我很伤心不是因为你欺骗了我，而是因为我不能够在信任你了");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    @ResponseBody
    @GetMapping("/qrcode/read")
    public String readQrcode(){
        try {
            qrCodeUtilSerevice.readQrCode(new FileInputStream(new File("E:\\qr2.jpg")));
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }




    @ResponseBody
    @GetMapping("/pdf")
    public String  createPdf(){

        String template = " ${name}:张三（${idCard}:152502199106267252）${tel}：18518431201。自${hire_date}入职我公司担任 品质总监职位至${departure_date} 日因个人原因 离职，经公司研究决定，同意其离职，已办理离职手续且与我公司解除劳动关系，双方无劳动纠纷。对在我公司任职期间获知的技术秘密和商业秘密 ， 负有保密义务，自离职之日起两年内有义务保守我公司的技术秘密和商务秘密。";
        try {
            pdfService.pdf(template,"sdjfhbishfbv","pdf测试","E:\\qr2.jpg","E:\\我爱你有限公司.png","E:\\1.pdf",10,20,15);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @ResponseBody
    @GetMapping("/pdf2")
    public String  createPdf2(){
        Map<String,String> map =  new HashMap<>();
        map.put("name","tom");
        map.put("tel","110");
        map.put("idCard","410181829746928743");
        map.put("hire_date","1928年5月9日");
        map.put("departure_date","1090年9月8日");

        try {
            pdfService.pdf(map,"dsjfhbsi","E:\\qr2.jpg","E:\\我爱你有限公司.png","E:\\3.pdf");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }


    }




}
