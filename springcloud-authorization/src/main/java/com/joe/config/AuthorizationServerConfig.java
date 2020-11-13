package com.joe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @description: 认证服务器配置
 * @author: Joe
 **/
@Order(2)
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private DruidConfig druidConfig;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;
    @Autowired
    private UserDetailsServiceConfig userDetailsService;

    /**
     * 数据源配置
     * @return
     */
    @Bean
    public DataSource dataSource() {
          return druidConfig.dataSource();
    }

    /**
     * 将客户端信息从数据库获取
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsService(){
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource());
        ((JdbcClientDetailsService)clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    /**
     * 授权码服务
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        //设置授权码模式的授权码如何存取，暂时采用内存方式
        //return new InMemoryAuthorizationCodeServices();
        //采用数据方式存储授权码
        return new JdbcAuthorizationCodeServices(dataSource());
    }

    /**
     * 令牌管理服务
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService());//客户端服务信息
        services.setSupportRefreshToken(true);//是否产生刷新令牌
        services.setTokenStore(tokenStore);//令牌存储策略
        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        services.setTokenEnhancer(tokenEnhancerChain);
        services.setAccessTokenValiditySeconds(7200);//令牌默认有效期2小时
        services.setRefreshTokenValiditySeconds(259200);//刷新令牌默认的有效期3天
        return services;
    }

    /**
     * 令牌访问端点的安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //配置允许认证的权限
        // 允许通过form提交客户端认证信息(client_id,client_secret),默认为basic方式认证
        security.allowFormAuthenticationForClients();
        // "/oauth/check_token"端点默认不允许访问
        security.checkTokenAccess("isAuthenticated()");
        // "/oauth/token_key"断点默认不允许访问
        security.tokenKeyAccess("isAuthenticated()");
    }

    /**
     * 客户端配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
        /*clients.inMemory()//使用in‐memory存储
                .withClient("c1") // client_id
                .secret(bCryptPasswordEncoder.encode("123")) // client_secret
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token") // 该client允许的授权类型
                .scopes("all") // 允许的授权范围
                .redirectUris("https://www.baidu.com")//回调地址
                .resourceIds("order-service")//资源服务器id,需要与资源服务器对应
                .autoApprove(false);//登录后绕过批准询问(/oauth/confirm_access)*/
    }

    /**
     * 令牌配置访问端点和令牌服务
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//认证管理器
                .userDetailsService(userDetailsService)//密码模式
                .authorizationCodeServices(authorizationCodeServices())//授权码模式
                .tokenServices(tokenServices())//令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);//允许POST提交
    }

}
