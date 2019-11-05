package com.xyjsoft.shzs.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xyjsoft.shzs.model.CcbUserSms;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class sendSmsUtils {

    //无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    //无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";
    //短信签名
    private static final   String  signature="讯宜捷科技";
    //必填,请参考"开发准备"获取如下数据,替换为实际值
    private static    String url = "https://api.rtc.huaweicloud.com:10443/sms/batchSendSms/v1"; //APP接入地址+接口访问URI
    private static    String appKey = "8t0U3uV9IkHSI5pm5xEU2aZ5w0Vk"; //APP_Key
    private static    String appSecret = "10L91bzu7V7ml504hQnEA5L7mWuO"; //APP_Secret
    private static    String sender = "881909179272"; //国内短信签名通道号或国际/港澳台短信通道号
    //private static    String templateId = "151e06b96b404e6285cffbcaf3d8727d"; //模板ID

    public static  String[] templateId  = {
            "151e06b96b404e6285cffbcaf3d8727d"//0您的验证码为：${NUM_6}，为保护账户安全，请勿向他人提供此验证码。
    };
    public static  String[] templateParam  = {
            "验证码 您的验证码为：${NUM_6}，为保护账户安全，请勿向他人提供此验证码"
    };

    ////选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告
    private static   String statusCallBack = "";

    public static  String  getYzm(String phone,Integer type,String code) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException {
        /**
         * 选填,使用无变量模板时请赋空值 String templateParas = "";
         * 单变量模板示例:模板内容为"您的验证码是${NUM_6}"时,templateParas可填写为"[\"369751\"]"
         * 双变量模板示例:模板内容为"您有${NUM_2}件快递请到${TXT_20}领取"时,templateParas可填写为"[\"3\",\"人民公园正门\"]"
         * ${DATE}${TIME}变量不允许取值为空,${TXT_20}变量可以使用英文空格或点号替代空值,${NUM_6}变量可以使用0替代空值
         * 查看更多模板和变量规范:产品介绍>模板和变量规范
         */
        String templateParas = "[\""+code+"\"]"; //模板变量
        //请求Body,不携带签名名称时,signature请填null
        String body = buildRequestBody(sender, phone, templateId[type], templateParas, statusCallBack, signature);
        if (null == body || body.isEmpty()) {
          throw   new RuntimeException("华为云短信发送失败，body为空");
        }

        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            throw   new RuntimeException("华为云短信发送失败，请求Headers中的X-WSSE参数值为空");
        }

        //如果JDK版本是1.8,可使用如下代码
        //为防止因HTTPS证书认证失败造成API调用失败,需要先忽略证书信任问题
        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
                        (x509CertChain, authType) -> true).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        HttpResponse response = client.execute(RequestBuilder.create("POST")//请求方法POST
                .setUri(url)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
                .addHeader("X-WSSE", wsseHeader)
                .setEntity(new StringEntity(body)).build());
        JSONObject JsonT = JSON.parseObject(EntityUtils.toString(response.getEntity()));
        String description = JsonT.getString("description");
        return  description;
    }
    /**
     * 构造请求Body体
     * @param sender
     * @param receiver
     * @param templateId
     * @param templateParas
     * @param statusCallbackUrl
     * @param signature | 签名名称,使用国内短信通用模板时填写
     * @return
     */
    static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
                                   String statusCallbackUrl, String signature) {
        if (null == sender || null == receiver || null == templateId || sender.isEmpty() || receiver.isEmpty()
                || templateId.isEmpty()) {
            System.out.println("buildRequestBody(): sender, receiver or templateId is null.");
            return null;
        }
        List<NameValuePair> keyValues = new ArrayList<NameValuePair>();

        keyValues.add(new BasicNameValuePair("from", sender));
        keyValues.add(new BasicNameValuePair("to", receiver));
        keyValues.add(new BasicNameValuePair("templateId", templateId));
        if (null != templateParas && !templateParas.isEmpty()) {
            keyValues.add(new BasicNameValuePair("templateParas", templateParas));
        }
        if (null != statusCallbackUrl && !statusCallbackUrl.isEmpty()) {
            keyValues.add(new BasicNameValuePair("statusCallback", statusCallbackUrl));
        }
        if (null != signature && !signature.isEmpty()) {
            keyValues.add(new BasicNameValuePair("signature", signature));
        }

        return URLEncodedUtils.format(keyValues, Charset.forName("UTF-8"));
    }

    /**
     * 构造X-WSSE参数值
     * @param appKey
     * @param appSecret
     * @return
     */
    static String buildWsseHeader(String appKey, String appSecret) {
        if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
            System.out.println("buildWsseHeader(): appKey or appSecret is null.");
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date()); //Created
        String nonce = UUID.randomUUID().toString().replace("-", ""); //Nonce

        byte[] passwordDigest = DigestUtils.sha256(nonce + time + appSecret);
        String hexDigest = Hex.encodeHexString(passwordDigest);
        //如果JDK版本是1.8,请加载原生Base64类,并使用如下代码
        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(hexDigest.getBytes()); //PasswordDigest
        //如果JDK版本低于1.8,请加载三方库提供Base64类,并使用如下代码
        //String passwordDigestBase64Str = Base64.encodeBase64String(hexDigest.getBytes(Charset.forName("utf-8"))); //PasswordDigest
        //若passwordDigestBase64Str中包含换行符,请执行如下代码进行修正
        //passwordDigestBase64Str = passwordDigestBase64Str.replaceAll("[\\s*\t\n\r]", "");
        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    public static String getNumYZM(int t){
        // 定义验证码的字符表
        String chars = "0123456789";
        char[] rands = new char[t];
        for (int i = 0; i < t; i++) {
            int rand = (int) (Math.random() * 10);
            rands[i] = chars.charAt(rand);
        }
        return String.valueOf(rands);
    }

    /**
     * 保存短信发送记录
     * @param phone
     * @param type
     * @param code
     * @return
     */
   public  static CcbUserSms   getappuserSms(String phone,Integer type,String code){
       CcbUserSms ccbUserSms=new CcbUserSms();
       ccbUserSms.setPhone(phone);
       ccbUserSms.setSetdate(new Date());
       ccbUserSms.setType(templateParam[type].split(" ")[0]);
       ccbUserSms.setMemo("【"+signature+"】"+templateParam[type].split(" ")[1].replace("${NUM_6}",code));
       return ccbUserSms;
   }

}


