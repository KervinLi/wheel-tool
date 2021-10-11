package com.tool.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

/**
 * @Description: http 工具类
 * @Author KerVinLi
 * @Version 1.0
 */
@Slf4j
public class HttpUtils {
    /**
     * http请求
     *
     * @param requestJson 请求报文Json字符串
     * @param requestUrl 请求地址
     * @param title 请求标题 日志跟踪
     * @param tClass 相应报文 实体
     * @return
     */
    public static <T> T requestPost(String requestJson, String requestUrl, String title, Class<T> tClass) {
        long startTime = System.currentTimeMillis();
        T resp = null;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(30000)
                .setConnectionRequestTimeout(30000).setSocketTimeout(90000)
                .build();

        HttpPost post = new HttpPost(requestUrl);
        post.setHeader("Content-type", "application/json;charset=utf-8");
        post.setHeader("Connection", "Close");
        post.setConfig(config);

        // 构建消息实体
        StringEntity entity = new StringEntity(requestJson, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求
        entity.setContentType("application/json");
        post.setEntity(entity);

        HttpResponse response = null;
        try {
            response = httpClient.execute(post);
            //返回成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String responseJson = EntityUtils.toString(response.getEntity(), "UTF-8");
                resp = JSON.parseObject(responseJson, tClass);
                log.info("{} 返回报文: {}", title, responseJson);
            } else {
                log.info("{}请求异常，异常信息：{}", title, JSON.toJSONString(response));
                throw new RuntimeException(StringUtils.formatString("请求异常,异常返回码:{}", response.getStatusLine().getStatusCode()));
            }
        } catch (Exception e) {
            log.info("{}请求出错，出错信息:{}", title, e.getMessage());
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            log.info("{}处理完毕 耗时:{}ms \n请求报文:{} \n返回报文:{}", title, (System.currentTimeMillis() - startTime), requestJson, JSON.toJSONString(resp));
        }
        return resp;
    }
}
