package com.sunkang.oauth.config;

import com.sunkang.oauth.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 认证服务
 */
@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter  {

    /**
     * redis工厂，默认使用lettue
     */
    @Autowired
    public RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("wuzzClientId")//客户端得ID,比如我们在QQ互联中心申请得。可以写多个。配置 循环
                .secret(passwordEncoder().encode("wuzzSecret")) // 客户端密钥，需要进行加密
                .accessTokenValiditySeconds(7200)// token 有效时常  0 永久有效
                .authorizedGrantTypes("password", "implicit", "refresh_token", "authorization_code")// 支持得授权类型
//                .redirectUris("http://www.baidu.com")//回调地址
                .scopes("all", "read", "write");//拥有的 scope  可选
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.userDetailsService(userDetailsService()) // 用户信息得服务，一版是都数据库
                .authenticationManager(authenticationManager())// 认证管理器。
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                //配置token存储的服务与位置
                .tokenServices(tokenService())
                .tokenStore(tokenStore());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()//允许表单登录
                .checkTokenAccess("permitAll()"); //开启/oauth/check_token验证端口认证权限访问
    }

    @Bean // 注入认证管理器
    public AuthenticationManager authenticationManager() {
        AuthenticationManager authenticationManager = new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return daoAuthenticationProvider().authenticate(authentication);
            }
        };
        return authenticationManager;
    }

    @Bean//注入认证器
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean//注入 用户信息服务
    public UserDetailsService userDetailsService() {
        return new MyUserDetailService();
    }

    @Bean//注入密码加密
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public TokenStore tokenStore() {
        //使用redis存储token
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        //设置redis token存储中的前缀
        redisTokenStore.setPrefix("auth-token:");
        return redisTokenStore;
    }

    @Bean
    public DefaultTokenServices tokenService() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        //配置token存储
        tokenServices.setTokenStore(tokenStore());
        //开启支持refresh_token，此处如果之前没有配置，启动服务后再配置重启服务，可能会导致不返回token的问题，解决方式：清除redis对应token存储
        tokenServices.setSupportRefreshToken(true);
        //复用refresh_token
        tokenServices.setReuseRefreshToken(true);
        //token有效期，设置12小时
        tokenServices.setAccessTokenValiditySeconds(12 * 60 * 60);
        //refresh_token有效期，设置一周
        tokenServices.setRefreshTokenValiditySeconds(7 * 24 * 60 * 60);
        return tokenServices;
    }
}
