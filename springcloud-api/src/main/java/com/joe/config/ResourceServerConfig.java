package com.joe.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.commons.Result;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @description: 配置资源服务器
 * @author: Joe
 **/
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 资源服务器安全配置
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //设置资源服务器id,需要与认证服务器对应
        resources.resourceId("api-service");
        //当权限不足时返回
        resources.accessDeniedHandler((request, response, e) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter()
                    .write(objectMapper.writeValueAsString(Result.from("0001", "权限不足", null)));
        });
        //当token不正确时返回
        resources.authenticationEntryPoint((request, response, e) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter()
                    .write(objectMapper.writeValueAsString(Result.from("0002", "access_token错误", null)));
        });
    }

    /**
     * 配置uri拦截策略
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, exception) -> {
                    resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    resp.getWriter()
                            .write(objectMapper.writeValueAsString(Result.from("0002", "没有携带token", null)));
                })
                .and()
                //无需登陆
                .authorizeRequests().antMatchers("/test/noauth").permitAll()
                .and()
                //拦截所有请求,并且检查sope
                .authorizeRequests().anyRequest().access("isAuthenticated() && #oauth2.hasScope('ROLE_API')");
    }

}
