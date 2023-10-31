package com.bsc.stokoin.config.security;

import com.bsc.stokoin.config.security.filter.TokenAuthenticationErrorFilter;
import com.bsc.stokoin.config.security.filter.TokenAuthenticationFilter;
import com.bsc.stokoin.config.security.handler.LoginSuccessHandler;
import com.bsc.stokoin.config.security.handler.RestAccessDeniedHandler;
import com.bsc.stokoin.config.security.handler.RestAuthenticationEntryPoint;
import com.bsc.stokoin.config.security.service.CustomOauth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOauth2Service customOauth2Service;
    private final LoginSuccessHandler loginSuccessHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final TokenAuthenticationErrorFilter tokenAuthenticationErrorFilter;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // csrf 사용안함
                .csrf(AbstractHttpConfigurer::disable)

                //h2-console 사용을 위한 설정
                .headers((headers) ->
                        headers.frameOptions((frameOptions) ->
                                frameOptions.sameOrigin()
                        )
                )
                // session 사용안함
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // form login 제거
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

//                .authorizeHttpRequests((authorize) ->
//                        authorize.anyRequest().permitAll())

                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(new RestAccessDeniedHandler()));

                // oauth2 login 설정
                // oauth2 login 을 사용하면 기본적으로 /login 으로 redirect 된다.
                // 이를 막기 위해 oauth2Login 을 사용하고, /api/v1/oauth2/authorization 으로 redirect 된다.
                // 이후, oauth2Login 의 successHandler 를 통해 로그인 성공시 처리를 진행한다.
                // 이후, oauth2Login 의 userInfoEndpoint 를 통해 사용자 정보를 가져온다.
                //
//                .oauth2Login((oauth2Login) ->
//                        oauth2Login
//                        .successHandler(loginSuccessHandler)
//                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
//                        .userService(customOauth2Service))
//                        .authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig
//                        .baseUri("/api/v1/oauth2/authorization")));

        http.addFilterBefore(tokenAuthenticationErrorFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



}
