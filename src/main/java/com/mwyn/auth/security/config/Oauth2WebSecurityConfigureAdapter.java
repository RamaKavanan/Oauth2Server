package com.mwyn.auth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.mwyn.auth.persistence.service.OAuthUserDetailService;

@Configuration
@EnableWebSecurity
@Order(Ordered.LOWEST_PRECEDENCE-3)
public class Oauth2WebSecurityConfigureAdapter extends WebSecurityConfigurerAdapter{


    @Autowired
    private OAuthUserDetailService userDetailsService;

    @Autowired
    protected void registerAuthentication(
            final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
           .antMatchers("/resources/**").antMatchers("/favicon.ico", "/swagger.json"); // #3
    }


}