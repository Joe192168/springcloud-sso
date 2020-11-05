package com.joe.filter;

import com.alibaba.fastjson.JSON;
import com.joe.commons.enums.ResponseCodeEnum;
import com.joe.commons.vo.Result;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @ClassName TokenFilter
 * @Desc TODO   请求认证过滤器
 */

public class TokenFilter implements GlobalFilter{

    private String SIGNING_KEY = "sso123";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 请求对象
        ServerHttpRequest request = exchange.getRequest();
        // 响应对象
        ServerHttpResponse response = exchange.getResponse();
        // 从请求headers中获取token参数
        String token = request.getHeaders().getFirst("Authorization");
        //判断token为空，返回提示信息
        if (StringUtils.isBlank(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return getVoidMono(response, ResponseCodeEnum.TOKEN_MISSION);
        }
        Claims claims = null;
        //验证token
        try {
            token = StringUtils.substringAfter(token,"Bearer ");
            //利用签名解析token获取信息
            claims = Jwts.parser().setSigningKey(SIGNING_KEY.getBytes("UTF-8"))
                    .parseClaimsJws(token).getBody();
        } catch (MalformedJwtException malformedJwtException) {
            return getVoidMono(response,ResponseCodeEnum.TOKEN_INVALID);
        } catch (SignatureException signatureException) {
            return getVoidMono(response,ResponseCodeEnum.TOKEN_SIGNATURE_INVALID);
        } catch (ExpiredJwtException tokenExpiredException) {
            return getVoidMono(response,ResponseCodeEnum.TOKEN_EXPIRED);
        } catch (Exception ex) {
            return getVoidMono(response,ResponseCodeEnum.UNKNOWN_ERROR);
        }
        String userName = (String)claims.get("user_name");
        ServerHttpRequest mutableReq = request.mutate().header("userName", userName).build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
        return chain.filter(mutableExchange);
    }

    private Mono<Void> getVoidMono(ServerHttpResponse serverHttpResponse, ResponseCodeEnum responseCodeEnum) {
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        Result responseResult = Result.error(responseCodeEnum.getCode(), responseCodeEnum.getMessage());
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(JSON.toJSONString(responseResult).getBytes());
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }
 
}
