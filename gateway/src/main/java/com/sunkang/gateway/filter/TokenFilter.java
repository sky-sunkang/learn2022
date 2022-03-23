package com.sunkang.gateway.filter;

import cn.hutool.json.JSONObject;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.xml.transform.Result;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 全局token过滤器
 */
@Slf4j
@Data
@Component
public class TokenFilter implements GlobalFilter, Order {

    private static final String TOKEN_KEY = "token";

    @Value("${filter.exclude}")
    private List<String> excludeList = new ArrayList<>();

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //测试redis json
        //redisTemplate.boundValueOps("哈哈哈").set("红红火火恍恍惚惚");　

        //不需要鉴权处理
        if (excludeList.contains(exchange.getRequest().getURI().getPath())) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(TOKEN_KEY);

        if (StringUtils.isEmpty(token)) {
            return unauthorized(exchange);
        }

        if (!checkToken(token)) {
            return unauthorized(exchange);
        }

        return chain.filter(exchange);
    }


    /**
     * 校验token 可尝试feign调用
     *
     * @param token
     * @return
     */
    public boolean checkToken(String token) {
        //异步调用oauth2认证 ，新版gateway不能阻塞调用
        CompletableFuture<JSONObject> f = CompletableFuture.supplyAsync(() -> {
            MultiValueMap<String, String> forms = new LinkedMultiValueMap<String, String>();
            forms.put("token", Collections.singletonList(token));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(forms, headers);
            JSONObject body = restTemplate.postForObject("http://oauth:2000/oauth/check_token", httpEntity, JSONObject.class);
            return body;
        });
        try {
            boolean result = f.get().getBool("active");
            return result;
        } catch (Exception e) {
            log.error("check_token error", e);
            return false;
        }
    }


    /**
     * 校验不通过 401
     *
     * @param exchange
     * @return
     */
    public Mono<Void> unauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    /**
     * 顺序，可定义顺序
     *
     * @return
     */
    @Override
    public int value() {
        return 1;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

}
