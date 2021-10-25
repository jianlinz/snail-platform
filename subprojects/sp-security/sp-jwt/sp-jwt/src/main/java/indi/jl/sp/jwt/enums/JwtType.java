package indi.jl.sp.jwt.enums;

/**
 * JWT类型 目前仅支持COMMON 通用型
 */
public enum JwtType {
    /**
     * access token
     */
    ACCESS,
    /**
     * 刷新token
     */
    REFRESH,
    /**
     * secretToken
     */
    SECRET
}
