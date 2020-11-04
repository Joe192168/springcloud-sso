package com.joe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @description: 配置资源服务器
 * @author: Joe
 **/
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${spring.application.name}")
    private String resourceId;
    @Autowired
    private TokenStore tokenStore;

    //private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 资源服务器安全配置
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //设置资源服务器id,需要与认证服务器对应
        resources.resourceId(resourceId);
        //本地校验令牌
        resources.tokenStore(tokenStore);
        //远程验证令牌的服务
        //resources.tokenServices(tokenService());
        resources.stateless(true);
        //下面是对yml文件中远程校验toke配置
        /*//当权限不足时返回
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
        });*/
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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //无需登陆
                .authorizeRequests().antMatchers("/test/noauth").permitAll()
                .and()
                //拦截所有请求,并且检查sope
                .authorizeRequests().antMatchers("/**").access("#oauth2.hasScope('all')");
    }

    /**
     * 资源服务令牌解析服务
     */
    /*@Bean
    public ResourceServerTokenServices tokenService() {
        //使用远程服务请求授权服务器校验token,必须指定校验token 的url、client_id，client_secret
        RemoteTokenServices service=new RemoteTokenServices();
        service.setCheckTokenEndpointUrl("http://localhost:8082/oauth/check_token");
        service.setClientId("c1");
        service.setClientSecret("123");
        return service;
    }*/

}
