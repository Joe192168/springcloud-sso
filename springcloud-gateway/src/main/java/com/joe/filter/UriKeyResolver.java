package com.joe.filter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName UriKeyResolver
 * @Desc TODO   Spring Cloud Gateway 网关限流过滤器
 * @Version 1.0
 */
public class UriKeyResolver implements KeyResolver {

    /**a
     * 根据请求的 uri 限流
     * @param exchange
     * @return
     */
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getURI().getPath());
    }

}