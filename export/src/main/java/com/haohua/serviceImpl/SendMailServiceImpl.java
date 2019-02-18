package com.haohua.serviceImpl;

import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.haohua.service.SendMailService;
import org.springframework.stereotype.Service;

/**
 * @author L-heng
 */
@Service
public class SendMailServiceImpl implements SendMailService {

    @Override
    public boolean isEmail(String email) {
        //判断是否为空邮箱
        int k = 0;
        if (email == null) {
            return false;
        }
         /*
          * 单引号引的数据 是char类型的
            双引号引的数据 是String类型的
            单引号只能引一个字符
            而双引号可以引0个及其以上*
          */

        //判断是否有仅有一个@且不能在开头或结尾
        if (email.indexOf("@") > 0 && email.indexOf('@') == email.lastIndexOf('@') && email.indexOf('@') < email.length() - 1) {
            k++;
        }

        //判断"@"之后必须有"."且不能紧跟
        if (email.indexOf('.', email.indexOf('@')) > email.indexOf('@') + 1) {
            k++;
        }
        //判断"@"之前或之后不能紧跟"."
        if (email.indexOf('.') < email.indexOf('@') - 1 || email.indexOf('.') > email.indexOf('@') + 1) {
            k++;
        }
        //@之前要有6个字符
        if (email.indexOf('@') > 5) {
            k++;
        }

        if (email.endsWith("com") || email.endsWith("org") || email.endsWith("cn") || email.endsWith("net")) {
            k++;
        }
        if (k == 5) {
            return true;
        }
        return false;
    }

    @Override
    public void sendMail(String email, String[] filePath, String title) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.163.com");
        prop.setProperty("mail.transport.protocol", "smtps");
        prop.setProperty("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        prop.put("mail.smtp.host", "smtp.163.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.timeout", "10000");

        prop.put("mail.smtp.ssl.checkserveridentity", "false");
        prop.put("mail.smtp.ssl.trust", "*");
        prop.put("mail.smtp.connectiontimeout", "10000");

        prop.put("mail.smtp.socketFactory.fallback", "false");

        // 使用JavaMail发送邮件的5个步骤
        // 1、创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(false);
        // 2、通过session得到transport对象
        Transport ts = session.getTransport("smtps");
        // 3、连上邮件服务器
        ts.connect("smtp.163.com", "13512835407@163.com", "RHH19910523");
        // 4、创建邮件
        //		    Message message = createAttachMail(session);
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息
        // 发件人
        message.setFrom(new InternetAddress("13512835407@163.com"));
        // 收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        // 邮件标题
        message.setSubject("职场通行证-" + title);
        // 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("<br>" + "您好：<br>" + "    给你传两张图片请查看！\n" + "<br>" + "<br>" + "<br>" + "<br>" + "<br>" + "<br>" + "<br>" + "-------------------------------------------<br>" + "公司名称：北京升谱科技有限公司<br>" + "公司网址：http://www.risepu.com<br>" + "公司公众号：职场通行证<br>" + "公司热线：010-53357562", "text/html;charset=UTF-8");
        MimeMultipart mp = new MimeMultipart();
        //			DataHandler dh = new DataHandler(new FileDataSource("src\\2.jpg"));
        if(filePath!=null){
            for(int i = 0;i<filePath.length;i++){
                // 创建邮件附件 多个附件
                MimeBodyPart attach = new MimeBodyPart();
                DataHandler dh = new DataHandler(new FileDataSource(filePath[i]));
                attach.setDataHandler(dh);
                attach.setFileName(MimeUtility.encodeText(dh.getName()));
                mp.addBodyPart(attach);
            }
        }
        // 创建容器描述数据关系
        mp.addBodyPart(text);
        mp.setSubType("mixed");
        message.setContent(mp);
        message.saveChanges();
//         将创建的Email写入到E盘存储
//         message.writeTo(new FileOutputStream("E:\\attachMail.eml"));
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }
}
