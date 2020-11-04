package com.joe;

import com.joe.filter.TokenFilter;
import com.joe.filter.UriKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @description: 网关启动类
 * @author: Joe
 **/
@SpringBootApplication
@EnableEurekaClient
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class,args);
    }

    /**
     * 配置限流 bean
     * @return
     */
    @Bean(name = "uriKeyResolver")
    public UriKeyResolver uriKeyResolver() {
        return new UriKeyResolver();
    }

    /**
     * 配置认证过滤器 Bean
     * @return
     */
    @Bean(name = "tokenFilter")
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }

}
