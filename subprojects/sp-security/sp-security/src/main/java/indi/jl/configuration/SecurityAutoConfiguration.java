package indi.jl.configuration;

import indi.jl.sp.core.advice.ControllerExceptionAdvice;
import indi.jl.sp.core.exception.SpSystemException;
import indi.jl.sp.security.api.SecurityProperties;
import indi.jl.sp.security.api.filter.RequestHeaderOrParamSecretTokenFilter;
import indi.jl.sp.security.api.handler.*;
import indi.jl.sp.security.handler.defaults.access.DefaultAccessDeniedHandler;
import indi.jl.sp.security.handler.defaults.access.DefaultAuthenticationEntryPoint;
import indi.jl.sp.security.handler.defaults.auth.DefaultCustomMetadataSourceHandler;
import indi.jl.sp.security.handler.defaults.auth.DefaultUrlAccessDecisionManagerHandler;
import indi.jl.sp.security.handler.defaults.login.DefaultLoginFailureHandler;
import indi.jl.sp.security.handler.defaults.login.DefaultLoginSuccessHandler;
import indi.jl.sp.security.handler.defaults.login.DefaultLogoutSuccessHandler;
import indi.jl.sp.security.context.SpHttpSessionJwtSecurityContextRepository;
import indi.jl.sp.security.api.encoder.Md5PasswordEncoder;
import indi.jl.sp.security.api.filter.RequestHeaderOrParamAccessTokenFilter;
import indi.jl.sp.security.provider.SpDaoAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@AutoConfigureAfter(SecurityApiAutoConfiguration.class)
public class SecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private FilterInvocationSecurityMetadataSource customMetadataSourceHandler;

    @Autowired
    private ControllerExceptionAdvice controllerExceptionAdvice;

    @Autowired
    private AccessDecisionManager accessDecisionManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private SecretTokenAuthenticationManagerHandler secretTokenAuthenticationManagerHandler;

    @Autowired
    private AccessTokenAuthenticationManagerHandler accessTokenAuthenticationManagerHandler;

    @Bean
    @ConditionalOnMissingBean(UserDetailsHandler.class)
    public UserDetailsService defaultUserDetailsHandler() {
        return username -> {
            throw new SpSystemException("The login request was not implemented");
        };
    }

    @Bean
    @ConditionalOnMissingBean(AccessDecisionManager.class)
    public AccessDecisionManager defaultUrlAccessDecisionManagerHandler() {
        return new DefaultUrlAccessDecisionManagerHandler();
    }

    @Bean
    @ConditionalOnMissingBean(FilterInvocationSecurityMetadataSource.class)
    public FilterInvocationSecurityMetadataSource defaultCustomMetadataSourceHandler() {
        return new DefaultCustomMetadataSourceHandler();
    }

    @Bean
    @ConditionalOnMissingBean(LoginSuccessHandler.class)
    public LoginSuccessHandler defaultLoginSuccessHandler() {
        return new DefaultLoginSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(LoginFailHandler.class)
    public LoginFailHandler defaultLoginFailureHandler() {
        return new DefaultLoginFailureHandler(controllerExceptionAdvice);
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler defaultLogoutSuccessHandler() {
        return new DefaultLogoutSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AccessTokenAuthenticationManagerHandler.class)
    public AccessTokenAuthenticationManagerHandler defaultAccessTokenAuthenticationManagerHandler() {
        return authentication -> {
            throw new SpSystemException("DefaultAccessTokenAuthenticationManagerHandler was not implemented, have a try import sp-jwt");
        };
    }

    @Bean
    @ConditionalOnMissingBean(SecretTokenAuthenticationManagerHandler.class)
    public SecretTokenAuthenticationManagerHandler defaultSecretTokenAuthenticationManagerHandler() {
        return authentication -> {
            throw new SpSystemException("DefaultSecretTokenAuthenticationManagerHandler was not implemented, have a try import sp-jwt");
        };
    }

    @Bean
    @ConditionalOnMissingBean(AccessDeniedHandler.class)
    public DefaultAccessDeniedHandler defaultAccessDeniedHandler() {
        return new DefaultAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
    public DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint() {
        return new DefaultAuthenticationEntryPoint();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //?????????????????????url??????
        //??????????????????????????????
        securityProperties.getAuthIgnores().add("/actuator/health");
        securityProperties.getAuthIgnores().add("/error");
        securityProperties.getAuthIgnores().add("/login/fail");
        securityProperties.getAuthIgnores().add("/logout/success");
        securityProperties.getAuthIgnores().add("/jwt/token/refresh");
        web.ignoring().antMatchers(securityProperties.getAuthIgnores().toArray(new String[securityProperties.getAuthIgnores().size()]));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //?????????????????????????????????Provider
        //????????????BCryptPasswordEncoder????????????????????????  daoProvider.setPasswordEncoder();
        //???????????????spring-security???????????????DaoAuthenticationProvider.additionalAuthenticationChecks
        SpDaoAuthenticationProvider daoProvider = new SpDaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        daoProvider.setPasswordEncoder(new Md5PasswordEncoder());
        //????????????Provider  daoProvider??????UserNameAndPasswordToken????????????
        //JwtAuthenticationProvider??????JWT???JWTAuthenticationToken??????  spring-security?????????Token??????????????????
        auth.authenticationProvider(daoProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //?????????????????????
        http.headers().addHeaderWriter(new StaticHeadersWriter(accessControlWriteHeader()));
        //?????????????????????
        //??????JWT??????????????? ???????????????,?????????authIgnore????????? ??????TOKEN
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O filterSecurityInterceptor) {
                        filterSecurityInterceptor.setSecurityMetadataSource(customMetadataSourceHandler);
                        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);
                        return filterSecurityInterceptor;
                    }
                }).anyRequest().permitAll();
        //??????csrf
        http.csrf().disable();
        if (userDetailsService instanceof UserDetailsHandler) {
            //??????????????????????????????????????????????????????
            //??????????????????spring-security?????????form??????
            http.formLogin()
                    .successForwardUrl("/login/success")
                    .failureForwardUrl("/login/fail")
                    .and()
                    .logout()
                    //?????????????????????????????????
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/logout/success");
        } else {
            http.formLogin().disable();
        }
        //session?????? ????????????session
        if (securityProperties.getSessionEnable()) {
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .maximumSessions(securityProperties.getMaximumSessions());
        } else {
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
        http.exceptionHandling()
                //???????????????????????????
                .accessDeniedHandler(accessDeniedHandler)
                //??????????????????????????????
                .authenticationEntryPoint(authenticationEntryPoint);
        //??????????????????
        http.anonymous().disable();
        if (securityProperties.getJwtEnable()) {
            //??????JWT???session??????  JWT????????????session
            http.securityContext().securityContextRepository(new SpHttpSessionJwtSecurityContextRepository());
            http.addFilterBefore(requestHeaderOrParamAccessTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            http.addFilterBefore(requestHeaderOrParamSecretTokenFilter(), RequestHeaderOrParamAccessTokenFilter.class);
        }
    }

    public RequestHeaderOrParamAccessTokenFilter requestHeaderOrParamAccessTokenFilter() {
        RequestHeaderOrParamAccessTokenFilter requestHeaderOrParamAccessTokenFilter = new RequestHeaderOrParamAccessTokenFilter();
        requestHeaderOrParamAccessTokenFilter.setPrincipalRequestParam(securityProperties.getAccessTokenName());
        requestHeaderOrParamAccessTokenFilter.setPrincipalRequestHeader(securityProperties.getAccessTokenName());
        requestHeaderOrParamAccessTokenFilter.setAuthenticationManager(accessTokenAuthenticationManagerHandler);
        requestHeaderOrParamAccessTokenFilter.setCheckForPrincipalChanges(true);
        requestHeaderOrParamAccessTokenFilter.setInvalidateSessionOnPrincipalChange(false);
        requestHeaderOrParamAccessTokenFilter.setContinueFilterChainOnUnsuccessfulAuthentication(false);
        return requestHeaderOrParamAccessTokenFilter;
    }

    public RequestHeaderOrParamSecretTokenFilter requestHeaderOrParamSecretTokenFilter() {
        RequestHeaderOrParamSecretTokenFilter requestHeaderOrParamSecretTokenFilter = new RequestHeaderOrParamSecretTokenFilter();
        requestHeaderOrParamSecretTokenFilter.setPrincipalRequestParam(securityProperties.getSecretTokenName());
        requestHeaderOrParamSecretTokenFilter.setPrincipalRequestHeader(securityProperties.getSecretTokenName());
        requestHeaderOrParamSecretTokenFilter.setAuthenticationManager(secretTokenAuthenticationManagerHandler);
        requestHeaderOrParamSecretTokenFilter.setCheckForPrincipalChanges(true);
        requestHeaderOrParamSecretTokenFilter.setInvalidateSessionOnPrincipalChange(false);
        requestHeaderOrParamSecretTokenFilter.setContinueFilterChainOnUnsuccessfulAuthentication(false);
        return requestHeaderOrParamSecretTokenFilter;
    }

    /**
     * ???????????????
     *
     * @return ???????????????
     */
    @Bean
    public List<Header> accessControlWriteHeader() {
        List<Header> headers = new ArrayList<>();
        //???????????????header
        Header exposeHeader = new Header("Access-Control-Expose-Headers",
                securityProperties.getExposeHeaders()
                        .toArray(new String[securityProperties.getExposeHeaders().size()]));
        headers.add(exposeHeader);
        return headers;
    }

}