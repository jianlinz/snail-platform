package indi.jl.sp.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * JWT配置信息
 */
@ConfigurationProperties(prefix = "sp.jwt")
public class JwtProperties {

    /**
     * accessToken 有效期 默认:2h 配置示例,如:1000ms  or 3h or 100s
     */
    private Duration accessTokenExp = Duration.ofHours(2);

    /**
     * 刷新token有效期 默认:30d 配置示例,如:1000ms  or 3h or 100s
     */
    private Duration refreshTokenExp = Duration.ofDays(30);

    /**
     * secret有效期 用于跨系统访问 或系统内部匿名访问  理论有效期为无限 默认:99999d 配置示例,如:1000ms  or 3h or 100s
     */
    private Duration secretTokenExp = Duration.ofDays(99999);

    /**
     * 加密方式
     * 目前仅支持HS256
     */
    private String alg = "HS256";

    /**
     * 权限验证中心名称 默认auth-jwt-manager
     */
    private String authApplicationName = "auth-jwt-manager";

    /**
     * jwt 签名盐  相同盐的jwt可以服务间共享验证
     */
    private String salt = "eyJ1c2VySWQiOjIsInVzZ";

    public Duration getAccessTokenExp() {
        return accessTokenExp;
    }

    public void setAccessTokenExp(Duration accessTokenExp) {
        this.accessTokenExp = accessTokenExp;
    }

    public String getAuthApplicationName() {
        return authApplicationName;
    }

    public void setAuthApplicationName(String authApplicationName) {
        this.authApplicationName = authApplicationName;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Duration getRefreshTokenExp() {
        return refreshTokenExp;
    }

    public void setRefreshTokenExp(Duration refreshTokenExp) {
        this.refreshTokenExp = refreshTokenExp;
    }

    public Duration getSecretTokenExp() {
        return secretTokenExp;
    }

    public void setSecretTokenExp(Duration secretTokenExp) {
        this.secretTokenExp = secretTokenExp;
    }
}
