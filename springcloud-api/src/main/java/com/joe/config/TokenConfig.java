package com.joe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration
public class TokenConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    private String SIGNING_KEY = "sso123";

    /**
     * 令牌存储方式
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        //存储缓存中
        //return new InMemoryTokenStore();
        //存储数据库
        //return new JwtTokenStore(accessTokenConverter());
        //存储Redis
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * Jwt访问令牌转换器
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY); //对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }

}
