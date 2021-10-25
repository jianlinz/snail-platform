package indi.jl.sp.jwt;

import indi.jl.sp.core.constant.DateConstant;
import indi.jl.sp.core.util.StringUtil;
import indi.jl.sp.jwt.enums.JwtType;
import indi.jl.sp.jwt.pojo.JwtHeader;
import indi.jl.sp.jwt.pojo.JwtPayload;
import indi.jl.sp.core.SpApplicationContext;
import indi.jl.sp.core.util.JsonUtil;
import indi.jl.sp.security.api.pojo.SecurityUserDetail;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

public class JwtUtil {

    private JwtUtil() {
    }

    public static JwtHeader getHeader(String token) {
        try {
            String[] split = token.split("\\.");
            String headerStr = split[0];
            JwtHeader jwtHeader = JsonUtil.parse(new String(Base64.decodeBase64(headerStr)), JwtHeader.class);
            if (null == jwtHeader || StringUtils.isEmpty(jwtHeader.getAlg())) {
                throw new ProviderNotFoundException("Token header message missing!");
            }
            return jwtHeader;
        } catch (Exception e) {
            throw new PreAuthenticatedCredentialsNotFoundException("Token format error!");
        }
    }


    public static JwtPayload getPayload(String token) {
        try {
            String[] split = token.split("\\.");
            String payloadStr = split[1];
            JwtPayload jwtPayload = JsonUtil.parse(new String(Base64.decodeBase64(payloadStr)), JwtPayload.class);
            if (null == jwtPayload || StringUtils.isEmpty(jwtPayload.getUserName())) {
                throw new ProviderNotFoundException("Token payload message missing!");
            }
            return jwtPayload;
        } catch (Exception e) {
            throw new PreAuthenticatedCredentialsNotFoundException("Token format error!");
        }
    }

    /**
     * 验证token合法性
     *
     * @param token token
     */
    public static void verify(String token) {
        if (StringUtil.isEmpty(token)) {
            throw new PreAuthenticatedCredentialsNotFoundException("Token is empty!");
        }
        //不满足Jwt格式直接返回
        if (!JwtUtil.isJwtToken(token)) {
            throw new ProviderNotFoundException("Token format error!");
        }
        //解析token
        String[] split = token.split("\\.");
        String headerBase64 = split[0];
        String payloadBase64 = split[1];
        String sign = split[2];
        JwtHeader jwtHeader = getHeader(token);
        JwtPayload jwtPayload = getPayload(token);
        boolean access = sign.equals(JwtUtil.generateSign(headerBase64, payloadBase64, generateSalt(jwtPayload)));
        if (!access) {
            throw new PreAuthenticatedCredentialsNotFoundException("Token is invalid!");
        }
        if (null == jwtPayload.getExp() || jwtPayload.getExp().isBefore(LocalDateTime.now())) {
            throw new AccountExpiredException("Token expired!");
        }
    }

    /**
     * 根据用户信息生成accessToken
     *
     * @param user 用户
     * @return token
     */
    public static String generateAccessToken(SecurityUserDetail user) {
        JwtHeader jwtHeader = composeJwtHeader(JwtType.ACCESS);
        JwtPayload payload = JwtUtil.composeJwtPayload(jwtHeader, user);
        return generateToken(jwtHeader, payload);
    }

    /**
     * 根据用户信息生成刷新token
     *
     * @param user 用户
     * @return token
     */
    public static String generateRefreshToken(SecurityUserDetail user) {
        JwtHeader jwtHeader = composeJwtHeader(JwtType.REFRESH);
        JwtPayload payload = JwtUtil.composeJwtPayload(jwtHeader, user);
        return generateToken(jwtHeader, payload);
    }


    /**
     * 根据用户信息生成secretToken
     * <p>
     * 注意secretToken 由于有效期无限长 且 通常权限赋予的比较敏感  所以建议仅在应用内部访问使用  不暴露在前端
     *
     * @param user 用户
     * @return token
     */
    public static String generateSecretToken(SecurityUserDetail user) {
        JwtHeader jwtHeader = composeJwtHeader(JwtType.SECRET);
        JwtPayload payload = JwtUtil.composeJwtPayload(jwtHeader, user);
        return generateToken(jwtHeader, payload);
    }

    /**
     * 根据头部负载和中部负载生成 token
     *
     * @param jwtHeader 头部负载
     * @param payload   中部负载
     * @return token
     */
    private static String generateToken(JwtHeader jwtHeader, JwtPayload payload) {
        if (null == jwtHeader) {
            throw new PreAuthenticatedCredentialsNotFoundException("generateToken error header cant be null");
        }
        if (null == jwtHeader.getTyp()) {
            throw new ProviderNotFoundException("generateToken error typ cant be empty");
        }
        if (null == payload) {
            throw new PreAuthenticatedCredentialsNotFoundException("generateToken error payload cant be null");
        }
        if (StringUtil.isEmpty(payload.getUserName())) {
            throw new ProviderNotFoundException("generateToken error userName cant be empty");
        }

        switch (jwtHeader.getTyp()) {
            case REFRESH:
                payload.setExp(LocalDateTime.now().plusHours(SpApplicationContext.getBean(JwtProperties.class).getRefreshTokenExp().toHours()));
                break;
            case SECRET:
                payload.setExp(LocalDateTime.now().plusHours(SpApplicationContext.getBean(JwtProperties.class).getSecretTokenExp().toHours()));
                break;
            case ACCESS:
            default:
                payload.setExp(LocalDateTime.now().plusHours(SpApplicationContext.getBean(JwtProperties.class).getAccessTokenExp().toHours()));
        }
        String headerStr = JsonUtil.toJsonString(jwtHeader);
        String payloadStr = JsonUtil.toJsonString(payload);
        String headerBase64 = JwtUtil.base64(headerStr);
        String payloadBase64 = JwtUtil.base64(payloadStr);
        String sign = generateSign(headerBase64, payloadBase64, generateSalt(payload));
        //根据签名拼接好Jwt的三段信息作为token返回
        return StringUtil.join(new String[]{headerBase64, payloadBase64, sign}, ".");
    }

    public static JwtHeader composeJwtHeader(JwtType jwtType) {
        JwtHeader header = new JwtHeader();
        if (null == jwtType) {
            throw new ProviderNotFoundException("generateToken error jwtType cant be empty");
        }
        header.setAlg(SpApplicationContext.getBean(JwtProperties.class).getAlg());
        header.setTyp(jwtType);
        return header;
    }

    public static JwtPayload composeJwtPayload(JwtHeader jwtHeader, SecurityUserDetail user) {
        if (null == user) {
            throw new PreAuthenticatedCredentialsNotFoundException("generateUserToken error user cant be null");
        }
        if (StringUtil.isEmpty(user.getUserName())) {
            throw new ProviderNotFoundException("generateUserToken error userName cant be empty");
        }
        JwtPayload payload = new JwtPayload();
        payload.setUserName(user.getUserName());
        if (jwtHeader.getTyp() == JwtType.REFRESH) {
            return payload;
        }
        payload.setUserId(user.getId());
        payload.setName(user.getName());
        payload.setRoles(user.getRoles());
        payload.setExtMessage(user.getExtMessage());
        return payload;
    }

    /**
     * 动态盐获取
     * <p>
     * 动态盐算法
     * MD5 （ username + 配置盐sp.jwt.salt + exp时间-4h-2m-30m）
     *
     * @param jwtPayload 中部负载
     * @return 动态盐
     */
    public static String generateSalt(JwtPayload jwtPayload) {
        if (StringUtil.isEmpty(jwtPayload.getUserName())) {
            throw new ProviderNotFoundException("generateUserToken error userName cant be empty");
        }
        if (null == jwtPayload.getExp()) {
            throw new ProviderNotFoundException("generateUserToken error exp cant be empty");
        }
        return DigestUtils.md5Hex(jwtPayload.getUserName()
                + SpApplicationContext.getBean(JwtProperties.class).getSalt()
                + jwtPayload.getExp().minusHours(4).minusMinutes(2).minusSeconds(30).format(DateConstant.DATE_TIME_FORMATTER));
    }

    /**
     * 根据JWT第一段 第二段 和secret生成签名
     *
     * @param headerBase64  JWT第一段BASE64
     * @param payloadBase64 JWT第二段BASE64
     * @param salt          动态盐
     * @return 签名
     */
    public static String generateSign(String headerBase64, String payloadBase64, String salt) {
        return hmacSha256Base64(salt, headerBase64 + "." + payloadBase64);
    }

    public static String hmacSha256Base64(String secret, String message) {
        return Base64.encodeBase64URLSafeString(new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secret).hmac(message));
    }

    public static String base64(String str) {
        return Base64.encodeBase64URLSafeString(str.getBytes(Charset.forName("UTF-8")));
    }

    public static boolean isJwtToken(String token) {
        return token.contains(".") && token.split("\\.").length == 3;
    }

}
