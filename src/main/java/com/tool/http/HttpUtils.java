package com.tool.http;

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
 * @Description: http 工具
 * @Author KerVinLi
 * @Version 1.0
 */
@Slf4j
public class HttpUtils {

    public <T> T requestStart(String requestJson, String requestUrl, String title, Class<T> tClass) {
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

        if (requestJson.length() < 3000000) {
            log.info("{}请求报文:{}", title, requestJson);
        } else {
            log.info("{}请求报文太大，不宜展示");
        }

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
                //throw new RuntimeException(CommonUtil.formatString("请求异常,异常返回码:{}", response.getStatusLine().getStatusCode()));
            }
        } catch (Exception e) {
            log.info("{}请求出错，出错信息:{}", title, e.getMessage());
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (requestJson.length() < 3000000) {
                log.info("{}处理完毕 耗时:{}ms \n请求报文:{} \n返回报文:{}", title, (System.currentTimeMillis() - startTime), requestJson, JSON.toJSONString(resp));
            } else {
                log.info("{}处理完毕 耗时:{}ms \n请求报文过大，不宜展示 \n返回报文:{}", title, (System.currentTimeMillis() - startTime), JSON.toJSONString(resp));
            }
        }
        return resp;
    }

}
