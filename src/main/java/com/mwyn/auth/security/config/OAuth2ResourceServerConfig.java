package com.mwyn.auth.security.config;
import javax.sql.DataSource;

import com.mwyn.auth.persistence.service.OAuthUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter  {

    private static final String REST_RESOURCE_ID = "mwyn-resource";

    @Autowired
    @Qualifier("oauthDataSource")
    DataSource dataSource;

    @Autowired
    private OAuthUserDetailService userDetailsService;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(REST_RESOURCE_ID).stateless(false);
    }


    @Bean(name="oauthAuthenticationEntryPoint")
    public OAuth2AuthenticationEntryPoint oauthAuthenticationEntryPoint() {
        OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
        entryPoint.setRealmName("mwyn-auth");
        return entryPoint;
    }

    @Bean
    public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
        OAuth2AccessDeniedHandler accessDeniedHandler = new OAuth2AccessDeniedHandler();
        return accessDeniedHandler;
    }


    @Autowired
    private OAuth2AuthenticationEntryPoint oauthAuthenticationEntryPoint;

    @Autowired
    private OAuth2AccessDeniedHandler oauthAccessDeniedHandler;



    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.userDetailsService(userDetailsService)
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers("/v1/**").hasRole("ADMIN")
                .and()
                .httpBasic()
//                .authenticationEntryPoint(oauthAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().accessDeniedHandler(oauthAccessDeniedHandler);

    }

}