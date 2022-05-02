package com.sixsixsix516.im.client.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author SUN
 * @date 2021/3/15
 */
@Slf4j
public class HttpUtil {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    static {
        REST_TEMPLATE.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    /**
     * 发送POST JSON请求
     *
     * @param url  请求地址
     * @param data 请求数据
     */
    public static JSONObject post(String url, Object data) {
        return post(url, data, JSONObject.class);
    }

    /**
     * 发送POST JSON请求
     *
     * @param url         请求地址
     * @param data        请求数据
     * @param returnClass 返回类型
     */
    public static <T> T post(String url, Object data, Class<T> returnClass) {
        log.info("发送HTTP post请求 url: {},data: {}", url, data);
        String resultStr = REST_TEMPLATE.postForObject(url, data, String.class);
        log.info("HTTP POST请求返回数据: {}", resultStr);
        return JSON.parseObject(resultStr, returnClass);
    }


    /**
     * 发送GET请求
     */
    public static JSONObject get(String url) {
        return get(url, JSONObject.class);
    }

    /**
     * 发送GET请求: 返回值类型自定义
     */
    public static <T> T get(String url, Class<T> returnClass) {
        log.info("发送HTTP GET请求 url: {}", url);
        String resultStr = REST_TEMPLATE.getForObject(URI.create(url), String.class);
        log.info("HTTP GET请求返回数据: {}", resultStr);
        return JSON.parseObject(resultStr, returnClass);
    }


}
