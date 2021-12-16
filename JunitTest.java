package com.shuyue.inventory_server;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.report.impl.ConsoleReporter;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author haohua.rong
 * @description
 * @contact 13512835407@163.com
 * @date 2021/12/15 10:46
 */
@Slf4j
public class JunitTest {

    /**
     * threads:执行时使用多少线程
     * warmUp：准备时间
     * duration：执行时间
     * reporter：存放报告信息类
     */
    @JunitPerfConfig(threads = 10, warmUp = 1_000, duration = 30_000, reporter = {HtmlReporter.class, ConsoleReporter.class})
//    @Test
    public void testHomeInterface() {
        System.out.println("开始压测");
        try {
            CloseableHttpClient signedClient = getSignedClient();
            String requestBody = "{\"deliverNo\":\"\",\"partsOrderNo\":\"\",\"logisticsNo\":\"\",\"batchNumber\":\"\",\"purchaseOdd\":\"\",\"customerId\":\"\",\"customerName\":\"\",\"purchaseTime\":[\"\",\"\"],\"createName\":\"\",\"createTime\":[],\"deliverTime\":[],\"warehouseName\":\"\",\"status\":\"\",\"page\":1,\"pageSize\":10,\"makingTime\":[\"\",\"\"],\"shipmentsTime\":[\"\",\"\"]}";
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(requestBody.getBytes("UTF-8"));
            byteArrayEntity.setContentType("application/json");
            HttpPost httpPost = new HttpPost("http://localhost:8082/inventory/inventory-server/partsOrder/getPartsDeliverList");
            httpPost.setEntity(byteArrayEntity);
            String execute = signedClient.execute(httpPost, httpResponse -> {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode < 200 || statusCode >= 300) {
                    System.out.println(statusCode);
                }
                HttpEntity entity = httpResponse.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            });
            System.out.println(execute);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private CloseableHttpClient getSignedClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

//        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(), NoopHostnameVerifier.INSTANCE);

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).build();

//        registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory())
//                        .register("https",socketFactory).build();

        return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setSSLSocketFactory(socketFactory).build();
    }

}
