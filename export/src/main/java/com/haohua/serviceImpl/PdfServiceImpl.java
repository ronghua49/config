package com.haohua.serviceImpl;

import com.haohua.model.Pdf;
import com.haohua.service.PdfService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @author L-heng
 */
@Service
public class PdfServiceImpl implements PdfService {


    @Override
    public String pdf(String _template, String hash, String title, String qrFilePath, String GrFilePath, String pdfFilePath, Integer hashSize, Integer titleSize, Integer contentSize) throws Exception {
        // TODO Auto-generated method stub
//        _template = _template.replaceAll("/t", " ");
        //设置纸张
        Rectangle rect = new Rectangle(PageSize.A4);
        //创建文档实例
        Document doc = new Document(rect, 80, 80, 50, 50);
        //添加中文字体
        BaseFont bfChinese = BaseFont.createFont("/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        //设置字体样式
        Font textFont1 = new Font(bfChinese, 14, Font.BOLD); //加粗
        Font hashFont = new Font(bfChinese, hashSize, Font.UNDEFINED); //哈希
        hashFont.setColor(BaseColor.RED);
        Font contentFont = new Font(bfChinese, contentSize, Font.UNDEFINED); //正文
        Font titleFont = new Font(bfChinese, titleSize, Font.BOLD); //标题


        //创建输出流
        PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(new File(pdfFilePath)));

        doc.open();
        PdfContentByte cd = pdfWriter.getDirectContent();
        doc.newPage();

//        //段落
//        Paragraph p1 = new Paragraph("【职场通行证】", textFont1);
//        p1.setAlignment(Element.ALIGN_CENTER);
//        doc.add(p1);

        Paragraph p1 = new Paragraph();
//        p1.setLeading(30);
        //短语
        Phrase ph1 = new Phrase();
        //块
        Chunk c2 = new Chunk("区块链防伪编码：", hashFont);
        Chunk c22 = new Chunk(hash, hashFont);
        //将块添加到短语
        ph1.add(c2);
        ph1.add(c22);
        //将短语添加到段落
        p1.add(ph1);
        //将段落添加到短语
//        p1.setAlignment(Element.ALIGN_CENTER);

        doc.add(p1);

        p1 = new Paragraph(title, titleFont);
        //设置行间距
        p1.setLeading(60);
        p1.setAlignment(Element.ALIGN_CENTER);
        doc.add(p1);

        p1 = new Paragraph(" ");
        //设置行间距
        p1.setLeading(20);
        doc.add(p1);

        String[] a = _template.split("/n");
        for (int i = 0; i < a.length; i++) {
            p1 = new Paragraph();
            ph1 = new Phrase();
            Chunk c1 = new Chunk(a[i], contentFont);
            p1.setLeading(30);
            ph1.add(c1);
            p1.add(ph1);
            doc.add(p1);
        }

        //插入一个二维码图片
        Image image = Image.getInstance(qrFilePath);
        image.setAbsolutePosition(80, 170);//坐标
        image.scaleAbsolute(172, 172);//自定义大小
        doc.add(image);

        cd.beginText();
//        cd.setFontAndSize(bfChinese, 14);
//        cd.showTextAligned(Element.ALIGN_UNDEFINED, "【职场通行证】", 120, 145, 0);

        cd.setFontAndSize(bfChinese, 14);
        cd.showTextAligned(Element.ALIGN_UNDEFINED, "扫一扫   验真伪", 115, 150, 0);
        cd.endText();

        //插入公司盖章图片
        Image image1 = Image.getInstance(GrFilePath);
        image1.setAbsolutePosition(300, 185);//坐标
        image1.scaleAbsolute(210, 60);//自定义大小
        doc.add(image1);

        cd.beginText();
        //文字加粗
        //设置文本描边宽度
        //cd.setLineWidth(0.5);
        //设置文本为描边模式
        //cd.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
        cd.setFontAndSize(bfChinese, 14);
        cd.showTextAligned(Element.ALIGN_UNDEFINED, "xxxx年xx月xx日", 360, 150, 0);
        cd.endText();

        doc.close();
        return pdfFilePath;
    }

//    public static void main(String[] args) {
////        PdfServiceImpl a = new PdfServiceImpl();
////        try {
////            a.pdf("     张三（身份证号152502199106267252）电话：18518431201。自2015年01月01日入职我公司担任 品质总监职位至2018年12月01 日因个人原因 离职，经公司研究决定，同意其离职，已办理离职手续且与我公司解除劳动关系，双方无劳动纠纷。\n" +
////                    "     对在我公司任职期间获知的技术秘密和商业秘密 ， 负有保密义务，自离职之日起两年内有义务保守我公司的技术秘密和商务秘密。 \n" +
////                    "     特此证明！", "SFDSFSFSFSDFSDGSFDGDFGDFGDFGDGD", "离职证明", "/file-path/2019-01/04/示例二维码.jpg", "/file-path/2019-01/04/示例盖章.jpg", "/file-path/test.pdf", 12, 26, 14);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }

    @Override
    public String pdf(Map<String, String> map, String hash, String qrFilePath, String GrFilePath, String pdfFilePath) throws Exception {

        //获取pdf标题
        String title ="测试";
        int number = 0;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日");
        String date = "" + ft.format(new Date());

        //设置纸张
        Rectangle rect = new Rectangle(PageSize.A4);
        //创建文档实例
        Document doc = new Document(rect, 80, 80, 50, 50);

        //添加中文字体
        BaseFont bfChinese = BaseFont.createFont("/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        //设置字体样式
        Font textFont = new Font(bfChinese, 13, Font.BOLD); //加粗
        Font textFont1 = new Font(bfChinese, 14, Font.BOLD); //加粗
        Font hashFont = new Font(bfChinese,12, Font.UNDEFINED); //哈希
        hashFont.setColor(BaseColor.RED);
        Font contentFont = new Font(bfChinese, 10, Font.UNDEFINED); //正文
        Font titleFont = new Font(bfChinese, 20, Font.BOLD); //标题

        //创建输出流
        PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(new File(pdfFilePath)));

        doc.open();
        PdfContentByte cd = pdfWriter.getDirectContent();
        doc.newPage();

        Paragraph p1 = new Paragraph();
        //短语
        Phrase ph1 = new Phrase();
        //块
        Chunk c1 = new Chunk("区块链防伪编码：", hashFont);
        Chunk c11 = new Chunk(hash, hashFont);
        //将块添加到短语
        ph1.add(c1);
        ph1.add(c11);
        //将短语添加到段落
        p1.add(ph1);
        //将段落添加到短语
        doc.add(p1);

        p1 = new Paragraph(title, titleFont);
        //设置行间距
        p1.setLeading(60);
        p1.setAlignment(Element.ALIGN_CENTER);
        doc.add(p1);

        p1 = new Paragraph(" ");
        //设置行间距
        p1.setLeading(20);
        doc.add(p1);

        p1 = new Paragraph();
        ph1 = new Phrase();
        p1.setLeading(30);

        String _template = " 姓名：${name}，身份证号：${idCard}，电话号码：${tel}。自${hire_date}入职我公司担任 品质总监职位至${departure_date} 日因个人原因 离职，经公司研究决定，同意其离职，已办理离职手续且与我公司解除劳动关系，双方无劳动纠纷。\n"+
                 "对在我公司任职期间获知的技术秘密和商业秘密 ， 负有保密义务，自离职之日起两年内有义务保守我公司的技术秘密和商务秘密。";

        List list = Arrays.asList(new String[]{"name","idCard","tel","hire_date","departure_date"});

        List list1 = Arrays.asList(new String[]{"姓名","身份证号","电话号码","入职日期","离职日期"});

        Map<String,Pdf> map1 = new HashMap();


        int index = 0;
        for (int j = 0; j < list.size(); j++) {
            String text = (String)list.get(j);
            String key = "${" + text + "}";
            String key1 = text;
            //得到页面输入的值  根据模板字段 查找对应
            while ((index = _template.indexOf(key, index)) != -1) {
                String value = (String) list1.get(j);
                Pdf pdf = new Pdf();
                pdf.setIndex(index);
                pdf.setKey(key);
                pdf.setValue(value);
                pdf.setCode(key1);
                map1.put(key1, pdf);
                index = index + key.length();
            }
        }

        String content = "";
        //得到页面输入的值
        while ((index = _template.indexOf("${", number)) != -1) {
            int index2 = _template.indexOf("}", index);
            String key = _template.substring(index + 2, index2);
            //根据key 字段 获得对应的对象
            Pdf pdf1 = map1.get(key);
            String value = map.get(pdf1.getCode());
            if (number > index) {
                content = _template.substring(number);
            } else {
                content = _template.substring(number, index);
            }
            Chunk c2 = new Chunk(content, contentFont);
            Chunk c22 = new Chunk(" " + value + " ", textFont);
            c22.setUnderline(0.1f, -2f);
            ph1.add(c2);
            ph1.add(c22);
            number = index + pdf1.getKey().length();
        }
        content = _template.substring(number);
        Chunk c2 = new Chunk(content, contentFont);
        ph1.add(c2);

        p1.add(ph1);
        doc.add(p1);
        //插入一个二维码图片
        Image image = Image.getInstance(qrFilePath);
        image.setAbsolutePosition(80, 170);//坐标
        image.scaleAbsolute(172, 172);//自定义大小
        doc.add(image);

        cd.beginText();

        cd.setFontAndSize(bfChinese, 14);
        cd.showTextAligned(Element.ALIGN_UNDEFINED, "扫一扫   验真伪", 115, 150, 0);
        cd.endText();

        //插入公司盖章图片
        Image image1 = Image.getInstance(GrFilePath);
        image1.setAbsolutePosition(300, 185);//坐标
        image1.scaleAbsolute(210, 60);//自定义大小
        doc.add(image1);

        cd.beginText();

        cd.setFontAndSize(bfChinese, 14);
        cd.showTextAligned(Element.ALIGN_UNDEFINED, date, 360, 150, 0);
        cd.endText();
        doc.close();
        return pdfFilePath;
    }

}
