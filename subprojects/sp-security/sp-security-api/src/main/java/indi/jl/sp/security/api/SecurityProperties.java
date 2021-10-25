package indi.jl.sp.security.api;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JWT配置信息
 */
@ConfigurationProperties(prefix = "sp.security")
public class SecurityProperties {

    /**
     * 同用户最大同时登录数  默认同用户只能有一人登录
     */
    private Integer maximumSessions = 1;

    /**
     * 不需要权限校验的请求  例:/security/**
     */
    private List<String> authIgnores = new ArrayList<>();

    /**
     * accessToken的名称
     * header  requestParam中统一的accessToken命名 默认:sp-access-token
     */
    private String accessTokenName = "sp-access-token";

    /**
     * secretToken的名称
     * header  requestParam中统一的secretToken命名 默认:sp-secret-token
     */
    private String secretTokenName = "sp-secret-token";

    /**
     * refreshToken的名称
     * refreshToken命名 默认:sp-refresh-token
     */
    private String refreshTokenName = "sp-refresh-token";

    /**
     * 允许请求头返回
     */
    private List<String> exposeHeaders = new ArrayList<>(Collections.singletonList(accessTokenName));

    /**
     * 是否开启JWT默认false
     */
    private Boolean jwtEnable = false;

    /**
     * 是不是开始session默认false
     */
    private Boolean sessionEnable = false;


    public List<String> getAuthIgnores() {
        return authIgnores;
    }

    public void setAuthIgnores(List<String> authIgnores) {
        this.authIgnores = authIgnores;
    }

    public List<String> getExposeHeaders() {
        return exposeHeaders;
    }

    public void setExposeHeaders(List<String> exposeHeaders) {
        this.exposeHeaders = exposeHeaders;
    }

    public String getAccessTokenName() {
        return accessTokenName;
    }

    public String getRefreshTokenName() {
        return refreshTokenName;
    }

    public void setRefreshTokenName(String refreshTokenName) {
        this.refreshTokenName = refreshTokenName;
    }

    public void setAccessTokenName(String accessTokenName) {
        this.accessTokenName = accessTokenName;
    }

    public String getSecretTokenName() {
        return secretTokenName;
    }

    public void setSecretTokenName(String secretTokenName) {
        this.secretTokenName = secretTokenName;
    }

    public Boolean getJwtEnable() {
        return jwtEnable;
    }

    public void setJwtEnable(Boolean jwtEnable) {
        this.jwtEnable = jwtEnable;
    }

    public Integer getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(Integer maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public Boolean getSessionEnable() {
        return sessionEnable;
    }

    public void setSessionEnable(Boolean sessionEnable) {
        this.sessionEnable = sessionEnable;
    }
}


