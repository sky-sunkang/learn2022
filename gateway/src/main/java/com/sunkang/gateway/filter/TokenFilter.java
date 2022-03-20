package com.sunkang.gateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            return chain.filter(exchange);
        }

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("token", "0FckSFlZ6jxL4UMeEH3pcXG7zzo");
        String s = restTemplate.postForObject("http://oauth:2000/oauth/check_token", new HashMap<String, String>().put("token", "0FckSFlZ6jxL4UMeEH3pcXG7zzo"), String.class);

        HttpHeaders headers = exchange.getRequest().getHeaders();
        //token无效
        if (StringUtils.isEmpty(headers.getFirst(TOKEN_KEY))) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        return chain.filter(exchange);
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
